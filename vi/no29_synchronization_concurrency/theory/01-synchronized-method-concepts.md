# Đồng Bộ Hóa và Đồng Thời - Phần 1 (Synchronization and Concurrency - Part 1)

## Khung Nội Dung (Outline Coverage)

---

## Ghi Chú Chi Tiết (Detailed Notes)

### Phương thức Synchronized vs Khối Synchronized

#### Phương thức Synchronized (Synchronized Method)
Chiếm giữ khóa liên kết với đối tượng nhận phương thức (`this` đối với phương thức thực thể, đối tượng lớp đối với phương thức tĩnh).
```java
public class Counter {
    private int count = 0;

    // Khóa thực thể Counter 'this' hiện tại
    public synchronized void increment() {
        count++;
    }
}
```

#### Khối Synchronized (Synchronized Block)
Cho phép thực hiện khóa trên một đối tượng monitor riêng tư cụ thể. Điều này giúp ngăn chặn mã nguồn bên ngoài khóa trùng thực thể đó, làm giảm nguy cơ xảy ra bế tắc (deadlock) hoặc tình trạng đói tài nguyên ngoài ý muốn.
```java
public class BetterCounter {
    private int count = 0;
    private final Object lock = new Object(); // Đối tượng khóa riêng tư

    public void increment() {
        synchronized (lock) { // Chỉ thực hiện khóa trên đối tượng monitor riêng tư
            count++;
        }
    }
}
```

### Khóa Đối Tượng vs Khóa Lớp (Object Lock vs Class Lock)
* **Khóa Đối Tượng**: Bảo vệ các trường thực thể. Hai luồng khác nhau có thể thực thi các phương thức synchronized thực thể trên *hai thực thể đối tượng khác nhau* của lớp một cách đồng thời.
* **Khóa Lớp**: Bảo vệ các trường tĩnh. Chỉ duy nhất một luồng có thể thực thi phương thức synchronized tĩnh trong toàn bộ JVM cho lớp đó, bất kể có bao nhiêu thực thể đối tượng đang tồn tại.

```java
class Demo {
    // Khóa lớp (khóa đối tượng Demo.class)
    public static synchronized void staticMethod() {}
    
    // Khóa đối tượng (khóa thực thể 'this' của Demo)
    public synchronized void instanceMethod() {}
}
```

> Xem thêm: Các đặc tính của từ khóa `synchronized` khi dùng như một modifier, được trình bày chi tiết trong [Ch.10 - Access Modifiers](../../no10_modifiers/theory/02-abstract-concepts.md).

### Hợp Đồng Truyền Tín Hiệu Wait và Notify
Các phương thức `wait()`, `notify()`, và `notifyAll()` được dùng để điều phối các thay đổi trạng thái giữa các luồng.
* **Yêu Cầu Sở Hữu Khóa**: Một luồng **bắt buộc** phải sở hữu khóa monitor của đối tượng đích trước khi gọi các phương thức này. Nếu không, ngoại lệ `IllegalMonitorStateException` sẽ bị ném ra lúc chạy.
* **Yêu Cầu Vòng Lặp Khi Chờ**: Hiện tượng đánh thức giả tạo (spurious wakeups - luồng tự thức dậy mà không có luồng nào phát tín hiệu notify) có thể xảy ra ở cấp độ JVM/HĐH. Do đó, phương thức `wait()` bắt buộc phải luôn được gọi bên trong một vòng lặp `while` để kiểm tra lại điều kiện.

```java
synchronized (lock) {
    while (!condition) {
        lock.wait(); // Giải phóng khóa và chặn luồng hiện tại
    }
    // Điều kiện đúng, thực thi công việc
}
```

---

## Ví Dụ Thực Tế: Hàng Đợi Chặn Có Giới Hạn (Bounded Blocking Queue)

### Bài toán
Triển khai một hàng đợi chặn có giới hạn an toàn luồng, nơi luồng sản xuất (producer) sẽ bị chặn nếu hàng đợi đã đầy, và luồng tiêu thụ (consumer) sẽ bị chặn nếu hàng đợi trống.

### Giải pháp
Sử dụng một khóa riêng tư và cơ chế truyền tín hiệu `wait()` / `notifyAll()`.
```java
import java.util.LinkedList;
import java.util.Queue;

public class BoundedQueue<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;
    private final Object lock = new Object();

    public BoundedQueue(int capacity) {
        this.capacity = capacity;
    }

    public void put(T item) throws InterruptedException {
        synchronized (lock) {
            while (queue.size() == capacity) {
                lock.wait(); // Chờ cho đến khi có chỗ trống
            }
            queue.add(item);
            lock.notifyAll(); // Đánh thức các luồng tiêu thụ đang chờ dữ liệu
        }
    }

    public T take() throws InterruptedException {
        synchronized (lock) {
            while (queue.isEmpty()) {
                lock.wait(); // Chờ cho đến khi có dữ liệu
            }
            T item = queue.poll();
            lock.notifyAll(); // Đánh thức các luồng sản xuất đang chờ chỗ trống
            return item;
        }
    }
}
```

---

## Các Sai Lầm Thường Gặp (Common Mistakes)

### 1. Bỏ qua hiện tượng đánh thức giả tạo (Spurious Wakeups)
Sử dụng câu lệnh `if` thay vì vòng lặp `while` khi gọi `wait()`.
```java
// SAI
synchronized(lock) {
    if (queue.isEmpty()) {
        lock.wait(); // Có thể bị thức dậy giả tạo và lấy ra phần tử null!
    }
    return queue.poll();
}

// ĐÚNG: Luôn sử dụng vòng lặp
synchronized(lock) {
    while (queue.isEmpty()) {
        lock.wait();
    }
    return queue.poll();
}
```

### 2. Thực hiện khóa trên các đối tượng dùng chung hoặc đối tượng có thể thay đổi
Việc thực hiện khóa trên các hằng chuỗi (String literals), các lớp bao bọc Boolean hoặc các lớp bao bọc kiểu số nguyên nguyên thủy là cực kỳ nguy hiểm.
* Các hằng chuỗi được quản lý tập trung trong String Pool. Nếu một thư viện không liên quan khác vô tình thực hiện khóa trên cùng một hằng chuỗi đó, nó có thể làm treo ứng dụng của bạn (gây ra bế tắc - deadlock).
* Khóa trên các đối tượng có thể thay đổi (mutable locks - các đối tượng có thể thay đổi giá trị trường) có thể khiến các luồng thực hiện khóa trên các thực thể khác nhau, bỏ qua kiểm tra loại trừ tương hỗ.
* **Quy tắc**: Luôn sử dụng `private final Object lock = new Object();` làm đối tượng khóa.

### 3. Gọi `wait()` hoặc `notify()` khi chưa nắm giữ khóa monitor
```java
Object lock = new Object();
lock.notify(); // Ném ra IllegalMonitorStateException
```

## Tại Sao Khối synchronized Ngăn Chặn Được Tranh Chấp Điều Kiện (Race Conditions)

Trong Java, mọi đối tượng đều được liên kết với một khóa nội tại (intrinsic lock), hay còn gọi là monitor. Trình biên dịch dịch khối `synchronized` thành các chỉ thị mã byte `monitorenter` và `monitorexit`. Khi một luồng đi tới chỉ thị `monitorenter`, nó sẽ cố gắng chiếm giữ khóa monitor. Nếu monitor đang bị chiếm giữ bởi luồng khác, luồng yêu cầu sẽ bị tạm dừng và đưa vào danh sách chờ của monitor (trạng thái blocked). Khi thực thi chỉ thị `monitorexit`, chủ sở hữu khóa sẽ giải phóng monitor, cho phép một luồng đang chờ thức dậy và chiếm giữ khóa.

### Mô Hình Tư Duy: Hàng đợi khóa Monitor (Monitor Lock Queue)
```text
[Luồng A (Sở hữu)] ──► [Bộ giám sát đối tượng Monitor (Bị khóa)]
                             │
                             ▼ [Danh sách chờ - Entry Set]
                      ┌──────────────┐
                      │ Luồng B, C   │ (BỊ CHẶN - BLOCKED)
                      └──────────────┘
```

### Ví Dụ Mã Nguồn
```java
public class MonitorLockDemo {
    private int count = 0;
    private final Object lock = new Object();

    public void increment() {
        synchronized (lock) { // monitorenter
            count++;
        } // monitorexit
    }

    public static void main(String[] args) throws Exception {
        MonitorLockDemo demo = new MonitorLockDemo();
        Thread t1 = new Thread(() -> demo.increment());
        Thread t2 = new Thread(() -> demo.increment());
        t1.start(); t2.start(); t1.join(); t2.join();
        System.out.println(demo.count); // Kết quả in ra: 2
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)
Luồng đi vào khối `synchronized` &rarr; chạy chỉ thị bytecode `monitorenter` &rarr; kiểm tra trạng thái monitor &rarr; monitor đang bận &rarr; luồng đi vào danh sách chờ của monitor (BLOCKED) &rarr; luồng sở hữu chạy chỉ thị `monitorexit` &rarr; monitor trở nên tự do &rarr; luồng đang chờ thức dậy và chiếm giữ monitor &rarr; ngăn chặn tranh chấp điều kiện thành công.
