# Đa luồng (Multithreading) - Phần 2

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm các trạng thái thực thi luồng, lập lịch, và các hoạt động cơ bản như khởi chạy (start), thực thi (run), và tạm dừng (sleep). Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, thay vì chỉ là từ vựng rời rạc.

## Phạm vi đề mục (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `Runnable` (Thread State) | Trạng thái mà một luồng đang thực thi hoặc sẵn sàng/đủ điều kiện để thực thi, chờ đợi bộ lập lịch luồng của hệ điều hành (OS thread scheduler) phân bổ thời gian CPU. |
| `Running` | Trạng thái phụ mang tính khái niệm của `RUNNABLE` khi các chỉ thị của luồng đang được thực thi tích cực trên một nhân CPU. Java ánh xạ cả hai trạng thái sẵn sàng và đang chạy vào `Thread.State.RUNNABLE`. |
| `Blocked` | Trạng thái của một luồng đang chờ để có được khóa giám sát đối tượng (cho một khối/phương thức `synchronized`). |
| `Waiting` | Trạng thái của một luồng đang chờ vô thời hạn để một luồng khác thực hiện một hành động cụ thể (thông qua `Object.wait()` hoặc `Thread.join()`). |
| `Timed Waiting` | Trạng thái của một luồng đang chờ trong một khoảng thời gian giới hạn (thông qua `Thread.sleep()`, `Object.wait(timeout)`, hoặc `Thread.join(timeout)`). |
| `Terminated` | Trạng thái của một luồng đã hoàn thành thực thi (bình thường hoặc do ném ra một ngoại lệ không được xử lý). |
| `start() vs run()` | Phương thức `start()` phân bổ tài nguyên OS và lên lịch cho luồng thực thi bất đồng bộ; `run()` thực thi mã nhiệm vụ một cách đồng bộ trong luồng hiện tại. |
| `sleep` | Một phương thức tĩnh (`Thread.sleep()`) tạm dừng thực thi luồng hiện tại trong một khoảng thời gian được chỉ định, giải phóng CPU nhưng **giữ lại** bất kỳ khóa nào đã có được. |

## Ghi chú chi tiết (Detailed Notes)

### Chi tiết các trạng thái luồng (Thread States in Detail)

Java định nghĩa các trạng thái của luồng trong enum `Thread.State`. Chúng ta có thể truy vấn trạng thái của một luồng thông qua `thread.getState()`.

```mermaid
graph TD
    NEW[NEW (Mới tạo)] -->|start| RUNNABLE[RUNNABLE (Sẵn sàng chạy)]
    RUNNABLE -->|chờ khóa| BLOCKED[BLOCKED (Bị chặn)]
    BLOCKED -->|đã có khóa| RUNNABLE
    RUNNABLE -->|wait, join| WAITING[WAITING (Đang chờ)]
    WAITING -->|notify, hoàn thành join| RUNNABLE
    RUNNABLE -->|sleep, wait có timeout| TIMED_WAITING[TIMED_WAITING (Chờ có thời hạn)]
    TIMED_WAITING -->|hết thời gian, được notify| RUNNABLE
    RUNNABLE -->|hoàn thành run| TERMINATED[TERMINATED (Đã kết thúc)]
```

#### 1. RUNNABLE
Luồng đang chạy hoặc đủ điều kiện để chạy.
```java
Thread t = new Thread(() -> {
    while (true) {
        // Thực thi tích cực
    }
});
t.start();
System.out.println("State: " + t.getState()); // In ra RUNNABLE
```

#### 2. BLOCKED
Xảy ra khi một luồng cố gắng đi vào một khối `synchronized` nhưng một luồng khác đã giữ khóa giám sát.
```java
public class BlockedDemo {
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Runnable r = () -> {
            synchronized (lock) {
                try { Thread.sleep(5000); } catch (InterruptedException e) {}
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();
        Thread.sleep(100); // Đảm bảo t1 lấy khóa trước
        t2.start();
        Thread.sleep(100);

        System.out.println("t2 state: " + t2.getState()); // In ra BLOCKED
    }
}
```

#### 3. WAITING và TIMED_WAITING (WAITING and TIMED_WAITING)
* `WAITING` được kích hoạt bằng cách gọi `Object.wait()` không có thời hạn (timeout) hoặc `Thread.join()`.
* `TIMED_WAITING` được kích hoạt bằng cách gọi `Thread.sleep(millis)`, `Object.wait(millis)`, hoặc `Thread.join(millis)`.
```java
Thread sleeper = new Thread(() -> {
    try { Thread.sleep(1000); } catch (InterruptedException e) {}
});
sleeper.start();
Thread.sleep(100); // Đảm bảo luồng bắt đầu ngủ
System.out.println("Sleeper state: " + sleeper.getState()); // In ra TIMED_WAITING
```

---

## Nghiên cứu tình huống: Phân tích trạng thái luồng dưới tác động của tranh chấp khóa (Case Study: Analyzing Thread States under Lock Contention)

### Bài toán (Problem)
Một hệ thống thương mại điện tử đang gặp phải thời gian phản hồi cực kỳ chậm ở chức năng thanh toán. Các bản ghi thông tin luồng (thread dumps) cho thấy có nhiều luồng đang xử lý thanh toán.

### Phân tích (Analysis)
Bằng cách in ra các trạng thái luồng, lập trình viên phát hiện:
* Luồng A (Thread-A) giữ khóa trên đối tượng giám sát dùng chung `Inventory` và ở trạng thái `TIMED_WAITING` (đang ngủ trong một cuộc gọi cơ sở dữ liệu bên trong khối synchronized).
* Các luồng B, C và D ở trạng thái `BLOCKED`, đang chờ để đi vào phương thức thanh toán.

### Bản minh họa mã nguồn (Code Demonstration)
```java
class Inventory {
    public synchronized void update() {
        try {
            // Giả lập ghi cơ sở dữ liệu chậm
            Thread.sleep(3000); 
        } catch (InterruptedException e) {}
    }
}
```
**Bài học**: Các khối synchronized không nên bọc các hoạt động I/O chặn (như cuộc gọi mạng hoặc cơ sở dữ liệu) vì bất kỳ luồng nào bị chặn sẽ giữ khóa, gây ra tình trạng tắc nghẽn dây chuyền cho các luồng khác.

---

## Các lỗi thường gặp (Common Mistakes)

### 1. Giả định rằng Thread.sleep() sẽ giải phóng khóa (Assuming Thread.sleep() Releases Locks)
Một luồng đang ngủ KHÔNG giải phóng các khóa giám sát của nó. Nếu một luồng ngủ bên trong một khối synchronized, không có luồng nào khác có thể đi vào khối synchronized đó.
```java
synchronized(lock) {
    Thread.sleep(5000); // LỖI: Giữ khóa trong 5 giây trong khi không làm gì cả
}
```
**Khắc phục**: Nếu bạn cần đợi một điều kiện và giải phóng khóa, hãy sử dụng `lock.wait()` thay vì `sleep()`.

### 2. Quên xử lý ngoại lệ InterruptedException (Forgetting to Handle InterruptedException)
`Thread.sleep()` ném ra `InterruptedException`, đây là một ngoại lệ có kiểm tra (checked exception). Nếu một luồng đang ngủ bị ngắt (interrupt), quá trình ngủ sẽ kết thúc ngay lập tức. Đừng bao giờ nuốt (swallow) ngoại lệ này mà không khôi phục lại trạng thái ngắt hoặc ném lại nó.
```java
// SAI
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    // Bị nuốt thầm lặng!
}

// ĐÚNG
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    Thread.currentThread().interrupt(); // Khôi phục lại trạng thái bị ngắt
}
```

## Tại sao Runnable/Callable lại được ưa chuộng hơn việc kế thừa lớp Thread (Why Runnable/Callable Is Preferred Over Extending Thread)

Quy tắc đơn kế thừa lớp của Java tạo ra một hạn chế nghiêm trọng về mặt kiến trúc khi kế thừa lớp `Thread`. Nếu một lớp kế thừa từ `Thread`, nó không thể kế thừa từ bất kỳ lớp cơ sở nào khác, điều này hạn chế khả năng mở rộng và tái sử dụng logic nghiệp vụ (business logic) bên trong các framework của doanh nghiệp. Hơn thế nữa, việc tạo lớp con của `Thread` vi phạm Nguyên tắc Đơn Nhiệm (Single Responsibility Principle) bằng cách kết hợp ngữ cảnh thực thi (luồng vật lý do OS quản lý) với chính nhiệm vụ tính toán. Bằng cách triển khai `Runnable` hoặc `Callable`, bạn tách biệt rõ ràng định nghĩa nhiệm vụ cốt lõi khỏi hạ tầng thực thi. Sự phân tách (decoupling) này cho phép các nhiệm vụ được gửi đến các hạ tầng thực thi hiện đại như nhóm luồng `ExecutorService`, được tái sử dụng trên các công cụ thực thi khác nhau, và dễ dàng được giả lập (mock) hoặc kiểm thử độc lập. Giao diện `Callable` đặc biệt cải tiến mẫu thiết kế này bằng cách cho phép các nhiệm vụ trả về kết quả tính toán một cách bất đồng bộ và truyền các ngoại lệ có kiểm tra lên ngăn xếp cuộc gọi, điều không thể thực hiện được với phương thức `run()` tiêu chuẩn trong lớp `Thread`.

### Mô hình tư duy (Mental Model)
```text
[Liên kết chặt chẽ (Kế thừa)]
+-----------------------------+
| CustomTask extends Thread   | ---> Khe kế thừa duy nhất đã bị chiếm!
|  - Logic điều khiển luồng   |
|  - Logic nhiệm vụ (run())   |
+-----------------------------+

[Liên kết lỏng lẻo (Thành phần)]
+----------------------+     +-----------------------+
|  Task (Runnable)     |     | Thread / Thread Pool  |
|  - Logic nhiệm vụ    |===> | - Cơ chế thực thi    |
+----------------------+     +-----------------------+
```

### Ví dụ mã nguồn (Code Example)
```java
import java.util.concurrent.*;

public class TaskDecoupling {
    public static void main(String[] args) throws Exception {
        Callable<String> task = () -> "Task executed by: " + Thread.currentThread().getName();
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(task);
        
        System.out.println(future.get());
        executor.shutdown();
    }
}
/*
Đầu ra:
Task executed by: pool-1-thread-1
*/
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
1. Mã nguồn triển khai Runnable/Callable
  → Logic nhiệm vụ được phân tách khỏi cơ chế thực thi.
```

2. Khe đơn kế thừa lớp vẫn mở &rarr; Lớp có thể kế thừa các tiện ích cơ sở dữ liệu, mạng hoặc framework.
3. Các nhiệm vụ phân tách được gửi đến ExecutorService &rarr; JVM tránh được chi phí tự tạo các luồng thủ công.
4. Callable truyền tải kết quả và ngoại lệ &rarr; Luồng gọi xử lý các kết quả bất đồng bộ một cách an toàn.

## Tại sao start() lại bắt buộc để tạo ra một Luồng mới (Why start() Is Required to Spawn a Thread)

Việc gọi trực tiếp phương thức `run()` trên một thể hiện `Thread` sẽ thực thi các chỉ thị nhiệm vụ một cách đồng bộ trong ngăn xếp cuộc gọi (call stack) của luồng đang gọi, không thể tạo ra một luồng chạy đồng thời. Để đạt được việc thực thi đa luồng thực sự, bạn phải gọi `start()`, phương thức này kích hoạt một chuỗi các hoạt động của JVM gốc và hệ điều hành. Gọi `start()` thực hiện kiểm tra trạng thái để đảm bảo luồng ở trạng thái `NEW`, sau đó gọi phương thức gốc nội bộ của JVM là `start0()`. Mối liên kết gốc này yêu cầu bộ lập lịch luồng của hệ điều hành phân bổ một cấu trúc luồng cấp nền tảng mới và thiết lập ngăn xếp thực thi riêng của nó. Khi hệ điều hành lên lịch cho luồng mới này, JVM sẽ gọi phương thức `run()` một cách bất đồng bộ bên trong ngữ cảnh luồng mới được tạo. Việc cố gắng gọi `start()` nhiều lần là không hợp lệ vì máy trạng thái (state machine) nội bộ của luồng đã chuyển khỏi trạng thái `NEW`; làm như vậy sẽ lập tức ném ra ngoại lệ `IllegalThreadStateException`.

### Mô hình tư duy (Mental Model)
```text
[Gọi trực tiếp run()]
Ngăn xếp luồng gọi: [main()] -> [run()]   (Đồng bộ, chung ngăn xếp)

[Gọi phương thức start()]
Ngăn xếp luồng gọi: [main()] -> [start()] -> [native start0()]
                                                    |
                                                    v (Hệ điều hành tạo luồng)
Ngăn xếp luồng mới:                              [run()] (Bất đồng bộ)
```

### Ví dụ mã nguồn (Code Example)
```java
public class StartVsRun {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("Executing inside: " + Thread.currentThread().getName());
        });

        System.out.println("Calling run() directly:");
        thread.run(); // Thực thi đồng bộ trên ngăn xếp main

        System.out.println("Calling start():");
        thread.start(); // Tạo luồng mới bất đồng bộ
    }
}
/*
Đầu ra:
Calling run() directly:
Executing inside: main
Calling start():
Executing inside: Thread-0
*/
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
1. Người gọi gọi start() trên một Thread
  → JVM thực hiện kiểm tra trạng thái để đảm bảo luồng là NEW.
```

2. JVM gọi phương thức gốc start0() &rarr; Bộ lập lịch luồng của OS phân bổ cấu trúc luồng nền tảng.
3. OS cấu hình một ngăn xếp cuộc gọi riêng mới &rarr; Trạng thái luồng chuyển từ NEW sang RUNNABLE.
4. OS lên lịch cho luồng chạy trên CPU &rarr; Phương thức run() của JVM thực thi bất đồng bộ trên ngăn xếp mới.
