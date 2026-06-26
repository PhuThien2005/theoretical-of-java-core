# Kiểm thử đơn vị cơ bản - Phần 2: Ngoại lệ, Logic Private & Độ bao phủ (Basic Unit Testing - Part 2: Exceptions, Private Logic & Coverage)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm việc kiểm thử các hành vi ngoại lệ (exceptional behavior), chiến lược kiểm thử các phương thức private, cũng như ý nghĩa và các giới hạn của độ bao phủ mã nguồn (code coverage).

## Phạm vi đề cương (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `Test exception` | Xác minh rằng mã nguồn ném ra đúng kiểu ngoại lệ mong đợi trong các điều kiện lỗi. |
| `Test private logic indirectly` | Kiểm thử gián tiếp các phương thức helper private thông qua các API công khai thay vì phá vỡ tính bao đóng (encapsulation) thông qua reflection. |
| `Basic code coverage` | Chỉ số cho biết phần trăm các dòng code hoặc các nhánh code được thực thi trong quá trình chạy bộ kiểm thử. |

---

## Ghi chú chi tiết (Detailed Notes)

### Kiểm thử ngoại lệ (Test Exception)

Các bài kiểm thử đơn vị phải xác minh rằng mã nguồn xử lý các dữ liệu đầu vào không hợp lệ hoặc các chế độ lỗi một cách khéo léo bằng cách ném ra các ngoại lệ mong đợi.

- **Sử dụng `assertThrows` trong JUnit 5**:
  - Cách tiếp cận tiêu chuẩn là sử dụng `Assertions.assertThrows(Class<T> expectedType, Executable executable)`.
  - Nó trả về thực thể ngoại lệ được ném ra, cho phép bạn thực hiện tiếp các khẳng định chi tiết hơn như kiểm tra nội dung chính xác của thông điệp lỗi hoặc trạng thái ngoại lệ tùy chỉnh.

- **Ví dụ có thể chạy được**:
  ```java
  import org.junit.jupiter.api.Test;
  import static org.junit.jupiter.api.Assertions.*;

  class Account {
      private double balance;

      public void withdraw(double amount) {
          if (amount <= 0) {
              throw new IllegalArgumentException("Amount must be positive");
          }
          if (amount > balance) {
              throw new IllegalStateException("Insufficient funds: " + balance);
          }
          balance -= amount;
      }
  }

  class AccountTest {
      @Test
      void testWithdrawalThrowsOnInsufficientFunds() {
          Account acc = new Account(); // số dư ban đầu là 0
          
          // Xác minh đúng lớp ngoại lệ được ném ra
          IllegalStateException exception = assertThrows(
              IllegalStateException.class, 
              () -> acc.withdraw(100.0)
          );

          // Xác minh chi tiết thông điệp ngoại lệ
          assertEquals("Insufficient funds: 0.0", exception.getMessage());
      }
  }
  ```

---

### Kiểm thử gián tiếp logic private (Test Private Logic Indirectly)

Một tình huống khó xử phổ biến là làm thế nào để kiểm thử một phương thức helper `private` phức tạp.

- **Quy tắc**: **Không bao giờ chuyển một phương thức thành public hoặc sử dụng reflection chỉ để phục vụ cho việc kiểm thử.** Các phương thức private đại diện cho các chi tiết triển khai bên dưới.
- **Tại sao?**: Nếu bạn kiểm thử trực tiếp các phương thức private (chẳng hạn như sử dụng reflection hoặc khả năng truy cập package-private), bạn đang ràng buộc bài kiểm thử của mình vào chi tiết triển khai hiện tại. Nếu bạn tái cấu trúc (refactor) thiết kế nội bộ mà không làm thay đổi hợp đồng API công khai, các bài kiểm thử của bạn sẽ bị gãy.
- **Giải pháp**: Hãy kiểm thử các phương thức private một cách **gián tiếp** bằng cách gọi các phương thức public có sử dụng chúng. Nếu một phương thức private quá phức tạp để kiểm thử theo cách này, đó là một mùi thiết kế (design smell) báo hiệu rằng logic đó nên được trích xuất sang một lớp helper riêng biệt với API công khai riêng và được tiêm vào dưới dạng phụ thuộc.

---

### Độ bao phủ mã nguồn cơ bản (Basic Code Coverage)

**Độ bao phủ mã nguồn (Code Coverage)** đo lường tỷ lệ mã nguồn được thực thi khi một bộ kiểm thử chạy. Các loại phổ biến bao gồm:
1. **Độ bao phủ dòng/lệnh (Line/Statement Coverage)**: Việc thực thi có đi qua dòng code cụ thể này không?
2. **Độ bao phủ nhánh (Branch Coverage)**: Việc thực thi có bao phủ cả hai đường dẫn `true` và `false` của các câu lệnh điều khiển luồng (`if`, `switch`) không?

- **Giới hạn**: **Độ bao phủ cao không đảm bảo chất lượng kiểm thử cao.**
  - Các công cụ đo độ bao phủ mã nguồn chỉ kiểm tra xem các dòng code có được *thực thi* hay không. Chúng không xác minh xem các khẳng định (assertions) bên trong bài kiểm thử có đúng đắn hay có ý nghĩa hay không.
  - Bạn có thể viết các bài kiểm thử đạt độ bao phủ 100% nhưng không khẳng định bất kỳ điều gì (Kiểm thử không có khẳng định - Assertion-free testing). Các bài kiểm thử như vậy sẽ thất bại khi chương trình bị sập, nhưng sẽ không bắt được các lỗi logic.

---

## Các lỗi thường gặp & Cạm bẫy (Common Mistakes & Traps)

### 1. Cạm bẫy try-catch "Thiếu ngoại lệ" (The Try-Catch "Missing Exception" Trap)
Trước khi `assertThrows` ra đời, các lập trình viên thường sử dụng các khối try-catch để kiểm thử ngoại lệ. Một lỗi phổ biến là quên làm bài kiểm thử thất bại nếu ngoại lệ *không* được ném ra.

*Không chính xác:*
```java
@Test
void testWithdrawal_Bad() {
    Account acc = new Account();
    try {
        acc.withdraw(100.0); // Nếu phương thức này KHÔNG ném ngoại lệ, bài test vẫn PASS!
    } catch (IllegalStateException e) {
        assertEquals("Insufficient funds: 0.0", e.getMessage());
    }
}
```

*Chính xác (Sử dụng `assertThrows`):*
```java
@Test
void testWithdrawal_Good() {
    Account acc = new Account();
    IllegalStateException ex = assertThrows(IllegalStateException.class, () -> acc.withdraw(100.0));
    assertEquals("Insufficient funds: 0.0", ex.getMessage());
}
```

---

## Trường hợp điển hình: Kiểm thử gián tiếp logic Validator Private (Case Study: Testing Private Validator Logic Indirectly)

Xem xét một lớp `UserRegistrationService` có các quy tắc kiểm thực (validation) email và mật khẩu private nội bộ:

```java
public class UserRegistrationService {
    
    public void registerUser(String email, String password) {
        validateEmail(email);
        validatePassword(password);
        // Logic lưu người dùng...
    }

    private void validateEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password too short");
        }
    }
}
```

Thay vì sử dụng reflection hoặc chuyển phương thức `validateEmail` sang dạng package-private, chúng ta kiểm thử logic này thông qua API công khai `registerUser`:

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserRegistrationServiceTest {
    private final UserRegistrationService service = new UserRegistrationService();

    @Test
    void testRegisterUser_InvalidEmail_ThrowsException() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class, 
            () -> service.registerUser("invalid-email", "validPassword123")
        );
        assertEquals("Invalid email format", ex.getMessage());
    }

    @Test
    void testRegisterUser_ShortPassword_ThrowsException() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class, 
            () -> service.registerUser("test@example.com", "short")
        );
        assertEquals("Password too short", ex.getMessage());
    }
}
```

---

## Tại sao các phương thức Private nên được kiểm thử gián tiếp qua API công khai (Why Private Methods Should Be Tested Indirectly Through the Public API)

Một phương thức `private` là một **chi tiết triển khai bên dưới (implementation detail)** — nó đại diện cho việc một lớp đạt được hành vi của nó như thế nào, chứ không phải những gì nó hứa sẽ thực hiện. API công khai của lớp là hợp đồng của nó. Khi bạn kiểm thử trực tiếp các phương thức private (thông qua reflection `setAccessible(true)`), bạn đang phá vỡ tính bao đóng và tạo ra hai vấn đề nghiêm trọng:

1. **Ràng buộc chi tiết triển khai (Implementation coupling)**: Bài kiểm thử phụ thuộc vào tên phương thức nội bộ cụ thể, chữ ký phương thức và sự tồn tại của nó. Nếu bạn tái cấu trúc triển khai (đổi tên, gộp phương thức hoặc sắp xếp lại các phương thức private) mà không làm thay đổi hành vi public, bài kiểm thử dựa trên reflection sẽ bị hỏng — mặc dù không có hành vi quan sát được nào bị thay đổi.

2. **Bỏ sót hợp đồng**: Các bài kiểm thử phương thức private chỉ xác minh rằng một cơ chế private cụ thể hoạt động tốt. Chúng không xác minh xem phương thức public có điều phối chính xác tất cả các bước private lại với nhau hay không. Một phương thức private có thể hoạt động chính xác khi đứng độc lập nhưng lại bị gọi sai thứ tự từ phương thức public.

Cách tiếp cận đúng đắn: hãy kiểm thử phương thức public với các dữ liệu đầu vào được thiết kế để thực thi phương thức helper private thông qua các luồng chạy code thông thường. Nếu một phương thức private quá phức tạp đến mức không thể kiểm thử đầy đủ qua API công khai, độ phức tạp đó là một **mùi thiết kế (design smell)** — logic đó nên được trích xuất sang một lớp cộng tác (collaborator class) với API công khai của riêng nó và được tiêm vào dưới dạng một phụ thuộc.

### Mô hình tư duy: Kiểm thử trực tiếp so với Gián tiếp (Mental Model: Direct vs. Indirect Testing)
```
[Không chính xác — reflection phá vỡ tính bao đóng]
Method method = MyClass.class.getDeclaredMethod("validateEmail", String.class);
method.setAccessible(true);
method.invoke(service, "invalid");
// Nếu validateEmail đổi tên thành checkEmailFormat → bài test bị hỏng
// Mặc dù hành vi public có thể không đổi


[Chính xác — kiểm thử qua API công khai]
// Gọi registerUser với một email không hợp lệ
IllegalArgumentException ex = assertThrows(
    IllegalArgumentException.class,
    () -> service.registerUser("invalid", "password123")
);
assertEquals("Invalid email format", ex.getMessage());
// Nếu validateEmail đổi tên nội bộ → bài test vẫn pass
// vì hợp đồng public (hành vi ngoại lệ) không đổi
```

### Ví dụ Code: Kiểm thử logic Private qua API công khai (Code Example: Testing Private Logic Through Public API)
```java
class UserRegistrationServiceTest {
    private final UserRegistrationService service = new UserRegistrationService();

    @Test
    void privateValidateEmail_isExercisedViaRegisterUser() {
        // Thực thi validateEmail() private mà không cần truy cập trực tiếp
        assertThrows(IllegalArgumentException.class,
            () -> service.registerUser("not-an-email", "validpassword123"));
    }

    @Test
    void privateValidatePassword_isExercisedViaRegisterUser() {
        // Thực thi validatePassword() private mà không cần truy cập trực tiếp
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> service.registerUser("valid@test.com", "short"));
        assertEquals("Password too short", ex.getMessage());
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Phương thức private là chi tiết triển khai
  → Kiểm thử trực tiếp qua reflection ràng buộc bài test vào cấu trúc nội bộ
  → Tái cấu trúc phương thức nội bộ làm gãy bài test dù hành vi không đổi
  → Kiểm thử qua API công khai thay thế
  → Bài test thực thi logic private như một tác dụng phụ của cuộc gọi public
  → Tái cấu trúc nội bộ không làm hỏng bài test
  → Nếu phương thức private quá phức tạp để kiểm thử gián tiếp
  → Trích xuất nó sang một lớp cộng tác với API công khai của riêng nó.
```


---

## Tại sao assertThrows an toàn hơn Try-Catch để kiểm thử ngoại lệ (Why assertThrows Is Safer Than Try-Catch for Exception Testing)

Mẫu thiết kế try-catch cho kiểm thử ngoại lệ có một chế độ thất bại im lặng (silent failure mode) cực kỳ nguy hiểm: nếu ngoại lệ **không** được ném ra, bài kiểm thử vẫn trôi qua (pass) mà không có bất kỳ khẳng định (assertion) nào được thực thi. Bài kiểm thử mang lại sự tự tin giả tạo — nó báo cáo "PASS" trong khi hành vi mà nó được thiết kế để xác minh chưa bao giờ xảy ra.

Lệnh `assertThrows(ExceptionType.class, () -> ...)` giải quyết điều này bằng cách làm bài kiểm thử thất bại ngay lập tức nếu không có ngoại lệ nào được ném ra. Ngoài ra, nó trả về đối tượng ngoại lệ được ném ra, cho phép bạn thực hiện khẳng định trên thông điệp của ngoại lệ, nguyên nhân (cause), hoặc các trường tùy chỉnh — những thứ vốn không dễ dàng tiếp cận trong mẫu thiết kế try-catch cũ.

### Mô hình tư duy: Cạm bẫy try-catch so với tính an toàn của assertThrows (Mental Model: try-catch trap vs assertThrows safety)
```
[Dùng Try-catch — pass im lặng khi không có ngoại lệ]
try {
    account.withdraw(100.0); // Nếu chỗ này KHÔNG ném ngoại lệ → không có gì xảy ra
} catch (IllegalStateException e) {
    assertEquals("Insufficient funds", e.getMessage());
}
// Nếu không có ngoại lệ được ném ra, khối catch không bao giờ chạy
// Tất cả các khẳng định bị bỏ qua → bài test pass mà không kiểm thực gì cả!


[Dùng assertThrows — thất bại rõ ràng khi không có ngoại lệ]
IllegalStateException ex = assertThrows(
    IllegalStateException.class,
    () -> account.withdraw(100.0)
    // Nếu không ném ngoại lệ → KIỂM THỬ THẤT BẠI với thông báo "Expected IllegalStateException to be thrown"
);
assertEquals("Insufficient funds: 0.0", ex.getMessage()); // Khẳng định thông điệp lỗi
```

### Ví dụ Code: Mẫu assertThrows hoàn chỉnh (Code Example: Complete assertThrows pattern)
```java
@Test
void testWithdraw_insufficientFunds_throwsWithCorrectMessage() {
    Account acc = new Account(); // balance = 0

    // assertThrows fails if no exception is thrown
    IllegalStateException ex = assertThrows(
        IllegalStateException.class,
        () -> acc.withdraw(100.0)
    );

    // Can also assert exception details — not possible in try-catch without extra code
    assertEquals("Insufficient funds: 0.0", ex.getMessage());
    assertNotNull(ex); // redundant but demonstrates ex is accessible
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Mẫu try-catch: ngoại lệ không được ném ra
  → khối catch bị bỏ qua
  → tất cả các khẳng định bên trong catch bị bỏ qua
  → bài test báo cáo PASS mặc dù không có gì được xác minh.
```

`assertThrows`: ngoại lệ không được ném ra &rarr; bài test lập tức thất bại với thông điệp rõ ràng "Expected X to be thrown, but nothing was thrown" &rarr; Trả về ngoại lệ được ném ra để khẳng định chi tiết hơn &rarr; Xác minh ngoại lệ hoàn tất chỉ trong một câu lệnh.

---

## Tại sao Độ bao phủ mã nguồn là chỉ số chất lượng cần thiết nhưng chưa đủ (Why Code Coverage Is a Necessary but Insufficient Quality Metric)

Độ bao phủ mã nguồn đo lường **sự thực thi** (execution) — liệu một dòng code hoặc một nhánh code có được đi qua trong quá trình chạy kiểm thử hay không. Nó không đo lường **tính đúng đắn** (correctness) — liệu các khẳng định (assertions) trong các bài kiểm thử đó có thực sự xác minh đúng hành vi mong muốn hay không. Khoảng cách này tạo ra anti-pattern "kiểm thử không có khẳng định (assertion-free testing)": các bài kiểm thử đạt độ bao phủ cao bằng cách chạy qua code nhưng không bao giờ khẳng định bất kỳ kết quả nào.

Ví dụ, một bài kiểm thử gọi `calculator.add(2, 3)` mà không gọi bất kỳ lệnh `assertEquals` hoặc khẳng định nào khác vẫn đạt được 100% độ bao phủ dòng cho phương thức `add`, nhưng không bắt được bất kỳ lỗi nào. Ngay cả một bài test với `assertTrue(true)` cũng giúp tăng độ bao phủ. Các công cụ đo độ bao phủ không thể phân biệt giữa các khẳng định có ý nghĩa và các khẳng định rỗng.

Độ bao phủ mã nguồn cao là một nền tảng **cần thiết** (mã nguồn chưa được kiểm thử chắc chắn có 0% bảo vệ) nhưng là một sự đảm bảo **chưa đủ** về chất lượng. Chỉ số bổ sung cần thiết là **kiểm thử đột biến (mutation testing)** (các công cụ như PIT), giới thiệu các đột biến code nhân tạo và xác minh xem bộ kiểm thử của bạn có phát hiện và bắt được chúng hay không.

### Mô hình tư duy: Độ bao phủ so với Tính chính xác (Mental Model: Coverage vs. Correctness)
```
[Độ bao phủ cao, chất lượng bằng 0]
@Test
void testAdd() {
    calculator.add(2, 3); // Code được THỰC THI ← đạt 100% độ bao phủ dòng
    // Không có khẳng định — kết quả bị vứt bỏ
    // Lỗi: add() luôn trả về 0 → bài test vẫn pass, lỗi không bị phát hiện
}

[Độ bao phủ cao + khẳng định chất lượng]
@Test
void testAdd() {
    int result = calculator.add(2, 3);
    assertEquals(5, result); // XÁC MINH kết quả
    // Lỗi: add() trả về 0 → bài test thất bại → phát hiện lỗi
}
```

### Ví dụ Code: Độ bao phủ nhánh và các giới hạn của độ bao phủ dòng (Code Example: Branch coverage and the limits of line coverage)
```java
public String classify(int n) {
    if (n > 0) return "positive";  // dòng 1
    if (n < 0) return "negative";  // dòng 2
    return "zero";                  // dòng 3
}

@Test
void testClassify_linesCovered() {
    classify(1);  // Đi qua dòng 1 — bao phủ 1/3 dòng, 0 khẳng định
    classify(-1); // Đi qua dòng 2 — bao phủ 2/3 dòng, 0 khẳng định
    classify(0);  // Đi qua dòng 3 — bao phủ 3/3 dòng (100%), 0 khẳng định!
    // Kết quả: 100% bao phủ dòng, 0% khả năng phát hiện lỗi
}

@Test
void testClassify_withAssertions() {
    assertEquals("positive", classify(1));
    assertEquals("negative", classify(-1));
    assertEquals("zero", classify(0));
    // Cùng độ bao phủ, nhưng bây giờ các khẳng định sẽ bắt được các giá trị trả về không chính xác
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Đạt 100% độ bao phủ dòng
  → Mọi dòng code đều được thực thi trong các bài test
  → KHÔNG có nghĩa là hành vi của từng dòng đã được khẳng định
  → Các bài kiểm thử không có khẳng định vẫn pass im lặng bất chấp lỗi
  → Anti-pattern kiểm thử không khẳng định: chỉ số bao phủ được đáp ứng nhưng chất lượng thì không
  → Bổ sung độ bao phủ bằng việc xem xét chất lượng khẳng định và kiểm thử đột biến.
```


## Liên kết tham khảo (Reference Links)

- https://junit.org/junit5/docs/current/user-guide/ (JUnit 5 User Guide)
- https://site.mockito.org/ (Mockito documentation)
