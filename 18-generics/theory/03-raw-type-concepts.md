# Generics – Part 3: Raw Types and Generic Limitations

## 1. Raw Types

**Definition:** A raw type is a generic class or interface used **without** any type arguments.

```java
// Generic (correct)
List<String> names = new ArrayList<>();

// Raw type (avoid)
List rawList = new ArrayList();
```

**How they work:** The compiler treats the raw type as if all type parameters were replaced with `Object`. All generic safety is disabled.

**Effect on code:**
```java
List rawList = new ArrayList();
rawList.add("hello");
rawList.add(42);            // no compile error — anything goes
String s = (String) rawList.get(1);  // ClassCastException at runtime!
```

**Compiler warnings:** Using raw types triggers `unchecked` warnings:
```
Note: MyClass.java uses unchecked or unsafe operations.
```

**When raw types appear legitimately:**
1. Interoperating with pre-Java 5 legacy APIs that have no generic versions.
2. Inside `instanceof` checks (you cannot use `instanceof List<String>` anyway):
   ```java
   if (obj instanceof List) {         // raw — necessary here
       List<?> list = (List<?>) obj;  // immediately switch to wildcard
   }
   ```

**Key rule:** As soon as you assign a raw type to a variable, use `List<?>` (not the raw type) for the remainder of the code.

---

## 2. Generic Limitations

Java generics have several built-in restrictions, almost all caused by **type erasure**.

### 2.1 Cannot Instantiate Type Parameters

```java
class Container<T> {
    T value = new T();   // COMPILE ERROR — T erased to Object at runtime
}
```

**Work-around:** Pass a `Class<T>` token:
```java
class Container<T> {
    T value;
    Container(Class<T> clazz) throws Exception {
        value = clazz.getDeclaredConstructor().newInstance();
    }
}
```

---

### 2.2 Cannot Create Generic Arrays

```java
T[] arr = new T[10];              // COMPILE ERROR
List<String>[] lists = new ArrayList<String>[3];  // COMPILE ERROR
```

**Why:** Arrays carry their component type at runtime (`String[]` knows it is a `String[]`). After erasure, `T[]` would just be `Object[]`, breaking array type-safety.

**Work-around:**
```java
// Option 1: use List<T>
List<T> list = new ArrayList<>();

// Option 2: unchecked cast with class token
@SuppressWarnings("unchecked")
T[] arr = (T[]) new Object[10];

// Option 3: Array.newInstance
T[] arr = (T[]) Array.newInstance(clazz, 10);
```

---

### 2.3 Cannot Use Primitive Type Arguments

```java
List<int> nums = new ArrayList<>();   // COMPILE ERROR
```

**Why:** Generics are implemented via `Object` references; primitives are not objects.

**Work-around:** Use wrapper classes. Autoboxing makes this mostly transparent:
```java
List<Integer> nums = new ArrayList<>();
nums.add(1);            // autoboxed to Integer
int n = nums.get(0);    // unboxed to int
```

**Performance note:** Autoboxing has overhead. For performance-critical code, consider `int[]` or third-party primitive collections.

---

### 2.4 Cannot Have Static Fields of Type Parameter Type

```java
class Bag<T> {
    static T instance;   // COMPILE ERROR — static belongs to class, not T
}
```

**Why:** `static` fields are shared across all instances of `Bag`. `Bag<String>` and `Bag<Integer>` share the same class, so a single `T` field is nonsensical.

**Work-around:** Make the field non-static, or use a separate `Class<T>` parameter.

---

### 2.5 Cannot Catch or Throw Generic Exceptions

```java
class MyException<T> extends Exception { ... }   // COMPILE ERROR (extends Throwable)
// (legal to declare but not to catch with a generic type argument)

<T extends Exception> void process() throws T { }   // OK to declare
try { } catch (T e) { }                             // COMPILE ERROR in catch
```

**Why:** The JVM matches exception types at runtime; erased types cannot be used in `catch`.

---

### 2.6 Cannot Overload Methods Whose Parameter Lists Erase to the Same Signature

```java
void print(List<String> list) { }
void print(List<Integer> list) { }  // COMPILE ERROR — both erase to print(List)
```

---

### 2.7 Cannot Use `instanceof` with Parameterized Types

```java
if (obj instanceof List<String>) { }   // COMPILE ERROR — type info erased
if (obj instanceof List<?>)      { }   // OK — unbounded wildcard is allowed
if (obj instanceof List)         { }   // OK — raw type check
```

---

## Summary Table

| Limitation | Cause | Work-around |
|------------|-------|-------------|
| `new T()` | Erasure | `Class<T>` token + reflection |
| `new T[n]` | Array type reification | `List<T>` or `(T[]) new Object[n]` |
| `List<int>` | Primitives not objects | `List<Integer>` + autoboxing |
| `static T field` | Static shared across type params | Non-static field |
| `catch (T e)` | JVM needs concrete type | Specific exception type |
| Overloaded methods with same erasure | Same bytecode signature | Rename methods |
| `instanceof List<String>` | Type erased | Use `List<?>` or raw |

## Reference Links

- https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html
- https://docs.oracle.com/javase/tutorial/java/generics/erasure.html
- https://docs.oracle.com/javase/tutorial/java/generics/rawTypes.html
