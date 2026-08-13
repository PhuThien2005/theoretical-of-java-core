# Kiểm thử đơn vị cơ bản - Phần 2: Ngoại lệ, Logic Private & Độ bao phủ mã nguồn

---

## Ghi Chú Chi Tiết

### Kiểm thử ngoại lệ (Test Exception)

Các bài kiểm thử đơn vị phải xác minh rằng mã nguồn xử lý các đầu vào tồi hoặc các chế độ lỗi một cách khéo léo bằng cách ném ra các ngoại lệ mong đợi.

  - Cách tiếp cận tiêu chuẩn là sử dụng `Assertions.assertThrows(Class<T> expectedType, Executable executable)`.
  - Nó trả về thể hiện ngoại lệ được ném ra, cho phép khẳng định thêm các chi tiết như thông báo lỗi chính xác hoặc trạng thái ngoại lệ tùy chỉnh.

- **Ví Dụ Thực Tế**:
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
          Account acc = new Account(); // số dư bằng 0
          
          // Xác minh đúng lớp ngoại lệ được ném ra
          IllegalStateException exception = assertThrows(
              IllegalStateException.class, 
              () -> acc.withdraw(100.0)
          );

          // Xác minh chi tiết thông báo ngoại lệ
          assertEquals("Insufficient funds: 0.0", exception.getMessage());
      }
  }
  ```

---

### Kiểm thử gián tiếp logic private (Test Private Logic Indirectly)

Một tình huống khó xử phổ biến là làm thế nào để kiểm thử một phương thức bổ trợ `private` phức tạp.

- **Quy tắc**: **Không bao giờ chuyển một phương thức thành public hoặc sử dụng phản chiếu chỉ để kiểm thử nó.** Các phương thức private đại diện cho các chi tiết triển khai.
- **Tại sao?**: Nếu bạn kiểm thử trực tiếp các phương thức private (ví dụ: bằng cách sử dụng phản chiếu hoặc phạm vi truy cập package-private), bạn sẽ gắn khớp các bài kiểm thử của mình với triển khai hiện tại. Nếu bạn tái cấu trúc thiết kế nội bộ mà không thay đổi hợp đồng công khai, các bài kiểm thử của bạn sẽ bị hỏng.
- **Giải pháp**: Kiểm thử các phương thức private **gián tiếp** bằng cách gọi các phương thức public sử dụng chúng. Nếu một phương thức private quá phức tạp để kiểm thử theo cách này, đó là một dấu hiệu thiết kế tồi (design smell) chỉ ra rằng logic đó nên được trích xuất sang một lớp bổ trợ riêng biệt với API công khai của riêng nó và được tiêm vào dưới dạng phụ thuộc.

---

### Độ bao phủ mã nguồn cơ bản (Basic Code Coverage)

**Độ bao phủ mã nguồn (Code Coverage)** đo lường tỷ lệ mã nguồn được thực thi khi chạy bộ kiểm thử. Các loại phổ biến bao gồm:
1. **Độ bao phủ dòng/câu lệnh (Line/Statement Coverage)**: Việc thực thi có đi qua dòng mã này không?
2. **Độ bao phủ nhánh (Branch Coverage)**: Việc thực thi có bao phủ cả hai đường dẫn `true` và `false` của các câu lệnh điều khiển luồng (`if`, `switch`) không?

- **Giới hạn**: **Độ bao phủ cao không phải là sự đảm bảo cho chất lượng cao.**
  - Các công cụ đo độ bao phủ mã nguồn chỉ kiểm tra xem các dòng mã có được *thực thi* hay không. Chúng không xác minh xem các khẳng định (assertion) bên trong bài kiểm thử có chính xác hoặc có ý nghĩa hay không.
  - Bạn có thể viết các bài kiểm thử có độ bao phủ 100% nhưng không khẳng định gì cả (Kiểm thử không có khẳng định - Assertion-free testing). Các bài kiểm thử này sẽ thất bại khi xảy ra sự cố sập chương trình, nhưng sẽ không bắt được lỗi logic.

---

## Sai Lầm Thường Gặp & Bẫy

### 1. Bẫy try-catch "Bỏ Sót Ngoại Lệ"
Trước khi có `assertThrows`, các lập trình viên thường sử dụng các khối try-catch để khẳng định ngoại lệ. Một lỗi phổ biến là quên làm thất bại bài kiểm thử nếu ngoại lệ *không* được ném ra.

*Sai:*
```java
@Test
void testWithdrawal_Bad() {
    Account acc = new Account();
    try {
        acc.withdraw(100.0); // Nếu dòng này KHÔNG ném ngoại lệ, bài kiểm thử vẫn vượt qua!
    } catch (IllegalStateException e) {
        assertEquals("Insufficient funds: 0.0", e.getMessage());
    }
}
```

*Đúng (Sử dụng `assertThrows`):*
```java
@Test
void testWithdrawal_Good() {
    Account acc = new Account();
    IllegalStateException ex = assertThrows(IllegalStateException.class, () -> acc.withdraw(100.0));
    assertEquals("Insufficient funds: 0.0", ex.getMessage());
}
```

---

## Ví dụ Thực Tế: Kiểm Thử Gián Tiếp Logic Validator Private

Quan sát một `UserRegistrationService` có các quy tắc xác thực email và mật khẩu private nội bộ:

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

Thay vì sử dụng phản chiếu hoặc chuyển `validateEmail` thành package-private, chúng ta kiểm thử logic này thông qua API công khai `registerUser`:

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

## Tại Sao Nên Kiểm Thử Gián Tiếp Các Phương Thức Private Qua API Công Khai

Một phương thức `private` là một **chi tiết triển khai (implementation detail)** — nó đại diện cho cách một lớp đạt được hành vi của nó, chứ không phải những gì nó hứa sẽ làm. API công khai của lớp chính là hợp đồng của nó. Khi bạn kiểm thử trực tiếp các phương thức private (thông qua phản chiếu `setAccessible(true)`), bạn sẽ phá vỡ tính đóng gói và tạo ra hai vấn đề nghiêm trọng:

1. **Khớp nối triển khai (Implementation coupling)**: Bài kiểm thử phụ thuộc vào tên phương thức nội bộ, chữ ký và sự tồn tại cụ thể của nó. Nếu bạn tái cấu trúc triển khai (đổi tên, gộp dòng inline hoặc tổ chức lại các phương thức private) mà không thay đổi hành vi công khai, bài kiểm thử dựa trên phản chiếu sẽ bị hỏng — mặc dù không có hành vi có thể quan sát nào thay đổi.

2. **Bỏ sót hợp đồng**: Các bài kiểm thử phương thức private xác minh rằng một cơ chế private cụ thể hoạt động đúng. Chúng không xác minh rằng phương thức public đã điều phối chính xác tất cả các bước private lại với nhau. Một phương thức private có thể hoạt động đúng khi cô lập nhưng lại được gọi sai thứ tự từ phương thức public.

Cách tiếp cận đúng: kiểm thử phương thức public với các đầu vào được thiết kế để vận hành phương thức bổ trợ private thông qua các đường dẫn mã nguồn thông thường. Nếu một phương thức private quá phức tạp đến mức không thể kiểm thử đầy đủ qua API công khai, thì sự phức tạp đó là một **dấu hiệu thiết kế tồi (design smell)** — logic đó nên được trích xuất sang một lớp cộng tác (collaborator class) với API công khai của riêng nó và được tiêm vào như một phụ thuộc.

### Mô Hình Tư Duy: Kiểm Thử Trực Tiếp so với Gián Tiếp
```
[Sai — phản chiếu phá vỡ tính đóng gói]
Method method = MyClass.class.getDeclaredMethod("validateEmail", String.class);
method.setAccessible(true);
method.invoke(service, "invalid");
// Nếu validateEmail được đổi tên thành checkEmailFormat → kiểm thử bị hỏng
// Nhưng hành vi công khai có thể không thay đổi

[Đúng — kiểm thử qua API công khai]
// Gọi registerUser với một email không hợp lệ
IllegalArgumentException ex = assertThrows(
    IllegalArgumentException.class,
    () -> service.registerUser("invalid", "password123")
);
assertEquals("Invalid email format", ex.getMessage());
// Nếu validateEmail được đổi tên nội bộ → kiểm thử vẫn vượt qua
// vì hợp đồng công khai (hành vi ngoại lệ) không thay đổi
```

### Ví Dụ Thực Tế: Kiểm Thử Logic Private Qua API Công Khai
```java
class UserRegistrationServiceTest {
    private final UserRegistrationService service = new UserRegistrationService();

    @Test
    void privateValidateEmail_isExercisedViaRegisterUser() {
        // Vận hành validateEmail() private mà không truy cập trực tiếp
        assertThrows(IllegalArgumentException.class,
            () -> service.registerUser("not-an-email", "validpassword123"));
    }

    @Test
    void privateValidatePassword_isExercisedViaRegisterUser() {
        // Vận hành validatePassword() private mà không truy cập trực tiếp
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> service.registerUser("valid@test.com", "short"));
        assertEquals("Password too short", ex.getMessage());
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả
Phương thức private là chi tiết triển khai &rarr; Kiểm thử trực tiếp qua phản chiếu gắn khớp bài kiểm thử với cấu trúc nội bộ &rarr; Tái cấu trúc phương thức nội bộ làm hỏng bài kiểm thử dù hành vi không đổi &rarr; Thay vào đó hãy kiểm thử qua API công khai &rarr; Kiểm thử vận hành logic private như một tác dụng phụ của cuộc gọi public &rarr; Tái cấu trúc nội bộ không làm hỏng bài kiểm thử &rarr; Nếu phương thức private quá phức tạp để kiểm thử gián tiếp &rarr; Trích xuất nó sang một lớp cộng tác với API công khai của riêng nó.

---

## Tại Sao assertThrows An Toàn Hơn Try-Catch Khi Kiểm Thử Ngoại Lệ

Mẫu try-catch để kiểm thử ngoại lệ có một chế độ thất bại âm thầm cực kỳ nguy hiểm: nếu ngoại lệ **không** được ném ra, bài kiểm thử vẫn vượt qua mà không có bất kỳ khẳng định nào được thực thi. Bài kiểm thử mang lại sự tin tưởng giả tạo — báo cáo "PASS" khi hành vi cần xác minh chưa bao giờ xảy ra.

`assertThrows(ExceptionType.class, () -> ...)` giải quyết vấn đề này bằng cách làm thất bại bài kiểm thử nếu không có ngoại lệ nào được ném ra. Ngoài ra, nó trả về đối tượng ngoại lệ được ném ra, cho phép thực hiện khẳng định trên thông báo lỗi, nguyên nhân (cause) hoặc các trường tùy chỉnh của ngoại lệ — những thứ không thể truy cập thuận tiện trong mẫu try-catch.

### Mô Hình Tư Duy: Bẫy try-catch so với Sự an toàn của assertThrows
```
[Try-catch — thành công âm thầm khi thiếu ngoại lệ]
try {
    account.withdraw(100.0); // Nếu dòng này KHÔNG ném ngoại lệ → không có gì xảy ra
} catch (IllegalStateException e) {
    assertEquals("Insufficient funds", e.getMessage());
}
// Nếu không có ngoại lệ nào được ném ra, khối catch không bao giờ chạy
// Tất cả khẳng định bị bỏ qua → bài kiểm thử vượt qua mà không xác minh gì!

[assertThrows — thất bại rõ ràng khi thiếu ngoại lệ]
IllegalStateException ex = assertThrows(
    IllegalStateException.class,
    () -> account.withdraw(100.0)
    // Nếu không có ngoại lệ → BÀI KIỂM THỬ THẤT BẠI với "Expected IllegalStateException to be thrown"
);
assertEquals("Insufficient funds: 0.0", ex.getMessage()); // Cũng khẳng định thông báo
```

### Ví Dụ Thực Tế: Mẫu assertThrows Hoàn Chỉnh
```java
@Test
void testWithdraw_insufficientFunds_throwsWithCorrectMessage() {
    Account acc = new Account(); // balance = 0

    // assertThrows thất bại nếu không có ngoại lệ nào được ném ra
    IllegalStateException ex = assertThrows(
        IllegalStateException.class,
        () -> acc.withdraw(100.0)
    );

    // Cũng có thể khẳng định các chi tiết ngoại lệ — điều không thể làm trong try-catch nếu không viết thêm mã nguồn
    assertEquals("Insufficient funds: 0.0", ex.getMessage());
    assertNotNull(ex); // dư thừa nhưng chứng minh ex có thể truy cập được
}
```

### Chuỗi Nguyên Nhân - Kết Quả
Mẫu try-catch: ngoại lệ không được ném ra &rarr; khối catch bị bỏ qua &rarr; mọi khẳng định bên trong catch bị bỏ qua &rarr; bài kiểm thử báo cáo PASS mặc dù không có gì được xác minh. `assertThrows`: ngoại lệ không được ném ra &rarr; bài kiểm thử lập tức thất bại với thông báo rõ ràng "Expected X to be thrown, but nothing was thrown" &rarr; Trả về ngoại lệ được ném ra để thực hiện khẳng định tiếp theo &rarr; Xác minh ngoại lệ hoàn chỉnh chỉ trong một câu lệnh.

---

## Tại Sao Độ Bao Phủ Mã Nguồn Là Chỉ Số Chất Lượng Cần Thiết Nhưng Chưa Đủ

Độ bao phủ mã nguồn đo lường việc **thực thi** — một dòng hoặc nhánh mã nguồn có được đi qua trong các lần chạy kiểm thử hay không. Nó không đo lường **tính chính xác** — liệu các khẳng định trong các bài kiểm thử đó có thực sự xác minh đúng hành vi hay không. Khoảng cách này tạo ra phản mẫu "kiểm thử không có khẳng định (assertion-free testing)": các bài kiểm thử đạt độ bao phủ cao bằng cách chạy qua mã nguồn nhưng không bao giờ kiểm tra kết quả.

Ví dụ, một bài kiểm thử gọi `calculator.add(2, 3)` mà không gọi bất kỳ `assertEquals` hoặc khẳng định nào khác vẫn đạt được 100% độ bao phủ dòng cho phương thức `add`, nhưng bắt được không lỗi nào. Ngay cả một bài kiểm thử với `assertTrue(true)` cũng đạt được độ bao phủ. Các công cụ đo độ bao phủ không thể phân biệt các khẳng định có ý nghĩa với các khẳng định trống rỗng.

Độ bao phủ mã nguồn cao là một nền tảng **cần thiết** (mã nguồn chưa được kiểm thử chắc chắn có mức bảo vệ bằng không) nhưng là một sự đảm bảo **chưa đủ** về chất lượng. Chỉ số bổ sung là **kiểm thử đột biến (mutation testing)** (các công cụ như PIT), hoạt động bằng cách chèn các đột biến mã nguồn nhân tạo và xác minh xem bộ kiểm thử có phát hiện ra chúng hay không.

### Mô Hình Tư Duy: Độ Bao Phủ so với Tính Đúng Đắn
```
[Độ bao phủ cao, chất lượng bằng không]
@Test
void testAdd() {
    calculator.add(2, 3); // Mã nguồn được THỰC THI ← đạt 100% độ bao phủ dòng
    // Không có khẳng định — kết quả bị vứt bỏ
    // Lỗi: add() luôn trả về 0 → bài kiểm thử vượt qua, không phát hiện được lỗi
}

[Độ bao phủ cao + khẳng định chất lượng]
@Test
void testAdd() {
    int result = calculator.add(2, 3);
    assertEquals(5, result); // XÁC MINH kết quả
    // Lỗi: add() trả về 0 → bài kiểm thử thất bại → phát hiện được lỗi
}
```

### Ví Dụ Thực Tế: Độ bao phủ nhánh và giới hạn của độ bao phủ dòng
```java
public String classify(int n) {
    if (n > 0) return "positive";  // dòng 1
    if (n < 0) return "negative";  // dòng 2
    return "zero";                  // dòng 3
}

@Test
void testClassify_linesCovered() {
    classify(1);  // Bao phủ dòng 1 — 1/3 số dòng được bao phủ, 0 khẳng định
    classify(-1); // Bao phủ dòng 2 — 2/3 số dòng được bao phủ, 0 khẳng định
    classify(0);  // Bao phủ dòng 3 — 3/3 số dòng được bao phủ (100%), 0 khẳng định!
    // Kết quả: 100% độ bao phủ dòng, 0% khả năng phát hiện lỗi
}

@Test
void testClassify_withAssertions() {
    assertEquals("positive", classify(1));
    assertEquals("negative", classify(-1));
    assertEquals("zero", classify(0));
    // Cùng độ bao phủ, nhưng giờ đây các khẳng định sẽ bắt được các giá trị trả về sai
}
```

### Chuỗi Nguyên Nhân - Kết Quả
Đạt 100% độ bao phủ dòng &rarr; Mọi dòng mã nguồn đều được thực thi trong quá trình kiểm thử &rarr; KHÔNG có nghĩa là hành vi của mỗi dòng đều được khẳng định &rarr; Các bài kiểm thử không có khẳng định vượt qua âm thầm bất kể có lỗi &rarr; Phản mẫu kiểm thử không có khẳng định: chỉ số bao phủ được thỏa mãn, nhưng chất lượng thì không &rarr; Bổ sung độ bao phủ bằng việc đánh giá chất lượng khẳng định và kiểm thử đột biến.

## Liên Kết Tham Khảo

- https://junit.org/junit5/docs/current/user-guide/ (JUnit 5 User Guide)
- https://site.mockito.org/ (Mockito documentation)
