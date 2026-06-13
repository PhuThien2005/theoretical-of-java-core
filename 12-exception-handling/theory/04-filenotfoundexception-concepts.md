# Exception Handling - Part 4

## Learning Goal

This file covers a focused slice of **Exception Handling**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `FileNotFoundException` | Subclass of IOException thrown when opening a file fails due to absence or access issues. |
| `SQLException` | Checked exception representing database connection or query execution errors. |
| `Best practices when handling exceptions` | Core engineering guidelines to handle exceptions robustly and keep code maintainable. |

## Detailed Notes

### FileNotFoundException

A checked exception, subclass of `IOException`, that is thrown when a file with the specified pathname cannot be found, or cannot be opened for reading/writing (e.g., trying to write to a directory, or access denied).

Practical check:

- Define `FileNotFoundException` in one sentence.
- Recognize `FileNotFoundException` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `FileNotFoundException`.

Tiny example or mental model:

- `new FileReader("does_not_exist.txt")` throws `FileNotFoundException`.

#### Runnable Code Example: Triggering and Handling
```java
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class FileNotFoundDemo {
    public static void main(String[] args) {
        File file = new File("invalid_path_to_file.txt");
        try {
            FileReader fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println("Caught FileNotFoundException: " + e.getMessage());
        }
    }
}
```

### SQLException

A checked exception that provides information on a database access error or other errors related to interaction with a relational database.

Practical check:

- Define `SQLException` in one sentence.
- Recognize `SQLException` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `SQLException`.

Tiny example or mental model:

- Connecting to a database with invalid credentials.

#### Runnable Code Example: Triggering and Handling (Simulated)
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDemo {
    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://localhost:3306/non_existent_db";
        try {
            // Attempting connection (will throw SQLException if DB or driver is not set up)
            Connection conn = DriverManager.getConnection(dbUrl, "user", "password");
        } catch (SQLException e) {
            System.out.println("Caught SQLException!");
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
        }
    }
}
```

### Best practices when handling exceptions

Robust exception handling ensures the application can recover from unexpected errors gracefully, log relevant details for debugging, and release resources properly.

Practical check:

- Define `Best practices when handling exceptions` in one sentence.
- Recognize `Best practices when handling exceptions` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Best practices when handling exceptions`.

Tiny example or mental model:

- Always close resources using try-with-resources; never catch `Throwable` when you can catch specific exceptions.

#### Key Exception Handling Best Practices
1. **Never swallow exceptions**: An empty `catch` block hides bugs. Always log the failure or wrap and rethrow it.
2. **Catch specific exceptions**: Avoid catching generic `Exception` or `Throwable`. Catch specific subclasses so you don't inadvertently handle runtime errors (like NPE) that represent logic bugs.
3. **Use Try-with-Resources**: Avoid manual resource cleanup in `finally` blocks, which is verbose and prone to secondary resource closure exceptions.
4. **Preserve Stack Traces**: When wrapping a low-level exception in a custom exception, always pass the original exception to the constructor so the root cause is preserved.
5. **Do not use exceptions for flow control**: Exception creation is expensive due to stack trace generation. Use conditional statements instead.

```java
// GOOD: Exception chaining preserves the original source stack trace
try {
    // ... file reading
} catch (IOException e) {
    throw new CustomBusinessException("Failed to read user data", e);
}

// BAD: Original exception is discarded, stack trace starts here
try {
    // ... file reading
} catch (IOException e) {
    throw new CustomBusinessException("Failed to read user data: " + e.getMessage());
}
```

## Common Mistakes

### 1. Using Exceptions for Flow Control
Using exceptions to control the execution path of a program is a major anti-pattern. Exceptions should only be reserved for abnormal, unexpected conditions.
```java
// BAD: Using Exception to exit a loop
try {
    int i = 0;
    while (true) {
        System.out.println(array[i++]);
    }
} catch (ArrayIndexOutOfBoundsException e) {
    // Loop finished
}

// GOOD: Clean loop condition
for (int i = 0; i < array.length; i++) {
    System.out.println(array[i]);
}
```

### 2. Catching Throwable
Catching `Throwable` will catch both `Exception` and `Error`. Catching errors like `OutOfMemoryError` or `InternalError` is dangerous because the JVM may not be in a stable state to continue execution.
```java
// DANGEROUS: Catching system-level errors
try {
    process();
} catch (Throwable t) { 
    System.out.println("Caught everything!");
}
```

### 3. Logging and Rethrowing
Logging an exception and then immediately rethrowing it results in duplicate logs at every level of the call stack, filling logs with noise.
```java
// BAD: Duplicate logging
try {
    readFile();
} catch (IOException e) {
    logger.error("Failed to read file", e); // Logged here
    throw e; // Logged again by the caller!
}
```

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
