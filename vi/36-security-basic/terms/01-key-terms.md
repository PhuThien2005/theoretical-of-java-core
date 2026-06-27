# Thuật Ngữ Bảo Mật Cơ Bản (Basic Security Terms)

Sử dụng file này khi một từ trong phần lý thuyết có cảm giác quá ngắn gọn. Mỗi thuật ngữ đều đi kèm với ý nghĩa, tầm quan trọng, điểm dễ gây nhầm lẫn và một ví dụ nhỏ.

## Lập Trình An Toàn (Secure Coding)

Lập trình an toàn (secure coding) là hoạt động viết phần mềm nhằm giảm thiểu tối đa các lỗ hổng bảo mật bằng cách xác thực đầu vào, bảo vệ dữ liệu nhạy cảm, sử dụng các API an toàn, và tuân thủ các nguyên tắc đặc quyền tối thiểu.

- **Tại sao điều này quan trọng**: Hầu hết các vụ xâm nhập bảo mật đều khai thác các lỗi lập trình (chèn mã - injection, tràn bộ đệm - buffer overflow, giải tuần tự hóa không an sau - insecure deserialization). Lập trình an toàn giúp ngăn chặn các lỗi này ngay từ cấp độ mã nguồn, chứ không chỉ ở vành đai mạng.
- **Nhầm lẫn phổ biến**: Các nhà phát triển thường nghĩ rằng bảo mật chỉ là về tường lửa hoặc cấu hình mạng, mà không nhận ra rằng hầu hết các lỗi CVE (Lỗi và Lỗ hổng Phổ biến) đều do các lỗi mã nguồn ở tầng ứng dụng gây ra.
- **Ví dụ nhỏ**: Việc sử dụng `PreparedStatement` thay vì `Statement` cho các truy vấn SQL giúp loại bỏ hoàn toàn lỗi tấn công chèn mã SQL ở cấp độ code.

## Băm (Hashing)

Một hàm băm mật mã (cryptographic hash function) là một hàm toán học một chiều giúp ánh xạ dữ liệu đầu vào có kích thước tùy ý thành một chuỗi đại diện (digest) có kích thước cố định. Từ chuỗi đại diện này, việc khôi phục lại dữ liệu đầu vào ban đầu là bất khả thi về mặt tính toán.

- **Tại sao điều này quan trọng**: Hàm băm cho phép lưu trữ mật khẩu mà không cần lưu trữ văn bản thuần túy (lưu mã băm, so sánh mã băm), kiểm tra tính toàn vẹn của dữ liệu (mã checksum của tệp), và chữ ký số (băm rồi ký).
- **Nhầm lẫn phổ biến**: Băm không phải là mã hóa. Băm được thiết kế để không thể đảo ngược (không thể giải mã). Mã hóa có thể đảo ngược bằng cách sử dụng khóa. Hàm băm của `HashMap` KHÔNG phải là băm mật mã — nó được thiết kế để phân phối các phần tử, chứ không phải cho bảo mật.
- **Ví dụ nhỏ**: `MessageDigest.getInstance("SHA-256").digest("password".getBytes())` tạo ra một chuỗi băm đại diện dài 32 byte.

## Base64

Base64 là một sơ đồ mã hóa chuyển đổi nhị phân sang văn bản (binary-to-text), chuyển đổi các byte nhị phân tùy ý thành bảng chữ cái ASCII gồm 64 ký tự (A–Z, a–z, 0–9, +, /).

- **Tại sao điều này quan trọng**: Nhiều giao thức dựa trên văn bản (HTTP, JSON, email) không thể truyền tải trực tiếp các byte nhị phân thô. Base64 cho phép dữ liệu nhị phân (hình ảnh, chứng chỉ, khóa) được nhúng an toàn vào các payload văn bản.
- **Nhầm lẫn phổ biến**: Base64 KHÔNG phải là mã hóa. Bất kỳ ai có dữ liệu được mã hóa đều có thể đảo ngược giải mã nó một cách cực kỳ dễ dàng. Việc sử dụng Base64 không giúp bảo vệ tính bảo mật của dữ liệu dưới bất kỳ hình thức nào.
- **Ví dụ nhỏ**: `Base64.getEncoder().encodeToString("Hello".getBytes())` trả về `"SGVsbG8="` — và bất kỳ ai cũng có thể giải mã nó.

## Mã Hóa (Encryption)

Mã hóa là một chuyển đổi có thể đảo ngược từ văn bản thuần túy (plaintext) thành văn bản mật mã (ciphertext) bằng cách sử dụng thuật toán và khóa mật mã (như AES), sao cho chỉ những ai có khóa chính xác mới có thể giải mã văn bản mật mã trở lại văn bản thuần túy ban đầu.

- **Tại sao điều này quan trọng**: Mã hóa bảo vệ tính bảo mật của dữ liệu trong quá trình truyền tải (TLS/HTTPS) và khi lưu trữ (cơ sở dữ liệu được mã hóa, tệp được mã hóa). Nếu không có mã hóa, bất kỳ ai giám sát mạng đều có thể đọc dữ liệu.
- **Nhầm lẫn phổ biến**: Mã hóa thường bị nhầm lẫn với băm. Khác biệt chính: băm là một chiều và tạo ra một chuỗi đại diện có kích thước cố định; mã hóa là hai chiều và bảo toàn dữ liệu để người nhận có thể khôi phục lại.
- **Ví dụ nhỏ**: `Cipher.getInstance("AES/CBC/PKCS5Padding")` triển khai mã hóa AES — cùng một khóa được sử dụng để mã hóa phải được sử dụng để giải mã.

## KeyStore

`KeyStore` là kho lưu trữ an toàn của Java dành cho các khóa mật mã và chứng chỉ số. Nó có thể lưu trữ các khóa riêng tư (private key), các chứng chỉ khóa công khai (public key certificate), và các khóa bí mật (khóa đối xứng) dưới dạng mã hóa trên đĩa.

- **Tại sao điều này quan trọng**: Các ứng dụng sử dụng TLS/HTTPS cần lưu trữ các chứng chỉ SSL và các khóa riêng tư một cách an toàn. `KeyStore` cung cấp một kho lưu trữ được mã hóa và bảo vệ bằng mật khẩu mà `SSLContext` của JVM có thể sử dụng trực tiếp.
- **Nhầm lẫn phổ biến**: Các nhà phát triển đôi khi viết cứng các khóa riêng tư trong mã nguồn dưới dạng chuỗi. `KeyStore` tồn tại chính xác để tránh điều này — các khóa được lưu trữ dưới dạng mã hóa và được tải thông qua một API bảo vệ bằng mật khẩu.
- **Ví dụ nhỏ**: `KeyStore.getInstance("PKCS12")` tải một kho chứng chỉ `.p12` hoặc `.pfx` thường được sử dụng để xác thực máy khách và máy chủ TLS.

## TLS

Bảo mật tầng truyền tải (Transport Layer Security - TLS) là một giao thức mật mã cung cấp tính năng xác thực, bảo mật và toàn vẹn cho các thông tin liên lạc mạng. HTTPS là HTTP chạy trên giao thức TLS.

- **Tại sao điều này quan trọng**: Nếu không có TLS, kẻ tấn công thực hiện cuộc tấn công xen giữa (man-in-the-middle) có thể đọc, chặn hoặc sửa đổi lưu lượng mạng dưới dạng văn bản rõ (cleartext). TLS ngăn chặn điều này bằng cách thiết lập một đường truyền mã hóa với việc xác thực chứng chỉ từ cả hai phía.
- **Nhầm lẫn phổ biến**: TLS thường bị nhầm với SSL. SSL là tiền thân đã lỗi thời của TLS. Các hệ thống hiện đại sử dụng TLS 1.2 hoặc TLS 1.3. Việc nói "SSL" trong ngữ cảnh Java thường ám chỉ TLS 1.2+ thông qua `SSLContext`.
- **Ví dụ nhỏ**: `SSLContext.getInstance("TLS")` khởi tạo một ngữ cảnh TLS để sử dụng trong `HttpsURLConnection` or `SSLSocket`.

## Xác Thực Đầu Vào (Input Validation)

Xác thực đầu vào (input validation) là hoạt động kiểm tra tất cả các đầu vào từ bên ngoài (dữ liệu người dùng, nội dung tệp, tin nhắn mạng, các biến môi trường) đối chiếu với các quy tắc được định nghĩa trước khi xử lý chúng.

- **Tại sao điều này quan trọng**: Hầu hết các cuộc tấn công chèn mã (SQL, XSS, LDAP, chèn lệnh) thành công là do các ứng dụng tin tưởng vào dữ liệu đầu vào không an toàn. Việc xác thực sẽ giúp loại bỏ dữ liệu độc hại trước khi nó tiếp cận các tầng xử lý nhạy cảm bên dưới.
- **Nhầm lẫn phổ biến**: Các nhà phát triển thường chỉ xác thực ở tầng giao diện UI và bỏ qua xác thực phía máy chủ (server-side), giả định rằng UI đã thực thi các ràng buộc. Kẻ tấn công có thể bỏ qua UI và gửi các yêu cầu HTTP thô trực tiếp với các payload tùy ý.
- **Ví dụ nhỏ**: `if (input.matches("[a-zA-Z0-9]{1,50}")) { ... }` — xác thực regex kiểu danh sách trắng (allowlist), từ chối mọi thứ không khớp với khuôn mẫu.
