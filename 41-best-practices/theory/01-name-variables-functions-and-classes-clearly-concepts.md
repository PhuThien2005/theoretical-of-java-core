# Best Practices in Java - Part 1

## Learning Goal

This file covers a focused slice of **Best Practices in Java**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Name variables, functions, and classes clearly` |Name variables, functions, and classes clearly is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Code according to convention` |Code according to convention is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Do not overuse static` | Static means the member belongs to the class rather than to one particular object. |
| `Do not overuse inheritance` |Do not overuse inheritance is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Prefer composition over inheritance` |Prefer composition over inheritance is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Override equals/hashCode correctly` | equals() defines logical equality between objects. |
| `Use StringBuilder when concatenating strings many times` |Use StringBuilder when concatenating strings many times is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Use BigDecimal for money` | BigDecimal represents decimal numbers precisely and is commonly used for money. |
| `Use try-with-resources` | Try-with-resources automatically closes resources that implement AutoCloseable. |
| `Do not catch overly broad Exception if unnecessary` | An exception represents an abnormal condition that a program may catch or propagate. |

## Detailed Notes

### Name variables, functions, and classes clearly

Name variables, functions, and classes clearly is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `Name variables, functions, and classes clearly` in one sentence.
- Recognize `Name variables, functions, and classes clearly` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Name variables, functions, and classes clearly`.

Tiny example or mental model:

- When reading code, ask: what does `Name variables, functions, and classes clearly` change, allow, reject, or clarify?

### Code according to convention

Code according to convention is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Code according to convention` in one sentence.
- Recognize `Code according to convention` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Code according to convention`.

Tiny example or mental model:

- When reading code, ask: what does `Code according to convention` change, allow, reject, or clarify?

### Do not overuse static

Static means the member belongs to the class rather than to one particular object.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Do not overuse static` in one sentence.
- Recognize `Do not overuse static` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Do not overuse static`.

Tiny example or mental model:

- `ClassName.member` accesses a class-level member.

### Do not overuse inheritance

Do not overuse inheritance is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Do not overuse inheritance` in one sentence.
- Recognize `Do not overuse inheritance` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Do not overuse inheritance`.

Tiny example or mental model:

- When reading code, ask: what does `Do not overuse inheritance` change, allow, reject, or clarify?

### Prefer composition over inheritance

Prefer composition over inheritance is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Prefer composition over inheritance` in one sentence.
- Recognize `Prefer composition over inheritance` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Prefer composition over inheritance`.

Tiny example or mental model:

- When reading code, ask: what does `Prefer composition over inheritance` change, allow, reject, or clarify?

### Override equals/hashCode correctly

equals() defines logical equality between objects.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Override equals/hashCode correctly` in one sentence.
- Recognize `Override equals/hashCode correctly` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Override equals/hashCode correctly`.

Tiny example or mental model:

- When reading code, ask: what does `Override equals/hashCode correctly` change, allow, reject, or clarify?

### Use StringBuilder when concatenating strings many times

Use StringBuilder when concatenating strings many times is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Use StringBuilder when concatenating strings many times` in one sentence.
- Recognize `Use StringBuilder when concatenating strings many times` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Use StringBuilder when concatenating strings many times`.

Tiny example or mental model:

- When reading code, ask: what does `Use StringBuilder when concatenating strings many times` change, allow, reject, or clarify?

### Use BigDecimal for money

BigDecimal represents decimal numbers precisely and is commonly used for money.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Use BigDecimal for money` in one sentence.
- Recognize `Use BigDecimal for money` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Use BigDecimal for money`.

Tiny example or mental model:

- When reading code, ask: what does `Use BigDecimal for money` change, allow, reject, or clarify?

### Use try-with-resources

Try-with-resources automatically closes resources that implement AutoCloseable.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Use try-with-resources` in one sentence.
- Recognize `Use try-with-resources` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Use try-with-resources`.

Tiny example or mental model:

- `try { ... } catch (IOException ex) { ... }` handles a specific failure path.

### Do not catch overly broad Exception if unnecessary

An exception represents an abnormal condition that a program may catch or propagate.

It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Practical check:

- Define `Do not catch overly broad Exception if unnecessary` in one sentence.
- Recognize `Do not catch overly broad Exception if unnecessary` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Do not catch overly broad Exception if unnecessary`.

Tiny example or mental model:

- `try { ... } catch (IOException ex) { ... }` handles a specific failure path.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
