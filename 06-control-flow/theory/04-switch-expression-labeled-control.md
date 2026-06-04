# Switch Expressions and Labeled Control

Modern Java has switch expressions, which make `switch` usable as a value-producing expression. Java also has labeled `break` and `continue`, mostly for nested loops.

## Switch Statement vs Switch Expression

A switch statement performs actions.

```java
switch (status) {
    case "NEW":
        System.out.println("Create record");
        break;
    case "DONE":
        System.out.println("Archive record");
        break;
    default:
        System.out.println("Unknown");
}
```

A switch expression produces a value.

```java
String label = switch (status) {
    case "NEW" -> "Create record";
    case "DONE" -> "Archive record";
    default -> "Unknown";
};
```

The expression form is useful when every branch should produce a result.

## Arrow Cases

Arrow cases use `->` and do not fall through.

```java
switch (day) {
    case 1 -> System.out.println("Monday");
    case 2 -> System.out.println("Tuesday");
    default -> System.out.println("Unknown");
}
```

This reduces accidental fall-through.

## `yield` In Switch Expressions

When a switch expression branch needs a block, use `yield` to provide the value.

```java
String message = switch (code) {
    case 200 -> "OK";
    case 500 -> {
        logError();
        yield "Server error";
    }
    default -> "Unknown";
};
```

`yield` is not the same as `return`. `yield` provides a value for the switch expression. `return` exits the method.

## Exhaustiveness

A switch expression must be exhaustive: it must cover every possible input value or have a `default`.

```java
String label = switch (day) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    default -> "Other";
};
```

This rule exists because an expression must produce a value.

## Labeled `break`

A label can name a loop. A labeled `break` exits the named loop.

```java
outer:
for (int row = 0; row < 3; row++) {
    for (int col = 0; col < 3; col++) {
        if (row == 1 && col == 1) {
            break outer;
        }
    }
}
```

This exits both loops.

## Labeled `continue`

A labeled `continue` jumps to the next iteration of the named loop.

```java
outer:
for (int row = 0; row < 3; row++) {
    for (int col = 0; col < 3; col++) {
        if (col == 1) {
            continue outer;
        }
    }
}
```

This skips the rest of the inner loop and moves to the next outer-loop iteration.

## Use Labels Carefully

Labels are legal, but they can make code harder to read if overused. Often, extracting nested logic into a method and using `return` is clearer.

Use labels mainly when:

- You are inside nested loops.
- The exit target would otherwise require awkward flags.
- The labeled control flow remains easy to see.
