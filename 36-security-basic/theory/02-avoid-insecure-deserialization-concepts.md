# Basic Security - Part 2

## Learning Goal

This file covers a focused slice of **Basic Security**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Avoid insecure deserialization` | Serialization converts an object graph into bytes so it can be stored or transferred. |

## Detailed Notes

### Avoid insecure deserialization

Serialization converts an object graph into bytes so it can be stored or transferred.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Avoid insecure deserialization` in one sentence.
- Recognize `Avoid insecure deserialization` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Avoid insecure deserialization`.

Tiny example or mental model:

- When reading code, ask: what does `Avoid insecure deserialization` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
