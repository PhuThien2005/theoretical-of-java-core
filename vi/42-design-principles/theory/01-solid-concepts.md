# Các nguyên tắc thiết kế cơ bản thường đi kèm với Java Core - Phần 1 (Basic Design Principles Often Paired With Java Core - Part 1)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm các **Nguyên tắc thiết kế (Design Principles)** cơ bản đi kèm với phát triển Java Core (SOLID, DRY, KISS, YAGNI, coupling, cohesion và Clean Code). Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế.

## Phạm vi đề cương (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `SOLID` | Năm nguyên tắc thiết kế hướng đối tượng cốt lõi để xây dựng phần mềm dễ bảo trì và mở rộng. |
| `DRY` | "Don't Repeat Yourself" (Đừng lặp lại chính mình) – tránh dư thừa trong mã nguồn và tri thức hệ thống. |
| `KISS` | "Keep It Simple, Stupid" (Giữ mọi thứ đơn giản) – ưu tiên cấu trúc đơn giản, dễ đọc hơn là những trừu tượng (Abstraction) hóa phức tạp. |
| `YAGNI` | "You Aren't Gonna Need It" (Bạn chưa cần nó đâu) – tránh triển khai các tính năng sớm cho đến khi chúng thực sự cần thiết. |
| `Composition over inheritance` | Tái sử dụng hành vi bằng cách bao đóng các biến instance thay vì mở rộng (kế thừa) các lớp. |
| `Coupling` | Mức độ phụ thuộc lẫn nhau giữa các lớp; mục tiêu là độ liên kết (coupling) lỏng lẻo (loose coupling). |
| `Cohesion` | Mức độ tập trung của một lớp vào một tác vụ duy nhất; mục tiêu là độ gắn kết (cohesion) cao (high cohesion). |
| `Basic Dependency Injection` | Tiêm các phụ thuộc từ bên ngoài thông qua các tham số của hàm khởi tạo hoặc phương thức để dễ dàng kiểm thử. |
| `Defensive programming` | Kiểm thực các điều kiện tiên quyết, dữ liệu đầu vào và các giả định trạng thái để viết mã nguồn chống sập ứng dụng. |
| `Basic Clean Code` | Viết mã nguồn Java dễ đọc, định dạng tốt và dễ dàng tái cấu trúc (refactoring). |

---

## Ghi chú chi tiết (Detailed Notes)

### SOLID

SOLID đại diện cho năm nguyên tắc cốt lõi của thiết kế hướng đối tượng:

1. **S**ingle Responsibility Principle (SRP - Nguyên tắc đơn trách nhiệm): Một lớp chỉ nên có một lý do duy nhất để thay đổi.
2. **O**pen/Closed Principle (OCP - Nguyên tắc Đóng/Mở): Các thực thể phần mềm nên mở rộng để phát triển thêm nhưng đóng lại đối với việc sửa đổi trực tiếp.
3. **L**iskov Substitution Principle (LSP - Nguyên tắc thay thế Liskov): Các lớp con (subtypes) phải có khả năng thay thế cho các lớp cha (base types) mà không làm thay đổi tính đúng đắn của chương trình.
4. **I**nterface Segregation Principle (ISP - Nguyên tắc phân tách giao diện): Client không nên bị buộc phải phụ thuộc vào các phương thức mà họ không sử dụng (chia nhỏ các giao diện quá lớn - fat interfaces).
5. **D**ependency Inversion Principle (DIP - Nguyên tắc đảo ngược phụ thuộc): Nên phụ thuộc vào trừu tượng (interfaces/abstract classes), không nên phụ thuộc vào các lớp cụ thể.

- **Ví dụ có thể chạy được (Đảo ngược phụ thuộc - Dependency Inversion)**:
  ```java
  public interface MessageSender {
      void send(String msg);
  }

  public class EmailSender implements MessageSender {
      public void send(String msg) { /* sends email */ }
  }

  public class NotificationService {
      private final MessageSender sender; // Phụ thuộc vào interface trừu tượng

      public NotificationService(MessageSender sender) { // Được tiêm vào qua hàm khởi tạo
          this.sender = sender;
      }
  }
  ```

---

### DRY (Don't Repeat Yourself)

"Don't Repeat Yourself" (Đừng lặp lại chính mình) chỉ ra rằng mỗi phần logic của hệ thống phải có một biểu diễn duy nhất, không mơ hồ và có thẩm quyền trong mã nguồn.

- **Ví dụ có thể chạy được**:
  ```java
  // TỒI: Copy-paste logic kiểm tra dữ liệu đầu vào trong nhiều controller
  
  // TỐT: Trích xuất việc kiểm thực vào một lớp tiện ích validator tĩnh thống nhất
  public final class InputValidator {
      public static void validateEmail(String email) {
          if (email == null || !email.contains("@")) {
              throw new IllegalArgumentException("Malformed email address");
          }
      }
  }
  ```

- **Sai lầm thường gặp**: **Lạm dụng DRY (Over-DRYing)**. Chia sẻ mã nguồn giữa hai miền nghiệp vụ (business domains) vô tình trông giống hệt nhau hôm nay, nhưng phục vụ các nhu cầu nghiệp vụ hoàn toàn khác nhau. Nếu các yêu cầu của chúng phân rã vào ngày mai, bạn sẽ kết thúc với các lớp cực kỳ phức tạp chứa đầy các cờ điều kiện. Mã nguồn trùng lặp tốt hơn là một trừu tượng hóa sai lầm.

---

### KISS (Keep It Simple, Stupid)

"Keep It Simple, Stupid" (Giữ mọi thứ đơn giản) yêu cầu mã nguồn nên được viết đơn giản và trực tiếp nhất có thể. Tránh việc thiết kế quá mức (over-engineering) với các mẫu thiết kế (design patterns) sớm, phân cấp kế thừa sâu hoặc reflection phức tạp khi logic đơn giản, dễ đọc là đủ để giải quyết vấn đề.

- **Ví dụ có thể chạy được**:
  ```java
  // TỒI: Kiểm tra phức tạp quá mức
  public boolean isPositive(int number) {
      return Optional.of(number)
                     .filter(n -> n > 0)
                     .isPresent();
  }

  // TỐT: Đơn giản, trực tiếp và hiệu năng tốt hơn
  public boolean isPositive(int number) {
      return number > 0;
  }
  ```

---

### YAGNI (You Aren't Gonna Need It)

"You Aren't Gonna Need It" (Bạn chưa cần nó đâu) quy định rằng bạn không nên triển khai các tính năng, lớp tiện ích hoặc các lớp có khả năng mở rộng dựa trên giả định rằng "chúng ta có thể cần chúng sau này".

- **Sự đánh đổi**: Triển khai các tính năng suy đoán làm lãng phí thời gian của lập trình viên, làm phình các bài kiểm thử (tests), tăng bề mặt bảo trì ứng dụng và hạn chế tính linh hoạt trong tương lai. Chỉ viết mã nguồn bạn thực sự cần *ngày hôm nay*.

---

### Ưu tiên thành phần hơn kế thừa (Composition over inheritance)

Đạt được hành vi đa hình (polymorphic) và tái sử dụng mã nguồn bằng cách nhóm các instance của lớp tiện ích helper ("has-a" - có một) thay vì tạo lớp con kế thừa lớp cha ("is-a" - là một).

- **Ví dụ có thể chạy được**:
  ```java
  public class Engine {
      public void start() {}
  }

  // TỐT: Lớp Car bao đóng Engine để tái sử dụng hành vi khởi động
  public class Car {
      private final Engine engine = new Engine();

      public void drive() {
          engine.start();
          System.out.println("Driving...");
      }
  }
  ```

---

### Độ liên kết (Coupling)

Độ liên kết (Coupling) đo lường mức độ phụ thuộc lẫn nhau giữa hai lớp. Mục tiêu là **độ liên kết lỏng lẻo (loose coupling)** để việc sửa đổi lớp A không làm hỏng lớp B.

- **Giảm thiểu**: Sử dụng các interface để định nghĩa ranh giới, khai báo các phụ thuộc một cách rõ ràng thông qua các tham số của hàm khởi tạo, và ẩn các chi tiết triển khai đằng sau các modifier private.

---

### Độ gắn kết (Cohesion)

Độ gắn kết (Cohesion) đo lường mức độ tập trung của các phương thức và biến trong một lớp vào một tác vụ logic duy nhất. Mục tiêu là **độ gắn kết cao (high cohesion)**.

- **Ví dụ**:
  - **Độ gắn kết thấp**: Một lớp tiện ích `UserHelper` xử lý băm mật khẩu, tải cơ sở dữ liệu, tuần tự hóa JSON và gửi tin nhắn SMS xác thực.
  - **Độ gắn kết cao**: Lớp `PasswordHasher` tập trung hoàn toàn vào việc mã hóa và xác thực các chuỗi băm.

---

### Tiêm phụ thuộc cơ bản (Basic Dependency Injection)

Các lớp nên nhận các phụ thuộc cần thiết của chúng từ bên ngoài (thường thông qua các đối số của hàm khởi tạo) thay vì tự khởi tạo chúng bên trong lớp.

- **Ví dụ có thể chạy được**:
  ```java
  public class OrderService {
      private final PaymentClient paymentClient;

      // Phụ thuộc được tiêm vào thay vì được tạo trực tiếp qua "new PaymentClient()"
      public OrderService(PaymentClient paymentClient) {
          this.paymentClient = paymentClient;
      }
  }
  ```

---

### Lập trình phòng thủ (Defensive programming)

Lập trình phòng thủ (Defensive programming) là việc thiết kế mã nguồn để ứng dụng tiếp tục thực thi hoặc thất bại một cách an toàn ngay cả khi gặp phải các dữ liệu đầu vào không mong muốn, trạng thái hệ thống không hợp lệ hoặc các cuộc gọi sai.

- **Ví dụ có thể chạy được**:
  ```java
  public void registerUser(String username, int age) {
      // Validate inputs early (fail fast - thất bại sớm)
      Objects.requireNonNull(username, "Username cannot be null");
      if (age < 18) {
          throw new IllegalArgumentException("User must be at least 18 years old");
      }
      // Tiếp tục quá trình đăng ký
  }
  ```

---

### Mã sạch cơ bản (Basic Clean Code)

Mã sạch (Clean Code) là mã nguồn được viết chủ yếu để người khác dễ đọc và dễ hiểu.

- **Quy tắc cốt lõi**:
  - Các hàm nên ngắn gọn và chỉ làm đúng một việc.
  - Hạn chế độ sâu thụt lề (indentation) của phương thức (ví dụ: tránh các vòng lặp lồng nhau và kiểm tra `if` sâu quá 2 cấp; trả về sớm - return early - để giữ code phẳng).
  - Viết tên mang tính mô tả, và không sử dụng các bình luận (comments) để giải thích cho mã nguồn tồi — hãy viết lại code cho rõ ràng.

---

## Tại saọ Đơn trách nhiệm thúc đẩy Độ gắn kết cao (Why Single Responsibility Promotes High Cohesion)

Trong JVM, các lớp là đơn vị cơ bản cho việc triển khai, nạp lớp (class loading) và thực thi. Khi một lớp có nhiều trách nhiệm, nó sẽ tích lũy các biến instance và phương thức không liên quan, làm giảm độ gắn kết của nó.

Một lớp có độ gắn kết cao có các trường và phương thức thống nhất về mặt khái niệm và chức năng, nghĩa là các phương thức của lớp hoạt động nhất quán trên các trường của nó. Khi nhiều trách nhiệm được đóng gói vào một lớp duy nhất, việc thay đổi yêu cầu của một miền nghiệp vụ sẽ buộc toàn bộ lớp phải được biên dịch lại và triển khai lại, ảnh hưởng đến các miền nghiệp vụ khác không liên quan. Điều này có thể gây phình phụ thuộc classpath và tăng rủi ro tác dụng phụ (side effects), nơi các sửa đổi cho một tính năng vô tình làm hỏng tính năng khác do chia sẻ trạng thái.

Bằng cách thực thi Nguyên tắc đơn trách nhiệm (SRP), chúng ta đảm bảo rằng một lớp được tải bởi ClassLoader như một đơn vị thay đổi duy nhất, cô lập với một mục đích duy nhất, từ đó giảm độ liên kết lớp (class coupling) và các phụ thuộc ở thời điểm biên dịch.

### Mô hình tư duy (Mental Model)
```text
Độ gắn kết thấp (Lớp béo - Fat Class):
+------------------------------------------+
|                 UserClass                |
|  [dữ liệu] name, email, hashedPassword   |
|  [phương thức] saveToDb(), sendEmail()   |
+------------------------------------------+
                  /         \
         Thay đổi ở DB      Thay đổi ở Email API
                  \         /
             Biên dịch lại toàn bộ lớp!


Độ gắn kết cao (Tách biệt theo SRP):
+------------------+     +------------------+
|    UserEntity    |     |   EmailService   |
| [dữ liệu] name...|---->| [phương thức]    |
+------------------+     | sendEmail()      |
                         +------------------+
```

### Ví dụ Code (Code Example)
```java
// Thiết kế tuân thủ SRP
class User {
    private final String username;
    private final String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getEmail() { return email; }
    public String getUsername() { return username; }
}

class EmailSender {
    public void sendWelcomeEmail(User user) {
        System.out.println("Email sent to " + user.getEmail());
    }
}

public class Main {
    public static void main(String[] args) {
        User user = new User("alice", "alice@example.com");
        EmailSender sender = new EmailSender();
        sender.sendWelcomeEmail(user);
        // Output: Email sent to alice@example.com
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Đơn trách nhiệm cho một lớp
  → Tất cả các phương thức tập trung vào một tác vụ duy nhất
  → Các trường và phương thức liên quan chặt chẽ với nhau (Độ gắn kết cao)
  → Sửa đổi một yêu cầu chỉ thay đổi lớp tương ứng
  → Phần còn lại của hệ thống không bị ảnh hưởng
  → Độ liên kết lỏng lẻo được bảo toàn.
```


---

## Tại sao Nguyên tắc Đóng/Mở bảo vệ Mã nguồn hiện có (Why Open/Closed Principle Protects Existing Code)

Nguyên tắc Đóng/Mở (OCP) tận dụng các cơ chế hướng đối tượng của Java về đa hình (polymorphism) và liên kết động (dynamic binding) để cho phép khả năng mở rộng phần mềm.

Khi hành vi được mở rộng thông qua kế thừa (subclassing) hoặc triển khai các interface, các lệnh `invokevirtual` và `invokeinterface` của JVM sẽ thực hiện phân phát phương thức động (dynamic method dispatch) tại thời điểm chạy, giải quyết cuộc gọi phương thức dựa trên kiểu đối tượng thực tế chứ không phải kiểu tham chiếu.

Việc sửa đổi trực tiếp các lớp đã biên dịch hiện có là rất rủi ro vì nó yêu cầu chỉnh sửa mã nguồn đã được xác thực, kiểm thử, có thể gây ra lỗi hồi quy (regression bugs) và phá hỏng khả năng tương thích nhị phân (binary compatibility) hiện có. Bằng cách thiết kế hệ thống sử dụng các lớp trừu tượng hoặc các hợp đồng giao diện (interface contracts), logic cơ bản vẫn nguyên vẹn và đóng lại với việc sửa đổi, trong khi các tính năng mới được thêm dưới dạng các lớp mới (mở để mở rộng).

Sự tách biệt ở thời điểm biên dịch này đảm bảo rằng bytecode hiện tại không cần phải biên dịch lại hoặc xác thực lại bởi JVM, giúp ổn định đáng kể các hoạt động triển khai phần mềm doanh nghiệp.

### Mô hình tư duy (Mental Model)
```text
Không có OCP (Sửa đổi lớp hiện có):
Client ---> [ PaymentProcessor ]  <-- (Sửa đổi lớp này để thêm phương thức mới)
              (Nguy cơ làm hỏng xử lý Visa hiện tại!)

Có OCP (Mở rộng qua Interface):
Client ---> [ PaymentProcessor (Interface) ]
                    ^                  ^
                    |                  |
            [ VisaProcessor ]   [ PayPalProcessor ] <-- Lớp mới, không rủi ro cho Visa!
```

### Ví dụ Code (Code Example)
```java
interface Payment {
    void process();
}

class VisaPayment implements Payment {
    public void process() {
        System.out.println("Visa payment processed.");
    }
}

class PayPalPayment implements Payment {
    public void process() {
        System.out.println("PayPal payment processed.");
    }
}

class PaymentService {
    public void executePayment(Payment payment) {
        payment.process();
    }
}

public class Main {
    public static void main(String[] args) {
        PaymentService service = new PaymentService();
        service.executePayment(new VisaPayment());
        service.executePayment(new PayPalPayment());
        // Output:
        // Visa payment processed.
        // PayPal payment processed.
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Chương trình phụ thuộc vào các giao diện
  → Các tính năng mới được triển khai bằng cách thêm các lớp mới
  → Không sửa đổi các lớp hiện có
  → Các lớp cũ không cần biên dịch lại hoặc kiểm thử lại
  → Tránh được các rủi ro hồi quy
  → Hệ thống duy trì tính ổn định.
```


---

## Tại sao Nguyên tắc thay thế Liskov thực thi các Hợp đồng hành vi (Why Liskov Substitution Principle Enforces Behavioral Contracts)

Đa hình kiểu con (Subtype polymorphism) trong Java cho phép một biến tham chiếu của một lớp cha hoặc kiểu interface trỏ đến bất kỳ instance nào của lớp con. Nguyên tắc thay thế Liskov (LSP) đảm bảo rằng sự thay thế này là an toàn bằng cách yêu cầu các lớp con phải tuân thủ hợp đồng hành vi (behavioral contract) được xác định bởi lớp cha.

Trong Java, mặc dù trình biên dịch thực thi tính an toàn kiểu tĩnh (static type safety - chẳng hạn như chữ ký phương thức và tính đồng biến của kiểu trả về), nó không thể thực thi các bất biến hành vi (behavioral invariants) tại thời điểm chạy. Các lớp con vi phạm LSP khi chúng tăng cường các điều kiện tiên quyết (chẳng hạn như ném ra một ngoại lệ checked mới hoặc yêu cầu các tham số đầu vào đáp ứng các ràng buộc chặt chẽ hơn) hoặc làm yếu đi các điều kiện sau (như trả về một tham chiếu null khi hợp đồng lớp cha đảm bảo một đối tượng không null, hoặc sửa đổi trạng thái kế thừa theo cách phá hỏng các bất biến của lớp cha).

Khi các hợp đồng hành vi tại thời điểm chạy này bị vi phạm, tính đa hình sẽ thất bại vì mã client được thiết kế để hoạt động với lớp cha sẽ hành xử không thể đoán trước hoặc ném ra các ngoại lệ runtime khi gặp phải lớp con.

### Mô hình tư duy (Mental Model)
```text
Lớp cha (Hợp đồng: trả về số nguyên dương)
    [ MathHelper ] -> getValue() trả về >= 1

Lớp con A (Tuân thủ LSP)
    [ SecureHelper ] -> getValue() trả về >= 1 (Tuân thủ hợp đồng)

Lớp con B (Vi phạm LSP)
    [ BadHelper ] -> getValue() trả về 0 hoặc số âm (Vi phạm hợp đồng!)
    Client mong đợi số nguyên dương sẽ bị lỗi (ví dụ: chia cho 0)!
```

### Ví dụ Code (Code Example)
```java
class Rectangle {
    protected int width;
    protected int height;

    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public int getArea() { return width * height; }
}

class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width;
    }

    @Override
    public void setHeight(int height) {
        this.width = height;
        this.height = height;
    }
}

public class Main {
    public static void verifyRectangle(Rectangle r) {
        r.setWidth(5);
        r.setHeight(10);
        System.out.println("Expected Area: 50, Actual: " + r.getArea());
    }

    public static void main(String[] args) {
        verifyRectangle(new Rectangle()); // Output: Expected Area: 50, Actual: 50
        verifyRectangle(new Square());    // Output: Expected Area: 50, Actual: 100 (Vi phạm LSP!)
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Lớp con ghi đè phương thức lớp cha
  → Lớp con thắt chặt các điều kiện trước hoặc làm lỏng lẻo các điều kiện sau
  → Mã client giữ tham chiếu lớp cha
  → Mã client thực thi phương thức lớp con qua phân phát động
  → Lớp con vi phạm các giả định hành vi của lớp cha
  → Xảy ra lỗi runtime hoặc logic không chính xác.
```


---

## Tại sao Phân tách giao diện ngăn chặn Độ liên kết giao diện béo (Why Interface Segregation Prevents Fat Interface Coupling)

Trong JVM, khi một lớp triển khai một giao diện (interface), nó phải cung cấp triển khai cụ thể cho tất cả các phương thức không phải mặc định (non-default) được định nghĩa bởi giao diện đó, hoặc nếu không lớp đó phải được khai báo là abstract.

Một giao diện "béo" (fat interface) chứa các phương thức dành cho các client khác nhau, không liên quan đến nhau sẽ buộc mọi lớp triển khai phải phụ thuộc vào và triển khai các phương thức mà nó không yêu cầu, thường dẫn đến các thân phương thức rỗng hoặc giả lập ném ra ngoại lệ `UnsupportedOperationException`. Thiết kế này liên kết các thành phần không liên quan lại với nhau tại thời điểm biên dịch: nếu chữ ký phương thức trong giao diện béo thay đổi, tất cả các lớp triển khai phải được biên dịch lại và liên kết lại bởi JVM, ngay cả khi chúng chưa bao giờ gọi hoặc sử dụng phương thức đó.

Bằng cách phân tách một giao diện phình to thành các giao diện nhỏ, đặc trưng cho từng client, chúng ta giảm thiểu kích thước của các tham chiếu bảng giao diện (interface table - `itable`) được phân giải trong các cuộc gọi `invokeinterface`. Do đó, client chỉ phụ thuộc vào các phương thức cụ thể mà họ thực sự thực thi, điều này loại bỏ các phụ thuộc biên dịch không cần thiết, chi phí nạp lớp và tính mong manh của mã nguồn lúc chạy.

### Mô hình tư duy (Mental Model)
```text
Giao diện béo (Fat Interface - liên kết các client không liên quan):
+-------------------------------+
|        MultiFunction          |
|  print(), scan(), fax()       |
+-------------------------------+
        ^               ^
        |               |
  SimplePrinter     SuperOfficeJet (Cần tất cả)
  (bị buộc phải ném UnsupportedOperationException trên fax()!)


Giao diện phân tách (Nhỏ gọn, đặc trưng cho client):
+-------------+   +-------------+
|   Printer   |   |   Scanner   |
|   print()   |   |   scan()    |
+-------------+   +-------------+
        ^                 ^
        |                 |
        +--- SimplePrinter+
```

### Ví dụ Code (Code Example)
```java
interface Printer {
    void print();
}

interface Scanner {
    void scan();
}

class BasicPrinter implements Printer {
    public void print() {
        System.out.println("Printing document...");
    }
}

class MultiFunctionPrinter implements Printer, Scanner {
    public void print() {
        System.out.println("Printing document...");
    }
    public void scan() {
        System.out.println("Scanning document...");
    }
}

public class Main {
    public static void main(String[] args) {
        Printer printer = new BasicPrinter();
        printer.print();
        
        MultiFunctionPrinter mfp = new MultiFunctionPrinter();
        mfp.print();
        mfp.scan();
        // Output:
        // Printing document...
        // Printing document...
        // Scanning document...
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Giao diện béo chứa các phương thức không liên quan
  → Các lớp triển khai bị buộc phải viết các triển khai rỗng/giả lập
  → Việc sửa đổi chữ ký phương thức không sử dụng xảy ra
  → Phải biên dịch lại và liên kết lại tất cả các lớp triển khai
  → Tăng độ liên kết ở thời điểm biên dịch và rủi ro xảy ra lỗi runtime.
```


---

## Tại sao Đảo ngược phụ thuộc giúp tách biệt các Module (Why Dependency Inversion Decouples Modules)

Nguyên tắc đảo ngược phụ thuộc (DIP) đảo ngược dòng phụ thuộc từ trên xuống truyền thống của các hệ thống phần mềm bằng cách tuyên bố rằng các module cấp cao không nên phụ thuộc vào các triển khai cụ thể cấp thấp.

Theo mô hình phụ thuộc trực tiếp, các mối quan hệ ở thời điểm biên dịch bị ràng buộc trực tiếp vào các lớp cụ thể, nghĩa là các lớp cấp cao không thể được biên dịch hoặc kiểm thử độc lập với các module cấp thấp như cơ sở dữ liệu hoặc API bên ngoài. Bằng cách giới thiệu các interface làm lớp trừu tượng ở giữa các tầng này, cả module cấp cao và cấp thấp đều phụ thuộc vào giao diện trừu tượng đó.

Ở thời điểm biên dịch, lớp cấp cao hoàn toàn dựa vào kiểu interface, được xác thực bởi trình kiểm tra kiểu tĩnh của Java. Tại thời điểm chạy, các triển khai cụ thể được tiêm vào lớp cấp cao bằng cách sử dụng Tiêm phụ thuộc (Dependency Injection - DI) thông qua các hàm khởi tạo hoặc setter, và JVM giải quyết các cuộc gọi phương thức động thông qua tính đa hình.

Điều này tách biệt mối quan hệ ở thời điểm biên dịch, cho phép thay thế giả lập (mock substitution) dễ dàng cho việc kiểm thử đơn vị (unit testing) và cho phép các lập trình viên hoán đổi các lớp cơ sở hạ tầng cấp thấp mà không cần thay đổi logic nghiệp vụ cốt lõi.

### Mô hình tư duy (Mental Model)
```text
Phụ thuộc trực tiếp (Liên kết chặt chẽ - Tight Coupling):
[ High-Level Service ] ---> [ Concrete MySQLDatabase ]
(Service bị code cứng với MySQL; không thể test nếu database không chạy!)

Đảo ngược phụ thuộc (Liên kết lỏng lẻo - Loose Coupling):
[ High-Level Service ] ---> [ Database (Interface) ]
                                   ^
                                   |
                       [ Concrete MySQLDatabase ] hoặc [ MockDatabase ]
```

### Ví dụ Code (Code Example)
```java
interface Database {
    void save(String data);
}

class MySqlDatabase implements Database {
    public void save(String data) {
        System.out.println("Saved to MySQL: " + data);
    }
}

class MockDatabase implements Database {
    public void save(String data) {
        System.out.println("Saved to Mock: " + data);
    }
}

class OrderProcessor {
    private final Database database;

    public OrderProcessor(Database database) {
        this.database = database;
    }

    public void process(String orderId) {
        database.save(orderId);
    }
}

public class Main {
    public static void main(String[] args) {
        OrderProcessor production = new OrderProcessor(new MySqlDatabase());
        production.process("order-100");

        OrderProcessor test = new OrderProcessor(new MockDatabase());
        test.process("order-100");
        // Output:
        // Saved to MySQL: order-100
        // Saved to Mock: order-100
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Module cấp cao tham chiếu giao diện trừu tượng
  → Các triển khai cụ thể cấp thấp triển khai cùng một giao diện
  → Dependency Injection cung cấp thực thể cụ thể tại thời điểm chạy
  → Tham chiếu ở thời điểm biên dịch vẫn gắn liền với sự trừu tượng
  → Thay đổi cấp thấp không yêu cầu biên dịch lại mã cấp cao
  → Các thành phần hệ thống liên kết lỏng lẻo và có thể kiểm thử được.
```


## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/javase/tutorial/java/concepts/
- https://docs.oracle.com/javase/specs/jls/se21/html/index.html
