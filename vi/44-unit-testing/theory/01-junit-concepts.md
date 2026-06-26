# Kiểm thử đơn vị cơ bản - Phần 1: Các kiến thức cơ bản về JUnit 5 & Mockito (Basic Unit Testing - Part 1: JUnit 5 & Mockito Basics)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm các nguyên lý cơ bản của kiểm thử đơn vị (unit testing) trong Java sử dụng JUnit 5 và Mockito. Bạn sẽ tìm hiểu về vòng đời kiểm thử (test lifecycle), các khẳng định (assertions) và tương tác với đối tượng giả lập (mock objects).

## Phạm vi đề cương (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `JUnit` | Framework kiểm thử Java để viết và chạy các bài kiểm thử đơn vị tự động. |
| `Test case` | Một phương thức được đánh dấu bằng `@Test` dùng để xác minh một hành vi cụ thể của một lớp. |
| `Assertion` | Các phương thức tĩnh từ `org.junit.jupiter.api.Assertions` để xác minh kết quả mong đợi. |
| `@Test` | Annotation đánh dấu một phương thức là một ca kiểm thử (test case); không được là private hoặc static. |
| `@BeforeEach` | Chạy trước mỗi phương thức `@Test` để thiết lập môi trường kiểm thử (test fixtures). |
| `@AfterEach` | Chạy sau mỗi phương thức `@Test` để dọn dẹp trạng thái. |
| `@BeforeAll` | Chạy một lần duy nhất trước tất cả các bài kiểm thử trong một lớp; phải là static trừ khi sử dụng vòng đời trên mỗi lớp (per-class lifecycle). |
| `@AfterAll` | Chạy một lần duy nhất sau tất cả các bài kiểm thử trong một lớp; phải là static trừ khi sử dụng vòng đời trên mỗi lớp (per-class lifecycle). |
| `Basic Mockito` | Framework giả lập (mocking framework) được sử dụng để cô lập đơn vị được kiểm thử (unit under test) bằng cách mô phỏng các phụ thuộc. |
| `Mock object` | Một phụ thuộc được mô phỏng, cấu hình để trả về các phản hồi có sẵn hoặc xác thực các cuộc gọi phương thức. |

---

## Ghi chú chi tiết (Detailed Notes)

### JUnit & Ca kiểm thử (JUnit & The Test Case)

**JUnit 5** (Jupiter) là thư viện tiêu chuẩn để viết các bài kiểm thử đơn vị tự động trong Java hiện đại. Một **Ca kiểm thử (Test Case)** là một lớp kiểm thử đơn lẻ hoặc phương thức kiểm thử đơn lẻ nhắm mục tiêu vào một đường dẫn hành vi cụ thể trong mã nguồn.

- **Vòng đời kiểm thử & Các Annotation (Test Lifecycle & Annotations)**:
  - `@Test`: Đánh dấu một phương thức là một bài kiểm thử. JUnit tạo ra một thực thể (instance) mới của lớp kiểm thử cho mỗi phương thức `@Test` để đảm bảo tính cô lập của kiểm thử (test isolation).
  - `@BeforeEach` / `@AfterEach`: Được thực thi trước và sau mỗi phương thức kiểm thử riêng lẻ. Rất lý tưởng để reset các trường thực thể, mở các tài nguyên sạch chỉ dùng cục bộ cho bài kiểm thử.
  - `@BeforeAll` / `@AfterAll`: Được thực thi một lần trước khi tất cả các bài kiểm thử chạy, và một lần sau khi tất cả các bài kiểm thử đã hoàn thành.
    - *Lưu ý*: Theo mặc định, các phương thức này phải là `static` vì JUnit tạo thực thể lớp kiểm thử một lần cho mỗi phương thức kiểm thử. Nếu bạn đánh dấu lớp của mình bằng `@TestInstance(Lifecycle.PER_CLASS)`, bạn có thể khai báo các phương thức này mà không cần static.

- **Ví dụ có thể chạy được (Vòng đời & Khẳng định - Lifecycle & Assertions)**:
  ```java
  import org.junit.jupiter.api.*;
  import static org.junit.jupiter.api.Assertions.*;

  @TestInstance(TestInstance.Lifecycle.PER_METHOD) // Vòng đời mặc định
  class CalculatorTest {
      private Calculator calculator;

      @BeforeAll
      static void initGlobalResource() {
          System.out.println("Shared resource initialized once");
      }

      @BeforeEach
      void setUp() {
          calculator = new Calculator(); // Tạo thực thể mới cho mỗi bài kiểm thử
      }

      @Test
      void testAddition() {
          int result = calculator.add(10, 5);
          assertEquals(15, result, "10 + 5 should equal 15");
      }

      @Test
      void testDivisionByZero() {
          assertThrows(ArithmeticException.class, () -> calculator.divide(10, 0));
      }

      @AfterEach
      void tearDown() {
          calculator = null;
      }

      @AfterAll
      static void cleanGlobalResource() {
          System.out.println("Shared resource cleaned up once");
      }
  }
  ```

---

### Mockito cơ bản & Đối tượng giả lập (Basic Mockito & Mock Objects)

**Mockito** là một framework giả lập cho phép lập trình viên cô lập lớp được kiểm thử bằng cách thay thế các phụ thuộc thực tế bằng các **Đối tượng giả lập (Mock Objects)**. Đối tượng mock có thể trả về các giá trị được cấu hình trước (stubbing) và ghi nhớ các tương tác (xác thực - verification).

- **Stubbing (`when`) so với Xác thực (`verify`) (Stubbing vs. Verification)**:
  - **Stubbing (giả lập kết quả trả về)**: Cấu hình một đối tượng mock để trả về một giá trị cụ thể khi một phương thức cụ thể được gọi.
  - **Verification (xác thực)**: Kiểm tra xem một phương thức trên đối tượng mock đã được gọi với các tham số cụ thể bao nhiêu lần.

- **Ví dụ có thể chạy được (Mockito)**:
  ```java
  import org.junit.jupiter.api.Test;
  import org.mockito.Mockito;
  import static org.junit.jupiter.api.Assertions.*;
  import static org.mockito.Mockito.*;

  class UserServiceTest {

      @Test
      void testGetUserEmail_WithMockedRepository() {
          // 1. Tạo đối tượng giả lập
          UserRepository mockRepo = mock(UserRepository.class);

          // 2. Hành vi stubbing: khi mockRepo.findUsername(1) được gọi, trả về "alice"
          when(mockRepo.findUsername(1L)).thenReturn("alice");

          UserService userService = new UserService(mockRepo);

          // 3. Thực thi hành động & khẳng định
          String username = userService.getUsername(1L);
          assertEquals("alice", username);

          // 4. Xác thực: Xác minh rằng findUsername(1L) được gọi chính xác 1 lần
          verify(mockRepo, times(1)).findUsername(1L);
      }
  }
  ```

---

## Các lỗi thường gặp & Cạm bẫy (Common Mistakes & Traps)

### 1. Sai thứ tự tham số khẳng định (JUnit 5 vs. TestNG / AssertJ) (Wrong Assertion Parameter Order (JUnit 5 vs. TestNG / AssertJ))
Trong JUnit 5, các khẳng định (assertions) có chữ ký: `assertEquals(expected, actual, message)`.
Một lỗi thường gặp là hoán đổi vị trí hai tham số `expected` (kết quả mong đợi) và `actual` (kết quả thực tế). Mặc dù điều này không làm bài kiểm thử thất bại trực tiếp, nó tạo ra các báo cáo lỗi cực kỳ gây nhầm lẫn và sai lệch.

*Không chính xác:*
```java
// Nếu add(2,2) trả về 5, báo cáo sẽ ghi: "Expected [5] but was [4]" (nhầm lẫn)
assertEquals(calculator.add(2, 2), 4); 
```

*Chính xác:*
```java
// Báo cáo ghi chính xác: "Expected [4] but was [5]"
assertEquals(4, calculator.add(2, 2)); 
```

### 2. Không khai báo static cho các phương thức `@BeforeAll` / `@AfterAll` (Failing to Make `@BeforeAll` / `@AfterAll` Methods Static)
Nếu không thiết lập `@TestInstance(Lifecycle.PER_CLASS)`, việc quên khai báo static cho các phương thức `@BeforeAll` hoặc `@AfterAll` sẽ gây ra ngoại lệ `JUnitException` tại thời điểm chạy trước khi bất kỳ bài kiểm thử nào được thực thi.

*Không chính xác:*
```java
@BeforeAll
void init() { // SẬP: Phải là static trừ khi thiết lập PER_CLASS!
    dbConnection = new DbConnection();
}
```

### 3. Giả lập Value Objects hoặc DTO (Mocking Value Objects or DTOs)
Mockito nên được sử dụng để giả lập các service, repository hoặc các ranh giới hệ thống bên ngoài. Không giả lập các cấu trúc dữ liệu thuần túy (data structures), DTO hoặc các bộ sưu tập Collections. Thay vào đó, hãy sử dụng các thực thể thực tế của các đối tượng đó. Việc giả lập một List hoặc một String đơn giản tạo ra các đoạn code boilerplate rườm rà và dễ gãy.

---

## Trường hợp điển hình: Kiểm thử một Service với Cơ sở dữ liệu và API ngoài được giả lập (Case Study: Testing a Service with Mocked Database and External API)

Dưới đây là một mẫu thiết kế production điển hình giúp cô lập lớp `PaymentService` khỏi cơ sở dữ liệu và client thông báo email bằng cách sử dụng Mockito.

```java
public class PaymentService {
    private final PaymentRepository repository;
    private final EmailClient emailClient;

    public PaymentService(PaymentRepository repository, EmailClient emailClient) {
        this.repository = repository;
        this.emailClient = emailClient;
    }

    public boolean processPayment(long userId, double amount) {
        if (amount <= 0) return false;
        
        boolean saved = repository.saveTransaction(userId, amount);
        if (saved) {
            emailClient.sendNotification(userId, "Payment of $" + amount + " successful.");
            return true;
        }
        return false;
    }
}
```

Chúng ta kiểm thử logic nghiệp vụ của `PaymentService` mà không cần kích hoạt một giao dịch cơ sở dữ liệu thực tế hoặc gửi email thật:

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Test
    void testProcessPayment_Success() {
        // Thiết lập (Arrange)
        PaymentRepository mockRepo = mock(PaymentRepository.class);
        EmailClient mockEmail = mock(EmailClient.class);
        
        when(mockRepo.saveTransaction(123L, 99.99)).thenReturn(true);
        
        PaymentService service = new PaymentService(mockRepo, mockEmail);

        // Thực thi (Act)
        boolean result = service.processPayment(123L, 99.99);

        // Khẳng định (Assert)
        assertTrue(result);
        
        // Xác minh các tương tác giả lập diễn ra đúng như mong đợi
        verify(mockRepo).saveTransaction(123L, 99.99);
        verify(mockEmail).sendNotification(123L, "Payment of $99.99 successful.");
    }
}
```

---

## Tại sao Giả lập giúp cô lập Đơn vị được kiểm thử (Why Mocking Isolates the Unit Under Test)

Kiểm thử đơn vị có một mục tiêu cụ thể: kiểm thử **một đơn vị** (một lớp hoặc phương thức) một cách độc lập khỏi các phụ thuộc bên ngoài của nó. Khi một lớp được kiểm thử (`UserService`) phụ thuộc vào một repository cơ sở dữ liệu (`UserRepository`), việc gọi các phương thức cơ sở dữ liệu thực tế trong một kiểm thử đơn vị sẽ gây ra nhiều vấn đề:

1. **Sự liên kết (Coupling)**: Bài kiểm thử sẽ thất bại nếu cơ sở dữ liệu không khả dụng, schema bị thay đổi hoặc dữ liệu kiểm thử bị sai — ngay cả khi logic của `UserService` là hoàn toàn chính xác.
2. **Tốc độ (Speed)**: Thao tác I/O cơ sở dữ liệu thực tế chậm hơn nhiều lần so với các phép khẳng định trong bộ nhớ. Một bộ kiểm thử sử dụng I/O thật có thể mất vài phút thay vì vài mili giây.
3. **Tính phi xác định (Non-determinism)**: Các hệ thống bên ngoài thực tế (các API, cơ sở dữ liệu, đồng hồ hệ thống) trả về các kết quả khác nhau giữa các lần chạy, làm cho các bài kiểm thử trở nên không ổn định (flaky).

Mockito thay thế các phụ thuộc thực tế bằng các đối tượng mock trong bộ nhớ để trả về các giá trị được cấu hình trước (stubbed). Bài kiểm thử giờ đây chỉ thực thi logic của `UserService` — repository được kiểm soát hoàn toàn bởi chính bài kiểm thử đó.

### Mô hình tư duy: Phụ thuộc thực tế so với Giả lập (Mental Model: Real Dependency vs. Mock)
```
[Không dùng Mock — liên kết chặt chẽ với cơ sở dữ liệu]
Bài kiểm thử gọi UserService.getUser(1)
    → UserService gọi UserRepository.findById(1)  ← Gọi DB thực tế
    → DB có thể bị sập, chậm hoặc có dữ liệu khác  ← Kiểm thử thất bại vì lý do sai
    → Bài kiểm thử thực chất đang test DB + mạng + cấu hình, chứ không phải UserService

[Sử dụng Mock — kiểm thử đơn vị cô lập]
Bài kiểm thử gọi UserService.getUser(1)
    → UserService gọi mockRepo.findById(1)
    → mockRepo trả về stub "alice" ngay lập tức (trong bộ nhớ)
    → Bài kiểm thử xác minh xem UserService đã xử lý "alice" chính xác chưa
    → Chỉ có logic của UserService được kiểm thử
```

### Ví dụ Code: Cô lập thông qua Mockito Stubbing và Verification (Code Example: Isolation via Mockito Stubbing and Verification)
```java
@Test
void testGetUserEmail() {
    // Arrange: giả lập phụ thuộc, định nghĩa hành vi của nó
    UserRepository mockRepo = mock(UserRepository.class);
    when(mockRepo.findById(1L)).thenReturn(new User(1L, "alice@example.com"));

    UserService service = new UserService(mockRepo); // tiêm mock vào service

    // Act: chỉ kiểm thử UserService
    String email = service.getUserEmail(1L);

    // Assert: UserService trích xuất email chính xác
    assertEquals("alice@example.com", email);

    // Verify: UserService đã gọi repo chính xác một lần
    verify(mockRepo, times(1)).findById(1L);
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
`UserService` phụ thuộc vào `UserRepository`
  → Bài kiểm thử tiêm một đối tượng `mock(UserRepository.class)` thay vì một kết nối DB thực tế
  → Lệnh `when(mockRepo.findById(1L)).thenReturn(...)` cấu hình giá trị trả về được kiểm soát
  → Bài kiểm thử chạy hoàn toàn trong bộ nhớ chỉ mất vài mili giây
  → Chỉ có logic của `UserService` mới có thể làm bài kiểm thử thất bại
  → Bài kiểm thử chạy nhanh, xác định và cô lập.
```


---

## Tại sao JUnit tạo một thực thể mới cho mỗi phương thức kiểm thử (Why JUnit Creates a New Instance Per Test Method)

Vòng đời mặc định của JUnit 5 là `PER_METHOD` — một thực thể lớp kiểm thử mới sẽ được tạo ra cho mỗi phương thức `@Test`. Lựa chọn thiết kế này đảm bảo **tính cô lập kiểm thử (test isolation)**: mỗi bài kiểm thử bắt đầu với một trạng thái thực thể sạch sẽ, ngăn ngừa sự biến đổi trạng thái trong một bài kiểm thử bị rò rỉ sang bài kiểm thử khác.

Nếu JUnit tái sử dụng một thực thể lớp kiểm thử duy nhất cho tất cả các bài kiểm thử, các trường thực thể dùng chung bị biến đổi bởi một bài kiểm thử sẽ ảnh hưởng đến bài kiểm thử tiếp theo. Thứ tự kiểm thử sẽ trở nên quan trọng, và các bài kiểm thử sẽ thất bại tùy thuộc vào thứ tự chúng được chạy — một loại lỗi được gọi là "lỗi kiểm thử phụ thuộc vào thứ tự (order-dependent test failures)".

Hệ quả thực tế: các phương thức `@BeforeAll` và `@AfterAll` theo mặc định phải là `static`, vì chúng cần chạy trước và sau khi tất cả các thực thể được tạo/hủy. Chúng không thể tham chiếu tới từ khóa `this` (không có thực thể nào tồn tại tại thời điểm đó).

`@TestInstance(Lifecycle.PER_CLASS)` chuyển sang chế độ sử dụng một thực thể chia sẻ duy nhất. Điều này hữu ích khi việc thiết lập tốn kém (chẳng hạn như khởi động một cơ sở dữ liệu nhúng - embedded database) chỉ nên diễn ra một lần cho tất cả các bài kiểm thử. Tuy nhiên, nó đòi hỏi việc cô lập kiểm thử phải được xử lý cẩn thận — phương thức `@BeforeEach` phải reset rõ ràng các trạng thái khả biến (mutable state) mà `@BeforeAll` đã khởi tạo.

### Mô hình tư duy: Vòng đời thực thể PER_METHOD so với PER_CLASS (Mental Model: PER_METHOD vs PER_CLASS instance lifecycle)
```
PER_METHOD (mặc định):
Bắt đầu chạy kiểm thử
    → @BeforeAll (tĩnh): chạy một lần
    → tạo new TestClass() cho test1 → @BeforeEach → @Test test1 → @AfterEach → GC
    → tạo new TestClass() cho test2 → @BeforeEach → @Test test2 → @AfterEach → GC
    → @AfterAll (tĩnh): chạy một lần
    → Không rò rỉ trạng thái giữa test1 và test2 (các thực thể khác nhau)

PER_CLASS:
    → @BeforeAll (không cần tĩnh): chạy một lần
    → @BeforeEach → @Test test1 → @AfterEach   (cùng một thực thể)
    → @BeforeEach → @Test test2 → @AfterEach   (cùng một thực thể)
    → @AfterAll (không cần tĩnh): chạy một lần
    → Trạng thái thực thể được chia sẻ — phải reset rõ ràng trong @BeforeEach
```

### Ví dụ Code: Vòng đời PER_CLASS với @BeforeAll không tĩnh (Code Example: PER_CLASS lifecycle with non-static @BeforeAll)
```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseIntegrationTest {

    private EmbeddedDatabase db;

    @BeforeAll // không cần tĩnh — được phép khi dùng PER_CLASS
    void startDatabase() {
        db = EmbeddedDatabaseBuilder.build(); // thiết lập tốn kém — một lần duy nhất cho toàn bộ lớp
    }

    @BeforeEach
    void resetState() {
        db.clearAll(); // reset giữa các bài kiểm thử — ngăn chặn sự can thiệp chéo
    }

    @Test
    void testInsert() { db.insert("alice"); assertEquals(1, db.count()); }

    @Test
    void testDelete() { db.insert("bob"); db.delete("bob"); assertEquals(0, db.count()); }

    @AfterAll
    void stopDatabase() { db.shutdown(); }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Mặc định JUnit tạo một thực thể mới trên mỗi phương thức test
  → Mỗi `@Test` bắt đầu với các trường dữ liệu sạch
  → Các thay đổi trạng thái ở test1 không ảnh hưởng đến test2
  → Các kiểm thử không phụ thuộc thứ tự
  → `@BeforeAll` bắt buộc phải là static (chưa có thực thể)
  → `@TestInstance(PER_CLASS)` chuyển sang thực thể dùng chung
  → Một lần thiết lập tốn kém duy nhất; `@BeforeAll` được phép không tĩnh
  → `@BeforeEach` phải dọn dẹp trạng thái khả biến để bảo toàn tính cô lập.
```


## Liên kết tham khảo (Reference Links)

- https://junit.org/junit5/docs/current/user-guide/ (JUnit 5 User Guide)
- https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html (Mockito API)
