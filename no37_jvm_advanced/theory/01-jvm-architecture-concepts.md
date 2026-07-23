# Advanced JVM - Part 1

## Learning Goal

This file covers a focused slice of **Advanced JVM**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `JVM architecture` | The JVM executes bytecode and manages runtime services such as memory, JIT, and GC. |
| `Class Loader Subsystem` |`Class Loader Subsystem` — Class Loader Subsystem provides specific functionality and rules in Java development. |
| `Runtime Data Areas:` | Runtime Data Areas is a group of related rules in Advanced JVM that groups several related details. |
| `Heap` | Heap stores objects created at runtime. |
| `Stack` | Stack stores method frames, local variables, and call flow for each thread. |
| `Method Area / Metaspace` | Metaspace stores class metadata outside the ordinary Java heap in modern JVMs. |
| `PC Register` |The PC register tracks the current JVM instruction for a thread. |
| `Native Method Stack` | Native Method Stack stores frames for executing native (non-Java) methods. |

## Detailed Notes

### JVM architecture

The JVM executes bytecode and manages runtime services such as memory, JIT, and GC.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `JVM architecture` in one sentence.
- Recognize `JVM architecture` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `JVM architecture`.

Tiny example or mental model:

- When reading code, ask: what does `JVM architecture` change, allow, reject, or clarify?

### Class Loader Subsystem

### Runtime Data Areas:

Runtime Data Areas is a group of related rules in Advanced JVM that groups several related details.

### Heap

Heap stores objects created at runtime.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Heap` in one sentence.
- Recognize `Heap` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Heap`.

Tiny example or mental model:

- When reading code, ask: what does `Heap` change, allow, reject, or clarify?

### Stack

Stack stores method frames, local variables, and call flow for each thread.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Stack` in one sentence.
- Recognize `Stack` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Stack`.

Tiny example or mental model:

- When reading code, ask: what does `Stack` change, allow, reject, or clarify?

### Method Area / Metaspace

Metaspace stores class metadata outside the ordinary Java heap in modern JVMs.

### PC Register

The PC register tracks the current JVM instruction for a thread.

### Native Method Stack

Native Method Stack stores frames for executing native (non-Java) methods, such as JNI functions written in C or C++.

It matters because when Java code calls native code (like native crypto libraries or platform APIs), the thread's execution context shifts to this stack. A stack overflow here can crash the entire JVM process without throwing a standard Java StackOverflowError.

Practical check:

- Define `Native Method Stack` in one sentence.
- Recognize `Native Method Stack` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Native Method Stack`.

Tiny example or mental model:

- When reading code, ask: what does `Native Method Stack` change, allow, reject, or clarify?

## Code Examples

### Querying Memory Information Programmatically
```java
Runtime runtime = Runtime.getRuntime();
long maxMemory = runtime.maxMemory();   // Equivalent to -Xmx
long totalMemory = runtime.totalMemory(); // Current heap size allocated
long freeMemory = runtime.freeMemory();   // Free space in current heap

System.out.println("Max Heap: " + (maxMemory / 1024 / 1024) + " MB");
```

## Common Mistakes

- **Assuming StackOverflowError is Heap-related**: A `StackOverflowError` occurs in the Thread Stack when call frames exceed stack memory limits (often due to infinite recursion). This is unrelated to the Heap.
- **Confusing Metaspace with Heap**: Class metadata is stored in Metaspace (off-heap/native memory) since Java 8. It does not compete with Java objects for Heap space, but can still exhaust native memory if too many classes are loaded.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?

## Why Class Loading Has Three Distinct Phases

The JVM class loading subsystem splits class loading into three distinct phases (Loading, Linking, and Initializing) to enforce security, verify structural integrity, and optimize memory allocation before code execution. During the **Loading** phase, the JVM locates the binary representation of a class (typically a `.class` file) and imports it into the Method Area/Metaspace, creating a `java.lang.Class` object. In the **Linking** phase, the JVM performs Verification (crucial for security, checking format, bytecode constraints, and type rules to prevent malicious exploits), Preparation (allocating memory for static fields and initializing them to default values), and Resolution (optionally resolving symbolic references into direct references). Finally, during **Initialization**, the JVM executes the static initialization blocks and assigns the actual values declared in code to the static variables via the compiler-generated `<clinit>` method.

### Mental Model: Class Loading Phases

```text
+-------------------------------------------------------------------------------+
|                               CLASS LOADING                                   |
+-------------------------------------------------------------------------------+
|  1. LOADING          |  2. LINKING                                 |  3. INIT |
|                      |  a. Verification -> b. Prep -> c. Resolution|          |
|  [Find bytecode]     |  [Verify safety]  [Alloc defaults] [Resolve]| [<clinit>|
|  .class file -> JVM  |  Type checking    static x = 0     symbols  |  x = 42] |
+-------------------------------------------------------------------------------+
```

### Code Example

```java
package theory;

public class ClassLoaderDemo {
    // Allocation of static memory occurs in Preparation, but value assignment occurs in Initialization
    public static final int CONSTANT_VAL = 42; 
    public static int mutableVal = 99;

    static {
        System.out.println("ClassLoaderDemo initialized!");
        mutableVal = 100;
    }

    public static void main(String[] args) {
        // Accessing CONSTANT_VAL (a constant compile-time value) does NOT trigger full initialization
        System.out.println("Constant: " + ClassLoaderDemo.CONSTANT_VAL);
        // Accessing mutableVal triggers static block execution (Initialization)
        System.out.println("Mutable Value: " + ClassLoaderDemo.mutableVal);
    }
}
/* Output:
Constant: 42
ClassLoaderDemo initialized!
Mutable Value: 100
*/
```

### Cause-Effect Chain

Classloader reads `.class` byte stream &rarr; Verification runs type checks &rarr; Preparation allocates memory with default values &rarr; Initialization runs `<clinit>` method &rarr; Class is fully usable by the application.

## Reference Links

- https://docs.oracle.com/javase/specs/jvms/se21/html/jvms-5.html (Chapter 5. Loading, Linking, and Initializing)

