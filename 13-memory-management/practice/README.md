# Practice Exercises: Memory Management

This folder contains hands-on practice exercises to reinforce your understanding of JVM memory management, garbage collection (GC) roots, memory leaks, and weak references.

## Exercises

### 1. Memory Leak Simulator (`memory-leak-simulator`)
A memory leak in Java occurs when objects that are no longer needed by the application are still referenced from GC Roots (like static fields). This prevents the Garbage Collector from reclaiming them, eventually causing an `OutOfMemoryError` (OOM).
- **Goal**: Implement a controlled simulation that intentionally leaks memory using static collections to trigger an `OutOfMemoryError`, catches it safely, and releases the references to restore JVM stability.

#### Directory Structure
- [MemoryLeakSimulator.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/13-memory-management/practice/memory-leak-simulator/src/MemoryLeakSimulator.java)
- [MemoryLeakSimulatorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/13-memory-management/practice/memory-leak-simulator/test/MemoryLeakSimulatorTest.java)
- [MemoryLeakSimulator.java (Solution)](file:///home/fhu_thjen/projects/learning-java/13-memory-management/practice/memory-leak-simulator/solution/MemoryLeakSimulator.java)

---

### 2. Weak Reference Cache (`weak-reference-cache`)
Strong references prevent the garbage collector from reclaiming objects. Java provides `WeakReference` to allow objects to be garbage collected when no strong references to them remain. This is highly useful for building in-memory caches.
- **Goal**: Implement a simple, generic key-value cache using `WeakReference` and demonstrate how cached values are cleared automatically by the JVM during garbage collection when the strong references to them are discarded.

#### Directory Structure
- [WeakReferenceCache.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/13-memory-management/practice/weak-reference-cache/src/WeakReferenceCache.java)
- [WeakReferenceCacheTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/13-memory-management/practice/weak-reference-cache/test/WeakReferenceCacheTest.java)
- [WeakReferenceCache.java (Solution)](file:///home/fhu_thjen/projects/learning-java/13-memory-management/practice/weak-reference-cache/solution/WeakReferenceCache.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 13-memory-management
```
