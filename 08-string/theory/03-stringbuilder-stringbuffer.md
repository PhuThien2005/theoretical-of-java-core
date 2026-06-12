# StringBuilder and StringBuffer

When performing frequent operations on strings (such as inside loops), the immutable nature of the `String` class leads to high memory overhead because each modification creates a new object in the Heap. To solve this, Java provides mutable character sequences: `StringBuilder` and `StringBuffer`.

---

## Internal Buffer and Capacity

Both `StringBuilder` and `StringBuffer` extend a package-private abstract superclass called `AbstractStringBuilder`.
- They manage a mutable byte/char array buffer to store characters.
- **Length:** The number of characters actually stored in the buffer.
- **Capacity:** The total size of the allocated buffer array. By default, a new builder has an initial capacity of **16 characters** plus the length of the string used to initialize it.

### Resizing Algorithm
When you append content that exceeds the current capacity, the JVM allocates a larger array and copies the old contents. The expansion formula is:
$$\text{New Capacity} = (\text{Old Capacity} \times 2) + 2$$
If this new capacity is still insufficient, the JVM sets the capacity to the exact length of the new content.

---

## Detailed Feature Comparison

| Feature | `String` | `StringBuilder` (Java 5+) | `StringBuffer` (Java 1.0) |
| :--- | :--- | :--- | :--- |
| **Mutability** | Immutable | Mutable | Mutable |
| **Thread Safety** | **Safe** (due to immutability) | **Not Safe** | **Safe** (Synchronized) |
| **Performance** | Slowest (for operations) | **Fastest** | Slow (due to locking overhead) |
| **Storage Area** | Heap & String Pool | Heap | Heap |

---

## Thread Safety and Lock Contention

- **`StringBuffer`:** All write operations (like `append()`, `insert()`, `delete()`) are marked with the `synchronized` keyword. This ensures that only one thread can modify the buffer at a time. However, this synchronization has a performance cost:
  - Even in a single-threaded program, acquiring and releasing monitor locks introduces thread synchronization overhead.
  - In multi-threaded environments, if multiple threads attempt to write to the same `StringBuffer` concurrently, it causes **lock contention**, blocking threads and degrading performance.
- **`StringBuilder`:** Removes all `synchronized` keywords. It is not thread-safe. If multiple threads write to a single `StringBuilder` instance simultaneously, it will result in corrupted data or index out of bounds exceptions. However, for local variables inside a method, `StringBuilder` is always preferred since local variables are thread-confined.

---

## Key API Methods

### 1. `append()`
Appends the string representation of any type to the end of the sequence. Supports method chaining.
```java
StringBuilder sb = new StringBuilder("Base");
sb.append("-").append(12.34).append(true); // "Base-12.34true"
```

### 2. `insert(int offset, Object obj)`
Inserts characters at the specified index.
```java
StringBuilder sb = new StringBuilder("Jva");
sb.insert(1, "a"); // sb is now "Java"
```
- Throws `StringIndexOutOfBoundsException` if `offset < 0` or `offset > length()`.

### 3. `delete(int start, int end)` and `deleteCharAt(int index)`
- `delete()`: Removes characters from `start` (inclusive) to `end` (exclusive).
- `deleteCharAt()`: Removes a single character.
```java
StringBuilder sb = new StringBuilder("012345");
sb.delete(2, 4); // Removes indices 2 and 3 -> sb is now "0145"
```

### 4. `replace(int start, int end, String str)`
Replaces characters from `start` to `end` with `str`.
```java
StringBuilder sb = new StringBuilder("Hello World");
sb.replace(6, 11, "Java"); // sb is now "Hello Java"
```

### 5. `reverse()`
Reverses the sequence.
```java
StringBuilder sb = new StringBuilder("live");
sb.reverse(); // sb is now "evil"
```

### 6. `setLength(int newLength)`
Sets the length of the character sequence:
- If `newLength` is smaller than the current length, the character sequence is truncated.
- If `newLength` is larger, the buffer is padded with null characters (`\u0000`).
```java
StringBuilder sb = new StringBuilder("Java");
sb.setLength(2); // sb is now "Ja"
```

### 7. `ensureCapacity(int minimumCapacity)`
Forces the buffer to allocate space for at least `minimumCapacity` characters, preventing multiple resizes if you know the final content size beforehand.
