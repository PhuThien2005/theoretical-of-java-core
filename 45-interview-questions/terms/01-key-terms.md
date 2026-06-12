# Common Java Core Interview Questions Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## pass-by-value

pass-by-value is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `pass-by-value` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `pass-by-value` change, allow, reject, or clarify?

## String immutability

String immutability is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `String immutability` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `String immutability` change, allow, reject, or clarify?

## HashMap internals

A Map stores key-value pairs and retrieves values by key.

Why it matters: It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Common confusion: learners often memorize `HashMap internals` as a word but cannot explain what problem it solves or what rule it changes.

Small example: `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

## equals and hashCode

equals() defines logical equality between objects.

Why it matters: It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Common confusion: learners often memorize `equals and hashCode` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `equals and hashCode` change, allow, reject, or clarify?

## deadlock

Deadlock happens when threads wait forever for locks held by each other.

Why it matters: It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Common confusion: learners often memorize `deadlock` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `deadlock` change, allow, reject, or clarify?

## stream laziness

A Stream is a pipeline for processing elements through lazy operations.

Why it matters: It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Common confusion: learners often memorize `stream laziness` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `stream laziness` change, allow, reject, or clarify?

## GC interview answer

GC interview answer is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Common confusion: learners often memorize `GC interview answer` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `GC interview answer` change, allow, reject, or clarify?
