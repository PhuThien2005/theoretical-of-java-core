import re

with open('/home/fhu_thjen/projects/learning-java/vi/no10_modifiers/theory/03-static-block-concepts.md', 'r') as f:
    content = f.read()

# Remove the boilerplate "Sử dụng khái niệm này để dự đoán..."
content = re.sub(
    r'Sử dụng khái niệm này để dự đoán quy tắc chính xác của Java, các dạng được phép và các lỗi có thể xảy ra\. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ tên gọi\.\n\n',
    '',
    content
)

# Remove the boilerplate "Kiểm tra thực tế: ... Ví dụ nhỏ hoặc mô hình tư duy: ..."
content = re.sub(
    r'Kiểm tra thực tế:\n\n- Định nghĩa `[^`]+` trong một câu\.\n- Nhận biết `[^`]+` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn\.\n- Giải thích một lỗi logic, giới hạn hoặc sự đánh đổi liên quan đến `[^`]+`\.\n\nVí dụ nhỏ hoặc mô hình tư duy:\n\n- [^\n]+\n\n?',
    '',
    content
)

# Insert Naming Convention and Thread Safety before "Ví Dụ Mã Nguồn Biến Final"
replacement_final_var = """#### Quy Ước Đặt Tên (Naming Convention)
Khi kết hợp `static final` để tạo hằng số toàn cục, quy ước bắt buộc trong Java là sử dụng chữ in hoa phân cách bằng dấu gạch dưới (UPPER_SNAKE_CASE). Ví dụ: `public static final int MAX_USERS = 500;`.

#### Thread-Safety Của Biến Final (JMM Guarantee)
Biến `final` có một ý nghĩa đặc biệt trong Mô hình Bộ nhớ Java (JMM). JMM đảm bảo rằng nếu một biến `final` được khởi tạo trong constructor, thì **bất kỳ luồng nào** khi nhận được tham chiếu của đối tượng đó cũng sẽ nhìn thấy giá trị chính xác của biến `final` (miễn là không để lọt tham chiếu đối tượng ra ngoài `this` trước khi constructor hoàn tất). Điều này làm cho các đối tượng bất biến (immutable objects) tự động đạt chuẩn thread-safe.

#### Ví Dụ Mã Nguồn Biến Final"""
content = content.replace("#### Ví Dụ Mã Nguồn Biến Final", replacement_final_var)

# Insert Redundancy before "Ví Dụ Mã Nguồn Phương Thức Final"
replacement_final_method = """#### Sự dư thừa `private final`
Mọi phương thức `private` đều không thể bị lớp con nhìn thấy, do đó hiển nhiên không thể bị ghi đè. Việc khai báo một phương thức là `private final` là hoàn toàn hợp lệ về mặt cú pháp nhưng thừa thãi. Trình biên dịch ngầm coi mọi phương thức `private` đều là `final`.

#### Ví Dụ Mã Nguồn Phương Thức Final"""
content = content.replace("#### Ví Dụ Mã Nguồn Phương Thức Final", replacement_final_method)

# Insert String/Integer before "Ví Dụ Mã Nguồn Lớp Final"
replacement_final_class = """#### Tại sao `String` và `Integer` là lớp final?
Các lớp cốt lõi trong Java như `String`, `Integer`, `Double` đều được thiết kế là lớp `final`. Quyết định này nhằm:
1. **Bảo mật (Security)**: Ngăn chặn hacker tạo một lớp con mạo danh `String` với hành vi độc hại để vượt qua các khâu kiểm tra an ninh hệ thống.
2. **Bất biến (Immutability)**: Đảm bảo trạng thái của chuỗi không bao giờ bị thay đổi sau khi tạo, giúp chia sẻ chuỗi an toàn trong môi trường đa luồng và String Pool.

#### Ví Dụ Mã Nguồn Lớp Final"""
content = content.replace("#### Ví Dụ Mã Nguồn Lớp Final", replacement_final_class)

with open('/home/fhu_thjen/projects/learning-java/vi/no10_modifiers/theory/03-static-block-concepts.md', 'w') as f:
    f.write(content)

