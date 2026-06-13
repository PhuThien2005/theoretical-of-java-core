# Common Java Core Interview Questions - Part 4

## Learning Goal

This file covers advanced Java Core interview questions regarding Stream transformations, Optional behaviors, thread-safe Maps, hash consistency, Garbage Collection mechanics, and JVM memory organization.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `How are map and flatMap different?` | `map` transforms each element into a single value (1-to-1); `flatMap` transforms each element into a Stream and flattens them (1-to-many). |
| `How are orElse and orElseGet different?` | `orElse` always evaluates its parameter; `orElseGet` evaluates lazily using a `Supplier` only if the `Optional` is empty. |
| `How are HashMap, Hashtable, and ConcurrentHashMap different?` | `HashMap` is non-synchronized; `Hashtable` locks the whole table; `ConcurrentHashMap` uses lock striping/CAS for high concurrency. |
| `Why must overriding equals() also override hashCode()?` | To maintain the contract that equal objects must have equal hashcodes, ensuring correct behavior in hash collections. |
| `How does Garbage Collection work?` | Reclaims memory of unreachable objects; typically uses generational collection dividing heap into Young and Old Gen. |
| `How are Stack and Heap different?` | Stack stores local variables and method execution frames (per-thread); Heap stores all objects and arrays (shared). |

---

## Detailed Notes

### map() vs. flatMap()

Both are Stream/Optional intermediate operations, but they differ in mapping style:
- **`map`**: Transforms `Stream<T>` to `Stream<R>` using a function `T -> R`.
- **`flatMap`**: Transforms `Stream<T>` to `Stream<R>` using a function `T -> Stream<R>`. It merges (flattens) multiple inner streams into a single outer stream.

```java
// map: [ ["a", "b"], ["c"] ] -> [ 2, 1 ] (lengths)
List<List<String>> list = List.of(List.of("a", "b"), List.of("c"));
Stream<Integer> sizes = list.stream().map(List::size);

// flatMap: [ ["a", "b"], ["c"] ] -> [ "a", "b", "c" ] (flattened)
Stream<String> flat = list.stream().flatMap(Collection::stream);
```

---

### Optional: `orElse` vs. `orElseGet`

- **`orElse(T other)`**: The default value `other` is evaluated **eagerly**, even if the `Optional` is not empty.
- **`orElseGet(Supplier<? extends T> other)`**: The default value is evaluated **lazily** (using a lambda) only if the `Optional` is empty.

```java
public String getDatabaseValue() {
    System.out.println("Costly DB query run!");
    return "DB_VALUE";
}

Optional<String> optional = Optional.of("Alice");
optional.orElse(getDatabaseValue());    // PRINTS: "Costly DB query run!" (eager evaluation)
optional.orElseGet(() -> getDatabaseValue()); // DOES NOT PRINT (lazy evaluation)
```

---

### HashMap vs. Hashtable vs. ConcurrentHashMap

- **`HashMap`**: Non-synchronized, accepts one `null` key and multiple `null` values. High performance for single-threaded or external synchronizations.
- **`Hashtable`**: Legacy class. Synchronizes every method on the entire map instance. Poor concurrent performance. Rejects `null` keys/values.
- **`ConcurrentHashMap`**: Highly concurrent. In Java 8+, it uses a combination of Compare-And-Swap (CAS) and synchronized locks at the bucket/node level (lock striping), allowing concurrent reads and writes in different buckets. Rejects `null` keys/values.

---

### Why equals() and hashCode() must be overridden together

If you override `equals()`, you must override `hashCode()`.
- **The Contract**: If `o1.equals(o2)` is `true`, then `o1.hashCode() == o2.hashCode()` must be `true`.
- **Failure Consequence**: If you violate this, putting an object in a `HashMap` or `HashSet` will result in duplicate keys or lookup failures. The hash collection maps the equal keys to different buckets because their hashcodes differ.

---

### How Garbage Collection (GC) works

Garbage collection automatically reclaims heap memory allocated to objects that are no longer reachable from any **GC Roots** (active thread stacks, static variables, JNI references).

- **Generational GC Theory**: Most objects die young. Therefore, the JVM heap is divided into:
  1. **Young Generation**: Subdivided into Eden and survivor spaces (S0, S1). Minor GCs happen here frequently and are very fast.
  2. **Old Generation**: Holds long-lived objects. Major/Full GCs happen here less frequently and take longer.

---

### Stack vs. Heap Memory

- **Stack**:
  - Memory allocated per thread.
  - Stores local variables, reference pointers, and stack frames for method calls.
  - Allocation/deallocation follows LIFO (Last-In-First-Out) structure and is handled automatically. Very fast.
- **Heap**:
  - Memory shared across all threads.
  - Stores all objects and arrays.
  - Managed by the Garbage Collector. Slower allocation and cleanup.

---

## Common Mistakes & Traps

### 1. Database/API query inside `orElse()`
Calling a DB retrieval inside `orElse(...)` runs the query every time, even if the value exists:
```java
// Database call runs even if user is cached in Optional!
User u = optionalUser.orElse(db.fetchDefaultUser()); 
```
Instead, use `orElseGet()`:
```java
User u = optionalUser.orElseGet(() -> db.fetchDefaultUser());
```

### 2. Violating GC reachability assumptions
Assuming setting a reference to `null` forces immediate GC. Setting `u = null` only makes the object *eligible* for GC. The actual cleanup happens when the JVM runs the collector.
