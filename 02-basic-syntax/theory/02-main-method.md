# The `main` Method

The `main` method is the entry point for a standard Java console program.

```java
public static void main(String[] args) {
    System.out.println("Hello Java");
}
```

When you run:

```bash
java HelloWorld
```

the JVM looks for a compatible `main` method and starts execution there.

## Breaking Down The Signature

```java
public static void main(String[] args)
```

### `public`

`public` means the method can be accessed from outside the class.

The JVM needs to be able to call the method when launching the program.

### `static`

`static` means the method belongs to the class, not to a specific object.

The JVM can call `main` without creating an instance of the class first.

### `void`

`void` means the method does not return a value.

The program can still print output, modify state, or call other methods, but `main` itself does not return a result to the caller.

### `main`

`main` is the method name recognized by the JVM as the program entry point.

### `String[] args`

`String[] args` receives command-line arguments.

Example:

```java
public class ArgsDemo {
    public static void main(String[] args) {
        System.out.println(args[0]);
    }
}
```

Run:

```bash
javac ArgsDemo.java
java ArgsDemo Java
```

Output:

```text
Java
```

## Main Method Flow

```mermaid
sequenceDiagram
    participant User
    participant Java as java command
    participant JVM
    participant Main as main method

    User->>Java: java HelloWorld
    Java->>JVM: start JVM
    JVM->>Main: call main(String[] args)
    Main->>Main: execute statements
```

## Valid Variation

This is also accepted:

```java
public static void main(String... args) {
    System.out.println("Hello");
}
```

`String... args` is varargs syntax and is compatible with `String[] args`.

## Common Mistakes

- Writing `Main` instead of `main`.
- Removing `static`.
- Returning `int` instead of `void`.
- Forgetting the `String[] args` parameter.
- Trying to run a class that has no valid `main` method.
