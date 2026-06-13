# Stream API - Part 5

## Learning Goal

This file covers a focused slice of **Stream API**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Lazy evaluation` |Lazy evaluation is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Short-circuiting` |Short-circuiting is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Parallel stream` | A Stream is a pipeline for processing elements through lazy operations. |
| `Collectors:` | Collectors is a group of related rules in Stream API that groups several related details. |
| `toSet` | A Set is a collection that rejects duplicates according to equality rules. |
| `toMap` | A Map stores key-value pairs and retrieves values by key. |
| `joining` |joining is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `groupingBy` |groupingBy is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### Lazy evaluation

Lazy evaluation is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Intermediate operations do not execute until a terminal operation is called
Stream<String> stream = Stream.of("a", "b", "c")
                              .peek(s -> System.out.println("Processing: " + s));
System.out.println("Stream pipeline built.");
stream.count(); // Now execution starts!
```

Practical check:

- Define `Lazy evaluation` in one sentence.
- Recognize `Lazy evaluation` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Lazy evaluation`.

Tiny example or mental model:

- When reading code, ask: what does `Lazy evaluation` change, allow, reject, or clarify?

### Short-circuiting

Short-circuiting is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Short-circuiting halts pipeline processing early
Stream.iterate(1, i -> i + 1)
      .peek(i -> System.out.println("Generated: " + i))
      .limit(3) // Limits elements flowing downstream
      .count(); // Prints Generated: 1, 2, 3
```

Practical check:

- Define `Short-circuiting` in one sentence.
- Recognize `Short-circuiting` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Short-circuiting`.

Tiny example or mental model:

- When reading code, ask: what does `Short-circuiting` change, allow, reject, or clarify?

### Parallel stream

A Stream is a pipeline for processing elements through lazy operations.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

#### Code Example
```java
// Processing in parallel using ForkJoinPool.commonPool()
long sum = LongStream.rangeClosed(1, 1_000_000)
                     .parallel()
                     .sum();
```

### Case Study: Why parallel streams can be slower for small datasets or stateful operations

Parallel streams split data using `Spliterator`, submit tasks to the `ForkJoinPool.commonPool()`, coordinate execution across CPU threads, and merge partial results back together.

#### Why small datasets are slower
The overhead of thread management, context switching, task splitting, and result merging is much larger than the execution time of a small loop. For example, processing 100 integers sequentially takes a few nanoseconds, whereas setting up parallel execution takes milliseconds.

#### Why stateful operations degrade parallel performance
Stateful intermediate operations like `sorted()`, `distinct()`, `limit()`, and `skip()` require cross-thread coordination and barrier synchronization.
- For `sorted()`, elements must be fully collected, merged, sorted, and re-split.
- For `limit(n)`, elements must be processed keeping strict track of the encounter order across multiple threads, creating sequential bottlenecks.

#### The N * Q Rule
A good heuristic to decide whether to use parallel streams is $N \times Q > 10,000$, where:
- $N$ is the number of data elements.
- $Q$ is the computational cost per element.
If $N \times Q$ is small, sequential streams are almost always faster.

Practical check:

- Define `Parallel stream` in one sentence.
- Recognize `Parallel stream` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Parallel stream`.

Tiny example or mental model:

- When reading code, ask: what does `Parallel stream` change, allow, reject, or clarify?

### Collectors:

Collectors is a group of related rules in Stream API that groups several related details.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// General use of Collectors factory methods
List<String> list = Stream.of("a", "b").collect(Collectors.toList());
```

Practical check:

- Define `Collectors:` in one sentence.
- Recognize `Collectors:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Collectors:`.

Tiny example or mental model:

- When reading code, ask: what does `Collectors:` change, allow, reject, or clarify?

### toSet

A Set is a collection that rejects duplicates according to equality rules.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

#### Code Example
```java
// Accumulate into a Set to eliminate duplicates
Set<String> set = Stream.of("a", "b", "a").collect(Collectors.toSet()); // ["a", "b"]
```

Practical check:

- Define `toSet` in one sentence.
- Recognize `toSet` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `toSet`.

Tiny example or mental model:

- When reading code, ask: what does `toSet` change, allow, reject, or clarify?

### toMap

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

#### Code Example
```java
// Accumulate into a Map (requires key mapper, value mapper, and optional merge function)
Map<Integer, String> map = Stream.of("apple", "banana")
                                 .collect(Collectors.toMap(
                                     String::length, 
                                     s -> s, 
                                     (existing, replacement) -> existing
                                 ));
```

Practical check:

- Define `toMap` in one sentence.
- Recognize `toMap` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `toMap`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### joining

joining is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Join string elements with delimiter
String joined = Stream.of("a", "b", "c")
                      .collect(Collectors.joining(", ")); // "a, b, c"
```

Practical check:

- Define `joining` in one sentence.
- Recognize `joining` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `joining`.

Tiny example or mental model:

- When reading code, ask: what does `joining` change, allow, reject, or clarify?

### groupingBy

groupingBy is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Group elements by classifier function
Map<Integer, List<String>> groups = Stream.of("a", "bb", "c", "ddd")
                                          .collect(Collectors.groupingBy(String::length));
// Result: {1=["a", "c"], 2=["bb"], 3=["ddd"]}
```

## Common Mistakes

### 1. Collectors.toMap() Duplicate Keys Exception
If the keys returned by the key mapper function are not unique, `Collectors.toMap` throws an `IllegalStateException` unless a merge function is provided.
```java
// Throws IllegalStateException: Duplicate key 5
Stream.of("apple", "peach")
      .collect(Collectors.toMap(String::length, s -> s)); 

// Correct way with merge function:
Map<Integer, String> map = Stream.of("apple", "peach")
      .collect(Collectors.toMap(
          String::length, 
          s -> s, 
          (existing, replacement) -> existing // Keeps first one found
      ));
```

### 2. Modifying shared state from stream operations
Parallel streams execute operations on multiple threads. Mutating a shared collection like `ArrayList` from within stream operations leads to race conditions.
```java
List<Integer> list = new ArrayList<>();
List.of(1, 2, 3, 4).parallelStream().forEach(list::add); // DANGEROUS: Race condition!
```

Practical check:

- Define `groupingBy` in one sentence.
- Recognize `groupingBy` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `groupingBy`.

Tiny example or mental model:

- When reading code, ask: what does `groupingBy` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
