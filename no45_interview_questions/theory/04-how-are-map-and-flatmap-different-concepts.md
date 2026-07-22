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

---

## Why map and flatMap Stream Operations Differ

Java Streams provide `map` and `flatMap` to support different transformation topologies for elements in a pipeline. The `map` operation takes a mapper function `T -> R` and applies it to each element individually, yielding a `Stream<R>` of the same size as the input stream (a strict 1-to-1 mapping). Conversely, `flatMap` takes a mapper function `T -> Stream<R>`, converting each input element into a new sub-stream, and then flattens all generated sub-streams into a single contiguous outer stream. This allows `flatMap` to handle 1-to-many transformations or map nested collections (like a `List<List<T>>`) into a flat list structure. Under the hood, `flatMap` creates and closes multiple intermediate streams, which can introduce slight performance overhead compared to the direct element-to-element mapping performed by `map`.

### Mental Model

```text
  Input Stream: [ [A, B], [C, D] ]
  
  Map (List::size):
    [A, B] ---> 2
    [C, D] ---> 2
    Output Stream: [ 2, 2 ] (No structural change, 1-to-1)
  
  FlatMap (Collection::stream):
    [A, B] ---> Stream[A, B] \
                             +---> Output Stream: [ A, B, C, D ]
    [C, D] ---> Stream[C, D] /        (Flattened into single stream)
```

### Code Example

The example below demonstrates how `map` and `flatMap` handle a nested list of strings differently.

```java
import java.util.List;
import java.util.stream.Collectors;

public class StreamMappingDemo {
    public static void main(String[] args) {
        List<List<String>> nested = List.of(List.of("A", "B"), List.of("C"));

        // map() keeps the nested structure: List<List<String>> -> List<Integer>
        List<Integer> lengths = nested.stream().map(List::size).collect(Collectors.toList());
        System.out.println(lengths); // Output: [2, 1]

        // flatMap() flattens the nesting: List<List<String>> -> List<String>
        List<String> flat = nested.stream().flatMap(List::stream).collect(Collectors.toList());
        System.out.println(flat);    // Output: [A, B, C]
    }
}
```

### Cause-Effect Chain

```text
Stream element T processed by intermediate operation
  → IF map(T -> R) called: function returns R directly; R is passed to downstream
  → IF flatMap(T -> Stream<R>) called: function generates nested Stream<R>
  → FlatMap internal spliterator traverses the nested stream element by element
  → Individual nested elements R are fed into the outer stream pipeline sequentially
  → Nested stream resources are closed, resulting in a single flat Stream<R> output
```

---

## Why Generic Compile-Time Verification Differs from Runtime

Java generics were designed with backward compatibility in mind, leading to a major discrepancy between compile-time type verification and runtime type enforcement. At compile-time, the `javac` compiler performs strict type checking to ensure that objects inserted into a parameterized collection conform to the specified type arguments. However, once compilation completes, the compiler performs **Type Erasure**, removing all generic type parameters from the class files and replacing them with their raw bounds (usually `Object`). As a result, the JVM executes bytecode containing raw types, meaning it is unaware of the generic constraints at runtime. If a developer uses raw types or unsafe casts to bypass compile-time verification, they can introduce incompatible objects into generic collections, a phenomenon known as Heap Pollution, which eventually triggers a `ClassCastException` at runtime when the elements are retrieved and implicitly cast.

### Mental Model

```text
  Compile-Time (Strict Type Check)
    List<String> list = new ArrayList<>();
    list.add("Hello"); // OK
    list.add(123);     // Compilation Error!
         │
         ▼ (Type Erasure by javac)
  Runtime JVM Bytecode (Raw Types / Erasure to Object)
    List list = new ArrayList();
    list.add("Hello"); // OK
    list.add(123);     // OK at runtime! (Heap Pollution)
    String s = (String) list.get(1); // Throws ClassCastException!
```

### Code Example

The code below demonstrates how bypassing generic compile-time checks results in heap pollution and a runtime `ClassCastException`.

```java
import java.util.*;

public class GenericsErasureDemo {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        strings.add("Safe");
        List raw = strings; // raw reference
        raw.add(100); // Heap Pollution

        try {
            String s = strings.get(1); // Implicit cast throws
        } catch (ClassCastException e) {
            System.out.println("Failed: " + e.getMessage());
            // Output: Failed: class java.lang.Integer cannot be cast to class java.lang.String
        }
    }
}
```

### Cause-Effect Chain

```text
Compile-time: javac verifies generic types on parameterized collections
  → Type Erasure: compiler strips type parameters, replacing them with Object/bounds
  → Compiler inserts implicit casts (e.g. checkcast) at collection read operations
  → Runtime: Code uses raw type reference or unsafe cast to add wrong element type
  → Element is successfully inserted into backing object array (no JVM type check)
  → Read operation executes checkcast instruction on the mismatching object
  → JVM throws ClassCastException due to runtime type incompatibility
```
