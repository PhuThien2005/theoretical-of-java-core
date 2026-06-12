# Stream API - Part 1

## Learning Goal

This file covers a focused slice of **Stream API**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `What is Stream?` | A Stream is a pipeline for processing elements through lazy operations. |
| `Stream vs Collection` | A collection is an object that groups multiple elements under a common API. |
| `Create Stream:` | A Stream is a pipeline for processing elements through lazy operations. |
| `from List` | A List is an ordered collection that can contain duplicates and supports positional access. |
| `from Array` |from Array is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `from Map` | A Map stores key-value pairs and retrieves values by key. |
| `Stream.of` | A Stream is a pipeline for processing elements through lazy operations. |
| `IntStream` | A Stream is a pipeline for processing elements through lazy operations. |

## Detailed Notes

### What is Stream?

A Stream is a pipeline for processing elements through lazy operations.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `What is Stream?` in one sentence.
- Recognize `What is Stream?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `What is Stream?`.

Tiny example or mental model:

- When reading code, ask: what does `What is Stream?` change, allow, reject, or clarify?

### Stream vs Collection

A collection is an object that groups multiple elements under a common API.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Stream vs Collection` in one sentence.
- Recognize `Stream vs Collection` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Stream vs Collection`.

Tiny example or mental model:

- When reading code, ask: what does `Stream vs Collection` change, allow, reject, or clarify?

### Create Stream:

A Stream is a pipeline for processing elements through lazy operations.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `Create Stream:` in one sentence.
- Recognize `Create Stream:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Create Stream:`.

Tiny example or mental model:

- When reading code, ask: what does `Create Stream:` change, allow, reject, or clarify?

### from List

A List is an ordered collection that can contain duplicates and supports positional access.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `from List` in one sentence.
- Recognize `from List` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `from List`.

Tiny example or mental model:

- `List<String> names = new ArrayList<>();` stores ordered elements.

### from Array

from Array is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `from Array` in one sentence.
- Recognize `from Array` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `from Array`.

Tiny example or mental model:

- When reading code, ask: what does `from Array` change, allow, reject, or clarify?

### from Map

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `from Map` in one sentence.
- Recognize `from Map` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `from Map`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### Stream.of

A Stream is a pipeline for processing elements through lazy operations.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `Stream.of` in one sentence.
- Recognize `Stream.of` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Stream.of`.

Tiny example or mental model:

- When reading code, ask: what does `Stream.of` change, allow, reject, or clarify?

### IntStream

A Stream is a pipeline for processing elements through lazy operations.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `IntStream` in one sentence.
- Recognize `IntStream` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `IntStream`.

Tiny example or mental model:

- When reading code, ask: what does `IntStream` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
