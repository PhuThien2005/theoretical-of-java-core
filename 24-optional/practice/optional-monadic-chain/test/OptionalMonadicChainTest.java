import java.util.Optional;

/**
 * Test runner for OptionalMonadicChain.
 */
public class OptionalMonadicChainTest {

    public static void main(String[] args) {
        try {
            testCacheHit();
            testDatabaseHit();
            testApiHit();
            testFallbackToDefault();
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

    private static void testCacheHit() {
        MockSource cache = new MockSource("cached-value");
        MockSource db = new MockSource(null);
        MockSource api = new MockSource(null);

        String result = OptionalMonadicChain.findConfig("timeout", cache, db, api);
        
        assertEquals("cached-value", result, "Find config from cache");
        assertEquals(1, cache.callCount, "Cache should be queried once");
        assertEquals(0, db.callCount, "Database should not be queried (lazy validation)");
        assertEquals(0, api.callCount, "API should not be queried");
    }

    private static void testDatabaseHit() {
        MockSource cache = new MockSource(null);
        MockSource db = new MockSource("db-value");
        MockSource api = new MockSource(null);

        String result = OptionalMonadicChain.findConfig("timeout", cache, db, api);
        
        assertEquals("db-value", result, "Find config from database");
        assertEquals(1, cache.callCount, "Cache should be queried once");
        assertEquals(1, db.callCount, "Database should be queried once");
        assertEquals(0, api.callCount, "API should not be queried (lazy validation)");
    }

    private static void testApiHit() {
        MockSource cache = new MockSource(null);
        MockSource db = new MockSource(null);
        MockSource api = new MockSource("api-value");

        String result = OptionalMonadicChain.findConfig("timeout", cache, db, api);
        
        assertEquals("api-value", result, "Find config from API");
        assertEquals(1, cache.callCount, "Cache queried");
        assertEquals(1, db.callCount, "DB queried");
        assertEquals(1, api.callCount, "API queried");
    }

    private static void testFallbackToDefault() {
        MockSource cache = new MockSource(null);
        MockSource db = new MockSource(null);
        MockSource api = new MockSource(null);

        String result = OptionalMonadicChain.findConfig("timeout", cache, db, api);
        
        assertEquals("default-config", result, "Fallback to default-config when all sources empty");
    }

    // Mock ConfigSource spy
    static class MockSource implements ConfigSource {
        private final String value;
        int callCount = 0;

        public MockSource(String value) {
            this.value = value;
        }

        @Override
        public Optional<String> find(String key) {
            callCount++;
            return Optional.ofNullable(value);
        }
    }
}
