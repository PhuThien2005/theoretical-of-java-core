# Generics - Part 2

## Learning Goal

This file covers a focused slice of **Generics**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `<?>` |<?> is an unbounded wildcard meaning an unknown type. |
| `<? extends T>` |<? extends T> is an upper-bounded wildcard for producers of T values. |
| `<? super T>` |<? super T> is a lower-bounded wildcard for consumers of T values. |
| `PECS:` | PECS is a group of related rules in Generics that groups several related details. |
| `Producer Extends` |Producer Extends means use ? extends T when an API mainly produces T values for reading. |
| `Consumer Super` |Consumer Super means use ? super T when an API mainly consumes T values for writing. |
| `Generic with Collection` | A collection is an object that groups multiple elements under a common API. |
| `Type erasure` | Type erasure is how Java implements generics by removing most generic type information at runtime. |

## Detailed Notes

### <?>

<?> is an unbounded wildcard meaning an unknown type.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `<?>` in one sentence.
- Recognize `<?>` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `<?>`.

Tiny example or mental model:

- When reading code, ask: what does `<?>` change, allow, reject, or clarify?

### <? extends T>

<? extends T> is an upper-bounded wildcard for producers of T values.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `<? extends T>` in one sentence.
- Recognize `<? extends T>` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `<? extends T>`.

Tiny example or mental model:

- When reading code, ask: what does `<? extends T>` change, allow, reject, or clarify?

### <? super T>

<? super T> is a lower-bounded wildcard for consumers of T values.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `<? super T>` in one sentence.
- Recognize `<? super T>` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `<? super T>`.

Tiny example or mental model:

- When reading code, ask: what does `<? super T>` change, allow, reject, or clarify?

### PECS:

PECS is a group of related rules in Generics that groups several related details.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `PECS:` in one sentence.
- Recognize `PECS:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `PECS:`.

Tiny example or mental model:

- When reading code, ask: what does `PECS:` change, allow, reject, or clarify?

### Producer Extends

Producer Extends means use ? extends T when an API mainly produces T values for reading.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Producer Extends` in one sentence.
- Recognize `Producer Extends` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Producer Extends`.

Tiny example or mental model:

- When reading code, ask: what does `Producer Extends` change, allow, reject, or clarify?

### Consumer Super

Consumer Super means use ? super T when an API mainly consumes T values for writing.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `Consumer Super` in one sentence.
- Recognize `Consumer Super` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Consumer Super`.

Tiny example or mental model:

- When reading code, ask: what does `Consumer Super` change, allow, reject, or clarify?

### Generic with Collection

A collection is an object that groups multiple elements under a common API.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Generic with Collection` in one sentence.
- Recognize `Generic with Collection` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Generic with Collection`.

Tiny example or mental model:

- When reading code, ask: what does `Generic with Collection` change, allow, reject, or clarify?

### Type erasure

Type erasure is how Java implements generics by removing most generic type information at runtime.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Type erasure` in one sentence.
- Recognize `Type erasure` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Type erasure`.

Tiny example or mental model:

- When reading code, ask: what does `Type erasure` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
