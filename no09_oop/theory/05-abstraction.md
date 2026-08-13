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

### Code Example: Abstract Class vs. Interface
Here is a comparison using a payment and banking system:

```java
// Abstract Class represents identity (IS-A)
abstract class BankAccount {
    private String accountNumber;
    protected double balance; // Can have instance variables

    BankAccount(String accountNumber, double balance) { // Can have constructors
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public double getBalance() { return balance; } // Concrete method

    abstract void processInterest(); // Abstract method
}

// Interface represents capability (CAN-DO)
interface Payable {
    int TAX_RATE_PERCENT = 10; // Implicitly public static final constant

    void pay(double amount); // Implicitly public abstract method
}

// Concrete class extending the abstract class and implementing the interface
class SavingsAccount extends BankAccount implements Payable {
    SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    void processInterest() {
        balance += balance * 0.02;
    }

    @Override
    public void pay(double amount) { // Must use public modifier
        balance -= amount;
    }
}
```

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

## Case Study: Diamond Problem and How Interfaces Solve It

In traditional multiple inheritance (like in C++), if a class `D` inherits from both `B` and `C`, which both inherit from `A`, and both override a method `foo()` from `A`, then calling `foo()` on an instance of `D` is ambiguous (the "Diamond Problem").

Java avoids this by:
1. Allowing only single class inheritance. A class can never extend more than one class.
2. Allowing multiple interface inheritance. Since interface methods were originally purely abstract (no body), there was no implementation conflict.

### The Modern Challenge: Default Methods (Java 8+)
With default methods, interfaces can now carry behavior. This reintroduced a form of implementation conflict (the Diamond Interface Problem) when a class implements two interfaces declaring the same default method signature.

```java
interface Flyer {
    default void move() { System.out.println("Flying"); }
}

interface Swimmer {
    default void move() { System.out.println("Swimming"); }
}

// class Duck implements Flyer, Swimmer {} // Compile Error: Duck inherits unrelated defaults for move()
```

### The Solution
Java forces the implementing class to explicitly override the conflicting method, resolving the ambiguity at compile-time. Inside the overridden method, you can write custom behavior or explicitly delegate to one of the interfaces using `<InterfaceName>.super.<methodName>()`.

```java
class Duck implements Flyer, Swimmer {
    @Override
    public void move() {
        // Option 1: Provide a brand-new behavior
        System.out.println("Walking like a duck");

        // Option 2: Explicitly delegate to Flyer's default behavior
        Flyer.super.move();

        // Option 3: Explicitly delegate to Swimmer's default behavior
        Swimmer.super.move();
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

---

## Common Mistakes

### 1. Trying to Instantiate an Abstract Class or Interface
Attempting to directly instantiate an abstract class or interface results in a compile-time error. They are incomplete contracts and must be extended or implemented by concrete classes first.
```java
abstract class Shape {}
// Shape s = new Shape(); // Compile Error: Shape is abstract; cannot be instantiated
```

### 2. Overriding Interface Methods Without the `public` Modifier
All methods declared in an interface are implicitly `public`. When a class implements an interface method, it must explicitly declare it `public`. Omitting `public` defaults the class method to package-private, which is a weaker privilege and fails compilation.
```java
interface Drivable {
    void drive();
}

class Car implements Drivable {
    // void drive() {} // Compile Error: attempting to assign weaker access privileges; was public
    
    @Override
    public void drive() {} // Correct
}
```

### 3. Trying to Declare Instance Fields in Interfaces
Every variable declared in an interface is implicitly `public static final` (a constant). It must be initialized immediately and cannot be modified. Declaring normal instance fields in an interface is impossible.
```java
interface Config {
    int TIMEOUT; // Compile Error: variable TIMEOUT might not have been initialized
}
```

### Reference Links

- Oracle Java Tutorials - Interfaces: https://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html
- Oracle Java Tutorials - Defining an interface: https://docs.oracle.com/javase/tutorial/java/IandI/interfaceDef.html
- Oracle Java Tutorials - Abstract methods and classes: https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html
- Oracle Java Tutorials - Default methods: https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html
- Dev.java OOP overview: https://dev.java/learn/oop/

---

## Why Java Uses Interfaces Instead of Multiple Class Inheritance

Java's designers deliberately prohibited multiple class inheritance because allowing `class D extends B, C` — where both `B` and `C` independently override a method from a shared ancestor `A` — creates an ambiguity that has no safe, deterministic resolution: the compiler and JVM would need to pick one implementation over the other with no principled basis for the choice, leading to unpredictable behavior known as the **Diamond Problem**. Languages like C++ allow multiple class inheritance but require explicit disambiguation syntax that adds significant complexity for both compiler writers and developers. Java solved this by restricting every class to a single `extends` relationship while allowing unlimited `implements` relationships. Interfaces originally carried no implementations — only method signatures — so implementing two interfaces with the same method name created no ambiguity: the implementing class was simply obligated to provide one implementation that satisfied both contracts. When Java 8 introduced `default` methods (concrete implementations inside interfaces), the diamond problem re-emerged in a new form: if two interfaces both declare a `default` method with the same signature and a class implements both, it inherits two conflicting implementations. Java resolves this by refusing to compile such a class unless it explicitly overrides the conflicting method, forcing the developer to resolve the ambiguity deliberately using either custom logic or the `InterfaceName.super.method()` delegation syntax.

### Mental Model

```
Diamond Problem (class inheritance — Java PROHIBITS):

        A
       / \
      B   C         B.foo() and C.foo() are different implementations
       \ /
        D           D.foo() — ambiguous! Which parent's foo() runs? No answer.

Java's Solution (interface inheritance):

  interface Flyable { void move(); }    // no implementation
  interface Swimmer { void move(); }    // no implementation
  class Duck implements Flyable, Swimmer {
      @Override public void move() { ... }  // Duck MUST provide ONE explicit implementation
  }

Default Method Conflict (Java 8+ — still resolved at compile time):

  interface Flyable { default void move() { "fly"; } }
  interface Swimmer { default void move() { "swim"; } }
  class Duck implements Flyable, Swimmer {
      // COMPILE ERROR unless Duck overrides move()
      @Override public void move() {
          Flyer.super.move();  // OR: Swimmer.super.move();  OR: custom
      }
  }
```

### Code Example

```java
interface Flyable {
    default void move() { System.out.println("Flying"); }
}

interface Swimmer {
    default void move() { System.out.println("Swimming"); }
}

// class Duck implements Flyable, Swimmer {} // Compile Error: inherits unrelated defaults for move()

class Duck implements Flyable, Swimmer {
    @Override
    public void move() {
        // Must resolve the conflict explicitly
        Flyable.super.move(); // delegates to Flyable's default
    }
}

public class Main {
    public static void main(String[] args) {
        new Duck().move();
    }
}
// Output:
// Flying
```

### Cause-Effect Chain

Java prohibits `class D extends B, C` (multiple class inheritance)
→ Eliminates the Diamond Problem: no two parent classes can supply conflicting concrete vtable slots for the same method
→ Interfaces with only abstract methods can be multiply implemented — no conflict because no implementations exist
→ Java 8 introduces `default` methods: interfaces can now carry implementations
→ Two interfaces with the same default method signature creates a new conflict
→ Java compiler refuses to compile unless the implementing class explicitly overrides the method
→ Developer must use `InterfaceName.super.method()` or provide custom logic
→ Conflict always resolved at compile time — never left ambiguous at runtime

---

## Why Abstract Classes and Interfaces Serve Different Design Purposes

The choice between an abstract class and an interface is a **semantic** decision about what the type represents, not merely a technical one about what language features are needed. An abstract class models an **is-a identity**: it represents a real, recognizable category of objects that share state, lifecycle, and partial behavior (e.g., every `BankAccount` has a balance and an account number regardless of whether it is a savings or checking account). Because an abstract class can declare instance fields and constructors, it is the natural home for shared mutable state that all subclasses need to inherit and build upon; subclasses call `super(...)` to initialize that shared state before adding their own. An interface models a **can-do capability**: it describes a role or contract that completely unrelated classes might fulfil (e.g., both a `Robot` and a `Human` can be `Payable`, but they share no common identity or state). The design rule that resolves the choice is the **Liskov Substitution Principle together with the presence of shared state**: if the abstraction carries instance fields, requires a constructor, or represents a stable identity hierarchy from which subclasses truly inherit state and behavior, use an abstract class; if the abstraction is a pure behavioral contract that should be applicable across unrelated types without forcing a single inheritance slot, use an interface. In modern Java (8+), interfaces support `default` and `static` methods, narrowing the gap — but they still cannot hold instance state, which remains the hard boundary between the two mechanisms.

### Mental Model

```
ABSTRACT CLASS — use when there is shared identity + state:

  abstract class BankAccount {
      private double balance;       // shared state — instance field
      private String accountNumber; // shared state — instance field
      BankAccount(String acct, double bal) { ... } // shared constructor

      public double getBalance() { return balance; } // shared concrete method

      abstract void processInterest();               // specialization point
  }

  SavingsAccount extends BankAccount  → IS-A BankAccount, inherits balance/accountNumber
  CheckingAccount extends BankAccount → IS-A BankAccount, inherits balance/accountNumber

INTERFACE — use when there is only a behavioral contract across unrelated types:

  interface Payable {
      void pay(double amount);   // contract only — no state
  }

  SavingsAccount implements Payable  → CAN-DO paying
  Robot          implements Payable  → CAN-DO paying (unrelated hierarchy!)
  Employee       implements Payable  → CAN-DO paying (completely different type!)

Decision Rule:
  Has shared instance state?  → Abstract Class
  Pure behavioral contract?   → Interface
  Needs both?                 → Abstract Class + Interface(s)
```

### Code Example

```java
// Abstract class: shared identity + state
abstract class Shape {
    private String color; // shared instance state

    Shape(String color) { this.color = color; }

    public String getColor() { return color; }

    abstract double area(); // specialization point
}

// Interface: pure capability, applicable to unrelated types
interface Printable {
    void print(); // behavioral contract only
}

class Circle extends Shape implements Printable {
    private double radius;

    Circle(String color, double radius) {
        super(color); // initializes shared Shape state
        this.radius = radius;
    }

    @Override
    public double area() { return Math.PI * radius * radius; }

    @Override
    public void print() {
        System.out.printf("Circle [color=%s, area=%.2f]%n", getColor(), area());
    }
}

public class Main {
    public static void main(String[] args) {
        Circle c = new Circle("red", 5.0);
        c.print();
        System.out.printf("Area: %.2f%n", c.area());
    }
}
// Output:
// Circle [color=red, area=78.54]
// Area: 78.54
```

### Cause-Effect Chain

Type needs to carry instance fields and a constructor for shared state
→ Use an abstract class: subclasses call `super(...)` to initialize that state
→ Single inheritance slot is consumed; subclass can still implement multiple interfaces
→ Type is only a behavioral contract with no instance state
→ Use an interface: any class from any hierarchy can implement it
→ Multiple interfaces can be implemented simultaneously — no slot conflict
→ If both shared state and a cross-cutting capability are needed:
→ Extend one abstract class for identity/state AND implement one or more interfaces for capabilities
