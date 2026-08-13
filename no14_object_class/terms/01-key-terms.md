# Object class Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## Object class

`Object class` — The root class of the Java class hierarchy; every class implicitly inherits from Object.

## equals contract

equals() defines logical equality between objects.

## hashCode contract

hashCode() returns an integer hash used by hash-based collections.

Why it matters: It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Common confusion: learners often memorize `hashCode contract` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `hashCode contract` change, allow, reject, or clarify?

## reference equality

`reference equality` — Checks whether two reference variables point to the exact same memory location on the heap (using ==).

## value equality

`value equality` — Checks whether two distinct objects contain logically equivalent state (using .equals()).

## monitor methods

`monitor methods` — Methods in Object (wait, notify, notifyAll) used for thread synchronization on object monitors.
