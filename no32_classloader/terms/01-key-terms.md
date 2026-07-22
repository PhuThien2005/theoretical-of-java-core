# ClassLoader Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## Class loader

An abstract class (`java.lang.ClassLoader`) responsible for loading class definitions from binary data sources (like filesystem files, network resources, or databases) into the JVM runtime memory, turning them into Class objects.

- **Why it matters:** It is the mechanism that dynamically loads bytecode at runtime, enabling features like class isolation, plugins, hot-reloading, and server containers.
- **Common confusion:** Compiling code with `javac` only verifies type safety and creates `.class` bytecode; the ClassLoader is a runtime-only system that executes when classes are referenced during run.
- **Small example:** `MyClass.class.getClassLoader()` returns the ClassLoader instance that loaded `MyClass`.

## Bootstrap ClassLoader

The root-level classloader of the JVM, typically written in native C/C++ code, responsible for loading essential JDK runtime classes (like `java.lang.Object`, `java.lang.String`) from the platform runtime images.

- **Why it matters:** It bootstraps the virtual machine by loading core packages that all other Java classes depend on, serving as the foundational trust anchor for the parent delegation hierarchy.
- **Common confusion:** Since it is written in native code, it has no Java object representation; calling `String.class.getClassLoader()` returns `null`.
- **Small example:** `Object.class.getClassLoader(); // returns null`

## Platform ClassLoader

The standard classloader (known as the Extension ClassLoader in Java 8 and earlier) that loads platform-specific non-core APIs and modules that are part of the Java standard but not embedded directly in the core base module.

- **Why it matters:** It separates Java SE specification libraries (like JDBC, XML processing) from the absolute bare minimum runtime, keeping the Bootstrap loader smaller and cleaner.
- **Common confusion:** In Java 9+, the extension directory mechanism (`jre/lib/ext`) was removed in favor of modular platform descriptors, but the Platform ClassLoader remains as a transition component.
- **Small example:** `java.sql.Connection.class.getClassLoader(); // returns the Platform ClassLoader instance`

## Application ClassLoader

Also known as the System ClassLoader, this Java class loader loads user-defined application classes and external libraries specified by the classpath (`-classpath`, `-cp`, or `CLASSPATH` environment variable).

- **Why it matters:** It is the loader that actually runs your custom code, your main method, and third-party dependency JARs.
- **Common confusion:** It is often assumed to be the default loader for *everything*, but it delegates to the Platform and Bootstrap loaders for standard library classes.
- **Small example:** `ClassLoader.getSystemClassLoader(); // returns the Application ClassLoader`

## Parent delegation model

A search protocol where a classloader, upon receiving a request to load a class, delegates the request to its parent loader before attempting to find and load the class itself.

- **Why it matters:** It prevents class shadowing, guarantees that core platform APIs cannot be hijacked or overridden by application-provided classes, and maintains JVM security namespaces.
- **Common confusion:** The parent loader relation is established by composition (a parent field inside the class loader instance), not by Java class inheritance.
- **Small example:** When loading `java.lang.Object`, the Application ClassLoader delegates to the Platform ClassLoader, which delegates to the Bootstrap ClassLoader, which loads it.

## Thread Context ClassLoader (TCCL)

A context-dependent classloader associated with the current running Thread, which can be retrieved using `Thread.currentThread().getContextClassLoader()`.

- **Why it matters:** It allows core system classes loaded by the Bootstrap ClassLoader to bypass the strict parent delegation model to load classes provided by user applications (crucial for SPI frameworks like JDBC and JNDI).
- **Common confusion:** TCCL is not a new type of classloader; it is simply a reference hook on a thread pointing to an existing ClassLoader instance (usually the Application ClassLoader).
- **Small example:** `Thread.currentThread().setContextClassLoader(myCustomLoader);`

## Loading phase

The first step in the classloading lifecycle, where the JVM locates binary bytecode for a class by name and creates a raw class structure in the Metaspace.

- **Why it matters:** It determines how classes are searched for, read, and registered as metadata.
- **Common confusion:** Loading a class does not automatically execute its static blocks or resolve its dependencies; those happen in subsequent linking and initialization phases.
- **Small example:** `Class.forName("com.example.Demo", false, loader); // Loads without initializing`

## Linking phase

The second step in the classloading lifecycle, which includes Verification (bytecode safety checks), Preparation (allocating static field memory with default values), and Resolution (mapping symbolic references to direct memory pointers).

- **Why it matters:** It ensures the class is safe to run and wires all runtime references so the bytecode can execute properly.
- **Common confusion:** During Preparation, static variables only receive JVM defaults (like `0` or `null`), not their developer-specified initial values (like `42`).
- **Small example:** `static int x = 42; // x gets value 0 during linking's preparation step`

## Initialization phase

The final step of the classloading lifecycle where the JVM runs the compiled `<clinit>` method, executing static blocks and assigning developer-specified initial values to static variables.

- **Why it matters:** It prepares class-level state for usage, running initialization logic lazily when the class is first actively referenced.
- **Common confusion:** This phase is executed only once per class definition per classloader, under strict thread-safety locks held by the JVM.
- **Small example:** Accessing `MyClass.myStaticField` or instantiating `new MyClass()` triggers the initialization phase.

## Metaspace

The native memory region of the JVM (introduced in Java 8 to replace PermGen) where class metadata, classloaders, method bytecode, and the constant pool are stored.

- **Why it matters:** Unlike PermGen, Metaspace is not restricted to a fixed default size and scales automatically with the OS native memory, though it can still be limited using `-XX:MaxMetaspaceSize`.
- **Common confusion:** It does not store actual Java objects (which go to the garbage-collected Java Heap) but stores the class structures needed to instantiate them.
- **Small example:** Loading millions of classes dynamically can lead to `java.lang.OutOfMemoryError: Metaspace`.

## Memory leak

A bug where objects that are no longer needed by the application are still referenced directly or indirectly from GC roots, preventing the garbage collector from reclaiming their memory.

- **Why it matters:** In classloading contexts, if an application reference keeps even a single class instance alive, it holds a reference to its Class object, which references its ClassLoader, which keeps *all* loaded class definitions alive in Metaspace, causing a major leak.
- **Common confusion:** Setting a custom classloader reference to `null` is not enough to garbage collect it if threads, ThreadLocals, or system-wide registries (like JDBC) still hold references to any classes it loaded.
- **Small example:** A web application undeploys but leaves a thread-local value containing an application class instance, preventing the entire webapp classloader from being reclaimed.
