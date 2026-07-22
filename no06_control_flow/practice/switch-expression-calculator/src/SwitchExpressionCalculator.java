package no06_control_flow.practice.switch_expression_calculator;

/**
 * A simple calculator utilizing Java switch expressions.
 */
public class SwitchExpressionCalculator {

    /**
     * Calculates the result of an arithmetic operation on two doubles.
     * Supports:
     * - addition: "+", "add"
     * - subtraction: "-", "subtract"
     * - multiplication: "*", "multiply"
     * - division: "/", "divide"
     * - modulo: "%", "modulo"
     *
     * @param a        first operand
     * @param b        second operand
     * @param operator operation symbol or name
     * @return the double result of the operation
     * @throws ArithmeticException      if division/modulo by zero is attempted
     * @throws IllegalArgumentException if the operator is unrecognized
     */
    public static double calculate(double a, double b, String operator) {
        // TODO: Implement using a Java switch expression.
        // Remember to use yield inside block cases (like division where you check for
        // zero).
        var res = switch (operator) {
            case "+", "add" -> a + b;
            case "-", "subtract" -> a - b;
            case "*", "multiply" -> a * b;
            case "/", "divide" -> {
                if (b == 0.0) {
                    throw new ArithmeticException("Divide by zero");
                }
                yield a / b;
            }
            case "%", "modulo" -> {
                if (b == 0.0) {
                    throw new ArithmeticException("Divide by zero");
                }
                yield a % b;
            }
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
        return res;
    }
}
