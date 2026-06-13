# Optional Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## Optional

An immutable container object which may or may not contain a single non-null value.

Why it matters: It provides a type-level representation of presence or absence of a value, forcing API consumers to explicitly handle the empty state, thereby reducing `NullPointerException` bugs.

Common confusion: Developers sometimes treat `Optional` as a full replacement for `null` checks on every reference, leading to bloated code and wrapper overhead. It should primarily be used as a method return type.

Small example: `Optional<String> optionalName = Optional.ofNullable(name);`

## empty optional

An instance of `Optional` that contains no value, retrieved via `Optional.empty()`.

Why it matters: It represents absence cleanly without resorting to returning `null`. Returning an empty `Optional` allows callers to chain fluent methods safely.

Common confusion: Since `Optional.empty()` returns a cached singleton instance, developers should never return `null` from a method that is declared to return an `Optional<T>`.

Small example: `return Optional.empty();`

## fallback value

A default value or block of execution provided to handle the case where an `Optional` is empty.

Why it matters: Methods like `orElse`, `orElseGet`, and `orElseThrow` allow unwrapping the `Optional` safely by defining what to do when the value is missing.

Common confusion: Using `orElse` (e.g. `orElse(new DatabaseQuery())`) always evaluates the parameter expression, causing performance side-effects. Use `orElseGet` for lazy evaluation of defaults.

Small example: `String val = opt.orElse("Default");`

## map

An intermediate method that transforms the value inside the `Optional` if it is present.

Why it matters: It allows applying transformation functions to the wrapped value without needing manual null checks or `isPresent` checks. The mapper's raw return value is automatically wrapped back into an `Optional`.

Common confusion: If the mapping function returns `null`, `map` returns `Optional.empty()`. If the mapping function returns another `Optional`, `map` returns a nested `Optional<Optional<T>>`.

Small example: `Optional<Integer> length = optName.map(String::length);`

## flatMap

An intermediate transformation method that flattens nested `Optional` structures.

Why it matters: When a mapping function itself returns an `Optional`, using `flatMap` avoids double-wrapping (`Optional<Optional<T>>`) by returning the inner `Optional` directly.

Common confusion: Unlike `map`, if the mapper function in `flatMap` returns a `null` instead of an `Optional.empty()`, it throws a `NullPointerException`.

Small example: `Optional<String> email = userOpt.flatMap(User::getEmail);`

## optional misuse

Anti-patterns where `Optional` is used in inappropriate contexts.

Why it matters: Misusing `Optional` in fields, parameters, or collections degrades performance, breaks serialization, and introduces new null-pointer risks.

Common confusion: Developers often use `Optional` for class fields, which breaks standard Java serialization because `Optional` is not serializable.

Small example: Using `public void setStreet(Optional<String> street)` is an anti-pattern. Use overloading or a nullable parameter instead.
