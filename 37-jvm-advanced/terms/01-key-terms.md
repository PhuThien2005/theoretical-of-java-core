# Advanced JVM Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## runtime data area

runtime data area is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `runtime data area` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `runtime data area` change, allow, reject, or clarify?

## execution engine

execution engine is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `execution engine` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `execution engine` change, allow, reject, or clarify?

## JIT

JIT is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Common confusion: learners often memorize `JIT` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `JIT` change, allow, reject, or clarify?

## heap generation

Heap stores objects created at runtime.

Why it matters: It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Common confusion: learners often memorize `heap generation` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `heap generation` change, allow, reject, or clarify?

## stop-the-world

stop-the-world is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `stop-the-world` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `stop-the-world` change, allow, reject, or clarify?

## GC algorithm

GC algorithm is a specific concept in Advanced JVM; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Common confusion: learners often memorize `GC algorithm` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `GC algorithm` change, allow, reject, or clarify?

## thread dump

A thread is a path of execution inside a process.

Why it matters: It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Common confusion: learners often memorize `thread dump` as a word but cannot explain what problem it solves or what rule it changes.

Small example: `new Thread(task).start()` starts work on another thread.
