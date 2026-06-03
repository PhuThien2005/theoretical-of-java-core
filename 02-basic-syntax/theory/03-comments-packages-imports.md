# Comments, Packages, And Imports

Comments, packages, and imports do not usually change business logic directly, but they make code understandable and organized.

## Comments

Java supports single-line and multi-line comments.

Single-line:

```java
// This prints a greeting.
System.out.println("Hello");
```

Multi-line:

```java
/*
 This is a longer explanation.
 It can span multiple lines.
*/
```

Documentation comment:

```java
/**
 * Calculates the total price.
 */
public double calculateTotal() {
    return 0;
}
```

Documentation comments can be used by tools such as `javadoc`.

## Good Comments

Good comments explain why code exists or clarify non-obvious decisions.

Useful:

```java
// Use BigDecimal because money calculations must avoid floating-point rounding issues.
```

Not useful:

```java
// Add 1 to count
count = count + 1;
```

The second comment only repeats the code.

## Packages

A package groups related classes and prevents name conflicts.

Example:

```java
package com.example.learning;
```

Package names are usually lowercase and often use a reversed domain name style:

```text
com.company.project.module
```

## Imports

An import lets you use a class from another package without writing its full name every time.

Without import:

```java
java.util.Scanner scanner = new java.util.Scanner(System.in);
```

With import:

```java
import java.util.Scanner;

Scanner scanner = new Scanner(System.in);
```

## Wildcard Imports

Java allows wildcard imports:

```java
import java.util.*;
```

This imports classes from the `java.util` package, but not from subpackages. Beginners should prefer explicit imports because they are clearer.

## Import Does Not Copy Code

An import statement does not paste code into your file. It only tells the compiler where to find a type by its simple name.

## Common Mistakes

- Writing imports below the class declaration.
- Using package names with uppercase letters.
- Thinking `import java.util.*` imports subpackages.
- Adding comments that repeat obvious code.
