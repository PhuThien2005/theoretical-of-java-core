# Thuật ngữ về Generics (Generics Terms)

Sử dụng tài liệu này khi một từ khóa trong phần lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều có định nghĩa, tầm quan trọng, hiểu lầm thường gặp và một ví dụ nhỏ.

## tham số kiểu (type parameter)

Một tên giữ chỗ (chẳng hạn như `T`, `E`, `K`, `V`) được khai báo trên các lớp, giao diện, hoặc phương thức generic. Nó được thay thế bằng một đối số kiểu cụ thể khi lớp được khởi tạo hoặc phương thức được gọi, cho phép cùng một đoạn mã hoạt động với các kiểu dữ liệu khác nhau trong khi vẫn bảo toàn tính an toàn kiểu.

* **Tầm quan trọng**: Nó cho phép trình biên dịch thực thi các ràng buộc kiểu tại thời điểm biên dịch (ví dụ: đảm bảo một `List<String>` chỉ chứa các chuỗi) mà không yêu cầu nhà phát triển phải ép kiểu rõ ràng, ngăn chặn các ngoại lệ tại thời điểm chạy.
* **Hiểu lầm thường gặp**: Nhầm lẫn *tham số kiểu (type parameter)* với *đối số kiểu (type argument)*. Tham số kiểu là tên giữ chỗ được khai báo trong chữ ký lớp (như `T` trong `Box<T>`), trong khi đối số kiểu là kiểu dữ liệu thực tế được sử dụng khi khởi tạo lớp (như `String` trong `Box<String>`).
* **Ví dụ nhỏ**:
  ```java
  // T is the type parameter
  public class Box<T> {
      private T item;
      public void set(T item) { this.item = item; }
      public T get() { return item; }
  }
  ```

## tham số kiểu có giới hạn (bounded type parameter)

Một tham số kiểu hạn chế phạm vi của các đối số kiểu được phép sử dụng từ khóa `extends` (ví dụ: `T extends Number`). Nó đảm bảo rằng bất kỳ đối số kiểu nào được cung cấp khi khởi tạo sẽ là kiểu con của giới hạn đã chỉ định.

* **Tầm quan trọng**: Việc giới hạn một tham số kiểu cho phép bạn gọi các phương thức được định nghĩa trên giới hạn đó (chẳng hạn như gọi `doubleValue()` trên một kiểu được giới hạn bởi `Number`) bên trong lớp generic, mà không cần phải ép kiểu đối tượng generic.
* **Hiểu lầm thường gặp**: Nghĩ rằng `extends` chỉ có nghĩa là kế thừa lớp. Trong các tham số kiểu có giới hạn, `extends` được sử dụng cho cả các lớp và giao diện (ví dụ: `T extends Comparable<T>` trong đó `Comparable` là một giao diện). Java không sử dụng `implements` cho các giới hạn generic.
* **Ví dụ nhỏ**:
  ```java
  // T is bounded by Number, allowing access to Number methods
  public class Calculator<T extends Number> {
      public double doubleValueOf(T value) {
          return value.doubleValue(); // Valid because T is at least a Number
      }
  }
  ```

## ký tự đại diện (wildcard)

Ký tự đại diện `?` đại diện cho một kiểu dữ liệu chưa biết trong các khai báo tham số generic. Nó có thể không giới hạn (`<?>`), giới hạn trên (`<? extends T>`), hoặc giới hạn dưới (`<? super T>`), cho phép các phương thức chấp nhận các đối số được tham số hóa thuộc nhiều kiểu khác nhau.

* **Tầm quan trọng**: Ký tự đại diện cho phép các nhà phát triển viết các API linh hoạt chấp nhận các kiểu generic có liên quan (ví dụ: cho phép một phương thức xử lý cả `List<Integer>` và `List<Double>` bằng cách sử dụng `List<? extends Number>`), điều mà bình thường là không thể do tính bất biến (invariance) của generic.
* **Hiểu lầm thường gặp**: Tin rằng các khai báo ký tự đại diện hoạt động giống như các biến kiểu thông thường. Bạn không thể sử dụng ký tự đại diện (`?`) bên trong thân phương thức để khai báo biến hoặc khởi tạo đối tượng (ví dụ: `? item = list.get(0)` là không hợp lệ).
* **Ví dụ nhỏ**:
  ```java
  // Accepts any list of Number or its subclasses
  public static void printNumbers(List<? extends Number> list) {
      for (Number n : list) {
          System.out.println(n);
      }
  }
  ```

## PECS

Một từ viết tắt của **Producer Extends, Consumer Super** (Nhà sản xuất dùng Extends, Người tiêu dùng dùng Super), hướng dẫn việc lựa chọn các giới hạn của ký tự đại diện. Sử dụng `? extends T` khi cấu trúc generic sản xuất dữ liệu (chỉ đọc), và `? super T` khi nó tiêu thụ dữ liệu (chỉ ghi).

* **Tầm quan trọng**: Nó giải quyết sự xung đột giữa tính bất biến của generic và tính linh hoạt của kiểu con. Tuân theo nguyên tắc PECS cho phép bạn viết các phương thức tiện ích có thể tái sử dụng để đọc từ và ghi vào các bộ sưu tập thuộc các cấp độ phân cấp khác nhau mà không gặp lỗi an toàn kiểu.
* **Hiểu lầm thường gặp**: Cố gắng sử dụng `? extends T` cho các bộ sưu tập mà bạn cần chèn thêm các phần tử. Một danh sách bị giới hạn bởi producer (`? extends T`) là chỉ đọc vì trình biên dịch không thể xác định lớp con chính xác tại thời điểm chạy, khiến tất cả các thao tác thêm mới (ngoại trừ `null`) đều bị lỗi biên dịch.
* **Ví dụ nhỏ**:
  ```java
  // dest consumes elements (super), src produces elements (extends)
  public static <T> void copy(List<? super T> dest, List<? extends T> src) {
      for (T item : src) {
          dest.add(item); // Safe write to dest, safe read from src
      }
  }
  ```

## xóa bỏ kiểu (type erasure)

Quá trình tại thời điểm biên dịch mà trình biên dịch Java loại bỏ tất cả các đối số kiểu generic khỏi các khai báo lớp, giao diện, và phương thức generic. Bytecode kết quả chỉ chứa các kiểu thô (raw type), với các phép ép kiểu ngầm định được chèn vào những nơi cần thiết.

* **Tầm quan trọng**: Nó đảm bảo khả năng tương thích ngược, cho phép mã nguồn generic đã biên dịch chạy trơn tru trên các phiên bản JVM cũ hơn và tương tác với các thư viện trước Java 5 vốn chỉ sử dụng các kiểu thô.
* **Hiểu lầm thường gặp**: Nghĩ rằng thông tin kiểu generic bị mất hoàn toàn. Mặc dù các tham số kiểu trên các biến và thực thể bị xóa bỏ, siêu dữ liệu của chính lớp đó (ví dụ: `class MyList<T>`) vẫn còn lại trong định nghĩa tệp lớp và có thể truy cập được thông qua phản chiếu reflection.
* **Ví dụ nhỏ**:
  ```java
  // What you write:
  List<String> list = new ArrayList<>();
  list.add("hello");
  String s = list.get(0);

  // What the compiler generates in bytecode:
  List list = new ArrayList();
  list.add("hello");
  String s = (String) list.get(0); // Inserted cast
  ```

## kiểu thô (raw type)

Tên của một lớp hoặc giao diện generic được sử dụng mà không có bất kỳ đối số kiểu nào (ví dụ: sử dụng `List` thay vì `List<String>`). Các kiểu thô hoạt động giống như trước Java 5, coi tất cả các tham số kiểu là giới hạn trên của chúng (thường là `Object`).

* **Tầm quan trọng**: Các kiểu thô cho phép mã nguồn cũ chạy trên các phiên bản Java hiện đại. Tuy nhiên, chúng vô hiệu hóa tất cả các kiểm tra an toàn kiểu tại thời điểm biên dịch, chuyển các lỗi không khớp kiểu thành các ngoại lệ tại thời điểm chạy.
* **Hiểu lầm thường gặp**: Tin rằng kiểu thô và ký tự đại diện không giới hạn là giống hệt nhau. Mặc dù cả hai đều có thể tham chiếu đến bất kỳ kiểu nào, `List` (thô) cho phép bạn thêm bất kỳ đối tượng nào (không an toàn), trong khi `List<?>` (ký tự đại diện) ngăn chặn việc thêm các phần tử (an toàn), thực thi các bất biến tại thời điểm biên dịch.
* **Ví dụ nhỏ**:
  ```java
  List rawList = new ArrayList(); // Raw type
  rawList.add("String");
  rawList.add(Integer.valueOf(100)); // Compiles, but risks ClassCastException on read

  List<?> wildcardList = new ArrayList<String>();
  // wildcardList.add("String"); // Compile Error: compiler prevents additions for safety
  ```
