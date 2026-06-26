# Thuật ngữ Hệ thống Mô-đun Java (Java Module System Terms)

Sử dụng file này khi một từ ngữ trong phần lý thuyết có cảm giác quá ngắn gọn. Mỗi thuật ngữ đều có ý nghĩa, tầm quan trọng, điểm dễ gây nhầm lẫn và một ví dụ nhỏ.

## module

Một tập hợp tự mô tả gồm mã nguồn (các gói) và dữ liệu (các tài nguyên), đi kèm với một trình mô tả mô-đun (`module-info.class`) chỉ định các phụ thuộc và quyền kiểm soát truy cập.

- **Tại sao nó quan trọng**: Nó chuyển đổi đơn vị biên dịch và thời gian chạy của Java từ một tập hợp phẳng các lớp thành một đồ thị phụ thuộc có cấu trúc, cho phép xác thực các phụ thuộc tại thời điểm biên dịch và thực thi tính đóng gói (Encapsulation) mạnh mẽ.
- **Nhầm lẫn phổ biến**: Người học thường nhầm lẫn một mô-đun với một gói (package) hoặc các dự án Maven/Gradle. Một gói là một không gian tên cho các lớp; một mô-đun tập hợp các gói và kiểm soát quyền truy cập vào chúng. Một dự án Maven là một công cụ xây dựng bao bọc, có thể chứa một hoặc nhiều mô-đun Java.
- **Ví dụ nhỏ**: Bản thân JDK được chia thành các mô-đun như `java.base`, `java.desktop`, và `java.sql`.

## module-info.java

Tệp nguồn mô tả mô-đun được đặt ở thư mục gốc của cây nguồn của mô-đun, định nghĩa tên mô-đun, các yêu cầu phụ thuộc, các gói xuất khẩu (exports), và quyền truy cập phản chiếu (reflective access).

- **Tại sao nó quan trọng**: Trình biên dịch sử dụng tệp này để thực thi các quy tắc truy cập trong quá trình biên dịch, và JVM sử dụng nó để xây dựng đồ thị mô-đun và thực thi các kiểm soát truy cập khi chạy chương trình.
- **Nhầm lẫn phổ biến**: Người học thường đặt nó bên trong một thư mục gói hoặc quên rằng nó phải sử dụng từ khóa `module` theo sau là tên mô-đun, tên này thường có định dạng tên miền đảo ngược (giống như một gói).
- **Ví dụ nhỏ**:
  ```java
  module com.myapp {
      requires java.sql;
      exports com.myapp.service;
  }
  ```

## requires

Một chỉ thị mô-đun được sử dụng trong `module-info.java` chỉ định một phụ thuộc của mô-đun hiện tại vào một mô-đun khác.

- **Tại sao nó quan trọng**: Nó thiết lập khả năng đọc hiểu (readability), cho phép mô-đun hiện tại truy cập vào các gói được xuất khẩu của mô-đun được chỉ định.
- **Nhầm lẫn phổ biến**: Nhầm lẫn chỉ thị `requires` (tham chiếu đến tên mô-đun) với các câu lệnh `import` trong các tệp nguồn Java (tham chiếu đến tên gói/lớp).
- **Ví dụ nhỏ**: `requires java.net.http;` báo cho JVM biết mô-đun này cần sử dụng mô-đun HTTP client.

## exports

Một chỉ thị mô-đun được sử dụng trong `module-info.java` giúp tất cả các lớp và giao diện public trong một gói có thể truy cập được bởi các mô-đun khác.

- **Tại sao nó quan trọng**: Đây là nền tảng của tính đóng gói. Các gói không được xuất khẩu một cách rõ ràng sẽ bị ẩn hoàn toàn và các mô-đun khác không thể truy cập được.
- **Nhầm lẫn phổ biến**: Nghĩ rằng `exports` làm cho các thành viên nội bộ/riêng tư (private) của một lớp có thể truy cập được. Nó chỉ xuất khẩu API công khai (public); các trường private vẫn không thể truy cập được.
- **Ví dụ nhỏ**: `exports com.myapp.api;` phơi bày các lớp public của gói `api`.

## opens

Một chỉ thị mô-đun được sử dụng trong `module-info.java` cho phép các mô-đun khác sử dụng cơ chế phản chiếu (reflection - bao gồm cả phản chiếu sâu trên các thành viên private) trên các gói, đồng thời chặn việc truy cập tại thời điểm biên dịch.

- **Tại sao nó quan trọng**: Cần thiết cho các framework (như Spring, Hibernate, hoặc JUnit) vốn yêu cầu phản chiếu sâu để tiêm phụ thuộc (dependency injection), ánh xạ cơ sở dữ liệu, hoặc chạy các bài kiểm thử trên các phần tử private.
- **Nhầm lẫn phổ biến**: Người học thường sử dụng `exports` khi họ thực sự cần `opens` cho cơ chế phản chiếu. Nếu bạn chỉ sử dụng `exports`, các framework sẽ ném ra ngoại lệ `InaccessibleObjectException` khi cố gắng truy cập các trường private.
- **Ví dụ nhỏ**: `opens com.myapp.domain to spring.core;` cho phép Spring truy cập phản chiếu vào các mô hình domain.

## unnamed module

Một mô-đun ngầm định được tạo bởi JVM để chứa tất cả các lớp được nạp từ classpath.

- **Tại sao nó quan trọng**: Cung cấp khả năng tương thích ngược cho các ứng dụng Java 8 cũ. Nó tự động xuất khẩu tất cả các gói của mình và có thể đọc mọi mô-đun trên module path.
- **Nhầm lẫn phổ biến**: Nghĩ rằng các mô-đun có tên (named modules) có thể khai báo `requires` đối với mô-đun không tên (unnamed module). Các mô-đun có tên không thể đọc mô-đun không tên vì nó không có tên, nghĩa là mã nguồn classpath cũ không dễ để các mã nguồn mô-đun phụ thuộc vào nếu không sử dụng các mô-đun tự động.
- **Ví dụ nhỏ**: Chạy lệnh `java -cp app.jar Main` sẽ đặt tất cả các lớp từ `app.jar` vào mô-đun không tên.

## automatic module

Một mô-đun có tên được tạo tự động bởi JVM khi một tệp JAR tiêu chuẩn (thiếu tệp `module-info.class`) được đặt trên module path.

- **Tại sao nó quan trọng**: Hoạt động như một cầu nối di trú. Nó xuất khẩu tất cả các gói của nó và có thể đọc cả các mô-đun có tên và mô-đun không tên, cho phép mã nguồn mô-đun phụ thuộc vào các tệp JAR thư viện cũ.
- **Nhầm lẫn phổ biến**: Tin rằng các mô-đun tự động yêu cầu một tệp `module-info.java`. Chúng không cần; tên của chúng được suy ra tự động từ tên tệp JAR hoặc tiêu đề manifest `Automatic-Module-Name`.
- **Ví dụ nhỏ**: Đặt tệp `guava-31.0.jar` trên module path sẽ tạo ra một mô-đun tự động tên là `guava`, có thể được khai báo phụ thuộc thông qua `requires guava;`.

## module path

Đường dẫn tìm kiếm được sử dụng bởi trình biên dịch Java và JVM để tìm các tệp JAR và thư mục mô-đun.

- **Tại sao nó quan trọng**: Khác với classpath, module path thực thi tính đóng gói nghiêm ngặt, xây dựng một đồ thị mô-đun xác định, và kiểm tra các phụ thuộc vòng (cyclic dependencies) cũng như chia tách gói (split packages) ngay khi khởi động.
- **Nhầm lẫn phổ biến**: Lẫn lộn giữa module path (`--module-path` hoặc `-p`) với classpath (`--class-path` or `-cp`). Mã nguồn trên module path được xác thực nghiêm ngặt; mã nguồn trên classpath được đặt vào mô-đun không tên.
- **Ví dụ nhỏ**: Lệnh `java --module-path mods -m my.module/my.package.Main` chạy một ứng dụng mô-đun.
