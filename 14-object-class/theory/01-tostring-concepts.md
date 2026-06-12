# Object class - Part 1

## Learning Goal

This file covers a focused slice of **Object class**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `toString()` |toString() returns a human-readable text representation of an object. |
| `equals()` | equals() defines logical equality between objects. |
| `hashCode()` | hashCode() returns an integer hash used by hash-based collections. |
| `getClass()` |getClass() returns the runtime Class object for an instance. |
| `clone()` |clone() creates a field-by-field copy when cloning is supported, but it is often avoided in modern Java design. |
| `finalize() deprecated` | Final means the variable, method, class, or parameter is restricted from later change in a specific way. |
| `wait()` |wait() releases an object monitor and pauses the current thread until notification or timeout. |
| `notify()` |notify() wakes one thread waiting on the same object monitor. |
| `notifyAll()` |notifyAll() wakes all threads waiting on the same object monitor. |
| `Why overriding equals() means you should also override hashCode()` | equals() defines logical equality between objects. |

## Detailed Notes

### toString()

toString() returns a human-readable text representation of an object.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `toString()` in one sentence.
- Recognize `toString()` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `toString()`.

Tiny example or mental model:

- When reading code, ask: what does `toString()` change, allow, reject, or clarify?

### equals()

equals() defines logical equality between objects.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `equals()` in one sentence.
- Recognize `equals()` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `equals()`.

Tiny example or mental model:

- When reading code, ask: what does `equals()` change, allow, reject, or clarify?

### hashCode()

hashCode() returns an integer hash used by hash-based collections.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `hashCode()` in one sentence.
- Recognize `hashCode()` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `hashCode()`.

Tiny example or mental model:

- When reading code, ask: what does `hashCode()` change, allow, reject, or clarify?

### getClass()

getClass() returns the runtime Class object for an instance.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `getClass()` in one sentence.
- Recognize `getClass()` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `getClass()`.

Tiny example or mental model:

- When reading code, ask: what does `getClass()` change, allow, reject, or clarify?

### clone()

clone() creates a field-by-field copy when cloning is supported, but it is often avoided in modern Java design.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `clone()` in one sentence.
- Recognize `clone()` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `clone()`.

Tiny example or mental model:

- When reading code, ask: what does `clone()` change, allow, reject, or clarify?

### finalize() deprecated

Final means the variable, method, class, or parameter is restricted from later change in a specific way.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `finalize() deprecated` in one sentence.
- Recognize `finalize() deprecated` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `finalize() deprecated`.

Tiny example or mental model:

- `final int limit = 10;` cannot be reassigned.

### wait()

wait() releases an object monitor and pauses the current thread until notification or timeout.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `wait()` in one sentence.
- Recognize `wait()` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `wait()`.

Tiny example or mental model:

- When reading code, ask: what does `wait()` change, allow, reject, or clarify?

### notify()

notify() wakes one thread waiting on the same object monitor.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `notify()` in one sentence.
- Recognize `notify()` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `notify()`.

Tiny example or mental model:

- When reading code, ask: what does `notify()` change, allow, reject, or clarify?

### notifyAll()

notifyAll() wakes all threads waiting on the same object monitor.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `notifyAll()` in one sentence.
- Recognize `notifyAll()` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `notifyAll()`.

Tiny example or mental model:

- When reading code, ask: what does `notifyAll()` change, allow, reject, or clarify?

### Why overriding equals() means you should also override hashCode()

equals() defines logical equality between objects.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Why overriding equals() means you should also override hashCode()` in one sentence.
- Recognize `Why overriding equals() means you should also override hashCode()` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Why overriding equals() means you should also override hashCode()`.

Tiny example or mental model:

- When reading code, ask: what does `Why overriding equals() means you should also override hashCode()` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
