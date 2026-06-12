# Inner Class and Nested Class - Part 1

## Learning Goal

This file covers a focused slice of **Inner Class and Nested Class**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Nested class` |Nested class is a specific concept in Inner Class and Nested Class; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Static nested class` | Static means the member belongs to the class rather than to one particular object. |
| `Inner class` |Inner class is a specific concept in Inner Class and Nested Class; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Local inner class` |Local inner class is a specific concept in Inner Class and Nested Class; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Anonymous inner class` |Anonymous inner class is a specific concept in Inner Class and Nested Class; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Access variables outside the class` |Access variables outside the class is a specific concept in Inner Class and Nested Class; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Use case of inner class` |Use case of inner class is a specific concept in Inner Class and Nested Class; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Anonymous class in event handler, thread, comparator` | Comparator defines external custom ordering for objects. |

## Detailed Notes

### Nested class

Nested class is a specific concept in Inner Class and Nested Class; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Nested class` in one sentence.
- Recognize `Nested class` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Nested class`.

Tiny example or mental model:

- When reading code, ask: what does `Nested class` change, allow, reject, or clarify?

### Static nested class

Static means the member belongs to the class rather than to one particular object.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Static nested class` in one sentence.
- Recognize `Static nested class` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Static nested class`.

Tiny example or mental model:

- `ClassName.member` accesses a class-level member.

### Inner class

Inner class is a specific concept in Inner Class and Nested Class; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Inner class` in one sentence.
- Recognize `Inner class` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Inner class`.

Tiny example or mental model:

- When reading code, ask: what does `Inner class` change, allow, reject, or clarify?

### Local inner class

Local inner class is a specific concept in Inner Class and Nested Class; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Local inner class` in one sentence.
- Recognize `Local inner class` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Local inner class`.

Tiny example or mental model:

- When reading code, ask: what does `Local inner class` change, allow, reject, or clarify?

### Anonymous inner class

Anonymous inner class is a specific concept in Inner Class and Nested Class; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Anonymous inner class` in one sentence.
- Recognize `Anonymous inner class` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Anonymous inner class`.

Tiny example or mental model:

- When reading code, ask: what does `Anonymous inner class` change, allow, reject, or clarify?

### Access variables outside the class

Access variables outside the class is a specific concept in Inner Class and Nested Class; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Access variables outside the class` in one sentence.
- Recognize `Access variables outside the class` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Access variables outside the class`.

Tiny example or mental model:

- When reading code, ask: what does `Access variables outside the class` change, allow, reject, or clarify?

### Use case of inner class

Use case of inner class is a specific concept in Inner Class and Nested Class; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Use case of inner class` in one sentence.
- Recognize `Use case of inner class` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Use case of inner class`.

Tiny example or mental model:

- When reading code, ask: what does `Use case of inner class` change, allow, reject, or clarify?

### Anonymous class in event handler, thread, comparator

Comparator defines external custom ordering for objects.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Anonymous class in event handler, thread, comparator` in one sentence.
- Recognize `Anonymous class in event handler, thread, comparator` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Anonymous class in event handler, thread, comparator`.

Tiny example or mental model:

- `new Thread(task).start()` starts work on another thread.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
