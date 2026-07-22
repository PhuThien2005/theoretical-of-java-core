# Các Câu Hỏi Phỏng Vấn Java Core Thường Gặp - Phần 2 (Common Java Core Interview Questions - Part 2)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này trình bày các câu hỏi phỏng vấn Java Core ở mức độ trung cấp liên quan đến các thao tác trên Set, các bổ từ sửa đổi (final, static), kế thừa ngoại lệ, các mối quan hệ hướng đối tượng (OOP) và ngữ cảnh thực thể (instance context).

## Khung Nội Dung (Outline Coverage)

- **`How does HashSet remove duplicates?`** — Sử dụng một `HashMap` bên dưới để lưu trữ các phần tử dưới dạng các khóa; việc kiểm tra trùng lặp dựa trên hai phương thức `hashCode()` và `equals()`.
- **`How are final, finally, and finalize different?`** — `final` là một từ khóa bổ từ; `finally` là một khối lệnh trong cấu trúc try-catch; `finalize()` là một phương thức dọn dẹp tài nguyên đã bị loại bỏ (deprecated).
- **`How are checked and unchecked exceptions different?`** — Ngoại lệ checked phải được khai báo hoặc bắt lại tại thời điểm biên dịch; ngoại lệ unchecked đại diện cho các lỗi logic trong thời gian chạy.
- **`How are abstract class and interface different?`** — Lớp trừu tượng cho phép lưu trữ trạng thái và chỉ hỗ trợ đơn kế thừa; giao diện hỗ trợ đa kế thừa và định nghĩa các hành vi mặc định.
- **`How are overload and override different?`** — Nạp chồng (Overload) là tính đa hình tại thời điểm biên dịch (cùng tên, khác tham số); Ghi đè (Override) là tính đa hình tại thời điểm chạy (mối quan hệ cha-con).
- **`Can static methods be overridden?`** — Không. Chúng chỉ có thể bị ẩn đi (hidden) vì các phương thức static được liên kết tĩnh tại thời điểm biên dịch dựa trên kiểu dữ liệu của lớp.
- **`Are constructors inherited?`** — Không. Chúng phải được khai báo lại trong lớp con hoặc được gọi thông qua hàm khởi tạo của lớp cha bằng cách sử dụng `super()`.
- **`How are this and super different?`** — `this` tham chiếu đến thực thể hiện tại của lớp; `super` tham chiếu đến ngữ cảnh thực thể của lớp cha trực tiếp.

---

## Ghi Chú Chi Tiết (Detailed Notes)

### Cách HashSet loại bỏ các phần tử trùng lặp (How does HashSet remove duplicates?)

Bên dưới lớp vỏ, một `HashSet` được hỗ trợ bởi một thực thể của `HashMap`:
```java
public class HashSet<E> {
    private transient HashMap<E, Object> map;
    private static final Object PRESENT = new Object(); // Giá trị giả lập (Dummy value)

    public boolean add(E e) {
        return map.put(e, PRESENT) == null; // HashMap trả về null nếu khóa chưa tồn tại
    }
}
```
- **Phát hiện Trùng lặp (Duplicate Detection)**: Khi thêm một phần tử `e`, `HashSet` sẽ gọi phương thức `e.hashCode()` để tìm thùng (bucket) phù hợp trong đối tượng `HashMap` nội bộ. Nếu một phần tử khác đã tồn tại với cùng mã băm, nó sẽ gọi `e.equals(existingElement)`. Nếu phương thức `equals` trả về `true`, phần tử trùng lặp sẽ bị từ chối/ghi đè.

---

### phân biệt final vs. finally vs. finalize()

- **`final`**: Một từ khóa bổ từ (modifier):
  - **Biến (Variable)**: Cấm gán lại giá trị (trở thành hằng số).
  - **Phương thức (Method)**: Cấm các lớp con ghi đè phương thức này.
  - **Lớp (Class)**: Cấm lớp khác kế thừa (ví dụ: `String`, `Integer`).
- **`finally`**: Một khối mã được sử dụng trong cấu trúc `try-catch-finally`. Nó đảm bảo luôn được thực thi bất kể ngoại lệ có bị ném ra hay được bắt lại hay không, giúp nó trở nên lý tưởng cho việc dọn dẹp các tài nguyên (đóng file, ngắt kết nối mạng).
- **`finalize()`**: Một phương thức trong lớp `java.lang.Object`. Trong lịch sử, nó được gọi bởi Bộ thu gom rác trước khi thu hồi bộ nhớ của đối tượng. Phương thức này đã bị **loại bỏ (deprecated)** kể từ Java 9 vì nó hoạt động không thể dự đoán trước, làm giảm hiệu năng và dễ gây rò rỉ tài nguyên.

---

### Ngoại lệ Checked vs. Unchecked (Checked vs. Unchecked Exceptions)

- **Ngoại lệ Checked (Checked Exceptions)**:
  - Là các lớp con trực tiếp của `Exception` (ngoại trừ `RuntimeException`).
  - Được kiểm tra bởi trình biên dịch. Chương trình bắt buộc phải xử lý chúng trong khối lệnh `try-catch` hoặc khai báo chúng trên chữ ký phương thức bằng từ khóa `throws` (ví dụ: `IOException`, `SQLException`).
- **Ngoại lệ Unchecked (Unchecked Exceptions)**:
  - Là các lớp con của `RuntimeException` hoặc `Error`.
  - Không bị kiểm tra tại thời điểm biên dịch. Chúng đại diện cho các lỗi lập trình hoặc các tình huống nghiêm trọng không thể khôi phục (ví dụ: `NullPointerException`, `IndexOutOfBoundsException`, `OutOfMemoryError`).

---

### Lớp Trừu Tượng vs. Giao Diện (Abstract Class vs. Interface)

**Lớp Trừu Tượng (Abstract Class)** hỗ trợ đơn kế thừa (`extends`), có thể chứa các biến thực thể (trạng thái), có thể định nghĩa các hàm khởi tạo và có thể có các phương thức private, protected, cụ thể và trừu tượng.

**Giao Diện (Interface)** hỗ trợ đa kế thừa (`implements`), chỉ có thể chứa các hằng số `public static final`, không thể chứa các hàm khởi tạo và mặc định các phương thức trừu tượng là public (có thể chứa các phương thức `default` và `static`).

---

### Nạp chồng vs. Ghi đè (Overloading vs. Overriding)

- **Nạp chồng phương thức (Method Overloading)**:
  - Xảy ra trong cùng một lớp (hoặc giữa lớp cha/con).
  - Có cùng tên phương thức nhưng khác danh sách tham số (khác kiểu dữ liệu, số lượng, hoặc thứ tự tham số).
  - Là tính đa hình tại **thời điểm biên dịch** (tĩnh - compile-time/static polymorphism).
- **Ghi đè phương thức (Method Overriding)**:
  - Xảy ra giữa lớp cha và lớp con.
  - Có cùng tên phương thức, cùng danh sách tham số và cùng kiểu trả về (hoặc kiểu đồng biến - covariant return type).
  - Là tính đa hình tại **thời điểm chạy** (động - runtime/dynamic polymorphism).

---

### Các phương thức static có thể bị ghi đè không? (Can static methods be overridden?)

Không. Việc ghi đè phương thức phụ thuộc vào liên kết động (dynamic binding) tại thời điểm chạy (dựa trên kiểu đối tượng thực tế trên heap). Các phương thức static được liên kết tĩnh tại thời điểm biên dịch dựa trên **kiểu tham chiếu** được khai báo của biến.
- Nếu một lớp con định nghĩa một phương thức static có cùng chữ ký (signature) với một phương thức static trong lớp cha, hành vi đó được gọi là **Ẩn phương thức (Method Hiding)**, chứ không phải ghi đè.

```java
class Parent {
    static void display() { System.out.println("Parent"); }
}
class Child extends Parent {
    static void display() { System.out.println("Child"); } // Ẩn phương thức Parent.display()
}

// Cách sử dụng:
Parent p = new Child();
p.display(); // In ra "Parent" (kiểu biên dịch của tham chiếu p là Parent)
```

---

### Các hàm khởi tạo có được kế thừa không? (Are constructors inherited?)

Không, các hàm khởi tạo không được kế thừa bởi các lớp con.
- Lớp con phải tự định nghĩa các hàm khởi tạo của riêng nó. Nếu lớp cha không có hàm khởi tạo mặc định (hàm không tham số), hàm khởi tạo của lớp con bắt buộc phải gọi rõ ràng một trong các hàm khởi tạo có tham số của lớp cha bằng từ khóa `super(...)` ở dòng lệnh đầu tiên của thân hàm.

---

### phân biệt this vs. super

- **`this`**: Đại diện cho con trỏ thực thể hiện tại của lớp. Được sử dụng để gọi các hàm khởi tạo khác của cùng một lớp (`this()`), tham chiếu đến các biến thực thể khi bị trùng tên với tham số (`this.name = name`), hoặc truyền đối tượng hiện tại làm tham số.
- **`super`**: Đại diện cho ngữ cảnh thực thể của lớp cha trực tiếp. Được sử dụng để gọi các hàm khởi tạo của lớp cha (`super()`) hoặc truy cập các phương thức bị ghi đè của lớp cha (`super.doWork()`).

---

## Các Sai Lầm Phổ Biến & Cạm Bẫy (Common Mistakes & Traps)

### 1. Vi phạm Ràng buộc giữa HashSet/HashMap (The HashSet/HashMap Contract Violation)
Thêm một đối tượng vào một `HashSet` mà không ghi đè đồng thời cả hai phương thức `hashCode()` và `equals()`:
```java
class Student {
    String name;
    Student(String name) { this.name = name; }
    // Không ghi đè hashCode() hay equals()!
}

Set<Student> set = new HashSet<>();
set.add(new Student("Bob"));
set.add(new Student("Bob")); // Set bây giờ chứa HAI phần tử vì các tham chiếu đối tượng là khác nhau!
```

### 2. Lầm tưởng khối `finally` luôn luôn thực thi (Assuming finally always executes)
Khối `finally` chạy trong hầu hết mọi trường hợp, nhưng nó sẽ KHÔNG chạy nếu:
- Phương thức `System.exit(0)` được gọi trong khối try hoặc catch.
- Hệ điều hành hoặc JVM bị sập (crash) đột ngột hoặc mất nguồn điện.
- Luồng (thread) đang chạy khối try-catch bị dừng/ngắt kết nối từ bên ngoài.

---

## Tại sao HashSet Tận Dụng HashMap Để Loại Bỏ Trùng Lặp (Why HashSet Leverages HashMap to Remove Duplicates)

Một lớp `HashSet` trong Java không tự triển khai logic xử lý va chạm băm của riêng nó; thay vào đó, nó ủy quyền toàn bộ việc lưu trữ và kiểm tra tính duy nhất cho một thực thể `HashMap` nội bộ. Khi một `HashSet` được khởi tạo, nó sẽ tạo ra một `HashMap` private, transient, nơi các phần tử của set đóng vai trò là các khóa (keys) của map, và một đối tượng giả lập dùng chung (`PRESENT`) được sử dụng làm giá trị hằng số (value). Bởi vì tập hợp khóa của một `HashMap` bắt buộc phải là duy nhất, việc gọi `add(element)` thực chất là thực hiện lệnh gọi `map.put(element, PRESENT)` bên dưới. Nếu phần tử đó đã tồn tại trong map, phương thức `put()` sẽ trả về giá trị cũ (`PRESENT`), từ đó khiến `add()` trả về `false` để báo hiệu rằng phần tử trùng lặp đã bị từ chối. Bằng cách tận dụng các kỹ thuật giải quyết va chạm mạnh mẽ của `HashMap` (như danh sách liên kết và các thùng chứa dạng cây - treeified buckets), Java tránh được việc trùng lặp mã nguồn và đảm bảo các thao tác tìm kiếm, chèn và xóa phần tử diễn ra với độ phức tạp thời gian trung bình là O(1).

### Mô hình Tư duy (Mental Model)

```text
  HashSet: [add(Key1)] ──(ủy quyền)──> HashMap: put(Key1, PRESENT)
  +-------------------------------------------------------+
  | Các khóa của HashMap (Các phần tử của Set)             |
  |  - "Java" -> ánh xạ tới PRESENT (đối tượng giả)       |
  |  - "Python" -> ánh xạ tới PRESENT                     |
  |  - ["Java" được thêm lại -> put() trả về PRESENT -> false] |
  +-------------------------------------------------------+
```

### Ví Dụ Mã Nguồn (Code Example)

Đoạn mã dưới đây minh họa cách `HashSet` sử dụng phương thức `add()` và cách nó gọi hoạt động `put()` của `HashMap` bên dưới.

```java
import java.util.HashSet;

public class HashSetMechanismDemo {
    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();
        // map.put("Java", PRESENT) trả về null -> add() trả về true
        System.out.println(set.add("Java")); // Kết quả: true
        // map.put("Java", PRESENT) trả về PRESENT -> add() trả về false
        System.out.println(set.add("Java")); // Kết quả: false
    }
}
```

### Chuỗi Nhân Quả (Cause-Effect Chain)

```text
Gọi phương thức hashSet.add(element)
  → Ủy quyền xử lý nội bộ cho map.put(element, PRESENT)
  → HashMap tính toán giá trị hash(element) và xác định chỉ số thùng (bucket index)
  → HashMap quét qua các nút trong thùng để kiểm tra key.equals(element)
  → NẾU tìm thấy khóa khớp: ghi đè giá trị bằng PRESENT, trả về PRESENT (add() trả về false)
  → NẾU KHÔNG tìm thấy khóa khớp: tạo nút mới, chèn cặp khóa-giá trị, trả về null (add() trả về true)
```
