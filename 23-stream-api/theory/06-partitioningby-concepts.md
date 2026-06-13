# Stream API - Part 6

## Learning Goal

This file covers a focused slice of **Stream API**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `partitioningBy` |partitioningBy is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `counting` |counting is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `summarizingInt` |summarizingInt is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `mapping` | A Map stores key-value pairs and retrieves values by key. |
| `reducing` |reducing is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### partitioningBy

partitioningBy is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Partition elements into true/false lists based on a predicate
Map<Boolean, List<String>> partitioned = Stream.of("a", "bb", "c", "ddd")
                                               .collect(Collectors.partitioningBy(s -> s.length() > 1));
// Result: {false=["a", "c"], true=["bb", "ddd"]}
```

Practical check:

- Define `partitioningBy` in one sentence.
- Recognize `partitioningBy` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `partitioningBy`.

Tiny example or mental model:

- When reading code, ask: what does `partitioningBy` change, allow, reject, or clarify?

### counting

counting is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Count elements downstream in a grouping or partitioning operation
Map<Boolean, Long> counts = Stream.of("a", "bb", "c", "ddd")
                                  .collect(Collectors.partitioningBy(
                                      s -> s.length() > 1, 
                                      Collectors.counting()
                                  ));
// Result: {false=2, true=2}
```

Practical check:

- Define `counting` in one sentence.
- Recognize `counting` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `counting`.

Tiny example or mental model:

- When reading code, ask: what does `counting` change, allow, reject, or clarify?

### summarizingInt

summarizingInt is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Gather statistics on int transformations (count, sum, min, average, max)
IntSummaryStatistics stats = Stream.of("a", "bb", "ccc")
                                   .collect(Collectors.summarizingInt(String::length));
System.out.println("Max: " + stats.getMax() + ", Average: " + stats.getAverage());
```

Practical check:

- Define `summarizingInt` in one sentence.
- Recognize `summarizingInt` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `summarizingInt`.

Tiny example or mental model:

- When reading code, ask: what does `summarizingInt` change, allow, reject, or clarify?

### mapping

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

#### Code Example
```java
// Adapt a collector to accept elements of a different type
Map<Integer, Set<String>> map = Stream.of("apple", "banana", "apricot")
                                      .collect(Collectors.groupingBy(
                                          String::length,
                                          Collectors.mapping(s -> s.substring(0, 1), Collectors.toSet())
                                      ));
// Result: {5=["a"], 6=["b", "a"]}
```

Practical check:

- Define `mapping` in one sentence.
- Recognize `mapping` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `mapping`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### reducing

reducing is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Perform downstream reduction
Map<Integer, Optional<String>> maxByLength = Stream.of("a", "bb", "ccc", "d")
    .collect(Collectors.groupingBy(
        s -> s.length() % 2,
        Collectors.reducing((s1, s2) -> s1.length() >= s2.length() ? s1 : s2)
    ));
```

## Common Mistakes

### 1. Expecting partitioningBy keys to be absent when empty
The map returned by `partitioningBy` always contains entries for both `true` and `false`, even if no input elements match one (or both) of the partitions. The associated value will be an empty list, not null.
```java
Map<Boolean, List<String>> result = Stream.of("a", "b")
    .collect(Collectors.partitioningBy(s -> s.length() > 5));
System.out.println(result.get(true)); // Prints [] (empty list, not null or missing key)
```

Practical check:

- Define `reducing` in one sentence.
- Recognize `reducing` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `reducing`.

Tiny example or mental model:

- When reading code, ask: what does `reducing` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
