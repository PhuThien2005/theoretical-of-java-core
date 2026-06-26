# Các Thực Hành Tốt Nhất trong Java - Phần 2 (Best Practices in Java - Part 2)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này trình bày các **Thực Hành Tốt Nhất (Best Practices)** nâng cao trong Java bao gồm các quy tắc xử lý ngoại lệ, an toàn kiểu dữ liệu, tránh sử dụng null, viết mã nguồn dễ kiểm thử, và các mô hình thiết kế lớp bất biến. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế.

## Khái Quát Nội Dung (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `Do not swallow exceptions` | Các quy tắc xử lý hoặc lan truyền ngoại lệ thay vì bỏ qua chúng. |
| `Use interface type when declaring Collection` | Lập trình hướng giao diện (`List`, `Set`, `Map`) thay vì các lớp cụ thể. |
| `List<String> list = new ArrayList<>();` | Khai báo các bộ sưu tập an toàn kiểu bằng cách sử dụng suy luận kim cương (diamond inference). |
| `Avoid raw type` | Ngăn chặn các cảnh báo kiểu ở thời điểm biên dịch bằng cách tránh sử dụng các lớp thô không có generic. |
| `Avoid null when possible` | Các kỹ thuật để ngăn chặn ngoại lệ NullPointerException. |
| `Write testable code` | Các quy tắc cấu trúc (Tiêm phụ thuộc - Dependency Injection, khả năng tương thích giả lập - mocking compatibility) phục vụ cho việc kiểm thử. |
| `Separate class/method responsibilities` | Áp dụng Nguyên lý Đơn Trách Nhiệm (SRP) để giữ cho mã nguồn dễ bảo trì. |
| `Immutability when appropriate` | Thiết lập các lớp bất biến sử dụng records, các trường final, và các chế độ xem không thể sửa đổi (unmodifiable views). |

---

## Ghi Chú Chi Tiết (Detailed Notes)

### Không nuốt ngoại lệ (Do not swallow exceptions)

Nuốt ngoại lệ (bắt một ngoại lệ và không làm gì cả, hoặc chỉ ghi nhật ký một thông báo tầm thường mà không giải quyết hoặc ném lại nó) là một phản khuôn mẫu lớn. Nó che giấu các lỗi hệ thống, khiến việc gỡ lỗi gần như bất khả thi.

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  // XẤU: Âm thầm bỏ qua ngoại lệ, để lại ứng dụng ở trạng thái không ổn định
  try {
      parseConfiguration();
  } catch (IOException e) {
      // Bị nuốt! Thực thi mã nguồn tiếp tục chạy một cách mù quáng.
  }

  // TỐT: Ghi nhật ký chi tiết và lan truyền ngoại lệ được bọc trong một RuntimeException
  try {
      parseConfiguration();
  } catch (IOException e) {
      logger.error("Configuration loading failed, exiting process.", e);
      throw new RuntimeException("Fatal error parsing configuration", e);
  }
  ```

---

### Sử dụng kiểu giao diện khi khai báo Collection (Use interface type when declaring Collection)

Viết mã hướng đến các giao diện (`List`, `Set`, `Map`, `Queue`) thay vì các lớp triển khai cụ thể (`ArrayList`, `HashSet`, `HashMap`, `LinkedList`). Việc này tách rời mã nguồn của bạn khỏi các chi tiết triển khai cụ thể, cho phép bạn hoán đổi các bộ sưu tập dễ dàng khi các yêu cầu thay đổi.

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  // XẤU: Viết cứng sự phụ thuộc vào chi tiết triển khai của ArrayList
  ArrayList<String> users = new ArrayList<>();

  // TỐT: Khai báo biến sử dụng kiểu giao diện
  List<String> users = new ArrayList<>();
  ```

---

### List<String> list = new ArrayList<>();

Luôn luôn chỉ định các đối số kiểu để đảm bảo an toàn kiểu tại thời điểm biên dịch. Sử dụng toán tử kim cương (`<>`) ở phía gọi hàm khởi tạo để trình biên dịch tự động suy luận các tham số kiểu.

- **Ví dụ**:
  ```java
  List<String> list = new ArrayList<>(); // Sạch sẽ, an toàn kiểu, tránh lặp lại (dry)
  ```

---

### Tránh sử dụng kiểu thô (Avoid raw type)

Kiểu thô (Raw types) là các lớp generic được khai báo không có tham số kiểu (ví dụ: sử dụng `List` thay vì `List<String>`). Chúng bỏ qua việc kiểm tra an toàn kiểu ở thời điểm biên dịch, quay lại các hành vi di sản trước phiên bản Java 5 và có nguy cơ ném ra ngoại lệ `ClassCastException` trong thời gian chạy.

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  // XẤU: Kiểu thô cho phép biên dịch việc thêm nhiều kiểu phần tử hỗn hợp
  List rawList = new ArrayList();
  rawList.add("Hello");
  rawList.add(123); // Biên dịch bình thường!

  // Treo chương trình lúc chạy khi đọc:
  String str = (String) rawList.get(1); // ClassCastException trong thời gian chạy!

  // TỐT: Danh sách Generic bắt các lỗi ngay từ thời điểm biên dịch
  List<String> genericList = new ArrayList<>();
  // genericList.add(123); // LỖI BIÊN DỊCH!
  ```

---

### Tránh sử dụng null khi có thể (Avoid null when possible)

Giảm thiểu các lỗi `NullPointerException` bằng cách áp dụng các mô hình lập trình phòng ngừa:

- **Quy tắc**:
  - **Trả về Bộ sưu tập Rỗng**: Không bao giờ trả về `null` cho các kiểu trả về danh sách/map/mảng. Hãy trả về `Collections.emptyList()`, `Collections.emptyMap()`, hoặc các mảng rỗng.
  - **Sử dụng Optional**: Đối với các giá trị đơn tùy chọn, hãy trả về `Optional<T>` để buộc người gọi phải xử lý trường hợp vắng mặt giá trị.
  - **Xác thực Đối số**: Sử dụng `Objects.requireNonNull()` hoặc các thư viện như các chú thích (annotations) `@NonNull`.
- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  // XẤU: Người gọi phải nhớ thực hiện kiểm tra null nếu không sẽ có nguy cơ bị NPE
  public List<String> getRoles() {
      return null; 
  }

  // TỐT: An toàn khi lặp trực tiếp mà không cần kiểm tra null
  public List<String> getRoles() {
      return Collections.emptyList();
  }
  ```

---

### Viết mã nguồn có thể kiểm thử (Write testable code)

Mã nguồn có thể kiểm thử có tính mô-đun, tách rời, và dễ dàng chạy cô lập bên trong các bài kiểm thử đơn vị.

- **Quy tắc**:
  - **Tránh viết cứng các đối tượng cộng tác**: Không khởi tạo các đối tượng phụ thuộc bằng từ khóa `new` bên trong các phương thức. Hãy sử dụng Tiêm Phụ Thuộc (Dependency Injection - DI) để truyền các đối tượng cộng tác thông qua hàm khởi tạo.
  - **Tránh các lời gọi Static**: Tránh gọi trực tiếp các phương thức môi trường static như `System.currentTimeMillis()` hoặc `Database.query()`. Hãy bọc chúng trong các lớp dịch vụ có thể được giả lập (mocked).
- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  // XẤU: Bị liên kết chặt chẽ với đồng hồ hệ thống. Không thể viết một kiểm thử xác định cho quá khứ/tương lai.
  class Order {
      public boolean isExpired() {
          return System.currentTimeMillis() > expirationTime;
      }
  }

  // TỐT: Tiêm vào một thực thể Clock. Cho phép giả lập các mốc thời gian cụ thể trong các bài kiểm thử.
  class Order {
      private final Clock clock;

      public Order(Clock clock) {
          this.clock = clock;
      }

      public boolean isExpired() {
          return clock.millis() > expirationTime;
      }
  }
  ```

---

### Phân chia rõ ràng trách nhiệm của lớp/phương thức (Separate class/method responsibilities)

Một lớp hoặc phương thức nên tuân thủ Nguyên lý Đơn Trách Nhiệm (SRP) — nó chỉ nên có duy nhất một lý do để thay đổi.

- **Quy tắc**: Tránh các "lớp vạn năng" (God classes) trộn lẫn giữa truy cập cơ sở dữ liệu, tính toán nghiệp vụ, phân tích cú pháp đầu vào và ghi nhật ký. Hãy chia chúng thành các dịch vụ chuyên biệt.
- **Ví dụ**: Tạo một `UserRepository` cho các hoạt động với DB, một `UserService` cho logic nghiệp vụ, và một `UserSerializer` cho việc chuyển đổi JSON.

---

### Sử dụng tính bất biến khi thích hợp (Immutability when appropriate)

Đối tượng bất biến là các đối tượng có trạng thái không thể bị thay đổi sau khi xây dựng. Chúng vốn đã an toàn với luồng (Thread), an toàn để chia sẻ, và dễ dàng để tư duy.

- **Quy tắc**:
  - Khai báo các trường là `private` và `final`.
  - Không cung cấp các phương thức setter.
  - Khai báo lớp là `final` (để nó không thể bị kế thừa).
  - Đối với các trường bộ sưu tập, hãy trả về các chế độ xem không thể sửa đổi (sử dụng `List.copyOf()` hoặc `Collections.unmodifiableList()`) hoặc thực hiện sao chép phòng ngừa (defensive copying) trong hàm khởi tạo và các phương thức truy xuất.
- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  public final class ImmutableUser {
    private final String username;
    private final List<String> roles;

    public ImmutableUser(String username, List<String> roles) {
        this.username = username;
        // Sao chép phòng ngừa để ngăn chặn người gọi sửa đổi danh sách được truyền vào
        this.roles = List.copyOf(roles); 
    }

    public String getUsername() { return username; }
    public List<String> getRoles() { return roles; } // Trả về chế độ xem không thể sửa đổi
    }
  ```

---

## Tại sao việc Nuốt Ngoại lệ lại Nguy hiểm (Why Exception Swallowing Is Dangerous)

Nuốt ngoại lệ xảy ra khi một chương trình bắt một ngoại lệ nhưng không thực hiện hành động khắc phục nào, không ghi nhật ký hoặc không lan truyền lỗi. Khi một ngoại lệ được ném ra, JVM tạm dừng luồng kiểm soát thông thường và thực hiện tìm kiếm trên Bảng Ngoại Lệ (Exception Table) của phương thức hiện tại trong mã bytecode. Nếu một bộ xử lý phù hợp, quyền kiểm soát được chuyển giao cho nó; nếu không, JVM sẽ gỡ rối ngăn xếp (unwinds the stack), loại bỏ các khung ngăn xếp đang hoạt động cho đến khi tìm thấy bộ xử lý hoặc luồng đó bị hủy. Việc nuốt một ngoại lệ sẽ tạm dừng quá trình gỡ rối ngăn xếp này mà không giải quyết nguyên nhân gốc rễ, loại bỏ trạng thái nội bộ của ngoại lệ bao gồm thông điệp, nguyên nhân và mảng dấu vết ngăn xếp. Điều này che giấu các lỗi cơ sở, ngăn cản các hệ thống giám sát thời gian chạy nắm bắt được các lỗi, và tạo ra các mối nguy hiểm nghiêm trọng về bảo mật nơi các hoạt động thất bại một cách âm thầm, có khả năng để lại hệ thống trong trạng thái bị hỏng hoặc mới được khởi tạo một nửa.

### Mô hình tư duy: Mất Dấu Vết Ngăn Xếp và Ngữ Cảnh Chẩn Đoán (Mental Model: Stack Trace and Diagnostic Context Loss)

```text
Đường dẫn lỗi bình thường (Được lan truyền):
Phương thức C (Ném) ---> Phương thức B (Lan truyền) ---> Phương thức A (Bắt & Ghi log Stack Trace)
Kết quả: Dấu vết được bảo toàn, lỗi được cô lập.

Đường dẫn bị nuốt (Mất):
Phương thức C (Ném) ---> Phương thức B (Bắt { }) ---> Phương thức A (Tiếp tục chạy mù quáng, giả định thành công)
Kết quả: Đối tượng ngoại lệ bị loại bỏ; Dấu vết Stack Trace bị mất; hệ thống âm thầm bị lỗi dữ liệu.
```

### Ví dụ Code

```java
import java.io.IOException;

public class ExceptionSwallowingDemo {
    public static void loadConfigSwallowed() {
        try {
            throw new IOException("Disk failure reading config file");
        } catch (IOException e) {
            // Bad: Ngoại lệ bị nuốt. Không ghi log, no rethrow, no recovery.
        }
    }

    public static void loadConfigSafe() {
        try {
            throw new IOException("Disk failure reading config file");
        } catch (IOException e) {
            // Good: Bảo toàn ngữ cảnh bằng cách bọc và ném
            throw new RuntimeException("Failed to load application config", e);
        }
    }

    public static void main(String[] args) {
        loadConfigSwallowed();
        System.out.println("Swallowed completed without warning. Application unstable."); 
        // Output: Swallowed completed without warning. Application unstable.

        try {
            loadConfigSafe();
        } catch (RuntimeException e) {
            System.out.println("Caught safely: " + e.getMessage() + " | Cause: " + e.getCause().getMessage());
            // Output: Caught safely: Failed to load application config | Cause: Disk failure reading config file
        }
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

```text
Ngoại lệ xảy ra → Khối catch trống chặn đối tượng ngoại lệ → Quá trình gỡ rối ngăn xếp của JVM bị tạm dừng → Các chi tiết chẩn đoán (stack trace, nguyên nhân) bị loại bỏ → Hệ thống tiếp tục thực thi với trạng thái bị hỏng/hành vi không xác định
```

---

## Tại sao Ngoại lệ Tự định nghĩa lại được Nhóm theo Lý do Phục hồi (Why Custom Exceptions Group by Recovery Rationale)

Các ngoại lệ tự định nghĩa nên được thiết kế dựa trên việc liệu ứng dụng gọi có thể phục hồi từ lỗi bằng lập trình hay không. Đặc tả Ngôn ngữ Java (JLS) thực thi các ngoại lệ có kiểm tra (checked exceptions) ở thời điểm biên dịch bằng cách yêu cầu các phương thức phải xử lý hoặc khai báo chúng trong chữ ký `throws` của họ. Các ngoại lệ không kiểm tra (unchecked exceptions), vốn kế thừa từ `RuntimeException`, bỏ qua việc xác minh ở thời điểm biên dịch vì chúng thường chỉ ra lỗi của lập trình viên hoặc các trạng thái hệ thống không thể phục hồi. Khi định nghĩa các ngoại lệ tùy chỉnh, việc kế thừa lớp `Exception` (có kiểm tra) ra hiệu cho người gọi rằng điều kiện đó là có thể phục hồi (ví dụ: lỗi hết thời gian mạng tạm thời) và buộc họ phải viết các đường dẫn phục hồi. Ngược lại, việc kế thừa lớp `RuntimeException` (không kiểm tra) biểu thị rằng thất bại đó là không thể phục hồi (ví dụ: thông tin đăng nhập cơ sở dữ liệu bị sai hoặc payload API không hợp lệ), cho phép luồng thất bại nhanh (fail fast), hủy bỏ hoạt động và ghi nhật ký dấu vết ngăn xếp chẩn đoán mà không làm ô nhiễm các chữ ký phương thức của chuỗi cuộc gọi.

### Mô hình tư duy: Phục hồi Ngoại lệ Có kiểm tra vs Không kiểm tra (Mental Model: Checked vs Unchecked Exception Recovery)

```text
Có thể phục hồi (Ngoại lệ Có kiểm tra):
[Lỗi đọc đĩa] ---> CustomCheckedException ---> Khối Catch ---> [Thử lại với tệp thay thế]

Không thể phục hồi (Ngoại lệ Không kiểm tra):
[Con trỏ Null/Cấu hình lỗi] ---> CustomUncheckedException ---> Hủy bỏ ---> [Ghi log Stack Trace & Thoát]
```

### Ví dụ Code

```java
// Ngoại lệ tùy chỉnh có thể phục hồi
class UserInputException extends Exception {
    public UserInputException(String msg) { super(msg); }
}

// Ngoại lệ tùy chỉnh không thể phục hồi
class SystemDatabaseException extends RuntimeException {
    public SystemDatabaseException(String msg, Throwable cause) { super(msg, cause); }
}

public class RecoveryExceptionDemo {
    public static void processInput(String input) throws UserInputException {
        if (input == null || input.isBlank()) {
            throw new UserInputException("Input cannot be empty. Please retry.");
        }
    }

    public static void connectDatabase() {
        // Lỗi tải trình điều khiển hoặc cấu hình không thể phục hồi
        throw new SystemDatabaseException("Database driver not found", new ClassNotFoundException());
    }

    public static void main(String[] args) {
        try {
            processInput("");
        } catch (UserInputException e) {
            System.out.println("Recoverable: " + e.getMessage()); // Output: Recoverable: Input cannot be empty. Please retry.
        }

        try {
            connectDatabase();
        } catch (SystemDatabaseException e) {
            System.out.println("Unrecoverable: " + e.getMessage() + " | Cause: " + e.getCause().getClass().getSimpleName());
            // Output: Unrecoverable: Database driver not found | Cause: ClassNotFoundException
        }
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

```text
Xác định khả năng phục hồi → Chọn lớp cha (Exception vs RuntimeException) → Kiểm tra tại thời điểm biên dịch được thực thi (đối với ngoại lệ có kiểm tra) vs luồng bị hủy bỏ (đối với ngoại lệ không kiểm tra) → Developer buộc phải triển khai phục hồi hoặc luồng được kết thúc an toàn → Phân chia sạch sẽ các mối quan tâm về xử lý lỗi
```

---

## Liên Kết Tham Khảo (Reference Links)

- [Oracle Java Exception Tutorial](https://docs.oracle.com/javase/tutorial/essential/exceptions/)
- [Java Language Specification - Exceptions](https://docs.oracle.com/javase/specs/jls/se21/html/jls-11.html)
- [Dev.java Exceptions Guide](https://dev.java/learn/exceptions/)
