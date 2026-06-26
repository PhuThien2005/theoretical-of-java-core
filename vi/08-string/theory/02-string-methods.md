# Các Phương Thức Chuỗi Và Định Dạng (String Methods and Formatting)

Lớp `String` cung cấp một tập hợp phong phú các phương thức có sẵn. Vì chuỗi là bất biến (immutable), không có phương thức nào trong số này chỉnh sửa trực tiếp đối tượng chuỗi hiện tại; chúng luôn luôn trả về một đối tượng chuỗi mới.

---

## Tham Chiếu Phương Thức Chi Tiết (Detailed Method Reference)

Dưới đây là tài liệu tham khảo toàn diện về các phương thức cốt lõi của `String`, giải thích rõ hành vi và các trường hợp đặc biệt của chúng.

### 1. `length()` và `isEmpty()` / `isBlank()`
- `length()`: Trả về số lượng đơn vị mã Unicode (Unicode code units - tương đương số ký tự) trong chuỗi.
- `isEmpty()` (Java 6+): Trả về `true` nếu `length() == 0`.
- `isBlank()` (Java 11+): Trả về `true` nếu chuỗi bị rỗng hoặc chỉ chứa các ký tự khoảng trắng (hỗ trợ kiểm tra toàn diện các khoảng trắng Unicode).
  ```java
  "  ".isEmpty(); // false
  "  ".isBlank(); // true
  ```

### 2. Tra Cứu Ký Tự: `charAt(int index)`
- Trả về giá trị `char` tại vị trí chỉ số (index) được chỉ định.
- Giới hạn chỉ số là từ `0` đến `length() - 1`.
- Ném ra ngoại lệ `StringIndexOutOfBoundsException` nếu chỉ số bị âm hoặc lớn hơn hoặc bằng `length()`.

### 3. Trích Xuất Phân Đoạn: `substring(int beginIndex)` và `substring(int beginIndex, int endIndex)`
- `substring(beginIndex)`: Trả về một chuỗi con bắt đầu từ `beginIndex` cho đến hết chuỗi.
- `substring(beginIndex, endIndex)`: Trả về một chuỗi con bắt đầu từ `beginIndex` (bao gồm) đến `endIndex` (loại trừ).
  $$\text{Độ dài chuỗi con} = \text{endIndex} - \text{beginIndex}$$
- Ném ra ngoại lệ `StringIndexOutOfBoundsException` nếu `beginIndex < 0`, `endIndex > length()`, hoặc `beginIndex > endIndex`.

```java
String msg = "Hello World";
String sub1 = msg.substring(6);      // "World"
String sub2 = msg.substring(0, 5);   // "Hello"
System.out.println(sub1); // World
System.out.println(sub2); // Hello
```

### 4. Tìm Kiếm và Xác Định Vị Trí: `indexOf()` và `lastIndexOf()`
- Xác định vị trí của một ký tự hoặc một chuỗi con. Trả về `-1` nếu không tìm thấy.
- `indexOf(String str)`: Tìm vị trí xuất hiện đầu tiên.
- `indexOf(String str, int fromIndex)`: Bắt đầu tìm kiếm từ vị trí `fromIndex`.
- `lastIndexOf(String str)`: Tìm vị trí xuất hiện cuối cùng (tìm kiếm ngược từ cuối về đầu).

### 5. Các Phép Kiểm Tra Xác Thực: `contains()`, `startsWith()`, `endsWith()`
- `contains(CharSequence s)`: Trả về `true` nếu chuỗi con được tìm kiếm tồn tại trong chuỗi.
- `startsWith(String prefix)` / `endsWith(String suffix)`: Kiểm tra xem chuỗi có bắt đầu hoặc kết thúc bằng tiền tố/hậu tố cụ thể hay không. Việc truyền vào đối số `null` sẽ ném ra ngoại lệ `NullPointerException`.

```java
String text = "Java Programming";
boolean hasJava = text.contains("Java"); // true
boolean hasKotlin = text.contains("Kotlin"); // false
System.out.println(hasJava);   // true
System.out.println(hasKotlin); // false
```

### 6. Loại Bỏ Khoảng Trắng: `trim()` so với `strip()` (Whitespace Cleanup: trim() vs. strip())
- `trim()`: Loại bỏ các ký tự khoảng trắng ở đầu và cuối chuỗi có giá trị mã code point nhỏ hơn hoặc bằng ký tự khoảng trắng ASCII (`U+0020`). Nó thất bại trong việc dọn dẹp các khoảng trắng Unicode đặc biệt (như khoảng trắng không ngắt `\u00A0` - non-breaking space).
- `strip()` (Java 11+): Sử dụng phương thức `Character.isWhitespace()` để nhận diện và loại bỏ tất cả các khoảng trắng tuân thủ chuẩn Unicode.
- `stripLeading()` / `stripTrailing()` (Java 11+): Chỉ dọn dẹp khoảng trắng ở một đầu tương ứng (đầu hoặc cuối) của chuỗi.

### Đi Sâu: Sự Khác Biệt Cơ Chế Giữa trim() và strip() (Deep-Dive: Mechanical Differences Between trim() and strip())

Sự khác biệt về mặt cơ chế giữa `String.trim()` và `String.strip()` nằm ở cách chúng định nghĩa và nhận diện các ký tự khoảng trắng. Phương thức kế thừa `trim()`, được thiết kế từ thời Java 1.0, xác định khoảng trắng một cách nghiêm ngặt bằng cách kiểm tra xem giá trị Unicode code point của ký tự có nhỏ hơn hoặc bằng ký tự khoảng trắng ASCII (`U+0020`) hay không. Do đó, nó hoàn toàn bỏ qua mọi ký tự khoảng trắng chuẩn Unicode hiện đại có giá trị code point cao hơn, chẳng hạn như khoảng trắng không ngắt (`U+00A0` - non-breaking space) hoặc khoảng trắng rộng (`U+2003` - em space). Ngược lại, phương thức `strip()` giới thiệu từ Java 11 sẽ gọi phương thức `Character.isWhitespace(int)`, thực hiện kiểm tra ký tự đối chiếu với cơ sở dữ liệu tiêu chuẩn Unicode chính thức. Điều này giúp `strip()` nhận biết đầy đủ các ký tự Unicode, đảm bảo rằng các ứng dụng quốc tế hóa hiện đại dọn dẹp chính xác các ký tự khoảng trắng ngoài hệ ASCII mà phương thức `trim()` sẽ âm thầm bỏ qua.

#### Bảng So Sánh Các Khoảng Trắng (Whitespace Comparison Matrix)

| Ký tự khoảng trắng | Mã Code Point | Hành động của trim() | Hành động của strip() | Lý do kỹ thuật |
| :--- | :--- | :--- | :--- | :--- |
| Khoảng trắng ASCII (ASCII Space) | `U+0020` | Loại bỏ | Loại bỏ | Code point $\le$ `U+0020` |
| Phím Tab (`\t`) | `U+0009` | Loại bỏ | Loại bỏ | Code point $\le$ `U+0020` |
| Khoảng trắng không ngắt (Non-Breaking Space) | `U+00A0` | **Bỏ qua** | **Loại bỏ** | Code point > `U+0020`, nhưng được nhận diện là khoảng trắng bởi Unicode |
| Khoảng trắng rộng (Em Space) | `U+2003` | **Bỏ qua** | **Loại bỏ** | Code point > `U+0020`, nhưng được nhận diện là khoảng trắng bởi Unicode |

#### Ví Dụ Code Minh Họa Khoảng Trắng Unicode (Unicode Whitespace Demonstration Code Example)

```java
// Chuỗi chứa ký tự khoảng trắng rộng Unicode Em Space (\u2003)
String input = "\u2003Java Core\u2003";

System.out.println("Độ dài ban đầu: " + input.length()); // Kết quả: 11
System.out.println("Độ dài sau trim(): " + input.trim().length()); // Kết quả: 11 (bị bỏ qua!)
System.out.println("Độ dài sau strip(): " + input.strip().length()); // Kết quả: 9 (được loại bỏ!)
```

#### Chuỗi Nguyên Nhân - Kết Quả Xử Lý Khoảng Trắng Unicode

```text
Ký tự Unicode `\u2003` (Em Space, giá trị `0x2003`)
  → Được đánh giá bởi `trim()`
  → Kiểm tra xem `0x2003 <= 0x20` (trả về `false`)
  → `trim()` bỏ qua ký tự
  → Được đánh giá bởi `strip()`
  → Gọi `Character.isWhitespace(0x2003)`
  → Trả về `true` dựa trên các thuộc tính Unicode
  → `strip()` loại bỏ ký tự thành công.
```


### 7. Chuyển Đổi: `toLowerCase()` và `toUpperCase()`
- Chuyển đổi các ký tự viết thường/viết hoa sử dụng các quy tắc đặc thù theo ngôn ngữ (locale-specific rules). Hãy cẩn thận: `"title".toUpperCase()` trong cấu hình ngôn ngữ Thổ Nhĩ Kỳ sẽ tạo ra chuỗi `TİTLE` thay vì `TITLE`.

### 8. Thay Thế: `replace()` so với `replaceAll()` (Replacement: replace() vs. replaceAll())
- `replace(char oldChar, char newChar)`: Thay thế tất cả các lượt xuất hiện của một ký tự.
- `replace(CharSequence target, CharSequence replacement)`: Thay thế tất cả các chuỗi con khớp giá trị. **Hoàn toàn không sử dụng biểu thức chính quy (regular expressions).**
- `replaceAll(String regex, String replacement)`: Thay thế các phần khớp với một **biểu thức chính quy**.
- `replaceFirst(String regex, String replacement)`: Chỉ thay thế phần khớp đầu tiên của một biểu thức chính quy.

```java
String src = "apple.orange.banana";

// replace() đối xử "." như một chuỗi ký tự thông thường
String r1 = src.replace(".", "-"); 
System.out.println(r1); // Kết quả: apple-orange-banana

// replaceAll() đối xử "." như một ký tự đại diện regex (khớp với mọi ký tự)
String r2 = src.replaceAll(".", "-"); 
System.out.println(r2); // Kết quả: -------------------
```

### 9. Cắt Tách Chuỗi: `split(String regex)` và `split(String regex, int limit)`
Cắt tách chuỗi dựa trên các điểm khớp của biểu thức chính quy.
- `split(regex)`: Tự động loại bỏ các chuỗi rỗng ở cuối mảng kết quả.
- `split(regex, limit)`:
  - Nếu `limit > 0`: Biểu thức chính quy được áp dụng tối đa `limit - 1` lần, dẫn đến độ dài tối đa của mảng kết quả là `limit`.
  - Nếu `limit < 0`: Biểu thức chính quy được áp dụng nhiều lần nhất có thể, và các chuỗi rỗng ở cuối mảng **không** bị loại bỏ.
  - Nếu `limit == 0`: Tương đương với `split(regex)` (các chuỗi rỗng ở cuối mảng bị loại bỏ).

```java
String s = "a:b:c::";
s.split(":").length;    // 3 -> {"a", "b", "c"} (chuỗi rỗng ở cuối bị loại bỏ)
s.split(":", -1).length; // 5 -> {"a", "b", "c", "", ""} (giữ lại các chuỗi rỗng)
s.split(":", 2).length;  // 2 -> {"a", "b:c::"} (giới hạn tối đa 2 phần tử)
```

### 10. Chuyển Đổi Thành Mảng Ký Tự: `toCharArray()`
- Trả về một mảng ký tự mới được cấp phát có độ dài bằng với độ dài chuỗi, chứa trình tự các ký tự đại diện bởi chuỗi đó.

```java
String word = "Java";
char[] chars = word.toCharArray();
for (char c : chars) {
    System.out.print(c + " "); // Kết quả: J a v a 
}
System.out.println();
```

---

## Nối Chuỗi Và Các Tối Ưu Hóa Của Trình Biên Dịch (String Concatenation and Compiler Optimizations)

### Đánh Giá Lúc Biên Dịch (Compile-time Evaluation)
Nếu bạn thực hiện nối các hằng chuỗi (literals), trình biên dịch sẽ tự động tính toán chúng tại thời điểm biên dịch và đặt chuỗi kết quả cuối cùng trực tiếp vào file bytecode:
```java
String s = "a" + "b" + "c"; // Được biên dịch tương đương: String s = "abc";
```

### Đánh Giá Động (Dynamic Evaluation - Sử Dụng Biến)
Nếu biểu thức nối chuỗi chứa các biến số, Java bắt buộc phải đánh giá chúng lúc runtime:
- **Java 8 trở về trước:** Được biên dịch dịch chuyển thành cấu trúc `new StringBuilder().append(a).append(b).toString()`.
- **Java 9 trở đi:** Sử dụng lệnh `invokedynamic` để gọi phương thức `StringConcatFactory.makeConcatWithTemplate()`. Cơ chế này tách biệt chiến lược nối chuỗi ra khỏi bytecode, cho phép JVM tối ưu hóa thao tác một cách linh hoạt lúc runtime.

---

## Các Tùy Chọn Định Dạng Chi Tiết (Detailed Formatting Options)

Các phương thức `String.format()` và `System.out.printf()` định dạng chuỗi dựa trên một mẫu (pattern).

### Cú Pháp:
$$\%[\text{argument\_index}\$][\text{flags}][\text{width}][.\text{precision}]\text{conversion}$$

### Các Ký Tự Chuyển Đổi Phổ Biến (Common Conversions):
- `%s`: Biểu diễn chuỗi (String)
- `%d`: Số nguyên (Integer)
- `%f`: Số thực dấu phẩy động (Floating-point)
- `%tF`: Ngày tháng định dạng YYYY-MM-DD (Date)
- `%n`: Ký tự xuống dòng đặc thù theo hệ điều hành (Platform-specific newline)

### Các Cờ Định Dạng Và Độ Chính Xác (Formatting Flags and Precision):
- `%-15s`: Căn lề trái cho chuỗi bên trong một vùng hiển thị rộng 15 ký tự.
- `%05d`: Đệm thêm các số 0 ở đầu để số có độ dài hiển thị là 5 chữ số.
- `%.2f`: Giới hạn hiển thị số thực dấu phẩy động đến 2 chữ số thập phân sau dấu phẩy.

```java
String.format("|%-10s|", "Java"); // "|Java      |"
String.format("%.3f", 3.14159);    // "3.142" (được làm tròn)
String.format("%04d", 42);          // "0042"

// Ví dụ định dạng chi tiết:
String name = "Alice";
int age = 30;
double gpa = 3.8567;

String formatted = String.format("Name: %s, Age: %d, GPA: %.2f", name, age, gpa);
System.out.println(formatted); // Kết quả: Name: Alice, Age: 30, GPA: 3.86
```

---

## Khối Văn Bản (Text Blocks - Java 15+)

Khối văn bản (Text Blocks) là cú pháp khai báo hằng chuỗi nhiều dòng được bao bọc bởi ba dấu ngoặc kép `"""`.

### Thuật Toán Loại Bỏ Khoảng Trắng (Whitespace Stripping Algorithm)
1. **Khoảng trắng ngẫu nhiên (Incidental Whitespace):** Trình biên dịch tính toán khoảng trắng thụt lề chung nhỏ nhất trên tất cả các dòng và tự động loại bỏ nó.
2. **Khoảng trắng cốt lõi (Essential Whitespace):** Các phần thụt lề vượt quá giới hạn chung nhỏ nhất nói trên sẽ được giữ lại.
3. Ký tự đóng `"""` quyết định mức thụt lề tối thiểu. Việc di chuyển ký tự đóng này sang bên trái sẽ giúp giữ lại các khoảng trắng thụt lề ở đầu dòng.

### Các Ký Tự Escape Đặc Biệt Trong Khối Văn Bản
- `\` (Line Continuation - Tiếp tục dòng): Ngăn chặn việc chèn ký tự xuống dòng ở cuối dòng hiện tại.
- `\s` (Trailing Space - Giữ khoảng trắng cuối): Giữ lại các ký tự khoảng trắng ở cuối dòng (vốn dĩ theo mặc định sẽ bị tự động loại bỏ).

```java
String html = """
              <html>
                  <body>\
                      <p>Hello World</p>\s\s
                  </body>
              </html>
              """;
```
- Dòng chứa thẻ `<p>` sẽ được nối liền với dòng tiếp theo nhờ ký tự `\`.
- Đoạn `\s\s` ở cuối thẻ `<p>` giúp giữ lại chính xác hai ký tự khoảng trắng ở cuối dòng.

---

## Các Lỗi Thường Gặp (Common Mistakes)

### 1. Ngoại lệ `StringIndexOutOfBoundsException` với `charAt` và `substring`
Chuỗi trong Java tính chỉ số bắt đầu từ số 0. Giới hạn chỉ số của `charAt(index)` là từ `0` đến `length() - 1`. Chỉ số kết thúc `endIndex` của `substring(beginIndex, endIndex)` là loại trừ, nhưng không được phép lớn hơn `length()`.
```java
String s = "hello";
char c = s.charAt(5); // Throws StringIndexOutOfBoundsException (độ dài là 5, chỉ số lớn nhất là 4)
String sub = s.substring(2, 6); // Throws StringIndexOutOfBoundsException (chỉ số kết thúc 6 vượt quá độ dài)
```

### 2. Cắt tách dựa trên các ký tự đặc biệt của Regex (Metacharacters)
Sử dụng trực tiếp các ký tự đặc biệt của biểu thức chính quy như `.`, `|`, `+`, `*`, `?` trong phương thức `split()` hoặc `replaceAll()` mà quên escape chúng.
```java
String data = "a.b.c";
String[] parts = data.split("."); // Sai! Ký tự "." khớp với mọi ký tự.
System.out.println(parts.length); // In ra 0 vì tất cả ký tự đã khớp và bị cắt đi mất.

// Đúng: Escape dấu chấm bằng cách dùng hai dấu gạch chéo ngược
String[] correctParts = data.split("\\.");
System.out.println(correctParts.length); // In ra 3
```

### 3. Nhầm lẫn giữa `replace` và `replaceAll`
Giả định rằng `replace(CharSequence, CharSequence)` chỉ thay thế vị trí đầu tiên xuất hiện hoặc không thay thế hết. Thực tế, `replace()` thay thế TẤT CẢ các vị trí khớp hằng chuỗi, trong khi `replaceAll()` cũng làm điều tương tự nhưng đối xử chuỗi tìm kiếm như một biểu thức chính quy (regex).
```java
String sentence = "I love Java. Java is fun.";
// Cả hai lệnh đều thay thế tất cả vị trí khớp, nhưng replace() chạy nhanh và an toàn hơn cho văn bản thường:
System.out.println(sentence.replace("Java", "Kotlin")); 
System.out.println(sentence.replaceAll("Java", "Kotlin"));
```

---

## Liên Kết Tham Khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#trim() (Tài liệu Javadoc của String.trim() trong Oracle Java API)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#strip() (Tài liệu Javadoc của String.strip() trong Oracle Java API)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Character.html#isWhitespace(int) (Tài liệu Javadoc của Character.isWhitespace() trong Oracle Java API)
