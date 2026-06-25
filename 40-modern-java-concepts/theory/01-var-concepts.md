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

---

## Why var Is Compile-Time Inference Not Dynamic Typing

`var` is not Python's duck typing or JavaScript's `var`. It is **local variable type inference** — the compiler analyzes the right-hand side of the declaration and statically assigns a fixed type at compile time. Once assigned, the variable's type is immutable for the duration of its scope. The variable is no different from one explicitly typed — the bytecode is identical.

The four forbidden locations for `var` reveal exactly how static typing is preserved:

1. **Fields** — a field's type must be explicitly declared so the type is visible to any code that reads or writes the field, in any class, at any time.
2. **Method parameters** — the caller must know the parameter's type to compile the call site.
3. **Method return types** — the caller must know what type to expect from the return value.
4. **`var` without initializer / `var x = null`** — the compiler has no right-hand side to infer from, so inference is impossible.

### Mental Model: var vs Dynamic Typing
```
Python (dynamic):
x = 10
x = "hello"   ← OK: x changes type at runtime

Java var (static inference):
var x = 10;           ← Inferred as int at compile time
x = "hello";          ← COMPILE ERROR: int cannot hold String
// bytecode: identical to: int x = 10;
```

### Code Example: var in practice
```java
// All of these are statically typed at compile time
var name = "Alice";                     // String
var count = 42;                         // int
var list = new ArrayList<String>();     // ArrayList<String>

for (var entry : Map.of("k", 1).entrySet()) {
    // entry inferred as Map.Entry<String, Integer>
    System.out.println(entry.getKey() + "=" + entry.getValue());
}

// ILLEGAL — compiler has no type to infer
// var x;             // Error: cannot infer type
// var y = null;      // Error: cannot infer type from null
// var z;             // Error: variable must be initialized
```

### Cause-Effect Chain
`var x = new ArrayList<String>()` used &rarr; Compiler reads the right-hand side type `ArrayList<String>` &rarr; Assigns that type permanently to `x` &rarr; Generated bytecode identical to explicit `ArrayList<String> x` &rarr; Any reassignment to incompatible type causes compile error &rarr; No runtime cost, no boxing overhead.

---

## Why Records Enforce Immutability Through Compiler-Generated Code

Before Records, creating a simple immutable data class required writing a constructor, `final` fields, accessor methods, `equals()`, `hashCode()`, and `toString()` — often 50–100 lines of boilerplate for a trivial data carrier. Records eliminate this by encoding the contract "this class is a transparent carrier of its named components" directly in the language.

The compiler generates:
- **Private final fields** for each record component — no mutation after construction.
- **A canonical constructor** with all components as parameters that assigns each field.
- **Accessor methods** named after the fields (e.g., `point.x()`, not `getX()`) — accessors match field names by design to make component access discoverable.
- **`equals()` and `hashCode()`** comparing all components by value.
- **`toString()`** printing all components.

Records are implicitly `final` and cannot extend other classes (they implicitly extend `java.lang.Record`). This prevents mutation through subclassing. The compact constructor syntax allows validation without re-declaring parameters.

### Mental Model: Record vs Manual Immutable Class
```
// Manual immutable class — ~60 lines:
public final class Point {
    private final int x;
    private final int y;
    public Point(int x, int y) { this.x = x; this.y = y; }
    public int x() { return x; }
    public int y() { return y; }
    @Override public boolean equals(Object o) { ... }
    @Override public int hashCode() { ... }
    @Override public String toString() { ... }
}

// Record equivalent — 1 line:
public record Point(int x, int y) {}
// Compiler generates everything above automatically
```

### Code Example: Records with validation
```java
public record User(String name, int age) {
    // Compact constructor — no parameter list, uses implicit assignments
    public User {
        if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
        name = name.strip(); // Can transform values before assignment
    }
}

User u = new User("Alice", 30);
System.out.println(u.name()); // "Alice" — accessor, not getName()
System.out.println(u.age());  // 30
System.out.println(u);        // User[name=Alice, age=30]

// Cannot mutate — fields are final
// u.name = "Bob"; // COMPILE ERROR: no such field access
```

### Cause-Effect Chain
`record Point(int x, int y)` declared &rarr; Compiler generates private final fields, canonical constructor, accessors, equals/hashCode/toString &rarr; All fields final from construction &rarr; No mutator methods generated &rarr; Record is immutable by design &rarr; Safe to share across threads and use as Map keys.

---

## Why Sealed Classes Enable Safe Exhaustive Pattern Matching

Without sealed classes, the compiler cannot know at compile time what all possible subclasses of an abstract class or interface are. Anyone in any module can extend an open class. This means switch expressions on the type cannot be exhaustive — the compiler must always require a `default` branch to handle unknown subclasses.

Sealed classes declare exactly which classes are permitted to implement or extend them in the `permits` clause. The compiler can enumerate the complete set of possible types. When a `sealed` class is used in a `switch` expression and all permitted subtypes are covered, the compiler verifies exhaustiveness without requiring a `default` case — and will compile-fail if a new permitted subtype is added but the switch is not updated.

This enables "closed-type algebraic data types" that are common in Scala, Haskell, and Kotlin (`sealed` classes). The compiler becomes a correctness guarantor for type-based branching.

### Mental Model: Open vs Sealed class hierarchy exhaustiveness
```
// Open class — compiler cannot enumerate all subclasses
abstract class Shape {}
// Switch MUST have default to be exhaustive
String describe = switch (shape) {
    case Circle c -> "circle with radius " + c.radius();
    default -> "unknown shape"; // Cannot remove this safely
};

// Sealed class — compiler knows all permitted subtypes
sealed class Shape permits Circle, Square {}
// Switch is exhaustive without default
String describe = switch (shape) {
    case Circle c -> "circle with radius " + c.radius();
    case Square s -> "square with side " + s.side();
    // No default needed — compiler verifies all subtypes covered
};
// If Rectangle is added to permits but not to switch → COMPILE ERROR
```

### Code Example: Sealed class with switch expression
```java
public sealed interface Shape permits Circle, Square, Triangle {}
public record Circle(double radius) implements Shape {}
public record Square(double side) implements Shape {}
public record Triangle(double base, double height) implements Shape {}

// Exhaustive switch — no default required
double area = switch (shape) {
    case Circle c -> Math.PI * c.radius() * c.radius();
    case Square s -> s.side() * s.side();
    case Triangle t -> 0.5 * t.base() * t.height();
};
```

### Cause-Effect Chain
`sealed` declared with `permits Circle, Square` &rarr; Compiler records permitted subtype set &rarr; Switch on `Shape` covers `Circle` and `Square` &rarr; Compiler verifies exhaustiveness &rarr; No `default` required &rarr; Adding `Triangle` to permits without updating switch &rarr; Compile error.

---

## Why Pattern Matching for Switch Requires Ordering by Specificity

The Java compiler enforces a **dominance rule** for pattern matching in switch: a more specific pattern cannot appear after a more general pattern that would match all the same inputs. If a general case appears first, the specific case that follows can never be reached — it is "dominated."

For guarded patterns like `case String s when s.length() > 5`, the guard `when s.length() > 5` is a subset of `case String s` (which matches all Strings). If `case String s` is listed first, the guarded case `case String s when s.length() > 5` can never execute — the unguarded case already captured all Strings.

This is a **compile-time** check. The compiler rejects switch statements where a case is dominated, preventing silent bugs where a more precise case was accidentally shadowed.

### Mental Model: Dominance ordering rule
```
// ILLEGAL — case String s dominates the guarded case below it
switch (obj) {
    case String s -> "any string: " + s;          // Matches ALL strings
    case String s when s.length() > 5 -> "long";  // COMPILE ERROR: dominated
}

// LEGAL — most specific guarded case first
switch (obj) {
    case String s when s.length() > 5 -> "long string: " + s; // Specific
    case String s -> "short string: " + s;                    // General
    case Integer i -> "integer: " + i;
    default -> "other";
}
```

### Code Example: Pattern matching switch with guards
```java
Object obj = "Hello World";
String desc = switch (obj) {
    case null -> "null value";
    case Integer i when i > 100 -> "large integer: " + i;
    case Integer i -> "small integer: " + i;
    case String s when s.length() > 5 -> "long string: " + s;
    case String s -> "short string: " + s;
    default -> "unknown: " + obj;
};
System.out.println(desc); // Output: long string: Hello World
```

### Cause-Effect Chain
Pattern cases evaluated top-to-bottom &rarr; More general `case String s` matches all Strings &rarr; Specific `case String s when ...` below it can never be reached &rarr; Compile error: pattern dominated &rarr; Reorder: guarded (specific) before unguarded (general) &rarr; Compiler verifies no unreachable patterns &rarr; All cases reachable.

---

## Why Virtual Threads Pin Carrier Threads in Synchronized Blocks

Virtual threads are managed by the JVM on top of a small pool of real OS "carrier" threads. When a virtual thread needs to block (waiting for I/O, a lock, a condition), the JVM **mounts** it on a carrier thread, and when blocking, **unmounts** it (suspending the virtual thread's state while freeing the carrier thread for other virtual threads).

The problem is `synchronized`. When a virtual thread enters a `synchronized` block, the JVM must hold the monitor lock on the carrier thread's native OS thread — this is a JVM implementation constraint of the current HotSpot lock model. If the virtual thread blocks while holding the monitor (e.g., blocking I/O inside `synchronized`), the carrier thread is "pinned" — it cannot be freed to run other virtual threads, defeating the whole purpose of virtual thread scalability.

`ReentrantLock` does not use OS-level monitors. It uses JVM-level queue structures that can be associated with the virtual thread, not the carrier thread. When a virtual thread blocks on `ReentrantLock.lock()`, the carrier thread is unmounted and freed while the virtual thread waits.

### Mental Model: synchronized pinning vs ReentrantLock unmounting
```
[synchronized — pins carrier thread]
Virtual Thread 1 enters synchronized block
    → Virtual Thread 1 blocks inside synchronized (waiting for I/O)
    → Carrier OS Thread 1 is PINNED — cannot accept other VTs
    → Only 1 effective VT running on that carrier — scalability lost

[ReentrantLock — allows carrier unmount]
Virtual Thread 1 calls reentrantLock.lock()
    → Virtual Thread 1 blocks on lock()
    → JVM unmounts VT1 from Carrier OS Thread 1
    → Carrier OS Thread 1 now free to run Virtual Thread 2, 3, ...
    → When lock released, VT1 remounts on any available carrier
```

### Code Example: ReentrantLock instead of synchronized for virtual threads
```java
import java.util.concurrent.locks.ReentrantLock;

public class SafeVirtualThreadCounter {
    private final ReentrantLock lock = new ReentrantLock(); // Use instead of synchronized
    private int count = 0;

    public void increment() {
        lock.lock(); // Does not pin the carrier thread
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }
}

// Creating virtual threads
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    for (int i = 0; i < 1_000_000; i++) {
        executor.submit(() -> counter.increment());
    }
}
```

### Cause-Effect Chain
Virtual thread enters `synchronized` block &rarr; JVM must hold OS-level monitor on carrier thread &rarr; Virtual thread blocks (I/O, wait) inside synchronized &rarr; Carrier thread pinned and unavailable &rarr; Virtual thread scalability degraded &rarr; Replace with `ReentrantLock` &rarr; Blocking on lock unmounts virtual thread from carrier &rarr; Carrier freed for other virtual threads &rarr; Full concurrency restored.

## Reference Links

- https://openjdk.org/jeps/286 (JEP 286 — Local-Variable Type Inference: var)
- https://openjdk.org/jeps/395 (JEP 395 — Records)
- https://openjdk.org/jeps/409 (JEP 409 — Sealed Classes)
- https://openjdk.org/jeps/441 (JEP 441 — Pattern Matching for switch)
- https://openjdk.org/jeps/444 (JEP 444 — Virtual Threads)

