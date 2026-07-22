# Java Memory Management - Part 2

## Learning Goal

This file covers a focused slice of **Java Memory Management**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Weak reference` |Weak reference is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Soft reference` |Soft reference is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Phantom reference` |Phantom reference is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Garbage Collection` | Garbage collection reclaims memory from objects that are no longer reachable. |
| `Conditions for an object to be GC'd` |Conditions for an object to be GC'd is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `System.gc()` |System.gc() is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Finalization, finalize() deprecated` | Final means the variable, method, class, or parameter is restricted from later change in a specific way. |
| `Memory leak in Java` |Memory leak in Java is a specific concept in Java Memory Management; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### Weak reference

A **Weak Reference** (represented by `java.lang.ref.WeakReference`) does not prevent its referent from being reclaimed by the Garbage Collector.

#### JVM Rule
- If an object is only reachable via weak references (no strong or soft reference paths from GC Roots), the GC will clear it during the next collection cycle, regardless of whether heap memory is low.
- Commonly used for metadata mappings, canonicalizing mappings, or caches (such as in `java.util.WeakHashMap`).

#### Code Example: WeakReference Behavior
```java
import java.lang.ref.WeakReference;

public class WeakRefDemo {
    public static void main(String[] args) {
        // Strong reference 'bigObject' points to a large String on the heap
        String bigObject = new String("PayloadData");
        
        // Weak reference pointing to the same heap object
        WeakReference<String> weakRef = new WeakReference<>(bigObject);
        
        System.out.println("Before GC: " + weakRef.get()); // Prints "PayloadData"
        
        // Sever the strong reference
        bigObject = null; 
        
        // Request GC (strictly for demonstration, do not do this in production)
        System.gc(); 
        
        // The object has been collected because it was only weakly reachable
        System.out.println("After GC: " + weakRef.get()); // Prints null
    }
}
```

### Soft reference

A **Soft Reference** (represented by `java.lang.ref.SoftReference`) is a stronger reference type than a weak reference, designed for memory-sensitive caching.

#### JVM Rule
- An object that is softly reachable (has only soft references pointing to it) will survive standard Garbage Collection cycles.
- The JVM will only reclaim softly-referenced objects if it is running out of memory (typically right before throwing an `OutOfMemoryError`).
- JVM implementations attempt to clear softly-referenced objects that have been idle the longest.

#### Code Example: SoftReference Usage
```java
import java.lang.ref.SoftReference;

public class SoftRefDemo {
    public static void main(String[] args) {
        String data = new String("CachedValue");
        SoftReference<String> softRef = new SoftReference<>(data);
        
        data = null; // Sever the strong reference
        
        System.gc(); // Suggest GC
        
        // Survives standard GC because memory is not low
        System.out.println("Soft reference get: " + softRef.get()); // Prints "CachedValue"
    }
}
```

### Phantom reference

A **Phantom Reference** (represented by `java.lang.ref.PhantomReference`) is the weakest reference type, used for post-mortem cleanup.

#### JVM Rule
- Unlike Weak and Soft references, calling `.get()` on a `PhantomReference` **always returns `null`**.
- It must be created with a `ReferenceQueue`.
- When the JVM determines an object is only phantom reachable, it queues the phantom reference. The developer can poll the queue to perform pre-cleanup actions (like freeing off-heap native memory).
- Unlike finalized objects, memory is not automatically freed; the phantom reference must be cleared via `phantomRef.clear()` to allow complete reclamation.

### Garbage Collection

Garbage Collection (GC) is the automatic memory management process in the JVM that reclaims heap memory occupied by objects that are no longer reachable by the application.

#### JVM Rule
- GC acts asynchronously in the background. It finds unreachable objects, frees their memory, and can compact the heap to prevent fragmentation.
- The application halts or experiences pauses (Stop-The-World) depending on the GC algorithm (e.g., G1, ZGC, Parallel GC).

### Conditions for an object to be GC'd

An object is eligible for garbage collection if it is no longer reachable from any **GC Root**.

#### What is a GC Root?
- Local variables and parameters in active thread stacks.
- Static fields of loaded classes.
- JNI (Java Native Interface) global and local references.
- System class loaders and active JVM internal references.

#### Islands of Isolation
- If Object A references Object B, and Object B references Object A, they point to each other.
- If neither A nor B can be reached from any GC Root, they form an **island of isolation**.
- The GC will reclaim both objects, even though they have non-null reference variables pointing to each other.

#### Code Example: GC Eligibility & Circular Reference
```java
public class GCEligibilityDemo {
    public static void main(String[] args) {
        Node n1 = new Node("First");
        Node n2 = new Node("Second");
        
        n1.next = n2;
        n2.next = n1; // n1 and n2 reference each other (circular dependency)
        
        n1 = null; // "First" is still reachable via n2.next
        // "First" is NOT eligible for GC yet.
        
        n2 = null; // "Second" is no longer reachable from main's stack.
        // n1 and n2 are now isolated from the GC Roots.
        // Both Node objects are now eligible for Garbage Collection.
    }
}

class Node {
    String name;
    Node next;
    Node(String name) { this.name = name; }
}
```

### System.gc()

Calling `System.gc()` or `Runtime.getRuntime().gc()` suggests that the JVM expend effort toward recycling unused objects.

#### JVM Rule
- This is merely a **hint** or request to the JVM. The JVM can choose to ignore the call completely (e.g., if configured with `-XX:+DisableExplicitGC`).
- There is no guarantee that GC will run immediately, nor that all eligible objects will be reclaimed upon invocation.
- Calling `System.gc()` is highly expensive and can freeze application threads during major collections.

### Finalization, finalize() deprecated

The `finalize()` method was originally designed to perform cleanup before an object was reclaimed.

#### JVM Rule
- `finalize()` has been **deprecated since Java 9** and is deprecated/disabled in modern versions.
- **Why it failed**: It introduced unpredictable execution timing, severe performance overhead, garbage collector stalling, and security vulnerabilities (finalize attacks where partially created objects could be resurrected).
- **Modern Alternatives**:
  - Implement `java.lang.AutoCloseable` and use the **try-with-resources** statement for deterministic cleanup of resources (files, sockets).
  - Use `java.lang.ref.Cleaner` or phantom references for non-deterministic native resource cleanup.

#### Code Example: Modern try-with-resources Alternative
```java
public class ResourceDemo {
    public static void main(String[] args) {
        // Deterministic cleanup using try-with-resources
        try (MyResource resource = new MyResource()) {
            resource.doWork();
        } // resource.close() is automatically called here, even if exceptions occur
    }
}

class MyResource implements AutoCloseable {
    public void doWork() {
        System.out.println("Working...");
    }

    @Override
    public void close() {
        System.out.println("Resource closed and cleaned up!");
    }
}
```

### Memory leak in Java

A memory leak in Java occurs when the application retains strong references to objects that are no longer needed, preventing the Garbage Collector from reclaiming them.

---

## Case Study: Memory leak in a cache — static HashMap that grows forever

### Scenario
An application uses an in-memory cache to store user session data. To make it globally accessible, the cache is implemented as a `static HashMap`. However, when users log out or sessions expire, the keys are never removed from the map.

```java
import java.util.HashMap;
import java.util.Map;

public class SessionCacheLeak {
    // A static variable lives as long as the class is loaded (typically the lifetime of the JVM).
    // It serves as a permanent GC Root. Any object stored in this map remains strongly reachable.
    private static final Map<String, UserSession> activeSessions = new HashMap<>();

    public static void userLoggedIn(String userId, UserSession session) {
        activeSessions.put(userId, session);
    }

    // Bug: Users log out, but we forget to call activeSessions.remove(userId).
    public static void userLoggedOut(String userId) {
        // Missing: activeSessions.remove(userId);
    }
}

class UserSession {
    private byte[] data = new byte[1024 * 1024]; // 1 MB session payload
}
```

### The Consequence
Because `activeSessions` is a static field, it is a GC Root. Every `UserSession` added remains strongly reachable forever, even if the user has logged out. If the application handles thousands of logins daily, the heap will eventually fill up, causing a `java.lang.OutOfMemoryError: Java heap space`.

### The Fixes
1. **Explicit Removal**: Ensure the removal code is executed inside a `finally` block or clean-up listener:
   ```java
   public static void userLoggedOut(String userId) {
       activeSessions.remove(userId);
   }
   ```
2. **WeakHashMap**: Use `java.util.WeakHashMap` if the session lifetime is tied to external references to the keys. Once the key is no longer strongly referenced elsewhere, the map entry is cleared by GC.
3. **Eviction Policies**: Use a bounded caching library like Guava Cache or Caffeine with time-based or size-based eviction limits.

---

## Common Mistakes

### 1. Failing to check for null on WeakReference
Developers often forget that the GC can clear a `WeakReference` at any moment. Calling `weakRef.get().someMethod()` without checking if `get()` returned `null` leads to a `NullPointerException`.
**Correction**:
```java
Object value = weakRef.get();
if (value != null) {
    // Safe to use
}
```

### 2. Creating WeakReference with String Literals
If you pass a string literal to a `WeakReference` (e.g., `new WeakReference<>("literal")`), it will *never* be garbage collected. This is because string literals are stored in the String Constant Pool (which holds strong references to them).

### 3. Relying on `finalize()` for Cleanup
Assuming `finalize()` will run reliably or quickly is a major error. It may never execute if the JVM exits before GC runs. Always use `try-with-resources`.

### 4. Thinking Islands of Isolation Cannot be GC'd
Thinking that any cyclic dependency (like Object A referencing B, and B referencing A) prevents GC is a mistake. Reachability is traced from GC Roots; if the entire group is disconnected from GC Roots, the entire island is collected.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?

## Why Different Reference Types Exist

Java provides distinct reference strengths to allow developers fine-grained control over object lifetimes and prevent memory leaks while optimizing caches or cleanup routines. Strong references prevent the Garbage Collector from reclaiming an object even under extreme memory pressure, which is necessary for active data but dangerous for temporary caches. Soft references act as a buffer, allowing objects to persist during normal execution but reclaiming them automatically right before the JVM throws an OutOfMemoryError, making them ideal for memory-sensitive caches. Weak references are cleared during the next collection cycle if the referent has no stronger reachability paths, which is perfect for associating metadata with objects (e.g., WeakHashMap) without extending their lifecycle. Phantom references are the weakest type, always returning null on get() and serving solely to notify developers via a ReferenceQueue when an object has been fully collected so off-heap cleanup can occur safely.

### Mental Model
```
[ GC Root ]
     |
     +===(Strong Reference)===> [ Object A ] (Never collected)
     |
     +---(Soft Reference)--->  [ Object B ] (Collected only if heap is exhausted)
     |
     +---(Weak Reference)--->  [ Object C ] (Collected on next GC cycle)
     |
     +---(Phantom Reference)--> [ Object D ] (Always returns null; enqueued on GC)
                                    |
                                    v
                             [ ReferenceQueue ] (Handles post-mortem cleanup)
```

### Code Example
```java
import java.lang.ref.*;

public class ReferenceTypesDemo {
    public static void main(String[] args) {
        Object referent = new Object();
        SoftReference<Object> softRef = new SoftReference<>(referent);
        WeakReference<Object> weakRef = new WeakReference<>(referent);
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        PhantomReference<Object> phantomRef = new PhantomReference<>(referent, queue);

        System.out.println("Weak get: " + (weakRef.get() != null));       // Output: Weak get: true
        System.out.println("Phantom get: " + phantomRef.get());            // Output: Phantom get: null (always)
    }
}
```

### Cause-Effect Chain
Heap exhausted &rarr; GC runs &rarr; Strong reference preserved &rarr; Soft reference cleared &rarr; Weak reference cleared &rarr; Phantom reference queued &rarr; OutOfMemoryError avoided/mitigated.

## Why Islands of Isolation Can Be Garbage Collected

Older or simpler garbage collectors utilized reference counting, which incremented a counter whenever an object was referenced and decremented it when a reference was severed. However, reference counting fails to reclaim circular references (islands of isolation) because their cross-references maintain a reference count greater than zero even when completely disconnected from the rest of the application. The JVM solves this fundamental limitation by implementing tracing garbage collection, which begins reachability checks from defined 'GC Roots' (such as stack frames, static fields, and JNI references). Any group of objects that cannot be reached by traversing the reference graph starting from these GC Roots is identified as unreachable, regardless of internal cross-references. Consequently, the garbage collector safely reclaims the entire island of isolation during a collection cycle because no path exists to them from any active application thread.

### Mental Model
```
[ GC Root (Stack Frame) ]
            |
            x (Reference severed)
            |
    +-------v--------+
    | Object A       | <=======> [ Object B ]
    | (Count = 1)    |           (Count = 1)
    +----------------+
    
    [ Island of Isolation (Unreachable from GC Root) ]
```

### Code Example
```java
public class IslandOfIsolation {
    IslandOfIsolation partner;

    public static void main(String[] args) {
        IslandOfIsolation a = new IslandOfIsolation();
        IslandOfIsolation b = new IslandOfIsolation();

        a.partner = b; // a references b
        b.partner = a; // b references a

        a = null;      // sever stack reference to a
        b = null;      // sever stack reference to b
        
        // Both objects reference each other but are unreachable from GC Roots.
        System.gc(); // Tracing GC reclaims both objects successfully.
    }
}
```

### Cause-Effect Chain
Stack references set to null &rarr; GC Roots traversal starts &rarr; GC traversal cannot reach Object A or B &rarr; Circular references ignored &rarr; Island of isolation marked unreachable &rarr; Both objects reclaimed.

## Reference Links

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/ref/package-summary.html (Package java.lang.ref)
- https://docs.oracle.com/en/java/javase/21/gctuning/introduction-garbage-collection-tuning.html (Garbage Collection Tuning Guide)
