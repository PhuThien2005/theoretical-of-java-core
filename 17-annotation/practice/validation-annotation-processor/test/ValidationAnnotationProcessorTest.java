/**
 * Test runner for ValidationAnnotationProcessor.
 */
public class ValidationAnnotationProcessorTest {

    public static void main(String[] args) {
        try {
            testValidObject();
            testNotNullViolation();
            testMinViolation();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(String expected, String actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + "\nExpected: [" + expected + "]\nActual:   [" + actual + "]");
        }
    }

    private static void testValidObject() throws Exception {
        User u = new User("Alice", 18);
        // Should compile and run without throwing any exception
        ValidationAnnotationProcessor.validate(u);
    }

    private static void testNotNullViolation() throws Exception {
        User u = new User(null, 25);
        try {
            ValidationAnnotationProcessor.validate(u);
            throw new AssertionError("Should throw IllegalArgumentException on null username");
        } catch (IllegalArgumentException e) {
            assertEquals("username cannot be null", e.getMessage(), "NotNull exception message");
        }
    }

    private static void testMinViolation() throws Exception {
        User u = new User("Bob", 17);
        try {
            ValidationAnnotationProcessor.validate(u);
            throw new AssertionError("Should throw IllegalArgumentException on age < 18");
        } catch (IllegalArgumentException e) {
            assertEquals("age must be at least 18", e.getMessage(), "Min exception message");
        }
    }

    // Test class
    static class User {
        @NotNull
        private String username;

        @Min(18)
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }
}
