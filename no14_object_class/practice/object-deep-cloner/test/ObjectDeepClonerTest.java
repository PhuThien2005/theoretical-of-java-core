package no14_object_class.practice.object_deep_cloner;

/**
 * Test runner for ObjectDeepCloner.
 */
public class ObjectDeepClonerTest {

    public static void main(String[] args) {
        try {
            testShallowFieldCopying();
            testDeepCopyAddressReference();
            testDeepCopyArrayReference();
            testIsolationOfModifications();
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

    private static void testShallowFieldCopying() throws CloneNotSupportedException {
        Address address = new Address("123 Main St", "Seattle");
        String[] skills = {"Java", "SQL"};
        User u1 = new User("Alice", address, skills);

        User u2 = (User) u1.clone();
        
        assertEquals(u1.getName(), u2.getName(), "Name should be copied");
        assertEquals(u1.getAddress().getStreet(), u2.getAddress().getStreet(), "Address street should match");
        assertEquals(u1.getSkills()[0], u2.getSkills()[0], "Skills should match");
    }

    private static void testDeepCopyAddressReference() throws CloneNotSupportedException {
        Address address = new Address("123 Main St", "Seattle");
        User u1 = new User("Alice", address, new String[]{"Java"});

        User u2 = (User) u1.clone();

        // References must be different
        assertTrue(u1 != u2, "Cloned User reference must be different");
        assertTrue(u1.getAddress() != u2.getAddress(), "Cloned Address reference must be different (Deep Copy)");
    }

    private static void testDeepCopyArrayReference() throws CloneNotSupportedException {
        User u1 = new User("Alice", new Address("123 Main St", "Seattle"), new String[]{"Java"});

        User u2 = (User) u1.clone();

        assertTrue(u1.getSkills() != u2.getSkills(), "Cloned skills array reference must be different (Deep Copy)");
    }

    private static void testIsolationOfModifications() throws CloneNotSupportedException {
        Address address = new Address("123 Main St", "Seattle");
        String[] skills = {"Java", "SQL"};
        User u1 = new User("Alice", address, skills);

        User u2 = (User) u1.clone();

        // Mutate cloned fields
        u2.getAddress().setStreet("456 Oak Rd");
        u2.getSkills()[0] = "Python";

        // Assert original fields did NOT change
        assertEquals("123 Main St", u1.getAddress().getStreet(), "Original street must remain unmodified after clone mutation");
        assertEquals("Java", u1.getSkills()[0], "Original skills array must remain unmodified after clone mutation");
    }
}
