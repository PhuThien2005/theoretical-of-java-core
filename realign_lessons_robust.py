import os
import asyncio
import sys
import time

PROJECT_DIR = "/home/fhu_thjen/projects/learning-java"
TIMEOUT_SECONDS = 300  # Max 300s per lesson file
CONCURRENCY_LIMIT = 4

async def run_single(md_path, json_path, idx, total, lesson_name, sem):
    async with sem:
        print(f"🚀 [{idx+1}/{total}] Starting re-alignment for {lesson_name}...", flush=True)
        cmd = ["python3", "generate_single_lesson.py", md_path, json_path]
        
        start_time = time.time()
        try:
            proc = await asyncio.create_subprocess_exec(
                *cmd,
                stdout=asyncio.subprocess.PIPE,
                stderr=asyncio.subprocess.PIPE,
                cwd=PROJECT_DIR
            )
            try:
                stdout, stderr = await asyncio.wait_for(proc.communicate(), timeout=TIMEOUT_SECONDS)
                elapsed = time.time() - start_time
                if proc.returncode == 0 and b"SUCCESS:" in stdout:
                    print(f"✅ [{idx+1}/{total}] Finished re-alignment for {lesson_name} in {elapsed:.2f}s", flush=True)
                    return True
                else:
                    print(f"❌ [{idx+1}/{total}] Failed re-alignment for {lesson_name} (Code: {proc.returncode})", flush=True)
                    if stderr:
                        print(f"   Error: {stderr.decode('utf-8').strip()}", flush=True)
                    return False
            except TimeoutError:
                try:
                    proc.kill()
                except ProcessLookupError:
                    pass
                print(f"⏳ [{idx+1}/{total}] Timeout re-alignment for {lesson_name} after {TIMEOUT_SECONDS}s", flush=True)
                return False
        except Exception as e:
            print(f"❌ [{idx+1}/{total}] Error launching {lesson_name}: {e}", flush=True)
            return False

async def main():
    vi_dir = os.path.join(PROJECT_DIR, 'vi')
    tasks = []
    sem = asyncio.Semaphore(CONCURRENCY_LIMIT)
    
    # Collect all lesson files
    lessons_to_align = []
    for root, dirs, files in sorted(os.walk(vi_dir)):
        if 'theory' in root and not root.endswith('audio'):
            for f in sorted(files):
                if f.endswith('.md') and not f.startswith('anki') and not f.startswith('study-notes'):
                    md_path = os.path.join(root, f)
                    base_name = os.path.splitext(f)[0]
                    json_path = os.path.join(root, 'audio', base_name + '.json')
                    
                    # Skip if completed recently (within the last 2 hours)
                    is_done_recently = False
                    if os.path.exists(json_path):
                        mtime = os.path.getmtime(json_path)
                        if time.time() - mtime < 1800:
                            is_done_recently = True
                            
                    if not is_done_recently:
                        lessons_to_align.append((md_path, json_path, base_name))
                    
    total = len(lessons_to_align)
    for idx, (md_path, json_path, base_name) in enumerate(lessons_to_align):
        tasks.append(run_single(md_path, json_path, idx, total, base_name, sem))
        
    print(f"🚀 Starting robust concurrent re-alignment for {len(tasks)} core lessons...")
    start_time = time.time()
    results = await asyncio.gather(*tasks)
    success = sum(1 for r in results if r)
    elapsed = time.time() - start_time
    
    print(f"\n🎉 Finished concurrent core lessons re-alignment!")
    print(f"  Total processed: {len(tasks)}")
    print(f"  Success: {success}")
    print(f"  Time elapsed: {elapsed:.2f}s")

if __name__ == '__main__':
    asyncio.run(main())
