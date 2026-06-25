# Synchronization and Concurrency Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## synchronized

The `synchronized` keyword in Java is used to provide mutual exclusion and establish a happens-before relationship between threads. It ensures that only one thread at a time can execute a synchronized method or block locked on a particular object monitor.

* **Why it matters**: It prevents race conditions and data corruption by serializing access to shared mutable state. It also guarantees memory visibility, ensuring that changes made by one thread inside a synchronized block are visible to any other thread subsequently entering a block synchronized on the same lock object.
* **Common confusion**: Assuming `synchronized` blocks on different objects exclude each other. A `synchronized` block only blocks other threads trying to synchronize on the *same* lock object. Locking on separate objects allows threads to execute the protected blocks concurrently.
* **Small example**:
  ```java
  private final Object lock = new Object();
  public void safeIncrement() {
      synchronized (lock) {
          count++;
      }
  }
  ```

## deadlock

A deadlock is a runtime state where two or more threads are permanently blocked, each waiting for a lock held by one of the other threads.

* **Why it matters**: Deadlocks completely halt execution of the affected threads, leading to application hangs and unresponsive systems. They cannot resolve themselves and usually require a system restart.
* **Common confusion**: Confusing deadlock with starvation or livelock. In a deadlock, threads are physically suspended (BLOCKED state) and consume zero CPU. In a livelock, threads are actively running and changing states (consuming CPU) but making no progress.
* **Small example**:
  ```java
  // Thread 1 locks A then B; Thread 2 locks B then A
  // Both hold one lock and wait forever for the other
  ```

## atomic variable

Classes in `java.util.concurrent.atomic` (like `AtomicInteger`, `AtomicReference`) that support lock-free, thread-safe programming on single variables.

* **Why it matters**: They allow highly performant, concurrent read-write access to a single variable without the heavy overhead of OS-level thread suspension and context switches associated with lock-based synchronization.
* **Common confusion**: Assuming that grouping multiple atomic variable operations makes the entire sequence atomic. For example, `int x = atomicInt.get(); atomicInt.set(x + 1);` is *not* thread-safe, even though each individual method call is atomic. You must use compound operations like `compareAndSet` or `incrementAndGet`.
* **Small example**:
  ```java
  private final AtomicInteger counter = new AtomicInteger(0);
  public void increment() {
      counter.incrementAndGet(); // Thread-safe atomic increment
  }
  ```

## CAS (Compare-And-Swap)

A hardware-level atomic instruction used to implement lock-free synchronization. It compares the contents of a memory location to a given expected value and, only if they are equal, modifies the contents to a new given value.

* **Why it matters**: It is the foundational building block for all atomic classes and non-blocking data structures in Java. It allows threads to update values concurrently and safely by retrying (spinning) rather than blocking on a lock.
* **Common confusion**: Thinking CAS is implemented via Java software loops. Java's `compareAndSet` maps directly to native CPU assembly instructions (e.g., `lock cmpxchg` on x86 architectures), making it extremely fast.
* **Small example**:
  ```java
  AtomicInteger val = new AtomicInteger(10);
  boolean success = val.compareAndSet(10, 11); // returns true, val is now 11
  ```

## CyclicBarrier

A synchronization aid that allows a set of threads to all wait for each other to reach a common barrier point before continuing.

* **Why it matters**: It is extremely useful in parallel algorithms where multiple threads perform independent subtasks and must wait for all others to finish before moving to the next phase. Crucially, it can be reset and reused after the barrier trips.
* **Common confusion**: Confusing it with `CountDownLatch`. A `CyclicBarrier` requires threads to actively block at the barrier point using `await()` to decrement the count. A thread cannot decrement the barrier without blocking itself.
* **Small example**:
  ```java
  CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("Phase complete!"));
  // 3 threads calling barrier.await() will trip the barrier, run the runnable, and proceed
  ```

## CountDownLatch

A synchronization aid that allows one or more threads to wait until a set of operations being performed in other threads completes.

* **Why it matters**: It acts as a one-shot gate. It is ideal for coordinating startup phases, where a main thread blocks via `await()` until all initialization workers call `countDown()`.
* **Common confusion**: Trying to reuse a `CountDownLatch`. Once a latch's count reaches zero, its gate remains permanently open, and subsequent calls to `await()` return immediately. It cannot be reset; a new instance must be created.
* **Small example**:
  ```java
  CountDownLatch latch = new CountDownLatch(3);
  // Workers call latch.countDown();
  // Main thread blocks on latch.await() until count is 0
  ```

## Executor

An object that executes submitted `Runnable` tasks. This interface decouples task submission from the mechanics of how each task will be run, such as thread use, scheduling, etc.

* **Why it matters**: Decoupling allows developers to focus on defining tasks while the configuration of thread execution (pooling, scheduling) can be managed separately and modified without changing submission code.
* **Common confusion**: Thinking `Executor` is a thread pool. `Executor` is just a simple functional interface with a single `execute(Runnable)` method. Its sub-interfaces and implementations (like `ExecutorService` and `ThreadPoolExecutor`) provide the thread pooling logic.
* **Small example**:
  ```java
  Executor executor = command -> new Thread(command).start();
  executor.execute(() -> System.out.println("Running task"));
  ```

## ForkJoinPool

An `ExecutorService` implementation specifically designed for divide-and-conquer tasks using a work-stealing algorithm.

* **Why it matters**: It maximizes CPU core utilization by ensuring that idle worker threads steal tasks from the deques of busy threads, reducing thread starvation and keeping all cores active.
* **Common confusion**: Using `ForkJoinPool` for blocking I/O tasks. Because `ForkJoinPool` is designed for compute-intensive tasks, blocking threads inside the pool can starve other tasks. Use a standard `ThreadPoolExecutor` with a cached or fixed pool for blocking operations.
* **Small example**:
  ```java
  ForkJoinPool pool = ForkJoinPool.commonPool();
  Long sum = pool.invoke(new SumTask(largeArray, 0, largeArray.length));
  ```
