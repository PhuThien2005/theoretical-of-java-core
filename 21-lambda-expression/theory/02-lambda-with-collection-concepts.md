# Lambda Expression - Part 2

## Learning Goal

This file covers a focused slice of **Lambda Expression**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Lambda with Collection` | A collection is an object that groups multiple elements under a common API. |
| `Lambda with Thread` | A lambda expression is a compact function-like block used where a functional interface is expected. |
| `Lambda with Comparator` | Comparator defines external custom ordering for objects. |

## Detailed Notes

### Lambda with Collection

A collection is an object that groups multiple elements under a common API.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Lambda with Collection` in one sentence.
- Recognize `Lambda with Collection` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Lambda with Collection`.

Tiny example or mental model:

- `n -> n > 0` is a lambda used as a predicate.

### Lambda with Thread

A lambda expression is a compact function-like block used where a functional interface is expected.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Lambda with Thread` in one sentence.
- Recognize `Lambda with Thread` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Lambda with Thread`.

Tiny example or mental model:

- `n -> n > 0` is a lambda used as a predicate.

### Lambda with Comparator

Comparator defines external custom ordering for objects.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `Lambda with Comparator` in one sentence.
- Recognize `Lambda with Comparator` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Lambda with Comparator`.

Tiny example or mental model:

- `n -> n > 0` is a lambda used as a predicate.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
