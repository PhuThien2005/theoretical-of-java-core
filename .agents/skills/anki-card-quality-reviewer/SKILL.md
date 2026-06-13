---
name: anki-card-quality-reviewer
description: Use when reviewing Java Core Anki TSV cards for specificity, usefulness, source quality, and non-generic explanations.
---

# Anki Card Quality Reviewer

## Purpose

Prevent shallow, repetitive, or weak Anki cards from entering the study deck.
A card must teach something concrete and retrievable — not just confirm that a concept exists.

---

## The #1 Quality Rule: Answers Must Be Substantive

A card is **hollow** (sáo rỗng) when the answer contains no information that the student couldn't already guess from the question.

### Hollow answer checklist

| Pattern | Hollow example | Improved example |
|---------|---------------|-----------------|
| **Count without listing** | Q: How many primitive types does Java have? A: Java has 8 primitive types. | A: `byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean` — 4 integer, 2 float, 1 char, 1 boolean. |
| **Definition echoes the term** | Q: What is autoboxing? A: Autoboxing is the autoboxing feature of Java. | A: Automatic conversion from a primitive (`int`) to its wrapper (`Integer`) when an object is required. |
| **Template placeholder text** | A: Learn its Java rule, valid use cases, and failure mode. | A: Actual rule with a concrete example. |
| **Name-only answer** | Q: What is type erasure? A: Type erasure is a concept in Generics. | A: The compiler removes all generic type info after type-checking; bytecode uses raw types + inserted casts. |
| **Vague "it depends"** | Q: When does autoboxing occur? A: It occurs when needed. | A: When a primitive is used where an `Object` is expected: `List<Integer> l = new ArrayList<>(); l.add(1);` |

### Fix rule

If you spot a hollow card, do **both**:
1. Merge or rewrite the `Back`/`Text` to include the actual facts (list all items, state the exact rule, show a tiny example).
2. If the information cannot fit in one `Back` field, move the elaboration into `Extra` and keep `Back` as the concise answer.

---

## Required Checks

### 1. Structural Checks

- Verify TSV headers match exactly: `basic.tsv`, `basic-extra.tsv`, `cloze.tsv`, `code-question.tsv`.
- Verify every row has a stable, unique `ID` with no duplicates inside the file.
- Verify field count per row matches the header count.

### 2. Hollow / Template Wording (run `audit-cards`)

The audit script detects:

| Detection | Example trigger |
|-----------|----------------|
| Template phrases | "is a specific concept in Generics; learn its Java rule…" |
| Hollow count answers | Back: "Java has 8 primitive types." (< 12 words, no listing) |
| Shallow listing answers | Front starts with "how many / what are / list / name"; Back < 8 words |
| Placeholder advice | "Use it to predict the exact Java rule, the allowed form, and the failure mode." |

### 3. Extra Field Depth

For `basic-extra.tsv`:
- `Extra` must explain **why** the answer matters, show a code example, or describe the failure mode.
- Minimum ~18 words in `Extra`.
- Avoid: "See the theory file for more details."

### 4. Code Question Cards

- `Code` field must include escaped `\n` for multi-line snippets.
- `Answer` must be precise (compile error / output / exception class name).
- `Explanation` must say **why** — not just repeat the answer.

### 5. Source Fields

- Every card that references a rule or JDK behavior must have a URL in `Source`.
- Local file path in `Source` must exist on disk.
- Prefer `official Java docs first → reputable secondary sources`.

### 6. Interview Cards

Cards tagged `java::interview` must:
- Ask something an interviewer would ask verbatim.
- Have an answer that includes: rule + concrete example + one common mistake.

---

## Commands

```bash
# Audit all cards
./r.sh audit-cards

# Audit one topic
./r.sh audit-cards --topic 03-data-types

# Audit and fail CI if any warnings exist
./r.sh audit-cards --fail-on-warn
```

## Output

The quality audit writes:

```text
reports/anki-quality-audit.tsv
```

Columns: `severity | path | line | id | reason`

Severity levels:
- `error` — must fix before sync (bad headers, duplicate IDs).
- `warn` — should fix (hollow answers, missing sources, shallow Extra).
- `info` — optional improvement (code formatting, URL coverage).

---

## Workflow Integration

Run `./r.sh audit-cards --topic <topic>` **before** running `./r.sh topic <topic>`.
If warnings are found, fix the cards before syncing to Anki.
Do not sync cards with `error` severity — the audit exits with code 1 in that case.
