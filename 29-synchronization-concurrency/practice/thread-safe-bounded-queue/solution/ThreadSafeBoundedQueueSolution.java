import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Reference solution for ThreadSafeBoundedQueueSolution.
 * 
 * Bounded Queue signaling:
 * - Lock protects shared state (queue operations).
 * - `notFull` condition blocks producers if queue is at capacity.
 * - `notEmpty` condition blocks consumers if queue is empty.
 * - Use loops (`while`) for checking conditions to prevent spurious wakeups.
 */
public class ThreadSafeBoundedQueueSolution<T> {

    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public ThreadSafeBoundedQueueSolution(int capacity) {
        this.capacity = capacity;
    }

    public void put(T item) throws InterruptedException {
        if (item == null) {
            throw new IllegalArgumentException("Cannot store null items");
        }
        lock.lock();
        try {
            // Loop checks prevent spurious wakeup errors
            while (queue.size() == capacity) {
                notFull.await(); // Releases lock, enters waiting queue
            }
            queue.add(item);
            notEmpty.signalAll(); // Notify consumers that item is available
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            T item = queue.poll();
            notFull.signalAll(); // Notify producers that space is available
            return item;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
}
