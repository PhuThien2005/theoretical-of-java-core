# Thuật ngữ Đa luồng (Multithreading Terms)

Sử dụng file này khi một từ ngữ trong phần lý thuyết có cảm giác quá ngắn gọn. Mỗi thuật ngữ đều có ý nghĩa, tầm quan trọng, điểm dễ gây nhầm lẫn và một ví dụ nhỏ.

## Process

Môi trường thực thi cấp hệ điều hành được phân bổ không gian địa chỉ ảo, bộ nhớ, các thẻ quản lý tệp và ngữ cảnh bảo mật riêng biệt.

- **Tại sao nó quan trọng**: Hiểu về tiến trình (Process) là chìa khóa để phân biệt cơ chế đa luồng cấp JVM (chạy bên trong một tiến trình duy nhất) với tính toán phân tán hoặc hệ thống đa tiến trình. Nó giúp các nhà phát triển nhận ra rằng bộ nhớ được chia sẻ giữa các luồng trong một tiến trình JVM duy nhất, trong khi các lỗi ở cấp tiến trình không trực tiếp làm hỏng bộ nhớ của các tiến trình khác.
- **Nhầm lẫn phổ biến**: Lập trình viên thường nhầm lẫn giữa luồng và tiến trình. Một tiến trình không trực tiếp thực thi mã nguồn; nó là một vùng chứa cho một hoặc nhiều luồng. IPC (Inter-Process Communication - Truyền thông liên tiến trình) yêu cầu các cơ chế của hệ điều hành (như socket hoặc pipe), trong khi truyền thông liên luồng chỉ đơn giản là sử dụng bộ nhớ heap dùng chung.
- **Ví dụ nhỏ**: Các tiến trình của hệ điều hành có thể được kiểm tra thông qua các lệnh CLI như `ps` (Linux) hoặc Task Manager (Windows). JVM chạy như một tiến trình đơn lẻ (ví dụ: `java MyClass`).

## Thread

Đơn vị thực thi nhỏ nhất được lên lịch bởi bộ lập lịch của hệ điều hành. Một luồng thực thi mã một cách tuần tự và thuộc về chính xác một tiến trình.

- **Tại sao nó quan trọng**: Các luồng giúp ứng dụng phản hồi nhanh chóng và đạt hiệu năng cao bằng cách thực thi nhiều nhiệm vụ đồng thời trên các CPU đa nhân.
- **Nhầm lẫn phổ biến**: Nghĩ rằng các luồng luôn chạy song song hoàn toàn vào mọi lúc. Trên CPU đơn nhân, các luồng chạy đồng thời thông qua cơ chế phân chia thời gian (time-slicing - chuyển đổi nhanh), chứ không phải song song. Ngoài ra, một luồng Java thường được ánh xạ tỷ lệ 1:1 với một luồng của hệ điều hành.
- **Ví dụ nhỏ**:
  ```java
  Thread thread = new Thread(() -> System.out.println("Running on a thread"));
  thread.start();
  ```

## Runnable

Một giao diện chức năng (functional interface) đại diện cho một nhiệm vụ có thể được thực thi đồng thời. Nó định nghĩa một phương thức duy nhất: `public void run()`.

- **Tại sao nó quan trọng**: Nó phân tách (decouple) định nghĩa của một nhiệm vụ khỏi cơ chế thực thi luồng. Điều này cho phép các nhiệm vụ được gửi đến các nhóm luồng (thread pools - `ExecutorService`) và được thực thi mà không cần tạo luồng thủ công.
- **Nhầm lẫn phổ biến**: Nhầm lẫn `Runnable` với trạng thái luồng `RUNNABLE`. Triển khai `Runnable` chỉ là định nghĩa một nhiệm vụ; nó không bắt đầu một luồng hoặc đưa bất kỳ thứ gì vào trạng thái đang chạy cho đến khi được truyền vào một `Thread` và gọi `start()`.
- **Ví dụ nhỏ**:
  ```java
  Runnable task = () -> System.out.println("Runnable task");
  new Thread(task).start();
  ```

## Callable

Một giao diện chức năng tương tự như `Runnable` nhưng đại diện cho một nhiệm vụ trả về một kết quả và có thể ném ra một ngoại lệ có kiểm tra (checked exception). Nó định nghĩa phương thức `public V call() throws Exception`.

- **Tại sao nó quan trọng**: Cần thiết để lấy kết quả tính toán từ các nhiệm vụ đồng thời hoặc xử lý các hoạt động có thể thất bại với các ngoại lệ có kiểm tra (ví dụ: truy vấn mạng, tra cứu cơ sở dữ liệu).
- **Nhầm lẫn phổ biến**: Giả định rằng `Callable` có thể được truyền trực tiếp vào hàm dựng của `Thread`. Thực tế thì không thể. Nó phải được bọc trong một `FutureTask` hoặc gửi đến một `ExecutorService`.
- **Ví dụ nhỏ**:
  ```java
  Callable<Integer> task = () -> 42;
  ExecutorService executor = Executors.newSingleThreadExecutor();
  Future<Integer> future = executor.submit(task);
  Integer result = future.get(); // Chặn cho đến khi hoàn thành, trả về 42
  executor.shutdown();
  ```

## Start vs Run

`start()` là một phương thức kiểm soát vòng đời tạo ra một luồng hệ điều hành mới và lên lịch thực thi bất đồng bộ; `run()` là một phương thức gọi đồng bộ tiêu chuẩn chứa logic của nhiệm vụ.

- **Tại sao nó quan trọng**: Sử dụng sai phương thức sẽ gây ra các lỗi thầm lặng trong đó mã nguồn chạy đồng bộ trên luồng gọi (thường là luồng `main`) thay vì chạy đồng thời, làm mất đi mọi lợi ích của đa luồng.
- **Nhầm lẫn phổ biến**: Gọi `.run()` trên một đối tượng luồng và nghĩ rằng nó thực thi trên một luồng mới. Thực tế nó thực thi đồng bộ trong ngăn xếp cuộc gọi (call stack) của người gọi.
- **Ví dụ nhỏ**:
  ```java
  Thread t = new Thread(() -> System.out.println(Thread.currentThread().getName()));
  t.run();   // In ra "main"
  t.start(); // In ra "Thread-0"
  ```

## Join

Một phương thức thể hiện (instance method) trên `Thread` nhằm chặn luồng gọi cho đến khi luồng mục tiêu hoàn thành thực thi hoặc luồng gọi bị ngắt (interrupted).

- **Tại sao nó quan trọng**: Cực kỳ quan trọng để điều phối các luồng, chẳng hạn như chờ các tính toán song song hoàn thành trước khi tổng hợp kết quả của chúng.
- **Nhầm lẫn phổ biến**: Nhầm lẫn luồng nào bị chặn. Nếu luồng `main` gọi `threadB.join()`, thì chính luồng `main` bị chặn, KHÔNG phải `threadB`. Luồng `threadB` vẫn tiếp tục chạy bình thường.
- **Ví dụ nhỏ**:
  ```java
  Thread worker = new Thread(() -> { /* nhiệm vụ dài hạn */ });
  worker.start();
  worker.join(); // Người gọi (luồng main) bị chặn cho đến khi worker hoàn thành
  ```

## Thread-Safety

Thuộc tính của một lớp, phương thức hoặc chương trình khẳng định rằng nó hoạt động chính xác (duy trì các bất biến nội bộ) khi được truy cập bởi nhiều luồng đồng thời, mà không yêu cầu điều phối từ bên ngoài.

- **Tại sao nó quan trọng**: Ngăn chặn hành vi thất thường, hỏng dữ liệu và đổ vỡ chương trình trong môi trường đồng thời.
- **Nhầm lẫn phổ biến**: Tin rằng việc khai báo một trường là `volatile` hoặc bọc một collection trong `Collections.synchronizedList` sẽ tự động làm cho toàn bộ lớp hoặc chuỗi các thao tác trở nên an toàn luồng. An toàn luồng đòi hỏi phải bảo vệ các thao tác phức hợp (compound operations) một cách nguyên tử.
- **Ví dụ nhỏ**: `AtomicInteger` là an toàn luồng, trong khi thao tác tăng `int` thô thì không.
  ```java
  AtomicInteger safeCounter = new AtomicInteger(0);
  safeCounter.incrementAndGet(); // Thao tác tăng nguyên tử an toàn luồng
  ```

## Race Condition

Một lỗi đồng thời khi tính chính xác của chương trình phụ thuộc vào thời gian tương đối hoặc sự đan xen (interleaving) thực thi của các luồng.

- **Tại sao nó quan trọng**: Điều kiện tranh đoạt dẫn đến các lỗi hỏng dữ liệu thầm lặng, cực kỳ khó tái hiện, thường vượt qua các bài kiểm tra cục bộ nhưng lại thất bại khi chạy trên môi trường sản xuất dưới tải nặng.
- **Nhầm lẫn phổ biến**: Tin rằng điều kiện tranh đoạt chỉ xảy ra trên các máy chủ hiệu năng cao. Chúng có thể xảy ra trên bất kỳ hệ thống nào nơi trạng thái khả biến dùng chung (shared mutable state) được truy cập bởi nhiều luồng mà không có đồng bộ hóa.
- **Ví dụ nhỏ**: Hai luồng đồng thời tăng cùng một biến đếm.
  ```java
  // Thao tác đọc-sửa-ghi không nguyên tử: counter++
  counter++; 
  ```

## Data Visibility

Sự đảm bảo rằng các thay đổi do một luồng thực hiện trên một biến dùng chung sẽ được nhìn thấy bởi các luồng khác khi chúng đọc biến đó.

- **Tại sao nó quan trọng**: Do các thanh ghi CPU (CPU registers), bộ nhớ đệm L1/L2/L3 và các tối ưu hóa của trình biên dịch (như sắp xếp lại lệnh - instruction reordering), các cập nhật bộ nhớ bởi một luồng có thể không hiển thị với các luồng khác vô thời hạn, dẫn đến các vòng lặp vô hạn hoặc dữ liệu cũ/lỗi thời (stale data).
- **Nhầm lẫn phổ biến**: Giả định rằng nếu luồng A ghi một giá trị vào một trường, luồng B sẽ nhìn thấy nó ngay lập tức vì chúng dùng chung một bộ nhớ heap. Nếu không có các rào cản đồng bộ hóa (như `volatile` hoặc `synchronized`), các cập nhật có thể không bao giờ truyền đến bộ nhớ chính hoặc không được B đọc.
- **Ví dụ nhỏ**:
  ```java
  private volatile boolean flag = true; // volatile đảm bảo hiển thị dữ liệu
  ```
