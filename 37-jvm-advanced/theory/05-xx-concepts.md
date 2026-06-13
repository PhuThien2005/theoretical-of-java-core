# Advanced JVM - Part 5

## Learning Goal

This file covers a focused slice of **Advanced JVM**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `-XX` |-XX is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Basic profiling` |Basic profiling is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Memory dump` |Memory dump is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Thread dump` | A thread dump is a snapshot of the state and stack trace of all active threads in a JVM. |

## Detailed Notes

### -XX

-XX is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `-XX` in one sentence.
- Recognize `-XX` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `-XX`.

Tiny example or mental model:

- When reading code, ask: what does `XX` change, allow, reject, or clarify?

### Basic profiling

Basic profiling is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Basic profiling` in one sentence.
- Recognize `Basic profiling` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Basic profiling`.

Tiny example or mental model:

- When reading code, ask: what does `Basic profiling` change, allow, reject, or clarify?

### Memory dump

Memory dump is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Memory dump` in one sentence.
- Recognize `Memory dump` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Memory dump`.

Tiny example or mental model:

- When reading code, ask: what does `Memory dump` change, allow, reject, or clarify?

### Thread dump

A thread dump is a snapshot of all active threads inside the JVM, showing the state (RUNNABLE, BLOCKED, WAITING) and full stack trace for each.

It matters because it allows developers to diagnose deadlocks, thread contention, infinite loops, and resource locks in concurrent applications.

Practical check:

- Define `Thread dump` in one sentence.
- Recognize `Thread dump` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Thread dump`.

Tiny example or mental model:

- `new Thread(task).start()` starts work on another thread.

## Code Examples

### CLI Command to capture thread/heap dumps
```bash
# Capture thread dump (PID: 1234)
jstack 1234 > thread_dump.txt

# Capture heap dump (PID: 1234)
jmap -dump:format=b,file=heap_dump.hprof 1234
```

## Common Mistakes

- **Manually parsing heap dumps**: Heap dumps are binary files and can be huge. Do not open them in raw text editors. Always use specialized tools like Eclipse Memory Analyzer (MAT) or VisualVM.
- **Failing to capture thread dumps during deadlocks**: When application threads hang, immediately capture 2-3 thread dumps spaced a few seconds apart to identify which threads are blocked on which monitors.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
