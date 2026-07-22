package sealedclasshierarchies;

import sealedclasshierarchies.SealedClassHierarchies.*;

public class SealedClassHierarchiesTest {

    public static void main(String[] args) {
        try {
            testEvaluationAndFormatting();
            testDivisionByZero();
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
            throw new AssertionError(message + " - Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void assertEquals(String expected, String actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " - Expected: [" + expected + "], Actual: [" + actual + "]");
        }
    }

    private static void testEvaluationAndFormatting() {
        // (2 * (5 - 3)) + 10 = 14
        Expr expr = new Add(
            new Mul(
                new Val(2),
                new Sub(new Val(5), new Val(3))
            ),
            new Val(10)
        );

        assertEquals(14, SealedClassHierarchies.evaluate(expr), "Expression evaluation failed");
        assertEquals("((2 * (5 - 3)) + 10)", SealedClassHierarchies.format(expr), "String formatting failed");
    }

    private static void testDivisionByZero() {
        // 5 / (3 - 3) -> should throw ArithmeticException
        Expr expr = new Div(
            new Val(5),
            new Sub(new Val(3), new Val(3))
        );

        try {
            SealedClassHierarchies.evaluate(expr);
            throw new AssertionError("Expected ArithmeticException on division by zero");
        } catch (ArithmeticException e) {
            assertEquals("Division by zero", e.getMessage(), "Exception message should be 'Division by zero'");
        }
    }
}
