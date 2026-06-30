/**
 * A utility class to perform math operations and casting with overflow checks.
 */
public class SafeMathOperations {

    /**
     * Adds two integers. Throws ArithmeticException if the result overflows or underflows.
     *
     * @param a first integer
     * @param b second integer
     * @return the sum of a and b
     * @throws ArithmeticException if overflow or underflow occurs
     */
    public static int safeAdd(int a, int b) {
        // TODO: Implement safe addition. Throw ArithmeticException on overflow/underflow.
        return 0;
    }

    /**
     * Multiplies two integers. Throws ArithmeticException if the result overflows or underflows.
     *
     * @param a first integer
     * @param b second integer
     * @return the product of a and b
     * @throws ArithmeticException if overflow or underflow occurs
     */
    public static int safeMultiply(int a, int b) {
        // TODO: Implement safe multiplication. Throw ArithmeticException on overflow/underflow.
        return 0;
    }

    /**
     * Casts a long value to an int. Throws ArithmeticException if the long value
     * is out of bounds for a standard 32-bit signed integer.
     *
     * @param value the long value to cast
     * @return the casted integer value
     * @throws ArithmeticException if the value does not fit in an int
     */
    public static int safeCastToInt(long value) {
        // TODO: Implement safe cast. Throw ArithmeticException if the value doesn't fit.
        return 0;
    }
}
