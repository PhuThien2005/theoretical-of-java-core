# Thực hành tốt nhất trong Java - Phần 1 (Best Practices in Java - Part 1)

## Mục tiêu học tập

Tài liệu này tập trung vào các **Thực hành tốt nhất (Best Practices)** nền tảng trong Java liên quan đến đặt tên, thiết kế cấu trúc, hiệu quả cộng chuỗi, toán học chính xác, và quản lý tài nguyên/ngoại lệ an toàn. Hãy nghiên cứu từng khái niệm dưới dạng quy tắc Java thực tế.

## Đề cương chi tiết

| Khái niệm | Điều cần biết |
| --- | --- |
| `Name variables, functions, and classes clearly` | Đặt tên rõ ràng cho các biến, hàm và lớp: Các quy tắc để viết mã tự tài liệu hóa với các tên mô tả rõ ràng. |
| `Code according to convention` | Viết mã theo quy ước (Code according to convention): Tuân thủ các phong cách chuẩn camelCase, PascalCase, và UPPER_SNAKE_CASE. |
| `Do not overuse static` | Không lạm dụng static (Do not overuse static): Sự đánh đổi khi lạm dụng các trạng thái và phương thức static (khả năng kiểm thử, các vấn đề về xử lý đồng thời). |
| `Do not overuse inheritance` | Không lạm dụng kế thừa (Do not overuse inheritance): Các rủi ro liên kết chặt chẽ liên quan đến kế thừa lớp (`extends`). |
| `Prefer composition over inheritance` | Ưu tiên thành phần hơn kế thừa (Prefer composition over inheritance): Mẫu thiết kế đạt được hành vi bằng cách sử dụng các mối quan hệ tham chiếu đối tượng thay vì tạo lớp con. |
| `Override equals/hashCode correctly` | Ghi đè equals/hashCode chính xác: Duy trì giao ước logic nghiêm ngặt giữa `equals()` và `hashCode()`. |
| `Use StringBuilder when concatenating strings many times` | Sử dụng StringBuilder khi cộng chuỗi nhiều lần: Tối ưu hóa việc cộng chuỗi trong vòng lặp để tránh tạo ra các đối tượng String dư thừa. |
| `Use BigDecimal for money` | Sử dụng BigDecimal cho tiền tệ: Loại bỏ các sai số làm tròn của số dấu phẩy động nhị phân trong tính toán tiền tệ. |
| `Use try-with-resources` | Sử dụng try-with-resources: Tự động dọn dẹp các luồng tài nguyên có triển khai `AutoCloseable`. |
| `Do not catch overly broad Exception if unnecessary` | Không bắt Exception quá chung chung nếu không cần thiết: Tập trung bắt các ngoại lệ được kiểm tra (checked exception) cụ thể thay vì bắt một `Exception` chung chung. |

---

## Ghi chú chi tiết

### Đặt tên rõ ràng cho các biến, hàm và lớp (Name variables, functions, and classes clearly)

Chọn các tên mô tả và thể hiện rõ ý định để làm cho mã nguồn tự tài liệu hóa. Tránh sử dụng tên có một ký tự (ngoại trừ các biến chỉ số vòng lặp) và các từ viết tắt mơ hồ.

- **Ví dụ**:
  ```java
  // BAD:
  int d = 86400; // Unclear unit and purpose
  
  // GOOD:
  int secondsPerDay = 86400;
  ```

---

### Viết mã theo quy ước (Code according to convention)

Tuân thủ các quy ước giúp mã nguồn của bạn dễ đọc hơn đối với các nhà phát triển khác.

- **Các quy ước**:
  - **Lớp (Class)**: PascalCase (ví dụ: `OrderProcessor`)
  - **Phương thức & Biến**: camelCase (ví dụ: `processOrder`, `customerId`)
  - **Hằng số**: UPPER_SNAKE_CASE (ví dụ: `MAX_RETRY_COUNT`)

---

### Không lạm dụng static (Do not overuse static)

`static` chỉ ra rằng một thành viên thuộc về kiểu lớp thay vì các thực thể của lớp.

- **Sự đánh đổi**:
  - **Khả năng kiểm thử (Testability)**: Các phương thức tĩnh rất khó giả lập (mock) trong các bài kiểm thử đơn vị (unit test), khiến việc kiểm thử cô lập trở nên khó khăn.
  - **An toàn luồng (Thread-Safety)**: Lưu trữ trạng thái trong các biến tĩnh (ví dụ: ngữ cảnh yêu cầu của người dùng) tạo ra các vấn đề truy cập đồng thời trong các máy chủ ứng dụng đa luồng.
  - **Quy tắc**: Giới hạn việc sử dụng `static` cho các hàm tiện ích thuần túy (ví dụ: `Math.sqrt()`) và các hằng số thực sự.

---

### Không lạm dụng kế thừa (Do not overuse inheritance)

Kế thừa (`extends`) tạo ra một liên kết cứng nhắc tại thời điểm biên dịch giữa lớp cha và lớp con.

- **Rủi ro (Lớp cơ sở dễ vỡ - Fragile Base Class)**:
  - Nếu một lớp cha thay đổi chi tiết triển khai của nó, nó có thể phá vỡ các giả định của lớp con một cách âm thầm hoặc gây ra xung đột phương thức.
  - Các lớp con kế thừa *tất cả* các phương thức public/protected từ lớp cha, làm lộ ra các API có thể không có ý nghĩa đối với lớp con (vi phạm tính đóng gói).

---

### Ưu tiên thành phần hơn kế thừa (Prefer composition over inheritance)

Thay vì mở rộng các lớp để tái sử dụng hành vi, hãy thu được hành vi bằng cách giữ một tham chiếu đến một thực thể của lớp đó (mối quan hệ "has-a" thay vì mối quan hệ "is-a").

- **Ví dụ**:
  ```java
  // BAD: Inheritance couples SecureStack tightly to Stack
  class SecureStack extends Stack<String> {
      // inherits all Stack methods, exposing stack implementation details
  }

  // GOOD: Composition wraps Stack, exposing only safe methods
  class SecureStack {
      private final Stack<String> stack = new Stack<>();

      public void push(String item) {
          // validate and delegate
          stack.push(item);
      }
  }
  ```

---

### Ghi đè equals/hashCode chính xác (Override equals/hashCode correctly)

Nếu bạn ghi đè `equals()`, bạn **bắt buộc** phải ghi đè `hashCode()` để duy trì giao ước bằng nhau về mặt logic.

- **Giao ước**: Nếu `a.equals(b)` là true, thì `a.hashCode() == b.hashCode()` cũng phải trả về true.
- **Ví dụ**:
  ```java
  public class User {
      private String email;

      @Override
      public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;
          User user = (User) o;
          return Objects.equals(email, user.email);
      }

      @Override
      public int hashCode() {
          return Objects.hash(email); // Must match equals evaluation fields!
      }
  }
  ```
- **Bẫy**: Việc không ghi đè `hashCode()` đồng nghĩa với việc hai đối tượng người dùng khác biệt có email giống hệt nhau sẽ trả về các mã băm khác nhau, gây ra các bản ghi trùng lặp trong `HashSet` hoặc thất bại khi truy xuất trong `HashMap`.

---

### Sử dụng StringBuilder khi cộng chuỗi nhiều lần (Use StringBuilder when concatenating strings many times)

Vì các đối tượng `String` là bất biến trong Java, việc cộng các đối tượng chuỗi bên trong một vòng lặp bằng cách sử dụng toán tử `+` sẽ tạo ra một thực thể String mới sau mỗi lần lặp, dẫn đến độ phức tạp thời gian là $O(n^2)$.

- **Ví dụ**:
  ```java
  // BAD: Creates 10,000 temporary String objects in heap
  String result = "";
  for (int i = 0; i < 10000; i++) {
      result += i; 
  }

  // GOOD: Single buffer modified in-place, O(n) execution
  StringBuilder sb = new StringBuilder();
  for (int i = 0; i < 10000; i++) {
      sb.append(i);
  }
  String finalResult = sb.toString();
  ```

---

### Sử dụng BigDecimal cho tiền tệ (Use BigDecimal for money)

Các kiểu dấu phẩy động nhị phân (`double` và `float`) không thể biểu diễn chính xác các phân số cơ số 10 (như 0.1), điều này gây ra các sai số làm tròn. Luôn luôn sử dụng `BigDecimal` cho các tính toán tiền tệ.

- **Ví dụ**:
  ```java
  // BAD: prints 0.30000000000000004
  System.out.println(0.1 + 0.2); 

  // GOOD: prints 0.3 exactly
  BigDecimal val1 = new BigDecimal("0.1");
  BigDecimal val2 = new BigDecimal("0.2");
  System.out.println(val1.add(val2));
  ```

---

### Sử dụng try-with-resources

Luôn đóng các tay cầm tài nguyên (luồng, tệp, socket, kết nối cơ sở dữ liệu) có triển khai `AutoCloseable` bằng cách sử dụng câu lệnh try-with-resources để tránh rò rỉ tài nguyên hệ thống.

- **Ví dụ**:
  ```java
  // Automatically calls reader.close() when leaving block, even if an exception occurs
  try (BufferedReader reader = new BufferedReader(new FileReader("config.txt"))) {
      System.out.println(reader.readLine());
  } catch (IOException e) {
      System.out.println("Error reading file");
  }
  ```

---

### Không bắt Exception quá chung chung nếu không cần thiết (Do not catch overly broad Exception if unnecessary)

Bắt `Exception` hoặc `Throwable` mở rộng việc xử lý ngoại lệ để bắt tất cả các lớp con, bao gồm cả các lỗi thời gian chạy không được kiểm tra (unchecked runtime failure).

- **Bẫy**:
  ```java
  try {
      readConfigFile();
  } catch (Exception e) {
      // BAD: This catches IOException, but also masks NullPointerException, 
      // OutOfMemoryError, and other developer errors!
  }
  ```
- **Quy tắc**: Chỉ bắt các ngoại lệ được kiểm tra cụ thể mà phương thức của bạn mong đợi (ví dụ: `IOException`, `SQLException`). Hãy để các lỗi lập trình không mong muốn nổi lên trên để chúng có thể được sửa chữa.

---

## Tại sao đặt tên mô tả lại quan trọng

Đặt tên mang tính mô tả là một thực hành viết mã sạch cốt lõi nhằm giảm tải nhận thức (cognitive load) khi đọc và bảo trì phần mềm. Khi tên gọi thể hiện rõ ý định, tránh thông tin sai lệch và cung cấp các phân biệt có ý nghĩa, nhà phát triển có thể hiểu được logic mà không cần đọc chi tiết triển khai. Trong Java, tên biến cục bộ được trình biên dịch sử dụng để xây dựng Bảng biến cục bộ (Local Variable Table) phục vụ việc gỡ lỗi, nhưng ở cấp độ mã bytecode, JVM tham chiếu đến các biến bằng các ô chỉ số (ví dụ: `iload_1`, `dstore_2`). Vì trình biên dịch Java (`javac`) loại bỏ tên biến trong quá trình biên dịch (trừ khi được biên dịch với tùy chọn `-g`), các tên mô tả dài hoàn toàn không gây ra chi phí thời gian chạy hoặc giảm hiệu năng trong JVM. Việc sử dụng các biến một ký tự (ngoại trừ các bộ đếm vòng lặp) hoặc ký hiệu Hungarian (ví dụ: `iCount`, `strName`) làm giảm khả năng đọc khi dự án mở rộng, vì nó buộc người đọc phải theo dõi các mã hóa tùy ý thay vì các khái niệm nghiệp vụ.

### Mô hình tư duy: Tải nhận thức khi đọc mã nguồn

```text
Đặt tên mơ hồ:
[Mã: x = a * b / 100] ---> [Tra cứu ý nghĩa của 'a'] ---> [Tra cứu ý nghĩa của 'b'] ---> Quá tải nhận thức!

Đặt tên mô tả:
[Mã: tax = price * rate / 100] ---------------------------------------------> Hiểu ngay lập tức
```

### Ví dụ mã nguồn

```java
public class NamingDemo {
    public static void main(String[] args) {
        // Obscure: What are x, y, and z representing?
        double x = 150.0;
        double y = 0.08;
        double z = x * y;
        System.out.println("Result: " + z); // Result: 12.0

        // Descriptive: Intention-revealing variables
        double itemPrice = 150.0;
        double salesTaxRate = 0.08;
        double calculatedSalesTax = itemPrice * salesTaxRate;
        System.out.println("Calculated Sales Tax: " + calculatedSalesTax); // Calculated Sales Tax: 12.0
    }
}
```

### Chuỗi nguyên nhân - kết quả

```text
Sử dụng tên biến mang tính mô tả ➔ Bị trình biên dịch javac loại bỏ trong quá trình biên dịch ➔ Được dịch thành các dịch vị chỉ số bytecode (không tốn chi phí thời gian chạy) ➔ Người đọc quét tên biến thay vì theo dõi kiểu dữ liệu/mã hóa ➔ Ngăn ngừa quá tải nhận thức và các phản mẫu đặt tên (naming anti-pattern)
```

---

## Tại sao các hằng số giúp ngăn ngừa các số ma thuật (Magic Numbers)

Số ma thuật (magic number) là các giá trị literal được sử dụng trong mã nguồn mà không có lời giải thích, làm lu mờ ý định của nhà phát triển và dễ gây ra lỗi khi cập nhật. Việc trích xuất các giá trị này vào các hằng số có tên giúp nâng cao khả năng đọc và đảm bảo các thay đổi được khu trú ở một nơi định nghĩa duy nhất. Từ góc độ ngôn ngữ và JVM, các giá trị nguyên thủy hoặc `String` kiểu `public static final` được công nhận là hằng số thời gian biên dịch (compile-time constant). Trình biên dịch Java (`javac`) thực hiện nội tuyến hằng số (constant inlining), thay thế trực tiếp giá trị của hằng số vào bytecode của lớp tiêu thụ thay vì chèn một lệnh `getstatic` để tra cứu trường tại thời điểm chạy. Mặc dù tối ưu hóa này giảm chi phí phân giải trường lúc chạy, nó tạo ra một sự phụ thuộc biên dịch nhỏ: nếu giá trị của hằng số bị thay đổi, tất cả các lớp tham chiếu đến nó phải được biên dịch lại để phản ánh sự thay đổi đó trong bytecode được nội tuyến của chúng.

### Mô hình tư duy: Nội tuyến hằng số thời điểm biên dịch

```text
Mã nguồn:
class Config { public static final int MAX_LIMIT = 50; }
class Client { int limit = Config.MAX_LIMIT; }

Bytecode (sau khi chạy javac):
Config.class  <-- Chứa định nghĩa trường
Client.class  <-- Chứa trực tiếp giá trị literal 'bipush 50' (Đã nội tuyến! Không tham chiếu lúc chạy tới Config)
```

### Ví dụ mã nguồn

```java
public class ConstantsDemo {
    // Declared as a compile-time constant
    public static final int DAYS_IN_WEEK = 7;
    public static final double SALES_TAX_PERCENT = 8.25;

    public static void main(String[] args) {
        double subtotal = 100.0;
        // Bad: Magic number 8.25 makes it hard to know if it's tax, interest, or a discount
        double taxAmountBad = subtotal * (8.25 / 100.0);
        System.out.println("Tax: " + taxAmountBad); // Tax: 8.25

        // Good: Named constant clarifies intent and is inlined at compile-time
        double taxAmountGood = subtotal * (SALES_TAX_PERCENT / 100.0);
        System.out.println("Tax: " + taxAmountGood); // Tax: 8.25
    }
}
```

### Chuỗi nguyên nhân - kết quả

```text
Trích xuất giá trị literal vào hằng số public static final ➔ javac xác định giá trị là hằng số thời gian biên dịch ➔ Giá trị hằng số được nội tuyến trực tiếp vào bytecode tiêu thụ ➔ Tránh được lệnh tra cứu getstatic của JVM lúc chạy ➔ Ngăn ngừa số ma thuật & tối ưu hóa tốc độ thực thi
```

---

## Tại sao các mệnh đề bảo vệ (Guard Clause) đơn giản hóa luồng điều khiển

Mô hình mệnh đề bảo vệ (guard clause) "Trả về sớm" hoặc "Thất bại ngay" thay thế các khối điều kiện lồng nhau sâu bằng các câu lệnh thoát sớm. Các khối điều kiện lồng nhau yêu cầu nhà phát triển phải duy trì một ngăn xếp tinh thần phức tạp của các tiền điều kiện để theo dõi các đường dẫn thực thi, điều này làm tăng tải nhận thức theo cấp số nhân. Ngược lại, các mệnh đề bảo vệ xử lý các trạng thái không hợp lệ hoặc các trường hợp tầm thường trước và thoát ra ngay lập tức, cho phép người đọc bỏ qua các đường dẫn đó trong phần còn lại của phương thức. Từ góc độ JVM, các mệnh đề bảo vệ tạo ra một Đồ thị luồng điều khiển (Control Flow Graph - CFG) phẳng hơn, tuyến tính hơn trong bytecode đã biên dịch. Cấu trúc tinh gọn này hỗ trợ các lượt tối ưu hóa của trình biên dịch JIT, giúp việc dự đoán nhánh (branch prediction) hiệu quả hơn và tăng khả năng nội tuyến phương thức thành công bằng cách tránh lồng ghép sâu.

### Mô hình tư duy: if-else lồng nhau so với Mệnh đề bảo vệ

```text
if-else lồng nhau (Ngăn xếp sâu):
[Kiểm tra A] ---> Có ---> [Kiểm tra B] ---> Có ---> [Xử lý logic cốt lõi]
    |                        |
  Không (Thoát)            Không (Thoát)

Mệnh đề bảo vệ (Đường dẫn tuyến tính):
[Kiểm tra A không tốt] ---> Ném ngoại lệ/Trả về (Thoát sớm)
[Kiểm tra B không tốt] ---> Ném ngoại lệ/Trả về (Thoát sớm)
[Xử lý logic cốt lõi] (Happy Path - Không lồng nhau)
```

### Ví dụ mã nguồn

```java
public class GuardClauseDemo {
    public static String processOrderNested(String orderId, int quantity) {
        if (orderId != null) {
            if (quantity > 0) {
                return "Order " + orderId + " processed successfully.";
            } else {
                return "Invalid quantity.";
            }
        } else {
            return "Invalid order ID.";
        }
    }

    public static String processOrderGuard(String orderId, int quantity) {
        // Guard clauses (fail fast)
        if (orderId == null) return "Invalid order ID.";
        if (quantity <= 0) return "Invalid quantity.";

        // Core business logic (happy path)
        return "Order " + orderId + " processed successfully.";
    }

    public static void main(String[] args) {
        System.out.println(processOrderNested("101", 5)); // Order 101 processed successfully.
        System.out.println(processOrderGuard("101", 5));  // Order 101 processed successfully.
    }
}
```

### Chuỗi nguyên nhân - kết quả

```text
Các mệnh đề bảo vệ đánh giá các đầu vào không hợp lệ trước ➔ Phương thức trả về hoặc ném ngoại lệ ngay lập tức khi thất bại ➔ Logic nghiệp vụ cốt lõi tiến hành mà không cần các khối lồng nhau thụt lề ➔ Đồ thị luồng điều khiển phẳng được tạo ra trong bytecode ➔ Giảm tải nhận thức cho nhà phát triển & tối ưu hóa dự đoán nhánh của JIT
```

---

## Liên kết tham khảo

- [Oracle Java Documentation](https://docs.oracle.com/javase/specs/jls/se21/html/index.html)
- [Oracle Java SE Naming Conventions](https://docs.oracle.com/javase/specs/jls/se21/html/jls-6.html#jls-6.1)
- [Java JIT Compiler Optimizations](https://docs.oracle.com/en/java/javase/21/gctuning/)
