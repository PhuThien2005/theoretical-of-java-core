# Stream API - Part 2

## Learning Goal

This file covers a focused slice of **Stream API**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `LongStream` | A Stream is a pipeline for processing elements through lazy operations. |
| `DoubleStream` | A Stream is a pipeline for processing elements through lazy operations. |
| `Intermediate operations:` | Intermediate operations is a group of related rules in Stream API that groups several related details. |
| `filter` |filter is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `map` | A Map stores key-value pairs and retrieves values by key. |
| `flatMap` | A Map stores key-value pairs and retrieves values by key. |
| `distinct` |distinct is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `sorted` |sorted is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### LongStream

A Stream is a pipeline for processing elements through lazy operations.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `LongStream` in one sentence.
- Recognize `LongStream` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `LongStream`.

Tiny example or mental model:

- When reading code, ask: what does `LongStream` change, allow, reject, or clarify?

### DoubleStream

A Stream is a pipeline for processing elements through lazy operations.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `DoubleStream` in one sentence.
- Recognize `DoubleStream` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `DoubleStream`.

Tiny example or mental model:

- When reading code, ask: what does `DoubleStream` change, allow, reject, or clarify?

### Intermediate operations:

Intermediate operations is a group of related rules in Stream API that groups several related details.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Intermediate operations:` in one sentence.
- Recognize `Intermediate operations:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Intermediate operations:`.

Tiny example or mental model:

- When reading code, ask: what does `Intermediate operations:` change, allow, reject, or clarify?

### filter

filter is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `filter` in one sentence.
- Recognize `filter` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `filter`.

Tiny example or mental model:

- When reading code, ask: what does `filter` change, allow, reject, or clarify?

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

### distinct

distinct is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `distinct` in one sentence.
- Recognize `distinct` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `distinct`.

Tiny example or mental model:

- When reading code, ask: what does `distinct` change, allow, reject, or clarify?

### sorted

sorted is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `sorted` in one sentence.
- Recognize `sorted` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `sorted`.

Tiny example or mental model:

- When reading code, ask: what does `sorted` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
