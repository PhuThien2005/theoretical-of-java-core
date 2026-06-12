# Exception Handling - Part 1

## Learning Goal

This file covers a focused slice of **Exception Handling**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `What is an exception?` | An exception represents an abnormal condition that a program may catch or propagate. |
| `Error vs Exception` | An exception represents an abnormal condition that a program may catch or propagate. |
| `Checked exception` | A checked exception must be handled or declared by compiler rules. |
| `Unchecked exception` | An unchecked exception is not required to be caught or declared. |
| `Runtime exception` | An exception represents an abnormal condition that a program may catch or propagate. |
| `try` |try marks the block whose exceptions you want to handle, clean up after, or propagate. |
| `catch` |catch handles a matching exception type thrown from the try block. |
| `multiple catch` |multiple catch lets different exception types be handled by different handlers, ordered from specific to broad. |

## Detailed Notes

### What is an exception?

An exception represents an abnormal condition that a program may catch or propagate.

It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Practical check:

- Define `What is an exception?` in one sentence.
- Recognize `What is an exception?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `What is an exception?`.

Tiny example or mental model:

- When reading code, ask: what does `What is an exception?` change, allow, reject, or clarify?

### Error vs Exception

An exception represents an abnormal condition that a program may catch or propagate.

It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Practical check:

- Define `Error vs Exception` in one sentence.
- Recognize `Error vs Exception` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Error vs Exception`.

Tiny example or mental model:

- When reading code, ask: what does `Error vs Exception` change, allow, reject, or clarify?

### Checked exception

A checked exception must be handled or declared by compiler rules.

It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Practical check:

- Define `Checked exception` in one sentence.
- Recognize `Checked exception` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Checked exception`.

Tiny example or mental model:

- When reading code, ask: what does `Checked exception` change, allow, reject, or clarify?

### Unchecked exception

An unchecked exception is not required to be caught or declared.

It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Practical check:

- Define `Unchecked exception` in one sentence.
- Recognize `Unchecked exception` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Unchecked exception`.

Tiny example or mental model:

- When reading code, ask: what does `Unchecked exception` change, allow, reject, or clarify?

### Runtime exception

An exception represents an abnormal condition that a program may catch or propagate.

It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Practical check:

- Define `Runtime exception` in one sentence.
- Recognize `Runtime exception` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Runtime exception`.

Tiny example or mental model:

- When reading code, ask: what does `Runtime exception` change, allow, reject, or clarify?

### try

try marks the block whose exceptions you want to handle, clean up after, or propagate.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `try` in one sentence.
- Recognize `try` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `try`.

Tiny example or mental model:

- `try { ... } catch (IOException ex) { ... }` handles a specific failure path.

### catch

catch handles a matching exception type thrown from the try block.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `catch` in one sentence.
- Recognize `catch` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `catch`.

Tiny example or mental model:

- `try { ... } catch (IOException ex) { ... }` handles a specific failure path.

### multiple catch

multiple catch lets different exception types be handled by different handlers, ordered from specific to broad.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `multiple catch` in one sentence.
- Recognize `multiple catch` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `multiple catch`.

Tiny example or mental model:

- `try { ... } catch (IOException ex) { ... }` handles a specific failure path.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
