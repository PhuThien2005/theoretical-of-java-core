# Đa luồng (Multithreading) - Phần 3

## Nội Dung Khái Quát (Outline Coverage)

## Ghi Chú Chi Tiết (Detailed Notes)

### Phương thức Thread Join
`join()` được sử dụng để điều phối việc kết thúc luồng. Luồng gọi sẽ tạm dừng cho đến khi luồng đích hoàn thành việc thực thi.
```java
public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            System.out.println("Worker done.");
        });

        worker.start();
        System.out.println("Waiting for worker...");
        worker.join(); // Main thread blocks here until worker finishes
        System.out.println("All work finished.");
    }
}
```

### Phương thức Thread Interrupt
Các ngắt mang tính chất hợp tác. Việc gọi `thread.interrupt()` không kết thúc luồng ngay lập tức; nó chỉ đơn thuần thiết lập một cờ ngắt (Interrupt Flag).
* Nếu một luồng đang bị nghẽn trong `sleep()`, `wait()`, hoặc `join()`, nó sẽ ném ra `InterruptedException` và **xóa** cờ ngắt của mình.
* Nếu một luồng đang thực thi các hoạt động CPU thông thường, nó phải kiểm tra cờ của mình theo định kỳ bằng cách sử dụng `Thread.currentThread().isInterrupted()`.

```java
public class InterruptDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                // Perform CPU intensive task
            }
            System.out.println("Worker stopped via interrupt.");
        });

        worker.start();
        Thread.sleep(500);
        worker.interrupt(); // Signal worker to stop
    }
}
```

### Luồng Daemon và Luồng Người Dùng (Daemon vs User Threads)
Theo mặc định, các luồng mới được tạo sẽ kế thừa trạng thái daemon từ luồng tạo ra nó. Bạn có thể thay đổi điều này bằng cách sử dụng `thread.setDaemon(boolean)`.
* **Quan trọng**: Bạn phải gọi `setDaemon()` **trước khi** khởi chạy luồng. Việc gọi phương thức này trên một luồng đang chạy sẽ ném ra ngoại lệ `IllegalThreadStateException`.

```java
public class DaemonDemo {
    public static void main(String[] args) {
        Thread daemon = new Thread(() -> {
            while (true) {
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        });
        daemon.setDaemon(true); // Must be set before start
        daemon.start();
        
        System.out.println("Main thread ending. JVM will exit despite daemon running.");
    }
}
```

### Vùng Tới Hạn (Critical Sections) và Tình Trạng Tương Tranh (Race Conditions)
Tình trạng tương tranh xảy ra khi nhiều luồng cùng đọc và ghi vào một biến chia sẻ một cách đồng thời mà không có sự đồng bộ hóa (Synchronization).
```java
class Counter {
    private int count = 0;

    public void increment() {
        count++; // CRITICAL SECTION. Non-atomic: read, modify, write.
    }
    
    public int getCount() { return count; }
}
```

---

## Ví Dụ Thực Tế: Mô Hình Điều Phối Luồng Làm Việc (Worker Coordinator Pattern)

### Vấn đề
Một hệ thống báo cáo cần lấy dữ liệu đồng thời từ ba API bên ngoài. Sau khi tất cả các API trả về dữ liệu, hệ thống sẽ biên soạn báo cáo cuối cùng.

### Giải pháp
Sử dụng `join()` để điều phối các luồng làm việc.
```java
import java.util.ArrayList;
import java.util.List;

public class ReportCoordinator {
    public static void main(String[] args) throws InterruptedException {
        List<Thread> workers = new ArrayList<>();
        
        // Start 3 workers
        for (int i = 1; i <= 3; i++) {
            final int id = i;
            Thread t = new Thread(() -> {
                System.out.println("Worker " + id + " fetching data...");
                try { Thread.sleep(1000 * id); } catch (InterruptedException e) {}
            });
            workers.add(t);
            t.start();
        }
        
        // Wait for all workers to finish
        for (Thread t : workers) {
            t.join();
        }
        
        System.out.println("All worker data collected. Compiling report.");
    }
}
```

---

## Các Lỗi Thường Gặp (Common Mistakes)

### 1. Nuốt ngoại lệ InterruptedException (Swallowing InterruptedException)
Việc nuốt `InterruptedException` sẽ xóa trạng thái ngắt của luồng, điều này có nghĩa là mã nguồn cấp cao hơn sẽ không biết rằng luồng đã được yêu cầu dừng lại.
```java
// BAD
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    // Swallowed and ignored
}

// GOOD: Restore the interrupt flag so caller knows
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    Thread.currentThread().interrupt(); 
}
```

### 2. Gọi `setDaemon()` trên một Luồng Đang Chạy
```java
Thread t = new Thread(() -> {});
t.start();
t.setDaemon(true); // Throws IllegalThreadStateException
```

### 3. Dựa vào `Thread.yield()` hoặc Độ Ưu Tiên của Luồng để Đảm Bảo Tính Đúng Đắn
Việc lập lịch luồng phụ thuộc vào nền tảng. Đặc tả của JVM không đưa ra bất kỳ đảm bảo nào về cách các độ ưu tiên được ánh xạ tới các độ ưu tiên của hệ điều hành hoặc cách `yield()` hoạt động. Mã nguồn dựa vào chúng để đồng bộ hóa là mã nguồn dễ phát sinh lỗi.

## Tại sao join() Làm Nghẽn Luồng Gọi (Why join() Blocks the Calling Thread)

Việc điều phối hoàn thành luồng thông qua `join()` được xây dựng trực tiếp trên cơ chế truyền tín hiệu wait-and-notify cơ bản của JVM. Khi một luồng gọi (Luồng A) gọi `threadB.join()`, Luồng A phải đi vào một khối đồng bộ hóa được khóa nội bộ trên thực thể đối tượng `threadB`. Bên trong ngữ cảnh đồng bộ hóa này, JVM sẽ kiểm tra trạng thái của Luồng B bằng cách sử dụng một vòng lặp chứa điều kiện `threadB.isAlive()`. Nếu Luồng B vẫn đang thực thi, JVM sẽ gọi `threadB.wait(0)` thay mặt cho Luồng A, khiến Luồng A giải phóng khóa và đi vào trạng thái WAITING. Khi Luồng B kết thúc quá trình thực thi và chuẩn bị chuyển sang trạng thái TERMINATED, môi trường thực thi của JVM sẽ thực hiện một lệnh tương đương với `lock.notifyAll()` trên đối tượng giám sát (Monitor Object) `threadB`. Thông báo này sẽ đánh thức Luồng A, cho phép nó giành lại khóa giám sát đối tượng, thoát khỏi vòng lặp vì `isAlive()` bây giờ là false, và tiếp tục thực thi phần mã nguồn còn lại của mình.

### Mô hình Tư duy (Mental Model)
```text
Thread A (Caller)                 Thread B (Target)              JVM Runtime
    |                                 |                              |
    |-- calls threadB.join()          |                              |
    |-- acquires lock on threadB      |                              |
    |-- loops on threadB.isAlive()    |                              |
    |-- calls threadB.wait()          |                              |
    |   (Releases lock, enters WAITING)|                              |
    :                                 |                              |
    :                                 |-- completes execution        |
    :                                 |----------------------------->|
    :                                 |                              |-- natively calls
    :                                 |                              |   threadB.notifyAll()
    |<-- wakes up (moves to RUNNABLE) <------------------------------|
    |-- acquires lock on threadB      |                              |
    |-- isAlive() loop returns false  |                              |
    |-- exits join() method           |                              |
    v                                 v                              v
```

### Ví dụ Mã nguồn
```java
public class JoinMechanism {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        });
        
        long start = System.currentTimeMillis();
        worker.start();
        
        System.out.println("Main thread joining worker...");
        worker.join(); // Blocks main thread using wait/notify mechanism
        
        System.out.println("Worker joined in " + (System.currentTimeMillis() - start) + " ms");
    }
}
/*
Output:
Main thread joining worker...
Worker joined in 505 ms
*/
```

### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)
1. Luồng A gọi threadB.join() &rarr; Luồng A giành được khóa giám sát trên đối tượng threadB.
2. Luồng A thấy threadB.isAlive() là true &rarr; Luồng A gọi threadB.wait(), đi vào trạng thái WAITING.
3. Luồng B hoàn thành thực thi &rarr; JVM kích hoạt notifyAll() một cách tự nhiên trên đối tượng giám sát threadB.
4. Luồng A được đánh thức &rarr; Luồng A đánh giá lại isAlive() thành false và thoát khỏi join().

---
