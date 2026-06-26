# Đồng bộ hóa và Đồng thời - Phần 4 (Synchronization and Concurrency - Part 4)

## Mục tiêu học tập (Learning Goal)

Tệp này bao gồm các cấu trúc dữ liệu đồng thời cấp cao (`ConcurrentHashMap`, `CopyOnWriteArrayList`, `BlockingQueue`) và các rào cản đồng bộ hóa (`CyclicBarrier`, `Phaser`). Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, không phải là từ vựng cô lập.

## Đề cương chi tiết (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `CyclicBarrier` | Một rào cản đồng bộ hóa có thể tái sử dụng, nơi một số lượng luồng cố định phải chờ đợi lẫn nhau trước khi tiếp tục. |
| `Phaser` | Một rào cản đồng bộ hóa linh hoạt, có thể tái sử dụng, hỗ trợ đăng ký động số lượng tham gia (parties) và thực thi nhiều giai đoạn (multi-phase). |
| `BlockingQueue` | Một giao diện hàng đợi an toàn luồng, chặn luồng đưa dữ liệu vào (putting threads) nếu hàng đợi đầy, và chặn luồng lấy dữ liệu ra (taking threads) nếu trống. |
| `Concurrent collections:` | Các tập hợp an toàn luồng đặc biệt trong gói `java.util.concurrent` được tối ưu hóa cho thông lượng đồng thời cao mà không cần khóa toàn cục. |
| `ConcurrentHashMap` | Một bản đồ băm (hash map) hiệu suất cao, an toàn luồng sử dụng cơ chế chia nhỏ khóa (fine-grained lock striping) và các thao tác CAS. Thao tác đọc là không chặn. |
| `CopyOnWriteArrayList` | Một danh sách an toàn luồng tạo ra một bản sao mới của mảng cơ sở sau mỗi thao tác ghi. Hiệu quả cho các kịch bản đọc nhiều (read-heavy). |
| `ConcurrentLinkedQueue` | Một hàng đợi an toàn luồng không giới hạn dựa trên các liên kết nút đồng thời không dùng khóa (sử dụng CAS). |
| `Executor Framework:` | Một khung thư viện giúp đơn giản hóa việc thực thi tác vụ bất đồng bộ bằng cách nhóm và quản lý các luồng làm việc. |

## Chi tiết tài liệu học tập (Detailed Notes)

### CyclicBarrier so với CountDownLatch (CyclicBarrier vs CountDownLatch)
* **CountDownLatch**: Không thể thiết lập lại (reset). Một luồng chờ đợi, các luồng khác thực hiện giảm số đếm.
* **CyclicBarrier**: Có thể tái sử dụng (thiết lập lại số đếm sau khi vượt qua). Các luồng chờ đợi lẫn nhau tại một điểm rào cản chung thông qua `barrier.await()`. Có thể thực thi một hành động rào cản ("barrier action") tùy chọn khi tất cả các luồng đã đến.

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

### Các tập hợp đồng thời: ConcurrentHashMap so với SynchronizedMap (Concurrent Collections: ConcurrentHashMap vs SynchronizedMap)
* `Collections.synchronizedMap()` khóa *toàn bộ* bản đồ cho mỗi thao tác đọc và ghi, gây ra sự tranh chấp luồng (thread contention) nghiêm trọng.
* `ConcurrentHashMap` phân chia bản đồ thành các dải khóa (lock stripes) hoặc các phân đoạn (buckets). Nhiều luồng có thể đọc đồng thời mà không cần khóa, và ghi đồng thời vào các phân đoạn khác nhau.
* **Quan trọng**: Các thao tác hỗn hợp (như kiểm tra-rồi-thực-hiện (check-then-act)) không an toàn trên `ConcurrentHashMap` trừ khi sử dụng các phương thức nguyên tử như `putIfAbsent()`, `replace()`, hoặc `computeIfAbsent()`.

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
Các thao tác thay đổi trạng thái (add, set, remove) sao chép toàn bộ mảng cơ sở. Điều này rất tốn kém đối với các thao tác ghi nhưng giúp thao tác đọc cực kỳ nhanh và không cần khóa. Các bộ lặp (Iterators) sẽ đọc một bản chụp (snapshot) của mảng và không bao giờ ném ra ngoại lệ `ConcurrentModificationException`.

---

## Tình huống nghiên cứu: Đăng ký lắng nghe sự kiện (Case Study: Event Listener Registry)

### Vấn đề (Problem)
Một khung phát triển giao diện người dùng (GUI framework) có một lớp lõi truyền các sự kiện tới danh sách các bộ lắng nghe (listeners) đã đăng ký. Các bộ lắng nghe có thể được thêm hoặc xóa một cách động, và đôi khi một bộ lắng nghe cố gắng hủy đăng ký *trong khi* một sự kiện đang được phát sóng (dẫn đến ngoại lệ `ConcurrentModificationException` nếu sử dụng một `ArrayList` tiêu chuẩn).

### Giải pháp (Solution)
Sử dụng `CopyOnWriteArrayList`.
```java
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventPublisher {
    // Highly read-heavy: events are fired frequently, listeners change rarely.
    private final List<Listener> listeners = new CopyOnWriteArrayList<>();

    public void addListener(Listener l) {
        listeners.add(l);
    }

    public void removeListener(Listener l) {
        listeners.remove(l);
    }

    public void publishEvent(String event) {
        // Safe lock-free iteration. No ConcurrentModificationException even if
        // a listener calls removeListener() inside onEvent().
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

## Các sai lầm thường gặp (Common Mistakes)

### 1. Sử dụng CopyOnWriteArrayList cho các danh sách ghi nhiều (Write-Heavy)
Nếu bạn ghi dữ liệu vào một `CopyOnWriteArrayList` bên trong một vòng lặp, nó sẽ sao chép toàn bộ mảng trên mỗi lần lặp, hủy hoại hiệu suất và gây ra áp lực rất lớn lên bộ thu gom rác (garbage collector — GC).
```java
// BUG: Massive array copy overhead
CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
for (int i = 0; i < 10000; i++) {
    list.add(i); // Copies array 10,000 times!
}
```

### 2. Lỗi Kiểm tra-rồi-thực-hiện (Check-Then-Act) với ConcurrentHashMap
Giả định rằng việc kiểm tra một giá trị trong `ConcurrentHashMap` rồi thực hiện hành động trên đó là nguyên tử.
```java
// BUG: Race condition! Two threads could see containsKey as false and both insert.
if (!map.containsKey("key")) {
    map.put("key", newValue);
}

// FIX: Sử dụng computeIfAbsent nguyên tử
map.computeIfAbsent("key", k -> newValue);
```

## Tại sao CyclicBarrier và CountDownLatch khác nhau (Why CyclicBarrier and CountDownLatch Differ)

`CountDownLatch` và `CyclicBarrier` là các tiện ích đồng thời được thiết kế để đồng bộ hóa luồng, nhưng chúng khác nhau đáng kể về khả năng tái sử dụng và cơ chế thực thi. `CountDownLatch` hoạt động như một cánh cổng một lần; các luồng giảm bộ đếm của nó bằng cách gọi `countDown()` và bị chặn tại `await()` cho đến khi số đếm về 0, tại thời điểm đó chốt không thể được thiết lập lại hoặc tái sử dụng. Ngược lại, `CyclicBarrier` hoàn toàn có thể tái sử dụng và đồng bộ hóa các luồng tại một điểm rào cản chung. Khi các luồng gọi `await()` trên một `CyclicBarrier`, chúng bị chặn cho đến khi số lượng luồng chỉ định đã đến đủ. Một khi số đếm rào cản đạt đến 0, rào cản được kích hoạt (tripped), thực thi một hành động rào cản tùy chọn, thiết lập lại bộ đếm nội bộ về trạng thái ban đầu và giải phóng tất cả các luồng đang chờ để tiếp tục.

### Mô hình tư duy: CountDownLatch so với CyclicBarrier (Mental Model: CountDownLatch vs. CyclicBarrier)
```
CountDownLatch (One-shot):
Threads ──► countDown() ──► [Count: 3 -> 2 -> 1 -> 0] ──► Gate Opens (Cannot reuse)

CyclicBarrier (Reusable):
Thread 1 ──► await() ──┐
Thread 2 ──► await() ──┼─► [Count: 3 -> 0] ─► Trip ─► Run Action ─► Reset to 3 ─► Release
Thread 3 ──► await() ──┘
```

### Ví dụ mã nguồn (Code Example)
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
        // Output:
        // Thread-1 arriving
        // Thread-2 arriving
        // Barrier Tripped!
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Các luồng gọi `barrier.await()`
  → Khóa nội bộ được giành lấy
  → Số lượng luồng đã đến giảm đi
  → Số đếm khác 0
  → Các luồng chờ đợi trên một Điều kiện (Condition)
  → Luồng cuối cùng gọi `await()`
  → Số đếm về 0
  → Hành động rào cản tùy chọn chạy
  → Rào cản thiết lập lại số đếm và thế hệ (generation)
  → Điều kiện gửi tín hiệu cho tất cả
  → Tất cả các luồng được giải phóng
  → Rào cản sẵn sàng cho chu kỳ tiếp theo.
```

