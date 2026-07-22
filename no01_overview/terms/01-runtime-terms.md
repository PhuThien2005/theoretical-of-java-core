# Runtime Terms

This file explains important runtime-related terms that appear across the overview notes. These terms are short in the main theory files, but they are important enough to deserve focused explanation cards.

## Term: Bytecode

### Short Definition

Bytecode is the intermediate instruction format stored in `.class` files and executed by the JVM.

### Why It Matters

Bytecode is one of the main reasons Java can be portable. The same bytecode can be executed by compatible JVMs on different platforms.

### Common Confusion

Bytecode is not Java source code. It is also not native machine code for a specific CPU.

### Example

```bash
javac HelloWorld.java
```

This command produces `HelloWorld.class`, which contains bytecode.

## Term: Runtime

### Short Definition

Runtime is the phase when a compiled program is actually executing.

### Why It Matters

Some problems are not visible during compilation. They happen only when the program executes with real values or real inputs.

### Common Confusion

Runtime is not the same as compile time. Compile time checks and translates source code. Runtime executes the compiled program.

## Term: Class Loading

### Short Definition

Class loading is the JVM process of finding and loading class definitions needed by a running Java program.

### Why It Matters

Java programs do not load every possible class immediately. Classes are loaded when the JVM needs them.

### Common Confusion

Class loading is not the same as object creation. Loading a class makes its definition available; creating an object creates a runtime instance from a class.

## Term: Reachability

### Short Definition

Reachability describes whether an object can still be accessed through active references from the running program.

### Why It Matters

Garbage Collection depends on reachability. An unreachable object may be reclaimed.

### Common Confusion

An object becoming unreachable does not mean it is immediately collected. It means the Garbage Collector may reclaim it later.
