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

- A topic folder such as `no01_overview/`.
- A topic README such as `no01_overview/README.md`.
- Topic theory files such as `no01_overview/theory/*.md`.
- Optional topic term files such as `no01_overview/terms/*.md`.
- Optional Anki media files such as `no01_overview/media/anki/*.svg`.
- Optional code examples from `practice/`.
- Optional reference links from official documentation, language specifications, trusted tutorials, or authoritative articles.

## Outputs

Write Anki cards into:

- `<topic>/anki/basic.tsv`
- `<topic>/anki/basic-extra.tsv`
- `<topic>/anki/cloze.tsv`
- `<topic>/anki/code-question.tsv`

The repository sync script reads those files through `scripts/sync_anki.py`.

Use these headers exactly:

```tsv
ID	Front	Back	Code	Source	Tags
```

```tsv
ID	Front	Back	Extra	Code	Source	Tags
```

```tsv
ID	Text	Extra	Code	Source	Tags
```

```tsv
ID	Question	Code	Answer	Explanation	Source	Tags
```

## Card Rules

- One card tests one idea.
- Keep answers short.
- Use `Code` for code examples/snippets, and `Extra` for explanations, gotchas, or memory hints.
- If `Back` uses a term that is not explained elsewhere, `Extra` must explain the term deeply enough: meaning, why it matters, common confusion.
- Use Cloze for definitions, counts, keyword lists, and contrasts.
- Use Code Question cards for code output prediction, compile/run flow, and bug spotting (or where the question is primarily code-based).
- Do not mix note types in one TSV file.
- Prefer TSV over CSV.
- Avoid tab characters inside field content.
- For multi-line code in TSV fields (including the `Code` field), write escaped `\n`; the sync script converts it into real line breaks for Anki.
- Store topic cards beside the topic notes, not in a separate global `anki/` folder.
- Use Mermaid diagrams in theory files when a process or relationship is easier to understand visually.
- For Anki cards, do not embed raw Mermaid. Export Mermaid diagrams to SVG/PNG in `<topic>/media/anki/` and reference them with `<img src="filename.svg">`.
- Prefer explicit stable `ID` values in TSV rows so AnkiConnect can update existing notes even after text edits.
- Create `terms/*.md` when important terms are mentioned but not explained deeply enough in theory files.
- Add reference links when they strengthen trust or clarify exact behavior. Prefer official Java documentation first, then reputable secondary sources.
- Put references in theory files under `## Reference Links` or a local references section.
- In Anki TSV files, the `Source` field may include both the local Markdown source and a URL, for example `no09_oop/theory/03-inheritance.md | https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html`.
- Run card and link audits before finalizing generated cards.

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
6. Gather reference links for important claims, especially from official Java documentation.
7. Add reference links to the theory files and, when useful, to the topic README.
8. Extract definitions, contrasts, commands, processes, diagrams, code snippets, common mistakes, and interview explanations.
9. Split large ideas into small questions.
10. Create Basic cards for direct definitions and direct facts.
11. Create Basic Extra cards for examples, gotchas, and deeper explanation.
12. Create Cloze cards for must-remember exact facts.
13. Create Code Question cards whenever the theory or terms contain code or commands.
14. Add local file paths and relevant URL references to card `Source` fields.
15. Export Mermaid diagrams to media files when visual recall would help.
16. Run `./r.sh audit-cards --topic <topic>` to catch shallow cards, bad sources, and TSV issues.
17. Run `./r.sh audit-links --topic <topic>` when the topic contains reference URLs.
18. Validate each TSV has the correct number of columns.
19. Link all four Anki files from the topic README when helpful.

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
- Core OOP, collections, generics, exceptions, concurrency, streams, and interview-heavy topics may need 150-250+ cards when the theory is dense.

## Quality Check

Before finishing, verify:

- Every line has the expected TSV field count.
- Cards are not duplicated across files unless they test different recall formats.
- Tags match the topic.
- The theory file and Anki files agree with each other.
- Reference links in cards point to the same theory file or documentation used to write the explanation.
- Important terms from theory files are explained in either the theory file or a `terms/*.md` file.
- `./r.sh audit-cards --topic <topic>` does not report generic template wording that should be rewritten.
- `./r.sh audit-links --topic <topic>` confirms reference URLs are reachable, or the dead links are documented and replaced.
- All four note types are considered; omit `code-question.tsv` only when the topic has no code, commands, or output-style examples.
