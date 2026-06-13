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
      public void checkout(int amount, PaymentStrategy strategy) {
          strategy.pay(amount);
      }
  }
  ```
