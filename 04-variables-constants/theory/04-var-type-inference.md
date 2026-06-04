# `var` And Type Inference

`var` was introduced in Java 10 for local variable type inference.

It lets the compiler infer the variable type from the initializer.

```java
var name = "Alice"; // String
var age = 18;       // int
```

`var` does not make Java dynamically typed. The type is still fixed at compile time.

```java
var age = 18;
// age = "eighteen"; // does not compile
```

## Where `var` Can Be Used

`var` can be used for local variables.

```java
public void demo() {
    var message = "Hello";
}
```

It cannot be used for fields.

```java
class Demo {
    // var name = "Alice"; // invalid
}
```

It cannot be used without an initializer.

```java
// var x; // invalid
```

## When `var` Helps

`var` can reduce noise when the type is obvious.

```java
var names = new ArrayList<String>();
```

## When `var` Hurts

`var` can make code harder to read when the type is not obvious.

Weak:

```java
var result = service.process(input);
```

If `process` does not make the return type clear, explicit typing may be better.

## Common Mistakes

- Thinking `var` means dynamic typing.
- Trying to use `var` for fields.
- Using `var` without an initializer.
- Using `var` when it hides important type information.
