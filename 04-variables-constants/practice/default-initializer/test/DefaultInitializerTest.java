/**
 * Test runner for DefaultInitializer.
 */
public class DefaultInitializerTest {

    public static void main(String[] args) {
        try {
            testDefaultValues();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(boolean expected, boolean actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void assertEquals(long expected, long actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void assertEquals(double expected, double actual, String message) {
        if (Double.compare(expected, actual) != 0) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testDefaultValues() {
        DefaultInitializer di = new DefaultInitializer();

        assertEquals(false, di.getDefaultBoolean(), "boolean default should be false");
        assertEquals(0, di.getDefaultByte(), "byte default should be 0");
        assertEquals('\u0000', di.getDefaultChar(), "char default should be null char (\\u0000)");
        assertEquals(0, di.getDefaultShort(), "short default should be 0");
        assertEquals(0, di.getDefaultInt(), "int default should be 0");
        assertEquals(0L, di.getDefaultLong(), "long default should be 0L");
        assertEquals(0.0f, di.getDefaultFloat(), "float default should be 0.0f");
        assertEquals(0.0, di.getDefaultDouble(), "double default should be 0.0");
        assertEquals(null, di.getDefaultString(), "String default reference should be null");

        assertEquals(0, DefaultInitializer.getDefaultStaticInt(), "static int default should be 0");
        assertEquals(null, DefaultInitializer.getDefaultStaticObject(), "static object default should be null");
    }
}
