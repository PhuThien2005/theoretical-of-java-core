# Some Common Utility APIs - Part 1

## Learning Goal

This file covers a focused slice of **Some Common Utility APIs** including mathematical, arbitrary-precision, system, and process execution APIs. Study each concept as a practical Java rule.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Math` | Mathematical functions class (`java.lang.Math`) with static operations. |
| `Random` | Pseudorandom generator class (`java.util.Random`). |
| `BigInteger` | Immutable arbitrary-precision integers (`java.math.BigInteger`). |
| `BigDecimal` | Immutable arbitrary-precision decimal numbers (`java.math.BigDecimal`) for precise financial maths. |
| `UUID` | Universally Unique Identifier (`java.util.UUID`) generation. |
| `Objects` | Null-safe utility helpers (`java.util.Objects`) for validation and object checks. |
| `Optional` | Container object (`java.util.Optional`) protecting against NullPointerExceptions. |
| `System` | Interface class (`java.lang.System`) to JVM properties, environment, streams, and system timers. |
| `Runtime` | JVM execution state controller (`java.lang.Runtime`). |
| `ProcessBuilder` | System process creation and management manager. |

---

## Detailed Notes

### Math

The `java.lang.Math` class contains static methods for performing basic numeric operations such as exponential, logarithmic, square root, and trigonometric calculations.

- **Runnable Example**:
  ```java
  double squareRoot = Math.sqrt(25.0); // 5.0
  int absoluteValue = Math.abs(-10);   // 10
  long rounded = Math.round(5.6);     // 6
  ```

- **Common Mistake / Failure Mode**:
  - **Overflow Vulnerability**: Standard methods do not check for overflow. For example, `Math.abs(Integer.MIN_VALUE)` returns `Integer.MIN_VALUE` because the absolute value of $-2^{31}$ cannot be represented in positive 32-bit signed two's complement.
  - **Solution**: Use Java 8's exact arithmetic methods (e.g., `Math.addExact`, `Math.multiplyExact`, `Math.absExact`) which throw an `ArithmeticException` on overflow:
    ```java
    try {
        int overflowed = Math.addExact(Integer.MAX_VALUE, 1);
    } catch (ArithmeticException e) {
        System.out.println("Overflow detected!");
    }
    ```

---

### Random

The `java.util.Random` class generates pseudorandom numbers using a Linear Congruential Generator (LCG).

- **Runnable Example**:
  ```java
  Random rand = new Random();
  int randomInt = rand.nextInt(100);    // 0 (inclusive) to 100 (exclusive)
  double randomDouble = rand.nextDouble(); // 0.0 to 1.0
  ```

- **Common Mistake / Failure Mode**:
  - **Security Risk**: `Random` is not cryptographically secure and is predictable. Never use it for generating security-sensitive data (e.g., passwords, session tokens, cryptography keys). Use `java.security.SecureRandom` instead.
  - **Multithreading Contention**: While thread-safe, a shared `Random` instance incurs performance penalties under high contention. Use `java.util.concurrent.ThreadLocalRandom.current().nextInt()` for concurrent environments.

---

### BigInteger

`java.math.BigInteger` represents arbitrary-precision integers. It is immutable and behaves like a primitive integer but without any bit-size limits.

- **Runnable Example**:
  ```java
  BigInteger largeVal = new BigInteger("123456789012345678901234567890");
  BigInteger multiplier = BigInteger.valueOf(10);
  BigInteger result = largeVal.multiply(multiplier);
  ```

- **Common Mistake / Failure Mode**:
  - **Immutability Ignored**: Arithmetic operations on `BigInteger` do not modify the instance itself. Instead, they return a new `BigInteger`.
    ```java
    BigInteger val = BigInteger.TEN;
    val.add(BigInteger.ONE); // WRONG: result is discarded!
    val = val.add(BigInteger.ONE); // CORRECT: val is now 11
    ```

---

### BigDecimal

`java.math.BigDecimal` represents arbitrary-precision decimal numbers, providing complete control over scale and rounding.

- **Runnable Example**:
  ```java
  BigDecimal val1 = new BigDecimal("0.1");
  BigDecimal val2 = new BigDecimal("0.2");
  BigDecimal sum = val1.add(val2); // Exactly 0.3
  ```

- **Common Mistake / Failure Mode**:
  - **Double Literal Initialization**: Initializing via `double` literal introduces floating-point noise.
    ```java
    BigDecimal bad = new BigDecimal(0.1); // Value is 0.10000000000000000555111...
    BigDecimal good = new BigDecimal("0.1"); // Value is exactly 0.1
    ```
  - **Non-Terminating Decimal Division**: Dividing without a rounding mode when result is infinite (like 1/3) crashes with an `ArithmeticException`.
    ```java
    BigDecimal one = BigDecimal.ONE;
    BigDecimal three = new BigDecimal("3");
    // BigDecimal res = one.divide(three); // WRONG: Throws ArithmeticException
    BigDecimal res = one.divide(three, 2, RoundingMode.HALF_UP); // CORRECT: 0.33
    ```

---

### UUID

`java.util.UUID` represents a 128-bit Universally Unique Identifier.

- **Runnable Example**:
  ```java
  UUID uniqueId = UUID.randomUUID(); // Cryptographically strong Type 4 UUID
  String uuidStr = uniqueId.toString();
  UUID parsed = UUID.fromString(uuidStr);
  ```

- **Common Mistake / Failure Mode**:
  - Parsing unchecked inputs: `UUID.fromString(input)` throws a runtime `IllegalArgumentException` if the string does not conform to the expected UUID hex structure. Always wrap in validation or try-catch.

---

### Objects

`java.util.Objects` consists of static utility methods for operating on objects (checking nulls, comparing, hashing, etc.).

- **Runnable Example**:
  ```java
  boolean isSame = Objects.equals(objA, objB); // Null-safe comparison
  int hashValue = Objects.hash(field1, field2); // Null-safe hash code generation
  Objects.requireNonNull(name, "Name cannot be null"); // Inline null check validation
  ```

- **Common Mistake / Failure Mode**:
  - Using `Objects.equals(array1, array2)` for comparing array values. It checks reference equality only, returning `false` for identical arrays with different memory addresses. Use `Arrays.equals(array1, array2)` instead.

---

### Optional

`java.util.Optional` is a container object introduced in Java 8 to represent the presence or absence of a non-null value, aiming to eliminate `NullPointerException` (NPE).

- **Runnable Example**:
  ```java
  Optional<String> opt = Optional.ofNullable(getStringThatMayBeNull());
  String val = opt.orElse("Fallback Default");
  
  // Lazy evaluation: fallback supplier only executes if opt is empty
  String lazyVal = opt.orElseGet(() -> computeDefaultValue());
  ```

- **Common Mistake / Failure Mode**:
  - **Eager Evaluation in `orElse`**: Passing a method call to `orElse` causes that method to run *every single time*, regardless of whether the Optional is empty or populated.
    ```java
    // WRONG: fetchDefault() runs even if opt has a value!
    String value = opt.orElse(fetchDefault());
    
    // CORRECT: fetchDefault() runs only if opt is empty.
    String value = opt.orElseGet(() -> fetchDefault());
    ```
  - **Optional Abuse**: Do not use `Optional` for method/constructor parameters, class fields, or wrapping collection types (return empty collections instead). It was designed primarily as a return type for methods that need a clear way to indicate "no result".

---

### System

`java.lang.System` provides access to system resources, standard streams, environment properties, and garbage collection controllers.

- **Runnable Example**:
  ```java
  long start = System.nanoTime(); // High-precision elapsed time in nanoseconds
  String tempDir = System.getProperty("java.io.tmpdir"); // Get JVM system property
  String path = System.getenv("PATH"); // Get OS environment variable
  ```

- **Common Mistake / Failure Mode**:
  - **Using CurrentTimeMillis for Timers**: Using `System.currentTimeMillis()` to measure elapsed method time is risky because it measures wall-clock time. If NTP syncs or a user changes the OS clock during execution, the result can be negative or highly inaccurate. Use `System.nanoTime()` for duration checks.

---

### Runtime

`java.lang.Runtime` allows the application to interface with the JVM runtime environment (querying memory, adding shutdown hooks, etc.).

- **Runnable Example**:
  ```java
  Runtime runtime = Runtime.getRuntime();
  long freeMemory = runtime.freeMemory(); // Free memory in JVM heap
  runtime.addShutdownHook(new Thread(() -> {
      System.out.println("Shutting down JVM gracefully...");
  }));
  ```

- **Common Mistake**: Trying to force garbage collection with `Runtime.getRuntime().gc()`. It is merely a suggestion to the JVM; the garbage collector is free to ignore the call entirely, and calling it repeatedly degrades performance.

---

### ProcessBuilder

`ProcessBuilder` is used to create and start operating system processes.

- **Runnable Example**:
  ```java
  ProcessBuilder builder = new ProcessBuilder("ls", "-la");
  builder.redirectErrorStream(true); // Combine standard error and stdout
  Process process = builder.start();
  ```

- **Common Mistake / Failure Mode**:
  - **Hanging Processes**: If you spawn an external process that generates significant output, and you do not read its output stream, the OS buffer will fill up. Once full, the spawned process blocks indefinitely, causing your Java program to hang. Redirect the output (`builder.inheritIO()` or `builder.redirectOutput(file)`) or actively read from `process.getInputStream()`.
