import os
import asyncio
import sys
import time

PROJECT_DIR = "/home/fhu_thjen/projects/learning-java"
TIMEOUT_SECONDS = 300  # Max 300s per README file
CONCURRENCY_LIMIT = 2  # Safe concurrent processes to prevent Microsoft Edge-TTS rate limits

async def run_single(readme_path, idx, total, chapter, sem):
    async with sem:
        print(f"🚀 [{idx+1}/{total}] Starting README for {chapter}...", flush=True)
        cmd = ["python3", "generate_single_readme.py", readme_path]
        
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
                    print(f"✅ [{idx+1}/{total}] Finished README for {chapter} in {elapsed:.2f}s", flush=True)
                    return True
                else:
                    print(f"❌ [{idx+1}/{total}] Failed README for {chapter} (Code: {proc.returncode})", flush=True)
                    if stderr:
                        print(f"   Error: {stderr.decode('utf-8').strip()}", flush=True)
                    return False
            except TimeoutError:
                try:
                    proc.kill()
                except ProcessLookupError:
                    pass
                print(f"⏳ [{idx+1}/{total}] Timeout README for {chapter} after {TIMEOUT_SECONDS}s", flush=True)
                return False
        except Exception as e:
            print(f"❌ [{idx+1}/{total}] Error launching {chapter}: {e}", flush=True)
            return False

async def main():
    vi_dir = os.path.join(PROJECT_DIR, 'vi')
    chapters = sorted([d for d in os.listdir(vi_dir) if os.path.isdir(os.path.join(vi_dir, d)) and d.startswith('no')])
    
    tasks = []
    sem = asyncio.Semaphore(CONCURRENCY_LIMIT)
    
    total = len(chapters)
    
    for idx, chapter in enumerate(chapters):
        readme_path = os.path.join(vi_dir, chapter, 'README.md')
        if os.path.exists(readme_path):
            tasks.append(run_single(readme_path, idx, total, chapter, sem))
            
    print(f"🚀 Starting robust concurrent batch generation for {len(tasks)} README files...")
    start_time = time.time()
    results = await asyncio.gather(*tasks)
    success = sum(1 for r in results if r)
    elapsed = time.time() - start_time
    
    print(f"\n🎉 Finished concurrent batch processing!")
    print(f"  Total processed: {len(tasks)}")
    print(f"  Success: {success}")
    print(f"  Time elapsed: {elapsed:.2f}s")

if __name__ == '__main__':
    asyncio.run(main())
