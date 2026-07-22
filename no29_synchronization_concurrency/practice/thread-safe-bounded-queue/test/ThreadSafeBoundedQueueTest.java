package no29_synchronization_concurrency.practice.thread_safe_bounded_queue;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Test runner for ThreadSafeBoundedQueue.
 */
public class ThreadSafeBoundedQueueTest {

    public static void main(String[] args) {
        try {
            testBasicOperations();
            testBlockingProducerBehavior();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: [" + expected + "], Actual: [" + actual + "])");
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testBasicOperations() throws InterruptedException {
        ThreadSafeBoundedQueue<String> queue = new ThreadSafeBoundedQueue<>(3);
        queue.put("A");
        queue.put("B");
        assertEquals(2, queue.size(), "Size matches 2");
        assertEquals("A", queue.take(), "Takes first inserted (FIFO)");
        assertEquals("B", queue.take(), "Takes second inserted");
        assertEquals(0, queue.size(), "Empty size");
    }

    private static void testBlockingProducerBehavior() throws InterruptedException {
        // Queue size of 2
        ThreadSafeBoundedQueue<Integer> queue = new ThreadSafeBoundedQueue<>(2);
        queue.put(1);
        queue.put(2);

        AtomicBoolean producerCompleted = new AtomicBoolean(false);

        // This producer will block because queue is full
        Thread producer = new Thread(() -> {
            try {
                queue.put(3);
                producerCompleted.set(true);
            } catch (InterruptedException e) {
                // Ignore
            }
        });
        producer.start();

        // Give producer time to run and block
        Thread.sleep(100);
        
        // Assert producer has not completed yet
        assertTrue(!producerCompleted.get(), "Producer must block on a full queue");
        assertEquals(2, queue.size(), "Queue size remains capped at 2");

        // Consume one element, which should trigger condition signal and unblock producer
        int value = queue.take();
        assertEquals(1, value, "Take first item");

        // Wait for producer to finish putting key 3
        producer.join(1000);

        assertTrue(producerCompleted.get(), "Producer should unblock after consumer takes an item");
        assertEquals(2, queue.size(), "Queue size is 2 (contains 2 and 3)");
    }
}
