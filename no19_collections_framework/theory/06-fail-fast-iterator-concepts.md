# Collections Framework - Part 6

## Learning Goal

This file covers a focused slice of **Collections Framework** including iterator behaviors (`Fail-fast` vs `Fail-safe`), structural modifications, and standard `Collections` utility class algorithms.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Fail-fast iterator` | Throws `ConcurrentModificationException` immediately if the collection is structurally modified during iteration (via methods other than the iterator's own). |
| `Fail-safe iterator` | Operates on a snapshot or weakly consistent view of the collection, allowing modifications during iteration without throwing exceptions. |
| `ConcurrentModificationException` | Runtime exception thrown when structural modification is detected on a collection during active iteration. |
| `Collections.sort` | Sorts a list in place in $O(N \log N)$ average/worst-case time. |
| `Collections.reverse` | Reverses the order of elements in a list. |
| `Collections.shuffle` | Randomly permutes elements in a list. |
| `Collections.max` | Returns the maximum element in a collection according to natural order or a custom comparator. |
| `Collections.min` | Returns the minimum element in a collection. |

## Detailed Notes

### Fail-Fast Iterator

Iterators for standard collections (like `ArrayList`, `HashSet`, `HashMap`) are **fail-fast**.
- **Mechanism**: The collection maintains a counter called `modCount` (modification count). When an iterator is created, it copies `modCount` into `expectedModCount`. On every `next()` or `remove()` invocation, the iterator compares these two counts. If they mismatch (meaning a modification happened outside the iterator), it immediately throws `ConcurrentModificationException`.

**Runnable Code Example (Fail-Fast Behavior):**
```java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FailFastDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("A", "B", "C"));
        Iterator<String> it = list.iterator();

        try {
            while (it.hasNext()) {
                String val = it.next();
                if (val.equals("B")) {
                    list.remove(val); // Modifies the collection structurally!
                }
            }
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Caught expected ConcurrentModificationException");
        }
    }
}
```

### Fail-Safe / Weakly Consistent Iterator

Iterators for concurrent collections (like `CopyOnWriteArrayList`, `ConcurrentHashMap`) do not throw `ConcurrentModificationException`.
- **Snapshot-based (e.g. `CopyOnWriteArrayList`)**: The iterator operates on a snapshot of the underlying array taken when the iterator was created. Modifications during iteration create new array copies, leaving the iterator's snapshot untouched.
- **Weakly consistent (e.g. `ConcurrentHashMap`)**: The iterator traverses elements as they exist, and may or may not reflect subsequent modifications, but will never crash.

**Runnable Code Example (Snapshot Iteration):**
```java
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class FailSafeDemo {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[]{"A", "B", "C"});
        Iterator<String> it = list.iterator();

        while (it.hasNext()) {
            String val = it.next();
            if (val.equals("B")) {
                list.remove(val); // Safe, makes a copy under the hood
            }
        }
        System.out.println("List after loop: " + list); // [A, C]
    }
}
```

### Collections Utility Class

`java.util.Collections` provides static algorithms that operate on collections.
- **`sort(List<T> list)`**: Sorts the list. Utilizes Timsort. Modifies list in place.
- **`reverse(List<?> list)`**: Reverses the order of list elements.
- **`shuffle(List<?> list)`**: Randomly reorders elements.
- **`max(Collection<? extends T> coll)`** / **`min(Collection<? extends T> coll)`**: Finds extreme elements based on sorting order.

**Runnable Code Example:**
```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsDemo {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(List.of(30, 10, 20));

        Collections.sort(numbers);
        System.out.println("Sorted: " + numbers); // [10, 20, 30]

        Collections.reverse(numbers);
        System.out.println("Reversed: " + numbers); // [30, 20, 10]

        Collections.shuffle(numbers);
        System.out.println("Max: " + Collections.max(numbers)); // 30
    }
}
```

---

## Case Study: Analyzing Fail-Fast vs Snapshot Iterator Performance

Let's write a performance test to observe the cost of modifications on standard collections (which throw exceptions unless utilizing `iterator.remove()`) versus concurrent snapshot collections (which copy the entire backing array on write).

```java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class IteratorBenchmark {
    public static void main(String[] args) {
        int count = 10_000;
        
        // 1. Benchmarking ArrayList using Iterator.remove()
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < count; i++) arrayList.add(i);

        long start = System.currentTimeMillis();
        Iterator<Integer> it1 = arrayList.iterator();
        while (it1.hasNext()) {
            if (it1.next() % 2 == 0) {
                it1.remove(); // Structural modification through iterator is fast and allowed
            }
        }
        long durationArrayList = System.currentTimeMillis() - start;

        // 2. Benchmarking CopyOnWriteArrayList using list.remove()
        List<Integer> cowList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < count; i++) cowList.add(i);

        start = System.currentTimeMillis();
        Iterator<Integer> it2 = cowList.iterator();
        while (it2.hasNext()) {
            int val = it2.next();
            if (val % 2 == 0) {
                cowList.remove(Integer.valueOf(val)); // Triggers array copying every time!
            }
        }
        long durationCowList = System.currentTimeMillis() - start;

        System.out.println("ArrayList (iterator.remove()): " + durationArrayList + " ms");
        System.out.println("CopyOnWriteArrayList (cowList.remove()): " + durationCowList + " ms");
    }
}
```

---

## Common Mistakes

### 1. Modifying a list in a for-each loop
The Java for-each loop internally uses an iterator. Calling `list.remove(item)` inside a for-each loop triggers a `ConcurrentModificationException` because the modification is structural and happens outside the iterator.
```java
// BUG: Will throw ConcurrentModificationException
for (String item : list) {
    if (item.equals("target")) {
        list.remove(item);
    }
}
```

### 2. High overhead of CopyOnWriteArrayList writes
Using `CopyOnWriteArrayList` in a write-intensive loop is a huge mistake. Since every write duplicates the backing array, large lists will cause memory churn and garbage collection pauses.

### 3. Assuming Iterator modification affects the original Collection in all list types
Some list views (like `List.of()` or `Collections.unmodifiableList()`) throw `UnsupportedOperationException` if you call `iterator.remove()`.

---

## Common Review Prompts

- How does a fail-fast iterator detect concurrent modifications? (By comparing the iterator's expectedModCount with the collection's modCount)
- What note type is returned by CopyOnWriteArrayList's iterator? (A snapshot array iterator that doesn't track modifications)
- Does Collections.sort modify the list in place or return a new list? (It modifies the list in place)

## Why Fail-Fast Iterators Throw ConcurrentModificationException

To prevent unpredictable runtime behavior and data corruption, Java's non-concurrent collections use a fail-fast iterator mechanism to detect concurrent modifications. The backing collection maintains an internal tracker called `modCount` (modification count), which increments with every structural modification such as additions, insertions, or removals. When an iterator is initialized, it caches this counter value into its own private field, `expectedModCount`. During subsequent operations like `next()`, `remove()`, or `forEachRemaining()`, the iterator compares the collection's live `modCount` with its stored `expectedModCount` value. If they mismatch, indicating that the collection was altered outside the iterator's control, the iterator immediately throws a `ConcurrentModificationException`. Conversely, fail-safe or weakly-consistent iterators (like that of `CopyOnWriteArrayList`) avoid this conflict entirely by iterating over an immutable snapshot of the backing array created at the moment of iterator construction, meaning modifications to the live collection target a separate copy and never interfere with the iterator's snapshot.

### Mental Model

Fail-fast iterator checks modification counts on every step:
```text
Collection State: modCount = 3
Iterator initialized -> expectedModCount = 3

1. Call iterator.next():
   Compare modCount (3) == expectedModCount (3) -> OK! Returns element.

2. Call collection.remove(x) (outside iterator):
   Collection increments modCount to 4.

3. Call iterator.next():
   Compare modCount (4) == expectedModCount (3) -> Mismatch detected!
   Action: Throw ConcurrentModificationException immediately.
```

### Code Example

```java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConcurrentModificationDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Python");
        list.add("Go");

        Iterator<String> iterator = list.iterator();

        try {
            while (iterator.hasNext()) {
                String language = iterator.next();
                if (language.equals("Python")) {
                    // Modifying the backing list directly, not via iterator.remove()
                    list.remove(language);
                }
            }
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Caught ConcurrentModificationException!");
            // Caught ConcurrentModificationException!
        }
    }
}
```

### Cause-Effect Chain

```text
Initialize Iterator → Cache modCount into expectedModCount → Modify collection structurally (add/remove) → modCount increments on backing collection → Iterator calls next() and compares modCount vs expectedModCount → Mismatch detected → Throw ConcurrentModificationException immediately
```

## Reference Links

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ConcurrentModificationException.html (ConcurrentModificationException API)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/CopyOnWriteArrayList.html (CopyOnWriteArrayList API documentation)
