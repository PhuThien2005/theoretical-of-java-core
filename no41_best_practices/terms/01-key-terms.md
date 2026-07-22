# Best Practices in Java Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## descriptive naming

The practice of selecting intention-revealing, precise names for classes, methods, and variables to make the codebase self-documenting.

Why it matters: Eliminates the need for verbose comments explaining what a variable is for. Because local variable names are discarded by the compiler at build time, descriptive names incur no bytecode or runtime performance cost on the JVM.

Common confusion: Believing that short names are faster to compile or execute, or that Hungarian notation is needed in Java to identify types (which are already statically checked).

Small example:
```java
// Bad
int d; 
// Good
int elapsedDays;
```

## exception swallowing

The anti-pattern of catching an exception and continuing execution without logging the details, rethrowing it, or resolving the underlying problem.

Why it matters: It hides active bugs, prevents support teams from diagnosing failures, and disrupts the JVM's stack-unwinding mechanism, resulting in silent failures and database corruption.

Common confusion: Thinking that an empty catch block is safe because "the exception is handled" or that printing a stack trace to `System.out` is sufficient logging in production systems.

Small example:
```java
// Bad
try { read(); } catch (IOException e) {}
// Good
try { read(); } catch (IOException e) {
    throw new RuntimeException("Read failed", e);
}
```

## custom exceptions

Developer-defined exception classes that inherit from `Exception` (checked) or `RuntimeException` (unchecked) to represent domain-specific failure conditions.

Why it matters: Allows API consumers to catch specific business errors and implement recovery logic rather than catching generic, low-level system exceptions like `IOException`.

Common confusion: Making every custom exception checked, which leads to bloated `throws` signatures and method coupling, even for programmer errors that cannot be programmatically recovered from.

Small example:
```java
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) { super(message); }
}
```

## magic numbers

Unnamed literal numeric or string values used directly in expression calculations without explanation or context.

Why it matters: Magic numbers obscure the meaning of values and force developers to update the same literal value in multiple places. Named constants (`public static final`) centralize these values and are optimized via compile-time inlining.

Common confusion: Thinking that values that are unlikely to change (like days in a week) do not need to be constants.

Small example:
```java
// Bad
double total = price * 1.0825;
// Good
public static final double SALES_TAX_RATE = 0.0825;
double total = price * (1 + SALES_TAX_RATE);
```

## guard clauses

A conditional check placed at the beginning of a method that returns or throws immediately if preconditions are not met, preventing deeply nested blocks.

Why it matters: Keeps the "happy path" aligned at the left margin, reducing cognitive load when reading methods, and simplifies the control flow graph for JVM branch prediction optimizations.

Common confusion: Assuming a method must only have a single return statement at the very end, which results in deeply nested `if-else` structures.

Small example:
```java
// Bad
if (user != null) {
    if (user.isActive()) {
        process(user);
    }
}
// Good
if (user == null || !user.isActive()) return;
process(user);
```

## composition

A design pattern where a class achieves code reuse and polymorphic behavior by holding references to other objects (a "has-a" relationship) rather than extending a class (an "is-a" relationship).

Why it matters: It decouples classes by eliminating rigid compile-time inheritance hierarchies, avoiding parent-child API collisions and protecting subclass encapsulation.

Common confusion: Believing that composition is less powerful than inheritance because it requires explicit delegation methods to access the wrapped object's features.

Small example:
```java
// Using composition instead of extending Stack
public class CustomStack {
    private final List<String> list = new ArrayList<>();
    public void push(String item) { list.add(item); }
}
```

## immutability

The design characteristic where an object's state cannot be altered after it is created.

Why it matters: Immutable objects are inherently thread-safe, require no defensive copying when shared, and make excellent map keys and set elements because their hash codes never change.

Common confusion: Thinking that making a collection reference `final` makes the collection itself immutable (the collection's contents can still be modified unless wrapped in an unmodifiable view).

Small example:
```java
// Immutable Record
public record User(String username, List<String> roles) {
    public User {
        roles = List.copyOf(roles); // Defensive copy
    }
}
```

## raw type

A generic class or interface used without its type arguments (e.g., declaring `List` instead of `List<String>`).

Why it matters: Raw types disable generic type safety checks, causing compile-time warnings and risking runtime `ClassCastException`s when elements are extracted.

Common confusion: Using raw types under the assumption that they improve performance or that the compiler automatically infers correct types without generic declarations.

Small example:
```java
// Bad: raw type
List list = new ArrayList();
// Good: parameterized type
List<String> list = new ArrayList<>();
```

## try-with-resources

An exception-handling construct that automatically closes objects implementing `AutoCloseable` at the end of the statement block.

Why it matters: Ensures that system resources (such as file handles, database connections, and sockets) are closed in the correct order, even if exceptions are thrown, preventing resource leaks.

Common confusion: Thinking that resources will close automatically if initialized outside the `try` parentheses, or forgetting that the resource must implement `AutoCloseable`.

Small example:
```java
try (FileWriter writer = new FileWriter("log.txt")) {
    writer.write("Hello");
} catch (IOException e) {
    // handled
}
```

## testable code

Code structured in a modular, decoupled manner (e.g., using dependency injection) to allow verification of behavior in isolation using unit tests.

Why it matters: Protects logic from regression bugs, enables parallel development, and forces clean decoupling of classes and separation of concerns.

Common confusion: Thinking that code can only be tested if a framework like Spring is running, or that unit tests should verify private helper methods rather than public APIs.

Small example:
```java
// Testable because clock dependency can be mocked
public class PaymentService {
    private final Clock clock;
    public PaymentService(Clock clock) { this.clock = clock; }
}
```

## responsibility

The specific domain concern or role assigned to a class or method, governed by the Single Responsibility Principle (SRP).

Why it matters: Classes with a single responsibility have fewer reasons to change, make code easier to locate, and are much easier to reuse and unit test in isolation.

Common confusion: Believing that a class should handle all aspects of a concept (e.g., a `User` class parsing JSON, saving to a DB, and validating emails) to keep things "organized".

Small example:
```java
// Separate responsibilities
public class UserRepository { public void save(User u) {} }
public class UserValidator { public boolean isValid(User u) { return true; } }
```
