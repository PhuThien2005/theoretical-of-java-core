# Đồng bộ hóa và Tính đồng thời (Synchronization and Concurrency) - Phần 4

## Mục Tiêu Học Tập

Tài liệu này tập trung vào các tập hợp đồng thời cấp cao (`ConcurrentHashMap`, `CopyOnWriteArrayList`, `BlockingQueue`) và các thanh chắn đồng bộ hóa (`CyclicBarrier`, `Phaser`). Hãy nghiên cứu từng khái niệm dưới dạng quy tắc thực tế trong Java, không chỉ đơn thuần là lý thuyết từ vựng.

## Tóm Tắt Nội Dung (Outline Coverage)

- **`CyclicBarrier`** — Một thanh chắn đồng bộ hóa có thể tái sử dụng, nơi một số lượng luồng cố định phải chờ đợi lẫn nhau trước khi tiếp tục.
- **`Phaser`** — Một thanh chắn đồng bộ hóa linh hoạt, có thể tái sử dụng, hỗ trợ đăng ký động các bên và thực thi nhiều giai đoạn.
- **`BlockingQueue`** — Một giao diện hàng đợi an toàn luồng sẽ chặn các luồng ghi nếu hàng đợi đầy, và chặn các luồng đọc nếu hàng đợi trống.
- **`Các tập hợp đồng thời:`** — Các tập hợp an toàn luồng đặc biệt trong gói `java.util.concurrent` được tối ưu hóa cho thông lượng đồng thời cao mà không cần khóa toàn cục.
- **`ConcurrentHashMap`** — Một bản đồ băm an toàn luồng, hiệu năng cao sử dụng cơ chế phân mảnh khóa hạt mịn và các hoạt động CAS. Các thao tác đọc là không chặn.
- **`CopyOnWriteArrayList`** — Một danh sách an toàn luồng tạo ra một bản sao mới của mảng nền bất cứ khi nào có thao tác ghi. Hiệu quả cho các kịch bản đọc nhiều ghi ít.
- **`ConcurrentLinkedQueue`** — Một hàng đợi an toàn luồng không giới hạn dựa trên các liên kết nút đồng thời không dùng khóa (sử dụng CAS).
- **`Khung công tác Executor:`** — Một khung thư viện đơn giản hóa việc thực thi tác vụ bất đồng bộ bằng cách gom nhóm và quản lý các luồng làm việc (worker thread).

## Ghi Chú Chi Tiết

### CyclicBarrier so với CountDownLatch (CyclicBarrier vs CountDownLatch)
* **CountDownLatch**: Chỉ sử dụng một lần, không thể đặt lại. Một luồng sẽ chờ, các luồng khác thực hiện giảm bộ đếm.
* **CyclicBarrier**: Có thể tái sử dụng (tự động đặt lại bộ đếm sau khi vượt qua). Các luồng chờ đợi lẫn nhau tại một điểm chắn chung thông qua lệnh `barrier.await()`. Có thể thực thi một tác vụ runnable tùy chọn ("tác vụ thanh chắn" - barrier action) khi tất cả các luồng đã tập hợp đầy đủ.

```java
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("Phase completed!"));

        Runnable worker = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " arriving.");
                barrier.await(); // Wait for all 3 threads
            } catch (Exception e) {}
        };

        new Thread(worker, "Thread-1").start();
        new Thread(worker, "Thread-2").start();
        new Thread(worker, "Thread-3").start();
    }
}
```

### Các Tập hợp Đồng thời: ConcurrentHashMap so với SynchronizedMap
* `Collections.synchronizedMap()` khóa *toàn bộ* bản đồ cho mỗi thao tác đọc và ghi, gây ra tình trạng tranh chấp luồng cực kỳ nghiêm trọng.
* `ConcurrentHashMap` phân mảnh bản đồ thành các phân đoạn khóa (lock stripe) hoặc các ngăn chứa (bucket). Nhiều luồng có thể đọc đồng thời mà không cần khóa, và ghi đồng thời vào các ngăn chứa khác nhau.
* **Quan trọng**: Các hoạt động phức hợp (như kiểm tra rồi thực hiện - check-then-act) không an toàn trên `ConcurrentHashMap` trừ khi sử dụng các phương thức nguyên tử như `putIfAbsent()`, `replace()`, hoặc `computeIfAbsent()`.

```java
import java.util.concurrent.ConcurrentHashMap;

public class MapDemo {
    private final ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    // Thread-safe compound operation
    public void increment(String key) {
        map.compute(key, (k, v) -> (v == null) ? 1 : v + 1);
    }
}
```

### CopyOnWriteArrayList
Các thao tác thay đổi dữ liệu (add, set, remove) sẽ tạo bản sao của toàn bộ mảng nền. Điều này rất tốn kém đối với các thao tác ghi nhưng giúp các thao tác đọc cực kỳ nhanh và không cần dùng khóa. Các bộ lặp (Iterator) đọc dữ liệu từ một bản chụp (snapshot) của mảng tại thời điểm tạo và không bao giờ ném ra ngoại lệ `ConcurrentModificationException`.

---

## Ví Dụ Thực Tế: Sổ đăng ký Lắng nghe Sự kiện (Event Listener Registry)

### Bài toán
Một khung công tác giao diện người dùng (GUI framework) có một lớp cốt lõi dùng để phát các sự kiện tới danh sách các bộ lắng nghe (listener) đã đăng ký. Các bộ lắng nghe có thể được thêm hoặc xóa một cách động, và đôi khi một bộ lắng nghe cố gắng hủy đăng ký *trong khi* một sự kiện đang được phát đi (dẫn đến lỗi `ConcurrentModificationException` nếu dùng `ArrayList` tiêu chuẩn).

### Giải pháp
Sử dụng `CopyOnWriteArrayList`.
```java
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventPublisher {
    // Đọc nhiều, ghi ít: sự kiện được phát liên tục, danh sách listener ít khi thay đổi.
    private final List<Listener> listeners = new CopyOnWriteArrayList<>();

    public void addListener(Listener l) {
        listeners.add(l);
    }

    public void removeListener(Listener l) {
        listeners.remove(l);
    }

    public void publishEvent(String event) {
        // Duyệt qua danh sách an toàn không cần khóa. Không gây ra lỗi
        // ConcurrentModificationException ngay cả khi một listener gọi
        // removeListener() bên trong phương thức onEvent().
        for (Listener l : listeners) {
            l.onEvent(event);
        }
    }

    interface Listener {
        void onEvent(String msg);
    }
}
```

---

## Các lỗi thường gặp

### 1. Sử dụng CopyOnWriteArrayList cho các Danh sách có thao tác Ghi nhiều
Nếu bạn thực hiện ghi vào một `CopyOnWriteArrayList` bên trong một vòng lặp, nó sẽ sao chép toàn bộ mảng ở mỗi vòng lặp đơn lẻ, làm hủy hoại hiệu năng và gây ra áp lực thu gom rác cực lớn.
```java
// BUG: Chi phí sao chép mảng cực lớn
CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
for (int i = 0; i < 10000; i++) {
    list.add(i); // Sao chép mảng 10,000 lần!
}
```

### 2. Lỗi Kiểm tra rồi Thực hiện (Check-Then-Act) với ConcurrentHashMap
Giả định sai lầm rằng việc kiểm tra một giá trị trong `ConcurrentHashMap` và sau đó hành động dựa trên nó là một thao tác nguyên tử.
```java
// BUG: Tình trạng tranh chấp! Hai luồng có thể cùng thấy containsKey là false và cả hai cùng chèn dữ liệu.
if (!map.containsKey("key")) {
    map.put("key", newValue);
}

// KHẮC PHỤC: Sử dụng phương thức computeIfAbsent nguyên tử
map.computeIfAbsent("key", k -> newValue);
```

---

## Tại sao CyclicBarrier và CountDownLatch Khác nhau

`CountDownLatch` và `CyclicBarrier` là các tiện ích đồng thời được thiết kế để đồng bộ hóa luồng, nhưng chúng khác nhau đáng kể về khả năng tái sử dụng và cơ chế hoạt động. `CountDownLatch` hoạt động giống như một cánh cổng một lần; các luồng giảm bộ đếm của nó bằng cách gọi `countDown()` và bị chặn tại lệnh `await()` cho đến khi bộ đếm về 0, tại thời điểm đó chốt không thể đặt lại hoặc tái sử dụng. Ngược lại, `CyclicBarrier` có khả năng tái sử dụng hoàn toàn và đồng bộ hóa các luồng tại một điểm chắn chung. Khi các luồng gọi `await()` trên một `CyclicBarrier`, chúng sẽ bị chặn cho đến khi số lượng luồng được chỉ định tập hợp đủ. Khi bộ đếm thanh chắn về 0, thanh chắn được kích hoạt, thực thi một tác vụ tùy chọn của thanh chắn, đặt lại bộ đếm nội bộ về trạng thái ban đầu và giải phóng tất cả các luồng đang chờ để tiếp tục thực thi.

### Mô hình Tư duy: CountDownLatch so với CyclicBarrier
```
CountDownLatch (Sử dụng một lần):
Luồng ──► countDown() ──► [Bộ đếm: 3 -> 2 -> 1 -> 0] ──► Mở Cổng (Không thể tái sử dụng)

CyclicBarrier (Tái sử dụng):
Luồng 1 ──► await() ──┐
Luồng 2 ──► await() ──┼─► [Bộ đếm: 3 -> 0] ─► Sập chắn ─► Chạy Tác vụ ─► Đặt lại về 3 ─► Giải phóng
Luồng 3 ──► await() ──┘
```

### Ví dụ Thực Tế
```java
import java.util.concurrent.CyclicBarrier;

public class BarrierDemo {
    public static void main(String[] args) {
        // A barrier for 2 threads with a reusable barrier action
        CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            System.out.println("Barrier Tripped!");
        });

        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " arriving");
                barrier.await(); // Thread blocks until count is 2
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        new Thread(task, "Thread-1").start();
        new Thread(task, "Thread-2").start();
    }
}
```

### Chuỗi Nguyên nhân - Kết quả
Các luồng gọi `barrier.await()` &rarr; Chiếm giữ khóa nội bộ &rarr; Giảm số lượng luồng cần chờ &rarr; Số lượng khác không &rarr; Các luồng chờ trên một Điều kiện (Condition) &rarr; Luồng cuối cùng gọi `await()` &rarr; Số lượng về không &rarr; Tác vụ tùy chọn của thanh chắn chạy &rarr; Thanh chắn đặt lại bộ đếm và thế hệ &rarr; Điều kiện phát tín hiệu (signalAll) tới tất cả luồng &rarr; Giải phóng toàn bộ luồng đang chờ &rarr; Thanh chắn sẵn sàng cho chu kỳ tiếp theo.
