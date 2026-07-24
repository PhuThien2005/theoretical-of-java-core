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

### Audit Failure Remediation
If the auditor reports an error (e.g., mismatch count or snippet difference):
1. **Regenerate Audio**: Run the generator script to overwrite the files:
   ```bash
   python3 generate_single_lesson.py vi/<topic>/theory/<lesson>.md vi/<topic>/theory/audio/<lesson>.json
   ```
2. **Re-run Audit**: Execute the auditor again to verify the fix.
