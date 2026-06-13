# Common Java Core Interview Questions - Part 1

## Learning Goal

This file covers foundational Java Core interview questions regarding JVM architecture, pass-by-value rules, String memory structure, and Collection Internals (HashMap/ArrayList).

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `How are JVM, JDK, and JRE different?` | JDK is the development toolkit, JRE is the runtime environment, and JVM executes bytecode. |
| `Does Java pass references?` | Java is strictly pass-by-value. It passes copies of reference values (pointers), not variables themselves. |
| `What is the difference between == and .equals()?` | `==` checks reference/memory address equality; `.equals()` checks logical value equality. |
| `Why is String immutable?` | For security, synchronization safety, caching hashcodes, and sharing in the String Pool. |
| `How are String, StringBuilder, and StringBuffer different?` | String is immutable; StringBuilder is mutable and non-thread-safe; StringBuffer is mutable and synchronized. |
| `How does HashMap work?` | Uses an array of buckets, hashing keys, handling collisions via linked lists and tree nodes. |
| `What improvements did HashMap have in Java 8?` | Treeification of buckets containing 8 or more items, reducing worst-case lookup from O(N) to O(log N). |
| `How are ArrayList and LinkedList different?` | ArrayList uses a dynamic array (O(1) access); LinkedList uses a doubly-linked list (O(1) insertion/deletion). |

---

## Detailed Notes

### How are JVM, JDK, and JRE different?

- **JVM (Java Virtual Machine)**: The engine that executes Java bytecode (`.class` files). It handles memory management (Stack/Heap), Garbage Collection, and JIT compilation to convert bytecode to native machine code.
- **JRE (Java Runtime Environment)**: Includes the JVM and core class libraries (`java.lang`, `java.util`, etc.) required to *run* Java applications.
- **JDK (Java Development Kit)**: Includes the JRE and development tools (compiler `javac`, packager `jar`, debugger, etc.) required to *write* and compile Java programs.

---

### Does Java pass references?

Java is **strictly pass-by-value**. When an object reference is passed to a method, Java copies the value of the reference (the memory address pointer).
- **The rule**: You can modify the *contents* of the object because both references point to the same memory location, but you cannot change the caller's reference itself.

```java
public class ReferenceTest {
    public static void main(String[] args) {
        User user = new User("Alice");
        changeName(user); // Modifies the object's contents
        System.out.println(user.name); // Prints "Bob"

        reassign(user); // Tries to reassign the reference
        System.out.println(user.name); // Still prints "Bob" (no change)
    }

    static void changeName(User u) {
        u.name = "Bob";
    }

    static void reassign(User u) {
        u = new User("Charlie"); // Only reassigns the local copied parameter
    }
}
```

---

### == vs .equals()

- **`==`**: Compares primitives for value equality, or object references to check if they point to the exact same memory location on the heap.
- **`.equals()`**: A method in `java.lang.Object`. By default, it uses `==` for identity comparison. Classes like `String`, `Integer`, and `Double` override it to perform **logical comparison** of actual data.

---

### String Immutability & Builders

Once a `String` object is created in Java, its value cannot be modified.

- **Why is String immutable?**:
  1. **String Pool**: Allows sharing of string literals to save heap space.
  2. **Security**: Strings represent database URLs, file paths, and network connections; immutability prevents malicious tampering.
  3. **Thread Safety**: Can be shared across threads without synchronization.
  4. **HashCode Caching**: The hashcode is cached when the string is created, making it extremely fast when used as a key in a `HashMap`.

- **String vs StringBuilder vs StringBuffer**:
  - `String`: Immutable. Every modification creates a new object.
  - `StringBuilder`: Mutable and designed for single-thread operations. Fast.
  - `StringBuffer`: Mutable but thread-safe. Uses synchronized methods, adding performance overhead.

---

### HashMap & Java 8 Improvements

A `HashMap` stores key-value pairs using hashing logic.

- **Internal Structure**: Uses an array of nodes (buckets).
  1. Calculates the key's hashcode using `hash(key)`.
  2. Maps the hash value to an index in the bucket array: `index = hash & (n - 1)`.
  3. If multiple keys map to the same index (collision), they are chained in a Linked List.
- **Java 8 Improvement**:
  - If a bucket's linked list grows past a threshold of **8** elements (and total table capacity >= 64), the linked list is converted to a **Red-Black Tree (Treeification)**.
  - This improves the worst-case lookup time from **O(N)** to **O(log N)**, protecting against Denial of Service attacks that intentionally trigger hash collisions.

---

### ArrayList vs LinkedList

- **ArrayList**:
  - Implements `List` using a resizable array.
  - Random access is **O(1)**.
  - Inserting/deleting in the middle is **O(N)** because elements must be shifted.
- **LinkedList**:
  - Implements `List` using a doubly-linked list.
  - Random access is **O(N)** because it must traverse from head or tail.
  - Inserting/deleting is **O(1)** if the node reference is already known (just updates pointers).

---

## Common Mistakes & Traps

### 1. The Pass-by-Value Reference Trap
Assuming a method can reassign an outer reference variable:
```java
void modify(List<String> list) {
    list = new ArrayList<>(); // Incorrect: Caller's list reference is unaffected!
    list.add("newVal");
}
```

### 2. Calling `equals()` on Null
Calling `equals()` on a null reference throws a `NullPointerException`. Always place constants or verified non-nulls on the left side of `.equals()`.
```java
String status = getStatus();
if (status.equals("ACTIVE")) {} // Crash if status is null!
if ("ACTIVE".equals(status)) {} // Safe!
```
