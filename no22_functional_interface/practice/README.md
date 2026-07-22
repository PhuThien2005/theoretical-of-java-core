# Practice Exercises: Functional Interfaces

This folder contains hands-on practice exercises to reinforce your understanding of Java's standard functional interfaces (`Predicate` and `Function`) and how to compose them into pipelines using default methods like `and()`, `or()`, and `andThen()`.

## Exercises

### 1. Data Validation Pipeline (`data-validation-pipeline`)
Java's `Predicate<T>` represents a boolean condition. You can chain predicates together using `.and()`, `.or()`, and `.negate()` to form complex validation pipelines.
- **Goal**: Implement standard predicate builders (length check, digit check, special character check) and compose them into a unified password policy validation check.

#### Directory Structure
- [DataValidationPipeline.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no22_functional_interface/practice/data-validation-pipeline/src/DataValidationPipeline.java)
- [DataValidationPipelineTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no22_functional_interface/practice/data-validation-pipeline/test/DataValidationPipelineTest.java)
- [DataValidationPipeline.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no22_functional_interface/practice/data-validation-pipeline/solution/DataValidationPipeline.java)

---

### 2. Functional Transformer (`functional-transformer`)
Java's `Function<T, R>` transforms an input of type T to an output of type R. Functions can be chained sequentially using `.andThen()` to build data processing pipelines.
- **Goal**: Implement text transformation functions (trimming, lowercase conversion, space replacement) and chain them together to create a URL slug generator.

#### Directory Structure
- [FunctionalTransformer.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no22_functional_interface/practice/functional-transformer/src/FunctionalTransformer.java)
- [FunctionalTransformerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no22_functional_interface/practice/functional-transformer/test/FunctionalTransformerTest.java)
- [FunctionalTransformer.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no22_functional_interface/practice/functional-transformer/solution/FunctionalTransformer.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no22_functional_interface
```
