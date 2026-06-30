import java.util.HashMap;
import java.util.Map;

/**
 * Test runner for ConsistentHashKey.
 */
public class ConsistentHashKeyTest {

    public static void main(String[] args) {
        try {
            testEqualsContract();
            testHashCodeContract();
            testHashMapLookup();
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

    private static void testEqualsContract() {
        ConsistentHashKey k1 = new ConsistentHashKey(1, "User");
        ConsistentHashKey k2 = new ConsistentHashKey(1, "User");
        ConsistentHashKey k3 = new ConsistentHashKey(2, "User");
        ConsistentHashKey k4 = new ConsistentHashKey(1, "Admin");

        // Reflexive
        assertTrue(k1.equals(k1), "Reflexive property: k1.equals(k1)");

        // Symmetric
        assertTrue(k1.equals(k2), "k1.equals(k2)");
        assertTrue(k2.equals(k1), "Symmetric property: k2.equals(k1)");

        // Inequality checks
        assertTrue(!k1.equals(k3), "Different IDs must not be equal");
        assertTrue(!k1.equals(k4), "Different categories must not be equal");
        assertTrue(!k1.equals(null), "Comparison with null must return false");
        assertTrue(!k1.equals("NotAKey"), "Comparison with different type must return false");
    }

    private static void testHashCodeContract() {
        ConsistentHashKey k1 = new ConsistentHashKey(1, "User");
        ConsistentHashKey k2 = new ConsistentHashKey(1, "User");

        if (k1.equals(k2)) {
            assertTrue(k1.hashCode() == k2.hashCode(), "HashCode contract: equal objects must have identical hash codes");
        }
    }

    private static void testHashMapLookup() {
        Map<ConsistentHashKey, String> map = new HashMap<>();
        
        ConsistentHashKey keyPut = new ConsistentHashKey(101, "Reports");
        map.put(keyPut, "DatabaseReport");

        // Lookup with an identical but separate key instance
        ConsistentHashKey keyLookup = new ConsistentHashKey(101, "Reports");
        
        assertEquals("DatabaseReport", map.get(keyLookup), "HashMap get should retrieve value when lookup key is equal to put key");
    }
}
