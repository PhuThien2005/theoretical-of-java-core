package no36_security_basic.practice.secure_vault;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * Reference solution for SecureVaultSolution.
 * 
 * Cryptography tools:
 * - `MessageDigest` represents secure one-way hash functions. SHA-256 creates a fixed-size 256-bit hash.
 * - Salts prevent pre-computed dictionary/rainbow table attacks.
 * - `Cipher` implements symmetric encryption. AES requires a matching SecretKey for both encrypt and decrypt operations.
 */
public class SecureVaultSolution {

    public static String hashPassword(String password, String salt) throws Exception {
        if (password == null || salt == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }

        // Combine salt and password
        String input = salt + password;
        
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

        // Format byte array to lowercase hexadecimal
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static byte[] encryptAES(String text, SecretKey key) throws Exception {
        if (text == null || key == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
    }

    public static String decryptAES(byte[] encryptedData, SecretKey key) throws Exception {
        if (encryptedData == null || key == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(encryptedData);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
