# Generics Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## type parameter

A placeholder name (such as `T`, `E`, `K`, `V`) declared on generic classes, interfaces, or methods. It is replaced with a concrete type argument when the class is instantiated or the method is called, allowing the same code to work with different types while preserving type safety.

Why it matters: It allows the compiler to enforce type constraints at compile-time (e.g., ensuring a `List<String>` only contains strings) without requiring explicit type casting by the developer, preventing runtime exceptions.

Common confusion: Confusing *type parameter* with *type argument*. A type parameter is the placeholder declared in the class signature (like `T` in `Box<T>`), whereas a type argument is the actual type used when instantiating the class (like `String` in `Box<String>`).

Small example:
```java
// T is the type parameter
public class Box<T> {
    private T item;
    public void set(T item) { this.item = item; }
    public T get() { return item; }
}
```

## bounded type parameter

A type parameter that restricts the range of allowed type arguments using the `extends` keyword (e.g., `T extends Number`). It guarantees that any type argument supplied at instantiation will be a subtype of the specified bound.

Why it matters: Bounding a type parameter allows you to call methods defined on the bound (such as calling `doubleValue()` on a type bounded by `Number`) inside the generic class, without having to cast the generic object.

Common confusion: Thinking `extends` means only class inheritance. In bounded type parameters, `extends` is used for both classes and interfaces (e.g., `T extends Comparable<T>` where `Comparable` is an interface). Java does not use `implements` for generic bounds.

Small example:
```java
// T is bounded by Number, allowing access to Number methods
public class Calculator<T extends Number> {
    public double doubleValueOf(T value) {
        return value.doubleValue(); // Valid because T is at least a Number
    }
}
```

## wildcard

The wildcard symbol `?` represents an unknown type in generic parameter declarations. It can be unbounded (`<?>`), upper-bounded (`<? extends T>`), or lower-bounded (`<? super T>`), letting methods accept parameterized arguments of varying types.

Why it matters: Wildcards allow developers to write flexible APIs that accept related generic types (e.g., letting a method process `List<Integer>` and `List<Double>` using `List<? extends Number>`), which is otherwise impossible due to generic invariance.

Common confusion: Believing that wildcard declarations behave like normal type variables. You cannot use a wildcard (`?`) inside the method body to declare variables or instantiate objects (e.g., `? item = list.get(0)` is invalid).

Small example:
```java
// Accepts any list of Number or its subclasses
public static void printNumbers(List<? extends Number> list) {
    for (Number n : list) {
        System.out.println(n);
    }
}
```

## PECS

An acronym standing for **Producer Extends, Consumer Super**, which guides the selection of wildcard bounds. Use `? extends T` when the generic structure produces data (read-only), and `? super T` when it consumes data (write-only).

Why it matters: It resolves the tension between generic invariance and subtyping flexibility. Following PECS allows you to write reusable utility methods that read from and write to collections of varying hierarchy levels without type safety errors.

Common confusion: Trying to use `? extends T` for collections where you need to insert elements. A producer-bounded list (`? extends T`) is read-only because the compiler cannot determine the exact subclass at runtime, making all additions (except `null`) compile errors.

Small example:
```java
// dest consumes elements (super), src produces elements (extends)
public static <T> void copy(List<? super T> dest, List<? extends T> src) {
    for (T item : src) {
        dest.add(item); // Safe write to dest, safe read from src
    }
}
```

## type erasure

The compile-time process by which the Java compiler removes all generic type arguments from generic class, interface, and method declarations. The resulting bytecode contains only raw types, with implicit casts inserted where necessary.

Why it matters: It ensures backwards compatibility, allowing compiled generic code to run seamlessly on older JVM versions and interact with pre-Java 5 libraries that only use raw types.

Common confusion: Thinking that generic type information is completely lost. While type parameters on variables and instances are erased, the metadata of the class itself (e.g., `class MyList<T>`) remains in the class file definition and can be accessed via reflection.

Small example:
```java
// What you write:
List<String> list = new ArrayList<>();
list.add("hello");
String s = list.get(0);

// What the compiler generates in bytecode:
List list = new ArrayList();
list.add("hello");
String s = (String) list.get(0); // Inserted cast
```

## raw type

The name of a generic class or interface used without any type arguments (e.g., using `List` instead of `List<String>`). Raw types operate as they did prior to Java 5, treating all type parameters as their upper bounds (typically `Object`).

Why it matters: Raw types permit legacy code to run on modern Java versions. However, they disable all compile-time type-safety checks, shifting type mismatch errors to runtime exceptions.

Common confusion: Believing that raw types and unbounded wildcards are identical. While both can refer to any type, `List` (raw) lets you add any object (unsafe), whereas `List<?>` (wildcard) prevents adding elements (safe), enforcing compile-time invariants.

Small example:
```java
List rawList = new ArrayList(); // Raw type
rawList.add("String");
rawList.add(Integer.valueOf(100)); // Compiles, but risks ClassCastException on read

List<?> wildcardList = new ArrayList<String>();
// wildcardList.add("String"); // Compile Error: compiler prevents additions for safety
```
