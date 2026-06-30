/**
 * Test runner for ScopeTracker.
 */
public class ScopeTrackerTest {

    public static void main(String[] args) {
        try {
            // Reset static variable to 0 first (since JVM might reuse class definitions in some runners)
            ScopeTracker.staticCount = 0;
            
            testInstanceVsStaticScope();
            testShadowing();
            testBlockScope();
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

    private static void testInstanceVsStaticScope() {
        ScopeTracker t1 = new ScopeTracker();
        ScopeTracker t2 = new ScopeTracker();

        // Initially both should be 0
        assertEquals(0, t1.instanceCount, "t1 instance count should start at 0");
        assertEquals(0, t2.instanceCount, "t2 instance count should start at 0");
        assertEquals(0, ScopeTracker.staticCount, "static count should start at 0");

        // Increment t1
        t1.increment();
        assertEquals(1, t1.instanceCount, "t1 instance count should be 1");
        assertEquals(0, t2.instanceCount, "t2 instance count should still be 0");
        assertEquals(1, ScopeTracker.staticCount, "static count should be 1");

        // Increment t2
        t2.increment();
        assertEquals(1, t1.instanceCount, "t1 instance count should still be 1");
        assertEquals(1, t2.instanceCount, "t2 instance count should be 1");
        assertEquals(2, ScopeTracker.staticCount, "static count should be 2 (shared across all instances)");
    }

    private static void testShadowing() {
        ScopeTracker tracker = new ScopeTracker();
        tracker.instanceCount = 10;
        
        // shadowDemo passes a value, say 5, and should return 5 + tracker.instanceCount (10) = 15.
        int result = tracker.shadowDemo(5);
        assertEquals(15, result, "shadowDemo(5) should return 15");
    }

    private static void testBlockScope() {
        ScopeTracker tracker = new ScopeTracker();
        int sum = tracker.blockScopeDemo(5); // 1 + 2 + 3 + 4 + 5 = 15
        assertEquals(15, sum, "blockScopeDemo(5) should sum 1 to 5 to equal 15");
    }
}
