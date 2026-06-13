# Collections Framework - Part 7

## Learning Goal

This file covers a focused slice of **Collections Framework** including wrapper collections (`unmodifiableList`, `synchronizedList`) and essential `Arrays` utility class algorithms.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Collections.unmodifiableList` | Returns a read-only view of a backing list. Attempts to modify it throw `UnsupportedOperationException`. Modifications to the backing list still propagate to the view. |
| `Collections.synchronizedList` | Returns a thread-safe list backed by the specified list. Iteration requires manual synchronization on the list object. |
| `Arrays.sort` | Sorts primitive or object arrays. Object arrays use Timsort; primitive arrays use Dual-Pivot Quicksort. |
| `Arrays.binarySearch` | Searches a sorted array. Returns index of match, or a negative value representing insertion point if not found. Undefined result if array is not sorted. |
| `Arrays.asList` | Returns a fixed-size list backed by the passed array. Modifications to elements write through to the array, but structural changes (add/remove) throw `UnsupportedOperationException`. |
| `Arrays.copyOf` | Copies the specified array, truncating or padding with default values as necessary. |
| `Arrays.equals` | Compares two 1D arrays for equality based on element contents. |
| `Arrays.deepEquals` | Recursively compares multi-dimensional arrays for deep equality. |

## Detailed Notes

### Unmodifiable vs Immutable Lists

`Collections.unmodifiableList(List)` returns an **unmodifiable view** of the backing list. It is not fully immutable because modifications to the original backing list are visible in the view. In contrast, `List.copyOf()` and `List.of()` return fully **immutable** lists that hold no reference to any original backing collections.

**Runnable Code Example:**
```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnmodifiableDemo {
    public static void main(String[] args) {
        List<String> backingList = new ArrayList<>();
        backingList.add("A");
        backingList.add("B");

        List<String> unmodifiableView = Collections.unmodifiableList(backingList);
        List<String> immutableList = List.copyOf(backingList);

        backingList.add("C"); // Modifying backing list

        System.out.println("Unmodifiable view: " + unmodifiableView); // [A, B, C]
        System.out.println("Immutable List: " + immutableList);       // [A, B]

        try {
            unmodifiableView.add("D"); // Throws exception
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify unmodifiable view directly");
        }
    }
}
```

### Collections.synchronizedList

Returns a synchronized (thread-safe) wrapper.
- **Iteration Trap**: Even though individual methods (`add`, `get`) are synchronized, iterating over the list is NOT thread-safe. You must manually synchronize on the wrapper list object during iteration.

**Runnable Code Example:**
```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedListDemo {
    public static void main(String[] args) {
        List<String> syncList = Collections.synchronizedList(new ArrayList<>());
        syncList.add("A");
        syncList.add("B");

        // Safe iteration requires manual synchronization
        synchronized (syncList) {
            for (String s : syncList) {
                System.out.println(s);
            }
        }
    }
}
```

### Arrays Utility Class

- **`Arrays.sort()`**: In-place sorting.
- **`Arrays.binarySearch()`**: Requires the array to be sorted.
  - **Rule**: If the element is found, it returns the index. If not found, it returns `-(insertion point) - 1`.
- **`Arrays.asList()`**: Wraps an array into a fixed-size list.
- **`Arrays.equals()` vs `Arrays.deepEquals()`**: `equals()` compares reference elements of 1D arrays; `deepEquals()` recursively compares sub-arrays in multi-dimensional arrays.

**Runnable Code Example:**
```java
import java.util.Arrays;
import java.util.List;

public class ArraysDemo {
    public static void main(String[] args) {
        // 1. Arrays.asList fixed-size list behavior
        String[] arr = {"One", "Two"};
        List<String> list = Arrays.asList(arr);
        list.set(0, "Updated"); // Writes through to backing array
        System.out.println("Array value: " + arr[0]); // Updated

        // 2. Binary search on sorted array
        int[] numbers = {10, 20, 30, 40};
        int index = Arrays.binarySearch(numbers, 30);
        System.out.println("Index of 30: " + index); // 2

        // 3. Equals vs Deep Equals
        int[][] matrix1 = {{1, 2}, {3, 4}};
        int[][] matrix2 = {{1, 2}, {3, 4}};
        System.out.println("Equals: " + Arrays.equals(matrix1, matrix2)); // false (checks 1D reference identity)
        System.out.println("Deep Equals: " + Arrays.deepEquals(matrix1, matrix2)); // true (checks nested contents)
    }
}
```

---

## Case Study: Evaluating Collections.unmodifiableList vs List.copyOf vs List.of

Let's look at reference behavior, null allowance, and performance characteristics of these unmodifiable/immutable factories.

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnmodifiableComparison {
    public static void main(String[] args) {
        List<String> original = new ArrayList<>();
        original.add("A");
        original.add(null); // original has null

        // 1. Collections.unmodifiableList allows nulls because it's a wrapper view
        List<String> view = Collections.unmodifiableList(original);
        System.out.println("View size: " + view.size()); // 2

        // 2. List.copyOf throws NullPointerException if collection contains null
        try {
            List.copyOf(original);
        } catch (NullPointerException e) {
            System.out.println("List.copyOf rejected list containing null");
        }

        // 3. List.of rejects null elements directly
        try {
            List.of("A", null);
        } catch (NullPointerException e) {
            System.out.println("List.of rejected direct null insertion");
        }

        // 4. Memory footprint and optimization
        // List.copyOf of an already immutable list returned by List.copyOf/List.of
        // will return the SAME reference (no duplication).
        List<String> immutable1 = List.of("X", "Y");
        List<String> immutable2 = List.copyOf(immutable1);
        System.out.println("Same reference: " + (immutable1 == immutable2)); // true!
    }
}
```

---

## Common Mistakes

### 1. Adding/removing elements from an `Arrays.asList` list
Since the list returned by `Arrays.asList` is fixed-size, calling `add()` or `remove()` throws `UnsupportedOperationException`. To get a fully mutable copy, wrap it: `new ArrayList<>(Arrays.asList(arr))`.

### 2. Binary search on unsorted arrays
Calling `Arrays.binarySearch()` on an unsorted array returns unpredictable results. Always sort the array first.

### 3. Iterating synchronized lists without manual locking
Writing concurrent loops over `Collections.synchronizedList()` without enclosing in a `synchronized(list)` block is a bug that leads to race conditions or `ConcurrentModificationException` if another thread modifies the list during traversal.

---

## Common Review Prompts

- What happens if you call `add()` on an `Arrays.asList()` list? (UnsupportedOperationException)
- What is the difference between `Arrays.equals` and `Arrays.deepEquals`? (equals is for 1D arrays, deepEquals recursively compares multi-dimensional array structures)
- Does List.copyOf copy the elements if the source list is already an immutable list? (No, it returns the same instance as an optimization)
