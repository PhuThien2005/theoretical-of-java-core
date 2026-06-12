# Modern Java Concepts To Know Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## record

A record is a compact Java class for immutable data carriers.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `record` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `record` change, allow, reject, or clarify?

## sealed class

A sealed class restricts which classes may extend or implement it.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `sealed class` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `sealed class` change, allow, reject, or clarify?

## pattern matching

pattern matching is a specific concept in Modern Java Concepts To Know; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `pattern matching` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `pattern matching` change, allow, reject, or clarify?

## text block

text block is a specific concept in Modern Java Concepts To Know; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Common confusion: learners often memorize `text block` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `text block` change, allow, reject, or clarify?

## virtual thread

A thread is a path of execution inside a process.

Why it matters: It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Common confusion: learners often memorize `virtual thread` as a word but cannot explain what problem it solves or what rule it changes.

Small example: `new Thread(task).start()` starts work on another thread.

## sequenced collection

A collection is an object that groups multiple elements under a common API.

Why it matters: It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Common confusion: learners often memorize `sequenced collection` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `sequenced collection` change, allow, reject, or clarify?
