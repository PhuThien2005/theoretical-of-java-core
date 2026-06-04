# if, else, and switch

Branching lets a Java program choose different paths.

## `if`

An `if` statement runs a block only when its condition is true.

```java
if (score >= 60) {
    System.out.println("Pass");
}
```

The condition must be a `boolean` expression. Java does not treat `0`, `1`, empty strings, or non-null objects as conditions the way some languages do.

```java
int count = 1;
// if (count) { } // invalid in Java
```

## `if/else`

`else` provides an alternative path.

```java
if (score >= 60) {
    System.out.println("Pass");
} else {
    System.out.println("Fail");
}
```

Exactly one of the two blocks runs.

## `else if`

An `else if` chain checks conditions in order. Java runs the first matching branch and skips the rest.

```java
if (score >= 90) {
    grade = "A";
} else if (score >= 80) {
    grade = "B";
} else if (score >= 70) {
    grade = "C";
} else {
    grade = "D";
}
```

Order matters. Put more specific or stricter conditions before broader ones.

## The Dangling `else`

When braces are omitted, an `else` belongs to the nearest unmatched `if`.

```java
if (loggedIn)
    if (isAdmin)
        System.out.println("Admin");
    else
        System.out.println("Not admin");
```

The `else` belongs to `if (isAdmin)`, not `if (loggedIn)`. Use braces to avoid ambiguity.

## Guard Clauses

A guard clause handles an invalid or special case early, often with `return`.

```java
void printName(String name) {
    if (name == null || name.isBlank()) {
        return;
    }

    System.out.println(name);
}
```

Guard clauses reduce nesting. Instead of wrapping the main logic inside a large `if`, the method exits early when it cannot continue.

## `switch` Statement

A `switch` selects a branch based on one expression.

```java
switch (day) {
    case 1:
        System.out.println("Monday");
        break;
    case 2:
        System.out.println("Tuesday");
        break;
    default:
        System.out.println("Unknown");
}
```

Traditional `switch` statements require `break` to prevent fall-through.

## Fall-Through

Fall-through means execution continues from one `case` into the next because there is no `break`, `return`, or other exit.

```java
switch (level) {
    case 1:
        System.out.println("Beginner");
    case 2:
        System.out.println("Intermediate");
}
```

If `level` is `1`, both lines print. Sometimes fall-through is intentional, but in beginner code it is usually a bug.

## When To Use `switch`

Use `switch` when one expression is compared against a clear set of known values.

Use `if/else` when conditions involve ranges, multiple variables, or complex boolean logic.
