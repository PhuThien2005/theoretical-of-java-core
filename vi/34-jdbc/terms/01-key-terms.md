# Các Thuật Ngữ JDBC

Hãy sử dụng tài liệu này khi một từ trong phần lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều có phần ý nghĩa, tầm quan trọng, hiểu lầm phổ biến và một ví dụ nhỏ.

## trình điều khiển (driver)

Một trình điều khiển cơ sở dữ liệu (database driver) là một thư viện phần mềm (triển khai `java.sql.Driver`) dịch các lệnh gọi API JDBC chung thành giao thức mạng độc quyền của một công cụ cơ sở dữ liệu cụ thể (ví dụ: PostgreSQL, MySQL, Oracle).

* **Tầm quan trọng**: Nó trừu tượng hóa giao tiếp đặc thù của cơ sở dữ liệu, cho phép một ứng dụng Java chuyển đổi cơ sở dữ liệu đơn giản bằng cách thay đổi lớp trình điều khiển và URL kết nối.
* **Hiểu lầm phổ biến**: Các lập trình viên nghĩ rằng trình điều khiển là một phần của JDK tiêu chuẩn. Thực chất nó là một thư viện phụ thuộc của bên thứ ba (ví dụ: tệp jar JDBC) phải được thêm vào đường dẫn lớp (classpath) lúc chạy.
* **Ví dụ nhỏ**:
  ```java
  // Tải động Trình điều khiển PostgreSQL
  Class.forName("org.postgresql.Driver");
  ```

## Connection

Một đối tượng `java.sql.Connection` đại diện cho một phiên (session)/socket cơ sở dữ liệu vật lý qua đó các câu lệnh SQL được thực thi và các giao dịch được xác nhận (commit) hoặc hoàn tác (rollback).

* **Tầm quan trọng**: Đây là điểm neo kiểm soát cho hành vi giao dịch. Bạn vô hiệu hóa tính năng tự động xác nhận (auto-commit) và gọi commit hoặc rollback trên đối tượng này.
* **Hiểu lầm phổ biến**: Tin rằng một `Connection` là nhẹ. Việc tạo một kết nối yêu cầu thiết lập một socket mạng và xác thực, đó là lý do tại sao chúng cần được đưa vào bể chứa (pool) và tái sử dụng.
* **Ví dụ nhỏ**:
  ```java
  try (Connection conn = dataSource.getConnection()) {
      conn.setAutoCommit(false);
      // Chạy các truy vấn...
      conn.commit();
  }
  ```

## PreparedStatement

Một `java.sql.PreparedStatement` là một đối tượng câu lệnh SQL được biên dịch trước chấp nhận các ký tự giữ chỗ (`?`) cho các tham số đầu vào, giúp tách biệt cú pháp truy vấn khỏi dữ liệu.

* **Tầm quan trọng**: Nó ngăn chặn các lỗ hổng chèn mã SQL (SQL injection) bằng cách xử lý các tham số thuần túy như các chuỗi ký tự (literal) và cải thiện hiệu năng bằng cách tận dụng các kế hoạch truy vấn được lưu đệm.
* **Hiểu lầm phổ biến**: Nghĩ rằng `PreparedStatement` biên dịch SQL bên trong Java. Việc biên dịch và tối ưu hóa diễn ra hoàn toàn trên máy chủ cơ sở dữ liệu.
* **Ví dụ nhỏ**:
  ```java
  String sql = "SELECT * FROM products WHERE price > ?";
  PreparedStatement pstmt = conn.prepareStatement(sql);
  pstmt.setDouble(1, 19.99); // Liên kết tham số an toàn
  ```

## ResultSet

Một `java.sql.ResultSet` đại diện cho luồng dữ liệu đầu ra dạng bảng được trả về từ một truy vấn cơ sở dữ liệu, duy trì một con trỏ (cursor) chỉ đến dòng dữ liệu hiện tại.

* **Tầm quan trọng**: Nó cung cấp quyền truy cập đọc tuần tự vào các bản ghi cơ sở dữ liệu, cho phép lấy ra các giá trị bằng chỉ mục cột hoặc nhãn cột.
* **Hiểu lầm phổ biến**: Cố gắng đọc các giá trị từ một `ResultSet` mới ngay lập tức. Con trỏ được định vị *tự nhiên trước* dòng đầu tiên, vì vậy bạn phải gọi `next()` để di chuyển con trỏ lên trước khi tìm nạp các cột.
* **Ví dụ nhỏ**:
  ```java
  try (ResultSet rs = stmt.executeQuery("SELECT name FROM users")) {
      while (rs.next()) {
          System.out.println(rs.getString("name"));
      }
  }
  ```

## giao dịch (transaction)

Một giao dịch (transaction) cơ sở dữ liệu là một đơn vị công việc logic nhóm nhiều sửa đổi SQL lại với nhau, tuân thủ các thuộc tính ACID (Atomicity - Tính nguyên tử, Consistency - Tính nhất quán, Isolation - Tính cô lập, Durability - Tính bền vững).

* **Tầm quan trọng**: Đảm bảo tính toàn vẹn của dữ liệu bằng cách chắc chắn rằng hoặc tất cả các thay đổi được lưu vĩnh viễn (`commit`) hoặc tất cả các thay đổi được hoàn tác (`rollback`) nếu xảy ra lỗi.
* **Hiểu lầm phổ biến**: Assuming transactions are active automatically. By default, JDBC connections are in auto-commit mode, which executes each statement as an independent transaction.
* **Ví dụ nhỏ**:
  ```java
  conn.setAutoCommit(false); // Bắt đầu giao dịch
  // Nhiều cập nhật cơ sở dữ liệu...
  conn.commit(); // Kết thúc giao dịch
  ```

## Tấn công chèn mã SQL (SQL injection)

Tấn công chèn mã SQL (SQL injection) là một lỗ hổng bảo mật trong đó đầu vào không đáng tin cậy của người dùng được nối trực tiếp vào một câu lệnh SQL, làm thay đổi cây cú pháp của truy vấn và thực thi các câu lệnh trái phép.

* **Tầm quan trọng**: Kẻ tấn công có thể vượt qua xác thực, đọc hoặc trích xuất các bảng cơ sở dữ liệu, hoặc phá hủy dữ liệu trên máy chủ.
* **Hiểu lầm phổ biến**: Nghĩ rằng việc tự lọc các dấu nháy (escaping quotes) thủ công là một biện pháp phòng thủ đáng tin cậy chống lại SQL injection. Các đầu vào được tham số hóa an toàn (qua `PreparedStatement`) là giải pháp mạnh mẽ duy nhất.
* **Ví dụ nhỏ**:
  ```java
  // KHÔNG AN TOÀN: Mục tiêu của tấn công chèn mã SQL động
  String sql = "SELECT * FROM users WHERE user = '" + input + "'";
  ```

## DataSource

Một `javax.sql.DataSource` là giao diện Java tiêu chuẩn để lấy các kết nối cơ sở dữ liệu, hoạt động như một giải pháp thay thế sạch sẽ cho lớp cũ `DriverManager`.

* **Tầm quan trọng**: Nó trừu tượng hóa các thuộc tính kết nối (URL, thông tin xác thực) và thường được cấu hình để quản lý một bể chứa kết nối (connection pool) dưới nền tảng, giúp cải thiện hiệu năng ứng dụng.
* **Hiểu lầm phổ biến**: Nghĩ rằng `DataSource` tự bản thân nó là một bể chứa kết nối. Nó chỉ đơn thuần là một giao diện nhà máy (factory interface); các lớp cụ thể như `HikariDataSource` triển khai nó để cung cấp các khả năng bể chứa.
* **Ví dụ nhỏ**:
  ```java
  HikariDataSource ds = new HikariDataSource();
  ds.setJdbcUrl("jdbc:postgresql://localhost/db");
  Connection conn = ds.getConnection(); // Lấy kết nối từ bể chứa (pool)
  ```
