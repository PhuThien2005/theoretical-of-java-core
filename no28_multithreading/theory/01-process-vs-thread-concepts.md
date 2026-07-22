# Multithreading - Part 1

## Learning Goal

This file covers the fundamentals of thread creation, lifecycle states, and thread management. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Process vs Thread` | A process is an isolated execution environment with its own memory space; a thread is a lightweight path of execution within a process that shares memory with other threads of the same process. |
| `Create thread using:` | Threads can be created by subclassing `Thread`, implementing `Runnable`, or implementing `Callable` (tasks with return values). |
| `extends Thread` | Inheriting from `java.lang.Thread` and overriding `run()`. Limits class inheritance due to Java's single inheritance rule. |
| `implements Runnable` | Implementing the functional interface `Runnable` (with `run()`), separating task logic from execution. |
| `implements Callable` | Implementing the functional interface `Callable<V>` (with `call()`), which returns a result and can throw checked exceptions. |
| `ExecutorService` | A high-level thread management utility from `java.util.concurrent` that manages a pool of worker threads and decouples task submission from execution. |
| `Lifecycle of Thread` | The states a thread can transition through: `NEW`, `RUNNABLE`, `BLOCKED`, `WAITING`, `TIMED_WAITING`, and `TERMINATED`. |
| `New` | The state of a thread that is instantiated (`new Thread()`) but whose `start()` method has not yet been invoked. |

## Detailed Notes

### Process vs Thread
* **Process**: An operating system unit of resource allocation. Each process has its own address space, memory, and file handles. Communication between processes (IPC) is heavy and slow.
* **Thread**: A path of execution inside a process, often called a lightweight process. Threads share the process's memory (heap, method area), but each thread has its own private execution stack and local variable memory.
* **Key Difference**: Sharing memory makes thread communication extremely fast, but introduces data corruption risks (race conditions).

### Thread Creation Mechanisms

#### 1. Extends Thread
Inheriting from the `Thread` class and overriding its `run()` method.
```java
class CustomThread extends Thread {
    @Override
    public void run() {
        System.out.println("Running in thread: " + Thread.currentThread().getName());
    }
}

public class ThreadDemo {
    public static void main(String[] args) {
        CustomThread t = new CustomThread();
        t.start(); // Starts execution on a new thread
    }
}
```

#### 2. Implements Runnable
Implementing the functional interface `Runnable` and passing it to a `Thread` instance. This is the preferred approach as it preserves the single inheritance slot of the class.
```java
class Task implements Runnable {
    @Override
    public void run() {
        System.out.println("Task executing in: " + Thread.currentThread().getName());
    }
}

public class RunnableDemo {
    public static void main(String[] args) {
        Thread t = new Thread(new Task());
        t.start();
    }
}
```

#### 3. Implements Callable
`Callable<V>` is similar to `Runnable` but returns a value and can throw checked exceptions. It must be run using an executor or wrapped in a `FutureTask`.
```java
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

class CalculationTask implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        Thread.sleep(100);
        return 42;
    }
}

public class CallableDemo {
    public static void main(String[] args) throws Exception {
        FutureTask<Integer> futureTask = new FutureTask<>(new CalculationTask());
        Thread t = new Thread(futureTask);
        t.start();
        
        // Block and get the result
        Integer result = futureTask.get();
        System.out.println("Result: " + result); // Prints 42
    }
}
```

### ExecutorService
Managing threads manually is inefficient. `ExecutorService` pools threads and manages their lifecycles.
```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        executor.submit(() -> System.out.println("Task 1 in " + Thread.currentThread().getName()));
        executor.submit(() -> System.out.println("Task 2 in " + Thread.currentThread().getName()));
        
        executor.shutdown(); // Must shut down executors to release threads!
    }
}
```

### Thread Lifecycle and the `NEW` State
A thread is in the `NEW` state immediately after creation, before `start()` is called. At this stage, it exists purely as a Java object; no operating system thread resources have been allocated yet.

---

## Case Study: Extends Thread vs Implements Runnable

### Problem
A team wants to run a network polling task concurrently. They subclass `Thread` for the implementation:
```java
public class Poller extends Thread {
    public void run() { /* polling logic */ }
}
```
Later, they need the `Poller` to inherit database capabilities from a class `BaseDatabaseService`. Because Java only supports single class inheritance, they cannot inherit from both `Thread` and `BaseDatabaseService`.

### Solution
Refactor to implement `Runnable` instead. This keeps the task logic decoupled from the thread execution mechanism:
```java
public class Poller extends BaseDatabaseService implements Runnable {
    @Override
    public void run() { /* polling logic */ }
}

// Execution
Thread thread = new Thread(new Poller());
thread.start();
```

---

## Common Mistakes

### 1. Calling `.run()` instead of `.start()`
Calling `run()` directly does not spawn a new thread. It simply executes the `run()` method in the calling thread (synchronously).
```java
Thread t = new Thread(() -> System.out.println(Thread.currentThread().getName()));
t.run(); // Prints "main" (not thread name like Thread-0)
t.start(); // Correct: Prints "Thread-0"
```

### 2. Re-starting a Thread
A thread can only be started once. Attempting to start an already started or terminated thread throws a run-time exception.
```java
Thread t = new Thread(() -> {});
t.start();
t.start(); // Throws IllegalThreadStateException
```

### 3. Forgetting to Shut Down an `ExecutorService`
An `ExecutorService` creates non-daemon threads by default. If you do not call `shutdown()`, the JVM will remain running even after the `main` method terminates.

## Why Processes Differ from Threads

At the operating system level, a process represents an isolated execution context containing its own address space, file handles, socket descriptors, and security tokens. In contrast, a thread is a lightweight execution path that exists within a parent process. The Java Virtual Machine (JVM) reflects this operating system model within its memory allocation architecture. When a new thread is spawned, the JVM allocates a private runtime stack and program counter (PC) register for that specific thread, ensuring that local variable states and execution instructions remain isolated. However, all threads of a single JVM process share the common Heap and Metaspace regions, enabling direct memory access and extremely fast inter-thread communication. This shared access eliminates the CPU overhead of inter-process communication (IPC) protocols, but it introduces the risk of data corruption, race conditions, and visibility errors when multiple threads write to the same memory locations concurrently.

### Mental Model
```text
+-----------------------------------------------------------+
| OS PROCESS (Isolated Address Space, File Handles, etc.)   |
|   +-----------------------------------------------------+   |
|   | JVM Instance Memory                                 |   |
|   |  Shared Heap (Objects)  &  Metaspace (Class Metadata)|   |
|   |  [Object A] <--------------+-------------+          |   |
|   +----------------------------|-------------|----------+   |
|   | Thread 1 Stack & PC  | Thread 2 Stack & PC          |   |
|   |  [Local Variables 1] |  [Local Variables 2]         |   |
|   +----------------------+------------------------------+   |
+-----------------------------------------------------------+
```

### Code Example
```java
// A runnable class showcasing thread memory sharing vs process isolation
public class ProcessVsThread {
    private static int sharedCounter = 0; // Shared memory in the Heap

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) sharedCounter++;
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) sharedCounter++;
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        // Output can be less than 2000 due to unsynchronized shared memory access!
        System.out.println("Shared Counter: " + sharedCounter);
    }
}
/*
Possible Output:
Shared Counter: 1984
*/
```

### Cause-Effect Chain
1. Operating System launches JVM process &rarr; OS allocates isolated virtual address space and resources.
2. JVM spawns Java threads &rarr; Threads share the JVM Heap/Metaspace but get private Stack/PC registers.
3. Multiple threads access Heap objects &rarr; Thread communication is extremely fast without IPC overhead.
4. Unsynchronized parallel writes occur &rarr; Interleaved CPU instructions corrupt shared state (Race Condition).

