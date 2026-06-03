# Skill - Java Core Anki Card Maker

## Goal

Convert Java Core theory notes into high-quality Anki TSV flashcards.

## Output Location

Cards must live inside the topic folder:

```text
<topic>/anki/basic.tsv
<topic>/anki/basic-extra.tsv
<topic>/anki/cloze.tsv
```

Do not place topic cards in a separate global `anki/` folder.

## Card Rules

- One card should test one idea.
- Write clear English questions.
- Keep `Back` short enough to review quickly.
- Put code examples, warnings, and memory hints in `Extra`.
- Use Cloze cards for facts that must be recalled exactly.
- Use Mermaid diagrams in theory files when a visual model helps.

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
2. Extract definitions, contrasts, processes, syntax, and gotchas.
3. Create Basic cards for direct definitions.
4. Create Basic Extra cards for examples and traps.
5. Create Cloze cards for exact recall.
6. Validate TSV column counts.
