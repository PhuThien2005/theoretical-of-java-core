# Bảo Mật Cơ Bản - Phần 1 (Basic Security - Part 1)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này trình bày một phần trọng tâm của **Bảo mật Cơ bản (Basic Security)**. Hãy nghiên cứu từng khái niệm dưới dạng một quy tắc thực hành trong Java, thay vì chỉ ghi nhớ các thuật ngữ riêng lẻ.

## Khung Nội Dung (Outline Coverage)

- **`Basic secure coding`** — Lập trình an toàn cơ bản (Basic secure coding) là một khái niệm cụ thể trong Bảo mật Cơ bản; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và chế độ lỗi (failure mode) thay vì chỉ nhớ tên khái niệm.
- **`Hashing`** — Băm (Hashing) ánh xạ dữ liệu đầu vào thành một chuỗi đại diện (digest) có kích thước cố định và là hàm một chiều trong sử dụng thông thường.
- **`MessageDigest`** — `MessageDigest` là một khái niệm cụ thể trong Bảo mật Cơ bản; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và chế độ lỗi thay vì chỉ nhớ tên khái niệm.
- **`SHA-256`** — SHA-256 là một thuật toán cụ thể trong Bảo mật Cơ bản; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và chế độ lỗi thay vì chỉ nhớ tên khái niệm.
- **`Base64`** — Base64 là một khái niệm cụ thể trong Bảo mật Cơ bản; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và chế độ lỗi thay vì chỉ nhớ tên khái niệm.
- **`Basic encryption/decryption`** — Mã hóa/giải mã cơ bản (Basic encryption/decryption) là một khái niệm cụ thể trong Bảo mật Cơ bản; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và chế độ lỗi thay vì chỉ nhớ tên khái niệm.
- **`KeyStore`** — `KeyStore` là một khái niệm cụ thể trong Bảo mật Cơ bản; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và chế độ lỗi thay vì chỉ nhớ tên khái niệm.
- **`Basic SSL/TLS`** — SSL/TLS cơ bản (Basic SSL/TLS) là một khái niệm cụ thể trong Bảo mật Cơ bản; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và chế độ lỗi thay vì chỉ nhớ tên khái niệm.
- **`Input validation`** — Kiểm chứng đầu vào (Input validation) là một khái niệm cụ thể trong Bảo mật Cơ bản; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và chế độ lỗi thay vì chỉ nhớ tên khái niệm.
- **`Avoid SQL Injection`** — Tấn công tiêm mã SQL (SQL injection) xảy ra khi đầu vào không đáng tin cậy làm thay đổi ý nghĩa của một câu lệnh SQL.

## Ghi Chú Chi Tiết (Detailed Notes)

### Lập trình an toàn cơ bản (Basic secure coding)

Lập trình an toàn cơ bản là một khái niệm cụ thể trong Bảo mật Cơ bản; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và chế độ lỗi thay vì chỉ nhớ tên khái niệm.

Sử dụng khái niệm này để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi. Hãy xem xét nó thông qua một ví dụ nhỏ thay vì chỉ học thuộc lòng nhãn tên.

Kiểm tra thực tế:

- Định nghĩa `Basic secure coding` trong một câu.
- Nhận diện `Basic secure coding` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Basic secure coding`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `Basic secure coding` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Băm (Hashing)

Băm ánh xạ dữ liệu đầu vào có kích thước tùy ý thành một chuỗi bit có kích thước cố định (digest). Đây là hàm một chiều, nghĩa là không thể khôi phục lại dữ liệu gốc từ kết quả băm bằng các phương pháp tính toán thông thường.

Khái niệm này quan trọng vì các hàm băm mật mã (như SHA-256) được sử dụng để xác minh tính toàn vẹn của dữ liệu, tạo chữ ký số và lưu trữ an toàn các biểu diễn băm của mật khẩu.

Kiểm tra thực tế:

- Định nghĩa `Hashing` trong một câu.
- Nhận diện `Hashing` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Hashing`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `Hashing` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### MessageDigest

`MessageDigest` là một lớp cụ thể cung cấp chức năng của thuật toán băm mật mã trong Java; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và chế độ lỗi thay vì chỉ nhớ tên khái niệm.

Sử dụng khái niệm này để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi. Hãy xem xét nó thông qua một ví dụ nhỏ thay vì chỉ học thuộc lòng nhãn tên.

Kiểm tra thực tế:

- Định nghĩa `MessageDigest` trong một câu.
- Nhận diện `MessageDigest` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `MessageDigest`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `MessageDigest` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### SHA-256

SHA-256 là một thuật toán băm mật mã cụ thể tạo ra chuỗi băm 256-bit; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và chế độ lỗi thay vì chỉ nhớ tên khái niệm.

Sử dụng khái niệm này để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi. Hãy xem xét nó thông qua một ví dụ nhỏ thay vì chỉ học thuộc lòng nhãn tên.

Kiểm tra thực tế:

- Định nghĩa `SHA-256` trong một câu.
- Nhận diện `SHA-256` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `SHA-256`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `SHA-256` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Base64

Base64 là một cơ chế mã hóa nhị phân thành văn bản ASCII; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và chế độ lỗi thay vì chỉ nhớ tên khái niệm.

Sử dụng khái niệm này để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi. Hãy xem xét nó thông qua một ví dụ nhỏ thay vì chỉ học thuộc lòng nhãn tên.

Kiểm tra thực tế:

- Định nghĩa `Base64` trong một câu.
- Nhận diện `Base64` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Base64`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `Base64` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Mã hóa/giải mã cơ bản (Basic encryption/decryption)

Mã hóa/giải mã cơ bản là một khái niệm cụ thể trong Bảo mật Cơ bản; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và chế độ lỗi thay vì chỉ nhớ tên khái niệm.

Sử dụng khái niệm này để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi. Hãy xem xét nó thông qua một ví dụ nhỏ thay vì chỉ học thuộc lòng nhãn tên.

Kiểm tra thực tế:

- Định nghĩa `Basic encryption/decryption` trong một câu.
- Nhận diện `Basic encryption/decryption` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Basic encryption/decryption`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `Basic encryption/decryption` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### KeyStore

`KeyStore` là một kho lưu trữ an toàn chứa các khóa mật mã và chứng chỉ; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và chế độ lỗi thay vì chỉ nhớ tên khái niệm.

Sử dụng khái niệm này để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi. Hãy xem xét nó thông qua một ví dụ nhỏ thay vì chỉ học thuộc lòng nhãn tên.

Kiểm tra thực tế:

- Định nghĩa `KeyStore` trong một câu.
- Nhận diện `KeyStore` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `KeyStore`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `KeyStore` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### SSL/TLS cơ bản (Basic SSL/TLS)

SSL/TLS cơ bản là giao thức truyền thông an toàn trên mạng; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và chế độ lỗi thay vì chỉ nhớ tên khái niệm.

Sử dụng khái niệm này để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi. Hãy xem xét nó thông qua một ví dụ nhỏ thay vì chỉ học thuộc lòng nhãn tên.

Kiểm tra thực tế:

- Định nghĩa `Basic SSL/TLS` trong một câu.
- Nhận diện `Basic SSL/TLS` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Basic SSL/TLS`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `Basic SSL/TLS` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Kiểm chứng đầu vào (Input validation)

Kiểm chứng đầu vào là quá trình đảm bảo dữ liệu đầu vào đáp ứng các tiêu chí an toàn trước khi xử lý; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và chế độ lỗi thay vì chỉ nhớ tên khái niệm.

Sử dụng khái niệm này để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi. Hãy xem xét nó thông qua một ví dụ nhỏ thay vì chỉ học thuộc lòng nhãn tên.

Kiểm tra thực tế:

- Định nghĩa `Input validation` trong một câu.
- Nhận diện `Input validation` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Input validation`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `Input validation` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Tránh SQL Injection (Avoid SQL Injection)

Tấn công tiêm mã SQL xảy ra khi đầu vào không đáng tin cậy làm thay đổi ý nghĩa của một câu lệnh SQL.

Sử dụng khái niệm này để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi. Hãy xem xét nó thông qua một ví dụ nhỏ thay vì chỉ học thuộc lòng nhãn tên.

Kiểm tra thực tế:

- Định nghĩa `Avoid SQL Injection` trong một câu.
- Nhận diện `Avoid SQL Injection` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Avoid SQL Injection`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `Avoid SQL Injection` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Các Câu Hỏi Ôn Tập Thường Gặp (Common Review Prompts)

- Khái niệm nào ở đây là các quy tắc tại thời điểm biên dịch (compile-time)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy (runtime)?
- Khái niệm nào ở đây dễ là "bẫy" trong các buổi phỏng vấn?

## Ví Dụ Mã Nguồn (Code Examples)

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

## Các Lỗi Thường Gặp (Common Mistakes)

- **Sử dụng MD5 hoặc SHA-1 cho bảo mật**: MD5 và SHA-1 có các lỗ hổng va chạm (collision) đã được biết đến. Luôn sử dụng các thuật toán mạnh mẽ, hiện đại như SHA-256, SHA-512 hoặc các hàm băm mật khẩu chuyên dụng như bcrypt/Argon2.
- **Nhầm lẫn Base64 với mã hóa (Encryption)**: Base64 là định dạng mã hóa cấu trúc truyền tải dùng để biểu diễn dữ liệu nhị phân dưới dạng văn bản ASCII. Nó KHÔNG phải là mã hóa bảo mật và cung cấp KHÔNG một chút bảo mật hay bảo mật thông tin nào.
- **Sử dụng java.util.Random để tạo khóa mật mã**: `java.util.Random` là bộ tạo số ngẫu nhiên giả (PRNG) có thể dự đoán được. Đối với các giá trị nhạy cảm với bảo mật (khóa, vectơ khởi tạo - IV, muối - salt, ID phiên làm việc), luôn luôn sử dụng `java.security.SecureRandom`.
- **Lưu trữ mật khẩu trong các chuỗi String thông thường**: Đối tượng String là bất biến (immutable) và nằm lại trong vùng nhớ String Pool của JVM cho đến khi bộ thu gom rác (garbage collection) dọn dẹp. Dữ liệu nhạy cảm như mật khẩu nên được lưu trữ trong mảng ký tự `char[]` và được xóa ngay lập tức (`Arrays.fill(charArray, '0')`) sau khi sử dụng.

---

## Tại sao Băm Mật Mã là Hàm Một Chiều và Kháng Va Chạm (Why Cryptographic Hashing Is One-Way and Collision-Resistant)

Các hàm băm mật mã như SHA-256 là các hàm toán học được thiết kế với hai thuộc tính bảo mật cơ bản giúp chúng phù hợp với các ứng dụng bảo mật dữ liệu:

1. **Một chiều (Kháng tiền ảnh - Pre-image resistance)**: Cho một kết quả băm `H`, về mặt tính toán là bất khả thi để tìm bất kỳ đầu vào `M` nào sao cho `hash(M) = H`. SHA-256 đạt được điều này vì hàm nén của nó sử dụng các phép toán bit phi tuyến tính (xoay bit, XOR, phép cộng) dễ áp dụng theo chiều thuận nhưng không thể đảo ngược nếu không thử tất cả các đầu vào có thể.

2. **Kháng va chạm (Collision resistance)**: Về mặt tính toán là bất khả thi để tìm hai đầu vào khác nhau `M1` and `M2` sao cho `hash(M1) = hash(M2)`. SHA-256 tạo ra kết quả đầu ra 256-bit (có 2^256 giá trị băm khả dĩ), khiến việc tìm kiếm va chạm bằng cách duyệt cạn là cực kỳ khó xảy ra với năng lực máy tính hiện tại.

Đây là lý do tại sao SHA-256 được sử dụng để băm mật khẩu (lưu trữ giá trị băm, không bao giờ lưu mật khẩu gốc), chữ ký số (băm rồi ký), và xác minh tính toàn vẹn dữ liệu (so sánh giá trị băm để phát hiện giả mạo). MD5 và SHA-1 đã bị phá vỡ vì các cuộc tấn công va chạm thực tế đã được chứng minh trên chúng, cho phép kẻ tấn công giả mạo tài liệu có cùng giá trị băm.

### Mô hình Tư duy: Hàm Một Chiều (Trapdoor) (Mental Model: One-Way Function (Trapdoor))
```
Mật khẩu đầu vào: "mySecret"
        |
        v
Nén SHA-256 (Phép toán bit phi tuyến tính: Sigma, Choose, Majority)
        |
        v
Chuỗi băm đầu ra: "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
        |
        X  (Không thể đảo ngược — không tồn tại hàm toán học ngược)
```

### Ví Dụ Mã Nguồn: Băm SHA-256 kèm Muối (Salt) (Code Example: SHA-256 Hashing with Salting)
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

### Chuỗi Nhân Quả (Cause-Effect Chain)
Áp dụng nén SHA-256 cho đầu vào &rarr; Các phép toán bit phi tuyến tính phá hủy tính định hướng thông tin &rarr; Tạo ra chuỗi đại diện 256-bit &rarr; Đảo ngược toán học chuỗi đại diện là bất khả thi nếu không duyệt cạn &rarr; Duyệt cạn yêu cầu 2^256 lượt thử &rarr; Bất khả thi về mặt tính toán với phần cứng hiện tại.

---

## Tại sao Base64 chỉ là Mã hóa chứ không phải Mã hóa Bảo mật (Why Base64 Is Encoding Not Encryption)

Base64 là một sơ đồ mã hóa nhị phân thành văn bản đại diện cho dữ liệu nhị phân (như byte, hình ảnh hoặc khóa) dưới dạng các ký tự ASCII có thể in được bằng bảng chữ cái gồm 64 ký tự (A–Z, a–z, 0–9, +, /). Đây đơn thuần là một biến đổi cấu trúc — không có khóa bí mật nào liên quan và không có thông tin nào được ẩn giấu.

Rủi ro bảo mật phát sinh khi các nhà phát triển lầm tưởng rằng kết quả đầu ra "được mã hóa" (encoded) là kết quả "được bảo mật" (secured). Nếu mã thông báo API, mật khẩu hoặc dữ liệu nhạy cảm chỉ được mã hóa Base64 (không phải mã hóa bảo mật), bất kỳ ai có được dữ liệu đã mã hóa đều có thể dễ dàng giải mã nó trong một bước duy nhất bằng bất kỳ bộ giải mã trực tuyến nào hoặc lệnh gọi `Base64.getDecoder().decode(bytes)` trong Java.

Base64 phục vụ một mục đích hợp lý: nó làm cho dữ liệu nhị phân an toàn khi truyền qua các kênh chỉ hỗ trợ văn bản (HTTP headers, JSON payloads, email bodies) vốn không thể chứa các byte thô. Ví dụ, phần header và payload của JWT (JSON Web Token) được mã hóa Base64url chứ không phải mã hóa bảo mật — đó là lý do tại sao các JWT không bí mật trừ khi chính payload được mã hóa bảo mật.

Mã hóa bảo mật yêu cầu một khóa bí mật và một thuật toán (như AES) biến đổi dữ liệu thành bản mã không thể hiểu được nếu không có khóa. Base64 cung cấp không một chút tính bảo mật nào.

### Mô hình Tư duy: Mã hóa truyền tải vs Mã hóa bảo mật (Mental Model: Encoding vs Encryption)
```
[Mã hóa Base64 — Không cần khóa]
Dữ liệu nhị phân: [0x48 0x65 0x6C 0x6C 0x6F] ("Hello")
     |
     v
Base64("Hello") = "SGVsbG8="  ← Bất kỳ ai cũng có thể giải mã
     |
     v
Base64.decode("SGVsbG8=") = "Hello"  ← Đảo ngược dễ dàng


[Mã hóa bảo mật AES — Yêu cầu Khóa bí mật]
Văn bản gốc: "Hello" + Khóa bí mật (256 bits) + IV
     |
     v
AES-256-CBC("Hello", key, iv) = [0xA3 0xF7 0x2E...] ← Không thể hiểu được nếu không có khóa
     |
     v  Giải mã AES-256-CBC cần cùng khóa và IV → "Hello"
```

### Ví Dụ Mã Nguồn: Mã hóa Base64 có tính Đảo ngược (Không An toàn) (Code Example: Base64 Encoding Is Reversible (Not Secure))
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
        // Output: Decoded: db_password=super_secret (loại bỏ lớp bọc và lộ bí mật!)
    }
}
```

### Chuỗi Nhân Quả (Cause-Effect Chain)
Lập trình viên lưu trữ khóa API dưới dạng chuỗi mã hóa Base64 &rarr; Không có khóa mã hóa bảo mật nào được sử dụng &rarr; Kẻ tấn công lấy được chuỗi mã hóa từ log hoặc phản hồi API &rarr; Giải mã trong một bước bằng các công cụ Base64 công khai &rarr; Lộ hoàn toàn thông tin nhạy cảm.

---

## Tại sao SecureRandom là Bắt buộc đối với các Giá trị Mật mã (Why SecureRandom Is Required for Cryptographic Values)

Lớp `java.util.Random` của Java là bộ tạo số ngẫu nhiên giả tuyến tính (Linear Congruential Generator - LCG), một bộ tạo số ngẫu nhiên giả (PRNG) tạo ra kết quả đầu ra bằng cách áp dụng một công thức toán học xác định cho một giá trị hạt giống (seed): `seed = (seed * 25214903917 + 11) & ((1L << 48) - 1)`. Nếu kẻ tấn công quan sát được một vài kết quả đầu ra từ một thể hiện `Random`, họ có thể tái dựng lại hạt giống nội bộ về mặt toán học và dự đoán tất cả các giá trị trong quá khứ và tương lai một cách chắc chắn. Điều này khiến `Random` hoàn toàn không phù hợp để tạo khóa mã hóa, vectơ khởi tạo (IV), ID phiên làm việc (session ID), muối (salt), hoặc bất kỳ giá trị ngẫu nhiên nào khác nhạy cảm về bảo mật.

`java.security.SecureRandom` được hỗ trợ bởi nguồn entropy (độ hỗn loạn) an toàn về mặt mật mã của hệ điều hành (ví dụ: `/dev/urandom` trên Linux, `BCryptGenRandom` trên Windows). Các nguồn entropy này thu thập các sự kiện vật lý không thể dự đoán được — thời gian gõ bàn phím, ngắt phần cứng, thời gian nhận gói tin mạng — làm cho đầu ra của chúng không thể phân biệt được về mặt thống kê với tính ngẫu nhiên thực sự, ngay cả đối với kẻ tấn công biết thuật toán SecureRandom.

### Mô hình Tư duy: PRNG Dự đoán được vs RNG An toàn Mật mã (Mental Model: Predictable PRNG vs. Cryptographically Secure RNG)
```
[java.util.Random — Dự đoán được]
hạt giống (seed) = 12345
Chuỗi đầu ra ngẫu nhiên: 6, 3, 1, 8, 2, 7, ...
Kẻ tấn công quan sát 3 đầu ra → Tái dựng hạt giống → Dự đoán tất cả đầu ra tương lai


[java.security.SecureRandom — Không thể dự đoán]
Nguồn Entropy: thời gian phím, ngắt phần cứng, /dev/urandom
SecureRandom đầu ra: [0xA3, 0x7F, 0xB9, ...] (phân phối đều về mặt thống kê, không thể dự đoán)
Kẻ tấn công quan sát đầu ra → Không thể tái dựng trạng thái nội bộ → Không thể dự đoán các đầu ra tương lai
```

### Ví Dụ Mã Nguồn: Tạo Khóa AES và IV An toàn Mật mã (Code Example: Generating a Cryptographically Secure AES Key and IV)
```java
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

public class SecureKeyGenDemo {
    public static void main(String[] args) throws Exception {
        // SAI: Sử dụng Math.random() hoặc new Random() cho khóa/IV
        // byte[] weakIV = new byte[16]; new Random().nextBytes(weakIV); // Dự đoán được!

        // ĐÚNG: Sử dụng SecureRandom cho các giá trị mật mã
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv); // Hoàn toàn không thể dự đoán
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // Việc tạo khóa cũng sử dụng SecureRandom bên dưới
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, new SecureRandom()); // Chỉ định rõ ràng SecureRandom
        SecretKey key = keyGen.generateKey();

        System.out.println("Key algorithm: " + key.getAlgorithm()); // Output: AES
        System.out.println("IV length: " + iv.length);              // Output: 16
    }
}
```

### Chuỗi Nhân Quả (Cause-Effect Chain)
`Random` được sử dụng để tạo khóa AES &rarr; Đầu ra khóa mang tính xác định và có thể tính được từ các đầu ra quan sát được &rarr; Kẻ tấn công đảo ngược khóa &rarr; Giải mã toàn bộ bản mã được bảo vệ bằng khóa đó &rarr; `SecureRandom` lấy nguồn từ entropy của hệ điều hành &rarr; Đầu ra không thể dự đoán &rarr; Khóa không thể bị suy đoán ngược ngay cả khi biết thuật toán.

---

## Tại sao Mật khẩu Không được Lưu trữ dưới dạng các Chuỗi String (Why Passwords Must Not Be Stored in Strings)

Các đối tượng Java `String` là bất biến (immutable) và được lưu trữ trong String Pool của JVM. Khi một chuỗi chứa mật khẩu được tạo ra, nó không thể được ghi đè thủ công hoặc thu gom rác theo yêu cầu. Chuỗi này có thể nằm lại trong bộ nhớ trong một khoảng thời gian không xác định — cho đến khi bộ thu gom rác quyết định thu dọn nó, quá trình này có thể mất vài giây, vài phút hoặc vài giờ tùy thuộc vào áp lực bộ nhớ heap và cấu hình GC.

Trong thời gian này, mật khẩu tồn tại dưới dạng văn bản rõ (clear-text) trong bộ nhớ heap của JVM. Nếu kẻ tấn công có được bản sao chụp bộ nhớ heap (heap dump) (thông qua `jmap`, kiểm tra bộ nhớ tiến trình, hoặc khai thác lỗ hổng làm lộ tệp dump heap trong sản xuất), họ có thể dễ dàng tìm thấy mật khẩu bằng cách quét các chuỗi ASCII có thể in được trong tệp dump.

Ngược lại, một mảng `char[]` có thể được ghi đè giá trị bằng số 0 hoặc ký tự null ngay sau khi sử dụng bằng cách gọi `Arrays.fill(passwordChars, '\0')`, loại bỏ mật khẩu khỏi bộ nhớ ngay khi không còn cần thiết và trước khi bộ thu gom rác chạy.

### Mô hình Tư duy: String Pool giữ lại bộ nhớ vs char[] ghi đè giải phóng bộ nhớ (Mental Model: String Pool Retention vs. char[] Zeroing)
```
[String — Không thể xóa bỏ lập tức]
String password = "mySecret";
    → JVM String Pool: ["mySecret"] ← vẫn nằm trong bộ nhớ cho đến khi GC chạy
    → GC có thể không chạy trong nhiều phút/giờ
    → Tệp dump heap thu giữ plaintext "mySecret"

[char[] — Có thể ghi đè xóa ngay lập tức]
char[] password = "mySecret".toCharArray();
// ... sử dụng password ...
Arrays.fill(password, '\0');  // Ghi đè bộ nhớ ngay lập tức
    → Vùng nhớ: ['\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0']
    → Tệp dump heap chỉ thu giữ các byte null → Mật khẩu không thể bị khôi phục
```

### Ví Dụ Mã Nguồn: Xử lý Mật khẩu An sau với char[] (Code Example: Safe Password Handling with char[])
```java
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;

public class SafePasswordHandling {
    public static byte[] hashPassword(char[] password) throws Exception {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        // PBEKeySpec nhận char[] (không phải String) theo thiết kế
        PBEKeySpec spec = new PBEKeySpec(password, salt, 310000, 256);
        Arrays.fill(password, '\0'); // Ghi đè xóa mật khẩu ngay sau khi sử dụng

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return factory.generateSecret(spec).getEncoded();
    }
}
```

### Chuỗi Nhân Quả (Cause-Effect Chain)
Mật khẩu được lưu trữ trong trường `String` &rarr; Chuỗi là bất biến và được giữ lại trong heap của JVM &rarr; Trì hoãn thu gom rác (GC) khiến mật khẩu có thể truy cập được trong bộ nhớ &rarr; Heap dump làm lộ bản rõ &rarr; Mật khẩu lưu trữ trong `char[]` &rarr; `Arrays.fill()` ghi đè bộ nhớ ngay sau khi sử dụng &rarr; Không còn dữ liệu mật khẩu đọc được trong heap.

## Liên Kết Tham Khảo (Reference Links)

- https://docs.oracle.com/javase/tutorial/security/ (Tài liệu Hướng dẫn Bảo mật Java)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/security/MessageDigest.html (Tài liệu API JavaDoc MessageDigest)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/security/SecureRandom.html (Tài liệu API JavaDoc SecureRandom)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Base64.html (Tài liệu API JavaDoc Base64)
