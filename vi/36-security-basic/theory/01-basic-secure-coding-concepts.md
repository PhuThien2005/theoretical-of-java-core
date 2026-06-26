# Bảo mật cơ bản - Phần 1 (Basic Security - Part 1)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm một phần trọng tâm của **Bảo mật cơ bản (Basic Security)**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là từ vựng rời rạc.

## Phạm vi đề cương (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `Basic secure coding` | Lập trình an toàn cơ bản (Basic secure coding) là một khái niệm cụ thể trong Bảo mật cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại (failure mode) của nó thay vì chỉ nhớ tên. |
| `Hashing` | Hàm băm (Hashing) ánh xạ dữ liệu đầu vào thành một giá trị băm (digest) có kích thước cố định và là một chiều trong sử dụng thông thường. |
| `MessageDigest` | `MessageDigest` là một khái niệm cụ thể trong Bảo mật cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên. |
| `SHA-256` | `SHA-256` là một khái niệm cụ thể trong Bảo mật cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên. |
| `Base64` | `Base64` là một khái niệm cụ thể trong Bảo mật cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên. |
| `Basic encryption/decryption` | Mã hóa/giải mã cơ bản (Basic encryption/decryption) là một khái niệm cụ thể trong Bảo mật cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên. |
| `KeyStore` | `KeyStore` là một khái niệm cụ thể trong Bảo mật cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên. |
| `Basic SSL/TLS` | SSL/TLS cơ bản (Basic SSL/TLS) là một khái niệm cụ thể trong Bảo mật cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên. |
| `Input validation` | Kiểm thực dữ liệu đầu vào (Input validation) là một khái niệm cụ thể trong Bảo mật cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên. |
| `Avoid SQL Injection` | Tấn công chèn mã SQL (SQL injection) xảy ra khi dữ liệu đầu vào không đáng tin cậy làm thay đổi ý nghĩa của một lệnh SQL. |

## Ghi chú chi tiết (Detailed Notes)

### Lập trình an toàn cơ bản (Basic secure coding)

Lập trình an toàn cơ bản là một khái niệm cụ thể trong Bảo mật cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Basic secure coding` trong một câu.
- Nhận biết `Basic secure coding` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Basic secure coding`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `Basic secure coding` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Băm (Hashing)

Hàm băm (Hashing) ánh xạ dữ liệu đầu vào có kích thước bất kỳ thành một chuỗi bit có kích thước cố định (giá trị băm - digest). Nó là một hàm một chiều (one-way), nghĩa là về mặt tính toán là không thể đảo ngược để tìm lại dữ liệu ban đầu.

Nó quan trọng vì các hàm băm mật mã (như SHA-256) được sử dụng để xác minh tính toàn vẹn của dữ liệu (data integrity), tạo chữ ký số, và lưu trữ an toàn các biểu diễn băm của mật khẩu.

Kiểm tra thực tế:

- Định nghĩa `Hashing` trong một câu.
- Nhận biết `Hashing` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Hashing`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `Hashing` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### MessageDigest (MessageDigest)

`MessageDigest` là một khái niệm cụ thể trong Bảo mật cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `MessageDigest` trong một câu.
- Nhận biết `MessageDigest` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `MessageDigest`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `MessageDigest` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### SHA-256 (SHA-256)

`SHA-256` là một khái niệm cụ thể trong Bảo mật cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `SHA-256` trong một câu.
- Nhận biết `SHA-256` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `SHA-256`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `SHA-256` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Base64 (Base64)

`Base64` là một khái niệm cụ thể trong Bảo mật cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Base64` trong một câu.
- Nhận biết `Base64` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Base64`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `Base64` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Mã hóa/giải mã cơ bản (Basic encryption/decryption)

Mã hóa/giải mã cơ bản là một khái niệm cụ thể trong Bảo mật cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Basic encryption/decryption` trong một câu.
- Nhận biết `Basic encryption/decryption` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Basic encryption/decryption`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `Basic encryption/decryption` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### KeyStore (KeyStore)

`KeyStore` là một khái niệm cụ thể trong Bảo mật cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `KeyStore` trong một câu.
- Nhận biết `KeyStore` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `KeyStore`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `KeyStore` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### SSL/TLS cơ bản (Basic SSL/TLS)

SSL/TLS cơ bản là một khái niệm cụ thể trong Bảo mật cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Basic SSL/TLS` trong một câu.
- Nhận biết `Basic SSL/TLS` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Basic SSL/TLS`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `Basic SSL/TLS` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Kiểm thực dữ liệu đầu vào (Input validation)

Kiểm thực dữ liệu đầu vào là một khái niệm cụ thể trong Bảo mật cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Input validation` trong một câu.
- Nhận biết `Input validation` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Input validation`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `Input validation` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Tránh SQL Injection (Avoid SQL Injection)

Tấn công chèn mã SQL (SQL injection) xảy ra khi dữ liệu đầu vào không đáng tin cậy làm thay đổi ý nghĩa của một lệnh SQL.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Avoid SQL Injection` trong một câu.
- Nhận biết `Avoid SQL Injection` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Avoid SQL Injection`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `Avoid SQL Injection` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Câu hỏi ôn tập phổ biến (Common Review Prompts)

- Khái niệm nào ở đây là quy tắc ở thời điểm biên dịch (compile-time)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi lúc chạy (runtime)?
- Khái niệm nào ở đây dễ là bẫy phỏng vấn (interview traps)?

## Các ví dụ code (Code Examples)

### Băm với MessageDigest (SHA-256) (Hashing with MessageDigest (SHA-256))
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

### Mã hóa & Giải mã Base64 (Base64 Encoding & Decoding)
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

### Mã hóa & Giải mã AES (Encryption & Decryption (AES))
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

## Các lỗi thường gặp (Common Mistakes)

- **Sử dụng MD5 hoặc SHA-1 cho Bảo mật**: MD5 và SHA-1 có các lỗ hổng đụng độ đã biết. Luôn sử dụng các thuật toán hiện đại, mạnh mẽ như SHA-256, SHA-512, hoặc các hàm băm mật khẩu chuyên dụng như bcrypt/Argon2.
- **Nhầm lẫn Base64 với Mã hóa**: Base64 là định dạng mã hóa ký tự được sử dụng để biểu diễn dữ liệu nhị phân dưới dạng văn bản ASCII. Nó KHÔNG phải là mã hóa bảo mật và mang lại KHÔNG phần trăm tính bảo mật hay bí mật.
- **Sử dụng java.util.Random cho Khóa mã hóa**: `java.util.Random` là một bộ tạo số giả ngẫu nhiên (PRNG) có thể dự đoán được. Đối với các giá trị nhạy cảm về bảo mật (khóa, IV, salt, session ID), luôn sử dụng `java.security.SecureRandom`.
- **Lưu trữ mật khẩu trong chuỗi String thông thường**: Đối tượng String là bất biến và tồn tại trong String Pool của JVM cho đến khi được gom rác. Dữ liệu nhạy cảm như mật khẩu nên được lưu trữ trong mảng `char[]` và được xóa trắng (`Arrays.fill(charArray, '0')`) ngay sau khi sử dụng.

---

## Tại sao Hàm băm mã hóa là một chiều và Kháng đụng độ (Why Cryptographic Hashing Is One-Way and Collision-Resistant)

Các hàm băm mã hóa như SHA-256 là các hàm toán học được thiết kế với hai đặc tính bảo mật cơ bản giúp chúng phù hợp cho các ứng dụng dữ liệu an toàn:

1. **Một chiều (Kháng tiền ảnh - Pre-image resistance)**: Cho trước một đầu ra băm `H`, về mặt tính toán là không thể tìm thấy bất kỳ đầu vào `M` nào sao cho `hash(M) = H`. SHA-256 đạt được điều này vì hàm nén của nó sử dụng các thao tác bit phi tuyến tính (xoay bit, XOR, phép cộng) dễ áp dụng theo chiều xuôi nhưng không thể đảo ngược nếu không thử tất cả các đầu vào có thể.

2. **Kháng đụng độ (Collision resistance)**: Về mặt tính toán là không thể tìm thấy hai đầu vào khác nhau `M1` và `M2` sao cho `hash(M1) = hash(M2)`. SHA-256 tạo ra đầu ra 256-bit (2^256 giá trị băm có thể có), khiến việc tìm kiếm đụng độ bằng brute-force là cực kỳ bất khả thi với sức mạnh tính toán hiện tại.

Đây là lý do tại sao SHA-256 được sử dụng để băm mật khẩu (lưu trữ mã băm, không bao giờ lưu mật khẩu rõ), chữ ký số (băm rồi ký), và kiểm tra tính toàn vẹn dữ liệu (so sánh các mã băm để phát hiện thay đổi). MD5 và SHA-1 đã bị bẻ khóa vì các cuộc tấn công đụng độ thực tế đã được chứng minh, cho phép kẻ tấn công giả mạo tài liệu có cùng mã băm.

### Mô hình tư duy: Hàm một chiều (Cửa sập) (Mental Model: One-Way Function (Trapdoor))
```
Mật khẩu đầu vào: "mySecret"
        |
        v
Hàm nén SHA-256 (Thao tác bit phi tuyến tính: Sigma, Choose, Majority)
        |
        v
Mã băm đầu ra: "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
        |
        X  (Không thể đảo ngược — không tồn tại hàm ngược toán học)
```

### Ví dụ Code: Băm SHA-256 kèm Salt (Code Example: SHA-256 Hashing with Salting)
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

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Áp dụng hàm nén SHA-256 vào đầu vào
  → Các thao tác bit phi tuyến tính phá hủy tính hướng của thông tin
  → Tạo ra giá trị băm 256-bit
  → Việc đảo ngược giá trị băm về mặt toán học là bất khả thi nếu không dùng brute force
  → Brute force đòi hỏi 2^256 lần thử
  → Không thể thực hiện được về mặt tính toán với phần cứng hiện tại.
```


---

## Tại sao Base64 là Mã hóa định dạng chứ không phải Mã hóa bảo mật (Why Base64 Is Encoding Not Encryption)

Base64 là một lược đồ mã hóa định dạng từ nhị phân sang văn bản (binary-to-text encoding scheme) biểu diễn dữ liệu nhị phân (như byte, hình ảnh hoặc khóa) dưới dạng các ký tự ASCII có thể in được bằng cách sử dụng bảng chữ cái 64 ký tự (A–Z, a–z, 0–9, +, /). Đây hoàn toàn là một phép biến đổi cấu trúc — không liên quan đến khóa bí mật và không có thông tin nào được ẩn giấu.

Rủi ro bảo mật phát sinh khi các lập trình viên tin tưởng sai lầm rằng đầu ra "được mã hóa định dạng" (encoded) là đầu ra "được bảo mật" (secured). Nếu một API token, mật khẩu hoặc dữ liệu nhạy cảm chỉ được mã hóa định dạng bằng Base64 (chứ không phải được mã hóa bảo mật - encrypted), bất kỳ ai có được dữ liệu đó đều có thể giải mã một cách dễ dàng trong một bước bằng cách sử dụng bất kỳ công cụ giải mã trực tuyến nào hoặc lệnh gọi `Base64.getDecoder().decode(bytes)` trong Java.

Base64 phục vụ một mục đích hợp lệ: nó giúp truyền dữ liệu nhị phân an toàn qua các kênh chỉ hỗ trợ văn bản (tiêu đề HTTP, chuỗi JSON, nội dung email) vốn không thể mang các byte thô. Ví dụ, tiêu đề và dữ liệu tải của JWT (JSON Web Token) được mã hóa Base64url, chứ không phải được mã hóa bảo mật — đó là lý do tại sao JWT không có tính bảo mật thông tin trừ khi chính dữ liệu tải đó được mã hóa bảo mật.

Mã hóa bảo mật đòi hỏi một khóa bí mật và một thuật toán (như AES) biến đổi dữ liệu thành văn bản mã hóa không thể đọc được nếu không có khóa. Base64 cung cấp không phần trăm tính bí mật.

### Mô hình tư duy: Mã hóa định dạng so với Mã hóa bảo mật (Mental Model: Encoding vs Encryption)
```
[Mã hóa định dạng Base64 — Không cần khóa]
Các byte nhị phân: [0x48 0x65 0x6C 0x6C 0x6F] ("Hello")
      |
      v
Base64("Hello") = "SGVsbG8="  ← Bất kỳ ai cũng có thể giải mã cái này
      |
      v
Base64.decode("SGVsbG8=") = "Hello"  ← Dễ dàng đảo ngược


[Mã hóa bảo mật AES — Yêu cầu khóa bí mật]
Văn bản rõ: "Hello"  + Khóa bí mật (256 bits) + IV
      |
      v
AES-256-CBC("Hello", key, iv) = [0xA3 0xF7 0x2E...] ← Không thể hiểu được nếu không có khóa
      |
      x  Giải mã AES-256-CBC cần cùng khóa và IV → "Hello"
```

### Ví dụ Code: Mã hóa Base64 có thể đảo ngược (Không bảo mật) (Code Example: Base64 Encoding Is Reversible (Not Secure))
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

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Lập trình viên lưu trữ khóa API dưới dạng chuỗi mã hóa Base64
  → Không sử dụng khóa mã hóa bảo mật
  → Kẻ tấn công lấy được chuỗi mã hóa định dạng từ log hoặc phản hồi API
  → Giải mã trong một bước bằng các công cụ Base64 công khai
  → Toàn bộ bí mật bị lộ.
```


---

## Tại sao SecureRandom được yêu cầu cho các giá trị mã hóa (Why SecureRandom Is Required for Cryptographic Values)

Lớp `java.util.Random` của Java là một Bộ tạo số giả ngẫu nhiên tuyến tính đồng dư (Linear Congruential Generator - LCG), tạo ra đầu ra của nó bằng cách áp dụng một công thức toán học xác định vào một giá trị hạt giống (seed): `seed = (seed * 25214903917 + 11) & ((1L << 48) - 1)`. Nếu một kẻ tấn công quan sát được dù chỉ một vài đầu ra từ một instance `Random`, họ có thể tái dựng lại hạt giống nội bộ bằng toán học và dự đoán chắc chắn tất cả các giá trị trong quá khứ và tương lai. Điều này làm cho `Random` hoàn toàn không phù hợp để tạo các khóa mã hóa, vectơ khởi tạo (IV), ID phiên (session ID), salt, hoặc bất kỳ giá trị ngẫu nhiên nhạy cảm về bảo mật nào khác.

`java.security.SecureRandom` được hỗ trợ bởi nguồn entropy an toàn mã hóa của hệ điều hành (ví dụ: `/dev/urandom` trên Linux, `BCryptGenRandom` trên Windows). Các nguồn entropy này tổng hợp các sự kiện vật lý không thể dự đoán được — thời gian nhấn bàn phím, ngắt phần cứng, thời gian đến của gói mạng — làm cho đầu ra của chúng không thể phân biệt được với tính ngẫu nhiên thực sự về mặt thống kê, ngay cả đối với kẻ tấn công biết thuật toán SecureRandom.

### Mô hình tư duy: PRNG có thể dự đoán so với RNG an toàn mã hóa (Mental Model: Predictable PRNG vs. Cryptographically Secure RNG)
```
[java.util.Random — Có thể dự đoán]
seed = 12345
Chuỗi đầu ra ngẫu nhiên: 6, 3, 1, 8, 2, 7, ...
Kẻ tấn công quan sát 3 đầu ra &rarr; Tái dựng hạt giống &rarr; Dự đoán tất cả các đầu ra tương lai


[java.security.SecureRandom — Không thể dự đoán]
Nguồn Entropy: thời gian phím bấm, ngắt phần cứng, /dev/urandom
Đầu ra SecureRandom: [0xA3, 0x7F, 0xB9, ...] (đồng nhất về mặt thống kê, không thể phân biệt về mặt mã hóa)
Kẻ tấn công quan sát các đầu ra &rarr; Không thể tái dựng trạng thái &rarr; Không thể dự đoán các đầu ra tương lai
```

### Ví dụ Code: Tạo Khóa AES và IV an toàn mã hóa (Code Example: Generating a Cryptographically Secure AES Key and IV)
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

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
`Random` được sử dụng để tạo khóa AES
  → Khóa đầu ra mang tính xác định và có thể dẫn xuất từ các đầu ra quan sát được
  → Kẻ tấn công đảo ngược dữ liệu khóa
  → Giải mã tất cả văn bản mã hóa được bảo vệ bằng khóa đó
  → `SecureRandom` lấy nguồn từ entropy của OS
  → Đầu ra không thể dự đoán về mặt mã hóa
  → Khóa không thể bị suy đoán ngay cả khi biết toàn bộ thuật toán.
```


---

## Tại sao Mật khẩu không được lưu trữ trong String (Why Passwords Must Not Be Stored in Strings)

Các đối tượng `String` trong Java là bất biến (immutable) và được lưu trữ trong vùng nhớ nhóm chuỗi (String Pool) của JVM. Khi một chuỗi chứa mật khẩu được tạo ra, nó không thể được xóa trắng thủ công hoặc gom rác theo yêu cầu. Chuỗi đó có thể tồn tại trong bộ nhớ trong một khoảng thời gian không xác định — cho đến khi bộ thu gom rác (Garbage collection) (garbage collector - GC) quyết định thu dọn nó, việc này có thể mất vài giây, vài phút hoặc vài giờ tùy thuộc vào áp lực bộ nhớ heap và cấu hình GC.

Trong thời gian này, mật khẩu tồn tại dưới dạng chuỗi văn bản rõ (clear-text string) trong bộ nhớ heap của JVM. Nếu một kẻ tấn công có được một bản sao lưu bộ nhớ heap (heap dump) (thông qua `jmap`, kiểm tra bộ nhớ tiến trình, hoặc khai thác một endpoint heap dump vô tình bị lộ trong production), họ có thể dễ dàng tìm thấy mật khẩu văn bản rõ bằng cách quét các chuỗi ASCII có thể in được trong bản sao lưu đó.

Ngược lại, một mảng `char[]` có thể được xóa trắng rõ ràng ngay sau khi sử dụng bằng cách gọi `Arrays.fill(passwordChars, '\0')`, loại bỏ mật khẩu khỏi bộ nhớ ngay khi không còn cần thiết và trước khi GC chạy.

### Mô hình tư duy: Lưu giữ trong String Pool so với Xóa char[] về không (Mental Model: String Pool Retention vs. char[] Zeroing)
```
[String — Không thể xóa sạch]
String password = "mySecret";
    → JVM String Pool: ["mySecret"] ← vẫn nằm trong bộ nhớ cho đến khi GC chạy
    → GC có thể không chạy trong nhiều phút/giờ
    → Heap dump chụp được "mySecret" dưới dạng văn bản rõ

[char[] — Có thể xóa ngay lập tức]
char[] password = "mySecret".toCharArray();
// ... sử dụng mật khẩu ...
Arrays.fill(password, '\0');  // Xóa trắng bộ nhớ ngay lập tức
    → Bộ nhớ: ['\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0']
    → Heap dump chỉ chụp được các byte null &rarr; Mật khẩu không thể khôi phục
```

### Ví dụ Code: Xử lý mật khẩu an toàn với char[] (Code Example: Safe Password Handling with char[])
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

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Mật khẩu được lưu trữ trong trường `String`
  → String là bất biến và được giữ lại trong heap của JVM
  → GC trễ khiến mật khẩu có thể truy cập được trong bộ nhớ
  → Heap dump làm lộ văn bản rõ
  → Mật khẩu được lưu trữ trong `char[]`
  → `Arrays.fill()` xóa bộ nhớ về không ngay sau khi sử dụng
  → Không còn dữ liệu mật khẩu nào có thể đọc được trong heap.
```


## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/javase/tutorial/security/ (Java Security Tutorial)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/security/MessageDigest.html (MessageDigest JavaDoc)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/security/SecureRandom.html (SecureRandom JavaDoc)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Base64.html (Base64 JavaDoc)
