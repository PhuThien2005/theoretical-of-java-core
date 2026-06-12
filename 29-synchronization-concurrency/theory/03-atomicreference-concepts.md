# Synchronization and Concurrency - Part 3

## Learning Goal

This file covers a focused slice of **Synchronization and Concurrency**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `AtomicReference` |AtomicReference is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Lock API:` | Lock API is a group of related rules in Synchronization and Concurrency that groups several related details. |
| `Lock` |Lock is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `ReentrantLock` |ReentrantLock is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `ReadWriteLock` |ReadWriteLock is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `StampedLock` |StampedLock is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Semaphore` | A Map stores key-value pairs and retrieves values by key. |
| `CountDownLatch` |CountDownLatch is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### AtomicReference

AtomicReference is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `AtomicReference` in one sentence.
- Recognize `AtomicReference` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `AtomicReference`.

Tiny example or mental model:

- When reading code, ask: what does `AtomicReference` change, allow, reject, or clarify?

### Lock API:

Lock API is a group of related rules in Synchronization and Concurrency that groups several related details.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Lock API:` in one sentence.
- Recognize `Lock API:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Lock API:`.

Tiny example or mental model:

- When reading code, ask: what does `Lock API:` change, allow, reject, or clarify?

### Lock

Lock is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Lock` in one sentence.
- Recognize `Lock` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Lock`.

Tiny example or mental model:

- When reading code, ask: what does `Lock` change, allow, reject, or clarify?

### ReentrantLock

ReentrantLock is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `ReentrantLock` in one sentence.
- Recognize `ReentrantLock` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `ReentrantLock`.

Tiny example or mental model:

- When reading code, ask: what does `ReentrantLock` change, allow, reject, or clarify?

### ReadWriteLock

ReadWriteLock is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `ReadWriteLock` in one sentence.
- Recognize `ReadWriteLock` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `ReadWriteLock`.

Tiny example or mental model:

- When reading code, ask: what does `ReadWriteLock` change, allow, reject, or clarify?

### StampedLock

StampedLock is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `StampedLock` in one sentence.
- Recognize `StampedLock` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `StampedLock`.

Tiny example or mental model:

- When reading code, ask: what does `StampedLock` change, allow, reject, or clarify?

### Semaphore

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Semaphore` in one sentence.
- Recognize `Semaphore` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Semaphore`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### CountDownLatch

CountDownLatch is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `CountDownLatch` in one sentence.
- Recognize `CountDownLatch` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `CountDownLatch`.

Tiny example or mental model:

- When reading code, ask: what does `CountDownLatch` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
