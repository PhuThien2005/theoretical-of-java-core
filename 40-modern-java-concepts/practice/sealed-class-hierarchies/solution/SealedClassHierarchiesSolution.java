package sealedclasshierarchies;

public class SealedClassHierarchiesSolution {

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
        if (expr == null) {
            throw new IllegalArgumentException("Expression cannot be null");
        }

        // Exhaustive switch expression leveraging Java's compiler-enforced pattern exhaustiveness.
        // We do not add a 'default' case, so if a new subtype of Expr is added, the compiler will fail to compile.
        return switch (expr) {
            case Val(int v) -> v;
            case Add(Expr l, Expr r) -> evaluate(l) + evaluate(r);
            case Sub(Expr l, Expr r) -> evaluate(l) - evaluate(r);
            case Mul(Expr l, Expr r) -> evaluate(l) * evaluate(r);
            case Div(Expr l, Expr r) -> {
                int rightVal = evaluate(r);
                if (rightVal == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                yield evaluate(l) / rightVal;
            }
        };
    }

    /**
     * Returns a fully parenthesized string representation of the expression.
     * e.g. Add(Val(2), Val(3)) -> "(2 + 3)"
     */
    public static String format(Expr expr) {
        if (expr == null) {
            throw new IllegalArgumentException("Expression cannot be null");
        }

        return switch (expr) {
            case Val(int v) -> String.valueOf(v);
            case Add(Expr l, Expr r) -> "(" + format(l) + " + " + format(r) + ")";
            case Sub(Expr l, Expr r) -> "(" + format(l) + " - " + format(r) + ")";
            case Mul(Expr l, Expr r) -> "(" + format(l) + " * " + format(r) + ")";
            case Div(Expr l, Expr r) -> "(" + format(l) + " / " + format(r) + ")";
        };
    }
}
