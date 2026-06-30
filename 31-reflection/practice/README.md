# Practice Exercises: Java Reflection

This folder contains hands-on practice exercises to reinforce your understanding of Java Reflection APIs: accessing class metadata, breaking encapsulation boundaries (reading/writing private fields dynamically), and building custom annotations processors.

## Exercises

### 1. Dependency Injection Container (`dependency-injection-container`)
Many enterprise Java frameworks (like Spring or Google Guice) use reflection to inspect fields annotated with `@Inject` (or `@Autowired`) and dynamically populate them from a registry.
- **Goal**: Implement a simple Dependency Injection framework `DependencyInjectionContainer` that registers service instances and automatically injects them into annotated target fields.

#### Directory Structure
- [DependencyInjectionContainer.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/31-reflection/practice/dependency-injection-container/src/DependencyInjectionContainer.java)
- [DependencyInjectionContainerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/31-reflection/practice/dependency-injection-container/test/DependencyInjectionContainerTest.java)
- [DependencyInjectionContainer.java (Solution)](file:///home/fhu_thjen/projects/learning-java/31-reflection/practice/dependency-injection-container/solution/DependencyInjectionContainer.java)

---

### 2. Private Field Mutator (`private-field-mutator`)
Reflection allows bypassing Java's compile-time access controls. Using `Field.setAccessible(true)`, you can inspect or modify private fields dynamically.
- **Goal**: Implement utility methods `getPrivateField` and `setPrivateField` in `PrivateFieldMutator` to read and write encapsulated private fields without getters or setters.

#### Directory Structure
- [PrivateFieldMutator.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/31-reflection/practice/private-field-mutator/src/PrivateFieldMutator.java)
- [PrivateFieldMutatorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/31-reflection/practice/private-field-mutator/test/PrivateFieldMutatorTest.java)
- [PrivateFieldMutator.java (Solution)](file:///home/fhu_thjen/projects/learning-java/31-reflection/practice/private-field-mutator/solution/PrivateFieldMutator.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 31-reflection
```
