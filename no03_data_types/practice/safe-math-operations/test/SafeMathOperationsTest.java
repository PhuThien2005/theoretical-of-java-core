package no03_data_types.practice.safe_math_operations;

/**
 * Test runner for SafeMathOperations.
 */
public class SafeMathOperationsTest {

    public static void main(String[] args) {
        try {
            testSafeAdd();
            testSafeMultiply();
            testSafeCastToInt();
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

    private static void testSafeAdd() {
        // Normal cases
        assertEquals(15, SafeMathOperations.safeAdd(5, 10), "5 + 10 = 15");
        assertEquals(-5, SafeMathOperations.safeAdd(5, -10), "5 + (-10) = -5");
        assertEquals(0, SafeMathOperations.safeAdd(0, 0), "0 + 0 = 0");

        // Overflow case
        try {
            SafeMathOperations.safeAdd(Integer.MAX_VALUE, 1);
            throw new AssertionError("safeAdd should have thrown ArithmeticException on positive overflow");
        } catch (ArithmeticException e) {
            // Expected
        }

        // Underflow case
        try {
            SafeMathOperations.safeAdd(Integer.MIN_VALUE, -1);
            throw new AssertionError("safeAdd should have thrown ArithmeticException on negative underflow");
        } catch (ArithmeticException e) {
            // Expected
        }
    }

    private static void testSafeMultiply() {
        // Normal cases
        assertEquals(50, SafeMathOperations.safeMultiply(5, 10), "5 * 10 = 50");
        assertEquals(-50, SafeMathOperations.safeMultiply(5, -10), "5 * (-10) = -50");
        assertEquals(0, SafeMathOperations.safeMultiply(0, 10), "0 * 10 = 0");

        // Overflow case
        try {
            SafeMathOperations.safeMultiply(Integer.MAX_VALUE, 2);
            throw new AssertionError("safeMultiply should have thrown ArithmeticException on overflow");
        } catch (ArithmeticException e) {
            // Expected
        }

        // Underflow case
        try {
            SafeMathOperations.safeMultiply(Integer.MIN_VALUE, 2);
            throw new AssertionError("safeMultiply should have thrown ArithmeticException on underflow");
        } catch (ArithmeticException e) {
            // Expected
        }
    }

    private static void testSafeCastToInt() {
        // Normal cases
        assertEquals(100, SafeMathOperations.safeCastToInt(100L), "Cast 100L to int");
        assertEquals(Integer.MAX_VALUE, SafeMathOperations.safeCastToInt((long) Integer.MAX_VALUE), "Cast Max Value");
        assertEquals(Integer.MIN_VALUE, SafeMathOperations.safeCastToInt((long) Integer.MIN_VALUE), "Cast Min Value");

        // Overflow case
        try {
            SafeMathOperations.safeCastToInt((long) Integer.MAX_VALUE + 1);
            throw new AssertionError("safeCastToInt should have thrown ArithmeticException on positive overflow");
        } catch (ArithmeticException e) {
            // Expected
        }

        // Underflow case
        try {
            SafeMathOperations.safeCastToInt((long) Integer.MIN_VALUE - 1);
            throw new AssertionError("safeCastToInt should have thrown ArithmeticException on negative underflow");
        } catch (ArithmeticException e) {
            // Expected
        }
    }
}
