# Modifiers in Java - Part 2

## Learning Goal

This file covers a focused slice of **Modifiers in Java**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `abstract` | Abstract means incomplete by design: subclasses or implementations must provide missing behavior. |
| `synchronized` | Synchronized protects a critical section by using a monitor lock. |
| `volatile` | Volatile gives visibility guarantees for a variable shared between threads, but it does not make compound operations atomic. |
| `transient` | Transient marks a field that should be skipped during Java serialization. |
| `native` |native is a specific concept in Modifiers in Java; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `strictfp` |strictfp is a specific concept in Modifiers in Java; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Static variable` | Static means the member belongs to the class rather than to one particular object. |
| `Static method` | Static means the member belongs to the class rather than to one particular object. |

## Detailed Notes

### abstract

Abstract means incomplete by design: subclasses or implementations must provide missing behavior.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### abstract Class Code Example
```java
// Abstract Class definition
public abstract class GraphicObject {
    int x, y;

    // Concrete method in abstract class
    void moveTo(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    // Abstract method (no body, ends with semicolon)
    abstract void draw();
}

// Subclass implementing abstract method
class Circle extends GraphicObject {
    void draw() {
        System.out.println("Drawing a circle at " + x + ", " + y);
    }
}
```

#### Common Mistake - Declaring an abstract method with a body or inside a concrete class
Any class that declares one or more `abstract` methods must also be declared `abstract`. Furthermore, `abstract` methods cannot have a body (no braces, just a semicolon at the end). Writing `abstract void draw() {}` is a compile error because the empty braces `{}` constitute a method body.

Practical check:

- Define `abstract` in one sentence.
- Recognize `abstract` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `abstract`.

Tiny example or mental model:

- When reading code, ask: what does `abstract` change, allow, reject, or clarify?

### synchronized

Synchronized protects a critical section by using a monitor lock.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

#### synchronized Code Example
```java
public class ThreadSafeCounter {
    private int count = 0;

    // Instance synchronized method locks on the 'this' object
    public synchronized void increment() {
        count++;
    }

    // Static synchronized method locks on ThreadSafeCounter.class
    public static synchronized void printHeader() {
        System.out.println("--- Counters Report ---");
    }

    public int getCount() {
        return count;
    }
}
```

#### Common Mistake - Static and instance synchronized methods blocking each other
Static synchronized methods and instance synchronized methods acquire DIFFERENT locks. A static synchronized method locks on the `Class` object, while an instance synchronized method locks on the individual object instance (`this`). Therefore, they will NOT block each other from running concurrently on different threads.

Practical check:

- Define `synchronized` in one sentence.
- Recognize `synchronized` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `synchronized`.

Tiny example or mental model:

- When reading code, ask: what does `synchronized` change, allow, reject, or clarify?

### volatile

Volatile gives visibility guarantees for a variable shared between threads, but it does not make compound operations atomic.

It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

#### volatile Code Example
```java
public class SharedFlagDemo {
    // volatile ensures write by one thread is immediately visible to others
    private volatile boolean running = true;

    public void stop() {
        running = false;
    }

    public void work() {
        while (running) {
            // Do some background processing
        }
        System.out.println("Stopped gracefully.");
    }
}
```

#### Common Mistake - Assuming volatile guarantees atomicity for compound operations
The `volatile` keyword only guarantees visibility and ordering (preventing instruction reordering). It does NOT guarantee atomicity for compound operations like incrementing a number (`count++`). If multiple threads execute `count++` on a volatile variable, updates can still be lost. For atomic operations, use `synchronized` or classes from `java.util.concurrent.atomic`.

Practical check:

- Define `volatile` in one sentence.
- Recognize `volatile` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `volatile`.

Tiny example or mental model:

- When reading code, ask: what does `volatile` change, allow, reject, or clarify?

### transient

Transient marks a field that should be skipped during Java serialization.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `transient` in one sentence.
- Recognize `transient` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `transient`.

Tiny example or mental model:

- When reading code, ask: what does `transient` change, allow, reject, or clarify?

### native

native is a specific concept in Modifiers in Java; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `native` in one sentence.
- Recognize `native` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `native`.

Tiny example or mental model:

- When reading code, ask: what does `native` change, allow, reject, or clarify?

### strictfp

strictfp is a specific concept in Modifiers in Java; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `strictfp` in one sentence.
- Recognize `strictfp` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `strictfp`.

Tiny example or mental model:

- When reading code, ask: what does `strictfp` change, allow, reject, or clarify?

### Static variable

Static means the member belongs to the class rather than to one particular object.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Static variable` in one sentence.
- Recognize `Static variable` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Static variable`.

Tiny example or mental model:

- `ClassName.member` accesses a class-level member.

### Static method

Static means the member belongs to the class rather than to one particular object.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

#### Static Method vs Instance Method Comparison
```java
public class MethodComparisonDemo {
    private int instanceValue = 42;
    private static int classValue = 100;

    // Instance method: requires an object instance, can access static & instance variables
    public void printInstance() {
        System.out.println("Instance value: " + this.instanceValue);
        System.out.println("Static value: " + classValue); // OK
    }

    // Static method: belongs to the class blueprint, can ONLY access static variables
    public static void printStatic() {
        System.out.println("Static value: " + classValue);
        // System.out.println(instanceValue); // COMPILE ERROR! Cannot access instance variable
    }
}
```

#### Common Mistake - Calling non-static members from static context
Static methods belong to the class blueprint, not to any individual instance. Hence, they cannot access instance fields or call non-static methods directly without an explicit instance reference. They also cannot use the `this` or `super` keywords.

Practical check:

- Define `Static method` in one sentence.
- Recognize `Static method` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Static method`.

Tiny example or mental model:

- `ClassName.member` accesses a class-level member.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
