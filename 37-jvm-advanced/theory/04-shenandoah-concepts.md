# Advanced JVM - Part 4

## Learning Goal

This file covers a focused slice of **Advanced JVM**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Shenandoah` |Shenandoah is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Stop-the-world` |Stop-the-world is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Minor GC` |Minor GC is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Major GC` |Major GC is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Full GC` |Full GC is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Basic JVM tuning:` | The JVM executes bytecode and manages runtime services such as memory, JIT, and GC. |
| `-Xms` |-Xms is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `-Xmx` |-Xmx is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### Shenandoah

Shenandoah is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Shenandoah` in one sentence.
- Recognize `Shenandoah` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Shenandoah`.

Tiny example or mental model:

- When reading code, ask: what does `Shenandoah` change, allow, reject, or clarify?

### Stop-the-world

Stop-the-world is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Stop-the-world` in one sentence.
- Recognize `Stop-the-world` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Stop-the-world`.

Tiny example or mental model:

- When reading code, ask: what does `Stop-the-world` change, allow, reject, or clarify?

### Minor GC

Minor GC is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Minor GC` in one sentence.
- Recognize `Minor GC` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Minor GC`.

Tiny example or mental model:

- When reading code, ask: what does `Minor GC` change, allow, reject, or clarify?

### Major GC

Major GC is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Major GC` in one sentence.
- Recognize `Major GC` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Major GC`.

Tiny example or mental model:

- When reading code, ask: what does `Major GC` change, allow, reject, or clarify?

### Full GC

Full GC is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Full GC` in one sentence.
- Recognize `Full GC` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Full GC`.

Tiny example or mental model:

- When reading code, ask: what does `Full GC` change, allow, reject, or clarify?

### Basic JVM tuning:

The JVM executes bytecode and manages runtime services such as memory, JIT, and GC.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Basic JVM tuning:` in one sentence.
- Recognize `Basic JVM tuning:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Basic JVM tuning:`.

Tiny example or mental model:

- When reading code, ask: what does `Basic JVM tuning:` change, allow, reject, or clarify?

### -Xms

-Xms is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `-Xms` in one sentence.
- Recognize `-Xms` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `-Xms`.

Tiny example or mental model:

- When reading code, ask: what does `Xms` change, allow, reject, or clarify?

### -Xmx

-Xmx is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `-Xmx` in one sentence.
- Recognize `-Xmx` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `-Xmx`.

Tiny example or mental model:

- When reading code, ask: what does `Xmx` change, allow, reject, or clarify?

## Code Examples

### JVM Tuning Flags Example
```bash
# Set initial heap to 1GB, max heap to 2GB, and target a 50ms GC pause time
java -Xms1g -Xmx2g -XX:MaxGCPauseMillis=50 -jar app.jar
```

## Common Mistakes

- **Mismatched -Xms and -Xmx**: If `-Xms` is smaller than `-Xmx`, the JVM will dynamically resize the heap. This resizing causes GC pauses and performance overhead. Setting them equal is best practice for production.
- **Setting MaxGCPauseMillis too low**: Setting it to an unrealistic target (e.g. 5ms) can cause the GC to run continuously, starving application threads of CPU.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
