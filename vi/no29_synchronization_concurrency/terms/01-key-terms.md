# Các Thuật Ngữ Về Đồng Bộ Hóa Và Đồng Thời

Hãy sử dụng tài liệu này khi một từ trong phần lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều có phần ý nghĩa, tầm quan trọng, hiểu lầm phổ biến và một ví dụ nhỏ.

## synchronized

Từ khóa `synchronized` trong Java được sử dụng để cung cấp tính loại trừ tương hỗ (mutual exclusion) và thiết lập mối quan hệ xảy ra trước (happens-before relationship) giữa các luồng. Nó đảm bảo rằng tại một thời điểm chỉ có duy nhất một luồng có thể thực thi một phương thức hoặc khối lệnh synchronized được khóa trên một giám sát đối tượng (object monitor) cụ thể.

* **Tầm quan trọng**: Nó ngăn chặn các tình trạng tranh chấp (race condition) và hư hỏng dữ liệu bằng cách tuần tự hóa quyền truy cập vào trạng thái có thể thay đổi dùng chung (shared mutable state). Nó cũng đảm bảo tính khả thị của bộ nhớ (memory visibility), đảm bảo rằng các thay đổi do một luồng thực hiện bên trong khối synchronized sẽ hiển thị với bất kỳ luồng nào khác sau đó đi vào một khối synchronized được khóa trên cùng một đối tượng khóa (lock object).
* **Hiểu lầm phổ biến**: Giả định rằng các khối `synchronized` trên các đối tượng khác nhau sẽ loại trừ tương hỗ lẫn nhau. Một khối `synchronized` chỉ chặn các luồng khác đang cố gắng đồng bộ hóa trên *cùng một* đối tượng khóa. Khóa trên các đối tượng riêng biệt sẽ cho phép các luồng thực thi các khối được bảo vệ một cách đồng thời.
* **Ví dụ nhỏ**:
  ```java
  private final Object lock = new Object();
  public void safeIncrement() {
      synchronized (lock) {
          count++;
      }
  }
  ```

## bế tắc (deadlock)

Bế tắc (deadlock) là một trạng thái tại thời điểm chạy (runtime) trong đó hai hoặc nhiều luồng bị chặn vĩnh viễn, mỗi luồng chờ đợi một khóa được nắm giữ bởi một luồng khác.

* **Tầm quan trọng**: Bế tắc làm dừng hoàn toàn việc thực thi của các luồng bị ảnh hưởng, dẫn đến việc ứng dụng bị treo và hệ thống không phản hồi. Chúng không thể tự giải quyết và thường yêu cầu khởi động lại hệ thống.
* **Hiểu lầm phổ biến**: Nhầm lẫn bế tắc với tình trạng đói tài nguyên (starvation) hoặc bế tắc động (livelock). Trong một bế tắc, các luồng bị đình chỉ về mặt vật lý (trạng thái BLOCKED) và tiêu thụ 0% CPU. Trong một livelock, các luồng đang hoạt động tích cực và thay đổi trạng thái (tiêu thụ CPU) nhưng không tạo ra tiến trình nào.
* **Ví dụ nhỏ**:
  ```java
  // Luồng 1 khóa A rồi đến B; Luồng 2 khóa B rồi đến A
  // Cả hai cùng nắm giữ một khóa và chờ đợi nhau mãi mãi
  ```

## biến nguyên tử (atomic variable)

Các lớp trong gói `java.util.concurrent.atomic` (như `AtomicInteger`, `AtomicReference`) hỗ trợ lập trình an toàn luồng và không dùng khóa (lock-free) trên các biến đơn lẻ.

* **Tầm quan trọng**: Chúng cho phép truy cập đọc-ghi đồng thời với hiệu năng rất cao trên một biến đơn lẻ mà không có chi phí nặng nề của việc đình chỉ luồng ở cấp hệ điều hành và chuyển đổi ngữ cảnh (context switch) liên quan đến đồng bộ hóa dựa trên khóa.
* **Hiểu lầm phổ biến**: Giả định rằng việc nhóm nhiều thao tác biến nguyên tử sẽ làm cho toàn bộ chuỗi thao tác đó trở nên nguyên tử. Ví dụ, `int x = atomicInt.get(); atomicInt.set(x + 1);` là *không* an toàn luồng, ngay cả khi mỗi phương thức gọi riêng lẻ là nguyên tử. Bạn phải sử dụng các thao tác hỗn hợp như `compareAndSet` hoặc `incrementAndGet`.
* **Ví dụ nhỏ**:
  ```java
  private final AtomicInteger counter = new AtomicInteger(0);
  public void increment() {
      counter.incrementAndGet(); // Thread-safe atomic increment
  }
  ```

## CAS (Compare-And-Swap)

Một chỉ lệnh nguyên tử ở cấp độ phần cứng được sử dụng để triển khai đồng bộ hóa không dùng khóa (lock-free). Nó so sánh nội dung của một vị trí bộ nhớ với một giá trị mong đợi được cung cấp và chỉ khi chúng bằng nhau, nó mới sửa đổi nội dung đó thành một giá trị mới được cung cấp.

* **Tầm quan trọng**: Đây là khối xây dựng nền tảng cho tất cả các lớp nguyên tử và cấu trúc dữ liệu không chặn (non-blocking) trong Java. Nó cho phép các luồng cập nhật các giá trị một cách đồng thời và an toàn bằng cách thử lại (quay vòng - spin) thay vì bị chặn trên một khóa.
* **Hiểu lầm phổ biến**: Nghĩ rằng CAS được triển khai thông qua các vòng lặp phần mềm Java. Phương thức `compareAndSet` của Java ánh xạ trực tiếp đến các chỉ lệnh lắp ráp (assembly) của CPU gốc (ví dụ: `lock cmpxchg` trên kiến trúc x86), giúp nó hoạt động cực kỳ nhanh.
* **Ví dụ nhỏ**:
  ```java
  AtomicInteger val = new AtomicInteger(10);
  boolean success = val.compareAndSet(10, 11); // trả về true, val bây giờ là 11
  ```

## CyclicBarrier

Một công cụ hỗ trợ đồng bộ hóa cho phép một tập hợp các luồng cùng chờ đợi lẫn nhau đạt đến một điểm rào cản chung (barrier point) trước khi tiếp tục.

* **Tầm quan trọng**: Nó cực kỳ hữu ích trong các thuật toán song song trong đó nhiều luồng thực hiện các tác vụ con độc lập và phải chờ đợi tất cả các luồng khác hoàn thành trước khi chuyển sang giai đoạn tiếp theo. Điểm cốt lõi là nó có thể được đặt lại (reset) và tái sử dụng sau khi rào cản được kích hoạt.
* **Hiểu lầm phổ biến**: Nhầm lẫn nó với `CountDownLatch`. Một `CyclicBarrier` yêu cầu các luồng phải chủ động chặn tại điểm rào cản bằng cách sử dụng `await()` để giảm số đếm. Một luồng không thể giảm số đếm của rào cản mà không tự chặn chính nó.
* **Ví dụ nhỏ**:
  ```java
  CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("Giai đoạn hoàn tất!"));
  // 3 luồng gọi barrier.await() sẽ kích hoạt rào cản, chạy tác vụ runnable và tiếp tục thực thi
  ```

## CountDownLatch

Một công cụ hỗ trợ đồng bộ hóa cho phép một hoặc nhiều luồng chờ đợi cho đến khi một tập hợp các hoạt động đang được thực hiện trong các luồng khác hoàn thành.

* **Tầm quan trọng**: Nó hoạt động giống như một cổng một chiều (one-shot gate). Nó là lựa chọn lý tưởng để phối hợp các giai đoạn khởi động, nơi luồng chính sẽ chặn thông qua `await()` cho đến khi tất cả các luồng khởi tạo gọi `countDown()`.
* **Hiểu lầm phổ biến**: Cố gắng tái sử dụng một `CountDownLatch`. Khi số đếm của chốt (latch) đạt đến 0, cổng của nó sẽ mở vĩnh viễn và các lệnh gọi `await()` tiếp theo sẽ trả về ngay lập tức. Nó không thể được đặt lại; bạn phải tạo một thể hiện mới.
* **Ví dụ nhỏ**:
  ```java
  CountDownLatch latch = new CountDownLatch(3);
  // Các luồng công việc (worker) gọi latch.countDown();
  // Luồng chính chặn trên latch.await() cho đến khi số đếm về 0
  ```

## Bộ thực thi (Executor)

Một đối tượng thực thi các tác vụ `Runnable` được gửi lên. Giao diện này tách biệt việc gửi tác vụ khỏi cơ chế thực thi của từng tác vụ, chẳng hạn như sử dụng luồng, lập lịch, v.v.

* **Tầm quan trọng**: Sự tách biệt này cho phép các lập trình viên tập trung vào việc định nghĩa tác vụ trong khi cấu hình thực thi luồng (gom nhóm luồng, lập lịch) có thể được quản lý riêng và sửa đổi mà không làm thay đổi mã nguồn gửi tác vụ.
* **Hiểu lầm phổ biến**: Nghĩ rằng `Executor` là một bể chứa luồng (thread pool). `Executor` chỉ là một giao diện chức năng đơn giản với một phương thức duy nhất `execute(Runnable)`. Các giao diện con và triển khai của nó (như `ExecutorService` và `ThreadPoolExecutor`) mới cung cấp logic của bể chứa luồng.
* **Ví dụ nhỏ**:
  ```java
  Executor executor = command -> new Thread(command).start();
  executor.execute(() -> System.out.println("Đang chạy tác vụ"));
  ```

## ForkJoinPool

Một triển khai `ExecutorService` được thiết kế đặc biệt cho các tác vụ chia để trị (divide-and-conquer) bằng cách sử dụng thuật toán trộm công việc (work-stealing algorithm).

* **Tầm quan trọng**: Nó tối đa hóa việc tận dụng các lõi CPU bằng cách đảm bảo các luồng công việc rảnh rỗi sẽ trộm tác vụ từ hàng đợi hai đầu (deque) của các luồng đang bận rộn, giảm thiểu tình trạng đói luồng và giữ cho tất cả các lõi CPU luôn hoạt động.
* **Hiểu lầm phổ biến**: Sử dụng `ForkJoinPool` cho các tác vụ I/O chặn (blocking I/O). Vì `ForkJoinPool` được thiết kế cho các tác vụ tính toán chuyên sâu (compute-intensive), việc chặn các luồng bên trong bể chứa có thể làm đói các tác vụ khác. Hãy sử dụng một `ThreadPoolExecutor` tiêu chuẩn với một bể chứa luồng lưu đệm (cached pool) hoặc cố định (fixed pool) cho các thao tác chặn.
* **Ví dụ nhỏ**:
  ```java
  ForkJoinPool pool = ForkJoinPool.commonPool();
  Long sum = pool.invoke(new SumTask(largeArray, 0, largeArray.length));
  ```
