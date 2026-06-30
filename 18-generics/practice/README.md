# Practice Exercises: Generics

This folder contains hands-on practice exercises to reinforce your understanding of Java Generics, generic classes, methods, bounds, and the PECS (Producer Extends, Consumer Super) wildcard rules.

## Exercises

### 1. Generic Repository Pattern (`generic-repository-pattern`)
Generics allow you to write reusable, type-safe data access logic. In this exercise, you will build a standard in-memory CRUD repository class.
- **Goal**: Implement a generic class `GenericRepository<T, ID>` that supports key-based storage, retrieval, exist checks, deletions, and listing all entities.

#### Directory Structure
- [GenericRepositoryPattern.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/18-generics/practice/generic-repository-pattern/src/GenericRepositoryPattern.java)
- [GenericRepositoryPatternTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/18-generics/practice/generic-repository-pattern/test/GenericRepositoryPatternTest.java)
- [GenericRepositoryPattern.java (Solution)](file:///home/fhu_thjen/projects/learning-java/18-generics/practice/generic-repository-pattern/solution/GenericRepositoryPattern.java)

---

### 2. Wildcard Utility (`wildcard-utility`)
Wildcards (`?`) provide flexibility when working with collections of subclass and superclass hierarchies.
- **Goal**: Implement two generic utility methods in `WildcardUtility`:
  1. A `copy` method that copies elements from a source list to a destination list using PECS (`<? extends T>` and `<? super T>`).
  2. A `findGreaterThan` method that filters a list of comparable elements greater than a threshold using bounded types (`<T extends Comparable<? super T>>`).

#### Directory Structure
- [WildcardUtility.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/18-generics/practice/wildcard-utility/src/WildcardUtility.java)
- [WildcardUtilityTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/18-generics/practice/wildcard-utility/test/WildcardUtilityTest.java)
- [WildcardUtility.java (Solution)](file:///home/fhu_thjen/projects/learning-java/18-generics/practice/wildcard-utility/solution/WildcardUtility.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 18-generics
```
