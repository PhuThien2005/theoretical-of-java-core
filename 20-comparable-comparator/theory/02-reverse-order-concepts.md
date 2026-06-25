# Comparable and Comparator - Part 2

## Learning Goal

This file covers a focused slice of **Comparable and Comparator**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Reverse order` |Reverse order is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Null handling:` | Null handling is a group of related rules in Comparable and Comparator that groups several related details. |
| `nullsFirst` |nullsFirst is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `nullsLast` |nullsLast is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### Reverse order

Reverse order allows sorting elements in the opposite of their normal sequence.

#### Enriched Explanation
There are two primary ways to reverse sorting orders in Java 8+:
1. `Comparator.reverseOrder()`: A static utility method that returns a comparator imposing the reverse of the **natural ordering** on `Comparable` objects.
2. `comparator.reversed()`: A default method on an existing `Comparator` instance that returns a new comparator imposing the reverse order of the original comparator.

#### Code Example
```java
List<String> list = new ArrayList<>(List.of("A", "C", "B"));
// Reversing natural ordering
list.sort(Comparator.reverseOrder()); // [C, B, A]

// Reversing custom ordering (by length)
Comparator<String> lenComp = Comparator.comparingInt(String::length);
list.sort(lenComp.reversed());
```

#### Gotchas & Failure Modes
- **Chaining Reversal Trap**: A very common bug is reversing a chained comparator incorrectly.
  ```java
  // Trapped: This reverses the ENTIRE chain (both department and salary)
  Comparator<Employee> comp = Comparator.comparing(Employee::getDepartment)
                                        .thenComparingDouble(Employee::getSalary)
                                        .reversed();
  ```
  If you only want the salary sorted in descending order while department remains ascending, apply the reversal to the salary comparator specifically:
  ```java
  // Corrected: Only salary is reversed
  Comparator<Employee> comp = Comparator.comparing(Employee::getDepartment)
                                        .thenComparing(Comparator.comparingDouble(Employee::getSalary).reversed());
  ```

### Null handling:

Null handling is crucial because default comparison operations throw NullPointerException when encountering null values.

#### Enriched Explanation
By default, comparing `null` values using `compareTo` or standard comparators throws a `NullPointerException`. Java 8 introduced utility decorators `Comparator.nullsFirst` and `Comparator.nullsLast` to make any comparator null-safe by specifying whether nulls should be sorted to the beginning or the end of the collection.

#### Code Example
```java
List<String> list = Arrays.asList("Apple", null, "Banana");
// Without null handling, this throws NPE:
// list.sort(Comparator.naturalOrder()); 

// Safe null handling:
list.sort(Comparator.nullsFirst(Comparator.naturalOrder())); // [null, Apple, Banana]
```

#### Gotchas & Failure Modes
- **Null Downstream Comparator**: If you pass `null` to `nullsFirst` or `nullsLast` (e.g. `Comparator.nullsFirst(null)`), all non-null values will be considered equal, resulting in their relative order being preserved (or not sorted at all), while null values are pushed to the boundary.

### nullsFirst

nullsFirst is a utility to sort null values before non-null values.

#### Enriched Explanation
`Comparator.nullsFirst(Comparator<? super T> downstream)` returns a null-friendly comparator that considers `null` values to be less than non-null values. If both compared elements are non-null, it delegates to the provided `downstream` comparator.

#### Code Example
```java
List<Integer> list = Arrays.asList(5, null, 2, null, 8);
list.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
System.out.println(list); // [null, null, 2, 5, 8]
```

#### Gotchas & Failure Modes
- **NPE on Un-Decorated Extraction**: If you use `Comparator.comparing(Employee::getName)` and a name is `null`, `nullsFirst` placed around the outer comparator will NOT prevent NPE if the key extractor itself returns `null`. You must make the key comparison null-safe:
  ```java
  // Safe way to handle null names:
  Comparator<Employee> comp = Comparator.comparing(
      Employee::getName, 
      Comparator.nullsFirst(Comparator.naturalOrder())
  );
  ```

### nullsLast

nullsLast is a utility to sort null values after non-null values.

#### Enriched Explanation
`Comparator.nullsLast(Comparator<? super T> downstream)` returns a null-friendly comparator that considers `null` values to be greater than non-null values. If both elements are non-null, it delegates to the `downstream` comparator.

#### Code Example
```java
List<Integer> list = Arrays.asList(5, null, 2, null, 8);
list.sort(Comparator.nullsLast(Comparator.naturalOrder()));
System.out.println(list); // [2, 5, 8, null, null]
```

#### Gotchas & Failure Modes
- **Wrapper vs Primitive Boxing**: If the downstream comparator uses primitive comparisons (like `comparingInt`), sorting a list containing `null` wrapper objects can cause a `NullPointerException` during auto-unboxing before the comparator can run. Ensure your collection holds objects, and use object-based comparators with null-safety wrapping.

## Why Java Uses Dual-Pivot Quicksort for Primitives but TimSort for Objects

Java segregates its array sorting algorithms based on whether the input contains primitive values or object references. Primitives are pure value types without a distinct identity, meaning that sorting stability—preserving the relative input order of equal elements—is irrelevant because one primitive `7` is completely indistinguishable from another. To optimize performance, the JDK uses **Dual-Pivot Quicksort** for primitive arrays because it is highly cache-efficient, requires small $O(\log N)$ auxiliary stack space, and executes faster on raw memory. Conversely, objects have distinct identities, references, and attributes, meaning that sorting stability is mandatory to ensure that sorting elements by a secondary criteria does not scramble the ordering established by a primary sorting pass. Therefore, Java uses **TimSort** (a hybrid of merge sort and insertion sort) for object arrays, which guarantees stable $O(N \log N)$ worst-case performance and adapts to pre-sorted runs efficiently, though it requires $O(N)$ auxiliary storage to manage runs.

### Mental Model: Stable Sort (TimSort) vs Unstable Sort (Quicksort)
Suppose we have a list of cards and want to sort them by value.
Input: `[5♣, 5♥]` where `5♣` appears before `5♥`.

```text
Stable Sort (TimSort):     [5♣, 5♥] (relative order of equal values is guaranteed to be preserved)
Unstable Sort (Quicksort): [5♥, 5♣] (equal values may have their relative order swapped)
```

| Metric | Dual-Pivot Quicksort (Primitives) | TimSort (Objects) |
|---|---|---|
| **Stability** | Unstable | Stable |
| **Worst-case Time** | $O(N^2)$ (rare) / $O(N \log N)$ | $O(N \log N)$ |
| **Best-case Time** | $O(N)$ (if already sorted or uniform) | $O(N)$ (if elements are in pre-sorted runs) |
| **Space Complexity** | $O(\log N)$ (in-place recursion stack) | $O(N)$ (requires temporary runs array) |

### Code Example: Illustrating the Importance of Stable Sorting for Objects
```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortingStabilityDemo {
    public static class LogEntry {
        final String severity;
        final int timestamp;

        public LogEntry(String severity, int timestamp) {
            this.severity = severity;
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return severity + "@" + timestamp;
        }
    }

    public static void main(String[] args) {
        List<LogEntry> logs = new ArrayList<>(List.of(
            new LogEntry("ERROR", 100),
            new LogEntry("INFO",  101),
            new LogEntry("ERROR", 102),
            new LogEntry("INFO",  103)
        ));

        // 1. Sort by timestamp (already in order)
        logs.sort(Comparator.comparingInt(l -> l.timestamp));

        // 2. Sort by severity. TimSort guarantees that for the same severity,
        // the original timestamp order is preserved.
        logs.sort(Comparator.comparing(l -> l.severity));
        System.out.println(logs);
        // Output: [ERROR@100, ERROR@102, INFO@101, INFO@103]
        // Note: ERROR@100 still precedes ERROR@102, and INFO@101 precedes INFO@103.
    }
}
```

### Cause-Effect Chain
Sorting array of primitives $\rightarrow$ Individual elements are pure values without identity $\rightarrow$ Stability is unnecessary $\rightarrow$ Use Dual-Pivot Quicksort to maximize CPU cache locality and avoid auxiliary heap memory allocations.
Sorting array of objects $\rightarrow$ Individual elements are references where relative order must be preserved $\rightarrow$ Stability is required for correct multi-key sorting $\rightarrow$ Use TimSort to guarantee a stable sort at the cost of allocating extra run-tracking memory.

## Common Mistakes with Reversal and Nulls

1. **Incorrectly reversing primitive comparators**: Reversing a primitive comparator using custom lambda subtraction `(a, b) -> b - a` is highly prone to overflow bugs (e.g. `Integer.MIN_VALUE` vs `1`). Always use `Comparator.reverseOrder()` or `Comparator.comparingInt(...).reversed()`.
2. **Double Reversal**: Using `comparator.reversed().reversed()` simply returns the original ordering but adds execution overhead due to the wrapping layers.
3. **Implicit unboxing NPE**: When sorting wrappers with `nullsLast` or `nullsFirst`, ensure the extractor returns the wrapper object (like `Integer`) and not the primitive (`int`), otherwise the JVM will attempt to auto-unbox the `null` to a primitive before passing it, causing a `NullPointerException`.


## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?

## Reference Links

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Comparator.html (Comparator nullsFirst/nullsLast specification)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Arrays.html#sort(int%5B%5D) (Dual-Pivot Quicksort specification)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Arrays.html#sort(java.lang.Object%5B%5D) (TimSort specification)
