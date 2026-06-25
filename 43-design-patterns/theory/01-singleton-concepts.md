# Basic Design Patterns Commonly Seen in Java - Part 1

## Learning Goal

This file covers a focused slice of creational and structural **Design Patterns** (GoF) widely used in Java. Study each concept as a practical Java rule.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Singleton` | Restricting class instantiation to a single object with global access. |
| `Factory Method` | Delegating object instantiation to subclasses using a factory method signature. |
| `Abstract Factory` | Interface creating families of related objects without specifying concrete classes. |
| `Builder` | Step-by-step construction of complex objects using a fluent API. |
| `Prototype` | Creating new objects by cloning a pre-configured instance. |
| `Adapter` | Unifying incompatible interfaces by wrapping a source class inside an adapter. |
| `Decorator` | Dynamically adding features to an object wrapping instance variables. |
| `Facade` | Providing a simplified API fronting a complex subsystems layer. |
| `Proxy` | Providing a placeholder object to control access, log, or lazy-load target instances. |
| `Strategy` | Encapsulating interchangeable algorithms selected at runtime. |

---

## Detailed Notes

### Singleton

Restricts a class to exactly one instance and provides a global point of access.

- **Runnable Example (Double-Checked Locking)**:
  ```java
  public final class DatabaseConnection {
      // volatile prevents instruction reordering issues during instantiation
      private static volatile DatabaseConnection instance;

      private DatabaseConnection() {
          // Prevent reflection breaking constructor encapsulation
          if (instance != null) {
              throw new IllegalStateException("Instance already exists!");
          }
      }

      public static DatabaseConnection getInstance() {
          if (instance == null) { // First check (no synchronization overhead)
              synchronized (DatabaseConnection.class) {
                  if (instance == null) { // Second check
                      instance = new DatabaseConnection();
                  }
              }
          }
          return instance;
      }
  }
  ```

- **Common Mistake / Failure Mode**:
  - **Missing Volatile**: Without `volatile`, the compiler/JVM can reorder instructions (allocating memory -> publishing reference -> running constructor). Another thread might read a half-initialized instance.
  - **Enum Singleton**: The absolute safest way to implement a Singleton is utilizing a single-element enum, which handles serialization and reflection attacks natively:
    ```java
    public enum SafeSingleton {
        INSTANCE;
        public void performAction() {}
    }
    ```

---

### Factory Method

Defines an interface for creating an object but lets subclasses decide which class to instantiate.

- **Runnable Example**:
  ```java
  public abstract class Dialog {
      public void renderWindow() {
          Button okButton = createButton();
          okButton.render();
      }
      // Subclasses override this factory method to supply different buttons
      protected abstract Button createButton();
  }

  public class WindowsDialog extends Dialog {
      protected Button createButton() { return new WindowsButton(); }
  }
  ```

---

### Abstract Factory

Provides an interface for creating families of related or dependent objects without specifying their concrete classes.

- **Runnable Example**:
  ```java
  public interface GUIFactory {
      Button createButton();
      Checkbox createCheckbox();
  }

  public class MacFactory implements GUIFactory {
      public Button createButton() { return new MacButton(); }
      public Checkbox createCheckbox() { return new MacCheckbox(); }
  }
  ```

---

### Builder

Separates the construction of a complex object from its representation, allowing step-by-step assembly of fields (especially useful when class contains many optional parameters).

- **Runnable Example**:
  ```java
  public class User {
      private final String name; // Required
      private final int age;     // Optional

      private User(Builder builder) {
          this.name = builder.name;
          this.age = builder.age;
      }

      public static class Builder {
          private final String name;
          private int age;

          public Builder(String name) { this.name = name; }
          public Builder age(int age) { this.age = age; return this; }
          public User build() { return new User(this); }
      }
  }

  // Usage:
  User u = new User.Builder("Bob").age(30).build();
  ```

---

### Prototype

Creates new objects by copying (cloning) an existing instance (prototype) instead of creating them via `new` from scratch.

- **Runnable Example**:
  ```java
  public interface Prototype {
      Prototype clone();
  }

  public class Cell implements Prototype {
      private String color;

      public Cell(Cell target) { if (target != null) this.color = target.color; }
      public Cell clone() { return new Cell(this); }
  }
  ```

---

### Adapter

Converts the interface of a class into another interface clients expect, enabling classes with incompatible interfaces to work together.

- **Runnable Example**:
  ```java
  public interface TypeCInput { void connectTypeC(); }
  
  public class LegacyUsbCable { void plugUsb() {} }

  // Adapter wraps LegacyUsbCable to expose TypeCInput interface
  public class UsbToTypeCAdapter implements TypeCInput {
      private final LegacyUsbCable usbCable;

      public UsbToTypeCAdapter(LegacyUsbCable cable) { this.usbCable = cable; }
      public void connectTypeC() { usbCable.plugUsb(); }
  }
  ```

---

### Decorator

Dynamically attaches additional responsibilities to an object. Decorators provide a flexible alternative to subclassing for extending functionality.

- **Runnable Example**:
  ```java
  public interface Coffee { double getCost(); }

  public class SimpleCoffee implements Coffee { public double getCost() { return 2.0; } }

  public class MilkDecorator implements Coffee {
      private final Coffee coffee;

      public MilkDecorator(Coffee coffee) { this.coffee = coffee; }
      public double getCost() { return coffee.getCost() + 0.5; }
  }
  ```

---

### Facade

Provides a unified, simplified interface to a set of interfaces in a complex subsystem.

- **Example**: Creating a `HomeTheaterFacade` that abstracts calls to `Amplifier.on()`, `DvdPlayer.play(movie)`, `Projector.widescreenMode()` into a single method: `facade.watchMovie("Inception")`.

---

### Proxy

Provides a surrogate or placeholder for another object to control access to it (lazy loading, authorization checking, logging, caching).

- **Runnable Example**:
  ```java
  public interface Image { void display(); }

  public class RealImage implements Image {
      public RealImage(String filename) { loadFromDisk(filename); }
      public void display() {}
      private void loadFromDisk(String filename) {}
  }

  // Proxy lazy loads RealImage only when display() is actually called
  public class ProxyImage implements Image {
      private RealImage realImage;
      private final String filename;

      public ProxyImage(String filename) { this.filename = filename; }
      public void display() {
          if (realImage == null) realImage = new RealImage(filename);
          realImage.display();
      }
  }
  ```

---

### Strategy

Defines a family of algorithms, encapsulates each one, and makes them interchangeable at runtime.

- **Runnable Example**:
  ```java
  public interface PaymentStrategy { void pay(int amount); }

  public class CreditCardPayment implements PaymentStrategy { public void pay(int amount) {} }
  public class PayPalPayment implements PaymentStrategy { public void pay(int amount) {} }

  public class ShoppingCart {
      public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        cart.checkout(100, new CreditCardPayment());
    }
}

---

## Why Double-Checked Locking Ensures Thread-Safe Singleton

The double-checked locking pattern is designed to minimize synchronization overhead in lazy initialization. Under the Java Memory Model, without double-checking, every call to `getInstance()` would require acquiring a class-level monitor, creating a significant performance bottleneck. In this pattern, the first check bypasses synchronization entirely once the instance is initialized. If the instance is null, the thread acquires the lock and performs a second check to verify that another thread did not initialize the singleton while this thread was waiting for the lock.

Crucially, the instance reference must be declared `volatile` to prevent instruction reordering by the compiler or JVM. The creation of a new object `new Singleton()` is not an atomic operation; it involves memory allocation, initializing the fields (executing the constructor), and writing the memory address to the reference variable. Without `volatile`, the compiler or CPU can reorder the writing of the address before the constructor runs. If this occurs, a concurrent thread executing the first, unsynchronized check will see a non-null reference and return it, leading to the exposure of a partially initialized object.

### Mental Model
```
            Thread A                           Thread B
     1. First check: null               1. First check: non-null!
     2. Acquire Lock                    2. Returns partial object (CRASH)
     3. [Reordered Execution]
        - Allocates memory
        - Writes address to 'instance'
        - (Constructor not run yet!)
```

### Code Example
```java
public final class DclSingleton {
    private static volatile DclSingleton instance;

    private DclSingleton() {}

    public static DclSingleton getInstance() {
        if (instance == null) {
            synchronized (DclSingleton.class) {
                if (instance == null) {
                    instance = new DclSingleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        DclSingleton s1 = DclSingleton.getInstance();
        DclSingleton s2 = DclSingleton.getInstance();
        System.out.println("Same: " + (s1 == s2)); // Output: Same: true
    }
}
```

### Cause-Effect Chain
`volatile` keyword omitted &rarr; Compiler/CPU reorders instance address write before constructor completion &rarr; Concurrent thread reads non-null reference during the first unsynchronized check &rarr; Thread returns a reference to a partially initialized object &rarr; Accessing object fields results in corrupted state or NullPointerException.

---

## Why Bill Pugh Singleton Achieves Thread-Safe Lazy Initialization

The Bill Pugh Singleton pattern relies on the Java Virtual Machine's class loading mechanics to achieve thread-safe lazy initialization. Under the Java Language Specification (JLS), a class is loaded and initialized only when it is first referenced by the execution flow. When the outer singleton class is loaded into memory, its nested helper class `SingletonHolder` remains unloaded. Only when the outer class's `getInstance()` method is explicitly invoked, referencing `SingletonHolder.INSTANCE`, does the JVM trigger the loading and initialization of the inner class.

Crucially, the JVM class loading subsystem inherently serializes class initialization. The JVM uses internal locks during class loading and verification to guarantee that only one thread can load a class at a time. This guarantees that `SingletonHolder` and its static final variable `INSTANCE` are created in a thread-safe manner without developer-facing synchronization. Because the class is loaded exactly once, the reference is safely published, completely bypassing runtime lock overhead on subsequent invocations.

### Mental Model
```
     Outer class loaded -> Nested class not loaded (Lazy)
     getInstance() called -> Trigger Nested class load
     JVM Class Loader locks loading -> Thread-safe execution
     Instance instantiated -> Static final published safely
```

### Code Example
```java
public final class BillPughSingleton {
    private BillPughSingleton() {}

    private static class SingletonHolder {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static void main(String[] args) {
        BillPughSingleton s1 = BillPughSingleton.getInstance();
        BillPughSingleton s2 = BillPughSingleton.getInstance();
        System.out.println("Same: " + (s1 == s2)); // Output: Same: true
    }
}
```

### Cause-Effect Chain
Call `getInstance()` method &rarr; JVM class loading subsystem triggers loading of `SingletonHolder` on demand &rarr; JVM class loader serializes initialization using internal JVM locks &rarr; Static final `INSTANCE` initialized safely and atomically &rarr; Subsequent reads retrieve the fully constructed instance without synchronization overhead.

---

## Why Factory Method Defers Object Instantiation

The Factory Method pattern resolves the tight coupling problem associated with using the `new` operator directly in client code. When a client instantiates a concrete class using `new`, it binds itself to that specific implementation, violating the Dependency Inversion Principle. The JVM must resolve that exact class type at compile time, making dynamic changes or subclass overrides impossible. By defining an abstract method for object creation, the Factory Method delegates the instantiation decision to runtime subclasses.

This design enables polymorphism at the creation level, ensuring the client interacts solely with abstract product interfaces. When subclasses override the factory method, the JVM dispatches the call dynamically using virtual method invocation (vtable lookup). As a result, the core framework remains decoupled from concrete classes, allowing developers to introduce new product types without modifying existing client code.

### Mental Model
```
     Client Code -----> Creator (Abstract) ---calls---> createProduct() [vtable]
                           ^                                |
                           | (Inheritance)                  | (Polymorphism)
                           |                                v
                    ConcreteCreator --------------> returns ConcreteProduct
```

### Code Example
```java
interface Shape { void draw(); }
class Circle implements Shape { public void draw() { System.out.println("Circle"); } }

abstract class ShapeCreator {
    public void render() {
        Shape shape = createShape();
        shape.draw();
    }
    protected abstract Shape createShape(); // Factory Method
}

class CircleCreator extends ShapeCreator {
    protected Shape createShape() { return new Circle(); }
}

public class Main {
    public static void main(String[] args) {
        ShapeCreator creator = new CircleCreator();
        creator.render(); // Output: Circle
    }
}
```

### Cause-Effect Chain
Direct `new` operator instantiation &rarr; Hardcoded dependency on concrete class at compile time &rarr; Switch to abstract Factory Method interface &rarr; Dynamic method dispatch resolves concrete subclasses at runtime &rarr; Client code remains decoupled and open for extension without modification.

---

## Why the Builder Pattern Replaces Telescoping Constructors

The telescoping constructor anti-pattern arises when a class contains numerous optional fields, leading to an exponential growth of overloaded constructors. In Java, this results in bloated constructor chains where each method calls `this(...)` with defaulted parameters, making the API unreadable and error-prone. The compiler cannot detect when a developer accidentally swaps two adjacent arguments of the same type (such as two strings or integers). The Builder pattern solves this by encapsulating construction logic into a dedicated helper class with a fluent API.

Using the Builder pattern, optional attributes are set step-by-step through named method invocations. This ensures that parameters are explicitly identified in client code, enhancing overall readability and preventing parameter-ordering mistakes. Additionally, it enforces object immutability by allowing fields in the target class to be declared `final`, initialized only once through a private constructor. The `build()` method serves as a centralized validation gate, throwing an exception if the combined parameters violate class invariants before the final object is instantiated.

### Mental Model
```
 Telescoping: Client -> Client -> Client (Hard to read, order mistakes likely)
 Builder:    Builder -> setFieldA() -> setFieldB() -> build() -> ImmutableObject
```

### Code Example
```java
public final class Laptop {
    private final String cpu;
    private final int ram;

    private Laptop(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
    }

    public static class Builder {
        private String cpu;
        private int ram;

        public Builder cpu(String cpu) { this.cpu = cpu; return this; }
        public Builder ram(int ram) { this.ram = ram; return this; }
        public Laptop build() {
            if (cpu == null) throw new IllegalStateException("CPU is required");
            return new Laptop(this);
        }
    }

    public static void main(String[] args) {
        Laptop laptop = new Laptop.Builder().cpu("M3").ram(16).build();
        System.out.println("Laptop: CPU=" + laptop.cpu + ", RAM=" + laptop.ram);
        // Output: Laptop: CPU=M3, RAM=16
    }
}
```

### Cause-Effect Chain
Multiple optional constructor parameters &rarr; Overloaded constructors with confusing parameter positions &rarr; Builder class constructed to take parameters step-by-step &rarr; Validation of parameters executed in `build()` method &rarr; Immutable target class safely instantiated with valid parameters.
  }
  ```
