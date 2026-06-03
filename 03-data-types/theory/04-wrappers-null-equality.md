# Wrapper Classes, Boxing, Null, And Equality

Wrapper classes are object versions of primitive types.

| Primitive | Wrapper |
| --- | --- |
| `byte` | `Byte` |
| `short` | `Short` |
| `int` | `Integer` |
| `long` | `Long` |
| `float` | `Float` |
| `double` | `Double` |
| `char` | `Character` |
| `boolean` | `Boolean` |

## Why Wrappers Exist

Many Java APIs work with objects, not primitives.

For example, collections cannot store primitive types directly:

```java
// List<int> numbers; // invalid
List<Integer> numbers;
```

`Integer` is used instead of `int`.

## Autoboxing

Autoboxing is automatic conversion from primitive to wrapper.

```java
Integer number = 10;
```

Java treats this roughly like:

```java
Integer number = Integer.valueOf(10);
```

## Unboxing

Unboxing is automatic conversion from wrapper to primitive.

```java
Integer boxed = 10;
int value = boxed;
```

Java extracts the primitive `int` value from the `Integer` object.

## Null And Unboxing

Wrappers can be `null`. Primitives cannot.

```java
Integer boxed = null;
int value = boxed; // NullPointerException at runtime
```

This fails because Java tries to unbox `null`.

## `==` With Primitives

With primitives, `==` compares values.

```java
int a = 5;
int b = 5;
System.out.println(a == b); // true
```

## `==` With References

With reference types, `==` compares whether two references point to the same object.

```java
String a = new String("Java");
String b = new String("Java");

System.out.println(a == b); // false
```

The contents are the same, but the objects are different.

## `.equals()`

`.equals()` is usually used to compare object content.

```java
String a = new String("Java");
String b = new String("Java");

System.out.println(a.equals(b)); // true
```

For `String`, `.equals()` compares the character content.

## Null-Safe String Comparison

This can throw `NullPointerException` if `text` is null:

```java
text.equals("Java")
```

This is safer:

```java
"Java".equals(text)
```

If `text` is null, the result is `false`, not an exception.

## Wrapper Equality Warning

Avoid comparing wrapper objects with `==` unless you specifically want reference comparison.

```java
Integer a = 1000;
Integer b = 1000;
System.out.println(a == b); // often false
System.out.println(a.equals(b)); // true
```

Use `.equals()` for value comparison.

## Common Mistakes

- Comparing String content with `==`.
- Forgetting wrappers can be `null`.
- Unboxing a null wrapper.
- Using `List<int>` instead of `List<Integer>`.
- Assuming `.equals()` is always null-safe when called on a possibly null variable.
