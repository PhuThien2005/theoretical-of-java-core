# Modern Java Concepts To Know - Part 1

## Learning Goal

This file covers a focused slice of **Modern Java Concepts** introduced in recent JDK releases (from Java 10 to 21). Study each concept as a practical Java rule.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `var` | Local variable type inference keyword (`var`) introduced in Java 10. |
| `Records` | Compact data-carrier class type introduced in Java 16 to represent immutable data records. |
| `Sealed class` | Class hierarchy control modifier introduced in Java 17 to restrict subclass inheritance. |
| `Pattern matching for instanceof` | Simplified type casting mechanism introduced in Java 16. |
| `Switch expression` | Arrow-syntax switch that returns values, introduced in Java 14. |
| `Text blocks` | Multiline string literal format (`"""`) introduced in Java 15. |
| `Enhanced NullPointerException message` | Verbose, exact trace JVM-level crash logs. |
| `Virtual Threads` | Lightweight thread architecture introduced in Java 21 for blocking concurrent workloads. |
| `Basic Structured Concurrency` | Concurrency pipeline organizing child tasks as a single transaction block. |
| `Pattern matching for switch` | Type-based branching and conditional guards inside switches (Java 21). |

---

## Detailed Notes

### var

`var` allows the compiler to infer the static type of a local variable based on its initializer expression.

- **Runnable Example**:
  ```java
  var name = "Alice";                       // Inferred as String
  var list = new ArrayList<String>();       // Inferred as ArrayList<String>
  
  for (var element : list) {                // Inferred as String inside loop
      System.out.println(element);
  }
  ```

- **Common Mistake / Failure Mode**:
  - **Not Dynamic Typing**: Variables declared with `var` are still statically typed. You cannot reassign them to incompatible types.
    ```java
    var count = 10;
    // count = "ten"; // COMPILE ERROR: Incompatible types
    ```
  - **Invalid Use Cases**: `var` cannot be initialized without a value, or with a null literal, and cannot be used for fields, method parameters, or return types.
    ```java
    // var x;      // COMPILE ERROR
    // var y = null; // COMPILE ERROR
    ```

---

### Records

Records are compact, final classes designed to act as simple immutable data carriers. The compiler automatically generates private final fields, a canonical constructor, accessor methods (matching field names), `equals()`, `hashCode()`, and `toString()`.

- **Runnable Example**:
  ```java
  public record Point(int x, int y) {}

  // Usage:
  Point p = new Point(10, 20);
  System.out.println(p.x()); // 10 (Accessors do NOT have a 'get' prefix)
  ```

- **Common Mistake / Failure Mode**:
  - **Implicit Finality**: Records are final and cannot extend other classes (they already extend `java.lang.Record`). All fields are final and cannot be modified.
  - **Validation in Constructors**: To validate inputs, use a compact constructor. Do not redeclare parameters or fields.
    ```java
    public record User(String name, int age) {
        public User { // Compact constructor (no parameter list)
            if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
        }
    }
    ```

---

### Sealed class

Sealed classes and interfaces restrict which other classes or interfaces may extend or implement them.

- **Runnable Example**:
  ```java
  public abstract sealed class Shape permits Circle, Square {}
  
  // Subclasses must be final, sealed, or non-sealed:
  public final class Circle extends Shape {}
  public non-sealed class Square extends Shape {}
  ```

- **Common Mistake / Failure Mode**:
  - Subclasses of a sealed class must explicitly declare one of three modifiers: `final` (cannot be subclassed further), `sealed` (can be subclassed only by permitted sub-subclasses), or `non-sealed` (opens the class back up to extension by any class). Omitting this modifier is a compilation error.

---

### Pattern matching for instanceof

Pattern matching for `instanceof` combines type checking and automatic casting into a single step.

- **Runnable Example**:
  ```java
  Object obj = "Hello World";
  if (obj instanceof String s) {
      // 's' is automatically cast to String and is in scope here
      System.out.println(s.toLowerCase());
  }
  ```

- **Common Mistake / Failure Mode**:
  - **Scope Limitation**: The binding variable is only in scope where the compiler can guarantee the type check was true.
    ```java
    // COMPILE ERROR: 's' is not in scope in the or (||) branch
    // if (obj instanceof String s || s.isEmpty()) {} 

    // CORRECT: 's' is in scope in the and (&&) branch due to short-circuiting
    if (obj instanceof String s && !s.isEmpty()) {
        System.out.println(s);
    }
    ```

---

### Switch expression

Switch expressions allow `switch` to yield a value, utilizing the arrow operator (`->`) which prevents fall-through, replacing the verbose colon-break syntax.

- **Runnable Example**:
  ```java
  int score = switch (grade) {
      case 'A' -> 100;
      case 'B' -> 80;
      case 'C', 'D' -> 60;
      default -> {
          System.out.println("Failing grade");
          yield 0; // Use yield to return value from multi-line blocks
      }
  };
  ```

- **Common Mistake / Failure Mode**:
  - **Exhaustiveness Check**: Switch expressions must be exhaustive. If you do not cover every possible case (e.g., all enum values or sealed subclasses), you must provide a `default` case, otherwise compilation fails.

---

### Text blocks

Text blocks provide multi-line string literals, preserving formatting and eliminating the need for escaped newline sequences.

- **Runnable Example**:
  ```java
  String html = """
                <html>
                    <body>
                        <p>Hello, World</p>
                    </body>
                </html>
                """;
  ```

- **Common Mistake / Failure Mode**:
  - **Opening Delimiter Rules**: The opening three double quotes `"""` must be immediately followed by a newline. Placing text on the same line as the opening quotes is a syntax error.
    ```java
    // COMPILE ERROR:
    // String bad = """hello
    // world""";
    ```

---

### Enhanced NullPointerException message

Since Java 14, the JVM outputs precise details explaining which variable or return value evaluated to null.

- **Example**:
  ```java
  // For statement: person.getAddress().getCity()
  // If getAddress() is null, the NPE stack trace details:
  // "Cannot invoke "Address.getCity()" because the return value of "Person.getAddress()" is null"
  ```

- **Tradeoff**: These details are generated at runtime by analyzing bytecode. While extremely helpful for debugging, it can be disabled on the command line using `-XX:-ShowCodeDetailsInExceptionMessages` to save performance or obscure class internals.

---

### Virtual Threads

Virtual threads are lightweight threads managed by the JVM rather than the OS. They allow running millions of concurrent threads with minimal memory overhead, ideal for block-on-I/O applications.

- **Runnable Example**:
  ```java
  // Create and start a single virtual thread
  Thread vt = Thread.ofVirtual().start(() -> {
      System.out.println("Running on virtual thread: " + Thread.currentThread());
  });

  // Use executor for high-concurrency tasks
  try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      executor.submit(() -> {
          // perform network blocking I/O
      });
  }
  ```

- **Common Mistake / Failure Mode**:
  - **Carrier Thread Pinning**: If a virtual thread runs blocking work inside a `synchronized` block or native method, it "pins" its underlying OS carrier thread, preventing other virtual threads from running on it.
  - **Mitigation**: Replace `synchronized` blocks with `java.util.concurrent.locks.ReentrantLock` for blocking code paths.
  - **Thread Pooling**: Do not pool virtual threads (avoid `fixedThreadPool`). They are cheap to create and should be discarded after use.

---

### Basic Structured Concurrency

Structured Concurrency (currently a preview feature) treats multiple concurrent subtasks running in separate threads as a single unit of work, coordinating lifetime and cancellation cleanly.

- **Runnable Example**:
  ```java
  // Requires compiling and running with '--enable-preview'
  try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
      Subtask<String> user  = scope.fork(() -> fetchUser());
      Subtask<Integer> order = scope.fork(() -> fetchOrder());

      scope.join();           // Join all subtasks
      scope.throwIfFailed();  // Propagate first failed task exception

      System.out.println("Result: " + user.get() + " | " + order.get());
  }
  ```

- **Common Mistake / Failure Mode**:
  - Calling `.get()` on a Subtask before calling `scope.join()` throws an `IllegalStateException`.

---

### Pattern matching for switch

Pattern matching for switch extends switch statements to check parameter classes and test conditions directly on type cases.

- **Runnable Example**:
  ```java
  Object obj = "Short";
  String desc = switch (obj) {
      case Integer i -> "An integer: " + i;
      // Case guard using 'when' (evaluates only if pattern matches String)
      case String s when s.length() > 5 -> "Long string: " + s;
      case String s -> "Short string: " + s;
      default -> "Unknown type";
  };
  ```

- **Common Mistake / Failure Mode**:
  - **Dominance issues**: Specific type/conditional patterns must be written above generic cases. If `case String s` is written above `case String s when s.length() > 5`, the compiler fails because the latter is dominated (unreachable).
