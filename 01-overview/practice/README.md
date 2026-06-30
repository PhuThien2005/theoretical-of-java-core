# Practice Exercises: Java Technology Overview

This folder contains hands-on practice exercises to reinforce your understanding of Java's runtime environment (JVM), JIT (Just-In-Time) compilation benefits, and runtime memory monitoring.

## Exercises

### 1. Runtime Inspector (`runtime-inspector`)
The Java Virtual Machine (JVM) manages memory automatically. Using the `java.lang.Runtime` class, you can inspect memory allocations (max, total, free, and used memory) at runtime.
- **Goal**: Implement `RuntimeInspector` to extract JVM memory metrics and calculate currently used heap space.

#### Directory Structure
- [RuntimeInspector.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/01-overview/practice/runtime-inspector/src/RuntimeInspector.java)
- [RuntimeInspectorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/01-overview/practice/runtime-inspector/test/RuntimeInspectorTest.java)
- [RuntimeInspector.java (Solution)](file:///home/fhu_thjen/projects/learning-java/01-overview/practice/runtime-inspector/solution/RuntimeInspector.java)

---

### 2. Platform Compatibility Checker (`platform-compatibility-checker`)
HotSpot JVM optimizes execution speed by dynamically compiling hot code paths (frequently run code blocks) to machine code using the JIT Compiler.
- **Goal**: Implement a math computation routine, warm it up to trigger JIT compilation, and measure the execution speedup ratio between early (interpreted) and optimized (JIT-compiled) runs.

#### Directory Structure
- [PlatformCompatibilityChecker.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/01-overview/practice/platform-compatibility-checker/src/PlatformCompatibilityChecker.java)
- [PlatformCompatibilityCheckerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/01-overview/practice/platform-compatibility-checker/test/PlatformCompatibilityCheckerTest.java)
- [PlatformCompatibilityChecker.java (Solution)](file:///home/fhu_thjen/projects/learning-java/01-overview/practice/platform-compatibility-checker/solution/PlatformCompatibilityChecker.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 01-overview
```
