# Multithreading Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## process

process is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `process` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `process` change, allow, reject, or clarify?

## thread

A thread is a path of execution inside a process.

Why it matters: It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Common confusion: learners often memorize `thread` as a word but cannot explain what problem it solves or what rule it changes.

Small example: `new Thread(task).start()` starts work on another thread.

## Runnable

Runnable means a thread is eligible to run, though it may be waiting for CPU scheduling.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `Runnable` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `Runnable` change, allow, reject, or clarify?

## Callable

Callable represents a task that returns a result and can throw checked exceptions.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `Callable` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `Callable` change, allow, reject, or clarify?

## thread lifecycle

A thread is a path of execution inside a process.

Why it matters: It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Common confusion: learners often memorize `thread lifecycle` as a word but cannot explain what problem it solves or what rule it changes.

Small example: `new Thread(task).start()` starts work on another thread.

## interrupt

interrupt is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `interrupt` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `interrupt` change, allow, reject, or clarify?

## daemon thread

A thread is a path of execution inside a process.

Why it matters: It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Common confusion: learners often memorize `daemon thread` as a word but cannot explain what problem it solves or what rule it changes.

Small example: `new Thread(task).start()` starts work on another thread.

## race condition

A race condition happens when correctness depends on unpredictable timing between threads.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `race condition` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `race condition` change, allow, reject, or clarify?
