# Final Variables And Constants

`final` means a variable can be assigned only once.

```java
final int maxScore = 100;
```

After assignment, you cannot assign a new value:

```java
maxScore = 90; // does not compile
```

## final With Primitives

For primitive variables, `final` prevents changing the stored primitive value.

```java
final int age = 18;
// age = 19; // invalid
```

## final With References

For reference variables, `final` prevents changing the reference, not necessarily the object.

```java
final StringBuilder builder = new StringBuilder("Java");
builder.append(" Core"); // allowed
// builder = new StringBuilder("Other"); // not allowed
```

The variable `builder` must keep pointing to the same object, but the object may still be mutable.

## Constants

A constant is a value intended not to change.

Java constants are often declared as `static final`.

```java
public static final int MAX_RETRY_COUNT = 3;
```

`static` means the value belongs to the class.

`final` means the variable cannot be reassigned.

Constants usually use UPPER_SNAKE_CASE.

## Why Constants Matter

Constants remove magic numbers and magic strings.

Weak:

```java
if (retryCount > 3) {
    // ...
}
```

Better:

```java
if (retryCount > MAX_RETRY_COUNT) {
    // ...
}
```

The second version explains the meaning of `3`.

## Compile-Time Constants

Some `static final` primitives and Strings initialized with constant expressions are compile-time constants.

```java
public static final int MAX_SIZE = 100;
public static final String APP_NAME = "Learning Java";
```

You do not need to master compile-time constants immediately, but you should recognize that constants are commonly used for shared fixed values.

## Common Mistakes

- Thinking `final` makes a mutable object immutable.
- Naming constants with normal camelCase.
- Using magic numbers instead of named constants.
- Making too many values global constants before they really need to be shared.
