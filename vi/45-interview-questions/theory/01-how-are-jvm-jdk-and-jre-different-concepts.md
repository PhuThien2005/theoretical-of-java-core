# Các câu hỏi phỏng vấn Java Core phổ biến - Phần 1 (Common Java Core Interview Questions - Part 1)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm các câu hỏi phỏng vấn Java Core cơ bản liên quan đến kiến trúc JVM, quy tắc truyền tham trị (pass-by-value), cấu trúc bộ nhớ String và các chi tiết bên trong Collection (HashMap/ArrayList).

## Phạm vi đề cương (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `How are JVM, JDK, and JRE different?` | JDK là bộ công cụ phát triển, JRE là môi trường chạy ứng dụng, và JVM thực thi bytecode. |
| `Does Java pass references?` | Java hoàn toàn là truyền tham trị (pass-by-value). Nó truyền bản sao của các giá trị tham chiếu (con trỏ), chứ không truyền bản thân biến đó. |
| `What is the difference between == and .equals()?` | Toán tử `==` kiểm tra sự bằng nhau của tham chiếu/địa chỉ bộ nhớ; phương thức `.equals()` kiểm tra sự bằng nhau của giá trị logic. |
| `Why is String immutable?` | Vì lý do bảo mật, an toàn đồng bộ hóa (synchronization safety), lưu bộ đệm mã băm (caching hashcodes) và chia sẻ trong String Pool. |
| `How are String, StringBuilder, and StringBuffer different?` | String là bất biến; StringBuilder là khả biến và không an toàn đa luồng; StringBuffer là khả biến và được đồng bộ hóa. |
| `How does HashMap work?` | Sử dụng một mảng các bucket, băm các khóa, xử lý đụng độ (collisions) thông qua danh sách liên kết (linked lists) và các nút cây (tree nodes). |
| `What improvements did HashMap have in Java 8?` | Chuyển đổi thành cây (Treeification) đối với các bucket chứa từ 8 phần tử trở lên, giảm thời gian tìm kiếm trong trường hợp xấu nhất từ O(N) xuống O(log N). |
| `How are ArrayList and LinkedList different?` | ArrayList sử dụng mảng động (truy cập O(1)); LinkedList sử dụng danh sách liên kết kép (thêm/xóa O(1)). |

---

## Ghi chú chi tiết (Detailed Notes)

### JVM, JDK và JRE khác nhau như thế nào? (How are JVM, JDK, and JRE different?)

- **JVM (Java Virtual Machine)**: Động cơ thực thi bytecode Java (các tệp `.class`). Nó xử lý quản lý bộ nhớ (Stack/Heap), Thu gom rác (Garbage Collection), và biên dịch JIT để chuyển đổi bytecode thành mã máy bản địa.
- **JRE (Java Runtime Environment)**: Bao gồm JVM và các thư viện lớp lõi (`java.lang`, `java.util`, v.v.) cần thiết để *chạy* các ứng dụng Java.
- **JDK (Java Development Kit)**: Bao gồm JRE và các công cụ phát triển (trình biên dịch `javac`, công cụ đóng gói `jar`, trình gỡ lỗi, v.v.) cần thiết để *viết* và biên dịch các chương trình Java.

---

### Java có truyền tham chiếu không? (Does Java pass references?)

Java là **truyền tham trị hoàn toàn (strictly pass-by-value)**. Khi một tham chiếu đối tượng được truyền vào một phương thức, Java sẽ sao chép giá trị của tham chiếu đó (con trỏ địa chỉ bộ nhớ).
- **Quy tắc**: Bạn có thể sửa đổi *nội dung* của đối tượng vì cả hai tham chiếu đều trỏ đến cùng một vị trí bộ nhớ, nhưng bạn không thể thay đổi bản thân tham chiếu của người gọi.

```java
public class ReferenceTest {
    public static void main(String[] args) {
        User user = new User("Alice");
        changeName(user); // Sửa đổi nội dung của đối tượng
        System.out.println(user.name); // In ra "Bob"

        reassign(user); // Cố gắng gán lại tham chiếu
        System.out.println(user.name); // Vẫn in ra "Bob" (không thay đổi)
    }

    static void changeName(User u) {
        u.name = "Bob";
    }

    static void reassign(User u) {
        u = new User("Charlie"); // Chỉ gán lại cho biến tham số bản sao cục bộ
    }
}
```

---

### Toán tử == so với phương thức .equals() (== vs .equals())

- **`==`**: So sánh các kiểu nguyên thủy (primitives) về sự bằng nhau của giá trị, hoặc so sánh các tham chiếu đối tượng để kiểm tra xem chúng có trỏ đến cùng một địa chỉ bộ nhớ chính xác trên heap hay không.
- **`.equals()`**: Một phương thức trong lớp `java.lang.Object`. Theo mặc định, nó sử dụng `==` để so sánh danh tính. Các lớp như `String`, `Integer`, và `Double` ghi đè (override) phương thức này để thực hiện **so sánh logic** dữ liệu thực tế.

---

### Tính bất biến của String & các lớp Builder (String Immutability & Builders)

Một khi đối tượng `String` được tạo ra trong Java, giá trị của nó không thể bị sửa đổi.

- **Tại sao String là bất biến?**:
  1. **String Pool (Vùng nhớ chuỗi dùng chung)**: Cho phép chia sẻ các chuỗi ký tự trực tiếp để tiết kiệm không gian heap.
  2. **Bảo mật (Security)**: Các chuỗi đại diện cho các URL cơ sở dữ liệu, đường dẫn tệp và kết nối mạng; tính bất biến ngăn chặn sự can thiệp độc hại.
  3. **An toàn đa luồng (Thread Safety)**: Có thể được chia sẻ giữa các luồng mà không cần đồng bộ hóa.
  4. **Lưu bộ đệm mã băm (HashCode Caching)**: Mã băm được lưu lại dưới dạng bộ đệm khi chuỗi được tạo, giúp nó xử lý cực kỳ nhanh khi được sử dụng làm khóa trong một `HashMap`.

- **Sự khác biệt giữa String, StringBuilder và StringBuffer**:
  - `String`: Bất biến. Mỗi sửa đổi đều tạo ra một đối tượng mới.
  - `StringBuilder`: Khả biến và được thiết kế cho các thao tác đơn luồng. Tốc độ nhanh.
  - `StringBuffer`: Khả biến nhưng an toàn đa luồng. Sử dụng các phương thức được đồng bộ hóa, điều này làm phát sinh chi phí hiệu năng.

---

### HashMap & các cải tiến trong Java 8 (HashMap & Java 8 Improvements)

Một `HashMap` lưu trữ các cặp khóa-giá trị bằng cách sử dụng logic băm.

- **Cấu trúc bên trong**: Sử dụng một mảng các nút (buckets).
  1. Tính toán mã băm của khóa bằng cách sử dụng `hash(key)`.
  2. Ánh xạ giá trị băm vào một chỉ mục trong mảng bucket: `index = hash & (n - 1)`.
  3. Nếu nhiều khóa cùng ánh xạ vào một chỉ mục (đụng độ - collision), chúng được liên kết lại trong một Danh sách liên kết (Linked List).
- **Cải tiến trong Java 8**:
  - Nếu danh sách liên kết của một bucket vượt quá ngưỡng **8** phần tử (và tổng dung lượng bảng >= 64), danh sách liên kết đó sẽ được chuyển đổi thành một **Cây Đỏ-Đen (Cây hóa - Treeification)**.
  - Điều này cải thiện thời gian tìm kiếm trong trường hợp xấu nhất từ **O(N)** xuống **O(log N)**, giúp bảo vệ ứng dụng khỏi các cuộc tấn công Từ chối dịch vụ (Denial of Service) cố ý kích hoạt đụng độ mã băm.

---

### ArrayList so với LinkedList (ArrayList vs LinkedList)

- **ArrayList**:
  - Triển khai interface `List` bằng cách sử dụng một mảng có thể thay đổi kích thước.
  - Truy cập ngẫu nhiên là **O(1)**.
  - Việc thêm/xóa ở giữa mảng là **O(N)** vì các phần tử phải dịch chuyển vị trí.
- **LinkedList**:
  - Triển khai interface `List` bằng cách sử dụng một danh sách liên kết kép (doubly-linked list).
  - Truy cập ngẫu nhiên là **O(N)** vì nó phải duyệt qua từ đầu hoặc cuối danh sách.
  - Việc thêm/xóa là **O(1)** nếu tham chiếu nút đã được biết trước (chỉ cần cập nhật các con trỏ).

---

## Các lỗi thường gặp & Cạm bẫy (Common Mistakes & Traps)

### 1. Cạm bẫy truyền tham chiếu của truyền tham trị (The Pass-by-Value Reference Trap)
Giả định sai lầm rằng một phương thức có thể gán lại một biến tham chiếu bên ngoài:
```java
void modify(List<String> list) {
    list = new ArrayList<>(); // Sai: Tham chiếu list của người gọi không bị ảnh hưởng!
    list.add("newVal");
}
```

### 2. Gọi phương thức equals() trên đối tượng Null (Calling equals() on Null)
Việc gọi phương thức `equals()` trên một tham chiếu null sẽ ném ra ngoại lệ `NullPointerException`. Luôn luôn đặt các hằng số hoặc các giá trị chắc chắn khác null ở phía bên trái của phương thức `.equals()`.
```java
String status = getStatus();
if (status.equals("ACTIVE")) {} // Sập nếu status là null!
if ("ACTIVE".equals(status)) {} // An toàn!
```

---

## Tại sao JDK, JRE và JVM khác nhau (Why JDK, JRE, and JVM Differ)

Nền tảng Java được thiết kế với ba lớp lồng nhau để hỗ trợ việc biên dịch một lần, chạy mọi nơi.

JVM là động cơ thực thi cốt lõi dịch bytecode trung gian thành các lệnh nền tảng bản địa, quản lý lập lịch luồng lúc chạy, các call stack và thu gom rác.

JRE bao bọc JVM bằng cách đóng gói nó cùng với các tệp lớp thư viện tiêu chuẩn Java (rt.jar/modules) và các bộ nạp lớp bootstrap cần thiết để thực thi ứng dụng.

JDK đại diện cho bộ công cụ phát triển hoàn chỉnh, bổ sung thêm các công cụ biên dịch, phân tích hiệu năng và gỡ lỗi như `javac`, `jdb`, và `jcmd` vốn không bao giờ cần thiết chỉ để chạy một chương trình.

Việc phân chia nền tảng theo cách này cho phép người dùng cuối cài đặt các môi trường chạy nhẹ tối giản, trong khi các lập trình viên vẫn giữ lại một bộ công cụ hoàn chỉnh để biên dịch và phân tích hiệu năng.

### Mô hình tư duy (Mental Model)

```text
+-------------------------------------------------------------+
| JDK (Java Development Kit)                                  |
|   - javac (Trình biên dịch)                                 |
|   - jdb (Trình gỡ lỗi)                                      |
|   - visualvm / jcmd (Chẩn đoán & Phân tích hiệu năng)       |
|  +-------------------------------------------------------+  |
|  | JRE (Java Runtime Environment)                        |  |
|  |   - Các thư viện tiêu chuẩn (java.base, java.util...) |  |
|  |   - Các công cụ khởi chạy (java)                      |  |
|  |  +-------------------------------------------------+  |  |
|  |  | JVM (Java Virtual Machine)                      |  |  |
|  |  |   - Hệ thống Class Loader                       |  |  |
|  |  |   - Vùng dữ liệu chạy (Stack, Heap, Method Area)|  |  |
|  |  |   - Động cơ thực thi (JIT, Interpreter, GC)     |  |  |
|  |  +-------------------------------------------------+  |  |
|  +-------------------------------------------------------+  |
+-------------------------------------------------------------+
```

### Ví dụ Code (Code Example)

Đoạn mã sau minh họa quá trình thực thi yêu cầu công cụ phát triển `javac` để biên dịch, nhưng chỉ yêu cầu trình chạy `java` của JRE để thực thi.

```java
// Lưu tên file: EnvironmentTest.java
// Biên dịch: javac EnvironmentTest.java  <- Được cung cấp bởi JDK
// Thực thi:  java EnvironmentTest        <- Được cung cấp bởi JRE/JVM

public class EnvironmentTest {
    public static void main(String[] args) {
        // Việc kiểm tra xem các công cụ JDK có nằm trên đường dẫn chạy hay không là một bước biên dịch/triển khai,
        // nhưng việc thực thi lấy thuộc tính JVM là một bước của môi trường chạy.
        String javaVersion = System.getProperty("java.version");
        String javaHome = System.getProperty("java.home");
        
        System.out.println("Java Version: " + javaVersion);
        System.out.println("Java Home (JRE Location): " + javaHome);
        // Đầu ra ví dụ:
        // Java Version: 17.0.1
        // Java Home (JRE Location): /usr/lib/jvm/java-17-openjdk
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)


```text
Viết mã nguồn Java (`.java`)
  → Biên dịch bằng `javac` (công cụ JDK) để tạo ra bytecode di động (`.class`)
  → Thực thi bytecode bằng trình chạy `java` (công cụ JRE)
  → Class Loader nạp bytecode vào bộ nhớ (vùng nhớ chạy JVM)
  → Trình biên dịch JIT/Trình thông dịch dịch bytecode thành mã máy cụ thể của CPU (lõi JVM)
  → Chương trình thực thi trên phần cứng Hệ điều hành đích.
```

