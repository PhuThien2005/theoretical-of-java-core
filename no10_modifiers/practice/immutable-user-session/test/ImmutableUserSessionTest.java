package no10_modifiers.practice.immutable_user_session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test runner for ImmutableUserSession.
 */
public class ImmutableUserSessionTest {

    public static void main(String[] args) {
        try {
            testImmutabilityModifiers();
            testDefensiveCopying();
            testUnmodifiableListGetter();
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

    private static void assertEquals(String expected, String actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testImmutabilityModifiers() {
        // Class must be final
        int classModifiers = ImmutableUserSession.class.getModifiers();
        assertTrue(java.lang.reflect.Modifier.isFinal(classModifiers), "ImmutableUserSession class must be declared final!");

        // All fields must be final
        java.lang.reflect.Field[] fields = ImmutableUserSession.class.getDeclaredFields();
        assertTrue(fields.length >= 3, "Must contain all session fields");
        for (java.lang.reflect.Field field : fields) {
            if (field.isSynthetic()) continue;
            
            int fieldModifiers = field.getModifiers();
            assertTrue(java.lang.reflect.Modifier.isPrivate(fieldModifiers), "Field " + field.getName() + " must be private");
            assertTrue(java.lang.reflect.Modifier.isFinal(fieldModifiers), "Field " + field.getName() + " must be final");
        }
    }

    private static void testDefensiveCopying() {
        List<String> permissions = new ArrayList<>(Arrays.asList("READ", "WRITE"));
        ImmutableUserSession session = new ImmutableUserSession("S001", "alice", permissions);

        // Modify input list
        permissions.add("ADMIN");

        // The session's internal list should NOT change
        List<String> sessionPerms = session.getPermissions();
        assertTrue(!sessionPerms.contains("ADMIN"), "Session must defensively copy the input list to prevent external modification!");
        assertEquals(2, sessionPerms.size(), "Permissions size must remain 2");
    }

    private static void testUnmodifiableListGetter() {
        ImmutableUserSession session = new ImmutableUserSession("S001", "alice", Arrays.asList("READ"));

        // Attempting to modify the returned list should throw UnsupportedOperationException
        try {
            session.getPermissions().add("WRITE");
            throw new AssertionError("Modifying the returned list must throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // Expected
        }
    }
}
