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

- **`getField(name)` vs `getDeclaredField(name)`**:
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
- **`setAccessible(true)`**: Disables runtime access modifier checks for that specific reflective invocation.
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
