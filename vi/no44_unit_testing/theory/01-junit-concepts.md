# Kiểm thử đơn vị cơ bản - Phần 1: Các kiến thức cơ bản về JUnit 5 & Mockito

## Mục Tiêu Học Tập

Tài liệu này trình bày các nguyên lý cơ bản của kiểm thử đơn vị trong Java bằng cách sử dụng JUnit 5 và Mockito. Bạn sẽ tìm hiểu về vòng đời kiểm thử, các khẳng định (assertion) và các tương tác đối tượng giả lập (mock object).

## Nội Dung Tổng Quan

- **`JUnit`** — Khung kiểm thử Java để viết và chạy các bài kiểm thử đơn vị tự động.
- **`Test case`** — Một phương thức được chú thích bằng `@Test` dùng để xác minh một hành vi cụ thể của một lớp.
- **`Assertion`** — Các phương thức tĩnh từ `org.junit.jupiter.api.Assertions` để xác minh kết quả mong đợi.
- **`@Test`** — Chú thích đánh dấu một phương thức là một trường hợp kiểm thử; không được là private hoặc static.
- **`@BeforeEach`** — Chạy trước mỗi phương thức `@Test` để thiết lập các môi trường kiểm thử (fixture).
- **`@AfterEach`** — Chạy sau mỗi phương thức `@Test` để dọn dẹp trạng thái.
- **`@BeforeAll`** — Chạy một lần duy nhất trước tất cả các bài kiểm thử trong một lớp; phải là static trừ khi sử dụng vòng đời per-class.
- **`@AfterAll`** — Chạy một lần duy nhất sau tất cả các bài kiểm thử trong một lớp; phải là static trừ khi sử dụng vòng đời per-class.
- **`Basic Mockito`** — Khung công tác giả lập (mocking framework) được sử dụng để cô lập đơn vị cần kiểm thử bằng cách mô phỏng các phụ thuộc.
- **`Mock object`** — Một phụ thuộc được mô phỏng cấu hình để trả về các phản hồi được chuẩn bị trước hoặc xác minh các lệnh gọi phương thức.

---

## Ghi Chú Chi Tiết

### JUnit & Trường hợp kiểm thử (Test Case)

**JUnit 5** (Jupiter) là thư viện tiêu chuẩn để viết các bài kiểm thử đơn vị tự động trong Java hiện đại. Một **Trường hợp kiểm thử (Test Case)** là một lớp kiểm thử hoặc phương thức kiểm thử đơn lẻ nhắm mục tiêu vào một đường dẫn hành vi cụ thể trong mã nguồn.

- **Vòng đời kiểm thử & các chú thích (Annotation)**:
  - `@Test`: Đánh dấu một phương thức là một kiểm thử. JUnit tạo một thể hiện mới của lớp kiểm thử cho mỗi phương thức `@Test` để đảm bảo tính cô lập của kiểm thử.
  - `@BeforeEach` / `@AfterEach`: Được thực thi trước và sau từng phương thức kiểm thử riêng lẻ. Lý tưởng để đặt lại các trường thể hiện (instance field), mở các tài nguyên cục bộ sạch cho kiểm thử.
  - `@BeforeAll` / `@AfterAll`: Được thực thi một lần duy nhất trước khi tất cả các bài kiểm thử chạy, và một lần duy nhất sau khi tất cả các bài kiểm thử hoàn thành.
    - *Lưu ý*: Theo mặc định, các phương thức này phải là `static` vì JUnit tạo thể hiện lớp kiểm thử một lần cho mỗi phương thức kiểm thử. Nếu bạn đánh dấu lớp của mình bằng `@TestInstance(Lifecycle.PER_CLASS)`, bạn có thể đặt các phương thức này là non-static (không tĩnh).

- **Ví Dụ Thực Tế (Vòng đời & Khẳng định)**:
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
          calculator = new Calculator(); // Thể hiện mới cho mỗi bài kiểm thử
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

### Mockito cơ bản & Đối tượng giả lập (Mock Object)

**Mockito** là một khung công tác giả lập (mocking framework) cho phép lập trình viên cô lập lớp cần kiểm thử bằng cách thay thế các phụ thuộc thực tế bằng các **Đối tượng Giả lập (Mock Object)**. Các mock trả về các giá trị được cấu hình (stubbing) và ghi nhớ các tương tác (verification).

- **Giả lập hành vi (`when`) so với Xác minh tương tác (`verify`)**:
  - **Stubbing (Giả lập)**: Cấu hình một mock để trả về một giá trị cụ thể khi một phương thức cụ thể được gọi.
  - **Verification (Xác minh)**: Kiểm tra xem một phương thức trên mock có được gọi với các đối số cụ thể trong một số lần nhất định hay không.

- **Ví Dụ Thực Tế (Mockito)**:
  ```java
  import org.junit.jupiter.api.Test;
  import org.mockito.Mockito;
  import static org.junit.jupiter.api.Assertions.*;
  import static org.mockito.Mockito.*;

  class UserServiceTest {

      @Test
      void testGetUserEmail_WithMockedRepository() {
          // 1. Tạo đối tượng giả lập (Mock Object)
          UserRepository mockRepo = mock(UserRepository.class);

          // 2. Giả lập hành vi (Stubbing): khi mockRepo.findUsername(1) được gọi, trả về "alice"
          when(mockRepo.findUsername(1L)).thenReturn("alice");

          UserService userService = new UserService(mockRepo);

          // 3. Thực thi & Khẳng định
          String username = userService.getUsername(1L);
          assertEquals("alice", username);

          // 4. Xác minh (Verification): Xác minh rằng findUsername(1L) được gọi đúng 1 lần
          verify(mockRepo, times(1)).findUsername(1L);
      }
  }
  ```

---

## Sai Lầm Thường Gặp & Bẫy

### 1. Sai Thứ Tự Tham Số Khẳng Định (JUnit 5 so với TestNG / AssertJ)
Trong JUnit 5, các khẳng định có chữ ký: `assertEquals(expected, actual, message)`.
Một lỗi thường gặp là đảo ngược các tham số `expected` (mong đợi) và `actual` (thực tế). Mặc dù điều này không trực tiếp làm thất bại bài kiểm thử, nhưng nó tạo ra các báo cáo lỗi rất khó hiểu và gây hiểu lầm.

*Sai:*
```java
// Nếu add(2,2) trả về 5, báo cáo ghi: "Expected [5] but was [4]"
assertEquals(calculator.add(2, 2), 4); 
```

*Đúng:*
```java
// Báo cáo ghi chính xác: "Expected [4] but was [5]"
assertEquals(4, calculator.add(2, 2)); 
```

### 2. Quên Không Đặt Các Phương Thức @BeforeAll / @AfterAll Là Static
Nếu không có `@TestInstance(Lifecycle.PER_CLASS)`, việc quên đặt `@BeforeAll` hoặc `@AfterAll` là static sẽ gây ra một lỗi `JUnitException` tại thời điểm chạy trước khi bất kỳ bài kiểm thử nào được thực thi.

*Sai:*
```java
@BeforeAll
void init() { // SẬP: Phải là static trừ khi PER_CLASS được thiết lập!
    dbConnection = new DbConnection();
}
```

### 3. Giả Lập Các Đối Tượng Giá Trị Hoặc DTO
Mockito nên được sử dụng để giả lập các dịch vụ (service), kho lưu trữ (repository) hoặc các ranh giới hệ thống bên ngoài. Đừng giả lập các cấu trúc dữ liệu thuần túy, DTO hoặc Collection. Thay vào đó, hãy sử dụng các thể hiện thực tế của các đối tượng đó. Giả lập một List đơn giản hoặc một String sẽ tạo ra các đoạn mã khung (boilerplate) dễ vỡ và không cần thiết.

---

## Ví dụ Thực Tế: Kiểm Thử Một Dịch Vụ Với Cơ Sở Dữ Liệu Và API Bên Ngoài Được Giả Lập

Dưới đây là một mẫu thiết kế thực tế điển hình cô lập một `PaymentService` khỏi cơ sở dữ liệu và client thông báo email bằng cách sử dụng Mockito.

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

Chúng ta kiểm thử logic nghiệp vụ của `PaymentService` mà không cần kích hoạt một giao dịch cơ sở dữ liệu thực tế hoặc gửi email thực tế:

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Test
    void testProcessPayment_Success() {
        // Sắp xếp (Arrange)
        PaymentRepository mockRepo = mock(PaymentRepository.class);
        EmailClient mockEmail = mock(EmailClient.class);
        
        when(mockRepo.saveTransaction(123L, 99.99)).thenReturn(true);
        
        PaymentService service = new PaymentService(mockRepo, mockEmail);

        // Thực thi (Act)
        boolean result = service.processPayment(123L, 99.99);

        // Khẳng định (Assert)
        assertTrue(result);
        
        // Xác minh các tương tác mock đã diễn ra đúng như mong đợi
        verify(mockRepo).saveTransaction(123L, 99.99);
        verify(mockEmail).sendNotification(123L, "Payment of $99.99 successful.");
    }
}
```

---

## Tại Sao Việc Giả Lập Giúp Cô Lập Đơn Vị Cần Kiểm Thử

Kiểm thử đơn vị có một mục tiêu cụ thể: kiểm thử **một đơn vị** (một lớp hoặc phương thức) một cách cô lập khỏi các phụ thuộc bên ngoài của nó. Khi một lớp cần kiểm thử (`UserService`) phụ thuộc vào một kho lưu trữ cơ sở dữ liệu (`UserRepository`), việc gọi các phương thức cơ sở dữ liệu thực tế trong một bài kiểm thử đơn vị sẽ gây ra nhiều vấn đề:

1. **Khớp nối (Coupling)**: Bài kiểm thử thất bại nếu cơ sở dữ liệu không khả dụng, lược đồ thay đổi, hoặc dữ liệu kiểm thử bị sai — ngay cả khi logic của `UserService` là chính xác.
2. **Tốc độ (Speed)**: I/O cơ sở dữ liệu thực tế chậm hơn hàng chục lần so với các khẳng định trong bộ nhớ. Một bộ kiểm thử với I/O thực tế có thể mất vài phút thay vì vài mili giây.
3. **Tính bất định (Non-determinism)**: Các hệ thống thực tế bên ngoài (API, cơ sở dữ liệu, đồng hồ hệ thống) trả về các kết quả khác nhau giữa các lần chạy, làm cho các bài kiểm thử trở nên chập chờn (flaky).

Mockito thay thế các phụ thuộc thực tế bằng các đối tượng giả lập trong bộ nhớ trả về các giá trị được cấu hình (stubbed). Bài kiểm thử giờ đây chỉ kiểm tra logic của `UserService` — kho lưu trữ được kiểm soát hoàn toàn bởi chính bài kiểm thử.

### Mô Hình Tư Duy: Phụ Thuộc Thực Tế so với Giả Lập
```
[Không sử dụng Mock — khớp nối với cơ sở dữ liệu]
Test gọi UserService.getUser(1)
    → UserService gọi UserRepository.findById(1)  ← gọi DB thực tế
    → DB có thể bị sập, chậm hoặc có dữ liệu khác  ← kiểm thử thất bại vì lý do sai
    → Kiểm thử thực chất đang kiểm thử DB + mạng + cấu hình, chứ không phải UserService

[Sử dụng Mock — kiểm thử đơn vị cô lập]
Test gọi UserService.getUser(1)
    → UserService gọi mockRepo.findById(1)
    → mockRepo trả về stub "alice" ngay lập tức (trong bộ nhớ)
    → Kiểm thử xác minh UserService xử lý "alice" chính xác
    → Chỉ có logic của UserService được kiểm thử
```

### Ví Dụ Thực Tế: Cô Lập via Mockito Stubbing và Verification
```java
@Test
void testGetUserEmail() {
    // Sắp xếp (Arrange): giả lập phụ thuộc, định nghĩa hành vi của nó
    UserRepository mockRepo = mock(UserRepository.class);
    when(mockRepo.findById(1L)).thenReturn(new User(1L, "alice@example.com"));

    UserService service = new UserService(mockRepo); // tiêm mock vào

    // Thực thi (Act): chỉ kiểm thử UserService
    String email = service.getUserEmail(1L);

    // Khẳng định (Assert): UserService trích xuất chính xác email
    assertEquals("alice@example.com", email);

    // Xác minh (Verify): UserService gọi repo đúng một lần
    verify(mockRepo, times(1)).findById(1L);
}
```

### Chuỗi Nguyên Nhân - Kết Quả
`UserService` phụ thuộc vào `UserRepository` &rarr; Bài kiểm thử tiêm một `mock(UserRepository.class)` thay vì một kết nối DB thực tế &rarr; `when(mockRepo.findById(1L)).thenReturn(...)` cấu hình giá trị trả về được kiểm soát &rarr; Bài kiểm thử chạy hoàn toàn trong bộ nhớ trong vài mili giây &rarr; Chỉ logic của `UserService` mới có thể làm thất bại bài kiểm thử &rarr; Bài kiểm thử diễn ra nhanh, mang tính xác định và được cô lập.

---

## Tại Sao JUnit Tạo Một Thể Hiện Mới Cho Mỗi Phương Thức Kiểm Thử

Vòng đời mặc định của JUnit 5 là `PER_METHOD` — một thể hiện lớp kiểm thử mới được tạo cho mỗi phương thức `@Test`. Lựa chọn thiết kế này đảm bảo **tính cô lập của kiểm thử (test isolation)**: mỗi bài kiểm thử bắt đầu với một trạng thái thể hiện sạch, ngăn ngừa các đột biến trạng thái trong một bài kiểm thử bị rò rỉ sang bài khác.

Nếu JUnit tái sử dụng một thể hiện lớp kiểm thử duy nhất cho tất cả các bài kiểm thử, các trường thể hiện dùng chung bị thay đổi bởi một bài kiểm thử sẽ mang sang bài kiểm thử tiếp theo. Thứ tự kiểm thử sẽ trở nên quan trọng, và các bài kiểm thử sẽ thất bại tùy thuộc vào thứ tự chúng được chạy — một nhóm lỗi được gọi là "lỗi kiểm thử phụ thuộc thứ tự (order-dependent test failure)".

Hệ quả thực tế: các phương thức `@BeforeAll` và `@AfterAll` theo mặc định phải là `static`, vì chúng cần chạy trước và sau khi tất cả các thể hiện được tạo ra/hủy đi. Chúng không thể tham chiếu đến `this` (vì không có thể hiện nào tồn tại ở thời điểm đó).

`@TestInstance(Lifecycle.PER_CLASS)` chuyển sang một thể hiện dùng chung duy nhất. Điều này hữu ích khi các thiết lập tốn kém (như khởi động một cơ sở dữ liệu nhúng) chỉ nên diễn ra một lần cho tất cả các bài kiểm thử. Nhưng nó đòi hỏi tính cô lập kiểm thử cẩn thận — `@BeforeEach` phải đặt lại một cách rõ ràng trạng thái có thể đột biến mà `@BeforeAll` đã khởi tạo.

### Mô Hình Tư Duy: Vòng đời thể hiện PER_METHOD so với PER_CLASS
```
PER_METHOD (mặc định):
Quá trình chạy kiểm thử bắt đầu
    → @BeforeAll (static): chạy một lần
    → new TestClass() cho test1 → @BeforeEach → @Test test1 → @AfterEach → Dọn rác (GC)
    → new TestClass() cho test2 → @BeforeEach → @Test test2 → @AfterEach → Dọn rác (GC)
    → @AfterAll (static): chạy một lần
    → Không rò rỉ trạng thái giữa test1 và test2 (các thể hiện khác nhau)

PER_CLASS:
    → @BeforeAll (có thể non-static): chạy một lần
    → @BeforeEach → @Test test1 → @AfterEach   (cùng một thể hiện)
    → @BeforeEach → @Test test2 → @AfterEach   (cùng một thể hiện)
    → @AfterAll (có thể non-static): chạy một lần
    → Trạng thái thể hiện dùng chung — phải đặt lại rõ ràng trong @BeforeEach
```

### Ví Dụ Thực Tế: Vòng đời PER_CLASS với @BeforeAll non-static
```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseIntegrationTest {

    private EmbeddedDatabase db;

    @BeforeAll // non-static — được cho phép với PER_CLASS
    void startDatabase() {
        db = EmbeddedDatabaseBuilder.build(); // thiết lập tốn kém — một lần cho tất cả các bài kiểm thử
    }

    @BeforeEach
    void resetState() {
        db.clearAll(); // đặt lại giữa các bài kiểm thử — ngăn chặn sự can thiệp chéo giữa các bài kiểm thử
    }

    @Test
    void testInsert() { db.insert("alice"); assertEquals(1, db.count()); }

    @Test
    void testDelete() { db.insert("bob"); db.delete("bob"); assertEquals(0, db.count()); }

    @AfterAll
    void stopDatabase() { db.shutdown(); }
}
```

### Chuỗi Nguyên Nhân - Kết Quả
Mặc định JUnit tạo thể hiện mới cho mỗi bài kiểm thử &rarr; Mỗi `@Test` bắt đầu với các trường thể hiện sạch &rarr; Các biến đổi trạng thái trong test1 không ảnh hưởng đến test2 &rarr; Các bài kiểm thử độc lập với thứ tự &rarr; `@BeforeAll` phải là static (chưa có thể hiện nào) &rarr; `@TestInstance(PER_CLASS)` chuyển sang thể hiện dùng chung &rarr; Một lần thiết lập tốn kém duy nhất; `@BeforeAll` có thể là non-static &rarr; `@BeforeEach` phải đặt lại trạng thái có thể biến đổi để duy trì tính cô lập.

## Liên Kết Tham Khảo

- https://junit.org/junit5/docs/current/user-guide/ (JUnit 5 User Guide)
- https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html (Mockito API)
