# Anki Card Workflow For Java Core

## 1. Write Theory First

Use Markdown for detailed explanations, diagrams, examples, and common mistakes.

Do not put long paragraphs directly into Anki. Anki works best when each card tests one small idea.

## 2. Split Large Topics Into Smaller Files

For a large topic, keep `README.md` as the index and put detailed theory in `theory/*.md`.

Example:

```text
03-data-types/
├── README.md
├── theory/
│   ├── 01-primitive-types.md
│   ├── 02-reference-types.md
│   └── 03-casting-boxing-comparison.md
└── anki/
    ├── basic.tsv
    ├── basic-extra.tsv
    └── cloze.tsv
```

## 3. Choose The Note Type

- Basic: direct question and answer.
- Basic Extra: question and answer plus code, examples, or warnings.
- Cloze: fill-in-the-blank for definitions, counts, names, and contrasts.

## 4. Use TSV

Use TSV instead of CSV because Java examples often contain commas, quotes, and punctuation.

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

## 5. Put Cards Beside The Topic

Cards for topic `03-data-types` should live in:

```text
03-data-types/anki/
```

This makes it easy to read theory and inspect flashcards without jumping to a separate global folder.

## 6. Import Into Anki

Import each file separately by note type:

```text
File -> Import -> basic.tsv
Note Type: Basic
Deck: Java Core
Fields: Front, Back, Tags
```

```text
File -> Import -> basic-extra.tsv
Note Type: Basic Extra
Deck: Java Core
Fields: Front, Back, Extra, Tags
```

```text
File -> Import -> cloze.tsv
Note Type: Cloze
Deck: Java Core
Fields: Text, Extra, Tags
```

## 7. Improve Cards After Review

If a card feels too hard, split it.

If a card feels too vague, rewrite the question.

If a card needs context, move the extra explanation into `Extra`.
