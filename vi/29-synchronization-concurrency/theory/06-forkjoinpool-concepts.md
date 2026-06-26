# Đồng bộ hóa và Đồng thời - Phần 6 (Synchronization and Concurrency - Part 6)

## Mục tiêu học tập (Learning Goal)

Tệp này bao gồm khung công tác Fork/Join (`ForkJoinPool`, `RecursiveTask`, `RecursiveAction`) và các chi tiết thực thi của Luồng (Thread) song song (Parallel Streams). Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, không phải là từ vựng cô lập.

## Đề cương chi tiết (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `ForkJoinPool` | Một nhóm thực thi chuyên biệt được thiết kế cho các tác vụ chia để trị (divide-and-conquer) bằng cách sử dụng thuật toán trộm công việc (work-stealing algorithm). |
| `Parallel Stream` | Một chế độ thực thi luồng dữ liệu (stream) giúp phân chia dữ liệu luồng và thực thi các giai đoạn xử lý song song, sử dụng `ForkJoinPool` chung. |

## Chi tiết tài liệu học tập (Detailed Notes)

### ForkJoinPool và Cơ chế trộm công việc (ForkJoinPool and Work-Stealing)
The `ForkJoinPool` triển khai một **thuật toán trộm công việc (work-stealing algorithm)**.
* Mỗi luồng làm việc duy trì hàng đợi hai đầu (deque) riêng của các tác vụ.
* Khi một luồng làm việc hết tác vụ, nó sẽ trộm các tác vụ con đang chờ xử lý từ **phía sau** (back) của hàng đợi thuộc về một luồng bận rộn khác. Điều này giúp tất cả các nhân CPU hoạt động tối đa với sự tranh chấp tối thiểu.
* Các tác vụ con được định nghĩa bằng cách sử dụng:
  1. `RecursiveAction`: cho các tác vụ không trả về kết quả (`void`).
  2. `RecursiveTask<V>`: cho các tác vụ trả về kết quả kiểu `V`.

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
            
            left.fork(); // Run left subtask asynchronously
            long rightResult = right.compute(); // Run right subtask synchronously
            long leftResult = left.join(); // Wait for left subtask result
            
            return leftResult + rightResult;
        }
    }
}
```

### Luồng song song và Nhóm luồng chung (Parallel Streams and the Common Pool)
Gọi `.parallelStream()` hoặc `.parallel()` trên một luồng dữ liệu hiện có sẽ chia tách các phần tử của luồng thành các khối (chunks) bằng cách sử dụng `Spliterator` và xử lý chúng đồng thời.
* **Cơ chế hoạt động**: Tất cả các luồng song song đều chạy trên một nhóm chung dùng chung toàn JVM: `ForkJoinPool.commonPool()`.
* **Quan trọng**: Vì nhóm luồng này được chia sẻ trên toàn JVM, bất kỳ thao tác chặn (blocking) hoặc chậm chạp nào được thực thi bên trong một luồng song song sẽ làm các luồng song song khác trong ứng dụng bị thiếu tài nguyên.

---

## Tình huống nghiên cứu: Tổng song song của một mảng lớn (Case Study: Parallel Sum of Large Array)

### Vấn đề (Problem)
Tính tổng một mảng gồm 100 triệu số nguyên. Một vòng lặp đơn luồng tốn quá nhiều thời gian, còn việc tự tạo luồng thủ công lại tạo ra quá nhiều chi phí điều phối.

### Giải pháp (Solution)
Sử dụng `ForkJoinPool` với `RecursiveTask` để chia nhỏ và tính tổng mảng song song.
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

## Các sai lầm thường gặp (Common Mistakes)

### 1. Chặn nhóm ForkJoinPool chung
Chạy các truy vấn cơ sở dữ liệu bị chặn, các cuộc gọi HTTP, hoặc các thao tác đọc tệp bên trong một luồng song song.
```java
// BUG: Starves the JVM's shared pool!
list.parallelStream().forEach(url -> {
    try {
        HttpConnection.fetch(url); // Blocks worker thread
    } catch (Exception e) {}
});
```
* **Sửa lỗi**: Sử dụng một nhóm luồng chuyên biệt (thông qua một `ExecutorService` tùy chỉnh) cho các tác vụ I/O chặn. Giữ các luồng song song hoàn toàn cho các tính toán tốn nhiều tài nguyên CPU.

### 2. Giả định rằng luồng song song luôn nhanh hơn
Các luồng song song gây ra thêm chi phí (chia nhỏ nguồn dữ liệu, quản lý hàng đợi tác vụ, hợp nhất các kết quả con). Đối với các tập hợp nhỏ hoặc các tập hợp tốn kém chi phí để chia tách (như `LinkedList`), các luồng song song có thể chậm hơn đáng kể so với một vòng lặp tuần tự tiêu chuẩn.
* **Quy tắc**: Chỉ sử dụng luồng song song khi:
  1. Kích thước dữ liệu lớn (N) và tính toán trên mỗi phần tử tốn nhiều chi phí (Q), sao cho $N \times Q$ lớn.
  2. Tập hợp dữ liệu dễ chia tách (như `ArrayList` hoặc các mảng, không giống như `LinkedList` hoặc `BufferedReader.lines()`).

## Tại sao ForkJoinPool sử dụng cơ chế trộm công việc (Why ForkJoinPool Uses Work-Stealing)

`ForkJoinPool` được tối ưu hóa cho xử lý chia để trị (divide-and-conquer) bằng cách sử dụng thuật toán trộm công việc (work-stealing) nhằm tối đa hóa hiệu suất sử dụng nhân CPU. Trong các nhóm luồng tiêu chuẩn, một hàng đợi đơn lẻ có thể trở thành điểm nghẽn tranh chấp khóa, và các luồng có thể rảnh rỗi nếu các tác vụ được chỉ định của chúng hoàn thành sớm. Để ngăn chặn điều này, `ForkJoinPool` cấp cho mỗi luồng làm việc hàng đợi hai đầu (deque) riêng tư của nó. Một luồng làm việc xử lý các tác vụ của chính nó bằng cách đẩy các tác vụ con mới vào đầu, và lấy các tác vụ ra khỏi đầu của hàng đợi deque (hoạt động như một ngăn xếp LIFO). Khi một luồng làm việc hết tác vụ, nó sẽ trộm một tác vụ từ cuối hàng đợi của luồng khác (hoạt động như hàng đợi FIFO), giảm thiểu tranh chấp khóa và giữ cho tất cả các luồng hoạt động.

### Mô hình tư duy: Hàng đợi trộm công việc (Mental Model: Work-Stealing Deques)
```
Worker 1 (Busy)                       Worker 2 (Idle)
   │                                     │
   ▼ (Push/Pop Head)                     ▼ (Out of work)
┌────────────┐                        ┌────────────┐
│ Task A [H] │                        │   Empty    │
│────────────│                        └────────────┘
│ Task B [T] │ ◄─────────────────────────┘ (Steals Task B from tail)
└────────────┘
```

### Ví dụ mã nguồn (Code Example)
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
            left.fork(); // Pushed to deque head
            return right.compute() + left.join(); // May steal here
        }
    }
    public static void main(String[] args) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        long result = pool.invoke(new Sum(1, 5));
        System.out.println("Sum: " + result); // Output: Sum: 10
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Tác vụ con được rẽ nhánh (forked)
  → Được đẩy vào đầu hàng đợi deque của luồng
  → Luồng thực thi các tác vụ riêng theo cơ chế LIFO
  → Một luồng khác hoàn thành các tác vụ của nó và trở nên rảnh rỗi
  → Luồng rảnh rỗi quét các hàng đợi khác
  → Trộm tác vụ từ cuối hàng đợi deque của luồng đang bận rộn theo cơ chế FIFO
  → Sự tranh chấp nhân được giảm thiểu
  → Các luồng phần cứng luôn được bận rộn
  → Tốc độ xử lý được tối đa hóa.
```

