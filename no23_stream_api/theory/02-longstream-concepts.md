# Stream API - Part 2

## Detailed Notes

### LongStream

The static factory method `Stream.of()` allows you to quickly create a Stream from directly supplied values (varargs).

#### Code Example
```java
// Primitive LongStream to avoid boxing overhead
LongStream longStream = LongStream.of(100L, 200L, 300L);
LongStream range = LongStream.rangeClosed(1, 100); // 1 to 100 inclusive
```

### DoubleStream

The static factory method `Stream.of()` allows you to quickly create a Stream from directly supplied values (varargs).

#### Code Example
```java
// Primitive DoubleStream to avoid boxing overhead
DoubleStream doubleStream = DoubleStream.of(1.5, 2.5, 3.5);
DoubleSummaryStatistics stats = doubleStream.summaryStatistics();
System.out.println("Average: " + stats.getAverage());
```

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

### filter

`filter` — filter provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Retain elements that match the given predicate
Stream.of("apple", "banana", "kiwi")
      .filter(s -> s.length() > 4)
      .forEach(System.out::println); // Prints: apple, banana
```

### map

Map does not inherit from Collection, so it does not have a direct `.stream()` method. However, you can create a Stream from a Map indirectly via `.keySet().stream()`, `.values().stream()`, or `.entrySet().stream()`.

#### Code Example
```java
// Transform each element 1-to-1
Stream.of("apple", "banana")
      .map(String::toUpperCase)
      .forEach(System.out::println); // Prints: APPLE, BANANA
```

### flatMap

Map does not inherit from Collection, so it does not have a direct `.stream()` method. However, you can create a Stream from a Map indirectly via `.keySet().stream()`, `.values().stream()`, or `.entrySet().stream()`.

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

#### When to use which?

#### Visualizing the flattening
If we have a stream of streams:
`Stream.of( Stream.of(1, 2), Stream.of(3, 4) )`
- Applying `map(s -> s)` keeps it as `Stream<Stream<Integer>>` (nested).
- Applying `flatMap(s -> s)` merges them into a single `Stream<Integer>` containing `[1, 2, 3, 4]`.

### distinct

`distinct` — distinct provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Remove duplicates based on Object.equals()
Stream.of(1, 2, 2, 3, 1)
      .distinct()
      .forEach(System.out::print); // Prints: 123
```

### sorted

`sorted` — sorted provides specific functionality and rules in Java development.

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

## Why flatMap() Differs from map()

In the Java Stream API, the fundamental difference between `map()` and `flatMap()` lies in the structure of the data they produce and how they transform elements. The `map()` operation is a one-to-one transformation, taking a function of type `T -> R` and returning a `Stream<R>` where each input element corresponds to exactly one output element. Conversely, `flatMap()` is a one-to-many transformation, taking a mapper function of type `T -> Stream<R>`. Instead of producing a nested stream structure like `Stream<Stream<R>>`, `flatMap()` merges or "flattens" the contents of each transient stream into a single, continuous downstream `Stream<R>`. As elements flow, the JVM executes the function, creates temporary stream objects, consumes their elements, and closes each transient stream sequentially.

### Mental Model
```
map() [One-to-One]:
Input:  [ "A" ] ---------> map(s -> s.toLowerCase()) ---------> Output: [ "a" ]

flatMap() [One-to-Many & Flatten]:
Input:  [ [1, 2], [3, 4] ]
              |
              +--> flatMap(list -> list.stream())
                      |
                      v
          Stream[1, 2] and Stream[3, 4]  (Nested Streams)
                      |
                      v (Flattening)
Output: [ 1, 2, 3, 4 ]                   (Single Stream)
```

### Code Example
```java
import java.util.List;
import java.util.stream.Stream;

public class FlatMapDemo {
    public static void main(String[] args) {
        List<List<String>> nestedList = List.of(
            List.of("Java", "Python"),
            List.of("C++", "Go")
        );

        // flatMap flattens the Stream<List<String>> into Stream<String>
        List<String> flattened = nestedList.stream()
            .flatMap(list -> list.stream())
            .map(String::toUpperCase)
            .toList();

        System.out.println("Flattened: " + flattened);
        // Console Output:
        // Flattened: [JAVA, PYTHON, C++, GO]
    }
}
```

### Cause-Effect Chain
Nested collection input &rarr; map() produces Stream of Streams (nested) &rarr; flatMap() receives mapper returning Stream&lt;R&gt; &rarr; flatMap() extracts and concatenates elements of intermediate streams &rarr; Intermediate streams automatically closed &rarr; Single unified downstream Stream produced
