# Abstraction

Abstraction is the process of hiding implementation details and exposing only the essential features of an object. It focuses on **what** an object does rather than **how** it does it.

---

## Abstract Classes and Abstract Methods

An **Abstract Class** is a class declared with the `abstract` keyword. It represents an incomplete class definition designed to be extended by concrete subclasses.

### Characteristics:
- **No Instantiation:** You cannot create an instance directly (`new GraphicObject()` is invalid).
- **Abstract Methods:** Methods declared without a body (ending with a semicolon `;`). They act as placeholders forcing subclasses to override them.
- **Instance State:** Can contain instance fields, constructors, static variables, blocks, and concrete methods.
- **Subclass Obligation:** Any concrete subclass extending an abstract class must provide implementations for all inherited abstract methods, or the subclass itself must be marked `abstract`.

```java
abstract class Vehicle {
    private String brand;

    Vehicle(String brand) { this.brand = brand; } // Abstract classes can have constructors

    public String getBrand() { return brand; } // Concrete method

    abstract void accelerate(); // Abstract method
}
```

---

## Interfaces

An **Interface** is a reference type that specifies a contract of behaviors. It contains no instance state.

### Characteristics:
- **Implicit Modifiers:**
  - All fields are implicitly **`public static final`** (constants).
  - All abstract methods are implicitly **`public abstract`**.
- **No Instance State:** Cannot declare instance fields or constructors.
- **Multiple Implementation:** A class can implement multiple interfaces (`class Car implements Drivable, Flyable`), enabling multiple inheritance of behavior.

---

## Contrast: Abstract Class vs. Interface

| Feature | Abstract Class | Interface |
| :--- | :--- | :--- |
| **Inheritance** | Single class inheritance (`extends`). | Multiple interface implementation (`implements`). |
| **Instance Fields** | Can have instance variables (private, protected, etc.). | Cannot have instance variables. Only `public static final` constants. |
| **Constructors** | Can have constructors (called via `super()`). | Cannot have constructors. |
| **Access Modifiers** | Methods can be public, protected, package-private, or private. | Methods are public by default (private is supported since Java 9). |
| **Root Intent** | Represents **identity** (IS-A relationship). Shared state and core identity. | Represents **capability** (CAN-DO relationship). Loose contract for unrelated classes. |

---

## Evolution of Interfaces (Java 8 and 9+)

Interfaces originally could only contain abstract methods. Java evolved interfaces to support API growth without breaking backwards compatibility.

### 1. Default Methods (Java 8+)
Default methods provide a concrete fallback implementation using the `default` keyword. implementing classes inherit them automatically but can override them.

```java
interface Drivable {
    void drive();
    default void park() { System.out.println("Parking vehicle"); }
}
```

#### Resolving Default Method Conflicts (Diamond Interface Problem)
If a class implements two interfaces that define default methods with the same signature, the class **will fail to compile** due to ambiguity. The class must resolve the conflict by overriding the method:

```java
interface A { default void msg() { System.out.println("A"); } }
interface B { default void msg() { System.out.println("B"); } }

class Test implements A, B {
    @Override
    public void msg() {
        // Option 1: Provide own implementation
        System.out.println("Custom");
        
        // Option 2: Explicitly delegate to one parent interface
        A.super.msg(); 
    }
}
```

### 2. Static Methods (Java 8+)
Used to define utility methods directly on the interface. They cannot be overridden and must be called using the interface name:
```java
interface Drivable {
    static boolean isValidSpeed(int speed) { return speed > 0; }
}
// Called via: Drivable.isValidSpeed(50)
```

### 3. Private Methods (Java 9+)
Allows sharing common helper code between multiple default or static methods in the interface without exposing them to implementing classes.
- **Private Instance Methods:** Can be accessed by default methods.
- **Private Static Methods:** Can be accessed by default, static, and private static methods.
```java
interface Drivable {
    default void start() { log("Start"); }
    default void stop() { log("Stop"); }

    private void log(String message) { // Private helper method
        System.out.println("LOG: " + message);
    }
}
```

## Deep Review: Choosing Between Abstract Class And Interface

Choose an interface when you want to describe a capability that many unrelated classes can provide.

```java
interface Payable {
    void pay(int amount);
}
```

Choose an abstract class when subclasses share identity, state, and partial implementation.

```java
abstract class Account {
    private int balance;

    Account(int openingBalance) {
        this.balance = openingBalance;
    }

    abstract void withdraw(int amount);
}
```

### Interface Evolution

Default methods let interface authors add behavior without immediately breaking every implementing class. This is useful for API evolution, but default methods should still be used carefully. Too many default methods can turn an interface into a confusing partial base class.

### Abstraction Checklist

- Does the abstraction hide unnecessary implementation details?
- Does the name describe behavior clearly?
- Are callers depending on the abstraction instead of concrete classes?
- Can multiple implementations be substituted safely?
- Are default methods used to support API evolution rather than to dump shared logic everywhere?

### Reference Links

- Oracle Java Tutorials - Interfaces: https://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html
- Oracle Java Tutorials - Defining an interface: https://docs.oracle.com/javase/tutorial/java/IandI/interfaceDef.html
- Oracle Java Tutorials - Abstract methods and classes: https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html
- Oracle Java Tutorials - Default methods: https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html
- Dev.java OOP overview: https://dev.java/learn/oop/
