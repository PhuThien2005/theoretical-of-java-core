# Đồng bộ hóa và Đồng thời - Phần 3 (Synchronization and Concurrency - Part 3)

## Mục tiêu học tập (Learning Goal)

Tệp này bao gồm API Lock hiển thị của Java (`Lock`, `ReentrantLock`, `ReadWriteLock`, `StampedLock`) và các bộ đồng bộ hóa cấp cao (`Semaphore`, `CountDownLatch`). Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, không phải là từ vựng cô lập.

## Đề cương chi tiết (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `AtomicReference` | Cung cấp các thao tác nguyên tử, không dùng khóa trên các tham chiếu đối tượng bằng cách sử dụng Compare-And-Swap. |
| `Lock API:` | Khung làm việc `java.util.concurrent.locks` cung cấp khả năng khóa linh hoạt và mạnh mẽ hơn so với các khối `synchronized`. |
| `Lock` | Giao diện gốc định nghĩa các thao tác giành khóa (`lock()`, `tryLock()`, `unlock()`). |
| `ReentrantLock` | Một khóa loại trừ tương hỗ có hành vi tương tự như các khóa monitor nội tại, nhưng cung cấp các tính năng bổ sung như tính công bằng (fairness), thời gian chờ (timeouts), và giành khóa có thể ngắt (interruptible). |
| `ReadWriteLock` | Một cặp khóa cho phép nhiều luồng (Thread) đọc đồng thời, nhưng giới hạn quyền truy cập ghi độc quyền cho một luồng duy nhất. |
| `StampedLock` | Một khóa nâng cao có ba chế độ (ghi, đọc, đọc lạc quan — optimistic read) và xác thực dựa trên dấu hiệu (stamp). Nó **không** có tính tái nhập (reentrant). |
| `Semaphore` | Một bộ đồng bộ hóa duy trì một tập hợp các giấy phép (permits) để hạn chế truy cập đồng thời vào một nhóm tài nguyên (resource pool). |
| `CountDownLatch` | Một công cụ hỗ trợ đồng bộ hóa cho phép một hoặc nhiều luồng chờ cho đến khi một tập hợp các thao tác được thực hiện trong các luồng khác hoàn thành. |

## Chi tiết tài liệu học tập (Detailed Notes)

### API Lock và ReentrantLock (Lock API and ReentrantLock)
Khác với các khối `synchronized` vốn có cấu trúc và phạm vi khối, các đối tượng `Lock` tường minh yêu cầu việc giành và giải phóng khóa thủ công.
* **Quan trọng**: Bạn phải luôn gọi `unlock()` bên trong một khối `finally` để ngăn rò rùi tài nguyên trong trường hợp xảy ra ngoại lệ.

```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExplicitLockDemo {
    private final Lock lock = new ReentrantLock();

    public void performTask() {
        lock.lock(); // Blocks until acquired
        try {
            // Critical section
        } finally {
            lock.unlock(); // Always release in finally block!
        }
    }
}
```

### ReadWriteLock
Cho phép tính đồng thời cao cho các thao tác đọc nhiều (read-heavy). Nhiều luồng đọc có thể giữ khóa đọc đồng thời, nhưng khóa ghi là độc quyền.
```java
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheDemo {
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private String data = "";

    public String read() {
        rwLock.readLock().lock();
        try { return data; }
        finally { rwLock.readLock().unlock(); }
    }

    public void write(String val) {
        rwLock.writeLock().lock();
        try { data = val; }
        finally { rwLock.writeLock().unlock(); }
    }
}
```

### StampedLock (Đọc lạc quan — StampedLock (Optimistic Reading))
`StampedLock` cung cấp một nhãn khóa (lock stamp). Nó hỗ trợ "đọc lạc quan" (optimistic reading), cho phép các luồng đọc truy xuất dữ liệu mà không chặn các luồng ghi. Nếu một thao tác ghi xảy ra trong quá trình đọc, nhãn khóa được xác thực là không hợp lệ, và luồng đọc sẽ thử lại bằng một khóa đọc bi quan (pessimistic read lock).
```java
import java.util.concurrent.locks.StampedLock;

public class StampedLockDemo {
    private final StampedLock lock = new StampedLock();
    private double x, y;

    public double getDistance() {
        long stamp = lock.tryOptimisticRead(); // Non-blocking read
        double curX = x, curY = y;
        
        if (!lock.validate(stamp)) { // Check if a write occurred
            stamp = lock.readLock(); // Fallback to pessimistic read lock
            try {
                curX = x; curY = y;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return Math.sqrt(curX * curX + curY * curY);
    }
}
```

### Semaphore và CountDownLatch
* **Semaphore**: Kiểm soát việc sử dụng tài nguyên thông qua các giấy phép (permits). Luồng gọi `acquire()` để nhận giấy phép (bị chặn nếu không còn giấy phép nào) và gọi `release()` để trả lại.
* **CountDownLatch**: Một cánh cổng một lần (one-time gate). Các luồng gọi `await()` để chặn cho đến khi các luồng khác gọi `countDown()` đủ số lần để giảm số đếm của chốt về 0.

```java
import java.util.concurrent.CountDownLatch;

public class LatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        Runnable worker = () -> {
            System.out.println("Step finished.");
            latch.countDown();
        };

        new Thread(worker).start();
        new Thread(worker).start();

        latch.await(); // Blocks until count becomes 0
        System.out.println("All steps completed.");
    }
}
```

---

## Tình huống nghiên cứu: Nhóm kết nối cơ sở dữ liệu có giới hạn thông qua Semaphore (Case Study: Bounded Database Connection Pool via Semaphore)

### Vấn đề (Problem)
Một nhóm kết nối cơ sở dữ liệu (connection pool) có giới hạn cứng là 5 kết nối vật lý. Nếu có nhiều hơn 5 luồng cố gắng giành kết nối đồng thời, chúng sẽ bị chặn cho đến khi một kết nối được giải phóng.

### Giải pháp (Solution)
Bao bọc việc truy cập kết nối bằng một `Semaphore`.
```java
import java.util.concurrent.Semaphore;

public class ConnectionPool {
    private final Semaphore semaphore = new Semaphore(5); // Maximum 5 connections
    private final Connection[] connections = new Connection[5];
    private final boolean[] used = new boolean[5];

    public Connection getConnection() throws InterruptedException {
        semaphore.acquire(); // Blocks if all 5 connections are in use
        return getNextAvailableConnection();
    }

    public void releaseConnection(Connection c) {
        if (markAsFree(c)) {
            semaphore.release(); // Releases a permit, waking up a blocked thread
        }
    }

    private synchronized Connection getNextAvailableConnection() {
        for (int i = 0; i < 5; i++) {
            if (!used[i]) {
                used[i] = true;
                return connections[i];
            }
        }
        return null;
    }

    private synchronized boolean markAsFree(Connection c) {
        for (int i = 0; i < 5; i++) {
            if (connections[i] == c) {
                if (used[i]) {
                    used[i] = false;
                    return true;
                }
            }
        }
        return false;
    }
    
    private static class Connection {} // Stub class
}
```

---

## Các sai lầm thường gặp (Common Mistakes)

### 1. Rò rỉ khóa (Quên mở khóa trong khối Finally - Leaking Locks (Forgetting to Unlock in Finally))
Nếu một ngoại lệ xảy ra bên trong vùng tranh chấp (critical section) and `unlock()` không nằm trong khối `finally`, khóa sẽ bị giữ mãi mãi, gây ra deadlock cho các luồng khác.
```java
// BUG
lock.lock();
doTask(); // If this throws RuntimeException, lock is leaked!
lock.unlock();
```

### 2. Tự gây bế tắc với StampedLock (Không có tính tái nhập - Self-Deadlock with StampedLock (Non-Reentrant))
Khác với `ReentrantLock`, `StampedLock` **không** có tính tái nhập. Một luồng đang giữ khóa ghi của `StampedLock` cố gắng giành lấy nó lần nữa sẽ tự làm mình bế tắc.
```java
StampedLock lock = new StampedLock();
long s1 = lock.writeLock();
long s2 = lock.writeLock(); // DEADLOCK: blocks waiting for its own lock!
```

## Tại sao các biến nguyên tử tránh đồng bộ hóa dựa trên khóa (Why Atomic Variables Avoid Lock-Based Synchronization)

Các biến nguyên tử tránh việc đồng bộ hóa dựa trên khóa bằng cách sử dụng các thuật toán không khóa (lock-free algorithms) được cung cấp bởi các chỉ thị So sánh và Tráo đổi (Compare-And-Swap - CAS) ở cấp độ phần cứng. Khác với các khối `synchronized` vốn đình chỉ hoạt động của luồng bằng cách chuyển đổi ngữ cảnh ở cấp hệ điều hành (OS-level context switching), CAS dựa vào các chỉ thị CPU như `CMPXCHG`. Thao tác CAS nhận ba đối số: một địa chỉ bộ nhớ, giá trị hiện tại mong đợi tại địa chỉ đó, và một giá trị đích mới. Nếu giá trị tại địa chỉ bộ nhớ khớp với giá trị mong đợi, CPU sẽ cập nhật nó sang giá trị mới trong một chỉ thị nguyên tử duy nhất. Nếu một luồng khác đã sửa đổi giá trị trong thời gian đó, bước kiểm tra sẽ thất bại, và luồng gọi sẽ lặp lại (xoay vòng - spins) để thử lại thao tác với giá trị được cập nhật thay vì bị chặn.

### Mô hình tư duy: Vòng lặp xoay CAS (Mental Model: CAS Spin Loop)
```
   [ Thread A ] ──► Read Value (V=5)
                          │
          Update local copy to (New=6)
                          │
              CAS(Address, V=5, New=6)
                          │
        ┌────────────────┴────────────────┐
        ▼ (Expected == Actual)            ▼ (Expected != Actual)
   [ SUCCESS: V becomes 6 ]     [ FAIL: Spin & retry with V=actual ]
```

### Ví dụ mã nguồn (Code Example)
```java
import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {
    private final AtomicInteger value = new AtomicInteger(0);

    public void safeIncrement() {
        int expected;
        int next;
        do {
            expected = value.get();
            next = expected + 1;
        } while (!value.compareAndSet(expected, next)); // CAS loop
    }

    public static void main(String[] args) {
        CASDemo demo = new CASDemo();
        demo.safeIncrement();
        System.out.println("Value: " + demo.value.get()); // Output: Value: 1
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Luồng đọc địa chỉ bộ nhớ
  → Biến cục bộ giữ giá trị mong đợi
  → Luồng tính toán giá trị mới
  → Luồng thực thi CAS phần cứng (`compareAndSet`)
  → CPU so sánh giá trị bộ nhớ hiện tại với giá trị mong đợi
  → Giá trị khớp
  → Cập nhật nguyên tử thành công
  → Giá trị không khớp
  → CAS trả về false
  → Luồng quay lại và thử lại (xoay vòng)
  → Đạt được an toàn luồng mà không tốn chi phí chặn.
```

