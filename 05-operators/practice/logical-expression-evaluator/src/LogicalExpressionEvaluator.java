/**
 * A utility class to evaluate compound logical expressions, demonstrating
 * Java's
 * logical operator precedence and short-circuit evaluation behavior.
 */
public class LogicalExpressionEvaluator {

    /**
     * Evaluates the logical expression: a || b && !c
     * 
     * Java Operator Precedence:
     * 1. logical NOT (!) has the highest precedence.
     * 2. logical AND (&&) has the second highest.
     * 3. logical OR (||) has the lowest.
     *
     * @param a boolean value a
     * @param b boolean value b
     * @param c boolean value c
     * @return the result of the expression matching Java precedence rules
     */
    public static boolean evaluatePrecedence(boolean a, boolean b, boolean c) {
        // TODO: Implement the expression: a || b && !c
        return a || b && !c;
    }

    /**
     * Evaluates a nested ternary operation to calculate points:
     * premium ? (active ? 100 : 50) : (active ? 20 : 0)
     * 
     * Ternary Operator:
     * - Evaluates conditions from left to right but associates right-to-left.
     *
     * @param active  whether the user is active
     * @param premium whether the user is premium
     * @return calculated points
     */
    public static int evaluateTernaryPrecedence(boolean active, boolean premium) {
        // TODO: Implement the nested ternary expression: premium ? (active ? 100 : 50)
        // : (active ? 20 : 0)
        // return -1;
        return premium ? (active ? 100 : 50) : (active ? 20 : 0);
    }

    /**
     * Checks if a year is a leap year using short-circuit logical operators.
     * Rules:
     * - A year is a leap year if it is divisible by 4 AND (not divisible by 100 OR
     * divisible by 400).
     * 
     * @param year the year to check
     * @return true if it is a leap year, false otherwise
     */
    public static boolean isLeapYear(int year) {
        // TODO: Implement leap year logic using operators: &&, ||, !=, ==

        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }
}
