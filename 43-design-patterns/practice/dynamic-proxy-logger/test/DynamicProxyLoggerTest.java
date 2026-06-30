package dynamicproxylogger;

public class DynamicProxyLoggerTest {

    public static void main(String[] args) {
        try {
            testProxyLoggingSuccess();
            testProxyExceptionUnwrapping();
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
            throw new AssertionError(message + " - Expected: [" + expected + "], Actual: [" + actual + "]");
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testProxyLoggingSuccess() {
        var logDest = new StringBuilder();
        var target = new DynamicProxyLogger.UserServiceImpl();
        var proxy = DynamicProxyLogger.createProxy(DynamicProxyLogger.UserService.class, target, logDest);

        // Call method 1
        String role = proxy.getRole("admin");
        assertEquals("ADMIN", role, "Proxy should forward call and return correct value");

        // Call method 2
        proxy.updateLastLogin("alice");

        String logs = logDest.toString();
        
        // Assert log start/end are captured
        assertTrue(logs.contains("[START] Method: getRole called with args: [admin]"), "Log should contain method start for getRole");
        assertTrue(logs.contains("[END] Method: getRole completed"), "Log should contain method completion for getRole");
        assertTrue(logs.contains("[START] Method: updateLastLogin called with args: [alice]"), "Log should contain method start for updateLastLogin");
        assertTrue(logs.contains("[END] Method: updateLastLogin completed"), "Log should contain method completion for updateLastLogin");
    }

    private static void testProxyExceptionUnwrapping() {
        var logDest = new StringBuilder();
        var target = new DynamicProxyLogger.UserServiceImpl();
        var proxy = DynamicProxyLogger.createProxy(DynamicProxyLogger.UserService.class, target, logDest);

        try {
            proxy.getRole(null);
            throw new AssertionError("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Success - exception was unwrapped successfully!
            assertEquals("Username cannot be null", e.getMessage(), "Exception message mismatch");
        } catch (Throwable t) {
            throw new AssertionError("Expected IllegalArgumentException but got " + t.getClass().getName());
        }
    }
}
