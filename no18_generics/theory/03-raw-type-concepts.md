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

## Why Raw Types Exist and Their Dangers

Raw types exist in the Java language solely to support backwards compatibility with legacy code written before Java 5. Prior to the introduction of generics, collections simply held `Object` references, and raw types allow this older code to compile and run on modern runtimes without modification. However, using raw types in new code bypasses all of the compiler's generic type-safety verifications. Because the compiler does not perform type checking on raw collections, it allows developers to insert mismatched types into a collection without any compile-time warnings. The actual type safety violation is then deferred to runtime, where reading an element and attempting to cast it to an incorrect type triggers a `ClassCastException` and crashes the application.

### Mental Model

```text
Developer Intent: List of Strings
[List rawList] = new ArrayList() ---> Accepts "Hello" (OK)
                                 ---> Accepts 123 (Unchecked: OK!)

Runtime Read:
String s = (String) rawList.get(1) ---> Casting Integer (123) to String
                                   ---> CRASH: ClassCastException
```

### Code Example

```java
import java.util.ArrayList;
import java.util.List;

public class RawTypeDanger {
    public static void main(String[] args) {
        // Raw type usage bypasses compile-time checks
        List rawList = new ArrayList();
        rawList.add("Safe String");
        rawList.add(Integer.valueOf(100)); // Compiles, but triggers unchecked warning

        System.out.println("Elements added successfully.");

        try {
            // This line compiles but throws an exception at runtime
            String element = (String) rawList.get(1); 
            System.out.println(element);
        } catch (ClassCastException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
            // Output: Caught expected exception: class java.lang.Integer cannot be cast to class java.lang.String
        }
    }
}
```

### Cause-Effect Chain

Use raw types &rarr; Compiler disables generic type checks &rarr; Mismatched objects inserted into collection &rarr; Code compiles without errors &rarr; Developer attempts to read and cast the object at runtime &rarr; JVM throws ClassCastException.

## Why Generics Do Not Support Primitives

Due to compile-time type erasure, all Java generic type parameters are erased to their leftmost bound, which is typically `Object` if unbounded. In the Java Virtual Machine, references to objects are represented in bytecode by reference slots (using the `a` prefix in bytecode instructions like `aload` and `astore`). Primitive types, such as `int` or `char`, do not inherit from `java.lang.Object` and are stored using different binary sizes and bytecode instructions (like `iload` for integers). Because the JVM cannot store a primitive directly in a memory slot designated for object references, generics cannot support primitives natively. As a result, Java requires wrapper classes (like `Integer`) and uses automatic conversion (autoboxing) to wrap primitives in heap-allocated objects when stored in generic structures.

### Mental Model

```text
Memory representation in JVM:
Generic Box<T> (Erased to Object reference):
[ Reference Slot (4/8 bytes) ] ---> Points to Heap Object: [ Integer (123) ]
                                                            (Autoboxed wrapper)

Cannot store primitive directly:
[ Reference Slot (4/8 bytes) ] -x-> Cannot hold raw binary 32-bit int [ 123 ]
```

### Code Example

```java
import java.util.ArrayList;
import java.util.List;

public class PrimitiveGenericsLimit {
    public static void main(String[] args) {
        // List<int> list = new ArrayList<>(); // Compile Error
        
        List<Integer> list = new ArrayList<>();
        
        // Autoboxing: compiler automatically converts primitive 42 into Integer.valueOf(42)
        list.add(42); 
        
        // Unboxing: compiler converts retrieved Integer back to primitive int via intValue()
        int val = list.get(0);
        
        System.out.println("Value: " + val); // Output: Value: 42
    }
}
```

### Cause-Effect Chain

Generics undergo Type Erasure &rarr; Type parameters erased to Object references &rarr; JVM represents references differently from primitive binaries &rarr; Primitives cannot occupy reference-only memory slots &rarr; List<int> is forbidden &rarr; Must use List<Integer> with boxing.

## Why Generic Array Creation and Runtime Type Checks Are Forbidden

In Java, arrays are reified, meaning they retain full knowledge of their element type at runtime and enforce type safety through JVM checks. If you attempt to store an incompatible element in an array, the JVM immediately throws an `ArrayStoreException` at runtime. Conversely, generics are erased, meaning all type parameter information is discarded after compilation. If generic array creation like `new T[10]` or `new List<String>[10]` were permitted, the JVM would have no way to enforce the correct element type at runtime because the actual component type would be erased to `Object[]`. For similar reasons, runtime checks such as `instanceof List<String>` are forbidden, because the type parameter is missing at runtime, leaving the JVM only capable of checking the raw type `instanceof List`.

### Mental Model

```text
Arrays (Reified - Type Known at Runtime):
String[] strings = new String[5]; ---> JVM knows this is [Ljava.lang.String;
strings[0] = "hello";             ---> OK
((Object[]) strings)[1] = 123;    ---> JVM checks runtime type ---> Throws ArrayStoreException

Generics (Erased - Type Lost at Runtime):
List<String> list = new ArrayList<>(); ---> JVM only knows this is List
```

### Code Example

```java
import java.util.ArrayList;
import java.util.List;

public class ArrayAndInstanceofLimit {
    public static void main(String[] args) {
        // 1. Generic array creation is forbidden:
        // List<String>[] listArray = new ArrayList<String>[5]; // Compile Error

        // 2. Runtime type checks with generics are forbidden:
        List<String> stringList = new ArrayList<>();
        // if (stringList instanceof ArrayList<String>) {} // Compile Error
        
        // Unbounded wildcard or raw type instanceof is allowed:
        if (stringList instanceof ArrayList<?>) {
            System.out.println("Check passed using wildcard."); // Output: Check passed using wildcard.
        }
    }
}
```

### Cause-Effect Chain

Arrays are reified &rarr; Arrays enforce their exact element type at runtime via JVM &rarr; Generics are erased &rarr; Generic type parameter is lost at runtime &rarr; JVM cannot enforce type safety of Generic Arrays at runtime &rarr; Generic array creation is banned.

## Reference Links

- https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html
- https://docs.oracle.com/javase/tutorial/java/generics/erasure.html
- https://docs.oracle.com/javase/tutorial/java/generics/rawTypes.html
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-4.html#jls-4.8 (Raw Types)
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-10.html (Arrays)

