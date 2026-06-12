# Advanced JVM - Part 1

## Learning Goal

This file covers a focused slice of **Advanced JVM**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `JVM architecture` | The JVM executes bytecode and manages runtime services such as memory, JIT, and GC. |
| `Class Loader Subsystem` |Class Loader Subsystem is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Runtime Data Areas:` | Runtime Data Areas is a group of related rules in Advanced JVM that groups several related details. |
| `Heap` | Heap stores objects created at runtime. |
| `Stack` | Stack stores method frames, local variables, and call flow for each thread. |
| `Method Area / Metaspace` | Metaspace stores class metadata outside the ordinary Java heap in modern JVMs. |
| `PC Register` |The PC register tracks the current JVM instruction for a thread. |
| `Native Method Stack` | Stack stores method frames, local variables, and call flow for each thread. |

## Detailed Notes

### JVM architecture

The JVM executes bytecode and manages runtime services such as memory, JIT, and GC.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `JVM architecture` in one sentence.
- Recognize `JVM architecture` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `JVM architecture`.

Tiny example or mental model:

- When reading code, ask: what does `JVM architecture` change, allow, reject, or clarify?

### Class Loader Subsystem

Class Loader Subsystem is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Class Loader Subsystem` in one sentence.
- Recognize `Class Loader Subsystem` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Class Loader Subsystem`.

Tiny example or mental model:

- When reading code, ask: what does `Class Loader Subsystem` change, allow, reject, or clarify?

### Runtime Data Areas:

Runtime Data Areas is a group of related rules in Advanced JVM that groups several related details.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Runtime Data Areas:` in one sentence.
- Recognize `Runtime Data Areas:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Runtime Data Areas:`.

Tiny example or mental model:

- When reading code, ask: what does `Runtime Data Areas:` change, allow, reject, or clarify?

### Heap

Heap stores objects created at runtime.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Heap` in one sentence.
- Recognize `Heap` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Heap`.

Tiny example or mental model:

- When reading code, ask: what does `Heap` change, allow, reject, or clarify?

### Stack

Stack stores method frames, local variables, and call flow for each thread.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Stack` in one sentence.
- Recognize `Stack` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Stack`.

Tiny example or mental model:

- When reading code, ask: what does `Stack` change, allow, reject, or clarify?

### Method Area / Metaspace

Metaspace stores class metadata outside the ordinary Java heap in modern JVMs.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Method Area / Metaspace` in one sentence.
- Recognize `Method Area / Metaspace` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Method Area / Metaspace`.

Tiny example or mental model:

- When reading code, ask: what does `Method Area / Metaspace` change, allow, reject, or clarify?

### PC Register

The PC register tracks the current JVM instruction for a thread.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `PC Register` in one sentence.
- Recognize `PC Register` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `PC Register`.

Tiny example or mental model:

- When reading code, ask: what does `PC Register` change, allow, reject, or clarify?

### Native Method Stack

Stack stores method frames, local variables, and call flow for each thread.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Native Method Stack` in one sentence.
- Recognize `Native Method Stack` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Native Method Stack`.

Tiny example or mental model:

- When reading code, ask: what does `Native Method Stack` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
