package no10_modifiers.practice.thread_safe_singleton;

/**
 * Test runner for ThreadSafeSingleton.
 */
public class ThreadSafeSingletonTest {

    public static void main(String[] args) {
        try {
            testSingletonInstantiation();
            testConstructorPrivacy();
            testCounterState();
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

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testSingletonInstantiation() {
        ThreadSafeSingleton s1 = ThreadSafeSingleton.getInstance();
        ThreadSafeSingleton s2 = ThreadSafeSingleton.getInstance();

        assertTrue(s1 != null, "Instance s1 should not be null");
        assertTrue(s1 == s2, "Both calls to getInstance() must return the exact same object reference");
    }

    private static void testConstructorPrivacy() {
        // Enforce constructor privacy via reflection
        java.lang.reflect.Constructor<?>[] constructors = ThreadSafeSingleton.class.getDeclaredConstructors();
        assertTrue(constructors.length >= 1, "Must have at least one constructor");
        
        // Find if there is any public constructor
        for (java.lang.reflect.Constructor<?> constructor : constructors) {
            int modifiers = constructor.getModifiers();
            assertTrue(java.lang.reflect.Modifier.isPrivate(modifiers), "All constructors must be private to prevent external instantiation!");
        }
    }

    private static void testCounterState() {
        ThreadSafeSingleton s1 = ThreadSafeSingleton.getInstance();
        ThreadSafeSingleton s2 = ThreadSafeSingleton.getInstance();

        // Increment on s1, check state on s2
        s1.increment();
        s1.increment();
        assertEquals(2, s2.getCounter(), "Counter updates on one reference must reflect in the other reference");
    }
}
