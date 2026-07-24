import os
import sys
import json
import subprocess
import argparse

PROJECT_DIR = "/home/fhu_thjen/projects/learning-java"

def get_markdown_blocks(md_path):
    cmd = ["node", "scratch_extract_blocks.js", md_path]
    env = os.environ.copy()
    env["NODE_PATH"] = os.path.join(PROJECT_DIR, "node_modules")
    
    res = subprocess.run(cmd, capture_output=True, text=True, env=env, cwd=PROJECT_DIR)
    if res.returncode != 0:
        raise RuntimeError(f"Failed to run scratch_extract_blocks.js: {res.stderr}")
    return json.loads(res.stdout)

def verify_alignment(md_path, json_path, mp3_path):
    issues = []
    
    # 1. Check file existence
    if not os.path.exists(md_path):
        return [f"Markdown file does not exist: {md_path}"]
    
    if not os.path.exists(json_path):
        return [f"Timestamps JSON file does not exist: {json_path}"]
        
    if not os.path.exists(mp3_path):
        return [f"Audio MP3 file does not exist: {mp3_path}"]
        
    # Check MP3 size
    mp3_size = os.path.getsize(mp3_path)
    if mp3_size < 10240: # 10KB
        issues.append(f"Audio MP3 file is suspiciously small ({mp3_size} bytes): {mp3_path}")
        
    # 2. Parse Markdown blocks
    try:
        blocks = get_markdown_blocks(md_path)
    except Exception as e:
        return [f"Failed to parse markdown blocks: {e}"]
        
    # 3. Parse JSON timestamps
    try:
        with open(json_path, 'r', encoding='utf-8') as f:
            data = json.load(f)
    except Exception as e:
        return [f"Failed to parse JSON file: {e}"]
        
    timestamps = data.get("timestamps", [])
    
    # 4. Compare length
    if len(blocks) != len(timestamps):
        issues.append(f"Count mismatch: Markdown has {len(blocks)} blocks, but JSON has {len(timestamps)} timestamps.")
        
    # 5. Compare content snippets
    min_len = min(len(blocks), len(timestamps))
    for i in range(min_len):
        b_text = blocks[i].strip()
        ts = timestamps[i]
        ts_snippet = ts.get("text_snippet", "").strip()
        
        # Check paragraph_id
        if ts.get("paragraph_id") != (i + 1):
            issues.append(f"Index mismatch at position {i+1}: expected paragraph_id {i+1}, found {ts.get('paragraph_id')}")
            
        # Check snippet alignment
        clean_b = b_text.lower()
        clean_ts = ts_snippet.lower()
        
        if "đoạn mã mẫu java:" in clean_ts:
            continue
            
        prefix_len = len(ts_snippet)
        b_prefix = b_text[:prefix_len].strip().lower()
        
        if not (clean_ts in clean_b or b_prefix in clean_ts or clean_ts[:15] in clean_b[:15]):
            issues.append(f"Content mismatch at paragraph {i+1}:\n  Markdown: {b_text[:50]}...\n  JSON snippet: {ts_snippet}...")
            
    return issues

def main():
    parser = argparse.ArgumentParser(description="Verify alignment between localized markdown text and edge-tts audio metadata.")
    parser.add_argument("--topic", help="Name of the topic folder, e.g., no12_exception_handling")
    args = parser.parse_args()
    
    vi_dir = os.path.join(PROJECT_DIR, 'vi')
    
    targets = []
    
    if args.topic:
        topic_path = os.path.join(vi_dir, args.topic)
        if not os.path.exists(topic_path):
            print(f"❌ Topic folder {args.topic} does not exist in vi/")
            sys.exit(1)
        for root, dirs, files in os.walk(topic_path):
            if 'theory' in root and not root.endswith('audio'):
                for f in files:
                    if f.endswith('.md') and not f.startswith('anki') and not f.startswith('study-notes'):
                        targets.append(os.path.join(root, f))
    else:
        for root, dirs, files in sorted(os.walk(vi_dir)):
            if 'theory' in root and not root.endswith('audio'):
                for f in sorted(files):
                    if f.endswith('.md') and not f.startswith('anki') and not f.startswith('study-notes'):
                        targets.append(os.path.join(root, f))
                        
    if not targets:
        print("No lesson files found to verify.")
        sys.exit(0)
        
    print(f"🔎 Scanning and verifying alignment for {len(targets)} lessons...")
    
    all_passed = True
    for idx, md_path in enumerate(targets):
        rel_path = os.path.relpath(md_path, PROJECT_DIR)
        base_name = os.path.splitext(os.path.basename(md_path))[0]
        theory_dir = os.path.dirname(md_path)
        json_path = os.path.join(theory_dir, 'audio', base_name + '.json')
        mp3_path = os.path.join(theory_dir, 'audio', base_name + '.mp3')
        
        issues = verify_alignment(md_path, json_path, mp3_path)
        if issues:
            all_passed = False
            print(f"❌ ERROR: Alignment failed for {rel_path}:")
            for iss in issues:
                print(f"   - {iss}")
        else:
            # print(f"✅ PASSED: {rel_path}")
            pass
            
    if all_passed:
        print(f"🎉 SUCCESS: All scanned lessons are 100% aligned with their audio and JSON timestamps!")
        sys.exit(0)
    else:
        print("❌ FAILED: Some alignment issues were found. Please re-run translation/audio generation.")
        sys.exit(1)

if __name__ == '__main__':
    main()
