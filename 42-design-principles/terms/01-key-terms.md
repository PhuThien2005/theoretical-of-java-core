# Basic Design Principles Often Paired With Java Core Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## SOLID

SOLID is a set of five object-oriented design principles (Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, and Dependency Inversion) to build maintainable, extensible software.

Why it matters: SOLID principles prevent codebase rigidity, fragility, and immobility. They guide developers in structuring classes and boundaries so that adding new features doesn't break existing, working code.

Common confusion: Developers often treat SOLID as a rigid, compiler-enforced rulebook, leading to over-engineering (e.g., creating interfaces for every class when only one implementation exists). They are heuristics, not laws.

Small example: When designing a system, rather than creating a massive monolithic class that handles everything, split it into smaller classes communicating via interfaces according to SOLID guidelines.

## Single Responsibility

The Single Responsibility Principle (SRP) dictates that a class should have one, and only one, reason to change. It means a class must focus on a single piece of functionality.

Why it matters: If a class has multiple responsibilities (e.g., database query parsing and HTML rendering), changes to one responsibility can break the code handling the other, tightly coupling unrelated components.

Common confusion: Believing "one reason to change" means a class should only have a single method. A class can have multiple methods as long as they are all highly cohesive and serve the same core responsibility.

Small example: Separate database storage from user serialization:
```java
class User { String username; }
class UserRepository { void save(User u) { /* database logic */ } }
class UserSerializer { String toJson(User u) { return "{...}"; } }
```

## Open Closed

The Open/Closed Principle (OCP) states that software entities (classes, modules, functions) should be open for extension but closed for modification.

Why it matters: It minimizes the risk of introducing regression bugs into verified, tested source code. Instead of editing tested classes to add behavior, you write new classes that extend or implement them.

Common confusion: Believing OCP means you can never edit a class once written. Bug fixes, refactoring, and code improvements are encouraged; OCP specifically targets preventing class modifications to support new features.

Small example: Relying on polymorphism rather than if-else type checks:
```java
interface Shape { double getArea(); }
class Circle implements Shape { 
    private double r;
    public double getArea() { return Math.PI * r * r; } 
}
class Square implements Shape { 
    private double s;
    public double getArea() { return s * s; } 
}
```

## Liskov Substitution

The Liskov Substitution Principle (LSP) states that subclasses must be substitutable for their parent classes or interfaces without breaking the correctness of the program.

Why it matters: LSP preserves type safety and behavioral expectations at runtime. When client code uses a parent reference, it assumes the subtype adheres to the parent's behavioral contract (invariants, pre/postconditions).

Common confusion: Thinking inheritance is purely about reusing code. Inheritance is also a behavioral contract. Subclasses must not strengthen preconditions (e.g., throwing new exceptions on parent inputs) or weaken postconditions.

Small example: A subclass of a read-only list throwing `UnsupportedOperationException` on a read operation or returning null when the parent contract guarantees a list violates LSP.

## Interface Segregation

The Interface Segregation Principle (ISP) states that clients should not be forced to depend on methods they do not use, advocating for multiple small, specific interfaces over a single bloated one.

Why it matters: Fat interfaces couple unrelated clients together. If a bloated interface changes a method signature, all classes implementing that interface must be recompiled and re-linked, even if they don't use that method.

Common confusion: Segregating interfaces to the point of "interface explosion," where every single method has its own interface. Interfaces should be split based on actual client usage boundaries.

Small example: Splitting a multi-function machine interface into smaller interfaces:
```java
interface Printer { void print(); }
interface Scanner { void scan(); }
class SimplePrinter implements Printer {
    public void print() { System.out.println("Printing..."); }
}
```

## Dependency Inversion

The Dependency Inversion Principle (DIP) states that high-level modules should not depend on low-level modules; both should depend on abstractions (interfaces). Abstractions should not depend on details; details should depend on abstractions.

Why it matters: It decouples core business logic from low-level details (databases, APIs). Changes to database engines or third-party libraries won't ripple up to modify or break high-level logic.

Common confusion: Confusing Dependency Inversion (DIP) with Dependency Injection (DI). DIP is the conceptual design principle (programming to abstractions); DI is the implementation technique used to pass in concrete instances.

Small example: An order processor depending on a `PaymentGateway` interface rather than a concrete `StripeGateway` class:
```java
interface PaymentGateway { void pay(double amount); }
class OrderProcessor {
    private final PaymentGateway gateway;
    public OrderProcessor(PaymentGateway gateway) { this.gateway = gateway; }
}
```

## DRY

DRY ("Don't Repeat Yourself") states that every piece of system knowledge or logic must have a single, unambiguous, authoritative representation within the codebase.

Why it matters: Duplicate logic makes code maintenance a nightmare. If a business rule or validation changes, you have to find and modify every duplicate copy, risking inconsistencies and bugs.

Common confusion: Applying DRY to two chunks of code that look identical today but serve completely different business concepts. If their requirements diverge tomorrow, over-DRYing creates complex conditional branching.

Small example: Extracting common verification logic into a utility method:
```java
public class ValidationUtils {
    public static void checkEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}
```

## KISS

KISS ("Keep It Simple, Stupid") is a design principle stating that code and systems should be designed as simply and directly as possible, avoiding over-engineering.

Why it matters: Simple code is easier to read, verify, debug, and maintain. Complex designs with deep hierarchies or speculative patterns introduce hidden bugs and raise cognitive load.

Common confusion: Conflating simplicity with crude or lazy code. Writing simple, clean code often requires more discipline, refactoring, and thought than writing complex, nested code.

Small example: Returning boolean expressions directly rather than wrapping them in unnecessary conditionals or Optionals:
```java
// simple and fast
return value > 0;
```

## YAGNI

YAGNI ("You Aren't Gonna Need It") is a software development principle stating that you should not implement speculative features or infrastructure until they are actually needed.

Why it matters: It saves developer time, avoids code bloat, reduces the testing surface area, and keeps the design flexible. Speculative code often turns out to be wrong for future requirements.

Common confusion: Confusing YAGNI with skipping basic architectural planning. You should still write clean, modular code, but you should not write code for unrequested, hypothetical features.

Small example: Do not build an elaborate caching layer, auditing framework, or microservice infrastructure on day one based on the assumption that the application will scale to millions of users.

## coupling

Coupling measures the level of interdependence between different software classes or modules. The goal is loose coupling.

Why it matters: High coupling makes the system fragile and rigid. Modifying a class or database schema will ripple through the codebase, breaking unrelated features and requiring massive refactoring.

Common confusion: Thinking loose coupling means zero relationships between classes. Classes must interact. Loose coupling means they interact through stable, public interfaces while hiding their internal implementations.

Small example: Declaring dependencies as interface types and injecting them, rather than hard-coding class instantiation internally.

## cohesion

Cohesion measures how focused, related, and unified the elements, fields, and methods within a single class or module are. The goal is high cohesion.

Why it matters: High cohesion makes classes easier to understand, test, reuse, and debug. When a class has low cohesion (does unrelated things), it accumulates bloat, making it difficult to maintain.

Common confusion: Thinking high cohesion means having as few methods as possible. A class can have many methods if they all coordinate to perform a single logical responsibility.

Small example: A class `EmailService` that only handles formatting and sending emails is highly cohesive. A class `UserManager` that hashes passwords, queries databases, formats HTML, and sends emails has low cohesion.

## dependency injection

Dependency Injection (DI) is a technique where an object's dependencies are provided by external callers rather than being created internally by the object itself.

Why it matters: It decouples object creation from object behavior. It makes unit testing trivial because mock or stub dependencies can be easily passed to the constructor.

Common confusion: Thinking Dependency Injection requires a framework like Spring or Guice. DI is a simple programming pattern (e.g., passing arguments to a constructor) that can be done entirely in plain Java.

Small example: Passing an `Engine` to the `Car` constructor:
```java
public class Car {
    private final Engine engine;
    public Car(Engine engine) { this.engine = engine; }
}
```
