# Lambda Expression - Part 1

## Learning Goal

This file covers a focused slice of **Lambda Expression**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `What is a lambda?` | A lambda expression is a compact function-like block used where a functional interface is expected. |
| `Lambda syntax` | A lambda expression is a compact function-like block used where a functional interface is expected. |
| `Functional interface` | A functional interface has exactly one abstract method and can be implemented by a lambda. |
| `@FunctionalInterface` |@FunctionalInterface is a specific concept in Lambda Expression; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Method reference:` | Method reference is a group of related rules in Lambda Expression that groups several related details. |
| `static method reference` | Static means the member belongs to the class rather than to one particular object. |
| `instance method reference` |instance method reference is a specific concept in Lambda Expression; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `constructor reference` |constructor reference is a specific concept in Lambda Expression; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Variable capture` |Variable capture is a specific concept in Lambda Expression; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Effectively final` | Final means the variable, method, class, or parameter is restricted from later change in a specific way. |

## Detailed Notes

### What is a lambda?

A lambda expression is a compact function-like block used where a functional interface is expected.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `What is a lambda?` in one sentence.
- Recognize `What is a lambda?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `What is a lambda?`.

Tiny example or mental model:

- `n -> n > 0` is a lambda used as a predicate.

#### Functional Interface Target Typing
A lambda has no explicit type of its own. It is bound to a type at compile time by looking at the context (called **target typing**). The target type must be a functional interface.
```java
// Target type is Runnable
Runnable runTask = () -> System.out.println("Executing...");

// Target type is Predicate<Integer>
java.util.function.Predicate<Integer> isPositive = n -> n > 0;
```

### Lambda syntax

A lambda expression is a compact function-like block used where a functional interface is expected.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `Lambda syntax` in one sentence.
- Recognize `Lambda syntax` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Lambda syntax`.

Tiny example or mental model:

- `n -> n > 0` is a lambda used as a predicate.

#### Code Example: Syntax Variations
```java
// 1. Zero parameters
Runnable r = () -> System.out.println("Zero params");

// 2. Single parameter (parentheses and types are optional)
java.util.function.Consumer<String> c1 = s -> System.out.println(s);
java.util.function.Consumer<String> c2 = (s) -> System.out.println(s);
java.util.function.Consumer<String> c3 = (String s) -> System.out.println(s);

// 3. Multiple parameters (parentheses required)
java.util.function.BinaryOperator<Integer> add = (a, b) -> a + b;

// 4. Block body (curly braces, statements, semicolons, and return keyword required)
java.util.function.BinaryOperator<Integer> calc = (a, b) -> {
    int sum = a + b;
    return sum;
};
```

#### Common Mistake: Incorrect Braces and return Keywords
- A lambda body with a single expression can implicitly return a value without curly braces or the `return` keyword.
- If curly braces `{}` are used, you must write a block of statements, which requires a `return` keyword if the method returns a value.
```java
// Compile error: return keyword cannot be used without curly braces
java.util.function.BinaryOperator<Integer> bad1 = (a, b) -> return a + b;

// Compile error: curly braces are used but return keyword is missing
java.util.function.BinaryOperator<Integer> bad2 = (a, b) -> { a + b; };
```

### Functional interface

A functional interface has exactly one abstract method and can be implemented by a lambda.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `Functional interface` in one sentence.
- Recognize `Functional interface` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Functional interface`.

Tiny example or mental model:

- When reading code, ask: what does `Functional interface` change, allow, reject, or clarify?

#### Code Example: Defining custom Functional Interfaces
```java
interface SimpleCalculator {
    int calculate(int x, int y); // exactly one abstract method
}

// Inherited Object methods do not count towards the abstract method limit
interface ObjectOverride {
    void process();
    boolean equals(Object obj); // abstract override of Object method, does not count
}
```

### @FunctionalInterface

@FunctionalInterface is a specific concept in Lambda Expression; learn its Java rule, valid use cases, and failure mode rather than only its name.

It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Practical check:

- Define `@FunctionalInterface` in one sentence.
- Recognize `@FunctionalInterface` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `@FunctionalInterface`.

Tiny example or mental model:

- When reading code, ask: what does `@FunctionalInterface` change, allow, reject, or clarify?

#### Code Example: Compile-time check
```java
@FunctionalInterface
interface Valid {
    void execute();
}

// Compile Error: InvalidFunctionalInterfaceException (multiple non-overriding abstract methods)
@FunctionalInterface
interface Invalid {
    void execute();
    void clean();
}
```

### Method reference:

Method reference is a group of related rules in Lambda Expression that groups several related details.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Method reference:` in one sentence.
- Recognize `Method reference:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Method reference:`.

Tiny example or mental model:

- When reading code, ask: what does `Method reference:` change, allow, reject, or clarify?

#### Classification of Method References
There are 4 main kinds of method references:
1. Static method reference: `ContainingClass::staticMethodName`
2. Bound instance method reference: `containingObject::instanceMethodName`
3. Unbound instance method reference: `ContainingClass::instanceMethodName`
4. Constructor reference: `ClassName::new`

### static method reference

Static means the member belongs to the class rather than to one particular object.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `static method reference` in one sentence.
- Recognize `static method reference` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `static method reference`.

Tiny example or mental model:

- `ClassName.member` accesses a class-level member.

#### Code Example: Static method references
```java
// Lambda form:
java.util.function.Function<String, Integer> parserLambda = s -> Integer.parseInt(s);

// Method reference equivalent:
java.util.function.Function<String, Integer> parserRef = Integer::parseInt;
```

### instance method reference

instance method reference is a specific concept in Lambda Expression; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `instance method reference` in one sentence.
- Recognize `instance method reference` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `instance method reference`.

Tiny example or mental model:

- When reading code, ask: what does `instance method reference` change, allow, reject, or clarify?

#### Bound vs Unbound Instance Method References
- **Bound Method Reference**: Refers to an instance method of an existing object. The receiver of the method call is fixed at compile time.
- **Unbound Method Reference**: Refers to an instance method of an arbitrary object of a particular type. The first parameter of the lambda is used as the receiver of the call.
```java
// 1. Bound instance method reference
String prefix = "DEBUG: ";
java.util.function.Consumer<String> boundPrinter = prefix::concat; 
// Equivalent lambda: msg -> prefix.concat(msg);

// 2. Unbound instance method reference
java.util.function.BiFunction<String, String, String> unboundConcat = String::concat;
// Equivalent lambda: (str, suffix) -> str.concat(suffix);
```

### constructor reference

constructor reference is a specific concept in Lambda Expression; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `constructor reference` in one sentence.
- Recognize `constructor reference` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `constructor reference`.

Tiny example or mental model:

- When reading code, ask: what does `constructor reference` change, allow, reject, or clarify?

#### Code Example: Constructor References
```java
// Matches no-arg constructor ArrayList()
java.util.function.Supplier<java.util.List<String>> listSupplier = java.util.ArrayList::new;

// Matches constructor ArrayList(int initialCapacity)
java.util.function.Function<Integer, java.util.List<String>> sizeSupplier = java.util.ArrayList::new;
```

### Variable capture

Variable capture is a specific concept in Lambda Expression; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Variable capture` in one sentence.
- Recognize `Variable capture` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Variable capture`.

Tiny example or mental model:

- When reading code, ask: what does `Variable capture` change, allow, reject, or clarify?

#### Code Example: Capturing Variables from Enclosing Scope
Lambdas can read static variables, instance variables, and local variables. However, local variables must be final or effectively final.
```java
class VariableCaptureDemo {
    private int instanceVar = 10;
    private static int staticVar = 20;

    public void demo() {
        int localVar = 30; // effectively final local variable

        Runnable r = () -> {
            instanceVar++; // OK: instance variables are not restricted
            staticVar++;   // OK: static variables are not restricted
            System.out.println(localVar); // OK: reading effectively final local variable
        };
        r.run();
    }
}
```

### Effectively final

Final means the variable, method, class, or parameter is restricted from later change in a specific way.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Effectively final` in one sentence.
- Recognize `Effectively final` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Effectively final`.

Tiny example or mental model:

- `final int limit = 10;` cannot be reassigned.

#### Code Example: Effectively Final Compilation Errors
If a local variable is re-assigned anywhere in the method (either inside the lambda or outside it), it ceases to be effectively final. Attempting to capture it in a lambda will cause a compile-time error.
```java
public void testEffectivelyFinal() {
    int val = 42; 
    
    // Compile Error: local variables referenced from a lambda expression must be final or effectively final
    Runnable r1 = () -> System.out.println(val); 
    
    val = 100; // reassigning makes it NOT effectively final
}

public void testReassignmentInLambda() {
    int count = 0;
    
    // Compile Error: local variables referenced from a lambda expression must be final or effectively final
    Runnable r2 = () -> {
        count = count + 1; // attempting to modify the variable inside lambda
    };
}
```

#### Why is this constraint necessary?
Local variables live on the stack and are destroyed when the enclosing method exits. However, lambdas can be stored and executed much later (e.g., in another thread). To support this, Java copies the value of the local variable into the lambda object. If the variable could be modified, the local variable and the copy inside the lambda would drift out of sync, violating Java's memory guarantees.

## Case Study: Refactoring Anonymous Class Callbacks to Lambdas and Method References

In legacy Java code, asynchronous or callback-driven interfaces were implemented using verbose anonymous inner classes. Refactoring these to lambda expressions and method references improves readability, reduces boilerplate, and avoids the overhead of creating a separate class file.

### Legacy Implementation (Anonymous Inner Class)
```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RefactoringDemo {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("Charles");
        names.add("Alice");
        names.add("Bob");

        // Verbose sorting callback using anonymous class
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });
    }
}
```

### Refactored to Lambda Expression
Since `Comparator` is a functional interface with a single abstract method `compare`, we can replace the anonymous class with a lambda expression:
```java
// Refactoring 1: Standard Lambda
Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
```

### Refactored to Method Reference
Because the lambda simply takes the arguments and forwards them to `s1.compareTo(s2)`, we can use an unbound instance method reference `String::compareTo`:
```java
// Refactoring 2: Unbound Instance Method Reference
Collections.sort(names, String::compareTo);

// Modern list sort syntax:
names.sort(String::compareTo);
```

### Key Differences and Gotchas
1. **`this` reference scope**: Inside an anonymous class, `this` refers to the anonymous class instance itself. Inside a lambda, `this` refers to the enclosing class instance where the lambda is defined.
2. **Class Files**: Anonymous classes generate an extra `.class` file at compile time (e.g., `RefactoringDemo$1.class`). Lambdas are compiled into private methods in the host class using `invokedynamic` instructions, improving memory footprint and startup time.


## Common Mistakes

### 1. Reassigning variables inside or outside a lambda (Effectively Final rule)
A very common mistake is attempting to update a counter or boolean flag inside a lambda.
```java
public void badCounter() {
    int count = 0;
    // Compile error: local variables referenced from a lambda expression must be final or effectively final
    java.util.List.of("a", "b").forEach(item -> count++); 
}
```
**Fix**: Use an object wrapper, a single-element array, or thread-safe atomic variables (like `AtomicInteger`) if synchronization is needed.
```java
public void goodCounter() {
    java.util.concurrent.atomic.AtomicInteger count = new java.util.concurrent.atomic.AtomicInteger(0);
    java.util.List.of("a", "b").forEach(item -> count.incrementAndGet()); // OK
}
```

### 2. Variable Shadowing Conflicts
Lambdas do not introduce a new scope level. They share the scope of the enclosing block. Therefore, you cannot declare a lambda parameter with the same name as a local variable in the enclosing method.
```java
public void shadowingDemo() {
    String message = "Hello";
    
    // Compile error: Variable 'message' is already defined in the scope
    java.util.function.Consumer<String> printer = message -> System.out.println(message);
}
```

### 3. Confusing Bound and Unbound Method Reference Parameter Counts
When using `Class::methodName` for an instance method (unbound method reference), the functional interface's method signature must accept the receiver object as its first parameter.
```java
// BiFunction<String, String, Boolean> requires two parameters
// String::startsWith requires one parameter. 
// Since it's unbound, parameter 1 becomes the receiver, parameter 2 is the argument. 
// String::startsWith is equivalent to: (str, prefix) -> str.startsWith(prefix)
java.util.function.BiFunction<String, String, Boolean> checker = String::startsWith; // OK

// Function<String, Boolean> requires only one parameter.
// If unbound (String::startsWith), it resolves to: (str) -> str.startsWith()
// But String has no startsWith() method without arguments, so this fails.
java.util.function.Function<String, Boolean> badChecker = String::startsWith; // Compile Error!
```

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
