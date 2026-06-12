# Common Java Core Interview Questions - Part 2

## Learning Goal

This file covers a focused slice of **Common Java Core Interview Questions**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `How does HashSet remove duplicates?` | A Set is a collection that rejects duplicates according to equality rules. |
| `How are final, finally, and finalize different?` | Final means the variable, method, class, or parameter is restricted from later change in a specific way. |
| `How are checked and unchecked exceptions different?` | An exception represents an abnormal condition that a program may catch or propagate. |
| `How are abstract class and interface different?` | Abstract means incomplete by design: subclasses or implementations must provide missing behavior. |
| `How are overload and override different?` |How are overload and override different? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Can static methods be overridden?` | Static means the member belongs to the class rather than to one particular object. |
| `Are constructors inherited?` |Are constructors inherited? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `How are this and super different?` |How are this and super different? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### How does HashSet remove duplicates?

A Set is a collection that rejects duplicates according to equality rules.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `How does HashSet remove duplicates?` in one sentence.
- Recognize `How does HashSet remove duplicates?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How does HashSet remove duplicates?`.

Tiny example or mental model:

- When reading code, ask: what does `How does HashSet remove duplicates?` change, allow, reject, or clarify?

### How are final, finally, and finalize different?

Final means the variable, method, class, or parameter is restricted from later change in a specific way.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `How are final, finally, and finalize different?` in one sentence.
- Recognize `How are final, finally, and finalize different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are final, finally, and finalize different?`.

Tiny example or mental model:

- `final int limit = 10;` cannot be reassigned.

### How are checked and unchecked exceptions different?

An exception represents an abnormal condition that a program may catch or propagate.

It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Practical check:

- Define `How are checked and unchecked exceptions different?` in one sentence.
- Recognize `How are checked and unchecked exceptions different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are checked and unchecked exceptions different?`.

Tiny example or mental model:

- When reading code, ask: what does `How are checked and unchecked exceptions different?` change, allow, reject, or clarify?

### How are abstract class and interface different?

Abstract means incomplete by design: subclasses or implementations must provide missing behavior.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `How are abstract class and interface different?` in one sentence.
- Recognize `How are abstract class and interface different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are abstract class and interface different?`.

Tiny example or mental model:

- When reading code, ask: what does `How are abstract class and interface different?` change, allow, reject, or clarify?

### How are overload and override different?

How are overload and override different? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `How are overload and override different?` in one sentence.
- Recognize `How are overload and override different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are overload and override different?`.

Tiny example or mental model:

- When reading code, ask: what does `How are overload and override different?` change, allow, reject, or clarify?

### Can static methods be overridden?

Static means the member belongs to the class rather than to one particular object.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Can static methods be overridden?` in one sentence.
- Recognize `Can static methods be overridden?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Can static methods be overridden?`.

Tiny example or mental model:

- `ClassName.member` accesses a class-level member.

### Are constructors inherited?

Are constructors inherited? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Are constructors inherited?` in one sentence.
- Recognize `Are constructors inherited?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Are constructors inherited?`.

Tiny example or mental model:

- When reading code, ask: what does `Are constructors inherited?` change, allow, reject, or clarify?

### How are this and super different?

How are this and super different? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `How are this and super different?` in one sentence.
- Recognize `How are this and super different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are this and super different?`.

Tiny example or mental model:

- When reading code, ask: what does `How are this and super different?` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
