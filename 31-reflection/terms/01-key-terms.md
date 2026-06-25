# Reflection Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## reflection

Reflection is a runtime capability of the Java Virtual Machine (JVM) that allows a program to inspect, query, and modify the metadata and behavior of its own classes, interfaces, fields, methods, and constructors during execution.

* **Why it matters**: It enables generic frameworks (like Spring, Hibernate, or Jackson) to operate on user-defined classes without compile-time knowledge of those classes, allowing for declarative configurations and automatic mapping.
* **Common confusion**: Developers often assume reflection is compile-time magic. In reality, reflection occurs entirely at runtime, bypassing compile-time static type-checking and accessibility verifications.
* **Small example**:
  ```java
  // Bypasses static checks to invoke a method by name at runtime
  java.lang.reflect.Method method = target.getClass().getMethod("toString");
  Object result = method.invoke(target);
  ```

## Class object

A `java.lang.Class` object is a special singleton metadata representation created by the JVM for every loaded Java class, interface, array type, primitive type, or `void` keyword.

* **Why it matters**: It serves as the primary entry point for the Reflection API. Without a reference to a `Class` object, you cannot retrieve methods, fields, constructors, or annotations.
* **Common confusion**: Confusing the `Class` object (which represents class metadata, allocated in Metaspace) with an instance of that class (which holds actual object data, allocated on the Heap).
* **Small example**:
  ```java
  Class<String> stringMetadata = String.class; // Class literal
  System.out.println("Simple Name: " + stringMetadata.getSimpleName()); // "String"
  ```

## Field

A `java.lang.reflect.Field` object represents metadata and accessor details for a single variable of a class or interface (static or instance field).

* **Why it matters**: It allows reading and writing field values dynamically at runtime, even if the fields are declared `private` or `protected`.
* **Common confusion**: Confusing `getField(name)` (which only returns public fields, including inherited ones) with `getDeclaredField(name)` (which returns any field declared directly inside the class, but not inherited ones).
* **Small example**:
  ```java
  java.lang.reflect.Field field = MyClass.class.getDeclaredField("secretCode");
  field.setAccessible(true);
  field.set(myInstance, 42); // Modifies the private field value
  ```

## Method

A `java.lang.reflect.Method` object represents a single method (static or instance) defined on a class or interface, enabling dynamic execution.

* **Why it matters**: It allows dynamic invocation of behavior based on string names and parameter types, which is essential for custom test runners or routing engines.
* **Common confusion**: Forgetting that exceptions thrown during the execution of a dynamically invoked method are not thrown directly; they are caught by the JVM reflection engine and wrapped inside a checked `java.lang.reflect.InvocationTargetException`.
* **Small example**:
  ```java
  java.lang.reflect.Method m = Math.class.getMethod("abs", double.class);
  double result = (double) m.invoke(null, -10.5); // Static call passes null
  System.out.println(result); // 10.5
  ```

## Constructor

A `java.lang.reflect.Constructor` object represents a class constructor, providing metadata about constructor parameters and enabling dynamic object instantiation.

* **Why it matters**: It is the modern standard for instantiating classes dynamically (via `constructor.newInstance()`), replacing the deprecated and unsafe `Class.newInstance()`.
* **Common confusion**: Developers think `Class.newInstance()` is identical to `Constructor.newInstance()`. However, `Class.newInstance()` bypasses compile-time checks for checked exceptions and can only invoke public no-arg constructors, whereas `Constructor.newInstance()` can invoke any constructor (even private ones if made accessible) and correctly wraps exceptions.
* **Small example**:
  ```java
  java.lang.reflect.Constructor<String> c = String.class.getConstructor(byte[].class);
  String str = c.newInstance(new byte[]{72, 101, 108, 108, 111}); // "Hello"
  ```

## private access

Private access refers to language-level encapsulation constraints (e.g. `private` modifier). In reflection, this boundary is bypassed using `AccessibleObject.setAccessible(true)`.

* **Why it matters**: Bypassing private access is the mechanical foundation of framework-based dependency injection (DI) and serialization, allowing frameworks to wire fields or serialize states without forcing developers to write public getters/setters.
* **Common confusion**: Assuming `setAccessible(true)` modifies the actual access level of the field or class definition permanently. It only overrides the access check flags for that specific `Field`, `Method`, or `Constructor` instance in your code.
* **Small example**:
  ```java
  java.lang.reflect.Field f = Target.class.getDeclaredField("privateKey");
  f.setAccessible(true); // Bypasses private access check for subsequent reads/writes
  Object value = f.get(instance);
  ```
