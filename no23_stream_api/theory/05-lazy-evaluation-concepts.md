# Stream API - Part 5

## Detailed Notes

### Lazy evaluation

`Lazy evaluation` — Intermediate stream operations are not executed until a terminal operation is invoked.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Intermediate operations do not execute until a terminal operation is called
Stream<String> stream = Stream.of("a", "b", "c")
                              .peek(s -> System.out.println("Processing: " + s));
System.out.println("Stream pipeline built.");
stream.count(); // Now execution starts!
```

### Short-circuiting

`Short-circuiting` — Short-circuiting provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Short-circuiting halts pipeline processing early
Stream.iterate(1, i -> i + 1)
      .peek(i -> System.out.println("Generated: " + i))
      .limit(3) // Limits elements flowing downstream
      .count(); // Prints Generated: 1, 2, 3
```

### Parallel stream

The static factory method `Stream.of()` allows you to quickly create a Stream from directly supplied values (varargs).

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

### Collectors:

Collectors is a group of related rules in Stream API that groups several related details.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// General use of Collectors factory methods
List<String> list = Stream.of("a", "b").collect(Collectors.toList());
```

### toSet

A Set is a collection that rejects duplicates according to equality rules.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

#### Code Example
```java
// Accumulate into a Set to eliminate duplicates
Set<String> set = Stream.of("a", "b", "a").collect(Collectors.toSet()); // ["a", "b"]
```

### toMap

Map does not inherit from Collection, so it does not have a direct `.stream()` method. However, you can create a Stream from a Map indirectly via `.keySet().stream()`, `.values().stream()`, or `.entrySet().stream()`.

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

### joining

`joining` — joining provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Join string elements with delimiter
String joined = Stream.of("a", "b", "c")
                      .collect(Collectors.joining(", ")); // "a, b, c"
```

### groupingBy

`groupingBy` — groupingBy provides specific functionality and rules in Java development.

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

## Why Streams Are Lazily Evaluated

Java Streams achieve laziness by separating pipeline construction from element processing. When you invoke intermediate operations like `filter()` or `map()`, the JVM does not traverse the data source or execute any lambda expressions. Instead, each intermediate operation returns a new Stream stage represented by an `AbstractPipeline` subclass, appending itself to form a linked list of stream stages. A `Sink` interface facilitates the actual execution; each stage wraps the downstream `Sink` inside its own `Sink` implementation, establishing a chained callback architecture. Only when a terminal operation is invoked does the pipeline traverse the source, pushing elements down the nested `Sink` chain one by one. This single-pass evaluation allows short-circuiting operations to terminate early and prevents the creation of overhead-heavy intermediate collections.

### Mental Model
```
[Source] -> AbstractPipeline (filter) -> AbstractPipeline (map) -> Terminal (collect)
                  |                            |
            Sink.begin()                 Sink.begin()
            Sink.accept()  ------------> Sink.accept()
            Sink.end()                   Sink.end()
```

### Code Example
```java
import java.util.List;
import java.util.stream.Stream;

public class LazyEvaluationDemo {
    public static void main(String[] args) {
        List<String> result = Stream.of("apple", "banana", "pear")
            .filter(s -> {
                System.out.println("Filtering: " + s);
                return s.length() > 4;
            })
            .map(s -> {
                System.out.println("Mapping: " + s);
                return s.toUpperCase();
            })
            .limit(1)
            .toList();
        
        System.out.println("Result: " + result);
        // Console Output:
        // Filtering: apple
        // Mapping: apple
        // Result: [APPLE]
    }
}
```

### Cause-Effect Chain
Intermediate operations registered &rarr; Pipeline constructed as linked AbstractPipeline nodes &rarr; Terminal operation invoked &rarr; Source elements pulled through Sink chain one-by-one &rarr; Short-circuit (limit) triggers early termination &rarr; Minimum CPU work performed

## Why Parallel Streams Are Not a Default Solution

Parallel streams split the stream's source data using a `Spliterator` and submit tasks to the shared `ForkJoinPool.commonPool()`. Because this pool is shared globally across the entire JVM classloader, long-running, CPU-intensive, or blocking operations in one stream will starve thread resources for other parts of the application. The efficiency of data splitting also heavily relies on the structure of the data source; an array or `ArrayList` can be split in O(1) time by dividing index ranges, whereas a `LinkedList` requires sequential traversal of O(N) elements to find the split point, destroying any performance gain. Furthermore, coordinating, context-switching, and merging results across worker threads introduces substantial JVM overhead. Consequently, for small datasets or non-trivial I/O-bound tasks, a parallel stream can run significantly slower than its sequential counterpart.

### Mental Model
```
ArrayList Splitting (O(1) index-based split):
[ Element 0 - 3 ] ---> Thread 1
[ Element 4 - 7 ] ---> Thread 2

LinkedList Splitting (O(N) sequential traversal):
[Head] -> [Node] -> [Node] -> [Node] -> [Node] -> [Node] -> [Node] -> [Tail]
 (Must traverse links step-by-step to find splitting point)
```

### Code Example
```java
import java.util.List;
import java.util.stream.LongStream;

public class ParallelStreamDemo {
    public static void main(String[] args) {
        // Parallel stream processing using the common ForkJoinPool
        long sum = LongStream.rangeClosed(1, 1_000_000)
                             .parallel()
                             .filter(n -> n % 2 == 0)
                             .sum();
        
        System.out.println("Sum: " + sum);
        // Console Output:
        // Sum: 250000500000
    }
}
```

### Cause-Effect Chain
Data source (e.g. LinkedList) is hard to split &rarr; Spliterator performs O(N) sequential traversal to split work &rarr; High splitting and coordination overhead &rarr; Thread execution in shared global ForkJoinPool.commonPool() &rarr; Blocking operations starve threads &rarr; Degraded performance compared to sequential stream
