# Collections Framework - Part 5

## Learning Goal

This file covers a focused slice of **Collections Framework** including concurrent maps (`ConcurrentHashMap`), specialized maps (`WeakHashMap`, `IdentityHashMap`), sorted/navigable maps, and iterator traversal models.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `ConcurrentHashMap` | High-performance thread-safe Map utilizing bucket head synchronization and CAS (Compare-And-Swap) operations rather than locking the entire map. |
| `WeakHashMap` | Map with keys wrapped in `WeakReference`, allowing them to be garbage collected when no longer in use outside the map. |
| `IdentityHashMap` | Map that compares keys using reference equality (`==`) rather than logical equality (`equals()`). |
| `SortedMap` | Interface representing a Map sorted in ascending order of its keys. |
| `NavigableMap` | Extends SortedMap, adding search/navigation methods like `lowerEntry()`, `floorKey()`, etc. |
| `Iterator` | Interface providing forward-only traversal of collections with safe removal support. |
| `ListIterator` | Bidirectional iterator for List objects allowing forward/backward traversal and element modification. |

## Detailed Notes

### ConcurrentHashMap

`ConcurrentHashMap` provides full concurrency for reads and high concurrency for writes.
- **Locking mechanism**: In Java 7, it used segment locking. Since Java 8, it uses node-level locking (synchronizing only on the first node of a bucket/bin) and CAS (Compare-And-Swap) operations for empty buckets. This permits multiple threads to write to different buckets concurrently.
- **Null Safety**: Rejects `null` keys and values.

**Runnable Code Example:**
```java
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMapDemo {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> inventory = new ConcurrentHashMap<>();
        inventory.put("Widget A", 100);

        // Atomic write operations
        inventory.computeIfPresent("Widget A", (key, val) -> val - 5);
        inventory.putIfAbsent("Widget B", 50);

        System.out.println("Inventory: " + inventory); // {Widget A=95, Widget B=50}
    }
}
```

### WeakHashMap

A hash table-based Map with keys wrapped in `WeakReference`. When a key is no longer referenced elsewhere, it is garbage collected, and the corresponding entry is subsequently removed from the map.
- **Typical Use Case**: Caches, metadata lookup tables, or listener registries where mapping should not keep objects alive.

**Runnable Code Example:**
```java
import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapDemo {
    public static void main(String[] args) throws InterruptedException {
        Map<Object, String> weakMap = new WeakHashMap<>();
        Object key = new Object(); // Strong reference to the key

        weakMap.put(key, "Cached Metadata");
        System.out.println("Before GC: " + weakMap.containsKey(key)); // true

        key = null; // Clear the strong reference
        System.gc(); // Request GC execution
        Thread.sleep(100); // Give GC time to run

        System.out.println("After GC: " + weakMap.isEmpty()); // true (key was garbage collected)
    }
}
```

### IdentityHashMap

An `IdentityHashMap` compares keys using reference equality (`key1 == key2`) instead of object equality (`key1.equals(key2)`). It is intentionally not a general-purpose Map implementation.
- **Typical Use Case**: Graph-traversal algorithms to detect cycles, serializing object graphs, or maintaining exact object instances.

**Runnable Code Example:**
```java
import java.util.IdentityHashMap;
import java.util.Map;

public class IdentityMapDemo {
    public static void main(String[] args) {
        Map<String, String> map = new IdentityHashMap<>();
        
        String key1 = new String("key");
        String key2 = new String("key");
        
        map.put(key1, "Value A");
        map.put(key2, "Value B");
        
        // Logical contents match, but references are different
        System.out.println("Size: " + map.size()); // 2
        System.out.println("Key1 value: " + map.get(key1)); // Value A
    }
}
```

### SortedMap & NavigableMap

- **SortedMap**: An interface that maintains its keys in sorted order.
- **NavigableMap**: Extends `SortedMap` and adds methods like `lowerEntry()`, `floorKey()`, `ceilingKey()`, and `higherKey()` to find closest matches relative to a given key. `TreeMap` is the primary implementation.

### Iterator vs ListIterator

- **Iterator**: Can traverse any `Collection` in the forward direction. Supports removing elements during iteration.
- **ListIterator**: Extends `Iterator`. Can only traverse `List` implementations. Supports bidirectional traversal (`hasPrevious()`, `previous()`), replacing elements (`set()`), adding elements (`add()`), and retrieving current index.

**Runnable Code Example:**
```java
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListIteratorDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("A", "B", "C"));
        ListIterator<String> lit = list.listIterator();

        // Traverse forward
        while (lit.hasNext()) {
            String element = lit.next();
            if (element.equals("B")) {
                lit.set("Updated B"); // Replace element
            }
        }

        // Traverse backward
        while (lit.hasPrevious()) {
            System.out.print(lit.previous() + " "); // C Updated B A 
        }
        System.out.println();
    }
}
```

---

## Case Study: High-Concurrency Counter using ConcurrentHashMap

When building multi-threaded aggregators, utilizing synchronized blocks or `synchronizedMap` creates severe performance bottlenecks. Using `ConcurrentHashMap` combined with `LongAdder` allows highly efficient concurrent counter updates.

```java
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class ConcurrentCounterApp {
    public static void main(String[] args) throws InterruptedException {
        // ConcurrentHashMap + LongAdder (preferred over AtomicInteger for high write-rate counters)
        ConcurrentHashMap<String, LongAdder> pageCounts = new ConcurrentHashMap<>();

        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Submit 1000 tasks to increment counts
        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                // computeIfAbsent is thread-safe and atomic
                pageCounts.computeIfAbsent("/home", k -> new LongAdder()).increment();
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Total visits to /home: " + pageCounts.get("/home").sum()); // 1000
    }
}
```

---

## Common Mistakes

### 1. WeakHashMap Strong Value Reference Loop
If the value in a `WeakHashMap` contains a strong reference to the key, the key will never be garbage collected. This leaks memory silently and defeats the entire purpose of `WeakHashMap`.

### 2. Modifying collections during iteration with Collection methods
Using `list.remove()` instead of `iterator.remove()` inside an active iterator loop triggers a `ConcurrentModificationException`. Always modify through the iterator's own methods.

### 3. Assuming `ConcurrentHashMap` operations are all atomic
While individual read and write operations on `ConcurrentHashMap` are thread-safe and atomic, sequences of operations (e.g., `if (!map.containsKey(key)) { map.put(key, value); }`) are NOT atomic. Use `putIfAbsent()`, `compute()`, or `computeIfAbsent()` instead to guarantee atomicity.

---

## Common Review Prompts

- What does ConcurrentHashMap use since Java 8 instead of segment locks? (CAS operations and synchronized bucket head nodes)
- Why does IdentityHashMap use `==`? (For identity matching rather than logical equals, useful for graph traversals or system level caches)
- Which iterator supports going backwards? (ListIterator)
