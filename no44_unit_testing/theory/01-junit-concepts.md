# Basic Unit Testing - Part 1: JUnit 5 & Mockito Basics

| Concept | What to know |
| --- | --- |
| `JUnit` | Java testing framework for writing and running automated unit tests. |
| `Test case` | A method annotated with `@Test` that verifies a specific behavior of a class. |
| `Assertion` | Static methods from `org.junit.jupiter.api.Assertions` to verify expected outcomes. |
| `@Test` | Annotation marking a method as a test case; must not be private or static. |
| `@BeforeEach` | Runs before each `@Test` method to set up test fixtures. |
| `@AfterEach` | Runs after each `@Test` method to clean up state. |
| `@BeforeAll` | Runs once before all tests in a class; must be static unless using per-class lifecycle. |
| `@AfterAll` | Runs once after all tests in a class; must be static unless using per-class lifecycle. |
| `Basic Mockito` | Mocking framework used to isolate the unit under test by simulating dependencies. |
| `Mock object` | A simulated dependency configured to return canned responses or verify method calls. |

---

## Detailed Notes

### JUnit & The Test Case

**JUnit 5** (Jupiter) is the standard library for writing automated unit tests in modern Java. A **Test Case** is a single test class or test method that targets a specific behavioral path in code.

- **Test Lifecycle & Annotations**:
  - `@Test`: Marks a method as a test. JUnit instantiates a new instance of the test class for each `@Test` method to ensure test isolation.
  - `@BeforeEach` / `@AfterEach`: Executed before and after each individual test method. Ideal for resetting instance fields, opening clean test-local resources.
  - `@BeforeAll` / `@AfterAll`: Executed once before all tests run, and once after all tests have completed.
    - *Note*: By default, these must be `static` because JUnit instantiates the test class once per test method. If you annotate your class with `@TestInstance(Lifecycle.PER_CLASS)`, you can make these methods non-static.

- **Runnable Example (Lifecycle & Assertions)**:
  ```java
  import org.junit.jupiter.api.*;
  import static org.junit.jupiter.api.Assertions.*;

  @TestInstance(TestInstance.Lifecycle.PER_METHOD) // Default lifecycle
  class CalculatorTest {
      private Calculator calculator;

      @BeforeAll
      static void initGlobalResource() {
          System.out.println("Shared resource initialized once");
      }

      @BeforeEach
      void setUp() {
          calculator = new Calculator(); // Fresh instance for each test
      }

      @Test
      void testAddition() {
          int result = calculator.add(10, 5);
          assertEquals(15, result, "10 + 5 should equal 15");
      }

      @Test
      void testDivisionByZero() {
          assertThrows(ArithmeticException.class, () -> calculator.divide(10, 0));
      }

      @AfterEach
      void tearDown() {
          calculator = null;
      }

      @AfterAll
      static void cleanGlobalResource() {
          System.out.println("Shared resource cleaned up once");
      }
  }
  ```

---

### Basic Mockito & Mock Objects

**Mockito** is a mocking framework that allows developers to isolate the class under test by replacing real dependencies with **Mock Objects**. Mocks return configured values (stubbing) and remember interactions (verification).

- **Stubbing (`when`) vs. Verification (`verify`)**:
  - **Stubbing**: Configuring a mock to return a specific value when a specific method is called.
  - **Verification**: Checking that a method on the mock was called with specific arguments a certain number of times.

- **Runnable Example (Mockito)**:
  ```java
  import org.junit.jupiter.api.Test;
  import org.mockito.Mockito;
  import static org.junit.jupiter.api.Assertions.*;
  import static org.mockito.Mockito.*;

  class UserServiceTest {

      @Test
      void testGetUserEmail_WithMockedRepository() {
          // 1. Create Mock Object
          UserRepository mockRepo = mock(UserRepository.class);

          // 2. Stubbing behavior: when mockRepo.findUsername(1) is called, return "alice"
          when(mockRepo.findUsername(1L)).thenReturn("alice");

          UserService userService = new UserService(mockRepo);

          // 3. Act & Assert
          String username = userService.getUsername(1L);
          assertEquals("alice", username);

          // 4. Verification: Verify that findUsername(1L) was called exactly 1 time
          verify(mockRepo, times(1)).findUsername(1L);
      }
  }
  ```

---

## Common Mistakes & Traps

### 1. Wrong Assertion Parameter Order (JUnit 5 vs. TestNG / AssertJ)
In JUnit 5, assertions have the signature: `assertEquals(expected, actual, message)`.
A frequent error is swapping the `expected` and `actual` parameters. While this doesn't fail the test directly, it produces highly confusing and misleading failure reports.

*Incorrect:*
```java
// If add(2,2) returns 5, report says: "Expected [5] but was [4]"
assertEquals(calculator.add(2, 2), 4); 
```

*Correct:*
```java
// Report correctly says: "Expected [4] but was [5]"
assertEquals(4, calculator.add(2, 2)); 
```

### 2. Failing to Make `@BeforeAll` / `@AfterAll` Methods Static
Without `@TestInstance(Lifecycle.PER_CLASS)`, forgetting to make `@BeforeAll` or `@AfterAll` static will cause a `JUnitException` at runtime before any test executes.

*Incorrect:*
```java
@BeforeAll
void init() { // CRASH: Must be static unless PER_CLASS is set!
    dbConnection = new DbConnection();
}
```

### 3. Mocking Value Objects or DTOs
Mockito should be used to mock services, repositories, or external system boundaries. Do not mock plain data structures, DTOs, or Collections. Use real instances of those objects instead. Mocking a simple List or a String creates fragile, unnecessary boilerplate.

---

## Case Study: Testing a Service with Mocked Database and External API

Here is a typical production pattern isolating a `PaymentService` from its database and email notification client using Mockito.

```java
public class PaymentService {
    private final PaymentRepository repository;
    private final EmailClient emailClient;

    public PaymentService(PaymentRepository repository, EmailClient emailClient) {
        this.repository = repository;
        this.emailClient = emailClient;
    }

    public boolean processPayment(long userId, double amount) {
        if (amount <= 0) return false;
        
        boolean saved = repository.saveTransaction(userId, amount);
        if (saved) {
            emailClient.sendNotification(userId, "Payment of $" + amount + " successful.");
            return true;
        }
        return false;
    }
}
```

We test the business logic of `PaymentService` without initiating a real database transaction or sending real emails:

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Test
    void testProcessPayment_Success() {
        // Arrange
        PaymentRepository mockRepo = mock(PaymentRepository.class);
        EmailClient mockEmail = mock(EmailClient.class);
        
        when(mockRepo.saveTransaction(123L, 99.99)).thenReturn(true);
        
        PaymentService service = new PaymentService(mockRepo, mockEmail);

        // Act
        boolean result = service.processPayment(123L, 99.99);

        // Assert
        assertTrue(result);
        
        // Verify mock interactions occurred as expected
        verify(mockRepo).saveTransaction(123L, 99.99);
        verify(mockEmail).sendNotification(123L, "Payment of $99.99 successful.");
    }
}
```

---

## Why Mocking Isolates the Unit Under Test

Unit testing has a specific goal: test **one unit** (a class or method) in isolation from its external dependencies. When a class under test (`UserService`) depends on a database repository (`UserRepository`), calling real database methods in a unit test creates multiple problems:

1. **Coupling**: The test fails if the database is unavailable, the schema changed, or the test data is wrong — even if `UserService` logic is correct.
2. **Speed**: Real database I/O is orders of magnitude slower than in-memory assertions. A test suite with real I/O can take minutes rather than milliseconds.
3. **Non-determinism**: Real external systems (APIs, databases, clocks) return different results across runs, making tests flaky.

Mockito replaces real dependencies with in-memory mock objects that return configured (stubbed) values. The test now exercises only `UserService` logic — the repository is controlled entirely by the test itself.

### Mental Model: Real Dependency vs. Mock
```
[Without Mock — coupled to database]
Test calls UserService.getUser(1)
    → UserService calls UserRepository.findById(1)  ← real DB call
    → DB may be down, slow, or have different data  ← test fails for wrong reason
    → Test is actually testing DB + network + config, not UserService

[With Mock — isolated unit test]
Test calls UserService.getUser(1)
    → UserService calls mockRepo.findById(1)
    → mockRepo returns stub "alice" immediately (in-memory)
    → Test verifies UserService processed "alice" correctly
    → Only UserService logic is tested
```

### Code Example: Isolation via Mockito Stubbing and Verification
```java
@Test
void testGetUserEmail() {
    // Arrange: mock the dependency, define its behavior
    UserRepository mockRepo = mock(UserRepository.class);
    when(mockRepo.findById(1L)).thenReturn(new User(1L, "alice@example.com"));

    UserService service = new UserService(mockRepo); // inject mock

    // Act: test only UserService
    String email = service.getUserEmail(1L);

    // Assert: UserService correctly extracted the email
    assertEquals("alice@example.com", email);

    // Verify: UserService called the repo exactly once
    verify(mockRepo, times(1)).findById(1L);
}
```

### Cause-Effect Chain
`UserService` depends on `UserRepository` &rarr; Test injects a `mock(UserRepository.class)` instead of a real DB connection &rarr; `when(mockRepo.findById(1L)).thenReturn(...)` configures controlled return &rarr; Test runs entirely in-memory in milliseconds &rarr; Only `UserService` logic can cause the test to fail &rarr; Test is fast, deterministic, and isolated.

---

## Why JUnit Creates a New Instance Per Test Method

JUnit 5's default lifecycle is `PER_METHOD` — a new test class instance is created for each `@Test` method. This design choice ensures **test isolation**: each test starts with a clean instance state, preventing state mutations in one test from leaking into another.

If JUnit reused a single test class instance across all tests, shared instance fields mutated by one test would carry over to the next. Test ordering would matter, and tests would fail depending on which order they ran — a category of bugs called "order-dependent test failures."

The practical consequence: `@BeforeAll` and `@AfterAll` methods must be `static` by default, because they need to run before and after all instances are created/destroyed. They cannot reference `this` (no instance exists at that point).

`@TestInstance(Lifecycle.PER_CLASS)` switches to a single shared instance. This is useful when expensive setup (like starting an embedded database) should happen once for all tests. But it requires careful test isolation — `@BeforeEach` must explicitly reset mutable state that `@BeforeAll` initialized.

### Mental Model: PER_METHOD vs PER_CLASS instance lifecycle
```
PER_METHOD (default):
Test run begins
    → @BeforeAll (static): runs once
    → new TestClass() for test1 → @BeforeEach → @Test test1 → @AfterEach → GC
    → new TestClass() for test2 → @BeforeEach → @Test test2 → @AfterEach → GC
    → @AfterAll (static): runs once
    → No state leaks between test1 and test2 (different instances)

PER_CLASS:
    → @BeforeAll (can be non-static): runs once
    → @BeforeEach → @Test test1 → @AfterEach   (same instance)
    → @BeforeEach → @Test test2 → @AfterEach   (same instance)
    → @AfterAll (can be non-static): runs once
    → Shared instance state — must explicitly reset in @BeforeEach
```

### Code Example: PER_CLASS lifecycle with non-static @BeforeAll
```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseIntegrationTest {

    private EmbeddedDatabase db;

    @BeforeAll // non-static — allowed with PER_CLASS
    void startDatabase() {
        db = EmbeddedDatabaseBuilder.build(); // expensive setup — once for all tests
    }

    @BeforeEach
    void resetState() {
        db.clearAll(); // reset between tests — prevents test interference
    }

    @Test
    void testInsert() { db.insert("alice"); assertEquals(1, db.count()); }

    @Test
    void testDelete() { db.insert("bob"); db.delete("bob"); assertEquals(0, db.count()); }

    @AfterAll
    void stopDatabase() { db.shutdown(); }
}
```

### Cause-Effect Chain
JUnit default creates new instance per test &rarr; Each `@Test` starts with clean instance fields &rarr; State mutations in test1 do not affect test2 &rarr; Tests are order-independent &rarr; `@BeforeAll` must be static (no instance yet) &rarr; `@TestInstance(PER_CLASS)` switches to shared instance &rarr; One expensive setup; `@BeforeAll` can be non-static &rarr; `@BeforeEach` must reset mutable state to preserve isolation.

## Reference Links

- https://junit.org/junit5/docs/current/user-guide/ (JUnit 5 User Guide)
- https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html (Mockito API)
