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

## Why Logical Operators Short-Circuit and How They Differ From Bitwise Operators

Short-circuiting is both a performance optimization and a safety mechanism built into Java's logical AND (`&&`) and OR (`||`) operators. By halting evaluation as soon as the final result of the expression is mathematically guaranteed (e.g., a `false` on the left of `&&`, or a `true` on the left of `||`), the virtual machine avoids wasting CPU cycles on unnecessary computations. More importantly, this behavior allows developers to write defensive guards, such as validating that an object reference is not null before checking its properties, all in a single expression. In contrast, boolean logical/bitwise operators (`&` and `|`) do not short-circuit and always evaluate both operands regardless of the left-hand side's result. If the right-hand side contains operations that rely on the safety check on the left, using a non-short-circuiting operator will result in runtime errors.

### Decision Flow Mental Model

The following diagram illustrates how `&&` (short-circuiting) and `&` (non-short-circuiting) handle a `false` left-hand side (LHS):

```mermaid
graph TD
    Start[Start: Evaluate LHS] --> LHS{Is LHS true?}
    LHS -- No (&&) --> SC[Short-Circuit: Return false\n(RHS is skipped)]
    LHS -- No (&) --> NoSC[No Short-Circuit: Evaluate RHS\n(Can cause NullPointerException)]
    NoSC --> Return[Return false]
```

### Cause-Effect Chain
`name` reference is `null` → `name != null` evaluates to `false` → `&&` operator detects a `false` left-hand operand → Java short-circuits and skips evaluating the right-hand side (`name.length() > 0`) → execution completes safely and returns `false` without throwing an exception.

### Code Example
```java
String name = null;

// Case 1: Safe short-circuit evaluation
boolean isNotEmptySafe = (name != null && name.length() > 0);
System.out.println(isNotEmptySafe); // false (LHS is false, RHS is ignored)

// Case 2: Unsafe non-short-circuit evaluation
try {
    boolean isNotEmptyUnsafe = (name != null & name.length() > 0);
} catch (NullPointerException e) {
    System.out.println("Caught NullPointerException!"); // Prints: Caught NullPointerException!
}
```

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
