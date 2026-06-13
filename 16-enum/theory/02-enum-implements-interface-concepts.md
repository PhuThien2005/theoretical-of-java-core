# Enum - Part 2

## Learning Goal

This file covers a focused slice of **Enum**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Enum implements interface` | Enums cannot extend classes, but they can implement interfaces to support polymorphism. |
| `Enum Singleton pattern` | Implementing a Singleton as a single-element enum provides built-in safety against reflection and serialization attacks. |

## Detailed Notes

### Enum implements interface

Although enums cannot inherit from another class (because they implicitly inherit from `java.lang.Enum`), they are fully permitted to implement one or more interfaces.
- **Constant-Specific Implementations**: Individual enum constants can override interface methods inside their own class bodies.
- **Use Case**: Allows applying polymorphism across enum constants.

```java
public interface Command {
    void execute();
}

public enum SystemAction implements Command {
    START {
        @Override
        public void execute() {
            System.out.println("Starting system...");
        }
    },
    STOP {
        @Override
        public void execute() {
            System.out.println("Stopping system...");
        }
    };
}
```

### Enum Singleton pattern

Joshua Bloch famously wrote in *Effective Java* that a single-element enum is the best way to implement a Singleton.
- **Built-in Thread-Safety**: The JVM guarantees that enum instances are created in a thread-safe manner when the class is loaded.
- **Reflection Protection**: The Java reflection API explicitly prevents instantiating enums (throws `IllegalArgumentException` in `Constructor.newInstance()`), blocking reflection attacks.
- **Serialization Safety**: The Java serialization mechanism ensures that no duplicate instances are created upon deserialization.

```java
public enum CacheManager {
    INSTANCE;

    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public Object get(String key) {
        return cache.get(key);
    }
}
```

---

## Common Mistakes

### 1. Declaring an enum class as abstract or final manually
**Mistake**: Adding `abstract` or `final` modifiers to an enum declaration.
```java
public final enum Color { RED, GREEN } // Compile error!
```
*Consequence*: The compiler automatically adds the correct modifiers depending on whether the enum constants have class bodies. Manual decoration is forbidden.

### 2. Attempting to bypass the Singleton pattern via reflection
**Mistake**: Trying to use reflection to create a new instance of an enum Singleton.
```java
Constructor<CacheManager> constructor = CacheManager.class.getDeclaredConstructor(String.class, int.class);
constructor.setAccessible(true);
CacheManager newInstance = constructor.newInstance("MOCK", 1); // Throws IllegalArgumentException!
```
*Consequence*: Java runtime explicitly prevents enum instantiation via reflection to preserve the Singleton contract.

## Reference Links

- [Official Oracle Java Tutorials - Enum Types](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html)
- [Java Platform, Standard Edition API Specification - Enum Class](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Enum.html)
