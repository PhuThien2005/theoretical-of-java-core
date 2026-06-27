# Thuật Ngữ Về Chuỗi (String Terms)

Tài liệu này chi tiết hóa các thuật ngữ chính liên quan đến chuỗi ký tự (string) trong Java.

## Vùng Lưu Trữ String (String Pool)

Một vùng nhớ đặc biệt nằm bên trong bộ nhớ Heap nơi JVM lưu trữ các chuỗi tường minh (string literal) duy nhất. Nó giúp tiết kiệm bộ nhớ bằng cách chia sẻ các đối tượng chuỗi giống nhau thay vì tạo ra các bản sao trùng lặp.

## Tính Bất Biến (Immutability)

Trạng thái của một đối tượng mà giá trị hoặc dữ liệu nội bộ của nó không thể bị thay đổi sau khi được tạo ra. Lớp `String` là bất biến, nghĩa là bất kỳ phương thức nào có vẻ như đang sửa đổi chuỗi thực tế đều trả về một đối tượng `String` mới.

## Chuỗi Tường Minh (String Literal)

Một chuỗi các ký tự được đặt trong dấu ngoặc kép (ví dụ: `"hello"`) được viết trực tiếp trong mã nguồn. Các chuỗi tường minh sẽ tự động được trình biên dịch và JVM lưu trữ trong String Pool.

## Nội Hóa Chuỗi (Interning)

Quá trình đưa một chuỗi ký tự vào trong String Pool. Nếu một chuỗi được tạo ra lúc runtime (ví dụ: thông qua dữ liệu nhập của người dùng hoặc bằng từ khóa `new String()`), việc gọi phương thức `.intern()` của nó sẽ trả về một tham chiếu đến chuỗi đó từ String Pool, và sẽ thêm nó vào pool trước nếu nó chưa tồn tại ở đó.

## Chuỗi Nhỏ Gọn (Compact Strings - Java 9+)

Một cơ chế tối ưu hóa nội bộ trong JVM. Trước Java 9, các chuỗi được lưu trữ dưới dạng mảng `char[]` (chiếm 2 byte cho mỗi ký tự). Từ Java 9 trở đi, các chuỗi được lưu trữ dưới dạng mảng `byte[]` cùng với một cờ mã hóa coder flag (chiếm 1 byte cho các ký tự Latin-1, 2 byte cho các ký tự UTF-16), giúp giảm mức tiêu thụ bộ nhớ lên tới 50% đối với các ngôn ngữ phương Tây thông thường.

## Khối Văn Bản (Text Block)

Một chuỗi tường minh nhiều dòng được giới thiệu từ Java 15, được khai báo bằng ba dấu ngoặc kép (`"""`). Nó giúp giữ nguyên định dạng văn bản và loại bỏ nhu cầu sử dụng các ký tự thoát (escape character) đối với hầu hết các ký tự thông thường (như dấu ngoặc kép và dấu xuống dòng).

## StringBuilder

Một chuỗi ký tự khả biến, không an toàn đa luồng (non-thread-safe). Nó được thiết kế để thao tác với chuỗi một cách hiệu quả trong môi trường đơn luồng (chẳng hạn như thực hiện nối chuỗi hoặc chèn chuỗi trong vòng lặp).

## StringBuffer

Một chuỗi ký tự khả biến, an toàn đa luồng (thread-safe). Tất cả các phương thức chính của nó đều được đồng bộ hóa (synchronized), điều này đảm bảo tính đúng đắn khi được truy cập bởi nhiều luồng nhưng cũng làm phát sinh hao phí hiệu suất.
