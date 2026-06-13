# Multithreading - Part 3

## Learning Goal

This file covers thread synchronization primitives (`join`), signaling (`interrupt`), daemon threads, and execution behavior. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `join` | An instance method (`thread.join()`) that blocks the calling thread until the target thread terminates. |
| `yield` | A static method (`Thread.yield()`) that suggests the scheduler pause the current thread to let other threads run. The scheduler is free to ignore this hint. |
| `interrupt` | A mechanism to signal a thread to stop what it is doing. Sets the thread's interrupt status and wakes up threads blocking in methods like `sleep()` or `wait()`. |
| `Daemon thread` | A background thread (like garbage collection) that does not keep the JVM alive. The JVM exits when only daemon threads remain. |
| `User thread` | A standard thread (such as the main thread). The JVM continues executing as long as at least one user thread is alive. |
| `Thread priority` | A numeric hint (1 to 10) to the OS thread scheduler. Behavior is highly platform-dependent and should not be relied on for program correctness. |
| `Race condition` | A concurrency bug where the program's outcome depends on the unpredictable interleaving of execution steps from multiple threads. |
| `Critical section` | A block of code that accesses a shared mutable resource and must not be concurrently accessed by multiple threads. |

## Detailed Notes

### Thread Join
`join()` is used to coordinate thread termination. The calling thread pauses until the target thread completes execution.
```java
public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            System.out.println("Worker done.");
        });

        worker.start();
        System.out.println("Waiting for worker...");
        worker.join(); // Main thread blocks here until worker finishes
        System.out.println("All work finished.");
    }
}
```

### Thread Interrupt
Interrupts are cooperative. Calling `thread.interrupt()` does not terminate the thread immediately; it merely sets an interrupt flag.
* If a thread is blocked in `sleep()`, `wait()`, or `join()`, it throws `InterruptedException` and **clears** its interrupt flag.
* If a thread is executing normal CPU operations, it must periodically check its flag using `Thread.currentThread().isInterrupted()`.

```java
public class InterruptDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                // Perform CPU intensive task
            }
            System.out.println("Worker stopped via interrupt.");
        });

        worker.start();
        Thread.sleep(500);
        worker.interrupt(); // Signal worker to stop
    }
}
```

### Daemon vs User Threads
By default, newly created threads inherit the daemon status of the creating thread. You can change this using `thread.setDaemon(boolean)`.
* **Important**: You must call `setDaemon()` **before** starting the thread. Calling it on a running thread throws `IllegalThreadStateException`.

```java
public class DaemonDemo {
    public static void main(String[] args) {
        Thread daemon = new Thread(() -> {
            while (true) {
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        });
        daemon.setDaemon(true); // Must be set before start
        daemon.start();
        
        System.out.println("Main thread ending. JVM will exit despite daemon running.");
    }
}
```

### Critical Sections and Race Conditions
A race condition occurs when multiple threads read and write a shared variable concurrently without synchronization.
```java
class Counter {
    private int count = 0;

    public void increment() {
        count++; // CRITICAL SECTION. Non-atomic: read, modify, write.
    }
    
    public int getCount() { return count; }
}
```

---

## Case Study: Worker Coordinator Pattern

### Problem
A reporting system needs to fetch data from three external APIs concurrently. Once all APIs return data, the system compiles the final report.

### Solution
Use `join()` to coordinate the workers.
```java
import java.util.ArrayList;
import java.util.List;

public class ReportCoordinator {
    public static void main(String[] args) throws InterruptedException {
        List<Thread> workers = new ArrayList<>();
        
        // Start 3 workers
        for (int i = 1; i <= 3; i++) {
            final int id = i;
            Thread t = new Thread(() -> {
                System.out.println("Worker " + id + " fetching data...");
                try { Thread.sleep(1000 * id); } catch (InterruptedException e) {}
            });
            workers.add(t);
            t.start();
        }
        
        // Wait for all workers to finish
        for (Thread t : workers) {
            t.join();
        }
        
        System.out.println("All worker data collected. Compiling report.");
    }
}
```

---

## Common Mistakes

### 1. Swallowing InterruptedException
Swallowing `InterruptedException` clears the thread's interrupt status, meaning higher-level code won't know the thread was requested to stop.
```java
// BAD
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    // Swallowed and ignored
}

// GOOD: Restore the interrupt flag so caller knows
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    Thread.currentThread().interrupt(); 
}
```

### 2. Calling `setDaemon()` on a Running Thread
```java
Thread t = new Thread(() -> {});
t.start();
t.setDaemon(true); // Throws IllegalThreadStateException
```

### 3. Relying on `Thread.yield()` or Thread Priorities for Correctness
Thread scheduling is platform-dependent. The JVM specification makes no guarantees about how priorities are mapped to OS priorities or how `yield()` behaves. Code that relies on them for synchronization is buggy.
