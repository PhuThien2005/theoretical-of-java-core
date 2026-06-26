# Đồng bộ hóa và Đồng thời - Phần 1 (Synchronization and Concurrency - Part 1)

## Mục tiêu học tập (Learning Goal)

Tệp này bao gồm các cơ chế khóa nội tại (intrinsic locking primitives) của Java, các bộ giám sát (monitors), và cơ chế truyền tín hiệu wait/notify. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, không phải là từ vựng cô lập.

## Đề cương chi tiết (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `synchronized method` | Khóa thực thể đối tượng (`this`) đối với các phương thức thực thể, hoặc đối tượng `Class` đối với các phương thức tĩnh, ngăn chặn các luồng khác thực thi bất kỳ phương thức synchronized nào trên khóa đó. |
| `synchronized block` | Khóa một đối tượng tham chiếu cụ thể, cho phép khóa ở mức độ chi tiết hơn (finer-grained) so với các phương thức synchronized. |
| `Object lock` | Khóa nội tại (monitor) được liên kết với một thực thể đối tượng cụ thể. Được giành lấy (acquired) thông qua các phương thức synchronized của thực thể hoặc các khối synchronized khóa trên thực thể đó. |
| `Class lock` | Khóa nội tại được liên kết với đối tượng `java.lang.Class` của một lớp. Được giành lấy thông qua các phương thức synchronized tĩnh hoặc các khối synchronized khóa trên đối tượng lớp literal (ví dụ: `MyClass.class`). |
| `Monitor` | Cơ chế đồng bộ hóa cơ bản (sử dụng các lệnh mã máy/bytecode `monitorenter` và `monitorexit`) điều khiển loại trừ tương hỗ (mutual exclusion) và truyền tín hiệu tập chờ (wait-set). |
| `wait` | Một phương thức kế thừa trên `java.lang.Object` dùng để giải phóng khóa monitor và đưa luồng gọi vào tập chờ (wait set) của đối tượng. |
| `notify` | Đánh thức một luồng đơn lẻ tùy ý đang chờ trong tập chờ monitor của đối tượng. Luồng được đánh thức phải giành lại khóa trước khi tiếp tục. |
| `notifyAll` | Đánh thức tất cả các luồng đang chờ trong tập chờ monitor của đối tượng. Được khuyến nghị thay vì `notify` để tránh các lỗi mất tín hiệu. |

## Chi tiết tài liệu học tập (Detailed Notes)

### Phương thức Synchronized so với Khối Synchronized (Synchronized Methods vs Synchronized Blocks)

#### Phương thức Synchronized (Synchronized Method)
Giành lấy khóa được liên kết với đối tượng nhận (`this` đối với phương thức thực thể, đối tượng class đối với phương thức tĩnh).
```java
public class Counter {
    private int count = 0;

    // Locks 'this' Counter instance
    public synchronized void increment() {
        count++;
    }
}
```

#### Khối Synchronized (Synchronized Block)
Cho phép khóa trên một đối tượng monitor riêng tư, cụ thể. Điều này ngăn mã bên ngoài khóa trên cùng một thực thể, giảm nguy cơ bế tắc (deadlock) hoặc đói tài nguyên (starvation) vô ý.
```java
public class BetterCounter {
    private int count = 0;
    private final Object lock = new Object(); // Private lock object

    public void increment() {
        synchronized (lock) { // Only locks the private monitor
            count++;
        }
    }
}
```

### Khóa đối tượng so với Khóa lớp (Object Lock vs Class Lock)
* **Khóa đối tượng (Object Lock)**: Bảo vệ các trường thực thể. Hai luồng khác nhau có thể thực thi các phương thức thực thể synchronized trên các thực thể *khách nhau* của lớp một cách đồng thời.
* **Khóa lớp (Class Lock)**: Bảo vệ các trường tĩnh. Chỉ có một luồng có thể thực thi các phương thức synchronized tĩnh trong toàn bộ JVM cho lớp đó, bất kể có bao nhiêu thực thể của lớp tồn tại.

```java
class Demo {
    // Class lock (locks Demo.class)
    public static synchronized void staticMethod() {}
    
    // Object lock (locks 'this' Demo instance)
    public synchronized void instanceMethod() {}
}
```

### Hợp đồng truyền tín hiệu Wait và Notify (Wait and Notify Signaling Contract)
Các phương thức `wait()`, `notify()`, và `notifyAll()` được sử dụng để điều phối các thay đổi trạng thái.
* **Yêu cầu quyền sở hữu khóa (Lock Ownership Requirement)**: Một luồng **phải** sở hữu khóa monitor của đối tượng đích trước khi gọi các phương thức này. Nếu không, ngoại lệ `IllegalMonitorStateException` sẽ được ném ra lúc chạy.
* **Yêu cầu vòng lặp đối với Wait (Loop Requirement for Wait)**: Đánh thức giả (spurious wakeups — các luồng tự thức dậy mà không có thông báo) được cho phép bởi JVM/Hệ điều hành. Do đó, `wait()` phải luôn được gọi bên trong một vòng lặp `while` để kiểm tra điều kiện.

```java
synchronized (lock) {
    while (!condition) {
        lock.wait(); // Releases lock, blocks thread
    }
    // Condition is true, perform work
}
```

---

## Tình huống nghiên cứu: Hàng đợi chặn có giới hạn (Case Study: Bounded Blocking Queue)

### Vấn đề (Problem)
Triển khai một hàng đợi có giới hạn, an toàn luồng, nơi nhà sản xuất (producer) bị chặn nếu hàng đợi đầy, và người tiêu dùng (consumer) bị chặn nếu hàng đợi trống.

### Giải pháp (Solution)
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
                lock.wait(); // Wait for space to open up
            }
            queue.add(item);
            lock.notifyAll(); // Wake up consumers waiting for data
        }
    }

    public T take() throws InterruptedException {
        synchronized (lock) {
            while (queue.isEmpty()) {
                lock.wait(); // Wait for data to arrive
            }
            T item = queue.poll();
            lock.notifyAll(); // Wake up producers waiting for space
            return item;
        }
    }
}
```

---

## Các sai lầm thường gặp (Common Mistakes)

### 1. Bỏ qua việc đánh thức giả (Swallowing Spurious Wakeups)
Sử dụng câu lệnh `if` thay vì vòng lặp `while` khi gọi `wait()`.
```java
// BUG
synchronized(lock) {
    if (queue.isEmpty()) {
        lock.wait(); // Might wake up spuriously and poll null!
    }
    return queue.poll();
}

// FIX: Always loop
synchronized(lock) {
    while (queue.isEmpty()) {
        lock.wait();
    }
    return queue.poll();
}
```

### 2. Khóa trên các đối tượng dùng chung hoặc có thể thay đổi (Locking on Shared or Mutable Objects)
Khóa trên các hằng chuỗi (String literals), các lớp bao bọc Boolean (Boolean wrappers), hoặc các lớp bao bọc kiểu nguyên thủy là vô cùng nguy hiểm.
* Các hằng chuỗi được lưu trữ trong một vùng chung (pooled). Nếu một thư viện không liên quan khác khóa trên cùng một hằng chuỗi, nó có thể làm đóng băng ứng dụng của bạn (gây ra bế tắc - deadlock).
* Các khóa có thể thay đổi (các đối tượng có trường thay đổi) có thể khiến các luồng khóa trên các thực thể khác nhau, bỏ qua việc kiểm tra loại trừ tương hỗ.
* **Quy tắc**: Luôn khóa trên `private final Object lock = new Object();`.

### 3. Gọi wait() hoặc notify() mà không giữ khóa monitor
```java
Object lock = new Object();
lock.notify(); // Throws IllegalMonitorStateException
```

## Tại sao các khối synchronized ngăn chặn điều kiện tranh đua (Why synchronized Blocks Prevent Race Conditions)

Trong Java, mỗi đối tượng đều được liên kết với một khóa nội tại (intrinsic lock), hay còn gọi là monitor. Trình biên dịch dịch một khối `synchronized` thành các lệnh bytecode `monitorenter` và `monitorexit`. Khi một luồng tiếp cận `monitorenter`, nó sẽ cố gắng giành lấy khóa monitor. Nếu monitor đang được giữ bởi một luồng khác, luồng yêu cầu sẽ bị đình chỉ và được đặt vào tập nhập cuộc (entry set) của monitor (trạng thái chặn — blocked state). Khi thực thi `monitorexit`, chủ sở hữu khóa sẽ giải phóng monitor, cho phép một luồng bị chặn thức dậy và giành lấy nó.

### Mô hình tư duy: Hàng đợi khóa bộ giám sát (Mental Model: Monitor Lock Queue)
```
[Thread A (Owner)] ──► [Object Monitor (Locked)]
                             │
                             ▼ [Entry Set]
                      ┌──────────────┐
                      │ Thread B, C  │ (BLOCKED)
                      └──────────────┘
```

### Ví dụ mã nguồn (Code Example)
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
        System.out.println(demo.count); // Output: 2
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Luồng đi vào synchronized
  → Chạy bytecode monitorenter
  → Kiểm tra trạng thái monitor
  → Monitor đang bận
  → Luồng đi vào Entry Set của monitor (BLOCKED)
  → Luồng chủ sở hữu chạy monitorexit
  → Monitor trở nên tự do
  → Luồng bị chặn thức dậy và giành lấy monitor
  → Ngăn chặn điều kiện tranh đua.
```

