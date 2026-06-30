/**
 * Test runner for GcTuningObserver.
 */
public class GcTuningObserverTest {

    public static void main(String[] args) {
        try {
            testGcObserverStats();
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

    private static void testGcObserverStats() {
        long beforeCount = GcTuningObserver.getGcCollectionCount();
        assertTrue(beforeCount >= 0, "GC collection count must be non-negative");

        // Run memory pressure and trigger GC
        GcTuningObserver.generateMemoryPressureAndGc();

        long afterCount = GcTuningObserver.getGcCollectionCount();
        assertTrue(afterCount >= beforeCount, "GC collection count after must be >= before");
        
        System.out.println("Garbage Collector stats verified. Collections count: " + afterCount);
    }
}
