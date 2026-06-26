# Đa luồng (Multithreading) - Phần 4

## Mục tiêu học tập (Learning Goal)

Tệp này bao gồm các nguyên lý về an toàn luồng (thread safety), tính bất biến của đối tượng (object immutability), và các thao tác nguyên tử (atomic operations). Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, không phải là từ vựng cô lập.

## Đề cương chi tiết (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `Thread safety` | Một đặc tính của một đối tượng hoặc phương thức đảm bảo nó hoạt động chính xác khi được truy cập bởi nhiều luồng (thread) đồng thời, mà không yêu cầu đồng bộ hóa bên ngoài bổ sung. |
| `Immutable object` | Một đối tượng có trạng thái không thể thay đổi sau khi được khởi tạo. Các đối tượng bất biến (immutable objects) vốn dĩ đã an toàn luồng. |
| `Atomic operation` | Một thao tác thực thi như một đơn vị công việc duy nhất, không thể chia cắt. Các trạng thái trung gian không bao giờ hiển thị đối với các luồng khác. |

## Chi tiết tài liệu học tập (Detailed Notes)

### An toàn luồng (Thread Safety)

Một đối tượng là an toàn luồng nếu nó duy trì các bất biến lớp (class invariants) của nó dưới sự thực thi đồng thời. Các cách để đạt được an toàn luồng:
1. **Tính bất biến (Immutability)**: Trạng thái chia sẻ không thể thay đổi vốn dĩ đã an toàn luồng.
2. **Khóa (Locking)**: Bảo vệ các vùng tranh chấp (critical sections) bằng cách sử dụng các khối `synchronized` hoặc `ReentrantLock`.
3. **Luồng cục bộ (Thread Local)**: Tránh hoàn toàn trạng thái chia sẻ bằng cách giữ các biến riêng tư cho mỗi luồng bằng cách sử dụng `ThreadLocal`.
4. **Cấu trúc dữ liệu đồng thời (Concurrent Data Structures)**: Sử dụng các cấu trúc an toàn luồng như `ConcurrentHashMap` hoặc `AtomicInteger`.

### Các quy tắc đối tượng bất biến (Immutable Object Rules)

Để tạo một lớp hoàn toàn bất biến trong Java, hãy tuân theo các quy tắc sau:
1. Khai báo lớp là `final` để nó không thể bị kế thừa (ngăn chặn việc ghi đè phương thức trả về trạng thái có thể thay đổi).
2. Khai báo tất cả các trường là `private` và `final`.
3. Không cung cấp bất kỳ phương thức thiết lập (setter/mutator) nào.
4. Nếu lớp chứa các tham chiếu đến các đối tượng có thể thay đổi, hãy thực hiện **sao chép sâu/sao chép phòng thủ (deep copy/defensive copying)** trong quá trình khởi tạo và khi trả về chúng từ các phương thức getter.

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ImmutablePerson {
    private final String name;
    private final List<String> hobbies; // Mutable list

    public ImmutablePerson(String name, List<String> hobbies) {
        this.name = name;
        // Defensive copy on construction
        this.hobbies = new ArrayList<>(hobbies);
    }

    public String getName() {
        return name;
    }

    // Defensive copy / unmodifiable wrap on retrieval
    public List<String> getHobbies() {
        return Collections.unmodifiableList(hobbies);
    }
}
```

### Thao tác nguyên tử trong Java (Atomic Operations in Java)

Một thao tác nguyên tử (atomic operation) xuất hiện dưới dạng không thể chia cắt đối với các luồng khác. Trong Java:
* Các thao tác đọc và ghi là nguyên tử đối với các biến tham chiếu và tất cả các biến nguyên thủy **ngoại trừ** `long` và `double` (vốn là 64-bit và có thể bị chia thành hai lần ghi 32-bit trên các JVM 32-bit).
* Các thao tác đọc và ghi đối với `long` và `double` được đảm bảo nguyên tử nếu được khai báo `volatile`.
* Các thao tác hỗn hợp như `count++` (vốn là thao tác đọc-sửa đổi-ghi (read-modify-write)) **không bao giờ** là nguyên tử.

```java
class UnsafeCounter {
    private int count = 0;

    // NOT thread-safe: multiple threads can read the same value, increment, and write back
    public void increment() {
        count++; 
    }
}

import java.util.concurrent.atomic.AtomicInteger;

class SafeCounter {
    private final AtomicInteger count = new AtomicInteger(0);

    // Thread-safe: uses lock-free CPU instructions (CAS) to perform atomic increments
    public void increment() {
        count.incrementAndGet(); 
    }
}
```

---

## Tình huống nghiên cứu: Rò rỉ tính đột biến (Case Study: Mutability Leakage)

### Vấn đề (Problem)
Một nhà phát triển cố gắng viết một lớp cấu hình bất biến, nhưng lại để lộ rò rỉ tính đột biến (mutability leak):
```java
public final class AppConfig {
    private final List<String> servers;

    public AppConfig(List<String> servers) {
        this.servers = servers; // BUG: stores direct reference to caller's list
    }

    public List<String> getServers() {
        return servers; // BUG: exposes mutable list reference
    }
}
```
Nếu bên gọi thay đổi danh sách mà họ đã truyền vào hàm khởi tạo, hoặc gọi `config.getServers().clear()`, họ sẽ sửa đổi trạng thái bên trong của cấu hình "bất biến", vi phạm an toàn luồng.

### Giải pháp (Solution)
Sử dụng sao chép phòng thủ (defensive copying):
```java
public final class AppConfig {
    private final List<String> servers;

    public AppConfig(List<String> servers) {
        this.servers = new ArrayList<>(servers); // Defensive copy
    }

    public List<String> getServers() {
        return Collections.unmodifiableList(servers); // Prevent modification
    }
}
```

---

## Các sai lầm thường gặp (Common Mistakes)

### 1. Giả định rằng `volatile` giúp các thao tác hỗn hợp trở nên an toàn luồng
Từ khóa `volatile` đảm bảo **tính hiển thị (visibility)** (các thay đổi được ghi ngay lập tức vào bộ nhớ chính và được đọc từ đó) và **thứ tự (ordering)** (ngăn chặn việc sắp xếp lại lệnh (instruction reordering)). Nó **KHÔNG** đảm bảo tính nguyên tử.
```java
public class UnsafeVolatile {
    private volatile int count = 0;

    // Still not thread-safe under concurrent access!
    public void increment() {
        count++; 
    }
}
```

### 2. Không khai báo `long` hoặc `double` là `volatile` trên các JVM 32-bit
Nếu không có `volatile`, các thao tác đọc/ghi đối với các biến 64-bit (`long`, `double`) có thể gặp phải hiện tượng "xé từ (word tearing)" — nơi một luồng ghi 32 bit đầu tiên và một luồng khác ghi 32 bit cuối cùng, dẫn đến giá trị bị hỏng.

## Tại sao điều kiện tranh đua (Race Conditions) và vấn đề hiển thị dữ liệu (Data Visibility Issues) xảy ra

Các lỗi đồng thời (concurrency bugs) bắt nguồn sâu sắc từ cách phần cứng CPU tương tác với đặc tả bộ nhớ JVM. Điều kiện tranh đua (race condition) xuất hiện khi nhiều luồng thực thi đồng thời các chuỗi đọc-sửa đổi-ghi (read-modify-write) xen kẽ trên trạng thái có thể thay đổi được chia sẻ mà không có khóa thích hợp. Vì các bước này không mang tính nguyên tử, giá trị trung gian của một luồng bị ghi đè bởi lần ghi cũ (stale write) của một luồng khác, dẫn đến dữ liệu bị hỏng. Các vấn đề về hiển thị dữ liệu (data visibility issues) xảy ra vì các CPU dựa vào các bộ đệm đa cấp cục bộ (local multi-level caches) và các lưu trữ thanh ghi (register stores) để thực thi các tác vụ một cách hiệu quả, kết hợp với việc sắp xếp lại lệnh bởi trình biên dịch hoặc CPU. Nếu các thay đổi cục bộ của luồng không được đẩy (flush) vào bộ nhớ chính, hoặc nếu các nhân CPU khác đọc các giá trị từ các dòng bộ đệm cũ, các cập nhật sẽ không hiển thị đối với các luồng khác. An toàn luồng giải quyết các vấn đề này bằng cách đảm bảo các bất biến lớp được bảo toàn và tính chính xác được duy trì dưới bất kỳ sự xen kẽ luồng tùy ý nào, mà không yêu cầu mã gọi phải triển khai đồng bộ hóa bổ sung.

### Mô hình tư duy (Mental Model)
```text
[Race Condition: Read-Modify-Write Interleaving]
Thread 1: Read count (0) --------> Increment (1) ---------> Write count (1)
Thread 2: -------> Read count (0) --------> Increment (1) ---------> Write count (1)
Result: count is 1 instead of 2!

[Data Visibility Cache Gap]
+-----------------------------------+
|            Main Memory            |
+-----------------------------------+
       ^                     ^
       | [Stale]             | [Flushed]
+--------------+      +--------------+
| Thread 1 L1  |      | Thread 2 L1  |
| Cache (val=0)|      | Cache (val=1)|
+--------------+      +--------------+
|   Thread 1   |      |   Thread 2   |
+--------------+      +--------------+
```

### Ví dụ mã nguồn (Code Example)
```java
public class ConcurrencyFlaws {
    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Runnable r = () -> {
            for (int i = 0; i < 10000; i++) counter++;
        };
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        // Result is erratic and unpredictable due to race conditions and visibility lag
        System.out.println("Final Counter: " + counter);
    }
}
/*
Possible Output:
Final Counter: 14382
*/
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
1. Nhiều luồng chạy các thao tác đọc-sửa đổi-ghi (read-modify-write) xen kẽ
  → Các giá trị đọc trung gian đã cũ (stale).
```

2. Luồng ghi lại giá trị lỗi thời &rarr; Các cập nhật đồng thời bị mất (Điều kiện tranh đua — Race Condition).
3. Bộ đệm CPU và trình biên dịch sắp xếp lại các lệnh &rarr; Các cập nhật cục bộ không được đẩy ngay lập tức vào RAM.
4. Các luồng khác đọc từ các bộ đệm cục bộ L1/L2 &rarr; Các luồng thấy dữ liệu lỗi thời (Vấn đề hiển thị — Visibility Issue).
