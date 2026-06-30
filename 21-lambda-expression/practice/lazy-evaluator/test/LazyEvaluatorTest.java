/**
 * Test runner for LazyEvaluator.
 */
public class LazyEvaluatorTest {

    public static void main(String[] args) {
        try {
            testLazyEvaluationAndCaching();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testLazyEvaluationAndCaching() {
        int[] computationCount = {0};

        // Define a lazy integer computation
        Lazy<Integer> lazyVal = new Lazy<>(() -> {
            computationCount[0]++; // Increment count whenever evaluated
            return 100 * 2;
        });

        // 1. Assert it has NOT been evaluated yet
        assertTrue(!lazyVal.isEvaluated(), "Should not be evaluated initially");
        assertEquals(0, computationCount[0], "Computation count should be 0");

        // 2. Fetch value: triggers first evaluation
        int result1 = lazyVal.get();
        assertEquals(200, result1, "Result should be 200");
        assertTrue(lazyVal.isEvaluated(), "Should be marked evaluated");
        assertEquals(1, computationCount[0], "Computation count should be 1");

        // 3. Fetch value again: returns cached result
        int result2 = lazyVal.get();
        assertEquals(200, result2, "Result matches on second retrieval");
        assertEquals(1, computationCount[0], "Computation count must remain 1 (cached!)");
    }
}
