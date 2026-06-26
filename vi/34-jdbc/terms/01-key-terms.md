# Thuật ngữ JDBC (JDBC Terms)

Sử dụng file này khi một từ ngữ trong phần lý thuyết có cảm giác quá ngắn gọn. Mỗi thuật ngữ đều có ý nghĩa, tầm quan trọng, điểm dễ gây nhầm lẫn và một ví dụ nhỏ.

## driver

Một trình điều khiển cơ sở dữ liệu (driver) là một thư viện phần mềm (triển khai `java.sql.Driver`) giúp chuyển dịch các cuộc gọi API JDBC chung thành giao thức mạng độc quyền của một công cụ cơ sở dữ liệu cụ thể (ví dụ: PostgreSQL, MySQL, Oracle).

- **Tại sao nó quan trọng**: Nó trừu tượng hóa giao tiếp đặc thù của từng cơ sở dữ liệu, cho phép ứng dụng Java chuyển đổi cơ sở dữ liệu một cách đơn giản chỉ bằng cách thay đổi lớp driver và URL kết nối.
- **Nhầm lẫn phổ biến**: Các nhà phát triển thường nghĩ driver là một phần của JDK tiêu chuẩn. Thực tế nó là một thư viện phụ thuộc của bên thứ ba (ví dụ: tệp jar JDBC) cần được thêm vào classpath khi chạy ứng dụng.
- **Ví dụ nhỏ**:
  ```java
  // Nạp Driver PostgreSQL một cách động
  Class.forName("org.postgresql.Driver");
  ```

## Connection

Một đối tượng `java.sql.Connection` đại diện cho một phiên làm việc (session)/socket vật lý với cơ sở dữ liệu, qua đó các câu lệnh SQL được thực thi và các giao dịch được commit hoặc rollback.

- **Tại sao nó quan trọng**: Nó là điểm neo kiểm soát cho hành vi giao dịch (transactional behavior). Bạn vô hiệu hóa tính năng tự động commit và gọi commit hoặc rollback trên đối tượng này.
- **Nhầm lẫn phổ biến**: Tin rằng một `Connection` là nhẹ. Việc tạo một kết nối yêu cầu thiết lập một mạng socket và xác thực, đó là lý do tại sao chúng cần được đưa vào nhóm (pool) và tái sử dụng.
- **Ví dụ nhỏ**:
  ```java
  try (Connection conn = dataSource.getConnection()) {
      conn.setAutoCommit(false);
      // Chạy các truy vấn...
      conn.commit();
  }
  ```

## PreparedStatement

Một `java.sql.PreparedStatement` là một đối tượng câu lệnh SQL được biên dịch trước, chấp nhận các trình giữ chỗ (`?`) cho các tham số đầu vào, tách biệt cú pháp truy vấn khỏi dữ liệu.

- **Tại sao nó quan trọng**: Nó ngăn chặn các lỗ hổng chèn mã SQL (SQL injection) bằng cách xử lý các tham số nghiêm ngặt dưới dạng các hằng văn bản (literals) và cải thiện hiệu năng bằng cách tận dụng các kế hoạch truy vấn được lưu trong cache (cached query plans).
- **Nhầm lẫn phổ biến**: Nghĩ rằng `PreparedStatement` biên dịch mã SQL bên trong mã nguồn Java. Việc biên dịch và tối ưu hóa thực tế xảy ra hoàn toàn trên máy chủ cơ sở dữ liệu.
- **Ví dụ nhỏ**:
  ```java
  String sql = "SELECT * FROM products WHERE price > ?";
  PreparedStatement pstmt = conn.prepareStatement(sql);
  pstmt.setDouble(1, 19.99); // Ràng buộc tham số an toàn
  ```

## ResultSet

Một `java.sql.ResultSet` đại diện cho luồng dữ liệu đầu ra dạng bảng được trả về bởi một truy vấn cơ sở dữ liệu, duy trì một con trỏ (cursor) trỏ đến dòng dữ liệu hiện tại.

- **Tại sao nó quan trọng**: Nó cung cấp quyền truy cập đọc tuần tự vào các bản ghi cơ sở dữ liệu, cho phép lấy các giá trị theo chỉ mục cột hoặc nhãn cột.
- **Nhầm lẫn phổ biến**: Cố gắng đọc các giá trị từ một `ResultSet` mới ngay lập tức. Con trỏ ban đầu được định vị *trước* dòng đầu tiên, vì vậy bạn phải gọi `next()` để di chuyển con trỏ tiến lên trước khi lấy dữ liệu các cột.
- **Ví dụ nhỏ**:
  ```java
  try (ResultSet rs = stmt.executeQuery("SELECT name FROM users")) {
      while (rs.next()) {
          System.out.println(rs.getString("name"));
      }
  }
  ```

## transaction

Một giao dịch cơ sở dữ liệu (transaction) là một đơn vị công việc logic nhóm nhiều sửa đổi SQL lại với nhau tuân thủ các thuộc tính ACID (Atomicity, Consistency, Isolation, Durability - Tính nguyên tử, Tính nhất quán, Tính cô lập, Tính bền vững).

- **Tại sao nó quan trọng**: Đảm bảo tính toàn vẹn dữ liệu bằng cách chắc chắn rằng hoặc tất cả các thay đổi được lưu vĩnh viễn (`commit`) hoặc tất cả các thay đổi bị hoàn tác (`rollback`) nếu xảy ra lỗi.
- **Nhầm lẫn phổ biến**: Giả định rằng các giao dịch tự động hoạt động. Theo mặc định, các kết nối JDBC ở chế độ tự động commit (auto-commit), chế độ này thực thi mỗi câu lệnh như một giao dịch độc lập.
- **Ví dụ nhỏ**:
  ```java
  conn.setAutoCommit(false); // Bắt đầu giao dịch
  // Nhiều cập nhật cơ sở dữ liệu...
  conn.commit(); // Kết thúc giao dịch
  ```

## SQL injection

Lỗi chèn mã SQL (SQL injection) là một lỗ hổng bảo mật khi đầu vào không đáng tin cậy của người dùng được nối trực tiếp vào một câu lệnh SQL, làm thay đổi cây cú pháp của truy vấn và thực thi các lệnh trái phép.

- **Tại sao nó quan trọng**: Kẻ tấn công có thể vượt qua bước xác thực, đọc hoặc trích xuất các bảng cơ sở dữ liệu, hoặc phá hủy dữ liệu trên máy chủ.
- **Nhầm lẫn phổ biến**: Nghĩ rằng việc tự kiểm soát thoát chuỗi các dấu nháy bằng tay là một biện pháp bảo vệ đáng tin cậy chống lại SQL injection. Các đầu vào được tham số hóa an toàn (thông qua `PreparedStatement`) là giải pháp mạnh mẽ duy nhất.
- **Ví dụ nhỏ**:
  ```java
  // KHÔNG AN TOÀN: Mục tiêu dễ bị tấn công SQL injection động
  String sql = "SELECT * FROM users WHERE user = '" + input + "'";
  ```

## DataSource

Một `javax.sql.DataSource` là giao diện Java tiêu chuẩn để lấy các kết nối cơ sở dữ liệu, đóng vai trò như một giải pháp thay thế sạch sẽ cho lớp cũ `DriverManager`.

- **Tại sao nó quan trọng**: Nó trừu tượng hóa các thuộc tính kết nối (URLs, thông tin đăng nhập) và thường được cấu hình để quản lý một nhóm kết nối (connection pool) đằng sau hậu trường, cải thiện hiệu năng ứng dụng.
- **Nhầm lẫn phổ biến**: Nghĩ rằng bản thân `DataSource` là một nhóm kết nối. Nó chỉ là một giao diện nhà máy (factory interface); các lớp cụ thể như `HikariDataSource` triển khai nó để cung cấp khả năng pooling.
- **Ví dụ nhỏ**:
  ```java
  HikariDataSource ds = new HikariDataSource();
  ds.setJdbcUrl("jdbc:postgresql://localhost/db");
  Connection conn = ds.getConnection(); // Lấy kết nối từ pool
  ```
