# Thuật Ngữ Về Chuỗi (String Terms)

Tệp này chi tiết các thuật ngữ cốt lõi liên quan đến chuỗi (strings) trong Java.

## Bộ Nhớ Đệm Chuỗi (String Pool)

Một vùng nhớ đặc biệt nằm bên trong Heap nơi JVM lưu giữ các hằng chuỗi (string literals) duy nhất. Nó giúp tiết kiệm dung lượng bộ nhớ bằng cách chia sẻ các đối tượng chuỗi giống hệt nhau thay vì nhân bản chúng ra nhiều lần.

## Tính Bất Biến (Immutability)

Trạng thái của một đối tượng khi giá trị hoặc dữ liệu nội bộ của nó không thể bị sửa đổi sau khi đã được tạo ra. Lớp `String` là bất biến, nghĩa là bất kỳ phương thức nào có vẻ như sửa đổi chuỗi thì thực tế đều trả về một đối tượng `String` mới hoàn toàn.

## Hằng Chuỗi (String Literal)

Một chuỗi các ký tự được bao bọc trong dấu ngoặc kép (ví dụ: `"hello"`) viết trực tiếp trong mã nguồn. Các hằng chuỗi tự động được trình biên dịch và JVM lưu trữ vào String Pool.

## Lưu Vào String Pool (Interning)

Quá trình đưa một chuỗi vào String Pool một cách chủ động. Nếu một chuỗi được tạo ra lúc runtime (ví dụ: thông qua dữ liệu nhập của người dùng hoặc gọi `new String()`), việc gọi phương thức `.intern()` của nó sẽ trả về tham chiếu đến chính chuỗi đó từ String Pool, và sẽ thêm chuỗi đó vào pool trước nếu nó chưa tồn tại ở đó.

## Chuỗi Nén (Compact Strings - Java 9+)

Một cơ chế tối ưu hóa nội bộ của JVM. Trước phiên bản Java 9, các chuỗi được lưu dưới dạng mảng `char[]` (chiếm 2 bytes cho mỗi ký tự). Từ Java 9 trở đi, các chuỗi được lưu dưới dạng mảng `byte[]` đi kèm một cờ mã hóa (coder flag - chiếm 1 byte cho ký tự chuẩn Latin-1, và 2 bytes cho ký tự UTF-16), giúp giảm lượng tiêu thụ bộ nhớ lên tới 50% đối với các ngôn ngữ phương Tây thông thường.

## Khối Văn Bản (Text Block)

Một cú pháp khai báo hằng chuỗi nhiều dòng được giới thiệu từ Java 15, khai báo bằng ba dấu ngoặc kép (`"""`). Nó giúp giữ nguyên định dạng của văn bản và loại bỏ sự cần thiết phải escape (sử dụng dấu gạch chéo ngược) đối với hầu hết các ký tự thông dụng (như dấu ngoặc kép và dấu xuống dòng).

## StringBuilder

Một chuỗi ký tự khả biến (mutable), không an toàn luồng (non-thread-safe). Lớp này được thiết kế để thao tác chuỗi hiệu năng cao trong môi trường đơn luồng (như nối chuỗi và chèn ký tự trong các vòng lặp).

## StringBuffer

Một chuỗi ký tự khả biến (mutable), an toàn luồng (thread-safe). Tất cả các phương thức chính của lớp này đều được đồng bộ hóa (synchronized), giúp đảm bảo tính chính xác khi được truy cập bởi nhiều luồng khác nhau nhưng đồng thời cũng làm tăng chi phí hiệu năng.
