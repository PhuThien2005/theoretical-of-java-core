# Comparison and Logical Operators

Comparison operators produce boolean results. Logical operators combine boolean values into larger conditions.

## Comparison Operators

Java comparison operators include:

| Operator | Meaning |
|---|---|
| `==` | equal to |
| `!=` | not equal to |
| `>` | greater than |
| `<` | less than |
| `>=` | greater than or equal to |
| `<=` | less than or equal to |

Examples:

```java
int age = 20;
boolean adult = age >= 18; // true
boolean exact = age == 20; // true
```

Comparison expressions return `boolean`, not an integer.

## `==` With Primitives

For primitive values, `==` compares the actual values.

```java
int a = 10;
int b = 10;
System.out.println(a == b); // true
```

This is straightforward for numeric primitives, `char`, and `boolean`.

## `==` With Objects

For object references, `==` compares whether two references point to the same object. It does not compare the meaningful content of the object.

```java
String a = new String("Java");
String b = new String("Java");

System.out.println(a == b);      // false
System.out.println(a.equals(b)); // true
```

Use `.equals()` when you want content equality for objects that define meaningful equality, such as `String`.

## Logical Operators

Logical operators combine boolean expressions.

| Operator | Name | Meaning |
|---|---|---|
| `&&` | logical AND | true only if both sides are true |
| `||` | logical OR | true if at least one side is true |
| `!` | logical NOT | reverses a boolean |

```java
boolean canEnter = age >= 18 && hasTicket;
boolean needsHelp = isNewUser || hasError;
boolean blocked = !canEnter;
```

## Short-Circuit Evaluation

`&&` and `||` short-circuit. That means Java may skip evaluating the right-hand side if the left-hand side already decides the result.

```java
if (user != null && user.isActive()) {
    System.out.println("Active user");
}
```

If `user != null` is false, Java does not call `user.isActive()`. This prevents a `NullPointerException`.

For `||`, if the left-hand side is true, Java skips the right-hand side.

```java
if (cached || loadFromDisk()) {
    System.out.println("Ready");
}
```

If `cached` is true, `loadFromDisk()` is not called.

## `&` and `|` With Booleans

Java also allows `&` and `|` with boolean operands. They do not short-circuit.

```java
if (user != null & user.isActive()) {
    // Dangerous if user is null.
}
```

Both sides are evaluated. This is rarely what beginners want in conditions. Prefer `&&` and `||` for normal decision logic.

## Common Mistakes

Do not confuse assignment and comparison:

```java
boolean ready = false;
// if (ready = true) { } // legal but usually wrong: assignment, not comparison
if (ready == true) { }
if (ready) { } // clearer
```

For booleans, `if (ready)` is usually clearer than `if (ready == true)`.

Do not use `==` for content equality of `String` values:

```java
String input = new String("yes");
if (input.equals("yes")) {
    System.out.println("Confirmed");
}
```

When the variable might be null, write the literal first:

```java
if ("yes".equals(input)) {
    System.out.println("Confirmed");
}
```
