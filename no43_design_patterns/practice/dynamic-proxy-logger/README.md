# Exercise: Dynamic Proxy Logger

## Objective
Implement a logging proxy pattern wrapping core service classes using Java's dynamic reflection proxies.

## Problem Description
A dynamic proxy allows you to intercept method calls to any interface dynamically at runtime. This is extremely useful for cross-cutting concerns like logging, security authorization, transaction management, or performance profiling.
In this exercise, you will implement an `InvocationHandler` that intercepts all methods called on a service interface, logs the details, and measures the elapsed execution time.

## Requirements
1. **Interface `UserService`**:
   - `String getRole(String username)`
   - `void updateLastLogin(String username)`
   - Both operations should be implemented by a concrete class `UserServiceImpl`.

2. **InvocationHandler `LoggingInvocationHandler`**:
   - Implements `java.lang.reflect.InvocationHandler`.
   - Takes a `target` Object (the actual implementation object) and a `StringBuilder` / list to write logs into.
   - For every method intercepted:
     - Log starting invocation: `"[START] Method: " + method.getName() + " called with args: " + Arrays.toString(args)`
     - Measure time before execution using `System.nanoTime()`.
     - Invoke the method on target using `method.invoke(target, args)`.
     - Measure time after and calculate duration in milliseconds (using floating point arithmetic, e.g. `(end - start) / 1_000_000.0`).
     - Log completion: `"[END] Method: " + method.getName() + " completed in X.XX ms"` (where X.XX is the floating point duration).
     - Return the result of the method call.
     - Note: Handle exceptions thrown by target methods cleanly (they are wrapped in `InvocationTargetException` - you should unwrap them and rethrow the target exception!).

3. **Proxy Creator**:
   - Provide a factory method `public static <T> T createProxy(Class<T> interfaceType, T target, StringBuilder logDestination)` to instantiate the proxy class using `Proxy.newProxyInstance()`.
