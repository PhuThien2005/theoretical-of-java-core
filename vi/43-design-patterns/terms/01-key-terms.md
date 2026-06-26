# Thuật ngữ các mẫu thiết kế cơ bản thường gặp trong Java (Basic Design Patterns Commonly Seen in Java Terms)

Hãy sử dụng tài liệu này khi một từ ngữ trong phần lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều đi kèm ý nghĩa, tầm quan trọng, sự nhầm lẫn phổ biến và một ví dụ nhỏ.

## Singleton (Singleton)

Singleton giới hạn một lớp chỉ có duy nhất một thực thể (instance) và cung cấp một điểm truy cập toàn cục đến thực thể đó.

- **Tại sao nó quan trọng**: Nó hữu ích để quản lý các tài nguyên dùng chung (như nhóm kết nối cơ sở dữ liệu - connection pool, trình quản lý bộ nhớ đệm cache, hoặc các cài đặt cấu hình hệ thống) nơi việc tồn tại nhiều thực thể sẽ dẫn đến trạng thái không nhất quán, tiêu tốn quá nhiều bộ nhớ hoặc xung đột tài nguyên.
- **Sự nhầm lẫn phổ biến**: Các lập trình viên thường triển khai Singleton tải lười biếng (lazy-loaded) mà không đồng bộ hóa (synchronization) đúng cách hoặc thiếu từ khóa `volatile`, dẫn đến các điều kiện chạy đua (race conditions). Ngoài ra, Singleton tạo ra độ liên kết chặt chẽ (tight coupling) và gây khó khăn cho việc viết kiểm thử đơn vị (unit testing) vì nó giữ nguyên trạng thái qua các bài test.
- **Ví dụ nhỏ**:
  ```java
  public enum DatabaseConnectionPool {
      INSTANCE;
      public void connect() {
          System.out.println("Connected to Database.");
      }
  }
  ```

## Khóa kiểm tra hai lần (Double-Checked Locking - DCL)

Một mẫu thiết kế đồng thời (concurrent design pattern) được thiết kế nhằm giảm thiểu chi phí đồng bộ hóa bằng cách chỉ lấy khóa khi việc khởi tạo thực sự cần thiết.

- **Tại sao nó quan trọng**: Nó tối ưu hóa quá trình khởi tạo lười biếng (lazy initialization) trong môi trường đa luồng. Nó thực hiện kiểm tra mà không dùng khóa, và chỉ khóa nếu thực thể là null, tiếp theo là một lần kiểm tra thứ hai bên trong khối đồng bộ hóa `synchronized`. Điều này đảm bảo các luồng chỉ phải chịu chi phí đồng bộ hóa một lần duy nhất.
- **Sự nhầm lẫn phổ biến**: Quên khai báo biến thực thể với từ khóa `volatile`. Không có `volatile`, việc tái sắp xếp lệnh (instruction reordering) do trình biên dịch hoặc CPU có thể khiến một luồng đọc một tham chiếu non-null trỏ đến một đối tượng mới chỉ được xây dựng một phần, dẫn đến các lỗi không thể dự đoán.
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

Một cách triển khai mẫu Singleton sử dụng một lớp tiện ích tĩnh nội bộ (nested static helper class) để đạt được quá trình khởi tạo lười biếng (lazy initialization) an toàn đa luồng.

- **Tại sao nó quan trọng**: Nó đạt được cùng mục tiêu như khóa kiểm tra hai lần nhưng với mã nguồn đơn giản hơn và hoàn toàn không tốn chi phí đồng bộ hóa. Nó dựa vào cơ chế đảm bảo nạp lớp (class loading) của JVM: một lớp nội bộ sẽ không được nạp cho đến khi nó được tham chiếu một cách rõ ràng, và việc nạp lớp là an toàn đa luồng (thread-safe).
- **Sự nhầm lẫn phổ biến**: Nghĩ rằng việc nạp lớp bên ngoài sẽ tự động nạp lớp tiện ích tĩnh nội bộ. Trong Java, các lớp tĩnh nội bộ được nạp lười biếng chỉ khi được tham chiếu (chẳng hạn như gọi `SingletonHolder.INSTANCE`), chứ không phải khi lớp bên ngoài được nạp.
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

## Factory Method (Factory Method)

Định nghĩa một interface để tạo đối tượng, nhưng để các lớp con quyết định lớp cụ thể nào sẽ được khởi tạo.

- **Tại sao nó quan trọng**: Nó tuân thủ Nguyên tắc đảo ngược phụ thuộc (DIP) bằng cách tách rời mã client khỏi các lớp triển khai cụ thể. Client tương tác với các interface sản phẩm trừu tượng, cho phép bổ sung các sản phẩm mới mà không cần sửa đổi mã client.
- **Sự nhầm lẫn phổ biến**: Nhầm lẫn Factory Method với một lớp Simple Factory (Nhà máy đơn giản). Simple Factory là một lớp tiện ích đơn lẻ với các phương thức tĩnh và các câu lệnh điều kiện (như `switch` hoặc `if-else`), trong khi Factory Method dựa vào kế thừa lớp và phân phát đa hình (Polymorphism) (polymorphic dispatch).
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

## Builder (Builder)

Tách biệt quá trình xây dựng một đối tượng phức tạp khỏi biểu diễn của nó, cho phép xây dựng từng bước các trường dữ liệu.

- **Tại sao nó quan trọng**: Nó giải quyết anti-pattern hàm khởi tạo phình to (telescoping constructor anti-pattern - các hàm khởi tạo có quá nhiều tham số tùy chọn). Nó cung cấp một API trôi chảy (fluent API) dễ đọc, ngăn ngừa lỗi thứ tự tham số và cho phép đối tượng cuối cùng là bất biến (immutable).
- **Sự nhầm lẫn phổ biến**: Viết một Builder trả về một đối tượng khả biến (mutable), làm mất đi mục đích của việc tạo đối tượng an toàn, hoặc không kiểm thực (validate) các tham số bên trong phương thức `build()` trước khi khởi tạo thực tế.
- **Ví dụ nhỏ**:
  ```java
  User user = new User.Builder()
                      .username("jdoe")
                      .email("jdoe@example.com")
                      .age(28)
                      .build();
  ```

## Adapter (Adapter)

Một mẫu thiết kế cấu trúc cho phép các đối tượng có giao diện (interfaces) không tương thích có thể cộng tác với nhau.

- **Tại sao nó quan trọng**: Nó cho phép bạn tái sử dụng các lớp hiện có hoặc các thư viện của bên thứ ba không khớp với interface mà mã client của bạn mong đợi, tránh việc phải viết lại hoặc sửa đổi mã nguồn cốt lõi.
- **Sự nhầm lẫn phổ biến**: Nhầm lẫn Adapter với Decorator. Adapter thay đổi interface của một đối tượng để làm cho nó tương thích, trong khi Decorator duy trì cùng một interface để bổ sung chức năng một cách năng động.
- **Ví dụ nhỏ**:
  ```java
  public class UsbToTypeCAdapter implements TypeC {
      private final LegacyUsbCable usbCable;
      public UsbToTypeCAdapter(LegacyUsbCable cable) { this.usbCable = cable; }
      public void connect() { usbCable.plugUsb(); }
  }
  ```

## Decorator (Decorator)

Bổ sung các trách nhiệm và hành vi bổ sung cho một đối tượng một cách năng động mà không làm thay đổi cấu trúc của nó.

- **Tại sao nó quan trọng**: Nó cung cấp một giải pháp thay thế linh hoạt cho kế thừa lớp để mở rộng chức năng. Bạn có thể xếp chồng các decorator một cách đệ quy để kết hợp nhiều hành vi tại thời điểm chạy (chẳng hạn như bọc `BufferedInputStream` của Java xung quanh một `FileInputStream`).
- **Sự nhầm lẫn phổ biến**: Tạo ra quá nhiều lớp decorator chồng chéo, khiến mã nguồn khó debug, hoặc triển khai các decorator vi phạm nguyên tắc đơn trách nhiệm.
- **Ví dụ nhỏ**:
  ```java
  Coffee sugarMilkCoffee = new SugarDecorator(new MilkDecorator(new SimpleCoffee()));
  ```

## Strategy (Strategy)

Định nghĩa một họ các thuật toán có thể hoán đổi cho nhau và bao đóng mỗi thuật toán đó bên trong một lớp riêng biệt.

- **Tại sao nó quan trọng**: Nó loại bỏ các cấu trúc mã điều kiện khổng lồ (như các khối `if-else` hoặc `switch` lớn) bằng cách ủy quyền lựa chọn thuật toán cho tính đa hình. Thuật toán có thể được chọn hoặc thay đổi tại thời điểm chạy.
- **Sự nhầm lẫn phổ biến**: Nhầm lẫn Strategy với State. Trong Strategy, client thường chọn cấu hình thuật toán một cách thủ công, trong khi trong State, ngữ cảnh (context) tự động chuyển đổi từ lớp trạng thái này sang lớp trạng thái khác.
- **Ví dụ nhỏ**:
  ```java
  paymentProcessor.setStrategy(new PayPalPayment());
  paymentProcessor.pay(150);
  ```

## Observer (Observer)

Định nghĩa một cơ chế đăng ký để thông báo cho nhiều đối tượng (observers) về bất kỳ sự kiện nào xảy ra đối với đối tượng mà họ đang quan sát (chủ thể - subject).

- **Tại sao nó quan trọng**: Nó tách rời chủ thể khỏi các observer cụ thể, cho phép thông báo đẩy năng động và kiến trúc hướng sự kiện. Nó được sử dụng nhiều trong các trình lắng nghe sự kiện (event listeners) và lập trình phản ứng (reactive programming).
- **Sự nhầm lẫn phổ biến**: Tin rằng các observer luôn được thông báo bất đồng bộ. Trong các mẫu thiết kế GoF tiêu chuẩn, việc thông báo là một vòng lặp đồng bộ. Các observer nên chuyển các tác vụ nặng hoặc chặn (blocking) sang một nhóm luồng nền để tránh làm chặn luồng của chủ thể.
- **Ví dụ nhỏ**:
  ```java
  subject.addObserver(event -> System.out.println("Received: " + event));
  ```

## Repository (Repository)

Làm trung gian giữa các mô hình miền (domain models) và các tầng lưu trữ dữ liệu (data persistence layers) bằng cách sử dụng giao diện giống như một bộ sưu tập (collection-like interface) để truy cập các thực thể miền.

- **Tại sao nó quan trọng**: Nó tách rời logic nghiệp vụ khỏi các cơ sở dữ liệu lưu trữ hoặc API, xử lý kho lưu trữ dữ liệu như một bộ sưu tập trong bộ nhớ. Điều này giúp dễ dàng viết các bài kiểm thử đơn vị bằng cách sử dụng các repository giả lập (mock repositories).
- **Sự nhầm lẫn phổ biến**: Nhầm lẫn Repository với DAO (Data Access Object). DAO ánh xạ chặt chẽ với các bảng/hoạt động cơ sở dữ liệu, trong khi Repository hoạt động ở cấp độ miền với các Gốc tổng hợp (Aggregate Roots), thường sử dụng nhiều DAO bên dưới.
- **Ví dụ nhỏ**:
  ```java
  public interface OrderRepository {
      void save(Order order);
      Order findById(String id);
  }
  ```
