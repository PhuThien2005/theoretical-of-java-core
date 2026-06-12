# Collections Framework - Part 1

## Learning Goal

This file covers a focused slice of **Collections Framework**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `What is the Collection Framework?` | A collection is an object that groups multiple elements under a common API. |
| `Iterable` | Iterable is the root traversal contract that allows an object to be used in enhanced for loops. |
| `Collection` | A collection is an object that groups multiple elements under a common API. |
| `List` | A List is an ordered collection that can contain duplicates and supports positional access. |
| `Set` | A Set is a collection that rejects duplicates according to equality rules. |
| `Queue` | Queue represents a collection designed for holding elements before processing, usually FIFO. |
| `Deque` | Deque is a double-ended queue that supports insertion and removal at both ends. |
| `Map` | A Map stores key-value pairs and retrieves values by key. |

## Detailed Notes

### What is the Collection Framework?

A collection is an object that groups multiple elements under a common API.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `What is the Collection Framework?` in one sentence.
- Recognize `What is the Collection Framework?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `What is the Collection Framework?`.

Tiny example or mental model:

- When reading code, ask: what does `What is the Collection Framework?` change, allow, reject, or clarify?

### Iterable

Iterable is the root traversal contract that allows an object to be used in enhanced for loops.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Iterable` in one sentence.
- Recognize `Iterable` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Iterable`.

Tiny example or mental model:

- When reading code, ask: what does `Iterable` change, allow, reject, or clarify?

### Collection

A collection is an object that groups multiple elements under a common API.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Collection` in one sentence.
- Recognize `Collection` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Collection`.

Tiny example or mental model:

- When reading code, ask: what does `Collection` change, allow, reject, or clarify?

### List

A List is an ordered collection that can contain duplicates and supports positional access.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `List` in one sentence.
- Recognize `List` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `List`.

Tiny example or mental model:

- `List<String> names = new ArrayList<>();` stores ordered elements.

### Set

A Set is a collection that rejects duplicates according to equality rules.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Set` in one sentence.
- Recognize `Set` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Set`.

Tiny example or mental model:

- When reading code, ask: what does `Set` change, allow, reject, or clarify?

### Queue

Queue represents a collection designed for holding elements before processing, usually FIFO.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Queue` in one sentence.
- Recognize `Queue` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Queue`.

Tiny example or mental model:

- When reading code, ask: what does `Queue` change, allow, reject, or clarify?

### Deque

Deque is a double-ended queue that supports insertion and removal at both ends.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Deque` in one sentence.
- Recognize `Deque` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Deque`.

Tiny example or mental model:

- When reading code, ask: what does `Deque` change, allow, reject, or clarify?

### Map

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Map` in one sentence.
- Recognize `Map` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Map`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
