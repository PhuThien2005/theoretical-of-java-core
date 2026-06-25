# Basic Unit Testing - Part 2: Exceptions, Private Logic & Coverage

## Learning Goal

This file covers testing exceptional behavior, the strategy for testing private methods, and the meaning and limitations of code coverage.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Test exception` | Verifying that code throws the expected exception type under erroneous conditions. |
| `Test private logic indirectly` | Testing private helper methods through public API endpoints rather than breaking encapsulation via reflection. |
| `Basic code coverage` | Metric indicating the percentage of code lines or branches executed during test suite runs. |

---

## Detailed Notes

### Test Exception

Unit tests must verify that code handles bad inputs or failure modes gracefully by throwing expected exceptions.

- **`assertThrows` in JUnit 5**:
  - The standard approach is to use `Assertions.assertThrows(Class<T> expectedType, Executable executable)`.
  - It returns the thrown exception instance, which allows asserting further details like the exact error message or custom exception state.

- **Runnable Example**:
  ```java
  import org.junit.jupiter.api.Test;
  import static org.junit.jupiter.api.Assertions.*;

  class Account {
      private double balance;

      public void withdraw(double amount) {
          if (amount <= 0) {
              throw new IllegalArgumentException("Amount must be positive");
          }
          if (amount > balance) {
              throw new IllegalStateException("Insufficient funds: " + balance);
          }
          balance -= amount;
      }
  }

  class AccountTest {
      @Test
      void testWithdrawalThrowsOnInsufficientFunds() {
          Account acc = new Account(); // balance is 0
          
          // Verify exact exception class is thrown
          IllegalStateException exception = assertThrows(
              IllegalStateException.class, 
              () -> acc.withdraw(100.0)
          );

          // Verify the exception message details
          assertEquals("Insufficient funds: 0.0", exception.getMessage());
      }
  }
  ```

---

### Test Private Logic Indirectly

A common dilemma is how to test a complex `private` helper method. 

- **The Rule**: **Never make a method public or use reflection just to test it.** Private methods represent implementation details.
- **Why?**: If you test private methods directly (e.g. by using reflection or package-private visibility), you couple your tests to the current implementation. If you refactor the internal design without changing the public contract, your tests will break.
- **Solution**: Test private methods **indirectly** by invoking the public methods that call them. If a private method is too complex to test this way, it is a design smell indicating that the logic should be extracted into a separate helper class with its own public API and injected dependency.

---

### Basic Code Coverage

**Code Coverage** measures the proportion of source code executed when a test suite runs. Common types include:
1. **Line/Statement Coverage**: Did execution pass through this line of code?
2. **Branch Coverage**: Did execution cover both `true` and `false` paths of control flow statements (`if`, `switch`)?

- **The Limit**: **High coverage is not a guarantee of high quality.**
  - Code coverage tools only check if lines of code were *executed*. They do not verify if the *assertions* inside the tests are correct or meaningful.
  - You can write tests with 100% coverage that assert nothing (Assertion-free testing). Such tests will fail on crashes, but won't catch logic bugs.

---

## Common Mistakes & Traps

### 1. The Try-Catch "Missing Exception" Trap
Before `assertThrows`, developers often used try-catch blocks to assert exceptions. A common error is forgetting to fail the test if the exception is *not* thrown.

*Incorrect:*
```java
@Test
void testWithdrawal_Bad() {
    Account acc = new Account();
    try {
        acc.withdraw(100.0); // If this does NOT throw, test still passes!
    } catch (IllegalStateException e) {
        assertEquals("Insufficient funds: 0.0", e.getMessage());
    }
}
```

*Correct (Using `assertThrows`):*
```java
@Test
void testWithdrawal_Good() {
    Account acc = new Account();
    IllegalStateException ex = assertThrows(IllegalStateException.class, () -> acc.withdraw(100.0));
    assertEquals("Insufficient funds: 0.0", ex.getMessage());
}
```

---

## Case Study: Testing Private Validator Logic Indirectly

Observe a `UserRegistrationService` that has internal private email and password validation rules:

```java
public class UserRegistrationService {
    
    public void registerUser(String email, String password) {
        validateEmail(email);
        validatePassword(password);
        // Save user logic...
    }

    private void validateEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password too short");
        }
    }
}
```

Instead of using reflection or making `validateEmail` package-private, we test this logic through the public API `registerUser`:

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserRegistrationServiceTest {
    private final UserRegistrationService service = new UserRegistrationService();

    @Test
    void testRegisterUser_InvalidEmail_ThrowsException() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class, 
            () -> service.registerUser("invalid-email", "validPassword123")
        );
        assertEquals("Invalid email format", ex.getMessage());
    }

    @Test
    void testRegisterUser_ShortPassword_ThrowsException() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class, 
            () -> service.registerUser("test@example.com", "short")
        );
        assertEquals("Password too short", ex.getMessage());
    }
}
```

---

## Why Private Methods Should Be Tested Indirectly Through the Public API

A `private` method is an **implementation detail** — it represents how a class achieves its behavior, not what it promises to do. The class's public API is its contract. When you test private methods directly (via `setAccessible(true)` reflection), you break encapsulation and create two serious problems:

1. **Implementation coupling**: The test depends on a specific internal method name, signature, and existence. If you refactor the implementation (rename, inline, or reorganize private methods) without changing the public behavior, the reflection-based test breaks — even though no observable behavior changed.

2. **Missing the contract**: Private method tests verify that a specific private mechanism works. They do not verify that the public method correctly orchestrates all private steps together. A private method may work correctly in isolation but be called in the wrong order from the public method.

The correct approach: test the public method with inputs designed to exercise the private helper through normal code paths. If a private method is so complex that it cannot be adequately tested via the public API, that complexity is a **design smell** — the logic should be extracted into a collaborator class with its own public API and injected as a dependency.

### Mental Model: Direct vs. Indirect Testing
```
[Wrong — reflection breaks encapsulation]
Method method = MyClass.class.getDeclaredMethod("validateEmail", String.class);
method.setAccessible(true);
method.invoke(service, "invalid");
// If validateEmail is renamed to checkEmailFormat → test breaks
// But the public behavior may be unchanged

[Correct — test through public API]
// Call registerUser with an invalid email
IllegalArgumentException ex = assertThrows(
    IllegalArgumentException.class,
    () -> service.registerUser("invalid", "password123")
);
assertEquals("Invalid email format", ex.getMessage());
// If validateEmail is renamed internally → test still passes
// because the public contract (exception behavior) is unchanged
```

### Code Example: Testing Private Logic Through Public API
```java
class UserRegistrationServiceTest {
    private final UserRegistrationService service = new UserRegistrationService();

    @Test
    void privateValidateEmail_isExercisedViaRegisterUser() {
        // Exercises the private validateEmail() without accessing it directly
        assertThrows(IllegalArgumentException.class,
            () -> service.registerUser("not-an-email", "validpassword123"));
    }

    @Test
    void privateValidatePassword_isExercisedViaRegisterUser() {
        // Exercises the private validatePassword() without accessing it directly
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> service.registerUser("valid@test.com", "short"));
        assertEquals("Password too short", ex.getMessage());
    }
}
```

### Cause-Effect Chain
Private method is an implementation detail &rarr; Testing directly via reflection couples test to internal structure &rarr; Refactoring internal methods breaks tests with no behavioral change &rarr; Test through public API instead &rarr; Test exercises private logic as side effect of public call &rarr; Refactoring internals does not break tests &rarr; If private method is too complex to test indirectly &rarr; Extract it to a collaborator class with its own public API.

---

## Why assertThrows Is Safer Than Try-Catch for Exception Testing

The try-catch pattern for exception testing has a critical silent failure mode: if the exception is **not** thrown, the test passes without any assertion having executed. The test provides false confidence — it says "PASS" when the behavior it was supposed to verify never occurred.

`assertThrows(ExceptionType.class, () -> ...)` solves this by failing the test if no exception is thrown. Additionally, it returns the thrown exception object, allowing assertions on the exception's message, cause, or custom fields — none of which were conveniently accessible in the try-catch pattern.

### Mental Model: try-catch trap vs assertThrows safety
```
[Try-catch — silent success when exception absent]
try {
    account.withdraw(100.0); // If this DOESN'T throw → nothing happens
} catch (IllegalStateException e) {
    assertEquals("Insufficient funds", e.getMessage());
}
// If no exception is thrown, the catch block never runs
// All assertions skipped → test passes with zero verification!

[assertThrows — explicit failure when exception absent]
IllegalStateException ex = assertThrows(
    IllegalStateException.class,
    () -> account.withdraw(100.0)
    // If no exception → TEST FAILS with "Expected IllegalStateException to be thrown"
);
assertEquals("Insufficient funds: 0.0", ex.getMessage()); // Also asserts message
```

### Code Example: Complete assertThrows pattern
```java
@Test
void testWithdraw_insufficientFunds_throwsWithCorrectMessage() {
    Account acc = new Account(); // balance = 0

    // assertThrows fails if no exception is thrown
    IllegalStateException ex = assertThrows(
        IllegalStateException.class,
        () -> acc.withdraw(100.0)
    );

    // Can also assert exception details — not possible in try-catch without extra code
    assertEquals("Insufficient funds: 0.0", ex.getMessage());
    assertNotNull(ex); // redundant but demonstrates ex is accessible
}
```

### Cause-Effect Chain
Try-catch pattern: exception not thrown &rarr; catch block skipped &rarr; all assertions inside catch skipped &rarr; test reports PASS despite nothing being verified. `assertThrows`: exception not thrown &rarr; test immediately fails with clear message "Expected X to be thrown, but nothing was thrown" &rarr; Returns thrown exception for further assertion &rarr; Complete exception verification in one statement.

---

## Why Code Coverage Is a Necessary but Insufficient Quality Metric

Code coverage measures **execution** — whether a line or branch of code was visited during test runs. It does not measure **correctness** — whether the assertions in those tests actually verify the right behavior. This gap creates the "assertion-free testing" anti-pattern: tests that achieve high coverage by executing code but never assert anything about the results.

For example, a test that calls `calculator.add(2, 3)` without calling any `assertEquals` or other assertion achieves 100% line coverage for the `add` method, but catches zero bugs. Even a test with `assertTrue(true)` achieves coverage. Coverage tools cannot distinguish meaningful assertions from empty ones.

High code coverage is a **necessary** baseline (untested code definitely has zero test protection) but an **insufficient** guarantee of quality. The complementary metric is **mutation testing** (tools like PIT), which introduces artificial code mutations and verifies that the test suite catches them.

### Mental Model: Coverage vs. Correctness
```
[High coverage, zero quality]
@Test
void testAdd() {
    calculator.add(2, 3); // Code is EXECUTED ← 100% line coverage achieved
    // No assertion — result is discarded
    // Bug: add() returns 0 always → test passes, bug undetected
}

[High coverage + quality assertions]
@Test
void testAdd() {
    int result = calculator.add(2, 3);
    assertEquals(5, result); // VERIFIES the result
    // Bug: add() returns 0 → test fails → bug detected
}
```

### Code Example: Branch coverage and the limits of line coverage
```java
public String classify(int n) {
    if (n > 0) return "positive";  // line 1
    if (n < 0) return "negative";  // line 2
    return "zero";                  // line 3
}

@Test
void testClassify_linesCovered() {
    classify(1);  // Covers line 1 — 1/3 lines covered, 0 assertions
    classify(-1); // Covers line 2 — 2/3 lines covered, 0 assertions
    classify(0);  // Covers line 3 — 3/3 lines covered (100%), 0 assertions!
    // Result: 100% line coverage, 0% bug detection
}

@Test
void testClassify_withAssertions() {
    assertEquals("positive", classify(1));
    assertEquals("negative", classify(-1));
    assertEquals("zero", classify(0));
    // Same coverage, but now assertions would catch incorrect return values
}
```

### Cause-Effect Chain
100% line coverage achieved &rarr; Every code line was executed during tests &rarr; Does NOT mean each line's behavior was asserted &rarr; Tests with no assertions pass silently despite any bug &rarr; Assertion-free testing anti-pattern: coverage metric is satisfied, quality is not &rarr; Complement coverage with assertion quality review and mutation testing.

## Reference Links

- https://junit.org/junit5/docs/current/user-guide/ (JUnit 5 User Guide)
- https://site.mockito.org/ (Mockito documentation)
