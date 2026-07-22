package no03_data_types.practice.binary_data_parser;

/**
 * Test runner for BinaryDataParser.
 */
public class BinaryDataParserTest {

    public static void main(String[] args) {
        try {
            testParseShort();
            testParseInt();
            testParseLong();
            testParseBoolean();
            testBoundaryExceptions();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(long expected, long actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void assertEquals(boolean expected, boolean actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testParseShort() {
        byte[] data = { 0x00, 0x05, (byte) 0xFF, (byte) 0xF2, 0x7F, (byte) 0xFF };
        
        assertEquals(5, BinaryDataParser.parseShort(data, 0), "Parse positive short");
        assertEquals(-14, BinaryDataParser.parseShort(data, 2), "Parse negative short");
        assertEquals(32767, BinaryDataParser.parseShort(data, 4), "Parse max short");
    }

    private static void testParseInt() {
        byte[] data = { 
            0x12, 0x34, 0x56, 0x78, 
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFE,
            0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF
        };

        assertEquals(0x12345678, BinaryDataParser.parseInt(data, 0), "Parse positive int");
        assertEquals(-2, BinaryDataParser.parseInt(data, 4), "Parse negative int");
        assertEquals(Integer.MAX_VALUE, BinaryDataParser.parseInt(data, 8), "Parse max int");
    }

    private static void testParseLong() {
        byte[] data = { 
            0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, (byte) 0x88,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF
        };

        assertEquals(0x1122334455667788L, BinaryDataParser.parseLong(data, 0), "Parse large positive long");
        assertEquals(-1L, BinaryDataParser.parseLong(data, 8), "Parse -1 long");
    }

    private static void testParseBoolean() {
        byte[] data = { 0x00, 0x01, 0x42 };

        assertEquals(false, BinaryDataParser.parseBoolean(data, 0), "0x00 should be false");
        assertEquals(true, BinaryDataParser.parseBoolean(data, 1), "0x01 should be true");
        assertEquals(true, BinaryDataParser.parseBoolean(data, 2), "non-zero 0x42 should be true");
    }

    private static void testBoundaryExceptions() {
        byte[] data = { 0x01, 0x02 };

        // Test Short boundary
        try {
            BinaryDataParser.parseShort(data, 1);
            throw new AssertionError("Should fail to parse short starting at index 1");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test Int boundary
        try {
            BinaryDataParser.parseInt(data, 0);
            throw new AssertionError("Should fail to parse int from 2-byte array");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test Long boundary
        try {
            BinaryDataParser.parseLong(data, 0);
            throw new AssertionError("Should fail to parse long from 2-byte array");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test Boolean boundary
        try {
            BinaryDataParser.parseBoolean(data, 2);
            throw new AssertionError("Should fail to parse boolean at index 2");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
