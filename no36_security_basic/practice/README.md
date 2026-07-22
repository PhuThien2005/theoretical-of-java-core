# Practice Exercises: Basic Security

This folder contains hands-on practice exercises to reinforce your understanding of Java Cryptography Architecture (JCA), password hashing using `MessageDigest`, symmetric encryption using `Cipher` (AES), and managing cryptographic keys using `KeyStore`.

## Exercises

### 1. Secure Vault (`secure-vault`)
Securing sensitive data requires robust cryptographic algorithms. In Java, password storage utilizes salt-based `MessageDigest` (e.g. SHA-256), and message encryption utilizes symmetric `Cipher` algorithms (e.g. AES).
- **Goal**: Implement `SecureVault` containing methods to generate password hashes and to encrypt/decrypt strings using AES.

#### Directory Structure
- [SecureVault.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no36_security_basic/practice/secure-vault/src/SecureVault.java)
- [SecureVaultTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no36_security_basic/practice/secure-vault/test/SecureVaultTest.java)
- [SecureVault.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no36_security_basic/practice/secure-vault/solution/SecureVault.java)

---

### 2. KeyStore Manager (`keystore-manager`)
Saving secret keys directly in plain source code is a major security vulnerability. Java `KeyStore` containers encrypt and secure cryptographic keys and certificates under a password barrier.
- **Goal**: Implement a `KeyStoreManager` that initializes PKCS12 key stores, writes secret keys to them, and retrieves them back using password protection.

#### Directory Structure
- [KeyStoreManager.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no36_security_basic/practice/keystore-manager/src/KeyStoreManager.java)
- [KeyStoreManagerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no36_security_basic/practice/keystore-manager/test/KeyStoreManagerTest.java)
- [KeyStoreManager.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no36_security_basic/practice/keystore-manager/solution/KeyStoreManager.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no36_security_basic
```
