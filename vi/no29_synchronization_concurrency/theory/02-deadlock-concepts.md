# Đồng bộ hóa và Đồng thời - Phần 2 (Synchronization and Concurrency - Part 2)

## Mục tiêu học tập

Tài liệu này tập trung vào các vấn đề đồng bộ hóa nâng cao (deadlock, livelock, starvation), tính hiển thị của bộ nhớ (`volatile`), và các nguyên ngữ nguyên tử không dùng khóa (lock-free atomic primitives). Hãy nghiên cứu từng khái niệm dưới dạng quy tắc Java thực tế, thay vì chỉ học các từ vựng rời rạc.

## Đề cương chi tiết

- **`Deadlock`** — Khóa chết (Deadlock): Tình huống mà hai hoặc nhiều luồng bị chặn vĩnh viễn, mỗi luồng chờ một khóa do luồng khác nắm giữ.
- **`Livelock`** — Khóa động (Livelock): Kịch bản mà các luồng tích cực thay đổi trạng thái của chúng để phản hồi lẫn nhau, nhưng không thể tạo ra bất kỳ tiến trình thực thi nào.
- **`Starvation`** — Đói tài nguyên (Starvation): Tình trạng một luồng liên tục bị từ chối truy cập vào tài nguyên chia sẻ hoặc chu kỳ CPU do các luồng tham lam hoặc định kiến lập lịch.
- **`Volatile`** — Biến volatile (Volatile): Từ khóa đảm bảo việc đọc và ghi vào một trường sẽ đi trực tiếp vào bộ nhớ chính, bỏ qua bộ nhớ đệm CPU. Ngăn chặn việc sắp xếp lại lệnh (instruction reordering) nhưng **không** đảm bảo tính nguyên tử.
- **`Atomic classes:`** — Các lớp nguyên tử (Atomic classes): Tập hợp các lớp trong `java.util.concurrent.atomic` sử dụng các chỉ thị phần cứng So sánh và Hoán đổi (Compare-And-Swap - CAS) không dùng khóa để đạt được tính an toàn luồng.
- **`AtomicInteger`** — AtomicInteger: Trình bao bọc nguyên tử cho kiểu dữ liệu `int` (các phương thức: `incrementAndGet()`, `compareAndSet()`).
- **`AtomicLong`** — AtomicLong: Trình bao bọc nguyên tử cho kiểu dữ liệu `long`.
- **`AtomicBoolean`** — AtomicBoolean: Trình bao bọc nguyên tử cho kiểu dữ liệu `boolean`.

## Ghi chú chi tiết

### Khóa chết và Khóa động (Deadlock and Livelock)

#### Khóa chết (Deadlock)
Deadlock yêu cầu bốn điều kiện đồng thời (các điều kiện Coffman):
1. **Loại trừ lẫn nhau (Mutual Exclusion)**: Các tài nguyên được nắm giữ độc quyền.
2. **Giữ và Đợi (Hold and Wait)**: Các luồng đang nắm giữ khóa sẽ đợi để có thêm các khóa bổ sung.
3. **Không cướp đoạt (No Preemption)**: Các khóa không thể bị tước đoạt một cách ép buộc từ các luồng.
4. **Chờ đợi vòng lặp (Circular Wait)**: Luồng A giữ Khóa 1 và chờ Khóa 2; Luồng B giữ Khóa 2 và chờ Khóa 1.

**Khắc phục**: Loại bộ chờ đợi vòng lặp bằng cách lấy các khóa theo một thứ tự toàn cục cố định.

```java
// Deadlock prone
public void transfer(Account from, Account to, double amt) {
    synchronized (from) {
        synchronized (to) {
            // transfer
        }
    }
}
```

#### Khóa động (Livelock)
Không giống như deadlock, các luồng không bị chặn. Chúng tích cực tiêu thụ các chu kỳ CPU, thay đổi trạng thái của mình để phản hồi lại các luồng khác, nhưng không thể hoàn thành nhiệm vụ.

### Tính hiển thị của Volatile (Volatile Visibility)
Nếu không có `volatile`, các cập nhật được thực hiện đối với một biến bởi Luồng A có thể được lưu trữ trong thanh ghi/bộ nhớ đệm CPU và không được nhìn thấy bởi Luồng B khi đọc từ bộ nhớ chính.
`volatile` đảm bảo **tính hiển thị (visibility)** và **thứ tự (ordering)** (ngăn chặn JVM sắp xếp lại các lệnh xung quanh biến đó). Nó **không** đảm bảo tính nguyên tử (atomicity).

```java
public class VolatileFlag implements Runnable {
    private volatile boolean active = true; // Volatile flag

    public void stop() { active = false; }

    @Override
    public void run() {
        while (active) {
            // Perform work
        }
        System.out.println("Stopped cleanly.");
    }
}
```

> Xem thêm: Bản chất của visibility và instruction reordering của `volatile`, được trình bày chi tiết trong [Ch.10 - Access Modifiers](../../no10_modifiers/theory/02-abstract-concepts.md).

### Các lớp nguyên tử và So sánh và Hoán đổi (Compare-And-Swap - CAS)
Các lớp nguyên tử sử dụng các chỉ thị CPU không dùng khóa (như `CMPXCHG` trên x86) để thực hiện các chu kỳ cập nhật nguyên tử.
* **Thao tác CAS**: Nhận giá trị kỳ vọng (expected value) và giá trị mới (new value). Chỉ cập nhật nếu giá trị hiện tại bằng giá trị kỳ vọng. Trả về true nếu thành công; ngược lại, lặp lại và thử lại.

```java
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    private final AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet(); // Lock-free atomic increment
    }

    public void updateMax(int newValue) {
        int current;
        do {
            current = count.get();
            if (newValue <= current) break;
        } while (!count.compareAndSet(current, newValue)); // CAS Loop
    }
}
```

---

## Ví Dụ Thực Tế: Thứ tự khóa khi chuyển khoản tài khoản ngân hàng

### Vấn đề
Trong một ứng dụng ngân hàng, nếu Khách hàng A chuyển tiền cho Khách hàng B trong khi Khách hàng B cũng đồng thời chuyển tiền cho Khách hàng A, một deadlock có thể xảy ra vì Luồng 1 khóa A rồi đến B, trong khi Luồng 2 khóa B rồi đến A.

### Giải pháp
Thiết lập một thứ tự khóa nhất quán bằng cách sử dụng các mã băm (hash code) hoặc một khóa tài khoản duy nhất.
```java
public class SafeBankTransfer {
    public void transfer(Account from, Account to, double amount) {
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);

        if (fromHash < toHash) {
            synchronized (from) {
                synchronized (to) {
                    doTransfer(from, to, amount);
                }
            }
        } else if (fromHash > toHash) {
            synchronized (to) {
                synchronized (from) {
                    doTransfer(from, to, amount);
                }
            }
        } else {
            // Tie-breaker lock in the rare case of hash collision
            synchronized (tieLock) {
                synchronized (from) {
                    synchronized (to) {
                        doTransfer(from, to, amount);
                    }
                }
            }
        }
    }
    private static final Object tieLock = new Object();
    private void doTransfer(Account from, Account to, double amt) {
        from.debit(amt);
        to.credit(amt);
    }
}
```

---

## Các lỗi thường gặp

### 1. Giả định volatile sẽ giúp count++ an toàn luồng
`count++` là một thao tác hỗn hợp gồm ba bước: đọc, cộng 1, ghi lại. Khai báo `count` là `volatile` đảm bảo các luồng khác thấy được thao tác ghi, nhưng không ngăn cản một luồng khác xen vào giữa các bước đọc và ghi đó.
```java
// BUG: Thread-unsafe
volatile int count = 0;
public void add() { count++; }
```

### 2. Lồng các khóa mà không có thứ tự cố định
Lồng các khóa trên các tài nguyên được truyền động dưới dạng đối số là nguyên nhân chính gây ra deadlock trong môi trường production.

## Tại sao Deadlock xảy ra và làm thế nào để tránh chúng

Một deadlock xảy ra trong Java khi hai hoặc nhiều luồng bị chặn vĩnh viễn, mỗi luồng chờ một khóa đang được nắm giữ bởi luồng khác. Để một deadlock xảy ra, bốn điều kiện Coffman phải được đáp ứng đồng thời: Loại trừ lẫn nhau (Mutual Exclusion - chỉ một luồng có thể giữ một tài nguyên tại một thời điểm), Giữ và Đợi (Hold and Wait - một luồng đang giữ tài nguyên có thể yêu cầu thêm các tài nguyên khác), Không cướp đoạt (No Preemption - tài nguyên không thể bị tước đoạt cưỡng chế từ một luồng), và Chờ đợi vòng lặp (Circular Wait - tồn tại một chuỗi kín các luồng trong đó mỗi luồng giữ một tài nguyên mà luồng tiếp theo cần). Tác nhân phổ biến nhất là việc khóa lồng nhau theo các thứ tự không nhất quán. Ví dụ: nếu Luồng A giữ Khóa 1 và yêu cầu Khóa 2, trong khi Luồng B giữ Khóa 2 và yêu cầu Khóa 1, cả hai luồng sẽ đi vào trạng thái bị chặn vĩnh viễn. Chúng ta có thể phá vỡ điều kiện Chờ đợi vòng lặp bằng cách thực thi một thứ tự lấy khóa nghiêm ngặt hoặc bằng cách sử dụng `tryLock()` của Lock API với một thời gian chờ (timeout) để tránh việc chờ đợi vô hạn.

### Mô hình tư duy: Chu trình Deadlock
```
    ┌──────────┐   Giữ   ┌──────────┐
    │ Luồng A  │ ──────► │  Khóa 1  │
    └──────────┘         └──────────┘
         ▲                     │
      Chờ đợi               Chờ đợi
         │                     ▼
    ┌──────────┐   Giữ   ┌──────────┐
    │  Khóa 2  │ ◄────── │ Luồng B  │
    └──────────┘         └──────────┘
```

### Ví dụ mã nguồn
```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockAvoidance {
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    public void safeMethod() {
        // Avoid deadlock by attempting non-blocking acquisition
        boolean acquired1 = lock1.tryLock();
        boolean acquired2 = lock2.tryLock();
        try {
            if (acquired1 && acquired2) {
                // Critical section safely executed
            }
        } finally {
            if (acquired1) lock1.unlock();
            if (acquired2) lock2.unlock();
        }
    }

    public static void main(String[] args) {
        DeadlockAvoidance demo = new DeadlockAvoidance();
        demo.safeMethod();
        System.out.println("Execution Completed"); // Output: Execution Completed
    }
}
```

### Chuỗi nguyên nhân - kết quả
Khóa lồng nhau trên các tài nguyên theo các thứ tự khác nhau &rarr; Luồng A lấy được Khóa 1, Luồng B lấy được Khóa 2 &rarr; Luồng A yêu cầu Khóa 2, Luồng B yêu cầu Khóa 1 &rarr; Không luồng nào có thể tiếp tục &rarr; Bốn điều kiện deadlock được thỏa mãn &rarr; Hiệu suất sử dụng CPU giảm về không đối với các luồng này &rarr; Việc thực thi luồng bị đóng băng &rarr; Deadlock xảy ra &rarr; Được giải quyết bằng thứ tự khóa hoặc `tryLock` dựa trên thời gian chờ.
