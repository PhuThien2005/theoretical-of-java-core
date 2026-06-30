# Exercise: User Service Test

## Objective
Implement a dependency-free unit testing framework and mock test suite for a `UserService`.

## Problem Description
To test unit components without external library dependencies, we will build a micro JUnit-like reflection test runner. This will demonstrate how annotation processing and test class instantiations work.

## Requirements
1. **Annotations**:
   - `@Retention(RetentionPolicy.RUNTIME)` annotations: `@MyTest`, `@MyBeforeEach`, `@MyAfterEach`.

2. **Core Domain**:
   - `User(String name, String email)`.
   - Interface `UserRepository` with `boolean existsByEmail(String email)` and `User save(User user)`.
   - `UserService(UserRepository repo)` with method `User registerUser(String name, String email)`:
     - Throws `IllegalArgumentException` if email is null, empty, or doesn't contain `@`.
     - Throws `IllegalStateException` if `existsByEmail(email)` is true.
     - Saves and returns the registered user.

3. **Mocks/Stubs**:
   - Implement `MockUserRepository implements UserRepository` that allows stubbing (e.g. configuring whether `existsByEmail` returns true or false, and tracking saved users).

4. **Test Suite**:
   - Create a test class `UserServiceTest` containing test methods marked with `@MyTest`, and setup/teardown methods marked with `@MyBeforeEach` / `@MyAfterEach`.
   - Run these test cases dynamically using a `main` method that scans the test class using reflection, executes `@MyBeforeEach`, then `@MyTest` (on a *new* instance of the class to prevent state pollution), and finally `@MyAfterEach`.
