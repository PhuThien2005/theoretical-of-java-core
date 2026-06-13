# Package and Access Control - Part 1

## Learning Goal

This file covers a focused slice of **Package and Access Control**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `What is a package?` | A package groups related classes and gives them a namespace. |
| `Create package` | A package groups related classes and gives them a namespace. |
| `Import package` | A package groups related classes and gives them a namespace. |
| `import static` | Static means the member belongs to the class rather than to one particular object. |
| `Default package` | A package groups related classes and gives them a namespace. |
| `Package naming convention` | A package groups related classes and gives them a namespace. |
| `Access between packages` | A package groups related classes and gives them a namespace. |
| `Classpath` | Classpath tells the JVM and compiler where to find classes and JARs. |
| `Basic module path` | Module path is the module-system-aware alternative to classpath for named modules. |

## Detailed Notes

### What is a package?

A package groups related classes and gives them a namespace.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `What is a package?` in one sentence.
- Recognize `What is a package?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `What is a package?`.

Tiny example or mental model:

- When reading code, ask: what does `What is a package?` change, allow, reject, or clarify?

#### Enriched Details & Code Example
A package is a grouping of related types (classes, interfaces, enums, annotations) providing access protection and namespace management. It solves naming conflicts by prefixing class names with package names.

```java
package com.example.geometry;

public class Point {
    private int x, y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
```

### Create package

A package groups related classes and gives them a namespace.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Create package` in one sentence.
- Recognize `Create package` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Create package`.

Tiny example or mental model:

- When reading code, ask: what does `Create package` change, allow, reject, or clarify?

#### Enriched Details & Code Example
A package is declared using the `package` statement. It must be the very first non-whitespace, non-comment statement in the Java source file. The physical directory path of the source and class files must mirror the package namespace structure.

```java
// Must be the first statement in the file (excluding comments/whitespace)
package com.example.util;

public class MathUtils {
    public static int add(int a, int b) {
        return a + b;
    }
}
```
**Common Mistake:** Placing the `package` declaration after `import` statements or class declarations. This causes a compile-time error: `class, interface, enum, or record expected`.

### Import package

A package groups related classes and gives them a namespace.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Import package` in one sentence.
- Recognize `Import package` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Import package`.

Tiny example or mental model:

- When reading code, ask: what does `Import package` change, allow, reject, or clarify?

#### Enriched Details & Code Example
To use a class from another package without its fully qualified name, use the `import` statement.
- **Specific Import:** Imports a single class.
- **Wildcard Import:** Imports all classes in a package using `*`. It is NOT recursive (does not import sub-packages).
- **Fully Qualified Class Name (FQCN):** Directly referencing a class by its absolute package path (e.g. `java.util.List`).

```java
package com.example.app;

// Specific Import
import java.util.ArrayList;
// Wildcard Import (imports java.util.List, java.util.Map, etc. but NOT java.util.concurrent.*)
import java.util.*; 

public class ImportDemo {
    public static void main(String[] args) {
        // Specific import used
        ArrayList<String> list = new ArrayList<>();
        
        // Fully Qualified Class Name (FQCN) used to bypass import
        java.time.LocalDate today = java.time.LocalDate.now();
    }
}
```
**Common Mistake:** Believing wildcard imports like `import java.util.*;` import sub-packages (like `java.util.concurrent.*`). They do not; sub-packages must be imported separately.

### import static

Static means the member belongs to the class rather than to one particular object.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `import static` in one sentence.
- Recognize `import static` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `import static`.

Tiny example or mental model:

- `ClassName.member` accesses a class-level member.

#### Enriched Details & Code Example
The `import static` statement allows access to static members (fields and methods) of a class without prefixing them with the class name.

```java
package com.example.math;

// Import a specific static member
import static java.lang.Math.PI;
// Import all static members of java.lang.Math
import static java.lang.Math.*;

public class StaticImportDemo {
    public double getArea(double radius) {
        // PI and pow are accessed directly
        return PI * pow(radius, 2);
    }
}
```
**Common Mistake:** Writing `static import` instead of `import static`. This is a compile error: `syntax error on token "static", import expected`.

### Default package

A package groups related classes and gives them a namespace.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Default package` in one sentence.
- Recognize `Default package` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Default package`.

Tiny example or mental model:

- When reading code, ask: what does `Default package` change, allow, reject, or clarify?

#### Enriched Details & Code Example
If a source file does not have a `package` statement, it belongs to the unnamed **default package**.

```java
// No package statement means this class is in the default package
public class Helper {
    public void printMessage() {
        System.out.println("Helper in default package");
    }
}
```
**Common Mistake:** Attempting to import a class from the default package into a named package. Classes in named packages cannot import classes in the default package. Doing so results in a compilation error.

### Package naming convention

A package groups related classes and gives them a namespace.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Package naming convention` in one sentence.
- Recognize `Package naming convention` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Package naming convention`.

Tiny example or mental model:

- When reading code, ask: what does `Package naming convention` change, allow, reject, or clarify?

#### Enriched Details & Code Example
Package names are always written in lowercase to avoid naming conflicts with classes. They use reversed internet domain names as a prefix to ensure uniqueness across different organizations.

```java
package com.mycompany.projectname.modulename;

public class Controller {
    // lowercase package names are readable and avoid class name collisions
}
```

### Access between packages

A package groups related classes and gives them a namespace.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Access between packages` in one sentence.
- Recognize `Access between packages` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Access between packages`.

Tiny example or mental model:

- When reading code, ask: what does `Access between packages` change, allow, reject, or clarify?

#### Enriched Details & Code Example
Java has four access levels:
- `public`: Accessible anywhere.
- `protected`: Accessible within the same package, and by subclasses in other packages.
- package-private (default): Accessible only within the same package.
- `private`: Accessible only within the same class.

```java
// File: pack1/Parent.java
package pack1;

public class Parent {
    public int publicVar = 1;
    protected int protectedVar = 2;
    int defaultVar = 3; // package-private
    private int privateVar = 4;
}

// File: pack2/Child.java
package pack2;

import pack1.Parent;

public class Child extends Parent {
    public void testAccess() {
        System.out.println(publicVar);      // OK: public
        System.out.println(protectedVar);   // OK: protected accessed via inheritance
        // System.out.println(defaultVar);  // COMPILE ERROR: default is package-private
        // System.out.println(privateVar);  // COMPILE ERROR: private is restricted
    }
}
```
**Common Mistake:** Subclasses in another package can access protected members *only* on instances of the subclass or its sub-types. They cannot access them on an instance of the superclass.

### Classpath

Classpath tells the JVM and compiler where to find classes and JARs.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Classpath` in one sentence.
- Recognize `Classpath` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Classpath`.

Tiny example or mental model:

- When reading code, ask: what does `Classpath` change, allow, reject, or clarify?

#### Enriched Details & Code Example
The classpath tells the compiler (`javac`) and the JVM (`java`) where to find user-defined classes and packages. It can be specified via the `CLASSPATH` environment variable or the `-cp` / `-classpath` command line flags.

```bash
# Compilation using classpath
javac -cp "lib/*:src" src/com/example/Main.java

# Running the application using classpath
java -cp "lib/*:bin" com.example.Main
```

### Basic module path

Module path is the module-system-aware alternative to classpath for named modules.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Basic module path` in one sentence.
- Recognize `Basic module path` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Basic module path`.

Tiny example or mental model:

- When reading code, ask: what does `Basic module path` change, allow, reject, or clarify?

#### Enriched Details & Code Example
Introduced in Java 9, the module path (`--module-path` or `-p`) is the modular alternative to the classpath. It specifies the location of application and library modules. Unlike the classpath, it enforces strong encapsulation and checks module dependencies at startup.

```bash
# Compiling a module
javac -d mods/com.example.app --module-source-path src src/com.example.app/module-info.java src/com.example.app/com/example/app/Main.java

# Running a modular application using module-path
java --module-path mods --module com.example.app/com.example.app.Main
```

## Case Study: Naming Collisions

When two different packages define classes with the same name, importing both packages will lead to compilation errors if the class name is used without qualification.

Suppose we want to use the class `Date` from both `java.util` and `java.sql`.

```java
import java.util.Date;
import java.sql.Date; // COMPILE ERROR: Date is already defined in a single-type import

public class CollisionDemo {
    public static void main(String[] args) {
        Date date = new Date(); // Ambiguity if we try to import both
    }
}
```

### Resolution 1: Use Fully Qualified Class Names (FQCN)
Instead of importing both, use their full package prefixes inside the code:
```java
public class CollisionDemo {
    public static void main(String[] args) {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
    }
}
```

### Resolution 2: Import One, Qualify the Other
Import the most frequently used class, and use the FQCN for the other:
```java
import java.util.Date; // Specific import

public class CollisionDemo {
    public static void main(String[] args) {
        Date utilDate = new Date(); // java.util.Date
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis()); // java.sql.Date
    }
}
```

---

## Common Mistakes Summary

1. **Sub-package Import Misconception**: Writing `import java.util.*;` does not import `java.util.concurrent.ConcurrentHashMap`. Imports are flat, not recursive.
2. **Incorrect Directory Structure**: Placing `package com.example;` inside a directory like `src/com/` instead of `src/com/example/`. The folder hierarchy must match the package declaration exactly.
3. **Importing from Default Package**: Attempting to import or use a class from the default (unnamed) package inside a named package class. Java does not allow this.
4. **Incorrect Order of import static**: Writing `static import` instead of `import static`.
5. **Accessing Protected Members of Superclass Instance in Different Package**: A subclass in package `B` inheriting from a class in package `A` can access the protected field of its superclass *only* via references of its own subclass type. It cannot access it using a parent/superclass reference.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
