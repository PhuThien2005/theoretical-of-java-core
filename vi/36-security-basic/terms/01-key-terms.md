# Thuật ngữ Bảo mật cơ bản (Basic Security Terms)

Hãy sử dụng tài liệu này khi một từ ngữ trong phần lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều đi kèm ý nghĩa, tầm quan trọng, sự nhầm lẫn phổ biến và một ví dụ nhỏ.

## Lập trình an toàn (Secure coding)

Lập trình an toàn (Secure coding) là việc viết phần mềm nhằm giảm thiểu tối đa các lỗ hổng (vulnerabilities) bảo mật bằng cách kiểm thực dữ liệu đầu vào, bảo vệ dữ liệu nhạy cảm, sử dụng các API an toàn và tuân thủ các nguyên tắc đặc quyền tối thiểu (least-privilege principles).

**Tại sao nó quan trọng**: Hầu hết các vụ vi phạm bảo mật (security breaches) đều khai thác các lỗi lập trình (chèn mã (injection), tràn bộ đệm (buffer overflows), giải tuần tự hóa không an toàn (insecure deserialization)). Lập trình an toàn ngăn chặn những điều này ngay từ cấp độ mã nguồn, chứ không chỉ ở vành đai mạng (network perimeter).

**Sự nhầm lẫn phổ biến**: Các lập trình viên thường nghĩ rằng bảo mật chỉ xoay quanh tường lửa (firewalls) hoặc cấu hình mạng, mà không nhận ra rằng hầu hết các lỗi bảo mật phổ biến CVE (Common Vulnerabilities and Exposures) đều bắt nguồn từ lỗi mã nguồn ở tầng ứng dụng.

**Ví dụ nhỏ**: Việc sử dụng `PreparedStatement` thay vì `Statement` cho các truy vấn SQL giúp loại bỏ lỗi chèn mã SQL (SQL injection) ngay ở cấp độ mã nguồn.

## Băm (Hashing)

Một hàm băm mã hóa (cryptographic hash function) là một hàm toán học một chiều (one-way) ánh xạ dữ liệu đầu vào có kích thước bất kỳ thành một giá trị băm (digest) có kích thước cố định. Khi đã có giá trị băm, về mặt tính toán là không thể khôi phục lại dữ liệu đầu vào ban đầu.

**Tại sao nó quan trọng**: Băm (Hashing) cho phép lưu trữ mật khẩu (password storage) mà không cần lưu trữ dưới dạng văn bản rõ (plaintext) (lưu giá trị băm, so sánh giá trị băm), kiểm tra tính toàn vẹn dữ liệu (mã hash kiểm tra tệp - checksum) và chữ ký số (băm rồi ký).

**Sự nhầm lẫn phổ biến**: Băm không phải là mã hóa (encryption). Băm được thiết kế để không thể đảo ngược (bất khả nghịch). Mã hóa có thể đảo ngược bằng cách sử dụng một khóa (key). Hàm băm của `HashMap` KHÔNG phải là hàm băm mã hóa — nó được thiết kế để phân bổ dữ liệu vào các bucket chứ không phải vì mục đích bảo mật.

**Ví dụ nhỏ**: `MessageDigest.getInstance("SHA-256").digest("password".getBytes())` tạo ra một giá trị băm dài 32 byte.

## Base64 (Base64)

Base64 là một lược đồ mã hóa nhị phân sang văn bản (binary-to-text encoding scheme) chuyển đổi các byte nhị phân tùy ý thành bảng chữ cái ASCII gồm 64 ký tự (A–Z, a–z, 0–9, +, /).

**Tại sao nó quan trọng**: Nhiều giao thức dạng văn bản (text-based protocols) (HTTP, JSON, email) không thể truyền tải các byte nhị phân thô. Base64 cho phép nhúng dữ liệu nhị phân (hình ảnh, chứng chỉ, khóa) một cách an toàn vào các chuỗi dữ liệu dạng văn bản.

**Sự nhầm lẫn phổ biến**: Base64 KHÔNG phải là mã hóa bảo mật. Bất kỳ ai có dữ liệu được mã hóa này đều có thể đảo ngược nó một cách dễ dàng. Sử dụng Base64 không giúp bảo vệ tính bí mật (confidentiality) dưới bất kỳ hình thức nào.

**Ví dụ nhỏ**: `Base64.getEncoder().encodeToString("Hello".getBytes())` trả về `"SGVsbG8="` — và bất kỳ ai cũng có thể giải mã nó.

## Mã hóa (Encryption)

Mã hóa (Encryption) là một phép biến đổi có thể đảo ngược của văn bản rõ (plaintext) thành văn bản mã hóa (ciphertext) bằng cách sử dụng một khóa mã hóa (cryptographic key) và thuật toán (như AES), sao cho chỉ những người có khóa chính xác mới có thể giải mã văn bản mã hóa trở lại thành văn bản rõ ban đầu.

**Tại sao nó quan trọng**: Mã hóa bảo vệ tính bí mật (confidentiality) của dữ liệu trong quá trình truyền tải (TLS/HTTPS) và khi lưu trữ (cơ sở dữ liệu được mã hóa, tệp được mã hóa). Không có mã hóa, bất kỳ ai giám sát mạng đều có thể đọc được dữ liệu.

**Sự nhầm lẫn phổ biến**: Mã hóa thường bị nhầm lẫn với băm. Sự khác biệt chính: băm là một chiều và tạo ra một giá trị băm có kích thước cố định; mã hóa là hai chiều và giữ nguyên dữ liệu có thể khôi phục bởi người nhận.

**Ví dụ nhỏ**: `Cipher.getInstance("AES/CBC/PKCS5Padding")` triển khai mã hóa AES — cùng một khóa được sử dụng để mã hóa thì phải được sử dụng để giải mã.

## KeyStore (KeyStore)

`KeyStore` là kho chứa an toàn của Java dành cho các khóa mã hóa (cryptographic keys) và chứng chỉ kỹ thuật số (digital certificates). Nó có thể lưu trữ các khóa riêng tư (private keys), chứng chỉ khóa công khai (public key certificates) và các khóa bí mật (secret/symmetric keys) dưới dạng mã hóa trên đĩa.

**Tại sao nó quan trọng**: Các ứng dụng sử dụng TLS/HTTPS cần lưu trữ các chứng chỉ SSL và khóa riêng tư một cách an toàn. `KeyStore` cung cấp một kho lưu trữ được mã hóa và bảo vệ bằng mật khẩu mà `SSLContext` của JVM có thể tiêu thụ trực tiếp.

**Sự nhầm lẫn phổ biến**: Các lập trình viên đôi khi code cứng (hardcode) các khóa riêng tư trong mã nguồn dưới dạng chuỗi. `KeyStore` tồn tại chính xác để tránh điều này — các khóa được lưu trữ dưới dạng mã hóa và được tải thông qua một API được bảo vệ bằng mật khẩu.

**Ví dụ nhỏ**: `KeyStore.getInstance("PKCS12")` tải một kho chứng chỉ `.p12` hoặc `.pfx` thường được sử dụng cho việc xác thực client và server trong TLS.

## TLS (TLS)

Bảo mật tầng truyền vận (Transport Layer Security - TLS) là một giao thức mã hóa cung cấp tính năng xác thực, tính bảo mật và tính toàn vẹn cho giao tiếp mạng. HTTPS là HTTP chạy trên nền TLS.

**Tại sao nó quan trọng**: Không có TLS, kẻ tấn công thực hiện cuộc tấn công xen giữa (man-in-the-middle attack) có thể đọc, chặn hoặc sửa đổi lưu lượng mạng dưới dạng văn bản rõ. TLS ngăn chặn điều này bằng cách thiết lập một đường ống mã hóa với sự xác thực chứng chỉ lẫn nhau.

**Sự nhầm lẫn phổ biến**: TLS thường bị nhầm lẫn với SSL. SSL là phiên bản tiền nhiệm đã lỗi thời của TLS. Các hệ thống hiện đại sử dụng TLS 1.2 hoặc TLS 1.3. Nói "SSL" trong ngữ cảnh Java thường có nghĩa là TLS 1.2+ thông qua `SSLContext`.

**Ví dụ nhỏ**: `SSLContext.getInstance("TLS")` khởi tạo một ngữ cảnh TLS để sử dụng trong `HttpsURLConnection` hoặc `SSLSocket`.

## Kiểm thực dữ liệu đầu vào (Input validation)

Kiểm thực dữ liệu đầu vào (Input validation) là việc kiểm tra tất cả các dữ liệu đầu vào bên ngoài (dữ liệu người dùng, nội dung tệp, tin nhắn mạng, biến môi trường) với các quy tắc được định nghĩa trước khi xử lý chúng.

**Tại sao nó quan trọng**: Hầu hết các cuộc tấn công chèn mã (SQL, XSS, LDAP, chèn lệnh hệ điều hành) thành công vì ứng dụng tin tưởng dữ liệu đầu vào không đáng tin cậy. Kiểm thực loại bỏ dữ liệu độc hại trước khi nó tiếp cận các tầng xử lý nhạy cảm.

**Sự nhầm lẫn phổ biến**: Các lập trình viên thường chỉ kiểm thực ở tầng giao diện người dùng (UI) và bỏ qua việc kiểm thực phía server, giả định rằng UI đã thực thi các ràng buộc. Kẻ tấn công có thể dễ dàng vượt qua UI và gửi các yêu cầu HTTP thô với dữ liệu độc hại trực tiếp lên server.

**Ví dụ nhỏ**: `if (input.matches("[a-zA-Z0-9]{1,50}")) { ... }` — kiểm thực bằng danh sách cho phép (allowlist) với biểu thức chính quy (regex) để từ chối bất kỳ dữ liệu nào không khớp với mẫu.
