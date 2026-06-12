# Modern Java Concepts To Know - Part 1

## Learning Goal

This file covers a focused slice of **Modern Java Concepts To Know**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `var` |var is a specific concept in Modern Java Concepts To Know; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Records` | A record is a compact Java class for immutable data carriers. |
| `Sealed class` | A sealed class restricts which classes may extend or implement it. |
| `Pattern matching for instanceof` |Pattern matching for instanceof is a specific concept in Modern Java Concepts To Know; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Switch expression` |Switch expression is a specific concept in Modern Java Concepts To Know; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Text blocks` |Text blocks is a specific concept in Modern Java Concepts To Know; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Enhanced NullPointerException message` | An exception represents an abnormal condition that a program may catch or propagate. |
| `Virtual Threads` | Virtual threads are lightweight threads designed for high-throughput blocking-style concurrency. |
| `Basic Structured Concurrency` |Basic Structured Concurrency is a specific concept in Modern Java Concepts To Know; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Pattern matching for switch` |Pattern matching for switch is a specific concept in Modern Java Concepts To Know; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### var

var is a specific concept in Modern Java Concepts To Know; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `var` in one sentence.
- Recognize `var` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `var`.

Tiny example or mental model:

- When reading code, ask: what does `var` change, allow, reject, or clarify?

### Records

A record is a compact Java class for immutable data carriers.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Records` in one sentence.
- Recognize `Records` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Records`.

Tiny example or mental model:

- When reading code, ask: what does `Records` change, allow, reject, or clarify?

### Sealed class

A sealed class restricts which classes may extend or implement it.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Sealed class` in one sentence.
- Recognize `Sealed class` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Sealed class`.

Tiny example or mental model:

- When reading code, ask: what does `Sealed class` change, allow, reject, or clarify?

### Pattern matching for instanceof

Pattern matching for instanceof is a specific concept in Modern Java Concepts To Know; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Pattern matching for instanceof` in one sentence.
- Recognize `Pattern matching for instanceof` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Pattern matching for instanceof`.

Tiny example or mental model:

- When reading code, ask: what does `Pattern matching for instanceof` change, allow, reject, or clarify?

### Switch expression

Switch expression is a specific concept in Modern Java Concepts To Know; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Switch expression` in one sentence.
- Recognize `Switch expression` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Switch expression`.

Tiny example or mental model:

- When reading code, ask: what does `Switch expression` change, allow, reject, or clarify?

### Text blocks

Text blocks is a specific concept in Modern Java Concepts To Know; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Text blocks` in one sentence.
- Recognize `Text blocks` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Text blocks`.

Tiny example or mental model:

- When reading code, ask: what does `Text blocks` change, allow, reject, or clarify?

### Enhanced NullPointerException message

An exception represents an abnormal condition that a program may catch or propagate.

It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Practical check:

- Define `Enhanced NullPointerException message` in one sentence.
- Recognize `Enhanced NullPointerException message` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Enhanced NullPointerException message`.

Tiny example or mental model:

- When reading code, ask: what does `Enhanced NullPointerException message` change, allow, reject, or clarify?

### Virtual Threads

Virtual threads are lightweight threads designed for high-throughput blocking-style concurrency.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Virtual Threads` in one sentence.
- Recognize `Virtual Threads` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Virtual Threads`.

Tiny example or mental model:

- `new Thread(task).start()` starts work on another thread.

### Basic Structured Concurrency

Basic Structured Concurrency is a specific concept in Modern Java Concepts To Know; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Basic Structured Concurrency` in one sentence.
- Recognize `Basic Structured Concurrency` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Basic Structured Concurrency`.

Tiny example or mental model:

- When reading code, ask: what does `Basic Structured Concurrency` change, allow, reject, or clarify?

### Pattern matching for switch

Pattern matching for switch is a specific concept in Modern Java Concepts To Know; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Pattern matching for switch` in one sentence.
- Recognize `Pattern matching for switch` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Pattern matching for switch`.

Tiny example or mental model:

- When reading code, ask: what does `Pattern matching for switch` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
