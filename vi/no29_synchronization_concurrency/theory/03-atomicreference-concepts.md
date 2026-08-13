# Đồng Bộ Hóa và Độ Đồng Thời (Synchronization and Concurrency) - Phần 3

## Ghi Chú Chi Tiết

### Lock API và ReentrantLock
Khác với các khối `synchronized` vốn có cấu trúc và giới hạn theo phạm vi khối lệnh, các đối tượng `Lock` tường minh yêu cầu việc tranh chấp khóa và giải phóng khóa thủ công.
* **Quan trọng**: Bạn phải luôn gọi `unlock()` bên trong khối `finally` để ngăn chặn rò rỉ tài nguyên trong trường hợp xảy ra ngoại lệ.

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
Cho phép độ đồng thời cao cho các hoạt động thiên về đọc dữ liệu. Nhiều luồng đọc có thể giữ khóa đọc đồng thời, nhưng khóa ghi là độc quyền.
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

### StampedLock (Đọc Tối Ưu Hóa - Optimistic Reading)
`StampedLock` cung cấp một nhãn khóa (stamp). Nó hỗ trợ "đọc lạc quan (optimistic reading)", cho phép các luồng đọc lấy dữ liệu mà không chặn các phép ghi. Nếu một phép ghi xảy ra trong quá trình đọc, nhãn khóa được xác thực là không hợp lệ, và trình đọc sẽ thử lại bằng một khóa đọc bi quan (pessimistic read lock).
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
* **Semaphore**: Kiểm soát việc sử dụng tài nguyên thông qua các giấy phép (permits). Luồng gọi `acquire()` để lấy một giấy phép (chặn nếu không còn cái nào) và gọi `release()` để trả lại nó.
* **CountDownLatch**: Một cánh cổng dùng một lần. Các luồng gọi `await()` để chặn cho đến khi các luồng khác gọi `countDown()` đủ số lần để giảm số lượng chốt (latch count) về 0.

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

## Ví Dụ Thực Tế: Nhóm Kết Nối Cơ Sở Dữ Liệu Bị Giới Hạn Qua Semaphore

### Vấn đề
Một nhóm kết nối cơ sở dữ liệu (database connection pool) có giới hạn cứng là 5 kết nối vật lý. Nếu có nhiều hơn 5 luồng cố gắng lấy một kết nối đồng thời, chúng sẽ bị chặn cho đến khi một kết nối được giải phóng.

### Giải pháp
Bọc việc truy cập kết nối bằng một `Semaphore`.
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

## Các Lỗi Thường Gặp

### 1. Rò Rỉ Khóa (Quên Không Giải Phóng Khóa Trong Khối Finally)

Nếu xảy ra ngoại lệ bên trong miền tranh chấp (critical section) và `unlock()` không nằm trong khối `finally`, khóa sẽ bị giữ mãi mãi, gây ra hiện tượng bế tắc (deadlock) cho các luồng khác.
```java
// LỖI (BUG)
lock.lock();
doTask(); // Nếu dòng này ném ra RuntimeException, khóa sẽ bị rò rỉ!
lock.unlock();
```

### 2. Tự Bế Tắc Với StampedLock (Không Reentrant)

Khác với `ReentrantLock`, `StampedLock` **không** hỗ trợ reentrant. Một luồng đang giữ khóa ghi của `StampedLock` mà cố gắng lấy lại nó một lần nữa sẽ tự làm mình rơi vào trạng thái bế tắc.
```java
StampedLock lock = new StampedLock();
long s1 = lock.writeLock();
long s2 = lock.writeLock(); // BẾ TẮC (DEADLOCK): chặn để chờ chính khóa của nó!
```

## Tại Sao Các Biến Nguyên Tử Giúp Tránh Cơ Chế Đồng Bộ Hóa Dựa Trên Khóa

Các biến nguyên tử tránh cơ chế đồng bộ hóa dựa trên khóa bằng cách sử dụng các thuật toán không dùng khóa (lock-free) hoạt động trên các chỉ thị Compare-And-Swap (CAS) ở cấp độ phần cứng. Ngược lại với các khối `synchronized` vốn tạm dừng các luồng bằng cách sử dụng chuyển đổi ngữ cảnh cấp hệ điều hành, CAS dựa vào các chỉ thị CPU như `CMPXCHG`. Hoạt động CAS nhận ba đối số: một địa chỉ bộ nhớ, giá trị hiện tại mong đợi tại địa chỉ đó, và một giá trị đích mới. Nếu giá trị tại địa chỉ bộ nhớ khớp với giá trị mong đợi, CPU sẽ cập nhật nó sang giá trị mới trong một chỉ thị nguyên tử duy nhất. Nếu một luồng khác đã sửa đổi giá trị trong thời gian đó, bước kiểm tra sẽ thất bại, và luồng gọi sẽ lặp (spin) để thử lại hoạt động với giá trị đã cập nhật thay vì bị chặn lại.

### Mô Hình Tư Duy: Vòng Lặp Trống CAS (CAS Spin Loop)
```text
   [ Thread A ] ──► Đọc giá trị (V=5)
                          │
          Cập nhật bản sao cục bộ thành (Mới=6)
                          │
              CAS(Địa chỉ, V=5, Mới=6)
                          │
        ┌────────────────┴────────────────┐
        ▼ (Mong đợi == Thực tế)           ▼ (Mong đợi != Thực tế)
    [ THÀNH CÔNG: V trở thành 6 ]     [ THẤT BẠI: Vòng lặp spin & thử lại với V=thực tế ]
```

### Ví Dụ Mã Nguồn
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
        } while (!value.compareAndSet(expected, next)); // Vòng lặp CAS
    }

    public static void main(String[] args) {
        CASDemo demo = new CASDemo();
        demo.safeIncrement();
        System.out.println("Giá trị: " + demo.value.get()); // Output: Giá trị: 1
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả
Luồng đọc địa chỉ bộ nhớ &rarr; Biến cục bộ giữ giá trị mong đợi &rarr; Luồng tính toán giá trị mới &rarr; Luồng thực thi CAS phần cứng (`compareAndSet`) &rarr; CPU so sánh giá trị bộ nhớ hiện tại với giá trị mong đợi &rarr; Giá trị khớp &rarr; Cập nhật nguyên tử thành công &rarr; Giá trị không khớp &rarr; CAS trả về false &rarr; Luồng quay lại vòng lặp và thử lại (spin) &rarr; Đạt được an toàn luồng mà không tốn chi phí chặn luồng.
