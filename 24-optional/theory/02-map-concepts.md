# Optional - Part 2

## Learning Goal

This file covers a focused slice of **Optional**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `map` | A Map stores key-value pairs and retrieves values by key. |
| `flatMap` | A Map stores key-value pairs and retrieves values by key. |
| `filter` |filter is a specific concept in Optional; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Do not overuse Optional` | Optional is a container that may or may not hold a non-null value. |
| `Optional in return type` | Optional is a container that may or may not hold a non-null value. |

## Detailed Notes

### map

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `map` in one sentence.
- Recognize `map` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `map`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### flatMap

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `flatMap` in one sentence.
- Recognize `flatMap` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `flatMap`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### filter

filter is a specific concept in Optional; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `filter` in one sentence.
- Recognize `filter` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `filter`.

Tiny example or mental model:

- When reading code, ask: what does `filter` change, allow, reject, or clarify?

### Do not overuse Optional

Optional is a container that may or may not hold a non-null value.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Do not overuse Optional` in one sentence.
- Recognize `Do not overuse Optional` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Do not overuse Optional`.

Tiny example or mental model:

- `Optional.ofNullable(value)` handles a possibly-null value.

### Optional in return type

Optional is a container that may or may not hold a non-null value.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Optional in return type` in one sentence.
- Recognize `Optional in return type` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Optional in return type`.

Tiny example or mental model:

- `Optional.ofNullable(value)` handles a possibly-null value.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
