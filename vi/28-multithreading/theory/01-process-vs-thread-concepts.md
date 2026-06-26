# Đa luồng (Multithreading) - Phần 1

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm các nguyên lý cơ bản về việc tạo luồng, các trạng thái vòng đời và quản lý luồng. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, thay vì chỉ là từ vựng rời rạc.

## Phạm vi đề mục (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `Process vs Thread` | Một tiến trình (process) là một môi trường thực thi cô lập với không gian bộ nhớ riêng; một luồng (thread) là một đường dẫn thực thi nhẹ trong một tiến trình chia sẻ bộ nhớ với các luồng khác thuộc cùng tiến trình đó. |
| `Tạo luồng bằng cách: (Create thread using:)` | Các luồng có thể được tạo bằng cách kế thừa (Inheritance) lớp con `Thread`, triển khai `Runnable`, hoặc triển khai `Callable` (các nhiệm vụ có giá trị trả về). |
| `extends Thread` | Kế thừa từ `java.lang.Thread` và ghi đè `run()`. Hạn chế việc kế thừa lớp do quy tắc đơn kế thừa của Java. |
| `implements Runnable` | Triển khai giao diện chức năng `Runnable` (với phương thức `run()`), tách biệt logic nhiệm vụ khỏi việc thực thi. |
| `implements Callable` | Triển khai giao diện chức năng `Callable<V>` (với phương thức `call()`), trả về kết quả và có thể ném ra ngoại lệ có kiểm tra (checked exception). |
| `ExecutorService` | Một công cụ quản lý luồng cấp cao từ `java.util.concurrent` quản lý một nhóm các luồng làm việc (worker threads) và phân tách việc gửi nhiệm vụ khỏi việc thực thi. |
| `Lifecycle of Thread` | Các trạng thái một luồng có thể chuyển đổi qua lại: `NEW`, `RUNNABLE`, `BLOCKED`, `WAITING`, `TIMED_WAITING`, và `TERMINATED`. |
| `New` | Trạng thái của một luồng vừa được khởi tạo (`new Thread()`) nhưng phương thức `start()` của nó chưa được gọi. |

## Ghi chú chi tiết (Detailed Notes)

### Tiến trình so với Luồng (Process vs Thread)
* **Tiến trình (Process)**: Một đơn vị phân bổ tài nguyên của hệ điều hành. Mỗi tiến trình có không gian địa chỉ, bộ nhớ và các thẻ quản lý tệp (file handles) riêng. Giao tiếp giữa các tiến trình (IPC) rất nặng và chậm.
* **Luồng (Thread)**: Một đường dẫn thực thi bên trong một tiến trình, thường được gọi là tiến trình nhẹ (lightweight process). Các luồng chia sẻ bộ nhớ của tiến trình (heap, vùng phương thức - method area), nhưng mỗi luồng có một ngăn xếp thực thi (execution stack) và bộ nhớ biến cục bộ (local variable memory) riêng biệt.
* **Khác biệt chính**: Việc chia sẻ bộ nhớ làm cho giao tiếp giữa các luồng cực kỳ nhanh chóng, nhưng lại mang đến các rủi ro hỏng dữ liệu (điều kiện tranh đoạt - race conditions).

### Các cơ chế tạo luồng (Thread Creation Mechanisms)

#### 1. Kế thừa Thread (Extends Thread)
Kế thừa từ lớp `Thread` và ghi đè phương thức `run()` của nó.
```java
class CustomThread extends Thread {
    @Override
    public void run() {
        System.out.println("Running in thread: " + Thread.currentThread().getName());
    }
}

public class ThreadDemo {
    public static void main(String[] args) {
        CustomThread t = new CustomThread();
        t.start(); // Bắt đầu thực thi trên một luồng mới
    }
}
```

#### 2. Triển khai Runnable (Implements Runnable)
Triển khai giao diện chức năng `Runnable` và truyền nó vào một thể hiện của `Thread`. Đây là cách tiếp cận được ưa chuộng hơn vì nó bảo toàn khả năng kế thừa lớp khác cho lớp hiện tại.
```java
class Task implements Runnable {
    @Override
    public void run() {
        System.out.println("Task executing in: " + Thread.currentThread().getName());
    }
}

public class RunnableDemo {
    public static void main(String[] args) {
        Thread t = new Thread(new Task());
        t.start();
    }
}
```

#### 3. Triển khai Callable (Implements Callable)
`Callable<V>` tương tự như `Runnable` nhưng trả về một giá trị và có thể ném ra một ngoại lệ có kiểm tra. Nó phải được chạy bằng cách sử dụng một executor hoặc bọc trong một `FutureTask`.
```java
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

class CalculationTask implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        Thread.sleep(100);
        return 42;
    }
}

public class CallableDemo {
    public static void main(String[] args) throws Exception {
        FutureTask<Integer> futureTask = new FutureTask<>(new CalculationTask());
        Thread t = new Thread(futureTask);
        t.start();
        
        // Chặn và lấy kết quả
        Integer result = futureTask.get();
        System.out.println("Result: " + result); // In ra 42
    }
}
```

### ExecutorService
Quản lý các luồng thủ công là không hiệu quả. `ExecutorService` tập hợp các luồng (thread pool) và quản lý vòng đời của chúng.
```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        executor.submit(() -> System.out.println("Task 1 in " + Thread.currentThread().getName()));
        executor.submit(() -> System.out.println("Task 2 in " + Thread.currentThread().getName()));
        
        executor.shutdown(); // Bắt buộc phải tắt các executor để giải phóng luồng!
    }
}
```

### Vòng đời của luồng và trạng thái NEW (Thread Lifecycle and the NEW State)
Một luồng ở trạng thái `NEW` ngay sau khi được khởi tạo, trước khi phương thức `start()` được gọi. Ở giai đoạn này, nó tồn tại thuần túy như một đối tượng Java; chưa có bất kỳ tài nguyên luồng nào của hệ điều hành được phân bổ.

---

## Nghiên cứu tình huống: Kế thừa Thread so với Triển khai Runnable (Case Study: Extends Thread vs Implements Runnable)

### Bài toán (Problem)
Một nhóm phát triển muốn chạy một nhiệm vụ thăm dò mạng đồng thời. Họ kế thừa lớp con từ `Thread` để triển khai:
```java
public class Poller extends Thread {
    public void run() { /* logic thăm dò */ }
}
```
Sau đó, họ cần lớp `Poller` này kế thừa các khả năng tương tác cơ sở dữ liệu từ một lớp `BaseDatabaseService`. Vì Java chỉ hỗ trợ đơn kế thừa lớp, họ không thể kế thừa đồng thời từ cả `Thread` và `BaseDatabaseService`.

### Giải pháp (Solution)
Tái cấu trúc (refactor) để triển khai `Runnable` thay thế. Điều này giúp logic của nhiệm vụ tách biệt khỏi cơ chế thực thi luồng:
```java
public class Poller extends BaseDatabaseService implements Runnable {
    @Override
    public void run() { /* logic thăm dò */ }
}

// Thực thi
Thread thread = new Thread(new Poller());
thread.start();
```

---

## Các lỗi thường gặp (Common Mistakes)

### 1. Gọi .run() thay vì .start() (Calling .run() instead of .start())
Gọi trực tiếp `run()` không tạo ra một luồng mới. Nó chỉ đơn giản là thực thi phương thức `run()` trong luồng gọi một cách đồng bộ.
```java
Thread t = new Thread(() -> System.out.println(Thread.currentThread().getName()));
t.run(); // In ra "main" (không phải tên luồng như Thread-0)
t.start(); // Đúng: In ra "Thread-0"
```

### 2. Khởi chạy lại một Luồng (Re-starting a Thread)
Một luồng chỉ có thể được bắt đầu một lần. Cố gắng khởi chạy một luồng đã khởi chạy hoặc đã kết thúc sẽ ném ra ngoại lệ thời gian chạy `IllegalThreadStateException`.
```java
Thread t = new Thread(() -> {});
t.start();
t.start(); // Ném ra IllegalThreadStateException
```

### 3. Quên đóng ExecutorService (Forgetting to Shut Down an ExecutorService)
Một `ExecutorService` tạo ra các luồng không phải daemon (non-daemon threads) theo mặc định. Nếu bạn không gọi `shutdown()`, JVM sẽ tiếp tục chạy ngay cả sau khi phương thức `main` kết thúc.

## Tại sao Tiến trình khác biệt so với Luồng (Why Processes Differ from Threads)

Ở cấp độ hệ điều hành, một tiến trình đại diện cho một bối cảnh thực thi cô lập chứa không gian địa chỉ, các thẻ quản lý tệp, các bộ mô tả socket, và các mã thông báo bảo mật riêng biệt. Ngược lại, một luồng là một đường dẫn thực thi nhẹ nằm trong một tiến trình cha. Máy ảo Java (JVM) phản ánh mô hình hệ điều hành này trong cấu trúc phân bổ bộ nhớ của nó. Khi một luồng mới được tạo ra, JVM phân bổ một ngăn xếp thực thi (runtime stack) riêng và thanh ghi con trỏ chương trình (PC - program counter) cho luồng cụ thể đó, đảm bảo trạng thái biến cục bộ và các hướng dẫn thực thi được cô lập. Tuy nhiên, tất cả các luồng trong một tiến trình JVM đơn lẻ đều chia sẻ các vùng Heap và Metaspace chung, cho phép truy cập bộ nhớ trực tiếp và giao tiếp liên luồng cực kỳ nhanh chóng. Việc truy cập dùng chung này loại bỏ chi phí CPU của các giao thức truyền thông liên tiến trình (IPC), nhưng nó lại đưa vào rủi ro hỏng dữ liệu, điều kiện tranh đoạt (race conditions) và các lỗi hiển thị dữ liệu (visibility errors) khi nhiều luồng đồng thời ghi vào cùng một vị trí bộ nhớ.

### Mô hình tư duy (Mental Model)
```text
+-----------------------------------------------------------+
| OS PROCESS (Không gian địa chỉ cô lập, File Handles, v.v.)|
|   +-----------------------------------------------------+   |
|   | Bộ nhớ thể hiện JVM (JVM Instance Memory)           |   |
|   | Heap dùng chung (Objects) & Metaspace (Class Metadata)  |   |
|   |  [Object A] <--------------+-------------+          |   |
|   +----------------------------|-------------|----------+   |
|   | Thread 1 Stack & PC  | Thread 2 Stack & PC          |   |
|   |  [Local Variables 1] |  [Local Variables 2]         |   |
|   +----------------------+------------------------------+   |
+-----------------------------------------------------------+
```

### Ví dụ mã nguồn (Code Example)
```java
// Một lớp chạy được thể hiện việc chia sẻ bộ nhớ luồng so với cô lập tiến trình
public class ProcessVsThread {
    private static int sharedCounter = 0; // Bộ nhớ dùng chung trong Heap

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) sharedCounter++;
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) sharedCounter++;
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        // Đầu ra có thể nhỏ hơn 2000 do truy cập bộ nhớ dùng chung không đồng bộ!
        System.out.println("Shared Counter: " + sharedCounter);
    }
}
/*
Đầu ra khả dĩ:
Shared Counter: 1984
*/
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
1. Hệ điều hành chạy tiến trình JVM
  → OS phân bổ không gian địa chỉ ảo cô lập và tài nguyên.
```

2. JVM tạo ra các luồng Java &rarr; Các luồng dùng chung Heap/Metaspace của JVM nhưng nhận các thanh ghi Stack/PC riêng biệt.
3. Nhiều luồng truy cập các đối tượng trên Heap &rarr; Giao tiếp luồng cực kỳ nhanh chóng mà không cần chi phí quản lý của IPC.
4. Ghi song song không đồng bộ xảy ra &rarr; Các chỉ thị CPU bị đan xen làm hỏng trạng thái dùng chung (Race Condition).
