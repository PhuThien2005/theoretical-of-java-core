# Modern Java Concepts To Know - Part 2

## Learning Goal

This file covers Sequenced Collections and String Templates. Study each concept as a practical Java rule.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Sequenced Collections` | Interfaces introduced in Java 21 representing collections with a defined encounter order. |
| `String templates were once preview; currently they should not be used as a stable feature` | Status and alternatives for the removed Java 21 String Templates preview feature. |

---

## Detailed Notes

### Sequenced Collections

Sequenced Collections (introduced in Java 21) unify collections that have a defined first and last element, providing a standard API for retrieval, modification, and reverse-order views.

- **Interface Hierarchy**:
  - `SequencedCollection<E>` (extended by `List`, `Deque`, and `SequencedSet`)
  - `SequencedSet<E>` (extended by `LinkedHashSet` and `SortedSet`)
  - `SequencedMap<K, V>` (extended by `LinkedHashMap` and `SortedMap`)

- **Runnable Example**:
  ```java
  SequencedCollection<String> coll = new ArrayList<>(List.of("one", "two", "three"));

  // Uniform retrieval
  String first = coll.getFirst(); // "one"
  String last = coll.getLast();   // "three"

  // Uniform modification
  coll.addFirst("zero");
  coll.addLast("four");

  // Reverse view (runs in O(1) time without copying elements)
  SequencedCollection<String> reversed = coll.reversed();
  System.out.println(reversed.getFirst()); // "four"
  ```

- **Common Mistake / Failure Mode**:
  - **Empty Collections**: Calling `getFirst()` or `getLast()` on an empty collection throws a runtime `NoSuchElementException`.
  - **Reversed Mutability**: The collection returned by `reversed()` is a view, not a copy. Modifying the reversed view directly mutates the backing original collection.

---

### String templates were once preview; currently they should not be used as a stable feature

String Templates (e.g., `STR."Hello \{name}"`) were introduced as a preview feature in Java 21. However, due to feedback, they were **removed** in subsequent releases (Java 22+) and did not proceed to standardization.

- **Correct Practice**:
  - Avoid using String Templates (`STR.`) in any standard or production code, as they will cause compilation failures in modern JDK versions.
  - **Standard Alternatives**:
    Use traditional string concatenation, `String.format()`, or the `String.formatted()` instance method:
    ```java
    String name = "Alice";
    
    // Concatenation
    String message1 = "Hello " + name;

    // String.format
    String message2 = String.format("Hello %s", name);

    // String.formatted (cleanest alternative)
    String message3 = "Hello %s".formatted(name);
    ```
