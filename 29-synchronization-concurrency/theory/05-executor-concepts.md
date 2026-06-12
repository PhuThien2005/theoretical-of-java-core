# Synchronization and Concurrency - Part 5

## Learning Goal

This file covers a focused slice of **Synchronization and Concurrency**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Executor` |Executor is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `ExecutorService` |ExecutorService manages task execution using worker threads. |
| `ScheduledExecutorService` |ScheduledExecutorService is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `ThreadPoolExecutor` | A thread is a path of execution inside a process. |
| `Executors` |Executors is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Future` |Future is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Callable` |Callable represents a task that returns a result and can throw checked exceptions. |
| `CompletableFuture` |CompletableFuture is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### Executor

Executor is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Executor` in one sentence.
- Recognize `Executor` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Executor`.

Tiny example or mental model:

- When reading code, ask: what does `Executor` change, allow, reject, or clarify?

### ExecutorService

ExecutorService manages task execution using worker threads.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `ExecutorService` in one sentence.
- Recognize `ExecutorService` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `ExecutorService`.

Tiny example or mental model:

- When reading code, ask: what does `ExecutorService` change, allow, reject, or clarify?

### ScheduledExecutorService

ScheduledExecutorService is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `ScheduledExecutorService` in one sentence.
- Recognize `ScheduledExecutorService` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `ScheduledExecutorService`.

Tiny example or mental model:

- When reading code, ask: what does `ScheduledExecutorService` change, allow, reject, or clarify?

### ThreadPoolExecutor

A thread is a path of execution inside a process.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `ThreadPoolExecutor` in one sentence.
- Recognize `ThreadPoolExecutor` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `ThreadPoolExecutor`.

Tiny example or mental model:

- `new Thread(task).start()` starts work on another thread.

### Executors

Executors is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Executors` in one sentence.
- Recognize `Executors` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Executors`.

Tiny example or mental model:

- When reading code, ask: what does `Executors` change, allow, reject, or clarify?

### Future

Future is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Future` in one sentence.
- Recognize `Future` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Future`.

Tiny example or mental model:

- When reading code, ask: what does `Future` change, allow, reject, or clarify?

### Callable

Callable represents a task that returns a result and can throw checked exceptions.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Callable` in one sentence.
- Recognize `Callable` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Callable`.

Tiny example or mental model:

- When reading code, ask: what does `Callable` change, allow, reject, or clarify?

### CompletableFuture

CompletableFuture is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `CompletableFuture` in one sentence.
- Recognize `CompletableFuture` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `CompletableFuture`.

Tiny example or mental model:

- When reading code, ask: what does `CompletableFuture` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
