# break, continue, and return

`break`, `continue`, and `return` change the normal flow of execution. They are early-exit tools, but they exit different things.

## `break`

`break` exits the nearest loop or `switch`.

```java
for (int i = 0; i < 10; i++) {
    if (i == 3) {
        break;
    }
    System.out.println(i);
}
```

This prints `0`, `1`, and `2`, then exits the loop.

In a traditional `switch`, `break` prevents fall-through.

## `continue`

`continue` skips the rest of the current loop iteration and moves to the next iteration.

```java
for (int i = 0; i < 5; i++) {
    if (i == 2) {
        continue;
    }
    System.out.println(i);
}
```

This prints `0`, `1`, `3`, and `4`.

In a `for` loop, `continue` still goes to the update step before checking the condition again.

## `return`

`return` exits the current method.

```java
int max(int a, int b) {
    if (a >= b) {
        return a;
    }

    return b;
}
```

In a non-void method, `return` must provide a value compatible with the method return type. In a `void` method, `return;` exits without a value.

## Comparing The Three

| Statement | Exits what? | Common use |
|---|---|---|
| `break` | nearest loop or switch | stop searching, prevent switch fall-through |
| `continue` | current loop iteration | skip one item and keep looping |
| `return` | current method | finish a method early or return a result |

## Early Exit And Readability

Early exits can make code clearer when they remove unnecessary nesting.

```java
void process(User user) {
    if (user == null) {
        return;
    }

    if (!user.isActive()) {
        return;
    }

    sendMessage(user);
}
```

This uses guard clauses. The main action appears after invalid cases are handled.

Early exits can also hurt readability if scattered unpredictably through a long method. Use them to clarify the flow, not to hide it.

## Nested Loops

Inside nested loops, plain `break` exits only the nearest loop.

```java
for (int row = 0; row < 3; row++) {
    for (int col = 0; col < 3; col++) {
        if (row == col) {
            break;
        }
    }
}
```

If you need to exit an outer loop, you can use a flag, extract a method and `return`, or use a labeled `break`.
