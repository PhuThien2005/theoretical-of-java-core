# Các Câu Hỏi Phỏng Vấn Java Core Thường Gặp - Phần 3 (Common Java Core Interview Questions - Part 3)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này trình bày các câu hỏi phỏng vấn Java Core từ trung cấp đến nâng cao liên quan đến sắp xếp đối tượng, hành vi của bộ lặp (iterator), đồng bộ hóa đa luồng và logic thực thi của Stream API.

## Khung Nội Dung (Outline Coverage)

- **`How are Comparable and Comparator different?`** — `Comparable` định nghĩa thứ tự sắp xếp tự nhiên của một lớp thông qua `compareTo()`; `Comparator` định nghĩa các quy tắc sắp xếp tùy chỉnh bên ngoài thông qua `compare()`.
- **`How are fail-fast and fail-safe iterators different?`** — Bộ lặp fail-fast ném ra ngoại lệ `ConcurrentModificationException` ngay khi bộ sưu tập bị thay đổi cấu trúc; bộ lặp fail-safe hoạt động trên một bản sao, tránh được ngoại lệ này.
- **`How are volatile and synchronized different?`** — Từ khóa `volatile` đảm bảo tính hiển thị (visibility) và thứ tự lệnh của biến giữa các luồng; `synchronized` đảm bảo tính hiển thị, thứ tự lệnh VÀ tính nguyên tố (atomicity) thông qua cơ chế khóa.
- **`What is deadlock?`** — Là trạng thái bế tắc xảy ra khi hai hoặc nhiều luồng bị khóa vĩnh viễn, mỗi luồng đều chờ đợi khóa đang được giữ bởi luồng kia.
- **`How are Thread start() and run() different?`** — Phương thức `start()` tạo ra một luồng mới và thực thi logic trong `run()` bất đồng bộ; phương thức `run()` thực thi đồng bộ trong luồng gọi hiện tại.
- **`How are sleep() and wait() different?`** — Phương thức `sleep()` tạm dừng luồng hiện tại nhưng **không giải phóng khóa**; `wait()` tạm dừng luồng và **giải phóng khóa** để chờ thông báo từ luồng khác.
- **`How are notify() and notifyAll() different?`** — Phương thức `notify()` đánh thức ngẫu nhiên một luồng đơn lẻ đang chờ; `notifyAll()` đánh thức toàn bộ các luồng đang chờ trên màn hình giám sát đối tượng (object monitor).
- **`Is Stream API lazy?`** — Có. Các hoạt động trung gian (như `filter`, `map`) là trì hoãn (lazy) và chỉ được thực thi khi một hoạt động kết thúc (như `collect`) được gọi.

---

## Ghi Chú Chi Tiết (Detailed Notes)

### Comparable vs. Comparator

- **`Comparable`**:
  - Nằm trong gói `java.lang`.
  - Được sử dụng để định nghĩa **thứ tự sắp xếp tự nhiên (natural ordering)** của các phần tử (ví dụ: thứ tự bảng chữ cái đối với String, thứ tự tăng dần đối với Integer).
  - Bản thân lớp triển khai giao diện `Comparable<T>` và ghi đè phương thức `compareTo(T o)`.
- **`Comparator`**:
  - Nằm trong gói `java.util`.
  - Được sử dụng để định nghĩa **thứ tự sắp xếp tùy chỉnh/thay thế (custom ordering)** (ví dụ: sắp xếp chuỗi theo độ dài hoặc theo các trường tùy chỉnh).
  - Được triển khai ở một lớp riêng biệt hoặc dưới dạng biểu thức lambda truyền trực tiếp vào `Collections.sort()` hoặc `list.sort()`.

```java
// Comparable: Thứ tự tự nhiên (theo ID)
public class Person implements Comparable<Person> {
    int id;
    public int compareTo(Person other) { return Integer.compare(this.id, other.id); }
}

// Comparator: Thứ tự tùy chỉnh (theo tên)
Comparator<Person> nameComparator = (p1, p2) -> p1.name.compareTo(p2.name);
```

---

### Bộ Lặp Fail-Fast vs. Fail-Safe (Non-Fail-Fast Iterators)

- **Bộ lặp Fail-Fast (Lỗi nhanh)**:
  - Duyệt trực tiếp trên cấu trúc dữ liệu nội bộ của bộ sưu tập.
  - Sẽ ném ngay ngoại lệ `ConcurrentModificationException` nếu bộ sưu tập bị thay đổi cấu trúc (thêm/xóa phần tử) trong khi đang duyệt, ngoại trừ khi thay đổi bằng chính phương thức `remove()` của bộ lặp.
  - Ví dụ: bộ lặp của `ArrayList`, bộ lặp keyset của `HashMap`.
- **Bộ lặp Fail-Safe (An toàn lỗi - Weakly Consistent)**:
  - Duyệt trên một bản sao của bộ sưu tập, hoặc xử lý đồng thời thông qua các cấu trúc nội bộ an toàn luồng.
  - Không ném ra ngoại lệ khi bộ sưu tập bị sửa đổi, nhưng các thay đổi được thực hiện trong quá trình lặp có thể không hiển thị với bộ lặp.
  - Ví dụ: bộ lặp của `CopyOnWriteArrayList`, bộ lặp của `ConcurrentHashMap`.

---

### volatile vs. synchronized

- **`volatile`**:
  - Là bổ từ cho biến.
  - Đảm bảo **tính hiển thị (visibility)** (các thao tác đọc/ghi được thực hiện trực tiếp trên bộ nhớ chính, bỏ qua bộ nhớ đệm CPU) và ngăn chặn trình biên dịch **tái sắp xếp chỉ thị (instruction reordering)**.
  - *Không* đảm bảo **tính nguyên tố (atomicity)** (ví dụ: phép toán `count++` không phải là nguyên tố và vẫn cần đồng bộ hóa).
- **`synchronized`**:
  - Bổ từ cho phương thức hoặc khối lệnh.
  - Đảm bảo **tính hiển thị**, **thứ tự lệnh**, và **tính nguyên tố** bằng cách chiếm giữ khóa giám sát đối tượng. Chỉ có một luồng duy nhất có thể thực thi khối mã tại một thời điểm.

---

### Hiện tượng Bế tắc (What is Deadlock?)

Bế tắc (Deadlock) xảy ra khi Luồng 1 giữ Khóa A và chờ Khóa B, trong khi Luồng 2 giữ Khóa B và chờ Khóa A. Cả hai luồng đều không thể tiếp tục thực thi.

- **Cách phòng tránh bế tắc**:
  1. Chiếm giữ các khóa theo một thứ tự toàn cục nghiêm ngặt.
  2. Sử dụng các khóa có thời gian chờ (ví dụ: `ReentrantLock.tryLock()`).
  3. Giữ cho các khối đồng bộ `synchronized` càng nhỏ càng tốt.

---

### Luồng `start()` vs. `run()` (Thread start() vs. run())

- **`thread.start()`**:
  - Cấp phát tài nguyên hệ thống, tạo một luồng thực thi mới trong JVM và lên lịch chạy cho nó.
  - JVM sẽ gọi phương thức `run()` của luồng đó một cách bất đồng bộ trong ngữ cảnh luồng mới.
- **`thread.run()`**:
  - Chỉ là một lời gọi phương thức thông thường. Không có luồng mới nào được tạo ra.
  - Thực thi đồng bộ bên trong ngăn xếp (stack) của luồng gọi hiện tại.

---

### sleep() vs. wait()

- **`Thread.sleep(millis)`**:
  - Là phương thức tĩnh (static) của lớp `Thread`.
  - Luồng sẽ tạm dừng trong một khoảng thời gian nhưng **vẫn giữ tất cả các khóa mà nó đang sở hữu**.
  - Có thể được gọi ở bất cứ đâu trong mã nguồn.
- **`object.wait()`**:
  - Là phương thức thực thể (instance method) của lớp `java.lang.Object`.
  - Luồng sẽ nhường quyền thực thi và **giải phóng khóa** trên màn hình giám sát đối tượng, cho phép các luồng khác có thể truy cập.
  - Bắt buộc phải được gọi bên trong một khối đồng bộ `synchronized` của đối tượng đó.

---

### notify() vs. notifyAll()

- **`notify()`**: Đánh thức một luồng duy nhất đang chờ trên màn hình giám sát đối tượng. Việc luồng nào được đánh thức là không xác định (do bộ lập lịch luồng của JVM quyết định).
- **`notifyAll()`**: Đánh thức tất cả các luồng đang chờ trên màn hình giám sát đối tượng. Sau đó chúng sẽ cạnh tranh để giành lại khóa; luồng chiến thắng sẽ tiếp tục thực thi, các luồng khác tiếp tục bị chặn. Cách này nhìn chung an toàn hơn để tránh mất tín hiệu thông báo.

---

### Stream API Có Trì Hoãn Thực Thi (Lazy) Không? (Is Stream API Lazy?)

Có, các hoạt động của Java Stream API được chia thành hai nhóm:
1. **Các Hoạt Động Trung Gian (Intermediate Operations)** (ví dụ: `filter()`, `map()`, `sorted()`): Trả về một Stream mới nhưng chưa thực hiện xử lý phần tử. Chúng chỉ xây dựng một kế hoạch thực thi.
2. **Các Hoạt Động Kết Thúc (Terminal Operations)** (ví dụ: `collect()`, `forEach()`, `reduce()`): Kích hoạt quá trình xử lý toàn bộ đường dẫn dữ liệu.

- **Bằng chứng về tính trì hoãn (Proof of Laziness)**:
```java
Stream.of("A", "B", "C")
      .filter(s -> {
          System.out.println("Filter: " + s); // Câu lệnh này sẽ CHƯA in ra bất cứ thứ gì
          return true;
      }); 
// Do không có hoạt động kết thúc nào được gọi, nên không có kết quả đầu ra nào được tạo ra.
```

---

## Các Sai Lầm Phổ Biến & Cạm Bẫy (Common Mistakes & Traps)

### 1. volatile không làm cho `count++` trở nên an toàn luồng
Phép toán `count++` thực chất gồm ba bước: đọc giá trị, sửa đổi và ghi lại. Từ khóa `volatile` chỉ đảm bảo các luồng khác nhìn thấy giá trị ghi mới nhất, nhưng nó không ngăn chặn hai luồng cùng đọc một giá trị cũ tại cùng một thời điểm. Hãy sử dụng `AtomicInteger` hoặc khối `synchronized`.

### 2. Quên đồng bộ hóa trước khi gọi `wait()` hoặc `notify()`
Việc gọi `wait()`, `notify()`, hoặc `notifyAll()` mà không sở hữu khóa giám sát đối tượng (gọi ngoài khối `synchronized`) sẽ ném ra ngoại lệ `IllegalMonitorStateException` tại thời điểm chạy.
```java
Object lock = new Object();
lock.wait(); // SẬP: Không nằm trong khối synchronized(lock)!
```

---

## Tại sao Comparable và Comparator Khác Biệt Trong Thiết Kế Sắp Xếp (Why Comparable and Comparator Differ in Sorting Design)

Java phân chia logic sắp xếp thành `Comparable` và `Comparator` để phân biệt rõ ràng giữa thứ tự sắp xếp tự nhiên, vốn có của đối tượng và thứ tự sắp xếp tùy chỉnh tùy thuộc vào ngữ cảnh. Khi một lớp triển khai `Comparable`, nó sẽ ghi đè phương thức `compareTo()` để thiết lập quy tắc sắp xếp mặc định đại diện cho thuộc tính định danh duy nhất của thực thể đó (ví dụ: sắp xếp Sinh viên theo ID duy nhất của họ). Ngược lại, một `Comparator` được định nghĩa độc lập bên ngoài như một đối tượng hoặc một biểu thức lambda riêng biệt, ghi đè phương thức `compare()` để áp dụng các chiến lược sắp xếp tùy chỉnh tạm thời (ví dụ: sắp xếp Sinh viên theo tên, theo điểm số, hoặc theo tuổi). Việc triển khai sắp xếp thông qua một `Comparator` độc lập giúp tránh làm ô nhiễm lớp thực thể cốt lõi với quá nhiều chiến lược sắp xếp và tuân thủ Nguyên tắc đơn trách nhiệm (Single Responsibility Principle). Hơn nữa, thiết kế này cho phép các nhà phát triển sắp xếp các bộ sưu tập chứa các lớp của bên thứ ba mà mã nguồn của họ không thể sửa đổi để triển khai `Comparable`.

### Mô hình Tư duy (Mental Model)

```text
  Lớp Thực Thể (ví dụ: Student)
  +-------------------------------------------------+
  | implements Comparable -> compareTo(Student o)  | -> Thứ tự Tự nhiên (ID)
  +-------------------------------------------------+
          | (các chế độ xem sắp xếp thay thế khác)
          v
  Các lớp trợ giúp / Lambda bên ngoài (Comparators)
  +-------------------------------------------------+
  | Comparator1 -> compare(Student s1, Student s2)  | -> Sắp xếp theo Tên
  | Comparator2 -> compare(Student s1, Student s2)  | -> Sắp xếp theo Tuổi
  +-------------------------------------------------+
```

### Ví Dụ Mã Nguồn (Code Example)

Đoạn mã dưới đây minh họa cách thứ tự tự nhiên thông qua `Comparable` và sắp xếp tùy chỉnh thông qua `Comparator` cùng tồn tại trên một lớp.

```java
import java.util.*;

public class SortDemo {
    static class Item implements Comparable<Item> {
        int id; String name;
        Item(int i, String n) { id = i; name = n; }
        public int compareTo(Item o) { return Integer.compare(this.id, o.id); }
    }
    public static void main(String[] args) {
        List<Item> list = new ArrayList<>(List.of(new Item(2, "B"), new Item(1, "A")));
        Collections.sort(list); // Sử dụng Comparable (theo ID) -> [1, 2]
        System.out.println(list.get(0).name); // In ra: A
        list.sort((x, y) -> y.name.compareTo(x.name)); // Sử dụng Comparator (theo tên giảm dần) -> [B, A]
        System.out.println(list.get(0).name); // In ra: B
    }
}
```

### Chuỗi Nhân Quả (Cause-Effect Chain)

```text
Kích hoạt sắp xếp bộ sưu tập (ví dụ: Collections.sort(list) hoặc list.sort(comparator))
  → NẾU không cung cấp comparator: kiểm tra xem các phần tử có triển khai Comparable không
    → CÓ: gọi liên tục phương thức compareTo(o) trong quá trình sắp xếp
    → KHÔNG: ném ra ngoại lệ ClassCastException tại thời điểm chạy
  → NẾU có cung cấp comparator: bỏ qua Comparable, gọi phương thức comparator.compare(a, b)
  → Thuật toán sắp xếp sắp xếp lại các tham chiếu dựa trên dấu của kết quả so sánh (âm/không/dương)
```
