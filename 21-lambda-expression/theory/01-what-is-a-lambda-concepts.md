# Lambda Expression - Part 1

## Learning Goal

This file covers a focused slice of **Lambda Expression**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `What is a lambda?` | A lambda expression is a compact function-like block used where a functional interface is expected. |
| `Lambda syntax` | A lambda expression is a compact function-like block used where a functional interface is expected. |
| `Functional interface` | A functional interface has exactly one abstract method and can be implemented by a lambda. |
| `@FunctionalInterface` |@FunctionalInterface is a specific concept in Lambda Expression; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Method reference:` | Method reference is a group of related rules in Lambda Expression that groups several related details. |
| `static method reference` | Static means the member belongs to the class rather than to one particular object. |
| `instance method reference` |instance method reference is a specific concept in Lambda Expression; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `constructor reference` |constructor reference is a specific concept in Lambda Expression; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Variable capture` |Variable capture is a specific concept in Lambda Expression; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Effectively final` | Final means the variable, method, class, or parameter is restricted from later change in a specific way. |

## Detailed Notes

### What is a lambda?

A lambda expression is a compact function-like block used where a functional interface is expected.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `What is a lambda?` in one sentence.
- Recognize `What is a lambda?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `What is a lambda?`.

Tiny example or mental model:

- `n -> n > 0` is a lambda used as a predicate.

### Lambda syntax

A lambda expression is a compact function-like block used where a functional interface is expected.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `Lambda syntax` in one sentence.
- Recognize `Lambda syntax` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Lambda syntax`.

Tiny example or mental model:

- `n -> n > 0` is a lambda used as a predicate.

### Functional interface

A functional interface has exactly one abstract method and can be implemented by a lambda.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `Functional interface` in one sentence.
- Recognize `Functional interface` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Functional interface`.

Tiny example or mental model:

- When reading code, ask: what does `Functional interface` change, allow, reject, or clarify?

### @FunctionalInterface

@FunctionalInterface is a specific concept in Lambda Expression; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `@FunctionalInterface` in one sentence.
- Recognize `@FunctionalInterface` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `@FunctionalInterface`.

Tiny example or mental model:

- When reading code, ask: what does `@FunctionalInterface` change, allow, reject, or clarify?

### Method reference:

Method reference is a group of related rules in Lambda Expression that groups several related details.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Method reference:` in one sentence.
- Recognize `Method reference:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Method reference:`.

Tiny example or mental model:

- When reading code, ask: what does `Method reference:` change, allow, reject, or clarify?

### static method reference

Static means the member belongs to the class rather than to one particular object.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `static method reference` in one sentence.
- Recognize `static method reference` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `static method reference`.

Tiny example or mental model:

- `ClassName.member` accesses a class-level member.

### instance method reference

instance method reference is a specific concept in Lambda Expression; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `instance method reference` in one sentence.
- Recognize `instance method reference` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `instance method reference`.

Tiny example or mental model:

- When reading code, ask: what does `instance method reference` change, allow, reject, or clarify?

### constructor reference

constructor reference is a specific concept in Lambda Expression; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `constructor reference` in one sentence.
- Recognize `constructor reference` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `constructor reference`.

Tiny example or mental model:

- When reading code, ask: what does `constructor reference` change, allow, reject, or clarify?

### Variable capture

Variable capture is a specific concept in Lambda Expression; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Variable capture` in one sentence.
- Recognize `Variable capture` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Variable capture`.

Tiny example or mental model:

- When reading code, ask: what does `Variable capture` change, allow, reject, or clarify?

### Effectively final

Final means the variable, method, class, or parameter is restricted from later change in a specific way.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Effectively final` in one sentence.
- Recognize `Effectively final` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Effectively final`.

Tiny example or mental model:

- `final int limit = 10;` cannot be reassigned.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
