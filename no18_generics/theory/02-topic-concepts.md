# Generics – Part 2: Wildcards, PECS, Generics with Collections, Type Erasure

## 1. Wildcards

A **wildcard** (`?`) represents an unknown type in a generic type argument position. Unlike a named type parameter (`T`), a wildcard cannot be referenced by name — it is anonymous.

### 1.1 Unbounded Wildcard `<?>`

**Meaning:** Any type. The list could hold `String`, `Integer`, anything.

**When to use:** When you only need to **read** elements as `Object`, or when the code is truly type-agnostic.

```java
void printAll(List<?> list) {
    for (Object o : list) System.out.println(o);
}
```

**Read:** ✅ Returns `Object`.  
**Write:** ❌ Cannot add any element (except `null`) because the actual type is unknown.

```java
List<?> list = new ArrayList<String>();
list.add("hello");   // COMPILE ERROR – type unknown, unsafe
list.add(null);      // OK – null is always safe
```

---

### 1.2 Upper-Bounded Wildcard `<? extends T>`

**Meaning:** Some unknown subtype of `T`. The list is a **producer** of `T` values.

**Read:** ✅ Returns `T` (or a subtype).  
**Write:** ❌ Cannot add (except `null`) — the exact subtype is unknown.

```java
void sumNumbers(List<? extends Number> numbers) {
    double sum = 0;
    for (Number n : numbers) sum += n.doubleValue(); // safe: n is-a Number
}

// Call with any subtype:
sumNumbers(List.of(1, 2, 3));          // List<Integer>
sumNumbers(List.of(1.1, 2.2));         // List<Double>
```

**Failure mode:**
```java
List<? extends Number> nums = new ArrayList<Integer>();
nums.add(42);    // COMPILE ERROR – could be Double, Long, etc.; unsafe
```

---

### 1.3 Lower-Bounded Wildcard `<? super T>`

**Meaning:** Some unknown supertype of `T`. The list is a **consumer** of `T` values.

**Write:** ✅ Can safely add `T` or any subtype of `T`.  
**Read:** ⚠️ Returns only `Object` — actual type above `T` is unknown.

```java
void addNumbers(List<? super Integer> list) {
    list.add(1);   // safe: Integer fits in Integer, Number, or Object
    list.add(2);
}

// Call with supertypes:
addNumbers(new ArrayList<Integer>());  // OK
addNumbers(new ArrayList<Number>());   // OK
addNumbers(new ArrayList<Object>());   // OK
```

**Failure mode:**
```java
List<? super Integer> list = new ArrayList<Number>();
Integer n = list.get(0);   // COMPILE ERROR – actual element could be any Number
Object o  = list.get(0);   // OK – Object is always a safe assignment
```

---

## 2. PECS — Producer Extends, Consumer Super

**Mnemonic:** _PECS — Producer Extends, Consumer Super_

| Role | Wildcard | Can read typed value? | Can add typed value? |
|------|----------|-----------------------|----------------------|
| Producer | `<? extends T>` | ✅ Yes (`T`) | ❌ No |
| Consumer | `<? super T>` | ❌ Only `Object` | ✅ Yes (`T`) |

**Decision rule:**
- If a parameter **produces** (you read `T` values out of it) → `<? extends T>`.
- If a parameter **consumes** (you write `T` values into it) → `<? super T>`.
- If both reading and writing are needed → use exact type `T` (no wildcard).

**Real JDK example:**
```java
// src produces T → extends; dest consumes T → super
public static <T> void copy(List<? super T> dest, List<? extends T> src) {
    for (T item : src) dest.add(item);
}
```

**Stream example:**
```java
// map: Function<? super T, ? extends R>
// T is consumed (in) → super; R is produced (out) → extends
Stream<String> names = Stream.of("alice", "bob");
Stream<Integer> lengths = names.map(s -> s.length());
```

**Common mistake:** Using `<? extends T>` when you need to add elements:
```java
List<? extends Number> nums = new ArrayList<>();
nums.add(1);   // COMPILE ERROR — cannot add to upper-bounded wildcard
```

---

## 3. Generics with Collections

Every Java Collection interface is generic. Understanding the type parameter unlocks the full contract.

| Interface | Declaration | Key constraint |
|-----------|-------------|----------------|
| `List<E>` | `interface List<E>` | Ordered, index-based, allows duplicates |
| `Set<E>` | `interface Set<E>` | No duplicates (via `equals`/`hashCode`) |
| `Map<K,V>` | `interface Map<K,V>` | Unique keys; one value per key |
| `Queue<E>` | `interface Queue<E>` | FIFO; `peek`/`poll` from head |
| `Deque<E>` | `interface Deque<E>` | Double-ended; stack or queue |
| `Optional<T>` | `class Optional<T>` | Contains 0 or 1 value; avoids null |

**Wildcard usage with collections:**
```java
// Read from any List of Numbers → ? extends
double totalScore(List<? extends Number> scores) { ... }

// Write Integers to any list that can hold them → ? super
void addDefaults(List<? super Integer> list) { list.add(0); }

// Process any list of any type → ?
void logAll(List<?> items) { items.forEach(System.out::println); }
```

**Diamond operator (`<>`):** Since Java 7, the right-hand side type argument can be inferred:
```java
List<String> names   = new ArrayList<>();   // compiler infers ArrayList<String>
Map<String, Integer> freq = new HashMap<>();
```

**Collections utility and generics:**
```java
Collections.sort(List<T> list)          // T must implement Comparable<? super T>
Collections.max(Collection<? extends T>)
Collections.unmodifiableList(List<? extends T>)
```

---

## 4. Type Erasure

**What it is:** The Java compiler removes all generic type information after type-checking. The bytecode only contains **raw types** and **inserted casts**.

**Steps the compiler performs:**
1. Replace all type parameters with their upper bound (or `Object` if unbounded).
2. Insert explicit casts wherever a typed value is retrieved.
3. Generate bridge methods when necessary to preserve polymorphism.

**Result in bytecode:**
```java
// Source
List<String> names = new ArrayList<>();
names.add("Alice");
String first = names.get(0);

// Bytecode equivalent (after erasure)
List names = new ArrayList();
names.add("Alice");
String first = (String) names.get(0);   // cast inserted by compiler
```

**Consequences of type erasure:**

| What you cannot do | Why |
|--------------------|-----|
| `if (obj instanceof List<String>)` | Generic type unknown at runtime |
| `new T[10]` | Cannot create generic arrays |
| `new T()` | Cannot instantiate type parameter |
| Overload methods that differ only in generic type | After erasure they have identical signatures |
| Catch generic exception: `catch (SomeException<T> e)` | Illegal — generic info erased |

**Example — overload conflict (compile error):**
```java
void process(List<String> list) { }
void process(List<Integer> list) { }   // COMPILE ERROR: same erasure List
```

**Work-arounds:**
- Pass `Class<T> clazz` as a token to create instances via `clazz.getDeclaredConstructor().newInstance()`.
- Use `Array.newInstance(clazz, size)` for arrays.

## Why Java Uses Type Erasure

Java generics were introduced in Java 5 to provide compile-time type safety. At that time, billions of lines of legacy Java bytecode were already running on production systems. If the JVM had been redesigned to enforce generics at runtime (similar to C# reified generics), older pre-generic libraries would have been incompatible with newer runtimes, requiring massive recompilation. To preserve strict backward compatibility, Java opted for type erasure, a design where type parameters exist only at compile time for safety checks, and are removed by the compiler before producing standard class files. Consequently, the compiled bytecode uses raw types and implicit type casts, allowing old bytecode and new generic code to run side-by-side on the same virtual machine without modifications.

### Mental Model

```text
Compile-Time (Safe checking):
[List<String>] ---> Allows adding "hello" ---> Rejects adding 123 (Compile Error)

       |
       | Compilation (Type Erasure & Cast Insertion)
       v

Runtime (JVM Execution):
[List] (Raw ArrayList holding Objects) ---> [Object: "hello"]
                                             |
                                             v (Implicit cast inserted by compiler)
                                        (String) value
```

### Code Example

```java
import java.util.ArrayList;
import java.util.List;

public class TypeErasureExplanation {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Java Generics");
        
        // At compile-time, get(0) is checked to be String.
        // At runtime, the bytecode performs a cast: (String) list.get(0)
        String value = list.get(0); 
        System.out.println(value); // Output: Java Generics
        
        // Demonstrating that runtime class ignores generic type
        System.out.println(list.getClass() == ArrayList.class); // Output: true
    }
}
```

### Cause-Effect Chain

Goal: Run legacy bytecode alongside generic code &rarr; Adopt Type Erasure &rarr; Generic type parameters erased to bounds/Object during compilation &rarr; JVM bytecode contains only raw types and inserted casts &rarr; Older JVMs can execute the bytecode without knowing about generics.

## Why Generics Are Invariant and How PECS Solves It

In Java, arrays are covariant, meaning `Integer[]` is a subtype of `Number[]`. However, generic types are invariant; for example, `List<Integer>` is not a subtype of `List<Number>`, even though `Integer` inherits from `Number`. If generics were covariant, you could assign a `List<Integer>` to a `List<Number>` reference, and then invoke `list.add(1.5)` (a double) on that reference, corrupting the integer list at runtime with invalid elements. To restore flexibility while maintaining type safety, Java provides wildcards under the PECS rule: Producer Extends, Consumer Super. Covariance with `? extends T` guarantees that we can safely read elements from a producer because they are guaranteed to be at least of type `T`, while contravariance with `? super T` guarantees we can safely write `T` elements into a consumer because the structure is guaranteed to hold `T` or its supertypes.

### Mental Model

```text
Invariance (Strict Type Matching):
List<Number>  <--- No Relationship --->  List<Integer>

PECS Solution for Flexibility:
                       +-------------------------+
                       |   List<? extends Number> |  <--- Read-Only (Covariant)
                       +-------------------------+
                                    ^
                                    | (Allows pointing to)
                        List<Integer> or List<Double>

                       +-------------------------+
                       |   List<? super Integer>  |  <--- Write-Only (Contravariant)
                       +-------------------------+
                                    ^
                                    | (Allows pointing to)
                         List<Number> or List<Object>
```

### Code Example

```java
import java.util.ArrayList;
import java.util.List;

public class PecsExplanation {
    public static void main(String[] args) {
        List<Integer> ints = new ArrayList<>();
        ints.add(10);
        ints.add(20);

        // 1. Invariance prevention: List<Number> nums = ints; // Compile Error

        // 2. Producer Extends (Read from list):
        List<? extends Number> producer = ints;
        Number num = producer.get(0); // Safe read: guaranteed to be Number
        System.out.println(num); // Output: 10
        // producer.add(5.5); // Compile Error: Write forbidden

        // 3. Consumer Super (Write to list):
        List<Number> numList = new ArrayList<>();
        List<? super Integer> consumer = numList;
        consumer.add(42); // Safe write: Integer is a subtype of Number/Object
        System.out.println(numList.get(0)); // Output: 42
    }
}
```

### Cause-Effect Chain

Covariance allows assignment of subtypes &rarr; Writing arbitrary supertypes to the reference corrupts the collection &rarr; Generics made invariant &rarr; Restricts APIs too much &rarr; PECS introduced &rarr; Use extends for safe reading (covariance) and super for safe writing (contravariance).

## Reference Links

- https://docs.oracle.com/javase/tutorial/java/generics/wildcards.html
- https://docs.oracle.com/javase/tutorial/java/generics/upperBounded.html
- https://docs.oracle.com/javase/tutorial/java/generics/lowerBounded.html
- https://docs.oracle.com/javase/tutorial/java/generics/erasure.html
- https://docs.oracle.com/javase/tutorial/java/generics/wildcardGuidelines.html
- https://docs.oracle.com/javase/tutorial/java/generics/subtyping.html
