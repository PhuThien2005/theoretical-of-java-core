import os
import json
import asyncio
import subprocess
import sys
import edge_tts

VOICE = "vi-VN-HoaiMyNeural"
PROJECT_DIR = "/home/fhu_thjen/projects/learning-java"
SEMAPHORE_LIMIT = 4  # Max 4 concurrent edge-tts server connections to prevent rate limit

async def get_marked_blocks(md_path):
    cmd = ["node", "scratch_extract_blocks.js", md_path]
    env = os.environ.copy()
    env["NODE_PATH"] = os.path.join(PROJECT_DIR, "node_modules")
    
    proc = await asyncio.create_subprocess_exec(
        *cmd,
        stdout=asyncio.subprocess.PIPE,
        stderr=asyncio.subprocess.PIPE,
        env=env,
        cwd=PROJECT_DIR
    )
    stdout, stderr = await proc.communicate()
    if proc.returncode != 0:
        raise RuntimeError(f"Node script failed: {stderr.decode('utf-8')}")
        
    return json.loads(stdout.decode('utf-8'))

async def generate_readme_audio(readme_path, sem):
    chapter_dir = os.path.dirname(readme_path)
    audio_dir = os.path.join(chapter_dir, 'audio')
    os.makedirs(audio_dir, exist_ok=True)
    
    mp3_path = os.path.join(audio_dir, 'README.mp3')
    json_path = os.path.join(audio_dir, 'README.json')
    
    chapter_name = os.path.basename(chapter_dir)
    
    try:
        blocks = await get_marked_blocks(readme_path)
    except Exception as e:
        print(f"❌ Failed to parse DOM blocks for {chapter_name} README: {e}", flush=True)
        return False
        
    if not blocks:
        return False
        
    chunks = []
    current_chunk = []
    current_char_count = 0
    
    for idx, b in enumerate(blocks):
        current_chunk.append((idx, b))
        current_char_count += len(b)
        if len(current_chunk) >= 5 or current_char_count >= 4000:
            chunks.append(current_chunk)
            current_chunk = []
            current_char_count = 0
    if current_chunk:
        chunks.append(current_chunk)
        
    timestamps = []
    current_offset = 0.0
    full_audio_bytes = bytearray()
    
    for chunk in chunks:
        texts = [item[1] for item in chunk]
        full_chunk_text = " . ".join(texts)
        
        chunk_audio_bytes = bytearray()
        cues = []
        
        async with sem:
            communicate = edge_tts.Communicate(full_chunk_text, VOICE)
            try:
                async for item in communicate.stream():
                    if item["type"] == "audio":
                        chunk_audio_bytes.extend(item["data"])
                    elif item["type"] == "SentenceBoundary":
                        cues.append({
                            "start": item["offset"] / 10000000.0,
                            "duration": item["duration"] / 10000000.0,
                            "text": item["text"]
                        })
            except Exception as e:
                pass
                
        full_audio_bytes.extend(chunk_audio_bytes)
        
        cue_idx = 0
        chunk_offset = 0.0
        
        for original_idx, b_text in chunk:
            b_clean = b_text.strip().lower()
            start_time = None
            end_time = 0.0
            
            while cue_idx < len(cues):
                c_text = cues[cue_idx]["text"].strip().lower()
                if c_text in b_clean or b_clean in c_text or (len(c_text) > 5 and c_text[:5] in b_clean):
                    if start_time is None:
                        start_time = cues[cue_idx]["start"]
                    end_time = cues[cue_idx]["start"] + cues[cue_idx]["duration"]
                    cue_idx += 1
                else:
                    break
                    
            if start_time is None:
                start_time = chunk_offset
                duration_est = len(b_text.split()) * 0.35
                end_time = chunk_offset + duration_est
                
            block_duration = max(0.4, round(end_time - start_time, 2))
            block_start = round(current_offset + start_time, 2)
            block_end = round(block_start + block_duration, 2)
            
            timestamps.append({
                "paragraph_id": original_idx + 1,
                "start": block_start,
                "end": block_end,
                "duration": block_duration,
                "text_snippet": b_text[:80]
            })
            
            chunk_offset = end_time
            
        if cues:
            chunk_total_duration = cues[-1]["start"] + cues[-1]["duration"]
        else:
            chunk_total_duration = sum(len(b_text.split()) * 0.35 for _, b_text in chunk)
            
        current_offset += chunk_total_duration
            
    # Save files
    with open(mp3_path, 'wb') as f:
        f.write(full_audio_bytes)
        
    out_data = {
        "lesson": "README",
        "total_duration_seconds": round(current_offset, 2),
        "total_paragraphs": len(timestamps),
        "timestamps": timestamps
    }
    
    with open(json_path, 'w', encoding='utf-8') as f:
        json.dump(out_data, f, indent=2, ensure_ascii=False)
        
    print(f"✅ Generated audio & timestamps for {chapter_name} README!", flush=True)
    return True

async def main():
    vi_dir = os.path.join(PROJECT_DIR, 'vi')
    tasks = []
    sem = asyncio.Semaphore(SEMAPHORE_LIMIT)
    
    for d in sorted(os.listdir(vi_dir)):
        chapter_path = os.path.join(vi_dir, d)
        if os.path.isdir(chapter_path) and d.startswith('no'):
            readme_path = os.path.join(chapter_path, 'README.md')
            if os.path.exists(readme_path):
                tasks.append(generate_readme_audio(readme_path, sem))
                
    print(f"🚀 Starting audio generation for {len(tasks)} README files...", flush=True)
    results = await asyncio.gather(*tasks)
    success_count = sum(1 for r in results if r)
    print(f"🎉 Completed! Successfully generated {success_count}/{len(tasks)} README audio files.", flush=True)

if __name__ == '__main__':
    asyncio.run(main())
