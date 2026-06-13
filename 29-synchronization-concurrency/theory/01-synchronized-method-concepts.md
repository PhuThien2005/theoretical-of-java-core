# Synchronization and Concurrency - Part 1

## Learning Goal

This file covers Java's intrinsic locking primitives, monitors, and the wait/notify signaling mechanism. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `synchronized method` | Locks the object instance (`this`) for instance methods, or the `Class` object for static methods, preventing other threads from executing any synchronized methods on that lock. |
| `synchronized block` | Locks a specific object reference, allowing finer-grained locking compared to synchronized methods. |
| `Object lock` | Intrinsic lock (monitor) associated with a specific object instance. Acquired via instance synchronized methods or synchronized blocks locking on that instance. |
| `Class lock` | Intrinsic lock associated with a class's `java.lang.Class` object. Acquired via static synchronized methods or synchronized blocks locking on the class literal (e.g. `MyClass.class`). |
| `Monitor` | The underlying synchronization mechanism (using bytecode instructions `monitorenter` and `monitorexit`) that controls mutual exclusion and wait-set signaling. |
| `wait` | An inherited method on `java.lang.Object` that releases the monitor lock and places the calling thread in the object's wait set. |
| `notify` | Wakes up a single arbitrary thread waiting in the object's monitor wait set. The awakened thread must re-acquire the lock before continuing. |
| `notifyAll` | Wakes up all threads waiting in the object's monitor wait set. Recommended over `notify` to avoid lost signal bugs. |

## Detailed Notes

### Synchronized Methods vs Synchronized Blocks

#### Synchronized Method
Acquires the lock associated with the receiver object (`this` for instance methods, class object for static methods).
```java
public class Counter {
    private int count = 0;

    // Locks 'this' Counter instance
    public synchronized void increment() {
        count++;
    }
}
```

#### Synchronized Block
Allows locking on a specific, private monitor object. This prevents external code from locking the same instance, reducing the risk of deadlock or accidental starvation.
```java
public class BetterCounter {
    private int count = 0;
    private final Object lock = new Object(); // Private lock object

    public void increment() {
        synchronized (lock) { // Only locks the private monitor
            count++;
        }
    }
}
```

### Object Lock vs Class Lock
* **Object Lock**: Guards instance fields. Two different threads can execute synchronized instance methods on *different* instances of the class concurrently.
* **Class Lock**: Guards static fields. Only one thread can execute static synchronized methods in the entire JVM for that class, regardless of how many class instances exist.

```java
class Demo {
    // Class lock (locks Demo.class)
    public static synchronized void staticMethod() {}
    
    // Object lock (locks 'this' Demo instance)
    public synchronized void instanceMethod() {}
}
```

### Wait and Notify Signaling Contract
The methods `wait()`, `notify()`, and `notifyAll()` are used to coordinate state changes.
* **Lock Ownership Requirement**: A thread **must** own the target object's monitor lock before calling these methods. Otherwise, an `IllegalMonitorStateException` is thrown at runtime.
* **Loop Requirement for Wait**: Spurious wakeups (threads waking up without being notified) are allowed by the JVM/OS. Therefore, `wait()` must always be called inside a `while` loop that checks the condition.

```java
synchronized (lock) {
    while (!condition) {
        lock.wait(); // Releases lock, blocks thread
    }
    // Condition is true, perform work
}
```

---

## Case Study: Bounded Blocking Queue

### Problem
Implement a thread-safe bounded queue where a producer blocks if the queue is full, and a consumer blocks if the queue is empty.

### Solution
Use a private lock and `wait()` / `notifyAll()` signaling.
```java
import java.util.LinkedList;
import java.util.Queue;

public class BoundedQueue<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;
    private final Object lock = new Object();

    public BoundedQueue(int capacity) {
        this.capacity = capacity;
    }

    public void put(T item) throws InterruptedException {
        synchronized (lock) {
            while (queue.size() == capacity) {
                lock.wait(); // Wait for space to open up
            }
            queue.add(item);
            lock.notifyAll(); // Wake up consumers waiting for data
        }
    }

    public T take() throws InterruptedException {
        synchronized (lock) {
            while (queue.isEmpty()) {
                lock.wait(); // Wait for data to arrive
            }
            T item = queue.poll();
            lock.notifyAll(); // Wake up producers waiting for space
            return item;
        }
    }
}
```

---

## Common Mistakes

### 1. Swallowing Spurious Wakeups
Using an `if` statement instead of a `while` loop when calling `wait()`.
```java
// BUG
synchronized(lock) {
    if (queue.isEmpty()) {
        lock.wait(); // Might wake up spuriously and poll null!
    }
    return queue.poll();
}

// FIX: Always loop
synchronized(lock) {
    while (queue.isEmpty()) {
        lock.wait();
    }
    return queue.poll();
}
```

### 2. Locking on Shared or Mutable Objects
Locking on String literals, Boolean wrappers, or primitive wrappers is extremely dangerous.
* String literals are pooled. If another unrelated library locks on the same string literal, it can freeze your application (causing deadlock).
* Mutable locks (objects whose fields change) can cause threads to lock on different instances, bypassing the mutual exclusion check.
* **Rule**: Always lock on `private final Object lock = new Object();`.

### 3. Calling `wait()` or `notify()` without holding the monitor lock
```java
Object lock = new Object();
lock.notify(); // Throws IllegalMonitorStateException
```
