# Inner Class and Nested Class - Part 1

## Learning Goal

This file covers a focused slice of **Inner Class and Nested Class**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Nested class` | A class defined within another class. Divided into static nested classes and non-static nested classes (inner classes). |
| `Static nested class` | A nested class declared static; it behaves like any other top-level class package-wise but is nested for grouping, and doesn't require an outer instance. |
| `Inner class` | A non-static nested class associated with a specific instance of the outer class. |
| `Local inner class` | A class defined within a method block; it can only access final or effectively final local variables. |
| `Anonymous inner class` | An inner class without a name declared and instantiated in a single expression to extend a class or implement an interface. |
| `Access variables outside the class` | Rules governing how nested, inner, local, and anonymous classes access enclosing instance members or method-local variables. |
| `Use case of inner class` | Logical grouping of helper classes, encapsulation (e.g. Iterators), and maintaining clean top-level namespaces. |
| `Anonymous class in event handler, thread, comparator` | Implementing quick one-off behaviors before lambdas; understanding why `this` scope and compilation differs from lambdas. |

---

## Detailed Notes

### Nested class

A **nested class** is any class defined within the body of another enclosing class. In Java, nested classes are divided into two main categories:
1. **Static nested classes**: Declared with the `static` modifier. They do not have access to the instance of the enclosing class.
2. **Inner classes** (Non-static nested classes): Declared without the `static` modifier. They are bound to an instance of the outer class.

```
                  Nested Class
                     /    \
                    /      \
      Static Nested Class  Inner Class (Non-static)
                            /     \
                           /       \
                 Local Inner Class  Anonymous Inner Class
```

---

### Static nested class

A **static nested class** behaves like a top-level class that has been nested inside another class for packaging convenience. It does not have an implicit reference to an instance of the outer class.

#### Access Rules
- **Can access**: All static members (variables and methods) of the outer class, including `private` ones.
- **Cannot access**: Instance members (fields or methods) of the outer class directly. It must create an instance of the outer class to access them.
- **Static members**: Static nested classes can define static variables, static methods, and non-static members.

#### Instantiation Syntax
Because it does not require an outer instance, you instantiate it using the outer class name:
```java
Outer.StaticNested nestedInstance = new Outer.StaticNested();
```

#### Code Example
```java
public class Outer {
    private static String staticOuterField = "Private Static Outer Field";
    private String instanceOuterField = "Private Instance Outer Field";

    public static class StaticNested {
        public void display() {
            // Can access static members directly (even private)
            System.out.println("Accessing: " + staticOuterField);

            // Cannot access instanceOuterField directly:
            // System.out.println(instanceOuterField); // Compile error!

            // Must instantiate Outer to access instance members
            Outer outer = new Outer();
            System.out.println("Accessing via instance: " + outer.instanceOuterField);
        }
    }
}
```

---

### Inner class (Non-static nested class)

An **inner class** is a non-static nested class. Every instance of an inner class is implicitly bound to a specific instance of the outer class.

#### Access Rules
- **Can access**: All members of the outer class (instance and static), including `private` ones.
- **Cannot define**: Prior to Java 16, inner classes could not define static members (except for static final constant variables). From Java 16 onwards, inner classes can declare static members.
- **Implicit reference**: Keeps a hidden reference to the outer instance (`Outer.this`), which prevents the outer instance from being garbage collected as long as the inner instance exists.

#### Instantiation Syntax
You must have an instance of the outer class to instantiate an inner class:
```java
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();
```

#### Code Example
```java
public class Outer {
    private String outerField = "Outer Instance Field";

    public class Inner {
        public void printOuter() {
            // Direct access to the enclosing instance's field
            System.out.println("Enclosing field: " + outerField);
            // Explicit reference syntax:
            System.out.println("Enclosing field (explicit): " + Outer.this.outerField);
        }
    }
}
```

---

### Local inner class

A **local inner class** is defined within a block of code, typically inside a method body. Its scope is restricted entirely to that block.

#### Access Rules
- **Scope**: Local to the block. Cannot be declared with access modifiers (`public`, `protected`, `private`) or `static`.
- **Can access**: Outer class members, plus local variables of the enclosing block **only if** they are `final` or **effectively final** (variables whose value is never changed after initialization).
- **Modification**: Cannot modify local variables of the outer method inside the local class.

#### Instantiation Syntax
You can only instantiate a local class within the enclosing method, after the class has been defined.

#### Code Example
```java
public class Outer {
    private String outerField = "Outer Field";

    public void processData(final String inputParam) {
        String localVal = "Local Temp Value"; // Effectively final
        String nonFinalVal = "Initial Value";
        nonFinalVal = "Changed Value"; // Not effectively final anymore

        class LocalInner {
            public void run() {
                System.out.println(outerField);  // Accesses outer instance field
                System.out.println(inputParam);  // Accesses final parameter
                System.out.println(localVal);    // Accesses effectively final local variable
                // System.out.println(nonFinalVal); // Compile error! Not effectively final
            }
        }

        LocalInner inner = new LocalInner();
        inner.run();
    }
}
```

---

### Anonymous inner class

An **anonymous inner class** is a local class without a name. It is declared and instantiated at the same time using the `new` operator. It must either extend an existing class or implement an interface.

#### Access Rules
- **Structure**: Cannot define constructors (has no name), but can use instance initializers `{ ... }`.
- **Variables**: Same final/effectively final rules as local classes apply to accessed method-local variables.
- **Usage**: Used for quick, one-off overrides of class behavior or interface implementations.

#### Instantiation Syntax
```java
InterfaceName obj = new InterfaceName() {
    @Override
    public void method() {
        // Implementation
    }
};
```

#### Code Example
```java
public class Button {
    interface ClickListener {
        void onClick();
    }

    public void setListener(ClickListener listener) {
        listener.onClick();
    }
}

class Test {
    public void setup() {
        Button btn = new Button();
        
        // Implementing ClickListener using Anonymous Inner Class
        btn.setListener(new Button.ClickListener() {
            private int clickCount = 0; // Can define instance fields

            @Override
            public void onClick() {
                clickCount++;
                System.out.println("Clicked! Count: " + clickCount);
            }
        });
    }
}
```

---

### Summary Access Rules Table

| Class Type | Inner/Nested | Can Access Outer Instance? | Can Access Method Locals? | Instantiation Syntax | Can Define Static Members? |
| --- | --- | --- | --- | --- | --- |
| **Static Nested** | Nested | No | N/A | `new Outer.StaticNested()` | Yes |
| **Inner Class** | Inner | Yes | N/A | `outerInstance.new Inner()` | Yes (Java 16+), No (Pre-Java 16 except constant variables) |
| **Local Class** | Inner | Yes | Yes (if final/effectively final) | Inside method body only | Yes (Java 16+), No (Pre-Java 16 except constant variables) |
| **Anonymous Class**| Inner | Yes | Yes (if final/effectively final) | Inline declaration & creation | Yes (Java 16+), No (Pre-Java 16 except constant variables) |

---

### Use cases of inner classes

1. **Logical Grouping**: If class B is only useful to class A, B can be nested within A to keep packages clean.
2. **Enhanced Encapsulation**: Inner classes can access private members of the outer class. If B needs to manipulate private fields of A without exposing them to the rest of the application (e.g. `java.util.HashMap.KeyIterator`), B should be an inner class of A.
3. **Namespace Management**: Prevents cluttering the top-level namespace with small, specialized classes that are only used in one place.

---

## Common Mistakes

### 1. Direct Instantiation of Inner Class without Enclosing Instance
A common mistake is trying to instantiate a non-static inner class as if it were a static nested class.
```java
// WRONG:
Outer.Inner inner = new Outer.Inner(); // Compile error!

// CORRECT:
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();
```

### 2. Accessing Instance Members from Static Nested Class
Static nested classes cannot access outer non-static fields directly because they do not have a reference to an outer object.
```java
public class Outer {
    int x = 10;
    static class StaticNested {
        void run() {
            // System.out.println(x); // Compile error!
            System.out.println(new Outer().x); // Correct
        }
    }
}
```

### 3. Modifying Method-Local Variables (Effectively Final Violation)
Attempting to modify a local variable inside a local or anonymous class, or modifying it later in the enclosing method, will trigger a compiler error.
```java
public void doSomething() {
    int counter = 0;
    Runnable r = new Runnable() {
        @Override
        public void run() {
            // counter++; // Compile error: local variables referenced from an inner class must be final or effectively final
        }
    };
}
```

### 4. Shadowing and the `this` Reference Trap
Inside an inner or anonymous class, `this` refers to the inner class itself, not the outer class. To refer to the outer class instance, use `Outer.this`.
```java
public class Outer {
    String name = "Outer";

    public class Inner {
        String name = "Inner";
        
        public void print() {
            System.out.println(this.name);       // Prints "Inner"
            System.out.println(Outer.this.name); // Prints "Outer"
        }
    }
}
```

---

## Case Study: Anonymous Class vs Lambda for Runnable/Comparator

Java 8 introduced lambda expressions as a cleaner alternative to anonymous classes. However, they are not completely identical.

### 1. Functional Interfaces vs Classes/Multiple Methods
- **Lambdas** can *only* be used for Functional Interfaces (interfaces with a single abstract method, or SAM).
- **Anonymous Classes** can implement interfaces with multiple methods, implement interfaces with zero methods (marker interfaces), or extend concrete/abstract classes.

```java
// Anonymous Class extending an abstract class
abstract class Worker { abstract void work(); }
Worker w = new Worker() {
    void work() { System.out.println("Working..."); }
}; // Cannot use lambda here because Worker is a class, not an interface!
```

### 2. Scope of `this` and Variable Shadowing
- **Anonymous Class**: Introduces a new scope. `this` refers to the anonymous class instance itself. It can also declare local fields that shadow outer fields.
- **Lambda**: Lexical scope. `this` refers to the enclosing outer class instance where the lambda is defined. It does not introduce a new scope level; declaring a variable with the same name as a local variable in the enclosing method is a compilation error.

```java
public class ScopeTest {
    private String name = "Outer";

    public void runTest() {
        // 1. Anonymous Class
        Runnable r1 = new Runnable() {
            private String name = "Anonymous";
            @Override
            public void run() {
                System.out.println(this.name); // Prints "Anonymous"
            }
        };

        // 2. Lambda
        Runnable r2 = () -> {
            // String name = "Lambda"; // Compile Error: Variable 'name' is already defined in scope
            System.out.println(this.name); // Prints "Outer" (lexical 'this')
        };
        
        r1.run();
        r2.run();
    }
}
```

### 3. Compilation and Performance (Bytecode Differences)
- **Anonymous Class**: Compiles to a separate physical `.class` file (e.g. `Outer$1.class`). This requires the JVM to load a separate class at runtime, increasing startup overhead and memory footprint.
- **Lambda**: Uses the `invokedynamic` (indy) opcode introduced in Java 7. Instead of generating a class file at compile time, the compiler generates a bootstrap method. The runtime JVM uses `LambdaMetafactory` to generate the call site dynamically, avoiding classloader overhead and enabling JVM-level inlining optimizations.

---

## Reference Links
- [Oracle Java Tutorials: Nested Classes](https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html)
- [Oracle Java Tutorials: Inner Class Classes](https://docs.oracle.com/javase/tutorial/java/javaOO/innerclasses.html)
- [Oracle Java Tutorials: Local Classes](https://docs.oracle.com/javase/tutorial/java/javaOO/localclasses.html)
- [Oracle Java Tutorials: Anonymous Classes](https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html)
