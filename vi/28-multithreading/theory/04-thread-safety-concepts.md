# Đa Luồng (Multithreading) - Phần 4

## Mục Tiêu Học Tập

File này đề cập đến các nguyên tắc an toàn luồng (thread safety), tính bất biến của đối tượng (object immutability) và các hoạt động nguyên tử (atomic operation). Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là những từ vựng rời rạc.

## Đề Cương Khái Niệm

| Khái niệm | Những điều cần biết |
| --- | --- |
| `An toàn luồng (Thread safety)` | Đặc tính của một đối tượng hoặc phương thức đảm bảo nó hoạt động chính xác khi được truy cập đồng thời bởi nhiều luồng mà không cần thêm sự đồng bộ hóa bên ngoài. |
| `Đối tượng bất biến (Immutable object)` | Một đối tượng có trạng thái không thể thay đổi sau khi được tạo ra. Các đối tượng bất biến vốn dĩ đã an toàn luồng. |
| `Hoạt động nguyên tử (Atomic operation)` | Một hoạt động được thực thi như một đơn vị công việc duy nhất, không thể chia cắt. Các trạng thái trung gian không bao giờ hiển thị đối với các luồng khác. |

## Ghi Chú Chi Tiết

### An Toàn Luồng (Thread Safety)

Một đối tượng được coi là an toàn luồng nếu nó duy trì được các bất biến lớp (class invariant) dưới sự thực thi đồng thời. Các cách để đạt được sự an toàn luồng:
1. **Tính bất biến (Immutability)**: Trạng thái chia sẻ không thể thay đổi thì vốn dĩ đã an toàn luồng.
2. **Khóa (Locking)**: Bảo vệ các miền tranh chấp (critical section) bằng cách sử dụng các khối `synchronized` hoặc `ReentrantLock`.
3. **Thread Local**: Tránh hoàn toàn việc chia sẻ trạng thái bằng cách giữ các biến riêng tư cho từng luồng bằng cách sử dụng `ThreadLocal`.
4. **Cấu trúc dữ liệu đồng thời**: Sử dụng các cấu trúc dữ liệu an toàn luồng như `ConcurrentHashMap` hoặc `AtomicInteger`.

### Các Quy Tắc Thiết Kế Đối Tượng Bất Biến

Để tạo ra một lớp hoàn toàn bất biến trong Java, hãy tuân thủ các quy tắc sau:
1. Khai báo lớp là `final` để nó không thể bị kế thừa (ngăn chặn việc ghi đè phương thức để trả về trạng thái thay đổi được).
2. Khai báo tất cả các trường là `private` và `final`.
3. Không cung cấp bất kỳ phương thức setter hoặc phương thức thay đổi trạng thái nào.
4. Nếu lớp chứa các tham chiếu đến các đối tượng thay đổi được, hãy thực hiện **sao chép sâu/sao chép phòng thủ (deep copy/defensive copying)** trong quá trình khởi tạo và khi trả về chúng từ các phương thức getter.

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

### Các Hoạt Động Nguyên Tử Trong Java

Một hoạt động nguyên tử được biểu hiện như không thể bị chia cắt đối với các luồng khác. Trong Java:
* Các phép đọc và ghi là nguyên tử đối với các biến tham chiếu và tất cả các biến nguyên thủy **ngoại trừ** `long` and `double` (vì chúng có kích thước 64-bit và có thể bị chia làm hai lần ghi 32-bit trên các JVM 32-bit).
* Các phép đọc và ghi đối với `long` và `double` được đảm bảo tính nguyên tử nếu được khai báo với từ khóa `volatile`.
* Các hoạt động phức hợp như `count++` (vốn là một hoạt động đọc-sửa-ghi) **không bao giờ** mang tính nguyên tử.

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

## Ví Dụ Thực Tế: Rò Rỉ Tính Thay Đổi Được (Mutability Leakage)

### Vấn đề
Một nhà phát triển cố gắng viết một lớp cấu hình bất biến, nhưng lại để lại một lỗi rò rỉ tính thay đổi được:
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
Nếu người gọi thay đổi danh sách mà họ đã truyền vào hàm khởi tạo, hoặc gọi `config.getServers().clear()`, họ sẽ sửa đổi trạng thái bên trong của cấu hình "bất biến" đó, vi phạm nguyên tắc an toàn luồng.

### Giải pháp
Sử dụng kỹ thuật sao chép phòng thủ:
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

## Các Lỗi Thường Gặp

### 1. Giả Định Volatile Giúp Các Hoạt Động Phức Hợp An Toàn Luồng

Từ khóa `volatile` chỉ đảm bảo **tính hiển thị (visibility)** (các thay đổi được ghi ngay lập tức vào bộ nhớ chính và đọc ra từ đó) và **thứ tự thực thi (ordering)** (ngăn chặn việc sắp xếp lại các chỉ thị lệnh). Nó **KHÔNG** đảm bảo tính nguyên tử.
```java
public class UnsafeVolatile {
    private volatile int count = 0;

    // Still not thread-safe under concurrent access!
    public void increment() {
        count++; 
    }
}
```

### 2. Không Khai Báo `long` Hoặc `double` Là `volatile` Trên Các JVM 32-bit

Nếu không có `volatile`, các phép đọc/ghi đối với các biến 64-bit (`long`, `double`) có thể gặp phải hiện tượng "rách từ (word tearing)", trong đó một luồng ghi 32 bit đầu tiên và một luồng khác ghi 32 bit cuối cùng, dẫn đến một giá trị bị lỗi hỏng.

## Tại Sao Hiện Tượng Tranh Chấp Điều Kiện Và Vấn Đề Hiển Thị Dữ Liệu Lại Xảy Ra

Các lỗi đồng thời (concurrency bug) bắt nguồn sâu sắc từ cách phần cứng CPU tương tác với đặc tả bộ nhớ của JVM. Một tình trạng tranh chấp điều kiện (race condition) xuất hiện khi nhiều luồng đồng thời thực thi xen kẽ các chuỗi đọc-sửa-ghi trên một trạng thái thay đổi được chia sẻ mà không có cơ chế khóa thích hợp. Vì các bước này không mang tính nguyên tử, giá trị trung gian của một luồng sẽ bị ghi đè bởi lượt ghi lỗi thời của một luồng khác, dẫn đến dữ liệu bị lỗi hỏng. Vấn đề hiển thị dữ liệu xảy ra do các CPU dựa vào các bộ nhớ đệm đa cấp cục bộ (L1/L2/L3 cache) và các thanh ghi để thực thi các tác vụ một cách hiệu quả, kết hợp với việc sắp xếp lại chỉ thị lệnh bởi trình biên dịch hoặc CPU. Nếu các thay đổi cục bộ của luồng không được đẩy (flush) về bộ nhớ chính, hoặc nếu các nhân CPU khác đọc giá trị từ các dòng cache lỗi thời, các cập nhật sẽ vô hình đối với các luồng khác. Sự an toàn luồng giải quyết các vấn đề này bằng cách đảm bảo các bất biến lớp được bảo toàn và tính đúng đắn được duy trì dưới bất kỳ sự thực thi xen kẽ ngẫu nhiên nào của các luồng, mà không đòi hỏi mã nguồn gọi phải triển khai thêm bất kỳ sự đồng bộ hóa nào.

### Mô Hình Tư Duy

```text
[Tranh chấp điều kiện: Đọc-Sửa-Ghi xen kẽ]
Thread 1: Đọc count (0) --------> Tăng (1) ---------> Ghi count (1)
Thread 2: -------> Đọc count (0) --------> Tăng (1) ---------> Ghi count (1)
Kết quả: count là 1 thay vì 2!

[Khoảng cách bộ nhớ đệm của tính hiển thị dữ liệu]
+-----------------------------------+
|            Bộ nhớ chính           |
+-----------------------------------+
       ^                     ^
       | [Lỗi thời]          | [Đã đẩy]
+--------------+      +--------------+
| Thread 1 L1  |      | Thread 2 L1  |
|Cache(giá trị=|      |Cache(giá trị=|
|      0)      |      |      1)      |
+--------------+      +--------------+
|   Thread 1   |      |   Thread 2   |
+--------------+      +--------------+
```

### Ví Dụ Mã Nguồn

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
        System.out.println("Bộ đếm cuối cùng: " + counter);
    }
}
/*
Đầu ra có thể xảy ra:
Bộ đếm cuối cùng: 14382
*/
```

### Chuỗi Nguyên Nhân - Kết Quả

1. Nhiều luồng chạy xen kẽ các hoạt động đọc-sửa-ghi &rarr; Các giá trị đọc trung gian bị lỗi thời.
2. Luồng ghi ngược lại giá trị lỗi thời &rarr; Các cập nhật đồng thời bị mất (Tình trạng tranh chấp điều kiện - Race Condition).
3. Bộ nhớ đệm CPU và trình biên dịch sắp xếp lại các chỉ thị lệnh &rarr; Các cập nhật cục bộ không được đẩy ngay lập tức về RAM.
4. Các luồng khác đọc từ bộ nhớ đệm L1/L2 cục bộ &rarr; Các luồng nhìn thấy dữ liệu lỗi thời (Vấn đề hiển thị dữ liệu - Visibility Issue).
