# Đồng bộ hóa và Đồng thời - Phần 5 (Synchronization and Concurrency - Part 5)

## Mục tiêu học tập (Learning Goal)

Tệp này bao gồm Khung công tác Trình thực thi của Java (`Executor`, `ExecutorService`, `ThreadPoolExecutor`, `ScheduledExecutorService`), các kết quả tác vụ bất đồng bộ (`Future`), và chuỗi liên kết tác vụ bất đồng bộ nâng cao (`CompletableFuture`). Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, không phải là từ vựng cô lập.

## Đề cương chi tiết (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `Executor` | Giao diện đơn giản nhất định nghĩa việc thực thi tác vụ thông qua `execute(Runnable)`. |
| `ExecutorService` | Giao diện con bổ sung quản lý vòng đời tác vụ, gửi tác vụ trả về một `Future` (`submit()`), và các phương thức tắt (shutdown). |
| `ScheduledExecutorService` | Giao diện con lên lịch để chạy các tác vụ sau một khoảng thời gian trễ, hoặc thực thi định kỳ. |
| `ThreadPoolExecutor` | Lớp triển khai nhóm luồng tiêu chuẩn, được cấu hình bằng các tham số như số lượng luồng cốt lõi, số lượng luồng tối đa, dung lượng hàng đợi, và bộ xử lý từ chối (rejection handler). |
| `Executors` | Một lớp tiện ích nhà máy chứa các phương thức tĩnh để tạo các nhóm luồng được cấu hình sẵn (ví dụ: fixed, cached, scheduled). |
| `Future` | Đại diện cho kết quả chờ xử lý của một phép tính bất đồng bộ. Gọi `.get()` để chặn và lấy kết quả. |
| `Callable` | Một tác vụ đại diện cho một phép tính trả về kết quả và có thể ném ra một ngoại lệ đã được kiểm tra (checked exception). |
| `CompletableFuture` | Một lớp triển khai `Future` và `CompletionStage` hỗ trợ các hàm gọi lại (callbacks), xử lý theo đường ống (pipelined staging), và kết hợp nhiều tác vụ bất đồng bộ. |

## Chi tiết tài liệu học tập (Detailed Notes)

### Các tham số của ThreadPoolExecutor (The ThreadPoolExecutor Parameters)
Để cấu hình một nhóm luồng tùy chỉnh một cách an toàn, bạn phải hiểu các tham số cốt lõi của nó:
1. **Số lượng luồng cốt lõi (Core Pool Size)**: Số lượng luồng tối thiểu được duy trì hoạt động trong nhóm luồng, ngay cả khi rảnh rỗi.
2. **Số lượng luồng tối đa (Maximum Pool Size)**: Số lượng luồng tối đa được phép tồn tại trong nhóm luồng.
3. **Thời gian giữ sống (Keep Alive Time)**: Thời gian mà các luồng rảnh rỗi vượt quá số lượng cốt lõi sẽ chờ trước khi bị chấm dứt.
4. **Hàng đợi công việc (Work Queue)**: `BlockingQueue` được sử dụng để giữ các tác vụ trước khi chúng được thực thi.
5. **Chính sách từ chối (Rejection Policy)**: Các bộ xử lý được gọi khi nhóm luồng và hàng đợi bị bão hòa (ví dụ: `AbortPolicy` ném ra ngoại lệ, `CallerRunsPolicy` thực thi tác vụ trong luồng gọi, `DiscardPolicy` âm thầm loại bỏ tác vụ).

```java
import java.util.concurrent.*;

public class CustomPoolDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
            2,                              // Core threads
            4,                              // Max threads
            60, TimeUnit_SECONDS,           // Keep alive
            new ArrayBlockingQueue<>(10),    // Bounded queue
            new ThreadPoolExecutor.CallerRunsPolicy() // Backpressure handler
        );
        
        pool.submit(() -> System.out.println("Executing task"));
        pool.shutdown();
    }
    private static final TimeUnit TimeUnit_SECONDS = TimeUnit.SECONDS;
}
```

### ScheduledExecutorService
Được sử dụng để chạy các tác vụ định kỳ hoặc bị trì hoãn. Hãy lưu ý sự khác biệt:
* `scheduleAtFixedRate(task, init, period, unit)`: Chạy các tác vụ ở các khoảng thời gian cố định (ví dụ: mỗi 5 giây). Nếu việc thực thi mất 6 giây, tác vụ tiếp theo sẽ chạy ngay lập tức (các tác vụ không trùng lặp theo mặc định trong một luồng đơn lẻ, nhưng khoảng thời gian được tính từ thời điểm bắt đầu tác vụ).
* `scheduleWithFixedDelay(task, init, delay, unit)`: Chờ khoảng thời gian trễ chỉ định *sau khi* tác vụ trước đó hoàn thành rồi mới bắt đầu tác vụ tiếp theo.

```java
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PollingDemo {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        // Starts next task 3 seconds after previous completes
        scheduler.scheduleWithFixedDelay(
            () -> System.out.println("Polling API..."),
            0, 3, TimeUnit.SECONDS
        );
    }
}
```

### Đường ống CompletableFuture (CompletableFuture Pipelines)
`CompletableFuture` hỗ trợ các chuỗi gọi lại (callback chains) không chặn.
```java
import java.util.concurrent.CompletableFuture;

public class AsyncChainDemo {
    public static void main(String[] args) throws Exception {
        CompletableFuture.supplyAsync(() -> "User Data")
            .thenApply(data -> data + " Processed")
            .thenAccept(System.out::println) // Consumes result
            .exceptionally(ex -> {
                System.out.println("Failed: " + ex.getMessage());
                return null;
            });
    }
}
```

---

## Tình huống nghiên cứu: Đường ống thanh toán thương mại điện tử bất đồng bộ (Case Study: Asynchronous E-Commerce Checkout Pipeline)

### Vấn đề (Problem)
Một hệ thống thanh toán trực tuyến cần xử lý các khoản thanh toán, cập nhật kho hàng, và gửi email xác nhận. Việc thực hiện các tác vụ này tuần tự trên một luồng duy nhất sẽ dẫn đến thời gian phản hồi chậm.

### Giải pháp (Solution)
Sử dụng `CompletableFuture` để điều phối việc thực thi song song.
```java
import java.util.concurrent.CompletableFuture;

public class CheckoutProcessor {
    public void processCheckout(Order order) {
        // Step 1: Start payment process asynchronously
        CompletableFuture<PaymentResult> paymentFuture = 
            CompletableFuture.supplyAsync(() -> processPayment(order));

        // Step 2: Start inventory update concurrently
        CompletableFuture<InventoryResult> inventoryFuture = 
            CompletableFuture.supplyAsync(() -> updateInventory(order));

        // Step 3: Combine both steps to generate invoice
        paymentFuture.thenCombine(inventoryFuture, (pay, inv) -> generateInvoice(pay, inv))
            .thenAccept(invoice -> sendEmail(invoice)) // Step 4: Email customer
            .exceptionally(ex -> {
                logError(ex);
                return null;
            });
    }

    private PaymentResult processPayment(Order o) { return new PaymentResult(); }
    private InventoryResult updateInventory(Order o) { return new InventoryResult(); }
    private Invoice generateInvoice(PaymentResult p, InventoryResult i) { return new Invoice(); }
    private void sendEmail(Invoice inv) {}
    private void logError(Throwable t) {}

    static class Order {}
    static class PaymentResult {}
    static class InventoryResult {}
    static class Invoice {}
}
```

---

## Các sai lầm thường gặp (Common Mistakes)

### 1. Sử dụng hàng đợi không giới hạn trong các nhóm luồng môi trường Production
`Executors.newFixedThreadPool(n)` sử dụng một `LinkedBlockingQueue` không giới hạn. Nếu các tác vụ đến nhanh hơn tốc độ xử lý, hàng đợi sẽ phình to vô hạn, cuối cùng gây ra lỗi `OutOfMemoryError` (OOM).
* **Sửa lỗi**: Luôn cấu hình hàng đợi có giới hạn (như `ArrayBlockingQueue`) và định nghĩa một chính sách từ chối cho môi trường production.

### 2. Chặn trên Future.get() bên trong một vòng lặp
Gọi `.get()` ngay lập tức chặn luồng gọi, biến xử lý song song thành thực thi tuần tự chậm chạp.
```java
// BUG: Runs tasks one-by-one synchronously!
for (Callable<Integer> task : tasks) {
    Future<Integer> f = executor.submit(task);
    System.out.println(f.get()); // Blocks here!
}
```
* **Sửa lỗi**: Gửi tất cả các tác vụ để thu thập các đối tượng `Future` trước, sau đó lấy kết quả của chúng trong một vòng lặp riêng biệt.

## Tại sao ExecutorService và nhóm luồng là cần thiết (Why ExecutorService and Thread Pools Are Required)

Việc tự tạo luồng (spawning threads) thủ công cho mỗi tác vụ là cực kỳ kém hiệu quả và nguy hiểm đối với JVM. Mỗi luồng được tạo ra trong Java đều yêu cầu một luồng của hệ điều hành, vốn mang chi phí cấp phát đáng kể bao gồm kích thước ngăn xếp mặc định khoảng 1MB. Nếu một ứng dụng tạo luồng không giới hạn, nó sẽ nhanh chóng làm cạn kiệt bộ nhớ hệ thống hoặc các bộ mô tả tệp (file descriptors), gây ra sự cố treo ứng dụng. `ExecutorService` giải quyết vấn đề này bằng cách sử dụng các nhóm luồng để duy trì một tập hợp các luồng làm việc hoạt động cố định và được quản lý để xử lý các tác vụ một cách đồng thời. Hơn nữa, việc cấu hình các hàng đợi có giới hạn (như `ArrayBlockingQueue`) bên trong nhóm luồng sẽ ngăn chặn việc tích tụ các tác vụ gửi đến làm tiêu hao toàn bộ bộ nhớ, bảo vệ JVM khỏi lỗi `OutOfMemoryError` thông qua một chính sách từ chối (rejection handler policy) có cấu trúc.

### Mô hình tư duy: Nhóm luồng với hàng đợi có giới hạn (Mental Model: Thread Pool with Bounded Queue)
```
[Tasks Submitted] ──► [Bounded Queue (capacity = 100)]
                            │ (If full: Rejection Policy)
                            ▼
                    ┌─────────────────────────┐
                    │  Thread Pool (Workers)  │
                    │ ┌──────┐┌──────┐┌──────┐ │
                    │ │  T1  ││  T2  ││  T3  │ │
                    │ └──────┘└──────┘└──────┘ │
                    └─────────────────────────┘
```

### Ví dụ mã nguồn (Code Example)
```java
import java.util.concurrent.*;

public class ExecutorDemo {
    public static void main(String[] args) {
        // Safe thread pool with bounded queue and rejection policy
        ExecutorService executor = new ThreadPoolExecutor(
            2, 4, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10),
            new ThreadPoolExecutor.AbortPolicy()
        );

        try {
            executor.submit(() -> System.out.println("Executing Task"));
        } finally {
            executor.shutdown();
        }
        // Output:
        // Executing Task
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Tạo thủ công luồng mới cho mỗi yêu cầu
  → Chi phí cấp phát luồng hệ điều hành cao
  → Tiêu thụ 1MB bộ nhớ ngăn xếp trên mỗi luồng
  → Cạn kiệt bộ nhớ / treo hệ thống
  → Thay thế bằng `ExecutorService`
  → Tái sử dụng tập hợp luồng làm việc cố định
  → Hàng đợi có giới hạn hạn chế việc tích lũy tác vụ
  → Chính sách từ chối của ThreadPool xử lý tải thừa
  → JVM được bảo vệ khỏi lỗi `OutOfMemoryError`.
```

