# Generics in Java – Detailed Theory

## 1. Generic Class
- **Definition:** A class that declares one or more type parameters, allowing it to operate on objects of various types while providing compile‑time type safety.
- **Java rule:** The type parameter list appears after the class name, e.g. `class Box<T> { private T value; … }`. The type parameter can be used anywhere a normal type is allowed inside the class body.
- **Valid use case:** Containers that hold any type – `Box<T>`, `Pair<K,V>`.
- **Failure mode:** Using a raw type (`Box` without `<T>`) discards generic information, leading to unchecked conversions and possible `ClassCastException` at runtime.

## 2. Generic Method
- **Definition:** A method that introduces its own type parameters, independent of the class’s type parameters.
- **Java rule:** Type parameters are declared before the return type, e.g. `public static <T> T identity(T obj) { return obj; }`.
- **Valid use case:** Utility methods like `Collections.max(Collection<? extends T>)` or `Arrays.asList(T... a)` that work for any type.
- **Failure mode:** Omitting the type parameter list causes the method to fall back to raw types, which disables compile‑time checks.

## 3. Generic Interface
- **Definition:** An interface that declares type parameters, enabling implementations to specify concrete types.
- **Java rule:** Similar to classes – `interface Comparable<T> { int compareTo(T o); }`.
- **Valid use case:** Contracts that operate on a specific type, e.g., `Comparator<T>`, `Iterable<T>`.
- **Failure mode:** Implementing the raw form (`Comparable`) loses generic guarantees; the compiler will emit unchecked warnings.

## 4. Type Parameter
- **Definition:** A placeholder name (usually a single capital letter) representing an unknown type.
- **Common conventions:** `T` – type, `E` – element, `K` – key, `V` – value, `N` – number, `S,U,V` – multiple types.
- **Scope:** Visible only inside the generic declaration (class, method, interface).

## 5. Multiple Type Parameters
- **Syntax:** Separate with commas, e.g. `class MapEntry<K, V> { private K key; private V value; }`.
- **Use case:** Data structures that need more than one type, such as `Map<K,V>`, `BiFunction<T,U,R>`.

## 6. Bounded Type Parameter
- **Syntax:** `T extends Bound` where `Bound` can be a class or interface (or a combination via `&`).
- **Example:** `class NumericBox<T extends Number> { private T value; }`.
- **Rule:** The bound restricts the set of allowed types; inside the class you can call methods defined by the bound.
- **Failure mode:** Trying to instantiate with an unrelated type (`new NumericBox<String>()`) results in a compilation error.

## 7. Wildcards (`?`)
### 7.1 Unbounded Wildcard – `?`
- **Meaning:** Unknown type. Useful when you only need to read from a collection.
- **Example:** `void printAll(List<?> list) { for (Object o : list) System.out.println(o); }`.

### 7.2 Upper‑Bounded Wildcard – `<? extends T>`
- **Meaning:** Some unknown subtype of `T`.
- **PECS (Producer Extends):** Use when the generic object **produces** values of type `T`.
- **Example:** `List<? extends Number> numbers = List.of(1, 2.5); // read‑only`
- **Failure mode:** You cannot add elements (except `null`) because the exact subtype is unknown.

### 7.3 Lower‑Bounded Wildcard – `<? super T>`
- **Meaning:** Some unknown supertype of `T`.
- **PECS (Consumer Super):** Use when the generic object **consumes** values of type `T`.
- **Example:** `List<? super Integer> ints = new ArrayList<Number>(); ints.add(10);`
- **Failure mode:** When reading, you only get `Object` because the exact supertype is unknown.

## 8. PECS – Producer Extends, Consumer Super
- **Guideline:** 
  - If a generic **produces** values → use `extends`.
  - If it **consumes** values → use `super`.
- **Typical APIs:**
  - `Collections.copy(List<? super T> dest, List<? extends T> src)`
  - `Stream<T> map(Function<? super T, ? extends R>)`

## 9. Generics with Collections
| Collection | Typical Declaration | Reason |
|------------|--------------------|--------|
| `List` | `List<E>` – `E` is the element type. | Allows type‑safe addition/retrieval.
| `Set` | `Set<E>` – no duplicate elements of type `E`. |
| `Map` | `Map<K,V>` – `K` key, `V` value. | Enables compile‑time checking of both key and value types.
| `Queue` | `Queue<E>` – FIFO semantics. |
| `Deque` | `Deque<E>` – double‑ended queue. |
| `Optional` | `Optional<T>` – container for possibly‑absent value. |

**Examples:**
```java
List<String> names = new ArrayList<>();
Map<Integer, String> idToName = new HashMap<>();
Queue<Runnable> tasks = new ArrayDeque<>();
```
When you need flexibility you can use wildcards:
```java
void processAll(List<? extends Number> numbers) { ... }
void addAll(List<? super Integer> ints) { ints.add(1); }
```

## 10. Type Erasure
- **What happens:** At compile time, generic type information is removed. The bytecode contains only the **raw type** and casts are inserted where needed.
- **Consequences:**
  - No runtime generic type checks.
  - You cannot overload methods that differ only by generic type parameters.
  - `instanceof` cannot be used with a generic type (`if (obj instanceof List<String>)` is illegal).

## 11. Raw Types
- **Definition:** Using a generic class or interface without specifying type arguments, e.g., `List raw = new ArrayList();`.
- **Effect:** Disables generic safety, triggers unchecked warnings, and can cause `ClassCastException` at runtime.
- **When to avoid:** Almost always; only use when interacting with legacy code that predates generics (Java 5).

## 12. Generic Limitations
| Limitation | Explanation | Work‑around |
|------------|-------------|------------|
| **No generic arrays** | `new T[10]` illegal because of type erasure. | Use `List<T>` or `Array.newInstance(clazz, size)` with a `Class<T>` token.
| **No generic primitives** | Type parameters must be reference types. | Use wrapper classes (`Integer`, `Double`).
| **No static fields of type parameter** | Static members belong to the class, not to a particular type argument. | Use non‑static fields or capture type with a `Class<T>` argument.
| **Cannot create generic subclasses of non‑generic classes with specific type arguments** | Example: `class MyStringList extends ArrayList<String>` is allowed, but you cannot later treat it as `ArrayList<T>`.
| **Type inference limits** | Complex nested generics may require explicit type arguments. | Provide explicit type parameters or use helper methods.

## 13. Putting It All Together – Example
```java
public class Pair<K, V> {
    private final K key;
    private final V value;
    public Pair(K key, V value) { this.key = key; this.value = value; }
    public K getKey() { return key; }
    public V getValue() { return value; }
}

// Using bounded type parameters and PECS
public static <T extends Number> double sum(List<? extends T> numbers) {
    double total = 0;
    for (T n : numbers) total += n.doubleValue(); // safe: T is a Number
    return total;
}

public static void addIntegers(List<? super Integer> list) {
    list.add(1); // safe: list can accept Integer or any of its supertypes
}
```
**Failure scenarios:**
- Passing a `List<Object>` to `sum` – compilation error because `Object` does not extend `Number`.
- Trying to add to a `List<? extends Number>` – compile‑time error: cannot add any element except `null`.

---
**Takeaway:** Generics give Java strong static typing for collections and APIs while preserving backward compatibility through type erasure. Understanding the rules, bounds, wildcards, and their proper placement (PECS) prevents common pitfalls such as unchecked casts and runtime `ClassCastException`.
