/**
 * Test runner for ChainedExceptionTracker.
 */
public class ChainedExceptionTrackerTest {

    public static void main(String[] args) {
        try {
            testPerformDatabaseQuery();
            testProcessOrderChaining();
            testRootCauseUnwrapping();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(String expected, String actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + "\nExpected: [" + expected + "]\nActual:   [" + actual + "]");
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testPerformDatabaseQuery() {
        try {
            ChainedExceptionTracker.performDatabaseQuery();
            throw new AssertionError("performDatabaseQuery should have thrown DatabaseException");
        } catch (DatabaseException e) {
            assertEquals("Connection timeout", e.getMessage(), "DatabaseException message matches");
        }
    }

    private static void testProcessOrderChaining() {
        try {
            ChainedExceptionTracker.processOrder();
            throw new AssertionError("processOrder should have thrown BusinessException");
        } catch (BusinessException e) {
            assertEquals("Order processing failed", e.getMessage(), "BusinessException message matches");
            
            // Assert that the cause is a DatabaseException
            Throwable cause = e.getCause();
            assertTrue(cause instanceof DatabaseException, "Cause of BusinessException must be a DatabaseException");
            assertEquals("Connection timeout", cause.getMessage(), "Cause message matches 'Connection timeout'");
        }
    }

    private static void testRootCauseUnwrapping() {
        // Multi-level exception chain: Exception C -> cause is Exception B -> cause is Exception A
        Exception causeA = new IllegalArgumentException("Root root cause");
        Exception causeB = new NullPointerException("Middle cause");
        causeB.initCause(causeA);
        
        Exception top = new RuntimeException("Top exception", causeB);

        String rootMessage = ChainedExceptionTracker.getRootCauseMessage(top);
        assertEquals("Root root cause", rootMessage, "getRootCauseMessage should find the bottom-most exception cause");

        // Single exception with no cause
        Exception single = new Exception("Only me");
        assertEquals("Only me", ChainedExceptionTracker.getRootCauseMessage(single), "Single exception returns its own message");
    }
}
