package no05_operators.practice.logical_expression_evaluator;

/**
 * Test runner for LogicalExpressionEvaluator.
 */
public class LogicalExpressionEvaluatorTest {

    public static void main(String[] args) {
        try {
            testEvaluatePrecedence();
            testEvaluateTernaryPrecedence();
            testIsLeapYear();
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

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testEvaluatePrecedence() {
        // a || b && !c
        // 1. false || true && !false => false || true && true => true
        assertEquals(true, LogicalExpressionEvaluator.evaluatePrecedence(false, true, false), "false || true && !false should be true");

        // 2. false || true && !true => false || true && false => false
        assertEquals(false, LogicalExpressionEvaluator.evaluatePrecedence(false, true, true), "false || true && !true should be false");

        // 3. true || false && !false => true || false && true => true (short-circuit)
        assertEquals(true, LogicalExpressionEvaluator.evaluatePrecedence(true, false, false), "true || false && !false should be true");
    }

    private static void testEvaluateTernaryPrecedence() {
        // active, premium
        assertEquals(100, LogicalExpressionEvaluator.evaluateTernaryPrecedence(true, true), "Active Premium should get 100");
        assertEquals(50, LogicalExpressionEvaluator.evaluateTernaryPrecedence(false, true), "Inactive Premium should get 50");
        assertEquals(20, LogicalExpressionEvaluator.evaluateTernaryPrecedence(true, false), "Active Non-Premium should get 20");
        assertEquals(0, LogicalExpressionEvaluator.evaluateTernaryPrecedence(false, false), "Inactive Non-Premium should get 0");
    }

    private static void testIsLeapYear() {
        assertEquals(true, LogicalExpressionEvaluator.isLeapYear(2000), "2000 is a leap year");
        assertEquals(false, LogicalExpressionEvaluator.isLeapYear(1900), "1900 is NOT a leap year");
        assertEquals(true, LogicalExpressionEvaluator.isLeapYear(2024), "2024 is a leap year");
        assertEquals(false, LogicalExpressionEvaluator.isLeapYear(2023), "2023 is NOT a leap year");
    }
}
