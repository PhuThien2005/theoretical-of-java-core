# Các Câu Hỏi Phỏng Vấn Java Core Thường Gặp - Phần 1

## Mục Tiêu Học Tập

File này đề cập đến các câu hỏi phỏng vấn Java Core nền tảng liên quan đến kiến trúc JVM, quy tắc truyền tham trị (pass-by-value), cấu trúc bộ nhớ String và cơ chế hoạt động bên trong của các Collection (HashMap/ArrayList).

## Đề Cương Khái Niệm

- **`JVM, JDK, và JRE khác nhau như thế nào?`** — JVM, JDK, và JRE khác nhau như thế nào?: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Java có truyền tham chiếu không?`** — Java có truyền tham chiếu không?: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Sự khác biệt giữa == và .equals() là gì?`** — Sự khác biệt giữa == và .equals() là gì?: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Tại sao String lại bất biến (immutable)?`** — Tại sao String lại bất biến (immutable)?: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`String, StringBuilder, và StringBuffer khác nhau như thế nào?`** — String, StringBuilder, và StringBuffer khác nhau như thế nào?: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`HashMap hoạt động như thế nào?`** — HashMap hoạt động như thế nào?: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`HashMap có những cải tiến gì trong Java 8?`** — HashMap có những cải tiến gì trong Java 8?: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`ArrayList và LinkedList khác nhau như thế nào?`** — ArrayList và LinkedList khác nhau như thế nào?: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

---

## Ghi Chú Chi Tiết

### JVM, JDK, và JRE Khác Nhau Như Thế Nào?

- **JVM (Java Virtual Machine - Máy ảo Java)**: Bộ máy thực thi Java bytecode (các tệp `.class`). Nó xử lý quản lý bộ nhớ (Stack/Heap), Bộ thu gom rác (Garbage Collection), và biên dịch JIT để chuyển đổi bytecode thành mã máy gốc.
- **JRE (Java Runtime Environment - Môi trường chạy Java)**: Bao gồm JVM và các thư viện lớp cốt lõi (`java.lang`, `java.util`, v.v.) cần thiết để *chạy* các ứng dụng Java.
- **JDK (Java Development Kit - Bộ công cụ phát triển Java)**: Bao gồm JRE và các công cụ phát triển (trình biên dịch `javac`, trình đóng gói `jar`, trình gỡ lỗi, v.v.) cần thiết để *viết* và biên dịch các chương trình Java.

---

### Java Có Truyền Tham Chiếu Không?

Java **tuân thủ nghiêm ngặt việc truyền tham trị (pass-by-value)**. Khi một tham chiếu đối tượng được truyền vào một phương thức, Java sẽ sao chép giá trị của tham chiếu đó (con trỏ địa chỉ bộ nhớ).
- **Quy tắc**: Bạn có thể sửa đổi *nội dung* của đối tượng vì cả hai tham chiếu đều trỏ đến cùng một vị trí bộ nhớ, nhưng bạn không thể thay đổi chính bản thân tham chiếu của bên gọi.

```java
public class ReferenceTest {
    public static void main(String[] args) {
        User user = new User("Alice");
        changeName(user); // Modifies the object's contents
        System.out.println(user.name); // Prints "Bob"

        reassign(user); // Tries to reassign the reference
        System.out.println(user.name); // Still prints "Bob" (no change)
    }

    static void changeName(User u) {
        u.name = "Bob";
    }

    static void reassign(User u) {
        u = new User("Charlie"); // Only reassigns the local copied parameter
    }
}
```

---

### == so với .equals()

- **`==`** — ==: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`.equals()`** — .equals(): Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

---

### Tính Bất Biến Của String & Các Bộ Dựng (Builder)

Một khi đối tượng `String` được tạo ra trong Java, giá trị của nó không thể bị sửa đổi.

- **Tại sao String lại bất biến?**:
  1. **String Pool (Bể chứa chuỗi)**: Cho phép chia sẻ các chuỗi literal để tiết kiệm không gian heap.
  2. **Bảo mật**: Các chuỗi đại diện cho URL cơ sở dữ liệu, đường dẫn tệp và các kết nối mạng; tính bất biến ngăn chặn sự can thiệp ác ý.
  3. **An toàn luồng (Thread Safety)**: Có thể được chia sẻ giữa các luồng mà không cần đồng bộ hóa.
  4. **Lưu bộ đệm HashCode (HashCode Caching)**: Giá trị hashcode được lưu vào bộ đệm khi chuỗi được tạo, giúp nó truy cập cực kỳ nhanh khi được sử dụng làm khóa trong `HashMap`.

- **String so với StringBuilder so với StringBuffer**:
  - `String`: Bất biến. Mỗi sửa đổi đều tạo ra một đối tượng mới.
  - `StringBuilder`: Khả biến (mutable) và được thiết kế cho các hoạt động đơn luồng. Tốc độ nhanh.
  - `StringBuffer`: Khả biến nhưng an toàn luồng. Sử dụng các phương thức đồng bộ hóa, làm tăng thêm chi phí hiệu năng.

---

### HashMap & Các Cải Tiến Trong Java 8

Một `HashMap` lưu trữ các cặp khóa-giá trị (key-value) sử dụng logic băm.

- **Cấu trúc bên trong**: Sử dụng một mảng các nút (gọi là bucket).
  1. Tính toán hashcode của khóa bằng hàm `hash(key)`.
  2. Ánh xạ giá trị băm tới một chỉ mục (index) trong mảng bucket: `index = hash & (n - 1)`.
  3. Nếu nhiều khóa cùng ánh xạ tới một chỉ mục (xung đột/collision), chúng sẽ được liên kết trong một Danh sách Liên kết (Linked List).
- **Cải tiến trong Java 8**:
  - Nếu danh sách liên kết của một bucket phát triển vượt quá ngưỡng **8** phần tử (và tổng dung lượng bảng >= 64), danh sách liên kết sẽ được chuyển đổi thành **Cây Đỏ-Đen (Red-Black Tree)** (quá trình này gọi là Treeification - cấu trúc lại thành cây).
  - Điều này cải thiện thời gian tìm kiếm trong trường hợp xấu nhất từ **O(N)** xuống **O(log N)**, giúp bảo vệ chống lại các cuộc tấn công từ chối dịch vụ (Denial of Service - DoS) cố tình kích hoạt xung đột băm.

---

### ArrayList so với LinkedList

- **ArrayList**:
  - Triển khai `List` sử dụng một mảng có thể thay đổi kích thước.
  - Truy cập ngẫu nhiên là **O(1)**.
  - Chèn/xóa ở giữa là **O(N)** vì các phần tử phải được dịch chuyển.
- **LinkedList**:
  - Triển khai `List` sử dụng một danh sách liên kết kép.
  - Truy cập ngẫu nhiên là **O(N)** vì nó phải duyệt từ đầu hoặc cuối.
  - Chèn/xóa là **O(1)** nếu tham chiếu nút đã được biết trước (chỉ cần cập nhật các con trỏ).

---

## Các Lỗi Thường Gặp & Bẫy Cú Pháp

### 1. Bẫy Tham Chiếu Trong Truyền Tham Trị
Giả định rằng một phương thức có thể gán lại một biến tham chiếu bên ngoài:
```java
void modify(List<String> list) {
    list = new ArrayList<>(); // Incorrect: Caller's list reference is unaffected!
    list.add("newVal");
}
```

### 2. Gọi `equals()` Trên Đối Tượng Null
Gọi `equals()` trên một tham chiếu null sẽ ném ra lỗi `NullPointerException`. Luôn luôn đặt các hằng số hoặc các giá trị chắc chắn không phải null ở phía bên trái của phương thức `.equals()`.
```java
String status = getStatus();
if (status.equals("ACTIVE")) {} // Crash if status is null!
if ("ACTIVE".equals(status)) {} // Safe!
```

---

## Tại Sao JDK, JRE, và JVM Khác Nhau

Nền tảng Java được thiết kế với ba tầng lồng nhau để hỗ trợ mô hình phát triển phần mềm "viết một lần, chạy mọi nơi". JVM là công cụ thực thi cốt lõi dịch mã bytecode trung gian thành các chỉ thị nền tảng gốc, quản lý lập lịch luồng thời gian chạy, ngăn xếp cuộc gọi (call stack) và bộ thu gom rác. JRE bao bọc JVM bằng cách đóng gói nó cùng các tệp lớp thư viện tiêu chuẩn Java (rt.jar/modules) và các bootstrap class loader cần thiết để thực thi ứng dụng. JDK đại diện cho bộ công cụ phát triển hoàn chỉnh, bổ sung các công cụ biên dịch, phân tích hiệu năng (profiling) và gỡ lỗi như `javac`, `jdb`, và `jcmd` vốn không bao giờ cần thiết để chạy một chương trình đơn thuần. Việc phân chia nền tảng theo cách này cho phép người dùng cuối cài đặt các runtime gọn nhẹ tối thiểu, trong khi các nhà phát triển sở hữu một bộ công cụ đầy đủ để biên dịch và phân tích hiệu năng.

### Phân tầng kiến trúc Java (Mental Model)

```text
+-------------------------------------------------------------+
| JDK (Java Development Kit)                                  |
|   - javac (Trình biên dịch)                                 |
|   - jdb (Trình gỡ lỗi)                                      |
|   - visualvm / jcmd (Chẩn đoán & Phân tích hiệu năng)       |
|  +-------------------------------------------------------+  |
|  | JRE (Java Runtime Environment)                        |  |
|  |   - Thư viện tiêu chuẩn (java.base, java.util, v.v.)  |  |
|  |   - Công cụ khởi chạy (java)                          |  |
|  |  +-------------------------------------------------+  |  |
|  |  | JVM (Java Virtual Machine)                      |  |  |
|  |  |   - Hệ thống Class Loader                       |  |  |
|  |  |   - Vùng Dữ liệu Runtime (Stack, Heap, Method)  |  |  |
|  |  |   - Công cụ Thực thi (JIT, Trình thông dịch, GC)|  |  |
|  |  +-------------------------------------------------+  |  |
|  +-------------------------------------------------------+  |
+-------------------------------------------------------------+
```

### Ví Dụ Mã Nguồn

Mã nguồn sau đây minh họa rằng quá trình thực thi yêu cầu công cụ của nhà phát triển `javac` để biên dịch, nhưng chỉ yêu cầu trình chạy runtime `java` để thực thi.

```java
// Save as: EnvironmentTest.java
// Run compilation: javac EnvironmentTest.java  <- Provided by JDK
// Run execution:   java EnvironmentTest        <- Provided by JRE/JVM

public class EnvironmentTest {
    public static void main(String[] args) {
        // Checking if JDK tools are on the runtime path is a compilation/deployment step,
        // but executing JVM property retrieval is a runtime environment step.
        String javaVersion = System.getProperty("java.version");
        String javaHome = System.getProperty("java.home");
        
        System.out.println("Java Version: " + javaVersion);
        System.out.println("Java Home (JRE Location): " + javaHome);
        // Sample Output:
        // Java Version: 17.0.1
        // Java Home (JRE Location): /usr/lib/jvm/java-17-openjdk
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả

```text
Viết mã nguồn Java (.java)
  &rarr; Biên dịch bằng `javac` (công cụ JDK) để tạo ra mã bytecode di động (.class)
  &rarr; Thực thi bytecode bằng trình chạy `java` (tiện ích runtime JRE)
  &rarr; Class Loader tải bytecode vào bộ nhớ (vùng runtime JVM)
  &rarr; Trình biên dịch JIT/Trình thông dịch dịch bytecode thành các chỉ thị CPU cụ thể (JVM core)
  &rarr; Chương trình thực thi trên phần cứng Hệ điều hành đích
```
