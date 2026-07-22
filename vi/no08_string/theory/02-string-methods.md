# Các phương thức và Định dạng Chuỗi (String Methods and Formatting)

Lớp `String` cung cấp một tập hợp phong phú các phương thức tích hợp sẵn (built-in methods). Vì chuỗi (string) có tính chất bất biến (immutable), không có phương thức nào trong số này sửa đổi đối tượng chuỗi hiện tại; chúng luôn trả về một chuỗi mới.

---

## Tài Liệu Tham Khảo Chi Tiết Về Phương Thức (Detailed Method Reference)

Dưới đây là tài liệu tham khảo toàn diện về các phương thức cốt lõi của `String`, giải thích hành vi và các trường hợp đặc biệt (edge cases) của chúng.

### 1. `length()` và `isEmpty()` / `isBlank()`
- `length()`: Trả về số lượng đơn vị mã Unicode (Unicode code units) (ký tự) trong chuỗi.
- `isEmpty()` (Java 6+): Trả về `true` nếu `length() == 0`.
- `isBlank()` (Java 11+): Trả về `true` nếu chuỗi trống hoặc chỉ chứa các ký tự khoảng trắng (whitespace) tương thích với Unicode (Unicode-aware).
  ```java
  "  ".isEmpty(); // false
  "  ".isBlank(); // true
  ```

### 2. Tìm Kiếm Ký Tự (Character Lookup): `charAt(int index)`
- Trả về giá trị `char` tại chỉ mục (index) được chỉ định.
- Giới hạn chỉ mục nằm trong khoảng từ `0` đến `length() - 1`.
- Ném ra ngoại lệ `StringIndexOutOfBoundsException` nếu chỉ mục âm hoặc lớn hơn hoặc bằng `length()`.

### 3. Trích Xuất (Extracting): `substring(int beginIndex)` và `substring(int beginIndex, int endIndex)`
- `substring(beginIndex)`: Trả về một chuỗi con (substring) bắt đầu từ `beginIndex` cho đến hết.
- `substring(beginIndex, endIndex)`: Trả về một chuỗi con bắt đầu từ `beginIndex` (bao gồm (inclusive)) đến `endIndex` (loại trừ (exclusive)).
  $$\text{Độ dài chuỗi con} = \text{endIndex} - \text{beginIndex}$$
- Ném ra ngoại lệ `StringIndexOutOfBoundsException` nếu `beginIndex < 0`, `endIndex > length()`, hoặc `beginIndex > endIndex`.

```java
String msg = "Hello World";
String sub1 = msg.substring(6);      // "World"
String sub2 = msg.substring(0, 5);   // "Hello"
System.out.println(sub1); // World
System.out.println(sub2); // Hello
```

### 4. Tìm Kiếm và Định Vị (Search and Location): `indexOf()` và `lastIndexOf()`
- Định vị một ký tự hoặc chuỗi con. Trả về `-1` nếu không tìm thấy.
- `indexOf(String str)`: Tìm vị trí xuất hiện đầu tiên.
- `indexOf(String str, int fromIndex)`: Bắt đầu tìm kiếm từ `fromIndex`.
- `lastIndexOf(String str)`: Tìm vị trí xuất hiện cuối cùng (tìm kiếm ngược).

### 5. Kiểm Tra Tính Hợp Lệ (Validation Checks): `contains()`, `startsWith()`, `endsWith()`
- `contains(CharSequence s)`: Trả về `true` nếu chuỗi ký tự tồn tại.
- `startsWith(String prefix)` / `endsWith(String suffix)`: Khớp với phần bắt đầu hoặc kết thúc. Đầu vào có giá trị `null` sẽ ném ra ngoại lệ `NullPointerException`.

```java
String text = "Java Programming";
boolean hasJava = text.contains("Java"); // true
boolean hasKotlin = text.contains("Kotlin"); // false
System.out.println(hasJava);   // true
System.out.println(hasKotlin); // false
```

### 6. Loại Bỏ Khoảng Trắng (Whitespace Cleanup): `trim()` so với `strip()`
- `trim()`: Loại bỏ các ký tự ở đầu/cuối có điểm mã (code point) nhỏ hơn hoặc bằng khoảng trắng ASCII (`U+0020`). Phương thức này không thể loại bỏ các khoảng trắng Unicode (như khoảng trắng không ngắt (non-breaking space) `\u00A0`).
- `strip()` (Java 11+): Sử dụng `Character.isWhitespace()` để xác định và loại bỏ tất cả các khoảng trắng tuân thủ tiêu chuẩn Unicode (Unicode-compliant).
- `stripLeading()` / `stripTrailing()` (Java 11+): Chỉ loại bỏ khoảng trắng ở một đầu của chuỗi.

### Phân Tích Sâu: Sự Khác Biệt Về Cơ Chế Giữa trim() và strip() (Deep-Dive: Mechanical Differences Between trim() and strip())

Sự khác biệt về cơ chế (mechanical differences) giữa `String.trim()` and `String.strip()` nằm ở cách chúng định nghĩa và xác định các ký tự khoảng trắng. Phương thức kế thừa (legacy) `trim()`, được thiết kế từ Java 1.0, xác định khoảng trắng một cách nghiêm ngặt bằng cách kiểm tra xem giá trị điểm mã của ký tự đó có nhỏ hơn hoặc bằng ký tự khoảng trắng ASCII (`U+0020`) hay không. Do đó, nó không thể loại bỏ bất kỳ ký tự khoảng trắng hiện đại nào được định nghĩa bởi Unicode có điểm mã cao hơn, chẳng hạn như khoảng trắng không ngắt (`U+00A0`) hoặc khoảng trắng em space (em space) (`U+2003`). Ngược lại, phương thức `strip()` được giới thiệu trong Java 11 sẽ truy vấn phương thức `Character.isWhitespace(int)`, phương thức này đối chiếu ký tự với cơ sở dữ liệu tiêu chuẩn Unicode chính thức. Điều này giúp `strip()` hoàn toàn tương thích với Unicode, đảm bảo các ứng dụng quốc tế hóa (internationalized) hiện đại dọn dẹp chính xác các ký tự khoảng trắng không phải ASCII mà `trim()` sẽ âm thầm bỏ qua.

#### Bảng So Sánh Khoảng Trắng (Whitespace Comparison Matrix)

| Ký tự khoảng trắng | Điểm mã (Code Point) | Hành động của trim() | Hành động của strip() | Lý do kỹ thuật |
| :--- | :--- | :--- | :--- | :--- |
| Khoảng trắng ASCII | `U+0020` | Loại bỏ | Loại bỏ | Điểm mã $\le$ `U+0020` |
| Tab (`\t`) | `U+0009` | Loại bỏ | Loại bỏ | Điểm mã $\le$ `U+0020` |
| Khoảng trắng không ngắt | `U+00A0` | **Bỏ qua** | **Loại bỏ** | Điểm mã > `U+0020`, nhưng được Unicode công nhận là khoảng trắng |
| Khoảng trắng em space | `U+2003` | **Bỏ qua** | **Loại bỏ** | Điểm mã > `U+0020`, nhưng được Unicode công nhận là khoảng trắng |

#### Ví Dụ Mã Nguồn Minh Họa Khoảng Trắng Unicode (Unicode Whitespace Demonstration Code Example)

```java
// String containing Unicode Em Space (\u2003)
String input = "\u2003Java Core\u2003";

System.out.println("Original length: " + input.length()); // Output: 11
System.out.println("trim() length: " + input.trim().length()); // Output: 11 (ignored!)
System.out.println("strip() length: " + input.strip().length()); // Output: 9 (removed!)
```

#### Chuỗi Nguyên Nhân - Kết Quả Trong Xử Lý Khoảng Trắng Unicode (Cause-Effect Chain of Unicode Whitespace Processing)
Ký tự Unicode `\u2003` (Khoảng trắng em space, giá trị `0x2003`) $\rightarrow$ Được đánh giá bởi `trim()` $\rightarrow$ Kiểm tra xem `0x2003 <= 0x20` (cho kết quả là `false`) $\rightarrow$ `trim()` bỏ qua ký tự $\rightarrow$ Được đánh giá bởi `strip()` $\rightarrow$ Gọi `Character.isWhitespace(0x2003)` $\rightarrow$ Trả về `true` dựa trên các thuộc tính Unicode $\rightarrow$ `strip()` loại bỏ ký tự.

### 7. Chuyển Đổi (Conversions): `toLowerCase()` và `toUpperCase()`
- Chuyển đổi các ký tự bằng cách sử dụng các quy tắc đặc thù của từng vùng (locale-specific). Hãy cẩn thận: `"title".toUpperCase()` trong vùng cài đặt (locale) Thổ Nhĩ Kỳ sẽ tạo ra `TİTLE` thay vì `TITLE`.

### 8. Thay Thế (Replacement): `replace()` so với `replaceAll()`
- `replace(char oldChar, char newChar)`: Thay thế tất cả các lần xuất hiện của một ký tự.
- `replace(CharSequence target, CharSequence replacement)`: Thay thế tất cả các chuỗi con khớp. **Không sử dụng biểu thức chính quy (regular expressions).**
- `replaceAll(String regex, String replacement)`: Thay thế các kết quả khớp của một **biểu thức chính quy**.
- `replaceFirst(String regex, String replacement)`: Chỉ thay thế kết quả khớp đầu tiên của một biểu thức chính quy.

```java
String src = "apple.orange.banana";

// replace() treats "." as a literal string
String r1 = src.replace(".", "-"); 
System.out.println(r1); // Output: apple-orange-banana

// replaceAll() treats "." as a regex wildcard (matches any character)
String r2 = src.replaceAll(".", "-"); 
System.out.println(r2); // Output: -------------------
```

### 9. Tách Chuỗi (Splitting): `split(String regex)` và `split(String regex, int limit)`
Tách chuỗi dựa trên các kết quả khớp với biểu thức chính quy.
- `split(regex)`: Loại bỏ các chuỗi trống ở cuối.
- `split(regex, limit)`:
  - Nếu `limit > 0`: Mẫu tìm kiếm (pattern) được áp dụng tối đa `limit - 1` lần, dẫn đến kích thước mảng tối đa là `limit`.
  - Nếu `limit < 0`: Mẫu tìm kiếm được áp dụng nhiều lần nhất có thể, và các chuỗi trống ở cuối **không** bị loại bỏ.
  - Nếu `limit == 0`: Tương tự như `split(regex)` (các chuỗi trống ở cuối bị loại bỏ).

```java
String s = "a:b:c::";
s.split(":").length;    // 3 -> {"a", "b", "c"} (trailing empty strings discarded)
s.split(":", -1).length; // 5 -> {"a", "b", "c", "", ""} (empty strings preserved)
s.split(":", 2).length;  // 2 -> {"a", "b:c::"} (capped at 2 splits)
```

### 10. Chuyển Đổi Sang Mảng Ký Tự (Converting to Character Array): `toCharArray()`
- Trả về một mảng ký tự mới được cấp phát có độ dài bằng độ dài của chuỗi này, chứa chuỗi ký tự được biểu diễn bởi chuỗi đó.

```java
String word = "Java";
char[] chars = word.toCharArray();
for (char c : chars) {
    System.out.print(c + " "); // Output: J a v a 
}
System.out.println();
```

---

## Phép Cộng Chuỗi và Các Tối Ưu Hóa Của Trình Biên Dịch (String Concatenation and Compiler Optimizations)

### Đánh Giá Tại Thời Điểm Biên Dịch (Compile-time Evaluation)
Nếu bạn cộng các chuỗi hằng (literals), trình biên dịch sẽ đánh giá chúng tại thời điểm biên dịch và đặt chuỗi cuối cùng trực tiếp vào mã bytecode (bytecode):
```java
String s = "a" + "b" + "c"; // Compiled as: String s = "abc";
```

### Đánh Giá Động (Dynamic Evaluation) (Biến)
Nếu biểu thức chứa các biến, Java sẽ đánh giá chúng tại thời điểm chạy (runtime):
- **Java 8 trở về trước:** Được chuyển đổi thành `new StringBuilder().append(a).append(b).toString()`.
- **Java 9 trở về sau:** Sử dụng `invokedynamic` để gọi `StringConcatFactory.makeConcatWithTemplate()`. Điều này tách biệt chiến lược cộng chuỗi khỏi bytecode, cho phép máy ảo Java (JVM) tối ưu hóa hoạt động này một cách động.

---

## Các Tùy Chọn Định Dạng Chi Tiết (Detailed Formatting Options)

`String.format()` và `System.out.printf()` định dạng chuỗi dựa trên một mẫu.

### Cú Pháp (Syntax):
$$\%[\text{argument\_index}\$][\text{flags}][\text{width}][.\text{precision}]\text{conversion}$$

### Các Chuyển Đổi Phổ Biến (Common Conversions):
- `%s`: Biểu diễn chuỗi
- `%d`: Số nguyên
- `%f`: Số dấu phẩy động (Floating-point)
- `%tF`: Ngày tháng theo định dạng YYYY-MM-DD
- `%n`: Ký tự xuống dòng đặc thù của nền tảng (platform-specific)

### Các Cờ Định Dạng và Độ Chính Xác (Formatting Flags and Precision):
- `%-15s`: Căn lề trái chuỗi bên trong một trường có độ rộng 15 ký tự.
- `%05d`: Thêm các chữ số 0 ở đầu số để tạo thành số có 5 chữ số.
- `%.2f`: Giới hạn số dấu phẩy động ở 2 chữ số thập phân.

```java
String.format("|%-10s|", "Java"); // "|Java      |"
String.format("%.3f", 3.14159);    // "3.142" (rounded)
String.format("%04d", 42);          // "0042"

// Detailed Formatting Example:
String name = "Alice";
int age = 30;
double gpa = 3.8567;

String formatted = String.format("Name: %s, Age: %d, GPA: %.2f", name, age, gpa);
System.out.println(formatted); // Output: Name: Alice, Age: 30, GPA: 3.86
```

---

## Khối Văn Bản (Text Blocks) (Java 15+)

Khối văn bản là các chuỗi hằng nhiều dòng được bao bọc trong dấu nháy kép ba `"""`.

### Thuật Toán Loại Bỏ Khoảng Trắng (Whitespace Stripping Algorithm)
1. **Khoảng Trắng Phát Sinh (Incidental Whitespace):** Trình biên dịch tính toán khoảng trắng ở đầu chung giữa tất cả các dòng và loại bỏ nó.
2. **Khoảng Trắng Cần Thiết (Essential Whitespace):** Thụt lề (indentation) vượt quá ngưỡng chung sẽ được giữ lại.
3. Dấu `"""` đóng xác định độ thụt lề tối thiểu. Việc di chuyển nó sang bên trái sẽ giữ lại các khoảng trống ở đầu.

### Các Chuỗi Thoát Đặc Biệt Trong Khối Văn Bản (Special Escape Sequences in Text Blocks)
- `\` (Nối dòng (Line Continuation)): Ngăn việc chèn ký tự xuống dòng ở cuối dòng.
- `\s` (Khoảng trắng ở cuối (Trailing Space)): Giữ lại các khoảng trắng ở cuối dòng đó (những khoảng trắng này theo mặc định sẽ bị loại bỏ).

```java
String html = """
              <html>
                  <body>\
                      <p>Hello World</p>\s\s
                  </body>
              </html>
              """;
```
- Dòng chứa thẻ `<p>` sẽ được gộp với dòng tiếp theo do ký tự `\`.
- Ký tự `\s\s` ở cuối dòng chứa thẻ `<p>` giữ lại hai khoảng trắng ở cuối.

---

## Các Lỗi Thường Gặp (Common Mistakes)

### 1. Ngoại Lệ `StringIndexOutOfBoundsException` Với `charAt` và `substring`
Chuỗi trong Java có chỉ mục bắt đầu từ 0 (zero-indexed). Giới hạn cho `charAt(index)` là từ `0` đến `length() - 1`. Chỉ mục `endIndex` của `substring(beginIndex, endIndex)` là loại trừ, nhưng không được vượt quá `length()`.
```java
String s = "hello";
char c = s.charAt(5); // StringIndexOutOfBoundsException (length is 5, max index is 4)
String sub = s.substring(2, 6); // StringIndexOutOfBoundsException (endIndex 6 exceeds length)
```

### 2. Tách Chuỗi Theo Các Ký Tự Đặc Biệt Của Biểu Thức Chính Quy (Splitting on Regex Metacharacters)
Sử dụng trực tiếp các ký tự đặc biệt của biểu thức chính quy như `.`, `|`, `+`, `*`, `?` trong `split()` hoặc `replaceAll()` mà không thoát ký tự (escaping) cho chúng.
```java
String data = "a.b.c";
String[] parts = data.split("."); // Incorrect! "." matches any character.
System.out.println(parts.length); // Prints 0 because it matched and split everything away.

// Correct: Escape the dot using a double backslash
String[] correctParts = data.split("\\.");
System.out.println(correctParts.length); // Prints 3
```

### 3. Nhầm Lẫn Giữa `replace` và `replaceAll` (Mixing up replace and replaceAll)
Giả định rằng `replace(CharSequence, CharSequence)` chỉ thay thế lần xuất hiện đầu tiên hoặc không thay thế tất cả. Trên thực tế, `replace()` thay thế TẤT CẢ các lần xuất hiện của chuỗi hằng mục tiêu, trong khi `replaceAll()` cũng thực hiện tương tự nhưng coi mục tiêu đó là một biểu thức chính quy.
```java
String sentence = "I love Java. Java is fun.";
// Both replace all occurrences, but replace() is faster/safer for plain text:
System.out.println(sentence.replace("Java", "Kotlin")); 
System.out.println(sentence.replaceAll("Java", "Kotlin"));
```

---

## Các Liên Kết Tham Khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#trim() (Oracle Java API: String.trim())
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#strip() (Oracle Java API: String.strip())
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Character.html#isWhitespace(int) (Oracle Java API: Character.isWhitespace())

---