# Common Java Core Interview Questions - Part 3

## Learning Goal

This file covers intermediate to advanced Java Core interview questions regarding object sorting, iterator semantics, multithreading synchronization, and Stream API execution logic.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `How are Comparable and Comparator different?` | `Comparable` defines a class's natural ordering via `compareTo()`; `Comparator` defines custom external ordering via `compare()`. |
| `How are fail-fast and fail-safe iterators different?` | Fail-fast throws `ConcurrentModificationException` on mutation; fail-safe works on a clone/copy, avoiding exceptions. |
| `How are volatile and synchronized different?` | `volatile` guarantees thread visibility and ordering; `synchronized` guarantees visibility, ordering, AND atomicity via lock execution. |
| `What is deadlock?` | A state where two or more threads are blocked forever, each waiting for a lock held by the other. |
| `How are Thread start() and run() different?` | `start()` spawns a new thread and executes its run logic asynchronously; `run()` executes synchronously in the caller's thread. |
| `How are sleep() and wait() different?` | `sleep()` temporarily pauses thread execution without releasing locks; `wait()` releases locks, waiting to be notified by another thread. |
| `How are notify() and notifyAll() different?` | `notify()` wakes up a single random waiting thread; `notifyAll()` wakes up all threads waiting on the object monitor. |
| `Is Stream API lazy?` | Yes. Intermediate operations (like `filter`, `map`) are lazy and only execute when a terminal operation (like `collect`) is called. |

---

## Detailed Notes

### Comparable vs. Comparator

- **`Comparable`** — Comparable: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
  - Located in `java.lang`.
  - Used for **natural ordering** of elements (e.g. alphabetical for Strings, ascending for Integers).
  - The class itself implements `Comparable<T>` and overrides `compareTo(T o)`.
- **`Comparator`** — Comparator: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
  - Located in `java.util`.
  - Used for **custom/alternative ordering** (e.g. sorting strings by length or custom fields).
  - Implemented in a separate class or as a lambda passed directly to `Collections.sort()` or `list.sort()`.

```java
// Comparable: Natural order (by ID)
public class Person implements Comparable<Person> {
    int id;
    public int compareTo(Person other) { return Integer.compare(this.id, other.id); }
}

// Comparator: Custom order (by Name)
Comparator<Person> nameComparator = (p1, p2) -> p1.name.compareTo(p2.name);
```

---

### Fail-Fast vs. Fail-Safe (Non-Fail-Fast) Iterators

- **Fail-Fast Iterators**:
  - Traversed directly over the collection's internal structure.
  - Throw `ConcurrentModificationException` immediately if the collection is structurally modified (add/remove) during traversal by anything other than the iterator's own `remove()` method.
  - Examples: `ArrayList` iterator, `HashMap` keyset iterator.
- **Fail-Safe (Weakly Consistent) Iterators**:
  - Traverse over a copy or clone of the collection, or handle concurrency via thread-safe internal structures.
  - Do not throw exceptions on modification, but changes made during iteration might not be visible to the iterator.
  - Examples: `CopyOnWriteArrayList` iterator, `ConcurrentHashMap` iterator.

---

### volatile vs. synchronized

- **`volatile`** — volatile: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
  - Variable modifier.
  - Guarantees **visibility** (reads/writes go directly to main memory, bypassing CPU caches) and prevents compiler instruction **reordering**.
  - Does *not* guarantee **atomicity** (e.g. `count++` is not atomic and still needs sync).
- **`synchronized`** — synchronized: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
  - Method or block modifier.
  - Guarantees **visibility**, **ordering**, and **atomicity** by obtaining a monitor lock. Only one thread can execute the block at a time.

---

### What is Deadlock?

Deadlock occurs when Thread 1 holds Lock A and waits for Lock B, while Thread 2 holds Lock B and waits for Lock A. Neither thread can proceed.

- **To avoid deadlocks**:
  1. Acquire locks in a strict global order.
  2. Use timeout locks (e.g., `ReentrantLock.tryLock()`).
  3. Keep synchronized blocks as small as possible.

---

### Thread `start()` vs. `run()`

- **`thread.start()`** — thread.start(): Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
  - Allocates system resources, creates a new execution thread in the JVM, and schedules it to run.
  - The JVM calls the thread's `run()` method asynchronously in the new thread context.
- **`thread.run()`** — thread.run(): Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
  - Just a regular method call. No new thread is spawned.
  - Executes synchronously inside the *calling* thread's stack.

---

### sleep() vs. wait()

- **`Thread.sleep(millis)`** — Thread.sleep(millis): Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
  - Static method of `Thread` class.
  - The thread pauses for a duration but **keeps any locks it currently holds**.
  - Can be called anywhere.
- **`object.wait()`** — object.wait(): Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
  - Instance method of `java.lang.Object`.
  - The thread yields execution and **releases the lock** on the object monitor, allowing other threads to enter.
  - Must be called inside a synchronized block on that specific object.

---

### notify() vs. notifyAll()

- **`notify()`** — notify(): Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`notifyAll()`** — notifyAll(): Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

---

### Is Stream API Lazy?

Yes, Java Stream API operations are divided into:
1. **Intermediate Operations** (e.g. `filter()`, `map()`, `sorted()`): These return a new Stream but do not process any elements. They build an execution plan.
2. **Terminal Operations** (e.g. `collect()`, `forEach()`, `reduce()`): These trigger the processing of the pipeline.

- **Proof of Laziness**:
```java
Stream.of("A", "B", "C")
      .filter(s -> {
          System.out.println("Filter: " + s); // This will NOT print anything yet
          return true;
      }); 
// No terminal operation was called, so no output is produced.
```

---

## Common Mistakes & Traps

### 1. `volatile` does not make `count++` thread-safe
`count++` consists of three operations: read, modify, and write. `volatile` only ensures other threads see the latest write, but it doesn't prevent two threads from reading the same stale value concurrently. Use `AtomicInteger` or `synchronized`.

### 2. Forgetting to synchronize before calling `wait()` or `notify()`
Calling `wait()`, `notify()`, or `notifyAll()` without holding the monitor lock (outside a synchronized block) throws an `IllegalMonitorStateException` at runtime.
```java
Object lock = new Object();
lock.wait(); // CRASH: Not inside synchronized(lock)!
```

---

## Why Comparable and Comparator Differ in Sorting Design

Java separates sorting logic into `Comparable` and `Comparator` to distinguish between an object's inherent natural ordering and its context-dependent custom orderings. When a class implements `Comparable`, it overrides `compareTo()` to establish a default sorting rule that represents the single, intrinsic identity ordering of that entity (e.g., sorting Students by their unique ID). Conversely, a `Comparator` is defined externally as a separate object or lambda, overriding `compare()` to apply temporary, custom sorting strategies (e.g., sorting Students by name, grade, or age). Implementing sorting as a separate `Comparator` avoids polluting the core entity class with multiple sort strategies and conforms to the Single Responsibility Principle. Furthermore, this design allows developers to sort collections of third-party classes whose source code cannot be modified to implement `Comparable`.

### Mental Model

```text
  Entity Class (e.g., Student)
  +-------------------------------------------------+
  | implements Comparable -> compareTo(Student o)  | -> Natural Order (ID)
  +-------------------------------------------------+
          | (alternative sorting views)
          v
  External Helper Classes / Lambdas (Comparators)
  +-------------------------------------------------+
  | Comparator1 -> compare(Student s1, Student s2)  | -> Sort by Name
  | Comparator2 -> compare(Student s1, Student s2)  | -> Sort by Age
  +-------------------------------------------------+
```

### Code Example

The code below demonstrates how natural ordering via `Comparable` and custom sorting via `Comparator` coexist for the same class.

```java
import java.util.*;

public class SortDemo {
    static class Item implements Comparable<Item> {
        int id; String name;
        Item(int i, String n) { id = i; name = n; }
        public int compareTo(Item o) { return Integer.compare(this.id, o.id); }
    }
    public static void main(String[] args) {
        List<Item> list = new ArrayList<>(List.of(new Item(2, "B"), new Item(1, "A")));
        Collections.sort(list); // Comparable (ID) -> [1, 2]
        System.out.println(list.get(0).name); // Output: A
        list.sort((x, y) -> y.name.compareTo(x.name)); // Comparator (Desc Name) -> [B, A]
        System.out.println(list.get(0).name); // Output: B
    }
}
```

### Cause-Effect Chain

```text
Collection sort triggered (e.g., Collections.sort(list) vs list.sort(comparator))
  → IF no comparator provided: checks if elements implement Comparable
    → YES: invokes compareTo(o) repeatedly during sort
    → NO: throws ClassCastException at runtime
  → IF comparator provided: bypasses Comparable, invokes comparator.compare(a, b)
  → Sort algorithm reorders references based on sign of comparison result (negative/zero/positive)
```
