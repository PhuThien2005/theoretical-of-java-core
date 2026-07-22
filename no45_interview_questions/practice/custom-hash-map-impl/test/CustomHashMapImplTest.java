package customhashmapimpl;

public class CustomHashMapImplTest {

    public static void main(String[] args) {
        try {
            testBasicPutAndGet();
            testKeyUpdate();
            testNullKeySupport();
            testCollisionChaining();
            testRemoveOperations();
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
            throw new AssertionError(message + " - Expected: [" + expected + "], Actual: [" + actual + "]");
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testBasicPutAndGet() {
        var map = new CustomHashMapImpl<String, Integer>();
        assertEquals(0, map.size(), "Initial map size should be 0");

        map.put("One", 1);
        map.put("Two", 2);

        assertEquals(2, map.size(), "Map size should be 2 after insertions");
        assertEquals(1, map.get("One"), "Get 'One' failed");
        assertEquals(2, map.get("Two"), "Get 'Two' failed");
        assertEquals(null, map.get("Three"), "Get non-existent key should return null");
    }

    private static void testKeyUpdate() {
        var map = new CustomHashMapImpl<String, String>();
        map.put("key", "value1");
        map.put("key", "value2");

        assertEquals(1, map.size(), "Map size should not increase on key overwrite");
        assertEquals("value2", map.get("key"), "Value should be overwritten");
    }

    private static void testNullKeySupport() {
        var map = new CustomHashMapImpl<String, Integer>();
        map.put(null, 100);
        assertEquals(1, map.size(), "Map size should be 1 with null key");
        assertEquals(100, map.get(null), "Get null key failed");

        map.put(null, 200);
        assertEquals(200, map.get(null), "Overwriting null key value failed");
    }

    private static void testCollisionChaining() {
        var map = new CustomHashMapImpl<Integer, String>();
        
        // With default capacity 16, Integer keys 1, 17, and 33 will all hash to bucket index 1.
        // This forces linked list chaining/collisions.
        map.put(1, "Value1");
        map.put(17, "Value17");
        map.put(33, "Value33");

        assertEquals(3, map.size(), "Size mismatch with collided keys");
        assertEquals("Value1", map.get(1), "Get failed for key 1");
        assertEquals("Value17", map.get(17), "Get failed for key 17");
        assertEquals("Value33", map.get(33), "Get failed for key 33");
    }

    private static void testRemoveOperations() {
        var map = new CustomHashMapImpl<Integer, String>();
        map.put(1, "Value1");
        map.put(17, "Value17");
        map.put(33, "Value33");

        // Remove from middle of collision chain (key 17)
        String removedMiddle = map.remove(17);
        assertEquals("Value17", removedMiddle, "Remove middle key returned incorrect value");
        assertEquals(2, map.size(), "Size mismatch after removal");
        assertEquals(null, map.get(17), "Removed key should not be retrievable");
        assertEquals("Value1", map.get(1), "Other keys in chain should remain intact");
        assertEquals("Value33", map.get(33), "Other keys in chain should remain intact");

        // Remove from head of collision chain (key 33)
        String removedHead = map.remove(33);
        assertEquals("Value33", removedHead, "Remove head key returned incorrect value");
        assertEquals(1, map.size(), "Size mismatch after removal");
        assertEquals(null, map.get(33), "Removed key should not be retrievable");
        assertEquals("Value1", map.get(1), "Remaining keys should be retrievable");

        // Remove non-existent key
        assertEquals(null, map.remove(99), "Removing non-existent key should return null");
    }
}
