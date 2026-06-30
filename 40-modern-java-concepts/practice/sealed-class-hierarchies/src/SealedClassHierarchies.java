package sealedclasshierarchies;

public class SealedClassHierarchies {

    public sealed interface Expr permits Val, Add, Sub, Mul, Div {}

    public record Val(int value) implements Expr {}

    public record Add(Expr left, Expr right) implements Expr {}

    public record Sub(Expr left, Expr right) implements Expr {}

    public record Mul(Expr left, Expr right) implements Expr {}

    public record Div(Expr left, Expr right) implements Expr {}

    /**
     * Recursively evaluates the arithmetic expression.
     * Throws ArithmeticException on division by zero.
     */
    public static int evaluate(Expr expr) {
        // TODO: Implement using an exhaustive switch expression over Expr.
        // DO NOT add a 'default' branch! The compiler should enforce that we handle all permitted types.
        return 0;
    }

    /**
     * Returns a fully parenthesized string representation of the expression.
     * e.g. Add(Val(2), Val(3)) -> "(2 + 3)"
     */
    public static String format(Expr expr) {
        // TODO: Implement using an exhaustive switch expression over Expr.
        // DO NOT add a 'default' branch!
        return "";
    }
}
