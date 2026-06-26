# Các câu hỏi phỏng vấn Java Core phổ biến - Phần 3 (Common Java Core Interview Questions - Part 3)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm các câu hỏi phỏng vấn Java Core từ trung cấp đến nâng cao liên quan đến sắp xếp đối tượng, ngữ nghĩa của iterator, đồng bộ hóa đa luồng và logic thực thi của Stream API.

## Phạm vi đề cương (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `How are Comparable and Comparator different?` | `Comparable` định nghĩa thứ tự tự nhiên (natural ordering) của một lớp thông qua `compareTo()`; `Comparator` định nghĩa thứ tự tùy chỉnh bên ngoài thông qua `compare()`. |
| `How are fail-fast and fail-safe iterators different?` | Iterator fail-fast ném ra ngoại lệ `ConcurrentModificationException` khi có sự thay đổi cấu trúc; iterator fail-safe hoạt động trên một bản sao, tránh được ngoại lệ. |
| `How are volatile and synchronized different?` | `volatile` đảm bảo tính hiển thị (visibility) và tính thứ tự (ordering) của biến giữa các luồng; `synchronized` đảm bảo tính hiển thị, tính thứ tự VÀ tính nguyên tử (atomicity) thông qua việc khóa. |
| `What is deadlock?` | Một trạng thái mà hai hoặc nhiều luồng bị chặn vĩnh viễn, mỗi luồng chờ đợi một khóa đang được giữ bởi luồng kia. |
| `How are Thread start() and run() different?` | Phương thức `start()` tạo ra một luồng mới và thực thi logic `run()` bất đồng bộ; phương thức `run()` thực thi đồng bộ trên luồng của người gọi. |
| `How are sleep() and wait() different?` | Phương thức `sleep()` tạm dừng thực thi luồng trong một khoảng thời gian nhưng **không giải phóng khóa**; phương thức `wait()` giải phóng khóa và chờ được thông báo bởi một luồng khác. |
| `How are notify() and notifyAll() different?` | Phương thức `notify()` đánh thức một luồng ngẫu nhiên đang chờ; phương thức `notifyAll()` đánh thức tất cả các luồng đang chờ trên monitor của đối tượng. |
| `Is Stream API lazy?` | Có. Các thao tác trung gian (như `filter`, `map`) là lười biếng (lazy) và chỉ thực thi khi một thao tác kết thúc (như `collect`) được gọi. |

---

## Ghi chú chi tiết (Detailed Notes)

### Comparable so với Comparator (Comparable vs. Comparator)

- **`Comparable`**:
  - Nằm trong package `java.lang`.
  - Được sử dụng cho **thứ tự tự nhiên (natural ordering)** của các phần tử (ví dụ: thứ tự chữ cái cho String, thứ tự tăng dần cho Integer).
  - Lớp đích tự triển khai `Comparable<T>` và ghi đè `compareTo(T o)`.
- **`Comparator`**:
  - Nằm trong package `java.util`.
  - Được sử dụng cho **thứ tự tùy chỉnh/thay thế** (ví dụ: sắp xếp các chuỗi theo độ dài hoặc theo các trường dữ liệu tùy chọn).
  - Được triển khai trong một lớp riêng biệt hoặc dưới dạng một biểu thức lambda được truyền trực tiếp vào `Collections.sort()` hoặc `list.sort()`.

```java
// Comparable: Thứ tự tự nhiên (theo ID)
public class Person implements Comparable<Person> {
    int id;
    public int compareTo(Person other) { return Integer.compare(this.id, other.id); }
}

// Comparator: Thứ tự tùy chỉnh (theo Tên)
Comparator<Person> nameComparator = (p1, p2) -> p1.name.compareTo(p2.name);
```

---

### Iterator Fail-Fast so với Fail-Safe (Fail-Fast vs. Fail-Safe (Non-Fail-Fast) Iterators)

- **Iterator Fail-Fast**:
  - Duyệt trực tiếp trên cấu trúc nội bộ của bộ sưu tập.
  - Ném ra ngoại lệ `ConcurrentModificationException` ngay lập tức nếu bộ sưu tập bị sửa đổi cấu trúc (thêm/xóa phần tử) trong quá trình duyệt bởi bất kỳ thao tác nào khác ngoài phương thức `remove()` của chính iterator đó.
  - Ví dụ: iterator của `ArrayList`, iterator keyset của `HashMap`.
- **Iterator Fail-Safe (Nhất quán yếu - Weakly Consistent)**:
  - Duyệt trên một bản sao của bộ sưu tập, hoặc xử lý đồng thời thông qua các cấu trúc dữ liệu nội bộ an toàn đa luồng.
  - Không ném ra ngoại lệ khi có sửa đổi, nhưng các thay đổi được thực hiện trong quá trình duyệt có thể không hiển thị đối với iterator.
  - Ví dụ: iterator của `CopyOnWriteArrayList`, iterator của `ConcurrentHashMap`.

---

### volatile so với synchronized (volatile vs. synchronized)

- **`volatile`**:
  - Bổ từ dành cho biến (variable modifier).
  - Đảm bảo **tính hiển thị (visibility)** (các thao tác đọc/ghi đi trực tiếp vào bộ nhớ chính, bỏ qua bộ nhớ đệm CPU) và ngăn chặn việc **tái sắp xếp lệnh (instruction reordering)** của trình biên dịch.
  - Không đảm bảo **tính nguyên tử (atomicity)** (ví dụ: thao tác `count++` không phải là nguyên tử và vẫn cần đồng bộ hóa).
- **`synchronized`**:
  - Bổ từ dành cho phương thức hoặc khối mã.
  - Đảm bảo **tính hiển thị**, **tính thứ tự** và **tính nguyên tử** bằng cách lấy khóa giám sát (monitor lock). Chỉ một luồng có thể thực thi khối mã tại một thời điểm.

---

### Bế tắc là gì? (What is Deadlock?)

Bế tắc (Deadlock) xảy ra khi Luồng 1 giữ Khóa A và chờ Khóa B, trong khi Luồng 2 giữ Khóa B và chờ Khóa A. Cả hai luồng không thể tiếp tục thực thi.

- **Để tránh bế tắc**:
  1. Lấy các khóa theo một thứ tự toàn cục nghiêm ngặt.
  2. Sử dụng các khóa có thời gian chờ (ví dụ: `ReentrantLock.tryLock()`).
  3. Giữ các khối synchronized nhỏ nhất có thể.

---

### start() so với run() của Thread (Thread start() vs. run())

- **`thread.start()`**:
  - Phân bổ tài nguyên hệ thống, tạo một luồng thực thi mới trong JVM và lập lịch để nó chạy.
  - JVM gọi phương thức `run()` của luồng một cách bất đồng bộ trong ngữ cảnh luồng mới.
- **`thread.run()`**:
  - Chỉ là một cuộc gọi phương thức thông thường. Không có luồng mới nào được tạo ra.
  - Thực thi đồng bộ bên trong stack của luồng *gọi* nó.

---

### sleep() so với wait() (sleep() vs. wait())

- **`Thread.sleep(millis)`**:
  - Phương thức tĩnh của lớp `Thread`.
  - Luồng tạm dừng trong một khoảng thời gian nhưng **vẫn giữ bất kỳ khóa nào nó đang có**.
  - Có thể được gọi ở bất cứ đâu.
- **`object.wait()`**:
  - Phương thức thực thể của lớp `java.lang.Object`.
  - Luồng nhường quyền thực thi và **giải phóng khóa** trên bộ giám sát đối tượng (object monitor), cho phép các luồng khác đi vào.
  - Bắt buộc phải được gọi bên trong một khối synchronized của chính đối tượng đó.

---

### notify() so với notifyAll() (notify() vs. notifyAll())

- **`notify()`**: Đánh thức một luồng duy nhất đang chờ trên bộ giám sát đối tượng. Luồng nào được đánh thức là phi xác định (do bộ lập lịch luồng JVM lựa chọn).
- **`notifyAll()`**: Đánh thức tất cả các luồng đang chờ trên bộ giám sát đối tượng. Chúng sau đó sẽ cạnh tranh để lấy khóa; luồng chiến thắng sẽ tiếp tục chạy, trong khi các luồng khác bị chặn. Cách này thường an toàn hơn để tránh mất tín hiệu.

---

### Stream API có lười biếng không? (Is Stream API Lazy?)

Có, các thao tác của Java Stream API được chia thành:
1. **Các thao tác trung gian (Intermediate Operations)** (ví dụ: `filter()`, `map()`, `sorted()`): Trả về một Stream mới nhưng chưa xử lý bất kỳ phần tử nào. Chúng chỉ xây dựng một kế hoạch thực thi.
2. **Các thao tác kết thúc (Terminal Operations)** (ví dụ: `collect()`, `forEach()`, `reduce()`): Kích hoạt quá trình xử lý của đường ống dữ liệu.

- **Minh chứng cho tính lười biếng (laziness)**:
```java
Stream.of("A", "B", "C")
      .filter(s -> {
          System.out.println("Filter: " + s); // Dòng này sẽ CHƯA in ra bất kỳ thứ gì
          return true;
      }); 
// Do không có thao tác kết thúc nào được gọi, không có kết quả nào được tạo ra.
```

---

## Các lỗi thường gặp & Cạm bẫy (Common Mistakes & Traps)

### 1. volatile không làm cho count++ an toàn đa luồng (volatile does not make count++ thread-safe)
Thao tác `count++` gồm ba bước: đọc, sửa đổi và ghi. Từ khóa `volatile` chỉ đảm bảo các luồng khác thấy giá trị ghi mới nhất, nhưng nó không ngăn hai luồng cùng đọc một giá trị cũ đồng thời. Hãy sử dụng `AtomicInteger` hoặc `synchronized`.

### 2. Quên đồng bộ hóa trước khi gọi wait() hoặc notify() (Forgetting to synchronize before calling wait() or notify())
Việc gọi `wait()`, `notify()`, hoặc `notifyAll()` mà không giữ khóa monitor (bên ngoài một khối synchronized) sẽ ném ra ngoại lệ `IllegalMonitorStateException` lúc chạy.
```java
Object lock = new Object();
lock.wait(); // SẬP: Không nằm trong khối synchronized(lock)!
```

---

## Tại sao Comparable và Comparator khác nhau trong thiết kế sắp xếp (Why Comparable and Comparator Differ in Sorting Design)

Java tách biệt logic sắp xếp thành `Comparable` và `Comparator` để phân biệt giữa thứ tự tự nhiên vốn có của đối tượng và các thứ tự tùy chỉnh phụ thuộc vào ngữ cảnh.

Khi một lớp triển khai `Comparable`, nó ghi đè `compareTo()` để thiết lập quy tắc sắp xếp mặc định đại diện cho danh tính nội tại duy nhất của thực thể đó (ví dụ: sắp xếp Học sinh theo ID duy nhất của họ).

Ngược lại, một `Comparator` được định nghĩa bên ngoài như một đối tượng hoặc biểu thức lambda riêng biệt, ghi đè `compare()` để áp dụng các chiến lược sắp xếp tùy chỉnh tạm thời (ví dụ: sắp xếp Học sinh theo tên, điểm số, hoặc tuổi).

Việc triển khai sắp xếp dưới dạng một `Comparator` riêng biệt giúp tránh làm phình lớp thực thể cốt lõi với nhiều chiến lược sắp xếp khác nhau, đồng thời tuân thủ Nguyên tắc đơn trách nhiệm. Hơn nữa, thiết kế này cho phép các lập trình viên sắp xếp các bộ sưu tập của các lớp bên thứ ba vốn không thể sửa đổi mã nguồn để triển khai `Comparable`.

### Mô hình tư duy (Mental Model)

```text
  Lớp thực thể (ví dụ: Student)
  +-------------------------------------------------+
  | implements Comparable -> compareTo(Student o)  | -> Thứ tự tự nhiên (ID)
  +-------------------------------------------------+
          | (các góc nhìn sắp xếp thay thế)
          v
  Các lớp Helper bên ngoài / Lambdas (Comparators)
  +-------------------------------------------------+
  | Comparator1 -> compare(Student s1, Student s2)  | -> Sắp xếp theo Tên
  | Comparator2 -> compare(Student s1, Student s2)  | -> Sắp xếp theo Tuổi
  +-------------------------------------------------+
```

### Ví dụ Code (Code Example)

Mã nguồn dưới đây minh họa cách thứ tự tự nhiên qua `Comparable` và sắp xếp tùy chỉnh qua `Comparator` cùng tồn tại cho một lớp.

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
        Collections.sort(list); // Comparable (ID) -> [1, 2]
        System.out.println(list.get(0).name); // Output: A
        list.sort((x, y) -> y.name.compareTo(x.name)); // Comparator (Tên giảm dần) -> [B, A]
        System.out.println(list.get(0).name); // Output: B
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)


```text
Kích hoạt sắp xếp bộ sưu tập (ví dụ: `Collections.sort(list)` hoặc `list.sort(comparator)`)
  → NẾU không cung cấp comparator: kiểm tra xem các phần tử có triển khai Comparable hay không
  → CÓ: gọi `compareTo(o)` liên tục trong quá trình sắp xếp
  → KHÔNG: ném ngoại lệ ClassCastException lúc chạy
  → NẾU có cung cấp comparator: bỏ qua Comparable, gọi `comparator.compare(a, b)`
  → Thuật toán sắp xếp thay đổi thứ tự các tham chiếu dựa trên dấu của kết quả so sánh (âm/không/dương).
```

