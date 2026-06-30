# Practice Exercises: Basic Unit Testing

This folder contains hands-on practice exercises to reinforce your understanding of Java unit testing, lifecycle annotations, mock objects, and parameterized tests.

## Exercises

### 1. User Service Test (`user-service-test`)
Simulate JUnit 5 and Mockito dependency-free:
- **Test Lifecycle**: Define custom annotations `@Test`, `@BeforeEach`, `@AfterEach` and implement a reflection-based test runner that creates a new class instance per test (simulating JUnit's default lifecycle).
- **Mocking**: Test `UserService` in isolation by writing a hand-written stub (`MockUserRepository`) that simulates database operations.

#### Directory Structure
- [UserServiceTest.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/44-unit-testing/practice/user-service-test/src/UserServiceTest.java)
- [UserServiceTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/44-unit-testing/practice/user-service-test/test/UserServiceTest.java)
- [UserServiceTest.java (Solution)](file:///home/fhu_thjen/projects/learning-java/44-unit-testing/practice/user-service-test/solution/UserServiceTest.java)

---

### 2. Parameterized Custom Test (`parameterized-custom-test`)
Simulate JUnit's parameterized tests:
- **Parameterized Runs**: Implement a custom runner that executes a test method multiple times using different datasets (inputs and expected outputs) to test edge cases (like email formats or password strengths).

#### Directory Structure
- [ParameterizedCustomTest.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/44-unit-testing/practice/parameterized-custom-test/src/ParameterizedCustomTest.java)
- [ParameterizedCustomTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/44-unit-testing/practice/parameterized-custom-test/test/ParameterizedCustomTest.java)
- [ParameterizedCustomTest.java (Solution)](file:///home/fhu_thjen/projects/learning-java/44-unit-testing/practice/parameterized-custom-test/solution/ParameterizedCustomTest.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository to test your implementations:

```bash
python3 scripts/verify_exercise.py 44-unit-testing
```
