# Practice Exercises: The Object Class

This folder contains hands-on practice exercises to reinforce your understanding of the root `java.lang.Object` class, including contract-abiding overrides for `equals()` and `hashCode()`, and correct implementation of deep copy cloning using the `Cloneable` interface.

## Exercises

### 1. Consistent Hash Key (`consistent-hash-key`)
To use custom objects as keys in a hash-based collection (like `HashMap` or `HashSet`), you must correctly override `equals()` and `hashCode()`. If you override one but not the other, or implement them inconsistently, collections will fail to find or store your items correctly.
- **Goal**: Implement a `ConsistentHashKey` class representing an immutable composite key, adhering strictly to the `equals` and `hashCode` contracts.

#### Directory Structure
- [ConsistentHashKey.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no14_object_class/practice/consistent-hash-key/src/ConsistentHashKey.java)
- [ConsistentHashKeyTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no14_object_class/practice/consistent-hash-key/test/ConsistentHashKeyTest.java)
- [ConsistentHashKey.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no14_object_class/practice/consistent-hash-key/solution/ConsistentHashKey.java)

---

### 2. Object Deep Cloner (`object-deep-cloner`)
By default, the `clone()` method inherited from `java.lang.Object` performs a shallow copy. If your object contains references to mutable objects (like arrays or custom classes), both the original and cloned objects will share the same nested references, violating true object isolation.
- **Goal**: Implement a proper deep copy clone for a `User` class containing a reference to a mutable `Address` object and a `String[]` array, ensuring modifying the clone does not impact the original object.

#### Directory Structure
- [ObjectDeepCloner.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no14_object_class/practice/object-deep-cloner/src/ObjectDeepCloner.java)
- [ObjectDeepClonerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no14_object_class/practice/object-deep-cloner/test/ObjectDeepClonerTest.java)
- [ObjectDeepCloner.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no14_object_class/practice/object-deep-cloner/solution/ObjectDeepCloner.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no14_object_class
```
