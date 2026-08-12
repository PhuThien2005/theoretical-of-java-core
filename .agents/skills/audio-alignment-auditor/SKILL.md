---
name: audio-alignment-auditor
description: Use when verifying whether the markdown lesson texts, edge-tts MP3 files, and JSON timestamps are 100% aligned with matching paragraph counts and content snippets.
---

# Audio Alignment Auditor

## Purpose

Verify that the Vietnamese translated markdown files, their corresponding generated `.mp3` audios, and their `.json` timestamps are perfectly aligned. This avoids "audio drift" bugs where the highlighting in the browser gets out of sync with the narration, or the narration reads unexpected text blocks.

---

## Technical Audit Checks

1. **Existence Verification**: Check if all three files exist in the chapter directory:
   - Localized markdown: `vi/<topic>/theory/<lesson>.md`
   - Timestamps metadata: `vi/<topic>/theory/audio/<lesson>.json`
   - Narrated audio: `vi/<topic>/theory/audio/<lesson>.mp3`
2. **File Integrity**: Check that the `.mp3` is not corrupted or empty (size > 10KB).
3. **Count Alignment**: Ensure the number of elements extracted by `scratch_extract_blocks.js` matches the number of timestamps in the JSON array:
   - `len(markdown_blocks) == len(json_timestamps)`
4. **Snippet Validation**: For each paragraph index, ensure the `text_snippet` in the JSON is a valid prefix of the markdown block text (ignoring case, whitespace, and formatting punctuation).

---

## Workflow Commands

### Check a specific topic folder
To audit a single topic's alignment (e.g. `no12_exception_handling`):
```bash
python3 scripts/verify_audio_alignment.py --topic no12_exception_handling
```

### Check all topics
To run the audit script across the entire workspace:
```bash
python3 scripts/verify_audio_alignment.py
```

### Audit Failure Remediation & Audio Regeneration Workflow

#### Option A: Single Lesson Audio Generation
For a single modified markdown file:
```bash
python3 generate_single_lesson.py vi/<topic>/theory/<lesson>.md vi/<topic>/theory/audio/<lesson>.json
```

#### Option B: 16-Thread Parallel Batch Audio Generation (Recommended for Speed)
When regenerating audio for multiple lessons or an entire chapter, execute audio generation in parallel using **16 concurrent worker threads**:

```python
import subprocess
import sys
from concurrent.futures import ThreadPoolExecutor, as_completed

files = [
    ("vi/<topic1>/theory/<lesson1>.md", "vi/<topic1>/theory/audio/<lesson1>.json"),
    ("vi/<topic2>/theory/<lesson2>.md", "vi/<topic2>/theory/audio/<lesson2>.json"),
    # ... add all target files
]

def run_single(md, json_out):
    print(f"🚀 Processing: {md}", flush=True)
    res = subprocess.run([sys.executable, "generate_single_lesson.py", md, json_out], capture_output=True, text=True)
    if res.returncode != 0:
        print(f"❌ [FAIL] {md}:\n{res.stderr}", flush=True)
        return False
    print(f"✅ [SUCCESS] {md}", flush=True)
    return True

# Run up to 16 parallel threads concurrently for maximum throughput
with ThreadPoolExecutor(max_workers=16) as executor:
    futures = [executor.submit(run_single, md, json_out) for md, json_out in files]
    for future in as_completed(futures):
        future.result()
```

Or via bash one-liner / python script:
```bash
python3 -c '
import os, glob, subprocess, sys
from concurrent.futures import ThreadPoolExecutor

tasks = []
for md in glob.glob("vi/*/theory/*.md"):
    json_out = md.replace("/theory/", "/theory/audio/").replace(".md", ".json")
    os.makedirs(os.path.dirname(json_out), exist_ok=True)
    tasks.append((md, json_out))

def run_gen(t):
    subprocess.run([sys.executable, "generate_single_lesson.py", t[0], t[1]])

with ThreadPoolExecutor(max_workers=16) as pool:
    list(pool.map(run_gen, tasks))
'
```

#### Step 2: Re-run Audit
Execute the auditor again to verify 100% alignment across all generated files:
```bash
python3 scripts/verify_audio_alignment.py --topic <topic>
```
