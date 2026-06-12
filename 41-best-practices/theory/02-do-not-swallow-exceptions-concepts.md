# Best Practices in Java - Part 2

## Learning Goal

This file covers a focused slice of **Best Practices in Java**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Do not swallow exceptions` | An exception represents an abnormal condition that a program may catch or propagate. |
| `Use interface type when declaring Collection:` | A collection is an object that groups multiple elements under a common API. |
| `List<String> list = new ArrayList<>();` | A List is an ordered collection that can contain duplicates and supports positional access. |
| `Avoid raw type` |Avoid raw type is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Avoid null when possible` |Avoid null when possible is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Write testable code` |Write testable code is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Separate class/method responsibilities` |Separate class/method responsibilities is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Immutability when appropriate` |Immutability when appropriate is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### Do not swallow exceptions

An exception represents an abnormal condition that a program may catch or propagate.

It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Practical check:

- Define `Do not swallow exceptions` in one sentence.
- Recognize `Do not swallow exceptions` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Do not swallow exceptions`.

Tiny example or mental model:

- When reading code, ask: what does `Do not swallow exceptions` change, allow, reject, or clarify?

### Use interface type when declaring Collection:

A collection is an object that groups multiple elements under a common API.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Use interface type when declaring Collection:` in one sentence.
- Recognize `Use interface type when declaring Collection:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Use interface type when declaring Collection:`.

Tiny example or mental model:

- When reading code, ask: what does `Use interface type when declaring Collection:` change, allow, reject, or clarify?

### List<String> list = new ArrayList<>();

A List is an ordered collection that can contain duplicates and supports positional access.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `List<String> list = new ArrayList<>();` in one sentence.
- Recognize `List<String> list = new ArrayList<>();` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `List<String> list = new ArrayList<>();`.

Tiny example or mental model:

- `List<String> names = new ArrayList<>();` stores ordered elements.

### Avoid raw type

Avoid raw type is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Avoid raw type` in one sentence.
- Recognize `Avoid raw type` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Avoid raw type`.

Tiny example or mental model:

- When reading code, ask: what does `Avoid raw type` change, allow, reject, or clarify?

### Avoid null when possible

Avoid null when possible is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Avoid null when possible` in one sentence.
- Recognize `Avoid null when possible` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Avoid null when possible`.

Tiny example or mental model:

- When reading code, ask: what does `Avoid null when possible` change, allow, reject, or clarify?

### Write testable code

Write testable code is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because tests protect behavior during change. A common confusion is testing implementation details instead of observable behavior.

Practical check:

- Define `Write testable code` in one sentence.
- Recognize `Write testable code` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Write testable code`.

Tiny example or mental model:

- When reading code, ask: what does `Write testable code` change, allow, reject, or clarify?

### Separate class/method responsibilities

Separate class/method responsibilities is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Separate class/method responsibilities` in one sentence.
- Recognize `Separate class/method responsibilities` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Separate class/method responsibilities`.

Tiny example or mental model:

- When reading code, ask: what does `Separate class/method responsibilities` change, allow, reject, or clarify?

### Immutability when appropriate

Immutability when appropriate is a specific concept in Best Practices in Java; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Immutability when appropriate` in one sentence.
- Recognize `Immutability when appropriate` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Immutability when appropriate`.

Tiny example or mental model:

- When reading code, ask: what does `Immutability when appropriate` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
