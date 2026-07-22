# Practice Exercises: Multithreading

This folder contains hands-on practice exercises to reinforce your understanding of Java threads, basic runnable tasks, preventing race conditions, and executing concurrent tasks using `ExecutorService`.

## Exercises

### 1. Thread-Safe Counter (`thread-safe-counter`)
When multiple threads read and modify a shared mutable variable without synchronization, updates can be lost, causing race conditions.
- **Goal**: Implement a `ThreadSafeCounter` and launch multiple parallel threads that increment the counter concurrently. The starter code should demonstrate a race condition (returning less than the expected total), and the solution should resolve it using synchronized blocks or methods.

#### Directory Structure
- [ThreadSafeCounter.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no28_multithreading/practice/thread-safe-counter/src/ThreadSafeCounter.java)
- [ThreadSafeCounterTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no28_multithreading/practice/thread-safe-counter/test/ThreadSafeCounterTest.java)
- [ThreadSafeCounter.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no28_multithreading/practice/thread-safe-counter/solution/ThreadSafeCounter.java)

---

### 2. Parallel Task Executor (`parallel-task-executor`)
Launching raw threads manually is resource-heavy. Java's Concurrency Utilities provide `ExecutorService` to manage thread pools and handle tasks returning values via `Callable` and `Future`.
- **Goal**: Implement a `ParallelTaskExecutor` that runs a list of `Callable` tasks concurrently in a fixed thread pool, aggregates their results, and shuts down the executor pool.

#### Directory Structure
- [ParallelTaskExecutor.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no28_multithreading/practice/parallel-task-executor/src/ParallelTaskExecutor.java)
- [ParallelTaskExecutorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no28_multithreading/practice/parallel-task-executor/test/ParallelTaskExecutorTest.java)
- [ParallelTaskExecutor.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no28_multithreading/practice/parallel-task-executor/solution/ParallelTaskExecutor.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no28_multithreading
```
