import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Test runner for ParallelTaskExecutor.
 */
public class ParallelTaskExecutorTest {

    public static void main(String[] args) {
        ParallelTaskExecutor executor = null;
        try {
            executor = new ParallelTaskExecutor(3);
            testParallelExecution(executor);
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        } finally {
            if (executor != null) {
                executor.shutdown();
            }
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

    private static void testParallelExecution(ParallelTaskExecutor executor) throws Exception {
        Callable<Integer> t1 = () -> {
            Thread.sleep(100);
            return 1;
        };
        Callable<Integer> t2 = () -> {
            Thread.sleep(100);
            return 2;
        };
        Callable<Integer> t3 = () -> {
            Thread.sleep(100);
            return 3;
        };

        long startTime = System.currentTimeMillis();
        
        List<Integer> results = executor.executeTasks(Arrays.asList(t1, t2, t3));
        
        long duration = System.currentTimeMillis() - startTime;

        // Verify results
        assertEquals(3, results.size(), "Should have 3 results");
        assertEquals(Arrays.asList(1, 2, 3), results, "Results match task return values");

        // If executed in parallel with 3 threads, total sleep time should be ~100ms.
        // If run sequentially, it would take >= 300ms.
        // Let's assert it completed in less than 250ms!
        assertTrue(duration < 250, "Tasks should run in parallel (took " + duration + "ms, expected < 250ms)");
    }
}
