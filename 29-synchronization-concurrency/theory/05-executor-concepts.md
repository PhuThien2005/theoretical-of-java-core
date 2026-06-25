# Synchronization and Concurrency - Part 5

## Learning Goal

This file covers the Java Executor Framework (`Executor`, `ExecutorService`, `ThreadPoolExecutor`, `ScheduledExecutorService`), asynchronous task results (`Future`), and advanced promise chaining (`CompletableFuture`). Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Executor` | The simplest interface defining task execution via `execute(Runnable)`. |
| `ExecutorService` | A sub-interface adding task lifecycle management, task submission returning a `Future` (`submit()`), and shutdown methods. |
| `ScheduledExecutorService` | A sub-interface that schedules tasks to run after a delay, or execute periodically. |
| `ThreadPoolExecutor` | The standard thread pool implementation, configured using parameters like core pool size, max pool size, queue capacity, and rejection handler. |
| `Executors` | A factory utility class containing static methods to create pre-configured thread pools (e.g. fixed, cached, scheduled). |
| `Future` | Represents the pending result of an asynchronous computation. Call `.get()` to block and retrieve the result. |
| `Callable` | A task representing a computation that returns a result and can throw a checked exception. |
| `CompletableFuture` | A class implementing `Future` and `CompletionStage` that supports functional callbacks, pipelined staging, and combining multiple asynchronous tasks. |

## Detailed Notes

### The ThreadPoolExecutor Parameters
To configure a custom thread pool safely, you must understand its core parameters:
1. **Core Pool Size**: The minimum number of threads kept alive in the pool, even if idle.
2. **Maximum Pool Size**: The maximum number of threads allowed in the pool.
3. **Keep Alive Time**: Time idle threads above the core size will wait before being terminated.
4. **Work Queue**: The `BlockingQueue` used to hold tasks before execution.
5. **Rejection Policy**: Handlers invoked when the pool and queue are saturated (e.g. `AbortPolicy` throws exception, `CallerRunsPolicy` executes task in the calling thread, `DiscardPolicy` silently drops task).

```java
import java.util.concurrent.*;

public class CustomPoolDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
            2,                              // Core threads
            4,                              // Max threads
            60, TimeUnit_SECONDS,           // Keep alive
            new ArrayBlockingQueue<>(10),    // Bounded queue
            new ThreadPoolExecutor.CallerRunsPolicy() // Backpressure handler
        );
        
        pool.submit(() -> System.out.println("Executing task"));
        pool.shutdown();
    }
    private static final TimeUnit TimeUnit_SECONDS = TimeUnit.SECONDS;
}
```

### ScheduledExecutorService
Used to run periodic or delayed tasks. Know the difference:
* `scheduleAtFixedRate(task, init, period, unit)`: Runs tasks at fixed intervals (e.g., every 5 seconds). If execution takes 6 seconds, the next task runs immediately (tasks do not overlap by default in a single thread, but interval is calculated from task start).
* `scheduleWithFixedDelay(task, init, delay, unit)`: Waits for the specified delay *after* the previous task completes before starting the next one.

```java
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PollingDemo {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        // Starts next task 3 seconds after previous completes
        scheduler.scheduleWithFixedDelay(
            () -> System.out.println("Polling API..."),
            0, 3, TimeUnit.SECONDS
        );
    }
}
```

### CompletableFuture Pipelines
`CompletableFuture` supports non-blocking callback chains.
```java
import java.util.concurrent.CompletableFuture;

public class AsyncChainDemo {
    public static void main(String[] args) throws Exception {
        CompletableFuture.supplyAsync(() -> "User Data")
            .thenApply(data -> data + " Processed")
            .thenAccept(System.out::println) // Consumes result
            .exceptionally(ex -> {
                System.out.println("Failed: " + ex.getMessage());
                return null;
            });
    }
}
```

---

## Case Study: Asynchronous E-Commerce Checkout Pipeline

### Problem
An online checkout system needs to process payments, update inventory, and send email confirmations. Performing these tasks sequentially in a single thread causes slow response times.

### Solution
Use `CompletableFuture` to coordinate parallel execution.
```java
import java.util.concurrent.CompletableFuture;

public class CheckoutProcessor {
    public void processCheckout(Order order) {
        // Step 1: Start payment process asynchronously
        CompletableFuture<PaymentResult> paymentFuture = 
            CompletableFuture.supplyAsync(() -> processPayment(order));

        // Step 2: Start inventory update concurrently
        CompletableFuture<InventoryResult> inventoryFuture = 
            CompletableFuture.supplyAsync(() -> updateInventory(order));

        // Step 3: Combine both steps to generate invoice
        paymentFuture.thenCombine(inventoryFuture, (pay, inv) -> generateInvoice(pay, inv))
            .thenAccept(invoice -> sendEmail(invoice)) // Step 4: Email customer
            .exceptionally(ex -> {
                logError(ex);
                return null;
            });
    }

    private PaymentResult processPayment(Order o) { return new PaymentResult(); }
    private InventoryResult updateInventory(Order o) { return new InventoryResult(); }
    private Invoice generateInvoice(PaymentResult p, InventoryResult i) { return new Invoice(); }
    private void sendEmail(Invoice inv) {}
    private void logError(Throwable t) {}

    static class Order {}
    static class PaymentResult {}
    static class InventoryResult {}
    static class Invoice {}
}
```

---

## Common Mistakes

### 1. Using Unbounded Queues in Production Pools
`Executors.newFixedThreadPool(n)` uses an unbounded `LinkedBlockingQueue`. If tasks arrive faster than they are processed, the queue grows infinitely, eventually causing an `OutOfMemoryError` (OOM).
* **Fix**: Always configure a bounded queue (like `ArrayBlockingQueue`) and define a rejection policy for production.

### 2. Blocking on `Future.get()` inside a Loop
Calling `.get()` immediately blocks the calling thread, turning parallel processing into slow synchronous execution.
```java
// BUG: Runs tasks one-by-one synchronously!
for (Callable<Integer> task : tasks) {
    Future<Integer> f = executor.submit(task);
    System.out.println(f.get()); // Blocks here!
}
```
* **Fix**: Submit all tasks to collect their `Future` objects first, then retrieve their results in a separate loop.

## Why ExecutorService and Thread Pools Are Required

Manually spawning threads for each task is highly inefficient and dangerous for the JVM. Every thread created in Java requires an operating system thread, which carries a substantial allocation overhead including a default stack footprint of about 1MB. If an application spawns threads without bounds, it will quickly exhaust system memory or file descriptors, causing crashes. `ExecutorService` addresses this by utilizing thread pools that maintain a managed, fixed set of active worker threads to process tasks concurrently. Furthermore, configuring bounded queues (such as `ArrayBlockingQueue`) inside the pool prevents incoming task pile-ups from consuming all memory, safeguarding the JVM from `OutOfMemoryError` via a structured rejection handler policy.

### Mental Model: Thread Pool with Bounded Queue
```
[Tasks Submitted] ──► [Bounded Queue (capacity = 100)]
                            │ (If full: Rejection Policy)
                            ▼
                    ┌─────────────────────────┐
                    │  Thread Pool (Workers)  │
                    │ ┌──────┐┌──────┐┌──────┐ │
                    │ │  T1  ││  T2  ││  T3  │ │
                    │ └──────┘└──────┘└──────┘ │
                    └─────────────────────────┘
```

### Code Example
```java
import java.util.concurrent.*;

public class ExecutorDemo {
    public static void main(String[] args) {
        // Safe thread pool with bounded queue and rejection policy
        ExecutorService executor = new ThreadPoolExecutor(
            2, 4, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10),
            new ThreadPoolExecutor.AbortPolicy()
        );

        try {
            executor.submit(() -> System.out.println("Executing Task"));
        } finally {
            executor.shutdown();
        }
        // Output:
        // Executing Task
    }
}
```

### Cause-Effect Chain
Manually spawn new thread for each request → High OS thread allocation overhead → 1MB stack memory consumed per thread → Memory exhaustion / system crash → Replace with `ExecutorService` → Reuses fixed set of worker threads → Bounded queues restrict task accumulation → ThreadPool Rejection Policy handles excess load → JVM protected against OutOfMemoryError.
