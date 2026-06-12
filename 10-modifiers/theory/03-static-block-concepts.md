# Modifiers in Java - Part 3

## Learning Goal

This file covers a focused slice of **Modifiers in Java**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Static block` | Static means the member belongs to the class rather than to one particular object. |
| `Static nested class` | Static means the member belongs to the class rather than to one particular object. |
| `Static import` | Static means the member belongs to the class rather than to one particular object. |
| `Final variable` | Final means the variable, method, class, or parameter is restricted from later change in a specific way. |
| `Final method` | Final means the variable, method, class, or parameter is restricted from later change in a specific way. |
| `Final class` | Final means the variable, method, class, or parameter is restricted from later change in a specific way. |
| `Final parameter` | Final means the variable, method, class, or parameter is restricted from later change in a specific way. |
| `Blank final variable` | Final means the variable, method, class, or parameter is restricted from later change in a specific way. |

## Detailed Notes

### Static block

Static means the member belongs to the class rather than to one particular object.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Static block` in one sentence.
- Recognize `Static block` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Static block`.

Tiny example or mental model:

- `ClassName.member` accesses a class-level member.

### Static nested class

Static means the member belongs to the class rather than to one particular object.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Static nested class` in one sentence.
- Recognize `Static nested class` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Static nested class`.

Tiny example or mental model:

- `ClassName.member` accesses a class-level member.

### Static import

Static means the member belongs to the class rather than to one particular object.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Static import` in one sentence.
- Recognize `Static import` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Static import`.

Tiny example or mental model:

- `ClassName.member` accesses a class-level member.

### Final variable

Final means the variable, method, class, or parameter is restricted from later change in a specific way.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Final variable` in one sentence.
- Recognize `Final variable` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Final variable`.

Tiny example or mental model:

- `final int limit = 10;` cannot be reassigned.

### Final method

Final means the variable, method, class, or parameter is restricted from later change in a specific way.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Final method` in one sentence.
- Recognize `Final method` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Final method`.

Tiny example or mental model:

- `final int limit = 10;` cannot be reassigned.

### Final class

Final means the variable, method, class, or parameter is restricted from later change in a specific way.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Final class` in one sentence.
- Recognize `Final class` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Final class`.

Tiny example or mental model:

- `final int limit = 10;` cannot be reassigned.

### Final parameter

Final means the variable, method, class, or parameter is restricted from later change in a specific way.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Final parameter` in one sentence.
- Recognize `Final parameter` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Final parameter`.

Tiny example or mental model:

- `final int limit = 10;` cannot be reassigned.

### Blank final variable

Final means the variable, method, class, or parameter is restricted from later change in a specific way.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Blank final variable` in one sentence.
- Recognize `Blank final variable` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Blank final variable`.

Tiny example or mental model:

- `final int limit = 10;` cannot be reassigned.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
