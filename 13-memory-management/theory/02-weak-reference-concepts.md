# Java Memory Management - Part 2

## Learning Goal

This file covers a focused slice of **Java Memory Management**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Weak reference` |Weak reference is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Soft reference` |Soft reference is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Phantom reference` |Phantom reference is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Garbage Collection` | Garbage collection reclaims memory from objects that are no longer reachable. |
| `Conditions for an object to be GC'd` |Conditions for an object to be GC'd is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `System.gc()` |System.gc() is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Finalization, finalize() deprecated` | Final means the variable, method, class, or parameter is restricted from later change in a specific way. |
| `Memory leak in Java` |Memory leak in Java is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### Weak reference

Weak reference is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Weak reference` in one sentence.
- Recognize `Weak reference` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Weak reference`.

Tiny example or mental model:

- When reading code, ask: what does `Weak reference` change, allow, reject, or clarify?

### Soft reference

Soft reference is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Soft reference` in one sentence.
- Recognize `Soft reference` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Soft reference`.

Tiny example or mental model:

- When reading code, ask: what does `Soft reference` change, allow, reject, or clarify?

### Phantom reference

Phantom reference is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Phantom reference` in one sentence.
- Recognize `Phantom reference` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Phantom reference`.

Tiny example or mental model:

- When reading code, ask: what does `Phantom reference` change, allow, reject, or clarify?

### Garbage Collection

Garbage collection reclaims memory from objects that are no longer reachable.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Garbage Collection` in one sentence.
- Recognize `Garbage Collection` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Garbage Collection`.

Tiny example or mental model:

- When reading code, ask: what does `Garbage Collection` change, allow, reject, or clarify?

### Conditions for an object to be GC'd

Conditions for an object to be GC'd is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Conditions for an object to be GC'd` in one sentence.
- Recognize `Conditions for an object to be GC'd` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Conditions for an object to be GC'd`.

Tiny example or mental model:

- When reading code, ask: what does `Conditions for an object to be GC'd` change, allow, reject, or clarify?

### System.gc()

System.gc() is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `System.gc()` in one sentence.
- Recognize `System.gc()` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `System.gc()`.

Tiny example or mental model:

- When reading code, ask: what does `System.gc()` change, allow, reject, or clarify?

### Finalization, finalize() deprecated

Final means the variable, method, class, or parameter is restricted from later change in a specific way.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Finalization, finalize() deprecated` in one sentence.
- Recognize `Finalization, finalize() deprecated` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Finalization, finalize() deprecated`.

Tiny example or mental model:

- `final int limit = 10;` cannot be reassigned.

### Memory leak in Java

Memory leak in Java is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Memory leak in Java` in one sentence.
- Recognize `Memory leak in Java` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Memory leak in Java`.

Tiny example or mental model:

- When reading code, ask: what does `Memory leak in Java` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
