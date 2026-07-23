# Đồng Bộ Hóa và Đồng Thời - Phần 5 (Synchronization and Concurrency - Part 5)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này tập trung vào Framework Executor của Java (`Executor`, `ExecutorService`, `ThreadPoolExecutor`, `ScheduledExecutorService`), các kết quả tác vụ bất đồng bộ (`Future`), và chuỗi lời hứa nâng cao (`CompletableFuture`). Hãy học từng khái niệm như một quy tắc Java thực tế, tránh việc ghi nhớ từ vựng một cách máy móc.

## Khung Nội Dung (Outline Coverage)

- **`Executor`** — Executor: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`ExecutorService`** — ExecutorService: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`ScheduledExecutorService`** — ScheduledExecutorService: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`ThreadPoolExecutor`** — ThreadPoolExecutor: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Executors`** — Executors: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Future`** — Future: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Callable`** — Callable: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`CompletableFuture`** — CompletableFuture: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

---

## Ghi Chú Chi Tiết (Detailed Notes)

### Các Tham Số Của ThreadPoolExecutor
Để cấu hình một nhóm luồng tùy chỉnh an toàn, bạn bắt buộc phải hiểu rõ các tham số cốt lõi sau:
1. **Core Pool Size (Kích thước luồng cốt lõi)**: Số lượng luồng tối thiểu được duy trì hoạt động trong nhóm, ngay cả khi chúng đang nhàn rỗi.
2. **Maximum Pool Size (Kích thước luồng tối đa)**: Số lượng luồng tối đa được phép hoạt động trong nhóm.
3. **Keep Alive Time (Thời gian duy trì hoạt động)**: Khoảng thời gian các luồng nhàn rỗi vượt quá số lượng cốt lõi sẽ chờ trước khi bị hủy bỏ.
4. **Work Queue (Hàng đợi công việc)**: Hàng đợi chặn `BlockingQueue` được dùng để chứa các tác vụ trước khi chúng được thực thi.
5. **Rejection Policy (Chính sách từ chối)**: Bộ xử lý được gọi khi nhóm luồng và hàng đợi đã bị quá tải (ví dụ: `AbortPolicy` ném ra ngoại lệ, `CallerRunsPolicy` thực thi tác vụ trực tiếp trên luồng gọi, `DiscardPolicy` âm thầm bỏ qua tác vụ).

```java
import java.util.concurrent.*;

public class CustomPoolDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
            2,                              // Luồng cốt lõi
            4,                              // Luồng tối đa
            60, TimeUnit_SECONDS,           // Thời gian duy trì hoạt động
            new ArrayBlockingQueue<>(10),    // Hàng đợi giới hạn dung lượng
            new ThreadPoolExecutor.CallerRunsPolicy() // Bộ xử lý kiểm soát áp lực ngược
        );
        
        pool.submit(() -> System.out.println("Executing task"));
        pool.shutdown();
    }
    private static final TimeUnit TimeUnit_SECONDS = TimeUnit.SECONDS;
}
```

> Xem thêm: Cơ chế đa luồng cơ bản (Process vs Thread, Runnable) mà Executor quản lý, được trình bày chi tiết trong [Ch.28 - Multithreading](../../no28_multithreading/README.md).

### ScheduledExecutorService
Được sử dụng để chạy các tác vụ định kỳ hoặc các tác vụ có độ trễ. Cần phân biệt rõ:
- `scheduleAtFixedRate(task, init, period, unit)`: Chạy tác vụ theo các khoảng thời gian cố định tính từ thời điểm bắt đầu (ví dụ: cứ mỗi 5 giây). Nếu một tác vụ chạy mất 6 giây, tác vụ tiếp theo sẽ bắt đầu ngay lập tức (các tác vụ mặc định không chạy chồng lấn lên nhau trong một luồng đơn, nhưng khoảng thời gian được tính từ lúc bắt đầu tác vụ).
- `scheduleWithFixedDelay(task, init, delay, unit)`: Chờ một khoảng thời gian trễ được chỉ định tính **từ lúc** tác vụ trước đó hoàn thành xong xuôi rồi mới bắt đầu chạy tác vụ tiếp theo.

```java
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PollingDemo {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        // Bắt đầu tác vụ tiếp theo sau khi tác vụ trước hoàn thành được 3 giây
        scheduler.scheduleWithFixedDelay(
            () -> System.out.println("Polling API..."),
            0, 3, TimeUnit.SECONDS
        );
    }
}
```

### Đường Dẫn CompletableFuture (CompletableFuture Pipelines)
`CompletableFuture` hỗ trợ xây dựng các chuỗi gọi lại không gây nghẽn luồng.
```java
import java.util.concurrent.CompletableFuture;

public class AsyncChainDemo {
    public static void main(String[] args) throws Exception {
        CompletableFuture.supplyAsync(() -> "User Data")
            .thenApply(data -> data + " Processed")
            .thenAccept(System.out::println) // Tiêu thụ kết quả
            .exceptionally(ex -> {
                System.out.println("Failed: " + ex.getMessage());
                return null;
            });
    }
}
```

---

## Ví Dụ Thực Tế: Đường Dẫn Thanh Toán Thương Mại Điện Tử Bất Đồng Bộ

### Bài toán
Một hệ thống thanh toán trực tuyến cần thực hiện xử lý thanh toán, cập nhật kho hàng và gửi email xác nhận. Việc thực hiện tuần tự các tác vụ này trên một luồng đơn sẽ làm chậm thời gian phản hồi hệ thống.

### Giải pháp
Sử dụng `CompletableFuture` để điều phối chạy song song các tác vụ.
```java
import java.util.concurrent.CompletableFuture;

public class CheckoutProcessor {
    public void processCheckout(Order order) {
        // Bước 1: Bắt đầu xử lý thanh toán bất đồng bộ
        CompletableFuture<PaymentResult> paymentFuture = 
            CompletableFuture.supplyAsync(() -> processPayment(order));

        // Bước 2: Bắt đầu cập nhật kho hàng song song đồng thời
        CompletableFuture<InventoryResult> inventoryFuture = 
            CompletableFuture.supplyAsync(() -> updateInventory(order));

        // Bước 3: Kết hợp cả hai bước trên để tạo hóa đơn
        paymentFuture.thenCombine(inventoryFuture, (pay, inv) -> generateInvoice(pay, inv))
            .thenAccept(invoice -> sendEmail(invoice)) // Bước 4: Gửi email cho khách hàng
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

## Các Sai Lầm Thường Gặp (Common Mistakes)

### 1. Sử dụng Hàng Đợi Không Giới Hạn trong môi trường Production
Phương thức `Executors.newFixedThreadPool(n)` sử dụng một hàng đợi không giới hạn `LinkedBlockingQueue`. Nếu các tác vụ gửi đến nhanh hơn tốc độ xử lý của nhóm luồng, hàng đợi này sẽ phình to vô hạn, cuối cùng dẫn đến lỗi hết bộ nhớ `OutOfMemoryError` (OOM).
- **Khắc phục**: Luôn cấu hình cụ thể một hàng đợi giới hạn dung lượng (như `ArrayBlockingQueue`) và định nghĩa một chính sách từ chối rõ ràng cho môi trường production.

### 2. Gọi chặn `Future.get()` bên trong vòng lặp
Việc gọi phương thức `.get()` ngay lập tức chặn luồng hiện tại, biến quá trình xử lý song song thành thực thi tuần tự chậm chạp.
```java
// SAI: Chạy từng tác vụ tuần tự và gây chặn luồng!
for (Callable<Integer> task : tasks) {
    Future<Integer> f = executor.submit(task);
    System.out.println(f.get()); // Bị chặn tại đây!
}
```
- **Khắc phục**: Gửi toàn bộ các tác vụ để thu thập danh sách các đối tượng `Future` trước, sau đó mới duyệt qua danh sách để lấy kết quả trong một vòng lặp riêng biệt khác.

## Tại Sao Phải Sử Dụng ExecutorService và Nhóm Luồng (Thread Pools)

Việc khởi tạo thủ công các luồng mới cho mỗi tác vụ là cực kỳ kém hiệu quả và nguy hiểm đối với máy ảo JVM. Mọi luồng được tạo ra trong Java đều đòi hỏi một luồng thực tế của hệ điều hành (OS thread), đi kèm với chi phí cấp phát bộ nhớ lớn bao gồm một phân vùng ngăn xếp (stack size) mặc định khoảng 1MB. Nếu ứng dụng khởi tạo các luồng không giới hạn, nó sẽ nhanh chóng làm cạn kiệt bộ nhớ hệ thống hoặc các mô tả tệp (file descriptors), dẫn đến sập hệ thống. `ExecutorService` giải quyết vấn đề này bằng cách sử dụng các nhóm luồng quản lý và tái sử dụng một tập hợp cố định các luồng công nhân (worker threads) để xử lý đồng thời các tác vụ. Hơn nữa, việc cấu hình các hàng đợi giới hạn dung lượng (như `ArrayBlockingQueue`) bên trong nhóm luồng sẽ ngăn chặn việc tích tụ các tác vụ gửi đến làm tiêu tốn hết bộ nhớ, bảo vệ JVM khỏi lỗi `OutOfMemoryError` nhờ các chính sách từ chối tác vụ có cấu trúc.

### Mô Hình Tư Duy: Nhóm luồng với Hàng đợi giới hạn dung lượng

```text
[Nhiệm vụ được gửi] ──► [Hàng đợi giới hạn (dung lượng = 100)]
                             │ (Nếu đầy: Áp dụng Chính sách từ chối)
                             ▼
                    ┌───────────────────────────┐
                    │ Nhóm luồng (Công nhân)    │
                    │ ┌──────┐┌──────┐┌──────┐  │
                    │ │  T1  ││  T2  ││  T3  │  │
                    │ └──────┘└──────┘└──────┘  │
                    └───────────────────────────┘
```

### Ví Dụ Mã Nguồn
```java
import java.util.concurrent.*;

public class ExecutorDemo {
    public static void main(String[] args) {
        // Nhóm luồng an toàn với hàng đợi giới hạn và chính sách từ chối AbortPolicy
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
        // Kết quả:
        // Executing Task
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)
Khởi tạo thủ công luồng mới cho mỗi yêu cầu &rarr; Chi phí cấp phát luồng hệ điều hành cao &rarr; Tiêu tốn 1MB bộ nhớ ngăn xếp cho mỗi luồng &rarr; Cạn kiệt bộ nhớ / sập hệ điều hành &rarr; Thay thế bằng `ExecutorService` &rarr; Tái sử dụng tập hợp cố định các luồng công nhân &rarr; Các hàng đợi giới hạn dung lượng ngăn chặn tích tụ tác vụ &rarr; Chính sách từ chối xử lý tải trọng thừa &rarr; JVM được bảo vệ an toàn khỏi lỗi OutOfMemoryError.
