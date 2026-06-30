/**
 * Test runner for MemoryProfilerSimulator.
 */
public class MemoryProfilerSimulatorTest {

    public static void main(String[] args) {
        try {
            testMemoryProfilingEstimation();
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

    private static void testMemoryProfilingEstimation() {
        int numInstances = 10_000;
        double footprint = MemoryProfilerSimulator.estimateObjectFootprintBytes(numInstances);

        // Print estimated footprint
        System.out.println("Estimated footprint of SampleObject: " + String.format("%.2f", footprint) + " bytes");

        // Footprint estimate should be positive
        assertTrue(footprint > 0.0, "Object footprint estimate must be positive");
        
        // Assert that the footprint size is reasonable (e.g. less than 1000 bytes for our small SampleObject class)
        assertTrue(footprint < 1000.0, "SampleObject memory footprint should not exceed 1000 bytes (Actual: " + footprint + ")");
    }
}
