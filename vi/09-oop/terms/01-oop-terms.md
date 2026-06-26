# Thuật Ngữ OOP (OOP Terms)

Tài liệu này trình bày chi tiết các thuật ngữ chính liên quan đến Lập Trình Hướng Đối Tượng (Object-Oriented Programming - OOP) trong Java.

## Lớp (Class)

Một bản thiết kế hoặc mẫu logic định nghĩa các biến và phương thức chung cho tất cả các đối tượng thuộc một loại nhất định.

## Đối Tượng (Object)

Một thể hiện (instance) của lớp tồn tại về mặt vật lý trong bộ nhớ (heap), có trạng thái riêng (các trường - fields) và hành vi riêng (các phương thức - methods).

## Hàm Khởi Tạo (Constructor)

Một khối mã tương tự như phương thức, được gọi khi một thể hiện đối tượng được tạo ra. Mục đích chính của nó là khởi tạo trạng thái của đối tượng mới.

## Đóng Gói (Encapsulation)

Thực hành nhóm các biến và phương thức lại với nhau thành một lớp, và ẩn các chi tiết nội bộ (ẩn dữ liệu - data hiding) để hạn chế truy cập trực tiếp từ các thành phần bên ngoài.

## Kế Thừa (Inheritance)

Một kỹ thuật thiết kế trong đó lớp con (subclass) kế thừa các thuộc tính và hành vi từ lớp cha (superclass), thúc đẩy tái sử dụng mã và hình thành mối quan hệ IS-A (là một loại).

## Đa Hình (Polymorphism)

Khả năng của một hành động hoặc đối tượng có thể hoạt động khác nhau trong các ngữ cảnh khác nhau. Trong Java, điều này được thể hiện qua nạp chồng - overloading (compile-time) và ghi đè - overriding (runtime).

## Trừu Tượng Hóa (Abstraction)

Khái niệm biểu diễn các tính năng thiết yếu mà không bao gồm các chi tiết hoặc giải thích nền tảng. Nó xác định "cái gì" mà một đối tượng làm hơn là "làm thế nào" nó làm điều đó.

## Bộ Điều Chỉnh Truy Cập (Access Modifier)

Các từ khóa (`private`, `protected`, `public`, hoặc default) thiết lập mức độ hiển thị và quyền truy cập của các lớp, phương thức, hàm khởi tạo và trường.

## Nạp Chồng Phương Thức (Method Overloading)

Khai báo nhiều phương thức trong cùng một lớp với cùng tên nhưng chữ ký (danh sách tham số) khác nhau. Được giải quyết tại compile-time.

## Ghi Đè Phương Thức (Method Overriding)

Định nghĩa lại một phương thức của lớp cha trong lớp con với đúng cùng tên, kiểu trả về và tham số. Được giải quyết tại runtime.

## Phân Phối Phương Thức Động (Dynamic Method Dispatch)

Quá trình tra cứu runtime trong đó JVM giải quyết lời gọi đến một phương thức bị ghi đè dựa trên kiểu thực tế của đối tượng trong heap, không phải kiểu của biến tham chiếu.

## Bài Toán Hình Thoi (Diamond Problem)

Sự mơ hồ phát sinh khi một lớp con kế thừa từ hai lớp cha đều định nghĩa một phương thức với cùng chữ ký. Java tránh điều này bằng cách chỉ cho phép kế thừa đơn (single inheritance).

## Ép Kiểu Lên (Upcasting)

Chuyển đổi ngầm định một tham chiếu lớp con sang kiểu lớp cha. Luôn an toàn và tự động.

## Ép Kiểu Xuống (Downcasting)

Chuyển đổi tường minh một tham chiếu lớp cha trở lại kiểu lớp con. Cần kiểm tra kiểu (như `instanceof`) để tránh `ClassCastException` tại runtime.

## Lớp Trừu Tượng (Abstract Class)

Một lớp được đánh dấu bằng `abstract` không thể được khởi tạo và có thể chứa các phương thức trừu tượng (phương thức không có phần thân triển khai).

## Interface

Một hợp đồng chỉ định tập hợp các phương thức mà một lớp phải triển khai. Không có trạng thái instance và hỗ trợ đa kế thừa hành vi.

## Phương Thức Mặc Định (Default Method)

Một phương thức được định nghĩa bên trong một interface với từ khóa `default` cung cấp triển khai mặc định cụ thể. Được giới thiệu trong Java 8 để cho phép phát triển interface mà không phá vỡ mã hiện có.
