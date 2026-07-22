package no28_multithreading.practice.parallel_task_executor;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Starter template for a thread-pool task executor.
 */
public class ParallelTaskExecutor {

    /**
     * Initializes the task executor with a thread pool of the specified size.
     */
    public ParallelTaskExecutor(int threadCount) {
        // TODO: Initialize ExecutorService
    }

    /**
     * Executes all tasks in parallel using the thread pool and blocks until they finish,
     * returning their results.
     *
     * @param tasks the list of tasks to execute
     * @return the list of results
     * @throws Exception if execution fails
     */
    public <T> List<T> executeTasks(List<Callable<T>> tasks) throws Exception {
        // TODO: Submit tasks, collect Future values, block and retrieve results
        return null;
    }

    /**
     * Shuts down the executor pool.
     */
    public void shutdown() {
        // TODO: Shut down ExecutorService
    }
}
