# Modifiers in Java - Part 1

## Learning Goal

This file covers a focused slice of **Modifiers in Java**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Access modifier:` | Access modifier is a group of related rules in Modifiers in Java that groups several related details. |
| `public` |public allows access from any package when the class or member is otherwise visible. |
| `protected` |protected allows access from the same package and from subclasses, with subclass access rules across packages. |
| `default` |Default access, also called package-private, allows access only inside the same package. |
| `private` |private restricts access to the declaring class only. |
| `Non-access modifier:` | Non-access modifier is a group of related rules in Modifiers in Java that groups several related details. |
| `static` | Static means the member belongs to the class rather than to one particular object. |
| `final` | Final means the variable, method, class, or parameter is restricted from later change in a specific way. |

## Detailed Notes

### Access modifier:

Access modifier is a group of related rules in Modifiers in Java that groups several related details.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Access modifier:` in one sentence.
- Recognize `Access modifier:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Access modifier:`.

Tiny example or mental model:

- When reading code, ask: what does `Access modifier:` change, allow, reject, or clarify?

### public

public allows access from any package when the class or member is otherwise visible.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `public` in one sentence.
- Recognize `public` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `public`.

Tiny example or mental model:

- When reading code, ask: what does `public` change, allow, reject, or clarify?

### protected

protected allows access from the same package and from subclasses, with subclass access rules across packages.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `protected` in one sentence.
- Recognize `protected` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `protected`.

Tiny example or mental model:

- When reading code, ask: what does `protected` change, allow, reject, or clarify?

### default

Default access, also called package-private, allows access only inside the same package.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `default` in one sentence.
- Recognize `default` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `default`.

Tiny example or mental model:

- When reading code, ask: what does `default` change, allow, reject, or clarify?

### private

private restricts access to the declaring class only.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `private` in one sentence.
- Recognize `private` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `private`.

Tiny example or mental model:

- When reading code, ask: what does `private` change, allow, reject, or clarify?

### Non-access modifier:

Non-access modifier is a group of related rules in Modifiers in Java that groups several related details.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Non-access modifier:` in one sentence.
- Recognize `Non-access modifier:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Non-access modifier:`.

Tiny example or mental model:

- When reading code, ask: what does `Non-access modifier:` change, allow, reject, or clarify?

### static

Static means the member belongs to the class rather than to one particular object.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `static` in one sentence.
- Recognize `static` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `static`.

Tiny example or mental model:

- `ClassName.member` accesses a class-level member.

### final

Final means the variable, method, class, or parameter is restricted from later change in a specific way.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `final` in one sentence.
- Recognize `final` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `final`.

Tiny example or mental model:

- `final int limit = 10;` cannot be reassigned.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
