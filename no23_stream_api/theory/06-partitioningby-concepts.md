# Stream API - Part 6

## Detailed Notes

### partitioningBy

`partitioningBy` — Collector that partitions stream elements into two groups based on a Predicate.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Partition elements into true/false lists based on a predicate
Map<Boolean, List<String>> partitioned = Stream.of("a", "bb", "c", "ddd")
                                               .collect(Collectors.partitioningBy(s -> s.length() > 1));
// Result: {false=["a", "c"], true=["bb", "ddd"]}
```

### counting

`counting` — counting provides specific functionality and rules in Java development.

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

### summarizingInt

`summarizingInt` — summarizingInt provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Gather statistics on int transformations (count, sum, min, average, max)
IntSummaryStatistics stats = Stream.of("a", "bb", "ccc")
                                   .collect(Collectors.summarizingInt(String::length));
System.out.println("Max: " + stats.getMax() + ", Average: " + stats.getAverage());
```

### mapping

Map does not inherit from Collection, so it does not have a direct `.stream()` method. However, you can create a Stream from a Map indirectly via `.keySet().stream()`, `.values().stream()`, or `.entrySet().stream()`.

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

### reducing

`reducing` — reducing provides specific functionality and rules in Java development.

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

## Why groupingBy and partitioningBy Serve Different Purposes

In the Java Collectors API, `partitioningBy` and `groupingBy` serve distinct classification strategies, differing in key types, optimization, and structure. The `partitioningBy` collector accepts a `Predicate` and divides the input stream into exactly two categories, returning a map with keys of type `Boolean` (`true` and `false`). Internally, it leverages a specialized, highly efficient binary-only collector that pre-populates a map with both boolean keys initialized to empty downstream structures.

Conversely, `groupingBy` is a general-purpose classifier accepting a `Function<T, K>`, mapping elements to arbitrary keys of type `K`. It dynamically constructs keys and groups items into a standard `HashMap` (by default) or a specified map type, allowing multiple arbitrary buckets based on the classifier's output.

### Mental Model
```
partitioningBy(s -> s.length() > 3):
[ "cat", "elephant" ]
        |
        +-----> [ true  ] ---> [ "elephant" ]
        +-----> [ false ] ---> [ "cat" ] (Fixed to true & false keys only)

groupingBy(String::length):
[ "a", "bb", "c" ]
        |
        +-----> [ Key: 1 ] ---> [ "a", "c" ]
        +-----> [ Key: 2 ] ---> [ "bb" ] (Dynamic, arbitrary keys created)
```

### Code Example
```java
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassificationDemo {
    public static void main(String[] args) {
        List<String> words = List.of("dog", "elephant", "cat");

        // partitioningBy: always exactly true and false keys
        Map<Boolean, List<String>> partition = words.stream()
            .collect(Collectors.partitioningBy(s -> s.length() > 3));

        // groupingBy: keys are dynamic and depend on classification function
        Map<Integer, List<String>> groups = words.stream()
            .collect(Collectors.groupingBy(String::length));

        System.out.println("Partition: " + partition);
        System.out.println("Groups: " + groups);
        // Console Output:
        // Partition: {false=[dog, cat], true=[elephant]}
        // Groups: {3=[dog, cat], 8=[elephant]}
    }
}
```

### Cause-Effect Chain
Need to categorize elements &rarr; Choose partitioningBy for simple boolean check / Choose groupingBy for complex classification &rarr; partitioningBy pre-populates Boolean.TRUE and Boolean.FALSE keys &rarr; groupingBy dynamically instantiates keys on demand &rarr; partitioningBy returns Map&lt;Boolean, List&lt;T&gt;&gt; / groupingBy returns Map&lt;K, List&lt;T&gt;&gt;
