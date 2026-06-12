# Multithreading - Part 3

## Learning Goal

This file covers a focused slice of **Multithreading**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `join` |join is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `yield` |yield is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `interrupt` |interrupt is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Daemon thread` | A thread is a path of execution inside a process. |
| `User thread` | A thread is a path of execution inside a process. |
| `Thread priority` | A thread is a path of execution inside a process. |
| `Race condition` | A race condition happens when correctness depends on unpredictable timing between threads. |
| `Critical section` |A critical section is code that accesses shared mutable state and must be protected. |

## Detailed Notes

### join

join is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `join` in one sentence.
- Recognize `join` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `join`.

Tiny example or mental model:

- When reading code, ask: what does `join` change, allow, reject, or clarify?

### yield

yield is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `yield` in one sentence.
- Recognize `yield` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `yield`.

Tiny example or mental model:

- When reading code, ask: what does `yield` change, allow, reject, or clarify?

### interrupt

interrupt is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `interrupt` in one sentence.
- Recognize `interrupt` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `interrupt`.

Tiny example or mental model:

- When reading code, ask: what does `interrupt` change, allow, reject, or clarify?

### Daemon thread

A thread is a path of execution inside a process.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Daemon thread` in one sentence.
- Recognize `Daemon thread` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Daemon thread`.

Tiny example or mental model:

- `new Thread(task).start()` starts work on another thread.

### User thread

A thread is a path of execution inside a process.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `User thread` in one sentence.
- Recognize `User thread` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `User thread`.

Tiny example or mental model:

- `new Thread(task).start()` starts work on another thread.

### Thread priority

A thread is a path of execution inside a process.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Thread priority` in one sentence.
- Recognize `Thread priority` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Thread priority`.

Tiny example or mental model:

- `new Thread(task).start()` starts work on another thread.

### Race condition

A race condition happens when correctness depends on unpredictable timing between threads.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Race condition` in one sentence.
- Recognize `Race condition` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Race condition`.

Tiny example or mental model:

- When reading code, ask: what does `Race condition` change, allow, reject, or clarify?

### Critical section

A critical section is code that accesses shared mutable state and must be protected.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Critical section` in one sentence.
- Recognize `Critical section` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Critical section`.

Tiny example or mental model:

- When reading code, ask: what does `Critical section` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
