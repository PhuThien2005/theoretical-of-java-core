# Lambda Expression - Part 2

## Learning Goal

This file covers a focused slice of **Lambda Expression**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Lambda with Collection` | A collection is an object that groups multiple elements under a common API. |
| `Lambda with Thread` | A lambda expression is a compact function-like block used where a functional interface is expected. |
| `Lambda with Comparator` | Comparator defines external custom ordering for objects. |

## Detailed Notes

### Lambda with Collection

A collection is an object that groups multiple elements under a common API.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Lambda with Collection` in one sentence.
- Recognize `Lambda with Collection` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Lambda with Collection`.

Tiny example or mental model:

- `n -> n > 0` is a lambda used as a predicate.

#### Code Example: Collection methods accepting Lambdas
```java
java.util.List<String> list = new java.util.ArrayList<>(java.util.List.of("apple", "banana", "cherry"));

// 1. Iteration with Consumer
list.forEach(item -> System.out.println(item));

// 2. Inline filtering with Predicate
list.removeIf(item -> item.startsWith("b")); // removes "banana"

// 3. Inline replacing with UnaryOperator
list.replaceAll(item -> item.toUpperCase()); // replaces remaining with "APPLE", "CHERRY"
```

#### Common Mistake: Modifying Unmodifiable Collections at Runtime
Methods like `List.of()`, `Map.of()`, or `Collections.unmodifiableList()` produce unmodifiable collections. Passing a lambda to `removeIf()` or `replaceAll()` on these lists compiles fine but throws `UnsupportedOperationException` at runtime.
```java
java.util.List<String> fixedList = java.util.List.of("a", "b");
// Throws UnsupportedOperationException at runtime!
fixedList.removeIf(s -> s.equals("a")); 
```

### Lambda with Thread

A lambda expression is a compact function-like block used where a functional interface is expected.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Practical check:

- Define `Lambda with Thread` in one sentence.
- Recognize `Lambda with Thread` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Lambda with Thread`.

Tiny example or mental model:

- `n -> n > 0` is a lambda used as a predicate.

#### Code Example: Running Tasks Asynchronously
Because `Runnable` is a functional interface (having only the `run()` abstract method), we can use lambda expressions to define thread tasks or executor service submits.
```java
// 1. Thread constructor
new Thread(() -> System.out.println("Async run")).start();

// 2. ExecutorService submission
java.util.concurrent.ExecutorService executor = java.util.concurrent.Executors.newSingleThreadExecutor();
executor.submit(() -> System.out.println("Executor task"));
executor.shutdown();
```

### Lambda with Comparator

Comparator defines external custom ordering for objects.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `Lambda with Comparator` in one sentence.
- Recognize `Lambda with Comparator` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Lambda with Comparator`.

Tiny example or mental model:

- `n -> n > 0` is a lambda used as a predicate.

#### Code Example: Custom sorting logic
```java
java.util.List<String> names = new java.util.ArrayList<>(java.util.List.of("Charles", "Bob", "Alice"));

// Sorting using custom lambda comparator (sorts by length)
names.sort((s1, s2) -> Integer.compare(s1.length(), s2.length()));

// Sorting using Comparator utility methods and method references
names.sort(java.util.Comparator.comparingInt(String::length));
```

#### Common Mistake: Integer Overflow in Subtraction Comparator
A classic mistake when comparing integer values is using subtraction instead of `Integer.compare()`.
```java
// Compile-safe but prone to integer overflow bugs!
names.sort((s1, s2) -> s1.length() - s2.length()); 
// If s1.length() is Integer.MAX_VALUE and s2.length() is -1, subtraction overflows!
// Correct approach is to always use Integer.compare(x, y).
```

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
