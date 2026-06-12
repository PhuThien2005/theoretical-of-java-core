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
- **`private`:** Restricts access strictly to members of the defining class. Highly recommended for all instance variables.
- **`default` (package-private):** Members are accessible only by classes in the same package.
- **`protected`:** Accessible by classes in the same package, and by subclasses located in other packages.
  *Note:* A subclass in a different package can access a `protected` member of a parent class only through inheritance (using reference variables of the subclass type), not via reference variables of the parent class type.
- **`public`:** Unrestricted access.

---

## Getter and Setter Design Patterns

To interact with private fields, classes expose public accessor methods (getters) and mutator methods (setters). This pattern provides multiple software design benefits:

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
