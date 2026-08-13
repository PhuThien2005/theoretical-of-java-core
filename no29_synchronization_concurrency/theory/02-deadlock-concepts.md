# Synchronization and Concurrency - Part 2

| Concept | What to know |
| --- | --- |
| `Deadlock` | A situation where two or more threads are blocked forever, each waiting for a lock held by another thread. |
| `Livelock` | A scenario where threads actively change their states in response to each other, but fail to make any execution progress. |
| `Starvation` | A condition where a thread is perpetually denied access to shared resources or CPU cycles due to greedy threads or scheduling bias. |
| `Volatile` | A keyword ensuring that reads and writes to a field go directly to main memory, bypassing CPU cache. Prevents instruction reordering but does **not** ensure atomicity. |
| `Atomic classes:` | A suite of classes in `java.util.concurrent.atomic` utilizing lock-free Compare-And-Swap (CAS) hardware instructions to achieve thread safety. |
| `AtomicInteger` | An atomic wrapper around `int` (methods: `incrementAndGet()`, `compareAndSet()`). |
| `AtomicLong` | An atomic wrapper around `long`. |
| `AtomicBoolean` | An atomic wrapper around `boolean`. |

## Detailed Notes

### Deadlock and Livelock

#### Deadlock
Deadlock requires four concurrent conditions (Coffman conditions):
1. **Mutual Exclusion**: Resources are held exclusively.
2. **Hold and Wait**: Threads holding locks wait for additional locks.
3. **No Preemption**: Locks cannot be forcefully taken away from threads.
4. **Circular Wait**: Thread A holds Lock 1 and waits for Lock 2; Thread B holds Lock 2 and waits for Lock 1.

**Fix**: Eliminate circular wait by acquiring locks in a fixed, global ordering.

```java
// Deadlock prone
public void transfer(Account from, Account to, double amt) {
    synchronized (from) {
        synchronized (to) {
            // transfer
        }
    }
}
```

#### Livelock
Unlike deadlock, threads are not blocked. They actively consume CPU cycles, changing their state in reaction to other threads, but cannot complete the task.

### Volatile Visibility
Without `volatile`, updates made to a variable by Thread A may be cached in a CPU register/cache and remain invisible to Thread B reading from main memory.
`volatile` guarantees **visibility** and **ordering** (prevents JVM from reordering instructions around the variable). It does **not** guarantee atomicity.

```java
public class VolatileFlag implements Runnable {
    private volatile boolean active = true; // Volatile flag

    public void stop() { active = false; }

    @Override
    public void run() {
        while (active) {
            // Perform work
        }
        System.out.println("Stopped cleanly.");
    }
}
```

### Atomic Classes and Compare-And-Swap (CAS)
Atomic classes use lock-free CPU instructions (like `CMPXCHG` on x86) to perform atomic update cycles.
* **CAS Operation**: Takes expected value and new value. Updates only if current value equals expected value. Returns true if successful; otherwise, loops and retries.

```java
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    private final AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet(); // Lock-free atomic increment
    }

    public void updateMax(int newValue) {
        int current;
        do {
            current = count.get();
            if (newValue <= current) break;
        } while (!count.compareAndSet(current, newValue)); // CAS Loop
    }
}
```

---

## Case Study: Bank Account Transfer Lock Ordering

### Problem
In a banking app, if Customer A transfers to Customer B while Customer B concurrently transfers to Customer A, a deadlock can occur because Thread 1 locks A then B, while Thread 2 locks B then A.

### Solution
Establish a consistent locking order using hash codes or a unique account key.
```java
public class SafeBankTransfer {
    public void transfer(Account from, Account to, double amount) {
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);

        if (fromHash < toHash) {
            synchronized (from) {
                synchronized (to) {
                    doTransfer(from, to, amount);
                }
            }
        } else if (fromHash > toHash) {
            synchronized (to) {
                synchronized (from) {
                    doTransfer(from, to, amount);
                }
            }
        } else {
            // Tie-breaker lock in the rare case of hash collision
            synchronized (tieLock) {
                synchronized (from) {
                    synchronized (to) {
                        doTransfer(from, to, amount);
                    }
                }
            }
        }
    }
    private static final Object tieLock = new Object();
    private void doTransfer(Account from, Account to, double amt) {
        from.debit(amt);
        to.credit(amt);
    }
}
```

---

## Common Mistakes

### 1. Assuming `volatile` makes `count++` thread-safe
`count++` is a three-step compound operation: read, add 1, write back. Declaring `count` as `volatile` ensures other threads see the write, but does not prevent another thread from interleaving during the read and write steps.
```java
// BUG: Thread-unsafe
volatile int count = 0;
public void add() { count++; }
```

### 2. Nesting Locks Without Fixed Order
Nesting locks on resources dynamically passed as arguments is the primary cause of deadlocks in production environments.

## Why Deadlocks Occur and How to Avoid Them

A deadlock occurs in Java when two or more threads are blocked forever, each waiting for a lock that is held by another. For a deadlock to happen, four Coffman conditions must hold simultaneously: Mutual Exclusion (only one thread can hold a resource at a time), Hold and Wait (a thread holding resources can request additional ones), No Preemption (resources cannot be forcibly taken from a thread), and Circular Wait (a closed chain of threads exists where each holds a resource needed by the next). The most common trigger is nested locking in inconsistent orders. For example, if Thread A holds Lock 1 and requests Lock 2, while Thread B holds Lock 2 and requests Lock 1, both threads enter a blocked state forever. We can break the Circular Wait condition by enforcing a strict lock acquisition order or by using the Lock API's `tryLock()` with a timeout to avoid indefinite waiting.

### Mental Model: Deadlock Cycle
```
   ┌──────────┐  Holds  ┌──────────┐
   │ Thread A │ ──────► │  Lock 1  │
   └──────────┘         └──────────┘
        ▲                     │
     Waits For            Waits For
        │                     ▼
   ┌──────────┐  Holds  ┌──────────┐
   │  Lock 2  │ ◄────── │ Thread B │
   └──────────┘         └──────────┘
```

### Code Example
```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockAvoidance {
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    public void safeMethod() {
        // Avoid deadlock by attempting non-blocking acquisition
        boolean acquired1 = lock1.tryLock();
        boolean acquired2 = lock2.tryLock();
        try {
            if (acquired1 && acquired2) {
                // Critical section safely executed
            }
        } finally {
            if (acquired1) lock1.unlock();
            if (acquired2) lock2.unlock();
        }
    }

    public static void main(String[] args) {
        DeadlockAvoidance demo = new DeadlockAvoidance();
        demo.safeMethod();
        System.out.println("Execution Completed"); // Output: Execution Completed
    }
}
```

### Cause-Effect Chain
Nest locking on resources in different orders → Thread A acquires Lock 1, Thread B acquires Lock 2 → Thread A requests Lock 2, Thread B requests Lock 1 → Neither can proceed → Four deadlock conditions met → CPU utilization drops to zero for these threads → Thread execution freezes → Deadlock occurs → Broken by lock ordering or timeout-based `tryLock`.
