# Practice Exercises: Variables and Constants

This folder contains hands-on practice exercises to reinforce your understanding of variable scopes, lifetimes, shadowing, and default initializations in Java.

## Exercises

### 1. Scope Tracker (`scope-tracker`)
In Java, where a variable is declared determines its scope and lifetime. This exercise helps you understand the differences between static (class) variables, instance (object) variables, local (method) variables, and block-scoped variables.
- **Goal**: Implement counters and shadowing rules to demonstrate how these different scopes behave and how their values persist (or don't persist) across instances and method calls.

#### Directory Structure
- [ScopeTracker.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/04-variables-constants/practice/scope-tracker/src/ScopeTracker.java)
- [ScopeTrackerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/04-variables-constants/practice/scope-tracker/test/ScopeTrackerTest.java)
- [ScopeTracker.java (Solution)](file:///home/fhu_thjen/projects/learning-java/04-variables-constants/practice/scope-tracker/solution/ScopeTracker.java)

---

### 2. Default Initializer (`default-initializer`)
Java automatically assigns default values to class and instance variables when they are declared but not explicitly initialized. However, local variables are not automatically initialized, and reading them causes a compile-time error.
- **Goal**: Map out and retrieve the default values for various primitive and reference types in instance and static scopes.

#### Directory Structure
- [DefaultInitializer.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/04-variables-constants/practice/default-initializer/src/DefaultInitializer.java)
- [DefaultInitializerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/04-variables-constants/practice/default-initializer/test/DefaultInitializerTest.java)
- [DefaultInitializer.java (Solution)](file:///home/fhu_thjen/projects/learning-java/04-variables-constants/practice/default-initializer/solution/DefaultInitializer.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 04-variables-constants
```
