# Stream API - Part 4

## Detailed Notes

### min

`min` — Returns the minimum element of the stream according to the provided Comparator.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Find the minimum element
Optional<Integer> minVal = Stream.of(5, 2, 8, 1)
                                 .min(Integer::compareTo); // Returns Optional[1]
```

### max

`max` — max provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Find the maximum element
Optional<Integer> maxVal = Stream.of(5, 2, 8, 1)
                                 .max(Integer::compareTo); // Returns Optional[8]
```

### reduce

`reduce` — reduce provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Reduce elements to a single value
int sum = Stream.of(1, 2, 3, 4)
                .reduce(0, (a, b) -> a + b); // Returns 10
```

### anyMatch

`anyMatch` — anyMatch provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Check if any element matches predicate (short-circuiting)
boolean hasEven = Stream.of(1, 3, 4, 5)
                        .anyMatch(n -> n % 2 == 0); // true
```

### allMatch

`allMatch` — allMatch provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Check if all elements match predicate (short-circuiting)
boolean allEven = Stream.of(2, 4, 6)
                        .allMatch(n -> n % 2 == 0); // true
```

### noneMatch

`noneMatch` — noneMatch provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Check if no elements match predicate (short-circuiting)
boolean noneNegative = Stream.of(1, 2, 3)
                             .noneMatch(n -> n < 0); // true
```

### findFirst

`findFirst` — findFirst provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Get the first element in encounter order (short-circuiting)
Optional<String> first = Stream.of("banana", "apple", "cherry")
                               .findFirst(); // Optional["banana"]
```

### findAny

`findAny` — findAny provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Get any element, optimized for parallel streams (short-circuiting)
Optional<String> any = Stream.of("banana", "apple", "cherry")
                             .findAny(); // Returns any elements
```

## Common Mistakes

### 1. Identity value violation in reduce()
The identity value in `reduce(identity, accumulator)` must be an actual identity for the accumulator function (i.e. `accumulator.apply(identity, x) == x` for all `x`). If it is not, the reduction produces wrong results, especially when run in parallel.
```java
// Incorrect identity: using 10 for sum
// In sequential: 10 + 1 + 2 + 3 = 16
// In parallel: (10 + 1) + (10 + 2) + (10 + 3) = 36!
int sum = List.of(1, 2, 3).parallelStream()
              .reduce(10, Integer::sum); 
```

### 2. Assuming findFirst() and findAny() perform identically on parallel streams
`findFirst()` must strictly respect the encounter order of the stream. In a parallel stream, coordinating thread outputs to return the first element is expensive. `findAny()` returns the first element computed by any thread, which is much faster.
```java
// Slow in parallel because it forces encounter order tracking
Optional<Integer> first = List.of(1, 2, 3, 4, 5).parallelStream()
                              .filter(n -> n > 3)
                              .findFirst();

// Fast in parallel (returns any element > 3 as soon as found)
Optional<Integer> any = List.of(1, 2, 3, 4, 5).parallelStream()
                             .filter(n -> n > 3)
                             .findAny();
```
