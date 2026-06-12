# Stream API - Part 5

## Learning Goal

This file covers a focused slice of **Stream API**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Lazy evaluation` |Lazy evaluation is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Short-circuiting` |Short-circuiting is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Parallel stream` | A Stream is a pipeline for processing elements through lazy operations. |
| `Collectors:` | Collectors is a group of related rules in Stream API that groups several related details. |
| `toSet` | A Set is a collection that rejects duplicates according to equality rules. |
| `toMap` | A Map stores key-value pairs and retrieves values by key. |
| `joining` |joining is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `groupingBy` |groupingBy is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### Lazy evaluation

Lazy evaluation is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Lazy evaluation` in one sentence.
- Recognize `Lazy evaluation` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Lazy evaluation`.

Tiny example or mental model:

- When reading code, ask: what does `Lazy evaluation` change, allow, reject, or clarify?

### Short-circuiting

Short-circuiting is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Short-circuiting` in one sentence.
- Recognize `Short-circuiting` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Short-circuiting`.

Tiny example or mental model:

- When reading code, ask: what does `Short-circuiting` change, allow, reject, or clarify?

### Parallel stream

A Stream is a pipeline for processing elements through lazy operations.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `Parallel stream` in one sentence.
- Recognize `Parallel stream` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Parallel stream`.

Tiny example or mental model:

- When reading code, ask: what does `Parallel stream` change, allow, reject, or clarify?

### Collectors:

Collectors is a group of related rules in Stream API that groups several related details.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Collectors:` in one sentence.
- Recognize `Collectors:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Collectors:`.

Tiny example or mental model:

- When reading code, ask: what does `Collectors:` change, allow, reject, or clarify?

### toSet

A Set is a collection that rejects duplicates according to equality rules.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `toSet` in one sentence.
- Recognize `toSet` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `toSet`.

Tiny example or mental model:

- When reading code, ask: what does `toSet` change, allow, reject, or clarify?

### toMap

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `toMap` in one sentence.
- Recognize `toMap` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `toMap`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### joining

joining is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `joining` in one sentence.
- Recognize `joining` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `joining`.

Tiny example or mental model:

- When reading code, ask: what does `joining` change, allow, reject, or clarify?

### groupingBy

groupingBy is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `groupingBy` in one sentence.
- Recognize `groupingBy` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `groupingBy`.

Tiny example or mental model:

- When reading code, ask: what does `groupingBy` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
