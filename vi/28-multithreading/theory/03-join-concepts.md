# Đa luồng (Multithreading) - Phần 3

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm các nguyên ngữ đồng bộ luồng (`join`), báo hiệu (`interrupt`), luồng daemon và hành vi thực thi. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, thay vì chỉ là từ vựng rời rạc.

## Phạm vi đề mục (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `join` | Một phương thức thể hiện (`thread.join()`) chặn luồng gọi cho đến khi luồng mục tiêu kết thúc. |
| `yield` | Một phương thức tĩnh (`Thread.yield()`) gợi ý bộ lập lịch tạm dừng luồng hiện tại để cho phép các luồng khác chạy. Bộ lập lịch có quyền bỏ qua gợi ý này. |
| `interrupt` | Cơ chế thông báo cho một luồng dừng những gì nó đang làm. Nó thiết lập trạng thái bị ngắt của luồng và đánh thức các luồng đang bị chặn trong các phương thức như `sleep()` hoặc `wait()`. |
| `Daemon thread` | Một luồng chạy nền (như dọn rác garbage collection) không giữ cho JVM tiếp tục chạy. JVM sẽ thoát khi chỉ còn lại các luồng daemon. |
| `User thread` | Một luồng tiêu chuẩn (chẳng hạn như luồng main). JVM tiếp tục chạy khi còn ít nhất một luồng người dùng hoạt động. |
| `Thread priority` | Một gợi ý dạng số (1 đến 10) gửi đến bộ lập lịch luồng của OS. Hành vi của nó phụ thuộc rất lớn vào nền tảng và không nên dựa vào đó để đảm bảo tính chính xác của chương trình. |
| `Race condition` | Một lỗi đồng thời khi kết quả của chương trình phụ thuộc vào sự đan xen không thể dự đoán trước của các bước thực thi từ nhiều luồng. |
| `Critical section` | Một khối mã truy cập vào một tài nguyên khả biến dùng chung và tuyệt đối không được truy cập đồng thời bởi nhiều luồng. |

## Ghi chú chi tiết (Detailed Notes)

### Thread Join
`join()` được sử dụng để điều phối việc kết thúc luồng. Luồng gọi tạm dừng cho đến khi luồng mục tiêu hoàn thành việc thực thi.
```java
public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            System.out.println("Worker done.");
        });

        worker.start();
        System.out.println("Waiting for worker...");
        worker.join(); // Luồng main bị chặn tại đây cho đến khi worker kết thúc
        System.out.println("All work finished.");
    }
}
```

### Thread Interrupt
Cơ chế ngắt (Interrupt) mang tính hợp tác (cooperative). Gọi `thread.interrupt()` không kết thúc luồng ngay lập tức; nó chỉ thiết lập một cờ bị ngắt (interrupt flag).
* Nếu một luồng đang bị chặn trong `sleep()`, `wait()`, hoặc `join()`, nó sẽ ném ra `InterruptedException` và **xóa** cờ bị ngắt của nó.
* Nếu một luồng đang thực thi các hoạt động CPU bình thường, nó phải định kỳ kiểm tra cờ của mình bằng cách sử dụng `Thread.currentThread().isInterrupted()`.

```java
public class InterruptDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                // Thực hiện tác vụ tiêu tốn CPU
            }
            System.out.println("Worker stopped via interrupt.");
        });

        worker.start();
        Thread.sleep(500);
        worker.interrupt(); // Báo hiệu dừng worker
    }
}
```

### Luồng Daemon so với Luồng Người dùng (Daemon vs User Threads)
Theo mặc định, các luồng mới được tạo kế thừa trạng thái daemon từ luồng đã tạo ra nó. Bạn có thể thay đổi trạng thái này bằng phương thức `thread.setDaemon(boolean)`.
* **Quan trọng**: Bạn phải gọi `setDaemon()` **trước khi** khởi chạy luồng. Gọi phương thức này trên một luồng đang chạy sẽ ném ra ngoại lệ `IllegalThreadStateException`.

```java
public class DaemonDemo {
    public static void main(String[] args) {
        Thread daemon = new Thread(() -> {
            while (true) {
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        });
        daemon.setDaemon(true); // Bắt buộc phải đặt trước khi start
        daemon.start();
        
        System.out.println("Main thread ending. JVM will exit despite daemon running.");
    }
}
```

### Các đoạn găng và điều kiện tranh đoạt (Critical Sections and Race Conditions)
Điều kiện tranh đoạt (race condition) xảy ra khi nhiều luồng đồng thời đọc và ghi vào một biến dùng chung mà không có sự đồng bộ hóa.
```java
class Counter {
    private int count = 0;

    public void increment() {
        count++; // ĐOẠN GĂNG (CRITICAL SECTION). Không nguyên tử: đọc, sửa, ghi.
    }
    
    public int getCount() { return count; }
}
```

---

## Nghiên cứu tình huống: Mẫu điều phối luồng (Worker Coordinator Pattern)

### Bài toán (Problem)
Một hệ thống báo cáo cần lấy dữ liệu từ ba API bên ngoài một cách đồng thời. Khi tất cả các API trả về dữ liệu, hệ thống sẽ tổng hợp báo cáo cuối cùng.

### Giải pháp (Solution)
Sử dụng `join()` để điều phối các luồng làm việc.
```java
import java.util.ArrayList;
import java.util.List;

public class ReportCoordinator {
    public static void main(String[] args) throws InterruptedException {
        List<Thread> workers = new ArrayList<>();
        
        // Khởi chạy 3 luồng làm việc
        for (int i = 1; i <= 3; i++) {
            final int id = i;
            Thread t = new Thread(() -> {
                System.out.println("Worker " + id + " fetching data...");
                try { Thread.sleep(1000 * id); } catch (InterruptedException e) {}
            });
            workers.add(t);
            t.start();
        }
        
        // Đợi tất cả các luồng làm việc hoàn thành
        for (Thread t : workers) {
            t.join();
        }
        
        System.out.println("All worker data collected. Compiling report.");
    }
}
```

---

## Các lỗi thường gặp (Common Mistakes)

### 1. Nuốt ngoại lệ InterruptedException (Swallowing InterruptedException)
Việc nuốt ngoại lệ `InterruptedException` sẽ xóa trạng thái bị ngắt của luồng, có nghĩa là mã ở cấp cao hơn sẽ không biết rằng luồng được yêu cầu dừng lại.
```java
// SAI
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    // Bị nuốt thầm lặng và bỏ qua
}

// ĐÚNG: Khôi phục lại cờ bị ngắt để người gọi được biết
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    Thread.currentThread().interrupt(); 
}
```

### 2. Gọi setDaemon() trên một luồng đang chạy (Calling setDaemon() on a Running Thread)
```java
Thread t = new Thread(() -> {});
t.start();
t.setDaemon(true); // Ném ra IllegalThreadStateException
```

### 3. Dựa dẫm vào Thread.yield() hoặc độ ưu tiên luồng để đảm bảo tính đúng đắn (Relying on Thread.yield() or Thread Priorities for Correctness)
Việc lập lịch luồng phụ thuộc hoàn toàn vào hệ điều hành. Đặc tả JVM không đưa ra bất kỳ sự đảm bảo nào về cách các mức ưu tiên được ánh xạ sang các mức ưu tiên của hệ điều hành hoặc cách `yield()` hoạt động. Mã nguồn dựa vào chúng để đồng bộ hóa là một thiết kế bị lỗi.

## Tại sao join() chặn Luồng gọi (Why join() Blocks the Calling Thread)

Việc điều phối hoàn thành luồng thông qua `join()` được xây dựng trực tiếp trên cơ chế báo hiệu `wait-and-notify` nguyên thủy của JVM. Khi một luồng gọi (Luồng A) gọi `threadB.join()`, Luồng A phải đi vào một khối synchronized bị khóa nội bộ trên chính thực thể đối tượng `threadB`. Bên trong ngữ cảnh synchronized này, JVM kiểm tra trạng thái của Luồng B bằng một vòng lặp chứa điều kiện `threadB.isAlive()`. Nếu Luồng B vẫn đang thực thi, JVM sẽ gọi `threadB.wait(0)` thay mặt cho Luồng A, khiến Luồng A giải phóng khóa và đi vào trạng thái `WAITING`. Khi Luồng B kết thúc thực thi và chuẩn bị chuyển sang trạng thái `TERMINATED`, môi trường chạy của JVM sẽ thực thi một lời gọi gốc tương đương `lock.notifyAll()` trên đối tượng giám sát (monitor object) `threadB`. Thông báo này đánh thức Luồng A, cho phép nó lấy lại khóa giám sát đối tượng, thoát khỏi vòng lặp vì `isAlive()` bây giờ là false, và tiếp tục thực thi phần mã còn lại của nó.

### Mô hình tư duy (Mental Model)
```text
Luồng A (Người gọi)               Luồng B (Mục tiêu)             Trình chạy JVM
    |                                 |                              |
    |-- gọi threadB.join()            |                              |
    |-- lấy khóa trên threadB         |                              |
    |-- lặp kiểm tra threadB.isAlive()|                              |
    |-- gọi threadB.wait()            |                              |
    |   (Giải phóng khóa, vào WAITING) |                              |
    :                                 |                              |
    :                                 |-- hoàn thành thực thi        |
    :                                 |----------------------------->|
    :                                 |                              |-- gọi native
    :                                 |                              |   threadB.notifyAll()
    |<-- thức dậy (chuyển sang RUNNABLE)<------------------------------|
    |-- lấy khóa trên threadB         |                              |
    |-- vòng lặp isAlive() trả về false|                              |
    |-- thoát khỏi phương thức join()  |                              |
    v                                 v                              v
```

### Ví dụ mã nguồn (Code Example)
```java
public class JoinMechanism {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        });
        
        long start = System.currentTimeMillis();
        worker.start();
        
        System.out.println("Main thread joining worker...");
        worker.join(); // Chặn luồng main bằng cơ chế wait/notify
        
        System.out.println("Worker joined in " + (System.currentTimeMillis() - start) + " ms");
    }
}
/*
Đầu ra:
Main thread joining worker...
Worker joined in 505 ms
*/
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
1. Luồng A gọi threadB.join()
  → Luồng A lấy khóa giám sát trên đối tượng threadB.
```

2. Luồng A nhận thấy threadB.isAlive() là true &rarr; Luồng A gọi threadB.wait(), chuyển sang WAITING.
3. Luồng B hoàn thành thực thi &rarr; JVM kích hoạt notifyAll() một cách tự nhiên trên đối tượng giám sát threadB.
4. Luồng A thức dậy &rarr; Luồng A đánh giá lại isAlive() là false và thoát khỏi join().
