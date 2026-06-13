# Primitive Types

Primitive types are the simplest built-in data types in Java. They store simple values directly and are not objects.

Java has 8 primitive types:

| Type | Category | Typical Use |
| --- | --- | --- |
| `byte` | integer | very small integer values |
| `short` | integer | small integer values |
| `int` | integer | default integer choice |
| `long` | integer | large integer values |
| `float` | floating-point | decimal values with less precision |
| `double` | floating-point | default decimal choice |
| `char` | character | a single UTF-16 code unit |
| `boolean` | logical | `true` or `false` |

## Integer Types

Integer types store whole numbers.

```java
byte small = 10;
short count = 300;
int age = 18;
long population = 8_000_000_000L;
```

`int` is the default type for integer literals.

`long` literals usually use `L`:

```java
long value = 10000000000L;
```

Use uppercase `L`, not lowercase `l`, because lowercase `l` is easy to confuse with `1`.

## Floating-Point Types

Floating-point types store decimal numbers.

```java
float price = 9.99F;
double score = 8.75;
```

`double` is the default type for decimal literals.

`float` literals need `F`:

```java
float ratio = 0.5F;
```

Floating-point numbers are not ideal for money because they can have rounding errors.

Example:

```java
System.out.println(0.1 + 0.2);
```

The result may not be exactly `0.3`.

For money, Java developers often use `BigDecimal`.

## char

`char` stores a single UTF-16 code unit.

```java
char grade = 'A';
```

Single quotes are used for `char`. Double quotes are used for `String`.

```java
char c = 'A';
String s = "A";
```

They are not the same type.

## boolean

`boolean` stores only:

```java
true
false
```

Example:

```java
boolean isActive = true;
```

Java does not treat `0` as `false` or `1` as `true`.

Invalid:

```java
boolean valid = 1;
```

## Default Values

Local variables do not get automatic default values. They must be assigned before use.

Fields have default values.

Common defaults:

| Type | Default Field Value |
| --- | --- |
| numeric primitives | `0` or `0.0` |
| `char` | `'\u0000'` |
| `boolean` | `false` |
| reference types | `null` |

## Common Mistakes

- Using `float` without `F`.
- Using `long` without `L` for large literals.
- Using double quotes for `char`.
- Assuming Java treats `1` as `true`.
- Using floating-point types for exact money calculations.

---

## Integer Overflow

Java integer arithmetic wraps around silently when it exceeds the type's range — no exception is thrown.

`int` range: −2,147,483,648 to 2,147,483,647.

```java
int max = Integer.MAX_VALUE;        // 2147483647
System.out.println(max + 1);       // -2147483648  (wraps to MIN_VALUE!)
System.out.println(max + 1 > max); // false
```

### Why It Happens

Java uses two's complement arithmetic. Adding 1 to the maximum value flips all bits and gives the minimum value.

### How to Detect / Prevent Overflow

```java
// Option 1: use long for intermediate calculation
long safe = (long) max + 1;        // 2147483648L — correct

// Option 2: use Math.addExact (throws ArithmeticException on overflow)
try {
    int result = Math.addExact(max, 1);
} catch (ArithmeticException e) {
    System.out.println("Overflow detected: " + e.getMessage());
}
```

### Common Mistake

Overflow is silent in Java — it never throws an exception by default. This can cause subtle bugs in loop counters, checksums, or capacity calculations.

---

## Case Study: Why Using Float for Money Is Dangerous

Floating-point types (`float`, `double`) store numbers in binary format. Decimal fractions like `0.1` and `0.2` cannot be represented exactly in binary, causing rounding errors.

```java
// What a developer might write
double price = 0.10;
double tax   = 0.20;
double total = price + tax;

System.out.println(total);          // 0.30000000000000004  ← NOT 0.30!
System.out.println(total == 0.30);  // false
```

### Real-World Impact

If a shopping cart has 1,000,000 transactions each rounding by ~0.000000000000001, the accumulated error can produce cents or even dollars of discrepancy — a serious financial bug.

### Correct Approach: BigDecimal

```java
import java.math.BigDecimal;

BigDecimal price = new BigDecimal("0.10");
BigDecimal tax   = new BigDecimal("0.20");
BigDecimal total = price.add(tax);

System.out.println(total);           // 0.30  ← exact
System.out.println(total.compareTo(new BigDecimal("0.30")) == 0); // true
```

**Key rule:** Always use `String` constructor for `BigDecimal` — `new BigDecimal(0.1)` still captures the double's inaccuracy!

```java
// Wrong — captures double inaccuracy
BigDecimal bad = new BigDecimal(0.1);
System.out.println(bad); // 0.1000000000000000055511151231257827021181583404541015625

// Correct — exact decimal
BigDecimal good = new BigDecimal("0.1");
System.out.println(good); // 0.1
```

---

## char As a Number

`char` stores a UTF-16 code unit, which is an unsigned 16-bit integer (0–65535). This means `char` participates in arithmetic as a number.

```java
char c = 'A';           // Unicode code point 65
System.out.println(c);  // A

// char promotes to int in arithmetic
System.out.println(c + 1);          // 66  (int, not char!)
System.out.println((char)(c + 1));  // B

// Iterate through letters
for (char letter = 'A'; letter <= 'Z'; letter++) {
    System.out.print(letter + " ");
}
// Output: A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
```

### char to int and Back

```java
char ch = 'Z';
int code = ch;          // widening: char → int, value is 90
System.out.println(code); // 90

char back = (char) code;  // narrowing: int → char
System.out.println(back); // Z
```

### Common Mistake

```java
char a = 'A';
char b = 'B';
// char sum = a + b; // compile error: a + b is int, not char
int sum = a + b;        // 65 + 66 = 131
System.out.println(sum); // 131
```

---

## Boolean Default Value (Fields vs Local Variables)

`boolean` fields in a class are initialized to `false` by default. Local `boolean` variables must be explicitly assigned before use.

```java
public class Demo {
    boolean active;         // field — default is false

    void example() {
        // boolean flag;
        // System.out.println(flag); // compile error: variable flag might not have been initialized

        boolean ready = false;
        System.out.println(active); // false — default field value
        System.out.println(ready);  // false — explicitly set
    }
}
```

---

## Widening vs Narrowing: Extra Edge Cases

### Widening Is Automatic But Not Always Lossless

```java
int bigInt = 123456789;
float f = bigInt;            // widening int → float (automatic)
System.out.println(f);       // 1.23456792E8  ← precision lost!
System.out.println((int) f); // 123456792     ← not the original value!
```

`float` has only ~7 significant decimal digits. Widening from `int` to `float` can still lose precision for large integers.

### Narrowing Truncates, Not Rounds

```java
double d = -9.9;
int i = (int) d;
System.out.println(i); // -9, NOT -10  (truncates toward zero)
```

### Widening Chain

```java
byte  b  = 10;
short s  = b;      // widening byte → short
int   i2 = s;      // widening short → int
long  l  = i2;     // widening int → long
float f2 = l;      // widening long → float
double d2 = f2;    // widening float → double
System.out.println(d2); // 10.0
```
