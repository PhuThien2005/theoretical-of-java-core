# Basic Design Patterns Commonly Seen in Java Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## Singleton

Singleton restricts a class to exactly one instance and provides a global point of access to that instance.

- **Why it matters**: It is useful for managing shared resources (like database connection pools, cache managers, or system configuration settings) where having multiple instances would lead to inconsistent state, excessive memory usage, or resource conflicts.
- **Common confusion**: Developers often implement lazy-loaded Singletons without proper synchronization or the `volatile` keyword, which leads to race conditions. Additionally, Singletons introduce tight coupling and make unit testing difficult because they persist state across tests.
- **Small example**:
  ```java
  public enum DatabaseConnectionPool {
      INSTANCE;
      public void connect() {
          System.out.println("Connected to Database.");
      }
  }
  ```

## Double-Checked Locking (DCL)

A concurrent design pattern designed to reduce overhead by acquiring a lock only when the initialization is actually required.

- **Why it matters**: It optimizes lazy initialization in multi-threaded environments. It performs a check without locking, and only locks if the instance is null, followed by a second check inside the synchronized block. This ensures that threads only pay the synchronization cost once.
- **Common confusion**: Forgetting to declare the instance variable as `volatile`. Without `volatile`, instruction reordering by the compiler or CPU can cause a thread to read a non-null reference pointing to a partially constructed object, causing unpredictable errors.
- **Small example**:
  ```java
  public class DclService {
      private static volatile DclService instance;
      public static DclService getInstance() {
          if (instance == null) {
              synchronized (DclService.class) {
                  if (instance == null) {
                      instance = new DclService();
                  }
              }
          }
          return instance;
      }
  }
  ```

## Bill Pugh Singleton

An implementation of the Singleton pattern that uses a nested static helper class to achieve thread-safe lazy initialization.

- **Why it matters**: It achieves the same goal as double-checked locking but with simpler code and zero synchronization overhead. It relies on the JVM class loader guarantees: a nested class is not loaded until it is explicitly referenced, and class loading is thread-safe.
- **Common confusion**: Thinking that loading the outer class automatically loads the inner static helper class. In Java, nested static classes are loaded lazily only when referenced (e.g. calling `SingletonHolder.INSTANCE`), not when the outer class is loaded.
- **Small example**:
  ```java
  public class BillPughSingleton {
      private BillPughSingleton() {}
      private static class SingletonHolder {
          private static final BillPughSingleton INSTANCE = new BillPughSingleton();
      }
      public static BillPughSingleton getInstance() {
          return SingletonHolder.INSTANCE;
      }
  }
  ```

## Factory Method

Defines an interface for creating an object, but lets subclasses decide which concrete class to instantiate.

- **Why it matters**: It adheres to the Dependency Inversion Principle by decoupling client code from concrete implementations. Clients interact with abstract product interfaces, allowing new products to be introduced without modifying client code.
- **Common confusion**: Confusing Factory Method with a Simple Factory class. A Simple Factory is a single helper class with static methods and conditionals (e.g., `switch` statements), whereas Factory Method relies on subclassing and polymorphic dispatch.
- **Small example**:
  ```java
  abstract class DocumentCreator {
      public void openDocument() {
          Document doc = createDocument();
          doc.open();
      }
      protected abstract Document createDocument();
  }
  ```

## Builder

Separates the construction of a complex object from its representation, allowing step-by-step construction of fields.

- **Why it matters**: It solves the telescoping constructor anti-pattern (constructors with many optional arguments). It offers a readable, fluent API, prevents parameter-ordering mistakes, and allows the final object to be immutable.
- **Common confusion**: Writing a Builder that returns a mutable object, which defeats the purpose of safe creation, or failing to validate parameters inside the `build()` method before instantiation.
- **Small example**:
  ```java
  User user = new User.Builder()
                      .username("jdoe")
                      .email("jdoe@example.com")
                      .age(28)
                      .build();
  ```

## Adapter

A structural design pattern that allows objects with incompatible interfaces to collaborate.

- **Why it matters**: It lets you reuse existing classes or third-party libraries that do not match the interface expected by your client code, avoiding rewrite or modification of core codebase.
- **Common confusion**: Confusing Adapter with Decorator. An Adapter changes the interface of an object to make it compatible, whereas a Decorator maintains the same interface to add functionality dynamically.
- **Small example**:
  ```java
  public class UsbToTypeCAdapter implements TypeC {
      private final LegacyUsbCable usbCable;
      public UsbToTypeCAdapter(LegacyUsbCable cable) { this.usbCable = cable; }
      public void connect() { usbCable.plugUsb(); }
  }
  ```

## Decorator

Dynamically attaches additional responsibilities and behaviors to an object without altering its structure.

- **Why it matters**: It provides a flexible alternative to subclassing for extending functionality. You can stack decorators recursively to combine multiple behaviors at runtime (e.g., wrapping Java's `BufferedInputStream` around a `FileInputStream`).
- **Common confusion**: Creating too many decorator layers, which can make the code hard to debug, or implementing decorators that violate the single responsibility principle.
- **Small example**:
  ```java
  Coffee sugarMilkCoffee = new SugarDecorator(new MilkDecorator(new SimpleCoffee()));
  ```

## Strategy

Defines a family of interchangeable algorithms and encapsulates each one inside a separate class.

- **Why it matters**: It eliminates massive conditional code structures (like `if-else` or `switch` blocks) by delegating algorithmic choices to polymorphism. The algorithm can be selected or changed at runtime.
- **Common confusion**: Confusing Strategy with State. In Strategy, the client typically selects the algorithm configuration manually, whereas in State, the context transitions automatically from one state class to another.
- **Small example**:
  ```java
  paymentProcessor.setStrategy(new PayPalPayment());
  paymentProcessor.pay(150);
  ```

## Observer

Defines a subscription mechanism to notify multiple objects (observers) about any events that happen to the object they are observing (subject).

- **Why it matters**: It decouples the subject from concrete observers, enabling dynamic push notifications and event-driven architectures. It is heavily used in event listeners and reactive programming.
- **Common confusion**: Believing that observers are always notified asynchronously. In standard GoF patterns, notification is a synchronous loop. Observers should defer heavy or blocking tasks to a background thread pool to avoid blocking the subject.
- **Small example**:
  ```java
  subject.addObserver(event -> System.out.println("Received: " + event));
  ```

## Repository

Mediates between domain models and data persistence layers using a collection-like interface for accessing domain entities.

- **Why it matters**: It decouples business logic from persistence databases or APIs, treating the data store as an in-memory collection. This makes it easier to write unit tests using mock repositories.
- **Common confusion**: Confusing Repository with DAO (Data Access Object). A DAO maps closely to database tables/operations, whereas a Repository works at the domain level with Aggregate Roots, often using multiple DAOs under the hood.
- **Small example**:
  ```java
  public interface OrderRepository {
      void save(Order order);
      Order findById(String id);
  }
  ```
