---
name: theory-depth-auditor
description: Use when auditing whether theory files explain the "why" behind concepts deeply enough for learners to answer Self-Check questions without external help.
---

# Theory Depth Auditor

## Purpose

Evaluate whether a topic's theory files provide enough **mechanism, reasoning, and mental models** for a learner to answer every Self-Check question independently — without Googling or guessing.

This skill detects the "What without Why" anti-pattern: theory that lists facts, syntax, and tables but never explains the underlying reason.

---

## Inputs

- Topic folder path (e.g., `03-data-types/`).
- `README.md` (contains Self-Check questions, or should).
- All `theory/*.md` files.
- All `terms/*.md` files (if they exist).

---

## Outputs

Two report files:

1. **Per-question detail** — `reports/theory-depth-audit.tsv`

```tsv
Topic	SelfCheckQuestion	Verdict	Score	CriteriaBreakdown	TheoreticalGap	FileNeeded	SuggestedSection
```

2. **Topic summary** — `reports/theory-depth-summary.md`

Markdown summary with pass/fail counts across all audited topics.

---

## Workflow

### Step 0: Check For Self-Check Questions

Read the topic `README.md` and locate the `## Self-Check` section.

- If the section **exists**, extract all questions.
- If the section **does not exist**, flag this as a `MISSING_SELFCHECK` issue:
  - Generate 4–8 "why" questions based on the topic's theory files and outline.md concepts.
  - Record the generated questions as `GENERATED` (not `EXISTING`).
  - Proceed with the audit using the generated questions.

### Step 1: Read All Theory And Terms

Read every file in:
- `<topic>/theory/*.md`
- `<topic>/terms/*.md` (if present)

Build a mental index of what each section explains.

### Step 2: Evaluate Each Self-Check Question

For each Self-Check question, search the theory for content that answers it. Apply the **5 Depth Criteria** below.

### Step 3: Score And Classify

- **PASS** (4–5 / 5 criteria met): Theory adequately answers the question.
- **PARTIAL** (2–3 / 5 criteria met): Some content exists but is incomplete.
- **FAIL** (0–1 / 5 criteria met): Theory does not answer the question.

### Step 4: Write Reports

- Append results to `reports/theory-depth-audit.tsv`.
- Update `reports/theory-depth-summary.md` with topic-level stats.

---

## 5 Depth Criteria

Evaluate each Self-Check question against these 5 criteria. Each criterion scores 0 (absent) or 1 (present).

### Criterion 1: Mechanism Explained

The theory explains **how or why** something works at a technical level, not just what happens.

| PASS example | FAIL example |
|---|---|
| "Java chops off the leading bits that don't fit because `byte` only stores 8 bits. For value `1000`, the binary `00000011 11101000` is truncated to `11101000`, which is −24 in two's complement." | "Narrowing conversion may lose information." |

### Criterion 2: Mental Model Provided

The theory includes at least one of: analogy, Mermaid diagram, ASCII art, or visual metaphor that helps the learner **picture** the concept.

| PASS example | FAIL example |
|---|---|
| Mermaid diagram showing Stack (fixed-size boxes for `int`) vs Heap (variable-size String objects with reference arrows). | Text-only description: "primitives are stored on the Stack." |

### Criterion 3: Concrete Example With Output

The theory includes a **runnable code example** with expected output shown as comments, demonstrating the mechanism in action.

| PASS example | FAIL example |
|---|---|
| `int v = 1000; byte b = (byte) v; System.out.println(b); // -24` with explanation of why −24. | `byte small = (byte) value;` with no output and just "may produce an unexpected result". |

### Criterion 4: Cause-Effect Chain

The theory connects a chain of reasoning: A causes B which leads to C. The learner can trace the logic step by step.

| PASS example | FAIL example |
|---|---|
| "`Integer boxed = null;` → null means no object exists → Java calls `.intValue()` during unboxing → calling a method on null → `NullPointerException`" | "Unboxing a null wrapper causes `NullPointerException`." |

### Criterion 5: Standalone Answerable

After reading the theory section, a learner can **fully answer** the Self-Check question without any external resource. The answer requires no information that is only available outside the theory files.

| PASS example | FAIL example |
|---|---|
| The section "Why String Is Not a Primitive" contains: primitives need fixed size, String size is variable, therefore it must be an Object on the Heap. A learner can directly compose an answer. | The section says "String is not a primitive type" and "String is special" but never explains the design reason. A learner would need to Google. |

---

## Common Anti-Patterns To Flag

| Anti-Pattern | Description | Severity |
|---|---|---|
| **Statement without reason** | "X is Y" with no "because..." | `FAIL` |
| **Result without mechanism** | "This may lose data" with no bit-level or memory-level explanation | `PARTIAL` |
| **Copy-paste template text** | Identical boilerplate paragraph repeated across sections verbatim | `FAIL` |
| **Definition echoes term** | "Autoboxing is the automatic boxing of values" — circular | `FAIL` |
| **Deferred explanation** | "This is explained in a later chapter" when the Self-Check question is in the current topic | `FAIL` |
| **Code without prose** | Code example exists but no textual explanation of why it behaves that way | `PARTIAL` |

---

## Topic-Level Summary Scoring

After auditing all Self-Check questions for a topic, compute:

```text
Topic Score = (PASS count × 2 + PARTIAL count × 1) / (Total questions × 2) × 100%
```

| Score Range | Grade | Action Needed |
|---|---|---|
| 80–100% | 🟢 A | Minor polish only |
| 60–79% | 🟡 B | Add "why" sections for PARTIAL items |
| 40–59% | 🟠 C | Significant rewriting needed |
| 0–39% | 🔴 D | Major overhaul — most questions unanswered |

---

## Edge Cases

### Topic With No Theory Files

If a topic folder exists but has no `theory/*.md` files, record all questions as `FAIL` with gap = "No theory files exist."

### Self-Check Question Spans Multiple Files

If a question requires knowledge from multiple theory files (e.g., "Why is String not a primitive?" needs both `01-primitive-types.md` and `02-reference-types.md`), check across ALL theory files. The question PASSes if the combined content answers it, but note in the report that a cross-reference link should be added.

### Generated Self-Check Questions

When Self-Check questions are generated (because the README lacks them), mark them with `Source: GENERATED` in the report. The `theory-depth-improver` should add these questions to the README's `## Self-Check` section after the theory is improved.

---

## Integration

- Run **before** `theory-depth-improver` to identify gaps.
- Run **after** `theory-depth-improver` to verify gaps are filled.
- The audit loop should run at most 2 iterations per topic.
