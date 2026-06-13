# Program Anatomy

A Java program is usually organized around classes. Even a very small program normally has at least one class.

## Minimal Example

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello Java");
    }
}
```

This program has:

- A class declaration: `public class HelloWorld`.
- A method declaration: `public static void main(String[] args)`.
- A statement: `System.out.println("Hello Java");`.
- Blocks marked by `{}`.

## File Name And Public Class Name

If a top-level class is `public`, the file name must match the class name.

Correct:

```text
HelloWorld.java
public class HelloWorld
```

Incorrect:

```text
Main.java
public class HelloWorld
```

The incorrect version causes a compile-time error because the public class name and file name do not match.

## Statements

A statement is an instruction that the program executes.

Example:

```java
System.out.println("Hello Java");
```

Most Java statements end with a semicolon.

Common beginner error:

```java
System.out.println("Hello Java")
```

This fails because the semicolon is missing.

## Blocks

A block is a group of code between `{` and `}`.

```java
if (true) {
    System.out.println("Inside block");
}
```

Blocks matter because they define structure and often affect scope.

## Case Sensitivity

Java is case-sensitive.

These are different names:

```java
Student
student
STUDENT
```

This matters for class names, variable names, method names, and keywords.

## Whitespace

Java usually ignores extra spaces and line breaks between tokens, but formatting still matters for readability.

These compile similarly:

```java
int x = 10;
```

```java
int
x
=
10
;
```

The second style is legal in many cases but terrible to read. Good formatting makes code maintainable.

## Common Mistakes

- Forgetting semicolons.
- Using the wrong capitalization.
- Putting code outside a class.
- Mismatching `{` and `}`.
- Naming the file differently from the public class.

### Common Mistake: Missing Semicolon

```java
// Compile error — semicolon missing
public class Bad {
    public static void main(String[] args) {
        System.out.println("Hello")   // ← error: ';' expected
    }
}
```

```java
// Correct
public class Good {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
```

### Common Mistake: File Name Mismatch

```java
// File is named: Main.java
// Compile error: class HelloWorld is public, should be declared in a file named HelloWorld.java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hi");
    }
}
```

Fix: rename the file to `HelloWorld.java`.

## `print` vs `println`

`System.out.print` prints without a trailing newline.
`System.out.println` prints and then moves to the next line.

```java
// print — no newline after each call
System.out.print("Hello");
System.out.print(" World");
// Output: Hello World    (on one line)

// println — newline appended after each call
System.out.println("A");
System.out.println("B");
// Output:
// A
// B
```

Mixing them is valid:

```java
System.out.print("Score: ");
System.out.println(42);
// Output: Score: 42
```

## Case Study: A Minimal But Complete Program

```java
// File: Greeter.java
package com.example;

/**
 * A minimal greeting program demonstrating all basic anatomy elements.
 */
public class Greeter {          // class name matches file name

    // Entry point
    public static void main(String[] args) {
        String name = "Java";   // variable with a meaningful name
        // Print greeting — no newline first, then println to finish the line
        System.out.print("Hello, ");
        System.out.println(name);
    }
}
// Output: Hello, Java
```

This program demonstrates: package declaration, doc comment, public class matching file name,
`main` method, meaningful variable name, mixed `print`/`println`, and block structure.
