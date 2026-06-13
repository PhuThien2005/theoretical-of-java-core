# Inheritance

Inheritance is the OOP mechanism where a subclass inherits fields and methods from a superclass, promoting code reuse and establishing an **IS-A relationship** (e.g., a Dog IS-A Mammal).

---

## Types of Inheritance in Java

Java supports three forms of class inheritance:
1. **Single Inheritance:** A class inherits from a single parent class (`B extends A`).
2. **Multilevel Inheritance:** A class inherits from a subclass, creating a chain of inheritance (`C extends B` and `B extends A`).
3. **Hierarchical Inheritance:** Multiple subclasses inherit from the same parent class (`B extends A` and `C extends A`).

### Code Example: Inheritance Chain (Multilevel Inheritance)
Below is a concrete code example of an inheritance chain establishing IS-A relationships:

```java
class Animal {
    void eat() {
        System.out.println("This animal eats food.");
    }
}

class Mammal extends Animal {
    void breathe() {
        System.out.println("This mammal breathes air.");
    }
}

class Dog extends Mammal { // Multilevel inheritance chain: Dog IS-A Mammal, Mammal IS-A Animal
    void bark() {
        System.out.println("Woof!");
    }
}
```

---

## Multiple Inheritance and the Diamond Problem

Java **prohibits multiple inheritance through classes** (e.g., `class C extends A, B` is invalid) to avoid the **Diamond Problem**.

### The Diamond Problem Explained
If class `A` defines a method `foo()`, and classes `B` and `C` both override `foo()` with different behaviors, a class `D` extending both `B` and `C` would inherit two conflicting implementations of `foo()`. Calling `new D().foo()` would create ambiguity for the JVM.

Java solves this by:
1. Only allowing single class inheritance.
2. Allowing multiple inheritance of behavior through **interfaces** (since interface method conflicts must be resolved explicitly by the implementing class).

---

## The `super` Keyword

The `super` keyword refers to the immediate parent class.

### 3 Main Uses of `super`:
1. **To call parent constructors:** `super()` or `super(args)`.
   - Must be the first statement of the subclass constructor.
   - If omitted, the compiler automatically inserts a default `super()` call.
2. **To access hidden fields:** Differentiate subclass fields from parent fields with matching names (Field Hiding).
   ```java
   int parentVal = super.value;
   ```
3. **To invoke overridden methods:** Call the parent's version of a method from the subclass.
   ```java
   super.displayInfo();
   ```

---

## Constructor Execution Flow

Constructors are **not inherited** by subclasses. However, when a subclass object is instantiated:
1. The subclass constructor begins execution.
2. It immediately delegates up the chain by executing `super()`.
3. This delegation reaches the top class (`java.lang.Object`), whose constructor runs.
4. Constructors execute from the top parent down to the child class.

> [!WARNING]
> If a parent class does not define a default no-argument constructor (because it only has parameterized constructors), the subclass constructor **must** explicitly call `super(args)` on its first line, or a compile-time error occurs.

---

## Method Overriding Rules

Method Overriding occurs when a subclass provides a specific implementation of a method defined in its superclass.

### Code Example: Overriding with `@Override`
Annotating the subclass method with `@Override` instructs the compiler to verify that the method matches a signature in the superclass, preventing bugs caused by typos.

```java
class Vehicle {
    void fuelUp() {
        System.out.println("Refueling with generic fuel.");
    }
}

class ElectricCar extends Vehicle {
    @Override // Compiler verifies that fuelUp() exists in Vehicle
    void fuelUp() {
        System.out.println("Charging the battery pack.");
    }
}
```

### Essential Rules for Overriding:
1. **Method Signature:** Must have the exact same name and parameter list.
2. **Return Type:** Must be the same, or a **covariant return type** (a subclass of the parent's return type).
   ```java
   class Food {}
   class Meat extends Food {}
   class Animal { Food getFood() { return new Food(); } }
   class Tiger extends Animal { @Override Meat getFood() { return new Meat(); } } // Covariant return type (Meat is Food)
   ```
3. **Access Modifier:** The overriding method cannot be more restrictive than the overridden method:
   - Parent `public` $\rightarrow$ Child must be `public`.
   - Parent `protected` $\rightarrow$ Child must be `protected` or `public`.
   - Parent `default` $\rightarrow$ Child must be `default`, `protected`, or `public`.
4. **Exceptions:** The overriding method cannot throw broader or new **checked exceptions**. It can throw fewer, narrower checked exceptions, or any unchecked (runtime) exceptions.
5. **Non-overridable methods:**
   - Methods marked `final` or `private` cannot be overridden.
   - Constructors cannot be overridden.
   - Static methods cannot be overridden (see Method Hiding below).

---

## Method Hiding vs. Field Hiding

### Method Hiding (Static Methods)
If a subclass defines a static method with the same signature as a static method in the superclass, the parent's method is **hidden**, not overridden.
- Static methods are resolved at compile time based on the **reference variable type**, not the runtime object in the heap.
- Placing `@Override` on a static method causes a compilation error.

### Field Hiding
If a subclass declares a field with the same name as a superclass field, the parent's field is hidden.
- Fields are **not polymorphic**. They are resolved statically at compile time based on the reference variable type.

```java
class Parent { int value = 10; }
class Child extends Parent { int value = 20; }

Parent p = new Child();
System.out.println(p.value); // Prints 10 (resolved by Reference Type Parent)
```

## Deep Review: Inheritance Design Rules

Inheritance is powerful but easy to overuse. It should represent a stable **is-a** relationship, not merely "I want to reuse some code."

Use inheritance when:

- The subclass truly is a specialized form of the superclass.
- The superclass defines behavior that subclasses can safely inherit or override.
- The hierarchy is stable and unlikely to need many unrelated variations.

Prefer composition when:

- You only want to reuse implementation.
- The relationship is "has-a" rather than "is-a".
- Different behaviors should be swapped independently.

```java
class Engine { }

class Car {
    private final Engine engine; // composition: Car has an Engine
}
```

### Overriding vs Hiding Traps

- Instance methods are polymorphic and can be overridden.
- Static methods are hidden, not overridden.
- Fields are hidden, not overridden.
- Constructors are never inherited.
- Private methods are not overridden because subclasses cannot see them.

---

## Case Study: Why calling an overridden method from a constructor is dangerous

When a subclass object is instantiated, the superclass constructor executes first. If the superclass constructor calls an instance method that the subclass overrides, the subclass's implementation of that method runs **before** the subclass constructor has initialized its own fields.

### Example Scenario
Consider what happens when we instantiate `Child`:

```java
class Parent {
    Parent() {
        System.out.println("Parent Constructor starts.");
        printState(); // Calling polymorphic method from constructor!
    }

    void printState() {
        System.out.println("Parent state: active");
    }
}

class Child extends Parent {
    private String customConfig = "InitializedChildConfig"; // Field Initializer

    Child() {
        System.out.println("Child Constructor runs.");
    }

    @Override
    void printState() {
        // Danger: customConfig is still null here because Child's initializers and constructor haven't run!
        System.out.println("Child config length: " + (customConfig != null ? customConfig.length() : "NULL"));
    }
}

public class Main {
    public static void main(String[] args) {
        new Child();
    }
}
```

### Output:
```text
Parent Constructor starts.
Child config length: NULL
Child Constructor runs.
```

### Key Takeaway
Never invoke overridable methods inside constructors. If you must call a method to perform initialization work in a constructor, make sure it is `private` or `final`, which prevents it from being overridden polymorphically.

---

## Common Mistakes

### 1. Confusing Overload with Override (Signature Typo)
If the subclass method signature differs slightly from the parent's, Java treats it as an overload, not an override. Without the `@Override` annotation, this mistake will compile silently but fail to execute polymorphically at runtime.
```java
class Printer {
    void print(String message) {}
}

class ColorPrinter extends Printer {
    // Mistake: parameter type Object instead of String. Overloads instead of overrides!
    void print(Object message) {} 
}
```

### 2. Attempting to Override Static Methods
If a subclass defines a static method with the same signature as a parent static method, it **hides** it rather than overriding it. Using `@Override` on a static method causes a compilation error.
```java
class Base {
    static void display() {}
}
class Derived extends Base {
    // @Override // Compile Error: static methods cannot be overridden
    static void display() {} 
}
```

### 3. Accessing Hidden Fields Polymorphically
Fields are resolved statically at compile time based on the reference variable's type, not the runtime object type. Declaring a field with the same name in a subclass hides the parent field, which can lead to confusion.
```java
class Super { int val = 100; }
class Sub extends Super { int val = 200; }

Super obj = new Sub();
System.out.println(obj.val); // Prints 100 (resolved from Super reference type, not Sub)
```

### Reference Links

- Oracle Java Tutorials - Inheritance: https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html
- Oracle Java Tutorials - Overriding and hiding methods: https://docs.oracle.com/javase/tutorial/java/IandI/override.html
- Oracle Java Tutorials - Interfaces and Inheritance: https://docs.oracle.com/javase/tutorial/java/IandI/index.html
- Dev.java Inheritance: https://dev.java/learn/inheritance/
