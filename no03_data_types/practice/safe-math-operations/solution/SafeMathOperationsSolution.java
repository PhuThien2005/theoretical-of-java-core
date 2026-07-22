package no03_data_types.practice.safe_math_operations;

/**
 * Reference solution for SafeMathOperationsSolution.
 */
public class SafeMathOperationsSolution {

    /**
     * Adds two integers. Throws ArithmeticException if the result overflows or underflows.
     * We use a long-based conversion to perform the math in a larger space and easily
     * check if the result lies outside the boundaries of a standard 32-bit signed int.
     */
    public static int safeAdd(int a, int b) {
        long sum = (long) a + b;
        if (sum < Integer.MIN_VALUE || sum > Integer.MAX_VALUE) {
            throw new ArithmeticException("Integer addition overflow/underflow: " + a + " + " + b);
        }
        return (int) sum;
    }

    /**
     * Multiplies two integers. Throws ArithmeticException if the result overflows or underflows.
     * Like addition, we promote operands to 64-bit long values to check the boundaries.
     */
    public static int safeMultiply(int a, int b) {
        long prod = (long) a * b;
        if (prod < Integer.MIN_VALUE || prod > Integer.MAX_VALUE) {
            throw new ArithmeticException("Integer multiplication overflow/underflow: " + a + " * " + b);
        }
        return (int) prod;
    }

    /**
     * Casts a long value to an int. Throws ArithmeticException if the long value
     * is out of bounds for a standard 32-bit signed integer.
     */
    public static int safeCastToInt(long value) {
        if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
            throw new ArithmeticException("Value fits outside the boundaries of an integer: " + value);
        }
        return (int) value;
    }
}
