# Collections Framework - Part 7

## Learning Goal

This file covers a focused slice of **Collections Framework**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Collections.unmodifiableList` | A collection is an object that groups multiple elements under a common API. |
| `Collections.synchronizedList` | Synchronized protects a critical section by using a monitor lock. |
| `Arrays.sort` |Arrays.sort is a specific concept in Collections Framework; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Arrays.binarySearch` |Arrays.binarySearch is a specific concept in Collections Framework; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Arrays.asList` | A List is an ordered collection that can contain duplicates and supports positional access. |
| `Arrays.copyOf` |Arrays.copyOf is a specific concept in Collections Framework; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Arrays.equals` | equals() defines logical equality between objects. |
| `Arrays.deepEquals` | equals() defines logical equality between objects. |

## Detailed Notes

### Collections.unmodifiableList

A collection is an object that groups multiple elements under a common API.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Collections.unmodifiableList` in one sentence.
- Recognize `Collections.unmodifiableList` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Collections.unmodifiableList`.

Tiny example or mental model:

- `List<String> names = new ArrayList<>();` stores ordered elements.

### Collections.synchronizedList

Synchronized protects a critical section by using a monitor lock.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Collections.synchronizedList` in one sentence.
- Recognize `Collections.synchronizedList` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Collections.synchronizedList`.

Tiny example or mental model:

- `List<String> names = new ArrayList<>();` stores ordered elements.

### Arrays.sort

Arrays.sort is a specific concept in Collections Framework; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Arrays.sort` in one sentence.
- Recognize `Arrays.sort` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Arrays.sort`.

Tiny example or mental model:

- When reading code, ask: what does `Arrays.sort` change, allow, reject, or clarify?

### Arrays.binarySearch

Arrays.binarySearch is a specific concept in Collections Framework; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Arrays.binarySearch` in one sentence.
- Recognize `Arrays.binarySearch` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Arrays.binarySearch`.

Tiny example or mental model:

- When reading code, ask: what does `Arrays.binarySearch` change, allow, reject, or clarify?

### Arrays.asList

A List is an ordered collection that can contain duplicates and supports positional access.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Arrays.asList` in one sentence.
- Recognize `Arrays.asList` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Arrays.asList`.

Tiny example or mental model:

- `List<String> names = new ArrayList<>();` stores ordered elements.

### Arrays.copyOf

Arrays.copyOf is a specific concept in Collections Framework; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Arrays.copyOf` in one sentence.
- Recognize `Arrays.copyOf` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Arrays.copyOf`.

Tiny example or mental model:

- When reading code, ask: what does `Arrays.copyOf` change, allow, reject, or clarify?

### Arrays.equals

equals() defines logical equality between objects.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Arrays.equals` in one sentence.
- Recognize `Arrays.equals` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Arrays.equals`.

Tiny example or mental model:

- When reading code, ask: what does `Arrays.equals` change, allow, reject, or clarify?

### Arrays.deepEquals

equals() defines logical equality between objects.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Arrays.deepEquals` in one sentence.
- Recognize `Arrays.deepEquals` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Arrays.deepEquals`.

Tiny example or mental model:

- When reading code, ask: what does `Arrays.deepEquals` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
