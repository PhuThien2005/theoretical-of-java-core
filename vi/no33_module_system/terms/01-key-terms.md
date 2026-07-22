# Thuật Ngữ Hệ Thống Module Java (Java Module System Terms)

Sử dụng file này khi một từ trong phần lý thuyết có cảm giác quá ngắn gọn. Mỗi thuật ngữ đều đi kèm với ý nghĩa, tầm quan trọng, điểm dễ gây nhầm lẫn và một ví dụ nhỏ.

## module (Module)

Một tập hợp tự mô tả của mã nguồn (các package) và dữ liệu (các tài nguyên), đi kèm với một mô tả module (`module-info.class`) chỉ rõ các phụ thuộc và kiểm soát truy cập.

- **Tại sao điều này quan trọng**: Nó dịch chuyển đơn vị biên dịch và thời gian chạy của Java từ một tập hợp phẳng các lớp thành một biểu đồ phụ thuộc có cấu trúc, cho phép xác thực các phụ thuộc tại thời điểm biên dịch và thực thi tính đóng gói mạnh mẽ.
- **Nhầm lẫn phổ biến**: Người học thường nhầm lẫn module với các package hoặc các dự án Maven/Gradle. Một package là một không gian tên (namespace) cho các lớp; một module tổng hợp các package và kiểm soát việc truy cập vào chúng. Một dự án Maven là một trình bao bọc của công cụ xây dựng (build tool), nó có thể chứa một hoặc nhiều module Java.
- **Ví dụ nhỏ**: Bản thân JDK được chia thành các module như `java.base`, `java.desktop`, và `java.sql`.

## module-info.java

Tệp nguồn mô tả module được đặt ở thư mục gốc của cây thư mục nguồn của một module, định nghĩa tên module, các yêu cầu, các package xuất ra (exports) và quyền truy cập phản xạ.

- **Tại sao điều này quan trọng**: Trình biên dịch sử dụng tệp này để thực thi các quy tắc truy cập trong quá trình biên dịch, và JVM sử dụng nó để xây dựng biểu đồ module và thực thi kiểm soát truy cập tại thời điểm chạy.
- **Nhầm lẫn phổ biến**: Người học thường đặt nó bên trong một thư mục package hoặc quên rằng nó phải sử dụng từ khóa `module` theo sau bởi tên module, thường có định dạng đảo ngược tên miền (như một package).
- **Ví dụ nhỏ**:
  ```java
  module com.myapp {
      requires java.sql;
      exports com.myapp.service;
  }
  ```

## requires

Một chỉ thị module được sử dụng trong `module-info.java` chỉ định một phụ thuộc của module hiện tại vào một module khác.

- **Tại sao điều này quan trọng**: Nó thiết lập khả năng đọc (readability), cho phép module hiện tại truy cập vào các package được xuất ra của module chỉ định.
- **Nhầm lẫn phổ biến**: Nhầm lẫn `requires` (tham chiếu đến tên một module) với câu lệnh `import` trong các tệp nguồn Java (tham chiếu đến tên package/lớp).
- **Ví dụ nhỏ**: `requires java.net.http;` báo cho JVM biết module này cần module HTTP client.

## exports

Một chỉ thị module được sử dụng trong `module-info.java` giúp tất cả các lớp và interface công khai (`public`) trong một package có thể truy cập được bởi các module khác.

- **Tại sao điều này quan trọng**: Nó là nền tảng của tính đóng gói. Các package không được xuất ra một cách tường minh sẽ hoàn toàn bị ẩn và các module khác không thể truy cập được.
- **Nhầm lẫn phổ biến**: Nghĩ rằng `exports` giúp các thành viên nội bộ/private của một lớp có thể truy cập được. Nó chỉ xuất ra các API public; các trường private vẫn không thể truy cập được.
- **Ví dụ nhỏ**: `exports com.myapp.api;` phơi bày các lớp public của package `api`.

## opens

Một chỉ thị module được sử dụng trong `module-info.java` cho phép các module khác sử dụng phản xạ (bao gồm cả phản xạ sâu trên các thành viên private) trên các package này, đồng thời chặn quyền truy cập tại thời điểm biên dịch.

- **Tại sao điều này quan trọng**: Thiết yếu cho các framework (như Spring, Hibernate, hoặc JUnit) vốn yêu cầu phản xạ sâu để tiêm phụ thuộc, ánh xạ cơ sở dữ liệu, hoặc chạy các bài kiểm thử trên các phần tử private.
- **Nhầm lẫn phổ biến**: Người học thường sử dụng `exports` khi họ thực sự cần `opens` cho phản xạ. Nếu bạn chỉ sử dụng `exports`, các framework sẽ ném ra `InaccessibleObjectException` khi cố gắng truy cập các trường private.
- **Ví dụ nhỏ**: `opens com.myapp.domain to spring.core;` cho phép Spring truy cập phản xạ vào các mô hình miền (domain model).

## unnamed module (Module Vô Danh)

Một module ngầm định do JVM tạo ra để chứa tất cả các lớp được tải từ classpath.

- **Tại sao điều này quan trọng**: Cung cấp khả năng tương thích ngược cho các ứng dụng Java 8 cũ. Nó tự động xuất ra tất cả các package của nó và có thể đọc mọi module trên đường dẫn module (module path).
- **Nhầm lẫn phổ biến**: Nghĩ rằng các module có tên có thể khai báo `requires` đối với module vô danh. Các module có tên không thể đọc module vô danh vì nó không có tên, có nghĩa là mã nguồn classpath cũ không dễ dàng làm phụ thuộc cho mã nguồn modular mà không sử dụng các module tự động (automatic module).
- **Ví dụ nhỏ**: Chạy `java -cp app.jar Main` đặt tất cả các lớp từ `app.jar` vào module vô danh.

## automatic module (Module Tự Động)

Một module có tên được tạo tự động bởi JVM khi một tệp JAR tiêu chuẩn (vốn thiếu `module-info.class`) được đặt trên đường dẫn module (module path).

- **Tại sao điều này quan trọng**: Đóng vai trò là cầu nối di chuyển (migration bridge). Nó xuất ra tất cả các package của nó và có thể đọc cả các module có tên và cả module vô danh, cho phép mã nguồn modular phụ thuộc vào các tệp JAR thư viện cũ.
- **Nhầm lẫn phổ biến**: Tin rằng các module tự động yêu cầu một tệp `module-info.java`. Chúng không cần; tên của chúng tự động được suy ra từ tên tệp JAR hoặc từ phần đầu đề (header) `Automatic-Module-Name` trong file manifest.
- **Ví dụ nhỏ**: Đặt `guava-31.0.jar` trên đường dẫn module sẽ tạo ra một module tự động có tên là `guava`, có thể được gọi thông qua `requires guava;`.

## module path (Đường Dẫn Module)

Đường dẫn tìm kiếm được sử dụng bởi trình biên dịch Java và JVM để tìm các thư mục và tệp JAR modular.

- **Tại sao điều này quan trọng**: Khác với classpath, đường dẫn module thực thi tính đóng gói nghiêm ngặt, xây dựng một biểu đồ module xác định, và kiểm tra các phụ thuộc vòng (cyclic dependency) cũng như phân tách package (split package) khi khởi động.
- **Nhầm lẫn phổ biến**: Lẫn lộn giữa đường dẫn module (`--module-path` hoặc `-p`) với classpath (`--class-path` hoặc `-cp`). Mã nguồn trên đường dẫn module được xác thực nghiêm ngặt; mã nguồn trên classpath được đặt trong module vô danh.
- **Ví dụ nhỏ**: `java --module-path mods -m my.module/my.package.Main` chạy một ứng dụng modular.
