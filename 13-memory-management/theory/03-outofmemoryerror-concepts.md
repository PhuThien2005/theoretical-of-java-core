# Java Memory Management - Part 3

## Learning Goal

This file covers a focused slice of **Java Memory Management**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `OutOfMemoryError` |OutOfMemoryError is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `StackOverflowError` | Stack stores method frames, local variables, and call flow for each thread. |

## Detailed Notes

### OutOfMemoryError

OutOfMemoryError is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `OutOfMemoryError` in one sentence.
- Recognize `OutOfMemoryError` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `OutOfMemoryError`.

Tiny example or mental model:

- When reading code, ask: what does `OutOfMemoryError` change, allow, reject, or clarify?

### StackOverflowError

Stack stores method frames, local variables, and call flow for each thread.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `StackOverflowError` in one sentence.
- Recognize `StackOverflowError` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `StackOverflowError`.

Tiny example or mental model:

- When reading code, ask: what does `StackOverflowError` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
