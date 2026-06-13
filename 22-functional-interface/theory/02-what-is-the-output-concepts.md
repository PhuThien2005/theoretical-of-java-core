# Functional Interface - Part 2

## Learning Goal

This file covers a focused slice of **Functional Interface**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `What is the output?` | What is the output is a key question for understanding Functional Interface. |
| `When to use which interface?` |When to use which interface? is a specific concept in Functional Interface; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### What is the output?

What is the output is a key question for understanding Functional Interface.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `What is the output?` in one sentence.
- Recognize `What is the output?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `What is the output?`.

Tiny example or mental model:

- When reading code, ask: what does `What is the output?` change, allow, reject, or clarify?

#### Detailed Explanation and Code Examples
The return type (output) of a functional interface's abstract method determines how it can be utilized in code.
- `void`: Used for side effects (e.g. `Consumer`).
- `boolean`: Used for filtering and checking conditions (e.g. `Predicate`).
- A generic/primitive type: Used for mappings and computations (e.g. `Function`, `Supplier`).

```java
import java.util.function.*;

public class OutputExample {
    public static void main(String[] args) {
        // Output is void: side effects only
        Consumer<String> consumer = s -> System.out.println("Processing " + s);
        
        // Output is boolean: checks conditions
        Predicate<Integer> isPositive = n -> n > 0;
        
        // Output is a type: returns a value
        Supplier<String> supplier = () -> "Hello World";
        Function<String, Integer> lengthMapper = String::length;
    }
}
```

---

### When to use which interface?

When to use which interface? is a specific concept in Functional Interface; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `When to use which interface?` in one sentence.
- Recognize `When to use which interface?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `When to use which interface?`.

Tiny example or mental model:

- When reading code, ask: what does `When to use which interface?` change, allow, reject, or clarify?

#### Decision Matrix
Use this guide to pick the appropriate functional interface based on input count and output type:

| Inputs | Output | Standard Interface | Primitive Example | Bi-Variant |
|--------|--------|--------------------|-------------------|------------|
| 0 | Type `T` | `Supplier<T>` | `IntSupplier` | N/A |
| 1 (`T`) | `void` | `Consumer<T>` | `DoubleConsumer` | `BiConsumer<T, U>` |
| 1 (`T`) | `boolean`| `Predicate<T>` | `LongPredicate` | `BiPredicate<T, U>`|
| 1 (`T`) | Type `R` | `Function<T, R>` | `ToIntFunction<T>` | `BiFunction<T, U, R>`|
| 1 (`T`) | Type `T` | `UnaryOperator<T>`| `IntUnaryOperator` | N/A |
| 2 (`T`, `T`)| Type `T` | `BinaryOperator<T>`| `IntBinaryOperator`| N/A |

---

### Case Study: Designing a custom functional interface for a processing pipeline with default chaining methods

In real-world applications, you may need a custom processing pipeline that goes beyond the standard JDK functional interfaces. Below is a complete implementation of a custom `DataProcessor<T>` functional interface featuring default chaining methods (`andThen` and `compose`).

```java
import java.util.Objects;

@FunctionalInterface
public interface DataProcessor<T> {
    
    /**
     * Processes the input data and returns the processed result.
     */
    T process(T data);

    /**
     * Returns a chained DataProcessor that first applies this processor,
     * and then applies the 'after' processor to the result.
     */
    default DataProcessor<T> andThen(DataProcessor<T> after) {
        Objects.requireNonNull(after);
        return (T data) -> after.process(this.process(data));
    }

    /**
     * Returns a chained DataProcessor that first applies the 'before' processor,
     * and then applies this processor to the result.
     */
    default DataProcessor<T> compose(DataProcessor<T> before) {
        Objects.requireNonNull(before);
        return (T data) -> this.process(before.process(data));
    }
}
```

#### Pipeline Demonstration
Here is how we use the custom `DataProcessor` to clean, normalize, and format a raw text input:

```java
public class PipelineDemo {
    public static void main(String[] args) {
        // Define individual stages of the processing pipeline
        DataProcessor<String> stripWhitespace = String::strip;
        DataProcessor<String> toLowerCase = String::toLowerCase;
        DataProcessor<String> sanitize = s -> s.replaceAll("[^a-zA-Z0-9 ]", "");
        DataProcessor<String> formatOutput = s -> "[" + s.replace(" ", "_") + "]";

        // Chain the processors together using andThen
        DataProcessor<String> textPipeline = stripWhitespace
                .andThen(toLowerCase)
                .andThen(sanitize)
                .andThen(formatOutput);

        String rawInput = "   Hello, World! Java 21 is great.   ";
        String processed = textPipeline.process(rawInput);
        
        System.out.println("Raw: '" + rawInput + "'");
        System.out.println("Processed: " + processed);
        // Output -> Processed: [hello_world_java_21_is_great]
    }
}
```

---

## Common Mistakes

### 1. Ambiguity in Chaining Order
Confusing `andThen` with `compose` can lead to processing pipelines executing in the wrong sequence, resulting in corrupted or unexpected data output. Always remember:
- `a.andThen(b)` executes `a` first, then `b`.
- `a.compose(b)` executes `b` first, then `a`.

### 2. Passing null into default chaining methods
Chaining methods in both custom and JDK functional interfaces (like `Function.andThen`) will throw a `NullPointerException` at composition time (not execution time) if `null` is passed as the chained argument.

---

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
