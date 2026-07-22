package no24_optional.practice.null_safety_refactoring;

/**
 * Test runner for NullSafetyRefactoring.
 */
public class NullSafetyRefactoringTest {

    public static void main(String[] args) {
        try {
            testFullUserGraph();
            testNullUser();
            testNullProfile();
            testNullContact();
            testNullEmail();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: [" + expected + "], Actual: [" + actual + "])");
        }
    }

    private static void testFullUserGraph() {
        ContactInfo info = new ContactInfo("alice@test.com");
        Profile profile = new Profile(info);
        User user = new User(profile);

        assertEquals("alice@test.com", NullSafetyRefactoring.getEmailOrDefault(user), "Valid full graph lookup");
    }

    private static void testNullUser() {
        assertEquals("default@example.com", NullSafetyRefactoring.getEmailOrDefault(null), "Null user fallback");
    }

    private static void testNullProfile() {
        User user = new User(null);
        assertEquals("default@example.com", NullSafetyRefactoring.getEmailOrDefault(user), "Null profile fallback");
    }

    private static void testNullContact() {
        Profile profile = new Profile(null);
        User user = new User(profile);
        assertEquals("default@example.com", NullSafetyRefactoring.getEmailOrDefault(user), "Null contact fallback");
    }

    private static void testNullEmail() {
        ContactInfo info = new ContactInfo(null);
        Profile profile = new Profile(info);
        User user = new User(profile);
        assertEquals("default@example.com", NullSafetyRefactoring.getEmailOrDefault(user), "Null email fallback");
    }
}
