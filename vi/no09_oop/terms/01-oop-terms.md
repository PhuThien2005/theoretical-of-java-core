# Thuật Ngữ OOP (OOP Terms)

File này chi tiết các thuật ngữ chính liên quan đến Lập trình hướng đối tượng (Object-Oriented Programming - OOP) trong Java.

## Lớp (Class)

Một bản thiết kế logic hoặc khuôn mẫu định nghĩa các biến và phương thức chung cho tất cả các đối tượng cùng loại.

## Đối Tượng (Object)

Một thực thể (instance) của một lớp tồn tại vật lý trong bộ nhớ heap, có trạng thái riêng (các trường - fields) và hành vi riêng (các phương thức - methods).

## Hàm Khởi Tạo (Constructor)

Một khối mã tương tự như một phương thức được gọi khi một thực thể của một đối tượng được tạo ra. Mục đích chính của nó là khởi tạo trạng thái của đối tượng mới.

## Đóng Gói (Encapsulation)

Thực hành nhóm các biến và phương thức lại với nhau thành một lớp, và che giấu dữ liệu (data hiding) chi tiết nội bộ để hạn chế sự truy cập trực tiếp từ các thành phần bên ngoài.

## Kế Thừa (Inheritance)

Một kỹ thuật thiết kế trong đó một lớp con (subclass) kế thừa các thuộc tính và hành vi từ một lớp cha (superclass), thúc đẩy việc tái sử dụng mã nguồn và tạo thành mối quan hệ IS-A (là một).

## Đa Hình (Polymorphism)

Khả năng một hành động hoặc đối tượng cư xử khác nhau trong các ngữ cảnh khác nhau. Trong Java, điều này được thể hiện qua nạp chồng (overloading) ở thời gian biên dịch (compile-time) và ghi đè (overriding) ở thời gian chạy (runtime).

## Trừu Tượng (Abstraction)

Khái niệm biểu diễn các đặc trưng thiết yếu mà không bao gồm các chi tiết nền tảng hoặc giải thích chi tiết. Nó xác định một đối tượng làm "cái gì" thay vì làm điều đó "như thế nào".

## Phạm Vi Truy Cập (Access Modifier)

Các từ khóa (`private`, `protected`, `public`, hoặc mặc định) dùng để thiết lập khả năng hiển thị và quyền truy cập của các lớp, phương thức, hàm khởi tạo và các trường.

## Nạp Chồng Phương Thức (Method Overloading)

Khai báo nhiều phương thức trong cùng một lớp có cùng tên nhưng khác chữ ký phương thức (danh sách tham số - parameter list). Việc này được phân giải tại thời điểm biên dịch.

## Ghi Đè Phương Thức (Method Overriding)

Định nghĩa lại một phương thức của lớp cha trong một lớp con với tên, kiểu trả về và các tham số hoàn toàn giống hệt. Việc này được phân giải tại thời điểm chạy.

## Định Tuyến Phương Thức Động (Dynamic Method Dispatch)

Quá trình tra cứu tại thời điểm chạy nơi JVM phân giải một lời gọi đến phương thức được ghi đè dựa trên kiểu thực tế của đối tượng trong bộ nhớ heap, chứ không phải kiểu của biến tham chiếu.

## Vấn Đề Kim Cương (Diamond Problem)

Sự mơ hồ phát sinh khi một lớp con kế thừa từ hai lớp cha mà cả hai đều định nghĩa một phương thức có cùng chữ ký. Các lớp Java tránh điều này bằng cách chỉ cho phép đơn kế thừa.

## Ép Kiểu Lên (Upcasting)

Việc ép kiểu ngầm định một tham chiếu lớp con sang kiểu lớp cha. Việc này luôn an toàn và tự động.

## Ép Kiểu Xuống (Downcasting)

Việc ép kiểu tường minh một tham chiếu lớp cha trở lại kiểu lớp con. Việc này yêu cầu phải kiểm tra kiểu dữ liệu (như sử dụng `instanceof`) để tránh lỗi `ClassCastException` tại thời điểm chạy.

## Lớp Trừu Tượng (Abstract Class)

Một lớp được đánh dấu bằng từ khóa `abstract` không thể khởi tạo trực tiếp và có thể chứa các phương thức trừu tượng (các phương thức không có phần thân triển khai).

## Giao Diện (Interface)

Một hợp đồng chỉ định một tập hợp các phương thức mà một lớp phải triển khai. Nó không có trạng thái thực thể và hỗ trợ đa kế thừa hành vi.

## Phương Thức Mặc Định (Default Method)

Một phương thức được định nghĩa bên trong một giao diện với từ khóa `default`, cung cấp một triển khai mặc định cụ thể. Được giới thiệu từ Java 8 để cho phép giao diện phát triển tiến hóa mà không làm hỏng các mã nguồn hiện có.
