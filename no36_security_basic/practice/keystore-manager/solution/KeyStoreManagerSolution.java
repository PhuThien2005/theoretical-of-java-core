package no36_security_basic.practice.keystore_manager;

import java.security.KeyStore;
import javax.crypto.SecretKey;

/**
 * Reference solution for KeyStoreManagerSolution.
 * 
 * KeyStore security concepts:
 * - A PKCS12 KeyStore is the modern standard for storing cryptographic keys and certificates.
 * - `load(null, password)` initializes a new in-memory KeyStore.
 * - Secret keys are wrapped inside a `SecretKeyEntry`.
 * - Access to individual entries is protected by `PasswordProtection` credentials.
 */
public class KeyStoreManagerSolution {

    public static KeyStore createEmptyKeyStore(char[] password) throws Exception {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        
        // Instantiate PKCS12 keystore
        KeyStore ks = KeyStore.getInstance("PKCS12");
        
        // Load with null input stream to create empty keystore
        ks.load(null, password);
        
        return ks;
    }

    public static void storeKey(KeyStore ks, String alias, SecretKey key, char[] password) throws Exception {
        if (ks == null || alias == null || key == null || password == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        // Wrap secret key into key entry
        KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(key);
        
        // Protection parameter using entry password
        KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(password);
        
        // Save entry inside KeyStore
        ks.setEntry(alias, entry, protParam);
    }

    public static SecretKey retrieveKey(KeyStore ks, String alias, char[] password) throws Exception {
        if (ks == null || alias == null || password == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        // Entry protection password
        KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(password);
        
        // Retrieve and unwrap SecretKeyEntry
        KeyStore.Entry entry = ks.getEntry(alias, protParam);
        if (!(entry instanceof KeyStore.SecretKeyEntry)) {
            return null;
        }

        return ((KeyStore.SecretKeyEntry) entry).getSecretKey();
    }
}
