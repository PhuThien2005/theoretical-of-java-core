# Practice Exercises: Synchronization & Concurrency

This folder contains hands-on practice exercises to reinforce your understanding of advanced Java concurrency: explicit Locks, Conditions, thread signaling, and deadlocks.

## Exercises

### 1. Thread-Safe Bounded Queue (`thread-safe-bounded-queue`)
A bounded blocking queue is a thread-safe data structure that blocks producers when full and blocks consumers when empty.
- **Goal**: Implement a bounded queue `ThreadSafeBoundedQueue<T>` using `ReentrantLock` and two `Condition` variables (`notFull` and `notEmpty`) to coordinate thread signaling.

#### Directory Structure
- [ThreadSafeBoundedQueue.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/29-synchronization-concurrency/practice/thread-safe-bounded-queue/src/ThreadSafeBoundedQueue.java)
- [ThreadSafeBoundedQueueTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/29-synchronization-concurrency/practice/thread-safe-bounded-queue/test/ThreadSafeBoundedQueueTest.java)
- [ThreadSafeBoundedQueue.java (Solution)](file:///home/fhu_thjen/projects/learning-java/29-synchronization-concurrency/practice/thread-safe-bounded-queue/solution/ThreadSafeBoundedQueue.java)

---

### 2. Deadlock Simulator (`deadlock-simulator`)
A deadlock occurs when two or more threads block forever, each waiting for a lock held by another. The Java JVM provides diagnostic APIs in `ThreadMXBean` to detect deadlocked threads.
- **Goal**: Write a utility in `DeadlockSimulator` that simulates a classic resource deadlock between two daemon threads, detects the deadlock using `ThreadMXBean`, and returns the results.

#### Directory Structure
- [DeadlockSimulator.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/29-synchronization-concurrency/practice/deadlock-simulator/src/DeadlockSimulator.java)
- [DeadlockSimulatorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/29-synchronization-concurrency/practice/deadlock-simulator/test/DeadlockSimulatorTest.java)
- [DeadlockSimulator.java (Solution)](file:///home/fhu_thjen/projects/learning-java/29-synchronization-concurrency/practice/deadlock-simulator/solution/DeadlockSimulator.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 29-synchronization-concurrency
```
