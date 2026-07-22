# Practice Exercises: Exception Handling

This folder contains hands-on practice exercises to reinforce your understanding of Java exceptions, try-catch-finally blocks, try-with-resources, custom exceptions, and exception chaining.

## Exercises

### 1. Safe Resource Reader (`safe-resource-reader`)
Java's `try-with-resources` statement guarantees that each resource implementing `AutoCloseable` is closed at the end of the statement. This prevents resource leaks (e.g., file descriptors, sockets) even if exceptions are thrown during execution.
- **Goal**: Implement a try-with-resources operation utilizing a custom `AutoCloseable` class. Verify that resources are automatically closed when operations succeed or fail.

#### Directory Structure
- [SafeResourceReader.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no12_exception_handling/practice/safe-resource-reader/src/SafeResourceReader.java)
- [SafeResourceReaderTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no12_exception_handling/practice/safe-resource-reader/test/SafeResourceReaderTest.java)
- [SafeResourceReader.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no12_exception_handling/practice/safe-resource-reader/solution/SafeResourceReader.java)

---

### 2. Chained Exception Tracker (`chained-exception-tracker`)
When writing multi-tier applications, low-level exceptions (like database connection issues) are often caught and wrapped in high-level business exceptions. This preserves the root cause stack trace for debugging.
- **Goal**: Implement a multi-level execution method that catches database failures, wraps them in custom business exceptions, and provides an unwrapper method to find the root exception cause.

#### Directory Structure
- [ChainedExceptionTracker.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no12_exception_handling/practice/chained-exception-tracker/src/ChainedExceptionTracker.java)
- [ChainedExceptionTrackerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no12_exception_handling/practice/chained-exception-tracker/test/ChainedExceptionTrackerTest.java)
- [ChainedExceptionTracker.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no12_exception_handling/practice/chained-exception-tracker/solution/ChainedExceptionTracker.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no12_exception_handling
```
