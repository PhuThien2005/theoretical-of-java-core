package no05_operators.practice.logical_expression_evaluator;

/**
 * Reference solution for LogicalExpressionEvaluatorSolution.
 */
public class LogicalExpressionEvaluatorSolution {

    /**
     * Evaluates the logical expression: a || b && !c
     * 
     * In Java, because of precedence:
     * - `!c` is evaluated first.
     * - Then `b && (!c)` is evaluated.
     * - Finally, `a || (b && (!c))` is evaluated.
     * 
     * Note: Java also uses short-circuiting. If `a` is true, the rest of the expression
     * (b && !c) will not be evaluated at all.
     */
    public static boolean evaluatePrecedence(boolean a, boolean b, boolean c) {
        return a || b && !c;
    }

    /**
     * Evaluates: premium ? (active ? 100 : 50) : (active ? 20 : 0)
     */
    public static int evaluateTernaryPrecedence(boolean active, boolean premium) {
        return premium ? (active ? 100 : 50) : (active ? 20 : 0);
    }

    /**
     * Checks if a year is a leap year using short-circuit logical operators.
     * 
     * Formula: (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);
    }
}
