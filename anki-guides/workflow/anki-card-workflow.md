# Anki Card Workflow For Java Core

## 1. Write Theory First

Use Markdown for detailed explanations, diagrams, examples, counterexamples, commands, common mistakes, and interview framing.

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
│   └── 03-literals-casting-numeric-behavior.md
├── terms/
│   └── 01-important-terms.md
└── anki/
    ├── basic.tsv
    ├── basic-extra.tsv
    ├── cloze.tsv
    └── code-question.tsv
```

## 3. Generate Cards From Every Theory File

Do not create only a few summary cards for the topic. Read each file in `theory/` and extract cards from:

- Definitions.
- Lists.
- Processes and flows.
- Commands.
- Code snippets.
- Diagrams.
- Common mistakes.
- Interview-style explanations.
- Contrasts such as compile time vs runtime or JVM vs JRE vs JDK.

Minimum depth guideline:

- Small theory file: 10-20 cards.
- Medium theory file: 20-35 cards.
- Large theory file: 35+ cards.

For a multi-file topic, it is normal to produce 80-150+ cards when the theory is detailed.

## 4. Create Term Explanation Files When Needed

If the theory mentions terms without explaining them deeply, create a `terms/*.md` file for those terms.

Use terms files for:

- Important vocabulary.
- Interview terms.
- Easy-to-confuse concepts.
- Terms used in diagrams.
- Terms that support later topics.

Then generate Anki cards from `terms/*.md` too.

Read the detailed workflow:

- [Term Explanation Workflow](term-card-workflow.md)

If a Mermaid diagram should appear in Anki, export it to SVG/PNG and upload it as media:

- [Mermaid Images In Anki Cards](mermaid-anki-media.md)

## 5. Use Four Note Types

### Java Basic

Direct question and answer. Use this for definitions and direct facts.

File:

```text
<topic>/anki/basic.tsv
```

Header:

```tsv
ID	Front	Back	Source	Tags
```

Anki note type:

```text
Java Basic
```

Fields:

```text
ID
Front
Back
Source
```

### Java Basic Extra

Question and answer with an extra explanation, warning, example, or interview framing.

File:

```text
<topic>/anki/basic-extra.tsv
```

Header:

```tsv
ID	Front	Back	Extra	Source	Tags
```

Anki note type:

```text
Java Basic Extra
```

Fields:

```text
ID
Front
Back
Extra
Source
```

Extra depth rule:

- Do not use `Extra` as a vague one-line note when the card introduces important vocabulary.
- If `Back` contains a term that is not explained elsewhere, explain it in `Extra`.
- A strong `Extra` should answer at least one of these:
  - What does this term mean?
  - Why does it matter?
  - What is a common confusion?
  - What is a tiny example or counterexample?

Weak:

```text
Long-running services benefit from a stable runtime.
```

Better:

```text
Stable runtime means the JVM is mature, predictable, well-tested, and supported for production use. It matters because backend services may run for weeks or months, so memory management, JIT behavior, monitoring, and compatibility need to be reliable.
```

### Java Cloze

Fill-in-the-blank cards for exact recall: names, counts, commands, key terms, ordered processes, and short contrasts.

File:

```text
<topic>/anki/cloze.tsv
```

Header:

```tsv
ID	Text	Extra	Source	Tags
```

Anki note type:

```text
Java Cloze
```

Fields:

```text
ID
Text
Extra
Source
```

### Java Code Question

Code or command based question. Use this whenever theory includes code snippets, shell commands, compile/run flow, output prediction, bug spotting, or "what happens here?" questions.

File:

```text
<topic>/anki/code-question.tsv
```

Header:

```tsv
ID	Question	Code	Answer	Explanation	Source	Tags
```

Anki note type:

```text
Java Code Question
```

Fields:

```text
ID
Question
Code
Answer
Explanation
Source
```

## 6. Use Stable IDs

Every row should have a stable `ID`.

Good examples:

```text
overview-basic-001
overview-basic-extra-014
overview-cloze-008
overview-code-003
```

Do not change IDs after syncing to Anki. The sync script uses `ID` to update existing notes instead of creating duplicates.

## 7. Use TSV

Use TSV instead of CSV because Java examples often contain commas, quotes, and punctuation.

Avoid tab characters inside card content. If content needs spacing, use spaces or HTML line breaks.

For multi-line code in TSV, write escaped newlines:

```text
public class Main {\n    public static void main(String[] args) {\n        System.out.println("Hi");\n    }\n}
```

The sync script converts `\n` into real line breaks before sending the field to Anki.

## 8. Put Cards Beside The Topic

Cards for topic `03-data-types` should live in:

```text
03-data-types/anki/
```

This makes it easy to read theory and inspect flashcards without jumping to a separate global folder.

## 9. Sync With The Helper Script

Dry-run one topic:

```bash
./r.sh dry-topic 01-overview
```

Sync one topic:

```bash
./r.sh topic 01-overview
```

Sync all cards:

```bash
./r.sh
```

## 10. Improve Cards After Review

If a card feels too hard, split it.

If a card feels too vague, rewrite the question.

If a card needs context, move the explanation into `Extra` or `Explanation`.

If a theory file has detailed content but few cards, revisit it and generate more cards.
