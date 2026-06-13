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

---

## Common Mistakes

### Mistake 1 — Unreachable statements
Writing code immediately after a `break`, `continue`, or `return` statement in the same block causes a compile-time error.

```java
for (int i = 0; i < 5; i++) {
    if (i == 2) {
        continue;
        // BUG: Compile-time error: unreachable statement
        System.out.println("Skipping 2"); 
    }
}
```

**Fix**: Ensure no statements follow early-exit keywords in the same block.

### Mistake 2 — Confusing loop `break` with switch `break`
A `break` statement inside a `switch` block nested within a loop only exits the `switch`, NOT the loop itself.

```java
// BUG: Intended to exit the loop on status 200, but only exits the switch
while (true) {
    int status = getResponseCode();
    switch (status) {
        case 200:
            break; // Exits switch block, loop continues infinitely!
        case 500:
            System.out.println("Error");
            break;
    }
}

// FIX: Use a label, flag, or return to exit the loop
outerLoop:
while (true) {
    int status = getResponseCode();
    switch (status) {
        case 200:
            break outerLoop; // Exits the while loop labeled 'outerLoop'
        case 500:
            System.out.println("Error");
            break;
    }
}
```

### Mistake 3 — `continue` causing infinite loops in `while` loops
In a `for` loop, `continue` jumps to the update expression (e.g., `i++`). In a `while` or `do-while` loop, `continue` jumps directly to the condition check, skipping any update statements placed below it.

```java
int i = 0;
// BUG: Infinite loop because i++ is skipped when i == 1
while (i < 5) {
    if (i == 1) {
        continue; 
    }
    System.out.println(i);
    i++;
}

// FIX: Perform the update before continue or use a for loop
int i = 0;
while (i < 5) {
    if (i == 1) {
        i++;
        continue; 
    }
    System.out.println(i);
    i++;
}
```

---

## Case Study — Labeled Control Flow with Nested Loop Tracing

Labeled statements allow fine-grained control when managing nested loops. The label precedes the target loop (e.g., `labelName:`).

### Labeled `continue` Tracing Case Study

```java
outer:
for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 3; j++) {
        if (i == 2 && j == 2) {
            continue outer;
        }
        System.out.println("i=" + i + ", j=" + j);
    }
}
```

**Step-by-Step Execution Trace:**
1. **`i = 1`**: Outer loop begins.
   - **`j = 1`**: Condition `i==2 && j==2` is false. Prints `i=1, j=1`.
   - **`j = 2`**: Condition is false. Prints `i=1, j=2`.
   - **`j = 3`**: Condition is false. Prints `i=1, j=3`.
2. **`i = 2`**: Outer loop updates to 2.
   - **`j = 1`**: Condition `i==2 && j==1` is false. Prints `i=2, j=1`.
   - **`j = 2`**: Condition `i==2 && j==2` is **true**.
     - `continue outer` runs.
     - Execution jumps immediately to the update step of the `outer` loop (`i++`).
     - The inner loop iteration for `j=3` is completely skipped.
3. **`i = 3`**: Outer loop updates to 3.
   - **`j = 1`**: Condition is false. Prints `i=3, j=1`.
   - **`j = 2`**: Condition is false. Prints `i=3, j=2`.
   - **`j = 3`**: Condition is false. Prints `i=3, j=3`.

**Output:**
```text
i=1, j=1
i=1, j=2
i=1, j=3
i=2, j=1
i=3, j=1
i=3, j=2
i=3, j=3
```

---

### Labeled `break` Tracing Case Study

```java
outer:
for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 3; j++) {
        if (i == 2 && j == 2) {
            break outer;
        }
        System.out.println("i=" + i + ", j=" + j);
    }
}
```

**Step-by-Step Execution Trace:**
1. **`i = 1`**: Outer loop begins.
   - **`j = 1`**: Prints `i=1, j=1`.
   - **`j = 2`**: Prints `i=1, j=2`.
   - **`j = 3`**: Prints `i=1, j=3`.
2. **`i = 2`**: Outer loop updates to 2.
   - **`j = 1`**: Prints `i=2, j=1`.
   - **`j = 2`**: Condition `i==2 && j==2` is **true**.
     - `break outer` runs.
     - Execution breaks completely out of the loop labeled `outer`.
     - The program resumes at the statement immediately following the outer loop block.

**Output:**
```text
i=1, j=1
i=1, j=2
i=1, j=3
i=2, j=1
```
