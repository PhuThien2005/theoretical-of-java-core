import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Test runner for ThreadSafeCounter.
 */
public class ThreadSafeCounterTest {

    public static void main(String[] args) {
        try {
            testSynchronizationModifier();
            testConcurrentIncrements();
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

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testSynchronizationModifier() throws NoSuchMethodException {
        // Enforce that increment() is synchronized to prevent race conditions
        Method method = ThreadSafeCounter.class.getDeclaredMethod("increment");
        int modifiers = method.getModifiers();
        assertTrue(Modifier.isSynchronized(modifiers), "increment() method must be declared 'synchronized' to be thread-safe!");
    }

    private static void testConcurrentIncrements() throws InterruptedException {
        ThreadSafeCounter counter = new ThreadSafeCounter();
        int numThreads = 10;
        int incrementsPerThread = 1000;
        Thread[] threads = new Thread[numThreads];

        // Launch concurrent threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment();
                }
            });
            threads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }

        // Verify total increments
        int expectedTotal = numThreads * incrementsPerThread;
        assertEquals(expectedTotal, counter.getCount(), "Counter must match total concurrent increments exactly");
    }
}
