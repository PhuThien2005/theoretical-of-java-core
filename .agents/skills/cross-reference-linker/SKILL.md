---
name: cross-reference-linker
description: Use when adding cross-reference navigation links between related Java Core topic chapters to help learners discover and jump to related concepts.
---

# Cross-Reference Linker

## Purpose

Add `> Xem thêm:` navigation links across the learning-java project so that related concepts in different chapters are interconnected. This makes the curriculum navigable like a wiki instead of a flat list.

---

## Inputs

- A target topic folder (e.g., `no10_modifiers/`).
- The cross-reference relationship map (see `cross-reference-plan.md` or the embedded map below).
- All files in the target topic: `README.md`, `theory/*.md`, `terms/*.md`.
- The destination files in related chapters (to verify they exist and to add reverse links).

---

## Outputs

Modified files with `> Xem thêm:` blockquotes inserted at contextually appropriate locations.
Updated progress tracker at `reports/cross-reference-progress.tsv`.

---

## Cross-Reference Relationship Map

Use this map to determine which chapters should link to each other. The agent MUST consult this map before processing any chapter.

### Group A — Strong Relationships (Bidirectional ↔)

| Source Chapter | Target Chapter | Linking Concept |
|---|---|---|
| no03_data_types | no13_memory_management | Stack/Heap, reference types |
| no10_modifiers (synchronized/volatile) | no29_synchronization_concurrency | Monitor Lock, memory visibility |
| no10_modifiers (transient) | no26_io | Serialization |
| no10_modifiers (abstract) | 11-interfaces | Abstract class vs Interface |
| no10_modifiers (final) | 22-lambda | Effectively final |
| 11-interfaces (functional) | 22-lambda | Lambda implements FunctionalInterface |
| 11-interfaces (functional) | 41-functional-interfaces | Predicate, Function, Consumer |
| 22-lambda | no23_stream_api | Lambda in stream pipeline |
| no14_object_class (hashCode/equals) | no19_collections_framework (HashMap) | HashMap key contract |
| 15-generics | no19_collections_framework | Type parameter `<E>`, `<K,V>` |
| 21-comparable-comparator | no19_collections_framework | Sorting, TreeMap |
| no26_io | no27_nio | Traditional I/O vs NIO |
| no28_multithreading | no29_synchronization_concurrency | Thread → Sync |
| no28_multithreading | 30-executor-framework | Thread → Executor |
| no32_classloader | no37_jvm_advanced | Class Loading in JVM |
| no26_io (serialization) | no36_security_basic | Insecure deserialization |

### Group B — Medium Relationships (Unidirectional →)

| Source Chapter | Target Chapter | Linking Concept |
|---|---|---|
| no08_string (StringBuffer) | no28_multithreading | Thread-safe StringBuffer |
| no09_oop (inheritance) | 12-exception | Exception hierarchy |
| 12-exception | 33-jdbc | SQLException handling |
| no14_object_class (clone) | no26_io | Deep copy via Serialization |
| no17_annotations | no31_reflection | Runtime annotation processing |
| 19-collections (ConcurrentHashMap) | no29_synchronization_concurrency | Thread-safe collections |
| no23_stream_api (parallel) | 30-executor-framework | ForkJoinPool |
| no28_multithreading (thread-safety) | no43_design_patterns (Singleton) | Double-checked locking + volatile |
| 42-record-sealed | no09_oop, no10_modifiers | Record is implicitly final |
| no03_data_types (wrappers) | 15-generics | Wrappers required for generics |
| no03_data_types (NullPointerException) | 12-exception | Unboxing null |
| no07_arrays (covariance) | 15-generics | Array covariance vs generic invariance |
| no08_string (immutability) | no19_collections_framework | String as HashMap key |
| no24_optional | no26_io | Optional is not Serializable |

---

## Workflow

### Step 0: Identify Relationships

1. Read the relationship map above.
2. For the target chapter, find ALL rows where it appears as either Source or Target.
3. Build a list of `(direction, partner_chapter, concept, target_file)` tuples.

### Step 1: Read Source Files

Read all files in the target chapter:
- `README.md`
- `theory/*.md`
- `terms/*.md`

Identify sections where each linking concept is mentioned or discussed.

### Step 2: Verify Destination Files Exist

For each link target, verify the destination file exists using `list_dir` or `view_file`. If it doesn't exist, skip that link and log a warning.

### Step 3: Find Insertion Points

For each identified relationship, find the BEST insertion point using this priority:

1. **Theory files** — Insert `> Xem thêm:` immediately after the section that discusses the linking concept. Look for:
   - A subsection heading (`###`) that names the concept
   - A code example or explanation paragraph that uses the concept
   - The end of a "Chuỗi Nguyên Nhân - Kết Quả" chain
   - A "Sai lầm thường gặp" or "Lỗi Thường Gặp" section

2. **README.md checklist** — For deferred concepts (like `synchronized` in ch.10), add `→ _Học sâu tại:_` inline on the checklist item.

3. **Terms table** — Add `→ [Học sâu tại Ch.XX](path)` at the end of the "Ví Dụ / Ghi Chú" column.

### Step 4: Insert Links

Use the correct format:

**In theory files:**
```markdown

> Xem thêm: [Description of what the reader will find], được trình bày chi tiết trong [Display Name](relative-path-to-file.md).

```

**In README checklist (inline):**
```markdown
  - concept_name → _Học sâu tại:_ [Ch.XX - Topic](relative-path)
```

**In terms table (append to cell):**
```markdown
| `keyword` | Definition | Example → [Học sâu tại Ch.XX](relative-path) |
```

### Step 5: Add Reverse Links (Bidirectional Only)

For Group A relationships (↔), repeat Steps 1–4 for the partner chapter, adding a link back to the source chapter.

### Step 6: Validate

Run the validation script `scripts/validate-cross-refs.sh` or manually verify:
- [ ] All linked files exist
- [ ] Relative paths are correct
- [ ] No duplicate links in the same section
- [ ] Format matches the established pattern
- [ ] No original content was modified or deleted

### Step 7: Update Progress

Append a row to `reports/cross-reference-progress.tsv`:
```tsv
Chapter	Status	LinksAdded	FilesModified	Date	Notes
```

---

## Format Rules

1. **Use relative paths** (`../../other-chapter/...`), never absolute paths.
2. **Bidirectional for Group A** — both chapters get links to each other.
3. **Unidirectional for Group B** — only source chapter links to target.
4. **Each `> Xem thêm:` on its own line**, preceded by a blank line.
5. **Max 3 consecutive `Xem thêm` links** in the same section. If more than 3, combine into a single blockquote with multiple links separated by line breaks.
6. **No duplicates** — if a section already links to the target, do not add another.
7. **Preserve all existing content** — never delete or modify surrounding text.
8. **Vietnamese language** — All link descriptions must be in Vietnamese.

---

## Combining Multiple Links

When a section relates to 3+ other chapters, use this combined format:

```markdown
> Xem thêm:
> - Cơ chế Monitor Lock: [Ch.29 - Synchronized Method](../../no29_synchronization_concurrency/theory/01-synchronized-method-concepts.md)
> - Thread Safety và Race Condition: [Ch.28 - Thread Safety](../../no28_multithreading/theory/04-thread-safety-concepts.md)
> - Ứng dụng trong Singleton: [Ch.43 - Singleton](../../no43_design_patterns/theory/01-singleton-concepts.md)
```

---

## Edge Cases

### Section Mentions a Concept Briefly But Isn't the Main Topic

Add a link only if the concept is discussed for at least 2 sentences or includes a code example. A passing mention of one word is not enough.

### File Already Has a `Xem thêm` Link to the Same Chapter

Do not add a duplicate. Check for existing links before inserting.

### Theory File Discusses Multiple Linkable Concepts

Add separate `> Xem thêm:` blocks after each relevant section, not one giant block at the end of the file.

### Destination File Does Not Exist

Log a warning in the progress tracker: `SKIP - File not found: <path>`. Do not create a broken link.

---

## Quality Checklist (Per Chapter)

Before marking a chapter as DONE, verify:

- [ ] All relationships from the map have been addressed
- [ ] All linked files exist (test with `ls` or `stat`)
- [ ] Relative paths resolve correctly from the source file's location
- [ ] No original content was modified or deleted
- [ ] Format matches `> Xem thêm:` pattern
- [ ] Bidirectional links exist for Group A relationships
- [ ] Progress tracker is updated

---

## Integration

- Run **after** `theory-depth-improver` (ensure theory content is stable before adding links).
- Run **before** `anki-card-quality-reviewer` (links may reveal missing card coverage).
- Compatible with `outline-alignment-auditor` (links don't affect outline compliance).
