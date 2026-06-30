/**
 * Test runner for MemoryLeakSimulator.
 */
public class MemoryLeakSimulatorTest {

    public static void main(String[] args) {
        try {
            testOomTriggerAndRecovery();
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

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testOomTriggerAndRecovery() {
        // Assert container starts clean or at 0
        assertEquals(0, MemoryLeakSimulator.getLeakedCount(), "Leak container should start empty");

        // Trigger OOM
        boolean oomTriggered = MemoryLeakSimulator.triggerOom();
        assertTrue(oomTriggered, "OOM should be triggered and caught successfully");

        // Assert recovery was successful and container was cleared
        assertEquals(0, MemoryLeakSimulator.getLeakedCount(), "Leak container must be cleared on recovery to release memory");
    }
}
