# Best Practices in Java - Part 1

| Concept | What to know |
| --- | --- |
| `Name variables, functions, and classes clearly` | Rules for writing self-documenting code with descriptive names. |
| `Code according to convention` | Adherence to standard camelCase, PascalCase, and UPPER_SNAKE_CASE styles. |
| `Do not overuse static` | The tradeoff of overusing static states and methods (testability, concurrency issues). |
| `Do not overuse inheritance` | Tight coupling risks associated with class inheritance (`extends`). |
| `Prefer composition over inheritance` | The pattern of achieving behavior using object reference relationships rather than subclassing. |
| `Override equals/hashCode correctly` | Maintaining the strict logical contract between `equals()` and `hashCode()`. |
| `Use StringBuilder when concatenating strings many times` | Optimizing loop concatenation to avoid excessive string object creation. |
| `Use BigDecimal for money` | Eliminating binary floating-point rounding errors in monetary math. |
| `Use try-with-resources` | Automatic cleanup of resource streams implementing `AutoCloseable`. |
| `Do not catch overly broad Exception if unnecessary` | Targeting catching of specific checked exceptions instead of catching generic `Exception`. |

---

## Detailed Notes

### Name variables, functions, and classes clearly

Choose descriptive, intention-revealing names to make code self-documenting. Avoid single-character names (except for index counters) and obscure abbreviations.

- **Examples**:
  ```java
  // BAD:
  int d = 86400; // Unclear unit and purpose
  
  // GOOD:
  int secondsPerDay = 86400;
  ```

---

### Code according to convention

Following conventions makes your codebase readable to other developers.

- **Conventions**:
  - **Classes**: PascalCase (e.g., `OrderProcessor`)
  - **Methods & Variables**: camelCase (e.g., `processOrder`, `customerId`)
  - **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_RETRY_COUNT`)

---

### Do not overuse static

`static` indicates that a member belongs to the class type rather than to class instances.

- **Tradeoffs**:
  - **Testability**: Static methods are difficult to mock in unit tests, making isolation testing hard.
  - **Thread-Safety**: Storing state in static variables (e.g., user request context) creates concurrent access problems in multi-threaded application servers.
  - **Rule**: Limit `static` to pure utility functions (e.g., `Math.sqrt()`) and true constants.

---

### Do not overuse inheritance

Inheritance (`extends`) creates a rigid, compile-time link between parent and child classes.

- **Risks (Fragile Base Class)**:
  - If a parent class changes its implementation details, it can silently break subclass assumptions or introduce method collisions.
  - Subclasses inherit *all* public/protected methods from parent classes, exposing APIs that might not make sense for the child class (violating encapsulation).

---

### Prefer composition over inheritance

Instead of extending classes to reuse behavior, acquire behavior by holding a reference to an instance of that class (a "has-a" relationship instead of an "is-a" relationship).

- **Runnable Example**:
  ```java
  // BAD: Inheritance couples SecureStack tightly to Stack
  class SecureStack extends Stack<String> {
      // inherits all Stack methods, exposing stack implementation details
  }

  // GOOD: Composition wraps Stack, exposing only safe methods
  class SecureStack {
      private final Stack<String> stack = new Stack<>();

      public void push(String item) {
          // validate and delegate
          stack.push(item);
      }
  }
  ```

---

### Override equals/hashCode correctly

If you override `equals()`, you **must** override `hashCode()` to maintain the logical equality contract.

- **The Contract**: If `a.equals(b)` is true, then `a.hashCode() == b.hashCode()` must also evaluate to true.
- **Runnable Example**:
  ```java
  public class User {
      private String email;

      @Override
      public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;
          User user = (User) o;
          return Objects.equals(email, user.email);
      }

      @Override
      public int hashCode() {
          return Objects.hash(email); // Must match equals evaluation fields!
      }
  }
  ```
- **Pitfall**: Failing to override `hashCode()` means two distinct user objects with identical emails will return different hashes, causing duplicate records in `HashSet` or retrieval failures in `HashMap`.

---

### Use StringBuilder when concatenating strings many times

Since `String` objects are immutable in Java, concatenating string objects inside a loop using the `+` operator generates a new String instance on every iteration, leading to $O(n^2)$ time complexity.

- **Runnable Example**:
  ```java
  // BAD: Creates 10,000 temporary String objects in heap
  String result = "";
  for (int i = 0; i < 10000; i++) {
      result += i; 
  }

  // GOOD: Single buffer modified in-place, O(n) execution
  StringBuilder sb = new StringBuilder();
  for (int i = 0; i < 10000; i++) {
      sb.append(i);
  }
  String finalResult = sb.toString();
  ```

---

### Use BigDecimal for money

Binary floating-point types (`double` and `float`) cannot precisely represent fractions of base 10 (like 0.1), which introduces rounding errors. Always use `BigDecimal` for currency math.

- **Runnable Example**:
  ```java
  // BAD: prints 0.30000000000000004
  System.out.println(0.1 + 0.2); 

  // GOOD: prints 0.3 exactly
  BigDecimal val1 = new BigDecimal("0.1");
  BigDecimal val2 = new BigDecimal("0.2");
  System.out.println(val1.add(val2));
  ```

---

### Use try-with-resources

Always close resource handles (streams, files, sockets, DB connections) that implement `AutoCloseable` using a try-with-resources statement to avoid system resource leaks.

- **Runnable Example**:
  ```java
  // Automatically calls reader.close() when leaving block, even if an exception occurs
  try (BufferedReader reader = new BufferedReader(new FileReader("config.txt"))) {
      System.out.println(reader.readLine());
  } catch (IOException e) {
      System.out.println("Error reading file");
  }
  ```

---

### Do not catch overly broad Exception if unnecessary

Catching `Exception` or `Throwable` broadens exception handling to catch all subclasses, including unchecked runtime failures.

- **Pitfall**:
  ```java
  try {
      readConfigFile();
  } catch (Exception e) {
      // BAD: This catches IOException, but also masks NullPointerException, 
      // OutOfMemoryError, and other developer errors!
  }
  ```
- **Rule**: Catch only the specific checked exceptions your method expects (e.g. `IOException`, `SQLException`). Let unexpected programming errors bubble up so they can be fixed.

---

## Why Descriptive Naming Matters

Descriptive naming is a core clean code practice that reduces cognitive load when reading and maintaining software. When names reveal intent, avoid disinformation, and provide meaningful distinctions, developers can comprehend the logic without reading the implementation details. In Java, local variable names are used by the compiler to construct the Local Variable Table for debugging, but at the bytecode level, the JVM refers to variables by index slots (e.g., `iload_1`, `dstore_2`). Because the Java compiler (`javac`) discards variable names during compilation (unless compiled with `-g`), long descriptive names carry absolutely zero runtime overhead or performance penalty in the JVM. Utilizing single-letter variables (except as loop counters) or Hungarian notation (e.g., `iCount`, `strName`) degrades readability as codebases scale, since it forces the reader to track arbitrary encodings instead of domain concepts.

### Mental Model: Cognitive Load in Reading Code

```text
Obscure Naming:
[Code: x = a * b / 100] ---> [Lookup 'a' meaning] ---> [Lookup 'b' meaning] ---> Cognitive Overload!

Descriptive Naming:
[Code: tax = price * rate / 100] ---------------------------------------------> Immediate Understanding
```

### Code Example

```java
public class NamingDemo {
    public static void main(String[] args) {
        // Obscure: What are x, y, and z representing?
        double x = 150.0;
        double y = 0.08;
        double z = x * y;
        System.out.println("Result: " + z); // Result: 12.0

        // Descriptive: Intention-revealing variables
        double itemPrice = 150.0;
        double salesTaxRate = 0.08;
        double calculatedSalesTax = itemPrice * salesTaxRate;
        System.out.println("Calculated Sales Tax: " + calculatedSalesTax); // Calculated Sales Tax: 12.0
    }
}
```

### Cause-Effect Chain

```text
Descriptive variable names used → Discarded by javac compiler during compilation → Translated to bytecode index offsets (no runtime overhead) → Human readers scan names instead of tracking types/encodings → Prevented cognitive overload and naming anti-patterns
```

---

## Why Constants Prevent Magic Numbers

Magic numbers are literal values used in code without explanation, which obscure developer intent and make updates error-prone. Extracting these values into named constants improves readability and ensures changes are localized to a single definition. From a language and JVM perspective, `public static final` primitive or `String` values are recognized as compile-time constants. The Java compiler (`javac`) performs constant inlining, substituting the constant's value directly into the consuming class's bytecode rather than inserting a `getstatic` instruction to look up the field at runtime. While this optimization reduces runtime field-resolution overhead, it creates a subtle compilation dependency: if a constant's value is modified, all classes that reference it must be recompiled to reflect the change in their inlined bytecode.

### Mental Model: Compile-Time Constant Inlining

```text
Source Code:
class Config { public static final int MAX_LIMIT = 50; }
class Client { int limit = Config.MAX_LIMIT; }

Bytecode (after javac):
Config.class  <-- Contains field definition
Client.class  <-- Contains literal 'bipush 50' directly (Inlined! No runtime reference to Config)
```

### Code Example

```java
public class ConstantsDemo {
    // Declared as a compile-time constant
    public static final int DAYS_IN_WEEK = 7;
    public static final double SALES_TAX_PERCENT = 8.25;

    public static void main(String[] args) {
        double subtotal = 100.0;
        // Bad: Magic number 8.25 makes it hard to know if it's tax, interest, or a discount
        double taxAmountBad = subtotal * (8.25 / 100.0);
        System.out.println("Tax: " + taxAmountBad); // Tax: 8.25

        // Good: Named constant clarifies intent and is inlined at compile-time
        double taxAmountGood = subtotal * (SALES_TAX_PERCENT / 100.0);
        System.out.println("Tax: " + taxAmountGood); // Tax: 8.25
    }
}
```

### Cause-Effect Chain

```text
Literal extracted to public static final constant → javac identifies value as compile-time constant → Constant value inlined directly into consuming bytecode → getstatic JVM lookup instructions avoided at runtime → Magic numbers prevented & execution speed optimized
```

---

## Why Guard Clauses Simplify Control Flow

The "Return Early" or "Fail Fast" guard-clause pattern replaces deeply nested conditional blocks with early exit statements. Nested conditional blocks require developers to maintain a complex mental stack of preconditions to follow execution paths, which exponentially increases cognitive load. In contrast, guard clauses handle invalid states or trivial cases first and exit immediately, allowing the reader to ignore those paths for the remainder of the method. From a JVM perspective, guard clauses generate a flatter, more linear Control Flow Graph (CFG) in the compiled bytecode. This streamlined structure assists the JIT compiler's optimization passes, making branch prediction more effective and increasing the likelihood of successful method inlining by avoiding deep nesting.

### Mental Model: Nested if-else vs. Guard Clause Flow

```text
Nested if-else (Deep Stacking):
[Check A] ---> Yes ---> [Check B] ---> Yes ---> [Process Core Logic]
   |                       |
   No (Exit)               No (Exit)

Guard Clauses (Linear Path):
[Check A is Bad] ---> Throw/Return (Early Exit)
[Check B is Bad] ---> Throw/Return (Early Exit)
[Process Core Logic] (Happy Path - No Nesting)
```

### Code Example

```java
public class GuardClauseDemo {
    public static String processOrderNested(String orderId, int quantity) {
        if (orderId != null) {
            if (quantity > 0) {
                return "Order " + orderId + " processed successfully.";
            } else {
                return "Invalid quantity.";
            }
        } else {
            return "Invalid order ID.";
        }
    }

    public static String processOrderGuard(String orderId, int quantity) {
        // Guard clauses (fail fast)
        if (orderId == null) return "Invalid order ID.";
        if (quantity <= 0) return "Invalid quantity.";

        // Core business logic (happy path)
        return "Order " + orderId + " processed successfully.";
    }

    public static void main(String[] args) {
        System.out.println(processOrderNested("101", 5)); // Order 101 processed successfully.
        System.out.println(processOrderGuard("101", 5));  // Order 101 processed successfully.
    }
}
```

### Cause-Effect Chain

```text
Guard clauses evaluate invalid inputs first → Method returns or throws immediately on failure → Core business logic proceeds without indentation nested blocks → Flat control flow graph generated in bytecode → Reduced cognitive load for developers & JIT branch prediction optimized
```

---

## Reference Links

- [Oracle Java Documentation](https://docs.oracle.com/javase/specs/jls/se21/html/index.html)
- [Oracle Java SE Naming Conventions](https://docs.oracle.com/javase/specs/jls/se21/html/jls-6.html#jls-6.1)
- [Java JIT Compiler Optimizations](https://docs.oracle.com/en/java/javase/21/gctuning/)
