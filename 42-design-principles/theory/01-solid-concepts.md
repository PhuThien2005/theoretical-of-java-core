# Basic Design Principles Often Paired With Java Core - Part 1

## Learning Goal

This file covers the fundamental **Design Principles** paired with Java Core development (SOLID, DRY, KISS, YAGNI, coupling, cohesion, and Clean Code). Study each concept as a practical Java rule.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `SOLID` | Five core object-oriented design principles to build maintainable, extensible software. |
| `DRY` | "Don't Repeat Yourself" – avoiding redundancy in code and system knowledge. |
| `KISS` | "Keep It Simple, Stupid" – choosing simple, readable structures over complex abstractions. |
| `YAGNI` | "You Aren't Gonna Need It" – avoiding implementing premature features until they are needed. |
| `Composition over inheritance` | Reusing behavior by enclosing instance variables rather than extending classes. |
| `Coupling` | Interdependence level between classes; the goal is loose coupling. |
| `Cohesion` | The level of focus within a class on a single task; the goal is high cohesion. |
| `Basic Dependency Injection` | Injecting external dependencies via constructor/method parameters to facilitate testing. |
| `Defensive programming` | Validating preconditions, inputs, and state assumptions to write crash-resistant code. |
| `Basic Clean Code` | Writing readable, formatted, and easily refactorable Java code. |

---

## Detailed Notes

### SOLID

SOLID represents five core principles of object-oriented design:

1. **S**ingle Responsibility Principle (SRP): A class should have only one reason to change.
2. **O**pen/Closed Principle (OCP): Software entities should be open for extension but closed for modification.
3. **L**iskov Substitution Principle (LSP): Subtypes must be substitutable for their base types without altering correctness.
4. **I**nterface Segregation Principle (ISP): Clients should not be forced to depend on methods they do not use (split fat interfaces).
5. **D**ependency Inversion Principle (DIP): Depend on abstractions (interfaces), not on concrete classes.

- **Runnable Example (Dependency Inversion)**:
  ```java
  public interface MessageSender {
      void send(String msg);
  }

  public class EmailSender implements MessageSender {
      public void send(String msg) { /* sends email */ }
  }

  public class NotificationService {
      private final MessageSender sender; // Depends on interface abstraction

      public NotificationService(MessageSender sender) { // Injected via constructor
          this.sender = sender;
      }
  }
  ```

---

### DRY

"Don't Repeat Yourself" states that every piece of system logic must have a single, unambiguous, authoritative representation within the codebase.

- **Runnable Example**:
  ```java
  // BAD: Copy-pasting input verification logic in multiple controllers
  
  // GOOD: Extract validation to a unified static validator utility
  public final class InputValidator {
      public static void validateEmail(String email) {
          if (email == null || !email.contains("@")) {
              throw new IllegalArgumentException("Malformed email address");
          }
      }
  }
  ```

- **Common Mistake**: **Over-DRYing**. Sharing code between two business domains that happen to look identical today, but serve completely different business needs. If their requirements diverge tomorrow, you will end up with highly complex classes filled with conditional flags. Duplicate *code* is better than the wrong *abstraction*.

---

### KISS

"Keep It Simple, Stupid" commands that code should be written as simply and directly as possible. Avoid over-engineering with premature design patterns, deep hierarchies, or complex reflection when simple, readable logic works.

- **Runnable Example**:
  ```java
  // BAD: Over-engineered check
  public boolean isPositive(int number) {
      return Optional.of(number)
                     .filter(n -> n > 0)
                     .isPresent();
  }

  // GOOD: Simple, direct, and performs better
  public boolean isPositive(int number) {
      return number > 0;
  }
  ```

---

### YAGNI

"You Aren't Gonna Need It" dictates that you should not implement features, utility classes, or extensibility layers based on the assumption that "we might need them later."

- **Tradeoff**: Implementing speculative features wastes developer time, adds bloat to tests, increases maintenance surface area, and limits future flexibility. Only write the code that you need *today*.

---

### Composition over inheritance

Acquire polymorphic behavior and code reuse by grouping instances of helper classes ("has-a") rather than subclassing parent classes ("is-a").

- **Runnable Example**:
  ```java
  public class Engine {
      public void start() {}
  }

  // GOOD: Car encloses Engine to reuse start behavior
  public class Car {
      private final Engine engine = new Engine();

      public void drive() {
          engine.start();
          System.out.println("Driving...");
      }
  }
  ```

---

### Coupling

Coupling measures the degree of interdependence between two classes. The goal is **loose coupling** so that modifying class A does not break class B.

- **Mitigation**: Use interfaces to define boundaries, declare dependencies explicitly via constructor parameters, and hide implementation details behind private modifiers.

---

### Cohesion

Cohesion measures how focused the methods and variables within a class are on a single logical task. The goal is **high cohesion**.

- **Example**:
  - **Low Cohesion**: A utility class `UserHelper` that handles password hashing, database loading, JSON serialization, and sending verification SMS.
  - **High Cohesion**: A `PasswordHasher` class focused entirely on encrypting and verifying string hashes.

---

### Basic Dependency Injection

Classes should receive their required dependencies from the outside (typically via constructor arguments) rather than instantiating them internally.

- **Runnable Example**:
  ```java
  public class OrderService {
      private final PaymentClient paymentClient;

      // Dependency is injected rather than created via "new PaymentClient()"
      public OrderService(PaymentClient paymentClient) {
          this.paymentClient = paymentClient;
      }
  }
  ```

---

### Defensive programming

Defensive programming is the practice of designing code to continue executing or fail safely even when presented with unexpected inputs, system states, or invalid calls.

- **Runnable Example**:
  ```java
  public void registerUser(String username, int age) {
      // Validate inputs early (fail fast)
      Objects.requireNonNull(username, "Username cannot be null");
      if (age < 18) {
          throw new IllegalArgumentException("User must be at least 18 years old");
      }
      // Continue registration
  }
  ```

---

### Basic Clean Code

Clean code is written primarily to be readable and easily understood by other developers.

- **Key Rules**:
  - Functions should be short and do exactly one thing.
  - Limit method indentation depth (e.g. avoid nested loops and `if` checks deeper than 2 levels; return early to keep code flat).
  - Write descriptive names, and do not use comments to explain bad code—rewrite the code to be clear.
