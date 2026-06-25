# Java Module System Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## module

A self-describing collection of code (packages) and data (resources), with a module descriptor (`module-info.class`) specifying dependencies and access control.

- **Why it matters**: It shifts Java's compilation and runtime unit from a flat set of classes to a structured dependency graph, enabling compile-time validation of dependencies and strong encapsulation.
- **Common confusion**: Learners often confuse a module with a packages or Maven/Gradle projects. A package is a namespace for classes; a module aggregates packages and controls access to them. A Maven project is a build tool wrapper, which can contain one or more Java modules.
- **Small example**: The JDK itself is split into modules like `java.base`, `java.desktop`, and `java.sql`.

## module-info.java

The module descriptor source file placed at the root of a module's source tree that defines the module's name, requirements, exports, and reflective access.

- **Why it matters**: The compiler uses this file to enforce access rules during compilation, and the JVM uses it to build the module graph and enforce access controls at runtime.
- **Common confusion**: Learners often place it inside a package directory or forget that it must use the keyword `module` followed by the module name, which is usually in reverse domain name format (like a package).
- **Small example**:
  ```java
  module com.myapp {
      requires java.sql;
      exports com.myapp.service;
  }
  ```

## requires

A module directive used in `module-info.java` that specifies a dependency of the current module on another module.

- **Why it matters**: It establishes readability, allowing the current module to access the exported packages of the specified module.
- **Common confusion**: Confusing `requires` (which references a module name) with `import` statements in Java source files (which reference package/class names).
- **Small example**: `requires java.net.http;` tells the JVM this module needs the HTTP client module.

## exports

A module directive used in `module-info.java` that makes all public classes and interfaces in a package accessible to other modules.

- **Why it matters**: It is the foundation of encapsulation. Packages not explicitly exported are completely hidden and inaccessible to other modules.
- **Common confusion**: Thinking `exports` makes internal/private members of a class accessible. It only exports the public API; private fields remain inaccessible.
- **Small example**: `exports com.myapp.api;` exposes the public classes of the `api` package.

## opens

A module directive used in `module-info.java` that allows other modules to use reflection (including deep reflection on private members) on the packages, while blocking compile-time access.

- **Why it matters**: Essential for frameworks (like Spring, Hibernate, or JUnit) that require deep reflection to inject dependencies, map databases, or run tests on private elements.
- **Common confusion**: Learners often use `exports` when they actually need `opens` for reflection. If you only use `exports`, frameworks will throw `InaccessibleObjectException` when trying to access private fields.
- **Small example**: `opens com.myapp.domain to spring.core;` allows Spring to reflectively access domain models.

## unnamed module

An implicit module created by the JVM to contain all classes loaded from the classpath.

- **Why it matters**: Provides backwards compatibility for legacy Java 8 applications. It automatically exports all its packages and can read every module on the module path.
- **Common confusion**: Thinking named modules can declare `requires` on the unnamed module. Named modules cannot read the unnamed module because it has no name, meaning legacy classpath code cannot easily be depended upon by modular code without using automatic modules.
- **Small example**: Running `java -cp app.jar Main` places all classes from `app.jar` into the unnamed module.

## automatic module

A named module created automatically by the JVM when a standard JAR (which lacks a `module-info.class`) is placed on the module path.

- **Why it matters**: Acts as a migration bridge. It exports all its packages and can read both named modules and the unnamed module, allowing modular code to depend on legacy library JARs.
- **Common confusion**: Believing automatic modules require a `module-info.java` file. They do not; their name is automatically derived from the JAR file name or the `Automatic-Module-Name` manifest header.
- **Small example**: Placing `guava-31.0.jar` on the module path creates an automatic module named `guava`, which can be required via `requires guava;`.

## module path

The search path used by the Java compiler and JVM to find modular JARs and folders.

- **Why it matters**: Unlike the classpath, the module path enforces strict encapsulation, constructs a deterministic module graph, and checks for cyclic dependencies and split packages at startup.
- **Common confusion**: Mixing the module path (`--module-path` or `-p`) with the classpath (`--class-path` or `-cp`). Code on the module path is strictly validated; code on the classpath is placed in the unnamed module.
- **Small example**: `java --module-path mods -m my.module/my.package.Main` runs a modular app.
