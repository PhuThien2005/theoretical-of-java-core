# Java Memory Management - Part 3

## Learning Goal

This file covers a focused slice of **Java Memory Management**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `OutOfMemoryError` |`OutOfMemoryError` — Thrown when the JVM cannot allocate an object due to insufficient heap or native memory. |
| `StackOverflowError` | Stack stores method frames, local variables, and call flow for each thread. |

## Detailed Notes

### OutOfMemoryError

`java.lang.OutOfMemoryError` is a runtime error thrown when the Java Virtual Machine cannot allocate an object because it is out of memory, and no more memory can be made available by the Garbage Collector.

#### JVM Rule
- OOM is an **Error** (extends `java.lang.VirtualMachineError`), indicating a fatal system failure that standard applications should not catch or attempt to recover from.
- It can occur in different memory regions, signaled by the error message:
- **`java.lang.OutOfMemoryError: Java heap space`** — java.lang.OutOfMemoryError: Java heap space: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`java.lang.OutOfMemoryError: GC OverLimit exceeded`** — java.lang.OutOfMemoryError: GC OverLimit exceeded: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`java.lang.OutOfMemoryError: Metaspace`** — java.lang.OutOfMemoryError: Metaspace: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **Diagnostics**: Use `-XX:+HeapDumpOnOutOfMemoryError` and `-XX:HeapDumpPath` to generate a `.hprof` binary file for heap analysis when OOM occurs.

#### Code Example: OutOfMemoryError Scenario
```java
import java.util.ArrayList;
import java.util.List;

public class OOMDemo {
    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        
        // Infinite loop holding strong references to massive byte arrays.
        // The GC cannot reclaim these arrays because they are reachable from the list.
        while (true) {
            list.add(new byte[10 * 1024 * 1024]); // Allocate 10 MB per iteration
        }
    }
}
```

### StackOverflowError

`java.lang.StackOverflowError` is a runtime error thrown when a thread's stack space is exhausted.

#### JVM Rule
- Like OOM, this is a VirtualMachineError and should not be caught.
- It typically happens when the call stack grows too deep because of recursion, or if method frames are extremely large.
- The thread stack size is limited (default is typically 1MB on 64-bit systems) and is configured using the `-Xss` JVM flag (e.g., `-Xss512k`).

#### Code Example: StackOverflowError Scenario
```java
public class StackOverflowDemo {
    public static void main(String[] args) {
        recursiveCall(1);
    }

    // Bug: No base case to terminate recursion.
    // Each call pushes a new frame until the thread stack is completely full.
    private static void recursiveCall(int depth) {
        System.out.println("Depth: " + depth);
        recursiveCall(depth + 1); // Infinite recursion
    }
}
```

---

## Common Mistakes

### 1. Catching OutOfMemoryError or StackOverflowError
Many developers write `try-catch (Throwable t)` or `try-catch (OutOfMemoryError e)` blocks, thinking they can recover or log the error safely.
**The Trap:** When OOM is thrown, the JVM's state is completely compromised. The garbage collector has failed to free memory, threads are stalled, and attempting to log or execute recovery code might itself fail due to another OOM.
**The Correction:** Let the JVM terminate, capture the heap dump, and restart the process with fixed code or adjusted memory limits.

### 2. Confusing Heap and Stack Errors
- **Heap OOM**: Caused by memory leaks, caching issues, or simply processing too much data at once. Fixed by code optimization (removing leaks) or raising `-Xmx`.
- **StackOverflowError**: Caused by logical bugs (infinite recursion). Cannot be fixed by raising heap size (`-Xmx`). It requires fixing the recursion logic or raising stack size (`-Xss`).

### 3. Assuming GC Overhead Limit Exceeded is a Heap space error
While related, `GC Overhead Limit exceeded` occurs *before* physical heap space is fully exhausted. The JVM throws this preemptively to prevent the application from freezing completely while doing nothing but garbage collection. Raising heap size helps, but fixing memory leaks is the real solution.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?

## Why Heap and Stack Errors Differ

The JVM isolates thread-specific call structures from shared application data by dividing its memory into distinct regions, resulting in different error types when resources are exhausted. OutOfMemoryError occurs in the Heap or Metaspace when dynamic memory allocations exceed the physical space limits and the Garbage Collector is unable to reclaim any further space. StackOverflowError, on the other hand, occurs in the thread-specific stack when method invocation frames grow too deep and exhaust the allocated stack memory slot. Catching these VirtualMachineError subclasses within application code is a dangerous anti-pattern because the JVM's internal state is compromised and cannot guarantee stability. Attempting recovery operations or logging after an error occurs is highly likely to fail, potentially causing secondary errors like a nested OutOfMemoryError.

### Mental Model
```
+-------------------------------------------------------------+
| StackOverflowError (Thread Stack)                           |
| [ Frame n ] - Stack limit exceeded (Recursion loop)         |
|   ...                                                       |
| [ Frame 1 ] - Initial method call                           |
+-------------------------------------------------------------+

+-------------------------------------------------------------+
| OutOfMemoryError (Heap/Metaspace)                           |
| [ Reachable Objects | Reachable Objects | ... ]             |
| Heap is 100% full. GC cannot free space for new allocations |
+-------------------------------------------------------------+
```

### Code Example
```java
public class MemoryErrorsDemo {
    public static void main(String[] args) {
        try {
            causeStackOverflow(1);
        } catch (StackOverflowError e) {
            System.err.println("Caught StackOverflowError");
        }
    }

    private static void causeStackOverflow(int depth) {
        causeStackOverflow(depth + 1); // Infinite recursion
    }
}
```

### Cause-Effect Chain
Thread stack size exceeded &rarr; StackOverflowError thrown &rarr; State compromised &rarr; Attempted recovery catches Error &rarr; Log operations require memory &rarr; Nested OutOfMemoryError/Crash.

## Reference Links

- https://docs.oracle.com/javase/specs/jvms/se21/html/jvms-2.html#jvms-2.5 (Run-Time Data Areas)
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-11.html#jls-11.1.1 (Kinds of Exceptions - Errors)
