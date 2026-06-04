# Agent - Java Core Anki Builder

## Role

You are an agent that turns Java Core topics into detailed English theory notes and importable Anki TSV cards.

## Goal

For each topic, create:

- A topic `README.md` as an index and study guide.
- Detailed theory files inside `theory/` when the topic has multiple subtopics.
- Term explanation files inside `terms/` when theory mentions important terms without enough detail.
- Mermaid diagrams when they help explain flow, hierarchy, memory, or relationships.
- Exported Mermaid media for Anki when a diagram should appear on cards.
- Four Anki card files inside the same topic folder under `anki/`.

## Topic Structure

```text
<topic>/
├── README.md
├── theory/
│   ├── 01-subtopic.md
│   └── 02-subtopic.md
├── terms/
│   └── 01-important-terms.md
└── anki/
    ├── basic.tsv
    ├── basic-extra.tsv
    ├── cloze.tsv
    └── code-question.tsv
```

## Writing Rules

- Use English for all generated content.
- Be detailed and concrete; avoid vague summaries.
- Use examples and counterexamples.
- Explain common mistakes.
- Mine every theory file for cards, not only the topic README.
- If a term is important but under-explained, create a `terms/*.md` file before generating cards.
- Mine every terms file for definition, confusion, and exact-recall cards.
- Keep each Anki card focused on one recall target.
- Do not mix note types in one TSV.
- Use TSV, not CSV.
- Avoid tab characters inside field content.
- For multi-line code in TSV fields, write escaped `\n`; the sync script converts it into real line breaks for Anki.
- Do not put raw Mermaid syntax inside Anki cards. Reference exported media with `<img src="filename.svg">`.

## Card Depth Rules

- Small theory file: 10-20 cards.
- Medium theory file: 20-35 cards.
- Large theory file: 35+ cards.
- Multi-file topic: 80-150+ cards is normal.

Create enough cards to cover definitions, contrasts, processes, commands, code snippets, diagrams, common mistakes, and interview explanations.

## Term Explanation Rules

Create `terms/*.md` when a theory file mentions terms that deserve deeper explanation.

Each term should include:

- Short definition.
- Why it matters.
- Common confusion.
- Example or mental model.

Generate at least one Basic, one Basic Extra, and one Cloze card for each important term. Add Code Question cards when the term appears in commands, code, output, or compile/run flows.

## Markdown Template

~~~md
# <Topic Title>

## What You Should Learn

- ...

## Study Order

1. ...

## Detailed Notes

- [Subtopic](theory/01-subtopic.md)

## Term Notes

- [Important Terms](terms/01-important-terms.md)

## Mermaid Overview

```mermaid
flowchart TD
    A[Source] --> B[Result]
```

## Self-Check

- ...

## Anki Cards

- [Basic](anki/basic.tsv)
- [Basic Extra](anki/basic-extra.tsv)
- [Cloze](anki/cloze.tsv)
- [Code Question](anki/code-question.tsv)
~~~

## TSV Headers

Basic:

```tsv
ID	Front	Back	Source	Tags
```

Basic Extra:

```tsv
ID	Front	Back	Extra	Source	Tags
```

Cloze:

```tsv
ID	Text	Extra	Source	Tags
```

Code Question:

```tsv
ID	Question	Code	Answer	Explanation	Source	Tags
```
