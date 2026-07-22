# Các Thuật Ngữ Mẫu Thiết Kế Cơ Bản Thường Gặp Trong Java (Basic Design Patterns Commonly Seen in Java Terms)

Sử dụng tài liệu này khi một từ khóa trong lý thuyết có vẻ quá ngắn gọn. Mỗi thuật ngữ đều có định nghĩa, tầm quan trọng, điểm dễ gây nhầm lẫn và một ví dụ nhỏ đi kèm.

## Singleton (Mẫu Đơn Bản)

Singleton giới hạn một lớp chỉ có duy nhất một thực thể (instance) và cung cấp một điểm truy cập toàn cục đến thực thể đó.

- **Tầm quan trọng**: Hữu ích cho việc quản lý các tài nguyên chia sẻ (như bể kết nối cơ sở dữ liệu - database connection pools, trình quản lý bộ đệm - cache managers, hoặc các thiết lập cấu hình hệ thống) nơi mà việc tồn tại nhiều thực thể có thể dẫn đến trạng thái không nhất quán, sử dụng bộ nhớ quá mức, hoặc xung đột tài nguyên.
- **Điểm dễ nhầm lẫn**: Các lập trình viên thường triển khai Singleton theo kiểu nạp lười (lazy loading) mà không đồng bộ hóa (synchronization) đúng cách hoặc thiếu từ khóa `volatile`, dẫn đến tranh chấp điều kiện (race conditions) trong môi trường đa luồng. Ngoài ra, Singleton tạo ra liên kết chặt chẽ (tight coupling) và gây khó khăn cho việc viết kiểm thử đơn vị (unit testing) vì chúng giữ nguyên trạng thái qua các bài kiểm thử khác nhau.
- **Ví dụ nhỏ**:
  ```java
  public enum DatabaseConnectionPool {
      INSTANCE;
      public void connect() {
          System.out.println("Connected to Database.");
      }
  }
  ```

## Khóa kiểm tra kép (Double-Checked Locking - DCL)

Một mẫu thiết kế đồng thời (concurrent design pattern) nhằm giảm thiểu chi phí hiệu năng bằng cách chỉ thực hiện khóa đồng bộ khi việc khởi tạo thực sự cần thiết.

- **Tầm quan trọng**: Tối ưu hóa việc khởi tạo lười (lazy initialization) trong môi trường đa luồng. Nó thực hiện kiểm tra đầu tiên mà không dùng khóa, chỉ khóa đồng bộ nếu thực thể là null, tiếp theo là một lần kiểm tra thứ hai bên trong khối đồng bộ (synchronized block). Điều này đảm bảo rằng các luồng chỉ phải trả chi phí đồng bộ hóa một lần duy nhất.
- **Điểm dễ nhầm lẫn**: Quên khai báo biến thực thể là `volatile`. Nếu không có `volatile`, việc tái sắp xếp chỉ thị (instruction reordering) bởi trình biên dịch hoặc CPU có thể khiến một luồng đọc được một tham chiếu khác null trỏ đến một đối tượng mới chỉ được khởi tạo một phần, dẫn đến các lỗi không thể dự đoán trước.
- **Ví dụ nhỏ**:
  ```java
  public class DclService {
      private static volatile DclService instance;
      public static DclService getInstance() {
          if (instance == null) {
              synchronized (DclService.class) {
                  if (instance == null) {
                      instance = new DclService();
                  }
              }
          }
          return instance;
      }
  }
  ```

## Singleton kiểu Bill Pugh (Bill Pugh Singleton)

Một cách triển khai mẫu Singleton sử dụng một lớp trợ giúp tĩnh lồng nhau (nested static helper class) để đạt được việc khởi tạo lười an toàn luồng (thread-safe lazy initialization).

- **Tầm quan trọng**: Đạt được cùng mục tiêu với cơ chế khóa kiểm tra kép nhưng viết mã đơn giản hơn và không tốn chi phí đồng bộ hóa. Nó dựa trên sự đảm bảo của bộ nạp lớp (class loader) của JVM: một lớp lồng nhau sẽ không được nạp cho đến khi nó được tham chiếu một cách rõ ràng, và quá trình nạp lớp luôn là an toàn luồng.
- **Điểm dễ nhầm lẫn**: Lầm tưởng rằng việc nạp lớp bên ngoài sẽ tự động nạp lớp trợ giúp tĩnh bên trong. Trong Java, các lớp tĩnh lồng nhau chỉ được nạp lười khi được tham chiếu trực tiếp (ví dụ: khi gọi `SingletonHolder.INSTANCE`), chứ không phải khi lớp bên ngoài được nạp.
- **Ví dụ nhỏ**:
  ```java
  public class BillPughSingleton {
      private BillPughSingleton() {}
      private static class SingletonHolder {
          private static final BillPughSingleton INSTANCE = new BillPughSingleton();
      }
      public static BillPughSingleton getInstance() {
          return SingletonHolder.INSTANCE;
      }
  }
  ```

## Phương thức nhà máy (Factory Method)

Định nghĩa một giao diện (interface) để tạo một đối tượng, nhưng để các lớp con quyết định lớp cụ thể nào sẽ được khởi tạo.

- **Tầm quan trọng**: Tuân thủ Nguyên tắc đảo ngược phụ thuộc (Dependency Inversion Principle) bằng cách tách biệt mã nguồn của máy khách (client code) khỏi các triển khai cụ thể. Máy khách tương tác với các giao diện sản phẩm trừu tượng, cho phép thêm các sản phẩm mới mà không cần sửa đổi mã nguồn của máy khách.
- **Điểm dễ nhầm lẫn**: Nhầm lẫn giữa Factory Method với một lớp Nhà máy đơn giản (Simple Factory). Simple Factory là một lớp trợ giúp đơn lẻ có các phương thức tĩnh và các câu lệnh điều kiện (như `switch`), trong khi Factory Method dựa vào tính kế thừa và đa hình (polymorphic dispatch).
- **Ví dụ nhỏ**:
  ```java
  abstract class DocumentCreator {
      public void openDocument() {
          Document doc = createDocument();
          doc.open();
      }
      protected abstract Document createDocument();
  }
  ```

## Trình dựng (Builder)

Tách biệt quá trình xây dựng một đối tượng phức tạp khỏi biểu diễn của nó, cho phép xây dựng từng trường của đối tượng từng bước một.

- **Tầm quan trọng**: Giải quyết phản mẫu (anti-pattern) constructor hình kính thiên văn (telescoping constructor - quá nhiều hàm khởi tạo với các tham số tùy chọn khác nhau). Nó cung cấp một API dạng chuỗi lưu loát (fluent API) dễ đọc, ngăn ngừa các lỗi sai thứ tự tham số và cho phép đối tượng cuối cùng trở thành bất biến (immutable).
- **Điểm dễ nhầm lẫn**: Viết một Builder trả về một đối tượng khả biến (mutable), làm mất đi mục đích khởi tạo an toàn, hoặc không kiểm tra tính hợp lệ của các tham số bên trong phương thức `build()` trước khi thực hiện khởi tạo đối tượng thực tế.
- **Ví dụ nhỏ**:
  ```java
  User user = new User.Builder()
                      .username("jdoe")
                      .email("jdoe@example.com")
                      .age(28)
                      .build();
  ```

## Bộ điều hợp (Adapter)

Một mẫu thiết kế cấu trúc cho phép các đối tượng có giao diện không tương thích có thể cộng tác với nhau.

- **Tầm quan trọng**: Cho phép bạn tái sử dụng các lớp hiện có hoặc các thư viện bên thứ ba có giao diện không phù hợp với giao diện mà mã nguồn máy khách của bạn mong đợi, tránh việc phải viết lại hoặc sửa đổi mã nguồn cốt lõi của hệ thống.
- **Điểm dễ nhầm lẫn**: Nhầm lẫn giữa Adapter và Decorator. Adapter thay đổi giao diện của một đối tượng để làm cho nó tương thích, trong khi Decorator giữ nguyên giao diện gốc để bổ sung tính năng một cách động.
- **Ví dụ nhỏ**:
  ```java
  public class UsbToTypeCAdapter implements TypeC {
      private final LegacyUsbCable usbCable;
      public UsbToTypeCAdapter(LegacyUsbCable cable) { this.usbCable = cable; }
      public void connect() { usbCable.plugUsb(); }
  }
  ```

## Bộ trang trí (Decorator)

Bổ sung thêm các trách nhiệm và hành vi một cách động cho một đối tượng mà không làm thay đổi cấu trúc của đối tượng đó.

- **Tầm quan trọng**: Cung cấp một giải pháp thay thế linh hoạt cho việc kế thừa (subclassing) để mở rộng chức năng. Bạn có thể xếp chồng các bộ trang trí theo cách đệ quy để kết hợp nhiều hành vi lúc chạy (ví dụ: bọc `BufferedInputStream` xung quanh `FileInputStream` trong Java).
- **Điểm dễ nhầm lẫn**: Tạo ra quá nhiều lớp trang trí, khiến mã nguồn trở nên khó gỡ lỗi, hoặc triển khai các bộ trang trí vi phạm nguyên tắc đơn trách nhiệm (single responsibility principle).
- **Ví dụ nhỏ**:
  ```java
  Coffee sugarMilkCoffee = new SugarDecorator(new MilkDecorator(new SimpleCoffee()));
  ```

## Chiến lược (Strategy)

Định nghĩa một tập hợp các thuật toán có thể thay thế cho nhau và đóng gói mỗi thuật toán bên trong một lớp riêng biệt.

- **Tầm quan trọng**: Loại bỏ các cấu trúc mã điều kiện khổng lồ (như các khối `if-else` hoặc `switch` lớn) bằng cách ủy quyền lựa chọn thuật toán cho tính đa hình. Thuật toán có thể được chọn hoặc thay đổi linh hoạt ngay trong thời gian chạy.
- **Điểm dễ nhầm lẫn**: Nhầm lẫn giữa Strategy và State. Trong Strategy, thông thường máy khách sẽ chọn cấu hình thuật toán một cách thủ công, trong khi ở State, ngữ cảnh (context) sẽ tự động chuyển đổi từ lớp trạng thái này sang lớp trạng thái khác.
- **Ví dụ nhỏ**:
  ```java
  paymentProcessor.setStrategy(new PayPalPayment());
  paymentProcessor.pay(150);
  ```

## Người quan sát (Observer)

Định nghĩa một cơ chế đăng ký để thông báo cho nhiều đối tượng (observers) về bất kỳ sự kiện nào xảy ra đối với đối tượng mà họ đang quan sát (subject).

- **Tầm quan trọng**: Tách biệt subject khỏi các observer cụ thể, cho phép thực hiện các thông báo đẩy động và xây dựng kiến trúc hướng sự kiện (event-driven architectures). Nó được sử dụng rộng rãi trong các bộ lắng nghe sự kiện (event listeners) và lập trình phản ứng (reactive programming).
- **Điểm dễ nhầm lẫn**: Nghĩ rằng các observer luôn được thông báo một cách bất đồng bộ. Trong các mẫu GoF tiêu chuẩn, việc thông báo là một vòng lặp đồng bộ. Các observer nên đẩy các tác vụ nặng hoặc gây nghẽn (blocking) vào một nhóm luồng nền (background thread pool) để tránh làm nghẽn luồng xử lý của subject.
- **Ví dụ nhỏ**:
  ```java
  subject.addObserver(event -> System.out.println("Received: " + event));
  ```

## Kho lưu trữ (Repository)

Làm trung gian giữa các mô hình miền (domain models) và các tầng lưu trữ dữ liệu (data persistence layers) bằng cách sử dụng một giao diện giống như bộ sưu tập để truy cập các thực thể miền.

- **Tầm quan trọng**: Tách biệt logic nghiệp vụ khỏi các cơ sở dữ liệu lưu trữ hoặc API, coi kho lưu trữ dữ liệu như một bộ sưu tập trong bộ nhớ. Điều này giúp viết các bài kiểm thử đơn vị dễ dàng hơn bằng cách sử dụng các kho lưu trữ giả lập (mock repositories).
- **Điểm dễ nhầm lẫn**: Nhầm lẫn giữa Repository với DAO (Data Access Object). DAO ánh xạ trực tiếp đến các bảng/phép toán cơ sở dữ liệu, trong khi Repository hoạt động ở cấp độ miền với các Gốc tổng hợp (Aggregate Roots), thường sử dụng nhiều DAO bên dưới.
- **Ví dụ nhỏ**:
  ```java
  public interface OrderRepository {
      void save(Order order);
      Order findById(String id);
  }
  ```
