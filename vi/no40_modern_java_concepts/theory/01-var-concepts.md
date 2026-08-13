# Các Khái Niệm Java Hiện Đại Cần Biết - Phần 1

File này trình bày một mảng tập trung về **các tính năng Java hiện đại** được giới thiệu trong các phiên bản JDK gần đây (từ Java 10 đến 21). Hãy học từng khái niệm như một quy tắc Java thực tế.

## Nội Dung Đề Cương

---

## Ghi Chú Chi Tiết

### var

`var` cho phép trình biên dịch suy ra kiểu tĩnh (static type) của biến cục bộ dựa trên biểu thức khởi tạo bên phải.

- **Ví dụ chạy được**:
  ```java
  var name = "Alice";                       // Suy ra là String
  var list = new ArrayList<String>();       // Suy ra là ArrayList<String>
  
  for (var element : list) {                // Suy ra là String bên trong vòng lặp
      System.out.println(element);
  }
  ```

- **Lỗi Thường Gặp / Trường Hợp Thất Bại**:
  - **Không phải Kiểu Động**: Các biến khai báo với `var` vẫn được định kiểu tĩnh. Không thể gán lại chúng cho các kiểu không tương thích.
    ```java
    var count = 10;
    // count = "ten"; // LỖI BIÊN DỊCH: Kiểu không tương thích
    ```
  - **Các Trường Hợp Không Hợp Lệ**: `var` không thể được khởi tạo mà không có giá trị, hoặc với null literal, và không thể dùng cho field, tham số phương thức, hay kiểu trả về.
    ```java
    // var x;      // LỖI BIÊN DỊCH
    // var y = null; // LỖI BIÊN DỊCH
    ```

---

### Records

Record là các lớp `final` tinh giản được thiết kế để hoạt động như những vật chứa dữ liệu (data carrier) bất biến đơn giản. Trình biên dịch tự động tạo ra các field `private final`, một constructor chính (canonical constructor), các phương thức accessor (tên khớp với tên field), `equals()`, `hashCode()`, và `toString()`.

- **Ví dụ chạy được**:
  ```java
  public record Point(int x, int y) {}

  // Cách dùng:
  Point p = new Point(10, 20);
  System.out.println(p.x()); // 10 (Accessor KHÔNG có tiền tố 'get')
  ```

- **Lỗi Thường Gặp / Trường Hợp Thất Bại**:
  - **Tính Final Ngầm Định**: Record là final và không thể kế thừa các lớp khác (chúng đã kế thừa `java.lang.Record`). Tất cả field đều final và không thể thay đổi.
  - **Xác Thực trong Constructor**: Để xác thực đầu vào, dùng compact constructor. Không khai báo lại tham số hoặc field.
    ```java
    public record User(String name, int age) {
        public User { // Compact constructor (không có danh sách tham số)
            if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
        }
    }
    ```

---

### Sealed class

Sealed class và interface kín giới hạn những lớp hoặc interface nào có thể kế thừa hoặc triển khai chúng.

- **Ví dụ chạy được**:
  ```java
  public abstract sealed class Shape permits Circle, Square {}
  
  // Lớp con phải là final, sealed, hoặc non-sealed:
  public final class Circle extends Shape {}
  public non-sealed class Square extends Shape {}
  ```

- **Lỗi Thường Gặp / Trường Hợp Thất Bại**:
  - Các lớp con của sealed class phải khai báo rõ ràng một trong ba modifier: `final` (không thể phân lớp tiếp), `sealed` (chỉ có thể phân lớp bởi các lớp con được phép), hoặc `non-sealed` (mở lại lớp để kế thừa tự do). Bỏ qua modifier này là lỗi biên dịch.

---

### Pattern matching for instanceof

Pattern matching for `instanceof` kết hợp kiểm tra kiểu và ép kiểu tự động thành một bước duy nhất.

- **Ví dụ chạy được**:
  ```java
  Object obj = "Hello World";
  if (obj instanceof String s) {
      // 's' được tự động ép kiểu thành String và có phạm vi ở đây
      System.out.println(s.toLowerCase());
  }
  ```

- **Lỗi Thường Gặp / Trường Hợp Thất Bại**:
  - **Giới Hạn Phạm Vi**: Biến ràng buộc (binding variable) chỉ trong phạm vi khi trình biên dịch có thể đảm bảo kiểm tra kiểu là đúng.
    ```java
    // LỖI BIÊN DỊCH: 's' không trong phạm vi trong nhánh hoặc (||)
    // if (obj instanceof String s || s.isEmpty()) {} 

    // ĐÚNG: 's' trong phạm vi trong nhánh và (&&) do short-circuiting
    if (obj instanceof String s && !s.isEmpty()) {
        System.out.println(s);
    }
    ```

---

### Switch expression

Switch expression cho phép `switch` trả về giá trị, sử dụng toán tử mũi tên (`->`) ngăn chặn fall-through, thay thế cú pháp colon-break dài dòng.

- **Ví dụ chạy được**:
  ```java
  int score = switch (grade) {
      case 'A' -> 100;
      case 'B' -> 80;
      case 'C', 'D' -> 60;
      default -> {
          System.out.println("Failing grade");
          yield 0; // Dùng yield để trả về giá trị từ khối nhiều dòng
      }
  };
  ```

- **Lỗi Thường Gặp / Trường Hợp Thất Bại**:
  - **Kiểm Tra Toàn Diện**: Switch expression phải toàn diện. Nếu không bao phủ mọi trường hợp có thể (ví dụ: tất cả giá trị enum hoặc lớp con sealed), phải cung cấp case `default`, nếu không biên dịch sẽ thất bại.

---

### Text blocks

Text block cung cấp chuỗi literal đa dòng, giữ nguyên định dạng và loại bỏ nhu cầu escape chuỗi xuống dòng.

- **Ví dụ chạy được**:
  ```java
  String html = """
                <html>
                    <body>
                        <p>Hello, World</p>
                    </body>
                </html>
                """;
  ```

- **Lỗi Thường Gặp / Trường Hợp Thất Bại**:
  - **Quy Tắc Dấu Mở**: Ba dấu nháy kép mở `"""` phải được theo sau bởi một dòng mới ngay lập tức. Đặt văn bản trên cùng dòng với dấu mở là lỗi cú pháp.
    ```java
    // LỖI BIÊN DỊCH:
    // String bad = """hello
    // world""";
    ```

---

### Thông báo NullPointerException được cải thiện

Kể từ Java 14, JVM xuất ra chi tiết chính xác giải thích biến hoặc giá trị trả về nào được định giá trị null.

- **Ví dụ**:
  ```java
  // Với câu lệnh: person.getAddress().getCity()
  // Nếu getAddress() là null, stack trace NPE chi tiết:
  // "Cannot invoke "Address.getCity()" because the return value of "Person.getAddress()" is null"
  ```

- **Đánh đổi**: Các chi tiết này được tạo ra tại runtime bằng cách phân tích bytecode. Dù cực kỳ hữu ích để gỡ lỗi, có thể tắt trên command line bằng `-XX:-ShowCodeDetailsInExceptionMessages` để tiết kiệm hiệu suất hoặc che giấu nội bộ lớp.

---

### Virtual Threads

Virtual thread là thread nhẹ được JVM quản lý thay vì hệ điều hành. Chúng cho phép chạy hàng triệu thread đồng thời với tốn bộ nhớ tối thiểu, lý tưởng cho các ứng dụng blocking I/O.

- **Ví dụ chạy được**:
  ```java
  // Tạo và khởi động một virtual thread đơn lẻ
  Thread vt = Thread.ofVirtual().start(() -> {
      System.out.println("Running on virtual thread: " + Thread.currentThread());
  });

  // Dùng executor cho các tác vụ có tính đồng thời cao
  try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      executor.submit(() -> {
          // thực hiện blocking I/O mạng
      });
  }
  ```

- **Lỗi Thường Gặp / Trường Hợp Thất Bại**:
  - **Ghim Carrier Thread**: Nếu một virtual thread chạy công việc blocking bên trong khối `synchronized` hoặc phương thức native, nó "ghim" OS carrier thread bên dưới, ngăn các virtual thread khác chạy trên đó.
  - **Giải pháp**: Thay thế khối `synchronized` bằng `java.util.concurrent.locks.ReentrantLock` cho các đường dẫn code có blocking.
  - **Pooling Thread**: Không nên pool virtual thread (tránh `fixedThreadPool`). Chúng rẻ để tạo và nên được bỏ đi sau khi dùng.

---

### Cơ bản về Structured Concurrency

Structured Concurrency (đồng thời có cấu trúc - hiện là tính năng xem trước) coi nhiều tác vụ con đồng thời chạy trên các thread riêng biệt như một đơn vị công việc duy nhất, phối hợp vòng đời và hủy bỏ một cách rõ ràng.

- **Ví dụ chạy được**:
  ```java
  // Yêu cầu biên dịch và chạy với '--enable-preview'
  try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
      Subtask<String> user  = scope.fork(() -> fetchUser());
      Subtask<Integer> order = scope.fork(() -> fetchOrder());

      scope.join();           // Chờ tất cả subtask
      scope.throwIfFailed();  // Lan truyền ngoại lệ của tác vụ thất bại đầu tiên

      System.out.println("Result: " + user.get() + " | " + order.get());
  }
  ```

- **Lỗi Thường Gặp / Trường Hợp Thất Bại**:
  - Gọi `.get()` trên một Subtask trước khi gọi `scope.join()` sẽ ném `IllegalStateException`.

---

### Pattern matching for switch

Pattern matching for switch mở rộng câu lệnh switch để kiểm tra kiểu tham số và kiểm tra điều kiện trực tiếp trên các case kiểu.

- **Ví dụ chạy được**:
  ```java
  Object obj = "Short";
  String desc = switch (obj) {
      case Integer i -> "An integer: " + i;
      // Case guard dùng 'when' (chỉ kiểm tra nếu pattern khớp với String)
      case String s when s.length() > 5 -> "Long string: " + s;
      case String s -> "Short string: " + s;
      default -> "Unknown type";
  };
  ```

- **Lỗi Thường Gặp / Trường Hợp Thất Bại**:
  - **Vấn đề Dominance**: Các pattern kiểu/điều kiện cụ thể phải được viết trước các case tổng quát. Nếu `case String s` được liệt kê trước `case String s when s.length() > 5`, trình biên dịch sẽ thất bại vì cái sau bị dominated (không thể tiếp cận).

---

## Tại sao var là Suy Luận Tại Thời Điểm Biên Dịch Chứ Không Phải Kiểu Động

`var` không phải duck typing của Python hay `var` của JavaScript. Nó là **suy luận kiểu biến cục bộ (local variable type inference)** — trình biên dịch phân tích phía bên phải của khai báo và gán tĩnh một kiểu cố định tại thời điểm biên dịch. Sau khi gán, kiểu của biến là bất biến trong suốt phạm vi của nó. Biến không khác gì một biến được khai báo kiểu tường minh — bytecode hoàn toàn giống nhau.

Bốn vị trí bị cấm của `var` tiết lộ chính xác cách kiểu tĩnh được bảo toàn:

1. **Field** — kiểu của field phải được khai báo tường minh để kiểu có thể nhìn thấy bởi bất kỳ code nào đọc hoặc ghi field, trong bất kỳ lớp nào, bất kỳ lúc nào.
2. **Tham số phương thức** — người gọi phải biết kiểu của tham số để biên dịch điểm gọi.
3. **Kiểu trả về phương thức** — người gọi phải biết kiểu nào mong đợi từ giá trị trả về.
4. **`var` không có bộ khởi tạo / `var x = null`** — trình biên dịch không có phía bên phải để suy luận từ đó, nên suy luận là bất khả thi.

### Mô Hình Tư Duy: var vs Kiểu Động
```
Python (dynamic):
x = 10
x = "hello"   ← OK: x thay đổi kiểu tại runtime

Java var (static inference):
var x = 10;           ← Suy ra là int tại thời điểm biên dịch
x = "hello";          ← LỖI BIÊN DỊCH: int không thể chứa String
// bytecode: giống hệt: int x = 10;
```

### Ví Dụ Code: var trong thực tế
```java
// Tất cả đều được định kiểu tĩnh tại thời điểm biên dịch
var name = "Alice";                     // String
var count = 42;                         // int
var list = new ArrayList<String>();     // ArrayList<String>

for (var entry : Map.of("k", 1).entrySet()) {
    // entry được suy ra là Map.Entry<String, Integer>
    System.out.println(entry.getKey() + "=" + entry.getValue());
}

// BẤT HỢP LỆ — trình biên dịch không có kiểu để suy luận
// var x;             // Lỗi: không thể suy luận kiểu
// var y = null;      // Lỗi: không thể suy luận kiểu từ null
// var z;             // Lỗi: biến phải được khởi tạo
```

### Chuỗi Nguyên Nhân - Kết Quả
`var x = new ArrayList<String>()` được dùng &rarr; Trình biên dịch đọc kiểu bên phải `ArrayList<String>` &rarr; Gán kiểu đó vĩnh viễn cho `x` &rarr; Bytecode tạo ra giống hệt khai báo tường minh `ArrayList<String> x` &rarr; Bất kỳ việc gán lại sang kiểu không tương thích gây ra lỗi biên dịch &rarr; Không tốn chi phí runtime, không có overhead boxing.

---

## Tại sao Records Đảm Bảo Tính Bất Biến Thông Qua Code Do Trình Biên Dịch Tạo Ra

Trước Records, tạo một lớp dữ liệu bất biến đơn giản yêu cầu viết constructor, field `final`, các phương thức accessor, `equals()`, `hashCode()`, và `toString()` — thường 50–100 dòng boilerplate cho một data carrier tầm thường. Records loại bỏ điều này bằng cách mã hóa hợp đồng "lớp này là vật chứa trong suốt của các thành phần được đặt tên" trực tiếp trong ngôn ngữ.

Trình biên dịch tạo ra:
- **Field `private final`** cho mỗi thành phần record — không thể thay đổi sau khi khởi tạo.
- **Canonical constructor** với tất cả thành phần là tham số để gán mỗi field.
- **Phương thức Accessor** đặt tên theo field (ví dụ: `point.x()`, không phải `getX()`) — các accessor khớp tên field theo thiết kế để làm cho truy cập thành phần có thể khám phá.

Record là `final` ngầm định và không thể kế thừa các lớp khác (chúng ngầm định kế thừa `java.lang.Record`). Điều này ngăn chặn thay đổi thông qua phân lớp. Cú pháp compact constructor cho phép xác thực mà không cần khai báo lại tham số.

### Mô Hình Tư Duy: Record vs Lớp Bất Biến Thủ Công
```
// Lớp bất biến thủ công — ~60 dòng:
public final class Point {
    private final int x;
    private final int y;
    public Point(int x, int y) { this.x = x; this.y = y; }
    public int x() { return x; }
    public int y() { return y; }
    @Override public boolean equals(Object o) { ... }
    @Override public int hashCode() { ... }
    @Override public String toString() { ... }
}

// Tương đương Record — 1 dòng:
public record Point(int x, int y) {}
// Trình biên dịch tự động tạo ra tất cả bên trên
```

### Ví Dụ Code: Records với xác thực
```java
public record User(String name, int age) {
    // Compact constructor — không có danh sách tham số, dùng gán ngầm định
    public User {
        if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
        name = name.strip(); // Có thể chuyển đổi giá trị trước khi gán
    }
}

User u = new User("Alice", 30);
System.out.println(u.name()); // "Alice" — accessor, không phải getName()
System.out.println(u.age());  // 30
System.out.println(u);        // User[name=Alice, age=30]

// Không thể thay đổi — field là final
// u.name = "Bob"; // LỖI BIÊN DỊCH: không có quyền truy cập field như vậy
```

### Chuỗi Nguyên Nhân - Kết Quả
`record Point(int x, int y)` được khai báo &rarr; Trình biên dịch tạo ra field private final, canonical constructor, accessor, equals/hashCode/toString &rarr; Tất cả field final từ khi khởi tạo &rarr; Không có phương thức mutator được tạo &rarr; Record bất biến theo thiết kế &rarr; An toàn để chia sẻ giữa các thread và dùng làm khóa Map.

---

## Tại sao Sealed Classes Cho Phép Pattern Matching An Toàn Toàn Diện

Không có sealed class, trình biên dịch không thể biết tại thời điểm biên dịch tất cả các lớp con có thể của một lớp abstract hoặc interface. Bất kỳ ai trong bất kỳ module nào đều có thể kế thừa một lớp mở. Điều này có nghĩa là switch expression trên kiểu không thể toàn diện — trình biên dịch luôn yêu cầu một nhánh `default` để xử lý các lớp con không biết.

Sealed class khai báo chính xác những lớp nào được phép triển khai hoặc kế thừa chúng trong mệnh đề `permits`. Trình biên dịch có thể liệt kê toàn bộ tập hợp kiểu có thể. Khi một lớp `sealed` được dùng trong `switch` expression và tất cả kiểu con được phép đều được bao phủ, trình biên dịch xác minh tính toàn diện mà không yêu cầu case `default` — và sẽ lỗi biên dịch nếu một kiểu con được phép mới được thêm vào nhưng switch không được cập nhật.

Điều này cho phép "kiểu dữ liệu đại số kiểu đóng" phổ biến trong Scala, Haskell và Kotlin (lớp `sealed`). Trình biên dịch trở thành người bảo đảm tính đúng đắn cho việc phân nhánh dựa trên kiểu.

### Mô Hình Tư Duy: Tính Toàn Diện của Phân Cấp Lớp Mở vs Kín
```
// Lớp mở — trình biên dịch không thể liệt kê tất cả lớp con
abstract class Shape {}
// Switch PHẢI có default để toàn diện
String describe = switch (shape) {
    case Circle c -> "circle with radius " + c.radius();
    default -> "unknown shape"; // Không thể xóa an toàn
};

// Sealed class — trình biên dịch biết tất cả kiểu con được phép
sealed class Shape permits Circle, Square {}
// Switch toàn diện không cần default
String describe = switch (shape) {
    case Circle c -> "circle with radius " + c.radius();
    case Square s -> "square with side " + s.side();
    // Không cần default — trình biên dịch xác minh tất cả kiểu con được bao phủ
};
// Nếu Rectangle được thêm vào permits nhưng không vào switch → LỖI BIÊN DỊCH
```

### Ví Dụ Code: Sealed class với switch expression
```java
public sealed interface Shape permits Circle, Square, Triangle {}
public record Circle(double radius) implements Shape {}
public record Square(double side) implements Shape {}
public record Triangle(double base, double height) implements Shape {}

// Switch toàn diện — không cần default
double area = switch (shape) {
    case Circle c -> Math.PI * c.radius() * c.radius();
    case Square s -> s.side() * s.side();
    case Triangle t -> 0.5 * t.base() * t.height();
};
```

### Chuỗi Nguyên Nhân - Kết Quả
`sealed` được khai báo with `permits Circle, Square` &rarr; Trình biên dịch ghi lại tập hợp kiểu con được phép &rarr; Switch trên `Shape` bao phủ `Circle` và `Square` &rarr; Trình biên dịch xác minh tính toàn diện &rarr; Không cần `default` &rarr; Thêm `Triangle` vào permits mà không cập nhật switch &rarr; Lỗi biên dịch.

---

## Tại sao Pattern Matching for Switch Yêu Cầu Sắp Xếp Theo Độ Cụ Thể

Trình biên dịch Java thực thi **quy tắc dominance** cho pattern matching trong switch: một pattern cụ thể hơn không thể xuất hiện sau một pattern tổng quát hơn sẽ khớp với tất cả cùng đầu vào. Nếu một case tổng quát xuất hiện trước, case cụ thể theo sau không bao giờ có thể đạt được — nó bị "dominated" (chi phối).

Với các guarded pattern như `case String s when s.length() > 5`, guard `when s.length() > 5` là tập con của `case String s` (khớp tất cả String). Nếu `case String s` được liệt kê trước, guarded case `case String s when s.length() > 5` không bao giờ có thể thực thi — case không có guard đã bắt tất cả String.

Đây là kiểm tra **tại thời điểm biên dịch**. Trình biên dịch từ chối các câu lệnh switch nơi một case bị dominated, ngăn chặn các lỗi im lặng nơi một case chính xác hơn vô tình bị che khuất.

### Mô Hình Tư Duy: Quy tắc sắp xếp Dominance
```
// BẤT HỢP LỆ — case String s chi phối guarded case bên dưới nó
switch (obj) {
    case String s -> "any string: " + s;          // Khớp TẤT CẢ string
    case String s when s.length() > 5 -> "long";  // LỖI BIÊN DỊCH: bị dominated
}

// HỢP LỆ — guarded case cụ thể nhất lên đầu
switch (obj) {
    case String s when s.length() > 5 -> "long string: " + s; // Cụ thể
    case String s -> "short string: " + s;                    // Tổng quát
    case Integer i -> "integer: " + i;
    default -> "other";
}
```

### Ví Dụ Code: Pattern matching switch với guards
```java
Object obj = "Hello World";
String desc = switch (obj) {
    case null -> "null value";
    case Integer i when i > 100 -> "large integer: " + i;
    case Integer i -> "small integer: " + i;
    case String s when s.length() > 5 -> "long string: " + s;
    case String s -> "short string: " + s;
    default -> "unknown: " + obj;
};
System.out.println(desc); // Kết quả: long string: Hello World
```

### Chuỗi Nguyên Nhân - Kết Quả
Các case pattern được kiểm tra từ trên xuống &rarr; `case String s` tổng quát hơn khớp tất cả String &rarr; `case String s when ...` cụ thể hơn bên dưới không bao giờ đạt được &rarr; Lỗi biên dịch: pattern bị dominated &rarr; Sắp xếp lại: guarded (cụ thể) trước unguarded (tổng quát) &rarr; Trình biên dịch xác minh không có pattern không thể tiếp cận &rarr; Tất cả case có thể tiếp cận.

---

## Tại sao Virtual Threads Ghim Carrier Threads trong Synchronized Blocks

Virtual thread được JVM quản lý trên một pool nhỏ các OS "carrier" thread thực sự. Khi một virtual thread cần block (chờ I/O, khóa, điều kiện), JVM **mount** nó lên một carrier thread, và khi blocking, **unmount** nó (tạm dừng trạng thái virtual thread trong khi giải phóng carrier thread cho các virtual thread khác).

Vấn đề là `synchronized`. Khi một virtual thread vào khối `synchronized`, JVM phải giữ khóa monitor trên OS thread gốc của carrier thread — đây là ràng buộc triển khai JVM của mô hình khóa HotSpot hiện tại. Nếu virtual thread block trong khi giữ monitor (ví dụ: blocking I/O bên trong `synchronized`), carrier thread bị "ghim" — không thể được giải phóng để chạy các virtual thread khác, đánh bại toàn bộ mục đích của khả năng mở rộng virtual thread.

`ReentrantLock` không dùng monitor cấp OS. Nó dùng cấu trúc hàng đợi cấp JVM có thể được liên kết with virtual thread, không phải carrier thread. Khi một virtual thread block trên `ReentrantLock.lock()`, carrier thread được unmount và giải phóng trong khi virtual thread chờ.

### Mô Hình Tư Duy: synchronized ghim vs ReentrantLock unmount
```
[synchronized — ghim carrier thread]
Virtual Thread 1 vào khối synchronized
    → Virtual Thread 1 block bên trong synchronized (chờ I/O)
    → Carrier OS Thread 1 bị GHIM — không thể nhận VT khác
    → Chỉ 1 VT hiệu quả chạy trên carrier đó — mất khả năng mở rộng

[ReentrantLock — cho phép unmount carrier]
Virtual Thread 1 gọi reentrantLock.lock()
    → Virtual Thread 1 block trên lock()
    → JVM unmount VT1 từ Carrier OS Thread 1
    → Carrier OS Thread 1 giờ tự do chạy Virtual Thread 2, 3, ...
    → Khi khóa được giải phóng, VT1 remount trên bất kỳ carrier nào khả dụng
```

### Ví Dụ Code: ReentrantLock thay vì synchronized cho virtual thread
```java
import java.util.concurrent.locks.ReentrantLock;

public class SafeVirtualThreadCounter {
    private final ReentrantLock lock = new ReentrantLock(); // Dùng thay cho synchronized
    private int count = 0;

    public void increment() {
        lock.lock(); // Không ghim carrier thread
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }
}

// Tạo virtual thread
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    for (int i = 0; i < 1_000_000; i++) {
        executor.submit(() -> counter.increment());
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả
Virtual thread vào khối `synchronized` &rarr; JVM phải giữ monitor cấp OS trên carrier thread &rarr; Virtual thread block (I/O, wait) bên trong synchronized &rarr; Carrier thread bị ghim và không khả dụng &rarr; Khả năng mở rộng virtual thread bị suy giảm &rarr; Thay bằng `ReentrantLock` &rarr; Blocking trên lock unmount virtual thread khỏi carrier &rarr; Carrier được giải phóng cho các virtual thread khác &rarr; Khả năng đồng thời đầy đủ được khôi phục.

## Liên Kết Tham Khảo

- https://openjdk.org/jeps/286 (JEP 286 — Local-Variable Type Inference: var)
- https://openjdk.org/jeps/395 (JEP 395 — Records)
- https://openjdk.org/jeps/409 (JEP 409 — Sealed Classes)
- https://openjdk.org/jeps/441 (JEP 441 — Pattern Matching for switch)
- https://openjdk.org/jeps/444 (JEP 444 — Virtual Threads)
