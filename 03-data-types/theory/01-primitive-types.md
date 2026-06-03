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
