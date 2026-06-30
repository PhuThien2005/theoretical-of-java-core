/**
 * Test runner for PrivateFieldMutator.
 */
public class PrivateFieldMutatorTest {

    public static void main(String[] args) {
        try {
            testReadPrivateField();
            testWritePrivateField();
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

    private static void testReadPrivateField() throws Exception {
        SecretContainer sc = new SecretContainer("my-secret-key");
        
        // Read private field
        Object val = PrivateFieldMutator.getPrivateField(sc, "secret");
        
        assertEquals("my-secret-key", val, "Verify private field reading");
    }

    private static void testWritePrivateField() throws Exception {
        SecretContainer sc = new SecretContainer("old-key");

        // Write private field
        PrivateFieldMutator.setPrivateField(sc, "secret", "new-key-123");

        // Read and verify changes
        Object val = PrivateFieldMutator.getPrivateField(sc, "secret");
        assertEquals("new-key-123", val, "Verify private field writing");
    }

    // Test class containing completely encapsulated data
    static class SecretContainer {
        private String secret;

        public SecretContainer(String secret) {
            this.secret = secret;
        }
        
        // NO getter, NO setter
    }
}
