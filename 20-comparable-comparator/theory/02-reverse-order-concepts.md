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

## Common Mistakes with Reversal and Nulls

1. **Incorrectly reversing primitive comparators**: Reversing a primitive comparator using custom lambda subtraction `(a, b) -> b - a` is highly prone to overflow bugs (e.g. `Integer.MIN_VALUE` vs `1`). Always use `Comparator.reverseOrder()` or `Comparator.comparingInt(...).reversed()`.
2. **Double Reversal**: Using `comparator.reversed().reversed()` simply returns the original ordering but adds execution overhead due to the wrapping layers.
3. **Implicit unboxing NPE**: When sorting wrappers with `nullsLast` or `nullsFirst`, ensure the extractor returns the wrapper object (like `Integer`) and not the primitive (`int`), otherwise the JVM will attempt to auto-unbox the `null` to a primitive before passing it, causing a `NullPointerException`.


## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
