# Stream API - Part 1

## Detailed Notes

### What is Stream?

The static factory method `Stream.of()` allows you to quickly create a Stream from directly supplied values (varargs).

#### Code Example
```java
// Streams process data pipelines lazily without modifying the source
List<String> list = List.of("apple", "banana", "cherry");
long count = list.stream()
                 .filter(s -> s.startsWith("a"))
                 .count(); // Terminal operation triggers execution
System.out.println(count); // Output: 1
```



### Stream vs Collection

A collection is an object that groups multiple elements under a common API.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

#### Code Example
```java
// Collection holds elements in memory; Stream is a one-time pipeline
List<String> list = List.of("a", "b", "c");
// Collection can be iterated multiple times
list.forEach(System.out::print);
list.forEach(System.out::print); 

// Stream can only be consumed ONCE
Stream<String> stream = list.stream();
stream.forEach(System.out::print);
// stream.forEach(System.out::print); // Throws IllegalStateException!
```



### Create Stream:

The static factory method `Stream.of()` allows you to quickly create a Stream from directly supplied values (varargs).

#### Code Example
```java
// Various factories to create streams
Stream<String> emptyStream = Stream.empty();
Stream<Integer> streamOf = Stream.of(1, 2, 3);
```



### from List

You can easily create a Stream from a List (or any Collection) by calling the default `.stream()` method provided on the Collection interface.

#### Code Example
```java
List<String> list = List.of("Java", "Stream", "API");
Stream<String> stream = list.stream(); // Returns Stream<String>
```



### from Array

`from Array` — from Array provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
String[] arr = {"x", "y", "z"};
Stream<String> stream = Arrays.stream(arr);

// Primitive array creation returns a specialized primitive stream
int[] intArr = {1, 2, 3};
IntStream intStream = Arrays.stream(intArr);
```



### from Map

Map does not inherit from Collection, so it does not have a direct `.stream()` method. However, you can create a Stream from a Map indirectly via `.keySet().stream()`, `.values().stream()`, or `.entrySet().stream()`.

#### Code Example
```java
Map<String, Integer> map = Map.of("A", 1, "B", 2);
// Streams are created from keySet, values, or entrySet of the Map
Stream<String> keyStream = map.keySet().stream();
Stream<Integer> valueStream = map.values().stream();
Stream<Map.Entry<String, Integer>> entryStream = map.entrySet().stream();
```



### Stream.of

The static factory method `Stream.of()` allows you to quickly create a Stream from directly supplied values (varargs).

#### Code Example
```java
Stream<String> streamOf = Stream.of("hello", "world");
```

### from Files

You can create a lazy Stream of lines from a file path using `Files.lines(Path)`.

#### Code Example
```java
// Lazy reading from files (automatically managed in try-with-resources)
try (Stream<String> lines = Files.lines(Paths.get("example.txt"))) {
    lines.filter(line -> line.contains("ERROR"))
         .forEach(System.out::println);
} catch (IOException e) {
    // Handle I/O exception
}
```



### IntStream

The static factory method `Stream.of()` allows you to quickly create a Stream from directly supplied values (varargs).

#### Code Example
```java
// Create primitive IntStream
IntStream rangeStream = IntStream.range(1, 5); // Elements: 1, 2, 3, 4
IntStream closedStream = IntStream.rangeClosed(1, 5); // Elements: 1, 2, 3, 4, 5
```

## Common Mistakes

### 1. Reusing a closed stream
Once a terminal operation is called on a stream, the stream is consumed/closed. Attempting to call another operation throws `IllegalStateException`.
```java
Stream<String> stream = Stream.of("a", "b", "c");
stream.forEach(System.out::println); // Consumes the stream
// stream.count(); // Throws IllegalStateException!
```

### 2. Creating a Stream.of with a primitive array
Passing a primitive array to `Stream.of` creates a `Stream<int[]>` containing exactly one element (the array itself), rather than a stream of numbers.
```java
int[] numbers = {1, 2, 3};
Stream<int[]> badStream = Stream.of(numbers); 
System.out.println(badStream.count()); // Prints 1

// Correct way to get IntStream:
IntStream goodStream = Arrays.stream(numbers);
System.out.println(goodStream.count()); // Prints 3
```



## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?

## Why Primitive Streams Exist and Avoid Autoboxing

In Java, generic type parameters cannot be primitive types, which means standard reference streams like `Stream<Integer>` must work with boxed objects. This boxing model introduces significant overhead: every number is wrapped in an object on the heap, causing memory inflation and cache misses due to pointer chasing. To address this, the JDK provides specialized primitive streams: `IntStream`, `LongStream`, and `DoubleStream`. These streams process primitive values directly within native memory boundaries, entirely bypassing the CPU overhead of auto-boxing and unboxing. Additionally, primitive streams expose optimized numeric terminal operations such as `sum()`, `average()`, and `summaryStatistics()`, which are unavailable on general reference streams without mapping.

### Mental Model
```
Stream<Integer> (Boxed references, pointer chasing):
[ Stream Pipeline ] -> [ Integer Ref ] -> ( Heap Object: 16-byte header + 4-byte int )
                       [ Integer Ref ] -> ( Heap Object: 16-byte header + 4-byte int )

IntStream (Contiguous primitives, direct cache lookup):
[ Stream Pipeline ] -> [ Primitive 1 ] -> [ Primitive 2 ] -> [ Primitive 3 ]
                       (Raw 32-bit values directly in CPU register/cache)
```

### Code Example
```java
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrimitiveStreamDemo {
    public static void main(String[] args) {
        // Primitive stream avoiding box/unbox cycles
        int sum = IntStream.rangeClosed(1, 5)
                           .sum();

        // Reference stream incurring boxing overhead
        int boxedSum = Stream.of(1, 2, 3, 4, 5)
                             .mapToInt(Integer::intValue) // Unboxing
                             .sum();

        System.out.println("Sum: " + sum + ", Boxed Sum: " + boxedSum);
        // Console Output:
        // Sum: 15, Boxed Sum: 15
    }
}
```

### Cause-Effect Chain
Use of generic Stream&lt;Integer&gt; &rarr; Object references stored on Heap &rarr; Garbage collection pressure and cache-miss overhead &rarr; Migrate to specialized IntStream &rarr; Operates directly on native 32-bit values &rarr; Zero boxing overhead & maximized performance
