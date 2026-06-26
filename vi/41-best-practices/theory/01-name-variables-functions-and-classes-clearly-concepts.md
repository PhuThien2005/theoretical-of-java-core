# Các Thực Hành Tốt Nhất trong Java - Phần 1 (Best Practices in Java - Part 1)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này trình bày các **Thực Hành Tốt Nhất (Best Practices)** nền tảng trong Java liên quan đến việc đặt tên, thiết kế cấu trúc, hiệu quả nối chuỗi, số học chính xác, và quản lý ngoại lệ/tài nguyên an toàn. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế.

## Khái Quát Nội Dung (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `Name variables, functions, and classes clearly` | Các quy tắc viết mã nguồn tự tài liệu hóa với các tên gọi mang tính mô tả. |
| `Code according to convention` | Tuân thủ các phong cách chuẩn camelCase, PascalCase, và UPPER_SNAKE_CASE. |
| `Do not overuse static` | Sự đánh đổi khi lạm dụng trạng thái và phương thức static (vấn đề về khả năng kiểm thử và xử lý đồng thời). |
| `Do not overuse inheritance` | Rủi ro liên kết chặt chẽ liên quan đến kế thừa lớp (`extends`). |
| `Prefer composition over inheritance` | Mô hình đạt được hành vi bằng cách sử dụng các mối quan hệ tham chiếu đối tượng thay vì tạo lớp con (subclassing). |
| `Override equals/hashCode correctly` | Duy trì giao ước logic nghiêm ngặt giữa `equals()` và `hashCode()`. |
| `Use StringBuilder when concatenating strings many times` | Tối ưu hóa việc nối chuỗi trong vòng lặp để tránh tạo ra quá nhiều đối tượng String. |
| `Use BigDecimal for money` | Loại bỏ các lỗi làm tròn dấu phẩy động nhị phân trong các phép toán tiền tệ. |
| `Use try-with-resources` | Tự động dọn dẹp các luồng tài nguyên triển khai giao diện `AutoCloseable`. |
| `Do not catch overly broad Exception if unnecessary` | Nhắm mục tiêu bắt các ngoại lệ có kiểm tra cụ thể thay vì bắt `Exception` chung chung. |

---

## Ghi Chú Chi Tiết (Detailed Notes)

### Đặt tên biến, hàm và lớp một cách rõ ràng (Name variables, functions, and classes clearly)

Chọn các tên gọi mang tính mô tả, thể hiện rõ ý định để giúp mã nguồn tự tài liệu hóa. Tránh sử dụng tên một ký tự (ngoại trừ các biến đếm chỉ số vòng lặp) và các từ viết tắt khó hiểu.

- **Ví dụ**:
  ```java
  // BAD:
  int d = 86400; // Unclear unit and purpose
  
  // GOOD:
  int secondsPerDay = 86400;
  ```

---

### Viết mã theo đúng quy ước (Code according to convention)

Tuân thủ các quy ước giúp mã nguồn của bạn dễ đọc hơn đối với các nhà phát triển khác.

- **Quy ước**:
  - **Lớp (Classes)**: PascalCase (ví dụ: `OrderProcessor`)
  - **Phương thức & Biến (Methods & Variables)**: camelCase (ví dụ: `processOrder`, `customerId`)
  - **Hằng số (Constants)**: UPPER_SNAKE_CASE (ví dụ: `MAX_RETRY_COUNT`)

---

### Không lạm dụng static (Do not overuse static)

`static` chỉ ra rằng một thành viên thuộc về kiểu lớp (class type) thay vì thuộc về các thực thể lớp (class instances).

- **Sự đánh đổi**:
  - **Khả năng kiểm thử (Testability)**: Các phương thức static rất khó giả lập (mock) trong các bài kiểm thử đơn vị, gây khó khăn cho việc kiểm thử cô lập.
  - **An toàn luồng (Thread-Safety)**: Lưu trữ trạng thái trong các biến static (ví dụ: ngữ cảnh yêu cầu của người dùng - user request context) tạo ra các vấn đề truy cập đồng thời trong các máy chủ ứng dụng đa luồng.
  - **Quy tắc**: Giới hạn `static` cho các hàm tiện ích thuần túy (ví dụ: `Math.sqrt()`) và các hằng số thực sự.

---

### Không lạm dụng kế thừa (Do not overuse inheritance)

Kế thừa (`extends`) tạo ra một liên kết cứng nhắc tại thời điểm biên dịch giữa lớp cha và lớp con.

- **Rủi ro (Fragile Base Class)**:
  - Nếu một lớp cha thay đổi các chi tiết triển khai của nó, nó có thể âm thầm phá vỡ các giả định của lớp con hoặc gây ra xung đột phương thức.
  - Các lớp con kế thừa *tất cả* các phương thức public/protected từ các lớp cha, làm để lộ các API vốn có thể không có ý nghĩa đối với lớp con (vi phạm tính đóng gói).

---

### Ưu tiên lắp ghép hơn kế thừa (Prefer composition over inheritance)

Thay vì mở rộng các lớp để tái sử dụng hành vi, hãy có được hành vi bằng cách giữ một tham chiếu đến một thực thể của lớp đó (mối quan hệ "has-a" thay vì mối quan hệ "is-a").

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  // BAD: Kế thừa liên kết SecureStack chặt chẽ với Stack
  class SecureStack extends Stack<String> {
      // kế thừa tất cả các phương thức của Stack, làm lộ chi tiết triển khai của stack
  }

  // GOOD: Lắp ghép bọc lấy Stack, chỉ để lộ các phương thức an toàn
  class SecureStack {
      private final Stack<String> stack = new Stack<>();

      public void push(String item) {
          // xác thực và ủy quyền
          stack.push(item);
      }
  }
  ```

---

### Ghi đè equals/hashCode đúng cách (Override equals/hashCode correctly)

Nếu bạn ghi đè `equals()`, bạn **phải** ghi đè `hashCode()` để duy trì giao ước bằng nhau về mặt logic (logical equality contract).

- **Giao ước (The Contract)**: Nếu `a.equals(b)` là true, thì `a.hashCode() == b.hashCode()` cũng phải được đánh giá là true.
- **Ví dụ có thể chạy được (Runnable Example)**:
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
- **Cạm bẫy (Pitfall)**: Việc không ghi đè `hashCode()` có nghĩa là hai đối tượng người dùng riêng biệt có email giống hệt nhau sẽ trả về các mã băm khác nhau, gây ra các bản ghi trùng lặp trong `HashSet` hoặc thất bại khi truy xuất trong `HashMap`.

---

### Sử dụng StringBuilder khi nối chuỗi nhiều lần (Use StringBuilder when concatenating strings many times)

Vì các đối tượng `String` là bất biến trong Java, việc nối các đối tượng chuỗi bên trong một vòng lặp bằng toán tử `+` sẽ tạo ra một thực thể String mới trên mỗi vòng lặp, dẫn đến độ phức tạp thời gian là $O(n^2)$.

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  // BAD: Tạo ra 10,000 đối tượng String tạm thời trong heap
  String result = "";
  for (int i = 0; i < 10000; i++) {
      result += i; 
  }

  // GOOD: Một bộ đệm duy nhất được sửa đổi tại chỗ, thực thi trong O(n)
  StringBuilder sb = new StringBuilder();
  for (int i = 0; i < 10000; i++) {
      sb.append(i);
  }
  String finalResult = sb.toString();
  ```

---

### Sử dụng BigDecimal cho tiền tệ (Use BigDecimal for money)

Các kiểu số thực dấu phẩy động nhị phân (`double` và `float`) không thể biểu diễn chính xác các phân số của cơ số 10 (như 0.1), điều này gây ra các lỗi làm tròn số. Hãy luôn sử dụng `BigDecimal` cho các phép toán tiền tệ.

- **Ví dụ có thể chạy được (Runnable Example)**:
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

Luôn đóng các đối tượng quản lý tài nguyên (luồng dữ liệu, tệp, socket, kết nối cơ sở dữ liệu) triển khai `AutoCloseable` bằng cách sử dụng câu lệnh try-with-resources để tránh rò rỉ tài nguyên hệ thống.

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  // Tự động gọi reader.close() khi rời khỏi khối lệnh, ngay cả khi xảy ra ngoại lệ
  try (BufferedReader reader = new BufferedReader(new FileReader("config.txt"))) {
      System.out.println(reader.readLine());
  } catch (IOException e) {
      System.out.println("Error reading file");
  }
  ```

---

### Không bắt Exception quá chung chung nếu không cần thiết (Do not catch overly broad Exception if unnecessary)

Việc bắt `Exception` hoặc `Throwable` mở rộng phạm vi xử lý ngoại lệ để bắt tất cả các lớp con, bao gồm cả các lỗi thời gian chạy không có kiểm tra (unchecked runtime failures).

- **Cạm bẫy (Pitfall)**:
  ```java
  try {
      readConfigFile();
  } catch (Exception e) {
      // BAD: Việc này bắt IOException, nhưng cũng che khuất NullPointerException, 
      // OutOfMemoryError, và các lỗi lập trình khác!
  }
  ```
- **Quy tắc**: Chỉ bắt các ngoại lệ có kiểm tra cụ thể mà phương thức của bạn mong đợi (ví dụ: `IOException`, `SQLException`). Hãy để các lỗi lập trình không mong đợi lan truyền ngược lên (bubble up) để chúng có thể được sửa chữa.

---

## Tại sao việc đặt tên mô tả lại Quan trọng (Why Descriptive Naming Matters)

Đặt tên mang tính mô tả là một thực hành viết mã sạch cốt lõi giúp giảm tải nhận thức khi đọc và bảo trì phần mềm. Khi tên gọi tiết lộ ý định, tránh thông tin sai lệch và cung cấp các sự phân biệt có ý nghĩa, các nhà phát triển có thể hiểu được logic mà không cần đọc các chi tiết triển khai. Trong Java, tên các biến cục bộ được trình biên dịch sử dụng để xây dựng Bảng Biến Cục Bộ (Local Variable Table) phục vụ gỡ lỗi, nhưng ở cấp độ mã bytecode, JVM tham chiếu đến các biến bằng các ô chỉ số (ví dụ: `iload_1`, `dstore_2`). Bởi vì trình biên dịch Java (`javac`) loại bỏ các tên biến trong quá trình biên dịch (trừ khi được biên dịch với flag `-g`), các tên mô tả dài hoàn toàn không mang lại bất kỳ chi phí thời gian chạy hay hình phạt hiệu năng nào trong JVM. Việc sử dụng các biến một ký tự (ngoại trừ làm biến đếm vòng lặp) hoặc ký hiệu Hungarian (ví dụ: `iCount`, `strName`) làm giảm khả năng đọc khi quy mô dự án mở rộng, vì nó buộc người đọc phải theo dõi các mã hóa tùy ý thay vì các khái niệm nghiệp vụ của miền.

### Mô hình tư duy: Tải Nhận Thức Khi Đọc Mã Nguồn (Mental Model: Cognitive Load in Reading Code)

```text
Đặt tên khó hiểu:
[Mã nguồn: x = a * b / 100] ---> [Tra cứu ý nghĩa của 'a'] ---> [Tra cứu ý nghĩa của 'b'] ---> Quá tải nhận thức!

Đặt tên mô tả:
[Mã nguồn: tax = price * rate / 100] ---------------------------------------------> Thấu hiểu ngay lập tức
```

### Ví dụ Code

```java
public class NamingDemo {
    public static void main(String[] args) {
        // Khó hiểu: x, y, và z đại diện cho cái gì?
        double x = 150.0;
        double y = 0.08;
        double z = x * y;
        System.out.println("Result: " + z); // Result: 12.0

        // Mô tả: Các biến tiết lộ ý định rõ ràng
        double itemPrice = 150.0;
        double salesTaxRate = 0.08;
        double calculatedSalesTax = itemPrice * salesTaxRate;
        System.out.println("Calculated Sales Tax: " + calculatedSalesTax); // Calculated Sales Tax: 12.0
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

```text
Sử dụng tên biến mang tính mô tả → Bị trình biên dịch javac loại bỏ trong quá trình biên dịch → Được dịch thành các dịch vị chỉ số bytecode (không tốn chi phí thời gian chạy) → Người đọc quét các tên gọi thay vì theo dõi các kiểu/mã hóa → Ngăn chặn quá tải nhận thức và các phản khuôn mẫu đặt tên
```

---

## Tại sao Hằng số Giúp Ngăn chặn Con số Ma thuật (Why Constants Prevent Magic Numbers)

Các con số ma thuật là các giá trị trực tiếp được sử dụng trong mã nguồn mà không có giải thích, điều này che khuất ý định của nhà phát triển và làm cho các cập nhật dễ xảy ra lỗi. Việc trích xuất các giá trị này thành các hằng số có tên giúp cải thiện khả năng đọc và đảm bảo các thay đổi được khu trú vào một định nghĩa duy nhất. Từ góc độ ngôn ngữ và JVM, các giá trị nguyên thủy hoặc `String` khai báo `public static final` được công nhận là các hằng số tại thời điểm biên dịch. Trình biên dịch Java (`javac`) thực hiện cơ chế chèn hằng số trực tiếp (constant inlining), thay thế giá trị của hằng số trực tiếp vào mã bytecode của lớp tiêu thụ thay vì chèn một lệnh `getstatic` để tra cứu trường trong thời gian chạy. Mặc dù sự tối ưu hóa này làm giảm chi phí phân giải trường trong thời gian chạy, nó tạo ra một sự phụ thuộc biên dịch tinh tế: nếu giá trị của một hằng số bị sửa đổi, tất cả các lớp tham chiếu đến nó phải được biên dịch lại để phản ánh sự thay đổi trong mã bytecode được inlined của chúng.

### Mô hình tư duy: Chèn Hằng Số Trực Tiếp ở Thời Điểm Biên Dịch (Mental Model: Compile-Time Constant Inlining)

```text
Mã nguồn (Source Code):
class Config { public static final int MAX_LIMIT = 50; }
class Client { int limit = Config.MAX_LIMIT; }

Mã bytecode (sau khi biên dịch bằng javac):
Config.class  <-- Chứa định nghĩa trường
Client.class  <-- Chứa giá trị trực tiếp 'bipush 50' (Được inlined! Không có tham chiếu thời gian chạy đến Config)
```

### Ví dụ Code

```java
public class ConstantsDemo {
    // Được khai báo là một hằng số tại thời điểm biên dịch
    public static final int DAYS_IN_WEEK = 7;
    public static final double SALES_TAX_PERCENT = 8.25;

    public static void main(String[] args) {
        double subtotal = 100.0;
        // Bad: Con số ma thuật 8.25 làm khó biết đó là thuế, lãi suất hay chiết khấu
        double taxAmountBad = subtotal * (8.25 / 100.0);
        System.out.println("Tax: " + taxAmountBad); // Tax: 8.25

        // Good: Hằng số có tên làm rõ ý định và được inlined tại thời điểm biên dịch
        double taxAmountGood = subtotal * (SALES_TAX_PERCENT / 100.0);
        System.out.println("Tax: " + taxAmountGood); // Tax: 8.25
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

```text
Trích xuất giá trị trực tiếp thành hằng số public static final → javac xác định giá trị là hằng số biên dịch → Giá trị hằng số được inlined trực tiếp vào mã bytecode tiêu thụ → Tránh được các lệnh tra cứu getstatic của JVM trong thời gian chạy → Ngăn chặn các con số ma thuật & tối ưu hóa tốc độ thực thi
```

---

## Tại sao các Mệnh đề Bảo vệ Giúp Đơn giản hóa Luồng Kiểm soát (Why Guard Clauses Simplify Control Flow)

Mô hình mệnh đề bảo vệ "Trả về sớm" (Return Early) hoặc "Thất bại nhanh" (Fail Fast) thay thế các khối điều kiện lồng nhau sâu bằng các câu lệnh thoát sớm. Các khối điều kiện lồng nhau yêu cầu các nhà phát triển duy trì một bộ nhớ tạm thời phức tạp trong đầu về các điều kiện tiên quyết để theo dõi các đường dẫn thực thi, điều này làm tăng tải nhận thức theo cấp số nhân. Ngược lại, các mệnh đề bảo vệ xử lý các trạng thái không hợp lệ hoặc các trường hợp tầm thường trước và thoát ra ngay lập tức, cho phép người đọc bỏ qua các đường dẫn đó trong phần còn lại của phương thức. Từ góc độ JVM, các mệnh đề bảo vệ tạo ra một Biểu đồ Luồng Kiểm Soát (Control Flow Graph - CFG) phẳng hơn, tuyến tính hơn trong mã bytecode được biên dịch. Cấu trúc tinh giản này hỗ trợ các lượt tối ưu hóa của trình biên dịch JIT, giúp dự đoán nhánh hoạt động hiệu quả hơn và tăng khả năng biên dịch inlining phương thức thành công bằng cách tránh lồng nhau sâu.

### Mô hình tư duy: if-else lồng nhau vs. Luồng Mệnh đề Bảo vệ (Mental Model: Nested if-else vs. Guard Clause Flow)

```text
if-else lồng nhau (Lồng nhau sâu):
[Kiểm tra A] ---> Có ---> [Kiểm tra B] ---> Có ---> [Xử lý Logic cốt lõi]
    |                         |
    Không (Thoát)             Không (Thoát)

Các mệnh đề bảo vệ (Đường dẫn tuyến tính):
[Kiểm tra A là Xấu] ---> Ném lỗi/Trả về (Thoát sớm)
[Kiểm tra B là Xấu] ---> Ném lỗi/Trả về (Thoát sớm)
[Xử lý Logic cốt lõi] (Đường dẫn chính thuận lợi - Không lồng nhau)
```

### Ví dụ Code

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
        // Các mệnh đề bảo vệ (thất bại nhanh)
        if (orderId == null) return "Invalid order ID.";
        if (quantity <= 0) return "Invalid quantity.";

        // Logic nghiệp vụ cốt lõi (luồng chính thuận lợi)
        return "Order " + orderId + " processed successfully.";
    }

    public static void main(String[] args) {
        System.out.println(processOrderNested("101", 5)); // Order 101 processed successfully.
        System.out.println(processOrderGuard("101", 5));  // Order 101 processed successfully.
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

```text
Các mệnh đề bảo vệ đánh giá đầu vào không hợp lệ trước → Phương thức trả về hoặc ném ngoại lệ ngay lập tức khi thất bại → Logic nghiệp vụ cốt lõi tiếp tục mà không cần các khối lồng thụt lề → Biểu đồ luồng kiểm soát phẳng được tạo ra trong bytecode → Giảm tải nhận thức cho nhà phát triển & tối ưu hóa dự đoán nhánh JIT
```

---

## Liên Kết Tham Khảo (Reference Links)

- [Oracle Java Documentation](https://docs.oracle.com/javase/specs/jls/se21/html/index.html)
- [Oracle Java SE Naming Conventions](https://docs.oracle.com/javase/specs/jls/se21/html/jls-6.html#jls-6.1)
- [Java JIT Compiler Optimizations](https://docs.oracle.com/en/java/javase/21/gctuning/)
