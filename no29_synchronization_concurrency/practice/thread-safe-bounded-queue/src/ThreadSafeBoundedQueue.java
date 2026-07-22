package no29_synchronization_concurrency.practice.thread_safe_bounded_queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Starter template for a thread-safe bounded blocking queue.
 */
public class ThreadSafeBoundedQueue<T> {

    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    private final ReentrantLock lock = new ReentrantLock();
    // TODO: Define two conditions (notFull, notEmpty) from the lock

    public ThreadSafeBoundedQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Inserts an item into the queue.
     * Blocks if the queue is full.
     */
    public void put(T item) throws InterruptedException {
        // TODO: Implement putting with ReentrantLock and Conditions
    }

    /**
     * Retrieves and removes the head of the queue.
     * Blocks if the queue is empty.
     */
    public T take() throws InterruptedException {
        // TODO: Implement taking with ReentrantLock and Conditions
        return null;
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
