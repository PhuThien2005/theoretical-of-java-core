# Synchronization and Concurrency - Part 1

## Learning Goal

This file covers a focused slice of **Synchronization and Concurrency**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `synchronized method` | Synchronized protects a critical section by using a monitor lock. |
| `synchronized block` | Synchronized protects a critical section by using a monitor lock. |
| `Object lock` |Object lock is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Class lock` |Class lock is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Monitor` |Monitor is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `wait` |wait is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `notify` |notify is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `notifyAll` |notifyAll is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### synchronized method

Synchronized protects a critical section by using a monitor lock.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `synchronized method` in one sentence.
- Recognize `synchronized method` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `synchronized method`.

Tiny example or mental model:

- When reading code, ask: what does `synchronized method` change, allow, reject, or clarify?

### synchronized block

Synchronized protects a critical section by using a monitor lock.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `synchronized block` in one sentence.
- Recognize `synchronized block` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `synchronized block`.

Tiny example or mental model:

- When reading code, ask: what does `synchronized block` change, allow, reject, or clarify?

### Object lock

Object lock is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Object lock` in one sentence.
- Recognize `Object lock` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Object lock`.

Tiny example or mental model:

- When reading code, ask: what does `Object lock` change, allow, reject, or clarify?

### Class lock

Class lock is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Class lock` in one sentence.
- Recognize `Class lock` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Class lock`.

Tiny example or mental model:

- When reading code, ask: what does `Class lock` change, allow, reject, or clarify?

### Monitor

Monitor is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Monitor` in one sentence.
- Recognize `Monitor` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Monitor`.

Tiny example or mental model:

- When reading code, ask: what does `Monitor` change, allow, reject, or clarify?

### wait

wait is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `wait` in one sentence.
- Recognize `wait` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `wait`.

Tiny example or mental model:

- When reading code, ask: what does `wait` change, allow, reject, or clarify?

### notify

notify is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `notify` in one sentence.
- Recognize `notify` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `notify`.

Tiny example or mental model:

- When reading code, ask: what does `notify` change, allow, reject, or clarify?

### notifyAll

notifyAll is a specific concept in Synchronization and Concurrency; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `notifyAll` in one sentence.
- Recognize `notifyAll` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `notifyAll`.

Tiny example or mental model:

- When reading code, ask: what does `notifyAll` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
