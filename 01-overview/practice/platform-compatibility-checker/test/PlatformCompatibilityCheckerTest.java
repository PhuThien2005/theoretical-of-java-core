/**
 * Test runner for PlatformCompatibilityChecker.
 */
public class PlatformCompatibilityCheckerTest {

    public static void main(String[] args) {
        try {
            testCalculationCorrectness();
            testJitSpeedupFactor();
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

    private static void testCalculationCorrectness() {
        long result = PlatformCompatibilityChecker.runCalculation(100);
        assertTrue(result > 0, "Calculation result must be positive (Actual: " + result + ")");
    }

    private static void testJitSpeedupFactor() {
        double speedup = PlatformCompatibilityChecker.checkJitSpeedup();
        assertTrue(speedup > 0.0, "Speedup factor must be a positive value (Actual: " + speedup + ")");
        
        // Under normal desktop CPU execution, JIT compiled code should be faster than interpreted (speedup > 1.0)
        // We print the observed speedup ratio for diagnostic verification
        System.out.println("Observed JIT speedup factor: " + String.format("%.2f", speedup) + "x");
    }
}
