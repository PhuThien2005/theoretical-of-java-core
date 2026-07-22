# Build, Compile, Run Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## javac

The Java Compiler command-line tool that translates human-readable source code (`.java` files) into JVM-compatible bytecode (`.class` files).

- **Why it matters**: It enforces static type safety, checks syntax, and compiles class dependencies. Without it, Java source code cannot be run by the JVM.
- **Common confusion**: Confusing class name compiling with file compiling. `javac` compiles *files* (e.g., `javac Main.java`), not *classes* (e.g., `javac Main` is an error).
- **Small example**: `javac -d bin src/com/example/App.java` compiles the source file and places the output in `bin/com/example/App.class`.

## java command

The Java Application Launcher that starts a Java Virtual Machine (JVM), loads a compiled class, and runs its `public static void main(String[] args)` method.

- **Why it matters**: It is the runtime execution entry point. Since Java 11, it can also compile and run single-file source code in-memory (e.g., `java App.java`) without writing a class file to disk.
- **Common confusion**: Adding the `.class` extension (e.g., `java App.class` fails). The launcher expects a fully qualified class name using dot notation (e.g., `java com.example.App`).
- **Small example**: `java -cp bin com.example.App arg1 arg2` launches the JVM, loading the class `com.example.App` from the `bin` directory.

## JAR

Java Archive (`.jar`) is a file package format based on the ZIP compression format, used to bundle compiled classes, resources, and configuration metadata.

- **Why it matters**: It simplifies deployment and distribution by packaging hundreds of class files and directories into a single compressed artifact.
- **Common confusion**: Assuming JARs are secure. They are standard ZIP archives that can be easily renamed to `.zip`, extracted, or decompiled to reveal source code.
- **Small example**: `jar -tf app.jar` lists all files and directory paths inside `app.jar`.

## classpath

The lookup path parameter telling the compiler (`javac`) and the runtime JVM (`java`) where to find user-defined class definitions and packaged JAR files.

- **Why it matters**: It enables code reuse and modularity by pointing the JVM to third-party libraries. If the classpath is wrong, classloading fails with `NoClassDefFoundError` or `ClassNotFoundException`.
- **Common confusion**: Using the wrong OS separators. Windows uses `;` while Linux/macOS uses `:`. Also, specifying a classpath overrides the default current directory lookup (`.`), which must be manually re-added if needed.
- **Small example**: `java -cp bin:lib/gson.jar com.example.App` (on Unix) or `java -cp bin;lib\gson.jar com.example.App` (on Windows).

## manifest

A special metadata text file (`META-INF/MANIFEST.MF`) placed inside a JAR to define configurations like the entry-point class (`Main-Class`) and library dependencies (`Class-Path`).

- **Why it matters**: It enables double-clickable or self-contained running via `java -jar app.jar` by declaring the executable class.
- **Common confusion**: Forgetting the trailing newline. The manifest parser ignores the last line if there is no empty line at the end, leading to missing entry point errors.
- **Small example**:
  ```manifest
  Manifest-Version: 1.0
  Main-Class: com.example.App
  
  ```

## Maven

A declarative build automation and dependency management tool utilizing a `pom.xml` configuration to manage project lifecycles and libraries.

- **Why it matters**: It standardizes the project structure and manages transitive dependencies automatically from Maven Central.
- **Common confusion**: Misunderstanding phase execution. Running `mvn package` executes all prior phases (`validate`, `compile`, `test`) automatically.
- **Small example**: Running `mvn clean package` to delete old builds, compile new code, run tests, and output a JAR.

## Gradle

A programmatic, flexible build automation tool using Groovy or Kotlin DSL scripts (`build.gradle` or `build.gradle.kts`) to orchestrate build tasks.

- **Why it matters**: It provides faster incremental builds (via a daemon process) and supports highly customizable build logic compared to XML-based Maven.
- **Common confusion**: Forgetting to apply the Java plugin (`plugins { id 'java' }`), which prevents Gradle from recognizing compilation and test commands.
- **Small example**: Running `./gradlew build` using the Gradle wrapper.

## dependency

An external software library (usually a JAR file) that a Java project relies on to compile or run.

- **Why it matters**: Avoids rewriting common utilities (like JSON parsers or database drivers) by pulling verified packages into the project.
- **Common confusion**: "Jar Hell" version mismatches, where transitive dependencies conflict. These must be resolved via dependency exclusions.
- **Small example**: Declaring JUnit Jupiter inside a Gradle `dependencies` block with `testImplementation`.
