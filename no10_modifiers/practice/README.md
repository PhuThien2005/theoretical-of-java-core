# Practice Exercises: Modifiers

This folder contains hands-on practice exercises to reinforce your understanding of Java modifiers: `static`, `final`, `volatile`, and `synchronized`.

## Exercises

### 1. Thread-Safe Singleton (`thread-safe-singleton`)
A Singleton is a design pattern that restricts the instantiation of a class to one single instance. In a multithreaded environment, lazy initialization must be synchronized properly to prevent multiple threads from instantiating different objects.
- **Goal**: Implement a thread-safe, lazily-initialized Singleton using double-checked locking with the `volatile`, `static`, and `synchronized` modifiers.

#### Directory Structure
- [ThreadSafeSingleton.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no10_modifiers/practice/thread-safe-singleton/src/ThreadSafeSingleton.java)
- [ThreadSafeSingletonTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no10_modifiers/practice/thread-safe-singleton/test/ThreadSafeSingletonTest.java)
- [ThreadSafeSingleton.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no10_modifiers/practice/thread-safe-singleton/solution/ThreadSafeSingleton.java)

---

### 2. Immutable User Session (`immutable-user-session`)
Immutability is key for thread safety and preventing accidental state modifications. To make a class fully immutable, you must declare the class `final`, keep all fields `private` and `final`, and perform defensive copying of any mutable collections or references.
- **Goal**: Implement an immutable `ImmutableUserSession` class that manages a user session ID, username, and a list of security permissions.

#### Directory Structure
- [ImmutableUserSession.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no10_modifiers/practice/immutable-user-session/src/ImmutableUserSession.java)
- [ImmutableUserSessionTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no10_modifiers/practice/immutable-user-session/test/ImmutableUserSessionTest.java)
- [ImmutableUserSession.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no10_modifiers/practice/immutable-user-session/solution/ImmutableUserSession.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no10_modifiers
```
