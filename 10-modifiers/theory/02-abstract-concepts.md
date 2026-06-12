# Modifiers in Java - Part 2

## Learning Goal

This file covers a focused slice of **Modifiers in Java**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `abstract` | Abstract means incomplete by design: subclasses or implementations must provide missing behavior. |
| `synchronized` | Synchronized protects a critical section by using a monitor lock. |
| `volatile` | Volatile gives visibility guarantees for a variable shared between threads, but it does not make compound operations atomic. |
| `transient` | Transient marks a field that should be skipped during Java serialization. |
| `native` |native is a specific concept in Modifiers in Java; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `strictfp` |strictfp is a specific concept in Modifiers in Java; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Static variable` | Static means the member belongs to the class rather than to one particular object. |
| `Static method` | Static means the member belongs to the class rather than to one particular object. |

## Detailed Notes

### abstract

Abstract means incomplete by design: subclasses or implementations must provide missing behavior.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `abstract` in one sentence.
- Recognize `abstract` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `abstract`.

Tiny example or mental model:

- When reading code, ask: what does `abstract` change, allow, reject, or clarify?

### synchronized

Synchronized protects a critical section by using a monitor lock.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `synchronized` in one sentence.
- Recognize `synchronized` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `synchronized`.

Tiny example or mental model:

- When reading code, ask: what does `synchronized` change, allow, reject, or clarify?

### volatile

Volatile gives visibility guarantees for a variable shared between threads, but it does not make compound operations atomic.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `volatile` in one sentence.
- Recognize `volatile` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `volatile`.

Tiny example or mental model:

- When reading code, ask: what does `volatile` change, allow, reject, or clarify?

### transient

Transient marks a field that should be skipped during Java serialization.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `transient` in one sentence.
- Recognize `transient` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `transient`.

Tiny example or mental model:

- When reading code, ask: what does `transient` change, allow, reject, or clarify?

### native

native is a specific concept in Modifiers in Java; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `native` in one sentence.
- Recognize `native` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `native`.

Tiny example or mental model:

- When reading code, ask: what does `native` change, allow, reject, or clarify?

### strictfp

strictfp is a specific concept in Modifiers in Java; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `strictfp` in one sentence.
- Recognize `strictfp` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `strictfp`.

Tiny example or mental model:

- When reading code, ask: what does `strictfp` change, allow, reject, or clarify?

### Static variable

Static means the member belongs to the class rather than to one particular object.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Static variable` in one sentence.
- Recognize `Static variable` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Static variable`.

Tiny example or mental model:

- `ClassName.member` accesses a class-level member.

### Static method

Static means the member belongs to the class rather than to one particular object.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Static method` in one sentence.
- Recognize `Static method` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Static method`.

Tiny example or mental model:

- `ClassName.member` accesses a class-level member.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
