# Optional - Part 2

## Learning Goal

This file covers a focused slice of **Optional**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `map` | A Map stores key-value pairs and retrieves values by key. |
| `flatMap` | A Map stores key-value pairs and retrieves values by key. |
| `filter` |filter is a specific concept in Optional; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Do not overuse Optional` | Optional is a container that may or may not hold a non-null value. |
| `Optional in return type` | Optional is a container that may or may not hold a non-null value. |

## Detailed Notes

### map

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `map` in one sentence.
- Recognize `map` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `map`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

#### Detailed Explanation
`map(Function<? super T, ? extends U> mapper)` is used to transform the value inside the `Optional`. If a value is present, it applies the mapping function to the value. If the mapping function returns a non-null value, it returns an `Optional` containing that result. If the `Optional` is empty or if the mapper returns `null`, it returns an empty `Optional`.
Crucially, the mapping function returns a raw type `U`, and `map` automatically wraps it into `Optional<U>`.

#### Runnable Code Example
```java
import java.util.Optional;

public class OptionalMapExample {
    public static void main(String[] args) {
        Optional<String> opt = Optional.of("Hello");

        // Transform string to its length
        Optional<Integer> length = opt.map(String::length);
        System.out.println(length.orElse(0)); // Prints: 5

        // If mapper returns null, map() returns empty Optional
        Optional<String> nullResult = opt.map(val -> (String) null);
        System.out.println(nullResult.isPresent()); // Prints: false
    }
}
```

#### Common Mistake
Using `map` when the mapping function itself returns an `Optional`. This results in a nested `Optional<Optional<U>>`. In such cases, use `flatMap` instead.

### flatMap

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `flatMap` in one sentence.
- Recognize `flatMap` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `flatMap`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

#### Detailed Explanation
`flatMap(Function<? super T, ? extends Optional<? extends U>> mapper)` is similar to `map`, but is used when the mapping function returns an `Optional`. Instead of wrapping the returned `Optional` into another `Optional`, `flatMap` flattens the result by returning the mapper's `Optional` directly.
**Gotcha**: If the mapping function returns `null`, `flatMap` throws `NullPointerException` (unlike `map`, which would return an empty `Optional`).

#### Runnable Code Example
```java
import java.util.Optional;

class User {
    private final String name;
    private final Optional<String> email;

    public User(String name, String email) {
        this.name = name;
        this.email = Optional.ofNullable(email);
    }

    public Optional<String> getEmail() {
        return email;
    }
}

public class OptionalFlatMapExample {
    public static void main(String[] args) {
        Optional<User> userOpt = Optional.of(new User("Alice", "alice@example.com"));

        // Using map() would return Optional<Optional<String>>
        Optional<Optional<String>> nested = userOpt.map(User::getEmail);

        // Using flatMap() returns Optional<String> directly
        Optional<String> flattened = userOpt.flatMap(User::getEmail);
        System.out.println(flattened.orElse("No Email")); // alice@example.com
    }
}
```

#### Common Mistake
Confusing `map` and `flatMap` when the mapping function returns `Optional`. If you see a type like `Optional<Optional<T>>` in your code, you have used `map` when you should have used `flatMap`.

### filter

filter is a specific concept in Optional; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `filter` in one sentence.
- Recognize `filter` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `filter`.

Tiny example or mental model:

- When reading code, ask: what does `filter` change, allow, reject, or clarify?

#### Detailed Explanation
`filter(Predicate<? super T> predicate)` allows you to conditionally discard a value. If a value is present and matches the given predicate, the `Optional` is returned as-is. If the value does not match the predicate, or if the `Optional` is empty, an empty `Optional` is returned.

#### Runnable Code Example
```java
import java.util.Optional;

public class OptionalFilterExample {
    public static void main(String[] args) {
        Optional<String> opt = Optional.of("apple");

        // Predicate matches
        Optional<String> matched = opt.filter(s -> s.startsWith("a"));
        System.out.println(matched.isPresent()); // true

        // Predicate does not match
        Optional<String> unmatched = opt.filter(s -> s.startsWith("b"));
        System.out.println(unmatched.isPresent()); // false
    }
}
```

#### Common Mistake
Checking `isPresent()` and then performing an if-statement check on the unwrapped value, instead of using `filter()`.
*Before (Imperative):*
```java
if (opt.isPresent() && opt.get().length() > 5) {
    System.out.println(opt.get());
}
```
*After (Idiomatic):*
```java
opt.filter(s -> s.length() > 5).ifPresent(System.out::println);
```

### Do not overuse Optional

Optional is a container that may or may not hold a non-null value.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Do not overuse Optional` in one sentence.
- Recognize `Do not overuse Optional` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Do not overuse Optional`.

Tiny example or mental model:

- `Optional.ofNullable(value)` handles a possibly-null value.

#### Detailed Explanation
`Optional` is designed strictly as a return type to handle the absence of a value cleanly without throwing NPE. It should not be used as:
- Class fields (adds memory overhead, and `Optional` is not `Serializable`).
- Method parameters (forces caller to wrap parameters, increases risk of NPE if they pass a null `Optional`).
- Wrapping collection elements or return types of collection structures (e.g., return empty collections instead of an Optional wrapping a collection).

#### Runnable Code Example
```java
import java.util.Optional;
import java.util.List;
import java.util.Collections;

public class OveruseExample {
    // ANTI-PATTERN: Optional as a parameter
    public static void printUser(Optional<String> username) {
        // Bad! Caller might pass null instead of Optional.empty(), causing NPE here
        if (username.isPresent()) {
            System.out.println(username.get());
        }
    }

    // IDIOMATIC: Use method overloading or nullable parameter
    public static void printUser(String username) {
        if (username != null) {
            System.out.println(username);
        }
    }

    // ANTI-PATTERN: Optional of List
    public static Optional<List<String>> getNames(boolean exists) {
        return exists ? Optional.of(List.of("Alice")) : Optional.empty();
    }

    // IDIOMATIC: Return empty list
    public static List<String> getNamesIdiomatic(boolean exists) {
        return exists ? List.of("Alice") : Collections.emptyList();
    }
}
```

#### Common Mistake
Designing domain objects or entities with fields of type `Optional<T>`. This will break frameworks that serialize objects (e.g., Jackson, standard Java serialization) and wastes memory (an extra object reference per field).

### Optional in return type

Optional is a container that may or may not hold a non-null value.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Optional in return type` in one sentence.
- Recognize `Optional in return type` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Optional in return type`.

Tiny example or mental model:

- `Optional.ofNullable(value)` handles a possibly-null value.

#### Detailed Explanation
The primary purpose of `Optional` is to serve as a return type for methods that might not have a result. This forces the client/caller to explicitly handle the empty state.
**Rules for Returns**:
- Never return `null` from a method that is declared to return `Optional<T>`. Always return `Optional.empty()`. Returning `null` defeats the design and causes `NullPointerException` on the container itself when the caller attempts to chain operations.

#### Runnable Code Example
```java
import java.util.Optional;

public class OptionalReturnExample {
    // ANTI-PATTERN: Returning null for Optional
    public static Optional<String> findUserBad(int id) {
        if (id == 0) return null; // Horrible! Caller gets NPE on the Optional container.
        return Optional.of("User" + id);
    }

    // IDIOMATIC: Return Optional.empty()
    public static Optional<String> findUserGood(int id) {
        if (id == 0) return Optional.empty();
        return Optional.of("User" + id);
    }

    public static void main(String[] args) {
        try {
            findUserBad(0).orElse("Default"); // Throws NullPointerException!
        } catch (NullPointerException e) {
            System.out.println("NPE caught due to returning null!");
        }

        String user = findUserGood(0).orElse("Default"); // Safe and works!
        System.out.println("User: " + user); // User: Default
    }
}
```

#### Common Mistake
Returning `Optional` from getters where a nullable raw reference is expected by serialization or ORM libraries (like Hibernate). For entity fields, use standard nullable fields and write a getter that returns a raw nullable or construct the `Optional` on-the-fly.

## Case Study: Optional Anti-Patterns vs Idiomatic Code

To ensure clean design, performance, and standard-compliant Java code, developers must avoid misusing `Optional` in common scenarios.

### Anti-Pattern 1: Optional as Class Fields
```java
// BAD: Optional field (wastes memory, not Serializable)
public class Employee {
    private String name;
    private Optional<String> middleName; // Anti-pattern
}
```
**Why it is bad:**
1. `Optional` is not `Serializable`. If this class is serialized (e.g., in a session state, distributed cache, or via RMI), a `NotSerializableException` will be thrown.
2. Every `Optional` instance adds 16 bytes of memory overhead on 64-bit JVMs (plus references), which degrades performance when millions of entities are loaded.

**Idiomatic Solution:**
Keep the field nullable and return `Optional` in the getter if needed.
```java
// GOOD: Nullable field, Optional returned in getter
public class Employee {
    private String name;
    private String middleName; // Can be null

    public Optional<String> getMiddleName() {
        return Optional.ofNullable(middleName);
    }
}
```

### Anti-Pattern 2: Optional as Method Parameters
```java
// BAD: Optional parameter forces wrapper creation
public void updateAddress(int employeeId, Optional<String> street) {
    if (street.isPresent()) {
        // update
    }
}
```
**Why it is bad:**
1. It forces the caller to wrap their arguments (e.g., `updateAddress(1, Optional.of("Main St"))` or `updateAddress(1, Optional.empty())`), creating boilerplate.
2. Callers might pass `null` to the method instead of `Optional.empty()`, leading to a `NullPointerException` inside the method when calling `street.isPresent()`.

**Idiomatic Solution:**
Use method overloading or handle standard nullable parameters.
```java
// GOOD: Overloaded methods or raw nullable parameter
public void updateAddress(int employeeId, String street) {
    if (street != null) {
        // update
    }
}
```

### Anti-Pattern 3: Optional wrapping Collections
```java
// BAD: Optional of List
public Optional<List<Order>> getOrders(int customerId) {
    List<Order> orders = orderDb.find(customerId);
    if (orders.isEmpty()) {
        return Optional.empty(); // Anti-pattern
    }
    return Optional.of(orders);
}
```
**Why it is bad:**
Collections (Lists, Sets, Maps) already have a standard way of representing absence: the empty collection (`Collections.emptyList()`, `List.of()`). Wrapping them in `Optional` forces the caller to do a double check (checking if the Optional is empty, and then checking if the list is empty).

**Idiomatic Solution:**
Always return an empty collection directly.
```java
// GOOD: Return empty list directly
public List<Order> getOrders(int customerId) {
    List<Order> orders = orderDb.find(customerId);
    return orders != null ? orders : Collections.emptyList();
}
```

### Anti-Pattern 4: The `isPresent()` + `get()` Pattern (Imperative Optional)
```java
// BAD: Imperative check defeats functional purpose
Optional<User> userOpt = findUser(123);
if (userOpt.isPresent()) {
    System.out.println(userOpt.get().getName());
}
```
**Why it is bad:**
It mimics traditional null checking and does not gain any functional programming benefits. If the developer forgets the `isPresent()` check and calls `get()`, they get a runtime exception.

**Idiomatic Solution:**
Use `ifPresent`, `map`, `orElseGet`, or `orElseThrow`.
```java
// GOOD: Declarative transformation and consumption
findUser(123)
    .map(User::getName)
    .ifPresent(System.out::println);
```

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
