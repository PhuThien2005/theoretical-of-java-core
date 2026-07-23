# Basic Security - Part 1

## Learning Goal

This file covers a focused slice of **Basic Security**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Basic secure coding` |`Basic secure coding` — Practices ensuring software resilience against security exploits and vulnerabilities. |
| `Hashing` | Hashing maps input data to a fixed-size digest and is one-way in normal use. |
| `MessageDigest` |`MessageDigest` — Provides cryptographic hash algorithm functionality (e.g., SHA-256, MD5). |
| `SHA-256` |`SHA-256` — Cryptographic hash algorithm producing a 256-bit (32-byte) hash value. |
| `Base64` |`Base64` — Binary-to-text encoding scheme representing binary data in ASCII format. |
| `Basic encryption/decryption` |`Basic encryption/decryption` — Symmetric and asymmetric encryption algorithms securing data at rest and in transit. |
| `KeyStore` |`KeyStore` — Secure storage repository for cryptographic keys and public key certificates. |
| `Basic SSL/TLS` |`Basic SSL/TLS` — Cryptographic protocols providing secure, encrypted communications over a network. |
| `Input validation` |`Input validation` — Sanitizing and validating untrusted user input to prevent security exploits. |
| `Avoid SQL Injection` | SQL injection happens when untrusted input changes the meaning of a SQL command. |

## Detailed Notes

### Basic secure coding

### Hashing

Hashing maps input data of arbitrary size to a fixed-size bit string (digest). It is a one-way function, meaning it is computationally infeasible to invert.

It matters because cryptographic hashes (like SHA-256) are used to verify data integrity, generate digital signatures, and safely store hashed representations of passwords.

Practical check:

- Define `Hashing` in one sentence.
- Recognize `Hashing` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Hashing`.

Tiny example or mental model:

- When reading code, ask: what does `Hashing` change, allow, reject, or clarify?

### MessageDigest

### SHA-256

### Base64

### Basic encryption/decryption

### KeyStore

### Basic SSL/TLS

### Input validation

### Avoid SQL Injection

SQL injection happens when untrusted input changes the meaning of a SQL command.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?

## Code Examples

### Hashing with MessageDigest (SHA-256)
```java
String password = "mySecurePassword123";
MessageDigest digest = MessageDigest.getInstance("SHA-256");
byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

// Convert bytes to hex representation
StringBuilder hexString = new StringBuilder();
for (byte b : hashBytes) {
    String hex = Integer.toHexString(0xff & b);
    if (hex.length() == 1) hexString.append('0');
    hexString.append(hex);
}
System.out.println("SHA-256 Hash: " + hexString.toString());
```

### Base64 Encoding & Decoding
```java
String original = "Hello Security!";
// Encode
String encoded = Base64.getEncoder().encodeToString(original.getBytes(StandardCharsets.UTF_8));
System.out.println("Encoded: " + encoded);

// Decode
byte[] decodedBytes = Base64.getDecoder().decode(encoded);
String decoded = new String(decodedBytes, StandardCharsets.UTF_8);
System.out.println("Decoded: " + decoded);
```

### Encryption & Decryption (AES)
```java
// Generate a symmetric key
KeyGenerator keyGen = KeyGenerator.getInstance("AES");
keyGen.init(256);
SecretKey secretKey = keyGen.generateKey();

// Encrypt
Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
byte[] iv = new byte[16];
new SecureRandom().nextBytes(iv); // Generate initialization vector
IvParameterSpec ivSpec = new IvParameterSpec(iv);

cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
byte[] encrypted = cipher.doFinal("Secret Data".getBytes(StandardCharsets.UTF_8));

// Decrypt
cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
byte[] decryptedBytes = cipher.doFinal(encrypted);
System.out.println("Decrypted: " + new String(decryptedBytes, StandardCharsets.UTF_8));
```

## Common Mistakes

- **Using MD5 or SHA-1 for Security**: MD5 and SHA-1 have known collision vulnerabilities. Always use modern, strong algorithms like SHA-256, SHA-512, or specialized password hashing functions like bcrypt/Argon2.
- **Confusing Base64 with Encryption**: Base64 is an encoding format used to represent binary data in ASCII text. It is NOT encryption and provides ZERO security or confidentiality.
- **Using java.util.Random for Cryptographic Keys**: `java.util.Random` is a pseudorandom number generator (PRNG) that is predictable. For security-sensitive values (keys, IVs, salts, session IDs), always use `java.security.SecureRandom`.
- **Storing Passwords in Plain Strings**: Strings are immutable and remain in the JVM memory pool until garbage collection. Sensitive data like passwords should be stored in `char[]` and zeroed out (`Arrays.fill(charArray, '0')`) immediately after use.

---

## Why Cryptographic Hashing Is One-Way and Collision-Resistant

Cryptographic hash functions like SHA-256 are mathematical functions designed with two fundamental security properties that make them suitable for secure data applications:

1. **One-way (Pre-image resistance)**: Given a hash output `H`, it is computationally infeasible to find any input `M` such that `hash(M) = H`. SHA-256 achieves this because its compression function uses nonlinear bit operations (rotations, XOR, additions) that are easy to apply in the forward direction but impossible to reverse without trying all possible inputs.

2. **Collision resistance**: It is computationally infeasible to find two different inputs `M1` and `M2` such that `hash(M1) = hash(M2)`. SHA-256 produces a 256-bit output (2^256 possible hashes), making brute-force collision finding astronomically unlikely with current computing power.

This is why SHA-256 is used for password hashing (store the hash, never the password), digital signatures (hash then sign), and data integrity (compare hashes to detect tampering). MD5 and SHA-1 are broken because real collision attacks have been demonstrated against them, allowing attackers to forge documents with the same hash.

### Mental Model: One-Way Function (Trapdoor)
```
Input Password: "mySecret"
        |
        v
SHA-256 Compression (Nonlinear Bit Operations: Sigma, Choose, Majority)
        |
        v
Output Hash: "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
        |
        X  (Cannot be reversed — no mathematical inverse exists)
```

### Code Example: SHA-256 Hashing with Salting
```java
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HashingDemo {
    public static String hashWithSalt(String password) throws Exception {
        // Generate a random 16-byte salt to prevent rainbow table attacks
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(salt); // Incorporate salt before hashing
        byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        // Encode result as Base64 for storage
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hashBytes);
    }

    public static void main(String[] args) throws Exception {
        String hash = hashWithSalt("mySecretPassword");
        System.out.println("Salted SHA-256: " + hash);
        // Output: <16-byte-salt-base64>:<sha256-hash-base64>
    }
}
```

### Cause-Effect Chain
SHA-256 compression applied to input &rarr; Nonlinear bit operations destroy information directionality &rarr; 256-bit digest produced &rarr; Reversing digest mathematically impossible without brute force &rarr; Brute force requires 2^256 attempts &rarr; Computationally infeasible with current hardware.

---

## Why Base64 Is Encoding Not Encryption

Base64 is a binary-to-text encoding scheme that represents binary data (like bytes, images, or keys) as printable ASCII characters using a 64-character alphabet (A–Z, a–z, 0–9, +, /). It is a purely structural transformation — no secret key is involved and no information is hidden.

The security risk arises when developers mistakenly believe that "encoded" output is "secured" output. If an API token, password, or sensitive payload is only Base64-encoded (not encrypted), anyone who obtains the encoded data can trivially decode it in one step using any online decoder or the `Base64.getDecoder().decode(bytes)` call in Java.

Base64 serves a legitimate purpose: it makes binary data safe for transmission over text-only channels (HTTP headers, JSON payloads, email bodies) that cannot carry raw bytes. For example, JWT (JSON Web Token) headers and payloads are Base64url-encoded, not encrypted — which is why JWTs are not confidential unless the payload itself is encrypted.

Encryption requires a secret key and an algorithm (like AES) that transforms data into ciphertext that is unintelligible without the key. Base64 provides zero confidentiality.

### Mental Model: Encoding vs Encryption
```
[Base64 Encoding — No Key]
Binary bytes: [0x48 0x65 0x6C 0x6C 0x6F] ("Hello")
     |
     v
Base64("Hello") = "SGVsbG8="  ← Anyone can decode this
     |
     v
Base64.decode("SGVsbG8=") = "Hello"  ← Trivially reversible

[AES Encryption — Requires Secret Key]
Plaintext: "Hello"  + Secret Key (256 bits) + IV
     |
     v
AES-256-CBC("Hello", key, iv) = [0xA3 0xF7 0x2E...] ← Unintelligible without key
     |
     x  AES-256-CBC decrypt needs the same key and IV → "Hello"
```

### Code Example: Base64 Encoding Is Reversible (Not Secure)
```java
import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class Base64Demo {
    public static void main(String[] args) {
        String secret = "db_password=super_secret";

        // Base64 encoding (NOT encryption)
        String encoded = Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
        System.out.println("Encoded: " + encoded);
        // Output: Encoded: ZGJfcGFzc3dvcmQ9c3VwZXJfc2VjcmV0

        // Anyone can decode in one step
        String decoded = new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
        System.out.println("Decoded: " + decoded);
        // Output: Decoded: db_password=super_secret (full secret exposed!)
    }
}
```

### Cause-Effect Chain
Developer stores API key as Base64-encoded string &rarr; No encryption key is used &rarr; Attacker obtains encoded string from log or API response &rarr; Decodes in one step using public Base64 tools &rarr; Full secret exposed.

---

## Why SecureRandom Is Required for Cryptographic Values

Java's `java.util.Random` is a Linear Congruential Generator (LCG), a pseudorandom number generator (PRNG) that produces its output by applying a deterministic mathematical formula to a seed value: `seed = (seed * 25214903917 + 11) & ((1L << 48) - 1)`. If an attacker observes even a few outputs from a `Random` instance, they can mathematically reconstruct the internal seed and predict all past and future values with certainty. This makes `Random` completely unsuitable for generating encryption keys, initialization vectors (IVs), session IDs, salts, or any other security-sensitive random values.

`java.security.SecureRandom` is backed by the operating system's cryptographically secure entropy source (e.g., `/dev/urandom` on Linux, `BCryptGenRandom` on Windows). These entropy sources aggregate unpredictable physical events — keyboard timing, hardware interrupts, network packet arrival times — making their output statistically indistinguishable from true randomness, even to an attacker who knows the SecureRandom algorithm.

### Mental Model: Predictable PRNG vs. Cryptographically Secure RNG
```
[java.util.Random — Predictable]
seed = 12345
Random output sequence: 6, 3, 1, 8, 2, 7, ...
Attacker observes 3 outputs → Reconstructs seed → Predicts all future outputs

[java.security.SecureRandom — Unpredictable]
Entropy pool: keyboard timings, hardware interrupts, /dev/urandom
SecureRandom output: [0xA3, 0x7F, 0xB9, ...] (statistically uniform, cryptographically indistinguishable)
Attacker observes outputs → Cannot reconstruct state → Cannot predict future outputs
```

### Code Example: Generating a Cryptographically Secure AES Key and IV
```java
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

public class SecureKeyGenDemo {
    public static void main(String[] args) throws Exception {
        // WRONG: Using Math.random() or new Random() for keys/IVs
        // byte[] weakIV = new byte[16]; new Random().nextBytes(weakIV); // Predictable!

        // CORRECT: Using SecureRandom for cryptographic values
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv); // Truly unpredictable
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // Key generation also uses SecureRandom internally
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, new SecureRandom()); // Explicitly specify SecureRandom
        SecretKey key = keyGen.generateKey();

        System.out.println("Key algorithm: " + key.getAlgorithm()); // Output: AES
        System.out.println("IV length: " + iv.length);              // Output: 16
    }
}
```

### Cause-Effect Chain
`Random` used for AES key generation &rarr; Key output is deterministic and derivable from observed outputs &rarr; Attacker reverse-engineers key material &rarr; Decrypts all ciphertext protected with that key &rarr; `SecureRandom` sourced from OS entropy &rarr; Output is cryptographically unpredictable &rarr; Key cannot be derived even with full knowledge of algorithm.

---

## Why Passwords Must Not Be Stored in Strings

Java `String` objects are immutable and interned in the JVM's String Pool. When a string containing a password is created, it cannot be manually zeroed out or garbage-collected on demand. The string may remain in memory for an indeterminate period of time — until the garbage collector decides to collect it, which can be seconds, minutes, or hours later depending on heap pressure and GC tuning.

During this time, the password exists as a clear-text string in JVM heap memory. If an attacker achieves a heap dump (via `jmap`, process memory inspection, or exploiting a heap dump endpoint accidentally exposed in production), they can trivially find the plaintext password by scanning for printable ASCII strings in the dump.

In contrast, a `char[]` can be explicitly zeroed immediately after use by calling `Arrays.fill(passwordChars, '\0')`, eliminating the password from memory as soon as it is no longer needed and before the GC runs.

### Mental Model: String Pool Retention vs. char[] Zeroing
```
[String — Cannot be cleared]
String password = "mySecret";
    → JVM String Pool: ["mySecret"] ← remains in memory until GC
    → GC may not run for minutes/hours
    → Heap dump captures "mySecret" in clear text

[char[] — Can be zeroed immediately]
char[] password = "mySecret".toCharArray();
// ... use password ...
Arrays.fill(password, '\0');  // Immediate memory zeroing
    → Memory: ['\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0']
    → Heap dump captures only null bytes → Password not recoverable
```

### Code Example: Safe Password Handling with char[]
```java
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;

public class SafePasswordHandling {
    public static byte[] hashPassword(char[] password) throws Exception {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        // PBEKeySpec accepts char[] (not String) by design
        PBEKeySpec spec = new PBEKeySpec(password, salt, 310000, 256);
        Arrays.fill(password, '\0'); // Zero out password immediately after use

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return factory.generateSecret(spec).getEncoded();
    }
}
```

### Cause-Effect Chain
Password stored in `String` field &rarr; String immutable and retained in JVM heap &rarr; GC delay keeps password accessible in memory &rarr; Heap dump exposes plaintext &rarr; Password stored in `char[]` &rarr; `Arrays.fill()` zeroes memory immediately after use &rarr; No readable password data remains in heap.

## Reference Links

- https://docs.oracle.com/javase/tutorial/security/ (Java Security Tutorial)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/security/MessageDigest.html (MessageDigest JavaDoc)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/security/SecureRandom.html (SecureRandom JavaDoc)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Base64.html (Base64 JavaDoc)

