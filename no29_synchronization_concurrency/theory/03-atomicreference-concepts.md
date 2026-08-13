# Synchronization and Concurrency - Part 3

| Concept | What to know |
| --- | --- |
| `AtomicReference` | Provides lock-free, atomic operations on object references using Compare-And-Swap. |
| `Lock API:` | The `java.util.concurrent.locks` framework providing more flexible, powerful locking capabilities than `synchronized` blocks. |
| `Lock` | The root interface defining lock acquisition operations (`lock()`, `tryLock()`, `unlock()`). |
| `ReentrantLock` | A mutual exclusion lock with the same behavior as intrinsic monitor locks, but offering features like fairness, timeouts, and interruptible lock acquisition. |
| `ReadWriteLock` | A lock pair that allows multiple threads to read concurrently, but restricts write access exclusively to one thread. |
| `StampedLock` | An advanced lock featuring three modes (write, read, optimistic read) and stamp-based validation. It is **not** reentrant. |
| `Semaphore` | A synchronizer that maintains a set of permits to restrict concurrent access to a resource pool. |
| `CountDownLatch` | A synchronization aid that allows one or more threads to wait until a set of operations performed in other threads completes. |

## Detailed Notes

### Lock API and ReentrantLock
Unlike `synchronized` blocks, which are structured and block-scoped, explicit `Lock` objects require manual lock acquisition and release.
* **Important**: You must always call `unlock()` inside a `finally` block to prevent resource leaks in case of exceptions.

```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExplicitLockDemo {
    private final Lock lock = new ReentrantLock();

    public void performTask() {
        lock.lock(); // Blocks until acquired
        try {
            // Critical section
        } finally {
            lock.unlock(); // Always release in finally block!
        }
    }
}
```

### ReadWriteLock
Allows high concurrency for read-heavy operations. Multiple read threads can hold the read lock simultaneously, but the write lock is exclusive.
```java
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheDemo {
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private String data = "";

    public String read() {
        rwLock.readLock().lock();
        try { return data; }
        finally { rwLock.readLock().unlock(); }
    }

    public void write(String val) {
        rwLock.writeLock().lock();
        try { data = val; }
        finally { rwLock.writeLock().unlock(); }
    }
}
```

### StampedLock (Optimistic Reading)
`StampedLock` provides a lock stamp. It supports "optimistic reading", which allows read threads to acquire data without blocking writes. If a write occurs during the read, the stamp is validated as invalid, and the reader retries with a pessimistic read lock.
```java
import java.util.concurrent.locks.StampedLock;

public class StampedLockDemo {
    private final StampedLock lock = new StampedLock();
    private double x, y;

    public double getDistance() {
        long stamp = lock.tryOptimisticRead(); // Non-blocking read
        double curX = x, curY = y;
        
        if (!lock.validate(stamp)) { // Check if a write occurred
            stamp = lock.readLock(); // Fallback to pessimistic read lock
            try {
                curX = x; curY = y;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return Math.sqrt(curX * curX + curY * curY);
    }
}
```

### Semaphore and CountDownLatch
* **Semaphore**: Controls resource usage via permits. Thread calls `acquire()` to take a permit (blocking if none exist) and `release()` to return it.
* **CountDownLatch**: A one-time gate. Threads call `await()` to block until other threads call `countDown()` enough times to reduce the latch count to 0.

```java
import java.util.concurrent.CountDownLatch;

public class LatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        Runnable worker = () -> {
            System.out.println("Step finished.");
            latch.countDown();
        };

        new Thread(worker).start();
        new Thread(worker).start();

        latch.await(); // Blocks until count becomes 0
        System.out.println("All steps completed.");
    }
}
```

---

## Case Study: Bounded Database Connection Pool via Semaphore

### Problem
A database connection pool has a hard limit of 5 physical connections. If more than 5 threads attempt to acquire a connection concurrently, they should block until a connection is released.

### Solution
Wrap connection access with a `Semaphore`.
```java
import java.util.concurrent.Semaphore;

public class ConnectionPool {
    private final Semaphore semaphore = new Semaphore(5); // Maximum 5 connections
    private final Connection[] connections = new Connection[5];
    private final boolean[] used = new boolean[5];

    public Connection getConnection() throws InterruptedException {
        semaphore.acquire(); // Blocks if all 5 connections are in use
        return getNextAvailableConnection();
    }

    public void releaseConnection(Connection c) {
        if (markAsFree(c)) {
            semaphore.release(); // Releases a permit, waking up a blocked thread
        }
    }

    private synchronized Connection getNextAvailableConnection() {
        for (int i = 0; i < 5; i++) {
            if (!used[i]) {
                used[i] = true;
                return connections[i];
            }
        }
        return null;
    }

    private synchronized boolean markAsFree(Connection c) {
        for (int i = 0; i < 5; i++) {
            if (connections[i] == c) {
                if (used[i]) {
                    used[i] = false;
                    return true;
                }
            }
        }
        return false;
    }
    
    private static class Connection {} // Stub class
}
```

---

## Common Mistakes

### 1. Leaking Locks (Forgetting to Unlock in Finally)
If an exception occurs inside the critical section and `unlock()` is not inside a `finally` block, the lock remains held forever, causing deadlocks for other threads.
```java
// BUG
lock.lock();
doTask(); // If this throws RuntimeException, lock is leaked!
lock.unlock();
```

### 2. Self-Deadlock with StampedLock (Non-Reentrant)
Unlike `ReentrantLock`, `StampedLock` is **not** reentrant. A thread holding a StampedLock write lock that attempts to acquire it again will deadlock itself.
```java
StampedLock lock = new StampedLock();
long s1 = lock.writeLock();
long s2 = lock.writeLock(); // DEADLOCK: blocks waiting for its own lock!
```

## Why Atomic Variables Avoid Lock-Based Synchronization

Atomic variables avoid lock-based synchronization by utilizing lock-free algorithms powered by hardware-level Compare-And-Swap (CAS) instructions. In contrast to `synchronized` blocks which suspend threads using OS-level context switching, CAS relies on CPU instructions like `CMPXCHG`. The CAS operation takes three arguments: a memory address, the expected current value at that address, and a new target value. If the value at the memory address matches the expected value, the CPU updates it to the new value in a single, atomic instruction. If another thread modified the value in the meantime, the check fails, and the calling thread loops (spins) to retry the operation with the updated value rather than blocking.

### Mental Model: CAS Spin Loop
```
   [ Thread A ] ──► Read Value (V=5)
                         │
         Update local copy to (New=6)
                         │
             CAS(Address, V=5, New=6)
                         │
        ┌────────────────┴────────────────┐
        ▼ (Expected == Actual)            ▼ (Expected != Actual)
   [ SUCCESS: V becomes 6 ]     [ FAIL: Spin & retry with V=actual ]
```

### Code Example
```java
import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {
    private final AtomicInteger value = new AtomicInteger(0);

    public void safeIncrement() {
        int expected;
        int next;
        do {
            expected = value.get();
            next = expected + 1;
        } while (!value.compareAndSet(expected, next)); // CAS loop
    }

    public static void main(String[] args) {
        CASDemo demo = new CASDemo();
        demo.safeIncrement();
        System.out.println("Value: " + demo.value.get()); // Output: Value: 1
    }
}
```

### Cause-Effect Chain
Thread reads memory address → Local variable holds expected value → Thread computes new value → Thread executes hardware CAS (`compareAndSet`) → CPU compares current memory value to expected value → Value matches → Atomic update succeeds → Value does not match → CAS returns false → Thread loops back and retries (spins) → Thread safety achieved without blocking overhead.
