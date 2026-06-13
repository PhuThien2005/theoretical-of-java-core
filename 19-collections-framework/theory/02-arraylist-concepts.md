# Collections Framework - Part 2

## Learning Goal

This file covers a focused slice of **Collections Framework**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `ArrayList` | A List is an ordered collection that can contain duplicates and supports positional access. |
| `LinkedList` | A List is an ordered collection that can contain duplicates and supports positional access. |
| `Vector` |Vector is a specific concept in Collections Framework; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Stack` | Stack stores method frames, local variables, and call flow for each thread. |
| `Comparing ArrayList and LinkedList` | A List is an ordered collection that can contain duplicates and supports positional access. |
| `When to use List?` | A List is an ordered collection that can contain duplicates and supports positional access. |
| `HashSet` | A Set is a collection that rejects duplicates according to equality rules. |
| `LinkedHashSet` | A Set is a collection that rejects duplicates according to equality rules. |

## Detailed Notes

### ArrayList

An `ArrayList` is a resizable-array implementation of the `List` interface. 
- **Internal Structure**: Backed by a standard Java array. When the array is full, it dynamically grows by creating a new array (usually `1.5x` the old capacity) and copying elements over via `System.arraycopy()`.
- **Complexity**: O(1) random access by index. Adding/removing elements at the end is amortized O(1), but adding/removing in the middle requires shifting elements, which is O(N).

### LinkedList

A `LinkedList` is a doubly-linked list implementation of the `List` and `Deque` interfaces.
- **Internal Structure**: Composed of nodes, where each node has a reference to the element, the `next` node, and the `prev` node.
- **Complexity**: O(N) lookup time, as it must traverse from head or tail to locate the index. Insertion and removal at the head/tail are O(1), and insertion/removal at a known iterator position is O(1).

### Vector

`Vector` is a legacy, thread-safe version of `ArrayList`.
- **Synchronization**: Every public method is individually `synchronized`, leading to massive thread-contention overhead.
- **Obsolete**: Virtually obsolete. For single-thread applications, use `ArrayList`. For multi-thread applications, prefer `CopyOnWriteArrayList` or wrap with `Collections.synchronizedList()`.

### Stack

The `Stack` class represents a last-in-first-out (LIFO) stack of objects.
- **Design Flaw**: `Stack` extends `Vector`, which means it inherits all list operations (like index-based insertion/removal at arbitrary indices), violating the stack abstraction. It is also synchronized, harming performance.
- **Replacement**: Use `Deque` implementations like `ArrayDeque` for stack operations.

### Comparing ArrayList and LinkedList

| Operation | ArrayList | LinkedList | Note |
| --- | --- | --- | --- |
| **Get (index)** | `O(1)` | `O(N)` | ArrayList uses direct array offsetting. |
| **Insert/Remove (End)** | `O(1)` (Amortized) | `O(1)` | ArrayList may trigger resize; LinkedList updates pointers. |
| **Insert/Remove (Front)**| `O(N)` | `O(1)` | ArrayList shifts all items; LinkedList updates head pointers. |
| **Insert/Remove (Middle)**| `O(N)` | `O(N)` | LinkedList must traverse to middle index; ArrayList shifts. |
| **Memory Overhead** | Low (contiguous array) | High (3 references per node) | LinkedList creates a wrapper object for every element. |

#### Memory & Cache Friendliness
`ArrayList` stores elements in a contiguous block of memory. This matches modern CPU cache structures perfectly: loading one element pulls adjacent elements into the L2/L3 cache (spatial locality). `LinkedList` nodes can be scattered all over the heap, causing CPU cache misses on traversal.

### When to use List?
Use a `List` when:
1. Element order needs to be preserved.
2. Duplicate elements are acceptable.
3. Index-based element retrieval is required.
*Default choice: Always prefer `ArrayList` unless you have verified requirements for extensive insertion/removal at the head.*

### HashSet

A `HashSet` is a Set backed by a `HashMap` instance.
- **Ordering**: Offers no guarantee of iteration order. Order can change when new elements are added.
- **Performance**: O(1) time complexity for basic operations (`add`, `remove`, `contains`), assuming a good hash function.
- **Nulls**: Allows one `null` element.

### LinkedHashSet

`LinkedHashSet` is a hash-table and doubly-linked-list implementation of the `Set` interface.
- **Ordering**: Maintains insertion order. Iteration returns elements in the order they were added.
- **Performance**: O(1) operations, slightly slower than `HashSet` due to the cost of maintaining the doubly-linked list pointers.

---

**Runnable Code Example (Performance & Order Comparison):**
```java
import java.util.*;

public class ListSetComparison {
    public static void main(String[] args) {
        // 1. Insertion order comparison
        Set<String> hashSet = new HashSet<>();
        Set<String> linkedHashSet = new LinkedHashSet<>();
        
        List<String> fruits = List.of("Orange", "Apple", "Banana");
        hashSet.addAll(fruits);
        linkedHashSet.addAll(fruits);
        
        System.out.println("HashSet (arbitrary order): " + hashSet);
        System.out.println("LinkedHashSet (insertion order): " + linkedHashSet);
        
        // 2. Performance Comparison (ArrayList vs LinkedList lookup)
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        int count = 100_000;
        
        for (int i = 0; i < count; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }
        
        long start = System.nanoTime();
        int val1 = arrayList.get(count / 2);
        long arrayListTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        int val2 = linkedList.get(count / 2);
        long linkedListTime = System.nanoTime() - start;
        
        System.out.println("ArrayList mid-lookup time: " + arrayListTime + " ns");
        System.out.println("LinkedList mid-lookup time: " + linkedListTime + " ns");
    }
}
```

---

## Common Mistakes

### 1. Believing LinkedList is always faster for additions
A common misconception is that `LinkedList` is always superior for adding elements. While pointer updates are O(1), finding the index where the insertion must occur takes O(N) time. Furthermore, because each insertion creates a new node object, `LinkedList` causes far more garbage collection activity than `ArrayList`.

### 2. Using Stack instead of Deque
Using the legacy `java.util.Stack` class exposes synchronization overhead and bad OOP design (exposing list methods). Instead, use `Deque<Integer> stack = new ArrayDeque<>();`.

### 3. Modifying keys in a HashSet
If you insert a mutable object into a `HashSet`, and then modify that object's fields such that its `hashCode()` changes, the element is lost inside the Set. Attempts to search for it using `contains()` will return `false`, because Java searches in the bucket matching the new hashcode, while the object remains in the bucket matching the old hashcode.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
