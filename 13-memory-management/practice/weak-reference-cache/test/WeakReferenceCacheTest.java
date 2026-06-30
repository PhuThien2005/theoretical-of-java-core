/**
 * Test runner for WeakReferenceCache.
 */
public class WeakReferenceCacheTest {

    public static void main(String[] args) {
        try {
            testCachePutAndGet();
            testWeakCollection();
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

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testCachePutAndGet() {
        WeakReferenceCache<String, String> cache = new WeakReferenceCache<>();
        
        String val1 = new String("Value1");
        cache.put("k1", val1);
        
        // Assert it is retrieved successfully
        assertEquals(val1, cache.get("k1"), "Value should be cached and retrievable");
        assertEquals(1, cache.size(), "Cache size should be 1");
    }

    private static void testWeakCollection() {
        WeakReferenceCache<String, Object> cache = new WeakReferenceCache<>();
        
        // We create an object strongly referenced only by our local variable `tempObj`
        Object tempObj = new Object();
        cache.put("leakKey", tempObj);
        
        // Confirm it is in cache
        assertTrue(cache.get("leakKey") == tempObj, "Should retrieve object while strong reference exists");

        // Clear the strong reference
        tempObj = null;

        // Force GC to run by requesting it and allocating small temporary objects
        for (int i = 0; i < 10; i++) {
            System.gc();
            // Allocate a small 1MB array to apply memory pressure and trigger collection
            byte[] triggerGcMemory = new byte[1024 * 1024];
            if (cache.get("leakKey") == null) {
                break; // Successfully collected!
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // Ignore
            }
        }

        // Assert that the object has been garbage collected and cache.get() returns null
        assertTrue(cache.get("leakKey") == null, "Cached value must be garbage collected after its strong reference is discarded");
    }
}
