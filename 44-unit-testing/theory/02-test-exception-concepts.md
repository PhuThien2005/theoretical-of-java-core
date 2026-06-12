# Basic Unit Testing - Part 2

## Learning Goal

This file covers a focused slice of **Basic Unit Testing**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Test exception` | An exception represents an abnormal condition that a program may catch or propagate. |
| `Test private logic indirectly` |Test private logic indirectly is a specific concept in Basic Unit Testing; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Basic code coverage` |Basic code coverage is a specific concept in Basic Unit Testing; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### Test exception

An exception represents an abnormal condition that a program may catch or propagate.

It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Practical check:

- Define `Test exception` in one sentence.
- Recognize `Test exception` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Test exception`.

Tiny example or mental model:

- When reading code, ask: what does `Test exception` change, allow, reject, or clarify?

### Test private logic indirectly

Test private logic indirectly is a specific concept in Basic Unit Testing; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because tests protect behavior during change. A common confusion is testing implementation details instead of observable behavior.

Practical check:

- Define `Test private logic indirectly` in one sentence.
- Recognize `Test private logic indirectly` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Test private logic indirectly`.

Tiny example or mental model:

- When reading code, ask: what does `Test private logic indirectly` change, allow, reject, or clarify?

### Basic code coverage

Basic code coverage is a specific concept in Basic Unit Testing; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Basic code coverage` in one sentence.
- Recognize `Basic code coverage` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Basic code coverage`.

Tiny example or mental model:

- When reading code, ask: what does `Basic code coverage` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
