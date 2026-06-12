---
name: outline-alignment-auditor
description: Audit and enforce alignment between the global outline.md and the individual topic folders, ensuring no concepts are missing or undocumented.
---

# Outline Alignment Auditor Skill

## Purpose

Enforce strict synchronization between:
1. The global outline (`outline.md`).
2. The actual content in topic folders (README, `theory/*.md`, `terms/*.md`, and `anki/*.tsv`).

---

## Workflow

### Step 1: Parse the Section Outline
Extract the sub-bullets and concepts listed under the specific section of `outline.md`.
For example, under `37. Advanced JVM`, extract concepts like:
- JVM architecture
- Class Loader Subsystem
- Eden, Survivor, Old Gen
- Serial, Parallel, G1, ZGC, Shenandoah GCs
- Stop-the-world
- `-Xms`, `-Xmx`, `-XX` tuning flags
- Heap dump, thread dump

### Step 2: Audit Topic Content
Check if the corresponding topic folder (e.g., `37-jvm-advanced/`) contains theory notes, definitions, or cards for each extracted concept.
- If a concept is missing in the topic files, add a section in `theory/*.md` and create corresponding Anki cards.
- If a topic folder has extra Java concepts that are not listed in `outline.md`, add those concepts under the appropriate section of `outline.md`.

### Step 3: Verify Anki Cards
Ensure each concept has at least one Basic, Cloze, or Code Question card in the `anki/*.tsv` files.
