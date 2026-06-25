# Stream API Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## stream pipeline

A stream pipeline is a sequence of processing steps consisting of a source (such as a collection, an array, a generator function, or an I/O channel), followed by zero or more intermediate operations, and exactly one terminal operation. It acts as a declarative query builder rather than an active loop.

Why it matters: It allows developers to express complex data transformation logic readably and declaratively, shielding them from low-level iteration mechanics. By separating pipeline construction from execution, it optimizes computation steps and avoids temporary storage of intermediate structures.

Common confusion: Developers often think a pipeline is executed step-by-step for the entire collection (e.g., executing filter on all elements, then mapping all elements). In reality, elements flow vertically down the pipeline one at a time only when pulled by the terminal operation.

Small example:
```java
// A pipeline consisting of: List source -> filter -> map -> collect (terminal)
List<String> activeNames = users.stream()
                                .filter(User::isActive)
                                .map(User::getName)
                                .collect(Collectors.toList());
```

## intermediate operation

An intermediate operation is a stream operation (like `filter`, `map`, `flatMap`, `sorted`, or `distinct`) that transforms a stream into another stream. These operations are always lazy and do not perform any processing on the source elements.

Why it matters: They allow the pipeline to be built incrementally, enabling lazy evaluation and optimization. Without them, every transformation stage would require intermediate collections, leading to high heap allocations and redundant loops.

Common confusion: Thinking that calling an intermediate operation immediately processes elements. If there is no terminal operation at the end of the pipeline, intermediate operations do not run at all.

Small example:
```java
// No output is printed because filter is an intermediate operation and the stream is not terminated!
Stream.of("apple", "banana")
      .filter(s -> {
          System.out.println(s);
          return true;
      });
```

## terminal operation

 A terminal operation is the final operation in a stream pipeline (such as `collect`, `forEach`, `reduce`, `count`, `min`, `max`, `anyMatch`, or `toList`) that triggers the traversal of the pipeline and produces a result (or side effect).

Why it matters: It closes the stream pipeline, initiates element traversal, pushes elements through the registered operations, and collects the final output. Once a terminal operation is called, the stream is considered consumed and cannot be reused.

Common confusion: Attempting to call multiple terminal operations on the same stream instance. Doing so will throw an `IllegalStateException` because the stream is closed after the first terminal operation.

Small example:
```java
Stream<String> stream = Stream.of("a", "b");
long count = stream.count(); // Terminal operation executes successfully
// stream.forEach(System.out::println); // Throws IllegalStateException!
```

## lazy evaluation

Lazy evaluation is a compiler/runtime optimization strategy where computation is deferred until its result is actually required by a terminal operation.

Why it matters: It minimizes CPU cycles and memory footprint by preventing unnecessary computations. It also allows streams to handle infinite sources (like sequence generators) because elements are only evaluated on-demand.

Common confusion: Believing that lazy evaluation is identical to asynchronous execution. Lazy operations are fully synchronous but deferred; they execute on the invoking thread unless parallelized.

Small example:
```java
// Elements are only processed up to the first match because of lazy evaluation combined with limit
Stream.iterate(1, i -> i + 1)
      .filter(i -> i % 2 == 0)
      .limit(1)
      .forEach(System.out::println); // Prints: 2
```

## short-circuiting

A short-circuiting operation is a stream operation that can produce a finite result or terminate execution even when presented with infinite input. Examples include intermediate operations like `limit()` and terminal operations like `findFirst()`, `anyMatch()`, `allMatch()`, and `noneMatch()`.

Why it matters: It enables massive performance optimization by halting stream processing immediately after a matching condition or size limit is met, avoiding useless CPU work for the remainder of the dataset.

Common confusion: Thinking that short-circuiting operations always process the first element. In parallel streams, short-circuiting operations can evaluate multiple elements concurrently and might terminate based on the fastest thread's result.

Small example:
```java
// Stops as soon as any element matches, rather than checking the rest of the stream
boolean hasMatch = Stream.of("apple", "banana", "cherry")
                         .anyMatch(s -> {
                             System.out.println("Checking: " + s);
                             return s.startsWith("b");
                         }); // Prints "Checking: apple", "Checking: banana", then halts.
```

## collector

A collector is an implementation of the `Collector` interface (typically obtained via the `Collectors` utility class) used as the argument to the `collect()` terminal operation to accumulate stream elements into a mutable container.

Why it matters: It defines how stream elements should be aggregated into data structures (like Lists, Sets, or Maps) or summarized (joined, summed, grouped, partitioned). It handles container instantiation, element accumulation, parallel merging, and final transformation.

Common confusion: Confusing `Collectors.toList()` (which returns a mutable ArrayList wrapper) with `Stream.toList()` (Java 16+, which returns an unmodifiable List and is faster because it avoids collector overhead).

Small example:
```java
// Grouping words by length into a Map using a collector
Map<Integer, List<String>> groups = Stream.of("a", "bb", "c")
    .collect(Collectors.groupingBy(String::length)); // {1=[a, c], 2=[bb]}
```

## parallel stream

A parallel stream is a stream execution mode that splits the stream pipeline into multiple tasks, executing them concurrently using the JVM's shared `ForkJoinPool.commonPool()`.

Why it matters: It enables easy, declarative multi-threaded execution to leverage multi-core CPUs for large datasets, potentially reducing execution time for CPU-intensive tasks.

Common confusion: Assuming that parallel streams will always speed up execution. For small datasets, non-splittable sources (like LinkedList), or I/O-bound tasks, parallel streams can actually run slower due to thread management overhead and pool starvation.

Small example:
```java
// Computes sum concurrently on multiple threads
long sum = LongStream.rangeClosed(1, 100_000)
                     .parallel()
                     .sum();
```
