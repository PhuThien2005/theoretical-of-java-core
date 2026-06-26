# Thuật ngữ về Đồng bộ hóa và Đồng thời (Synchronization and Concurrency Terms)

Sử dụng tệp này khi một từ trong phần lý thuyết có cảm giác quá ngắn gọn. Mỗi thuật ngữ đều có ý nghĩa, tầm quan trọng, hiểu lầm phổ biến và một ví dụ nhỏ.

## Đồng bộ hóa (synchronized)

Từ khóa `synchronized` trong Java được sử dụng để cung cấp loại trừ tương hỗ (mutual exclusion) và thiết lập mối quan hệ xảy ra trước (happens-before relationship) giữa các luồng. Nó đảm bảo rằng tại một thời điểm chỉ có một luồng có thể thực thi một phương thức hoặc khối synchronized được khóa trên một bộ giám sát đối tượng (object monitor) cụ thể.

* **Tại sao nó quan trọng**: Nó ngăn chặn điều kiện tranh đua (race conditions) và hỏng dữ liệu bằng cách tuần tự hóa quyền truy cập vào trạng thái có thể thay đổi được chia sẻ. Nó cũng đảm bảo tính hiển thị của bộ nhớ (memory visibility), đảm bảo rằng các thay đổi do một luồng thực hiện bên trong khối synchronized sẽ hiển thị với bất kỳ luồng nào khác đi vào khối synchronized trên cùng một đối tượng khóa sau đó.
* **Hiểu lầm phổ biến**: Giả định rằng các khối `synchronized` trên các đối tượng khác nhau loại trừ lẫn nhau. Một khối `synchronized` chỉ chặn các luồng khác đang cố gắng đồng bộ hóa trên *cùng một* đối tượng khóa. Việc khóa trên các đối tượng riêng biệt cho phép các luồng thực thi các khối được bảo vệ một cách đồng thời.
* **Ví dụ nhỏ**:
  ```java
  private final Object lock = new Object();
  public void safeIncrement() {
      synchronized (lock) {
          count++;
      }
  }
  ```

## Bế tắc (deadlock)

Bế tắc (deadlock) là một trạng thái lúc chạy (runtime) nơi hai hoặc nhiều luồng bị chặn vĩnh viễn, mỗi luồng chờ một khóa do một luồng khác nắm giữ.

* **Tại sao nó quan trọng**: Deadlock dừng hoàn toàn việc thực thi của các luồng bị ảnh hưởng, dẫn đến tình trạng treo ứng dụng và hệ thống không phản hồi. Chúng không thể tự giải quyết và thường yêu cầu khởi động lại hệ thống.
* **Hiểu lầm phổ biến**: Nhầm lẫn deadlock với đói tài nguyên (starvation) hoặc nghẽn lặp (livelock). Trong một deadlock, các luồng bị đình chỉ về mặt vật lý (trạng thái BLOCKED) và tiêu thụ 0% CPU. Trong một livelock, các luồng đang chạy tích cực và thay đổi trạng thái (tiêu thụ CPU) nhưng không tiến triển.
* **Ví dụ nhỏ**:
  ```java
  // Thread 1 locks A then B; Thread 2 locks B then A
  // Both hold one lock and wait forever for the other
  ```

## Biến nguyên tử (atomic variable)

Các lớp trong `java.util.concurrent.atomic` (như `AtomicInteger`, `AtomicReference`) hỗ trợ lập trình an toàn luồng, không dùng khóa (lock-free) trên các biến đơn lẻ.

* **Tại sao nó quan trọng**: Chúng cho phép truy cập đọc-ghi đồng thời với hiệu suất cao vào một biến duy nhất mà không tốn nhiều tài nguyên cho việc đình chỉ luồng ở cấp hệ điều hành và chuyển đổi ngữ cảnh (context switches) vốn liên quan đến đồng bộ hóa dựa trên khóa.
* **Hiểu lầm phổ biến**: Giả định rằng việc nhóm nhiều thao tác trên biến nguyên tử sẽ làm cho toàn bộ chuỗi thao tác đó trở nên nguyên tử. Ví dụ, `int x = atomicInt.get(); atomicInt.set(x + 1);` là *không* an toàn luồng, mặc dù mỗi lệnh gọi phương thức riêng lẻ là nguyên tử. Bạn phải sử dụng các thao tác hỗn hợp như `compareAndSet` hoặc `incrementAndGet`.
* **Ví dụ nhỏ**:
  ```java
  private final AtomicInteger counter = new AtomicInteger(0);
  public void increment() {
      counter.incrementAndGet(); // Thread-safe atomic increment
  }
  ```

## So sánh và tráo đổi (CAS - Compare-And-Swap)

Một chỉ thị nguyên tử ở cấp độ phần cứng được sử dụng để triển khai đồng bộ hóa không khóa (lock-free). Nó so sánh nội dung của một vị trí bộ nhớ với một giá trị mong đợi cho trước, và chỉ khi chúng bằng nhau, nó mới sửa đổi nội dung đó thành một giá trị mới cho trước.

* **Tại sao nó quan trọng**: Nó là khối xây dựng nền tảng cho tất cả các lớp nguyên tử và cấu trúc dữ liệu không chặn (non-blocking data structures) trong Java. Nó cho phép các luồng cập nhật giá trị một cách đồng thời và an sau bằng cách thử lại (spinning) thay vì chặn trên một khóa.
* **Hiểu lầm phổ biến**: Nghĩ rằng CAS được triển khai thông qua các vòng lặp phần mềm của Java. Phương thức `compareAndSet` của Java ánh xạ trực tiếp tới các chỉ thị hợp ngữ (assembly instructions) gốc của CPU (ví dụ: `lock cmpxchg` trên kiến trúc x86), làm cho nó cực kỳ nhanh.
* **Ví dụ nhỏ**:
  ```java
  AtomicInteger val = new AtomicInteger(10);
  boolean success = val.compareAndSet(10, 11); // returns true, val is now 11
  ```

## Rào cản chu kỳ (CyclicBarrier)

Một công cụ hỗ trợ đồng bộ hóa cho phép một tập hợp các luồng cùng chờ đợi lẫn nhau để đạt đến một điểm rào cản chung (barrier point) trước khi tiếp tục.

* **Tại sao nó quan trọng**: Nó cực kỳ hữu ích trong các thuật toán song song nơi nhiều luồng thực hiện các tác vụ con độc lập và phải đợi tất cả các luồng khác hoàn thành trước khi chuyển sang giai đoạn tiếp theo. Quan trọng là, nó có thể được thiết lập lại (reset) và tái sử dụng sau khi rào cản được kích hoạt (tripped).
* **Hiểu lầm phổ biến**: Nhầm lẫn nó với `CountDownLatch`. Một `CyclicBarrier` yêu cầu các luồng phải chặn một cách chủ động tại điểm rào cản bằng cách sử dụng `await()` để giảm số đếm. Một luồng không thể giảm số đếm rào cản mà không tự chặn chính mình.
* **Ví dụ nhỏ**:
  ```java
  CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("Phase complete!"));
  // 3 threads calling barrier.await() will trip the barrier, run the runnable, and proceed
  ```

## Chốt đếm ngược (CountDownLatch)

Một công cụ hỗ trợ đồng bộ hóa cho phép một hoặc nhiều luồng chờ cho đến khi một tập hợp các thao tác đang được thực hiện trong các luồng khác hoàn thành.

* **Tại sao nó quan trọng**: Nó hoạt động như một cổng một lần (one-shot gate). Nó là lý tưởng để điều phối các giai đoạn khởi động, nơi luồng chính bị chặn thông qua `await()` cho đến khi tất cả các luồng khởi tạo (workers) gọi `countDown()`.
* **Hiểu lầm phổ biến**: Cố gắng tái sử dụng `CountDownLatch`. Một khi số đếm của chốt đạt đến 0, cổng của nó sẽ mở vĩnh viễn và các lệnh gọi `await()` tiếp theo sẽ trả về ngay lập tức. Nó không thể được thiết lập lại; một phiên bản mới phải được tạo.
* **Ví dụ nhỏ**:
  ```java
  CountDownLatch latch = new CountDownLatch(3);
  // Workers call latch.countDown();
  // Main thread blocks on latch.await() until count is 0
  ```

## Trình thực thi (Executor)

Một đối tượng thực thi các tác vụ `Runnable` được gửi vào. Giao diện (interface) này tách biệt việc gửi tác vụ khỏi cơ chế thực thi của từng tác vụ, chẳng hạn như việc sử dụng luồng, lập lịch, v.v.

* **Tại sao nó quan trọng**: Sự tách biệt này cho phép các nhà phát triển tập trung vào việc định nghĩa các tác vụ trong khi cấu hình thực thi luồng (pooling, lập lịch) có thể được quản lý riêng biệt và được sửa đổi mà không làm thay đổi mã nguồn gửi tác vụ.
* **Hiểu lầm phổ biến**: Nghĩ rằng `Executor` là một nhóm luồng (thread pool). `Executor` chỉ là một giao diện chức năng (functional interface) đơn giản với một phương thức duy nhất `execute(Runnable)`. Các giao diện con và các lớp triển khai của nó (như `ExecutorService` và `ThreadPoolExecutor`) mới cung cấp logic quản lý nhóm luồng.
* **Ví dụ nhỏ**:
  ```java
  Executor executor = command -> new Thread(command).start();
  executor.execute(() -> System.out.println("Running task"));
  ```

## ForkJoinPool

Một lớp triển khai `ExecutorService` được thiết kế đặc biệt cho các tác vụ chia để trị (divide-and-conquer) bằng cách sử dụng thuật toán trộm công việc (work-stealing algorithm).

* **Tại sao nó quan trọng**: Nó tối đa hóa việc sử dụng các nhân CPU bằng cách đảm bảo rằng các luồng làm việc rảnh rỗi sẽ trộm các tác vụ từ hàng đợi hai đầu (deques) của các luồng đang bận, giảm đói tài nguyên luồng và giữ cho tất cả các nhân hoạt động.
* **Hiểu lầm phổ biến**: Sử dụng `ForkJoinPool` cho các tác vụ I/O chặn (blocking I/O). Bởi vì `ForkJoinPool` được thiết kế cho các tác vụ tốn nhiều tài nguyên tính toán (compute-intensive), việc chặn các luồng bên trong pool có thể làm các tác vụ khác bị thiếu tài nguyên. Hãy sử dụng một `ThreadPoolExecutor` tiêu chuẩn với một nhóm luồng cố định (fixed pool) hoặc đệm (cached pool) cho các thao tác chặn.
* **Ví dụ nhỏ**:
  ```java
  ForkJoinPool pool = ForkJoinPool.commonPool();
  Long sum = pool.invoke(new SumTask(largeArray, 0, largeArray.length));
  ```
