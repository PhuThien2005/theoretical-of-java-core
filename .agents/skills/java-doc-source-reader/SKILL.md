---
name: java-doc-source-reader
description: Use when checking terminology and definitions against local Oracle Java Tutorial reference files to ensure absolute accuracy of theory and cards.
---

# Java Doc Source Reader Skill

## Purpose

To ensure that Java Core theory notes and Anki flashcards use 100% accurate terminology, definitions, and code structures as documented by Oracle's official Java Tutorials.

---

## Reference Folder

The reference documents are located in:
```text
references/java-tutorials/nutsandbolts/
```
These clean text versions cover:
- `index.txt`: Index of Nuts and Bolts
- `variables.txt`: Variable types (local, instance, static, parameters) and naming rules
- `datatypes.txt`: Primitive data types, default values, literals
- `arrays.txt`: Array declaration, allocation, copying, sorting, searching
- `operators.txt`: Operators precedence, bitwise, increment/decrement, ternary, instanceof
- `expressions.txt`: Expressions, statements, blocks
- `flow.txt`: Control flow statements (if, switch, for, while, do-while, break, continue)
- `QandE_questions_and_exercises.txt`: Official quiz questions and exercises

---

## Workflow

### Step 1: Verify Terminology
When editing or creating theory files/cards for topics 01 to 08:
1. Locate the corresponding topic reference file in `references/java-tutorials/nutsandbolts/`.
2. Check that the terms (e.g. "instance variables", "fields", "parameters", "naming conventions", "short-circuit evaluation") exactly match the official wording.
3. Verify that default values, type ranges, or operators behavior are strictly correct.

### Step 2: Auto-Sync Changes
Every time a file edit is completed in `no01_overview`, `no02_basic_syntax`, `no03_data_types`, `no04_variables_constants`, `no05_operators`, `no06_control_flow`, or `no07_arrays`:
1. Verify format correctness using `./r.sh dry`.
2. Execute the sync to Anki automatically by running `./r.sh` or `./r.sh topic <folder_name>` to synchronize the updated cards immediately.
3. Commit and push the updated cards and documentation to GitHub.
