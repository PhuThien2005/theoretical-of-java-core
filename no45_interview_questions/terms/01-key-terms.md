# Common Java Core Interview Questions Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## JDK

The Java Development Kit (JDK) is a software development environment used for developing Java applications. It contains the JRE (Java Runtime Environment) along with development tools like compiler (`javac`), debugger (`jdb`), and documentation tools.

* **Why it matters:** Developers need the JDK to compile source code (`.java`) into bytecode (`.class`). Without it, you cannot build Java projects locally or run profiling tools.
* **Common confusion:** Confusing JDK with JRE. End-users only need the JRE to run Java programs, whereas developers require the full JDK to compile and debug.
* **Small example:** Compiling a class using `javac HelloWorld.java` from the terminal requires a JDK installation.

## JRE

The Java Runtime Environment (JRE) is the part of the Java SDK that contains the JVM, core libraries, and other supporting files needed to run compiled Java programs.

* **Why it matters:** It provides the minimum requirements for executing a Java application on a client machine.
* **Common confusion:** Assuming the JRE can compile code. It contains the JVM and class libraries but lacks `javac` and developer tools.
* **Small example:** Running a pre-compiled Java program with `java HelloWorld` requires a JRE (which is also bundled inside the JDK).

## JVM

The Java Virtual Machine (JVM) is an abstract computing machine that enables a computer to run a Java program. It loads, verifies, executes bytecode, and provides a runtime execution environment.

* **Why it matters:** It is responsible for platform independence ("Write Once, Run Anywhere"). The JVM converts platform-neutral bytecode into OS-specific machine instructions.
* **Common confusion:** Thinking the JVM is platform-independent. While the bytecode is platform-independent, each operating system (Windows, Linux, macOS) requires its own specific JVM implementation.
* **Small example:** The JVM handles heap memory allocation and runs Garbage Collection automatically when memory runs low.

## HashSet

A collection that stores unique elements. It is backed by a `HashMap` and does not maintain any insertion order.

* **Why it matters:** Allows O(1) time complexity for check, add, and remove operations while preventing duplicate elements from entering the set.
* **Common confusion:** Assuming `HashSet` maintains insertion order or is sorted. In reality, it makes no guarantees about the order of elements over time.
* **Small example:** `Set<String> set = new HashSet<>(List.of("A", "A", "B"));` results in a set containing `["A", "B"]`.

## Comparable

A functional interface (`java.lang.Comparable`) implemented by a class to define its natural ordering. It contains a single method `compareTo(T o)`.

* **Why it matters:** Allows objects of the implementing class to be sorted automatically by collections utilities like `Collections.sort()` or `Arrays.sort()`.
* **Common confusion:** Confusing `Comparable` with `Comparator`. `Comparable` defines sorting inside the class itself, whereas `Comparator` is defined externally.
* **Small example:** Implementing `Comparable<Student>` to sort students naturally by their numeric `id`.

## Comparator

An interface (`java.util.Comparator`) used to define custom sorting rules external to the objects being sorted. It contains the method `compare(T o1, T o2)`.

* **Why it matters:** Enables multiple different sort views (e.g., sorting by name, then by age) without modifying the original class's code.
* **Common confusion:** Believing you can only have one `Comparator` per class. You can define as many custom comparators as needed, often using lambdas.
* **Small example:** `list.sort((s1, s2) -> s1.getName().compareTo(s2.getName()));` sorts a list by name.

## Map

An object that maps keys to values. A map cannot contain duplicate keys; each key can map to at most one value.

* **Why it matters:** Provides highly efficient key-value lookup capabilities, which is a fundamental requirement in almost all software systems.
* **Common confusion:** Thinking `Map` extends the `Collection` interface. It does not; it is a separate interface hierarchy in the `java.util` package.
* **Small example:** `Map<String, String> map = new HashMap<>(); map.put("user1", "Alice");`

## FlatMap

An intermediate operation in the Stream API that transforms a stream of collections/streams into a single flattened stream of elements.

* **Why it matters:** Solves the problem of working with nested collections (like `List<List<T>>`) by flattening them into a single stream of `T` values for processing.
* **Common confusion:** Thinking `flatMap` is just a faster `map`. `map` transforms each element 1-to-1, whereas `flatMap` transforms each element into a stream and merges them.
* **Small example:** `List<String> flat = nestedLists.stream().flatMap(List::stream).collect(Collectors.toList());`

## Generics

A language feature introduced in Java 5 that allows types (classes and methods) to be parameterized, providing compile-time type safety.

* **Why it matters:** Catches type errors at compile time rather than throwing `ClassCastException` at runtime, eliminating the need for manual casting.
* **Common confusion:** Believing generic type information exists at runtime. Because of Type Erasure, generic type parameters are stripped out during compilation.
* **Small example:** Using `List<String> list = new ArrayList<>();` instead of a raw `List` ensures only strings can be added.
