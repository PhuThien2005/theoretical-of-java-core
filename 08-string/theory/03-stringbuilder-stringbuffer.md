# StringBuilder and StringBuffer

When performing frequent operations on strings (such as inside loops), the immutable nature of the `String` class leads to high memory overhead because each modification creates a new object in the Heap. To solve this, Java provides mutable character sequences: `StringBuilder` and `StringBuffer`.

---

## Case Study: Why String Concatenation in a Loop is $O(n^2)$

### The Problem: Naive Concatenation

Consider a loop that builds a string of $n$ numbers:

```java
// DO NOT DO THIS in real code
String s = "";
for (int i = 0; i < n; i++) {
    s += i; // Or s = s + i;
}
```

Under the hood, the compiler translates `s += i` into:
```java
s = new StringBuilder().append(s).append(i).toString();
```

In every iteration:
1. A new `StringBuilder` is instantiated.
2. The entire content of the existing string `s` is copied character-by-character into the builder.
3. The new integer/character is appended.
4. `toString()` is called, which copies the builder's character array to construct a new `String` object.

If the loop runs $n$ times and each iteration appends a small string, the length of `s` grows linearly. In iteration $k$, the JVM copies $k$ characters.
The total number of characters copied across all iterations is:
$$\text{Total Copies} = 1 + 2 + 3 + \dots + n = \frac{n(n + 1)}{2} = O(n^2)$$

This results in:
- **Quadratic time complexity ($O(n^2)$):** The execution time grows quadratically with $n$.
- **Memory Churn / GC Pressure:** $n$ temporary `StringBuilder` objects and $n$ temporary `String` objects are allocated and discarded, triggering frequent garbage collection pauses.

### The Solution: `StringBuilder`

By initializing a single `StringBuilder` outside the loop, we avoid creating temporary objects and redundant array copies:

```java
StringBuilder sb = new StringBuilder();
for (int i = 0; i < n; i++) {
    sb.append(i);
}
String s = sb.toString();
```

Here:
1. Only **one** `StringBuilder` is allocated.
2. The `append()` method modifies the internal `byte[]`/`char[]` buffer in-place.
3. Array copying only occurs when the buffer runs out of capacity. Because of the doubling strategy (`(capacity * 2) + 2`), resizing happens logarithmically ($O(\log n)$ times).
4. The amortized complexity of each `append()` is $O(1)$.
5. The total time complexity for the entire loop is **$O(n)$**.
6. Only **one** final `String` object is created when calling `.toString()` at the end.

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

---

## Common Mistakes

### 1. Recreating the `StringBuilder` Inside the Loop
Creating a new `StringBuilder` inside the loop defeats the purpose. The code still suffers from $O(n^2)$ copying and garbage collection overhead.
```java
// BAD: StringBuilder is still created in every iteration!
String s = "";
for (int i = 0; i < 1000; i++) {
    StringBuilder sb = new StringBuilder();
    sb.append(s).append(i);
    s = sb.toString();
}

// GOOD: Single StringBuilder outside the loop
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append(i);
}
String s = sb.toString();
```

### 2. Using `append()` with String Concatenation
Writing `sb.append(a + b)` instead of `sb.append(a).append(b)`. The former performs a string concatenation *before* passing the result to `append()`, creating a temporary `String` object and wasting memory.
```java
String first = "John";
String last = "Doe";
StringBuilder sb = new StringBuilder();

// BAD: Creates a temporary string "John Doe"
sb.append(first + " " + last);

// GOOD: Method chaining avoids temporary allocations
sb.append(first).append(" ").append(last);
```

### 3. Sharing `StringBuilder` Concurrently
`StringBuilder` is NOT thread-safe. If multiple threads append to a shared `StringBuilder` concurrently, characters may overwrite each other or throw `ArrayIndexOutOfBoundsException`.
If thread-safety is required, use `StringBuffer` (or manage external synchronization/thread-local buffers).
```java
// UNSAFE: Multiple threads modifying the same builder
StringBuilder sharedBuilder = new StringBuilder();
Runnable r = () -> {
    for (int i = 0; i < 100; i++) {
        sharedBuilder.append("A"); // Race condition!
    }
};
```

### 4. Ignoring Initial Capacity
If you know that the final string will be large (e.g., 100,000 characters), initializing a `StringBuilder` with the default capacity of 16 will force the JVM to resize the buffer array many times.
Always specify an estimated initial capacity if known:
```java
// Resizes multiple times: 16 -> 34 -> 70 -> 142 -> ...
StringBuilder sb1 = new StringBuilder(); 

// Resizes 0 times:
StringBuilder sb2 = new StringBuilder(100_000); 
```
