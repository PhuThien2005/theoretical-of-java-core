# Exception Handling - Part 2

| Concept | What to know |
| --- | --- |
| `finally` | A block that always executes when the try block exits, ensuring cleanup occurs. |
| `throw` | Used to explicitly throw an exception from a method or block. |
| `throws` | Declares in the method signature which exceptions the method can propagate. |
| `try-with-resources` | Automatically closes resources implementing AutoCloseable at block exit. |
| `Custom exception` | A user-defined exception class extending Exception or RuntimeException. |
| `Exception propagation` | The process where unhandled exceptions are passed up the call stack to callers. |
| `Common exceptions:` | Standard built-in exceptions like NPE, ClassCastException, etc. |
| `NullPointerException` | Exception thrown when dereferencing a null object reference. |

## Detailed Notes

### finally

A `finally` block always executes when the `try` block exits. This ensures that the `finally` block is executed even if an unexpected exception occurs, or if a control transfer statement like `return`, `break`, or `continue` is reached.

Practical check:

- Define `finally` in one sentence.
- Recognize `finally` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `finally`.

Tiny example or mental model:

- Use `finally` to release system resources (like close files, database connections).

#### Runnable Code Example: try-catch-finally Execution Order
```java
public class FinallyOrderDemo {
    public static void main(String[] args) {
        try {
            System.out.println("1. Inside try");
            int x = 10 / 0;
            System.out.println("2. This won't print");
        } catch (ArithmeticException e) {
            System.out.println("3. Inside catch");
        } finally {
            System.out.println("4. Inside finally");
        }
        System.out.println("5. Outside try-catch-finally");
    }
}
```

#### Case Study: "finally block runs even when return is in try"
A very common interview gotcha is when a `return` statement, `break`, or `continue` is executed within the `try` or `catch` block. The JVM guarantees that the `finally` block will execute *before* control transfers back to the caller.
```java
public class FinallyReturnDemo {
    public static int getValue() {
        try {
            System.out.println("Inside try");
            return 42; // Returns 42, but finally executes first!
        } finally {
            System.out.println("Inside finally");
        }
    }

    public static int getTrickyValue() {
        try {
            return 10;
        } finally {
            return 20; // WARNING: This overrides the return value from the try block!
        }
    }

    public static void main(String[] args) {
        System.out.println("Returned value: " + getValue()); // Prints 42
        System.out.println("Tricky returned value: " + getTrickyValue()); // Prints 20!
    }
}
```
*Note: Returning a value or throwing an exception from a `finally` block overrides any previous return or exception in the `try`/`catch` block. This is considered an anti-pattern because it swallows exceptions and return values.*

#### When does `finally` NOT run?
1. If the JVM exits during try/catch execution via `System.exit(0)`.
2. If the thread running the code is killed or interrupted.
3. In case of a system crash, power failure, or OS shutdown.

### throw

The `throw` keyword is used to explicitly throw an exception from any method or block of code. You can throw either checked or unchecked exceptions.

Practical check:

- Define `throw` in one sentence.
- Recognize `throw` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `throw`.

Tiny example or mental model:

- When reading code, ask: what does `throw` change, allow, reject, or clarify?

#### Runnable Code Example: Throwing and Re-throwing Exceptions
```java
public class RethrowDemo {
    public static void process() throws Exception {
        try {
            // Throwing an exception
            throw new IllegalArgumentException("Invalid input data");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught locally, now re-throwing...");
            throw e; // Re-throwing the caught exception
        }
    }

    public static void main(String[] args) {
        try {
            process();
        } catch (Exception e) {
            System.out.println("Caught in main: " + e.getMessage());
        }
    }
}
```

### throws

The `throws` keyword is used in method signatures to declare that this method might throw one or more specified exceptions. Callers of this method must either handle these exceptions or declare them in their own signature.

Practical check:

- Define `throws` in one sentence.
- Recognize `throws` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `throws`.

Tiny example or mental model:

- When reading code, ask: what does `throws` change, allow, reject, or clarify?

#### Runnable Code Example: Declaring and Propagating Exceptions
```java
import java.io.IOException;

public class ThrowsDemo {
    // Declarative throws clause for checked exception
    public static void riskyMethod() throws IOException {
        throw new IOException("Connection failed");
    }

    public static void caller() throws IOException {
        riskyMethod(); // Propagates the exception upward
    }

    public static void main(String[] args) {
        try {
            caller();
        } catch (IOException e) {
            System.out.println("Caught propagated exception: " + e.getMessage());
        }
    }
}
```

### try-with-resources

Java 7 introduced try-with-resources to ensure that each resource is closed at the end of the statement. Any object that implements `java.lang.AutoCloseable` can be used as a resource.

Practical check:

- Define `try-with-resources` in one sentence.
- Recognize `try-with-resources` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `try-with-resources`.

Tiny example or mental model:

- `try (BufferedReader br = new BufferedReader(...)) { ... }` handles closing resources.

#### Runnable Code Example: Try-with-Resources and Close Order
```java
public class TryWithResourcesDemo {
    static class Resource implements AutoCloseable {
        private final String name;
        Resource(String name) {
            this.name = name;
            System.out.println("Opened " + name);
        }
        @Override
        public void close() {
            System.out.println("Closed " + name);
        }
    }

    public static void main(String[] args) {
        // Resources are closed in reverse order of their declaration: R2 then R1
        try (Resource r1 = new Resource("R1");
             Resource r2 = new Resource("R2")) {
            System.out.println("Inside try-with-resources block");
        }
    }
}
```

### Custom exception

Custom exceptions are user-defined exceptions created by extending either `Exception` (for checked exceptions) or `RuntimeException` (for unchecked exceptions).

Practical check:

- Define `Custom exception` in one sentence.
- Recognize `Custom exception` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Custom exception`.

Tiny example or mental model:

- Extend `RuntimeException` for unchecked custom business rules.

#### Runnable Code Example: Creating Custom Exceptions and Exception Chaining
```java
// Checked Custom Exception
class DatabaseConnectionException extends Exception {
    public DatabaseConnectionException(String message) {
        super(message);
    }
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause); // Exception chaining constructor
    }
}

// Unchecked Custom Exception
class InvalidUserRoleException extends RuntimeException {
    public InvalidUserRoleException(String message) {
        super(message);
    }
}

public class CustomExceptionDemo {
    public static void connectDatabase() throws DatabaseConnectionException {
        try {
            // Simulate low-level exception
            throw new java.sql.SQLException("Connection timeout");
        } catch (java.sql.SQLException e) {
            // Exception chaining: wrap the low-level exception in a high-level one
            throw new DatabaseConnectionException("Failed to initialize database", e);
        }
    }

    public static void main(String[] args) {
        try {
            connectDatabase();
        } catch (DatabaseConnectionException e) {
            System.out.println("Caught: " + e.getMessage());
            System.out.println("Underlying cause: " + e.getCause());
        }
    }
}
```

### Exception propagation

If an exception is not caught in the current method, it is popped off the call stack and propagated to the caller method. This continues until the exception is caught, or it reaches the `main` method (terminating the thread).

Practical check:

- Define `Exception propagation` in one sentence.
- Recognize `Exception propagation` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Exception propagation`.

Tiny example or mental model:

- Call stack search order: level3 -> level2 -> level1 -> main.

#### Runnable Code Example: Call Stack Exception Propagation
```java
public class ExceptionPropagationDemo {
    public static void level3() {
        int x = 10 / 0; // Throws unchecked ArithmeticException
    }

    public static void level2() {
        level3(); // Propagation
    }

    public static void level1() {
        try {
            level2();
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException at level 1: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        level1();
    }
}
```

### Common exceptions:

The Java platform provides numerous pre-defined exception classes that represent standard runtime and checked conditions.

Practical check:

- Define `Common exceptions:` in one sentence.
- Recognize `Common exceptions:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Common exceptions:`.

Tiny example or mental model:

- Unchecked: NullPointerException, ArithmeticException. Checked: IOException, FileNotFoundException.

### NullPointerException

Thrown when an application attempts to use `null` in a case where an object reference is required.

Practical check:

- Define `NullPointerException` in one sentence.
- Recognize `NullPointerException` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `NullPointerException`.

Tiny example or mental model:

- Triggered by calling a method on a variable that points to `null`.

#### Runnable Code Example: Triggering and Preventing NullPointerException
```java
public class NPEDemo {
    public static void main(String[] args) {
        String name = null;
        try {
            // Triggering NPE
            int len = name.length();
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException!");
        }

        // Prevention (Null check)
        if (name != null) {
            int len = name.length();
        } else {
            System.out.println("Name is null, null-check prevented NPE");
        }
    }
}
```

### Case Study: Exception Swallowing Anti-Pattern
Exception swallowing (or masking) occurs when a block of code catches an exception but fails to handle it, log it, or propagate it, making debugging extremely difficult. It also occurs when an exception is thrown or a return is executed in a `finally` block, which completely masks (swallows) any exception thrown in the corresponding `try` block.

#### Swallowing via Empty Catch Block
```java
try {
    int data = Integer.parseInt("not_a_number");
} catch (NumberFormatException e) {
    // EMPTY: Exception is swallowed and lost forever!
}
```

#### Swallowing via Finally Block Exception / Return
```java
public class SwallowingDemo {
    public static String readData() {
        try {
            throw new RuntimeException("Error in try block");
        } finally {
            // This return statement silently swallows the RuntimeException!
            return "Success"; 
        }
    }

    public static void main(String[] args) {
        // Will print "Success" and the exception will be completely lost!
        System.out.println(readData()); 
    }
}
```
*Best Practice:* Always log exceptions or wrap them in custom exceptions when catching, and never return or throw exceptions from a `finally` block unless you explicitly intend to discard the original exception.

## Common Mistakes

### 1. Modifying Return Value in Finally Block (Reference vs. Primitive)
If you return a primitive type from `try`, modifying it in `finally` without a return statement does not change the returned value. However, modifying a mutable object's state *will* affect the caller because the reference points to the same object in heap memory.
```java
public class FinallyModifyDemo {
    public static int getPrimitive() {
        int x = 10;
        try {
            return x; // Returns 10
        } finally {
            x = 20; // Modifies local copy, return value is already cached as 10
        }
    }

    static class Box { int val = 10; }
    public static Box getObject() {
        Box b = new Box();
        try {
            return b; // Returns reference to b
        } finally {
            b.val = 20; // Modifies state of object b, caller sees val = 20!
        }
    }

    public static void main(String[] args) {
        System.out.println("Primitive: " + getPrimitive()); // Prints 10
        System.out.println("Object field: " + getObject().val); // Prints 20
    }
}
```

### 2. Not Declaring Resources in try-with-resources Correctly
A common mistake is declaring resource variables outside of the try-with-resources parentheses. Doing so does not register them for automatic closure.
```java
// INCORRECT: resource is not automatically closed
BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
try (reader) { // Valid in Java 9+, but reader must be effectively final
    // ...
}
```

### 3. Forgetting to Implement AutoCloseable
Only classes that implement `java.lang.AutoCloseable` can be used as resources in try-with-resources. Trying to use any other class results in a compile error.
```java
// COMPILE ERROR: incompatible types: String cannot be converted to AutoCloseable
try (String s = "Hello") { // Compile error!
    System.out.println(s);
}
```

---

## Why Finally Executes Even When Try Returns Early

The `finally` block's guarantee is enforced by the JVM at the bytecode level, not just as a language convention. When the compiler compiles a `try-finally` construct, it generates bytecode that inserts the `finally` block's instructions at every possible exit path from the `try` block: normal completion, exception thrown, `return` executed, `break` executed, and `continue` executed.

The reason for this guarantee is resource management. Resources like file handles, database connections, and network sockets must be released regardless of whether the code inside the `try` block succeeded, failed, or returned early. Without a `finally` guarantee, a single unexpected exception path could bypass resource cleanup, causing resource leaks that accumulate over time and eventually crash the application.

When `return` is reached inside the `try` block, the JVM saves the return value in a register, executes the `finally` block, then resumes the return with the saved value. If the `finally` block itself contains a `return`, it overrides the saved return value — this is an anti-pattern because it silently discards the original return value and swallows any exception from the `try` block.

### Mental Model: JVM bytecode finally insertion
```
try {
    // code A
    return 42;            ← JVM saves 42 in temp register
    // code B (never reached after return)
} finally {
    // cleanup C          ← JVM inserts cleanup C here at EVERY exit path
}
// JVM resumes: returns saved 42 after C completes

// JVM inserts finally at EVERY exit from try:
//   normal exit       → execute finally → continue
//   return value      → save value → execute finally → return saved value
//   exception thrown  → execute finally → re-throw exception
//   System.exit()     → finally is NOT guaranteed (JVM terminates)
```

### Code Example: finally with return and with exception
```java
public class FinallyGuarantee {
    public static int getSavedValue() {
        try {
            System.out.println("In try");
            return 42; // ← return value saved, finally runs before method exits
        } finally {
            System.out.println("In finally"); // always runs
            // Output: "In try" → "In finally" → method returns 42
        }
    }

    public static int getOverriddenValue() {
        try {
            return 10; // ← saved, but...
        } finally {
            return 20; // ← ANTI-PATTERN: overrides saved 10, also swallows exceptions
        }
    }
}
// getSavedValue() → prints: "In try", "In finally", returns 42
// getOverriddenValue() → returns 20 (10 is discarded, any exception swallowed)
```

### Cause-Effect Chain
`return 42` in `try` &rarr; JVM saves return value 42 &rarr; Executes `finally` block &rarr; `finally` completes normally &rarr; JVM returns saved 42. Exception thrown in `try` &rarr; JVM begins stack unwinding &rarr; Executes `finally` block before leaving frame &rarr; `finally` completes &rarr; Exception re-thrown to caller. `System.exit(0)` in `try` &rarr; JVM terminates immediately &rarr; `finally` is NOT executed — JVM shutdown hook, not `finally`, is the last resort cleanup.

---

## Why Try-With-Resources Replaces Manual Finally for Resource Cleanup

Before Java 7, resource cleanup with `finally` had a subtle bug: if the `try` block threw an exception AND the `finally` block's `close()` call also threw an exception, the first exception was silently discarded — overwritten by the `close()` exception. This made debugging extremely difficult because the original failure cause was lost.

`try-with-resources` solves this through the concept of **exception suppression**. If both the `try` block and the `close()` call throw exceptions, the `close()` exception is attached to the original exception as a "suppressed exception" rather than replacing it. You can retrieve suppressed exceptions with `e.getSuppressed()`. The original failure cause is always preserved.

Additionally, `try-with-resources` generates compiler-guaranteed closing code. Resources are closed in reverse declaration order (last declared, first closed), and all resources are guaranteed to be closed even if earlier close calls throw exceptions. Manual `finally` with multiple resources requires nested try-finally blocks to achieve the same guarantee — code that is verbose, error-prone, and frequently written incorrectly.

### Mental Model: Manual finally vs try-with-resources closing guarantee
```
[Manual finally — original exception can be lost]
InputStream in = new FileInputStream("a");
OutputStream out = new FileOutputStream("b");
try {
    // ... work ...
    // if work throws IOException  ← saved as primary exception
} finally {
    in.close();   // if in.close() throws → REPLACES primary exception — original lost!
    out.close();  // if in.close() throws, out.close() never even runs!
}

[try-with-resources — original exception preserved, all resources closed]
try (InputStream in = new FileInputStream("a");
     OutputStream out = new FileOutputStream("b")) {
    // ... work ...
    // if work throws IOException ← primary exception kept
    // Compiler-generated close:
    //   out.close() called first (reverse order)
    //   in.close() called second
    //   if close throws → added as SUPPRESSED exception
    //   primary IOException is NOT replaced
}
```

### Code Example: try-with-resources and suppressed exceptions
```java
class TrackingResource implements AutoCloseable {
    private final String name;
    TrackingResource(String name) { this.name = name; System.out.println("Opened " + name); }

    @Override
    public void close() throws Exception {
        System.out.println("Closed " + name);
        // if close throws: throw new Exception("Close failed for " + name);
    }
}

try (TrackingResource r1 = new TrackingResource("R1");
     TrackingResource r2 = new TrackingResource("R2")) {
    System.out.println("Working...");
}
// Output:
// Opened R1
// Opened R2
// Working...
// Closed R2   ← reversed order: R2 first
// Closed R1

// If try body throws AND close throws:
// try { throw new IOException("primary"); }  ← primary exception
// close() { throw new IOException("close"); } ← added as suppressed
// e.getSuppressed()[0] → "close" (primary exception preserved)
```

### Cause-Effect Chain
Manual `finally` with `close()` in try + `finally` block &rarr; Both `try` body and `close()` throw &rarr; `close()` exception replaces primary exception &rarr; Original failure cause lost &rarr; Debugging nightmare. `try-with-resources` &rarr; Compiler generates close code at all exit paths &rarr; Multiple resources closed in reverse declaration order &rarr; `close()` exception added as suppressed &rarr; Original exception always preserved &rarr; `e.getSuppressed()` reveals close failure.

---

## Why Exception Chaining Preserves Debugging Context

When low-level exceptions are thrown by a dependency (like JDBC, file I/O, or an HTTP client), they contain implementation-specific details (SQL error codes, OS error numbers, driver class names) that are not meaningful to the caller. The caller may need to handle a higher-level abstraction — `DatabaseConnectionException` rather than `java.sql.SQLException("ORA-12170: TNS Connect Timeout")`.

Without exception chaining, translating low-level to high-level exceptions destroys the root cause information. When the application crashes, the stack trace shows only `DatabaseConnectionException: Failed to connect` — with no indication of what actually went wrong at the database layer. Debugging requires re-running the issue, adding extra logging, or guessing.

Exception chaining solves this by storing the original exception as the **cause** of the new exception via `new HighLevelException("msg", originalCause)`. The JVM includes the full cause chain in the stack trace output automatically. Any debugger or log aggregator that understands standard Java exceptions can traverse the cause chain with `e.getCause()` repeatedly to reach the root cause.

### Mental Model: Exception chaining cause chain
```
Application Layer Exception:
    DatabaseConnectionException: "Failed to initialize connection pool"
        caused by: HikariPoolException: "Pool connection timeout after 30000ms"
            caused by: java.sql.SQLException: "ORA-12170: TNS Connect Timeout occurred"
                caused by: java.net.SocketTimeoutException: "connect timed out"

Without chaining (bad):
    DatabaseConnectionException: "Failed to initialize connection pool"
    ← root cause completely lost. Developer has no idea why.

With chaining (correct):
    All 4 layers visible in a single stack trace printout.
    Developer immediately knows: it's a network socket timeout at the Oracle driver level.
```

### Code Example: Exception chaining with cause
```java
class DatabaseConnectionException extends Exception {
    public DatabaseConnectionException(String msg, Throwable cause) {
        super(msg, cause); // chains the cause
    }
}

public void connectToDatabase() throws DatabaseConnectionException {
    try {
        // Low-level driver call
        throw new java.sql.SQLException("ORA-12170: TNS Connect Timeout");
    } catch (java.sql.SQLException e) {
        // Translate to high-level exception, PRESERVING the root cause
        throw new DatabaseConnectionException("Failed to initialize connection pool", e);
    }
}

try {
    connectToDatabase();
} catch (DatabaseConnectionException e) {
    System.out.println("High-level: " + e.getMessage());
    System.out.println("Root cause: " + e.getCause().getMessage());
    // Output:
    // High-level: Failed to initialize connection pool
    // Root cause: ORA-12170: TNS Connect Timeout
}
```

### Cause-Effect Chain
Low-level `SQLException` thrown by JDBC driver &rarr; Caught at service layer &rarr; Wrapped in `new DatabaseConnectionException("msg", sqlException)` &rarr; Original `SQLException` stored as cause &rarr; `DatabaseConnectionException` propagates to caller &rarr; Caller catches high-level exception &rarr; Stack trace shows full cause chain including root `SQLException` &rarr; `e.getCause()` returns original `SQLException` with driver error details. Without chaining: `sqlException` discarded &rarr; Caller sees only generic message &rarr; Root cause irretrievably lost.

## Reference Links

- https://docs.oracle.com/javase/tutorial/essential/exceptions/ (Oracle Exception Tutorial)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Exception.html (Exception API)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/AutoCloseable.html (AutoCloseable API)
