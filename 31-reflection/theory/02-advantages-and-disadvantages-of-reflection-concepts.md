# Reflection - Part 2

## Learning Goal

This file covers a focused slice of **Reflection**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Advantages and disadvantages of reflection` | The critical tradeoffs of reflection regarding performance, design encapsulation, and extensibility. |
| `Reflection in frameworks such as Spring` | How modern enterprise frameworks use reflection to achieve Dependency Injection (DI) and dynamic behavior. |

---

## Detailed Notes

### Advantages and disadvantages of reflection

Reflection is a double-edged sword. While it offers unmatched runtime flexibility, it comes with severe costs.

#### Advantages
1. **Extensibility**: Applications can dynamically load third-party plugins or modules by name without static recompilation.
2. **Framework Decoupling**: Allows writing generic code that operates on arbitrary classes (e.g., JSON serializers, JDBC query builders).
3. **Rich Tooling**: Powering IDE features, code inspectors, dynamic proxies, mock libraries (Mockito), and test suites (JUnit).

#### Disadvantages
1. **Performance Overhead**:
   - **No Compiler Optimizations**: The JIT compiler cannot inline reflective method calls or optimize field lookups.
   - **Dynamic Type Checking**: Arguments must be checked, checked exceptions verified, and visibility permissions checked on every call.
   - **Object Wrapping**: Primitive arguments and return values must be boxed/unboxed, creating garbage collector overhead.
2. **Loss of Compile-time Safety**: Errors that would normally cause compilation failures (like misspelled field or class names) are deferred to runtime as exceptions.
3. **Bypassing Encapsulation**: Accessing private fields or methods breaks class invariants, making code highly fragile and tightly coupled to class internal implementations.
4. **Security/Module Restrictions**: Strong encapsulation rules in Java modules (since Java 9) or a configured JVM `SecurityManager` will block reflective access, causing runtime crashes.

#### Code Example: Performance Benchmark Idea
```java
// Reflective method lookup and invocation is typically 10x to 100x slower
// than direct method execution because of validation, boxing, and safety checks.
public class PerformanceComparison {
    public void executeDirect() {
        // Direct method call: resolved at compile-time and optimized by JIT
    }
    
    public void executeReflective(java.lang.reflect.Method m, Object target) throws Exception {
        m.invoke(target); // Expensive validation checks and JIT bypass on every call
    }
}
```

---

### Reflection in frameworks such as Spring

Enterprise Java frameworks rely heavily on reflection to manage object lifecycles, configure bean definitions, and implement dynamic cross-cutting concerns (Aspect-Oriented Programming).

- **Dependency Injection (DI)**: Spring scans classpath classes, reads annotations like `@Autowired` or `@Component`, and uses reflection to instantiate beans and inject dependencies directly into fields (even private ones) without constructor code.
- **Dynamic Proxies**: Spring AOP and `@Transactional` utilize `java.lang.reflect.Proxy` (or CGLIB code generation) to wrap your bean in a proxy object. When a method is called, the proxy intercepts the call reflectively, starts a transaction, delegates to your bean, and commits/rolls back the transaction.

#### Case Study: Simple Dependency Injection (DI) Container
```java
import java.lang.annotation.*;
import java.lang.reflect.Field;

// Define custom dependency marker annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Inject {}

class Engine {
    public void start() { System.out.println("Vroom!"); }
}

class Car {
    @Inject
    private Engine engine; // Spring injects this dynamically at runtime

    public void drive() { engine.start(); }
}

// Simple Container implementation demonstrating Spring-like reflection injection
public class MiniSpringContainer {
    public static void bootstrap(Car car) throws Exception {
        Class<?> clazz = car.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                // Instantiates dependency object reflectively
                Object dependency = field.getType().getDeclaredConstructor().newInstance();
                
                // Enable writing to the private engine field
                field.setAccessible(true);
                
                // Inject the dependency instance into the car instance
                field.set(car, dependency);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Car car = new Car();
        bootstrap(car);
        car.drive(); // Outputs "Vroom!"
    }
}
```

---

## Common Review Prompts

- **Why is reflection slower than direct code?**
  Reflection bypasses compile-time optimizations (like method inlining by the JIT compiler). It requires the JVM to perform name lookup, type matching, access check validation, and argument boxing/unboxing at runtime.
- **How does reflection break the Singleton pattern?**
  A client can obtain the private constructor of a Singleton class via reflection (`getDeclaredConstructor()`), change its accessibility with `setAccessible(true)`, and call `newInstance()` to create a second instance of the class.
- **What is the difference between JDK Dynamic Proxies and CGLIB?**
  - **JDK Dynamic Proxies** use standard reflection (`java.lang.reflect.Proxy`) to create proxy instances for classes that implement interfaces.
  - **CGLIB** generates subclasses at runtime to intercept method calls for classes that do not implement any interfaces.
