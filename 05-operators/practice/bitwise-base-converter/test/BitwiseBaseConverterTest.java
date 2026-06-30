/**
 * Test runner for BitwiseBaseConverter.
 */
public class BitwiseBaseConverterTest {

    public static void main(String[] args) {
        try {
            testToBinary();
            testToHex();
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

    private static void testToBinary() {
        assertEquals("0", BitwiseBaseConverter.toBinary(0), "Binary for 0");
        assertEquals("1", BitwiseBaseConverter.toBinary(1), "Binary for 1");
        assertEquals("1010", BitwiseBaseConverter.toBinary(10), "Binary for 10");
        assertEquals("1111111", BitwiseBaseConverter.toBinary(127), "Binary for 127");
        
        // Large integer
        assertEquals("1111111111111111111111111111111", BitwiseBaseConverter.toBinary(Integer.MAX_VALUE), "Binary for MAX_VALUE");
        
        // Negative integer
        assertEquals("11111111111111111111111111111111", BitwiseBaseConverter.toBinary(-1), "Binary for -1");
        assertEquals("10000000000000000000000000000000", BitwiseBaseConverter.toBinary(Integer.MIN_VALUE), "Binary for MIN_VALUE");
    }

    private static void testToHex() {
        assertEquals("0", BitwiseBaseConverter.toHex(0), "Hex for 0");
        assertEquals("a", BitwiseBaseConverter.toHex(10), "Hex for 10");
        assertEquals("ff", BitwiseBaseConverter.toHex(255), "Hex for 255");
        assertEquals("1a2f", BitwiseBaseConverter.toHex(6703), "Hex for 6703");
        
        // Large integer
        assertEquals("7fffffff", BitwiseBaseConverter.toHex(Integer.MAX_VALUE), "Hex for MAX_VALUE");
        
        // Negative integer
        assertEquals("ffffffff", BitwiseBaseConverter.toHex(-1), "Hex for -1");
        assertEquals("80000000", BitwiseBaseConverter.toHex(Integer.MIN_VALUE), "Hex for MIN_VALUE");
    }
}
