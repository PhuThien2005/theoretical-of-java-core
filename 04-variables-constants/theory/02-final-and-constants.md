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

## Case Study: `final` Reference vs Immutable Object

A developer wants to make a list constant but still be able to add to it:

```java
public class Config {
    public static final List<String> ALLOWED_ROLES =
            new ArrayList<>(Arrays.asList("ADMIN", "USER"));

    public static void main(String[] args) {
        ALLOWED_ROLES.add("MODERATOR"); // Allowed — the list object is mutable
        System.out.println(ALLOWED_ROLES); // [ADMIN, USER, MODERATOR]

        // ALLOWED_ROLES = new ArrayList<>(); // compile error — cannot reassign final
    }
}
```

`final` only protects the reference. The `ArrayList` itself can still be modified.

**To truly protect the list:**

```java
public static final List<String> ALLOWED_ROLES =
        Collections.unmodifiableList(Arrays.asList("ADMIN", "USER"));

ALLOWED_ROLES.add("MODERATOR"); // throws UnsupportedOperationException at runtime
```

Or in Java 9+:

```java
public static final List<String> ALLOWED_ROLES = List.of("ADMIN", "USER"); // immutable
```

## Blank Final Variables

A `final` variable does not have to be initialized at declaration — but it must be assigned exactly once before first use.

```java
public class Circle {
    final double radius; // blank final field

    public Circle(double r) {
        radius = r; // assigned in constructor — OK
    }

    // public Circle() {} // compile error: radius might not have been initialized
}
```

This pattern is useful when the value depends on constructor arguments.

## Common Mistakes

- Thinking `final` makes a mutable object immutable — only the reference is locked.
- Naming constants with normal camelCase — use `UPPER_SNAKE_CASE`.
- Using magic numbers instead of named constants.
- Making too many values global constants before they really need to be shared.
- Forgetting that blank final fields must be assigned in **every** constructor path.
