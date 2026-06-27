# Các Thuật Ngữ Đa Luồng (Multithreading Terms)

Sử dụng tài liệu này khi một từ ngữ trong phần lý thuyết có vẻ quá ngắn gọn. Mỗi thuật ngữ đều đi kèm ý nghĩa, tầm quan trọng, hiểu lầm phổ biến và một ví dụ nhỏ.

## Tiến trình (Process)

Một môi trường thực thi cấp hệ điều hành được cấp phát không gian địa chỉ ảo, bộ nhớ, mô tả tệp (file handle) và ngữ cảnh bảo mật riêng biệt.

- **Tại sao điều này quan trọng**: Hiểu về tiến trình là chìa khóa để phân biệt tính đồng thời ở cấp JVM (chạy trong một tiến trình duy nhất) với tính toán phân tán hoặc hệ thống đa tiến trình. Nó giúp lập trình viên nhận ra rằng bộ nhớ được chia sẻ giữa các luồng trong cùng một tiến trình JVM, trong khi các lỗi ở cấp tiến trình không trực tiếp làm hỏng bộ nhớ ở các tiến trình khác.
- **Hiểu lầm phổ biến**: Lập trình viên thường nhầm lẫn giữa luồng và tiến trình. Một tiến trình không trực tiếp chạy mã; nó là một vùng chứa cho một hoặc nhiều luồng. Giao tiếp liên tiến trình (IPC - Inter-Process Communication) yêu cầu các cơ chế của hệ điều hành (như socket hoặc pipe), trong khi giao tiếp giữa các luồng chỉ đơn giản là sử dụng bộ nhớ heap dùng chung.
- **Ví dụ nhỏ**: Các tiến trình của hệ điều hành có thể được kiểm tra qua các lệnh CLI như `ps` (Linux) hoặc Task Manager (Windows). The JVM runs as a single process (e.g., `java MyClass`).

## Luồng (Thread)

Đơn vị thực thi nhỏ nhất được lên lịch bởi bộ lập lịch của hệ điều hành. Một luồng chạy mã một cách tuần tự và thuộc về chính xác một tiến trình.

- **Tại sao điều này quan trọng**: Các luồng giúp ứng dụng phản hồi nhanh và đạt hiệu năng cao bằng cách thực thi đồng thời nhiều tác vụ trên các CPU nhiều nhân.
- **Hiểu lầm phổ biến**: Nghĩ rằng các luồng luôn chạy hoàn toàn song song tại mọi thời điểm. Trên CPU đơn nhân, các luồng chạy đồng thời thông qua cơ chế phân chia thời gian (Time-slicing - chuyển đổi nhanh), chứ không phải song song. Ngoài ra, một luồng Java thường được ánh xạ tỷ lệ 1:1 với một luồng của hệ điều hành.
- **Ví dụ nhỏ**:
  ```java
  Thread thread = new Thread(() -> System.out.println("Running on a thread"));
  thread.start();
  ```

## Runnable

Một giao diện chức năng (Functional interface) đại diện cho một tác vụ có thể được thực thi đồng thời. Nó định nghĩa một phương thức duy nhất: `public void run()`.

- **Tại sao điều này quan trọng**: Nó tách biệt định nghĩa của một tác vụ khỏi cơ chế thực thi luồng. Điều này cho phép gửi các tác vụ vào nhóm luồng (Thread pool - `ExecutorService`) và thực thi chúng mà không cần tạo luồng thủ công.
- **Hiểu lầm phổ biến**: Nhầm lẫn giữa giao diện `Runnable` và trạng thái luồng `RUNNABLE`. Triển khai `Runnable` chỉ là định nghĩa một tác vụ; nó không bắt đầu một luồng hay đưa bất cứ thứ gì vào trạng thái đang chạy cho đến khi tác vụ đó được truyền vào một đối tượng `Thread` và phương thức `start()` được gọi.
- **Ví dụ nhỏ**:
  ```java
  Runnable task = () -> System.out.println("Runnable task");
  new Thread(task).start();
  ```

## Callable

Một giao diện chức năng tương tự như `Runnable` nhưng đại diện cho một tác vụ có trả về kết quả và có thể ném ra một ngoại lệ đã được kiểm tra (Checked exception). Nó định nghĩa phương thức: `public V call() throws Exception`.

- **Tại sao điều này quan trọng**: Rất cần thiết để lấy kết quả tính toán từ các tác vụ đồng thời hoặc xử lý các hoạt động có thể thất bại với các ngoại lệ đã được kiểm tra (chẳng hạn như truy vấn mạng, tra cứu cơ sở dữ liệu).
- **Hiểu lầm phổ biến**: Giả định rằng `Callable` có thể được truyền trực tiếp vào hàm khởi tạo của `Thread`. Thực tế là không thể. Nó phải được bọc trong một `FutureTask` hoặc gửi vào một `ExecutorService`.
- **Ví dụ nhỏ**:
  ```java
  Callable<Integer> task = () -> 42;
  ExecutorService executor = Executors.newSingleThreadExecutor();
  Future<Integer> future = executor.submit(task);
  Integer result = future.get(); // Blocks until done, returns 42
  executor.shutdown();
  ```

## Start so với Run (Start vs Run)

`start()` là phương thức kiểm soát vòng đời dùng để tạo ra một luồng hệ điều hành mới và lập lịch cho nó chạy bất đồng bộ; `run()` là một cuộc gọi phương thức đồng bộ thông thường chứa logic của tác vụ.

- **Tại sao điều này quan trọng**: Sử dụng sai phương thức sẽ gây ra các lỗi ngầm, trong đó mã chạy đồng bộ trên luồng gọi (thường là luồng `main`) thay vì chạy đồng thời, làm mất đi mọi lợi ích của đa luồng.
- **Hiểu lầm phổ biến**: Gọi `.run()` trên một đối tượng Thread và nghĩ rằng nó đang thực thi trên một luồng mới. Thực chất nó thực thi đồng bộ trong ngăn xếp cuộc gọi của chính luồng gọi.
- **Ví dụ nhỏ**:
  ```java
  Thread t = new Thread(() -> System.out.println(Thread.currentThread().getName()));
  t.run();   // In ra "main"
  t.start(); // In ra "Thread-0"
  ```

## Join

Một phương thức thực thể trên lớp `Thread` giúp chặn luồng đang gọi cho đến khi luồng đích hoàn thành thực thi hoặc luồng đang gọi bị ngắt quãng (Interrupted).

- **Tại sao điều này quan trọng**: Cực kỳ quan trọng để phối hợp hoạt động giữa các luồng, chẳng hạn như chờ các tính toán song song hoàn thành trước khi gộp các kết quả của chúng lại.
- **Hiểu lầm phổ biến**: Nhầm lẫn luồng nào bị chặn. Nếu luồng `main` gọi `threadB.join()`, thì chính luồng `main` mới là luồng bị chặn, CHỨ KHÔNG PHẢI `threadB`. `threadB` vẫn tiếp tục chạy bình thường.
- **Ví dụ nhỏ**:
  ```java
  Thread worker = new Thread(() -> { /* long task */ });
  worker.start();
  worker.join(); // Caller (main thread) blocks until worker is done
  ```

## An toàn luồng (Thread-Safety)

Thuộc tính của một lớp, phương thức hoặc chương trình đảm bảo rằng nó hoạt động chính xác (duy trì các bất biến nội bộ) khi được truy cập bởi nhiều luồng đồng thời, mà không yêu cầu sự phối hợp từ bên ngoài.

- **Tại sao điều này quan trọng**: Ngăn chặn các hành vi thất thường, hư hỏng dữ liệu và sập ứng dụng trong môi trường đồng thời.
- **Hiểu lầm phổ biến**: Tin rằng việc khai báo một trường là `volatile` hoặc bao bọc một tập hợp trong `Collections.synchronizedList` sẽ tự động làm cho toàn bộ lớp hoặc chuỗi các hoạt động trở nên an toàn luồng. An toàn luồng đòi hỏi phải bảo vệ các hoạt động phức hợp (Compound operation) một cách nguyên tử (Atomically).
- **Ví dụ nhỏ**: `AtomicInteger` là an toàn luồng, trong khi phép tăng của một biến `int` thông thường thì không.
  ```java
  AtomicInteger safeCounter = new AtomicInteger(0);
  safeCounter.incrementAndGet(); // Tăng nguyên tử an toàn luồng
  ```

## Tình trạng tranh chấp (Race Condition)

Một lỗi đồng thời trong đó tính đúng đắn của chương trình phụ thuộc vào thời gian tương đối hoặc sự đan xen (Interleaving) thực thi của các luồng.

- **Tại sao điều này quan trọng**: Tình trạng tranh chấp dẫn đến các lỗi làm hỏng dữ liệu một cách ngầm, khó tái hiện, thường vượt qua các bài kiểm tra cục bộ nhưng lại thất bại trong môi trường sản xuất khi chịu tải nặng.
- **Hiểu lầm phổ biến**: Tin rằng tình trạng tranh chấp chỉ xảy ra trên các máy xuồng hiệu năng cao. Chúng có thể xảy ra trên bất kỳ hệ thống nào nơi trạng thái chia sẻ có thể thay đổi (Shared mutable state) được truy cập bởi nhiều luồng mà không có sự đồng bộ hóa.
- **Ví dụ nhỏ**: Hai luồng cùng tăng giá trị của một biến đếm đồng thời.
  ```java
  // Non-atomic read-modify-write: counter++
  counter++; 
  ```

## Khả năng hiển thị dữ liệu (Data Visibility)

Sự đảm bảo rằng các thay đổi do một luồng thực hiện trên một biến chia sẻ sẽ hiển thị với các luồng khác khi chúng đọc biến đó.

- **Tại sao điều này quan trọng**: Do các thanh ghi CPU, các cấp bộ đệm L1/L2/L3 và các tối ưu hóa của trình biên dịch (như sắp xếp lại lệnh - Instruction reordering), các cập nhật bộ nhớ của một luồng có thể vô hình với các luồng khác vô thời hạn, dẫn đến các vòng lặp vô hạn hoặc dữ liệu cũ.
- **Hiểu lầm phổ biến**: Giả định rằng nếu luồng A ghi một giá trị vào một trường, luồng B sẽ thấy nó ngay lập tức vì chúng dùng chung bộ nhớ heap. Nếu không có các rào cản đồng bộ hóa (như `volatile` hoặc `synchronized`), các cập nhật có thể không bao giờ được đẩy về bộ nhớ chính hoặc không bao giờ được B đọc.
- **Ví dụ nhỏ**:
  ```java
  private volatile boolean flag = true; // volatile đảm bảo khả năng hiển thị dữ liệu
  ```
