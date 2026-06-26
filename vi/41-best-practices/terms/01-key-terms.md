# Thuật ngữ Các Thực Hành Tốt Nhất trong Java (Best Practices in Java Terms)

Sử dụng tài liệu này khi một từ ngữ trong lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều có ý nghĩa, tầm quan trọng, điểm dễ nhầm lẫn và một ví dụ nhỏ.

## descriptive naming (đặt tên mang tính mô tả)

Thực hành lựa chọn các tên gọi thể hiện rõ ý định và chính xác cho các lớp, phương thức, và biến để giúp mã nguồn có tính tự tài liệu hóa (self-documenting).

- **Tầm quan trọng**: Loại bỏ sự cần thiết của các bình luận rườm rà giải thích mục đích của một biến. Vì các tên biến cục bộ bị trình biên dịch loại bỏ tại thời điểm xây dựng (build time), việc đặt tên mô tả không làm tăng chi phí mã bytecode hoặc hiệu năng thời gian chạy trên JVM.
- **Nhầm lẫn thường gặp**: Tin rằng các tên ngắn sẽ biên dịch hoặc thực thi nhanh hơn, hoặc ký hiệu Hungari (Hungarian notation) là cần thiết trong Java để nhận biết kiểu dữ liệu (vốn đã được kiểm tra tĩnh).
- **Ví dụ nhỏ**:
  ```java
  // Bad
  int d; 
  // Good
  int elapsedDays;
  ```

## exception swallowing (nuốt ngoại lệ)

Phản khuôn mẫu (anti-pattern) bắt một ngoại lệ và tiếp tục thực thi mà không ghi nhật ký chi tiết, ném lại ngoại lệ đó, hoặc giải quyết vấn đề cơ bản.

- **Tầm quan trọng**: Nó che giấu các lỗi đang hoạt động, ngăn cản các đội ngũ hỗ trợ chẩn đoán lỗi, và phá vỡ cơ chế gỡ rối ngăn xếp (stack-unwinding) của JVM, dẫn đến các lỗi ngầm và lỗi dữ liệu.
- **Nhầm lẫn thường gặp**: Nghĩ rằng một khối catch trống là an toàn vì "ngoại lệ đã được xử lý" hoặc việc in dấu vết ngăn xếp (stack trace) ra `System.out` là đủ để ghi nhật ký trong các hệ thống production.
- **Ví dụ nhỏ**:
  ```java
  // Bad
  try { read(); } catch (IOException e) {}
  // Good
  try { read(); } catch (IOException e) {
      throw new RuntimeException("Read failed", e);
  }
  ```

## custom exceptions (ngoại lệ tự định nghĩa)

Các lớp ngoại lệ do lập trình viên định nghĩa kế thừa (Inheritance) từ `Exception` (có kiểm tra) hoặc `RuntimeException` (không kiểm tra) để biểu diễn các điều kiện thất bại đặc thù của miền nghiệp vụ.

- **Tầm quan trọng**: Cho phép những người sử dụng API bắt các lỗi nghiệp vụ cụ thể và thực hiện logic phục hồi thay vì bắt các ngoại lệ hệ thống cấp thấp chung chung như `IOException`.
- **Nhầm lẫn thường gặp**: Biến mọi ngoại lệ tự định nghĩa thành ngoại lệ có kiểm tra (checked exception), điều này dẫn đến các khai báo `throws` cồng kềnh và liên kết chặt chẽ giữa các phương thức, ngay cả đối với các lỗi lập trình không thể phục hồi bằng lập trình.
- **Ví dụ nhỏ**:
  ```java
  public class UserNotFoundException extends RuntimeException {
      public UserNotFoundException(String message) { super(message); }
  }
  ```

## magic numbers (con số ma thuật)

Các giá trị số hoặc chuỗi trực tiếp không được đặt tên, được sử dụng trực tiếp trong các tính toán biểu thức mà không có giải thích hay ngữ cảnh.

- **Tầm quan trọng**: Các con số ma thuật che khuất ý nghĩa của các giá trị và buộc các nhà phát triển phải cập nhật cùng một giá trị trực tiếp ở nhiều nơi. Các hằng số được đặt tên (`public static final`) tập trung các giá trị này và được tối ưu hóa thông qua cơ chế inlining tại thời điểm biên dịch.
- **Nhầm lẫn thường gặp**: Nghĩ rằng các giá trị ít có khả năng thay đổi (như số ngày trong tuần) thì không cần phải chuyển thành hằng số.
- **Ví dụ nhỏ**:
  ```java
  // Bad
  double total = price * 1.0825;
  // Good
  public static final double SALES_TAX_RATE = 0.0825;
  double total = price * (1 + SALES_TAX_RATE);
  ```

## guard clauses (mệnh đề bảo vệ)

Một kiểm tra điều kiện được đặt ở đầu một phương thức, thực hiện trả về hoặc ném ngoại lệ ngay lập tức nếu các điều kiện tiên quyết không được đáp ứng, ngăn chặn các khối lệnh lồng nhau sâu.

- **Tầm quan trọng**: Giữ cho luồng chính ("happy path") thẳng hàng ở lề bên trái, giảm tải nhận thức khi đọc các phương thức, và đơn giản hóa biểu đồ luồng kiểm soát cho các tối ưu hóa dự đoán nhánh (branch prediction) của JVM.
- **Nhầm lẫn thường gặp**: Giả định rằng một phương thức chỉ được phép có một câu lệnh return duy nhất ở cuối cùng, dẫn đến các cấu trúc `if-else` lồng nhau sâu.
- **Ví dụ nhỏ**:
  ```java
  // Bad
  if (user != null) {
      if (user.isActive()) {
          process(user);
      }
  }
  // Good
  if (user == null || !user.isActive()) return;
  process(user);
  ```

## composition (lắp ghép)

Một mẫu thiết kế trong đó một lớp đạt được khả năng tái sử dụng mã nguồn và hành vi đa hình bằng cách giữ các tham chiếu đến các đối tượng khác (mối quan hệ "has-a" - có một) thay vì kế thừa một lớp khác (mối quan hệ "is-a" - là một).

- **Tầm quan trọng**: Nó tách rời các lớp bằng cách loại bỏ các phân cấp kế thừa cứng nhắc ở thời điểm biên dịch, tránh xung đột API giữa cha và con và bảo vệ tính đóng gói (Encapsulation) của lớp con.
- **Nhầm lẫn thường gặp**: Tin rằng lắp ghép yếu hơn kế thừa vì nó yêu cầu các phương thức ủy quyền (delegation methods) rõ ràng để truy cập các tính năng của đối tượng được bọc.
- **Ví dụ nhỏ**:
  ```java
  // Using composition instead of extending Stack
  public class CustomStack {
      private final List<String> list = new ArrayList<>();
      public void push(String item) { list.add(item); }
  }
  ```

## immutability (tính bất biến)

Đặc tính thiết kế trong đó trạng thái của một đối tượng không thể bị thay đổi sau khi nó được tạo ra.

- **Tầm quan trọng**: Các đối tượng bất biến vốn đã an toàn với luồng (thread-safe), không yêu cầu sao chép phòng ngừa (defensive copying) khi chia sẻ, và là các khóa Map cũng như phần tử Set tuyệt vời vì mã băm của chúng không bao giờ thay đổi.
- **Nhầm lẫn thường gặp**: Nghĩ rằng việc khai báo tham chiếu bộ sưu tập là `final` sẽ làm cho chính bộ sưu tập đó bất biến (nội dung bộ sưu tập vẫn có thể bị sửa đổi trừ khi được bọc trong một chế độ xem không thể sửa đổi).
- **Ví dụ nhỏ**:
  ```java
  // Immutable Record
  public record User(String username, List<String> roles) {
      public User {
          roles = List.copyOf(roles); // Defensive copy
      }
  }
  ```

## raw type (kiểu thô)

Một lớp hoặc giao diện generic được sử dụng mà không có các đối số kiểu của nó (ví dụ: khai báo `List` thay vì `List<String>`).

- **Tầm quan trọng**: Các kiểu thô vô hiệu hóa việc kiểm tra an toàn kiểu của generic, gây ra các cảnh báo tại thời điểm biên dịch và có nguy cơ ném ra ngoại lệ `ClassCastException` trong thời gian chạy khi các phần tử được trích xuất.
- **Nhầm lẫn thường gặp**: Sử dụng các kiểu thô dưới giả định rằng chúng cải thiện hiệu năng hoặc trình biên dịch tự động suy luận các kiểu đúng mà không cần khai báo generic.
- **Ví dụ nhỏ**:
  ```java
  // Bad: raw type
  List list = new ArrayList();
  // Good: parameterized type
  List<String> list = new ArrayList<>();
  ```

## try-with-resources

Một cấu trúc xử lý ngoại lệ tự động đóng các đối tượng triển khai `AutoCloseable` ở cuối khối lệnh.

- **Tầm quan trọng**: Đảm bảo rằng các tài nguyên hệ thống (chẳng hạn như handle tệp, kết nối cơ sở dữ liệu, và các socket) được đóng theo đúng thứ tự, ngay cả khi các ngoại lệ được ném ra, giúp ngăn ngừa rò rỉ tài nguyên.
- **Nhầm lẫn thường gặp**: Nghĩ rằng các tài nguyên sẽ tự động đóng nếu được khởi tạo bên ngoài dấu ngoặc đơn của `try`, hoặc quên rằng tài nguyên đó phải triển khai giao diện `AutoCloseable`.
- **Ví dụ nhỏ**:
  ```java
  try (FileWriter writer = new FileWriter("log.txt")) {
      writer.write("Hello");
  } catch (IOException e) {
      // handled
  }
  ```

## mã nguồn có thể kiểm thử (testable code)

Mã nguồn được cấu trúc theo cách mô-đun hóa, tách rời (ví dụ: sử dụng tiêm phụ thuộc - dependency injection) để cho phép xác minh hành vi một cách cô lập bằng các bài kiểm thử đơn vị.

- **Tầm quan trọng**: Bảo vệ logic khỏi các lỗi hồi quy (regression bugs), cho phép phát triển song song, và buộc việc tách rời các lớp một cách sạch sẽ cũng như phân chia rõ ràng các mối quan tâm (separation of concerns).
- **Nhầm lẫn thường gặp**: Nghĩ rằng mã nguồn chỉ có thể được kiểm thử nếu một khung làm việc như Spring đang chạy, hoặc các kiểm thử đơn vị nên xác minh các phương thức trợ giúp riêng tư (private helper methods) thay vì các API công khai.
- **Ví dụ nhỏ**:
  ```java
  // Testable because clock dependency can be mocked
  public class PaymentService {
      private final Clock clock;
      public PaymentService(Clock clock) { this.clock = clock; }
  }
  ```

## responsibility (trách nhiệm)

Mối quan tâm hoặc vai trò thuộc miền nghiệp vụ cụ thể được giao cho một lớp hoặc phương thức, được chi phối bởi Nguyên lý Đơn Trách Nhiệm (Single Responsibility Principle - SRP).

- **Tầm quan trọng**: Các lớp có một trách nhiệm duy nhất sẽ có ít lý do để thay đổi hơn, giúp mã nguồn dễ định vị hơn, và dễ dàng tái sử dụng cũng như kiểm thử đơn vị một cách cô lập hơn nhiều.
- **Nhầm lẫn thường gặp**: Tin rằng một lớp nên xử lý tất cả các khía cạnh của một khái niệm (ví dụ: lớp `User` vừa phân tích cú pháp JSON, lưu vào DB, vừa xác thực email) để giữ cho mọi thứ "ngăn nắp".
- **Ví dụ nhỏ**:
  ```java
  // Separate responsibilities
  public class UserRepository { public void save(User u) {} }
  public class UserValidator { public boolean isValid(User u) { return true; } }
  ```
