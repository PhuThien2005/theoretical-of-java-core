# Common Java Core Interview Questions - Part 3

## Learning Goal

This file covers a focused slice of **Common Java Core Interview Questions**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `How are Comparable and Comparator different?` | Comparable defines natural ordering inside the class being compared. |
| `How are fail-fast and fail-safe iterators different?` | An Iterator traverses a collection while hiding its internal representation. |
| `How are volatile and synchronized different?` | Volatile gives visibility guarantees for a variable shared between threads, but it does not make compound operations atomic. |
| `What is deadlock?` | Deadlock happens when threads wait forever for locks held by each other. |
| `How are Thread start() and run() different?` | A thread is a path of execution inside a process. |
| `How are sleep() and wait() different?` |How are sleep() and wait() different? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `How are notify() and notifyAll() different?` |How are notify() and notifyAll() different? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Is Stream API lazy?` | A Stream is a pipeline for processing elements through lazy operations. |

## Detailed Notes

### How are Comparable and Comparator different?

Comparable defines natural ordering inside the class being compared.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `How are Comparable and Comparator different?` in one sentence.
- Recognize `How are Comparable and Comparator different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are Comparable and Comparator different?`.

Tiny example or mental model:

- When reading code, ask: what does `How are Comparable and Comparator different?` change, allow, reject, or clarify?

### How are fail-fast and fail-safe iterators different?

An Iterator traverses a collection while hiding its internal representation.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `How are fail-fast and fail-safe iterators different?` in one sentence.
- Recognize `How are fail-fast and fail-safe iterators different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are fail-fast and fail-safe iterators different?`.

Tiny example or mental model:

- When reading code, ask: what does `How are fail-fast and fail-safe iterators different?` change, allow, reject, or clarify?

### How are volatile and synchronized different?

Volatile gives visibility guarantees for a variable shared between threads, but it does not make compound operations atomic.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `How are volatile and synchronized different?` in one sentence.
- Recognize `How are volatile and synchronized different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are volatile and synchronized different?`.

Tiny example or mental model:

- When reading code, ask: what does `How are volatile and synchronized different?` change, allow, reject, or clarify?

### What is deadlock?

Deadlock happens when threads wait forever for locks held by each other.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `What is deadlock?` in one sentence.
- Recognize `What is deadlock?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `What is deadlock?`.

Tiny example or mental model:

- When reading code, ask: what does `What is deadlock?` change, allow, reject, or clarify?

### How are Thread start() and run() different?

A thread is a path of execution inside a process.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `How are Thread start() and run() different?` in one sentence.
- Recognize `How are Thread start() and run() different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are Thread start() and run() different?`.

Tiny example or mental model:

- `new Thread(task).start()` starts work on another thread.

### How are sleep() and wait() different?

How are sleep() and wait() different? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `How are sleep() and wait() different?` in one sentence.
- Recognize `How are sleep() and wait() different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are sleep() and wait() different?`.

Tiny example or mental model:

- When reading code, ask: what does `How are sleep() and wait() different?` change, allow, reject, or clarify?

### How are notify() and notifyAll() different?

How are notify() and notifyAll() different? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `How are notify() and notifyAll() different?` in one sentence.
- Recognize `How are notify() and notifyAll() different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are notify() and notifyAll() different?`.

Tiny example or mental model:

- When reading code, ask: what does `How are notify() and notifyAll() different?` change, allow, reject, or clarify?

### Is Stream API lazy?

A Stream is a pipeline for processing elements through lazy operations.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `Is Stream API lazy?` in one sentence.
- Recognize `Is Stream API lazy?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Is Stream API lazy?`.

Tiny example or mental model:

- When reading code, ask: what does `Is Stream API lazy?` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
