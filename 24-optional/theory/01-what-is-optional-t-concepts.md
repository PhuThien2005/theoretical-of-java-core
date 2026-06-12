# Optional - Part 1

## Learning Goal

This file covers a focused slice of **Optional**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `What is Optional<T>?` | Optional is a container that may or may not hold a non-null value. |
| `Avoid NullPointerException` | An exception represents an abnormal condition that a program may catch or propagate. |
| `Optional.of` | Optional is a container that may or may not hold a non-null value. |
| `Optional.ofNullable` | Optional is a container that may or may not hold a non-null value. |
| `Optional.empty` | Optional is a container that may or may not hold a non-null value. |
| `isPresent` |isPresent is a specific concept in Optional; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `ifPresent` |ifPresent is a specific concept in Optional; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `orElse` |orElse is a specific concept in Optional; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `orElseGet` |orElseGet is a specific concept in Optional; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `orElseThrow` | A Set is a collection that rejects duplicates according to equality rules. |

## Detailed Notes

### What is Optional<T>?

Optional is a container that may or may not hold a non-null value.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `What is Optional<T>?` in one sentence.
- Recognize `What is Optional<T>?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `What is Optional<T>?`.

Tiny example or mental model:

- `Optional.ofNullable(value)` handles a possibly-null value.

### Avoid NullPointerException

An exception represents an abnormal condition that a program may catch or propagate.

It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Practical check:

- Define `Avoid NullPointerException` in one sentence.
- Recognize `Avoid NullPointerException` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Avoid NullPointerException`.

Tiny example or mental model:

- When reading code, ask: what does `Avoid NullPointerException` change, allow, reject, or clarify?

### Optional.of

Optional is a container that may or may not hold a non-null value.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Optional.of` in one sentence.
- Recognize `Optional.of` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Optional.of`.

Tiny example or mental model:

- `Optional.ofNullable(value)` handles a possibly-null value.

### Optional.ofNullable

Optional is a container that may or may not hold a non-null value.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Optional.ofNullable` in one sentence.
- Recognize `Optional.ofNullable` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Optional.ofNullable`.

Tiny example or mental model:

- `Optional.ofNullable(value)` handles a possibly-null value.

### Optional.empty

Optional is a container that may or may not hold a non-null value.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Optional.empty` in one sentence.
- Recognize `Optional.empty` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Optional.empty`.

Tiny example or mental model:

- `Optional.ofNullable(value)` handles a possibly-null value.

### isPresent

isPresent is a specific concept in Optional; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `isPresent` in one sentence.
- Recognize `isPresent` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `isPresent`.

Tiny example or mental model:

- When reading code, ask: what does `isPresent` change, allow, reject, or clarify?

### ifPresent

ifPresent is a specific concept in Optional; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `ifPresent` in one sentence.
- Recognize `ifPresent` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `ifPresent`.

Tiny example or mental model:

- When reading code, ask: what does `ifPresent` change, allow, reject, or clarify?

### orElse

orElse is a specific concept in Optional; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `orElse` in one sentence.
- Recognize `orElse` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `orElse`.

Tiny example or mental model:

- When reading code, ask: what does `orElse` change, allow, reject, or clarify?

### orElseGet

orElseGet is a specific concept in Optional; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `orElseGet` in one sentence.
- Recognize `orElseGet` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `orElseGet`.

Tiny example or mental model:

- When reading code, ask: what does `orElseGet` change, allow, reject, or clarify?

### orElseThrow

A Set is a collection that rejects duplicates according to equality rules.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `orElseThrow` in one sentence.
- Recognize `orElseThrow` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `orElseThrow`.

Tiny example or mental model:

- When reading code, ask: what does `orElseThrow` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
