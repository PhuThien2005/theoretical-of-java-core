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

Example:

```java
int read = 1;   // 001
int write = 2;  // 010
int both = read | write; // 011
```

For normal boolean conditions, do not use bitwise operators unless you specifically need non-short-circuit evaluation.
