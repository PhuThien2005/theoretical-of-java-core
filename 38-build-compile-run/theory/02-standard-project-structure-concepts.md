# Build, Compile, Run - Part 2

## Learning Goal

This file covers standard project structure layouts and unit testing with JUnit. Study each concept as a practical Java rule.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Standard project structure` | The standard directory layout for organizing source code, resources, and tests (used by Maven, Gradle, etc.). |
| `Basic unit test with JUnit` | Standard test frameworks and assertion libraries used to verify unit functionality of code. |

## Detailed Notes

### Standard project structure

Java projects follow a conventional directory layout defined by modern build tools (Maven/Gradle). Adhering to this convention allows tools to automate compilation, testing, and packaging without manual classpath configurations.

- **Standard Directory Layout**:
  ```text
  my-app/
  ├── pom.xml                   # Maven project configuration (or build.gradle for Gradle)
  └── src/                      # Root directory for all source code and assets
      ├── main/                 # Production code and assets
      │   ├── java/             # Java source files organized by package structure
      │   │   └── com/
      │   │       └── example/
      │   │           └── App.java
      │   └── resources/        # Configuration, properties, XMLs, static files
      │       └── application.properties
      └── test/                 # Test code and assets
          ├── java/             # Test source files matching production package structure
          │   └── com/
          │       └── example/
          │           └── AppTest.java
          └── resources/        # Test-only resource files
              └── test-data.json
  ```

- **Common Mistake / Failure Mode**:
  - Placing Java source files directly under `src/main/` or `src/` instead of `src/main/java/`. Build tools default their compilation tasks to look only inside `src/main/java`. Placed incorrectly, compiler tasks will complete successfully but compile zero files, leading to missing output files.
  - Adding production resources to `src/main/java/` instead of `src/main/resources/`. The compile phase only copies resource files out of resources folders. Placing them in `java/` means they might not get copied into the final JAR, resulting in runtime `NullPointerException` or `FileNotFoundException` when attempting to load them via `ClassLoader.getResourceAsStream()`.

---

### Basic unit test with JUnit

JUnit (currently JUnit 5 / Jupiter) is the primary framework for writing automated unit tests in the JVM ecosystem. Tests verify that individual classes and methods behave correctly.

- **Code Example**:
  ```java
  package com.example;

  import org.junit.jupiter.api.Test;
  import static org.junit.jupiter.api.Assertions.*;

  public class CalculatorTest {

      private final Calculator calculator = new Calculator();

      @Test
      void testAdd_withPositiveNumbers_shouldReturnSum() {
          int result = calculator.add(2, 3);
          
          // Assertion checks: expected value first, actual value second
          assertEquals(5, result, "2 + 3 should be 5");
      }

      @Test
      void testDivide_byZero_shouldThrowArithmeticException() {
          // Asserting exception throwing
          assertThrows(ArithmeticException.class, () -> {
              calculator.divide(10, 0);
          }, "Dividing by zero must throw ArithmeticException");
      }
  }
  ```

- **Common Mistake / Failure Mode**:
  - **Forgetting the `@Test` Annotation**: If you omit `@Test` (from package `org.junit.jupiter.api`), the build tool/IDE will completely ignore the method, giving you a false sense of security that all tests are passing.
  - **Reversed Assertion Arguments**: Writing `assertEquals(actual, expected)` instead of `assertEquals(expected, actual)`. If the test fails, JUnit's error message will read: `Expected: [actual_value] but was: [expected_value]`, which is highly confusing.
  - **Assertion Overload**: Putting dozens of unrelated assertions in a single test method. If the first assertion fails, subsequent assertions are not executed, hiding other bugs. Keep tests focused and split assertions where appropriate.

## Common Review Prompts

- Which directory handles production configurations vs test mock data? (`src/main/resources` vs `src/test/resources`)
- What is the risk of testing private implementation details? (It couples tests tightly to internal details, making refactoring difficult. Tests should verify public API interfaces and observable behavior).
- How do build tools interact with JUnit test results? (Build pipelines usually run `test` tasks; if any test assertions fail, the build fails, preventing the deployment of buggy code).
