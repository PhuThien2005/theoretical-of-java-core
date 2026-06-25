# Java Memory Management Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## stack

A thread-private, LIFO (Last-In-First-Out) memory region used by the JVM to store method call frames, local variable primitives, and object references during runtime execution.

Why it matters: Storing method execution context on the stack allows for extremely rapid allocation and deallocation. It ensures thread isolation and prevents thread-local variables from leaking into other threads, providing execution safety.

Common confusion: Many learners assume that *all* primitives reside on the stack. In reality, only local primitive variables declared inside methods reside on the stack; instance fields (even primitives) reside on the heap inside their enclosing objects.

Small example:
```java
void compute() {
    int localVal = 42; // Stored directly in compute()'s stack frame.
}
```

## heap

A shared runtime data area in JVM memory where class instances (objects) and arrays are dynamically allocated. It is the primary target of the Garbage Collector.

Why it matters: The heap allows objects to persist across method invocations and to be shared between threads. Dynamic allocation is critical because object sizes and lifetimes are often not known at compile time.

Common confusion: Believing that heap memory is cleared as soon as a method exits or when a reference goes out of scope. Heap objects are only reclaimed asynchronously by the Garbage Collector when they are no longer reachable from any GC Root.

Small example:
```java
Customer c = new Customer("Alice"); // 'c' is on the stack; the Customer object is on the heap.
```

## metaspace

A native memory region (introduced in Java 8 to replace PermGen) that stores class metadata, method definitions, method tables, annotation details, and the runtime constant pool.

Why it matters: Moving metadata off-heap to native memory prevents application crashes caused by `java.lang.OutOfMemoryError: PermGen space` limits. Metaspace can grow dynamically to fit the loaded classes.

Common confusion: Thinking static fields reside in Metaspace. Actually, static fields (including references and primitives) are allocated on the Java heap inside the `java.lang.Class` object for that loaded class.

Small example:
```java
// Metaspace holds the reflection/class metadata for MyClass:
Class<?> clazz = Class.forName("com.example.MyClass");
```

## strong reference

The default reference type in Java. Any object reference created via standard assignment (e.g., `Object obj = new Object()`) is a strong reference.

Why it matters: Strong references indicate to the Garbage Collector that an object is actively in use. As long as an object is reachable via a chain of strong references from any GC Root, it will never be collected, even under extreme memory pressure.

Common confusion: Assuming that clearing a strong reference (e.g., `obj = null`) immediately deletes the object. It only marks the object as eligible for GC; reclamation occurs later during a GC cycle.

Small example:
```java
List<String> list = new ArrayList<>(); // 'list' is a strong reference; ArrayList will not be collected.
```

## weak reference

An object reference (represented by `java.lang.ref.WeakReference`) that does not prevent its referent from being reclaimed by the Garbage Collector.

Why it matters: Weak references prevent memory leaks in cache-like structures where you want associated metadata to be reclaimed automatically as soon as the target object is no longer strongly reachable elsewhere.

Common confusion: Forgetting to check for `null` before dereferencing `weakRef.get()`. Because the GC can clear a weak reference at any time, invoking a method on the returned object without a null check will trigger a `NullPointerException`.

Small example:
```java
WeakReference<Customer> weakCustomer = new WeakReference<>(new Customer("Bob"));
// If no strong references exist, the next GC cycle clears the Customer object.
```

## garbage collection

The automatic JVM background process that monitors heap memory, identifies objects that are no longer reachable from any GC Root, and reclaims their memory to prevent exhaustion.

Why it matters: Automatic memory reclamation frees developers from manual memory tracking, avoiding double-free bugs and dangling pointers that are common in languages like C/C++.

Common confusion: Believing that calling `System.gc()` forces the JVM to instantly perform a full garbage collection. It is only a hint, which the JVM can completely ignore based on its configuration and active state.

Small example:
```java
String data = new String("temp");
data = null; // The String object is now unreachable and eligible for garbage collection.
```

## memory leak

A state where an application unintentionally retains strong references to objects that are no longer needed, preventing the Garbage Collector from reclaiming their memory.

Why it matters: Unchecked memory leaks gradually deplete the available heap memory, leading to severe performance degradation, frequent GC pauses, and eventually a fatal `java.lang.OutOfMemoryError`.

Common confusion: Thinking memory leaks cannot happen in Java because of automatic Garbage Collection. If a strong reference path exists from a GC Root (e.g., a static collection), the GC cannot reclaim the memory.

Small example:
```java
class Cache {
    private static final List<Object> leakList = new ArrayList<>();
    public void add(Object obj) { leakList.add(obj); } // Objects added are never removed, leaking memory.
}
```
