import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Test runner for CustomIterableStack.
 */
public class CustomIterableStackTest {

    public static void main(String[] args) {
        try {
            testStackOperations();
            testIteratorTraversal();
            testIteratorExceptions();
            testInnerClassModifiers();
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

    private static void testStackOperations() {
        CustomIterableStack<Integer> stack = new CustomIterableStack<>();
        assertTrue(stack.isEmpty(), "Stack should be empty initially");
        
        stack.push(10);
        stack.push(20);
        assertEquals(2, stack.size(), "Stack size should be 2");
        
        assertEquals(20, stack.pop(), "Pop should return top element (20)");
        assertEquals(10, stack.pop(), "Pop should return next element (10)");
        assertTrue(stack.isEmpty(), "Stack should be empty again");
    }

    private static void testIteratorTraversal() {
        CustomIterableStack<String> stack = new CustomIterableStack<>();
        stack.push("A");
        stack.push("B");
        stack.push("C");

        // Expected LIFO iteration order: C -> B -> A
        Iterator<String> it = stack.iterator();
        
        assertTrue(it.hasNext(), "Should have first item");
        assertEquals("C", it.next(), "First item should be C");
        
        assertTrue(it.hasNext(), "Should have second item");
        assertEquals("B", it.next(), "Second item should be B");
        
        assertTrue(it.hasNext(), "Should have third item");
        assertEquals("A", it.next(), "Third item should be A");
        
        assertTrue(!it.hasNext(), "Should have no more elements");
    }

    private static void testIteratorExceptions() {
        CustomIterableStack<Integer> stack = new CustomIterableStack<>();
        stack.push(42);
        
        Iterator<Integer> it = stack.iterator();
        assertEquals(42, it.next(), "Get only element");

        try {
            it.next();
            throw new AssertionError("Depleted iterator should throw NoSuchElementException on next()");
        } catch (NoSuchElementException e) {
            // Expected
        }
    }

    private static void testInnerClassModifiers() {
        // Enforce that StackIterator is a non-static inner class of CustomIterableStack
        Class<?>[] declaredClasses = CustomIterableStack.class.getDeclaredClasses();
        assertTrue(declaredClasses.length >= 1, "Must declare the inner StackIterator class");
        
        boolean foundInnerClass = false;
        for (Class<?> clazz : declaredClasses) {
            if (clazz.getSimpleName().equals("StackIterator")) {
                foundInnerClass = true;
                int modifiers = clazz.getModifiers();
                assertTrue(!java.lang.reflect.Modifier.isStatic(modifiers), "StackIterator must be a non-static inner class (do NOT declare it static)!");
            }
        }
        assertTrue(foundInnerClass, "Could not find StackIterator inner class");
    }
}
