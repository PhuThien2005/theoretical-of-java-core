---
name: java-core-anki-card-maker
description: Use when converting Java Core Markdown theory notes into detailed English study notes and concise Anki TSV flashcards stored inside each topic folder.
---

# Java Core Anki Card Maker

## Purpose

Convert Java Core topic notes into:

- Structured theory Markdown.
- Importable Anki TSV files.
- Focused flashcards that test one idea per card.

## Inputs

- A topic folder such as `01-overview/`.
- A topic README such as `01-overview/README.md`.
- Optional code examples from `practice/`.

## Outputs

Write Anki cards into:

- `<topic>/anki/basic.tsv`
- `<topic>/anki/basic-extra.tsv`
- `<topic>/anki/cloze.tsv`

The repository sync script reads those files through `scripts/sync_anki.py`.

Use these headers exactly:

```tsv
ID	Front	Back	Source	Tags
```

```tsv
ID	Front	Back	Extra	Source	Tags
```

```tsv
ID	Text	Extra	Source	Tags
```

## Card Rules

- One card tests one idea.
- Keep answers short.
- Use `Extra` for code examples, gotchas, or memory hints.
- Use Cloze for definitions, counts, keyword lists, and contrasts.
- Do not mix note types in one TSV file.
- Prefer TSV over CSV.
- Avoid tab characters inside field content.
- Store topic cards beside the topic notes, not in a separate global `anki/` folder.
- Use Mermaid diagrams in theory files when a process or relationship is easier to understand visually.
- Prefer explicit stable `ID` values in TSV rows so AnkiConnect can update existing notes even after text edits.

## Tags

Use one deck: `Java Core`.

Use hierarchical tags:

```text
java::core::<topic>
```

Add `java::interview` for interview-style cards.

Examples:

```text
java::core::overview
java::core::syntax
java::core::datatype java::interview
```

## Workflow

1. Read the topic README.
2. Extract key concepts and syntax.
3. Split large ideas into small questions.
4. Create Basic cards for direct definitions.
5. Create Basic Extra cards for examples or common mistakes.
6. Create Cloze cards for must-remember facts.
7. Validate each TSV has the correct number of columns.
8. Link generated Anki files from the topic README when helpful.

## Quality Check

Before finishing, verify:

- Every line has the expected TSV field count.
- Cards are not duplicated across files unless they test different recall formats.
- Tags match the topic.
- The theory file and Anki files agree with each other.
