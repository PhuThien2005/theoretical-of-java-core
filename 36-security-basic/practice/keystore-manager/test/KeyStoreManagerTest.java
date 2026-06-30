import java.security.KeyStore;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Test runner for KeyStoreManager.
 */
public class KeyStoreManagerTest {

    public static void main(String[] args) {
        try {
            testKeyStoreStorageAndRetrieval();
            testKeyStoreInvalidPasswordRetrieval();
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

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testKeyStoreStorageAndRetrieval() throws Exception {
        char[] ksPassword = "keystore-pass-123".toCharArray();
        char[] keyPassword = "secret-key-pass".toCharArray();

        // Create empty KeyStore
        KeyStore ks = KeyStoreManager.createEmptyKeyStore(ksPassword);
        assertEquals("PKCS12", ks.getType(), "Verify KeyStore format is PKCS12");

        // Generate temporary AES key
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey originalKey = keyGen.generateKey();

        // Store key
        KeyStoreManager.storeKey(ks, "my-aes-key", originalKey, keyPassword);

        // Retrieve key
        SecretKey restoredKey = KeyStoreManager.retrieveKey(ks, "my-aes-key", keyPassword);
        assertEquals(originalKey, restoredKey, "Restored key must match original key exactly");
    }

    private static void testKeyStoreInvalidPasswordRetrieval() throws Exception {
        char[] ksPassword = "keystore-pass-123".toCharArray();
        char[] keyPassword = "secret-key-pass".toCharArray();
        char[] wrongPassword = "wrong-password".toCharArray();

        KeyStore ks = KeyStoreManager.createEmptyKeyStore(ksPassword);

        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey originalKey = keyGen.generateKey();

        KeyStoreManager.storeKey(ks, "my-aes-key", originalKey, keyPassword);

        // Retrieve with wrong password should fail with an exception
        try {
            KeyStoreManager.retrieveKey(ks, "my-aes-key", wrongPassword);
            throw new AssertionError("Retrieving key with wrong password must throw an exception");
        } catch (Exception e) {
            // Expected (e.g. UnrecoverableKeyException or UnrecoverableEntryException)
        }
    }
}
