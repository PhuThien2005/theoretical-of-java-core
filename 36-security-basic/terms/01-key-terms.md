# Basic Security Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## secure coding

Secure coding is the practice of writing software to minimize security vulnerabilities by validating inputs, protecting sensitive data, using safe APIs, and following least-privilege principles.

Why it matters: Most security breaches exploit coding mistakes (injection, buffer overflows, insecure deserialization). Secure coding prevents these at the source code level, not just at the network perimeter.

Common confusion: Developers often think security is only about firewalls or network configuration, not realizing that most CVEs (Common Vulnerabilities and Exposures) are caused by application-layer code bugs.

Small example: Using `PreparedStatement` instead of `Statement` for SQL queries eliminates SQL injection at the code level.

## hashing

A cryptographic hash function is a one-way mathematical function that maps input data of arbitrary size to a fixed-size digest. Given the digest, it is computationally infeasible to recover the original input.

Why it matters: Hashing enables password storage without storing plaintext (store hash, compare hash), data integrity checks (file checksums), and digital signatures (hash then sign).

Common confusion: Hashing is not encryption. Hashing is irreversible by design. Encryption is reversible using a key. A `HashMap`'s hash function is NOT cryptographic — it is designed for distribution, not security.

Small example: `MessageDigest.getInstance("SHA-256").digest("password".getBytes())` produces a 32-byte digest.

## Base64

Base64 is a binary-to-text encoding scheme that converts arbitrary binary bytes into a 64-character ASCII alphabet (A–Z, a–z, 0–9, +, /).

Why it matters: Many text-based protocols (HTTP, JSON, email) cannot transmit raw binary bytes. Base64 allows binary data (images, certificates, keys) to be safely embedded in text payloads.

Common confusion: Base64 is NOT encryption. It is trivially reversible by anyone with the encoded data. Using Base64 does not protect confidentiality in any way.

Small example: `Base64.getEncoder().encodeToString("Hello".getBytes())` returns `"SGVsbG8="` — and anyone can decode it.

## encryption

Encryption is a reversible transformation of plaintext into ciphertext using a cryptographic key and algorithm (like AES), such that only someone with the correct key can decrypt the ciphertext back to plaintext.

Why it matters: Encryption protects data confidentiality in transit (TLS/HTTPS) and at rest (encrypted databases, encrypted files). Without encryption, any network observer can read data.

Common confusion: Encryption is often confused with hashing. Key difference: hashing is one-way and produces a fixed-size digest; encryption is two-way and preserves data recoverable by the recipient.

Small example: `Cipher.getInstance("AES/CBC/PKCS5Padding")` implements AES encryption — the same key used to encrypt must be used to decrypt.

## KeyStore

`KeyStore` is Java's secure container for cryptographic keys and digital certificates. It can store private keys, public key certificates, and secret (symmetric) keys in encrypted form on disk.

Why it matters: Applications that use TLS/HTTPS need to store SSL certificates and private keys securely. `KeyStore` provides a password-protected encrypted store that the JVM's `SSLContext` consumes directly.

Common confusion: Developers sometimes hardcode private keys in source code as strings. `KeyStore` exists precisely to avoid this — keys are stored encrypted at rest and loaded via a password-protected API.

Small example: `KeyStore.getInstance("PKCS12")` loads a `.p12` or `.pfx` certificate store commonly used for TLS client and server authentication.

## TLS

Transport Layer Security (TLS) is a cryptographic protocol that provides authentication, confidentiality, and integrity for network communications. HTTPS is HTTP over TLS.

Why it matters: Without TLS, an attacker performing a man-in-the-middle attack can read, intercept, or modify network traffic in cleartext. TLS prevents this by establishing an encrypted tunnel with mutual certificate verification.

Common confusion: TLS is often confused with SSL. SSL is the deprecated predecessor to TLS. Modern systems use TLS 1.2 or TLS 1.3. Saying "SSL" in a Java context usually means TLS 1.2+ via `SSLContext`.

Small example: `SSLContext.getInstance("TLS")` initializes a TLS context for use in `HttpsURLConnection` or `SSLSocket`.

## input validation

Input validation is the practice of checking all external inputs (user data, file contents, network messages, environment variables) against defined rules before processing them.

Why it matters: Most injection attacks (SQL, XSS, LDAP, command injection) succeed because applications trust untrusted input. Validation eliminates malicious data before it reaches sensitive processing layers.

Common confusion: Developers often validate only at the UI layer and skip server-side validation, assuming the UI enforces constraints. Attackers bypass the UI and send raw HTTP requests with arbitrary payloads directly.

Small example: `if (input.matches("[a-zA-Z0-9]{1,50}")) { ... }` — allowlist regex validation that rejects anything not matching the pattern.
