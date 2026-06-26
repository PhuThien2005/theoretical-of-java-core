# Thuật ngữ các Nguyên Tắc Thiết Kế Cơ Bản Thường Đi Kèm Với Java Core (Basic Design Principles Often Paired With Java Core Terms)

Sử dụng tài liệu này khi một từ ngữ trong lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều có ý nghĩa, tầm quan trọng, điểm dễ nhầm lẫn và một ví dụ nhỏ.

## SOLID

SOLID là tập hợp năm nguyên lý thiết kế hướng đối tượng (Đơn Trách Nhiệm - Single Responsibility, Đóng/Mở - Open/Closed, Thay thế Liskov - Liskov Substitution, Phân tách Giao diện - Interface Segregation, và Đảo ngược Phụ thuộc - Dependency Inversion) để xây dựng phần mềm dễ bảo trì và dễ mở rộng.

- **Tầm quan trọng**: Các nguyên lý SOLID ngăn chặn tính cứng nhắc, dễ vỡ, và khó di chuyển của mã nguồn. Chúng hướng dẫn lập trình viên cấu trúc các lớp và ranh giới sao cho việc thêm các tính năng mới không làm hỏng mã nguồn cũ đang chạy tốt.
- **Nhầm lẫn thường gặp**: Các nhà phát triển thường coi SOLID như một cuốn sách quy tắc cứng nhắc do trình biên dịch thực thi, dẫn đến việc thiết kế quá mức (over-engineering - ví dụ: tạo giao diện cho mọi lớp ngay cả khi chỉ có duy nhất một triển khai tồn tại). Chúng là các phương pháp phỏng đoán (heuristics) chứ không phải luật lệ.
- **Ví dụ nhỏ**: Khi thiết kế một hệ thống, thay vì tạo ra một lớp monolithic khổng lồ xử lý mọi thứ, hãy chia nhỏ nó thành các lớp nhỏ hơn giao tiếp thông qua các giao diện theo hướng dẫn của SOLID.

## Single Responsibility (Nguyên lý Đơn Trách Nhiệm)

Nguyên lý Đơn Trách Nhiệm (SRP) quy định rằng một lớp chỉ nên có một, và chỉ một, lý do duy nhất để thay đổi. Điều đó có nghĩa là một lớp phải tập trung vào một chức năng duy nhất.

- **Tầm quan trọng**: Nếu một lớp có nhiều trách nhiệm (ví dụ: phân tích truy vấn cơ sở dữ liệu và dựng giao diện HTML), những thay đổi đối với một trách nhiệm có thể làm hỏng mã xử lý trách nhiệm kia, liên kết chặt chẽ các thành phần không liên quan.
- **Nhầm lẫn thường gặp**: Tin rằng "một lý do để thay đổi" nghĩa là một lớp chỉ nên có một phương thức duy nhất. Một lớp có thể có nhiều phương thức miễn là chúng có tính liên kết cao và phục vụ cùng một trách nhiệm cốt lõi.
- **Ví dụ nhỏ**: Tách riêng việc lưu trữ cơ sở dữ liệu khỏi việc tuần hoàn hóa người dùng:
  ```java
  class User { String username; }
  class UserRepository { void save(User u) { /* database logic */ } }
  class UserSerializer { String toJson(User u) { return "{...}"; } }
  ```

## Open Closed (Nguyên lý Đóng/Mở)

Nguyên lý Đóng/Mở (OCP) tuyên bố rằng các thực thể phần mềm (lớp, mô-đun, hàm) nên mở cho việc mở rộng nhưng đóng cho việc sửa đổi.

- **Tầm quan trọng**: Nó giảm thiểu rủi ro đưa các lỗi hồi quy (regression bugs) vào mã nguồn đã được xác minh và kiểm thử. Thay vì chỉnh sửa các lớp đã được kiểm thử để thêm hành vi mới, bạn viết các lớp mới kế thừa (Inheritance) hoặc triển khai chúng.
- **Nhầm lẫn thường gặp**: Tin rằng OCP có nghĩa là bạn không bao giờ được chỉnh sửa một lớp sau khi đã viết xong. Việc sửa lỗi (bug fixes), tái cấu trúc (refactoring), và cải tiến mã nguồn luôn được khuyến khích; OCP đặc biệt nhắm mục tiêu ngăn chặn việc sửa đổi lớp hiện tại chỉ để hỗ trợ các tính năng mới.
- **Ví dụ nhỏ**: Dựa vào tính đa hình (Polymorphism) thay vì kiểm tra kiểu bằng if-else:
  ```java
  interface Shape { double getArea(); }
  class Circle implements Shape { 
      private double r;
      public double getArea() { return Math.PI * r * r; } 
  }
  class Square implements Shape { 
      private double s;
      public double getArea() { return s * s; } 
  }
  ```

## Liskov Substitution (Nguyên lý Thay thế Liskov)

Nguyên lý Thay thế Liskov (LSP) tuyên bố rằng các lớp con phải có khả năng thay thế cho các lớp cha hoặc giao diện của chúng mà không làm hỏng tính đúng đắn của chương trình.

- **Tầm quan trọng**: LSP bảo toàn tính an toàn kiểu và các kỳ vọng về hành vi tại thời điểm chạy. Khi mã của client sử dụng một tham chiếu của cha, nó giả định rằng kiểu con tuân thủ giao ước hành vi của cha (bất biến, điều kiện tiên quyết/sau đó).
- **Nhầm lẫn thường gặp**: Nghĩ rằng kế thừa hoàn toàn chỉ là về việc tái sử dụng mã nguồn. Kế thừa còn là một giao ước hành vi. Các lớp con không được thắt chặt các điều kiện tiên quyết (ví dụ: ném ra các ngoại lệ mới đối với đầu vào của lớp cha) hoặc làm suy yếu các điều kiện sau đó (postconditions).
- **Ví dụ nhỏ**: Một lớp con của một danh sách chỉ đọc (read-only list) ném ra `UnsupportedOperationException` trên một thao tác đọc, hoặc trả về null khi giao ước của cha đảm bảo trả về một danh sách, điều này vi phạm LSP.

## Interface Segregation (Nguyên lý Phân tách Giao diện)

Nguyên lý Phân tách Giao diện (ISP) tuyên bố rằng các client không nên bị buộc phải phụ thuộc vào các phương thức mà họ không sử dụng, chủ trương sử dụng nhiều giao diện nhỏ, cụ thể thay vì một giao diện cồng kềnh duy nhất.

- **Tầm quan trọng**: Giao diện béo (fat interfaces) liên kết các client không liên quan lại với nhau. Nếu một giao diện cồng kềnh thay đổi chữ ký phương thức, tất cả các lớp triển khai giao diện đó phải được biên dịch lại và liên kết lại, ngay cả khi chúng không sử dụng phương thức đó.
- **Nhầm lẫn thường gặp**: Phân tách các giao diện đến mức "bùng nổ giao diện", nơi mỗi phương thức đơn lẻ đều có giao diện riêng của nó. Các giao diện nên được chia nhỏ dựa trên ranh giới sử dụng thực tế của client.
- **Ví dụ nhỏ**: Chia nhỏ giao diện của một máy đa chức năng thành các giao diện nhỏ hơn:
  ```java
  interface Printer { void print(); }
  interface Scanner { void scan(); }
  class SimplePrinter implements Printer {
      public void print() { System.out.println("Printing..."); }
  }
  ```

## Dependency Inversion (Nguyên lý Đảo ngược Phụ thuộc)

Nguyên lý Đảo ngược Phụ thuộc (DIP) tuyên bố rằng các mô-đun cấp cao không nên phụ thuộc vào các mô-đun cấp thấp; cả hai nên phụ thuộc vào các trừu tượng (giao diện). Các trừu tượng không nên phụ thuộc vào các chi tiết; các chi tiết nên phụ thuộc vào các trừu tượng.

- **Tầm quan trọng**: Nó tách rời logic nghiệp vụ cốt lõi khỏi các chi tiết cấp thấp (cơ sở dữ liệu, API). Các thay đổi đối với công cụ cơ sở dữ liệu hoặc thư viện bên thứ ba sẽ không lan truyền ngược lên để sửa đổi hoặc làm hỏng logic cấp cao.
- **Nhầm lẫn thường gặp**: Nhầm lẫn giữa Đảo ngược Phụ thuộc (DIP) với Tiêm Phụ thuộc (DI). DIP là nguyên lý thiết kế khái niệm (lập trình hướng trừu tượng); DI là kỹ thuật triển khai được sử dụng để truyền các thực thể cụ thể vào.
- **Ví dụ nhỏ**: Một bộ xử lý đơn hàng phụ thuộc vào giao diện `PaymentGateway` thay vì lớp cụ thể `StripeGateway`:
  ```java
  interface PaymentGateway { void pay(double amount); }
  class OrderProcessor {
      private final PaymentGateway gateway;
      public OrderProcessor(PaymentGateway gateway) { this.gateway = gateway; }
  }
  ```

## DRY

DRY ("Đừng Lặp Lại Chính Mình" - Don't Repeat Yourself) tuyên bố rằng mỗi phần kiến thức hoặc logic của hệ thống phải có một biểu diễn duy nhất, không mơ hồ, có thẩm quyền duy nhất trong mã nguồn.

- **Tầm quan trọng**: Logic trùng lặp làm cho việc bảo trì mã nguồn trở thành một cơn ác mộng. Nếu một quy tắc nghiệp vụ hoặc xác thực thay đổi, bạn phải tìm và sửa đổi mọi bản sao trùng lặp, gây ra nguy cơ không nhất quán và lỗi.
- **Nhầm lẫn thường gặp**: Áp dụng DRY cho hai đoạn mã trông giống hệt nhau hôm nay nhưng lại phục vụ các khái niệm nghiệp vụ hoàn toàn khác nhau. Nếu các yêu cầu của chúng phân kỳ vào ngày mai, việc lạm dụng DRY sẽ tạo ra các phân nhánh điều kiện phức tạp.
- **Ví dụ nhỏ**: Trích xuất logic xác thực chung vào một phương thức tiện ích:
  ```java
  public class ValidationUtils {
      public static void checkEmail(String email) {
          if (email == null || !email.contains("@")) {
              throw new IllegalArgumentException("Invalid email");
          }
      }
  }
  ```

## KISS

KISS ("Giữ Cho Nó Đơn Giản, Thằng Ngốc" - Keep It Simple, Stupid) là một nguyên tắc thiết kế tuyên bố rằng mã nguồn và hệ thống nên được thiết kế đơn giản và trực tiếp nhất có thể, tránh thiết kế quá mức (over-engineering).

- **Tầm quan trọng**: Mã nguồn đơn giản sẽ dễ đọc, xác minh, gỡ lỗi, và bảo trì hơn. Các thiết kế phức tạp với phân cấp sâu hoặc các mô hình suy đoán trước (speculative patterns) sẽ đưa vào các lỗi tiềm ẩn và làm tăng tải nhận thức.
- **Nhầm lẫn thường gặp**: Đánh đồng sự đơn giản với mã nguồn cẩu thả hoặc lười biếng. Việc viết mã nguồn đơn giản, sạch sẽ thường đòi hỏi tính kỷ luật cao hơn, việc tái cấu trúc (refactoring) và suy nghĩ nhiều hơn là viết mã nguồn phức tạp, lồng nhau.
- **Ví dụ nhỏ**: Trả về các biểu thức boolean trực tiếp thay vì bọc chúng trong các điều kiện hoặc Optional không cần thiết:
  ```java
  // đơn giản và nhanh chóng
  return value > 0;
  ```

## YAGNI

YAGNI ("Bạn Sẽ Không Cần Đến Nó Đâu" - You Aren't Gonna Need It) là một nguyên tắc phát triển phần mềm tuyên bố rằng bạn không nên triển khai các tính năng hoặc cơ sở hạ tầng suy đoán trước cho đến khi chúng thực sự cần thiết.

- **Tầm quan trọng**: Nó tiết kiệm thời gian của nhà phát triển, tránh phình to mã nguồn, giảm diện tích bề mặt kiểm thử, và giữ cho thiết kế linh hoạt. Mã nguồn suy đoán trước thường trở nên không chính xác đối với các yêu cầu trong tương lai.
- **Nhầm lẫn thường gặp**: Nhầm lẫn YAGNI với việc bỏ qua quy hoạch kiến trúc cơ bản. Bạn vẫn nên viết mã sạch, có tính mô-đun, nhưng bạn không nên viết mã cho các tính năng giả thuyết, không được yêu cầu.
- **Ví dụ nhỏ**: Không xây dựng một lớp bộ đệm (cache) phức tạp, khung kiểm toán (auditing framework), hoặc cơ sở hạ tầng microservice ngay ngày đầu tiên dựa trên giả định rằng ứng dụng sẽ mở rộng lên hàng triệu người dùng.

## coupling (độ liên kết)

Coupling đo lường mức độ phụ thuộc lẫn nhau giữa các lớp hoặc mô-đun phần mềm khác nhau. Mục tiêu là liên kết lỏng lẻo (loose coupling).

- **Tầm quan trọng**: Liên kết chặt chẽ (high coupling) làm cho hệ thống trở nên dễ vỡ và cứng nhắc. Việc sửa đổi một lớp hoặc lược đồ cơ sở dữ liệu sẽ lan truyền khắp mã nguồn, làm hỏng các tính năng không liên quan và đòi hỏi tái cấu trúc quy mô lớn.
- **Nhầm lẫn thường gặp**: Nghĩ rằng liên kết lỏng lẻo nghĩa là không có mối quan hệ nào giữa các lớp. Các lớp bắt buộc phải tương tác. Liên kết lỏng lẻo nghĩa là chúng tương tác thông qua các giao diện công khai, ổn định trong khi vẫn ẩn đi các triển khai nội bộ của mình.
- **Ví dụ nhỏ**: Khai báo các phụ thuộc dưới dạng các kiểu giao diện và tiêm (inject) chúng vào, thay vì viết cứng việc khởi tạo lớp ở bên trong.

## cohesion (độ gắn kết)

Cohesion đo lường mức độ tập trung, liên quan, và thống nhất của các phần tử, các trường, và các phương thức bên trong một lớp hoặc mô-đun duy nhất. Mục tiêu là độ gắn kết cao (high cohesion).

- **Tầm quan trọng**: Độ gắn kết cao làm cho các lớp dễ hiểu, dễ kiểm thử, dễ tái sử dụng, và dễ gỡ lỗi hơn. Khi một lớp có độ gắn kết thấp (thực hiện nhiều việc không liên quan), nó tích tụ sự phình to, gây khó khăn cho việc bảo trì.
- **Nhầm lẫn thường gặp**: Nghĩ rằng độ gắn kết cao nghĩa là có càng ít phương thức càng tốt. Một lớp có thể có nhiều phương thức nếu tất cả chúng cùng điều phối để thực hiện một trách nhiệm logic duy nhất.
- **Ví dụ nhỏ**: Một lớp `EmailService` chỉ xử lý việc định dạng và gửi email là có tính gắn kết cao. Một lớp `UserManager` vừa băm mật khẩu, truy vấn cơ sở dữ liệu, định dạng HTML, vừa gửi email là có tính gắn kết thấp.

## dependency injection (tiêm phụ thuộc)

Tiêm Phụ thuộc (DI) là một kỹ thuật trong đó các phụ thuộc của một đối tượng được cung cấp bởi các đối tượng gọi bên ngoài thay vì được tạo ra nội bộ bởi chính đối tượng đó.

- **Tầm quan trọng**: Nó tách rời việc tạo đối tượng khỏi hành vi của đối tượng. Nó giúp việc kiểm thử đơn vị trở nên đơn giản vì các phụ thuộc giả lập (mock hoặc stub) có thể dễ dàng được truyền vào hàm khởi tạo.
- **Nhầm lẫn thường gặp**: Nghĩ rằng Tiêm Phụ thuộc yêu cầu một khung làm việc như Spring hoặc Guice. DI là một mẫu lập trình đơn giản (ví dụ: truyền đối số vào một hàm khởi tạo) có thể được thực hiện hoàn toàn bằng Java thuần túy.
- **Ví dụ nhỏ**: Truyền một `Engine` vào hàm khởi tạo của `Car`:
  ```java
  public class Car {
      private final Engine engine;
      public Car(Engine engine) { this.engine = engine; }
  }
  ```
