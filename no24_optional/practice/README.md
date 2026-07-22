# Practice Exercises: Optional

This folder contains hands-on practice exercises to reinforce your understanding of Java `java.util.Optional`, null-safety refactoring, map pipelines, and monadic chaining using `flatMap()` and `or()` fallbacks.

## Exercises

### 1. Null-Safety Refactoring (`null-safety-refactoring`)
Deeply nested null checks (if-not-null) are verbose and hard to read. Java `Optional` provides fluent mapping pipelines (`map`, `flatMap`, `filter`) to clean up nested lookups.
- **Goal**: Refactor a legacy null-check method extracting an email address from a nested `User -> Profile -> ContactInfo` object graph using `Optional.ofNullable()`, `map()`, and `orElse()`.

#### Directory Structure
- [NullSafetyRefactoring.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no24_optional/practice/null-safety-refactoring/src/NullSafetyRefactoring.java)
- [NullSafetyRefactoringTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no24_optional/practice/null-safety-refactoring/test/NullSafetyRefactoringTest.java)
- [NullSafetyRefactoring.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no24_optional/practice/null-safety-refactoring/solution/NullSafetyRefactoring.java)

---

### 2. Optional Monadic Chain (`optional-monadic-chain`)
Modern Java (Java 9+) provides the `.or()` method to chain lazy fallback Optional operations.
- **Goal**: Implement a config resolution method in `OptionalMonadicChain` that searches for a key in a local cache, a database, and a remote API in sequence, returning the first found value or a final default fallback.

#### Directory Structure
- [OptionalMonadicChain.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no24_optional/practice/optional-monadic-chain/src/OptionalMonadicChain.java)
- [OptionalMonadicChainTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no24_optional/practice/optional-monadic-chain/test/OptionalMonadicChainTest.java)
- [OptionalMonadicChain.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no24_optional/practice/optional-monadic-chain/solution/OptionalMonadicChain.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no24_optional
```
