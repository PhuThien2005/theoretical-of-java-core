# Advanced JVM Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## JVM

An abstract computing machine that enables a computer to run a Java program by executing Java bytecode and managing system resources (memory, threads, and I/O) dynamically.

- **Why it matters**: It provides platform independence ("Write Once, Run Anywhere") and handles low-level details like automatic memory management (Garbage Collection) and dynamic code loading.
- **Common confusion**: Confusing the JVM with the JRE (which includes standard libraries) or the JDK (which includes compilers and development tools). The JVM is strictly the execution engine.
- **Small example**: Running `java MyApp` starts a JVM instance that loads, verifies, and executes `MyApp.class`.

## ClassLoader

A subsystem of the JVM responsible for dynamically loading class files into the Method Area/Metaspace at runtime using a delegation-based lookup hierarchy.

- **Why it matters**: It allows class files to be loaded from diverse sources (local filesystem, network, JARs) dynamically and enforces namespace security boundaries.
- **Common confusion**: Thinking all classes are loaded eagerly when the JVM starts. Class loading is lazy, occurring only when a class is first referenced in code.
- **Small example**: Calling `MyClass.class.getClassLoader()` returns the ClassLoader instance (e.g., `AppClassLoader`) responsible for loading that specific class.

## JIT compiler

A performance-critical component of the JVM Execution Engine that compiles frequently executed bytecode (hot spots) into optimized native machine code at runtime.

- **Why it matters**: It bridges the gap between rapid application startup (via bytecode interpretation) and high peak execution speed, optimizing code on the fly based on real-world profile data.
- **Common confusion**: Believing Java is either strictly interpreted or compiled. It is both; `javac` compiles code to bytecode, and the JIT compiler compiles hot bytecode to machine code at runtime.
- **Small example**: Running a loop 10,000 times triggers the C1/C2 compilers to compile the loop body, reducing its execution time from milliseconds to microseconds.

## Eden

The memory region within the Young Generation of the heap where all newly created objects are initially allocated.

- **Why it matters**: It allows for extremely fast allocation using a sequential pointer-bump mechanism, aligning with the fact that most objects are short-lived.
- **Common confusion**: Believing objects reside in Eden for their entire lifespan. Eden is cleared completely during every Minor GC, and survivors are evacuated to Survivor spaces.
- **Small example**: Running `new MyObject()` immediately claims a block of memory in the Eden region.

## Survivor spaces

Two identical memory regions (Survivor 0 / S0 and Survivor 1 / S1) within the Young Generation used as an intermediate aging zone for objects that survive Minor GCs.

- **Why it matters**: They prevent heap memory fragmentation by copy-evacuating surviving objects back and forth, allowing the JVM to increment object age before promoting them to the Old Generation.
- **Common confusion**: Thinking both Survivor spaces are used concurrently. Only one Survivor space (the "from" space) is active at any time; the other (the "to" space) must remain empty to receive copies during the next Minor GC.
- **Small example**: During a Minor GC, surviving objects in Eden and S0 are copied to S1, S0 is cleared, and S1 becomes the active Survivor space.

## Shenandoah GC

A low-pause-time garbage collector that performs compaction concurrently with running Java application threads, keeping pause times minimal regardless of heap size.

- **Why it matters**: It prevents long Stop-The-World (STW) pauses in large-heap applications, making Java suitable for low-latency, real-time systems.
- **Common confusion**: Assuming Shenandoah eliminates pause times completely. It still requires very short pauses for root set scanning (typically under a millisecond).
- **Small example**: Activating Shenandoah via the flag `-XX:+UseShenandoahGC` to keep GC pauses under 10ms on a 100GB heap.

## Brooks Pointer

An additional reference field prepended to every object header in older Shenandoah GC implementations, pointing to the object itself or to its forwarded copy.

- **Why it matters**: It allows concurrent compaction by redirecting reads and writes to the correct memory location of an object while the GC is actively moving it.
- **Common confusion**: Thinking application threads must wait for the GC to finish moving an object. With Brooks Pointers, threads immediately access the new copy via the redirected pointer.
- **Small example**: An application thread attempts to write to `obj.field`; a load barrier inspects the Brooks Pointer, redirects to the to-space copy, and performs the write.

## JVM flags

Command-line options passed to the Java launcher to configure JVM behavior, tune memory allocations, select GC algorithms, and control compiler behavior.

- **Why it matters**: They allow operators to optimize application performance for specific hardware environments without changing any source code.
- **Common confusion**: Assuming all flags are supported on every Java version or vendor. Standard flags are stable, but `-XX` options can be removed or change behavior between JDK updates.
- **Small example**: Setting `-Xms512m -Xmx1024m -XX:+UseG1GC` configures the initial heap, maximum heap, and garbage collector type.
