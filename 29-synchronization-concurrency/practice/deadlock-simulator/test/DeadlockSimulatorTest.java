/**
 * Test runner for DeadlockSimulator.
 */
public class DeadlockSimulatorTest {

    public static void main(String[] args) {
        try {
            testDeadlockDetection();
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

    private static void testDeadlockDetection() throws InterruptedException {
        Object lock1 = new Object();
        Object lock2 = new Object();

        boolean isDeadlocked = DeadlockSimulator.runDeadlockCheck(lock1, lock2);
        
        assertTrue(isDeadlocked, "Deadlock checker must detect the deadlock simulation");
    }
}
