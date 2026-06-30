# Practice Exercises: Modern Java Concepts

This folder contains hands-on practice exercises to reinforce your understanding of modern Java concepts (Java 16+ to 21+), including records, sealed classes, pattern matching, switch expressions, and sequenced collections.

## Exercises

### 1. Pattern Matching with Records (`pattern-matching-record`)
An e-commerce order processing system utilizing Java Records and record pattern matching in instanceof/switch expressions.
- **Records**: Define nested models for items, customers, and discounts.
- **Pattern Matching**: Implement business logic for final price calculations and loyalty points computation using nested patterns.

#### Directory Structure
- [PatternMatchingRecord.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/40-modern-java-concepts/practice/pattern-matching-record/src/PatternMatchingRecord.java)
- [PatternMatchingRecordTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/40-modern-java-concepts/practice/pattern-matching-record/test/PatternMatchingRecordTest.java)
- [PatternMatchingRecord.java (Solution)](file:///home/fhu_thjen/projects/learning-java/40-modern-java-concepts/practice/pattern-matching-record/solution/PatternMatchingRecord.java)

---

### 2. Sealed Class Hierarchies (`sealed-class-hierarchies`)
An Abstract Syntax Tree (AST) arithmetic expression evaluator using sealed class hierarchies.
- **Sealed Interfaces/Classes**: Define the math expressions hierarchy (`Expr`, `Val`, `Add`, `Sub`, `Mul`, `Div`).
- **Exhaustive Switch**: Implement an expression evaluator using switch expression with record patterns that compiles without a `default` branch because of Sealed exhaustiveness.

#### Directory Structure
- [SealedClassHierarchies.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/40-modern-java-concepts/practice/sealed-class-hierarchies/src/SealedClassHierarchies.java)
- [SealedClassHierarchiesTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/40-modern-java-concepts/practice/sealed-class-hierarchies/test/SealedClassHierarchiesTest.java)
- [SealedClassHierarchies.java (Solution)](file:///home/fhu_thjen/projects/learning-java/40-modern-java-concepts/practice/sealed-class-hierarchies/solution/SealedClassHierarchies.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository to test your implementations:

```bash
python3 scripts/verify_exercise.py 40-modern-java-concepts
```
