# Một Số Utility API Thông Dụng — Phần 2

## Mục Tiêu Học Tập

File này trình bày các API tiện ích về tài nguyên đa ngôn ngữ, cấu hình properties, định dạng chuỗi tùy chỉnh và phân tích đầu vào. Học từng khái niệm như một quy tắc Java thực tiễn.

## Nội Dung Đề Cương

| Khái Niệm | Cần Biết |
| --- | --- |
| `Properties` | Lớp cấu hình key-value dạng văn bản (`java.util.Properties`). |
| `ResourceBundle` | Lớp quốc tế hóa (`java.util.ResourceBundle`) để tải file dịch theo vùng. |
| `Locale` | Lớp biểu diễn vùng địa lý/văn hóa (`java.util.Locale`). |
| `Currency` | Biểu diễn tiền tệ ISO 4217 (`java.util.Currency`). |
| `Formatter` | Tiện ích định dạng (`java.util.Formatter`) cho bố cục in kiểu C. |
| `Scanner` | Trình phân tích văn bản/luồng dựa trên token (`java.util.Scanner`). |

---

## Ghi Chú Chi Tiết

### Properties

`java.util.Properties` là lớp con của `Hashtable` dùng để lưu trữ cấu hình key-value mà cả khóa và giá trị đều là `String`. Properties có thể dễ dàng ghi vào hay đọc từ các luồng văn bản `.properties`.

- **Ví dụ chạy được**:
  ```java
  Properties props = new Properties();
  
  // Tải properties từ file
  try (InputStream input = new FileInputStream("config.properties")) {
      props.load(input);
  } catch (IOException ex) {
      System.out.println("Không tìm thấy file cấu hình.");
  }

  // Lấy properties với giá trị mặc định dự phòng
  String dbUser = props.getProperty("db.username");
  String dbPort = props.getProperty("db.port", "3306"); // mặc định 3306 nếu không có key
  ```

- **Lỗi Thường Gặp / Chế Độ Thất Bại**:
  - **Dùng Map put/get Trực Tiếp**: `Properties` triển khai `Map<Object, Object>` do kế thừa từ `Hashtable`. Điều này cho phép đưa các key hay value không phải `String` qua phương thức `.put(key, value)` chung. Làm vậy phá vỡ thiết kế cấu hình Properties và kích hoạt `ClassCastException` nếu bạn cố ghi bằng `store()` hay `list()`. Luôn dùng `setProperty(String, String)` và `getProperty(String)`.

### Tại Sao Thao Tác Trực Tiếp Map Lên Properties Là Nguy Hiểm

`java.util.Properties` được giới thiệu trong JDK 1.0 là lớp con của `java.util.Hashtable`. Trong thiết kế Java hiện đại, mô hình kế thừa này bị coi rộng rãi là vi phạm Nguyên Tắc Thay Thế Liskov (Liskov Substitution Principle — LSP). Vì kế thừa từ `Hashtable`, `Properties` lộ ra các phương thức `Map` tiêu chuẩn như `put(Object, Object)` và `putAll(Map)`.

#### Vi Phạm An Toàn Kiểu (Type Safety)
Hợp đồng của `Properties` chỉ định rằng cả khóa và giá trị phải là `java.lang.String`. Tuy nhiên, vì phương thức `put()` kế thừa nhận tham số `Object`, Java không thể ngăn các lần chèn thời gian biên dịch với khóa hay giá trị không phải `String`:
```java
Properties props = new Properties();
props.put("port", 8080); // Biên dịch hoàn toàn! (Tự động đóng hộp thành Integer)
```

#### Cơ Chế Thất Bại Bên Dưới
Khi bạn cố ghi những properties này ra đĩa bằng `store(OutputStream, String)` hay in bằng `list(PrintStream)`, các phương thức này duyệt qua các khóa và giá trị, cast chúng sang `String`. Nếu gặp đối tượng không phải `String`, JVM ném `ClassCastException` tại runtime, khiến lỗi cấu hình âm thầm gây sập ứng dụng trong quá trình tuần tự hóa (serialization).

#### So Sánh Vi Phạm LSP Bằng Phép Ẩn Dụ
Nghĩ hộp thư tiêu chuẩn (`Map<Object, Object>`) có thể nhận thư, tạp chí, gói hàng và rác. `Properties` giống hộp thư được dán nhãn chỉ nhận thư giấy. Vì khe hộp thư vật lý (`put(Object, Object)`), ai đó vẫn có thể thả một cục gạch (đối tượng không phải String) vào trong. Khi người đưa thư (`store()`) cố mở hộp thư và chỉ mong nhận thư để xử lý, họ bị thương vì cục gạch (`ClassCastException`).

```mermaid
flowchart TD
    subgraph PropertiesInheritance ["Vi Phạm LSP: Properties kế thừa Hashtable"]
        Hashtable["Hashtable&lt;Object,Object&gt;"] -->|Kế thừa put()| Properties["Properties (Mong đợi key/value là String)"]
    end
    subgraph Execution ["Luồng Tuần Tự Hóa Runtime"]
        Properties -->|put('port', 8080)| MapState["Map nội tại chứa key String và value Integer"]
        MapState -->|store() được gọi| Loop["Duyệt và cast key/value sang String"]
        Loop -->|Cast 8080 (Integer) sang String| Crash["ClassCastException (Sập!)"]
    end
```

#### Chuỗi Nhân Quả Của Lạm Dụng Map
```text
Properties kế thừa Hashtable → put(Object, Object) bị lộ → Đối tượng không phải String được chèn vào Properties → Biên dịch thành công → store() được gọi khi tắt ứng dụng → JVM cố cast non-String sang String → ClassCastException ném → Cấu hình không thể lưu
```

#### Ví Dụ Chạy Được
```java
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLspDemo {
    public static void main(String[] args) {
        Properties props = new Properties();

        // ĐÚNG: setProperty đảm bảo an toàn kiểu
        props.setProperty("db.user", "admin");

        // NGUY HIỂM: put() bỏ qua hợp đồng String của Properties
        props.put("db.port", 3306); // Bỏ qua cảnh báo trình biên dịch, tự đóng hộp thành Integer

        // Đọc qua getProperty trả về null nếu cần cast
        String portValue = props.getProperty("db.port");
        System.out.println("getProperty('db.port'): " + portValue); // In null vì không phải String!

        // Cố tuần tự hóa properties
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // Ném ClassCastException vì db.port là Integer, không phải String
            props.store(out, "Application Configuration");
        } catch (ClassCastException e) {
            System.out.println("Bắt được ClassCastException như mong đợi trong store(): " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```


---

### ResourceBundle

`java.util.ResourceBundle` chứa các file tài nguyên theo vùng (thường là dịch thuật) cho phép quốc tế hóa (I18N — Internationalization) ứng dụng dễ dàng.

- **Ví dụ chạy được**:
  ```java
  // Tải MessagesBundle_fr_FR.properties
  Locale frenchLocale = new Locale("fr", "FR");
  ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", frenchLocale);
  
  String greeting = bundle.getString("welcome_message");
  ```

- **Lỗi Thường Gặp / Chế Độ Thất Bại**:
  - **Thiếu Bundle Cơ Sở**: Khi tra key dịch, nếu không tìm thấy file properties cho vùng cụ thể, Java tìm kiếm lên chuỗi kế thừa locale (ví dụ `fr_FR` → `fr` → Locale hệ thống mặc định → Bundle cơ sở). Nếu key không có trong file properties nào, hay file bundle tài nguyên cơ sở (`MessagesBundle.properties`) hoàn toàn thiếu trong classpath, JVM ném `MissingResourceException` tại runtime. Luôn đóng gói file properties cơ sở mặc định.

---

### Locale

Đối tượng `java.util.Locale` đại diện cho một vùng địa lý, chính trị hay văn hóa cụ thể. Nó được dùng để định dạng số, ngày tháng, tiền tệ và chọn bản dịch.

- **Ví dụ chạy được**:
  ```java
  // Hằng số định sẵn
  Locale us = Locale.US;

  // Builder pattern (cách an toàn nhất để xây dựng locale tùy chỉnh)
  Locale custom = new Locale.Builder().setLanguage("en").setRegion("GB").build();

  // Phân tích language tag (IETF BCP 47)
  Locale parsed = Locale.forLanguageTag("vi-VN");
  ```

- **Lỗi Thường Gặp / Chế Độ Thất Bại**:
  - **Dùng Sai Constructor**: Khởi tạo locale như `new Locale("en_US")` bằng cách truyền language tag kết hợp. Constructor cũ mong đợi ngôn ngữ (`"en"`) và vùng (`"US"`) là hai tham số riêng. Truyền `"en_US"` tạo locale không hợp lệ với ngôn ngữ `"en_us"` và không có vùng, không tải được các resource bundle tương ứng. Dùng `Locale.forLanguageTag("en-US")` thay thế.

---

### Currency

Lớp `java.util.Currency` đại diện cho một loại tiền tệ ISO 4217.

- **Ví dụ chạy được**:
  ```java
  Currency usd = Currency.getInstance("USD");
  Locale locale = Locale.FRANCE;
  
  // In "USD" hay ký hiệu tiền "$" tùy theo locale của formatter
  String symbol = usd.getSymbol(locale); 
  ```

- **Lỗi Thường Gặp / Chế Độ Thất Bại**:
  - Gọi `Currency.getInstance(locale)` cho locale đại diện quốc gia không có tiền tệ chính thức hay thẻ vùng không hợp lệ, điều này ném `IllegalArgumentException`.

---

### Formatter

`java.util.Formatter` tạo ra các chuỗi văn bản được định dạng theo vùng theo bố cục in kiểu C.

- **Ví dụ chạy được**:
  ```java
  StringBuilder sb = new StringBuilder();
  try (Formatter formatter = new Formatter(sb, Locale.US)) {
      formatter.format("Item: %s | Price: $%,.2f", "Laptop", 1249.99);
  }
  // sb giờ chứa: "Item: Laptop | Price: $1,249.99"
  ```

- **Lỗi Thường Gặp / Chế Độ Thất Bại**:
  - **Sai Specifier Chuyển Đổi**: Ghép sai specifier với tham số (ví dụ `%d` cho double hay `%f` cho integer) ném `IllegalFormatConversionException` tại runtime.
  - **Rò Rỉ Luồng**: Khi xây dựng `Formatter` xung quanh file hay luồng đầu ra, không đóng formatter gây rò rỉ file descriptor. Luôn bao bọc trong try-with-resources.

---

### Scanner

`java.util.Scanner` là trình phân tích văn bản đơn giản chia nhỏ luồng đầu vào thành các token dùng dấu phân cách (mặc định là khoảng trắng) và phân tích kiểu nguyên thủy hay chuỗi.

- **Ví dụ chạy được**:
  ```java
  String input = "10 20 30";
  try (Scanner scanner = new Scanner(input)) {
      while (scanner.hasNextInt()) {
          int value = scanner.nextInt();
      }
  }
  ```

- **Lỗi Thường Gặp / Chế Độ Thất Bại**:
  - **Cạm Bẫy nextLine()**: Đọc giá trị nguyên thủy (như `nextInt()`) rồi gọi `nextLine()` để đọc văn bản. Các phương thức đọc nguyên thủy chỉ tiêu thụ token, để lại ký tự xuống dòng `\n` trong buffer. `nextLine()` tiếp theo tiêu thụ dòng trống ngay lập tức, bỏ qua dòng đầu vào thực sự:
    ```java
    Scanner scanner = new Scanner(System.in);
    int age = scanner.nextInt();    // Tiêu thụ int, để '\n' trong buffer
    scanner.nextLine();             // QUAN TRỌNG: Tiêu thụ dòng mới thừa
    String name = scanner.nextLine(); // Giờ đúng cách chờ đầu vào văn bản
    ```
  - **Đóng System.in**: Đóng Scanner bao bọc `System.in` (ví dụ `scanner.close()`) đóng chính `System.in`. Vì bạn không thể mở lại đầu vào tiêu chuẩn trong JVM đang chạy, bất kỳ lần đọc tiếp theo từ `System.in` sẽ gây sập. Tránh đóng Scanner bao bọc `System.in`.

### Tại Sao Cạm Bẫy nextLine() Xảy Ra

Lớp `java.util.Scanner` là trình phân tích dựa trên token. Hiểu cơ chế con trỏ và buffer nội tại của scanner là thiết yếu để tránh các lỗi bỏ qua đầu vào thông thường.

#### Cơ Chế Xử Lý Token vs. Dòng
- **Phương thức dựa trên token** (`nextInt()`, `nextDouble()`, `next()`):
  Các phương thức này bỏ qua bất kỳ dấu phân cách đầu (khoảng trắng, tab hay dòng mới mặc định), quét buffer đọc các ký tự khớp với mẫu của chúng và dừng đọc ngay sau mẫu khớp. Chúng **không tiêu thụ dấu phân cách kéo theo** (như ký tự xuống dòng `\n` được tạo khi người dùng nhấn Enter).
- **Phương thức dựa trên dòng** (`nextLine()`):
  Phương thức này đọc buffer từ vị trí con trỏ hiện tại cho đến khi gặp ký tự xuống dòng (`\n` hay `\r\n`), tiêu thụ toàn bộ dòng bao gồm ký tự xuống dòng, nhưng chỉ trả về văn bản *trước* ký tự xuống dòng.

#### Trạng Thái Con Trỏ và Buffer Từng Bước
Khi người dùng nhập `42` và nhấn Enter, buffer luồng đầu vào chứa:
`['4', '2', '\n']`

1. **`nextInt()` được gọi**:
   - Scanner đọc `'4'` và `'2'`.
   - Phân tích thành số nguyên `42` và trả về.
   - Con trỏ dừng *trước* `'\n'`. Buffer còn: `['\n']`.
2. **`nextLine()` được gọi**:
   - Scanner bắt đầu đọc từ vị trí con trỏ hiện tại.
   - Ngay lập tức gặp `'\n'`.
   - Tiêu thụ `'\n'` và xóa khỏi buffer.
   - Vì không có văn bản trước `'\n'`, trả về chuỗi rỗng `""` ngay lập tức mà không chờ đầu vào console mới.

#### So Sánh Trạng Thái Buffer Bằng Phép Ẩn Dụ
Hãy tưởng tượng băng chuyền mang các gói hàng (token) cách nhau bởi các miếng nhựa phân cách (dấu phân cách như `\n`).
`nextInt()` giống cánh tay robot chỉ lấy gói hàng (`42`), để lại miếng phân cách (`\n`) trên băng chuyền.
`nextLine()` giống cánh tay quét sạch mọi thứ trên băng chuyền cho đến miếng phân cách tiếp theo. Nếu miếng phân cách (`\n`) đã là vật tiếp theo trên băng, cánh tay quét kết thúc ngay và báo cáo đã quét không có gì, để lại cho bạn kết quả rỗng.

```mermaid
flowchart TD
    subgraph Step1 ["Trạng Thái Buffer: Người dùng nhập '42\\n'"]
        B1["[ '4', '2', '\\n' ]"]
    end
    subgraph Step2 ["Sau nextInt(): Con trỏ dừng trước '\\n'"]
        B2["[ '\\n' ]"]
        cur2["Vị trí con trỏ"] --> B2
    end
    subgraph Step3 ["Sau nextLine(): '\\n' bị tiêu thụ, trả về String rỗng"]
        B3["[ ]"]
        res["Trả về chuỗi rỗng: ''"]
    end
    Step1 -->|nextInt() tiêu thụ '42'| Step2
    Step2 -->|nextLine() tiêu thụ '\\n'| Step3
```

#### Chuỗi Nhân Quả Của Cạm Bẫy nextLine()
```text
Người dùng nhập '42\n' → nextInt() chỉ đọc '42' → '\n' còn lại ở đầu buffer → nextLine() được gọi → Scanner phát hiện và tiêu thụ '\n' ngay lập tức → nextLine() trả về chuỗi rỗng → Code tiếp tục mà không chờ đầu vào mới
```

#### Ví Dụ Chạy Được
```java
import java.util.Scanner;

public class ScannerPitfallDemo {
    public static void main(String[] args) {
        String inputBuffer = "42\nJohn Doe\n";
        
        // Mô phỏng chế độ thất bại
        try (Scanner buggyScanner = new Scanner(inputBuffer)) {
            int age = buggyScanner.nextInt(); // Tiêu thụ "42", để lại "\n"
            String name = buggyScanner.nextLine(); // Tiêu thụ "\n" ngay lập tức, trả về rỗng!
            
            System.out.println("Tuổi: " + age);
            System.out.println("Tên có lỗi (nên là John Doe): '" + name + "'");
        }

        // Mô phỏng cách tiếp cận đúng
        try (Scanner correctScanner = new Scanner(inputBuffer)) {
            int age = correctScanner.nextInt(); // Tiêu thụ "42", để lại "\n"
            correctScanner.nextLine();          // QUAN TRỌNG: Tiêu thụ và bỏ dòng mới thừa
            String name = correctScanner.nextLine(); // Giờ đọc "John Doe"
            
            System.out.println("Tuổi: " + age);
            System.out.println("Tên đúng: '" + name + "'");
        }
    }
}
```
