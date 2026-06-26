# Một số API Tiện ích Thông dụng - Phần 2 (Some Common Utility APIs - Part 2)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này trình bày các API về tài nguyên bản địa hóa, cấu hình thuộc tính, định dạng chuỗi tùy chỉnh và phân tích cú pháp nhập liệu. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế.

## Khái Quát Nội Dung (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `Properties` | Lớp cấu hình khóa-trị dạng văn bản (`java.util.Properties`). |
| `ResourceBundle` | Lớp quốc tế hóa (`java.util.ResourceBundle`) để tải các tệp dịch bản địa hóa. |
| `Locale` | Lớp đại diện cho văn hóa/địa lý (`java.util.Locale`). |
| `Currency` | Đại diện tiền tệ theo tiêu chuẩn ISO 4217 (`java.util.Currency`). |
| `Formatter` | Tiện ích định dạng (`java.util.Formatter`) cho việc chuyển đổi bố cục in ấn. |
| `Scanner` | Trình phân tích văn bản/luồng dữ liệu dạng thẻ (tokenized parser - `java.util.Scanner`). |

---

## Ghi Chú Chi Tiết (Detailed Notes)

### Properties

`java.util.Properties` là một lớp con của `Hashtable` được sử dụng để lưu trữ các cấu hình khóa-trị trong đó cả khóa và giá trị đều là các chuỗi (`String`). `Properties` có thể dễ dàng ghi vào hoặc đọc từ các luồng văn bản `.properties`.

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  Properties props = new Properties();
  
  // Load properties from a file
  try (InputStream input = new FileInputStream("config.properties")) {
      props.load(input);
  } catch (IOException ex) {
      System.out.println("Config file not found.");
  }

  // Get properties with a fallback default value
  String dbUser = props.getProperty("db.username");
  String dbPort = props.getProperty("db.port", "3306"); // defaults to 3306 if key not present
  ```

- **Sai lầm thường gặp / Chế độ thất bại (Common Mistake / Failure Mode)**:
  - **Sử dụng put/get của Map**: `Properties` triển khai `Map<Object, Object>` do kế thừa (Inheritance) từ `Hashtable`. Điều này cho phép đưa vào các khóa hoặc giá trị không phải kiểu `String` thông qua phương thức chung `.put(key, value)`. Việc này phá vỡ thiết kế cấu hình của `Properties` và kích hoạt lỗi `ClassCastException` nếu bạn cố gắng ghi các thuộc tính ra bằng `store()` hoặc `list()`. Hãy luôn sử dụng `setProperty(String, String)` và `getProperty(String)`.

### Tại sao thao tác trực tiếp dạng Map trên Properties lại Nguy hiểm (Why Direct Map Manipulation of Properties is Dangerous)

`java.util.Properties` được giới thiệu trong JDK 1.0 như một lớp con của `java.util.Hashtable`. Trong thiết kế Java hiện đại, mô hình kế thừa này được coi rộng rãi là vi phạm Nguyên lý thay thế Liskov (Liskov Substitution Principle - LSP). Vì kế thừa từ `Hashtable`, `Properties` để lộ các phương thức `Map` tiêu chuẩn như `put(Object, Object)` và `putAll(Map)`.

#### Vi phạm an toàn kiểu (The Type Safety Violation)
Hợp đồng của `Properties` chỉ định rằng cả khóa và giá trị phải thuộc kiểu `java.lang.String`. Tuy nhiên, vì phương thức `put()` kế thừa nhận các tham số kiểu `Object`, Java không thể ngăn chặn việc chèn các khóa hoặc giá trị không phải là `String` tại thời điểm biên dịch:
```java
Properties props = new Properties();
props.put("port", 8080); // Compiles perfectly! (Autoboxed to Integer)
```

#### Cơ chế thất bại dưới mui xe (Under-the-Hood Failure Mechanisms)
Khi bạn cố gắng ghi các thuộc tính này vào đĩa bằng phương thức `store(OutputStream, String)` hoặc in chúng bằng `list(PrintStream)`, các phương thức này lặp qua các khóa và giá trị, ép kiểu chúng về `String`. Nếu gặp phải một đối tượng không phải là `String`, JVM sẽ ném ra lỗi `ClassCastException` trong thời gian chạy, gây ra lỗi cấu hình ngầm làm treo ứng dụng trong quá trình tuần hoàn hóa (serialization).

#### Phép so sánh tương đồng về vi phạm LSP (LSP Violation Analogy)
Hãy tưởng tượng một hòm thư tiêu chuẩn (`Map<Object, Object>`) có thể nhận thư từ, tạp chí, gói bưu phẩm và rác. `Properties` giống như một hòm thư được dán nhãn cụ thể chỉ dành cho thư giấy. Bởi vì khe hòm thư là vật lý (`put(Object, Object)`), ai đó vẫn có thể thả một viên gạch (một đối tượng không phải là chuỗi) vào bên trong. Khi người đưa thư (`store()`) cố gắng mở hòm thư và chỉ mong đợi các bức thư để xử lý, họ sẽ bị thương bởi viên gạch (`ClassCastException`).

```mermaid
flowchart TD
    subgraph PropertiesInheritance ["Vi phạm LSP: Properties kế thừa Hashtable (LSP Violation)"]
        Hashtable["Hashtable&lt;Object,Object&gt;"] -->|Kế thừa put()| Properties["Properties (Mong đợi khóa/giá trị String)"]
    end
    subgraph Execution ["Luồng tuần hoàn hóa chạy thực tế (Runtime Serialization Flow)"]
        Properties -->|put('port', 8080)| MapState["Map nội bộ chứa khóa String và giá trị Integer"]
        MapState -->|Gọi store()| Loop["Lặp và ép kiểu khóa/giá trị về String"]
        Loop -->|Ép kiểu 8080 (Integer) về String| Crash["ClassCastException (Treo chương trình!)"]
    end
```

#### Chuỗi Nguyên Nhân - Kết Quả của lạm dụng Map (Cause-Effect Chain of Map Abuse)
```text
Properties kế thừa Hashtable → put(Object, Object) bị để lộ → Đối tượng không phải String được chèn vào Properties → Biên dịch hoàn tất thành công → store() được gọi trong lúc tắt ứng dụng → JVM cố gắng ép kiểu đối tượng không phải String về String → ClassCastException bị ném ra → Cấu hình lưu trữ thất bại
```

#### Ví dụ có thể chạy được (Runnable Example)
```java
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLspDemo {
    public static void main(String[] args) {
        Properties props = new Properties();

        // CORRECT: setProperty guarantees type safety
        props.setProperty("db.user", "admin");

        // DANGEROUS: put() bypasses the Properties String contract
        props.put("db.port", 3306); // Bypasses compiler warning, autoboxed to Integer

        // Reading via getProperty returns null if cast is required
        String portValue = props.getProperty("db.port");
        System.out.println("getProperty('db.port'): " + portValue); // Prints null because it is not a String!

        // Attempting to serialize properties
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // This throws ClassCastException because db.port is an Integer, not a String
            props.store(out, "Application Configuration");
        } catch (ClassCastException e) {
            System.out.println("Caught expected ClassCastException during store(): " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

### ResourceBundle

`java.util.ResourceBundle` chứa các tệp tài nguyên đặc trưng cho locale (thường là các bản dịch) cho phép bạn quốc tế hóa (I18N) ứng dụng của mình một cách dễ dàng.

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  // Loads MessagesBundle_fr_FR.properties
  Locale frenchLocale = new Locale("fr", "FR");
  ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", frenchLocale);
  
  String greeting = bundle.getString("welcome_message");
  ```

- **Sai lầm thường gặp / Chế độ thất bại (Common Mistake / Failure Mode)**:
  - **Thiếu gói cơ sở (Missing Base Bundle)**: Khi tra cứu các khóa dịch, nếu tệp thuộc tính locale cụ thể không được tìm thấy, Java sẽ tìm kiếm ngược chuỗi kế thừa locale (ví dụ: `fr_FR` -> `fr` -> Hệ Locale Mặc định của Hệ thống -> Gói cơ sở Base Bundle). Nếu khóa đó không nằm trong bất kỳ tệp thuộc tính nào, hoặc nếu tệp gói tài nguyên cơ sở (`MessagesBundle.properties`) hoàn toàn bị thiếu trong classpath, JVM sẽ ném ra lỗi `MissingResourceException` trong thời gian chạy. Hãy luôn đóng gói kèm một tệp thuộc tính cơ sở mặc định.

---

### Locale

Một đối tượng `java.util.Locale` đại diện cho một khu vực địa lý, chính trị hoặc văn hóa cụ thể. Nó được sử dụng để định dạng số, ngày tháng, tiền tệ và lựa chọn các bản dịch.

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  // Predefined constants
  Locale us = Locale.US;

  // Builder pattern (safest way to construct custom locales)
  Locale custom = new Locale.Builder().setLanguage("en").setRegion("GB").build();

  // Parsing a language tag (IETF BCP 47)
  Locale parsed = Locale.forLanguageTag("vi-VN");
  ```

- **Sai lầm thường gặp / Chế độ thất bại (Common Mistake / Failure Mode)**:
  - **Sử dụng sai hàm khởi tạo (Constructor Misuse)**: Khởi tạo một locale là `new Locale("en_US")` bằng cách truyền thẻ ngôn ngữ gộp. Hàm khởi tạo di sản (legacy) yêu cầu ngôn ngữ (`"en"`) và vùng miền (`"US"`) là các đối số riêng biệt. Việc truyền `"en_US"` dẫn đến một locale không hợp lệ với ngôn ngữ `"en_us"` và không có vùng miền, dẫn đến việc không tải được các gói tài nguyên phù hợp. Hãy sử dụng `Locale.forLanguageTag("en-US")` thay thế.

---

### Currency

Lớp `java.util.Currency` đại diện cho một loại tiền tệ theo tiêu chuẩn ISO 4217.

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  Currency usd = Currency.getInstance("USD");
  Locale locale = Locale.FRANCE;
  
  // Outputs "USD" or currency symbol "$" based on formatter locale
  String symbol = usd.getSymbol(locale); 
  ```

- **Sai lầm thường gặp / Chế độ thất bại (Common Mistake / Failure Mode)**:
  - Gọi `Currency.getInstance(locale)` cho một locale đại diện cho một quốc gia không có tiền tệ chính thức hoặc một thẻ vùng miền không hợp lệ, hành động này sẽ ném ra một `IllegalArgumentException`.

---

### Formatter

`java.util.Formatter` tạo ra các chuỗi văn bản được bản địa hóa định dạng theo kiểu in ấn của ngôn ngữ C.

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  StringBuilder sb = new StringBuilder();
  try (Formatter formatter = new Formatter(sb, Locale.US)) {
      formatter.format("Item: %s | Price: $%,.2f", "Laptop", 1249.99);
  }
  // sb now contains: "Item: Laptop | Price: $1,249.99"
  ```

- **Sai lầm thường gặp / Chế độ thất bại (Common Mistake / Failure Mode)**:
  - **Không khớp định dạng chuyển đổi (Conversion Mismatch)**: Khớp sai mã định dạng với một đối số (ví dụ: `%d` cho một số double hoặc `%f` cho một số nguyên) sẽ ném ra ngoại lệ `IllegalFormatConversionException` trong thời gian chạy.
  - **Rò rỉ luồng dữ liệu (Leaking Streams)**: Khi xây dựng `Formatter` xung quanh các tệp hoặc luồng đầu ra, việc không đóng formatter sẽ làm rò rỉ các mô tả tệp (file descriptors). Hãy luôn bọc chúng trong khối try-with-resources.

---

### Scanner

`java.util.Scanner` là một trình phân tích cú pháp văn bản đơn giản giúp chia luồng đầu vào thành các thẻ (tokens) bằng cách sử dụng dấu phân tách (mặc định là khoảng trắng) và phân tích các kiểu nguyên thủy hoặc chuỗi.

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  String input = "10 20 30";
  try (Scanner scanner = new Scanner(input)) {
      while (scanner.hasNextInt()) {
          int value = scanner.nextInt();
      }
  }
  ```

- **Sai lầm thường gặp / Chế độ thất bại (Common Mistake / Failure Mode)**:
  - **Bẫy của phương thức `nextLine()` (The nextLine() Pitfall)**: Đọc một giá trị nguyên thủy (như `nextInt()`) và sau đó gọi `nextLine()` để đọc văn bản. Các phương thức đọc kiểu nguyên thủy chỉ tiêu thụ phần thẻ (token), để lại ký tự xuống dòng `\n` trong bộ đệm. Lời gọi `nextLine()` tiếp theo sẽ tiêu thụ ký tự xuống dòng trống này ngay lập tức, bỏ qua dòng nhập liệu thực tế:
    ```java
    Scanner scanner = new Scanner(System.in);
    int age = scanner.nextInt();    // Consumes int, leaves '\n' in buffer
    scanner.nextLine();             // CRITICAL: Consume the leftover newline
    String name = scanner.nextLine(); // Now correctly waits for text input
    ```
  - **Tắt luồng System.in (System.in Shutdown)**: Đóng một `Scanner` bọc xung quanh `System.in` (ví dụ: `scanner.close()`) sẽ đóng chính luồng `System.in`. Vì bạn không thể mở lại luồng nhập chuẩn (standard input) trong một JVM đang chạy, bất kỳ nỗ lực đọc dữ liệu nào sau đó từ `System.in` sẽ bị lỗi treo chương trình. Tránh đóng các đối tượng Scanner bọc luồng `System.in`.

### Tại sao Bẫy nextLine() của Scanner lại Xảy ra (Why the Scanner nextLine() Pitfall Occurs)

Lớp `java.util.Scanner` là một trình phân tích dựa trên thẻ (token-based parser). Việc hiểu cơ chế con trỏ nội bộ và bộ đệm của scanner là rất quan trọng để tránh các lỗi bỏ qua nhập liệu phổ biến.

#### Cơ chế xử lý Token vs. Dòng (The Mechanics of Token vs. Line Processing)
- **Các phương thức dựa trên thẻ** (`nextInt()`, `nextDouble()`, `next()`):
  Các phương thức này bỏ qua bất kỳ dấu phân tách dẫn đầu nào (mặc định là khoảng trắng, tab, hoặc ký tự xuống dòng), quét bộ đệm để đọc các ký tự khớp với mẫu của chúng, và dừng đọc ngay sau mẫu khớp đó. Chúng **không tiêu thụ dấu phân tách theo sau** (chẳng hạn như ký tự xuống dòng `\n` được tạo ra khi người dùng nhấn Enter).
- **Các phương thức dựa trên dòng** (`nextLine()`):
  Phương thức này đọc bộ đệm từ vị trí con trỏ hiện tại cho đến khi nó gặp ký tự xuống dòng (`\n` hoặc `\r\n`), tiêu thụ toàn bộ dòng bao gồm cả ký tự xuống dòng đó, nhưng chỉ trả về văn bản *trước* ký tự xuống dòng.

#### Các bước hoạt động của Con trỏ và Bộ đệm (Cursor and Buffer Step-by-Step)
Khi người dùng nhập `42` và nhấn Enter, bộ đệm luồng đầu vào chứa:
`['4', '2', '\n']`

1. **`nextInt()` được gọi**:
   - Trình quét đọc `'4'` và `'2'`.
   - Nó phân tích cú pháp chúng thành số nguyên `42` và trả về.
   - Con trỏ dừng lại *trước* `'\n'`. Bộ đệm còn lại: `['\n']`.
2. **`nextLine()` được gọi**:
   - Trình quét bắt đầu đọc từ vị trí con trỏ hiện tại.
   - Nó ngay lập tức gặp `'\n'`.
   - Nó tiêu thụ `'\n'` và xóa nó khỏi bộ đệm.
   - Vì không có văn bản nào trước `'\n'`, nó trả về một chuỗi rỗng `""` ngay lập tức mà không cần đợi nhập liệu mới từ console.

#### Phép so sánh tương đồng về Trạng thái Bộ đệm (Buffer State Analogy)
Hãy tưởng tượng một băng chuyền chở các gói hàng (tokens) được phân tách bằng các miếng đệm nhựa (các dấu phân tách như `\n`). 
`nextInt()` giống như một cánh tay robot chỉ nhặt gói hàng (`42`), để lại miếng đệm (`\n`) trên băng chuyền. 
`nextLine()` giống như một cánh tay quét dọn sạch mọi thứ trên băng chuyền cho đến miếng đệm tiếp theo. Nếu miếng đệm (`\n`) đã là vật phẩm tiếp theo trên băng chuyền, cánh tay quét sẽ hoàn thành ngay lập tức và báo cáo rằng nó không quét được gì, để lại cho bạn một kết quả trống rỗng.

```mermaid
flowchart TD
    subgraph Step1 ["Trạng thái bộ đệm: Người dùng nhập '42\\n'"]
        B1["[ '4', '2', '\\n' ]"]
    end
    subgraph Step2 ["Sau khi gọi nextInt(): Con trỏ dừng trước '\\n'"]
        B2["[ '\\n' ]"]
        cur2["Vị trí con trỏ"] --> B2
    end
    subgraph Step3 ["Sau khi gọi nextLine(): '\\n' bị tiêu thụ, trả về chuỗi rỗng"]
        B3["[ ]"]
        res["Trả về chuỗi rỗng: ''"]
    end
    Step1 -->|nextInt() tiêu thụ '42'| Step2
    Step2 -->|nextLine() tiêu thụ '\\n'| Step3
```

#### Chuỗi Nguyên Nhân - Kết Quả của Bẫy nextLine() (Cause-Effect Chain of the nextLine() Pitfall)
```text
Người dùng nhập '42\n' → nextInt() chỉ đọc '42' → '\n' bị để lại ở đầu bộ đệm → nextLine() được gọi → Scanner phát hiện và tiêu thụ '\n' ngay lập tức → nextLine() trả về chuỗi rỗng → Code tiếp tục chạy mà không đợi nhập liệu mới
```

#### Ví dụ có thể chạy được (Runnable Example)
```java
import java.util.Scanner;

public class ScannerPitfallDemo {
    public static void main(String[] args) {
        String inputBuffer = "42\nJohn Doe\n";
        
        // Simulating the failure mode
        try (Scanner buggyScanner = new Scanner(inputBuffer)) {
            int age = buggyScanner.nextInt(); // Consumes "42", leaves "\n"
            String name = buggyScanner.nextLine(); // Consumes "\n" immediately, returning empty!
            
            System.out.println("Age: " + age);
            System.out.println("Buggy Name (should be John Doe): '" + name + "'");
        }

        // Simulating the correct approach
        try (Scanner correctScanner = new Scanner(inputBuffer)) {
            int age = correctScanner.nextInt(); // Consumes "42", leaves "\n"
            correctScanner.nextLine();          // CRITICAL: Consume and discard the leftover newline
            String name = correctScanner.nextLine(); // Now reads "John Doe"
            
            System.out.println("Age: " + age);
            System.out.println("Correct Name: '" + name + "'");
        }
    }
}
```
