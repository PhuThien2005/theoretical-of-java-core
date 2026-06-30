import java.security.KeyStore;
import javax.crypto.SecretKey;

/**
 * Starter template for managing SecretKeys inside Java KeyStore containers.
 */
public class KeyStoreManager {

    /**
     * Initializes and loads a new empty KeyStore of type "PKCS12".
     *
     * @param password the password to protect the KeyStore integrity
     * @return the loaded KeyStore instance
     */
    public static KeyStore createEmptyKeyStore(char[] password) throws Exception {
        // TODO: Instantiate "PKCS12" KeyStore and load(null, password)
        return null;
    }

    /**
     * Stores a SecretKey inside the KeyStore under a password-protected entry.
     *
     * @param ks the target KeyStore
     * @param alias the lookup name for the key
     * @param key the SecretKey to save
     * @param password the password protecting this specific key entry
     */
    public static void storeKey(KeyStore ks, String alias, SecretKey key, char[] password) throws Exception {
        // TODO: Store KeyStore.SecretKeyEntry in the KeyStore
    }

    /**
     * Retrieves a SecretKey from the KeyStore.
     *
     * @param ks the target KeyStore
     * @param alias the lookup name
     * @param password the entry protection password
     * @return the recovered SecretKey
     */
    public static SecretKey retrieveKey(KeyStore ks, String alias, char[] password) throws Exception {
        // TODO: Retrieve the entry and return the SecretKey
        return null;
    }
}
