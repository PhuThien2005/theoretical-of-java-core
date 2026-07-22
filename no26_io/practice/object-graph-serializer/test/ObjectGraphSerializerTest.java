package no26_io.practice.object_graph_serializer;

/**
 * Test runner for ObjectGraphSerializer.
 */
public class ObjectGraphSerializerTest {

    public static void main(String[] args) {
        try {
            testSerializationOfTransientFields();
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

    private static void assertEquals(long expected, long actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testSerializationOfTransientFields() throws Exception {
        UserSession original = new UserSession("alice", "sess-12345", "token-secret-xyz", 1719600000000L);

        // Serialize
        byte[] serializedBytes = ObjectGraphSerializer.serialize(original);

        // Deserialize
        UserSession restored = (UserSession) ObjectGraphSerializer.deserialize(serializedBytes);

        // Verify standard fields are restored
        assertEquals("alice", restored.getUsername(), "Username is preserved");
        assertEquals("sess-12345", restored.getSessionId(), "Session ID is preserved");

        // Verify transient fields are NOT restored (reset to default values)
        assertEquals(null, restored.getSecurityToken(), "Transient securityToken must be null");
        assertEquals(0L, restored.getLoginTimeMillis(), "Transient loginTimeMillis must be 0");
    }
}
