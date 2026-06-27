# Thực hành tốt nhất trong Java - Phần 2 (Best Practices in Java - Part 2)

## Mục tiêu học tập

Tài liệu này tập trung vào các **Thực hành tốt nhất (Best Practices)** nâng cao trong Java bao gồm các quy tắc xử lý ngoại lệ, an toàn kiểu dữ liệu, tránh null, viết mã nguồn phục vụ kiểm thử, và các mẫu thiết kế lớp bất biến. Hãy nghiên cứu từng khái niệm dưới dạng quy tắc Java thực tế.

## Đề cương chi tiết

| Khái niệm | Điều cần biết |
| --- | --- |
| `Do not swallow exceptions` | Không nuốt ngoại lệ (Do not swallow exceptions): Các quy tắc xử lý hoặc lan truyền ngoại lệ thay vì bỏ qua chúng. |
| `Use interface type when declaring Collection` | Sử dụng kiểu giao diện khi khai báo bộ sưu tập (Use interface type when declaring Collection): Viết mã hướng tới các giao diện (`List`, `Set`, `Map`) thay vì các lớp cụ thể. |
| `List<String> list = new ArrayList<>();` | List<String> list = new ArrayList<>();: Khai báo các bộ sưu tập an toàn kiểu bằng cách sử dụng suy luận kiểu kim cương (diamond inference). |
| `Avoid raw type` | Tránh kiểu thô (Avoid raw type): Ngăn ngừa các cảnh báo kiểu dữ liệu tại thời điểm biên dịch bằng cách tránh các lớp thô không sử dụng generic. |
| `Avoid null when possible` | Tránh null khi có thể (Avoid null when possible): Các kỹ thuật để ngăn chặn NullPointerException. |
| `Write testable code` | Viết mã nguồn có thể kiểm thử (Write testable code): Các quy tắc cấu trúc (Tiêm phụ thuộc, khả năng tương thích giả lập) để phục vụ kiểm thử. |
| `Separate class/method responsibilities` | Phân chia rõ ràng trách nhiệm của lớp/phương thức: Áp dụng Nguyên lý đơn nhiệm (SRP) để giữ cho mã nguồn dễ bảo trì. |
| `Immutability when appropriate` | Tính bất biến khi thích hợp (Immutability when appropriate): Tạo các lớp bất biến sử dụng record, các trường final, và các dạng xem không thể sửa đổi (unmodifiable view). |

---

## Ghi chú chi tiết

### Không nuốt ngoại lệ (Do not swallow exceptions)

Nuốt ngoại lệ (bắt một ngoại lệ và không làm gì cả, hoặc chỉ ghi một thông điệp ghi nhật ký tầm thường mà không giải quyết hay ném lại nó) là một phản mẫu (anti-pattern) lớn. Nó che giấu các lỗi hệ thống, khiến việc gỡ lỗi trở nên gần như không thể.

- **Ví dụ**:
  ```java
  // BAD: Silently ignores the exception, leaving application in an unstable state
  try {
      parseConfiguration();
  } catch (IOException e) {
      // Swallowed! Code execution continues blindly.
  }

  // GOOD: Logs details and propagates the exception wrapped in a RuntimeException
  try {
      parseConfiguration();
  } catch (IOException e) {
      logger.error("Configuration loading failed, exiting process.", e);
      throw new RuntimeException("Fatal error parsing configuration", e);
  }
  ```

---

### Sử dụng kiểu giao diện khi khai báo bộ sưu tập (Use interface type when declaring Collection)

Hãy lập trình hướng tới các giao diện (`List`, `Set`, `Map`, `Queue`) thay vì các lớp cụ thể (`ArrayList`, `HashSet`, `HashMap`, `LinkedList`). Điều này giúp tách biệt mã nguồn của bạn khỏi các chi tiết triển khai cụ thể, cho phép bạn dễ dàng thay đổi các bộ sưu tập khi các yêu cầu thay đổi.

- **Ví dụ**:
  ```java
  // BAD: Hardcodes dependency on ArrayList implementation details
  ArrayList<String> users = new ArrayList<>();

  // GOOD: Declares variable using the interface type
  List<String> users = new ArrayList<>();
  ```

---

### List<String> list = new ArrayList<>();

Luôn chỉ định các đối số kiểu để đảm bảo an toàn kiểu tại thời điểm biên dịch. Sử dụng toán tử kim cương (`<>`) ở phía gọi hàm khởi tạo để trình biên dịch tự động suy luận ra các tham số kiểu.

- **Ví dụ**:
  ```java
  List<String> list = new ArrayList<>(); // Clean, type-safe, dry
  ```

---

### Tránh kiểu thô (Avoid raw type)

Kiểu thô (raw type) là các lớp generic được khai báo mà không có tham số kiểu (ví dụ: sử dụng `List` thay vì `List<String>`). Chúng bỏ qua kiểm tra an toàn kiểu tại thời điểm biên dịch, quay lại các hành vi cũ trước Java 5 và có nguy cơ ném ra các ngoại lệ `ClassCastException` lúc chạy.

- **Ví dụ**:
  ```java
  // BAD: Raw type allows compiling mixed element additions
  List rawList = new ArrayList();
  rawList.add("Hello");
  rawList.add(123); // Compiles fine!

  // Runtime crash when reading:
  String str = (String) rawList.get(1); // ClassCastException at runtime!

  // GOOD: Generic list catches mistakes at compile time
  List<String> genericList = new ArrayList<>();
  // genericList.add(123); // COMPILE ERROR!
  ```

---

### Tránh null khi có thể (Avoid null when possible)

Giảm thiểu NullPointerException bằng cách áp dụng các mẫu lập trình phòng vệ:

- **Các quy tắc**:
  - **Trả về các bộ sưu tập trống**: Không bao giờ trả về `null` cho các kiểu trả về dạng danh sách/bản đồ/mảng. Hãy trả về `Collections.emptyList()`, `Collections.emptyMap()`, hoặc các mảng trống.
  - **Sử dụng Optional**: Đối với các giá trị đơn tùy chọn, trả về `Optional<T>` để buộc người gọi phải xử lý trường hợp vắng mặt giá trị.
  - **Xác thực đối số**: Sử dụng `Objects.requireNonNull()` hoặc các thư viện như các chú thích `@NonNull`.
- **Ví dụ**:
  ```java
  // BAD: Caller must remember to do null check or risk NPE
  public List<String> getRoles() {
      return null; 
  }

  // GOOD: Safe to iterate directly without null checks
  public List<String> getRoles() {
      return Collections.emptyList();
  }
  ```

---

### Viết mã nguồn có thể kiểm thử (Write testable code)

Mã nguồn có thể kiểm thử được thiết kế theo dạng mô-đun, tách biệt, và dễ chạy cô lập bên trong các bài kiểm thử đơn vị.

- **Các quy tắc**:
  - **Tránh các cộng sự được mã hóa cứng (Hardcoded Collaborators)**: Không khởi tạo các phụ thuộc bằng từ khóa `new` bên trong các phương thức. Sử dụng Tiêm phụ thuộc (Dependency Injection - DI) để truyền các cộng sự qua hàm khởi tạo.
  - **Tránh các cuộc gọi tĩnh (Static Call)**: Tránh các cuộc gọi trực tiếp đến các phương thức môi trường tĩnh như `System.currentTimeMillis()` hoặc `Database.query()`. Hãy bao bọc chúng trong các dịch vụ có thể được giả lập (mock).
- **Ví dụ**:
  ```java
  // BAD: Coupled to clock. Cannot write a deterministic test for past/future.
  class Order {
      public boolean isExpired() {
          return System.currentTimeMillis() > expirationTime;
      }
  }

  // GOOD: Inject a Clock instance. Allows mocking specific times in tests.
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

Một lớp hoặc phương thức nên tuân thủ Nguyên lý đơn nhiệm (Single Responsibility Principle - SRP) — nó nên có chính xác một lý do duy nhất để thay đổi.

- **Quy tắc**: Tránh các "lớp vạn năng (God class)" trộn lẫn truy cập cơ sở dữ liệu, tính toán nghiệp vụ, phân tích cú pháp đầu vào, và ghi log. Hãy chia nhỏ chúng thành các dịch vụ chuyên biệt.
- **Ví dụ**: Tạo một `UserRepository` cho các hoạt động cơ sở dữ liệu DB, một `UserService` cho logic nghiệp vụ, và một `UserSerializer` để chuyển đổi JSON.

---

### Tính bất biến khi thích hợp (Immutability when appropriate)

Các đối tượng bất biến là các đối tượng có trạng thái không thể thay đổi sau khi khởi tạo. Chúng vốn dĩ an toàn luồng, an toàn để chia sẻ, và dễ dàng để tư duy.

- **Các quy tắc**:
  - Khai báo các trường là `private` và `final`.
  - Không cung cấp các phương thức setter.
  - Khai báo lớp là `final` (để nó không thể bị kế thừa ghi đè).
  - Đối với các trường bộ sưu tập, trả về các dạng xem không thể sửa đổi (unmodifiable view) (sử dụng `List.copyOf()` hoặc `Collections.unmodifiableList()`) hoặc thực hiện sao chép phòng ngừa trong hàm khởi tạo và các phương thức truy cập.
- **Ví dụ**:
  ```java
  public final class ImmutableUser {
    private final String username;
    private final List<String> roles;

    public ImmutableUser(String username, List<String> roles) {
        this.username = username;
        // Defensive copy to prevent caller modifying the passed list
        this.roles = List.copyOf(roles); 
    }

    public String getUsername() { return username; }
    public List<String> getRoles() { return roles; } // Returns unmodifiable view
  }
  ```

---

## Tại sao nuốt ngoại lệ lại nguy hiểm

Nuốt ngoại lệ xảy ra khi một chương trình bắt một ngoại lệ nhưng không thực hiện hành động khắc phục nào, không ghi log hoặc không lan truyền lỗi. Khi một ngoại lệ được ném ra, JVM tạm dừng luồng điều khiển bình thường và thực hiện tìm kiếm trên Bảng ngoại lệ (Exception Table) của phương thức hiện tại trong mã bytecode. Nếu tìm thấy trình xử lý khớp, quyền điều khiển được chuyển sang cho nó; ngược lại, JVM tháo gỡ ngăn xếp (unwind the stack), đẩy các khung ngăn xếp (stack frame) đang hoạt động ra ngoài cho đến khi tìm thấy trình xử lý hoặc luồng chết. Việc nuốt ngoại lệ sẽ tạm dừng quá trình tháo gỡ này mà không giải quyết nguyên nhân gốc rễ, loại bỏ trạng thái nội bộ của ngoại lệ bao gồm thông điệp, nguyên nhân và mảng dấu vết ngăn xếp (stack trace). Điều này che giấu các lỗi cơ bản, ngăn chặn các hệ thống giám sát lúc chạy chụp lại các lỗi, và tạo ra các mối nguy hiểm bảo mật nghiêm trọng khi các thao tác thất bại trong im lặng, có khả năng để lại hệ thống ở trạng thái bị hỏng hoặc mới khởi tạo một nửa.

### Mô hình tư duy: Dấu vết ngăn xếp và Sự mất mát ngữ cảnh chẩn đoán

```text
Đường dẫn lỗi bình thường (Được lan truyền):
Phương thức C (Ném) ---> Phương thức B (Lan truyền) ---> Phương thức A (Bắt & Ghi log Stack Trace)
Kết quả: Dấu vết được bảo toàn, lỗi được cô lập.

Đường dẫn bị nuốt (Bị mất):
Phương thức C (Ném) ---> Phương thức B (Bắt { }) ---> Phương thức A (Tiếp tục mù quáng, giả định thành công)
Kết quả: Đối tượng ngoại lệ bị loại bỏ; Dấu vết ngăn xếp bị mất; hệ thống hỏng hóc trong im lặng.
```

### Ví dụ mã nguồn

```java
import java.io.IOException;

public class ExceptionSwallowingDemo {
    public static void loadConfigSwallowed() {
        try {
            throw new IOException("Disk failure reading config file");
        } catch (IOException e) {
            // Bad: Exception swallowed. No log, no rethrow, no recovery.
        }
    }

    public static void loadConfigSafe() {
        try {
            throw new IOException("Disk failure reading config file");
        } catch (IOException e) {
            // Good: Preserves context by wrapping and throwing
            throw new RuntimeException("Failed to load application config", e);
        }
    }

    public static void main(String[] args) {
        loadConfigSwallowed();
        System.out.println("Swallowed completed without warning. Application unstable."); 

        try {
            loadConfigSafe();
        } catch (RuntimeException e) {
            System.out.println("Caught safely: " + e.getMessage() + " | Cause: " + e.getCause().getMessage());
        }
    }
}
```

### Chuỗi nguyên nhân - kết quả

```text
Ngoại lệ xảy ra ➔ Khối catch trống chặn đối tượng ngoại lệ ➔ Quá trình tháo gỡ ngăn xếp JVM bị dừng lại ➔ Các chi tiết chẩn đoán (stack trace, nguyên nhân) bị loại bỏ ➔ Hệ thống tiếp tục thực thi với trạng thái bị hỏng/hành vi không xác định
```

---

## Tại sao ngoại lệ tùy chỉnh nhóm theo cơ sở phục hồi

Các ngoại lệ tùy chỉnh nên được thiết kế dựa trên việc ứng dụng gọi có thể phục hồi bằng mã một cách lập trình từ thất bại đó hay không. Đặc tả Ngôn ngữ Java (Java Language Specification) thực thi các ngoại lệ được kiểm tra (checked exception) tại thời điểm biên dịch bằng cách yêu cầu các phương thức phải xử lý chúng hoặc khai báo chúng trong chữ ký `throws`. Các ngoại lệ không được kiểm tra (unchecked exception), kế thừa từ `RuntimeException`, bỏ qua xác thực tại thời điểm biên dịch vì chúng thường chỉ ra các lỗi của lập trình viên hoặc trạng thái hệ thống không thể phục hồi. Khi định nghĩa các ngoại lệ tùy chỉnh, việc mở rộng `Exception` (checked) báo hiệu cho người gọi rằng điều kiện đó có thể phục hồi được (ví dụ: hết thời gian chờ mạng tạm thời) và buộc họ phải viết các đường dẫn phục hồi. Ngược lại, việc mở rộng `RuntimeException` (unchecked) biểu thị rằng thất bại là không thể phục hồi (ví dụ: thông tin đăng nhập cơ sở dữ liệu không hợp lệ hoặc dữ liệu API không hợp lệ), cho phép luồng thất bại nhanh (fail fast), hủy bỏ thao tác, và ghi nhật ký dấu vết ngăn xếp chẩn đoán mà không làm ô nhiễm các chữ ký phương thức trong chuỗi cuộc gọi.

### Mô hình tư duy: Phục hồi ngoại lệ Checked so với Unchecked

```text
Có thể phục hồi (Checked Exception):
[Đọc đĩa thất bại] ---> CustomCheckedException ---> Khối Catch ---> [Thử lại với tệp thay thế]

Không thể phục hồi (Unchecked Exception):
[Con trỏ Null/Cấu hình sai] ---> CustomUncheckedException ---> Hủy bỏ ---> [Ghi log Stack Trace & Thoát]
```

### Ví dụ mã nguồn

```java
// Recoverable custom exception
class UserInputException extends Exception {
    public UserInputException(String msg) { super(msg); }
}

// Unrecoverable custom exception
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
        // Unrecoverable configuration or driver load failure
        throw new SystemDatabaseException("Database driver not found", new ClassNotFoundException());
    }

    public static void main(String[] args) {
        try {
            processInput("");
        } catch (UserInputException e) {
            System.out.println("Recoverable: " + e.getMessage());
        }

        try {
            connectDatabase();
        } catch (SystemDatabaseException e) {
            System.out.println("Unrecoverable: " + e.getMessage() + " | Cause: " + e.getCause().getClass().getSimpleName());
        }
    }
}
```

### Chuỗi nguyên nhân - kết quả

```text
Xác định khả năng phục hồi ➔ Lựa chọn lớp cha (Exception so với RuntimeException) ➔ Thực thi kiểm tra lúc biên dịch (đối với checked) so với hủy luồng (đối với unchecked) ➔ Nhà phát triển buộc phải triển khai phục hồi hoặc luồng kết thúc an toàn ➔ Tách biệt sạch sẽ các mối quan tâm xử lý lỗi
```

---

## Liên kết tham khảo

- [Oracle Java Exception Tutorial](https://docs.oracle.com/javase/tutorial/essential/exceptions/)
- [Java Language Specification - Exceptions](https://docs.oracle.com/javase/specs/jls/se21/html/jls-11.html)
- [Dev.java Exceptions Guide](https://dev.java/learn/exceptions/)
