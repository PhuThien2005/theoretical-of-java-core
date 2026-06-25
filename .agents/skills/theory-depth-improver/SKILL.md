---
name: theory-depth-improver
description: Use when improving theory files based on depth audit results. Adds "why" explanations, mental models, mechanism diagrams, and concrete examples to fill gaps identified by theory-depth-auditor.
---

# Theory Depth Improver

## Purpose

Take the FAIL and PARTIAL items from `theory-depth-auditor` output and **add "why" sections** to the existing theory files. The goal is to transform every FAIL/PARTIAL into PASS so learners can answer Self-Check questions independently.

---

## Inputs

- Audit report from `theory-depth-auditor` (FAIL and PARTIAL items).
- The theory files that need improvement.
- Oracle Java reference files via `java-doc-source-reader` skill (for topics 01–08).
- The topic's `outline.md` section (for concept scope).

---

## Outputs

- **Improved theory files** with new "why" sections inserted.
- **Updated README.md** with Self-Check section (if it was missing).
- **Changelog** appended to `reports/theory-improvement-log.md`.

---

## Core Rules

### Rule 1: Never Delete Existing Content

Theory files already contain good "what" content (definitions, tables, code examples, common mistakes). **Only ADD new sections** — never remove, reorder, or rewrite existing content unless it contains factual errors or copy-paste template text.

Exception: If the auditor flagged **copy-paste template text** (identical boilerplate repeated verbatim), replace the boilerplate with topic-specific explanations.

### Rule 2: Place "Why" Sections Strategically

Insert each new "why" section **immediately after** the related "what" section.

```markdown
## Narrowing Casting                    ← existing "what" section

... existing content ...

## Why Narrowing Can Lose Data           ← NEW "why" section inserted here

... new explanation ...

## Integer Division                      ← next existing section continues
```

Do NOT group all "why" sections at the end of the file. They should flow naturally after their "what" counterpart.

### Rule 3: Minimum Depth Per "Why" Section

Every new "why" section must include **all of the following**:

| Required Element | Minimum | Example |
|---|---|---|
| **Prose explanation** | ≥ 4 sentences explaining the mechanism | "Primitives must have a fixed, known size at compile time because the Stack allocates memory in fixed-sized frames..." |
| **Mental model** | ≥ 1 (analogy, Mermaid diagram, or ASCII art) | Mermaid diagram of Stack vs Heap memory layout |
| **Code example with output** | ≥ 1 runnable snippet with `//` output comments | `System.out.println((byte) 1000); // -24` |
| **Cause-effect chain** | ≥ 1 explicit "A → B → C" reasoning | "`null` → no object exists → `.intValue()` called on nothing → `NullPointerException`" |

A "why" section that meets all four elements is typically 30–60 lines.

### Rule 4: Cross-Link Related Sections

When a "why" section references concepts explained in another theory file within the same topic, add a markdown link:

```markdown
> See also: [Why String Is Not a Primitive](02-reference-types.md#why-string-is-not-a-primitive)
```

### Rule 5: Verify Terminology

For topics 01–08 that have Oracle reference files in `references/java-tutorials/nutsandbolts/`:
- Use the `java-doc-source-reader` skill to verify that all terms match official Oracle wording.
- Do not invent terminology. Use Java Language Specification terms when available.

For all other topics:
- Prefer terms from the official Java documentation at `docs.oracle.com`.
- When no official term exists (e.g., "Stack vs Heap" is a simplification), explicitly note it: "This is a simplified model. The JLS does not mandate Stack allocation for primitives, but HotSpot JVM typically does this."

### Rule 6: Stay Within Topic Scope

Do not explain concepts that belong to other topics. If a "why" section requires knowledge from a future topic, provide a brief forward reference:

```markdown
> This relates to how the JVM manages memory, covered in detail in [13 - Memory Management](../13-memory-management/README.md).
```

### Rule 7: Add Reference Links

Every new "why" section should include at least one reference link under the file's existing `## Reference Links` section (or create one if absent):

```markdown
## Reference Links

- https://docs.oracle.com/javase/specs/jls/se21/html/jls-5.html#jls-5.1.3 (Narrowing Primitive Conversion)
```

---

## Workflow

### Step 1: Read The Audit Report

Load the FAIL and PARTIAL items for the target topic from `reports/theory-depth-audit.tsv`.

Group items by target file:
```
02-reference-types.md:
  - FAIL: "Why is String not a primitive?" → add ## Why String Is Not a Primitive
  - FAIL: "Why can int be stored directly?" → add ## Stack vs Heap Memory Model
04-wrappers-null-equality.md:
  - PARTIAL: "int vs Integer difference?" → expand with ## int vs Integer: Full Comparison
  - FAIL: "Why unboxing null throws?" → add ## Why Unboxing null Throws NullPointerException
```

### Step 2: Draft "Why" Sections

For each FAIL/PARTIAL item:

1. Identify which existing section it relates to.
2. Draft the new "why" section following Rule 3 (prose + mental model + code + cause-effect).
3. Determine the insertion point (Rule 2 — right after the related "what" section).

### Step 3: Verify With References

- For topics 01–08: cross-check with `references/java-tutorials/nutsandbolts/`.
- For all topics: verify key claims against Oracle docs or JLS.

### Step 4: Insert Sections Into Theory Files

Use file editing tools to insert the new sections. Preserve all existing content (Rule 1).

### Step 5: Update README If Needed

If the audit flagged `MISSING_SELFCHECK`:
- Add the generated Self-Check questions to the README's `## Self-Check` section.
- If the README doesn't have a `## Self-Check` section, create one after `## Key Terms` (or after `## Outline Checklist` if Key Terms doesn't exist).
- Add any new Key Terms that the "why" sections introduce (e.g., `Stack`, `Heap`, `Integer Cache`).

### Step 6: Log Changes

Append to `reports/theory-improvement-log.md`:

```markdown
## 03-data-types — 2026-06-25

### Files Modified
- `theory/02-reference-types.md`: Added "Why String Is Not a Primitive", "Stack vs Heap Memory Model", "Why Primitives Are Stored Directly"
- `theory/04-wrappers-null-equality.md`: Added "int vs Integer: Full Comparison", "Why Use .equals() for String Content", "Why Unboxing null Throws NullPointerException", "Integer Cache"
- `theory/03-literals-casting-numeric-behavior.md`: Added "Why Narrowing Can Lose Data"
- `README.md`: Added Stack, Heap, Integer Cache to Key Terms; added Self-Check hints

### Self-Check Coverage
- Before: 0/6 PASS, 4/6 PARTIAL, 2/6 FAIL
- After: 6/6 PASS
```

---

## Quality Checklist

Before marking a topic as improved, verify:

- [ ] All FAIL items from the audit are now addressed with new "why" sections.
- [ ] All PARTIAL items have been expanded with missing elements.
- [ ] Every new section meets Rule 3 (prose + mental model + code + cause-effect).
- [ ] No existing content was deleted (unless it was copy-paste template text).
- [ ] Cross-links are added between related sections (Rule 4).
- [ ] Terminology matches Oracle docs (Rule 5, for topics 01–08).
- [ ] New reference links are added (Rule 7).
- [ ] README Self-Check section exists and has questions.
- [ ] README Key Terms section includes new terms.

---

## Integration With Other Skills

After improving theory files, the following skills should run in order:

1. **`theory-depth-auditor`** — re-audit to verify FAIL → PASS.
2. **`java-core-anki-card-maker`** — create new Anki cards for the added "why" sections.
3. **`anki-card-quality-reviewer`** — verify card quality.
4. **`java-reference-auditor`** — verify new reference links.
