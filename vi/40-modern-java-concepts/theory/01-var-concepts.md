# Các Khái Niệm Java Hiện Đại Cần Biết - Phần 1 (Modern Java Concepts To Know - Part 1)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này trình bày một phần trọng tâm của **Các Khái Niệm Java Hiện Đại (Modern Java Concepts)** được giới thiệu trong các phiên bản JDK gần đây (từ Java 10 đến 21). Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế.

## Khái Quát Nội Dung (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `var` | Từ khóa suy luận kiểu biến cục bộ (`var`) được giới thiệu từ Java 10. |
| `Records` | Kiểu lớp rút gọn dùng làm vật chứa dữ liệu được giới thiệu từ Java 16 để biểu diễn các bản ghi dữ liệu bất biến. |
| `Sealed class` | Công cụ sửa đổi kiểm soát phân cấp lớp (Sealed class - lớp niêm phong) được giới thiệu từ Java 17 để giới hạn việc kế thừa của lớp con. |
| `Pattern matching for instanceof` | Cơ chế ép kiểu được đơn giản hóa (Khớp mẫu cho instanceof) được giới thiệu từ Java 16. |
| `Switch expression` | Biểu thức switch với cú pháp mũi tên trả về giá trị, được giới thiệu từ Java 14. |
| `Text blocks` | Định dạng chuỗi văn bản nhiều dòng (`"""`) được giới thiệu từ Java 15. |
| `Enhanced NullPointerException message` | Nhật ký lỗi treo chương trình cấp JVM hiển thị chi tiết và chính xác. |
| `Virtual Threads` | Kiến trúc luồng hạng nhẹ (Virtual Threads - luồng ảo) được giới thiệu từ Java 21 cho các khối lượng công việc đồng thời bị chặn (blocking concurrent workloads). |
| `Basic Structured Concurrency` | Đường ống đồng thời tổ chức các tác vụ con như một khối giao dịch (transaction block) duy nhất. |
| `Pattern matching for switch` | Rẽ nhánh dựa trên kiểu dữ liệu và các điều kiện bảo vệ (guards) bên trong các câu lệnh switch (Java 21). |

---

## Ghi Chú Chi Tiết (Detailed Notes)

### var

`var` cho phép trình biên dịch tự suy luận kiểu tĩnh của một biến cục bộ dựa trên biểu thức khởi tạo của nó.

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  var name = "Alice";                       // Inferred as String
  var list = new ArrayList<String>();       // Inferred as ArrayList<String>
  
  for (var element : list) {                // Inferred as String inside loop
      System.out.println(element);
  }
  ```

- **Sai lầm thường gặp / Chế độ thất bại (Common Mistake / Failure Mode)**:
  - **Không phải định kiểu động (Not Dynamic Typing)**: Các biến được khai báo bằng `var` vẫn được định kiểu tĩnh. Bạn không thể gán lại chúng cho các kiểu không tương thích.
    ```java
    var count = 10;
    // count = "ten"; // COMPILE ERROR: Incompatible types
    ```
  - **Các trường hợp sử dụng không hợp lệ (Invalid Use Cases)**: `var` không thể được khởi tạo mà không có giá trị, hoặc khởi tạo bằng giá trị null trực tiếp, và không thể được sử dụng cho các trường (fields), tham số phương thức (method parameters), hoặc kiểu trả về (return types).
    ```java
    // var x;      // COMPILE ERROR
    // var y = null; // COMPILE ERROR
    ```

---

### Records

Records là các lớp `final` rút gọn được thiết kế để hoạt động như các vật chứa dữ liệu bất biến đơn giản. Trình biên dịch sẽ tự động tạo ra các trường `private final`, một hàm khởi tạo chuẩn (canonical constructor), các phương thức truy xuất accessor (khớp với tên trường), `equals()`, `hashCode()`, và `toString()`.

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  public record Point(int x, int y) {}

  // Usage:
  Point p = new Point(10, 20);
  System.out.println(p.x()); // 10 (Accessors do NOT have a 'get' prefix)
  ```

- **Sai lầm thường gặp / Chế độ thất bại (Common Mistake / Failure Mode)**:
  - **Tính Final Ngầm định (Implicit Finality)**: Records là final và không thể kế thừa các lớp khác (chúng đã kế thừa lớp `java.lang.Record`). Tất cả các trường đều là final và không thể bị sửa đổi.
  - **Xác thực trong Hàm Khởi Tạo (Validation in Constructors)**: Để xác thực đầu vào, hãy sử dụng một hàm khởi tạo rút gọn (compact constructor). Không khai báo lại các tham số hoặc các trường.
    ```java
    public record User(String name, int age) {
        public User { // Compact constructor (no parameter list)
            if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
        }
    }
    ```

---

### Sealed class (Lớp niêm phong)

Các lớp và giao diện niêm phong (Sealed classes và interfaces) giới hạn lớp hoặc giao diện khác có thể kế thừa hoặc triển khai chúng.

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  public abstract sealed class Shape permits Circle, Square {}
  
  // Subclasses must be final, sealed, or non-sealed:
  public final class Circle extends Shape {}
  public non-sealed class Square extends Shape {}
  ```

- **Sai lầm thường gặp / Chế độ thất bại (Common Mistake / Failure Mode)**:
  - Các lớp con của một sealed class phải khai báo rõ ràng một trong ba bổ từ (modifiers): `final` (không thể được tạo lớp con thêm nữa), `sealed` (chỉ có thể được tạo lớp con bởi các lớp con thứ cấp được cho phép), hoặc `non-sealed` (mở lại lớp cho phép bất kỳ lớp nào kế thừa). Việc bỏ qua bổ từ này sẽ dẫn đến lỗi biên dịch.

---

### Pattern matching cho instanceof (Khớp mẫu cho instanceof)

Khớp mẫu cho `instanceof` kết hợp việc kiểm tra kiểu dữ liệu và tự động ép kiểu vào trong một bước duy nhất.

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  Object obj = "Hello World";
  if (obj instanceof String s) {
      // 's' is automatically cast to String and is in scope here
      System.out.println(s.toLowerCase());
  }
  ```

- **Sai lầm thường gặp / Chế độ thất bại (Common Mistake / Failure Mode)**:
  - **Giới hạn Phạm vi (Scope Limitation)**: Biến liên kết chỉ có phạm vi hiệu lực ở những nơi mà trình biên dịch có thể đảm bảo việc kiểm tra kiểu dữ liệu là đúng (true).
    ```java
    // COMPILE ERROR: 's' is not in scope in the or (||) branch
    // if (obj instanceof String s || s.isEmpty()) {} 

    // CORRECT: 's' is in scope in the and (&&) branch due to short-circuiting
    if (obj instanceof String s && !s.isEmpty()) {
        System.out.println(s);
    }
    ```

---

### Switch expression (Biểu thức switch)

Biểu thức switch cho phép `switch` trả ra một giá trị, sử dụng toán tử mũi tên (`->`) giúp ngăn chặn hành vi trôi xuống (fall-through), thay thế cú pháp dấu hai chấm kèm break rườm rà.

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  int score = switch (grade) {
      case 'A' -> 100;
      case 'B' -> 80;
      case 'C', 'D' -> 60;
      default -> {
          System.out.println("Failing grade");
          yield 0; // Use yield to return value from multi-line blocks
      }
  };
  ```

- **Sai lầm thường gặp / Chế độ thất bại (Common Mistake / Failure Mode)**:
  - **Kiểm tra tính đầy đủ (Exhaustiveness Check)**: Biểu thức switch phải có tính đầy đủ. Nếu bạn không bao gồm mọi trường hợp có thể xảy ra (ví dụ: tất cả các giá trị enum hoặc các lớp con được niêm phong), bạn phải cung cấp một trường hợp `default`, nếu không việc biên dịch sẽ thất bại.

---

### Text blocks (Khối văn bản)

Khối văn bản cung cấp các chuỗi văn bản nhiều dòng trực tiếp, giữ nguyên định dạng và loại bỏ sự cần thiết của các chuỗi ký tự xuống dòng thoát (escaped newline sequences).

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  String html = """
                <html>
                    <body>
                        <p>Hello, World</p>
                    </body>
                </html>
                """;
  ```

- **Sai lầm thường gặp / Chế độ thất bại (Common Mistake / Failure Mode)**:
  - **Quy tắc dấu phân tách mở đầu (Opening Delimiter Rules)**: Ba dấu ngoặc kép mở đầu `"""` phải được theo sau ngay lập tức bởi một ký tự xuống dòng. Việc đặt văn bản trên cùng một dòng với dấu ngoặc kép mở đầu là một lỗi cú pháp.
    ```java
    // COMPILE ERROR:
    // String bad = """hello
    // world""";
    ```

---

### Thông báo NullPointerException cải tiến (Enhanced NullPointerException message)

Từ Java 14, JVM xuất ra các chi tiết chính xác giải thích biến hoặc giá trị trả về nào được đánh giá là null.

- **Ví dụ (Example)**:
  ```java
  // For statement: person.getAddress().getCity()
  // If getAddress() is null, the NPE stack trace details:
  // "Cannot invoke "Address.getCity()" because the return value of "Person.getAddress()" is null"
  ```

- **Đánh đổi (Tradeoff)**: Các chi tiết này được tạo ra trong thời gian chạy bằng cách phân tích mã bytecode. Mặc dù cực kỳ hữu ích cho việc gỡ lỗi, tính năng này có thể bị tắt trên dòng lệnh bằng cách sử dụng `-XX:-ShowCodeDetailsInExceptionMessages` để tiết kiệm hiệu năng hoặc che giấu các chi tiết nội bộ của lớp.

---

### Virtual Threads (Luồng ảo)

Luồng ảo (Virtual Threads) là các luồng hạng nhẹ được quản lý bởi JVM thay vì hệ điều hành. Chúng cho phép chạy hàng triệu luồng đồng thời với chi phí bộ nhớ tối thiểu, lý tưởng cho các ứng dụng có hành vi chặn trên I/O (block-on-I/O applications).

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  // Create and start a single virtual thread
  Thread vt = Thread.ofVirtual().start(() -> {
      System.out.println("Running on virtual thread: " + Thread.currentThread());
  });

  // Use executor for high-concurrency tasks
  try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      executor.submit(() -> {
          // perform network blocking I/O
      });
  }
  ```

- **Sai lầm thường gặp / Chế độ thất bại (Common Mistake / Failure Mode)**:
  - **Ghim luồng vận chuyển (Carrier Thread Pinning)**: Nếu một luồng ảo chạy công việc bị chặn bên trong một khối `synchronized` hoặc phương thức gốc (native method), nó sẽ "ghim" luồng vận chuyển OS cơ sở của nó, ngăn cản các luồng ảo khác chạy trên đó.
  - **Giảm thiểu (Mitigation)**: Thay thế các khối `synchronized` bằng `java.util.concurrent.locks.ReentrantLock` cho các đường dẫn mã có hành vi bị chặn.
  - **Nhóm luồng (Thread Pooling)**: Không tạo nhóm luồng (thread pool) cho các luồng ảo (tránh `fixedThreadPool`). Chúng rất rẻ để tạo và nên được loại bỏ sau khi sử dụng.

---

### Lập trình đồng thời cấu trúc cơ bản (Basic Structured Concurrency)

Lập trình đồng thời cấu trúc (Structured Concurrency - hiện là tính năng xem trước) coi nhiều tác vụ con đồng thời chạy trong các luồng riêng biệt như một đơn vị công việc duy nhất, điều phối vòng đời và hủy bỏ một cách sạch sẽ.

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  // Requires compiling and running with '--enable-preview'
  try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
      Subtask<String> user  = scope.fork(() -> fetchUser());
      Subtask<Integer> order = scope.fork(() -> fetchOrder());

      scope.join();           // Join all subtasks
      scope.throwIfFailed();  // Propagate first failed task exception

      System.out.println("Result: " + user.get() + " | " + order.get());
  }
  ```

- **Sai lầm thường gặp / Chế độ thất bại (Common Mistake / Failure Mode)**:
  - Gọi `.get()` trên một Subtask trước khi gọi `scope.join()` sẽ ném ra một `IllegalStateException`.

---

### Pattern matching cho switch (Khớp mẫu cho switch)

Khớp mẫu cho switch mở rộng các câu lệnh switch để kiểm tra các lớp tham số và kiểm tra các điều kiện trực tiếp trên các trường hợp kiểu dữ liệu (type cases).

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  Object obj = "Short";
  String desc = switch (obj) {
      case Integer i -> "An integer: " + i;
      // Case guard using 'when' (evaluates only if pattern matches String)
      case String s when s.length() > 5 -> "Long string: " + s;
      case String s -> "Short string: " + s;
      default -> "Unknown type";
  };
  ```

- **Sai lầm thường gặp / Chế độ thất bại (Common Mistake / Failure Mode)**:
  - **Các vấn đề về độ ưu tiên (Dominance issues)**: Các mẫu kiểu/điều kiện cụ thể phải được viết phía trên các trường hợp tổng quát. Nếu `case String s` được viết phía trên `case String s when s.length() > 5`, trình biên dịch sẽ báo lỗi vì trường hợp sau bị bao trùm (không thể tiếp cận).

---

## Tại sao var là Suy Luận Kiểu Biên Dịch Không Phải Định Kiểu Động (Why var Is Compile-Time Inference Not Dynamic Typing)

`var` không phải là định kiểu vịt (duck typing) của Python hay `var` của JavaScript. Nó là **suy luận kiểu biến cục bộ (local variable type inference)** — trình biên dịch phân tích phía bên phải của khai báo và gán tĩnh một kiểu cố định tại thời điểm biên dịch. Sau khi được gán, kiểu của biến sẽ không thể thay đổi trong suốt phạm vi của nó. Biến này không có gì khác biệt so với một biến được định kiểu rõ ràng — mã bytecode được tạo ra là hoàn toàn giống nhau.

Bốn vị trí bị cấm sử dụng `var` cho thấy chính xác cách thức định kiểu tĩnh được bảo toàn:

1. **Các trường (Fields)** — kiểu của trường phải được khai báo rõ ràng để kiểu đó hiển thị với bất kỳ đoạn mã nào đọc hoặc ghi vào trường đó, trong bất kỳ lớp nào, tại bất kỳ thời điểm nào.
2. **Tham số phương thức (Method parameters)** — người gọi phải biết kiểu của tham số để biên dịch điểm gọi (call site).
3. **Kiểu trả về của phương thức (Method return types)** — người gọi phải biết kiểu dữ liệu nào được mong đợi từ giá trị trả về.
4. **`var` không có giá trị khởi tạo / `var x = null`** — trình biên dịch không có vế phải để suy luận, vì vậy việc suy luận kiểu là bất khả thi.

### Mô hình tư duy: var vs Định kiểu động (Mental Model: var vs Dynamic Typing)
```
Python (dynamic):
x = 10
x = "hello"   ← OK: x thay đổi kiểu trong thời gian chạy

Java var (static inference):
var x = 10;           ← Được suy luận là kiểu int tại thời điểm biên dịch
x = "hello";          ← COMPILE ERROR: kiểu int không thể chứa String
// mã bytecode: giống hệt như: int x = 10;
```

### Ví dụ Code: var trong thực tế (Code Example: var in practice)
```java
// Tất cả các biến này đều được định kiểu tĩnh tại thời điểm biên dịch
var name = "Alice";                     // String
var count = 42;                         // int
var list = new ArrayList<String>();     // ArrayList<String>

for (var entry : Map.of("k", 1).entrySet()) {
    // entry được suy luận là Map.Entry<String, Integer>
    System.out.println(entry.getKey() + "=" + entry.getValue());
}

// BẤT HỢP LỆ — trình biên dịch không có kiểu để suy luận
// var x;             // Error: không thể suy luận kiểu
// var y = null;      // Error: không thể suy luận kiểu từ null
// var z;             // Error: biến phải được khởi tạo
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

```text
Sử dụng `var x = new ArrayList<String>()`
  → Trình biên dịch đọc kiểu vế phải `ArrayList<String>`
  → Gán vế đó vĩnh viễn cho `x`
  → Mã bytecode được tạo ra giống hệt như khai báo tường minh `ArrayList<String> x`
  → Bất kỳ việc gán lại nào cho kiểu không tương thích đều gây ra lỗi biên dịch
  → Không tốn chi phí thời gian chạy, không có chi phí autoboxing.
```


---

## Tại sao Records Thực Thi Tính Bất Biến Qua Mã Nguồn Do Trình Biên Dịch Tạo Ra (Why Records Enforce Immutability Through Compiler-Generated Code)

Trước khi có Records, việc tạo một lớp dữ liệu bất biến đơn giản đòi hỏi phải viết một hàm khởi tạo, các trường `final`, các phương thức truy xuất accessor, `equals()`, `hashCode()`, và `toString()` — thường mất từ 50–100 dòng mã boilerplate cho một vật chứa dữ liệu đơn giản. Records loại bỏ điều này bằng cách mã hóa giao ước "lớp này là một vật chứa minh bạch của các thành phần được đặt tên của nó" trực tiếp vào trong ngôn ngữ.

Trình biên dịch tạo ra:
- **Các trường `private final`** cho mỗi thành phần của record — không thể thay đổi sau khi xây dựng.
- **Một hàm khởi tạo chuẩn (canonical constructor)** với tất cả các thành phần làm tham số để gán cho từng trường.
- **Các phương thức truy xuất (accessor methods)** được đặt tên theo các trường (ví dụ: `point.x()`, không phải `getX()`) — các phương thức truy xuất khớp với tên trường theo thiết kế để giúp việc truy cập thành phần dễ dàng phát hiện hơn.
- **`equals()` và `hashCode()`** so sánh tất cả các thành phần theo giá trị.
- **`toString()`** in ra tất cả các thành phần.

Records ngầm định là `final` và không thể kế thừa các lớp khác (chúng kế thừa ngầm lớp `java.lang.Record`). Điều này ngăn chặn việc thay đổi thông qua việc tạo lớp con. Cú pháp hàm khởi tạo rút gọn (compact constructor) cho phép xác thực mà không cần khai báo lại các tham số.

### Mô hình tư duy: Record vs Lớp Bất Biến Thủ Công (Mental Model: Record vs Manual Immutable Class)
```
// Lớp bất biến viết thủ công — ~60 dòng:
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

// Record tương đương — 1 dòng:
public record Point(int x, int y) {}
// Trình biên dịch tự động tạo ra tất cả những thứ trên
```

### Ví dụ Code: Records với xác thực (Code Example: Records with validation)
```java
public record User(String name, int age) {
    // Hàm khởi tạo rút gọn — không có danh sách tham số, sử dụng các phép gán ngầm định
    public User {
        if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
        name = name.strip(); // Có thể chuyển đổi giá trị trước khi gán
    }
}

User u = new User("Alice", 30);
System.out.println(u.name()); // "Alice" — accessor, không phải getName()
System.out.println(u.age());  // 30
System.out.println(u);        // User[name=Alice, age=30]

// Không thể thay đổi — các trường là final
// u.name = "Bob"; // COMPILE ERROR: không thể truy cập trường như vậy
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

```text
Khai báo `record Point(int x, int y)`
  → Trình biên dịch tạo các trường private final, hàm khởi tạo chuẩn, các phương thức truy xuất, equals/hashCode/toString
  → Tất cả các trường là final từ khi xây dựng
  → Không có phương thức thay đổi (mutator) nào được tạo ra
  → Record là bất biến theo thiết kế
  → An toàn khi chia sẻ giữa các luồng và sử dụng làm khóa cho Map.
```


---

## Tại sao Sealed Classes Cho Phép Khớp Mẫu Đầy Đủ An Toàn (Why Sealed Classes Enable Safe Exhaustive Pattern Matching)

Nếu không có các lớp niêm phong (sealed classes), trình biên dịch không thể biết tại thời điểm biên dịch tất cả các lớp con có thể có của một lớp trừu tượng hoặc giao diện là gì. Bất kỳ ai trong bất kỳ mô-đun nào cũng có thể kế thừa một lớp mở. Điều này có nghĩa là các biểu thức switch trên kiểu dữ liệu không thể có tính đầy đủ — trình biên dịch sẽ luôn yêu cầu một nhánh `default` để xử lý các lớp con không xác định.

Các lớp niêm phong khai báo chính xác lớp nào được phép triển khai hoặc kế thừa chúng trong mệnh đề `permits`. Trình biên dịch có thể liệt kê tập hợp hoàn chỉnh các kiểu dữ liệu có thể có. Khi một `sealed class` được sử dụng trong một biểu thức `switch` và tất cả các kiểu con được phép đều được bao gồm, trình biên dịch sẽ xác minh tính đầy đủ mà không yêu cầu trường hợp `default` — và sẽ báo lỗi biên dịch nếu một kiểu con được phép mới được thêm vào nhưng biểu thức switch chưa được cập nhật.

Điều này cho phép xây dựng "kiểu dữ liệu đại số đóng (closed-type algebraic data types)" vốn phổ biến trong Scala, Haskell, và Kotlin (các lớp `sealed`). Trình biên dịch trở thành người bảo đảm tính chính xác cho các rẽ nhánh dựa trên kiểu dữ liệu.

### Mô hình tư duy: Tính đầy đủ của phân cấp lớp Mở vs Niêm phong (Mental Model: Open vs Sealed class hierarchy exhaustiveness)
```
// Lớp mở — trình biên dịch không thể liệt kê tất cả các lớp con
abstract class Shape {}
// Switch BẮT BUỘC phải có default để đảm bảo tính đầy đủ
String describe = switch (shape) {
    case Circle c -> "circle with radius " + c.radius();
    default -> "unknown shape"; // Không thể loại bỏ dòng này một cách an toàn
};

// Lớp niêm phong — trình biên dịch biết tất cả các kiểu con được phép
sealed class Shape permits Circle, Square {}
// Switch đạt tính đầy đủ mà không cần default
String describe = switch (shape) {
    case Circle c -> "circle with radius " + c.radius();
    case Square s -> "square with side " + s.side();
    // Không cần default — trình biên dịch xác minh tất cả các kiểu con đã được bao phủ
};
// Nếu Rectangle được thêm vào permits nhưng không có trong switch → COMPILE ERROR
```

### Ví dụ Code: Sealed class với switch expression (Code Example: Sealed class with switch expression)
```java
public sealed interface Shape permits Circle, Square, Triangle {}
public record Circle(double radius) implements Shape {}
public record Square(double side) implements Shape {}
public record Triangle(double base, double height) implements Shape {}

// Switch đầy đủ — không yêu cầu default
double area = switch (shape) {
    case Circle c -> Math.PI * c.radius() * c.radius();
    case Square s -> s.side() * s.side();
    case Triangle t -> 0.5 * t.base() * t.height();
};
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

```text
khai báo `sealed` với `permits Circle, Square`
  → Trình biên dịch ghi lại tập hợp các kiểu con được phép
  → Switch trên `Shape` bao phủ `Circle` and `Square`
  → Trình biên dịch xác minh tính đầy đủ
  → Không yêu cầu `default`
  → Thêm `Triangle` vào mệnh đề permits mà không cập nhật biểu thức switch
  → Lỗi biên dịch.
```


---

## Tại sao Khớp Mẫu Cho Switch Yêu Cầu Sắp Xếp Theo Độ Cụ Thể (Why Pattern Matching for Switch Requires Ordering by Specificity)

Trình biên dịch Java thực thi một **quy tắc ưu tiên (dominance rule)** cho khớp mẫu trong switch: một mẫu cụ thể hơn không thể xuất hiện sau một mẫu tổng quát hơn vốn sẽ khớp với cùng các đầu vào đó. Nếu một trường hợp tổng quát xuất hiện trước, trường hợp cụ thể đi sau sẽ không bao giờ có thể tiếp cận được — nó bị "bao trùm (dominated)".

Đối với các mẫu được bảo vệ (guarded patterns) như `case String s when s.length() > 5`, mệnh đề bảo vệ `when s.length() > 5` là một tập hợp con của `case String s` (vốn khớp với tất cả các chuỗi String). Nếu `case String s` được liệt kê trước, trường hợp được bảo vệ `case String s when s.length() > 5` sẽ không bao giờ có thể thực thi — trường hợp không được bảo vệ đã chụp lại toàn bộ các chuỗi.

Đây là một kiểm tra ở thời điểm biên dịch (compile-time check). Trình biên dịch sẽ từ chối các câu lệnh switch trong đó một trường hợp bị bao trùm, ngăn chặn các lỗi ngầm nơi một trường hợp chính xác hơn vô tình bị che khuất.

### Mô hình tư duy: Quy tắc thứ tự ưu tiên (Mental Model: Dominance ordering rule)
```
// BẤT HỢP LỆ — case String s bao trùm trường hợp được bảo vệ bên dưới nó
switch (obj) {
    case String s -> "any string: " + s;          // Khớp với TẤT CẢ các chuỗi
    case String s when s.length() > 5 -> "long";  // COMPILE ERROR: bị bao trùm
}

// HỢP LỆ — trường hợp được bảo vệ cụ thể nhất được viết trước
switch (obj) {
    case String s when s.length() > 5 -> "long string: " + s; // Cụ thể
    case String s -> "short string: " + s;                    // Tổng quát
    case Integer i -> "integer: " + i;
    default -> "other";
}
```

### Ví dụ Code: Khớp mẫu switch với các điều kiện bảo vệ (Code Example: Pattern matching switch with guards)
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
System.out.println(desc); // Đầu ra: long string: Hello World
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

```text
Các trường hợp mẫu được đánh giá từ trên xuống dưới
  → Trường hợp tổng quát hơn `case String s` khớp với tất cả các chuỗi
  → Trường hợp cụ thể `case String s when ...` bên dưới nó không bao giờ có thể tiếp cận được
  → Lỗi biên dịch: mẫu bị bao trùm
  → Sắp xếp lại thứ tự: mẫu được bảo vệ (cụ thể) trước mẫu không được bảo vệ (tổng quát)
  → Trình biên dịch xác minh không có mẫu nào không thể tiếp cận
  → Tất cả các trường hợp đều có thể tiếp cận.
```


---

## Tại sao Luồng Ảo Ghim Luồng Vận Chuyển Trong Khối Synchronized (Why Virtual Threads Pin Carrier Threads in Synchronized Blocks)

Luồng ảo được quản lý bởi JVM trên một nhóm nhỏ các luồng hệ điều hành thực gọi là các luồng vận chuyển (carrier threads). Khi một luồng ảo cần bị chặn (đợi I/O, khóa, điều kiện), JVM **gắn (mount)** nó vào một luồng vận chuyển, và khi bị chặn, JVM **gỡ (unmount)** nó ra (đình chỉ trạng thái của luồng ảo trong khi giải phóng luồng vận chuyển cho các luồng ảo khác).

Vấn đề nằm ở khối `synchronized`. Khi một luồng ảo đi vào một khối `synchronized`, JVM phải giữ khóa màn giám sát (monitor lock) trên luồng OS gốc của luồng vận chuyển — đây là một hạn chế triển khai JVM của mô hình khóa HotSpot hiện tại. Nếu luồng ảo bị chặn trong khi đang giữ màn giám sát (ví dụ: chặn I/O bên trong khối synchronized), luồng vận chuyển sẽ bị "ghim (pinned)" — nó không thể được giải phóng để chạy các luồng ảo khác, làm mất đi toàn bộ mục đích về khả năng mở rộng của luồng ảo.

`ReentrantLock` không sử dụng các màn giám sát cấp hệ điều hành. Nó sử dụng các cấu trúc hàng đợi cấp JVM có thể liên kết với luồng ảo, chứ không phải luồng vận chuyển. Khi một luồng ảo bị chặn trên `ReentrantLock.lock()`, luồng vận chuyển sẽ được gỡ ra và giải phóng trong khi luồng ảo chờ đợi.

### Mô hình tư duy: Ghim của synchronized vs Gỡ của ReentrantLock (Mental Model: synchronized pinning vs ReentrantLock unmounting)
```
[synchronized — ghim luồng vận chuyển]
Luồng ảo 1 đi vào khối synchronized
    → Luồng ảo 1 bị chặn bên trong khối synchronized (chờ I/O)
    → Luồng vận chuyển OS 1 bị GHIM — không thể nhận luồng ảo khác
    → Chỉ có 1 luồng ảo chạy hiệu quả trên luồng vận chuyển đó — mất khả năng mở rộng

[ReentrantLock — cho phép gỡ luồng vận chuyển]
Luồng ảo 1 gọi reentrantLock.lock()
    → Luồng ảo 1 bị chặn trên lock()
    → JVM gỡ luồng ảo 1 khỏi luồng vận chuyển OS 1
    → Luồng vận chuyển OS 1 hiện được giải phóng để chạy luồng ảo 2, 3, ...
    → Khi khóa được giải phóng, luồng ảo 1 gắn lại vào bất kỳ luồng vận chuyển nào rảnh
```

### Ví dụ Code: Sử dụng ReentrantLock thay vì synchronized cho luồng ảo (Code Example: ReentrantLock instead of synchronized for virtual threads)
```java
import java.util.concurrent.locks.ReentrantLock;

public class SafeVirtualThreadCounter {
    private final ReentrantLock lock = new ReentrantLock(); // Sử dụng thay cho synchronized
    private int count = 0;

    public void increment() {
        lock.lock(); // Không ghim luồng vận chuyển
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }
}

// Tạo luồng ảo
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    for (int i = 0; i < 1_000_000; i++) {
        executor.submit(() -> counter.increment());
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

```text
Luồng ảo đi vào khối `synchronized`
  → JVM phải giữ màn giám sát cấp OS trên luồng vận chuyển
  → Luồng ảo bị chặn (I/O, chờ) bên trong khối synchronized
  → Luồng vận chuyển bị ghim và không khả dụng
  → Khả năng mở rộng luồng ảo bị giảm sút
  → Thay thế bằng `ReentrantLock`
  → Việc bị chặn trên khóa gỡ luồng ảo khỏi luồng vận chuyển
  → Luồng vận chuyển được giải phóng cho các luồng ảo khác
  → Khôi phục hoàn toàn khả năng xử lý đồng thời.
```


## Liên Kết Tham Khảo (Reference Links)

- https://openjdk.org/jeps/286 (JEP 286 — Local-Variable Type Inference: var)
- https://openjdk.org/jeps/395 (JEP 395 — Records)
- https://openjdk.org/jeps/409 (JEP 409 — Sealed Classes)
- https://openjdk.org/jeps/441 (JEP 441 — Pattern Matching for switch)
- https://openjdk.org/jeps/444 (JEP 444 — Virtual Threads)
