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

## Signed Right Shift (>>)

The signed right shift operator (`>>`) shifts the binary representation of a number to the right, filling the vacated leftmost bits with the original sign bit (0 for positive, 1 for negative). This arithmetic shift preserves the mathematical sign of the value.

## Unsigned Right Shift (>>>)

The unsigned right shift operator (`>>>`) shifts the binary representation of a number to the right, always filling the vacated leftmost bits with zeros regardless of the original sign. This logical shift converts negative numbers into positive integers.

## Operand Stack

The operand stack is a JVM runtime data structure used during method execution to push and pop operands, performing operations dynamically.

## Local Variable Slot

A local variable slot is a memory location allocated within a JVM stack frame to hold the value of a local variable or parameter during method execution.

## Pattern Variable

A pattern variable is a local variable declared inside a pattern check (such as `instanceof String text`). The variable is automatically typed, cast, and bound if the type check succeeds, with its scope restricted to where the type check is guaranteed to be true.
