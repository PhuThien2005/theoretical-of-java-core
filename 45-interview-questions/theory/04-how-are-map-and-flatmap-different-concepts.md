# Common Java Core Interview Questions - Part 4

## Learning Goal

This file covers a focused slice of **Common Java Core Interview Questions**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `How are map and flatMap different?` | A Map stores key-value pairs and retrieves values by key. |
| `How are orElse and orElseGet different?` |How are orElse and orElseGet different? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `How are HashMap, Hashtable, and ConcurrentHashMap different?` | A Map stores key-value pairs and retrieves values by key. |
| `Why must overriding equals() also override hashCode()?` | equals() defines logical equality between objects. |
| `How does Garbage Collection work?` | Garbage collection reclaims memory from objects that are no longer reachable. |
| `How are Stack and Heap different?` | Stack stores method frames, local variables, and call flow for each thread. |

## Detailed Notes

### How are map and flatMap different?

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `How are map and flatMap different?` in one sentence.
- Recognize `How are map and flatMap different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are map and flatMap different?`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### How are orElse and orElseGet different?

How are orElse and orElseGet different? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `How are orElse and orElseGet different?` in one sentence.
- Recognize `How are orElse and orElseGet different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are orElse and orElseGet different?`.

Tiny example or mental model:

- When reading code, ask: what does `How are orElse and orElseGet different?` change, allow, reject, or clarify?

### How are HashMap, Hashtable, and ConcurrentHashMap different?

A Map stores key-value pairs and retrieves values by key.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `How are HashMap, Hashtable, and ConcurrentHashMap different?` in one sentence.
- Recognize `How are HashMap, Hashtable, and ConcurrentHashMap different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are HashMap, Hashtable, and ConcurrentHashMap different?`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### Why must overriding equals() also override hashCode()?

equals() defines logical equality between objects.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Why must overriding equals() also override hashCode()?` in one sentence.
- Recognize `Why must overriding equals() also override hashCode()?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Why must overriding equals() also override hashCode()?`.

Tiny example or mental model:

- When reading code, ask: what does `Why must overriding equals() also override hashCode()?` change, allow, reject, or clarify?

### How does Garbage Collection work?

Garbage collection reclaims memory from objects that are no longer reachable.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `How does Garbage Collection work?` in one sentence.
- Recognize `How does Garbage Collection work?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How does Garbage Collection work?`.

Tiny example or mental model:

- When reading code, ask: what does `How does Garbage Collection work?` change, allow, reject, or clarify?

### How are Stack and Heap different?

Stack stores method frames, local variables, and call flow for each thread.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `How are Stack and Heap different?` in one sentence.
- Recognize `How are Stack and Heap different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are Stack and Heap different?`.

Tiny example or mental model:

- When reading code, ask: what does `How are Stack and Heap different?` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
