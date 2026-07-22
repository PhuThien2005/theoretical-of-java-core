# Thuật Ngữ Các Nguyên Tắc Thiết Kế Cơ Bản Thường Đi Kèm Với Java Core (Basic Design Principles Terms)

Sử dụng file này khi một từ trong phần lý thuyết có cảm giác quá ngắn gọn. Mỗi thuật ngữ đều đi kèm với ý nghĩa, tầm quan trọng, điểm dễ gây nhầm lẫn và một ví dụ nhỏ.

## SOLID

SOLID là một tập hợp gồm năm nguyên tắc thiết kế hướng đối tượng (Single Responsibility - Đơn trách nhiệm, Open/Closed - Đóng/Mở, Liskov Substitution - Thay thế Liskov, Interface Segregation - Phân tách Interface, và Dependency Inversion - Đảo ngược Phụ thuộc) nhằm xây dựng phần mềm dễ bảo trì và dễ mở rộng.

- **Tại sao điều này quan trọng**: Các nguyên tắc SOLID giúp ngăn chặn tính cứng nhắc (rigidity), dễ gãy (fragility) và bất động (immobility) của mã nguồn. Chúng hướng dẫn các nhà phát triển trong việc cấu trúc các lớp và ranh giới sao cho việc thêm các tính năng mới không làm hỏng mã nguồn đang hoạt động hiện có.
- **Nhầm lẫn phổ biến**: Các nhà phát triển thường coi SOLID như một bộ quy tắc cứng nhắc, được thực thi bởi trình biên dịch, dẫn đến việc kỹ nghệ hóa quá mức (ví dụ: tạo interface cho mỗi lớp ngay cả khi chỉ có một triển khai duy nhất). Chúng là các phương pháp kinh nghiệm (heuristic), không phải là luật lệ.
- **Ví dụ nhỏ**: Khi thiết kế một hệ thống, thay vì tạo ra một lớp monolithic khổng lồ xử lý mọi thứ, hãy chia nhỏ nó thành các lớp nhỏ hơn giao tiếp qua các interface theo hướng dẫn của SOLID.

## Đơn Trách Nhiệm (Single Responsibility)

Nguyên tắc Đơn trách nhiệm (SRP) quy định rằng một lớp chỉ nên có một, và chỉ một, lý do để thay đổi. Điều này có nghĩa là một lớp phải tập trung vào một phần chức năng duy nhất.

- **Tại sao điều này quan trọng**: Nếu một lớp gánh vác nhiều trách nhiệm (ví dụ: phân tích cú pháp truy vấn cơ sở dữ liệu và kết xuất HTML), những thay đổi đối với một trách nhiệm có thể làm hỏng mã nguồn xử lý trách nhiệm kia, liên kết chặt chẽ các thành phần không liên quan.
- **Nhầm lẫn phổ biến**: Tin rằng "một lý do để thay đổi" nghĩa là một lớp chỉ nên có một phương thức duy nhất. Một lớp có thể có nhiều phương thức miễn là chúng đều có tính gắn kết cao và phục vụ cùng một trách nhiệm cốt lõi.
- **Ví dụ nhỏ**: Tách biệt việc lưu trữ cơ sở dữ liệu khỏi việc tuần tự hóa người dùng:
  ```java
  class User { String username; }
  class UserRepository { void save(User u) { /* database logic */ } }
  class UserSerializer { String toJson(User u) { return "{...}"; } }
  ```

## Đóng Mở (Open Closed)

Nguyên tắc Đóng/Mở (OCP) phát biểu rằng các thực thể phần mềm (lớp, module, hàm) nên mở rộng cho việc phát triển nhưng đóng lại cho việc sửa đổi.

- **Tại sao điều này quan trọng**: Nó giảm thiểu rủi ro đưa các lỗi hồi quy (regression bug) vào mã nguồn đã được xác thực, kiểm thử. Thay vì chỉnh sửa các lớp đã được kiểm thử để thêm hành vi, bạn viết các lớp mới kế thừa hoặc triển khai them.
- **Nhầm lẫn phổ biến**: Tin rằng OCP có nghĩa là bạn không bao giờ có thể chỉnh sửa một lớp sau khi đã viết xong. Các hoạt động sửa lỗi (bug fix), tái cấu trúc (refactoring) và cải tiến mã nguồn luôn được khuyến khích; OCP nhắm mục tiêu cụ thể vào việc ngăn chặn sửa đổi lớp để hỗ trợ các tính năng mới.
- **Ví dụ nhỏ**: Dựa vào tính đa hình thay vì kiểm tra kiểu dữ liệu bằng if-else:
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

## Thay Thế Liskov (Liskov Substitution)

Nguyên tắc Thay thế Liskov (LSP) phát biểu rằng các lớp con phải có khả năng thay thế cho các lớp cha hoặc interface của chúng mà không làm hỏng tính đúng đắn của chương trình.

- **Tại sao điều này quan trọng**: LSP bảo toàn an toàn kiểu và các kỳ vọng về hành vi tại thời điểm chạy. Khi mã nguồn client sử dụng một tham chiếu kiểu cha, nó giả định rằng kiểu con tuân thủ hợp đồng hành vi của cha (các bất biến, điều kiện tiên quyết/điều kiện sau).
- **Nhầm lẫn phổ biến**: Nghĩ rằng kế thừa đơn thuần chỉ là để tái sử dụng mã nguồn. Kế thừa cũng là một hợp đồng hành vi. Các lớp con không được tăng cường các điều kiện tiên quyết (ví dụ: ném ra ngoại lệ mới đối với các đầu vào của cha) hoặc làm yếu đi các điều kiện sau.
- **Ví dụ nhỏ**: Một lớp con của một danh sách chỉ đọc (read-only list) ném ra `UnsupportedOperationException` đối với một hoạt động đọc, hoặc trả về null khi hợp đồng cha đảm bảo trả về một danh sách, là vi phạm LSP.

## Phân Tách Interface (Interface Segregation)

Nguyên tắc Phân tách Interface (ISP) phát biểu rằng các client không nên bị buộc phải phụ thuộc vào các phương thức mà chúng không sử dụng, ủng hộ việc sử dụng nhiều interface nhỏ, chuyên biệt hơn là một interface phình to duy nhất.

- **Tại sao điều này quan trọng**: Các interface phình to liên kết các client không liên quan lại với nhau. Nếu một interface phình to thay đổi một chữ ký phương thức, tất cả các lớp triển khai interface đó phải được biên dịch lại và liên kết lại, ngay cả khi chúng không sử dụng phương thức đó.
- **Nhầm lẫn phổ biến**: Phân tách các interface đến mức "bùng nổ interface", nơi mỗi phương thức đơn lẻ đều có interface riêng của nó. Các interface nên được chia nhỏ dựa trên các ranh giới sử dụng thực tế của client.
- **Ví dụ nhỏ**: Chia nhỏ một interface máy đa chức năng thành các interface nhỏ hơn:
  ```java
  interface Printer { void print(); }
  interface Scanner { void scan(); }
  class SimplePrinter implements Printer {
      public void print() { System.out.println("Printing..."); }
  }
  ```

## Đảo Ngược Phụ Thuộc (Dependency Inversion)

Nguyên tắc Đảo ngược Phụ thuộc (DIP) phát biểu rằng các module cấp cao không nên phụ thuộc vào các module cấp thấp; cả hai nên phụ thuộc vào các trừu tượng (interface). Các trừu tượng không nên phụ thuộc vào các chi tiết; các chi tiết nên phụ thuộc vào các trừu tượng.

- **Tại sao điều này quan trọng**: Nó tách biệt logic nghiệp vụ cốt lõi khỏi các chi tiết cấp thấp (cơ sở dữ liệu, API). Các thay đổi đối với công cụ cơ sở dữ liệu hoặc các thư viện bên thứ ba sẽ không lan rộng lên trên để sửa đổi hoặc làm hỏng logic cấp cao.
- **Nhầm lẫn phổ biến**: Lẫn lộn giữa Đảo ngược Phụ thuộc (DIP) với Tiêm Phụ thuộc (DI). DIP là nguyên tắc thiết kế mang tính khái niệm (lập trình hướng tới trừu tượng); DI là kỹ thuật triển khai được sử dụng để truyền các thực thể cụ thể vào.
- **Ví dụ nhỏ**: Một bộ xử lý đơn hàng phụ thuộc vào một interface `PaymentGateway` thay vì một lớp `StripeGateway` cụ thể:
  ```java
  interface PaymentGateway { void pay(double amount); }
  class OrderProcessor {
      private final PaymentGateway gateway;
      public OrderProcessor(PaymentGateway gateway) { this.gateway = gateway; }
  }
  ```

## DRY

DRY ("Don't Repeat Yourself" - Đừng lặp lại chính mình) quy định rằng mỗi phần tri thức hoặc logic của hệ thống phải có một biểu diễn duy nhất, không mơ hồ và có thẩm quyền trong mã nguồn.

- **Tại sao điều này quan trọng**: Logic trùng lặp làm cho việc bảo trì mã nguồn trở thành một cơn ác mộng. Nếu một quy tắc nghiệp vụ hoặc xác thực thay đổi, bạn phải tìm và sửa đổi từng bản sao trùng lặp, gây ra nguy cơ không nhất quán và xảy ra lỗi.
- **Nhầm lẫn phổ biến**: Áp dụng DRY cho hai đoạn mã trông giống hệt nhau ở hiện tại nhưng phục vụ các khái niệm nghiệp vụ hoàn toàn khác nhau. Nếu các yêu cầu của chúng phân kỳ vào ngày mai, việc lạm dụng DRY sẽ tạo ra các nhánh điều kiện phức tạp.
- **Ví dụ nhỏ**: Tách biệt logic xác thực chung vào một phương thức tiện ích:
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

KISS ("Keep It Simple, Stupid" - Giữ mọi thứ đơn giản) là một nguyên tắc thiết kế phát biểu rằng mã nguồn và hệ thống nên được thiết kế đơn giản và trực tiếp nhất có thể, tránh kỹ nghệ hóa quá mức.

- **Tại sao điều này quan trọng**: Mã nguồn đơn giản dễ đọc, xác thực, gỡ lỗi và bảo trì hơn. Các thiết kế phức tạp với phân cấp sâu hoặc các mẫu thiết kế suy đoán sẽ đưa vào các lỗi ẩn và tăng tải nhận thức.
- **Nhầm lẫn phổ biến**: Đánh đồng sự đơn giản với mã nguồn thô sơ hoặc cẩu thả. Việc viết mã nguồn đơn giản, sạch sẽ thường đòi hỏi tính kỷ luật cao hơn, tái cấu trúc nhiều hơn và suy nghĩ thấu đáo hơn việc viết mã phức tạp, lồng nhau.
- **Ví dụ nhỏ**: Trả về các biểu thức boolean trực tiếp thay vì bọc chúng trong các điều kiện hoặc Optional không cần thiết:
  ```java
  // đơn giản và nhanh chóng
  return value > 0;
  ```

## YAGNI

YAGNI ("You Aren't Gonna Need It" - Bạn chưa cần đến nó đâu) là một nguyên tắc phát triển phần mềm phát biểu rằng bạn không nên triển khai các tính năng hoặc cơ sở hạ tầng suy đoán cho đến khi chúng thực sự cần thiết.

- **Tại sao điều này quan trọng**: Nó tiết kiệm thời gian của nhà phát triển, tránh phình to mã nguồn, giảm diện tích bề mặt kiểm thử và giữ cho thiết kế linh hoạt. Mã nguồn suy đoán thường tỏ ra sai lầm đối với các yêu cầu trong tương lai.
- **Nhầm lẫn phổ biến**: Nhầm lẫn YAGNI với việc bỏ qua lập kế hoạch kiến trúc cơ bản. Bạn vẫn nên viết mã nguồn sạch, có tính module, nhưng bạn không nên viết mã cho các tính năng giả định chưa được yêu cầu.
- **Ví dụ nhỏ**: Không xây dựng một tầng lưu bộ nhớ đệm (caching) phức tạp, bộ khung kiểm toán (auditing) hoặc cơ sở hạ tầng microservice ngay từ ngày đầu tiên dựa trên giả định rằng ứng dụng sẽ mở rộng tới hàng triệu người dùng.

## Liên Kết (Coupling)

Liên kết (coupling) đo lường mức độ phụ thuộc lẫn nhau giữa các lớp hoặc module phần mềm khác nhau. Mục tiêu là liên kết lỏng (loose coupling).

- **Tại sao điều này quan trọng**: Liên kết cao làm cho hệ thống trở nên dễ gãy và cứng nhắc. Việc sửa đổi một lớp hoặc lược đồ cơ sở dữ liệu sẽ lan truyền qua mã nguồn, làm hỏng các tính năng không liên quan và đòi hỏi tái cấu trúc khổng lồ.
- **Nhầm lẫn phổ biến**: Nghĩ rằng liên kết lỏng có nghĩa là không có mối quan hệ nào giữa các lớp. Các lớp bắt buộc phải tương tác. Liên kết lỏng có nghĩa là chúng tương tác thông qua các interface công khai, ổn định trong khi vẫn ẩn đi các triển khai nội bộ.
- **Ví dụ nhỏ**: Khai báo các phụ thuộc dưới dạng kiểu interface và tiêm chúng vào, thay vì viết cứng việc khởi tạo lớp ở bên trong.

## Gắn Kết (Cohesion)

Gắn kết (cohesion) đo lường mức độ tập trung, liên quan và thống nhất của các phần tử, trường dữ liệu và phương thức bên trong một lớp hoặc module duy nhất. Mục tiêu là gắn kết cao (high cohesion).

- **Tại sao điều này quan trọng**: Gắn kết cao giúp các lớp dễ hiểu, dễ kiểm thử, tái sử dụng và gỡ lỗi hơn. Khi một lớp có tính gắn kết thấp (làm những việc không liên quan), nó sẽ tích lũy sự phình to, khiến việc bảo trì trở nên khó khăn.
- **Nhầm lẫn phổ biến**: Nghĩ rằng gắn kết cao có nghĩa là có ít phương thức nhất có thể. Một lớp có thể có nhiều phương thức nếu chúng cùng phối hợp để thực hiện một trách nhiệm logic duy nhất.
- **Ví dụ nhỏ**: Lớp `EmailService` chỉ xử lý định dạng và gửi email là có tính gắn kết cao. Lớp `UserManager` vừa băm mật khẩu, truy vấn cơ sở dữ liệu, định dạng HTML và gửi email là có tính gắn kết thấp.

## Tiêm Phụ Thuộc (Dependency Injection)

Tiêm phụ thuộc (Dependency Injection - DI) là một kỹ thuật trong đó các phụ thuộc của một đối tượng được cung cấp bởi các bên gọi bên ngoài thay vì được tạo ra nội bộ bởi chính đối tượng đó.

- **Tại sao điều này quan trọng**: Nó tách biệt việc tạo đối tượng khỏi hành vi đối tượng. Nó giúp việc kiểm thử đơn vị trở nên cực kỳ đơn giản vì các phụ thuộc mock hoặc stub có thể dễ dàng được truyền vào hàm khởi tạo.
- **Nhầm lẫn phổ biến**: Nghĩ rằng Tiêm phụ thuộc yêu cầu một framework như Spring hoặc Guice. DI là một mẫu lập trình đơn giản (ví dụ: truyền đối số vào một hàm khởi tạo) có thể được thực hiện hoàn toàn bằng Java thuần túy.
- **Ví dụ nhỏ**: Truyền một `Engine` vào hàm khởi tạo `Car`:
  ```java
  public class Car {
      private final Engine engine;
      public Car(Engine engine) { this.engine = engine; }
  }
  ```
