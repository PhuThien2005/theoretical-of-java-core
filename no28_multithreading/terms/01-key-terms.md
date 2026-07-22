# Multithreading Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## Process

An operating system-level execution environment that is allocated its own dedicated virtual address space, memory, file handles, and security context.

- **Why it matters**: Understanding processes is key to distinguishing JVM-level concurrency (which runs inside a single process) from distributed computing or multi-process systems. It helps developers realize that memory is shared among threads within a single JVM process, while process-level failures do not directly corrupt memory in other processes.
- **Common confusion**: Developers often confuse threads and processes. A process does not run code directly; it is a container for one or more threads. IPC (Inter-Process Communication) requires operating system mechanisms (like sockets or pipes), whereas inter-thread communication simply uses shared heap memory.
- **Small example**: Operating system processes can be inspected via CLI commands like `ps` (Linux) or Task Manager (Windows). The JVM runs as a single process (e.g., `java MyClass`).

## Thread

The smallest unit of execution scheduled by the operating system's scheduler. A thread runs code sequentially and belongs to exactly one process.

- **Why it matters**: Threads enable application responsiveness and high performance by executing multiple tasks concurrently on multi-core CPUs.
- **Common confusion**: Thinking threads run completely in parallel at all times. On a single-core CPU, threads run concurrently through time-slicing (fast switching), not in parallel. Also, a Java thread is typically mapped 1:1 to an operating system thread.
- **Small example**:
  ```java
  Thread thread = new Thread(() -> System.out.println("Running on a thread"));
  thread.start();
  ```

## Runnable

A functional interface representing a task that can be executed concurrently. It defines a single method: `public void run()`.

- **Why it matters**: It decouples the definition of a task from the thread execution mechanism. This allows tasks to be submitted to thread pools (`ExecutorService`) and executed without manual thread creation.
- **Common confusion**: Confusing `Runnable` with thread state `RUNNABLE`. Implementing `Runnable` is just defining a task; it does not start a thread or put anything in the running state until passed to a `Thread` and `start()` is invoked.
- **Small example**:
  ```java
  Runnable task = () -> System.out.println("Runnable task");
  new Thread(task).start();
  ```

## Callable

A functional interface similar to `Runnable` but representing a task that returns a result and can throw a checked exception. It defines `public V call() throws Exception`.

- **Why it matters**: Essential for retrieving calculation results from concurrent tasks or handling operations that might fail with checked exceptions (e.g., network queries, database lookups).
- **Common confusion**: Assuming `Callable` can be passed directly to a `Thread` constructor. It cannot. It must be wrapped in a `FutureTask` or submitted to an `ExecutorService`.
- **Small example**:
  ```java
  Callable<Integer> task = () -> 42;
  ExecutorService executor = Executors.newSingleThreadExecutor();
  Future<Integer> future = executor.submit(task);
  Integer result = future.get(); // Blocks until done, returns 42
  executor.shutdown();
  ```

## Start vs Run

`start()` is a lifecycle control method that creates a new OS thread and schedules it asynchronously; `run()` is a standard synchronous method call containing the task logic.

- **Why it matters**: Using the wrong method causes silent bugs where code runs synchronously on the calling thread (usually the `main` thread) instead of concurrently, negating any multithreading benefits.
- **Common confusion**: Calling `.run()` on a thread object and thinking it executes on a new thread. It executes synchronously in the caller's call stack.
- **Small example**:
  ```java
  Thread t = new Thread(() -> System.out.println(Thread.currentThread().getName()));
  t.run();   // Prints "main"
  t.start(); // Prints "Thread-0"
  ```

## Join

An instance method on `Thread` that blocks the calling thread until the target thread completes execution or the calling thread is interrupted.

- **Why it matters**: Crucial for thread coordination, such as waiting for parallel computations to finish before aggregating their results.
- **Common confusion**: Confusing which thread is blocked. If the `main` thread calls `threadB.join()`, it is the `main` thread that blocks, NOT `threadB`. `threadB` continues running normally.
- **Small example**:
  ```java
  Thread worker = new Thread(() -> { /* long task */ });
  worker.start();
  worker.join(); // Caller (main thread) blocks until worker is done
  ```

## Thread-Safety

A property of a class, method, or program stating that it functions correctly (maintains internal invariants) when accessed by multiple threads concurrently, without requiring external coordination.

- **Why it matters**: Prevents erratic behavior, data corruption, and crashes in concurrent environments.
- **Common confusion**: Believing that declaring a field `volatile` or wrapping a collection in `Collections.synchronizedList` automatically makes the entire class or sequence of operations thread-safe. Thread-safety requires protecting compound operations atomically.
- **Small example**: `AtomicInteger` is thread-safe, whereas a raw `int` increment is not.
  ```java
  AtomicInteger safeCounter = new AtomicInteger(0);
  safeCounter.incrementAndGet(); // Thread-safe atomic increment
  ```

## Race Condition

A concurrency defect where the correctness of a program depends on the relative timing or interleaving of threads.

- **Why it matters**: Race conditions lead to hard-to-reproduce, silent data corruption bugs that often pass local testing but fail in production under heavy load.
- **Common confusion**: Believing that race conditions only occur on high-performance servers. They can happen on any system where shared mutable state is accessed by multiple threads without synchronization.
- **Small example**: Two threads incrementing the same counter concurrently.
  ```java
  // Non-atomic read-modify-write: counter++
  counter++; 
  ```

## Data Visibility

The guarantee that changes made by one thread to a shared variable are visible to other threads when they read that variable.

- **Why it matters**: Due to CPU registers, L1/L2/L3 caches, and compiler optimizations (like instruction reordering), updates to memory by one thread can remain invisible to other threads indefinitely, leading to infinite loops or stale data.
- **Common confusion**: Assuming that if thread A writes a value to a field, thread B will see it immediately because they share the same heap. Without synchronization barriers (like `volatile` or `synchronized`), updates may never propagate to main memory or be read by B.
- **Small example**:
  ```java
  private volatile boolean flag = true; // volatile guarantees visibility
  ```
