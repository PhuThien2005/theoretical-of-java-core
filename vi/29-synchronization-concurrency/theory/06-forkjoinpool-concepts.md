# Đồng bộ hóa và Đồng thời - Phần 6

## Mục Tiêu Học Tập

Tài liệu này trình bày về khung Fork/Join (`ForkJoinPool`, `RecursiveTask`, `RecursiveAction`) và các chi tiết thực thi của Luồng song song (Parallel Stream). Hãy nghiên cứu từng khái niệm dưới dạng một quy tắc Java thực tế, thay vì chỉ học từ vựng riêng lẻ.

## Nội Dung Tổng Quan

| Khái niệm | Điều cần biết |
| --- | --- |
| `ForkJoinPool` | Một bể chứa trình thực thi (executor pool) chuyên dụng được thiết kế cho các tác vụ chia để trị (divide-and-conquer) bằng cách sử dụng thuật toán trộm công việc (work-stealing algorithm). |
| `Parallel Stream` | Một chế độ thực thi luồng chia nhỏ dữ liệu của luồng và thực thi song song các giai đoạn xử lý, sử dụng `ForkJoinPool` chung. |

## Ghi Chú Chi Tiết

### ForkJoinPool và Trộm công việc (Work-Stealing)
`ForkJoinPool` triển khai một **thuật toán trộm công việc (work-stealing algorithm)**.
* Mỗi luồng công việc (worker thread) duy trì hàng đợi hai đầu (deque) các tác vụ của riêng nó.
* Khi một luồng công việc hết tác vụ, nó sẽ trộm các tác vụ con đang chờ từ phía **sau (back/tail)** của deque của một luồng đang bận khác. Điều này giúp tất cả các lõi CPU hoạt động hết công suất với mức độ tranh chấp tối thiểu.
* Các tác vụ con được định nghĩa bằng cách sử dụng:
  1. `RecursiveAction`: dành cho các tác vụ không trả về kết quả (`void`).
  2. `RecursiveTask<V>`: dành cho các tác vụ trả về kết quả kiểu `V`.

```java
import java.util.concurrent.RecursiveTask;

public class SumTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 1000;
    private final int[] array;
    private final int start;
    private final int end;

    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if ((end - start) <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) sum += array[i];
            return sum;
        } else {
            int mid = start + (end - start) / 2;
            SumTask left = new SumTask(array, start, mid);
            SumTask right = new SumTask(array, mid, end);
            
            left.fork(); // Chạy tác vụ con bên trái một cách bất đồng bộ
            long rightResult = right.compute(); // Chạy tác vụ con bên phải một cách đồng bộ
            long leftResult = left.join(); // Chờ kết quả từ tác vụ con bên trái
            
            return leftResult + rightResult;
        }
    }
}
```

### Luồng song song và Bể chứa chung (Common Pool)
Gọi `.parallelStream()` hoặc `.parallel()` trên một luồng hiện có sẽ chia nhỏ các phần tử của luồng thành các khúc (chunk) bằng cách sử dụng một `Spliterator` và xử lý chúng một cách đồng thời.
* **Dưới nền tảng**: Tất cả các luồng song song chạy trên một bể chứa chung trong toàn bộ JVM: `ForkJoinPool.commonPool()`.
* **Quan trọng**: Vì bể chứa này được chia sẻ trên toàn bộ JVM, bất kỳ hoạt động chặn (blocking) hoặc chậm chạp nào được thực thi bên trong một luồng song song sẽ làm đói (starve) *tất cả* các luồng song song khác trong ứng dụng.

> Xem thêm: Cách Parallel Stream hoạt động và các nguy cơ về hiệu năng, được trình bày chi tiết trong [Ch.23 - Stream API](../../23-stream-api/README.md).

---

## Ví Dụ Thực Tế: Tính Tổng Mảng Lớn Song Song

### Vấn đề
Tính tổng một mảng gồm 100 triệu số nguyên. Một vòng lặp đơn luồng tốn quá nhiều thời gian, và việc tự tạo luồng thủ công lại phát sinh quá nhiều chi phí điều phối.

### Giải pháp
Sử dụng ForkJoinPool với RecursiveTask để chia nhỏ và tính tổng mảng một cách song song.
```java
import java.util.concurrent.ForkJoinPool;

public class ParallelSum {
    public static void main(String[] args) {
        int[] data = new int[10_000_000];
        for (int i = 0; i < data.length; i++) data[i] = 1;

        ForkJoinPool pool = ForkJoinPool.commonPool();
        SumTask task = new SumTask(data, 0, data.length);
        
        long totalSum = pool.invoke(task);
        System.out.println("Sum: " + totalSum);
    }
}
```

---

## Sai Lầm Thường Gặp

### 1. Chặn Bể Chứa ForkJoinPool Chung
Chạy các truy vấn cơ sở dữ liệu chặn, các cuộc gọi HTTP, hoặc đọc tệp chặn bên trong một luồng song song.
```java
// LỖI: Làm đói bể chứa dùng chung của JVM!
list.parallelStream().forEach(url -> {
    try {
        HttpConnection.fetch(url); // Chặn luồng công việc
    } catch (Exception e) {}
});
```
* **Cách khắc phục**: Sử dụng một bể chứa luồng chuyên dụng (thông qua một `ExecutorService` tùy chỉnh) cho các tác vụ I/O chặn. Hãy giữ luồng song song duy nhất cho các tính toán chuyên sâu về CPU.

### 2. Giả định rằng Luồng Song Song Luôn Nhanh Hơn
Luồng song song phát sinh thêm chi phí (chia tách nguồn dữ liệu, quản lý các deque tác vụ, gộp các kết quả con). Đối với các bộ sưu tập nhỏ, hoặc các bộ sưu tập tốn nhiều chi phí để chia nhỏ (như `LinkedList`), luồng song song có thể chậm hơn đáng kể so với một vòng lặp tuần tự tiêu chuẩn.
* **Quy tắc**: Chỉ sử dụng luồng song song khi:
  1. Kích thước dữ liệu lớn (N) và tính toán trên mỗi phần tử tốn kém (Q), sao cho tích $N \times Q$ là lớn.
  2. Bộ sưu tập dễ dàng chia nhỏ (như `ArrayList` hoặc mảng, trái ngược với `LinkedList` hoặc `BufferedReader.lines()`).

## Tại Sao ForkJoinPool Sử Dụng Trộm Công Việc (Work-Stealing)

ForkJoinPool được tối ưu hóa cho xử lý chia để trị bằng cách sử dụng thuật toán trộm công việc (work-stealing) để tối đa hóa việc tận dụng các lõi CPU. Trong các bể chứa luồng tiêu chuẩn, một hàng đợi đơn lẻ có thể trở thành nút thắt cổ chai về tranh chấp khóa, và các luồng có thể rơi vào trạng thái rảnh rỗi nếu các tác vụ được giao hoàn thành sớm. Để ngăn chặn điều này, ForkJoinPool chỉ định cho mỗi luồng công việc một hàng đợi hai đầu (deque) riêng của nó. Một luồng công việc xử lý các tác vụ của chính nó bằng cách đẩy (push) các tác vụ con mới vào và lấy (pop) các tác vụ ra khỏi phần đầu (head) của deque (hoạt động giống như ngăn xếp LIFO). Khi một luồng công việc hết tác vụ, nó sẽ trộm một tác vụ từ phần đuôi (tail) của deque của một luồng khác (hoạt động giống như hàng đợi FIFO), giúp giảm thiểu tranh chấp và giữ cho tất cả các luồng luôn hoạt động.

### Mô Hình Tư Duy: Hàng Đợi Hai Đầu Trộm Công Việc
```
Luồng công việc 1 (Đang bận)          Luồng công việc 2 (Rảnh rỗi)
   │                                     │
   ▼ (Đẩy/Lấy từ phần Đầu)               ▼ (Hết việc)
┌────────────┐                        ┌────────────┐
│ Task A [H] │                        │   Empty    │
├────────────┤                        └────────────┘
│ Task B [T] │ ◄─────────────────────────┘ (Trộm Task B từ phần đuôi)
└────────────┘
```

### Ví Dụ Mã Nguồn
```java
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class WorkStealingDemo {
    static class Sum extends RecursiveTask<Long> {
        private final int start, end;
        Sum(int s, int e) { this.start = s; this.end = e; }
        @Override protected Long compute() {
            if (end - start <= 1) return (long) start;
            int mid = (start + end) / 2;
            Sum left = new Sum(start, mid);
            Sum right = new Sum(mid, end);
            left.fork(); // Được đẩy vào đầu deque
            return right.compute() + left.join(); // Có thể trộm công việc ở đây
        }
    }
    public static void main(String[] args) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        long result = pool.invoke(new Sum(1, 5));
        System.out.println("Sum: " + result); // Output: Sum: 10
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả
Tác vụ con được phân nhánh (fork) &rarr; Được đẩy vào đầu deque của luồng &rarr; Luồng thực thi các tác vụ của chính nó theo thứ tự LIFO &rarr; Một luồng khác hoàn thành các tác vụ của nó và trở nên rảnh rỗi &rarr; Luồng rảnh rỗi quét các hàng đợi khác &rarr; Trộm tác vụ từ đuôi deque của luồng bận theo thứ tự FIFO &rarr; Giảm thiểu tranh chấp lõi &rarr; Các luồng phần cứng luôn bận rộn &rarr; Tối đa hóa tốc độ xử lý.
