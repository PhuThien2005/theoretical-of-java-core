# Practice Exercises: Lambda Expressions

This folder contains hands-on practice exercises to reinforce your understanding of Java lambda expressions, functional interfaces, callback registration patterns, and deferred (lazy) execution.

## Exercises

### 1. Callback Registry (`callback-registry`)
Lambda expressions are concise implementations of functional interfaces. They are frequently used as event listeners or callback hooks in design patterns.
- **Goal**: Implement a `CallbackRegistry` that registers `NotificationCallback` handlers and triggers them when a message arrives.

#### Directory Structure
- [CallbackRegistry.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/21-lambda-expression/practice/callback-registry/src/CallbackRegistry.java)
- [CallbackRegistryTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/21-lambda-expression/practice/callback-registry/test/CallbackRegistryTest.java)
- [CallbackRegistry.java (Solution)](file:///home/fhu_thjen/projects/learning-java/21-lambda-expression/practice/callback-registry/solution/CallbackRegistry.java)

---

### 2. Lazy Evaluator (`lazy-evaluator`)
In Java, lambda expressions can defer code execution. Using `java.util.function.Supplier`, you can wrap expensive computations and execute them only when their result is requested. Subsequent requests should return a cached value.
- **Goal**: Implement a `Lazy<T>` wrapper class that defers execution of a `Supplier` until the first `get()` call and memoizes the result.

#### Directory Structure
- [LazyEvaluator.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/21-lambda-expression/practice/lazy-evaluator/src/LazyEvaluator.java)
- [LazyEvaluatorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/21-lambda-expression/practice/lazy-evaluator/test/LazyEvaluatorTest.java)
- [LazyEvaluator.java (Solution)](file:///home/fhu_thjen/projects/learning-java/21-lambda-expression/practice/lazy-evaluator/solution/LazyEvaluator.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 21-lambda-expression
```
