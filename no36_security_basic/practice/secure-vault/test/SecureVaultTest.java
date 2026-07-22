package no36_security_basic.practice.secure_vault;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Test runner for SecureVault.
 */
public class SecureVaultTest {

    public static void main(String[] args) {
        try {
            testPasswordHashing();
            testAesEncryptionDecryption();
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

    private static void testPasswordHashing() throws Exception {
        String p1 = "mypassword123";
        String salt = "random_salt_value";

        String hash1 = SecureVault.hashPassword(p1, salt);

        // SHA-256 hash must be 64 characters long in hexadecimal format
        assertEquals(64, hash1.length(), "SHA-256 hex string length");
        
        // Assert lowercase
        assertEquals(hash1.toLowerCase(), hash1, "Hex string should be lowercase");

        // Verify different salts produce different hashes for same password (salting check)
        String hash2 = SecureVault.hashPassword(p1, "another_salt");
        assertTrue(!hash1.equals(hash2), "Salts must modify hashes to prevent rainbow table matches");

        // Verify different passwords produce different hashes
        String hash3 = SecureVault.hashPassword("otherpass", salt);
        assertTrue(!hash1.equals(hash3), "Different passwords must produce different hashes");
    }

    private static void testAesEncryptionDecryption() throws Exception {
        // Generate a new temporary AES key
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // 128-bit AES
        SecretKey key = keyGen.generateKey();

        String secretText = "Core Java Security and Cryptography features!";

        // Encrypt
        byte[] cipherText = SecureVault.encryptAES(secretText, key);
        assertTrue(cipherText != null && cipherText.length > 0, "Ciphertext must be generated");

        // Decrypt
        String plainText = SecureVault.decryptAES(cipherText, key);
        assertEquals(secretText, plainText, "Decrypted message must match original input text");
    }
}
