package userservicetest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import userservicetest.UserService.*;

public class UserServiceTest {

    // Hand-written Mock to simulate Mockito-like stubbing without external dependencies.
    private static class MockUserRepository implements UserRepository {
        private final Map<String, Boolean> existingEmails = new HashMap<>();
        private final List<User> savedUsers = new ArrayList<>();

        public void stubExistsByEmail(String email, boolean exists) {
            existingEmails.put(email, exists);
        }

        @Override
        public boolean existsByEmail(String email) {
            return existingEmails.getOrDefault(email, false);
        }

        @Override
        public User save(User user) {
            savedUsers.add(user);
            return user;
        }

        public List<User> getSavedUsers() {
            return savedUsers;
        }
    }

    private MockUserRepository repo;
    private UserService service;
    private static int beforeEachCount = 0;
    private static int afterEachCount = 0;

    @MyBeforeEach
    public void setUp() {
        repo = new MockUserRepository();
        service = new UserService(repo);
        beforeEachCount++;
    }

    @MyAfterEach
    public void tearDown() {
        repo = null;
        service = null;
        afterEachCount++;
    }

    @MyTest
    public void testRegisterUserSuccess() {
        repo.stubExistsByEmail("test@example.com", false);
        User registered = service.registerUser("Alice", "test@example.com");

        if (registered == null) {
            throw new AssertionError("Registered user should not be null");
        }
        if (!"Alice".equals(registered.name())) {
            throw new AssertionError("Registered user name mismatch");
        }
        if (repo.getSavedUsers().size() != 1) {
            throw new AssertionError("Repository should save exactly one user");
        }
    }

    @MyTest
    public void testRegisterUserAlreadyExists() {
        repo.stubExistsByEmail("duplicate@example.com", true);
        try {
            service.registerUser("Bob", "duplicate@example.com");
            throw new AssertionError("Expected IllegalStateException for duplicate email");
        } catch (IllegalStateException e) {
            // Success
            if (!e.getMessage().contains("already exists")) {
                throw new AssertionError("Incorrect exception message");
            }
        }
    }

    @MyTest
    public void testRegisterUserInvalidEmail() {
        try {
            service.registerUser("Charlie", "invalid-email");
            throw new AssertionError("Expected IllegalArgumentException for invalid email");
        } catch (IllegalArgumentException e) {
            // Success
        }
    }

    // Micro JUnit-like reflection-based test runner.
    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        try {
            Class<?> testClass = UserServiceTest.class;

            List<Method> beforeMethods = new ArrayList<>();
            List<Method> afterMethods = new ArrayList<>();
            List<Method> testMethods = new ArrayList<>();

            for (Method m : testClass.getDeclaredMethods()) {
                if (m.isAnnotationPresent(MyBeforeEach.class)) {
                    beforeMethods.add(m);
                } else if (m.isAnnotationPresent(MyAfterEach.class)) {
                    afterMethods.add(m);
                } else if (m.isAnnotationPresent(MyTest.class)) {
                    testMethods.add(m);
                }
            }

            for (Method testMethod : testMethods) {
                // Instantiating a NEW instance per test method simulates the default PER_METHOD lifecycle of JUnit 5.
                Object testInstance = testClass.getDeclaredConstructor().newInstance();

                try {
                    // Run setup methods
                    for (Method before : beforeMethods) {
                        before.invoke(testInstance);
                    }

                    // Run the actual test
                    testMethod.invoke(testInstance);

                    passed++;
                } catch (Throwable t) {
                    System.err.println("❌ Test " + testMethod.getName() + " failed!");
                    // Unwrap reflection TargetInvocationException to see actual assertion failure
                    if (t.getCause() != null) {
                        t.getCause().printStackTrace();
                    } else {
                        t.printStackTrace();
                    }
                    failed++;
                } finally {
                    // Run teardown methods
                    for (Method after : afterMethods) {
                        try {
                            after.invoke(testInstance);
                        } catch (Throwable t) {
                            System.err.println("Exception in @MyAfterEach: " + t.getMessage());
                        }
                    }
                }
            }

            // Assert that lifecycle methods were executed correctly
            if (beforeEachCount != testMethods.size()) {
                throw new AssertionError("SetUp count should match number of test runs");
            }
            if (afterEachCount != testMethods.size()) {
                throw new AssertionError("TearDown count should match number of test runs");
            }

            if (failed > 0) {
                System.err.println("❌ " + failed + " tests failed!");
                System.exit(1);
            } else {
                System.out.println("✅ All " + passed + " tests passed successfully!");
                System.exit(0);
            }

        } catch (Throwable t) {
            System.err.println("❌ Runner Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }
}
