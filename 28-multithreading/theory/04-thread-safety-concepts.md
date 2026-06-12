# Multithreading - Part 4

## Learning Goal

This file covers a focused slice of **Multithreading**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Thread safety` | A thread is a path of execution inside a process. |
| `Immutable object` |Immutable object is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Atomic operation` |An atomic operation appears indivisible to other threads. |

## Detailed Notes

### Thread safety

A thread is a path of execution inside a process.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Thread safety` in one sentence.
- Recognize `Thread safety` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Thread safety`.

Tiny example or mental model:

- `new Thread(task).start()` starts work on another thread.

### Immutable object

Immutable object is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Immutable object` in one sentence.
- Recognize `Immutable object` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Immutable object`.

Tiny example or mental model:

- When reading code, ask: what does `Immutable object` change, allow, reject, or clarify?

### Atomic operation

An atomic operation appears indivisible to other threads.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Atomic operation` in one sentence.
- Recognize `Atomic operation` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Atomic operation`.

Tiny example or mental model:

- When reading code, ask: what does `Atomic operation` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
