# Annotation - Part 2

## Learning Goal

This file covers a focused slice of **Annotation**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `@Documented` |@Documented is a specific concept in Annotation; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `@Inherited` |@Inherited is a specific concept in Annotation; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `@Repeatable` |@Repeatable is a specific concept in Annotation; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Custom annotation` | An annotation attaches metadata to program elements such as classes, methods, or fields. |
| `Runtime annotation` | An annotation attaches metadata to program elements such as classes, methods, or fields. |
| `Basic annotation processing` | An annotation attaches metadata to program elements such as classes, methods, or fields. |

## Detailed Notes

### @Documented

@Documented is a specific concept in Annotation; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `@Documented` in one sentence.
- Recognize `@Documented` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `@Documented`.

Tiny example or mental model:

- When reading code, ask: what does `@Documented` change, allow, reject, or clarify?

#### Concrete Explanation & Java Rules
By default, annotations applied to a class, method, or field are NOT included in the generated Javadoc. When a custom annotation declaration is annotated with the meta-annotation `@Documented`, any code elements annotated with this custom annotation will have it displayed in their generated API documentation.

```java
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PublishedAPI {
    String version() default "1.0";
}

// When generating Javadoc, @PublishedAPI(version = "2.0") will appear above publicApiMethod()
public class JavadocDemo {
    @PublishedAPI(version = "2.0")
    public void publicApiMethod() {}
}
```

---

### @Inherited

@Inherited is a specific concept in Annotation; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `@Inherited` in one sentence.
- Recognize `@Inherited` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `@Inherited`.

Tiny example or mental model:

- When reading code, ask: what does `@Inherited` change, allow, reject, or clarify?

#### Concrete Explanation & Java Rules
`@Inherited` indicates that an annotation on a class is automatically inherited by its subclasses. If a class `Child` extends `Parent`, and `Parent` has an annotation marked `@Inherited`, reflection queries for the annotation on `Child` will succeed even if `Child` has no annotation declaration.

**Crucial Limitations:**
1. It **only** applies to class inheritance. It does NOT apply if a class implements an interface that has an `@Inherited` annotation.
2. It does NOT apply to methods, fields, or constructors. If a parent class method has an inherited annotation, a subclass overriding that method does not inherit the method-level annotation.

```java
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@interface InheritedAnnotation {}

@Retention(RetentionPolicy.RUNTIME)
@interface StandardAnnotation {}

@InheritedAnnotation
@StandardAnnotation
class SuperClass {}

class SubClass extends SuperClass {}

// At runtime:
// SubClass.class.isAnnotationPresent(InheritedAnnotation.class) -> true
// SubClass.class.isAnnotationPresent(StandardAnnotation.class) -> false
```

---

### @Repeatable

@Repeatable is a specific concept in Annotation; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `@Repeatable` in one sentence.
- Recognize `@Repeatable` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `@Repeatable`.

Tiny example or mental model:

- When reading code, ask: what does `@Repeatable` change, allow, reject, or clarify?

#### Concrete Explanation & Java Rules
Introduced in Java 8, `@Repeatable` allows applying the same annotation multiple times to the same element. It requires a "container annotation" type, which must have a `value()` method returning an array of the repeatable annotation type. The container annotation must have a retention policy at least as long as the repeatable annotation.

```java
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 1. The Container Annotation
@Retention(RetentionPolicy.RUNTIME)
@interface Schedules {
    Schedule[] value();
}

// 2. The Repeatable Annotation, referencing the container class
@Repeatable(Schedules.class)
@Retention(RetentionPolicy.RUNTIME)
@interface Schedule {
    String day();
    String time();
}

// 3. Usage
public class ScheduleDemo {
    @Schedule(day = "Monday", time = "09:00")
    @Schedule(day = "Friday", time = "17:00")
    public void runJob() {}
}
```

---

### Custom annotation

An annotation attaches metadata to program elements such as classes, methods, or fields.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Custom annotation` in one sentence.
- Recognize `Custom annotation` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Custom annotation`.

Tiny example or mental model:

- When reading code, ask: what does `Custom annotation` change, allow, reject, or clarify?

#### Concrete Explanation & Java Rules
Custom annotations are declared using the `@interface` syntax. They implicitly extend `java.lang.annotation.Annotation`. Their elements are defined like parameterless methods and can specify default values using the `default` keyword.

**Legal Element Return Types:**
- All primitive types (`int`, `char`, `double`, `boolean`, etc.)
- `java.lang.String`
- `java.lang.Class` (optionally parameterized)
- Any `enum` type
- Any annotation type
- One-dimensional arrays of any of the above types (e.g. `String[]`, `int[]`, `MyEnum[]`)

**Illegal Return Types:**
- Nested or multi-dimensional arrays (e.g. `String[][]`, `int[][]`)
- Object wrappers (e.g. `Integer`, `Double`, `Boolean`)
- Arbitrary classes (e.g. `java.util.Date`, `java.util.List`)
- Type parameters (generics)

```java
public @interface Configuration {
    String name();                    // Required element
    int poolSize() default 10;        // Element with a default value
    Class<?> driverClass() default Object.class; // Class element
    String[] options() default {};    // Array element
}
```

---

### Runtime annotation

An annotation attaches metadata to program elements such as classes, methods, or fields.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Runtime annotation` in one sentence.
- Recognize `Runtime annotation` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Runtime annotation`.

Tiny example or mental model:

- When reading code, ask: what does `Runtime annotation` change, allow, reject, or clarify?

#### Concrete Explanation & Java Rules
Runtime annotations are declared with `@Retention(RetentionPolicy.RUNTIME)`. The JVM keeps their metadata in memory, which allows them to be examined dynamically via Reflection. 

```java
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Priority {
    int value() default 1;
}
```

---

### Basic annotation processing

An annotation attaches metadata to program elements such as classes, methods, or fields.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Basic annotation processing` in one sentence.
- Recognize `Basic annotation processing` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Basic annotation processing`.

Tiny example or mental model:

- When reading code, ask: what does `Basic annotation processing` change, allow, reject, or clarify?

#### Concrete Explanation & Java Rules
Annotation processing can happen at compile-time (using the standard `Processor` API in `javax.annotation.processing`) or at runtime (using the Reflection API).
At runtime, classes like `Class`, `Method`, `Field`, `Constructor`, and `Parameter` all implement the `AnnotatedElement` interface, which provides reflective methods to read annotation metadata:
- `isAnnotationPresent(Class<? extends Annotation> annotationClass)`: Returns true if the element has the specified annotation.
- `getAnnotation(Class<T> annotationClass)`: Returns the annotation of the specified type if present, or null.
- `getAnnotations()`: Returns all annotations present on the element (including inherited ones).
- `getDeclaredAnnotations()`: Returns annotations directly declared on the element (excluding inherited ones).

```java
import java.lang.reflect.Method;

public class BasicProcessor {
    public static void runProcess() throws Exception {
        Method method = MyClass.class.getMethod("executeTask");
        if (method.isAnnotationPresent(Priority.class)) {
            Priority priority = method.getAnnotation(Priority.class);
            System.out.println("Processing method with priority: " + priority.value());
        }
    }
}

class MyClass {
    @Priority(5)
    public void executeTask() {}
}
```

#### Why Runtime Annotation Processing Uses Dynamic Proxies Under the Hood

At runtime, when reflection queries an annotation using methods like `element.getAnnotation(MyAnnotation.class)`, the JVM does not return a direct instance of a compiler-generated class. Instead, because annotations are interfaces, the JVM dynamically synthesizes a proxy class that implements the annotation interface. 

Under the hood, this proxy delegates all element accessors (which look like methods in the annotation declaration) to an `InvocationHandler`, typically `sun.reflect.annotation.AnnotationInvocationHandler` (in standard HotSpot/OpenJDK implementations). This handler maps the method names to the pre-parsed metadata values stored in the class's constant pool. 

This design is highly efficient: it avoids generating, loading, and verifying a separate physical class file for every annotation declaration at compile-time, saving memory and keeping class-loading fast.

**Mental Model:**
*Analogy:* Imagine a restaurant menu (annotation interface). When you order food, the waiter (dynamic proxy) takes your order. The waiter doesn't cook the food themselves; they consult a recipe book (the parsed constant-pool metadata map) and return the pre-cooked dish (metadata value).

```
[User Reflection Call] -> MyAnnotation.class.getAnnotation(...)
                                 |
                                 v
                    [JVM Synthesized Proxy Class]
                    (e.g., $Proxy1 implements MyAnnotation)
                                 |
                                 v
                   [AnnotationInvocationHandler]
                (holds a Map<String, Object> of values)
                                 |
                                 v
                [Parsed Constant-Pool Metadata Map]
                (e.g., "value" -> 5, "message" -> "Hello")
```

**Code Example with Expected Output:**
```java
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@interface RuntimeCheck {
    String value() default "Default";
}

@RuntimeCheck("ProxyTest")
class AnnotatedClass {}

public class ProxyDemo {
    public static void main(String[] args) {
        RuntimeCheck anno = AnnotatedClass.class.getAnnotation(RuntimeCheck.class);
        
        // Verify that the annotation instance is a Proxy
        System.out.println(java.lang.reflect.Proxy.isProxyClass(anno.getClass())); // true
        
        // Print the synthesized proxy class name
        System.out.println(anno.getClass().getName().contains("Proxy")); // true
        
        // Print the interfaces implemented by the proxy class
        for (Class<?> iface : anno.getClass().getInterfaces()) {
            System.out.println(iface.getSimpleName());
            // output:
            // RuntimeCheck
        }
    }
}
```

**Cause-Effect Chain:**
Application calls `element.getAnnotation(MyAnno.class)` &rarr; Reflection subsystem checks constant pool attributes of the class file &rarr; JVM detects presence of `MyAnno` &rarr; JVM invokes dynamic proxy generation to produce a class implementing `MyAnno` &rarr; Proxy delegates method calls to `AnnotationInvocationHandler` &rarr; Handler retrieves corresponding values from pre-parsed metadata map &rarr; Client receives the annotated element value.

---

## Case Study: Custom Validation Annotation & Reflection Processor

Let's build a real-world validation framework utilizing a custom `@NonNull` field annotation and a reflection-based validator that inspects object fields at runtime.

### 1. Declaring the Custom Annotation
We require the annotation to target fields (`ElementType.FIELD`) and be visible at runtime (`RetentionPolicy.RUNTIME`).

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NonNull {
    String message() default "Field value cannot be null";
}
```

### 2. Applying the Annotation to a Model Class
We will apply `@NonNull` to some private fields of a `User` class.

```java
public class User {
    @NonNull(message = "Username is required and cannot be null")
    private String username;

    @NonNull(message = "Email address must not be null")
    private String email;

    private String phoneNumber; // Optional field, not annotated

    public User(String username, String email, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
```

### 3. Writing the Reflection-based Validator
The validator uses reflection to inspect all fields (even private ones) of a given object. If a field is annotated with `@NonNull` and has a value of `null`, it throws an exception.

```java
import java.lang.reflect.Field;

public class ObjectValidator {
    
    public static void validate(Object obj) throws IllegalAccessException {
        if (obj == null) {
            throw new IllegalArgumentException("Cannot validate a null object");
        }
        
        Class<?> clazz = obj.getClass();
        
        // Loop through all fields declared in the class
        for (Field field : clazz.getDeclaredFields()) {
            // Check if the field is annotated with @NonNull
            if (field.isAnnotationPresent(NonNull.class)) {
                // Since fields might be private, make them accessible
                field.setAccessible(true);
                
                Object value = field.get(obj);
                
                // If value is null, read the message and throw exception
                if (value == null) {
                    NonNull annotation = field.getAnnotation(NonNull.class);
                    throw new IllegalArgumentException(
                        "Validation failed for field '" + field.getName() + "': " + annotation.message()
                    );
                }
            }
        }
    }
}
```

### 4. Running the Case Study
```java
public class ValidationApp {
    public static void main(String[] args) {
        User validUser = new User("alice", "alice@example.com", null);
        User invalidUser = new User("bob", null, "123-456");

        // 1. Validate Valid User
        try {
            ObjectValidator.validate(validUser);
            System.out.println("Valid user successfully verified.");
        } catch (IllegalAccessException | IllegalArgumentException e) {
            System.out.println("Unexpected failure: " + e.getMessage());
        }

        // 2. Validate Invalid User
        try {
            ObjectValidator.validate(invalidUser);
            System.out.println("Invalid user mistakenly verified.");
        } catch (IllegalAccessException | IllegalArgumentException e) {
            System.out.println("Caught expected validation error: " + e.getMessage());
        }
    }
}
```

---

## Common Mistakes

### 1. Repeatable Annotations: Using `getAnnotation()` instead of `getAnnotationsByType()`
If an annotation is repeatable (e.g., `@Schedule`), and it is applied multiple times to an element, Java's reflection engine wraps them inside their container annotation (e.g., `@Schedules`). 
Calling `element.getAnnotation(Schedule.class)` returns `null` at runtime if multiple `@Schedule` annotations are present. 
To retrieve repeatable annotations correctly:
- Call `element.getAnnotationsByType(Schedule.class)` which automatically unpacks the container.
- Or query the container itself: `element.getAnnotation(Schedules.class)`.

### 2. Assuming `@Inherited` applies to Interfaces
A common interview trap is assuming `@Inherited` works for interface implementation. If class `Child` implements interface `MyInterface`, and `MyInterface` is annotated with an `@Inherited` annotation, `Child` does **NOT** inherit the annotation.

### 3. Invalid Return Types in Annotation Elements
Attempting to declare an element returning a wrapper object class (such as `Integer` or `Boolean`) or nested array structures (such as `String[][]`) is a compilation error. Only primitives, String, Class, enums, annotations, and 1D arrays of these types are permitted.

---

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?

## Reference Links

- [Java reflection API](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Proxy.html)
- [OpenJDK sun.reflect.annotation.AnnotationInvocationHandler Source](https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/sun/reflect/annotation/AnnotationInvocationHandler.java)
- [Java Language Specification: Annotations](https://docs.oracle.com/javase/specs/jls/se21/html/jls-9.html#jls-9.7)
