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

## Why var Type Inference Only Works Locally

In Java, type inference using `var` is strictly limited to local variables. This design decision is due to the fundamental role that class fields and method signatures play in establishing class contracts and API boundaries. Fields and methods are visible outside of their declaring class, and their types must be explicitly defined in compile-time metadata (`.class` files) so that other classes can be compiled independently. Allowing fields or method signatures to use `var` would mean the compiler has to parse the internal initialization blocks of one class file to resolve types needed by another class file, breaking separate compilation. Local variables, on the other hand, are internal implementation details confined within a single method block, making inference entirely safe and local.

### API Contract vs. Internal Implementation

```text
Public Boundary (API Contract) ──> Must be Explicitly Typed
[Class Demo] 
  ├── Field: public int count; ──> Explicit Type (Required)
  └── Method: public String process() ──> Explicit Return Type (Required)
  
Internal implementation (Hidden) ──> Local Inference Allowed
  └── Method Body:
        └── var list = new ArrayList<String>(); ──> Inferred Local Type
```

### Type Inference Boundary Code Demo

```java
public class ContractDemo {
    // Compile Error: 'var' is not allowed on fields
    // public var status = "ACTIVE"; 
    
    // Compile Error: 'var' is not allowed in method parameter or return types
    // public var doWork(var input) { return "Done"; }

    public String getStatus() {
        // Allowed: local variable is confined to getStatus() execution frame
        var currentStatus = "ACTIVE"; 
        return currentStatus;
    }
}
```

### API Contract Cause-Effect Chain

`var` allowed on fields/methods $\rightarrow$ Compiler must analyze method bodies to determine public API types $\rightarrow$ Separate compilation of classes becomes interdependent $\rightarrow$ Modifying internal code breaks external classes unexpectedly $\rightarrow$ `var` restricted to local scopes $\rightarrow$ Class interfaces remain static and explicit, maintaining compile-time speed and stability.

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

## `var` in For-Each Loops

`var` also works in enhanced for-each loops (Java 10+):

```java
var names = List.of("Alice", "Bob", "Carol");
for (var name : names) {
    System.out.println(name); // name is inferred as String
}
```

## Case Study: When `var` Helps vs. When It Hurts

**✅ `var` improves readability** — type is obvious from right-hand side:

```java
// Without var — verbose and redundant
HashMap<String, List<Integer>> scores = new HashMap<String, List<Integer>>();

// With var — type is still clear, less noise
var scores = new HashMap<String, List<Integer>>();
```

**❌ `var` harms readability** — return type hidden behind method name:

```java
// What is result? String? Integer? List? Nobody knows without checking the method.
var result = parser.parse(rawInput);

// Explicit type communicates intent immediately
ParsedResult result = parser.parse(rawInput);
```

**Rule of thumb**: use `var` when the right-hand side expression *shows* the type (constructor calls, literals, `new`, casts). Avoid `var` when the type comes from a method call whose name doesn't reveal the return type.

## `var` Cannot Be Used For

```java
class Config {
    var timeout = 30;         // compile error: 'var' not allowed here (field)

    var compute() {           // compile error: 'var' not allowed here (return type)
        return 42;
    }
}

void broken() {
    var x;                    // compile error: cannot infer type (no initializer)
    var y = null;             // compile error: cannot infer type from null alone
}
```

## Common Mistakes

- Thinking `var` means dynamic typing — the type is fixed at compile time.
- Trying to use `var` for fields — only allowed for local variables.
- Using `var` without an initializer — the compiler needs the initializer to infer the type.
- Using `var` when it hides important type information, especially with method return values.
- Initializing `var` with `null` — the compiler cannot infer a type from `null` alone.

## Reference Links

- [Java Language Specification: Local Variable Type Inference](https://docs.oracle.com/javase/specs/jls/se21/html/jls-14.html#jls-14.4)
- [OpenJDK FAQ: Local Variable Type Inference](https://openjdk.org/projects/amber/LVTIstyle.html)

