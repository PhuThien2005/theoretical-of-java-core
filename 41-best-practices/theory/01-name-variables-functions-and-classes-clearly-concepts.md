# Best Practices in Java - Part 1

## Learning Goal

This file covers foundational Java **Best Practices** regarding naming, structural design, string concatenation efficiency, precision arithmetic, and safe exception/resource management. Study each concept as a practical Java rule.

## Outline Coverage

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
