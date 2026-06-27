# Đa Luồng (Multithreading) - Phần 1

## Mục Tiêu Học Tập

Tài liệu này trình bày các nguyên lý cơ bản về tạo luồng, các trạng thái vòng đời và quản lý luồng. Hãy nghiên cứu từng khái niệm dưới dạng một quy tắc Java thực tế, thay vì chỉ học từ vựng riêng lẻ.

## Nội Dung Tổng Quan

| Khái niệm | Điều cần biết |
| --- | --- |
| `Process vs Thread` | Một tiến trình (process) là một môi trường thực thi biệt lập có không gian bộ nhớ riêng; một luồng (thread) là một đường dẫn thực thi nhẹ bên trong một tiến trình và chia sẻ bộ nhớ với các luồng khác của cùng tiến trình đó. |
| `Create thread using:` | Các luồng có thể được tạo bằng cách tạo lớp con của `Thread`, triển khai `Runnable`, hoặc triển khai `Callable` (các tác vụ có giá trị trả về). |
| `extends Thread` | Kế thừa từ `java.lang.Thread` và ghi đè `run()`. Việc này hạn chế khả năng kế thừa lớp khác do quy tắc đơn kế thừa của Java. |
| `implements Runnable` | Triển khai giao diện chức năng `Runnable` (với phương thức `run()`), tách biệt logic tác vụ khỏi cơ chế thực thi. |
| `implements Callable` | Triển khai giao diện chức năng `Callable<V>` (với phương thức `call()`), trả về một kết quả và có thể ném các ngoại lệ được kiểm tra (checked exception). |
| `ExecutorService` | Một công cụ quản lý luồng cấp cao từ gói `java.util.concurrent` quản lý một bể chứa luồng (thread pool) và tách biệt việc gửi tác vụ khỏi việc thực thi. |
| `Lifecycle of Thread` | Các trạng thái mà một luồng có thể chuyển qua: `NEW`, `RUNNABLE`, `BLOCKED`, `WAITING`, `TIMED_WAITING` và `TERMINATED`. |
| `New` | Trạng thái của một luồng vừa được khởi tạo (`new Thread()`) nhưng phương thức `start()` của nó chưa được gọi. |

## Ghi Chú Chi Tiết

### Tiến trình so với Luồng (Process vs Thread)
* **Tiến trình (Process)**: Một đơn vị phân bổ tài nguyên của hệ điều hành. Mỗi tiến trình có không gian địa chỉ, bộ nhớ và các trình xử lý tệp (file handle) riêng. Việc giao tiếp giữa các tiến trình (IPC) rất nặng nề và chậm chạp.
* **Luồng (Thread)**: Một đường dẫn thực thi bên trong một tiến trình, thường được gọi là một tiến trình nhẹ. Các luồng chia sẻ bộ nhớ của tiến trình (vùng nhớ heap, vùng nhớ phương thức - method area), nhưng mỗi luồng có ngăn xếp thực thi (stack) riêng và bộ nhớ biến cục bộ riêng.
* **Khác biệt cốt lõi**: Chia sẻ bộ nhớ giúp giao tiếp giữa các luồng cực kỳ nhanh chóng, nhưng lại mang đến rủi ro hư hỏng dữ liệu (tình trạng tranh chấp - race condition).

### Các Cơ Chế Tạo Luồng

#### 1. Kế thừa Thread (Extends Thread)
Kế thừa từ lớp Thread và ghi đè phương thức run() của nó.
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
Triển khai giao diện chức năng `Runnable` và truyền nó vào một thể hiện của `Thread`. Đây là cách tiếp cận được ưu tiên hơn vì nó bảo toàn khả năng kế thừa lớp khác của Java (do quy tắc đơn kế thừa).
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
`Callable<V>` tương tự như `Runnable` nhưng trả về một giá trị và có thể ném ra các ngoại lệ được kiểm tra. Nó phải được chạy bằng một trình thực thi (executor) hoặc được bọc trong một `FutureTask`.
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
        
        // Chặn và nhận kết quả
        Integer result = futureTask.get();
        System.out.println("Result: " + result); // Prints 42
    }
}
```

### ExecutorService
Quản lý luồng thủ công là không hiệu quả. `ExecutorService` gom nhóm các luồng (thread pool) và quản lý vòng đời của chúng.
```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        executor.submit(() -> System.out.println("Task 1 in " + Thread.currentThread().getName()));
        executor.submit(() -> System.out.println("Task 2 in " + Thread.currentThread().getName()));
        
        executor.shutdown(); // Phải tắt trình thực thi (executor) để giải phóng các luồng!
    }
}
```

### Vòng Đời Của Luồng Và Trạng Thái NEW
Một luồng ở trạng thái `NEW` ngay sau khi tạo, trước khi `start()` được gọi. Ở giai đoạn này, nó thuần túy chỉ tồn tại dưới dạng một đối tượng Java; chưa có bất kỳ tài nguyên luồng nào của hệ điều hành được phân bổ.

---

## Ví Dụ Thực Tế: Kế Thừa Thread so với Triển Khai Runnable

### Vấn đề
Một nhóm phát triển muốn chạy một tác vụ thăm dò mạng (network polling) một cách đồng thời. Họ đã tạo lớp con kế thừa từ `Thread` để triển khai:
```java
public class Poller extends Thread {
    public void run() { /* polling logic */ }
}
```
Sau đó, họ cần lớp `Poller` này kế thừa các chức năng cơ sở dữ liệu từ lớp `BaseDatabaseService`. Vì Java chỉ hỗ trợ đơn kế thừa lớp, họ không thể kế thừa cùng lúc từ cả `Thread` và `BaseDatabaseService`.

### Giải pháp
Tái cấu trúc để triển khai `Runnable` thay thế. Việc này giúp tách biệt logic tác vụ khỏi cơ chế thực thi luồng:
```java
public class Poller extends BaseDatabaseService implements Runnable {
    @Override
    public void run() { /* polling logic */ }
}

// Execution
Thread thread = new Thread(new Poller());
thread.start();
```

---

## Sai Lầm Thường Gặp

### 1. Gọi `.run()` thay vì `.start()`
Gọi trực tiếp phương thức `run()` không tạo ra luồng mới. Nó chỉ đơn giản là thực thi phương thức `run()` trong luồng gọi hiện tại (một cách đồng bộ).
```java
Thread t = new Thread(() -> System.out.println(Thread.currentThread().getName()));
t.run(); // Prints "main" (not thread name like Thread-0)
t.start(); // Correct: Prints "Thread-0"
```

### 2. Khởi động lại một Luồng
Một luồng chỉ có thể được khởi động một lần. Cố gắng bắt đầu một luồng đã hoạt động hoặc đã kết thúc sẽ ném ra ngoại lệ tại thời điểm chạy.
```java
Thread t = new Thread(() -> {});
t.start();
t.start(); // Throws IllegalThreadStateException
```

### 3. Quên tắt ExecutorService
Theo mặc định, `ExecutorService` tạo ra các luồng không phải daemon (non-daemon thread). Nếu bạn không gọi `shutdown()`, JVM sẽ tiếp tục chạy ngay cả khi phương thức `main` đã kết thúc.

## Tại Sao Tiến Trình Khác Biệt Với Luồng

Ở cấp độ hệ điều hành, một tiến trình đại diện cho một ngữ cảnh thực thi biệt lập chứa không gian địa chỉ, trình xử lý tệp, trình mô tả socket (socket descriptor) và mã thông báo bảo mật (security token) của riêng nó. Ngược lại, một luồng là một đường dẫn thực thi nhẹ nằm trong một tiến trình cha. Máy ảo Java (JVM) phản ánh mô hình hệ điều hành này trong kiến trúc phân bổ bộ nhớ của nó. Khi một luồng mới được tạo ra, JVM sẽ phân bổ một ngăn xếp thực thi (runtime stack) và thanh ghi bộ đếm chương trình (program counter - PC) riêng cho luồng đó, đảm bảo các biến cục bộ và các hướng dẫn thực thi được độc lập. Tuy nhiên, tất cả các luồng của cùng một tiến trình JVM sẽ chia sẻ chung các vùng nhớ Heap và Metaspace, cho phép truy cập bộ nhớ trực tiếp và giao tiếp giữa các luồng cực kỳ nhanh chóng. Việc chia sẻ này loại bỏ chi phí CPU của các giao thức giao tiếp liên tiến trình (IPC), nhưng lại làm phát sinh các rủi ro về hư hỏng dữ liệu, tình trạng tranh chấp (race condition) và lỗi khả thị (visibility error) khi nhiều luồng cùng viết vào một vùng nhớ đồng thời.

### Mô Hình Tư Duy
```text
+-----------------------------------------------------------+
| TIẾN TRÌNH HỆ ĐIỀU HÀNH (OS PROCESS)                      |
| (Không gian địa chỉ biệt lập, Trình xử lý tệp, v.v.)       |
|   +-----------------------------------------------------+   |
|   | Bộ nhớ Thể hiện JVM                                 |   |
|   | Vùng nhớ Heap (Đối tượng) & Metaspace (Class Metadata) dùng chung |   |
|   |  [Object A] <--------------+-------------+          |   |
|   +----------------------------|-------------|----------+   |
|   | Ngăn xếp & PC của Luồng 1  | Ngăn xếp & PC của Luồng 2|   |
|   |  [Các biến cục bộ 1]       |  [Các biến cục bộ 2]     |   |
|   +----------------------+------------------------------+   |
+-----------------------------------------------------------+
```

### Ví Dụ Mã Nguồn
```java
// Một lớp có thể chạy được để minh họa việc chia sẻ bộ nhớ của luồng so với sự biệt lập của tiến trình
public class ProcessVsThread {
    private static int sharedCounter = 0; // Bộ nhớ dùng chung trên vùng Heap

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
        // Kết quả đầu ra có thể nhỏ hơn 2000 do truy cập bộ nhớ dùng chung không được đồng bộ hóa!
        System.out.println("Shared Counter: " + sharedCounter);
    }
}
/*
Possible Output:
Shared Counter: 1984
*/
```

### Chuỗi Nguyên Nhân - Kết Quả
1. Hệ điều hành khởi chạy tiến trình JVM &rarr; OS phân bổ tài nguyên và không gian địa chỉ ảo biệt lập.
2. JVM tạo ra các luồng Java &rarr; Các luồng chia sẻ Heap/Metaspace của JVM nhưng nhận các ngăn xếp (Stack)/thanh ghi PC riêng tư.
3. Nhiều luồng truy cập các đối tượng trên Heap &rarr; Giao tiếp giữa các luồng cực kỳ nhanh chóng mà không có chi phí IPC.
4. Các thao tác ghi song song không đồng bộ diễn ra &rarr; Các chỉ lệnh CPU xen kẽ nhau làm hỏng trạng thái dùng chung (Tình trạng tranh chấp - Race Condition).
