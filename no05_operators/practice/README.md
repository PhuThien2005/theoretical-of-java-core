# Practice Exercises: Operators

This folder contains hands-on practice exercises to reinforce your understanding of Java operators, bitwise logic, and logical precedence.

## Exercises

### 1. Bitwise Base Converter (`bitwise-base-converter`)
This exercise challenges you to convert integers to their binary and hexadecimal string representations using raw bitwise operations (`>>>`, `&`, `|`) instead of relying on standard library methods like `Integer.toBinaryString()`.
- **Goal**: Implement manual bitwise division and masking to build binary and hexadecimal strings.

#### Directory Structure
- [BitwiseBaseConverter.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no05_operators/practice/bitwise-base-converter/src/BitwiseBaseConverter.java)
- [BitwiseBaseConverterTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no05_operators/practice/bitwise-base-converter/test/BitwiseBaseConverterTest.java)
- [BitwiseBaseConverter.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no05_operators/practice/bitwise-base-converter/solution/BitwiseBaseConverter.java)

---

### 2. Logical Expression Evaluator (`logical-expression-evaluator`)
Java features short-circuit logical operators (`&&`, `||`) and a ternary operator (`? :`) that follow strict precedence rules (`!` > `&&` > `||`).
- **Goal**: Evaluate compound logical queries and nested ternary conditions correctly, reflecting Java's operator precedence.

#### Directory Structure
- [LogicalExpressionEvaluator.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no05_operators/practice/logical-expression-evaluator/src/LogicalExpressionEvaluator.java)
- [LogicalExpressionEvaluatorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no05_operators/practice/logical-expression-evaluator/test/LogicalExpressionEvaluatorTest.java)
- [LogicalExpressionEvaluator.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no05_operators/practice/logical-expression-evaluator/solution/LogicalExpressionEvaluator.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no05_operators
```
