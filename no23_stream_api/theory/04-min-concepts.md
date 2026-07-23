# Stream API - Part 4

## Learning Goal

This file covers a focused slice of **Stream API**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `min` |`min` — Returns the minimum element of the stream according to the provided Comparator. |
| `max` |`max` — max provides specific functionality and rules in Java development. |
| `reduce` |`reduce` — reduce provides specific functionality and rules in Java development. |
| `anyMatch` |`anyMatch` — anyMatch provides specific functionality and rules in Java development. |
| `allMatch` |`allMatch` — allMatch provides specific functionality and rules in Java development. |
| `noneMatch` |`noneMatch` — noneMatch provides specific functionality and rules in Java development. |
| `findFirst` |`findFirst` — findFirst provides specific functionality and rules in Java development. |
| `findAny` |`findAny` — findAny provides specific functionality and rules in Java development. |

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

Practical check:

- Define `min` in one sentence.
- Recognize `min` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `min`.

Tiny example or mental model:

- When reading code, ask: what does `min` change, allow, reject, or clarify?

### max

`max` — max provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Find the maximum element
Optional<Integer> maxVal = Stream.of(5, 2, 8, 1)
                                 .max(Integer::compareTo); // Returns Optional[8]
```

Practical check:

- Define `max` in one sentence.
- Recognize `max` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `max`.

Tiny example or mental model:

- When reading code, ask: what does `max` change, allow, reject, or clarify?

### reduce

`reduce` — reduce provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Reduce elements to a single value
int sum = Stream.of(1, 2, 3, 4)
                .reduce(0, (a, b) -> a + b); // Returns 10
```

Practical check:

- Define `reduce` in one sentence.
- Recognize `reduce` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `reduce`.

Tiny example or mental model:

- When reading code, ask: what does `reduce` change, allow, reject, or clarify?

### anyMatch

`anyMatch` — anyMatch provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Check if any element matches predicate (short-circuiting)
boolean hasEven = Stream.of(1, 3, 4, 5)
                        .anyMatch(n -> n % 2 == 0); // true
```

Practical check:

- Define `anyMatch` in one sentence.
- Recognize `anyMatch` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `anyMatch`.

Tiny example or mental model:

- When reading code, ask: what does `anyMatch` change, allow, reject, or clarify?

### allMatch

`allMatch` — allMatch provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Check if all elements match predicate (short-circuiting)
boolean allEven = Stream.of(2, 4, 6)
                        .allMatch(n -> n % 2 == 0); // true
```

Practical check:

- Define `allMatch` in one sentence.
- Recognize `allMatch` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `allMatch`.

Tiny example or mental model:

- When reading code, ask: what does `allMatch` change, allow, reject, or clarify?

### noneMatch

`noneMatch` — noneMatch provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Check if no elements match predicate (short-circuiting)
boolean noneNegative = Stream.of(1, 2, 3)
                             .noneMatch(n -> n < 0); // true
```

Practical check:

- Define `noneMatch` in one sentence.
- Recognize `noneMatch` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `noneMatch`.

Tiny example or mental model:

- When reading code, ask: what does `noneMatch` change, allow, reject, or clarify?

### findFirst

`findFirst` — findFirst provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Get the first element in encounter order (short-circuiting)
Optional<String> first = Stream.of("banana", "apple", "cherry")
                               .findFirst(); // Optional["banana"]
```

Practical check:

- Define `findFirst` in one sentence.
- Recognize `findFirst` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `findFirst`.

Tiny example or mental model:

- When reading code, ask: what does `findFirst` change, allow, reject, or clarify?

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

Practical check:

- Define `findAny` in one sentence.
- Recognize `findAny` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `findAny`.

Tiny example or mental model:

- When reading code, ask: what does `findAny` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
