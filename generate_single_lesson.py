import sys
import os
import json
import asyncio
import edge_tts

VOICE = "vi-VN-HoaiMyNeural"
PROJECT_DIR = "/home/fhu_thjen/projects/learning-java"
SEMAPHORE_LIMIT = 2  # Keep it low to prevent rate limits from edge-tts

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

async def process_chunk_parallel(chunk_idx, chunk, sem):
    texts = [item[1] for item in chunk]
    full_chunk_text = " . ".join(texts)
    
    cues = []
    chunk_audio_bytes = bytearray()
    
    # Retry up to 5 times for resilience
    for attempt in range(5):
        async with sem:
            cues = []
            chunk_audio_bytes = bytearray()
            try:
                communicate = edge_tts.Communicate(full_chunk_text, VOICE)
                async for item in communicate.stream():
                    if item["type"] == "audio":
                        chunk_audio_bytes.extend(item["data"])
                    elif item["type"] == "SentenceBoundary":
                        cues.append({
                            "start": item["offset"] / 10000000.0,
                            "duration": item["duration"] / 10000000.0,
                            "text": item["text"]
                        })
                if len(chunk_audio_bytes) > 0:
                    break
            except Exception as e:
                print(f"   [Chunk {chunk_idx}] Attempt {attempt+1} failed: {e}. Retrying...", flush=True)
                await asyncio.sleep(2)
                
    if len(chunk_audio_bytes) == 0:
        raise RuntimeError(f"Failed to generate audio for chunk {chunk_idx} after 5 attempts.")
        
    return chunk_idx, cues, chunk_audio_bytes

async def main():
    if len(sys.argv) < 3:
        print("Usage: python3 generate_single_lesson.py <md_path> <json_path>")
        sys.exit(1)
        
    md_path = sys.argv[1]
    json_path = sys.argv[2]
    
    mp3_path = os.path.splitext(json_path)[0] + ".mp3"
    lesson_name = os.path.splitext(os.path.basename(md_path))[0]
    
    blocks = await get_marked_blocks(md_path)
    if not blocks:
        print(f"Empty blocks for {lesson_name}")
        return
        
    chunks = []
    current_chunk = []
    current_char_count = 0
    
    # Split into smaller chunks to prevent edge-tts timeout/truncation
    for idx, b in enumerate(blocks):
        current_chunk.append((idx, b))
        current_char_count += len(b)
        if len(current_chunk) >= 4 or current_char_count >= 2500:
            chunks.append(current_chunk)
            current_chunk = []
            current_char_count = 0
    if current_chunk:
        chunks.append(current_chunk)
        
    sem = asyncio.Semaphore(SEMAPHORE_LIMIT)
    tasks = [process_chunk_parallel(i, c, sem) for i, c in enumerate(chunks)]
    
    try:
        chunk_results = await asyncio.gather(*tasks)
    except Exception as e:
        print(f"❌ Failed generating lesson {lesson_name}: {e}")
        sys.exit(1)
        
    # Sort by chunk index to maintain order
    chunk_results.sort(key=lambda x: x[0])
    
    timestamps = []
    current_offset = 0.0
    full_audio_bytes = bytearray()
    
    for chunk_idx, cues, chunk_audio_bytes in chunk_results:
        chunk = chunks[chunk_idx]
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
        
    out_data = {
        "lesson": lesson_name,
        "total_duration_seconds": round(current_offset, 2),
        "total_paragraphs": len(timestamps),
        "timestamps": timestamps
    }
    
    # Save JSON timestamps
    os.makedirs(os.path.dirname(json_path), exist_ok=True)
    with open(json_path, 'w', encoding='utf-8') as f:
        json.dump(out_data, f, indent=2, ensure_ascii=False)
        
    # Save MP3 audio file
    if full_audio_bytes:
        with open(mp3_path, 'wb') as f:
            f.write(full_audio_bytes)
        print(f"SUCCESS: {lesson_name} (JSON & MP3 generated)")
    else:
        print(f"SUCCESS: {lesson_name} (JSON generated, MP3 empty)")

if __name__ == '__main__':
    asyncio.run(main())
