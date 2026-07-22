package no11_package_access_control.practice.cross_package_visibility_test;

import alpha.ParentClass;

/**
 * Test runner to verify access visibility and field modifiers.
 */
public class VisibilityTest {

    public static void main(String[] args) {
        try {
            testChildVisibility();
            testNeighborVisibility();
            testFieldModifiers();
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

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testChildVisibility() {
        String result = VisibilityTester.getChildFields();
        assertEquals("public-value-protected-value", result, "Child in different package should access public and protected fields");
    }

    private static void testNeighborVisibility() {
        String result = VisibilityTester.getNeighborFields();
        assertEquals("public-value-protected-value-default-value", result, "Neighbor in same package should access public, protected, and default fields");
    }

    private static void testFieldModifiers() {
        try {
            // Check publicField
            int publicMod = ParentClass.class.getDeclaredField("publicField").getModifiers();
            assertTrue(java.lang.reflect.Modifier.isPublic(publicMod), "publicField must be public");

            // Check protectedField
            int protectedMod = ParentClass.class.getDeclaredField("protectedField").getModifiers();
            assertTrue(java.lang.reflect.Modifier.isProtected(protectedMod), "protectedField must be protected");

            // Check defaultField (should not be public, protected, or private)
            int defaultMod = ParentClass.class.getDeclaredField("defaultField").getModifiers();
            assertTrue(!java.lang.reflect.Modifier.isPublic(defaultMod), "defaultField must not be public");
            assertTrue(!java.lang.reflect.Modifier.isProtected(defaultMod), "defaultField must not be protected");
            assertTrue(!java.lang.reflect.Modifier.isPrivate(defaultMod), "defaultField must not be private");

            // Check privateField
            int privateMod = ParentClass.class.getDeclaredField("privateField").getModifiers();
            assertTrue(java.lang.reflect.Modifier.isPrivate(privateMod), "privateField must be private");

        } catch (NoSuchFieldException e) {
            throw new AssertionError("Missing field in ParentClass", e);
        }
    }
}
