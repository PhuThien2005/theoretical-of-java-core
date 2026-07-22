package no06_control_flow.practice.switch_expression_calculator;

/**
 * Test runner for SwitchExpressionCalculator.
 */
public class SwitchExpressionCalculatorTest {

    public static void main(String[] args) {
        try {
            testBasicOperations();
            testDivisionAndModuloByZero();
            testInvalidOperator();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(double expected, double actual, String message) {
        // Since we are comparing doubles, we use a tiny delta
        if (Math.abs(expected - actual) > 0.000001) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testBasicOperations() {
        assertEquals(15.0, SwitchExpressionCalculator.calculate(10.0, 5.0, "+"), "10 + 5 = 15");
        assertEquals(15.0, SwitchExpressionCalculator.calculate(10.0, 5.0, "add"), "10 add 5 = 15");
        assertEquals(5.0, SwitchExpressionCalculator.calculate(10.0, 5.0, "-"), "10 - 5 = 5");
        assertEquals(5.0, SwitchExpressionCalculator.calculate(10.0, 5.0, "subtract"), "10 subtract 5 = 5");
        assertEquals(50.0, SwitchExpressionCalculator.calculate(10.0, 5.0, "*"), "10 * 5 = 50");
        assertEquals(50.0, SwitchExpressionCalculator.calculate(10.0, 5.0, "multiply"), "10 multiply 5 = 50");
        assertEquals(2.0, SwitchExpressionCalculator.calculate(10.0, 5.0, "/"), "10 / 5 = 2");
        assertEquals(2.0, SwitchExpressionCalculator.calculate(10.0, 5.0, "divide"), "10 divide 5 = 2");
        assertEquals(0.0, SwitchExpressionCalculator.calculate(10.0, 5.0, "%"), "10 % 5 = 0");
        assertEquals(0.0, SwitchExpressionCalculator.calculate(10.0, 5.0, "modulo"), "10 modulo 5 = 0");
    }

    private static void testDivisionAndModuloByZero() {
        try {
            SwitchExpressionCalculator.calculate(10.0, 0.0, "/");
            throw new AssertionError("Should throw ArithmeticException on division by zero");
        } catch (ArithmeticException e) {
            // Expected
        }

        try {
            SwitchExpressionCalculator.calculate(10.0, 0.0, "modulo");
            throw new AssertionError("Should throw ArithmeticException on modulo by zero");
        } catch (ArithmeticException e) {
            // Expected
        }
    }

    private static void testInvalidOperator() {
        try {
            SwitchExpressionCalculator.calculate(10.0, 5.0, "invalid");
            throw new AssertionError("Should throw IllegalArgumentException on unknown operator");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
