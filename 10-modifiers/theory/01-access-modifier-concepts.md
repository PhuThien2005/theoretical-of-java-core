# Modifiers in Java - Part 1

## Learning Goal

This file covers a focused slice of **Modifiers in Java**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Access modifier:` | Access modifier is a group of related rules in Modifiers in Java that groups several related details. |
| `public` |public allows access from any package when the class or member is otherwise visible. |
| `protected` |protected allows access from the same package and from subclasses, with subclass access rules across packages. |
| `default` |Default access, also called package-private, allows access only inside the same package. |
| `private` |private restricts access to the declaring class only. |
| `Non-access modifier:` | Non-access modifier is a group of related rules in Modifiers in Java that groups several related details. |
| `static` | Static means the member belongs to the class rather than to one particular object. |
| `final` | Final means the variable, method, class, or parameter is restricted from later change in a specific way. |

## Detailed Notes

### Access modifier:

Access modifier is a group of related rules in Modifiers in Java that groups several related details.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Access Modifier Visibility Table
| Modifier | Same Class | Same Package | Subclass (Diff Package) | World (Diff Package) |
| --- | --- | --- | --- | --- |
| `public` | Yes | Yes | Yes | Yes |
| `protected` | Yes | Yes | Yes (via inheritance only) | No |
| `default` (no keyword) | Yes | Yes | No | No |
| `private` | Yes | No | No | No |

#### Access Modifier Code Example
```java
// File: access/AccessControlDemo.java
package access;

public class AccessControlDemo {
    public int publicVar = 1;
    protected int protectedVar = 2;
    int defaultVar = 3; // package-private
    private int privateVar = 4;

    public void showAccess() {
        System.out.println(publicVar);    // OK
        System.out.println(protectedVar); // OK
        System.out.println(defaultVar);   // OK
        System.out.println(privateVar);   // OK
    }
}
```

Practical check:

- Define `Access modifier:` in one sentence.
- Recognize `Access modifier:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Access modifier:`.

Tiny example or mental model:

- When reading code, ask: what does `Access modifier:` change, allow, reject, or clarify?

### public

public allows access from any package when the class or member is otherwise visible.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### public Code Example
```java
// File: packages/PublicClass.java
package packages;

public class PublicClass {
    public void execute() {
        System.out.println("Public method accessed successfully.");
    }
}
```

#### Common Mistake - Mismatched Class and Member Visibility
Declaring a member `public` inside a package-private (default) class makes it look like it's accessible everywhere. However, because the class itself cannot be imported outside its package, the `public` member remains inaccessible.

Practical check:

- Define `public` in one sentence.
- Recognize `public` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `public`.

Tiny example or mental model:

- When reading code, ask: what does `public` change, allow, reject, or clarify?

### protected

protected allows access from the same package and from subclasses, with subclass access rules across packages.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### protected Code Example
```java
// File: p1/Parent.java
package p1;
public class Parent {
    protected String familySecret = "Secret Recipe";
}

// File: p2/Child.java
package p2;
import p1.Parent;

public class Child extends Parent {
    public void printSecret() {
        // Accessing familySecret via inheritance is OK
        System.out.println(this.familySecret); 
        
        // Accessing via Parent instance reference in a different package fails
        Parent p = new Parent();
        // System.out.println(p.familySecret); // COMPILE ERROR!
    }
}
```

#### Common Mistake - Accessing protected members via Parent reference
Subclasses in a different package can only access `protected` members of their parent class through inheritance (`this.familySecret` or `super.familySecret`). They cannot access them using a reference variable of the parent class.

Practical check:

- Define `protected` in one sentence.
- Recognize `protected` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `protected`.

Tiny example or mental model:

- When reading code, ask: what does `protected` change, allow, reject, or clarify?

### default

Default access, also called package-private, allows access only inside the same package.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### default Access Code Example
```java
// File: p1/DefaultClass.java
package p1;

class DefaultClass { // package-private class
    void doPackageWork() { // package-private method
        System.out.println("Working within package p1.");
    }
}
```

#### Common Mistake - Using 'default' keyword as an access modifier
The `default` access level is indicated by omitting the modifier entirely. Writing `default class MyClass {}` or `default int x;` inside a class is a compile-time error. The `default` keyword is only valid inside interfaces to declare default methods, or in switch blocks.

Practical check:

- Define `default` in one sentence.
- Recognize `default` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `default`.

Tiny example or mental model:

- When reading code, ask: what does `default` change, allow, reject, or clarify?

### private

private restricts access to the declaring class only.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### private Code Example
```java
public class SecureVault {
    private String passcode = "1234";

    private void decrypt() {
        System.out.println("Decrypting vault...");
    }

    public class Nestmate {
        public void accessVault() {
            // Nested classes can access private members of the outer class
            System.out.println("Passcode: " + passcode); 
            decrypt();
        }
    }
}
```

#### Common Mistake - Private methods do not participate in polymorphism
If a subclass defines a method with the same signature as a `private` method in the superclass, it does not override it. It is treated as an entirely separate method, and dynamic binding will not dispatch to the subclass version.

Practical check:

- Define `private` in one sentence.
- Recognize `private` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `private`.

Tiny example or mental model:

- When reading code, ask: what does `private` change, allow, reject, or clarify?

### Non-access modifier:

Non-access modifier is a group of related rules in Modifiers in Java that groups several related details.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Non-access modifier Code Example
```java
public class NonAccessDemo {
    public static final double GRAVITY = 9.81;
    protected synchronized void threadSafeTask() {
        // synchronized method block
    }
}
```

Practical check:

- Define `Non-access modifier:` in one sentence.
- Recognize `Non-access modifier:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Non-access modifier:`.

Tiny example or mental model:

- When reading code, ask: what does `Non-access modifier:` change, allow, reject, or clarify?

### static

Static means the member belongs to the class rather than to one particular object.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### static Code Example
```java
public class Counter {
    public static int count = 0; // Class-level variable
    
    public static void increment() { // Class-level method
        count++;
    }
}
```

#### Common Mistake - Accessing static members on a null reference
Java allows invoking static methods or accessing static variables on an object reference, even if that reference is `null`. The JVM does not throw a `NullPointerException` because the compiler resolves the call using the static type of the reference rather than the runtime object. This is highly discouraged as it misleads readers into thinking it is an instance method call.
```java
Counter obj = null;
obj.increment(); // Compiles and runs fine! No NullPointerException.
```

Practical check:

- Define `static` in one sentence.
- Recognize `static` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `static`.

Tiny example or mental model:

- `ClassName.member` accesses a class-level member.

### final

Final means the variable, method, class, or parameter is restricted from later change in a specific way.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### final Code Example
```java
public final class ImmutableConfig {
    public final double limit = 100.0;
    
    public final void printLimit() {
        System.out.println(limit);
    }
}
```

#### Common Mistake - Final reference vs Final object
Making an object reference `final` prevents the reference itself from being reassigned to a different object, but it does NOT make the referenced object immutable. The internal fields of the object can still be modified.
```java
final java.util.List<String> list = new java.util.ArrayList<>();
list.add("allowed"); // OK! The list object is modified
// list = new java.util.ArrayList<>(); // COMPILE ERROR! Cannot reassign a final reference
```

Practical check:

- Define `final` in one sentence.
- Recognize `final` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `final`.

Tiny example or mental model:

- `final int limit = 10;` cannot be reassigned.

## Case Study: Why making a field public breaks encapsulation — bank account example

### The Problem: Public Fields
Consider a simple `BankAccount` class where the balance field is declared `public`:

```java
public class BankAccount {
    public double balance; // Public field - direct access allowed
}
```

Now, any external class can directly read and write to this field without the `BankAccount` class knowing or being able to validate the change:

```java
BankAccount account = new BankAccount();
account.balance = -1000.0; // Problem 1: Invalid state (negative balance)
account.balance = 9999999.0; // Problem 2: Unauthorized modifications
```

By making `balance` public, we have broken **encapsulation**. The class has no control over its own internal state, and we cannot guarantee its invariants (e.g., balance cannot be negative).

### The Solution: Private Fields with Getter and Mutator Methods (Encapsulation)
To fix this, we restrict access to `balance` by making it `private` and exposing controlled entry points via methods:

```java
public class SecureBankAccount {
    private double balance; // Encapsulated field

    public SecureBankAccount(double initialBalance) {
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        } else {
            this.balance = 0;
        }
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient funds or invalid amount");
        }
    }
}
```

### Consequences of Encapsulation
1. **Validation and Control**: The `SecureBankAccount` class now enforces rules. A caller cannot set a negative balance or withdraw more than they have.
2. **Read-Only / Write-Only Access**: We can make the field read-only to the outside world by providing a getter but no direct setter (deposits and withdrawals are behavior-driven, not direct state mutation).
3. **Internal Representation Independence**: If we decide to change the internal data type of `balance` from `double` to `java.math.BigDecimal` (for currency precision), we can do so without breaking any client code because the public API (methods) remains the same.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
