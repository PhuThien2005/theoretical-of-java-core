# Stream API - Part 3

## Learning Goal

This file covers a focused slice of **Stream API**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `peek` |`peek` — Performs an action on each element as elements are consumed from the stream, mainly for debugging. |
| `limit` |`limit` — limit provides specific functionality and rules in Java development. |
| `skip` |`skip` — skip provides specific functionality and rules in Java development. |
| `Terminal operations:` | Terminal operations is a group of related rules in Stream API that groups several related details. |
| `forEach` |`forEach` — forEach provides specific functionality and rules in Java development. |
| `collect` |`collect` — collect provides specific functionality and rules in Java development. |
| `toList` | A List is an ordered collection that can contain duplicates and supports positional access. |
| `count` |`count` — count provides specific functionality and rules in Java development. |

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

Practical check:

- Define `peek` in one sentence.
- Recognize `peek` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `peek`.

Tiny example or mental model:

- When reading code, ask: what does `peek` change, allow, reject, or clarify?

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

Practical check:

- Define `limit` in one sentence.
- Recognize `limit` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `limit`.

Tiny example or mental model:

- When reading code, ask: what does `limit` change, allow, reject, or clarify?

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

Practical check:

- Define `skip` in one sentence.
- Recognize `skip` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `skip`.

Tiny example or mental model:

- When reading code, ask: what does `skip` change, allow, reject, or clarify?

### Terminal operations:

Terminal operations is a group of related rules in Stream API that groups several related details.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Terminal operations execute the pipeline and close the stream
long count = Stream.of(1, 2, 3).count();
```

Practical check:

- Define `Terminal operations:` in one sentence.
- Recognize `Terminal operations:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Terminal operations:`.

Tiny example or mental model:

- When reading code, ask: what does `Terminal operations:` change, allow, reject, or clarify?

### forEach

`forEach` — forEach provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Perform action on each element
Stream.of("a", "b").forEach(System.out::print); // Output: ab
```

Practical check:

- Define `forEach` in one sentence.
- Recognize `forEach` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `forEach`.

Tiny example or mental model:

- When reading code, ask: what does `forEach` change, allow, reject, or clarify?

### collect

`collect` — collect provides specific functionality and rules in Java development.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Code Example
```java
// Accumulate elements into a mutable container
List<String> list = Stream.of("a", "b").collect(Collectors.toList());
```

Practical check:

- Define `collect` in one sentence.
- Recognize `collect` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `collect`.

Tiny example or mental model:

- When reading code, ask: what does `collect` change, allow, reject, or clarify?

### toList

A List is an ordered collection that can contain duplicates and supports positional access.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

#### Code Example
```java
// toList() (Java 16+) returns an unmodifiable List directly
List<String> unmodifiableList = Stream.of("a", "b").toList();
```

Practical check:

- Define `toList` in one sentence.
- Recognize `toList` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `toList`.

Tiny example or mental model:

- `List<String> names = new ArrayList<>();` stores ordered elements.

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

Practical check:

- Define `count` in one sentence.
- Recognize `count` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `count`.

Tiny example or mental model:

- When reading code, ask: what does `count` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?

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
