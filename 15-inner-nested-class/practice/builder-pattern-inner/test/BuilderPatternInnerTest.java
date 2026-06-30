/**
 * Test runner for BuilderPatternInner.
 */
public class BuilderPatternInnerTest {

    public static void main(String[] args) {
        try {
            testBuilderRequiredFields();
            testBuilderOptionalFields();
            testConstructorPrivacy();
            testBuilderStaticModifier();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testBuilderRequiredFields() {
        UserAccount account = new UserAccount.Builder("jsmith", "john@example.com").build();
        
        assertEquals("jsmith", account.getUsername(), "Username matches");
        assertEquals("john@example.com", account.getEmail(), "Email matches");
        assertEquals("", account.getFirstName(), "Default empty first name");
        assertEquals("", account.getLastName(), "Default empty last name");
        assertTrue(account.isActive(), "Default active should be true");

        // Test exception on null required fields
        try {
            new UserAccount.Builder(null, "email@test.com");
            throw new AssertionError("Null username should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            new UserAccount.Builder("user", null);
            throw new AssertionError("Null email should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testBuilderOptionalFields() {
        UserAccount account = new UserAccount.Builder("alice", "alice@example.com")
            .firstName("Alice")
            .lastName("Wonderland")
            .active(false)
            .build();

        assertEquals("alice", account.getUsername(), "Username matches");
        assertEquals("alice@example.com", account.getEmail(), "Email matches");
        assertEquals("Alice", account.getFirstName(), "First name matches");
        assertEquals("Wonderland", account.getLastName(), "Last name matches");
        assertTrue(!account.isActive(), "Active status matches false");
    }

    private static void testConstructorPrivacy() {
        // Enforce that UserAccount's constructor is private, preventing direct instantiation
        java.lang.reflect.Constructor<?>[] constructors = UserAccount.class.getDeclaredConstructors();
        assertTrue(constructors.length >= 1, "Must contain constructors");
        for (java.lang.reflect.Constructor<?> constructor : constructors) {
            int modifiers = constructor.getModifiers();
            assertTrue(java.lang.reflect.Modifier.isPrivate(modifiers), "UserAccount constructors must be private to enforce builder pattern!");
        }
    }

    private static void testBuilderStaticModifier() {
        // Enforce that Builder is a static nested class of UserAccount
        Class<?>[] declaredClasses = UserAccount.class.getDeclaredClasses();
        assertTrue(declaredClasses.length >= 1, "Must declare nested Builder class");
        
        boolean foundBuilderClass = false;
        for (Class<?> clazz : declaredClasses) {
            if (clazz.getSimpleName().equals("Builder")) {
                foundBuilderClass = true;
                int modifiers = clazz.getModifiers();
                assertTrue(java.lang.reflect.Modifier.isStatic(modifiers), "Builder must be a static nested class (declare it public static class Builder)!");
            }
        }
        assertTrue(foundBuilderClass, "Could not find Builder nested class");
    }
}
