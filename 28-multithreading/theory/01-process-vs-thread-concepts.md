# Multithreading - Part 1

## Learning Goal

This file covers a focused slice of **Multithreading**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Process vs Thread` | A thread is a path of execution inside a process. |
| `Create thread using:` | A thread is a path of execution inside a process. |
| `extends Thread` | A thread is a path of execution inside a process. |
| `implements Runnable` |implements Runnable is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `implements Callable` |implements Callable is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `ExecutorService` |ExecutorService manages task execution using worker threads. |
| `Lifecycle of Thread:` | A thread is a path of execution inside a process. |
| `New` |New is the thread state after a Thread object is created but before start is called. |

## Detailed Notes

### Process vs Thread

A thread is a path of execution inside a process.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Process vs Thread` in one sentence.
- Recognize `Process vs Thread` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Process vs Thread`.

Tiny example or mental model:

- `new Thread(task).start()` starts work on another thread.

### Create thread using:

A thread is a path of execution inside a process.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Create thread using:` in one sentence.
- Recognize `Create thread using:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Create thread using:`.

Tiny example or mental model:

- `new Thread(task).start()` starts work on another thread.

### extends Thread

A thread is a path of execution inside a process.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `extends Thread` in one sentence.
- Recognize `extends Thread` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `extends Thread`.

Tiny example or mental model:

- `new Thread(task).start()` starts work on another thread.

### implements Runnable

implements Runnable is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `implements Runnable` in one sentence.
- Recognize `implements Runnable` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `implements Runnable`.

Tiny example or mental model:

- When reading code, ask: what does `implements Runnable` change, allow, reject, or clarify?

### implements Callable

implements Callable is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `implements Callable` in one sentence.
- Recognize `implements Callable` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `implements Callable`.

Tiny example or mental model:

- When reading code, ask: what does `implements Callable` change, allow, reject, or clarify?

### ExecutorService

ExecutorService manages task execution using worker threads.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `ExecutorService` in one sentence.
- Recognize `ExecutorService` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `ExecutorService`.

Tiny example or mental model:

- When reading code, ask: what does `ExecutorService` change, allow, reject, or clarify?

### Lifecycle of Thread:

A thread is a path of execution inside a process.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Lifecycle of Thread:` in one sentence.
- Recognize `Lifecycle of Thread:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Lifecycle of Thread:`.

Tiny example or mental model:

- `new Thread(task).start()` starts work on another thread.

### New

New is the thread state after a Thread object is created but before start is called.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `New` in one sentence.
- Recognize `New` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `New`.

Tiny example or mental model:

- When reading code, ask: what does `New` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
