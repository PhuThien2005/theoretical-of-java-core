# Java Memory Management Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## stack

Stack stores method frames, local variables, and call flow for each thread.

Why it matters: It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Common confusion: learners often memorize `stack` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `stack` change, allow, reject, or clarify?

## heap

Heap stores objects created at runtime.

Why it matters: It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Common confusion: learners often memorize `heap` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `heap` change, allow, reject, or clarify?

## metaspace

Metaspace stores class metadata outside the ordinary Java heap in modern JVMs.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `metaspace` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `metaspace` change, allow, reject, or clarify?

## strong reference

strong reference is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `strong reference` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `strong reference` change, allow, reject, or clarify?

## weak reference

weak reference is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `weak reference` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `weak reference` change, allow, reject, or clarify?

## garbage collection

Garbage collection reclaims memory from objects that are no longer reachable.

Why it matters: It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Common confusion: learners often memorize `garbage collection` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `garbage collection` change, allow, reject, or clarify?

## memory leak

memory leak is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `memory leak` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `memory leak` change, allow, reject, or clarify?
