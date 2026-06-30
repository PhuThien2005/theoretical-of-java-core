# Exercise: Sealed Class Hierarchies

## Objective
Implement an arithmetic expression AST (Abstract Syntax Tree) evaluator using sealed interfaces/classes, records, and pattern matching switch expressions.

## Requirements
1. **Sealed Hierarchy**:
   - Define a sealed interface `Expr` that permits only the following subtypes:
     - `Val(int value)` (leaf value record)
     - `Add(Expr left, Expr right)` (binary addition record)
     - `Sub(Expr left, Expr right)` (binary subtraction record)
     - `Mul(Expr left, Expr right)` (binary multiplication record)
     - `Div(Expr left, Expr right)` (binary division record)

2. **AST Evaluation**:
   - Implement the `evaluate(Expr expr)` method:
     - Use a Java `switch` expression on the `Expr` type.
     - Destructure the records using record patterns (e.g., `case Add(Expr l, Expr r)`).
     - Recursively evaluate left and right expressions.
     - For `Div(left, right)`, throw an `ArithmeticException("Division by zero")` if the divisor evaluates to `0`.
     - Do **not** include a `default` case in the switch. Because `Expr` is sealed and all permitted subtypes are handled, the compiler guarantees exhaustiveness.

3. **String Formatting**:
   - Implement `format(Expr expr)` method:
     - Produce a fully parenthesized string representation of the expression (e.g., `((2 * 3) + 5)`).
     - Handled via an exhaustive switch expression without `default`.
