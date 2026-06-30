import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * Starter template for a Secure Vault offering hashing and symmetric encryption.
 */
public class SecureVault {

    /**
     * Hashes the password and salt using SHA-256 and formats it as a hexadecimal string.
     * 
     * Requirements:
     * - Combine password and salt.
     * - Compute SHA-256 digest using MessageDigest.
     * - Convert the digest bytes to a lowercase hexadecimal String (2 digits per byte).
     *
     * @param password plain text password
     * @param salt unique random salt value
     * @return hex representation of hashed password
     */
    public static String hashPassword(String password, String salt) throws Exception {
        // TODO: Implement SHA-256 hashing
        return null;
    }

    /**
     * Encrypts the plain text using AES symmetric key.
     */
    public static byte[] encryptAES(String text, SecretKey key) throws Exception {
        // TODO: Initialize AES Cipher in ENCRYPT_MODE and encrypt text bytes
        return null;
    }

    /**
     * Decrypts the cipher text using AES symmetric key.
     */
    public static String decryptAES(byte[] encryptedData, SecretKey key) throws Exception {
        // TODO: Initialize AES Cipher in DECRYPT_MODE and decrypt bytes
        return null;
    }
}
