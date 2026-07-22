# Setup Basics

Setting up your environment correctly and understanding how Java compiles and executes code is the foundation of becoming a Java developer. While IDEs hide the details, knowing the underlying mechanics is essential for debugging runtime issues and understanding Java's architecture.

---

## JDK vs JRE

To run and build Java applications, you need to understand the relationship between the **Java Virtual Machine (JVM)**, the **Java Runtime Environment (JRE)**, and the **Java Development Kit (JDK)**.

### The Mechanism

The **Java Language Specification (JLS)** defines the syntax, grammar, and compile-time rules of the Java language (what constitutes a valid Java program). The **Java Virtual Machine Specification (JVMS)** defines the structure of class files and the instruction set executed by the JVM at runtime. 

- **JRE (Java Runtime Environment):** It is the execution (runtime) environment. It contains the JVM and the core libraries (like `java.lang`, `java.util`) needed to run Java bytecode. The JRE is unable to compile Java source code; it only executes compiled `.class` files.
- **JDK (Java Development Kit):** It is the development environment. It is a superset of the JRE, meaning it contains the JRE plus development tools like the compiler (`javac`), packager (`jar`), and debugger. The JDK compiles source code in compliance with the JLS and runs it in compliance with the JVMS and the **Java Memory Model (JMM)**, which governs thread synchronization and memory visibility.

```text
+-------------------------------------------------------+
| JDK (Development Kit: javac, jar, jdb)                |
|  +-------------------------------------------------+  |
|  | JRE (Runtime Environment: libraries, rt.jar)    |  |
|  |  +-------------------------------------------+  |  |
|  |  | JVM (Virtual Machine: execution engine)   |  |  |
|  |  +-------------------------------------------+  |  |
|  +-------------------------------------------------+  |
+-------------------------------------------------------+
```

### The Recipe Book Analogy

* **The JLS (Specification):** This is the official recipe book. It dictates the exact ingredients and steps required to describe a dish. It doesn't cook the meal; it just defines what a valid recipe looks like.
* **The JVM (Execution):** This is the chef's kitchen and the chef. The chef reads the compiled recipe instructions (bytecode) and cooks the meal (executes the code on the hardware).
* **The JRE (Runtime Package):** This is the entire restaurant dining room and kitchen. It provides the chef (JVM) and the basic pantry ingredients/utensils (core libraries) to serve the meal to customers.
* **The JDK (Development Package):** This is the food science laboratory and test kitchen. It contains the restaurant (JRE), plus tools to write new recipes, test new ingredients, and print the recipe books (compilation and development tools).

### Cause-Effect Chain

```text
Developer writes HelloWorld.java 
  ↓ (Needs JDK)
JDK's 'javac' compiles source code according to JLS rules 
  ↓ (Produces bytecode)
Compiler outputs HelloWorld.class 
  ↓ (Needs JRE/JVM)
JRE's 'java' command loads JVM and runtime libraries
  ↓ (Execution)
JVM executes bytecode instructions on target hardware platform
```

---

## Why Filename Must Match Public Class Name

In Java, if you have a file containing a class declared as `public`, the filename must match the name of that public class exactly (including case-sensitivity), appended with the `.java` extension.

### The Mechanism: Compiler Class Loading

This restriction is not arbitrary; it is designed to optimize compilation speed via the compiler's class loading and resolution mechanism.

1. **Compilation Dependecy Resolution:** When compiling a class `A`, it might reference another class `B`. If `B.class` does not exist yet, the compiler must locate the source file `B.java` to compile it on the fly.
2. **Search Performance:** If there were no filename matching rule, the compiler would have to open and parse every single `.java` file in the source directory and classpath to check if it contains the declaration `public class B`. For a project with 10,000 files, this would require `O(N)` file reads, making compilation incredibly slow.
3. **Instant Lookup (`O(1)`):** By enforcing that `public class B` must reside in `B.java`, the compiler can instantly locate the file by doing a direct file lookup on the file system: `sourcepath/B.java`. This reduces the search complexity to `O(1)`.
4. **One Public Class Constraint:** Because of this 1-to-1 mapping requirement between the file name and the public class name, a single Java source file can contain at most one `public` class (though it can contain multiple non-public/package-private classes).

### Compilation Failure Example

If you define a class named `MyCoolProgram` as public, but save it in a file named `Runner.java`:

```java
// Saved in file: Runner.java
public class MyCoolProgram {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Attempting to compile this file yields the following error:

```text
$ javac Runner.java
Runner.java:2: error: class MyCoolProgram is public, should be declared in a file named MyCoolProgram.java
public class MyCoolProgram {
       ^
1 error
```

### Cause-Effect Chain

```text
Class B references Class A
  ↓
Compiler looks for Class A definition
  ↓
Enforced naming rule allows compiler to search directly for 'A.java' in O(1) time
  ↓
No need to scan and parse other source files → Fast compile times
```

---

## Terminal vs IDE Compilation and Execution

There are two primary ways to compile and run Java programs: using the raw terminal commands or using an Integrated Development Environment (IDE) like IntelliJ IDEA, VS Code, or Eclipse.

### The Mechanism

* **Terminal Compilation & Execution:**
  You interact directly with the JDK binaries. You run `javac` to invoke the compiler, which translates human-readable source code into platform-independent bytecode (in `.class` files). You then run `java` to invoke the JVM, which loads the class file, verifies the bytecode, and executes it.
* **IDE Automated Management:**
  An IDE wraps these commands in a graphical interface. Instead of manual compilation, the IDE monitors file changes and performs **incremental compilation** in the background, compiling only the files that changed. It automatically manages the **classpath** (where Java looks for dependencies) and integrates real-time static analysis (linting) to show errors before you compile.

```mermaid
flowchart TD
    subgraph Terminal Workflow (Manual)
        A1[Write HelloWorld.java] --> A2[Run: javac HelloWorld.java]
        A2 --> A3[Generate HelloWorld.class]
        A3 --> A4[Run: java HelloWorld]
        A4 --> A5[JVM executes program]
    end

    subgraph IDE Workflow (Automated)
        B1[Write Code in IDE] --> B2[Background Incremental Compiler]
        B2 --> B3[IDE flags syntax errors instantly]
        B3 --> B4[Click Run Button]
        B4 --> B5[IDE configures Classpath & launches JVM]
    end
```

### Contrast Summary

| Feature | Terminal Compilation (`javac`/`java`) | IDE Compilation (IntelliJ, VS Code) |
|---|---|---|
| **Compilation** | Manual invocation of `javac file.java`. | Automatic background incremental compilation. |
| **Classpath Management** | Must be specified manually via `-cp` or `-classpath`. | Managed automatically via project build files (Maven/Gradle). |
| **Error Feedback** | Shown only after running the compilation command. | Highlighted instantly in the code editor (static analysis). |
| **Use Case** | Learning fundamentals, scripting, CI/CD pipelines. | Professional development, refactoring, debugging complex systems. |

### Code Example: Manual Compilation and Execution

Let's look at the standard Hello World structure and its commands:

```java
// Saved in file: HelloWorld.java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!"); // Output: Hello, World!
    }
}
```

Commands to run in the terminal:

```bash
# 1. Check compiler version
$ javac -version
javac 21.0.2

# 2. Compile source file into bytecode (.class)
$ javac HelloWorld.java

# 3. Verify .class file was generated
$ ls
HelloWorld.class  HelloWorld.java

# 4. Execute the bytecode on the JVM (do NOT include .class extension)
$ java HelloWorld
Hello, World!
```

### Cause-Effect Chain

```text
Run 'javac HelloWorld.java'
  ↓
Compiler parses code → checks JLS compliance → generates JVM bytecode
  ↓
Bytecode written to 'HelloWorld.class'
  ↓
Run 'java HelloWorld'
  ↓
JVM class loader fetches 'HelloWorld.class' → Bytecode Verifier checks security → JIT/Interpreter executes main()
```

---

## Reference Links

- [Oracle Java Tutorials: Getting Started](https://docs.oracle.com/javase/tutorial/getStarted/cupojava/index.html)
- [Java Language Specification (JLS) - Class Declarations](https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.1)
- [JVM Specification - Run-Time Data Areas](https://docs.oracle.com/javase/specs/jvms/se21/html/jvms-2.html#jvms-2.5)
