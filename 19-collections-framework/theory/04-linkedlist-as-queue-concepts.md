# Collections Framework - Part 4

## Learning Goal

This file covers a focused slice of **Collections Framework** including Queue operations, Map implementations (`HashMap`, `LinkedHashMap`, `TreeMap`, `Hashtable`), and FIFO/LIFO behaviors.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `LinkedList as Queue` | LinkedList implements the Queue interface, providing a standard FIFO queue backed by a doubly-linked list. |
| `FIFO` | First-In-First-Out ordering where elements are inserted at the tail and removed from the head. |
| `LIFO` | Last-In-First-Out ordering (Stack behavior) where elements are added and removed from the same end. |
| `HashMap` | Red-black tree and linked-list bucket hash table mapping keys to values. Unordered, allows one null key. |
| `LinkedHashMap` | Hash table and doubly-linked list implementation of the Map interface, preserving insertion or access order. |
| `TreeMap` | Red-black tree based NavigableMap implementation, sorting keys naturally or via a custom Comparator. |
| `Hashtable` | Legacy synchronized map. Rejects null keys and null values. Obsolete. |

## Detailed Notes

### LinkedList as Queue

`LinkedList` implements `Queue`, allowing it to act as a FIFO structure. Since it's a doubly-linked list, enqueuing (`offer()`) and dequeuing (`poll()`) are highly efficient `O(1)` operations.

**Runnable Code Example:**
```java
import java.util.LinkedList;
import java.util.Queue;

public class QueueDemo {
    public static void main(String[] args) {
        Queue<String> printerQueue = new LinkedList<>();

        // Enqueuing
        printerQueue.offer("Doc1.pdf");
        printerQueue.offer("Doc2.pdf");

        // Inspecting head
        System.out.println("Next up: " + printerQueue.peek()); // Doc1.pdf

        // Dequeuing
        System.out.println("Printing: " + printerQueue.poll()); // Doc1.pdf
        System.out.println("Printing: " + printerQueue.poll()); // Doc2.pdf

        // Queue is now empty, poll() returns null (does not throw exception)
        System.out.println("Empty poll: " + printerQueue.poll()); // null
    }
}
```

### FIFO vs LIFO

- **FIFO (First-In-First-Out)**: Elements are processed in the exact order they arrive. Used for job scheduling, buffering, etc.
- **LIFO (Last-In-First-Out)**: The newest element is processed first. Used for call stacks, undo/redo buffers. In Java, use `Deque` (e.g., `ArrayDeque`) for LIFO stacks rather than legacy `Stack`.

**Runnable Code Example:**
```java
import java.util.ArrayDeque;
import java.util.Deque;

public class LifoFifoDemo {
    public static void main(String[] args) {
        // LIFO Stack using ArrayDeque
        Deque<String> stack = new ArrayDeque<>();
        stack.push("Step 1");
        stack.push("Step 2");
        System.out.println("LIFO Pop: " + stack.pop()); // Step 2

        // FIFO Queue using ArrayDeque
        Deque<String> queue = new ArrayDeque<>();
        queue.addLast("Job 1");
        queue.addLast("Job 2");
        System.out.println("FIFO Remove: " + queue.removeFirst()); // Job 1
    }
}
```

### HashMap

`HashMap` stores key-value pairs using a hash table.
- **Internal Structure**: An array of Node/Entry buckets. When collisions occur, entries are stored in a linked list. In Java 8+, if a bucket's size exceeds 8 and the total table capacity is at least 64, the linked list is converted to a Red-Black Tree ("treeified") to improve worst-case performance from `O(N)` to `O(log N)`.
- **Iteration**: No order guarantee.

**Runnable Code Example:**
```java
import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {
    public static void main(String[] args) {
        Map<String, String> capitalMap = new HashMap<>();
        capitalMap.put("USA", "Washington D.C.");
        capitalMap.put("Japan", "Tokyo");
        capitalMap.put(null, "No Capital"); // Allows one null key

        System.out.println("Japan Capital: " + capitalMap.get("Japan")); // Tokyo
    }
}
```

### LinkedHashMap

`LinkedHashMap` extends `HashMap` but maintains a doubly-linked list running through all of its entries.
- **Ordering modes**:
  1. **Insertion Order**: Iteration matches insertion sequence (default).
  2. **Access Order**: Iteration matches order of last access (least-recently accessed to most-recently accessed). Useful for building caches.

**Runnable Code Example:**
```java
import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapDemo {
    public static void main(String[] args) {
        // Access order set to true
        Map<Integer, String> cache = new LinkedHashMap<>(16, 0.75f, true);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");

        cache.get(1); // Access key 1

        // Iteration shows accessed items moved to the end
        System.out.println(cache); // {2=B, 3=C, 1=A}
    }
}
```

### TreeMap

`TreeMap` is a Red-Black Tree implementation of `NavigableMap`.
- **Ordering**: Sorted by natural order of keys or custom `Comparator`.
- **Restrictions**: Keys must be mutually comparable and cannot be `null`.

**Runnable Code Example:**
```java
import java.util.TreeMap;

public class TreeMapDemo {
    public static void main(String[] args) {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("Zebra", 1);
        treeMap.put("Apple", 2);
        treeMap.put("Mango", 3);

        System.out.println("Sorted Map: " + treeMap); // {Apple=2, Mango=3, Zebra=1}
        System.out.println("First Key: " + treeMap.firstKey()); // Apple
        System.out.println("Ceiling key for 'M': " + treeMap.ceilingKey("M")); // Mango
    }
}
```

### Hashtable

`Hashtable` is a legacy synchronized map class.
- **Synchronization**: Uses coarse-grained synchronization on every method.
- **Null Safety**: Throws `NullPointerException` if any key or value is `null`.
- **Obsolete**: Avoid using it. Use `ConcurrentHashMap` for concurrent maps, or `HashMap` for non-concurrent maps.

---

## Case Study: Designing a Least Recently Used (LRU) Cache

An LRU cache discards the least recently used elements first when capacity is reached. We can implement this easily by extending `LinkedHashMap` and overriding `removeEldestEntry()`.

```java
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUCache(int capacity) {
        // Enable access-order sorting (third argument = true)
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // Automatically evict when size exceeds maximum capacity
        return size() > capacity;
    }

    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3);
        cache.put(1, "Value A");
        cache.put(2, "Value B");
        cache.put(3, "Value C");

        cache.get(1); // Access 1: Cache ordering becomes [2, 3, 1]

        cache.put(4, "Value D"); // Triggers eviction of key 2 (least recently used)

        System.out.println("Cache holds key 2: " + cache.containsKey(2)); // false
        System.out.println("Cache holds key 1: " + cache.containsKey(1)); // true
        System.out.println("Current Cache items: " + cache); // {3=Value C, 1=Value A, 4=Value D}
    }
}
```

---

## Common Mistakes

### 1. Queue method confusion: `poll()` vs `remove()`
Calling `remove()` on an empty Queue throws `NoSuchElementException`. In contrast, `poll()` returns `null`. Under-capacity conditions can crash programs if the developer mixes these methods up.

### 2. Modifying keys in a HashMap
If key properties are mutated after entry insertion such that the key's `hashCode()` changes, the entry becomes unreachable. The map will search for it in the wrong bucket and return `null`.

### 3. Assuming `LinkedHashMap` is sorted
Developers sometimes confuse `LinkedHashMap` (which maintains insertion/access order) with `TreeMap` (which maintains sorted/lexicographical order).

---

## Common Review Prompts

- Which map implementations reject null keys? (TreeMap, Hashtable)
- What is the difference between `peek()` and `element()`? (`peek()` returns null on empty queue, `element()` throws exception)
- How does HashMap handle hash collisions since Java 8? (Linked list up to 8 items, then treeifies to Red-Black tree if table capacity >= 64)
