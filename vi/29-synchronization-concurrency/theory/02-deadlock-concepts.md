# Đồng bộ hóa và Đồng thời - Phần 2 (Synchronization and Concurrency - Part 2)

## Mục tiêu học tập (Learning Goal)

Tệp này bao gồm các vấn đề đồng bộ hóa nâng cao (bế tắc - deadlock, nghẽn lặp - livelock, đói tài nguyên - starvation), hiển thị bộ nhớ (volatile), và các nguyên thủy nguyên tử không dùng khóa (lock-free atomic primitives). Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, không phải là từ vựng cô lập.

## Đề cương chi tiết (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `Deadlock` | Một tình huống mà hai hoặc nhiều luồng bị chặn vĩnh viễn, mỗi luồng chờ đợi một khóa được nắm giữ bởi một luồng khác. |
| `Livelock` | Một kịch bản trong đó các luồng tích cực thay đổi trạng thái của chúng để phản ứng lại nhau, nhưng không đạt được tiến trình thực thi nào. |
| `Starvation` | Một điều kiện mà một luồng vĩnh viễn bị từ chối truy cập vào các tài nguyên dùng chung hoặc chu kỳ CPU do các luồng tham lam hoặc định kiến lập lịch. |
| `Volatile` | Từ khóa đảm bảo các thao tác đọc và ghi vào một trường đi trực tiếp đến bộ nhớ chính, bỏ qua bộ đệm (cache) CPU. Ngăn chặn sắp xếp lại chỉ thị (instruction reordering) nhưng không đảm bảo tính nguyên tử. |
| `Atomic classes:` | Một bộ các lớp trong `java.util.concurrent.atomic` sử dụng các chỉ thị phần cứng So sánh và Tráo đổi (Compare-And-Swap - CAS) không dùng khóa để đạt được an toàn luồng. |
| `AtomicInteger` | Một lớp bao bọc nguyên tử cho kiểu `int` (các phương thức: `incrementAndGet()`, `compareAndSet()`). |
| `AtomicLong` | Một lớp bao bọc nguyên tử cho kiểu `long`. |
| `AtomicBoolean` | Một lớp bao bọc nguyên tử cho kiểu `boolean`. |

## Chi tiết tài liệu học tập (Detailed Notes)

### Bế tắc và Nghẽn lặp (Deadlock and Livelock)

#### Bế tắc (Deadlock)
Bế tắc (Deadlock) yêu cầu bốn điều kiện đồng thời (điều kiện Coffman):
1. **Loại trừ tương hỗ (Mutual Exclusion)**: Các tài nguyên được nắm giữ một cách độc quyền.
2. **Giữ và Chờ (Hold and Wait)**: Các luồng đang giữ khóa tiếp tục chờ các khóa bổ sung.
3. **Không cướp đoạt (No Preemption)**: Các khóa không thể bị lấy đi một cách cưỡng chế từ các luồng.
4. **Chờ đợi vòng tròn (Circular Wait)**: Luồng A giữ Khóa 1 và chờ Khóa 2; Luồng B giữ Khóa 2 và chờ Khóa 1.

**Khắc phục**: Loại bỏ chờ đợi vòng tròn bằng cách giành lấy các khóa theo một thứ tự cố định, toàn cục.

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

#### Nghẽn lặp (Livelock)
Khác với deadlock, các luồng trong livelock không bị chặn. Chúng tiêu thụ tài nguyên CPU một cách tích cực, thay đổi trạng thái của chúng để phản ứng với các luồng khác, nhưng không thể hoàn thành tác vụ.

### Hiển thị Volatile (Volatile Visibility)
Nếu không có `volatile`, các cập nhật được thực hiện đối với một biến bởi Luồng A có thể được lưu vào bộ đệm/thanh ghi CPU và không hiển thị đối với Luồng B đang đọc từ bộ nhớ chính.
`volatile` đảm bảo **tính hiển thị (visibility)** và **thứ tự (ordering)** (ngăn chặn JVM sắp xếp lại các chỉ thị xung quanh biến đó). Nó không đảm bảo tính nguyên tử.

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

### Các lớp nguyên tử và So sánh và tráo đổi (Atomic Classes and Compare-And-Swap - CAS)
Các lớp nguyên tử sử dụng các chỉ thị CPU không khóa (như `CMPXCHG` trên x86) để thực hiện các chu kỳ cập nhật nguyên tử.
* **Thao tác CAS**: Nhận giá trị mong đợi và giá trị mới. Chỉ cập nhật nếu giá trị hiện tại bằng giá trị mong đợi. Trả về true nếu thành công; ngược lại, lặp lại và thử lại.

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

## Tình huống nghiên cứu: Thứ tự khóa khi chuyển khoản ngân hàng (Case Study: Bank Account Transfer Lock Ordering)

### Vấn đề (Problem)
Trong một ứng dụng ngân hàng, nếu Khách hàng A chuyển tiền cho Khách hàng B trong khi Khách hàng B đồng thời chuyển tiền cho Khách hàng A, một sự bế tắc (deadlock) có thể xảy ra vì Luồng 1 khóa A rồi đến B, trong khi Luồng 2 khóa B rồi đến A.

### Giải pháp (Solution)
Thiết lập một thứ tự khóa nhất quán bằng cách sử dụng mã băm (hash codes) hoặc khóa tài khoản duy nhất.
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

## Các sai lầm thường gặp (Common Mistakes)

### 1. Giả định rằng volatile làm cho count++ trở nên an toàn luồng
`count++` là một thao tác hỗn hợp gồm ba bước: đọc, cộng 1, ghi lại. Khai báo `count` là `volatile` đảm bảo các luồng khác nhìn thấy việc ghi này, nhưng không ngăn được việc một luồng khác xen vào giữa các bước đọc và ghi.
```java
// BUG: Thread-unsafe
volatile int count = 0;
public void add() { count++; }
```

### 2. Lồng các khóa mà không có thứ tự cố định (Nesting Locks Without Fixed Order)
Lồng các khóa trên các tài nguyên được truyền động dưới dạng đối số là nguyên nhân hàng đầu gây ra deadlock trong môi trường sản xuất (production).

## Tại sao Deadlock xảy ra và cách tránh chúng (Why Deadlocks Occur and How to Avoid Them)

Một bế tắc (deadlock) xảy ra trong Java khi hai hoặc nhiều luồng bị chặn vĩnh viễn, mỗi luồng chờ đợi một khóa đang được nắm giữ bởi một luồng khác. Để deadlock xảy ra, bốn điều kiện Coffman phải đồng thời xảy ra: Loại trừ tương hỗ (chỉ một luồng có thể giữ tài nguyên tại một thời điểm), Giữ và Chờ (một luồng đang giữ tài nguyên có thể yêu cầu thêm tài nguyên mới), Không cướp đoạt (tài nguyên không thể bị tước đoạt cưỡng chế từ luồng), và Chờ đợi vòng tròn (tồn tại một chuỗi kín các luồng trong đó mỗi luồng giữ một tài nguyên mà luồng tiếp theo cần). Tác nhân kích hoạt phổ biến nhất là việc lồng các khóa theo các thứ tự không nhất quán. Ví dụ, nếu Luồng A giữ Khóa 1 và yêu cầu Khóa 2, trong khi Luồng B giữ Khóa 2 và yêu cầu Khóa 1, cả hai luồng sẽ rơi vào trạng thái bị chặn vĩnh viễn. Chúng ta có thể phá vỡ điều kiện Chờ đợi vòng tròn bằng cách áp đặt thứ tự giành khóa nghiêm ngặt hoặc bằng cách sử dụng phương thức `tryLock()` với thời gian chờ (timeout) của API Lock để tránh chờ đợi vô hạn.

### Mô hình tư duy: Chu kỳ bế tắc (Mental Model: Deadlock Cycle)
```
   ┌──────────┐  Holds  ┌──────────┐
   │ Thread A │ ──────► │  Lock 1  │
   └──────────┘         └──────────┘
        ▲                     │
     Waits For            Waits For
        │                     ▼
   ┌──────────┐  Holds  ┌──────────┐
   │  Lock 2  │ ◄────── │ Thread B │
   └──────────┘         └──────────┘
```

### Ví dụ mã nguồn (Code Example)
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

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Lồng các khóa trên tài nguyên theo các thứ tự khác nhau
  → Luồng A giành được Khóa 1, Luồng B giành được Khóa 2
  → Luồng A yêu cầu Khóa 2, Luồng B yêu cầu Khóa 1
  → Cả hai không thể tiến hành
  → Bốn điều kiện deadlock được đáp ứng
  → Hiệu suất sử dụng CPU giảm về 0 đối với các luồng này
  → Việc thực thi luồng bị đóng băng
  → Deadlock xảy ra
  → Được phá vỡ bằng thứ tự khóa hoặc dùng `tryLock` dựa trên thời gian chờ (timeout).
```

