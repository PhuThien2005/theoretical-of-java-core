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
