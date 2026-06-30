# Practice Exercises: Control Flow

This folder contains hands-on practice exercises to reinforce your understanding of Java control flow statements, including modern switch expressions and loops with break/continue control statements.

## Exercises

### 1. Switch Expression Calculator (`switch-expression-calculator`)
Modern Java (Java 14+) introduced switch expressions, which can return values, allow multiple labels per case, and use a cleaner arrow (`->`) syntax without the risk of fall-through bugs.
- **Goal**: Implement a math calculator that parses arithmetic operator strings (both symbols like `+` and names like `add`) using switch expressions and handles error conditions (division by zero and unknown operators) correctly.

#### Directory Structure
- [SwitchExpressionCalculator.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/06-control-flow/practice/switch-expression-calculator/src/SwitchExpressionCalculator.java)
- [SwitchExpressionCalculatorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/06-control-flow/practice/switch-expression-calculator/test/SwitchExpressionCalculatorTest.java)
- [SwitchExpressionCalculator.java (Solution)](file:///home/fhu_thjen/projects/learning-java/06-control-flow/practice/switch-expression-calculator/solution/SwitchExpressionCalculator.java)

---

### 2. FizzBuzz Extended (`fizzbuzz-extended`)
FizzBuzz is a classic programming problem, but in this extended version, you will control the loop logic dynamically using Java's jump statements (`break` and `continue`).
- **Goal**: Build a list of FizzBuzz outputs while skipping numbers divisible by a dynamic `skipDivisor` and terminating the loop early if a specific `stopNumber` is reached.

#### Directory Structure
- [FizzBuzzExtended.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/06-control-flow/practice/fizzbuzz-extended/src/FizzBuzzExtended.java)
- [FizzBuzzExtendedTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/06-control-flow/practice/fizzbuzz-extended/test/FizzBuzzExtendedTest.java)
- [FizzBuzzExtended.java (Solution)](file:///home/fhu_thjen/projects/learning-java/06-control-flow/practice/fizzbuzz-extended/solution/FizzBuzzExtended.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 06-control-flow
```
