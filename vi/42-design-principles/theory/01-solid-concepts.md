# Các Nguyên Tắc Thiết Kế Cơ Bản Thường Đi Kèm Với Java Core - Phần 1

## Mục Tiêu Học Tập

File này đề cập đến các **Nguyên tắc thiết kế (Design Principles)** cơ bản đi kèm với quá trình phát triển Java Core (SOLID, DRY, KISS, YAGNI, coupling, cohesion, và Clean Code). Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế.

## Đề Cương Khái Niệm

| Khái niệm | Những điều cần biết |
| --- | --- |
| `SOLID` | Năm nguyên tắc thiết kế hướng đối tượng cốt lõi để xây dựng phần mềm dễ bảo trì và dễ mở rộng. |
| `DRY` | "Don't Repeat Yourself" (Đừng lặp lại chính mình) – tránh sự dư thừa trong mã nguồn và tri thức hệ thống. |
| `KISS` | "Keep It Simple, Stupid" (Giữ mọi thứ đơn giản) – ưu tiên các cấu trúc đơn giản, dễ đọc hơn là các trừu tượng hóa phức tạp. |
| `YAGNI` | "You Aren't Gonna Need It" (Bạn chưa cần đến nó đâu) – tránh triển khai các tính năng sớm cho đến khi chúng thực sự cần thiết. |
| `Ưu tiên thành phần hơn kế thừa` | Tái sử dụng hành vi bằng cách bao bọc các biến thực thể (instance variable) thay vì kế thừa các lớp. |
| `Tính liên kết (Coupling)` | Mức độ phụ thuộc lẫn nhau giữa các lớp; mục tiêu là liên kết lỏng (loose coupling). |
| `Tính gắn kết (Cohesion)` | Mức độ tập trung của một lớp vào một nhiệm vụ duy nhất; mục tiêu là gắn kết cao (high cohesion). |
| `Tiêm phụ thuộc cơ bản` | Tiêm các phụ thuộc bên ngoài qua các tham số của hàm khởi tạo hoặc phương thức để tạo điều kiện thuận lợi cho việc kiểm thử. |
| `Lập trình phòng thủ` | Xác thực các điều kiện tiên quyết, đầu vào và các giả định trạng thái để viết mã nguồn chống sụp đổ chương trình (crash-resistant). |
| `Quy tắc viết code sạch cơ bản` | Viết mã nguồn Java dễ đọc, có định dạng và dễ dàng tái cấu trúc (refactoring). |

---

## Ghi Chú Chi Tiết

### SOLID

SOLID đại diện cho năm nguyên tắc cốt lõi của thiết kế hướng đối tượng:

> Xem thêm: Các đặc tính cốt lõi của Lập trình Hướng đối tượng (OOP) tạo nền tảng cho các nguyên tắc SOLID, được trình bày chi tiết trong [Ch.09 - OOP](../../09-oop/README.md).

1. Nguyên tắc Đơn trách nhiệm (**S**ingle Responsibility Principle - SRP): Một lớp chỉ nên có duy nhất một lý do để thay đổi.
2. Nguyên tắc Đóng/Mở (**O**pen/Closed Principle - OCP): Các thực thể phần mềm nên được mở rộng cho việc phát triển nhưng đóng cho việc sửa đổi.
3. Nguyên tắc Thay thế Liskov (**L**iskov Substitution Principle - LSP): Các kiểu con phải có khả năng thay thế cho các kiểu cha của chúng mà không làm thay đổi tính đúng đắn của chương trình.
4. Nguyên tắc Phân tách Interface (**I**nterface Segregation Principle - ISP): Các client không nên bị buộc phải phụ thuộc vào các phương thức mà chúng không sử dụng (chia nhỏ các interface phình to).
5. Nguyên tắc Đảo ngược Phụ thuộc (**D**ependency Inversion Principle - DIP): Hãy phụ thuộc vào các trừu tượng (interface), chứ không phải vào các lớp triển khai cụ thể.

- **Ví dụ chạy được (Đảo ngược Phụ thuộc)**:
  ```java
  public interface MessageSender {
      void send(String msg);
  }

  public class EmailSender implements MessageSender {
      public void send(String msg) { /* sends email */ }
  }

  public class NotificationService {
      private final MessageSender sender; // Depends on interface abstraction

      public NotificationService(MessageSender sender) { // Injected via constructor
          this.sender = sender;
      }
  }
  ```

---

### DRY

"Don't Repeat Yourself" (Đừng lặp lại chính mình) quy định rằng mỗi phần logic của hệ thống phải có một biểu diễn duy nhất, không mơ hồ và có thẩm quyền trong mã nguồn.

- **Ví dụ chạy được**:
  ```java
  // BAD: Copy-pasting input verification logic in multiple controllers
  
  // GOOD: Extract validation to a unified static validator utility
  public final class InputValidator {
      public static void validateEmail(String email) {
          if (email == null || !email.contains("@")) {
              throw new IllegalArgumentException("Malformed email address");
          }
      }
  }
  ```

- **Sai lầm phổ biến**: **Lạm dụng DRY (Over-DRYing)**. Chia sẻ mã nguồn giữa hai miền nghiệp vụ ngẫu nhiên trông giống hệt nhau ở hiện tại, nhưng thực tế phục vụ các nhu cầu nghiệp vụ hoàn toàn khác nhau. Nếu các yêu cầu của chúng phân kỳ vào ngày mai, bạn sẽ kết thúc với các lớp cực kỳ phức tạp chứa đầy các cờ điều kiện. Trùng lặp *mã nguồn* tốt hơn là một *trừu tượng hóa* sai.

---

### KISS

"Keep It Simple, Stupid" (Giữ mọi thứ đơn giản) yêu cầu mã nguồn nên được viết đơn giản và trực tiếp nhất có thể. Tránh kỹ nghệ hóa quá mức (over-engineering) với các mẫu thiết kế sớm, phân cấp sâu hoặc phản xạ phức tạp khi một logic đơn giản, dễ đọc đã có thể hoạt động tốt.

- **Ví dụ chạy được**:
  ```java
  // BAD: Over-engineered check
  public boolean isPositive(int number) {
      return Optional.of(number)
                     .filter(n -> n > 0)
                     .isPresent();
  }

  // GOOD: Simple, direct, and performs better
  public boolean isPositive(int number) {
      return number > 0;
  }
  ```

---

### YAGNI

"You Aren't Gonna Need It" (Bạn chưa cần đến nó đâu) quy định rằng bạn không nên triển khai các tính năng, lớp tiện ích hoặc các tầng mở rộng dựa trên giả định rằng "chúng ta có thể cần chúng sau này".

- **Đánh đổi**: Việc triển khai các tính năng suy đoán làm lãng phí thời gian của nhà phát triển, làm phình to các bài test, tăng diện tích bề mặt bảo trì và hạn chế tính linh hoạt trong tương lai. Chỉ viết những đoạn mã bạn thực sự cần *hôm nay*.

---

### Ưu Tiên Thành Phần Hơn Kế Thừa (Composition Over Inheritance)

Đạt được hành vi đa hình và tái sử dụng mã nguồn bằng cách nhóm các thực thể của các lớp trợ giúp ("has-a" - có một) thay vì phân lớp từ các lớp cha ("is-a" - là một).

- **Ví dụ chạy được**:
  ```java
  public class Engine {
      public void start() {}
  }

  // GOOD: Car encloses Engine to reuse start behavior
  public class Car {
      private final Engine engine = new Engine();

      public void drive() {
          engine.start();
          System.out.println("Driving...");
      }
  }
  ```

---

### Tính Liên Kết (Coupling)

Tính liên kết (coupling) đo lường mức độ phụ thuộc lẫn nhau giữa hai lớp. Mục tiêu là **liên kết lỏng (loose coupling)** để việc sửa đổi lớp A không làm gãy lớp B.

- **Biện pháp giảm thiểu**: Sử dụng các interface để xác định ranh giới, khai báo các phụ thuộc một cách tường minh qua các tham số của hàm khởi tạo, và ẩn các chi tiết triển khai đằng sau các bổ từ truy cập private.

---

### Tính Gắn Kết (Cohesion)

Tính gắn kết (cohesion) đo lường mức độ tập trung của các phương thức và biến bên trong một lớp vào một nhiệm vụ logic duy nhất. Mục tiêu là **gắn kết cao (high cohesion)**.

- **Ví dụ**:
  - **Gắn kết thấp**: Một lớp tiện ích `UserHelper` xử lý cả băm mật khẩu, tải cơ sở dữ liệu, tuần tự hóa JSON và gửi SMS xác thực.
  - **Gắn kết cao**: Một lớp `PasswordHasher` chỉ tập trung hoàn toàn vào việc mã hóa và xác thực các chuỗi băm.

---

### Tiêm Phụ Thuộc Cơ Bản (Basic Dependency Injection)

Các lớp nên nhận các phụ thuộc bắt buộc của chúng từ bên ngoài (thường qua các đối số của hàm khởi tạo) thay vì tự khởi tạo chúng ở bên trong.

- **Ví dụ chạy được**:
  ```java
  public class OrderService {
      private final PaymentClient paymentClient;

      // Dependency is injected rather than created via "new PaymentClient()"
      public OrderService(PaymentClient paymentClient) {
          this.paymentClient = paymentClient;
      }
  }
  ```

---

### Lập Trình Phòng Thủ (Defensive Programming)

Lập trình phòng thủ (defensive programming) là hoạt động thiết kế mã nguồn để tiếp tục thực thi hoặc thất bại một cách an toàn ngay cả khi gặp phải các đầu vào không mong đợi, trạng thái hệ thống bất thường hoặc các cuộc gọi không hợp lệ.

- **Ví dụ chạy được**:
  ```java
  public void registerUser(String username, int age) {
      // Validate inputs early (fail fast)
      Objects.requireNonNull(username, "Username cannot be null");
      if (age < 18) {
          throw new IllegalArgumentException("User must be at least 18 years old");
      }
      // Continue registration
  }
  ```

---

### Quy Tắc Viết Code Sạch Cơ Bản (Basic Clean Code)

Mã nguồn sạch (clean code) được viết chủ yếu để dễ đọc và dễ hiểu bởi các nhà phát triển khác.

- **Các quy tắc chính**:
  - Các hàm nên ngắn gọn và chỉ làm duy nhất một việc.
  - Giới hạn độ sâu thụt lề phương thức (ví dụ: tránh các vòng lặp lồng nhau và các kiểm tra `if` sâu hơn 2 cấp; trả về sớm để giữ cho mã nguồn phẳng).
  - Viết các tên có tính mô tả, và không sử dụng các comment để giải thích cho đoạn mã xấu — hãy viết lại đoạn mã đó cho rõ ràng.

---

## Tại Sao Đơn Trách Nhiệm Thúc Đẩy Tính Gắn Kết Cao

Trong JVM, các lớp là đơn vị cơ bản của việc triển khai (deployment), tải lớp (class loading) và thực thi. Khi một lớp gánh vác nhiều trách nhiệm, nó tích lũy các biến thực thể và phương thức không liên quan, làm giảm tính gắn kết của nó. Một lớp có tính gắn kết cao có các trường và phương thức thống nhất về mặt khái niệm và chức năng, nghĩa là các phương thức của lớp hoạt động nhất quán trên các trường của nó. Khi nhiều trách nhiệm được đóng gói vào một lớp duy nhất, sự thay đổi trong các yêu cầu của một miền nghiệp vụ sẽ buộc toàn bộ lớp đó phải biên dịch lại và triển khai lại, ảnh hưởng đến các miền không liên quan khác. Điều này có thể gây phình to phụ thuộc classpath và tăng nguy cơ gây ra các tác dụng phụ (side effect), nơi các sửa đổi đối với một tính năng vô tình làm hỏng tính năng khác do trạng thái dùng chung. Bằng cách thực thi Nguyên tắc Đơn trách nhiệm, chúng ta đảm bảo rằng một lớp được tải lên bởi ClassLoader như một đơn vị thay đổi độc lập, cô lập với một mục đích duy nhất, từ đó giảm thiểu tính liên kết giữa các lớp và các phụ thuộc tại thời điểm biên dịch.

### Đơn Trách Nhiệm và Sự Phân Tách (Mental Model)
```text
Gắn kết thấp (Lớp phình to):
+------------------------------------------+
|                 UserClass                |
|  [data] name, email, hashedPassword      |
|  [methods] saveToDb(), sendEmail()       |
+------------------------------------------+
                  /         \
         Thay đổi DB      Thay đổi Email API
                  \         /
             Biên dịch lại toàn bộ lớp!

Gắn kết cao (Tách biệt theo SRP):
+------------------+     +------------------+
|    UserEntity    |     |   EmailService   |
| [data] name, etc |---->|  [methods]       |
|                  |     |  sendEmail()     |
+------------------+     +------------------+
```

### Ví Dụ Mã Nguồn
```java
// SRP Compliant Design
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

### Chuỗi Nguyên Nhân - Kết Quả
Đơn trách nhiệm cho một lớp &rarr; Tất cả các phương thức tập trung vào một nhiệm vụ duy nhất &rarr; Các trường và phương thức liên quan chặt chẽ với nhau (Gắn kết cao) &rarr; Việc sửa đổi một yêu cầu chỉ thay đổi lớp tương ứng của nó &rarr; Phần còn lại của hệ thống không bị ảnh hưởng &rarr; Duy trì liên kết lỏng.

---

## Tại Sao Nguyên Tắc Đóng/Mở Bảo Vệ Mã Nguồn Hiện Có

Nguyên tắc Đóng/Mở (OCP) tận dụng các cơ chế hướng đối tượng của Java như tính đa hình (polymorphism) và liên kết động (dynamic binding) để cho phép khả năng mở rộng của phần mềm. Khi hành vi được mở rộng thông qua việc phân lớp hoặc triển khai các interface, các chỉ thị `invokevirtual` and `invokeinterface` của JVM sẽ thực hiện điều phối phương thức động (dynamic method dispatch) tại thời điểm chạy, phân giải lời gọi phương thức dựa trên kiểu đối tượng thực tế thay vì kiểu tham chiếu. Việc sửa đổi trực tiếp các lớp đã biên dịch hiện có là cực kỳ rủi ro vì nó đòi hỏi phải chỉnh sửa mã nguồn đã được xác thực, kiểm thử kỹ càng, điều này có thể đưa vào các lỗi hồi quy (regression bug) và phá vỡ tính tương thích nhị phân hiện có. Bằng cách thiết kế các hệ thống sử dụng các lớp trừu tượng hoặc các hợp đồng interface, logic cơ sở vẫn được giữ nguyên và đóng lại với việc sửa đổi, trong khi các tính năng mới được thêm dưới dạng các lớp mới (mở cho việc mở rộng). Sự tách biệt tại thời điểm biên dịch này đảm bảo rằng bytecode hiện tại không cần phải biên dịch lại hoặc xác thực lại bởi JVM, giúp ổn định hóa đáng kể việc triển khai phần mềm doanh nghiệp.

### Đóng để Sửa đổi so với Mở để Mở rộng (Mental Model)
```text
Không có OCP (Sửa đổi lớp hiện có):
Client ---> [ PaymentProcessor ]  <-- (Sửa đổi lớp này để thêm phương thức mới)
             (Nguy cơ làm hỏng quy trình xử lý Visa hiện tại!)

Có OCP (Mở rộng qua Interface):
Client ---> [ PaymentProcessor (Interface) ]
                    ^                  ^
                    |                  |
            [ VisaProcessor ]   [ PayPalProcessor ] <-- Lớp mới, không có rủi ro đối với Visa!
```

### Ví Dụ Mã Nguồn
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

### Chuỗi Nguyên Nhân - Kết Quả
Chương trình phụ thuộc vào các interface &rarr; Các tính năng mới được thực hiện bằng cách thêm các lớp mới &rarr; Không sửa đổi các lớp hiện có &rarr; Các lớp cũ vẫn được biên dịch sẵn và không bị ảnh hưởng &rarr; Tránh được các rủi ro hồi quy (regression risk) &rarr; Hệ thống duy trì tính ổn định.

---

## Tại Sao Nguyên Tắc Thay Thế Liskov Thực Thi Các Hợp Đồng Hành Vi

Đa hình kiểu con (subtype polymorphism) trong Java cho phép một biến tham chiếu thuộc kiểu lớp cha hoặc kiểu interface trỏ tới bất kỳ thực thể lớp con nào. Nguyên tắc Thay thế Liskov (LSP) đảm bảo rằng sự thay thế này là an toàn bằng cách yêu cầu các lớp con phải tuân thủ hợp đồng hành vi (behavioral contract) được định nghĩa bởi lớp cha. Trong Java, mặc dù trình biên dịch thực thi an toàn kiểu tĩnh (chẳng hạn như chữ ký phương thức và tính hiệp biến của kiểu trả về), nó không thể thực thi các bất biến hành vi (behavioral invariant) tại thời điểm chạy. Các lớp con vi phạm LSP khi chúng tăng cường các điều kiện tiên quyết (ví dụ: ném ra một ngoại lệ checked mới hoặc yêu cầu các tham số đầu vào đáp ứng các ràng buộc chặt chẽ hơn) hoặc làm yếu đi các điều kiện sau (chẳng hạn như trả về một tham chiếu null khi hợp đồng lớp cha đảm bảo một đối tượng non-null, hoặc sửa đổi trạng thái kế thừa theo cách phá vỡ các bất biến của lớp cha). Khi các hợp đồng hành vi thời gian chạy này bị vi phạm, tính đa hình sẽ thất bại vì mã nguồn của client được thiết kế để hoạt động với lớp cha sẽ hành xử không thể đoán trước hoặc ném ra các ngoại lệ runtime khi gặp phải lớp con.

### Hợp đồng hành vi Liskov (Mental Model)
```text
Lớp cha (Hợp đồng: trả về số nguyên dương)
    [ MathHelper ] -> getValue() trả về >= 1

Lớp con A (Tuân thủ LSP)
    [ SecureHelper ] -> getValue() trả về >= 1 (Tuân thủ hợp đồng)

Lớp con B (Vi phạm LSP)
    [ BadHelper ] -> getValue() trả về 0 hoặc số âm (Vi phạm hợp đồng!)
    Client mong đợi số nguyên dương bị crash do chia cho 0!
```

### Ví Dụ Mã Nguồn
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
        verifyRectangle(new Square());    // Output: Expected Area: 50, Actual: 100 (LSP Violation!)
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả
Lớp con ghi đè phương thức lớp cha &rarr; Lớp con tăng cường điều kiện tiên quyết hoặc làm yếu đi điều kiện sau &rarr; Mã nguồn client giữ tham chiếu kiểu cha &rarr; Mã nguồn client thực thi phương thức lớp con qua điều phối động &rarr; Lớp con vi phạm các giả định hành vi của lớp cha &rarr; Xảy ra lỗi crash khi chạy hoặc logic bị sai lệch.

---

## Tại Sao Phân Tách Interface Ngăn Chặn Liên Kết Interface Phình To

Trong JVM, khi một lớp triển khai một interface, nó phải cung cấp các triển khai cụ thể cho tất cả các phương thức không phải default được định nghĩa bởi interface đó, hoặc nếu không thì phải được khai báo là abstract. Một interface "phình to" (fat interface) chứa các phương thức dành cho các client khác nhau, không liên quan sẽ buộc mọi lớp triển khai phải phụ thuộc vào và hiện thực hóa các phương thức mà nó không yêu cầu, thường dẫn đến các thân phương thức trống hoặc giả lập ném ra `UnsupportedOperationException`. Thiết kế này liên kết các thành phần không liên quan lại với nhau tại thời điểm biên dịch: nếu một chữ ký phương thức trong một interface phình to thay đổi, tất cả các lớp triển khai phải được biên dịch lại và liên kết lại bởi JVM, ngay cả khi chúng chưa từng gọi hoặc sử dụng phương thức đó. Bằng cách phân tách một interface cồng kềnh thành các interface nhỏ, phục vụ riêng cho từng client, chúng ta giảm thiểu kích thước tham chiếu bảng interface (`itable`) được phân giải trong các lời gọi `invokeinterface`. Do đó, các client chỉ phụ thuộc vào các phương thức cụ thể mà chúng thực sự thực thi, giúp loại bỏ các phụ thuộc không cần thiết tại thời điểm biên dịch, chi phí tải lớp và tính mong manh của mã nguồn thời gian chạy.

### Interface phình to so với Interface phân tách (Mental Model)
```text
Interface phình to (Liên kết các client không liên quan):
+-------------------------------+
|        MultiFunction          |
|  print(), scan(), fax()       |
+-------------------------------+
        ^               ^
        |               |
  SimplePrinter     SuperOfficeJet (Cần tất cả)
  (buộc phải ném UnsupportedOperationException đối với fax()!)

Các interface được phân tách (Tinh gọn, dành riêng cho client):
+-------------+   +-------------+
|   Printer   |   |   Scanner   |
|   print()   |   |   scan()    |
+-------------+   +-------------+
       ^                 ^
       |                 |
       +--- SimplePrinter+
```

### Ví Dụ Mã Nguồn
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

### Chuỗi Nguyên Nhân - Kết Quả
Interface phình to chứa các phương thức không liên quan &rarr; Các lớp triển khai bị buộc phải viết các triển khai trống/giả lập &rarr; Sửa đổi chữ ký phương thức không dùng &rarr; Biên dịch lại và liên kết lại tất cả các lớp triển khai &rarr; Tăng liên kết tại thời điểm biên dịch và rủi ro xảy ra các ngoại lệ khi chạy.

---

## Tại Sao Đảo Ngược Phụ Thuộc Giúp Tách Biệt Các Module

Nguyên tắc Đảo ngược Phụ thuộc (DIP) đảo ngược luồng phụ thuộc từ trên xuống truyền thống của các hệ thống phần mềm bằng cách tuyên bố rằng các module cấp cao không nên phụ thuộc vào các triển khai cụ thể cấp thấp. Dưới mô hình phụ thuộc trực tiếp, các mối quan hệ tại thời điểm biên dịch được liên kết trực tiếp với các lớp cụ thể, có nghĩa là các lớp cấp cao không thể biên dịch hoặc kiểm thử độc lập với các module cấp thấp như cơ sở dữ liệu hoặc các API bên ngoài. Bằng cách giới thiệu các interface làm các trừu tượng hóa ở giữa các tầng này, cả module cấp cao và cấp thấp đều phụ thuộc vào interface trừu tượng đó. Tại thời điểm biên dịch, lớp cấp cao hoàn toàn dựa vào kiểu interface, điều này được xác thực bởi trình kiểm tra kiểu tĩnh của Java. Tại thời điểm chạy, các triển khai cụ thể được tiêm vào lớp cấp cao bằng cách sử dụng Tiêm phụ thuộc (DI) qua hàm khởi tạo hoặc setter, và JVM sẽ phân giải các cuộc gọi phương thức động thông qua tính đa hình. Điều này tách biệt mối quan hệ tại thời điểm biên dịch, cho phép dễ dàng thay thế mock để kiểm thử đơn vị và cho phép các nhà phát triển hoán đổi các lớp cơ sở hạ tầng cấp thấp mà không làm thay đổi logic nghiệp vụ cốt lõi.

### Phụ thuộc trực tiếp so với Đảo ngược phụ thuộc (Mental Model)
```text
Phụ thuộc trực tiếp (Liên kết chặt chẽ):
[ Service cấp cao ] ---> [ MySQLDatabase cụ thể ]
(Service được viết cứng với MySQL; không thể test nếu không chạy database!)

Đảo ngược phụ thuộc (Liên kết lỏng):
[ Service cấp cao ] ---> [ Database (Interface) ]
                                   ^
                                   |
                       [ MySQLDatabase cụ thể ] hoặc [ MockDatabase ]
```

### Ví Dụ Mã Nguồn
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

### Chuỗi Nguyên Nhân - Kết Quả
Module cấp cao tham chiếu tới interface trừu tượng &rarr; Các triển khai cụ thể cấp thấp kế thừa cùng interface &rarr; Tiêm phụ thuộc cung cấp thực thể cụ thể tại thời điểm chạy &rarr; Tham chiếu tại thời điểm biên dịch vẫn liên kết với sự trừu tượng hóa &rarr; Các thay đổi ở cấp thấp không yêu cầu biên dịch lại mã nguồn cấp cao &rarr; Các thành phần hệ thống được liên kết lỏng và dễ kiểm thử.

## Liên Kết Tham Khảo (Reference Links)

- https://docs.oracle.com/javase/tutorial/java/concepts/
- https://docs.oracle.com/javase/specs/jls/se21/html/index.html
