/**
 * Test runner for LruCache.
 */
public class LruCacheTest {

    public static void main(String[] args) {
        try {
            testBasicPutAndGet();
            testEvictionOrder();
            testAccessOrderUpdatesRecency();
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
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testBasicPutAndGet() {
        LruCache<String, Integer> cache = new LruCache<>(5);
        cache.put("one", 1);
        cache.put("two", 2);

        assertEquals(1, cache.get("one"), "Get one");
        assertEquals(2, cache.get("two"), "Get two");
        assertEquals(2, cache.size(), "Size is 2");
    }

    private static void testEvictionOrder() {
        // Cache of size 2
        LruCache<Integer, String> cache = new LruCache<>(2);
        cache.put(1, "A");
        cache.put(2, "B");
        
        // Add third element, 1 (eldest) should be evicted
        cache.put(3, "C");

        assertEquals(null, cache.get(1), "Key 1 should be evicted");
        assertEquals("B", cache.get(2), "Key 2 should still be cached");
        assertEquals("C", cache.get(3), "Key 3 should be cached");
        assertEquals(2, cache.size(), "Size remains capped at 2");
    }

    private static void testAccessOrderUpdatesRecency() {
        LruCache<Integer, String> cache = new LruCache<>(2);
        cache.put(1, "A");
        cache.put(2, "B");

        // Access key 1 (makes 2 the eldest/least recently accessed element)
        cache.get(1);

        // Put key 3 (triggers eviction of 2 instead of 1)
        cache.put(3, "C");

        assertEquals("A", cache.get(1), "Key 1 should still be cached (accessed recently)");
        assertEquals(null, cache.get(2), "Key 2 should be evicted (least recently accessed)");
        assertEquals("C", cache.get(3), "Key 3 should be cached");
    }
}
