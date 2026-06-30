/**
 * Test runner for RuntimeInspector.
 */
public class RuntimeInspectorTest {

    public static void main(String[] args) {
        try {
            testMemoryMetrics();
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

    private static void testMemoryMetrics() {
        long max = RuntimeInspector.getMaxMemory();
        long total = RuntimeInspector.getTotalMemory();
        long used = RuntimeInspector.getUsedMemory();

        assertTrue(max > 0, "Max memory must be positive");
        assertTrue(total > 0, "Total memory must be positive");
        assertTrue(used >= 0, "Used memory must be non-negative");

        // Heap boundaries constraints
        assertTrue(used <= total, "Used memory must not exceed total memory (Used: " + used + ", Total: " + total + ")");
        assertTrue(total <= max, "Total memory must not exceed max memory (Total: " + total + ", Max: " + max + ")");
    }
}
