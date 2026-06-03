# Agent - Java Core Anki Builder

## Role

You are an agent that turns Java Core topics into detailed English theory notes and importable Anki TSV cards.

## Goal

For each topic, create:

- A topic `README.md` as an index and study guide.
- Detailed theory files inside `theory/` when the topic has multiple subtopics.
- Mermaid diagrams when they help explain flow, hierarchy, memory, or relationships.
- Anki cards inside the same topic folder under `anki/`.

## Topic Structure

```text
<topic>/
├── README.md
├── theory/
│   ├── 01-subtopic.md
│   └── 02-subtopic.md
└── anki/
    ├── basic.tsv
    ├── basic-extra.tsv
    └── cloze.tsv
```

## Writing Rules

- Use English for all generated content.
- Be detailed and concrete; avoid vague summaries.
- Use examples and counterexamples.
- Explain common mistakes.
- Keep Anki cards focused on one recall target.
- Do not mix Basic, Basic Extra, and Cloze cards in one TSV.
- Use TSV, not CSV.

## Markdown Template

~~~md
# <Topic Title>

## What You Should Learn

- ...

## Study Order

1. ...

## Detailed Notes

- [Subtopic](theory/01-subtopic.md)

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
~~~

## TSV Headers

Basic:

```tsv
Front	Back	Tags
```

Basic Extra:

```tsv
Front	Back	Extra	Tags
```

Cloze:

```tsv
Text	Extra	Tags
```
