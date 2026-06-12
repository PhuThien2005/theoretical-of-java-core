# Synchronization and Concurrency Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## monitor

monitor is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `monitor` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `monitor` change, allow, reject, or clarify?

## object lock

object lock is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Common confusion: learners often memorize `object lock` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `object lock` change, allow, reject, or clarify?

## deadlock

Deadlock happens when threads wait forever for locks held by each other.

Why it matters: It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Common confusion: learners often memorize `deadlock` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `deadlock` change, allow, reject, or clarify?

## volatile

Volatile gives visibility guarantees for a variable shared between threads, but it does not make compound operations atomic.

Why it matters: It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Common confusion: learners often memorize `volatile` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `volatile` change, allow, reject, or clarify?

## atomic class

atomic class is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Common confusion: learners often memorize `atomic class` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `atomic class` change, allow, reject, or clarify?

## ReentrantLock

ReentrantLock is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Common confusion: learners often memorize `ReentrantLock` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `ReentrantLock` change, allow, reject, or clarify?

## CountDownLatch

CountDownLatch is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `CountDownLatch` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `CountDownLatch` change, allow, reject, or clarify?

## CompletableFuture

CompletableFuture is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `CompletableFuture` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `CompletableFuture` change, allow, reject, or clarify?
