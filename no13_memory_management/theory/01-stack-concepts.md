# Java Memory Management - Part 1

| Concept | What to know |
| --- | --- |
| `Stack` | Stack stores method frames, local variables, and call flow for each thread. |
| `Heap` | Heap stores objects created at runtime. |
| `Method Area / Metaspace` | Metaspace stores class metadata outside the ordinary Java heap in modern JVMs. |
| `PC Register` |The PC register tracks the current JVM instruction for a thread. |
| `Native Method Stack` | Stack stores method frames, local variables, and call flow for each thread. |
| `Object lifecycle` |Object lifecycle covers creation, reachability, use, and eventual garbage collection. |
| `Reference variable` |A reference variable stores a reference to an object, not the object data itself. |
| `Strong reference` |A strong reference keeps an object reachable and prevents it from being garbage collected. |

## Detailed Notes

### Stack

Stack memory is thread-private and is used to store method execution frames, local variables, parameters, and the invocation flow of a single thread. 

#### JVM Rule
- Every time a thread invokes a method, a new **Stack Frame** is pushed onto the thread's stack.
- When the method completes (either via a `return` or by throwing an unhandled exception), its stack frame is popped.
- Local variables of primitive types (e.g., `int`, `double`, `boolean`) and references to heap objects reside directly in the stack frame.
- Stack memory is allocated and deallocated automatically in a Last-In-First-Out (LIFO) order. It is extremely fast but has a fixed size (configured via `-Xss`).

#### Code Example: Stack Frame Lifecycle
```java
public class StackDemo {
    public static void main(String[] args) {
        int a = 10; // Stored in main's stack frame
        int b = 20; // Stored in main's stack frame
        int result = add(a, b); // Pushes a new frame for add()
        System.out.println(result);
    } // main's frame is popped, stack is empty

    private static int add(int x, int y) {
        int sum = x + y; // Stored in add's stack frame
        return sum; 
    } // add's frame is popped, sum, x, and y are reclaimed
}
```

### Heap

Heap memory is the shared runtime data area where the JVM allocates space for all class instances (objects) and arrays.

#### JVM Rule
- All Java objects, regardless of where they are created, reside on the Heap.
- Heap memory is shared among all threads, meaning objects can be accessed concurrently (which requires synchronization to ensure thread-safety).
- Unlike stack memory, heap allocation is dynamic and does not follow LIFO.
- Objects on the heap are not reclaimed immediately when a method exits. Instead, they remain on the heap until they are no longer reachable and the Garbage Collector (GC) reclaims them.
- Heap size is configured using JVM flags like `-Xms` (initial size) and `-Xmx` (maximum size). Exceeding it results in a `java.lang.OutOfMemoryError: Java heap space`.

#### Code Example: Stack vs. Heap Allocation
```java
public class MemoryAllocationDemo {
    public static void main(String[] args) {
        int localPrimitive = 42; // Value 42 is stored on the Stack
        
        // The reference variable 'customer' is on the Stack.
        // The actual 'Customer' object is allocated on the Heap.
        Customer customer = new Customer("Alice", 30);
        
        modifyCustomer(customer);
    }

    private static void modifyCustomer(Customer cust) {
        // 'cust' is a copy of the reference variable, pointing to the same heap object.
        cust.setAge(31); // Mutates the object on the Heap
    }
}

class Customer {
    private String name; // Reference to String object on Heap
    private int age;     // Primitive field, stored on Heap as part of the Customer object

    public Customer(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setAge(int age) { this.age = age; }
}
```

### Method Area / Metaspace

The Method Area is a shared JVM memory area that stores class-level metadata. In modern HotSpot JVMs (Java 8+), this is implemented as **Metaspace**.

#### JVM Rule
- Metaspace stores class structure information: class definitions, method bytecode, constructor code, runtime constant pools, annotations, and method tables.
- Since Java 8, Metaspace is allocated out of native memory (off-heap) rather than the standard Java heap. This prevents the class loading limit issues common with PermGen.
- While it can grow dynamically by default, its size can be restricted using `-XX:MaxMetaspaceSize`.
- When class loaders are garbage collected, their corresponding class metadata in Metaspace is unloaded.

### PC Register

Each thread has its own Program Counter (PC) Register.

#### JVM Rule
- If the thread is executing a non-native Java method, the PC Register holds the address of the JVM instruction currently being executed.
- If the thread is executing a native method, the PC Register value is undefined.
- The PC Register is lightweight and does not grow. It is crucial for thread scheduling and context switching, allowing threads to resume execution from the exact instruction where they were paused.

### Native Method Stack

The Native Method Stack is a thread-private stack dedicated to methods written in non-Java languages (usually C or C++) invoked via the Java Native Interface (JNI).

#### JVM Rule
- When a Java method calls a native method, the execution context shifts to the Native Method Stack.
- Like the Java stack, it is thread-private and can throw `StackOverflowError` if native call depth is exceeded.

### Object lifecycle

The lifecycle of an object consists of several distinct stages:

1. **Creation**: Memory is allocated on the heap, instance variables are initialized, and the constructor executes.
2. **Reachability**: The object is usable by the application as long as there is a chain of references from a active thread (GC Root) to the object.
3. **Unreachability / GC Eligibility**: When the object can no longer be reached from any GC Root, it becomes eligible for Garbage Collection.
4. **Finalization** (Deprecated): If the object defines a `finalize()` method, it may be run before reclamation.
5. **Reclamation**: The Garbage Collector reclaims the memory on the heap.

### Reference variable

A reference variable is a variable that stores the memory address (reference) of an object on the heap, rather than the object itself.

#### JVM Rule
- Declaring a reference variable (e.g., `Customer c;`) reserves space on the stack or heap for a pointer, initialized to `null`.
- Java is strictly **pass-by-value**. When an object reference is passed to a method, the reference itself (the memory address pointer) is copied. The caller's reference variable cannot be reassigned by the method, but the state of the object it points to can be modified.

```java
public class PassByValueDemo {
    public static void main(String[] args) {
        Customer c1 = new Customer("Bob", 25);
        reassign(c1);
        System.out.println(c1.getName()); // Prints "Bob" - original reference was not changed
        
        modify(c1);
        System.out.println(c1.getName()); // Prints "Charlie" - object state was mutated
    }

    private static void reassign(Customer c) {
        c = new Customer("Dave", 40); // Only changes the local copied parameter 'c'
    }

    private static void modify(Customer c) {
        c.setName("Charlie"); // Modifies the object pointed to by the reference
    }
}
```

### Strong reference

A strong reference is the default reference type in Java. Any object created with standard assignment (e.g., `Object obj = new Object();`) is strongly referenced.

#### JVM Rule
- As long as an object is reachable via at least one path of strong references starting from a GC Root, it will **never** be garbage collected.
- Even if the JVM is running critically low on memory and is about to throw `OutOfMemoryError`, it will not reclaim strongly-referenced objects.

---

## Common Mistakes

### 1. Thinking Primitives Always Live on the Stack
A very common interview trap is stating that all primitive variables live on the stack. 
**The Rule:** A primitive variable's location is determined by *where* it is declared:
- **Local primitives** (declared inside a method) reside on the **Stack**.
- **Instance primitives** (declared as class fields) reside on the **Heap** as part of the object they belong to.
- **Static primitives** (declared as static fields) reside on the **Heap** inside the `java.lang.Class` object.

### 2. Assuming `obj = null` Instantly Frees Memory
Setting a reference variable to `null` does not trigger immediate garbage collection.
**The Rule:** Reassigning a reference to `null` simply breaks that particular reference connection. If that was the last strong reference to the heap object, the object becomes *eligible* for GC. The GC will reclaim the memory asynchronously at an unpredictable future time.

### 3. Confusing StackOverflowError with OutOfMemoryError
- **StackOverflowError**: Caused by thread stack exhaustion (typically infinite recursion). The stack size is small (often 1MB) and handles call frames.
- **OutOfMemoryError: Java heap space**: Caused by heap exhaustion (creating too many active objects). The heap size is much larger and handles data storage.

### 4. Believing Static Fields Live in Metaspace
Since Java 8, static variables (both primitives and object references) are allocated on the Java Heap, specifically inside the `java.lang.Class` instance of that class. Metaspace only stores the metadata describing the class itself, not the actual values or instances of static variables.

## Why Primitives Live on the Stack or Heap

The JVM determines the physical memory allocation of primitive variables based entirely on their declaration scope rather than their data type. Local primitives declared within a method are stored directly inside that thread's stack frame because the stack's LIFO (Last-In-First-Out) lifecycle is tied to method execution, allowing instant allocation and deallocation. Conversely, instance primitives declared as fields of a class live on the Heap inside the memory block allocated for the parent object. Similarly, static primitives are class-level fields and are allocated within the Class metadata object residing on the Heap. The stack's frame-based lifecycle avoids the overhead of Garbage Collection for local variables, but heap-allocated variables must persist across method calls and thus rely on the Garbage Collector for cleanup.

### Mental Model
```
+-------------------------------------------------------------+
| Thread Stack Frame (LIFO)                                   |
| [ main() frame: localPrimitive = 100 ]                     |
| [ process() frame: tempVal = 42 ]                          |
+-------------------------------------------------------------+
                                | (References heap object)
                                v
+-------------------------------------------------------------+
| Heap Memory (Dynamic Lifecycle)                            |
| [ Container Object ] -------> [ instancePrimitive = 200 ]   |
| [ Class Metadata Object ] --> [ staticPrimitive = 300 ]     |
+-------------------------------------------------------------+
```

### Code Example
```java
public class PrimitiveAllocation {
    static int staticPrimitive = 300; // Allocated on the Heap (inside Class object)
    int instancePrimitive = 200;      // Allocated on the Heap (inside object payload)

    public void methodScope() {
        int localPrimitive = 100;     // Allocated on the Stack (inside current frame)
        System.out.println(localPrimitive);      // Output: 100
        System.out.println(instancePrimitive);   // Output: 200
        System.out.println(staticPrimitive);     // Output: 300
    }
}
```

### Cause-Effect Chain
Method invoked &rarr; Stack frame pushed &rarr; Local primitive allocated on Stack &rarr; Method exits &rarr; Stack frame popped &rarr; Local memory reclaimed instantly without GC overhead.

## Why Java Is Strictly Pass-by-Value

Java strictly implements pass-by-value, meaning that the JVM always copies the actual value stored in a variable when passing it as a parameter to a method. For primitive data types, the value passed is a direct copy of the bits representing the data itself. For reference variables (objects), the value passed is a copy of the pointer address (the memory address) referencing the object on the heap. Consequently, reassigning the parameter inside the method merely overwrites the local copy of the pointer on the stack frame, leaving the caller's original variable unaffected. However, because both the caller's variable and the parameter copy point to the exact same object location on the heap, mutating the object's fields inside the method changes the shared heap state.

### Mental Model
```
Stack Frame: main()               Stack Frame: modify()
+-----------------------+         +-----------------------+
| customerRef (0x7F2B)  | ----+   | parameterCopy (0x7F2B)|
+-----------------------+     |   +-----------------------+
                              |         |
                              +----+----+
                                   |
                                   v
                             Heap Memory
                             +---------------------------+
                             | Customer Object (0x7F2B)  |
                             | { name: "Alice" }         |
                             +---------------------------+
```

### Code Example
```java
public class PassByValueEx {
    public static void main(String[] args) {
        int num = 10;
        modifyPrimitive(num);
        System.out.println("Primitive: " + num); // Output: Primitive: 10

        Customer c = new Customer("Alice");
        modifyObject(c);
        System.out.println("Object: " + c.name); // Output: Object: Bob
    }
    static void modifyPrimitive(int x) { x = 20; }
    static void modifyObject(Customer cust) {
        cust.name = "Bob"; // Mutates heap object
        cust = new Customer("Charlie"); // Reassigns local stack copy
    }
    static class Customer {
        String name;
        Customer(String n) { name = n; }
    }
}
```

### Cause-Effect Chain
Reference passed to method &rarr; JVM copies pointer value onto new stack frame &rarr; Local parameter re-assigned &rarr; Copy pointer changes to new address &rarr; Caller's pointer remains at original address &rarr; Original reference unaffected.

## Reference Links

- https://docs.oracle.com/javase/specs/jls/se21/html/jls-4.html#jls-4.12.2 (Variables of Reference Type)
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.4.1 (Formal Parameters)
