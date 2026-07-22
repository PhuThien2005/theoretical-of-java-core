# Practice Exercises: Inner & Nested Classes

This folder contains hands-on practice exercises to reinforce your understanding of Java nested classes: non-static inner classes (which have access to the enclosing instance's state) and static nested classes (which act as standalone helper helper classes helper packaged inside an outer class).

## Exercises

### 1. Custom Iterable Stack (`custom-iterable-stack`)
Non-static inner classes are implicitly linked to the enclosing outer class instance, allowing them to access its private members directly. In this exercise, you will implement an array-based Stack that implements `Iterable`.
- **Goal**: Implement a non-static inner class `StackIterator` that implements `java.util.Iterator` to traverse the stack elements from top to bottom.

#### Directory Structure
- [CustomIterableStack.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no15_inner_nested_class/practice/custom-iterable-stack/src/CustomIterableStack.java)
- [CustomIterableStackTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no15_inner_nested_class/practice/custom-iterable-stack/test/CustomIterableStackTest.java)
- [CustomIterableStack.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no15_inner_nested_class/practice/custom-iterable-stack/solution/CustomIterableStack.java)

---

### 2. Builder Pattern Inner (`builder-pattern-inner`)
Static nested classes do not have a reference to an instance of the outer class. A classic application of static nested classes is the **Builder Pattern**, which provides a fluent API to construct complex, immutable objects.
- **Goal**: Implement a `UserAccount` class with a private constructor constructed via a public static nested `Builder` class.

#### Directory Structure
- [BuilderPatternInner.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no15_inner_nested_class/practice/builder-pattern-inner/src/BuilderPatternInner.java)
- [BuilderPatternInnerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no15_inner_nested_class/practice/builder-pattern-inner/test/BuilderPatternInnerTest.java)
- [BuilderPatternInner.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no15_inner_nested_class/practice/builder-pattern-inner/solution/BuilderPatternInner.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no15_inner_nested_class
```
