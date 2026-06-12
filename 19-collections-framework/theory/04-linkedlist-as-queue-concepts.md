# Collections Framework - Part 4

## Learning Goal

This file covers a focused slice of **Collections Framework**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `LinkedList as Queue` | A List is an ordered collection that can contain duplicates and supports positional access. |
| `FIFO` |FIFO is a specific concept in Collections Framework; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `LIFO` |LIFO is a specific concept in Collections Framework; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Priority queue` |Priority queue is a specific concept in Collections Framework; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `HashMap` | A Map stores key-value pairs and retrieves values by key. |
| `LinkedHashMap` | A Map stores key-value pairs and retrieves values by key. |
| `TreeMap` | A Map stores key-value pairs and retrieves values by key. |
| `Hashtable` |Hashtable is a legacy synchronized Map generally replaced by modern alternatives. |

## Detailed Notes

### LinkedList as Queue

A List is an ordered collection that can contain duplicates and supports positional access.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `LinkedList as Queue` in one sentence.
- Recognize `LinkedList as Queue` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `LinkedList as Queue`.

Tiny example or mental model:

- `List<String> names = new ArrayList<>();` stores ordered elements.

### FIFO

FIFO is a specific concept in Collections Framework; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `FIFO` in one sentence.
- Recognize `FIFO` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `FIFO`.

Tiny example or mental model:

- When reading code, ask: what does `FIFO` change, allow, reject, or clarify?

### LIFO

LIFO is a specific concept in Collections Framework; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `LIFO` in one sentence.
- Recognize `LIFO` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `LIFO`.

Tiny example or mental model:

- When reading code, ask: what does `LIFO` change, allow, reject, or clarify?

### Priority queue

Priority queue is a specific concept in Collections Framework; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Priority queue` in one sentence.
- Recognize `Priority queue` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Priority queue`.

Tiny example or mental model:

- When reading code, ask: what does `Priority queue` change, allow, reject, or clarify?

### HashMap

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `HashMap` in one sentence.
- Recognize `HashMap` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `HashMap`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### LinkedHashMap

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `LinkedHashMap` in one sentence.
- Recognize `LinkedHashMap` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `LinkedHashMap`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### TreeMap

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `TreeMap` in one sentence.
- Recognize `TreeMap` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `TreeMap`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### Hashtable

Hashtable is a legacy synchronized Map generally replaced by modern alternatives.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Hashtable` in one sentence.
- Recognize `Hashtable` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Hashtable`.

Tiny example or mental model:

- When reading code, ask: what does `Hashtable` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
