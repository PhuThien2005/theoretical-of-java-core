# Collections Framework Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## Iterable

Iterable is the root traversal contract that allows an object to be used in enhanced for loops.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `Iterable` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `Iterable` change, allow, reject, or clarify?

## Collection

A collection is an object that groups multiple elements under a common API.

Why it matters: It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Common confusion: learners often memorize `Collection` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `Collection` change, allow, reject, or clarify?

## List

A List is an ordered collection that can contain duplicates and supports positional access.

Why it matters: It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Common confusion: learners often memorize `List` as a word but cannot explain what problem it solves or what rule it changes.

Small example: `List<String> names = new ArrayList<>();` stores ordered elements.

## Set

A Set is a collection that rejects duplicates according to equality rules.

Why it matters: It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Common confusion: learners often memorize `Set` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `Set` change, allow, reject, or clarify?

## Queue

Queue represents a collection designed for holding elements before processing, usually FIFO.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `Queue` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `Queue` change, allow, reject, or clarify?

## Deque

Deque is a double-ended queue that supports insertion and removal at both ends.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `Deque` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `Deque` change, allow, reject, or clarify?

## Map

A Map stores key-value pairs and retrieves values by key.

Why it matters: It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Common confusion: learners often memorize `Map` as a word but cannot explain what problem it solves or what rule it changes.

Small example: `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

## Iterator

An Iterator traverses a collection while hiding its internal representation.

Why it matters: It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Common confusion: learners often memorize `Iterator` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `Iterator` change, allow, reject, or clarify?

## fail-fast

fail-fast is a specific concept in Collections Framework; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `fail-fast` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `fail-fast` change, allow, reject, or clarify?

## ConcurrentModificationException

An exception represents an abnormal condition that a program may catch or propagate.

Why it matters: It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Common confusion: learners often memorize `ConcurrentModificationException` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `ConcurrentModificationException` change, allow, reject, or clarify?
