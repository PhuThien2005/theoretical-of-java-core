# Skill - Java Core Anki Card Maker

## Goal

Convert Java Core theory notes into high-quality Anki TSV flashcards.

## Output Location

Cards must live inside the topic folder:

```text
<topic>/anki/basic.tsv
<topic>/anki/basic-extra.tsv
<topic>/anki/cloze.tsv
<topic>/anki/code-question.tsv
```

Do not place topic cards in a separate global `anki/` folder.

## Card Rules

- One card should test one idea.
- Write clear English questions.
- Keep `Back` short enough to review quickly.
- Put code examples, warnings, and memory hints in `Extra`.
- If a card introduces an important term that is not explained elsewhere, explain it in `Extra`; do not leave it as a vague one-liner.
- A strong `Extra` should explain the term's meaning, why it matters, common confusion, and a tiny example or counterexample when useful.
- Use reference links for important or exact behavior. Prefer official Java documentation when available.
- Add a local Markdown path and, when useful, a URL in the `Source` field, for example `no09_oop/theory/04-polymorphism.md | https://docs.oracle.com/javase/tutorial/java/IandI/polymorphism.html`.
- If an `Extra` or `Explanation` expands an idea using an external source, include a short `Reference:` link there too.
- Use Cloze cards for facts that must be recalled exactly.
- Use Code Question cards for code, commands, output prediction, and bug-spotting.
- Use Mermaid diagrams in theory files when a visual model helps.
- Add stable `ID` values to TSV rows so AnkiConnect can update notes later.
- Create `<topic>/terms/*.md` when important terms are mentioned but not explained deeply enough.

## Tags

Use:

```text
java::core::<topic>
```

Add:

```text
java::interview
```

for interview-style cards.

## Workflow

1. Read the topic README and theory files.
2. Identify important under-explained terms.
3. Add term explanations in `<topic>/terms/*.md` when needed.
4. Add reference links to theory files when documentation or trusted sources clarify the topic.
5. Extract definitions, contrasts, processes, syntax, commands, code snippets, diagrams, and gotchas from both `theory/` and `terms/`.
6. Create Basic cards for direct definitions.
7. Create Basic Extra cards for examples, traps, and deeper term explanation.
8. Create Cloze cards for exact recall.
9. Create Code Question cards for code or command based recall.
10. Add local source paths and relevant reference URLs to cards.
11. Make enough cards to cover the theory deeply; detailed topics may need 80-150+ cards, and core topics may need 150-250+ cards.
12. Validate TSV column counts.
