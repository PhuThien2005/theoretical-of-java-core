# Collections Framework - Part 5

## Learning Goal

This file covers a focused slice of **Collections Framework**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `ConcurrentHashMap` | A Map stores key-value pairs and retrieves values by key. |
| `WeakHashMap` | A Map stores key-value pairs and retrieves values by key. |
| `IdentityHashMap` | A Map stores key-value pairs and retrieves values by key. |
| `SortedMap` | A Map stores key-value pairs and retrieves values by key. |
| `NavigableMap` | A Map stores key-value pairs and retrieves values by key. |
| `When to use Map?` | A Map stores key-value pairs and retrieves values by key. |
| `Iterator` | An Iterator traverses a collection while hiding its internal representation. |
| `ListIterator` | A List is an ordered collection that can contain duplicates and supports positional access. |

## Detailed Notes

### ConcurrentHashMap

A Map stores key-value pairs and retrieves values by key.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `ConcurrentHashMap` in one sentence.
- Recognize `ConcurrentHashMap` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `ConcurrentHashMap`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### WeakHashMap

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `WeakHashMap` in one sentence.
- Recognize `WeakHashMap` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `WeakHashMap`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### IdentityHashMap

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `IdentityHashMap` in one sentence.
- Recognize `IdentityHashMap` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `IdentityHashMap`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### SortedMap

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `SortedMap` in one sentence.
- Recognize `SortedMap` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `SortedMap`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### NavigableMap

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `NavigableMap` in one sentence.
- Recognize `NavigableMap` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `NavigableMap`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### When to use Map?

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `When to use Map?` in one sentence.
- Recognize `When to use Map?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `When to use Map?`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### Iterator

An Iterator traverses a collection while hiding its internal representation.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Iterator` in one sentence.
- Recognize `Iterator` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Iterator`.

Tiny example or mental model:

- When reading code, ask: what does `Iterator` change, allow, reject, or clarify?

### ListIterator

A List is an ordered collection that can contain duplicates and supports positional access.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `ListIterator` in one sentence.
- Recognize `ListIterator` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `ListIterator`.

Tiny example or mental model:

- `List<String> names = new ArrayList<>();` stores ordered elements.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
