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

---

## Why Single Responsibility Promotes High Cohesion

In the JVM, classes are the fundamental unit of deployment, class loading, and execution. When a class has multiple responsibilities, it accumulates unrelated instance variables and methods, which decreases its cohesion. A highly cohesive class has fields and methods that are conceptually and functionally unified, meaning the class's methods consistently operate on its fields. When multiple responsibilities are packed into a single class, a change in one domain's requirements forces the recompilation and redeployment of the entire class, affecting unrelated domains. This can cause classpath dependency bloat and increase the risk of side effects, where modifications to one feature inadvertently break another due to shared state. By enforcing the Single Responsibility Principle, we ensure that a class is loaded by the ClassLoader as a single, isolated unit of change with a singular purpose, thereby reducing class coupling and compile-time dependencies.

### Mental Model
```text
Low Cohesion (Fat Class):
+------------------------------------------+
|                 UserClass                |
|  [data] name, email, hashedPassword      |
|  [methods] saveToDb(), sendEmail()       |
+------------------------------------------+
                  /         \
         Change in DB      Change in Email API
                  \         /
             Recompile entire class!

High Cohesion (SRP Split):
+------------------+     +------------------+
|    UserEntity    |     |   EmailService   |
| [data] name, etc |---->|  [methods]       |
+------------------+     |  sendEmail()     |
                         +------------------+
```

### Code Example
```java
// SRP Compliant Design
class User {
    private final String username;
    private final String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getEmail() { return email; }
    public String getUsername() { return username; }
}

class EmailSender {
    public void sendWelcomeEmail(User user) {
        System.out.println("Email sent to " + user.getEmail());
    }
}

public class Main {
    public static void main(String[] args) {
        User user = new User("alice", "alice@example.com");
        EmailSender sender = new EmailSender();
        sender.sendWelcomeEmail(user);
        // Output: Email sent to alice@example.com
    }
}
```

### Cause-Effect Chain
Single responsibility for a class &rarr; All methods focus on a single task &rarr; Highly related fields and methods (High Cohesion) &rarr; Modifying one requirement only changes its corresponding class &rarr; Rest of the system is unaffected &rarr; Loose coupling is preserved.

---

## Why Open/Closed Principle Protects Existing Code

The Open/Closed Principle (OCP) leverages Java's object-oriented mechanisms of polymorphism and dynamic binding to enable software extensibility. When behavior is extended through subclassing or implementing interfaces, the JVM's `invokevirtual` and `invokeinterface` instructions perform dynamic method dispatch at runtime, resolving the method call based on the actual object type rather than the reference type. Modifying existing compiled classes directly is highly risky because it requires editing verified, tested source code, which can introduce regression bugs and break existing binary compatibility. By designing systems using abstract classes or interface contracts, the base logic remains untouched and closed to modification, while new features are added as new classes (open to extension). This compile-time decoupling ensures that existing bytecode does not need to be recompiled or re-verified by the JVM, dramatically stabilizing enterprise software deployments.

### Mental Model
```text
Without OCP (Modifying Existing Class):
Client ---> [ PaymentProcessor ]  <-- (Modifying this class to add new methods)
             (Risk of breaking existing Visa processing!)

With OCP (Extending via Interface):
Client ---> [ PaymentProcessor (Interface) ]
                   ^                  ^
                   |                  |
           [ VisaProcessor ]   [ PayPalProcessor ] <-- New class, zero risk to Visa!
```

### Code Example
```java
interface Payment {
    void process();
}

class VisaPayment implements Payment {
    public void process() {
        System.out.println("Visa payment processed.");
    }
}

class PayPalPayment implements Payment {
    public void process() {
        System.out.println("PayPal payment processed.");
    }
}

class PaymentService {
    public void executePayment(Payment payment) {
        payment.process();
    }
}

public class Main {
    public static void main(String[] args) {
        PaymentService service = new PaymentService();
        service.executePayment(new VisaPayment());
        service.executePayment(new PayPalPayment());
        // Output:
        // Visa payment processed.
        // PayPal payment processed.
    }
}
```

### Cause-Effect Chain
Program depends on interfaces &rarr; New features implemented by adding new classes &rarr; No modification to existing classes &rarr; Pre-existing classes remain compiled and untested &rarr; Regression risks avoided &rarr; System maintains stability.

---

## Why Liskov Substitution Principle Enforces Behavioral Contracts

Subtype polymorphism in Java allows a reference variable of a parent class or interface type to refer to any subclass instance. The Liskov Substitution Principle (LSP) ensures that this substitution is safe by demanding that subclasses adhere to the behavioral contract defined by the parent class. In Java, while the compiler enforces static type safety (such as method signatures and return type covariance), it cannot enforce behavioral invariants at runtime. Subclasses violate LSP when they strengthen preconditions (for instance, throwing a new checked exception or requiring input parameters to meet tighter constraints) or weaken postconditions (such as returning a null reference when the parent contract guarantees a non-null object, or modifying inherited state in a way that breaks parent invariants). When these runtime behavioral contracts are violated, polymorphism fails because client code designed to work with the parent class behaves unpredictably or throws runtime exceptions when encountering the subclass.

### Mental Model
```text
Parent Class (Contract: returns positive integer)
    [ MathHelper ] -> getValue() returns >= 1

Subclass A (LSP compliant)
    [ SecureHelper ] -> getValue() returns >= 1 (Complies with contract)

Subclass B (LSP Violator)
    [ BadHelper ] -> getValue() returns 0 or negative (Violates contract!)
    Client expecting positive integer crashes due to division by zero!
```

### Code Example
```java
class Rectangle {
    protected int width;
    protected int height;

    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public int getArea() { return width * height; }
}

class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width;
    }

    @Override
    public void setHeight(int height) {
        this.width = height;
        this.height = height;
    }
}

public class Main {
    public static void verifyRectangle(Rectangle r) {
        r.setWidth(5);
        r.setHeight(10);
        System.out.println("Expected Area: 50, Actual: " + r.getArea());
    }

    public static void main(String[] args) {
        verifyRectangle(new Rectangle()); // Output: Expected Area: 50, Actual: 50
        verifyRectangle(new Square());    // Output: Expected Area: 50, Actual: 100 (LSP Violation!)
    }
}
```

### Cause-Effect Chain
Subclass overrides parent method &rarr; Subclass strengthens preconditions or weakens postconditions &rarr; Client code holds parent reference &rarr; Client code executes subclass method via dynamic dispatch &rarr; Subclass violates parent behavioral assumptions &rarr; Runtime crash or incorrect logic occurs.

---

## Why Interface Segregation Prevents Fat Interface Coupling

In the JVM, when a class implements an interface, it must provide concrete implementations for all non-default methods defined by that interface, or else be declared abstract. A "fat" interface containing methods for distinct, unrelated clients forces every implementing class to depend on and implement methods it does not require, often resulting in empty or dummy method bodies that throw `UnsupportedOperationException`. This design couples unrelated components together at compile-time: if a method signature in a fat interface changes, all implementing classes must be recompiled and re-linked by the JVM, even if they never invoked or used that method. By segregating a bloated interface into small, client-specific interfaces, we minimize the size of the interface table (`itable`) references resolved during `invokeinterface` calls. Consequently, clients only depend on the specific methods they actually execute, which eliminates unnecessary compile-time dependencies, classloading overhead, and runtime code fragility.

### Mental Model
```text
Fat Interface (Couples unrelated clients):
+-------------------------------+
|        MultiFunction          |
|  print(), scan(), fax()       |
+-------------------------------+
        ^               ^
        |               |
  SimplePrinter     SuperOfficeJet (Needs all)
  (forced to throw UnsupportedOperationException on fax()!)

Segregated Interfaces (Lean, client-specific):
+-------------+   +-------------+
|   Printer   |   |   Scanner   |
|   print()   |   |   scan()    |
+-------------+   +-------------+
       ^                 ^
       |                 |
       +--- SimplePrinter+
```

### Code Example
```java
interface Printer {
    void print();
}

interface Scanner {
    void scan();
}

class BasicPrinter implements Printer {
    public void print() {
        System.out.println("Printing document...");
    }
}

class MultiFunctionPrinter implements Printer, Scanner {
    public void print() {
        System.out.println("Printing document...");
    }
    public void scan() {
        System.out.println("Scanning document...");
    }
}

public class Main {
    public static void main(String[] args) {
        Printer printer = new BasicPrinter();
        printer.print();
        
        MultiFunctionPrinter mfp = new MultiFunctionPrinter();
        mfp.print();
        mfp.scan();
        // Output:
        // Printing document...
        // Printing document...
        // Scanning document...
    }
}
```

### Cause-Effect Chain
Fat interface contains unrelated methods &rarr; Implementing classes forced to write empty/dummy implementations &rarr; Modification of unused method signature &rarr; Recompilation and re-linking of all implementing classes &rarr; Increased compile-time coupling and risk of runtime exceptions.

---

## Why Dependency Inversion Decouples Modules

The Dependency Inversion Principle (DIP) reverses the traditional top-down dependency flow of software systems by declaring that high-level modules should not depend on low-level concrete implementations. Under a direct dependency model, compile-time relationships are bound directly to concrete classes, meaning that high-level classes cannot be compiled or tested independently of low-level modules like databases or external APIs. By introducing interfaces as abstractions between these layers, both high-level and low-level modules depend on the abstract interface. At compile time, the high-level class relies entirely on the interface type, which is verified by Java's static type checker. At runtime, concrete implementations are injected into the high-level class using Dependency Injection (DI) through constructors or setters, and the JVM resolves the dynamic method calls via polymorphism. This decouples the compile-time relationship, enabling easy mock substitution for unit testing and allowing developers to swap low-level infrastructure classes without changing the core business logic.

### Mental Model
```text
Direct Dependency (Tight Coupling):
[ High-Level Service ] ---> [ Concrete MySQLDatabase ]
(Service is hard-coded to MySQL; cannot test without database running!)

Dependency Inverted (Loose Coupling):
[ High-Level Service ] ---> [ Database (Interface) ]
                                   ^
                                   |
                       [ Concrete MySQLDatabase ] or [ MockDatabase ]
```

### Code Example
```java
interface Database {
    void save(String data);
}

class MySqlDatabase implements Database {
    public void save(String data) {
        System.out.println("Saved to MySQL: " + data);
    }
}

class MockDatabase implements Database {
    public void save(String data) {
        System.out.println("Saved to Mock: " + data);
    }
}

class OrderProcessor {
    private final Database database;

    public OrderProcessor(Database database) {
        this.database = database;
    }

    public void process(String orderId) {
        database.save(orderId);
    }
}

public class Main {
    public static void main(String[] args) {
        OrderProcessor production = new OrderProcessor(new MySqlDatabase());
        production.process("order-100");

        OrderProcessor test = new OrderProcessor(new MockDatabase());
        test.process("order-100");
        // Output:
        // Saved to MySQL: order-100
        // Saved to Mock: order-100
    }
}
```

### Cause-Effect Chain
High-level module references abstract interface &rarr; Low-level concrete implementations implement same interface &rarr; Dependency Injection provides concrete instance at runtime &rarr; Compile-time reference remains bound to the abstraction &rarr; Low-level changes do not require recompilation of high-level code &rarr; System components are loosely coupled and testable.

## Reference Links

- https://docs.oracle.com/javase/tutorial/java/concepts/
- https://docs.oracle.com/javase/specs/jls/se21/html/index.html
