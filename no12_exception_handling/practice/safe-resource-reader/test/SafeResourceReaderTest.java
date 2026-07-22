package no12_exception_handling.practice.safe_resource_reader;

/**
 * Test runner for SafeResourceReader.
 */
public class SafeResourceReaderTest {

    public static void main(String[] args) {
        try {
            testSuccessfulRead();
            testExceptionalRead();
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

    private static void testSuccessfulRead() {
        SafeResourceReader.MockResource resource = new SafeResourceReader.MockResource("hello", false);
        
        // Assert initial state
        assertTrue(!resource.isClosed(), "Resource should be open initially");
        
        String data = SafeResourceReader.readData(resource);
        assertEquals("hello", data, "Read data matches");
        
        // Assert it was closed automatically
        assertTrue(resource.isClosed(), "Resource must be automatically closed on successful read");
    }

    private static void testExceptionalRead() {
        SafeResourceReader.MockResource resource = new SafeResourceReader.MockResource("hello", true);
        
        // Assert initial state
        assertTrue(!resource.isClosed(), "Resource should be open initially");
        
        String data = SafeResourceReader.readData(resource);
        assertEquals("fallback-value", data, "Data should be fallback-value on Exception");

        // Assert it was closed automatically even though exception occurred
        assertTrue(resource.isClosed(), "Resource must be automatically closed even when an exception is thrown during read!");
    }
}
