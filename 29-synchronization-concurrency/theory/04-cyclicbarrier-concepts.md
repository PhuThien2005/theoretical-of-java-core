# Synchronization and Concurrency - Part 4

## Learning Goal

This file covers high-level concurrent collections (`ConcurrentHashMap`, `CopyOnWriteArrayList`, `BlockingQueue`) and synchronization barriers (`CyclicBarrier`, `Phaser`). Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `CyclicBarrier` | A reusable synchronization barrier where a fixed number of threads must wait for each other before proceeding. |
| `Phaser` | A flexible, reusable synchronization barrier that supports dynamic registration of parties and multi-phase execution. |
| `BlockingQueue` | A thread-safe queue interface that blocks putting threads if full, and taking threads if empty. |
| `Concurrent collections:` | Special thread-safe collections in `java.util.concurrent` optimized for high concurrent throughput without global locking. |
| `ConcurrentHashMap` | A high-performance, thread-safe hash map that uses fine-grained lock striping and CAS operations. Reads are non-blocking. |
| `CopyOnWriteArrayList` | A thread-safe list that creates a fresh copy of the underlying array upon any write operation. Efficient for read-heavy scenarios. |
| `ConcurrentLinkedQueue` | An unbounded thread-safe queue based on lock-free, concurrent node links (using CAS). |
| `Executor Framework:` | A library framework that simplifies asynchronous task execution by pooling and managing worker threads. |

## Detailed Notes

### CyclicBarrier vs CountDownLatch
* **CountDownLatch**: Cannot be reset. One thread waits, other threads decrement.
* **CyclicBarrier**: Reusable (resets count after passing). Threads wait for each other at a common barrier point via `barrier.await()`. Can execute an optional "barrier action" runnable when all threads arrive.

```java
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("Phase completed!"));

        Runnable worker = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " arriving.");
                barrier.await(); // Wait for all 3 threads
            } catch (Exception e) {}
        };

        new Thread(worker, "Thread-1").start();
        new Thread(worker, "Thread-2").start();
        new Thread(worker, "Thread-3").start();
    }
}
```

### Concurrent Collections: ConcurrentHashMap vs SynchronizedMap
* `Collections.synchronizedMap()` locks the *entire* map for every read and write operation, causing severe thread contention.
* `ConcurrentHashMap` partitions the map into lock stripes or buckets. Multiple threads can read concurrently without locking, and write concurrently to different buckets.
* **Important**: Compound operations (like check-then-act) are not safe on `ConcurrentHashMap` unless using atomic methods like `putIfAbsent()`, `replace()`, or `computeIfAbsent()`.

```java
import java.util.concurrent.ConcurrentHashMap;

public class MapDemo {
    private final ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    // Thread-safe compound operation
    public void increment(String key) {
        map.compute(key, (k, v) -> (v == null) ? 1 : v + 1);
    }
}
```

### CopyOnWriteArrayList
Mutating operations (add, set, remove) copy the entire backing array. This is expensive for writes but makes reads extremely fast and lock-free. Iterators read a snapshot of the array and never throw `ConcurrentModificationException`.

---

## Case Study: Event Listener Registry

### Problem
A GUI framework has a core class that fires events to a list of registered listeners. Listeners can be added or removed dynamically, and sometimes a listener attempts to unsubscribe *while* an event is being broadcasted (leading to `ConcurrentModificationException` with a standard `ArrayList`).

### Solution
Use `CopyOnWriteArrayList`.
```java
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventPublisher {
    // Highly read-heavy: events are fired frequently, listeners change rarely.
    private final List<Listener> listeners = new CopyOnWriteArrayList<>();

    public void addListener(Listener l) {
        listeners.add(l);
    }

    public void removeListener(Listener l) {
        listeners.remove(l);
    }

    public void publishEvent(String event) {
        // Safe lock-free iteration. No ConcurrentModificationException even if
        // a listener calls removeListener() inside onEvent().
        for (Listener l : listeners) {
            l.onEvent(event);
        }
    }

    interface Listener {
        void onEvent(String msg);
    }
}
```

---

## Common Mistakes

### 1. Using CopyOnWriteArrayList for Write-Heavy Lists
If you write to a `CopyOnWriteArrayList` inside a loop, it copies the entire array on every single iteration, destroying performance and causing massive GC pressure.
```java
// BUG: Massive array copy overhead
CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
for (int i = 0; i < 10000; i++) {
    list.add(i); // Copies array 10,000 times!
}
```

### 2. Check-Then-Act Bugs with ConcurrentHashMap
Assuming that checking a value in `ConcurrentHashMap` and then acting on it is atomic.
```java
// BUG: Race condition! Two threads could see containsKey as false and both insert.
if (!map.containsKey("key")) {
    map.put("key", newValue);
}

// FIX: Use atomic computeIfAbsent
map.computeIfAbsent("key", k -> newValue);
```

## Why CyclicBarrier and CountDownLatch Differ

`CountDownLatch` and `CyclicBarrier` are concurrency utilities designed for thread synchronization, but they differ significantly in reusability and execution mechanisms. `CountDownLatch` operates as a one-shot gate; threads decrement its counter by calling `countDown()` and block on `await()` until the count reaches zero, at which point the latch cannot be reset or reused. Conversely, `CyclicBarrier` is fully reusable and synchronizes threads at a common barrier point. When threads call `await()` on a `CyclicBarrier`, they block until the specified number of threads arrive. Once the barrier count reaches zero, the barrier trips, executes an optional barrier action, resets its internal counter back to its initial state, and releases all waiting threads to proceed.

### Mental Model: CountDownLatch vs. CyclicBarrier
```
CountDownLatch (One-shot):
Threads ──► countDown() ──► [Count: 3 -> 2 -> 1 -> 0] ──► Gate Opens (Cannot reuse)

CyclicBarrier (Reusable):
Thread 1 ──► await() ──┐
Thread 2 ──► await() ──┼─► [Count: 3 -> 0] ─► Trip ─► Run Action ─► Reset to 3 ─► Release
Thread 3 ──► await() ──┘
```

### Code Example
```java
import java.util.concurrent.CyclicBarrier;

public class BarrierDemo {
    public static void main(String[] args) {
        // A barrier for 2 threads with a reusable barrier action
        CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            System.out.println("Barrier Tripped!");
        });

        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " arriving");
                barrier.await(); // Thread blocks until count is 2
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        new Thread(task, "Thread-1").start();
        new Thread(task, "Thread-2").start();
        // Output:
        // Thread-1 arriving
        // Thread-2 arriving
        // Barrier Tripped!
    }
}
```

### Cause-Effect Chain
Threads invoke `barrier.await()` → Internal lock acquired → Arrival count decremented → Count is non-zero → Threads wait on a Condition → Final thread invokes `await()` → Count reaches zero → Optional barrier action runs → Barrier resets count and generation → Condition signals all → All threads released → Barrier ready for next cycle.
