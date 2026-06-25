# Modifiers in Java Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## access modifier

Keywords (`public`, `protected`, `private`, or default/package-private) that declare the visibility level and accessibility boundaries of classes, methods, constructors, and fields.

- **Why it matters**: It is the foundation of encapsulation, protecting internal object states from external corruption and limiting public APIs to stable, well-defined entry points.
- **Common confusion**: Assuming `protected` allows any class in a different package to access the member. Subclasses in other packages can only access the member through inheritance, not via an instance reference of the parent class.
- **Small example**: `private double balance;` ensures that balance cannot be mutated directly by client classes, requiring usage of `deposit()` or `withdraw()`.

## non-access modifier

Keywords (`static`, `final`, `abstract`, `synchronized`, `volatile`, `transient`, `native`, `strictfp`) that define behavioral characteristics and execution rules for classes, methods, and variables rather than access visibility.

- **Why it matters**: They control memory layout (class-level vs instance), JVM optimization limits, thread safety synchronization, cache visibility boundaries, and serialization exclusion.
- **Common confusion**: Confusing them with access modifiers. Non-access modifiers can be used in combination with access modifiers, such as `public static final`.
- **Small example**: `public synchronized void add()` uses the non-access modifier `synchronized` to ensure thread safety while keeping the method publicly accessible.

## static

A modifier indicating that a variable, method, block, or nested class belongs to the class blueprint itself rather than to any specific instance of that class.

- **Why it matters**: It enables shared class-wide state and access to utility methods without the overhead of creating object instances, with metadata allocated in Metaspace.
- **Common confusion**: Trying to access instance fields or use `this` or `super` keywords inside static methods. Because static methods execute without a receiver instance, the compiler rejects these references.
- **Small example**: `Math.pow(2, 3)` is invoked directly on the `Math` class blueprint rather than on a `new Math()` instance.

## final

A modifier that prevents a variable's value or reference from being reassigned, prevents a method from being overridden in subclasses, or prevents a class from being inherited.

- **Why it matters**: It provides compiler-enforced read-only safety, protects critical method overrides from subclass tampering, and enables performance optimizations like JIT constant inlining.
- **Common confusion**: Believing a `final` object reference makes the referenced object itself immutable. The reference cannot point to a new object, but internal fields of the object remain mutable.
- **Small example**: `final List<String> items = new ArrayList<>(); items.add("Apple"); // OK; items = new ArrayList<>(); // Compile error`

## abstract

A modifier declaring that a class cannot be instantiated directly and must be subclassed, or that a method contains no implementation body and must be overridden.

- **Why it matters**: It serves as a contract for polymorphism, forcing concrete subclasses to implement missing method signatures and define specific concrete behaviors.
- **Common confusion**: Declaring an abstract method inside a non-abstract (concrete) class. If a class contains even one abstract method, the class itself must be declared abstract.
- **Small example**: `public abstract class Animal { public abstract void makeSound(); }`

## volatile

A modifier applied to variables that guarantees thread visibility and prevents instruction reordering by forcing all reads and writes directly to main memory, bypassing CPU registers/caches.

- **Why it matters**: It ensures that updates made to a variable by one thread are instantly visible to all other threads, preventing stale cache reads in multi-threaded loops.
- **Common confusion**: Assuming `volatile` guarantees thread safety or atomicity for compound operations like `count++`. It does not perform lock synchronization, so race conditions can still occur.
- **Small example**: `private volatile boolean stopRequest = false;` ensures the worker thread immediately exits its loop once the controller thread sets the flag to true.

## transient

A modifier that prevents an instance variable from being serialized when the enclosing object is converted into a byte stream.

- **Why it matters**: It protects sensitive information (e.g., passwords, keys) from being saved to disk or transmitted over networks, and skips non-serializable resources like database connections or file streams.
- **Common confusion**: Marking a `static` variable `transient` to prevent it from being serialized. Static fields belong to the class metadata, not the object instance, and are already skipped during object serialization.
- **Small example**: `private transient String databasePassword;` ensures that database credentials are excluded when saving the system configuration object.
