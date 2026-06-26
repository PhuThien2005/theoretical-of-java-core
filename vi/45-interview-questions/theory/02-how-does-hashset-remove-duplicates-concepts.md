# Các câu hỏi phỏng vấn Java Core phổ biến - Phần 2 (Common Java Core Interview Questions - Part 2)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm các câu hỏi phỏng vấn Java Core trung cấp liên quan đến các thao tác trên Set, các bổ từ (modifiers - final, static), kế thừa (Inheritance) ngoại lệ, các mối quan hệ hướng đối tượng OOP và ngữ cảnh thực thể (instance context).

## Phạm vi đề cương (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `How does HashSet remove duplicates?` | Sử dụng một `HashMap` nội bộ để lưu trữ các phần tử dưới dạng khóa; việc kiểm tra trùng lặp dựa trên `hashCode()` và `equals()`. |
| `How are final, finally, and finalize different?` | `final` là một từ khóa bổ từ; `finally` là một phần của khối try-catch-finally; `finalize()` là một phương thức dọn dẹp đã lỗi thời. |
| `How are checked and unchecked exceptions different?` | Ngoại lệ checked phải được khai báo hoặc bắt lại ở thời điểm biên dịch; ngoại lệ unchecked đại diện cho lỗi ở thời điểm chạy. |
| `How are abstract class and interface different?` | Lớp trừu tượng cho phép lưu trữ trạng thái và chỉ hỗ trợ đơn kế thừa; giao diện hỗ trợ đa kế thừa và các hành vi mặc định (default methods). |
| `How are overload and override different?` | Overload là đa hình ở thời điểm biên dịch (cùng tên, khác tham số); Override là đa hình ở thời điểm chạy (quan hệ cha-con). |
| `Can static methods be overridden?` | Không. Chúng chỉ có thể bị che giấu (hidden) vì các phương thức tĩnh được giải quyết tĩnh ở thời điểm biên dịch bằng cách sử dụng kiểu lớp. |
| `Are constructors inherited?` | Không. Chúng phải được khai báo trong lớp con hoặc được gọi thông qua hàm khởi tạo cha bằng cách sử dụng `super()`. |
| `How are this and super different?` | `this` tham chiếu đến thực thể lớp hiện tại; `super` tham chiếu đến ngữ cảnh thực thể lớp cha trực tiếp. |

---

## Ghi chú chi tiết (Detailed Notes)

### HashSet loại bỏ các phần tử trùng lặp như thế nào? (How does HashSet remove duplicates?)

Bên dưới lớp vỏ, một `HashSet` được hỗ trợ bởi một thực thể `HashMap`:
```java
public class HashSet<E> {
    private transient HashMap<E, Object> map;
    private static final Object PRESENT = new Object(); // Giá trị giả giữ chỗ

    public boolean add(E e) {
        return map.put(e, PRESENT) == null; // HashMap trả về null nếu khóa chưa tồn tại
    }
}
```
- **Phát hiện trùng lặp**: Khi thêm một phần tử `e`, `HashSet` sẽ gọi `e.hashCode()` để tìm bucket phù hợp trong `HashMap` nội bộ. Nếu có một phần tử khác tồn tại với cùng mã băm, nó sẽ gọi `e.equals(existingElement)`. Nếu phương thức `equals` trả về `true`, phần tử trùng lặp sẽ bị từ chối/ghi đè.

---

### final so với finally và finalize() (final vs. finally vs. finalize())

- **`final`**: Một từ khóa bổ từ (modifier keyword):
  - **Biến**: Không được phép gán lại giá trị (hằng số).
  - **Phương thức**: Không thể bị ghi đè bởi các lớp con.
  - **Lớp**: Không thể bị kế thừa/mở rộng (ví dụ: `String`, `Integer`).
- **`finally`**: Một khối lệnh được sử dụng trong cấu trúc `try-catch-finally`. Nó đảm bảo luôn luôn được thực thi bất kể ngoại lệ có bị ném ra hay bắt được hay không, rất lý tưởng cho việc giải phóng tài nguyên.
- **`finalize()`**: Một phương thức trong lớp `java.lang.Object`. Về mặt lịch sử, nó được gọi bởi bộ thu gom rác Garbage Collector trước khi thu hồi bộ nhớ. Phương thức này đã bị **loại bỏ (deprecated)** kể từ Java 9 vì tính không dự đoán được, chạy chậm và dễ gây rò rỉ tài nguyên.

---

### Ngoại lệ Checked so với Unchecked (Checked vs. Unchecked Exceptions)

- **Ngoại lệ Checked (Checked Exceptions)**:
  - Là lớp con trực tiếp của `Exception` (ngoại trừ `RuntimeException`).
  - Được kiểm tra bởi trình biên dịch. Chương trình bắt buộc phải xử lý chúng trong khối `try-catch` hoặc khai báo chúng trên chữ ký phương thức bằng từ khóa `throws` (ví dụ: `IOException`, `SQLException`).
- **Ngoại lệ Unchecked (Unchecked Exceptions)**:
  - Là lớp con của `RuntimeException` hoặc `Error`.
  - Không bị kiểm tra ở thời điểm biên dịch. Chúng đại diện cho các lỗi lập trình hoặc các điều kiện không thể khôi phục (ví dụ: `NullPointerException`, `IndexOutOfBoundsException`, `OutOfMemoryError`).

---

### Lớp trừu tượng so với Giao diện (Abstract Class vs. Interface)

| Đặc tính | Lớp trừu tượng (Abstract Class) | Giao diện (Interface) |
| --- | --- | --- |
| **Kế thừa** | Đơn kế thừa (`extends`). | Đa kế thừa (`implements`). |
| **Trạng thái** | Có thể có các biến thực thể (trạng thái). | Chỉ có thể có hằng số `public static final`. |
| **Hàm khởi tạo** | Có thể có các hàm khởi tạo. | Không thể có hàm khởi tạo. |
| **Phương thức** | Có thể có phương thức private, protected, cụ thể và trừu tượng. | Mặc định tất cả phương thức trừu tượng là public. Có thể có các phương thức `default` và `static`. |

---

### Overloading so với Overriding (Overloading vs. Overriding)

- **Nạp chồng phương thức (Method Overloading)**:
  - Xảy ra trong cùng một lớp (hoặc giữa lớp cha/lớp con).
  - Cùng tên phương thức, khác danh sách tham số (kiểu, số lượng hoặc thứ tự).
  - Đa hình ở **thời điểm biên dịch** (static polymorphism).
- **Ghi đè phương thức (Method Overriding)**:
  - Xảy ra giữa lớp cha và lớp con.
  - Cùng tên phương thức, cùng danh sách tham số, cùng kiểu trả về (hoặc kiểu trả về đồng biến - covariant).
  - Đa hình ở **thời điểm chạy** (dynamic polymorphism).

---

### Các phương thức tĩnh có thể bị ghi đè không? (Can static methods be overridden?)

Không. Việc ghi đè phương thức phụ thuộc vào liên kết động (dynamic binding) tại thời điểm chạy (dựa trên kiểu đối tượng thực tế trên heap). Các phương thức tĩnh được liên kết tại thời điểm biên dịch dựa trên **kiểu tham chiếu** được khai báo của biến.
- Nếu một lớp con định nghĩa một phương thức tĩnh có cùng chữ ký với một phương thức tĩnh ở lớp cha, đó được gọi là **Che giấu phương thức (Method Hiding)**, chứ không phải ghi đè.

```java
class Parent {
    static void display() { System.out.println("Parent"); }
}
class Child extends Parent {
    static void display() { System.out.println("Child"); } // Che giấu Parent.display()
}

// Cách dùng:
Parent p = new Child();
p.display(); // In ra "Parent" (kiểu tham chiếu p ở thời điểm biên dịch là Parent)
```

---

### Các hàm khởi tạo có được kế thừa không? (Are constructors inherited?)

Không, các hàm khởi tạo không được kế thừa bởi các lớp con.
- Lớp con phải tự định nghĩa các hàm khởi tạo của chính nó. Nếu lớp cha không có hàm khởi tạo mặc định (không đối số), hàm khởi tạo của lớp con bắt buộc phải gọi rõ ràng một trong các hàm khởi tạo có tham số của lớp cha bằng cách sử dụng `super(...)` ở dòng lệnh đầu tiên của nó.

---

### this so với super (this vs. super)

- **`this`**: Đại diện cho con trỏ thực thể của lớp hiện tại. Được sử dụng để gọi các hàm khởi tạo cùng lớp (`this()`), tham chiếu đến các biến thực thể khi bị trùng tên với tham số truyền vào (`this.name = name`), hoặc truyền đối tượng hiện tại như một đối tượng đầu vào.
- **`super`**: Đại diện cho ngữ cảnh thực thể lớp cha. Được sử dụng để gọi các hàm khởi tạo của lớp cha (`super()`) hoặc truy cập các phương thức bị ghi đè của lớp cha (`super.doWork()`).

---

## Các lỗi thường gặp & Cạm bẫy (Common Mistakes & Traps)

### 1. Vi phạm hợp đồng HashSet/HashMap (The HashSet/HashMap Contract Violation)
Thêm một đối tượng vào một `HashSet` mà không ghi đè cả hai phương thức `hashCode()` và `equals()`:
```java
class Student {
    String name;
    Student(String name) { this.name = name; }
    // Không ghi đè hashCode() hoặc equals()!
}

Set<Student> set = new HashSet<>();
set.add(new Student("Bob"));
set.add(new Student("Bob")); // Set bây giờ chứa HAI phần tử vì địa chỉ tham chiếu là khác nhau!
```

### 2. Giả định block finally luôn luôn thực thi (Assuming finally always executes)
Khối `finally` chạy hầu như trong mọi trường hợp, ngoại trừ khi:
- Lệnh `System.exit(0)` được gọi trong khối try hoặc catch.
- JVM bị sập hoặc gặp sự cố mất nguồn điện phần cứng đột ngột.
- Luồng (Thread) thực thi khối try-catch bị hủy hoặc ngắt từ bên ngoài.

---

## Tại sao HashSet tận dụng HashMap để loại bỏ trùng lặp (Why HashSet Leverages HashMap to Remove Duplicates)

Một `HashSet` trong Java không tự triển khai logic giải quyết đụng độ băm của riêng nó; thay vào đó, nó chuyển giao toàn bộ việc lưu trữ và kiểm tra tính duy nhất cho một thực thể `HashMap` nội bộ.

Khi một `HashSet` được khởi tạo, nó sẽ khởi tạo một `HashMap` private, transient, nơi các phần tử của set đóng vai trò là các khóa (keys) của map, và một đối tượng giả dùng chung (`PRESENT`) được sử dụng làm giá trị (value) không đổi.

Bởi vì tập hợp khóa của `HashMap` phải là duy nhất, việc gọi `add(element)` thực hiện cuộc gọi `map.put(element, PRESENT)` bên dưới lớp vỏ. Nếu phần tử đã được ánh xạ, phương thức `put()` trả về giá trị cũ (`PRESENT`), khiến cho phương thức `add()` trả về `false` để cho biết phần tử trùng lặp đã bị từ chối.

Bằng cách tận dụng các kỹ thuật giải quyết đụng độ mạnh mẽ của `HashMap` (như danh sách liên kết và các bucket dạng cây), Java tránh được việc trùng lặp mã nguồn và đảm bảo rằng việc tìm kiếm, chèn và xóa phần tử diễn ra với độ phức tạp thời gian trung bình là O(1).

### Mô hình tư duy (Mental Model)

```text
  HashSet: [add(Key1)] ──(chuyển giao)──> HashMap: put(Key1, PRESENT)
  +-------------------------------------------------------+
  | Các khóa của HashMap bên dưới (Các phần tử của Set)    |
  |  - "Java" -> ánh xạ tới PRESENT (đối tượng giả)       |
  |  - "Python" -> ánh xạ tới PRESENT                     |
  |  - ["Java" được thêm lại -> put() trả về PRESENT -> trả về false] |
  +-------------------------------------------------------+
```

### Ví dụ Code (Code Example)

Đoạn mã dưới đây minh họa cách `HashSet` sử dụng `add()` và cách nó gọi ngầm thao tác `put()` của `HashMap` bên dưới.

```java
import java.util.HashSet;

public class HashSetMechanismDemo {
    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();
        // map.put("Java", PRESENT) trả về null -> add() trả về true
        System.out.println(set.add("Java")); // Output: true
        // map.put("Java", PRESENT) trả về PRESENT -> add() trả về false
        System.out.println(set.add("Java")); // Output: false
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)


```text
Gọi phương thức `hashSet.add(element)`
  → Chuyển giao nội bộ tới `map.put(element, PRESENT)`
  → HashMap tính toán `hash(element)` và xác định chỉ mục bucket đích
  → HashMap quét các nút trong bucket để kiểm tra `key.equals(element)`
  → NẾU tìm thấy khóa khớp: ghi đè giá trị bằng PRESENT, trả về PRESENT (`add()` trả về `false`)
  → NẾU KHÔNG tìm thấy khóa khớp: tạo nút mới, chèn cặp khóa-giá trị, trả về null (`add()` trả về `true`).
```

