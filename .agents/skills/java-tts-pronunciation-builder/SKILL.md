---
name: java-tts-pronunciation-builder
description: Use when extracting terms from Java theory Markdown files and building precise Vietnamese IPA phonetic dictionaries for the VS Code TTS Read Aloud Extension.
---

# Java TTS Pronunciation Builder & Auditor

## Purpose

Ensure that when the VS Code TTS Extension (`markdown-read-aloud`) reads Java theory notes using the Vietnamese male voice (`Microsoft An`), all English terms, Java class names, package names, JVM flags, error messages, and code symbols are pronounced naturally and fluently in Vietnamese without unnatural pauses, robotic spelling, character-level splitting, or mixed-voice glitches.

---

## Technical Architecture & Design Principles

1. **Single-Voice Vietnamese Execution**:
   - Force 100% of all sentences to be read by the Vietnamese male voice (`Microsoft An`).
   - Do NOT attempt mid-sentence voice switching between English and Vietnamese voices.

2. **Word-Level Phonetic Mapping (`PRONUNCIATIONS`)**:
   - Rely strictly on explicit word-level dictionary mapping (`PRONUNCIATIONS[word]`).
   - Do NOT use character-by-character replacement rules (e.g. converting `c` ➔ `ếch` inside words, which turns `access` into `ac ếch ess`). Each English word maps to ONE cohesive Vietnamese phonetic equivalent (e.g. `access` ➔ `ắc xét`, `exception` ➔ `ếch xép sần`).

3. **Vietnamese Word Exclusion (`VN_EXCLUSION_SET`)**:
   - Protect ~200 unaccented Vietnamese words (`ban`, `chi`, `cho`, `con`, `do`, `quan`, `vi`, `thong`, `nhan`...) from being mistakenly replaced by English phonetic lookups.

4. **Symbol & Complex Term Normalization Rules**:
   - **Full Class Names**: `java.lang.OutOfMemoryError` ➔ `gia va lang ao ốp me mô ri e rơ`
   - **JVM Flags**: `-XX:+HeapDumpOnOutOfMemoryError` ➔ `trừ ếch ếch cộng híp đăm on ao ốp me mô ri e rơ`
   - **Memory Flags**: `-Xss512k` ➔ `trừ ếch ét ét 512 cây`
   - **File Extensions**: `.hprof` ➔ `chấm ếch pờ rô phơ`
   - **CamelCase Words**: `StackOverflowError` ➔ `xtắc ô vơ phờ lâu e rơ`
   - **Error Messages**: `GC OverLimit exceeded` ➔ `gi xi ô vơ li mít ếch xí địt`

---

## Workflow Step-by-Step

### Step 1: Scan Target Markdown File
Extract all:
- Plain English words (`Outline`, `Coverage`, `Detailed`, `Scenario`, `Catching`...)
- Code symbols & Exception names (`OutOfMemoryError`, `StackOverflowError`, `VirtualMachineError`)
- Full package/class references (`java.lang.OutOfMemoryError`, `java.util.ArrayList`, `java.util.List`)
- JVM options & flags (`-XX:+HeapDumpOnOutOfMemoryError`, `-XX:HeapDumpPath`, `-Xss`, `-Xmx`)
- File extensions (`.hprof`)

### Step 2: Build Word-Level Vietnamese Phonetic Transliterations
Map each extracted English term to a natural, smooth Vietnamese IPA word (e.g. `management` ➔ `me nịt mần`, `coverage` ➔ `cơ vơ rích`, `reference` ➔ `ré phờ rần`, `interface` ➔ `in tơ phết`).

### Step 3: Inject into Dictionary & Extension
Update both:
1. `.vscode/settings.json` under `"markdownReadAloud.pronunciations"`.
2. Extension Webview script: `/home/fhu_thjen/.vscode-server/extensions/robinreiche.markdown-read-aloud-1.3.1/media/reader.js` inside `PRONUNCIATIONS` and `VN_EXCLUSION_SET`.

### Step 4: Verification
Run `node -c reader.js` to ensure zero syntax errors.
