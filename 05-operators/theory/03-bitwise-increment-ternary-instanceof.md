# Bitwise, Increment, Ternary, and instanceof

Some Java operators are less frequent in beginner code, but they are important for reading real programs and interview-style questions.

## Increment and Decrement

`++` increases a numeric variable by one. `--` decreases it by one.

```java
int count = 3;
count++; // count is now 4
count--; // count is now 3
```

The prefix and postfix forms differ when used inside a larger expression.

```java
int x = 5;
int a = x++; // a is 5, x becomes 6

int y = 5;
int b = ++y; // y becomes 6, b is 6
```

Postfix means "use the old value, then change the variable." Prefix means "change the variable, then use the new value."

Avoid writing dense expressions such as:

```java
int result = i++ + ++i;
```

They are legal in Java, but they force the reader to track side effects instead of understanding intent.

## Side Effects

A side effect is a change that happens while evaluating an expression. `i++` has a side effect because it changes `i`. Method calls can also have side effects if they modify state, print output, write files, or call external systems.

Side effects matter because short-circuit operators may skip them.

```java
int x = 0;
boolean result = true || x++ > 0;
System.out.println(x); // 0
```

The right side is never evaluated because `true || anything` is already true.

## Ternary Operator

The ternary operator chooses one of two expressions:

```java
String label = score >= 60 ? "pass" : "fail";
```

The structure is:

```java
condition ? valueIfTrue : valueIfFalse
```

Use it when both branches are short and produce a value. Prefer `if/else` when branches involve multiple statements or complex business rules.

## `instanceof`

The `instanceof` operator checks whether an object is compatible with a type.

```java
Object value = "Java";

if (value instanceof String) {
    System.out.println("It is a string");
}
```

Modern Java supports pattern matching for `instanceof`:

```java
if (value instanceof String text) {
    System.out.println(text.toUpperCase());
}
```

This both checks the type and declares a variable with the matched type.

`instanceof` returns false when the left-hand expression is `null`.

```java
String name = null;
System.out.println(name instanceof String); // false
```

## Bitwise Operators

Bitwise operators work on the binary representation of integer values.

| Operator | Meaning |
|---|---|
| `&` | bitwise AND |
| `|` | bitwise OR |
| `^` | bitwise XOR |
| `~` | bitwise complement |
| `<<` | left shift |
| `>>` | signed right shift |
| `>>>` | unsigned right shift |

Beginner Java code does not use bitwise operators often, but they appear in flags, permissions, low-level code, hashing, performance-sensitive code, and some library internals.

### Bitwise AND — Masking

`&` keeps a bit only when **both** operands have a 1 in that position.

```java
int a = 0b1010;  // 10 in decimal
int b = 0b1100;  // 12 in decimal
System.out.println(a & b); // 0b1000 = 8

// Common use: check whether a number is even or odd
int n = 7;
System.out.println(n & 1); // 1 → odd (least-significant bit is 1)
n = 8;
System.out.println(n & 1); // 0 → even
```

### Bitwise OR — Setting Flags

`|` sets a bit when **either** operand has a 1 in that position.

```java
int READ  = 0b001; // 1
int WRITE = 0b010; // 2
int EXEC  = 0b100; // 4

int permissions = READ | WRITE; // 0b011 = 3
System.out.println(permissions); // 3

// Check if WRITE is set:
System.out.println((permissions & WRITE) != 0); // true
```

### Bitwise XOR — Toggle

`^` produces 1 when the bits **differ**.

```java
int toggle = 0b1010;
int mask   = 0b1111;
System.out.println(toggle ^ mask); // 0b0101 = 5

// XOR with itself is always 0 — classic interview trick:
int x = 42;
System.out.println(x ^ x); // 0
```

### Shift Operators

Left shift (`<<`) multiplies by a power of 2. Right shift (`>>`) divides (signed).

```java
int n = 1;
System.out.println(n << 3);  // 8  (1 * 2^3)
System.out.println(16 >> 2); // 4  (16 / 2^2)

// Negative values: >> preserves sign bit (arithmetic shift)
System.out.println(-8 >> 1); // -4
// >>> fills with zeros regardless of sign (logical shift)
System.out.println(-8 >>> 1); // 2147483644 (very large positive)
```

For normal boolean conditions, do not use bitwise operators unless you specifically need non-short-circuit evaluation.

---

## Case Study: `i++` vs `++i` in a For Loop

A common interview question is: "Does it matter whether you write `i++` or `++i` in a for loop?"

```java
// Version A — postfix
for (int i = 0; i < 3; i++) {
    System.out.println(i);
}
// Output: 0  1  2

// Version B — prefix
for (int i = 0; i < 3; i++) {
    System.out.println(i);
}
// Output: 0  1  2   (identical!)
```

**In a for loop's update expression (`i++` or `++i`), the result of the expression is discarded.** Both forms simply increment `i` by 1 before the next iteration. The difference between prefix and postfix only matters when the **value** of the increment expression is used in a larger expression.

Where the difference IS visible:

```java
int i = 5;
System.out.println(i++); // prints 5, then i becomes 6
System.out.println(i);   // 6

int j = 5;
System.out.println(++j); // j becomes 6, then prints 6
System.out.println(j);   // 6
```

And a trickier combined example:

```java
int a = 3;
int b = a++ + ++a;
// Step 1: a++ → produces 3, side effect: a becomes 4
// Step 2: ++a → a becomes 5, produces 5
// Step 3: 3 + 5 = 8
System.out.println(b); // 8
System.out.println(a); // 5
```

**Rule of thumb**: Use `i++` or `++i` standalone. Avoid embedding them inside larger expressions — the side effects make the code hard to reason about.

---

## Common Mistakes

### Mistake 1 — Assuming `i++` and `++i` Always Differ

```java
// Both produce the same loop:
for (int i = 0; i < 5; i++) { /* ... */ }
for (int i = 0; i < 5; ++i) { /* ... */ }
// The loop body sees the same sequence: 0, 1, 2, 3, 4
```

They only differ when the expression result is used, not when the expression runs standalone.

### Mistake 2 — Misreading Postfix in Assignment

```java
int x = 10;
int y = x++;  // y = 10, x = 11  ← NOT both 11
System.out.println("x=" + x + " y=" + y); // x=11 y=10
```

### Mistake 3 — Ternary With Side Effects

```java
int count = 0;
// Which branch runs depends on the condition.
// Both increment count if you are not careful:
int result = (count > 0) ? count++ : ++count;
System.out.println(count);  // 1 either way, but result differs
System.out.println(result); // 0 if count was 0 (postfix path), would be 1 if prefix path
```

Prefer moving side effects out of the ternary expression entirely.
