package no28_multithreading.practice.thread_safe_counter;

/**
 * Starter template for a thread-safe counter.
 */
public class ThreadSafeCounter {

    private int count = 0;

    /**
     * Increments the counter by 1.
     * 
     * Requirements:
     * - Protect this method against race conditions so that multiple threads
     *   can call it concurrently without losing increments.
     */
    public void increment() {
        // TODO: Protect this increment operation
        count++;
    }

    public int getCount() {
        return count;
    }
}
