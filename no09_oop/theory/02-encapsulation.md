# Encapsulation

Encapsulation is the OOP principle of bundling data (instance fields) and the methods that operate on that data into a single unit (a class), while restricting direct access to some of the object's components. This is also referred to as **data hiding**.

---

## Access Modifiers and Scope Levels

Java provides access modifiers to restrict visibility at two levels:
1. **Class-level:** A top-level class can only be declared `public` or default (package-private). It cannot be declared `private` or `protected` (except for nested/inner classes).
2. **Member-level:** Fields, methods, and constructors can use all four visibility levels.

| Modifier | Inside Same Class | Inside Same Package | Subclass in Different Package | Everywhere (World) |
| :--- | :---: | :---: | :---: | :---: |
| **`private`** | Yes | No | No | No |
| **`default`** (no modifier) | Yes | Yes | No | No |
| **`protected`** | Yes | Yes | Yes (via inheritance) | No |
| **`public`** | Yes | Yes | Yes | Yes |

### Key Scopes Explained:
- **`private`** — private: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`default`** — default: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`protected`** — protected: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
  *Note:* A subclass in a different package can access a `protected` member of a parent class only through inheritance (using reference variables of the subclass type), not via reference variables of the parent class type.
- **`public`** — public: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

---

## Getter and Setter Design Patterns

To interact with private fields, classes expose public accessor methods (getters) and mutator methods (setters).

### Standard Encapsulation Example
Here is a class demonstrating standard encapsulation: private fields, a parameterized constructor, and public getters/setters to access and modify the state in a controlled manner.

```java
public class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        setSalary(salary); // Enforce validation during object creation
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary >= 0) {
            this.salary = salary;
        } else {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }
    }
}
```

This pattern provides multiple software design benefits:

### 1. Data Validation
Setters can intercept inputs to prevent invalid object states:
```java
public void setAge(int age) {
    if (age >= 0 && age <= 120) {
        this.age = age;
    } else {
        throw new IllegalArgumentException("Invalid age: " + age);
    }
}
```

### 2. Read-Only and Write-Only Control
- **Read-Only Class:** Expose getters but omit setters. Useful for creating immutable-like state containers.
- **Write-Only Class:** Expose setters but omit getters (e.g. updating a write-only database credential or logging system).

### 3. Defensive Copying (Critical for Reference Types)
Exposing getters for mutable objects (like arrays or lists) breaks encapsulation because the caller can modify the object directly through the returned reference. Getters should return defensive copies:

```java
class Team {
    private List<String> members = new ArrayList<>();

    // Vulnerable Getter:
    // public List<String> getMembers() { return this.members; } // Caller can call getMembers().clear()!

    // Encapsulated Getter:
    public List<String> getMembers() {
        return new ArrayList<>(this.members); // Returns a copy
    }
}
```

---

## Benefits of Encapsulation

1. **Flexibility and Maintainability:** You can change the internal data structure of a class without breaking the external code that depends on it. For example, changing a field `private int age` to `private LocalDate birthDate` can be done while keeping the `getAge()` method signature functional by calculating the age on the fly.
2. **Loose Coupling:** Reduces dependencies between classes, making components modular and easier to unit test.
3. **Security:** Protects fields from arbitrary modifications from outside the class.

## Deep Review: Encapsulation Is More Than Getters And Setters

Encapsulation is not the habit of generating getters and setters for every field. It is the habit of protecting object invariants behind a deliberate API.

A weak design exposes every field indirectly:

```java
class User {
    private String password;
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
```

A stronger design exposes behavior instead of raw state:

```java
class User {
    private String passwordHash;

    public boolean matchesPassword(String rawPassword) {
        return Passwords.matches(rawPassword, passwordHash);
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!matchesPassword(oldPassword)) {
            throw new IllegalArgumentException("old password is wrong");
        }
        this.passwordHash = Passwords.hash(newPassword);
    }
}
```

---

## Common Mistakes

### 1. Blindly Exposing Every Field with Getters/Setters
Generating public getters and setters for all fields makes the class act as a global state bag, allowing outside code to violate the object's rules. Setters should only exist if modification is logically permitted and validated.

### 2. Leaking Internal References (Breaking Encapsulation)
Exposing getters for mutable internal structures (e.g. `List`, `Map`, arrays) allows callers to mutate internal collections directly.
```java
class Wallet {
    private List<Coin> coins = new ArrayList<>();
    // Mistake: returns direct reference
    public List<Coin> getCoins() { return coins; } 
}
// Caller can do: wallet.getCoins().clear(); // bypasses Wallet control!
```
**Fix:** Return an unmodifiable wrapper or a defensive copy:
```java
public List<Coin> getCoins() { return Collections.unmodifiableList(coins); }
```

### 3. Leaving Fields Package-Private (Omitting `private` Modifier)
Omitting access modifiers defaults them to package-private, letting any other class in the same package modify the fields directly. Always declare fields `private` by default.
```java
class Account {
    double balance; // Missing private! Any class in package can write account.balance = -9999;
}
```

### Encapsulation Checklist

- Keep fields private by default.
- Expose methods that describe behavior, not only storage.
- Validate inputs before changing object state.
- Return defensive copies for mutable internal objects.
- Avoid setters that can put the object into an invalid state.
- Prefer immutable objects when the state does not need to change.

### Reference Links

- Oracle Java Tutorials - OOP concepts: https://docs.oracle.com/javase/tutorial/java/concepts/
- Oracle Java Tutorials - Classes and Objects: https://docs.oracle.com/javase/tutorial/java/javaOO/index.html
- Dev.java OOP overview: https://dev.java/learn/oop/

---

## Why Instance Variables Should Be Private

When an instance variable is declared `private`, the Java compiler enforces an access boundary at the source-code level: any attempt to read or write that field from outside the declaring class produces a compile-time error before a single byte of bytecode is generated. This is a language-level guarantee, not a JVM-level guarantee — the JVM itself does not prevent field access by bytecode; it only checks the access flags encoded in the `.class` file when the class is loaded and linked. In practice, reflection (`Field.setAccessible(true)`) can bypass those flags and reach `private` fields at runtime, which means the JVM does not truly prevent all access — it only refuses access through normal invocation. What the language enforces is that no legitimately compiled class file can reference a `private` field of another class without triggering a compiler error; only deliberately crafted or reflective bytecode can circumvent this. Making fields `private` therefore locks down the ordinary development path: all mutations must go through methods you deliberately expose, letting you validate inputs, maintain invariants, and swap the internal representation without touching any calling code.

### Mental Model

```
[Outside Code]                 [Account Class]
     |                              |
     |  account.balance = -999;     |
     |----------------------------> X  <-- Compile Error (language enforcement)
     |                              |
     |  account.setBalance(-999);   |
     |----------------------------> [setBalance()]
     |                              |  if (amount >= 0) this.balance = amount;
     |                              |  else throw IllegalArgumentException
     |                              |
     |  // Reflection bypass:       |
     |  f.setAccessible(true);      |
     |  f.set(account, -999);  ---> [JVM: checks AccessibleObject flag]
     |                              |  flag == true → JVM allows write
     |                              |  (JVM does NOT prevent; language prevented compilation)
```

### Code Example

```java
public class Account {
    private double balance; // private: compiler enforces access restriction

    public Account(double openingBalance) {
        setBalance(openingBalance);
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double amount) {
        if (amount >= 0) {
            this.balance = amount;
        } else {
            throw new IllegalArgumentException("Balance cannot be negative: " + amount);
        }
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        Account acc = new Account(500.0);

        // acc.balance = -999; // Compile Error: balance has private access in Account

        acc.setBalance(200.0);
        System.out.println(acc.getBalance()); // Output: 200.0

        // Reflection bypass (JVM does not prevent at runtime if setAccessible used)
        java.lang.reflect.Field f = Account.class.getDeclaredField("balance");
        f.setAccessible(true);
        f.set(acc, -999.0); // JVM allows this — language enforcement is already gone
        System.out.println(acc.getBalance()); // Output: -999.0  ← invariant violated!
    }
}
```

### Cause-Effect Chain

Field declared `private`
→ Java compiler rejects any `obj.field` access from outside the class at compile time
→ All external mutations must pass through public setter methods
→ Setter methods can validate inputs and enforce class invariants
→ Internal representation can change freely without breaking callers
→ JVM access flags in `.class` record `private`, but `setAccessible(true)` via reflection bypasses them at runtime
→ Language enforcement (compile-time) is the real protective layer; JVM enforcement is a softer runtime check that reflection can override
