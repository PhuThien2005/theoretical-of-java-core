package no08_string.practice.custom_string_builder;

/**
 * Test runner for CustomStringBuilder.
 */
public class CustomStringBuilderTest {

    public static void main(String[] args) {
        try {
            testInitialState();
            testAppendString();
            testAppendChar();
            testCapacityGrowth();
            testNullAppend();
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

    private static void assertEquals(String expected, String actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + "\nExpected: [" + expected + "]\nActual:   [" + actual + "]");
        }
    }

    private static void testInitialState() {
        CustomStringBuilder csb = new CustomStringBuilder();
        assertEquals(0, csb.length(), "Initial length should be 0");
        assertEquals(16, csb.capacity(), "Default capacity should be 16");
        assertEquals("", csb.toString(), "Initial value should be empty string");

        CustomStringBuilder csbCustom = new CustomStringBuilder(30);
        assertEquals(30, csbCustom.capacity(), "Custom capacity should be 30");
    }

    private static void testAppendString() {
        CustomStringBuilder csb = new CustomStringBuilder();
        csb.append("Hello").append(" ").append("World!");
        assertEquals("Hello World!", csb.toString(), "Append strings");
        assertEquals(12, csb.length(), "Length should be 12");
    }

    private static void testAppendChar() {
        CustomStringBuilder csb = new CustomStringBuilder();
        csb.append('A').append('B').append('C');
        assertEquals("ABC", csb.toString(), "Append chars");
        assertEquals(3, csb.length(), "Length should be 3");
    }

    private static void testCapacityGrowth() {
        // Create builder with small capacity
        CustomStringBuilder csb = new CustomStringBuilder(4);
        assertEquals(4, csb.capacity(), "Initial capacity 4");
        
        csb.append("abcd");
        assertEquals(4, csb.length(), "Length 4");
        assertEquals(4, csb.capacity(), "Capacity still 4");
        
        // Exceed capacity
        csb.append("e");
        assertEquals(5, csb.length(), "Length 5");
        // Capacity should double to 8
        assertEquals(8, csb.capacity(), "Capacity should double to 8");
        assertEquals("abcde", csb.toString(), "Accumulated string is correct");

        // Exceed again by many characters
        csb.append("fghijklmnopqrstuvwxyz");
        assertEquals(26, csb.length(), "Length 26");
        assertEquals("abcdefghijklmnopqrstuvwxyz", csb.toString(), "Full alphabet appended");
    }

    private static void testNullAppend() {
        CustomStringBuilder csb = new CustomStringBuilder();
        csb.append((String) null);
        assertEquals("null", csb.toString(), "Appending null should result in string 'null'");
        assertEquals(4, csb.length(), "Length should be 4");
    }
}
