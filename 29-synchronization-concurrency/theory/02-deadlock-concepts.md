# Synchronization and Concurrency - Part 2

## Learning Goal

This file covers a focused slice of **Synchronization and Concurrency**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Deadlock` | Deadlock happens when threads wait forever for locks held by each other. |
| `Livelock` |Livelock is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Starvation` |Starvation is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Volatile` | Volatile gives visibility guarantees for a variable shared between threads, but it does not make compound operations atomic. |
| `Atomic classes:` | Atomic classes is a group of related rules in Synchronization and Concurrency that groups several related details. |
| `AtomicInteger` |AtomicInteger is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `AtomicLong` |AtomicLong is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `AtomicBoolean` |AtomicBoolean is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### Deadlock

Deadlock happens when threads wait forever for locks held by each other.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Deadlock` in one sentence.
- Recognize `Deadlock` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Deadlock`.

Tiny example or mental model:

- When reading code, ask: what does `Deadlock` change, allow, reject, or clarify?

### Livelock

Livelock is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Livelock` in one sentence.
- Recognize `Livelock` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Livelock`.

Tiny example or mental model:

- When reading code, ask: what does `Livelock` change, allow, reject, or clarify?

### Starvation

Starvation is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Starvation` in one sentence.
- Recognize `Starvation` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Starvation`.

Tiny example or mental model:

- When reading code, ask: what does `Starvation` change, allow, reject, or clarify?

### Volatile

Volatile gives visibility guarantees for a variable shared between threads, but it does not make compound operations atomic.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Volatile` in one sentence.
- Recognize `Volatile` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Volatile`.

Tiny example or mental model:

- When reading code, ask: what does `Volatile` change, allow, reject, or clarify?

### Atomic classes:

Atomic classes is a group of related rules in Synchronization and Concurrency that groups several related details.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Atomic classes:` in one sentence.
- Recognize `Atomic classes:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Atomic classes:`.

Tiny example or mental model:

- When reading code, ask: what does `Atomic classes:` change, allow, reject, or clarify?

### AtomicInteger

AtomicInteger is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `AtomicInteger` in one sentence.
- Recognize `AtomicInteger` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `AtomicInteger`.

Tiny example or mental model:

- When reading code, ask: what does `AtomicInteger` change, allow, reject, or clarify?

### AtomicLong

AtomicLong is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `AtomicLong` in one sentence.
- Recognize `AtomicLong` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `AtomicLong`.

Tiny example or mental model:

- When reading code, ask: what does `AtomicLong` change, allow, reject, or clarify?

### AtomicBoolean

AtomicBoolean is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `AtomicBoolean` in one sentence.
- Recognize `AtomicBoolean` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `AtomicBoolean`.

Tiny example or mental model:

- When reading code, ask: what does `AtomicBoolean` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
