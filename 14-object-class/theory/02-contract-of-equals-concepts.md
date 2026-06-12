# Object class - Part 2

## Learning Goal

This file covers a focused slice of **Object class**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Contract of equals()` | equals() defines logical equality between objects. |
| `Contract of hashCode()` | hashCode() returns an integer hash used by hash-based collections. |
| `Comparing objects by reference and by value` |Comparing objects by reference and by value is a specific concept in Object class; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### Contract of equals()

equals() defines logical equality between objects.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Contract of equals()` in one sentence.
- Recognize `Contract of equals()` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Contract of equals()`.

Tiny example or mental model:

- When reading code, ask: what does `Contract of equals()` change, allow, reject, or clarify?

### Contract of hashCode()

hashCode() returns an integer hash used by hash-based collections.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Contract of hashCode()` in one sentence.
- Recognize `Contract of hashCode()` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Contract of hashCode()`.

Tiny example or mental model:

- When reading code, ask: what does `Contract of hashCode()` change, allow, reject, or clarify?

### Comparing objects by reference and by value

Comparing objects by reference and by value is a specific concept in Object class; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Comparing objects by reference and by value` in one sentence.
- Recognize `Comparing objects by reference and by value` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Comparing objects by reference and by value`.

Tiny example or mental model:

- When reading code, ask: what does `Comparing objects by reference and by value` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
