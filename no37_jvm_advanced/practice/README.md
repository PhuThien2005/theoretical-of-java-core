# Practice Exercises: Advanced JVM

This folder contains hands-on practice exercises to reinforce your understanding of advanced JVM monitoring, Garbage Collection (GC) monitoring using MXBeans, and estimating runtime object memory footprints.

## Exercises

### 1. Garbage Collection Tuning Observer (`gc-tuning-observer`)
The JVM uses automatic garbage collectors (like G1, ZGC, or Parallel GC) to reclaim memory. Using standard platform MXBeans, you can monitor garbage collection cycle counts and accumulated pause times.
- **Goal**: Implement `GcTuningObserver` to generate garbage collection pressure programmatically, trigger system garbage collection, and track collection counts using `GarbageCollectorMXBean`.

#### Directory Structure
- [GcTuningObserver.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no37_jvm_advanced/practice/gc-tuning-observer/src/GcTuningObserver.java)
- [GcTuningObserverTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no37_jvm_advanced/practice/gc-tuning-observer/test/GcTuningObserverTest.java)
- [GcTuningObserver.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no37_jvm_advanced/practice/gc-tuning-observer/solution/GcTuningObserver.java)

---

### 2. Memory Profiler Simulator (`memory-profiler-simulator`)
Understanding the memory footprint of custom Java objects (including object headers, primitive fields, reference fields, and 8-byte alignment padding) is critical for performance tuning.
- **Goal**: Implement `MemoryProfilerSimulator` to estimate the memory footprint (in bytes) of a simple object by allocating multiple instances and measuring the difference in JVM heap memory.

#### Directory Structure
- [MemoryProfilerSimulator.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no37_jvm_advanced/practice/memory-profiler-simulator/src/MemoryProfilerSimulator.java)
- [MemoryProfilerSimulatorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no37_jvm_advanced/practice/memory-profiler-simulator/test/MemoryProfilerSimulatorTest.java)
- [MemoryProfilerSimulator.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no37_jvm_advanced/practice/memory-profiler-simulator/solution/MemoryProfilerSimulator.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no37_jvm_advanced
```
