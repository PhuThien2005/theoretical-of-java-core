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
