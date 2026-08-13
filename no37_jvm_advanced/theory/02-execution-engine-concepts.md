# Advanced JVM - Part 2

| Concept | What to know |
| --- | --- |
| `Execution Engine` |`Execution Engine` — JVM component that executes bytecode via Interpreter and JIT Compiler. |
| `Interpreter` |`Interpreter` — Interpreter provides specific functionality and rules in Java development. |
| `JIT Compiler` | The JIT compiler turns hot bytecode into optimized machine code at runtime. |
| `Garbage Collector` |`Garbage Collector` — Garbage Collector provides specific functionality and rules in Java development. |
| `Native Interface` |`Native Interface` — Native Interface provides specific functionality and rules in Java development. |
| `Heap generation:` | Heap generation separates objects by age to optimize garbage collection efficiency. |
| `Young Generation` |`Young Generation` — Young Generation provides specific functionality and rules in Java development. |
| `Eden` |`Eden` — Eden provides specific functionality and rules in Java development. |

## Detailed Notes

### Execution Engine

### Interpreter

### JIT Compiler

The JIT compiler turns hot bytecode into optimized machine code at runtime.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `JIT Compiler` in one sentence.
- Recognize `JIT Compiler` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `JIT Compiler`.

Tiny example or mental model:

- When reading code, ask: what does `JIT Compiler` change, allow, reject, or clarify?

### Garbage Collector

### Native Interface

### Heap generation:

Heap generation separates objects by age (Young and Old generations) based on the weak generational hypothesis (most objects die young).

It matters because garbage collecting the entire heap is slow. By separating short-lived objects into the Young generation and long-lived objects into the Old generation, GC runs faster on smaller zones.

Practical check:

- Define `Heap generation:` in one sentence.
- Recognize `Heap generation:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Heap generation:`.

Tiny example or mental model:

- When reading code, ask: what does `Heap generation:` change, allow, reject, or clarify?

### Young Generation

### Eden

## Code Examples

### Explicit GC Call (Avoid in production)
```java
// Requests JVM to run Garbage Collector, but does not guarantee immediate execution
System.gc();
```

## Common Mistakes

- **Relying on System.gc()**: Calling `System.gc()` is a bad practice. It suggests the garbage collector should run, but the JVM can ignore it, and if it runs, it triggers a major/full stop-the-world GC pause.
- **Eden Size Misconfiguration**: Setting Eden too small causes frequent Minor GCs; setting it too large increases the duration of Minor GC pauses.

## Why JIT Compilation and Interpretation Are Combined

The JVM execution engine combines interpretation and Just-In-Time (JIT) compilation to balance fast application startup times with high peak execution performance. When an application launches, the **Interpreter** starts executing bytecode immediately without waiting for compilation, avoiding any startup latency. However, as the application runs, the JVM profiles the code to identify "hot spots"—methods or loops executed frequently. These hot spots are then compiled into native machine code by JIT compilers, specifically using a tiered compilation architecture. The **C1 compiler (Client)** compiles code quickly with simple optimizations to reduce execution time early on, while the **C2 compiler (Server)** performs highly aggressive optimizations (like method inlining, loop unrolling, and escape analysis) to achieve peak steady-state performance.

### Mental Model: Tiered Compilation Pipeline

```text
               +-------------+
               |  Bytecode   |
               +------+------+
                      |
                      v
             [ Tier 0: Interpreter ]  <-- Immediate execution, profiles code
                      |
                      v (Method Invocation / Back-Edge Counters exceed threshold)
             [ Tier 3: C1 Compiler ]   <-- Fast compilation with basic profiling
                      |
                      v (Fully profiled, extremely hot)
             [ Tier 4: C2 Compiler ]   <-- Aggressive optimizations, native code
```

### Code Example

Below is a demonstration that simulates hot-spot profiling. In a real JVM, executing a method repeatedly triggers JIT compilation.

```java
package theory;

public class JitTieringDemo {
    public static void main(String[] args) {
        long start = System.nanoTime();
        
        // Simulating invocation of a method to trigger compilation threshold
        double sum = 0;
        for (int i = 0; i < 15_000; i++) {
            sum += compute(i);
        }
        
        long duration = System.nanoTime() - start;
        System.out.println("Sum: " + sum + " computed in " + (duration / 1_000_000.0) + " ms");
    }

    // A method that will become "hot" and get JIT compiled by C1/C2 compilers
    private static double compute(int value) {
        return Math.sin(value) * Math.cos(value);
    }
}
/* Output (Simulated or run with -XX:+PrintCompilation):
Sum: -0.4908... computed in 4.2 ms
*/
```

### Cause-Effect Chain

Interpreter starts running immediately &rarr; Profiling counters track method calls & back-edges &rarr; Threshold reached &rarr; C1 compiles with light optimizations &rarr; Continued execution profiles code further &rarr; C2 compiles with aggressive optimizations (e.g. escape analysis) &rarr; Peak native performance achieved.

## Reference Links

- https://docs.oracle.com/en/java/javase/21/gctuning/ (Garbage Collection Tuning Guide / JIT Overview)
