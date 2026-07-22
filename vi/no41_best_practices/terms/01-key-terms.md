# Thuật ngữ Thực hành tốt nhất trong Java (Best Practices in Java Terms)

Sử dụng tài liệu này khi một từ khóa trong phần lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều có định nghĩa, tầm quan trọng, hiểu lầm thường gặp và một ví dụ nhỏ.

## đặt tên mô tả (descriptive naming)

Thực hành lựa chọn các tên mô tả rõ ý định và chính xác cho các lớp, phương thức, và biến để làm cho mã nguồn tự tài liệu hóa.

Tầm quan trọng: Loại bỏ sự cần thiết của các bình luận rườm rà giải thích mục đích của một biến. Vì tên biến cục bộ bị trình biên dịch loại bỏ khi xây dựng ứng dụng, các tên mô tả dài không gây ra bất kỳ chi phí mã bytecode hay hiệu năng thời gian chạy nào trên JVM.

Hiểu lầm thường gặp: Tin rằng tên ngắn sẽ biên dịch hoặc thực thi nhanh hơn, hoặc cần ký hiệu Hungarian trong Java để xác định kiểu dữ liệu (vốn đã được kiểm tra tĩnh).

Ví dụ nhỏ:
```java
// Bad
int d; 
// Good
int elapsedDays;
```

## nuốt ngoại lệ (exception swallowing)

Phản mẫu (anti-pattern) bắt một ngoại lệ và tiếp tục thực thi mà không ghi nhật ký (log) chi tiết, không ném lại ngoại lệ, hoặc không giải quyết vấn đề cốt lõi bên dưới.

Tầm quan trọng: Nó che giấu các lỗi đang hoạt động, ngăn cản các đội hỗ trợ chẩn đoán lỗi, và làm gián đoạn cơ chế tháo gỡ ngăn xếp (stack-unwinding) của JVM, dẫn đến các lỗi âm thầm và làm hỏng cơ sở dữ liệu.

Hiểu lầm thường gặp: Nghĩ rằng một khối catch trống là an toàn vì "ngoại lệ đã được xử lý" hoặc việc in dấu vết ngăn xếp (stack trace) ra `System.out` là ghi log đầy đủ trong các hệ thống production.

Ví dụ nhỏ:
```java
// Bad
try { read(); } catch (IOException e) {}
// Good
try { read(); } catch (IOException e) {
    throw new RuntimeException("Read failed", e);
}
```

## ngoại lệ tùy chỉnh (custom exceptions)

Các lớp ngoại lệ do nhà phát triển định nghĩa kế thừa từ `Exception` (được kiểm tra - checked) hoặc `RuntimeException` (không được kiểm tra - unchecked) để đại diện cho các điều kiện lỗi đặc thù của nghiệp vụ.

Tầm quan trọng: Cho phép người tiêu dùng API bắt các lỗi nghiệp vụ cụ thể và triển khai logic phục hồi thay vì bắt các ngoại lệ hệ thống cấp thấp chung chung như `IOException`.

Hiểu lầm thường gặp: Tạo tất cả các ngoại lệ tùy chỉnh dưới dạng checked exception, dẫn đến việc phình to các chữ ký `throws` và sự liên kết chặt chẽ giữa các phương thức, ngay cả đối với các lỗi lập trình không thể phục hồi bằng mã.

Ví dụ nhỏ:
```java
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) { super(message); }
}
```

## số ma thuật (magic numbers)

Các giá trị literal số hoặc chuỗi không có tên được sử dụng trực tiếp trong các biểu thức tính toán mà không có lời giải thích hoặc ngữ cảnh.

Tầm quan trọng: Số ma thuật làm lu mờ ý nghĩa của các giá trị và buộc các nhà phát triển phải cập nhật cùng một giá trị literal ở nhiều nơi. Các hằng số có tên (`public static final`) tập trung các giá trị này lại một nơi và được tối ưu hóa thông qua cơ chế nội tuyến tại thời điểm biên dịch.

Hiểu lầm thường gặp: Nghĩ rằng các giá trị ít có khả năng thay đổi (như số ngày trong tuần) thì không cần phải chuyển thành hằng số.

Ví dụ nhỏ:
```java
// Bad
double total = price * 1.0825;
// Good
public static final double SALES_TAX_RATE = 0.0825;
double total = price * (1 + SALES_TAX_RATE);
```

## mệnh đề bảo vệ (guard clauses)

Một kiểm tra điều kiện được đặt ở đầu phương thức để trả về hoặc ném ngoại lệ ngay lập tức nếu các tiền điều kiện không được đáp ứng, ngăn chặn các khối lồng nhau sâu.

Tầm quan trọng: Giữ cho "luồng xử lý suôn sẻ (happy path)" thẳng hàng ở lề bên trái, giảm tải nhận thức khi đọc các phương thức, và đơn giản hóa đồ thị luồng điều khiển cho việc tối ưu hóa dự đoán nhánh của JVM.

Hiểu lầm thường gặp: Giả định rằng một phương thức chỉ được phép có duy nhất một câu lệnh return ở cuối cùng, điều này dẫn đến cấu trúc `if-else` lồng nhau sâu.

Ví dụ nhỏ:
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

## thành phần (composition)

Một mẫu thiết kế trong đó một lớp đạt được khả năng tái sử dụng mã nguồn và hành vi đa hình bằng cách giữ các tham chiếu đến các đối tượng khác (mối quan hệ "has-a") thay vì kế thừa một lớp (mối quan hệ "is-a").

Tầm quan trọng: Nó tách biệt các lớp bằng cách loại bỏ các phân cấp kế thừa cứng nhắc tại thời điểm biên dịch, tránh xung đột API giữa cha và con và bảo vệ tính đóng gói của lớp con.

Hiểu lầm thường gặp: Tin rằng thành phần kém mạnh mẽ hơn kế thừa vì nó yêu cầu các phương thức ủy quyền (delegation method) rõ ràng để truy cập các tính năng của đối tượng được bao bọc.

Ví dụ nhỏ:
```java
// Using composition instead of extending Stack
public class CustomStack {
    private final List<String> list = new ArrayList<>();
    public void push(String item) { list.add(item); }
}
```

## tính bất biến (immutability)

Đặc tính thiết kế trong đó trạng thái của một đối tượng không thể thay đổi sau khi nó được tạo ra.

Tầm quan trọng: Các đối tượng bất biến vốn dĩ an toàn luồng, không yêu cầu sao chép phòng ngừa (defensive copy) khi chia sẻ, và là các khóa bản đồ (map key) cũng như các phần tử tập hợp (set element) tuyệt vời vì mã băm của chúng không bao giờ thay đổi.

Hiểu lầm thường gặp: Nghĩ rằng việc đặt tham chiếu bộ sưu tập là `final` sẽ làm cho chính bộ sưu tập đó bất biến (nội dung của bộ sưu tập vẫn có thể bị sửa đổi trừ khi được bao bọc trong một dạng xem không thể sửa đổi unmodifiable view).

Ví dụ nhỏ:
```java
// Immutable Record
public record User(String username, List<String> roles) {
    public User {
        roles = List.copyOf(roles); // Defensive copy
    }
}
```

## kiểu thô (raw type)

Một lớp hoặc giao diện generic được sử dụng mà không có các đối số kiểu của nó (ví dụ: khai báo `List` thay vì `List<String>`).

Tầm quan trọng: Các kiểu thô vô hiệu hóa việc kiểm tra an toàn kiểu của generic, gây ra các cảnh báo tại thời điểm biên dịch và có nguy cơ ném ra ngoại lệ `ClassCastException` lúc chạy khi lấy các phần tử ra.

Hiểu lầm thường gặp: Sử dụng các kiểu thô dưới giả định rằng chúng cải thiện hiệu năng hoặc trình biên dịch tự động suy luận ra kiểu chính xác mà không cần khai báo generic.

Ví dụ nhỏ:
```java
// Bad: raw type
List list = new ArrayList();
// Good: parameterized type
List<String> list = new ArrayList<>();
```

## cấu trúc try-with-resources (try-with-resources)

Một cấu trúc xử lý ngoại lệ tự động đóng các đối tượng có triển khai `AutoCloseable` ở cuối khối câu lệnh.

Tầm quan trọng: Đảm bảo các tài nguyên hệ thống (chẳng hạn như tay cầm tệp, kết nối cơ sở dữ liệu, và socket) được đóng theo đúng thứ tự, ngay cả khi có ngoại lệ xảy ra, ngăn ngừa rò rỉ tài nguyên.

Hiểu lầm thường gặp: Nghĩ rằng các tài nguyên sẽ tự động đóng nếu được khởi tạo bên ngoài dấu ngoặc đơn của `try`, hoặc quên rằng tài nguyên đó bắt buộc phải triển khai `AutoCloseable`.

Ví dụ nhỏ:
```java
try (FileWriter writer = new FileWriter("log.txt")) {
    writer.write("Hello");
} catch (IOException e) {
    // handled
}
```

## mã nguồn có thể kiểm thử (testable code)

Mã nguồn được cấu trúc theo cách mô-đun hóa, tách biệt (ví dụ: sử dụng tiêm phụ thuộc - dependency injection) để cho phép xác minh hành vi một cách cô lập bằng các bài kiểm thử đơn vị.

Tầm quan trọng: Bảo vệ logic khỏi các lỗi suy thoái (regression bug), cho phép phát triển song song, và buộc các lớp phải tách biệt sạch sẽ và phân chia rõ ràng các mối bận tâm (separation of concerns).

Hiểu lầm thường gặp: Nghĩ rằng mã nguồn chỉ có thể được kiểm thử nếu một framework như Spring đang chạy, hoặc các bài kiểm thử đơn vị nên xác minh các phương thức trợ giúp private thay vì các API công khai public.

Ví dụ nhỏ:
```java
// Testable because clock dependency can be mocked
public class PaymentService {
    private final Clock clock;
    public PaymentService(Clock clock) { this.clock = clock; }
}
```

## trách nhiệm (responsibility)

Mối quan tâm hoặc vai trò nghiệp vụ cụ thể được gán cho một lớp hoặc phương thức, được chi phối bởi Nguyên lý đơn nhiệm (Single Responsibility Principle - SRP).

Tầm quan trọng: Các lớp có một nhiệm vụ duy nhất có ít lý do để thay đổi hơn, giúp định vị mã nguồn dễ dàng hơn, và dễ dàng tái sử dụng cũng như viết kiểm thử đơn vị cô lập hơn rất nhiều.

Hiểu lầm thường gặp: Tin rằng một lớp nên xử lý tất cả các khía cạnh của một khái niệm (ví dụ: một lớp `User` thực hiện phân tích cú pháp JSON, lưu vào cơ sở dữ liệu DB, và xác thực email) để giữ cho mọi thứ "ngăn nắp".

Ví dụ nhỏ:
```java
// Separate responsibilities
public class UserRepository { public void save(User u) {} }
public class UserValidator { public boolean isValid(User u) { return true; } }
```
