package no28_multithreading.practice.parallel_task_executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Reference solution for ParallelTaskExecutorSolution.
 * 
 * Thread Pools & Executors:
 * - `Executors.newFixedThreadPool(n)` reuse a fixed number of threads.
 * - `invokeAll(tasks)` executes all callables concurrently, returning a list of `Future` objects
 *   representing the pending results. It blocks until all tasks complete.
 */
public class ParallelTaskExecutorSolution {

    private final ExecutorService executor;

    public ParallelTaskExecutorSolution(int threadCount) {
        if (threadCount <= 0) {
            throw new IllegalArgumentException("threadCount must be positive");
        }
        this.executor = Executors.newFixedThreadPool(threadCount);
    }

    public <T> List<T> executeTasks(List<Callable<T>> tasks) throws Exception {
        if (tasks == null) {
            throw new IllegalArgumentException("tasks list cannot be null");
        }

        // invokeAll automatically submits all tasks and blocks until all complete
        List<Future<T>> futures = executor.invokeAll(tasks);
        List<T> results = new ArrayList<>();

        for (Future<T> future : futures) {
            // Retrieve computed result (blocks if not finished, but invokeAll guarantees completion)
            results.add(future.get());
        }

        return results;
    }

    public void shutdown() {
        executor.shutdown();
    }
}
