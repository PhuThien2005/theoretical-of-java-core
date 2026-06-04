# Operator Terms

This file expands terms that are often used too quickly in operator explanations.

## Operand

An operand is a value that an operator acts on.

```java
int result = a + b;
```

In `a + b`, `a` and `b` are operands, and `+` is the operator.

## Expression

An expression is code that produces a value.

```java
2 + 3
age >= 18
name.toUpperCase()
```

Expressions can be small or large. Operators usually combine smaller expressions into larger expressions.

## Precedence

Precedence is the priority order of operators. It answers: "Which operator groups first?"

In `2 + 3 * 4`, multiplication groups first, so the result is `14`.

## Associativity

Associativity decides grouping when operators have the same precedence.

In `20 / 5 / 2`, division groups left-to-right, so the result is `(20 / 5) / 2`, which is `2`.

## Short-Circuit

Short-circuit means Java stops evaluating a boolean expression as soon as the final result is already known.

For `&&`, false on the left is enough to decide the whole expression is false. For `||`, true on the left is enough to decide the whole expression is true.

Short-circuiting is useful for safety checks and performance, but it also means skipped code does not run.

## Side Effect

A side effect is a change that happens while evaluating code. Examples include changing a variable, printing output, mutating an object, writing to a database, or calling a method that changes state.

`x++` has a side effect because it changes `x`.

## Integer Division

Integer division is division where both operands are integer types. Java discards the fractional part.

`7 / 3` is `2`, not `2.333`.

## Remainder

The remainder is what is left after division. In Java, `%` calculates the remainder.

`17 % 5` is `2` because `17 = 5 * 3 + 2`.

## Content Equality

Content equality asks whether two objects represent the same meaningful value. For many objects, content equality is checked with `.equals()`.

For `String`, `"Java".equals(input)` checks text content.

## Reference Equality

Reference equality asks whether two references point to the exact same object. In Java, `==` checks reference equality for objects.

Two different `String` objects may contain the same text but still fail `==`.

## Type Compatibility

Type compatibility means a value can legally be treated as a certain type. `instanceof` checks runtime type compatibility for object references.

If `value instanceof String text` is true, Java knows `text` is a `String` inside that block.
