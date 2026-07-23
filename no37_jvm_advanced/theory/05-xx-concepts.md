# Advanced JVM - Part 5

## Learning Goal

This file covers a focused slice of **Advanced JVM**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `-XX` |`XX` — XX provides specific functionality and rules in Java development. |
| `Basic profiling` |`Basic profiling` — Basic profiling provides specific functionality and rules in Java development. |
| `Memory dump` |`Memory dump` — Memory dump provides specific functionality and rules in Java development. |
| `Thread dump` | A thread dump is a snapshot of the state and stack trace of all active threads in a JVM. |

## Detailed Notes

### -XX

### Basic profiling

### Memory dump

### Thread dump

A thread dump is a snapshot of all active threads inside the JVM, showing the state (RUNNABLE, BLOCKED, WAITING) and full stack trace for each.

It matters because it allows developers to diagnose deadlocks, thread contention, infinite loops, and resource locks in concurrent applications.

Practical check:

- Define `Thread dump` in one sentence.
- Recognize `Thread dump` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Thread dump`.

Tiny example or mental model:

- `new Thread(task).start()` starts work on another thread.

## Code Examples

### CLI Command to capture thread/heap dumps
```bash
# Capture thread dump (PID: 1234)
jstack 1234 > thread_dump.txt

# Capture heap dump (PID: 1234)
jmap -dump:format=b,file=heap_dump.hprof 1234
```

## Common Mistakes

- **Manually parsing heap dumps**: Heap dumps are binary files and can be huge. Do not open them in raw text editors. Always use specialized tools like Eclipse Memory Analyzer (MAT) or VisualVM.
- **Failing to capture thread dumps during deadlocks**: When application threads hang, immediately capture 2-3 thread dumps spaced a few seconds apart to identify which threads are blocked on which monitors.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?

## Why JVM Flag Classifications Exist

The JVM organizes its command-line configuration options into three distinct classifications—Standard, Non-Standard (`-X`), and Developer/Experimental (`-XX`)—to manage flag stability, vendor portability, and experimental features. **Standard options** (e.g., `-classpath`, `-verbose:gc`) are guaranteed to be supported across all compliant JVM vendors and versions, ensuring basic command-line stability. **Non-Standard options** (prefixed with `-X`, such as `-Xms` and `-Xmx`) customize HotSpot-specific memory layouts or execution settings, but are not guaranteed to be supported by other vendors and are subject to change without notice. **Developer, experimental, or unstable options** (prefixed with `-XX`, such as `-XX:NewRatio` or `-XX:+UseG1GC`) allow deep customization of GC algorithms, JIT compiler policies, and memory sub-boundaries. These flags require explicit unlocking (via `-XX:+UnlockDiagnosticVMOptions` or `-XX:+UnlockExperimentalVMOptions`) because improper usage can severely degrade performance, crash the JVM, or lead to undefined runtime behavior.

### Mental Model: JVM Flag Categories and Heap Boundary Tuning

```text
  JVM Options Spectrum:
  [ Standard: -cp, -version ]  ===> Supported universally, stable
  [ Non-Standard: -Xms, -Xmx ] ===> HotSpot-specific heap sizing, subject to change
  [ Experimental: -XX:NewRatio ]==> System developer parameters, unstable/requires unlock
  
  Heap Sizing Flags Memory Layout:
  |<---------------------------- -Xmx (Max Heap Size) ----------------------------->|
  |<--------- -Xms (Initial Heap Size) --------->|
  +----------------------------------------------+---------------------------------+
  |      Young Gen (Eden + S0 + S1)              |            Old Gen              |
  |  (Proportion tuned via -XX:NewRatio)         |                                 |
  +----------------------------------------------+---------------------------------+
```

### Code Example

Below is a runnable Java program that queries heap parameters to show how command-line options set the memory boundaries.

```java
package theory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class HeapTuningInspection {
    public static void main(String[] args) {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        
        long initHeap = heapMemoryUsage.getInit();
        long maxHeap = heapMemoryUsage.getMax();
        
        System.out.println("Initial Heap (-Xms): " + (initHeap / 1024 / 1024) + " MB");
        System.out.println("Maximum Heap (-Xmx): " + (maxHeap / 1024 / 1024) + " MB");
    }
}
/* Output (Default or when run with -Xms256m -Xmx512m):
Initial Heap (-Xms): 256 MB
Maximum Heap (-Xmx): 512 MB
*/
```

### Cause-Effect Chain

Configuring standard flags &rarr; Guarantees cross-vendor portability &rarr; Adding `-Xms` and `-Xmx` sets boundary limits on Java heap &rarr; Adding `-XX:NewRatio=2` allocates twice as much space to Old Gen as Young Gen &rarr; Unlocking `-XX` experimental flags enables advanced features like Shenandoah &rarr; Fine-tuned JVM performance achieved for target workload.

## Reference Links

- https://docs.oracle.com/en/java/javase/21/docs/specs/man/java.html (Java Command-Line Tool Options Reference)

