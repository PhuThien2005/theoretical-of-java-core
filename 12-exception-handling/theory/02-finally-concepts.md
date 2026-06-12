# Exception Handling - Part 2

## Learning Goal

This file covers a focused slice of **Exception Handling**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `finally` | Final means the variable, method, class, or parameter is restricted from later change in a specific way. |
| `throw` |throw is a specific concept in Exception Handling; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `throws` |throws is a specific concept in Exception Handling; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `try-with-resources` | Try-with-resources automatically closes resources that implement AutoCloseable. |
| `Custom exception` | An exception represents an abnormal condition that a program may catch or propagate. |
| `Exception propagation` | An exception represents an abnormal condition that a program may catch or propagate. |
| `Common exceptions:` | An exception represents an abnormal condition that a program may catch or propagate. |
| `NullPointerException` | An exception represents an abnormal condition that a program may catch or propagate. |

## Detailed Notes

### finally

Final means the variable, method, class, or parameter is restricted from later change in a specific way.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `finally` in one sentence.
- Recognize `finally` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `finally`.

Tiny example or mental model:

- `final int limit = 10;` cannot be reassigned.

### throw

throw is a specific concept in Exception Handling; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `throw` in one sentence.
- Recognize `throw` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `throw`.

Tiny example or mental model:

- When reading code, ask: what does `throw` change, allow, reject, or clarify?

### throws

throws is a specific concept in Exception Handling; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `throws` in one sentence.
- Recognize `throws` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `throws`.

Tiny example or mental model:

- When reading code, ask: what does `throws` change, allow, reject, or clarify?

### try-with-resources

Try-with-resources automatically closes resources that implement AutoCloseable.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `try-with-resources` in one sentence.
- Recognize `try-with-resources` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `try-with-resources`.

Tiny example or mental model:

- `try { ... } catch (IOException ex) { ... }` handles a specific failure path.

### Custom exception

An exception represents an abnormal condition that a program may catch or propagate.

It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Practical check:

- Define `Custom exception` in one sentence.
- Recognize `Custom exception` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Custom exception`.

Tiny example or mental model:

- When reading code, ask: what does `Custom exception` change, allow, reject, or clarify?

### Exception propagation

An exception represents an abnormal condition that a program may catch or propagate.

It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Practical check:

- Define `Exception propagation` in one sentence.
- Recognize `Exception propagation` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Exception propagation`.

Tiny example or mental model:

- When reading code, ask: what does `Exception propagation` change, allow, reject, or clarify?

### Common exceptions:

An exception represents an abnormal condition that a program may catch or propagate.

It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Practical check:

- Define `Common exceptions:` in one sentence.
- Recognize `Common exceptions:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Common exceptions:`.

Tiny example or mental model:

- When reading code, ask: what does `Common exceptions:` change, allow, reject, or clarify?

### NullPointerException

An exception represents an abnormal condition that a program may catch or propagate.

It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Practical check:

- Define `NullPointerException` in one sentence.
- Recognize `NullPointerException` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `NullPointerException`.

Tiny example or mental model:

- When reading code, ask: what does `NullPointerException` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
