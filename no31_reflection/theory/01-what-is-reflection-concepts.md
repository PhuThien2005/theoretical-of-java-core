# Reflection - Part 1

## Learning Goal

This file covers a focused slice of **Reflection**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `What is Reflection?` | Reflection lets code inspect and manipulate classes, fields, methods, and constructors at runtime. |
| `Class<?>` | Class<?> represents metadata of a loaded Java class or interface in the JVM. |
| `Get class information` | Querying modifiers, package names, parent classes, and implemented interfaces at runtime. |
| `Get field` | Retrieving public or declared Field objects representing class fields. |
| `Get method` | Retrieving public or declared Method objects to inspect method signatures. |
| `Get constructor` | Retrieving public or declared Constructor objects to inspect class constructors. |
| `Invoke method using reflection` | Executing a method dynamically at runtime via reflection. |
| `Create object using reflection` | Instantiating classes dynamically using constructor reflection. |
| `Access private field/method` | Bypassing language access controls to read/write private fields or call private methods. |
| `Annotation + reflection` | Querying runtime annotations metadata using reflection methods. |

---

## Detailed Notes

### What is Reflection?

Reflection is a feature in Java that allows an executing program to inspect or manipulate its own internal structures (classes, interfaces, fields, methods, constructors) at runtime. It bypasses compile-time type verification, enabling dynamic behavior.

- **Dynamic Loading and Binding**: Reflection allows applications to load classes by name at runtime and instantiate them without compile-time dependencies.
- **Framework Enabler**: It is the mechanical foundation of Dependency Injection (DI) containers, Object-Relational Mappers (ORMs), testing libraries (JUnit), and serialization frameworks (Jackson).

#### Code Example: Checking Class Structure
```java
public class SimpleInspector {
    public static void printType(Object obj) {
        if (obj == null) return;
        Class<?> clazz = obj.getClass();
        System.out.println("Class Name: " + clazz.getName());
        System.out.println("Is Interface: " + clazz.isInterface());
    }
}
```

---

### Class<?>

`java.lang.Class` is the entry point for all reflection operations. The JVM instantiates a `Class` object for every type loaded.

- **Obtaining a Class Reference**:
  1. **From an instance**: `String s = ""; Class<?> c = s.getClass();`
  2. **From a class literal**: `Class<?> c = String.class;`
  3. **From a dynamic name string**: `Class<?> c = Class.forName("java.lang.String");` (throws Checked Exception `ClassNotFoundException`).

#### Code Example: Three Ways to Get Class Reference
```java
public class ClassFetchers {
    public static void main(String[] args) throws ClassNotFoundException {
        // 1. Instance
        String text = "hello";
        Class<? extends String> c1 = text.getClass();

        // 2. Class literal (safest, resolved at compile-time)
        Class<String> c2 = String.class;

        // 3. Dynamic lookup (flexible but throws CNFE)
        Class<?> c3 = Class.forName("java.lang.String");

        System.out.println(c1 == c2 && c2 == c3); // Prints true (singleton per ClassLoader)
    }
}
```

## Why Reflection Bypasses Compile-Time Type Safety

In standard Java execution, the compiler enforces static type checking by validating type compatibility, method signatures, and accessibility constraints before producing bytecode. Reflection bypasses these checks because it operates directly on JVM class metadata (`Class<?>` instances, `Method`, `Field`, `Constructor` objects) resolved dynamically at runtime rather than compile time. When code uses reflection, the compiler cannot verify whether a target method actually exists, whether argument types are compatible, or if access constraints are violated. Instead, these checks are deferred to the JVM execution engine, which performs dynamic lookup and verification during execution, shifting what would be compile-time errors into runtime exceptions.

### Mental Model: Compile-Time vs. Runtime Reflection Resolution
```mermaid
graph TD
    subgraph Compile Time [Compile-Time (Static Check)]
        A[Source Code: obj.someMethod()] --> B[Compiler checks Type of obj]
        B --> C{Method exists in declared class?}
        C -- Yes --> D[Generate invokevirtual/invokestatic Bytecode]
        C -- No --> E[Compilation Error]
    end
    subgraph Run Time [Runtime (Reflection Dynamic Resolution)]
        F[Reflective Code: method.invoke(obj)] --> G[Bypass compiler check: Type is java.lang.Object]
        G --> H[JVM queries Class<?> metadata at runtime]
        H --> I{Method signature matching & access checks?}
        I -- Pass --> J[Execute Method via JVM Engine]
        I -- Fail --> K[Throw NoSuchMethodException / IllegalAccessException]
    end
```

### Code Example: Bypassing Compile-time Verification
```java
import java.lang.reflect.Method;

public class TypeSafetyBypass {
    public static void main(String[] args) {
        Object target = "Hello Reflection";
        try {
            // Compile-time checks are bypassed because 'target' is declared as Object.
            // String type is resolved at runtime dynamically.
            Method lengthMethod = target.getClass().getMethod("length");
            Object result = lengthMethod.invoke(target);
            System.out.println("Result: " + result); // Prints "Result: 16"
            
            // Misspelled method name bypasses compile-time verification but fails at runtime
            Method invalidMethod = target.getClass().getMethod("lenght"); 
        } catch (Exception e) {
            System.out.println("Error: " + e.getClass().getSimpleName()); 
            // Prints "Error: NoSuchMethodException"
        }
    }
}
```

### Cause-Effect Chain
Reflective lookup with misspelled method name &rarr; Compiler sees generic metadata API call &rarr; Compilation succeeds &rarr; JVM attempts dynamic lookup at runtime &rarr; Method not found in `Class<?>` metadata &rarr; `NoSuchMethodException` thrown at runtime

---

### Get class information

Once you hold a `Class<?>` reference, you can inspect all aspects of the loaded type.

- **Superclass**: `clazz.getSuperclass()` returns the Class object of the parent class.
- **Interfaces**: `clazz.getInterfaces()` returns an array of interfaces implemented directly by the class.
- **Modifiers**: `clazz.getModifiers()` returns an integer mask of modifiers, which can be interpreted using `java.lang.reflect.Modifier`.

#### Code Example: Extracting Class Modifiers
```java
import java.lang.reflect.Modifier;

public class ModifierInspector {
    public static void inspect(Class<?> clazz) {
        int mods = clazz.getModifiers();
        System.out.println("Is Public: " + Modifier.isPublic(mods));
        System.out.println("Is Abstract: " + Modifier.isAbstract(mods));
        System.out.println("Is Final: " + Modifier.isFinal(mods));
    }
}
```

---

### Get field

Fields are inspected using `java.lang.reflect.Field`.

- **`getField(name)`** — getField(name): Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
  - `getField(name)` returns the specified **public** field, searching through the class and all its superclasses.
  - `getDeclaredField(name)` returns the specified field declared **directly** in the class (including private, protected, package-private), but ignores inherited fields.
- **Listing Fields**: `getFields()` vs `getDeclaredFields()` follow the same scoping rule.

#### Code Example: Reading Public vs Declared Fields
```java
import java.lang.reflect.Field;

class Parent {
    public int publicParentField;
}
class Child extends Parent {
    private int privateChildField;
    public int publicChildField;
}

public class FieldInspector {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Child.class;

        // getFields() -> returns publicParentField and publicChildField
        System.out.println("Public fields count: " + clazz.getFields().length); // 2

        // getDeclaredFields() -> returns only privateChildField and publicChildField
        System.out.println("Declared fields count: " + clazz.getDeclaredFields().length); // 2
    }
}
```

---

### Get method

Methods are represented by `java.lang.reflect.Method`.

- **Scoping**: `getMethod(name, parameterTypes...)` matches public methods (including inherited). `getDeclaredMethod(name, parameterTypes...)` matches any method defined in this class only.
- **Overloading Match**: You must pass the exact parameter types to locate overloaded methods (e.g., `getDeclaredMethod("setName", String.class)`).

#### Code Example: Fetching a Method by Signature
```java
import java.lang.reflect.Method;

class UserService {
    public void update(int id, String name) {}
}

public class MethodFetch {
    public static void main(String[] args) throws Exception {
        Class<UserService> clazz = UserService.class;
        // Specify parameter types to match the overload exactly
        Method m = clazz.getDeclaredMethod("update", int.class, String.class);
        System.out.println("Found method: " + m.toString());
    }
}
```

---

### Get constructor

Constructors are represented by `java.lang.reflect.Constructor`.

- **Instantiation**: You fetch a constructor to instantiate objects dynamically.
- **Signature matching**: Similar to methods, you specify the argument type array to find a specific overloaded constructor.

#### Code Example: Fetching Constructors
```java
import java.lang.reflect.Constructor;

class Product {
    public Product() {}
    public Product(String name, double price) {}
}

public class ConstructorInspector {
    public static void main(String[] args) throws Exception {
        Class<Product> clazz = Product.class;
        
        // Fetch default public constructor
        Constructor<Product> c1 = clazz.getConstructor();
        
        // Fetch parameterized constructor
        Constructor<Product> c2 = clazz.getConstructor(String.class, double.class);
        
        System.out.println("Default: " + c1);
        System.out.println("Parameterized: " + c2);
    }
}
```

---

### Invoke method using reflection

You execute methods dynamically using `Method.invoke(targetObject, arguments...)`.

- **Static Methods**: For static methods, pass `null` as the `targetObject`.
- **Exceptions**: Any exceptions thrown inside the invoked method are caught by the reflection engine and wrapped inside a checked `java.lang.reflect.InvocationTargetException`. You call `getTargetException()` or `getCause()` to inspect the real underlying error.

#### Code Example: Dynamically Invoking a Method
```java
import java.lang.reflect.Method;

public class Invoker {
    public static void main(String[] args) throws Exception {
        String text = "hello world";
        Method uppercaseMethod = String.class.getMethod("toUpperCase");
        
        // Equivalent to text.toUpperCase()
        Object result = uppercaseMethod.invoke(text);
        System.out.println(result); // Prints "HELLO WORLD"
    }
}
```

---

### Create object using reflection

Historically, you could instantiate an object using `Class.newInstance()`. However, this is deprecated starting in Java 9.

- **Deprecated `Class.newInstance()`**: Bypasses compile-time exception checks and propagates checked exceptions directly (violating Java's exception handling rules).
- **Correct Pattern**: Fetch the constructor explicitly and call `Constructor.newInstance(args...)`. It wraps thrown exceptions into `InvocationTargetException`.

#### Code Example: Creating Instances (Java 9+ standard)
```java
import java.lang.reflect.Constructor;

public class Instantiator {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = java.util.ArrayList.class;
        
        // CORRECT: getConstructor().newInstance()
        Constructor<?> constructor = clazz.getConstructor();
        Object list = constructor.newInstance();
        
        System.out.println("List created: " + list.getClass().getName()); // ArrayList
    }
}
```

---

### Access private field/method

Reflection can bypass access controls (like `private`, `protected`, or package-private) using `AccessibleObject.setAccessible(true)`.

- **AccessibleObject**: Superclass of `Field`, `Method`, and `Constructor`.
- **`setAccessible(true)`** — setAccessible(true): Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **Constraints**: 
  - A `SecurityManager` (if present) can block this action.
  - Java Module System (Java 9+) blocks deep reflection into non-exported modules unless `--add-opens` is passed to the JVM.

#### Code Example: Injecting Value Into a Private Field
```java
import java.lang.reflect.Field;

class Account {
    private double balance = 10.0;
}

public class PrivateAccess {
    public static void main(String[] args) throws Exception {
        Account account = new Account();
        Class<?> clazz = account.getClass();
        
        Field balanceField = clazz.getDeclaredField("balance");
        // Bypass the private check
        balanceField.setAccessible(true);
        
        // Read value
        double value = (double) balanceField.get(account);
        System.out.println("Original Balance: " + value); // 10.0
        
        // Write value
        balanceField.set(account, 5000.0);
        System.out.println("Modified Balance: " + balanceField.get(account)); // 5000.0
    }
}
```

## Why setAccessible(true) Can Bypass Access Controls and Its Module System Constraints

Java's access modifiers (`private`, `protected`, package-private) are language-level constraints designed to enforce encapsulation and maintain class invariants at compile time. Under the hood, the JVM enforces these modifiers at runtime by verifying field access and method invocation bytecode instructions. When a program invokes `AccessibleObject.setAccessible(true)` on a reflective object (such as a `Field` or `Method`), it instructs the JVM to disable these runtime access checks for that specific object instance. However, in modern Java versions, this capability is heavily restricted: a configured `SecurityManager` (if present) can block the operation by throwing a `SecurityException`, and the Java Module System (introduced in Java 9) strictly prevents deep reflection into encapsulated packages of named modules unless the module explicitly exports or opens the package to the caller.

### Mental Model: Accessibility Override Decision Tree
```mermaid
graph TD
    A[Caller Class] --> B{setAccessible(true)}
    B --> C{Is target class in a named module?}
    C -- Yes --> D{Is package opened/exported to caller?}
    D -- No --> E[InaccessibleObjectException thrown]
    D -- Yes --> F{Is SecurityManager active?}
    C -- No --> F
    F -- Yes --> G{Permission checkMemberAccess granted?}
    G -- No --> H[SecurityException thrown]
    G -- Yes --> I[Override flags set: access checks bypassed]
    F -- No --> I
```

### Code Example: Module Boundaries and setAccessible Constraints
```java
import java.lang.reflect.Field;

public class ModuleReflectionBypass {
    public static void main(String[] args) {
        try {
            // Attempting to reflectively access a private field in java.lang.String (module java.base)
            Field valueField = String.class.getDeclaredField("value");
            valueField.setAccessible(true); 
            // In Java 9+, this throws java.lang.reflect.InaccessibleObjectException 
            // because java.lang package is not opened to unnamed modules.
        } catch (Exception e) {
            System.out.println("Caught exception: " + e.getClass().getName());
            // Prints "Caught exception: java.lang.reflect.InaccessibleObjectException"
        }
    }
}
```

### Cause-Effect Chain
`setAccessible(true)` called on module-internal field &rarr; JVM checks module boundaries &rarr; Package not opened to caller module &rarr; JVM rejects override request &rarr; `InaccessibleObjectException` thrown at runtime

---

### Annotation + reflection

Annotations are only visible to reflection at runtime if they are configured with `@Retention(RetentionPolicy.RUNTIME)`.

- **Retrieving Metadata**: Methods like `isAnnotationPresent(Annotation.class)` and `getAnnotation(Annotation.class)` are available on `Class`, `Field`, `Method`, and `Constructor` because they implement the `AnnotatedElement` interface.

#### Code Example: Simple Annotation Processor
```java
import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface RunMe {}

class Task {
    @RunMe
    public void step1() { System.out.println("Running step 1"); }
    public void step2() { System.out.println("Running step 2"); }
}

public class AnnotationRunner {
    public static void main(String[] args) throws Exception {
        Task task = new Task();
        for (Method m : Task.class.getDeclaredMethods()) {
            if (m.isAnnotationPresent(RunMe.class)) {
                m.invoke(task); // Runs step1, skips step2
            }
        }
    }
}
```

---

## Common Review Prompts

- **Which concepts here are compile-time rules?**
  None; Reflection is entirely a runtime API. However, attempting reflection on invalid names throws exceptions (like `ClassNotFoundException` or `NoSuchMethodException`) at runtime.
- **Which concepts here affect runtime behavior?**
  Instantiations, method executions, private accesses, and exceptions.
- **Which concepts here are likely interview traps?**
  - Confusing `getField()` (public, inherited) with `getDeclaredField()` (any field, no inheritance).
  - Forgetting that exceptions thrown inside reflected code are wrapped in `InvocationTargetException`.
  - Using deprecated `Class.newInstance()` instead of `Constructor.newInstance()`.
  - Bypassing private modifiers using `setAccessible(true)` failing under restrictive JVM module configurations.
