# Arithmetic and Assignment Operators

Arithmetic operators perform numeric calculations. Assignment operators store values in variables. They often appear together because programs usually calculate something and then save the result.

## Arithmetic Operators

Java has five common arithmetic operators:

| Operator | Meaning | Example | Result |
|---|---|---:|---:|
| `+` | addition | `7 + 3` | `10` |
| `-` | subtraction | `7 - 3` | `4` |
| `*` | multiplication | `7 * 3` | `21` |
| `/` | division | `7 / 3` | `2` when both operands are integers |
| `%` | remainder | `7 % 3` | `1` |

An arithmetic expression is evaluated according to operator precedence, then left-to-right for operators with the same precedence. Multiplication, division, and remainder have higher precedence than addition and subtraction.

```java
int result = 2 + 3 * 4; // 14, not 20
```

Java calculates `3 * 4` first, then adds `2`.

## Integer Division

When both operands of `/` are integer types, Java performs integer division. The fractional part is discarded.

```java
int a = 7 / 3;      // 2
int b = 10 / 4;     // 2
double c = 10 / 4;  // 2.0, because the division already happened as integer division
double d = 10 / 4.0; // 2.5
```

The common trap is assigning the result to `double` and expecting a fractional result. The result type of the expression is decided before assignment.

## Remainder With `%`

The `%` operator returns the remainder after division.

```java
int r = 17 % 5; // 2
```

It is often used for:

- Checking divisibility: `n % 2 == 0`
- Wrapping indexes: `index % size`
- Separating digits in beginner exercises

For negative operands, the sign of the remainder follows the dividend, the left operand.

```java
System.out.println(-7 % 3); // -1
```

## String Concatenation With `+`

The `+` operator also concatenates strings. If either operand is a `String`, Java converts the other operand to text and joins them.

```java
System.out.println("Java " + 21);       // Java 21
System.out.println("Result: " + 2 + 3); // Result: 23
System.out.println("Result: " + (2 + 3)); // Result: 5
```

Once string concatenation begins from left to right, later `+` operations in that chain continue as string concatenation unless parentheses force arithmetic first.

## Assignment

The simple assignment operator is `=`.

```java
int score = 10;
score = 15;
```

Assignment stores the right-hand value into the variable on the left. It is not equality. In Java, equality comparison uses `==`.

## Compound Assignment

Compound assignment operators combine an operation and assignment:

```java
int count = 5;
count += 2; // same broad idea as count = count + 2
count *= 3; // same broad idea as count = count * 3
```

Common compound assignment operators include `+=`, `-=`, `*=`, `/=`, and `%=`.

There is one subtle difference: compound assignment includes an implicit cast back to the left-hand variable type.

```java
byte b = 1;
b += 1;      // allowed
// b = b + 1; // not allowed without a cast, because b + 1 is promoted to int
```

This matters because arithmetic on smaller integer types like `byte`, `short`, and `char` is promoted to `int`.

## Why Compound Assignment Performs Implicit Casting

In Java, any arithmetic operations on small integer types (`byte`, `short`, and `char`) automatically promote the operands to `int` before the operation runs. Consequently, writing `b = b + 1` on a `byte b` fails to compile because it attempts to assign an `int` result back to a `byte` variable. To prevent code from being littered with repetitive, explicit casts, the Java language specifications define compound assignment operators (like `+=`, `*=`) to include an implicit cast to the type of the left-hand variable. While this provides cleaner syntax, it also masks potential arithmetic overflow or loss of precision because the compiler will not warn you when the value exceeds the variable's type limits.

### Truncation Mental Model

When a value is implicitly cast back to a smaller type, Java performs a narrowing primitive conversion by discarding all the high-order bits that do not fit in the target type's size.

```text
Value 130 in decimal (int, 32-bit):
[00000000] [00000000] [00000000] [10000010]
                                      │
                         [Narrowing cast to byte (8-bit)]
                                      ▼
                             [10000010]  --> -126 in two's complement (MSB is 1)
```

### Cause-Effect Chain
`b += 10` is evaluated → Java promotes `b` and `10` to `int` and adds them → intermediate sum is `130` (32-bit `int`) → implicit cast `(byte)` is applied → high-order 24 bits are discarded → remaining `8` bits `10000010` have a sign bit (Most Significant Bit) of `1`, making the final stored value `-126` (overflow).

### Code Example
```java
byte b = 120;
b += 10; // Silently compiled as: b = (byte) (b + 10)
System.out.println(b); // -126
```

## Assignment Is An Expression

In Java, assignment has a value: the value assigned.

```java
int x;
int y;
x = y = 10;
```

This works because `y = 10` evaluates to `10`, then `x = 10` happens. Even though this is legal, long chains of assignment can reduce readability.

## Practical Rule

When arithmetic has learning traps, ask four questions:

1. What are the operand types?
2. Does Java promote the operands?
3. What result type does the expression produce?
4. Is the result assigned to a variable that changes the value further?

---

## Common Mistakes

### Mistake 1 — Integer Division Truncation

```java
// Beginner assumption: x will be 2.5
double x = 10 / 4;
System.out.println(x); // 2.0  ← truncated BEFORE assignment

// Fix: force floating-point division
double y = 10 / 4.0;   // 2.5
double z = (double) 10 / 4; // 2.5
```

The key rule: **the result type of an expression is decided by the operands, not by the receiving variable.**

### Mistake 2 — Remainder `%` With Negative Operands

```java
System.out.println( 7 % 3);  //  1
System.out.println(-7 % 3);  // -1  ← sign follows the DIVIDEND (left side)
System.out.println( 7 % -3); //  1  ← sign still follows the DIVIDEND
System.out.println(-7 % -3); // -1
```

Java's rule: the sign of the result equals the sign of the **left operand**. This differs from mathematical modulo where the result is always non-negative.

### Mistake 3 — String Concatenation Chain

```java
// All three lines look similar — outputs are very different
System.out.println(1 + 2 + " items");   // "3 items"  (ints add first, left-to-right)
System.out.println("items: " + 1 + 2);  // "items: 12" (String comes first, then concat)
System.out.println("items: " + (1 + 2)); // "items: 3" (parens force int addition first)
```

Once a `String` appears as the **left** operand of `+`, every subsequent `+` in the same chain treats its right operand as text too.

### Mistake 4 — Compound Assignment Hidden Cast

```java
byte b = 100;
b *= 2;         // compiles — implicit cast back to byte
                // result wraps around: 200 as byte is -56 (overflow!)
System.out.println(b); // -56

// Compare:
// b = (byte)(b * 2); // explicit; same result but intention is clear
```

The implicit cast silently allows overflow. For `byte`/`short` arithmetic, verify the result stays within range.
