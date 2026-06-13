# Stream API - Part 2

## Learning Goal

This file covers a focused slice of **Stream API**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `LongStream` | A Stream is a pipeline for processing elements through lazy operations. |
| `DoubleStream` | A Stream is a pipeline for processing elements through lazy operations. |
| `Intermediate operations:` | Intermediate operations is a group of related rules in Stream API that groups several related details. |
| `filter` |filter is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `map` | A Map stores key-value pairs and retrieves values by key. |
| `flatMap` | A Map stores key-value pairs and retrieves values by key. |
| `distinct` |distinct is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `sorted` |sorted is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### LongStream

A Stream is a pipeline for processing elements through lazy operations.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

#### Code Example
```java
// Primitive LongStream to avoid boxing overhead
LongStream longStream = LongStream.of(100L, 200L, 300L);
LongStream range = LongStream.rangeClosed(1, 100); // 1 to 100 inclusive
```

Practical check:

- Define `LongStream` in one sentence.
- Recognize `LongStream` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `LongStream`.

Tiny example or mental model:

- When reading code, ask: what does `LongStream` change, allow, reject, or clarify?

### DoubleStream

A Stream is a pipeline for processing elements through lazy operations.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

#### Code Example
```java
// Primitive DoubleStream to avoid boxing overhead
DoubleStream doubleStream = DoubleStream.of(1.5, 2.5, 3.5);
DoubleSummaryStatistics stats = doubleStream.summaryStatistics();
System.out.println("Average: " + stats.getAverage());
```

Practical check:

- Define `DoubleStream` in one sentence.
- Recognize `DoubleStream` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `DoubleStream`.

Tiny example or mental model:

- When reading code, ask: what does `DoubleStream` change, allow, reject, or clarify?

### Intermediate operations:

Intermediate operations is a group of related rules in Stream API that groups several related details.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Intermediate operations are chained and executed lazily
Stream.of("a", "b", "c")
      .filter(s -> !s.isEmpty())
      .map(String::toUpperCase); // Returns a new Stream (not executed yet)
```

Practical check:

- Define `Intermediate operations:` in one sentence.
- Recognize `Intermediate operations:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Intermediate operations:`.

Tiny example or mental model:

- When reading code, ask: what does `Intermediate operations:` change, allow, reject, or clarify?

### filter

filter is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Retain elements that match the given predicate
Stream.of("apple", "banana", "kiwi")
      .filter(s -> s.length() > 4)
      .forEach(System.out::println); // Prints: apple, banana
```

Practical check:

- Define `filter` in one sentence.
- Recognize `filter` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `filter`.

Tiny example or mental model:

- When reading code, ask: what does `filter` change, allow, reject, or clarify?

### map

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

#### Code Example
```java
// Transform each element 1-to-1
Stream.of("apple", "banana")
      .map(String::toUpperCase)
      .forEach(System.out::println); // Prints: APPLE, BANANA
```

Practical check:

- Define `map` in one sentence.
- Recognize `map` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `map`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### flatMap

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

#### Code Example
```java
// Flatten nested structures (1-to-many mapping)
List<List<String>> nestedList = List.of(
    List.of("a", "b"),
    List.of("c", "d")
);
nestedList.stream()
          .flatMap(List::stream)
          .forEach(System.out::print); // Prints: abcd
```

### Case Study: FlatMap vs Map in detail

#### The difference in signatures and return types
- **`map`**: Takes a `Function<T, R>` mapping one element of type `T` to one element of type `R`. Returns `Stream<R>`.
- **`flatMap`**: Takes a `Function<T, Stream<R>>` mapping one element of type `T` to a `Stream<R>`. It then "flattens" these individual streams into a single consolidated `Stream<R>`.

#### When to use which?
- Use **`map`** for simple one-to-one transformations (e.g., transforming a string to its length, converting an object to its ID).
- Use **`flatMap`** when each element maps to a collection/array/stream, or when dealing with nested structures (e.g., extracting a list of orders from a list of customers).

#### Visualizing the flattening
If we have a stream of streams:
`Stream.of( Stream.of(1, 2), Stream.of(3, 4) )`
- Applying `map(s -> s)` keeps it as `Stream<Stream<Integer>>` (nested).
- Applying `flatMap(s -> s)` merges them into a single `Stream<Integer>` containing `[1, 2, 3, 4]`.

Practical check:

- Define `flatMap` in one sentence.
- Recognize `flatMap` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `flatMap`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### distinct

distinct is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Remove duplicates based on Object.equals()
Stream.of(1, 2, 2, 3, 1)
      .distinct()
      .forEach(System.out::print); // Prints: 123
```

Practical check:

- Define `distinct` in one sentence.
- Recognize `distinct` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `distinct`.

Tiny example or mental model:

- When reading code, ask: what does `distinct` change, allow, reject, or clarify?

### sorted

sorted is a specific concept in Stream API; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Sort elements in natural order
Stream.of("banana", "apple", "cherry")
      .sorted()
      .forEach(System.out::println); // Prints: apple, banana, cherry
```

## Common Mistakes

### 1. Statefulness and sorted() blocking
Calling `.sorted()` requires all elements of the stream to be stored in memory before sorting can begin. Doing this on an infinite stream (e.g. `Stream.generate(...)` or `Stream.iterate(...)`) will cause a hang or OutOfMemoryError.
```java
// DANGEROUS: Will hang indefinitely
Stream.iterate(0, i -> i + 1)
      .sorted()
      .limit(5)
      .forEach(System.out::println);
```

### 2. Modifying elements inside map() or filter()
Intermediate operations should be side-effect-free. Modifying outer variables or mutably altering elements in map/filter leads to race conditions and bugs, especially in parallel streams.
```java
List<Integer> target = new ArrayList<>();
Stream.of(1, 2, 3)
      .map(x -> {
          target.add(x); // BAD: Side effect!
          return x * 2;
      })
      .count();
```

Practical check:

- Define `sorted` in one sentence.
- Recognize `sorted` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `sorted`.

Tiny example or mental model:

- When reading code, ask: what does `sorted` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
