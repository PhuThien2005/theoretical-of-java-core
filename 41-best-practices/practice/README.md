# Practice Exercises: Best Practices

This folder contains hands-on practice exercises to reinforce your understanding of Java best practices, refactoring code smells, and optimizing collection usage.

## Exercises

### 1. Code Smell Refactoring (`code-smell-refactoring`)
Refactor a legacy shipping calculator class that contains multiple code smells:
- Magic numbers.
- A long, complex `if-else` chain (violating OCP/polymorphism).
- Swallowed/uninformative exceptions.
- Hardcoded string concatenations in loop.
- Unclosed I/O resources (resource leak).

#### Directory Structure
- [CodeSmellRefactoring.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/41-best-practices/practice/code-smell-refactoring/src/CodeSmellRefactoring.java)
- [CodeSmellRefactoringTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/41-best-practices/practice/code-smell-refactoring/test/CodeSmellRefactoringTest.java)
- [CodeSmellRefactoring.java (Solution)](file:///home/fhu_thjen/projects/learning-java/41-best-practices/practice/code-smell-refactoring/solution/CodeSmellRefactoring.java)

---

### 2. Refactored Collection Usage (`refactored-collection-usage`)
Optimize collection declarations, initializations, and operations using modern Java guidelines:
- Declaring collections using interface types (`List`, `Map`, `Set`).
- Pre-allocating `HashMap` initial capacity to avoid resizing overhead.
- Using modern immutable factories (`List.of`, `Map.of`, `Set.of`).
- Optimizing list-to-array conversions.

#### Directory Structure
- [RefactoredCollectionUsage.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/41-best-practices/practice/refactored-collection-usage/src/RefactoredCollectionUsage.java)
- [RefactoredCollectionUsageTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/41-best-practices/practice/refactored-collection-usage/test/RefactoredCollectionUsageTest.java)
- [RefactoredCollectionUsage.java (Solution)](file:///home/fhu_thjen/projects/learning-java/41-best-practices/practice/refactored-collection-usage/solution/RefactoredCollectionUsage.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository to test your implementations:

```bash
python3 scripts/verify_exercise.py 41-best-practices
```
