# Java Memory Management - Part 1

## Learning Goal

This file covers a focused slice of **Java Memory Management**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Stack` | Stack stores method frames, local variables, and call flow for each thread. |
| `Heap` | Heap stores objects created at runtime. |
| `Method Area / Metaspace` | Metaspace stores class metadata outside the ordinary Java heap in modern JVMs. |
| `PC Register` |The PC register tracks the current JVM instruction for a thread. |
| `Native Method Stack` | Stack stores method frames, local variables, and call flow for each thread. |
| `Object lifecycle` |Object lifecycle covers creation, reachability, use, and eventual garbage collection. |
| `Reference variable` |A reference variable stores a reference to an object, not the object data itself. |
| `Strong reference` |A strong reference keeps an object reachable and prevents it from being garbage collected. |

## Detailed Notes

### Stack

Stack stores method frames, local variables, and call flow for each thread.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Stack` in one sentence.
- Recognize `Stack` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Stack`.

Tiny example or mental model:

- When reading code, ask: what does `Stack` change, allow, reject, or clarify?

### Heap

Heap stores objects created at runtime.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Heap` in one sentence.
- Recognize `Heap` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Heap`.

Tiny example or mental model:

- When reading code, ask: what does `Heap` change, allow, reject, or clarify?

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

### Object lifecycle

Object lifecycle covers creation, reachability, use, and eventual garbage collection.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Object lifecycle` in one sentence.
- Recognize `Object lifecycle` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Object lifecycle`.

Tiny example or mental model:

- When reading code, ask: what does `Object lifecycle` change, allow, reject, or clarify?

### Reference variable

A reference variable stores a reference to an object, not the object data itself.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Reference variable` in one sentence.
- Recognize `Reference variable` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Reference variable`.

Tiny example or mental model:

- When reading code, ask: what does `Reference variable` change, allow, reject, or clarify?

### Strong reference

A strong reference keeps an object reachable and prevents it from being garbage collected.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Strong reference` in one sentence.
- Recognize `Strong reference` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Strong reference`.

Tiny example or mental model:

- When reading code, ask: what does `Strong reference` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
