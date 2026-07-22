# Precedence and Short-Circuit Evaluation

Operator precedence controls which parts of an expression are evaluated first. Short-circuit evaluation controls whether some parts are evaluated at all.

## Precedence

Precedence is the priority order among operators.

```java
int result = 2 + 3 * 4;
```

The result is `14` because `*` has higher precedence than `+`.

Parentheses override precedence:

```java
int result = (2 + 3) * 4; // 20
```

## Associativity

Associativity decides evaluation grouping when operators have the same precedence.

Most arithmetic operators group left-to-right:

```java
int x = 20 / 5 / 2; // (20 / 5) / 2 = 2
```

Assignment operators group right-to-left:

```java
int a;
int b;
a = b = 10;
```

This means `b = 10` happens first, then `a = 10`.

## Why Precedence and Associativity Dictate Expression Correctness

Operator precedence and associativity determine the parser tree that the compiler constructs to evaluate compound expressions. Without strict, deterministic rules, expressions containing multiple mixed operators would produce ambiguous and unpredictable results. Precedence dictates which operators are evaluated first (like multiplication before addition), while associativity resolves the order of evaluation for operators of identical precedence (most group left-to-right, while assignment and unary operators group right-to-left). Parentheses act as an explicit override to this default parser tree, forcing specific sub-expressions to be grouped and evaluated first. Relying purely on implicit precedence rules makes code fragile and hard to read, whereas using parentheses clarifies developer intent and prevents subtle logic bugs.

### Operator Parsing Tree Mental Model

This diagram illustrates how operator precedence builds different parsing trees, changing the order of execution for `10 - 2 * 3` versus `(10 - 2) * 3`:

```mermaid
graph TD
    subgraph Parsing Tree: 10 - 2 * 3
        Minus1[-] --> Ten1[10]
        Minus1 --> Times1[*]
        Times1 --> Two1[2]
        Times1 --> Three1[3]
    end

    subgraph Parsing Tree: (10 - 2) * 3
        Times2[*] --> Minus2[-]
        Times2 --> Three2[3]
        Minus2 --> Ten2[10]
        Minus2 --> Two2[2]
    end
```

### Cause-Effect Chain
The expression `10 - 2 * 3` is parsed by the compiler → the compiler checks the operator precedence table and finds that `*` has higher precedence than `-` → the sub-expression `2 * 3` is grouped and evaluated first to `6` → the `-` operator subtracts `6` from `10` → the expression yields `4` (whereas force grouping with `(10 - 2)` would yield `24`).

### Code Example
```java
// Case A: Precedence controls the evaluation
int noParens = 10 - 2 * 3;
System.out.println(noParens); // 4 (multiplication happens first)

// Case B: Parentheses override precedence
int withParens = (10 - 2) * 3;
System.out.println(withParens); // 24 (subtraction happens first)

// Case C: Associativity resolves tie (left-to-right)
int assoc = 12 / 3 / 2; // Evaluated as (12 / 3) / 2
System.out.println(assoc); // 2
```

## Parentheses Are For Humans Too

You do not need parentheses in every expression, but you should use them when they make the intention obvious.

```java
boolean canAccess = (age >= 18 && hasTicket) || isStaff;
```

This is easier to read than forcing the reader to remember precedence between `&&` and `||`.

## Short-Circuit With `&&`

`&&` evaluates the right-hand side only if the left-hand side is true.

```java
if (account != null && account.isActive()) {
    process(account);
}
```

If `account` is null, Java stops immediately. This is a common guard pattern.

## Short-Circuit With `||`

`||` evaluates the right-hand side only if the left-hand side is false.

```java
if (isAdmin || hasPermission("DELETE")) {
    deleteItem();
}
```

If `isAdmin` is true, Java does not call `hasPermission`.

## Short-Circuit and Side Effects

Short-circuiting changes whether side effects happen.

```java
int attempts = 0;
boolean ok = true || ++attempts > 0;
System.out.println(attempts); // 0
```

The increment is skipped. This is why side effects inside conditions can make code harder to reason about.

## Best Practices

- Use parentheses when mixed operators make the expression hard to scan.
- Avoid side effects inside complex boolean expressions.
- Use `&&` and `||` for normal conditions.
- Use `&` and `|` with booleans only when you intentionally need both sides evaluated.
- Prefer simple, named boolean variables when a condition grows too long.

```java
boolean hasValidAge = age >= 18;
boolean hasEntryRight = hasTicket || isStaff;

if (hasValidAge && hasEntryRight) {
    enter();
}
```

---

## Common Mistakes

### Mistake 1 — Precedence Surprise: `||` and `&&`

```java
// These two conditions mean different things!
boolean a = true, b = false, c = true;

boolean r1 = a || b && c;    // a || (b && c) → true || false → true
boolean r2 = (a || b) && c;  // (true || false) && true → true

// Now with b=true, c=false:
b = true; c = false;
boolean r3 = a || b && c;    // a || (b && c) → true || false → true
boolean r4 = (a || b) && c;  // (true) && false → false  ← different result!
```

`&&` has higher precedence than `||`. Always parenthesize mixed `&&`/`||` conditions to prevent bugs.

### Mistake 2 — Short-Circuit Hides a Bug

```java
int[] arr = null;
int index = 0;

// This looks safe, but...
if (arr == null | arr[index] > 0) { // | does NOT short-circuit!
    System.out.println("check");
}
// Throws NullPointerException because arr[index] is still evaluated
// Fix: use && and || not & and |
```

### Mistake 3 — Side Effect Assumed to Always Run

```java
int counter = 0;

boolean ok = isReady() || (++counter > 0); // if isReady() is true, counter stays 0!
System.out.println(counter); // might be 0 or 1 depending on isReady()
```

Code that assumes `++counter` always runs will behave incorrectly when `isReady()` returns `true`. Separate the increment from the condition.

### Mistake 4 — Confusing `=` and `==` in Conditions

```java
boolean enabled = false;
if (enabled = true) {      // compiles! This is ASSIGNMENT, not comparison.
    System.out.println("always runs"); // always prints because assignment produces the value true
}
// Fix:
if (enabled == true) { }   // comparison
if (enabled) { }           // idiomatic Java
```

Java allows assignment inside `if` (because assignment is an expression), which makes this a silent logic bug.
