# Stream API - Part 3

## Detailed Notes

### peek

`peek` — Performs an action on each element as elements are consumed from the stream, mainly for debugging.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Inspecting elements as they flow through the pipeline (for debugging)
List<String> result = Stream.of("one", "two", "three")
                            .filter(s -> s.length() > 3)
                            .peek(s -> System.out.println("Filtered: " + s))
                            .map(String::toUpperCase)
                            .peek(s -> System.out.println("Mapped: " + s))
                            .collect(Collectors.toList());
```

### limit

`limit` — limit provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Truncate stream to a maximum size
Stream.of(1, 2, 3, 4, 5)
      .limit(3)
      .forEach(System.out::print); // Prints: 123
```

### skip

`skip` — skip provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Skip the first N elements
Stream.of(1, 2, 3, 4, 5)
      .skip(2)
      .forEach(System.out::print); // Prints: 345
```

### Terminal operations:

Terminal operations is a group of related rules in Stream API that groups several related details.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Terminal operations execute the pipeline and close the stream
long count = Stream.of(1, 2, 3).count();
```

### forEach

`forEach` — forEach provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Perform action on each element
Stream.of("a", "b").forEach(System.out::print); // Output: ab
```

### collect

`collect` — collect provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Accumulate elements into a mutable container
List<String> list = Stream.of("a", "b").collect(Collectors.toList());
```

### toList

You can easily create a Stream from a List (or any Collection) by calling the default `.stream()` method provided on the Collection interface.

#### Code Example
```java
// toList() (Java 16+) returns an unmodifiable List directly
List<String> unmodifiableList = Stream.of("a", "b").toList();
```

### count

`count` — count provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Count elements
long total = Stream.of(1, 2, 3).count(); // 3
```

## Common Mistakes

### 1. Assuming peek() always executes
Because streams are lazy, intermediate operations like `peek()` will not run unless a terminal operation is called. Additionally, JDK optimizations might skip `peek()` if the terminal operation (like `count()`) can be evaluated without traversing elements.
```java
// peek() does NOT execute here (no terminal operation)
Stream.of("a", "b").peek(System.out::println); 

// peek() might be optimized out in Java 9+ because map/peek doesn't affect stream size
long size = Stream.of("a", "b")
                  .peek(System.out::println) // Might print nothing!
                  .count();
```

### 2. Modifying elements inside forEach on parallel streams
Modifying shared non-thread-safe state from a parallel stream's `forEach` leads to race conditions.
```java
List<Integer> list = new ArrayList<>(); // Non-thread-safe
List.of(1, 2, 3, 4).parallelStream().forEach(list::add); // Race condition!
```

## Why peek() Should Not Be Used for State Mutation

The API specification for `Stream.peek()` explicitly states that its primary purpose is to support debugging, allowing you to observe elements as they flow past a certain point in a pipeline. Using `peek()` to mutate the state of elements or external variables is highly discouraged and error-prone because the stream implementation is free to optimize away intermediate pipeline steps. For example, if a terminal operation like `count()` is used, modern JDK versions (Java 9+) can determine the count directly from the stream source description without traversing the pipeline, meaning `peek()` will never execute. Furthermore, in parallel stream pipelines, invoking side-effects inside `peek()` introduces data races and thread-safety violations unless complex synchronization is added. State mutation inside `peek()` breaks the fundamental design goal of functional stream pipelines, which should remain pure, side-effect-free, and deterministic.

### Mental Model
```
Stream Source (size known) -> peek(mutate) -> count()
                                  |
                                  v
                    [ JVM Count Optimization ]
    (Source size is queried directly; pipeline traversal skipped)
                                  |
                                  v
                    peek() is NEVER executed!
```

### Code Example
```java
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PeekMutationDemo {
    public static void main(String[] args) {
        List<String> mutatedList = new ArrayList<>();
        
        // DANGEROUS: Using peek to mutate external state
        long totalCount = Stream.of("a", "b", "c")
                                .peek(mutatedList::add)
                                .count();
        
        System.out.println("Total Count: " + totalCount);
        System.out.println("Mutated List Size: " + mutatedList.size());
        // Console Output (Java 9+):
        // Total Count: 3
        // Mutated List Size: 0
    }
}
```

### Cause-Effect Chain
State mutation inside peek() &rarr; Execution depends on pipeline traversal &rarr; Terminal operation optimized (e.g., count() queries source directly) &rarr; Pipeline traversal skipped &rarr; Mutation logic never runs &rarr; External state inconsistent and bugs introduced
