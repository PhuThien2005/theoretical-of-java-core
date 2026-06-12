# Multithreading - Part 2

## Learning Goal

This file covers a focused slice of **Multithreading**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Runnable` |Runnable means a thread is eligible to run, though it may be waiting for CPU scheduling. |
| `Running` |Running is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Blocked` |Blocked means a thread is waiting to acquire a monitor lock. |
| `Waiting` |Waiting means a thread is waiting indefinitely for another thread action. |
| `Timed Waiting` |Timed Waiting means a thread is waiting for a bounded amount of time. |
| `Terminated` |Terminated means the thread has finished execution. |
| `start() vs run()` |start() vs run() is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `sleep` |sleep is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### Runnable

Runnable means a thread is eligible to run, though it may be waiting for CPU scheduling.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Runnable` in one sentence.
- Recognize `Runnable` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Runnable`.

Tiny example or mental model:

- When reading code, ask: what does `Runnable` change, allow, reject, or clarify?

### Running

Running is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Running` in one sentence.
- Recognize `Running` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Running`.

Tiny example or mental model:

- When reading code, ask: what does `Running` change, allow, reject, or clarify?

### Blocked

Blocked means a thread is waiting to acquire a monitor lock.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Blocked` in one sentence.
- Recognize `Blocked` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Blocked`.

Tiny example or mental model:

- When reading code, ask: what does `Blocked` change, allow, reject, or clarify?

### Waiting

Waiting means a thread is waiting indefinitely for another thread action.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Waiting` in one sentence.
- Recognize `Waiting` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Waiting`.

Tiny example or mental model:

- When reading code, ask: what does `Waiting` change, allow, reject, or clarify?

### Timed Waiting

Timed Waiting means a thread is waiting for a bounded amount of time.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Timed Waiting` in one sentence.
- Recognize `Timed Waiting` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Timed Waiting`.

Tiny example or mental model:

- When reading code, ask: what does `Timed Waiting` change, allow, reject, or clarify?

### Terminated

Terminated means the thread has finished execution.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Terminated` in one sentence.
- Recognize `Terminated` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Terminated`.

Tiny example or mental model:

- When reading code, ask: what does `Terminated` change, allow, reject, or clarify?

### start() vs run()

start() vs run() is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `start() vs run()` in one sentence.
- Recognize `start() vs run()` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `start() vs run()`.

Tiny example or mental model:

- When reading code, ask: what does `start() vs run()` change, allow, reject, or clarify?

### sleep

sleep is a specific concept in Multithreading; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `sleep` in one sentence.
- Recognize `sleep` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `sleep`.

Tiny example or mental model:

- When reading code, ask: what does `sleep` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
