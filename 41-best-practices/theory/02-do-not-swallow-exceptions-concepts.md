# Best Practices in Java - Part 2

## Learning Goal

This file covers advanced Java **Best Practices** including exception handling rules, type-safety, null avoidance, coding for testability, and class immutability patterns. Study each concept as a practical Java rule.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Do not swallow exceptions` | Rules for handling or propagating exceptions instead of ignoring them. |
| `Use interface type when declaring Collection` | Coding to interfaces (`List`, `Set`, `Map`) rather than concrete classes. |
| `List<String> list = new ArrayList<>();` | Declaring type-safe collections using diamond inference. |
| `Avoid raw type` | Preventing compile-time type warnings by avoiding non-generic raw classes. |
| `Avoid null when possible` | Techniques to prevent NullPointerExceptions. |
| `Write testable code` | Structural rules (Dependency Injection, mocking compatibility) for testability. |
| `Separate class/method responsibilities` | Applying the Single Responsibility Principle (SRP) to keep code maintainable. |
| `Immutability when appropriate` | Making classes immutable using records, final fields, and unmodifiable views. |

---

## Detailed Notes

### Do not swallow exceptions

Swallowing exceptions (catching an exception and doing nothing, or only logging a trivial message without resolving or rethrowing it) is a major anti-pattern. It masks system failures, making debugging close to impossible.

- **Runnable Example**:
  ```java
  // BAD: Silently ignores the exception, leaving application in an unstable state
  try {
      parseConfiguration();
  } catch (IOException e) {
      // Swallowed! Code execution continues blindly.
  }

  // GOOD: Logs details and propagates the exception wrapped in a RuntimeException
  try {
      parseConfiguration();
  } catch (IOException e) {
      logger.error("Configuration loading failed, exiting process.", e);
      throw new RuntimeException("Fatal error parsing configuration", e);
  }
  ```

---

### Use interface type when declaring Collection

Code against interfaces (`List`, `Set`, `Map`, `Queue`) rather than concrete classes (`ArrayList`, `HashSet`, `HashMap`, `LinkedList`). This decouples your code from specific implementation details, allowing you to swap collections easily when requirements change.

- **Runnable Example**:
  ```java
  // BAD: Hardcodes dependency on ArrayList implementation details
  ArrayList<String> users = new ArrayList<>();

  // GOOD: Declares variable using the interface type
  List<String> users = new ArrayList<>();
  ```

---

### List<String> list = new ArrayList<>();

Always specify type arguments to ensure compile-time type safety. Use the diamond operator (`<>`) on the constructor invocation side to let the compiler infer type parameters automatically.

- **Example**:
  ```java
  List<String> list = new ArrayList<>(); // Clean, type-safe, dry
  ```

---

### Avoid raw type

Raw types are generic classes declared without a type parameter (e.g., using `List` instead of `List<String>`). They bypass compile-time type safety checking, returning to legacy pre-Java 5 behaviors and risking runtime `ClassCastException`s.

- **Runnable Example**:
  ```java
  // BAD: Raw type allows compiling mixed element additions
  List rawList = new ArrayList();
  rawList.add("Hello");
  rawList.add(123); // Compiles fine!

  // Runtime crash when reading:
  String str = (String) rawList.get(1); // ClassCastException at runtime!

  // GOOD: Generic list catches mistakes at compile time
  List<String> genericList = new ArrayList<>();
  // genericList.add(123); // COMPILE ERROR!
  ```

---

### Avoid null when possible

Mitigate NullPointerExceptions by adopting defensive coding patterns:

- **Rules**:
  - **Return Empty Collections**: Never return `null` for list/map/array return types. Return `Collections.emptyList()`, `Collections.emptyMap()`, or empty arrays.
  - **Use Optional**: For optional single values, return `Optional<T>` to force the caller to handle absence.
  - **Validate Arguments**: Use `Objects.requireNonNull()` or libraries like `@NonNull` annotations.
- **Runnable Example**:
  ```java
  // BAD: Caller must remember to do null check or risk NPE
  public List<String> getRoles() {
      return null; 
  }

  // GOOD: Safe to iterate directly without null checks
  public List<String> getRoles() {
      return Collections.emptyList();
  }
  ```

---

### Write testable code

Testable code is modular, decoupled, and easy to run in isolation inside unit tests.

- **Rules**:
  - **Avoid Hardcoded Collaborators**: Do not instantiate dependencies using `new` inside methods. Use Dependency Injection (DI) to pass collaborators via constructors.
  - **Avoid Static Calls**: Avoid direct calls to static environment methods like `System.currentTimeMillis()` or `Database.query()`. Wrap them in services that can be mocked.
- **Runnable Example**:
  ```java
  // BAD: Coupled to clock. Cannot write a deterministic test for past/future.
  class Order {
      public boolean isExpired() {
          return System.currentTimeMillis() > expirationTime;
      }
  }

  // GOOD: Inject a Clock instance. Allows mocking specific times in tests.
  class Order {
      private final Clock clock;

      public Order(Clock clock) {
          this.clock = clock;
      }

      public boolean isExpired() {
          return clock.millis() > expirationTime;
      }
  }
  ```

---

### Separate class/method responsibilities

A class or method should adhere to the Single Responsibility Principle (SRP) — it should have exactly one reason to change.

- **Rule**: Avoid "God classes" that mix database access, business calculations, input parsing, and logging. Split them into specialized services.
- **Example**: Create a `UserRepository` for DB operations, a `UserService` for business logic, and a `UserSerializer` for JSON conversion.

---

### Immutability when appropriate

Immutable objects are objects whose state cannot be changed after construction. They are inherently thread-safe, safe to share, and simple to reason about.

- **Rules**:
  - Declare fields `private` and `final`.
  - Do not provide setters.
  - Make class `final` (so it cannot be overridden).
  - For collection fields, return unmodifiable views (using `List.copyOf()` or `Collections.unmodifiableList()`) or perform defensive copying in constructor and accessors.
- **Runnable Example**:
  ```java
  public final class ImmutableUser {
      private final String username;
      private final List<String> roles;

      public ImmutableUser(String username, List<String> roles) {
          this.username = username;
          // Defensive copy to prevent caller modifying the passed list
          this.roles = List.copyOf(roles); 
      }

      public String getUsername() { return username; }
      public List<String> getRoles() { return roles; } // Returns unmodifiable view
  }
  ```
