# Synchronization and Concurrency - Part 6

## Learning Goal

This file covers the Fork/Join framework (`ForkJoinPool`, `RecursiveTask`, `RecursiveAction`) and the execution details of Parallel Streams. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `ForkJoinPool` | A specialized executor pool designed for divide-and-conquer tasks using a work-stealing algorithm. |
| `Parallel Stream` | A stream execution mode that partitions stream data and executes processing stages in parallel, utilizing the common `ForkJoinPool`. |

## Detailed Notes

### ForkJoinPool and Work-Stealing
The `ForkJoinPool` implements a **work-stealing algorithm**.
* Each worker thread maintains its own double-ended queue (deque) of tasks.
* When a worker thread runs out of tasks, it steals pending sub-tasks from the **back** of another busy thread's deque. This keeps all CPU cores saturated with minimal contention.
* Sub-tasks are defined using:
  1. `RecursiveAction`: for tasks that return no result (`void`).
  2. `RecursiveTask<V>`: for tasks that return a result of type `V`.

```java
import java.util.concurrent.RecursiveTask;

public class SumTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 1000;
    private final int[] array;
    private final int start;
    private final int end;

    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if ((end - start) <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) sum += array[i];
            return sum;
        } else {
            int mid = start + (end - start) / 2;
            SumTask left = new SumTask(array, start, mid);
            SumTask right = new SumTask(array, mid, end);
            
            left.fork(); // Run left subtask asynchronously
            long rightResult = right.compute(); // Run right subtask synchronously
            long leftResult = left.join(); // Wait for left subtask result
            
            return leftResult + rightResult;
        }
    }
}
```

### Parallel Streams and the Common Pool
Calling `.parallelStream()` or `.parallel()` on an existing stream splits the stream elements into chunks using a `Spliterator` and processes them concurrently.
* **Under the Hood**: All parallel streams run on a shared, JVM-wide pool: `ForkJoinPool.commonPool()`.
* **Important**: Because the pool is shared JVM-wide, any blocking or slow operations executed inside a parallel stream will starve *all* other parallel streams in the application.

---

## Case Study: Parallel Sum of Large Array

### Problem
Sum an array of 100 million integers. A single-threaded loop takes too long, and manually spinning up threads introduces too much coordination overhead.

### Solution
Use `ForkJoinPool` with `RecursiveTask` to divide and sum the array in parallel.
```java
import java.util.concurrent.ForkJoinPool;

public class ParallelSum {
    public static void main(String[] args) {
        int[] data = new int[10_000_000];
        for (int i = 0; i < data.length; i++) data[i] = 1;

        ForkJoinPool pool = ForkJoinPool.commonPool();
        SumTask task = new SumTask(data, 0, data.length);
        
        long totalSum = pool.invoke(task);
        System.out.println("Sum: " + totalSum);
    }
}
```

---

## Common Mistakes

### 1. Blocking the Common ForkJoinPool
Running blocking database queries, HTTP calls, or file reads inside a parallel stream.
```java
// BUG: Starves the JVM's shared pool!
list.parallelStream().forEach(url -> {
    try {
        HttpConnection.fetch(url); // Blocks worker thread
    } catch (Exception e) {}
});
```
* **Fix**: Use a dedicated thread pool (via a custom `ExecutorService`) for blocking I/O tasks. Keep parallel streams strictly for CPU-intensive computations.

### 2. Assuming Parallel Streams are Always Faster
Parallel streams introduce overhead (splitting the source, managing task deques, merging sub-results). For small collections, or collections that are expensive to split (like `LinkedList`), parallel streams can be significantly slower than a standard sequential loop.
* **Rule**: Only use parallel streams when:
  1. The data size is large (N) and the calculation per element is expensive (Q), such that $N \times Q$ is large.
  2. The collection splits easily (like `ArrayList` or arrays, unlike `LinkedList` or `BufferedReader.lines()`).
