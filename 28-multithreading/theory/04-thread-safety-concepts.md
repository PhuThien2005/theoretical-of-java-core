# Multithreading - Part 4

## Learning Goal

This file covers thread safety principles, object immutability, and atomic operations. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Thread safety` | A property of an object or method ensuring it behaves correctly when accessed by multiple threads concurrently, without requiring additional external synchronization. |
| `Immutable object` | An object whose state cannot be changed after it is constructed. Immutable objects are inherently thread-safe. |
| `Atomic operation` | An operation that executes as a single, indivisible unit of work. Intermediate states are never visible to other threads. |

## Detailed Notes

### Thread Safety

An object is thread-safe if it maintains its class invariants under concurrent execution. Ways to achieve thread safety:
1. **Immutability**: Shared state that cannot change is inherently thread-safe.
2. **Locking**: Protecting critical sections using `synchronized` blocks or `ReentrantLock`.
3. **Thread Local**: Avoiding shared state entirely by keeping variables private to each thread using `ThreadLocal`.
4. **Concurrent Data Structures**: Using thread-safe structures like `ConcurrentHashMap` or `AtomicInteger`.

### Immutable Object Rules

To make a class completely immutable in Java, follow these rules:
1. Declare the class as `final` so it cannot be subclassed (preventing overriding methods to return mutable state).
2. Make all fields `private` and `final`.
3. Do not provide any setter/mutator methods.
4. If the class contains references to mutable objects, perform **deep copy/defensive copying** during construction and when returning them from getter methods.

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ImmutablePerson {
    private final String name;
    private final List<String> hobbies; // Mutable list

    public ImmutablePerson(String name, List<String> hobbies) {
        this.name = name;
        // Defensive copy on construction
        this.hobbies = new ArrayList<>(hobbies);
    }

    public String getName() {
        return name;
    }

    // Defensive copy / unmodifiable wrap on retrieval
    public List<String> getHobbies() {
        return Collections.unmodifiableList(hobbies);
    }
}
```

### Atomic Operations in Java

An atomic operation appears indivisible to other threads. In Java:
* Reads and writes are atomic for reference variables and all primitive variables **except** `long` and `double` (which are 64-bit and may be split into two 32-bit writes on 32-bit JVMs).
* Reads and writes to `long` and `double` are guaranteed atomic if declared `volatile`.
* Compound operations like `count++` (which is a read-modify-write operation) are **never** atomic.

```java
class UnsafeCounter {
    private int count = 0;

    // NOT thread-safe: multiple threads can read the same value, increment, and write back
    public void increment() {
        count++; 
    }
}

import java.util.concurrent.atomic.AtomicInteger;

class SafeCounter {
    private final AtomicInteger count = new AtomicInteger(0);

    // Thread-safe: uses lock-free CPU instructions (CAS) to perform atomic increments
    public void increment() {
        count.incrementAndGet(); 
    }
}
```

---

## Case Study: Mutability Leakage

### Problem
A developer attempts to write an immutable configuration class, but leaves a mutability leak:
```java
public final class AppConfig {
    private final List<String> servers;

    public AppConfig(List<String> servers) {
        this.servers = servers; // BUG: stores direct reference to caller's list
    }

    public List<String> getServers() {
        return servers; // BUG: exposes mutable list reference
    }
}
```
If a caller changes the list they passed to the constructor, or calls `config.getServers().clear()`, they modify the internal state of the "immutable" config, violating thread safety.

### Solution
Use defensive copying:
```java
public final class AppConfig {
    private final List<String> servers;

    public AppConfig(List<String> servers) {
        this.servers = new ArrayList<>(servers); // Defensive copy
    }

    public List<String> getServers() {
        return Collections.unmodifiableList(servers); // Prevent modification
    }
}
```

---

## Common Mistakes

### 1. Assuming `volatile` Makes Compound Operations Thread-Safe
The `volatile` keyword guarantees **visibility** (changes are immediately written to main memory and read from it) and **ordering** (prevents instruction reordering). It does **NOT** guarantee atomicity.
```java
public class UnsafeVolatile {
    private volatile int count = 0;

    // Still not thread-safe under concurrent access!
    public void increment() {
        count++; 
    }
}
```

### 2. Not Declaring `long` or `double` as `volatile` on 32-bit JVMs
Without `volatile`, reads/writes to 64-bit variables (`long`, `double`) can suffer from "word tearing" where one thread writes the first 32 bits and another writes the last 32 bits, resulting in a corrupted value.
