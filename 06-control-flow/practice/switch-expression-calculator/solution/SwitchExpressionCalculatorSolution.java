/**
 * Reference solution for SwitchExpressionCalculatorSolution.
 * 
 * Using modern switch expressions:
 * - Return value directly from the switch statement.
 * - Arrow (`->`) syntax prevents accidental fall-through (no `break` needed).
 * - Multi-label matching (`case "+", "add"`).
 * - Block cases can execute multiple statements and return a value using `yield`.
 */
public class SwitchExpressionCalculatorSolution {

    public static double calculate(double a, double b, String operator) {
        if (operator == null) {
            throw new IllegalArgumentException("Operator cannot be null");
        }

        // Switch expression returning a double value
        return switch (operator.toLowerCase().trim()) {
            case "+", "add" -> a + b;
            case "-", "subtract" -> a - b;
            case "*", "multiply" -> a * b;
            case "/", "divide" -> {
                if (b == 0.0) {
                    throw new ArithmeticException("Division by zero");
                }
                yield a / b;
            }
            case "%", "modulo" -> {
                if (b == 0.0) {
                    throw new ArithmeticException("Division by zero");
                }
                yield a % b;
            }
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }
}
