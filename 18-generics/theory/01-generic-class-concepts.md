# Generics – Part 1: Generic Class, Method, Interface, Type Parameters, Bounds

## 1. Generic Class

**Definition:** A class that declares one or more type parameters enclosed in `<>` after the class name. The parameter acts as a placeholder for a concrete type supplied at instantiation.

**Java rule:**
```java
class Box<T> {
    private T value;
    public Box(T value) { this.value = value; }
    public T get() { return value; }
}
```
- `T` can be used anywhere a normal type is allowed inside the class body (fields, method parameters, return types).
- The compiler checks type correctness at compile time; the bytecode uses the raw type `Object` (or the bound) after type erasure.

**Valid use cases:**
- `Box<String>`, `Box<Integer>` — single-typed containers.
- `Pair<K, V>` — data holders with multiple types.
- `Optional<T>` (JDK) — wrap nullable values safely.

**Failure mode:**
```java
Box rawBox = new Box("hello");   // raw type – no compile-time check
rawBox = new Box(42);            // silently allowed; ClassCastException risk later
Integer n = (Integer) rawBox.get(); // runtime ClassCastException
```
Using the raw type disables all generic safety. Always supply type arguments.

---

## 2. Generic Method

**Definition:** A method that introduces its own type parameters, independent of any class-level type parameters.

**Java rule:**
- Type parameters go **before the return type**.
```java
public static <T> T identity(T obj) { return obj; }
public static <T extends Comparable<T>> T max(T a, T b) {
    return a.compareTo(b) >= 0 ? a : b;
}
```
- The compiler infers `T` from the call-site argument; you can also specify it explicitly: `MyUtil.<String>identity("hi")`.

**Valid use cases:**
- `Collections.max(Collection<? extends T>)` — works for any comparable element type.
- `Arrays.asList(T... a)` — converts varargs to a typed list.
- Utility/helper methods that should be reusable across types.

**Failure mode:**
```java
// Missing <T> — compiler falls back to raw types
public static Object broken(Object obj) { return obj; }  // no generic safety
```
Without the type-parameter declaration, the compiler cannot enforce type consistency between parameters and return type.

---

## 3. Generic Interface

**Definition:** An interface that declares type parameters, forcing implementations to work with a specific type.

**Java rule:**
```java
interface Transformer<T, R> {
    R transform(T input);
}

class StringToInt implements Transformer<String, Integer> {
    public Integer transform(String s) { return s.length(); }
}
```
- Implementations must either supply concrete types (`Transformer<String, Integer>`) or remain generic (`class Proxy<T, R> implements Transformer<T, R>`).

**Key JDK examples:**
- `Comparable<T>` — total ordering.
- `Iterable<T>` — for-each support.
- `Comparator<T>` — external ordering.
- `Function<T, R>` — single-argument function.

**Failure mode:**
```java
class Broken implements Comparable {   // raw Comparable – no type safety
    public int compareTo(Object o) { ... }
}
// compareTo now accepts any Object; the compiler cannot catch:
broken.compareTo(42);   // no compile error even for wrong type
```

---

## 4. Type Parameter Conventions

**Definition:** A placeholder name declared in `<>` that represents an unknown type within a generic declaration.

**Standard single-letter conventions:**
| Letter | Meaning |
|--------|---------|
| `T` | Type (general) |
| `E` | Element (collections) |
| `K` | Key (maps) |
| `V` | Value (maps) |
| `N` | Number |
| `R` | Return type (functions) |
| `S`, `U` | Second, third type (multiple params) |

**Scope:** The parameter is only visible inside the generic class/method/interface where it is declared.

**Practical note:** Names like `T1`, `T2` or descriptive names (`Source`, `Destination`) are allowed but the single-letter convention dominates JDK APIs and is expected in code reviews.

---

## 5. Multiple Type Parameters

**Syntax:**
```java
class Pair<K, V> {
    private final K key;
    private final V value;
    public Pair(K key, V value) { this.key = key; this.value = value; }
    public K getKey()   { return key; }
    public V getValue() { return value; }
}
```

**Use cases:**
- `Map<K, V>` — maps key type to value type.
- `BiFunction<T, U, R>` — function with two input types and one return type.
- `Either<L, R>` (common in functional libraries) — holds one of two alternatives.

**Rule:** All type parameters must be distinct identifiers separated by commas. The order matters only in how the class uses them internally.

---

## 6. Bounded Type Parameter: `<T extends Bound>`

**Definition:** Restricts the set of valid type arguments to a type `T` that is a subtype of `Bound`.

**Syntax:**
```java
// Upper bound – single class or interface
<T extends Number>

// Multiple bounds – class must come first, then interfaces
<T extends Number & Comparable<T> & Serializable>
```

**Why upper bounds matter:**
Inside the class/method you can **call methods of the bound** on `T`:
```java
public static <T extends Number> double sum(List<T> list) {
    double total = 0;
    for (T n : list) total += n.doubleValue(); // doubleValue() defined on Number
    return total;
}
```
Without the bound, `n.doubleValue()` would be a compile error — `T` would be treated as `Object`.

**Failure mode:**
```java
sum(List.of("a", "b"));    // compile error: String does not extend Number
new NumericBox<String>();   // compile error
```

**Wildcard vs. bounded parameter:**
- `<T extends Number>` declares a new named type variable — use in methods when you need to reference `T` multiple times.
- `<? extends Number>` is an anonymous wildcard — use in method parameters when you only need to read.

## Reference Links

- https://docs.oracle.com/javase/tutorial/java/generics/types.html
- https://docs.oracle.com/javase/tutorial/java/generics/methods.html
- https://docs.oracle.com/javase/tutorial/java/generics/bounded.html
- https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html
