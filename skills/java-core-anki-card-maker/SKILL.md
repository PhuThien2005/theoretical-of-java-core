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
- Topic theory files such as `01-overview/theory/*.md`.
- Optional topic term files such as `01-overview/terms/*.md`.
- Optional Anki media files such as `01-overview/media/anki/*.svg`.
- Optional code examples from `practice/`.

## Outputs

Write Anki cards into:

- `<topic>/anki/basic.tsv`
- `<topic>/anki/basic-extra.tsv`
- `<topic>/anki/cloze.tsv`
- `<topic>/anki/code-question.tsv`

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

```tsv
ID	Question	Code	Answer	Explanation	Source	Tags
```

## Card Rules

- One card tests one idea.
- Keep answers short.
- Use `Extra` for code examples, gotchas, or memory hints.
- If `Back` uses a term that is not explained elsewhere, `Extra` must explain the term deeply enough: meaning, why it matters, common confusion, and a small example when useful.
- Use Cloze for definitions, counts, keyword lists, and contrasts.
- Use Code Question cards for code snippets, shell commands, output prediction, compile/run flow, and bug spotting.
- Do not mix note types in one TSV file.
- Prefer TSV over CSV.
- Avoid tab characters inside field content.
- For multi-line code in TSV fields, write escaped `\n`; the sync script converts it into real line breaks for Anki.
- Store topic cards beside the topic notes, not in a separate global `anki/` folder.
- Use Mermaid diagrams in theory files when a process or relationship is easier to understand visually.
- For Anki cards, do not embed raw Mermaid. Export Mermaid diagrams to SVG/PNG in `<topic>/media/anki/` and reference them with `<img src="filename.svg">`.
- Prefer explicit stable `ID` values in TSV rows so AnkiConnect can update existing notes even after text edits.
- Create `terms/*.md` when important terms are mentioned but not explained deeply enough in theory files.

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
2. Read every file in the topic `theory/` folder.
3. Identify important terms that are under-explained.
4. If needed, create topic term files in `<topic>/terms/*.md`.
5. Read every file in the topic `terms/` folder.
6. Extract definitions, contrasts, commands, processes, diagrams, code snippets, common mistakes, and interview explanations.
7. Split large ideas into small questions.
8. Create Basic cards for direct definitions and direct facts.
9. Create Basic Extra cards for examples, gotchas, and deeper explanation.
10. Create Cloze cards for must-remember exact facts.
11. Create Code Question cards whenever the theory or terms contain code or commands.
12. Export Mermaid diagrams to media files when visual recall would help.
13. Validate each TSV has the correct number of columns.
14. Link all four Anki files from the topic README when helpful.

## Term File Rule

Use `<topic>/terms/*.md` when a term needs more explanation than the main theory file should contain.

Each important term should include:

- Short definition.
- Why it matters.
- Common confusion.
- Example or mental model.
- Cards to create.

Term cards should usually include:

- One Basic definition card.
- One Basic Extra explanation/confusion card.
- One Cloze exact recall card.
- One Code Question card if code, commands, or output are involved.

Tag term cards with `java::term` in addition to the topic tag.

## Depth Rule

Do not under-card detailed theory.

- Small theory file: 10-20 cards.
- Medium theory file: 20-35 cards.
- Large theory file: 35+ cards.
- Multi-file topic: 80-150+ cards is normal when the theory is detailed.

## Quality Check

Before finishing, verify:

- Every line has the expected TSV field count.
- Cards are not duplicated across files unless they test different recall formats.
- Tags match the topic.
- The theory file and Anki files agree with each other.
- Important terms from theory files are explained in either the theory file or a `terms/*.md` file.
- All four note types are considered; omit `code-question.tsv` only when the topic has no code, commands, or output-style examples.
