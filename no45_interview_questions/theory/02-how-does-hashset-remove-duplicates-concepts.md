# Common Java Core Interview Questions - Part 2

## Learning Goal

This file covers intermediate Java Core interview questions regarding Set operations, modifiers (final, static), exception inheritance, OOP relationships, and instance context.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `How does HashSet remove duplicates?` | Uses an internal `HashMap` to store elements as keys; duplicate checks rely on `hashCode()` and `equals()`. |
| `How are final, finally, and finalize different?` | `final` is a modifier; `finally` is a try-catch block segment; `finalize()` is a deprecated cleanup method. |
| `How are checked and unchecked exceptions different?` | Checked exceptions must be declared or caught at compile-time; unchecked exceptions represent runtime bugs. |
| `How are abstract class and interface different?` | Abstract classes allow state storage and single inheritance; interfaces support multiple implementation and default behavior. |
| `How are overload and override different?` | Overload is compile-time polymorphism (same name, diff parameters); Override is runtime polymorphism (parent-child relationship). |
| `Can static methods be overridden?` | No. They can only be hidden because static methods are resolved statically at compile-time using class types. |
| `Are constructors inherited?` | No. They must be declared in the subclass or invoked via parent constructor using `super()`. |
| `How are this and super different?` | `this` refers to the current class instance; `super` refers to the immediate parent class instance context. |

---

## Detailed Notes

### How does HashSet remove duplicates?

Under the hood, a `HashSet` is backed by a `HashMap` instance:
```java
public class HashSet<E> {
    private transient HashMap<E, Object> map;
    private static final Object PRESENT = new Object(); // Dummy value

    public boolean add(E e) {
        return map.put(e, PRESENT) == null; // HashMap returns null if key didn't exist
    }
}
```
- **Duplicate Detection**: When adding an element `e`, `HashSet` invokes `e.hashCode()` to find the appropriate bucket in the internal `HashMap`. If another element exists with the same hash code, it calls `e.equals(existingElement)`. If `equals` returns `true`, the duplicate is rejected/overwritten.

---

### final vs. finally vs. finalize()

- **`final`** — final: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
  - **Variable**: Reassignment is prohibited (constant).
  - **Method**: Cannot be overridden by subclasses.
  - **Class**: Cannot be extended (e.g., `String`, `Integer`).
- **`finally`** — finally: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`finalize()`** — finalize(): Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

---

### Checked vs. Unchecked Exceptions

- **Checked Exceptions**:
  - Direct subclasses of `Exception` (excluding `RuntimeException`).
  - Checked by the compiler. The program must either handle them in a `try-catch` block or declare them in the method signature using `throws` (e.g., `IOException`, `SQLException`).
- **Unchecked Exceptions**:
  - Subclasses of `RuntimeException` or `Error`.
  - Not checked at compile-time. They represent programming bugs or unrecoverable conditions (e.g., `NullPointerException`, `IndexOutOfBoundsException`, `OutOfMemoryError`).

---

### Abstract Class vs. Interface

| Feature | Abstract Class | Interface |
| --- | --- | --- |
| **Inheritance** | Single inheritance (`extends`). | Multiple inheritance (`implements`). |
| **State** | Can have instance variables (state). | Can only have `public static final` constants. |
| **Constructors** | Can have constructors. | Cannot have constructors. |
| **Methods** | Can have private, protected, concrete, and abstract methods. | All abstract methods are public by default. Can have `default` and `static` methods. |

---

### Overloading vs. Overriding

- **Method Overloading**:
  - Happens within the same class (or across parent/child).
  - Same method name, different parameter list (type, number, or order).
  - **Compile-time** (static) polymorphism.
- **Method Overriding**:
  - Happens between parent and child classes.
  - Same method name, same parameter list, same/covariant return type.
  - **Runtime** (dynamic) polymorphism.

---

### Can static methods be overridden?

No. Method overriding depends on dynamic binding at runtime (based on the actual object type on the heap). Static methods are bound at compile-time based on the declared **reference type** of the variable.
- If a subclass defines a static method with the same signature as a static method in the parent class, it is called **Method Hiding**, not overriding.

```java
class Parent {
    static void display() { System.out.println("Parent"); }
}
class Child extends Parent {
    static void display() { System.out.println("Child"); } // Hides Parent.display()
}

// Usage:
Parent p = new Child();
p.display(); // Prints "Parent" (compile-time type of reference p is Parent)
```

---

### Are constructors inherited?

No, constructors are not inherited by subclasses. 
- The subclass must define its own constructors. If a parent class does not have a default (no-argument) constructor, the child constructor must explicitly call one of the parent's parameterized constructors using `super(...)` as the very first statement.

---

### this vs. super

- **`this`** — this: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`super`** — super: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

---

## Common Mistakes & Traps

### 1. The HashSet/HashMap Contract Violation
Adding an object to a `HashSet` without overriding both `hashCode()` and `equals()`:
```java
class Student {
    String name;
    Student(String name) { this.name = name; }
    // No hashCode() or equals() overridden!
}

Set<Student> set = new HashSet<>();
set.add(new Student("Bob"));
set.add(new Student("Bob")); // Set now contains TWO elements because references are different!
```

### 2. Assuming `finally` always executes
`finally` runs almost always, but it will NOT run if:
- `System.exit(0)` is called in the try or catch block.
- The JVM crashes or suffers a power failure.
- The thread running the try-catch block is killed/interrupted externally.

---

## Why HashSet Leverages HashMap to Remove Duplicates

A `HashSet` in Java does not implement its own hashing collision-handling logic; instead, it delegates all storage and uniqueness checks to an internal `HashMap` instance. When a `HashSet` is instantiated, it initializes a private, transient `HashMap` where the set's elements serve as the map's keys, and a shared dummy object (`PRESENT`) is used as the constant value. Because a `HashMap` key collection must remain unique, calling `add(element)` performs a `map.put(element, PRESENT)` call under the hood. If the element is already mapped, `put()` returns the old value (`PRESENT`), prompting `add()` to return `false` to indicate a duplicate was rejected. By piggybacking on `HashMap`'s robust collision-resolution techniques (like linked lists and treeified buckets), Java avoids code duplication and ensures that element lookup, insertion, and deletion occur with O(1) average time complexity.

### Mental Model

```text
  HashSet: [add(Key1)] ──(delegates)──> HashMap: put(Key1, PRESENT)
  +-------------------------------------------------------+
  | Backing HashMap Keys (Set Elements)                   |
  |  - "Java" -> maps to PRESENT (dummy Object)           |
  |  - "Python" -> maps to PRESENT                        |
  |  - ["Java" added again -> put() returns PRESENT -> false] |
  +-------------------------------------------------------+
```

### Code Example

The code snippet below demonstrates how `HashSet` uses `add()` and how it internally calls the backing `HashMap`'s `put()` operation.

```java
import java.util.HashSet;

public class HashSetMechanismDemo {
    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();
        // map.put("Java", PRESENT) returns null -> add() returns true
        System.out.println(set.add("Java")); // Output: true
        // map.put("Java", PRESENT) returns PRESENT -> add() returns false
        System.out.println(set.add("Java")); // Output: false
    }
}
```

### Cause-Effect Chain

```text
Call hashSet.add(element)
  → Delegates internally to map.put(element, PRESENT)
  → HashMap computes hash(element) and locates target bucket index
  → HashMap scans bucket nodes checking key.equals(element)
  → IF matching key found: overwrites value with PRESENT, returns PRESENT (add() returns false)
  → IF matching key NOT found: creates new node, inserts key-value, returns null (add() returns true)
```
