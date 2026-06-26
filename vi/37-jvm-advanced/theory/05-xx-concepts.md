# JVM Nâng Cao - Phần 5 (Advanced JVM - Part 5)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này trình bày một phần trọng tâm của **JVM Nâng cao (Advanced JVM)**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, không chỉ là các từ vựng rời rạc.

## Khái Quát Nội Dung (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `-XX` | -XX là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi. |
| `Basic profiling` | Phân tích hiệu năng cơ bản (Basic profiling) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi. |
| `Memory dump` | Sao chụp bộ nhớ (Memory dump) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi. |
| `Thread dump` | Sao chụp luồng (Thread dump) là một ảnh chụp nhanh (snapshot) về trạng thái và dấu vết ngăn xếp (stack trace) của tất cả các luồng đang hoạt động trong một JVM. |

## Ghi Chú Chi Tiết (Detailed Notes)

### -XX

-XX là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi.

Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được cho phép, và chế độ thất bại. Hãy xem lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

Kiểm tra thực tế (Practical check):

- Định nghĩa `-XX` trong một câu.
- Nhận biết `-XX` trong code, câu lệnh, tài liệu, hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi (tradeoff) liên quan đến `-XX`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Khi đọc code, hãy hỏi: XX thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

### Phân tích hiệu năng cơ bản (Basic profiling)

Phân tích hiệu năng cơ bản (Basic profiling) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi.

Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được cho phép, và chế độ thất bại. Hãy xem lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

Kiểm tra thực tế (Practical check):

- Định nghĩa `Basic profiling` trong một câu.
- Nhận biết `Basic profiling` trong code, câu lệnh, tài liệu, hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi (tradeoff) liên quan đến `Basic profiling`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Khi đọc code, hãy hỏi: `Basic profiling` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

### Sao chụp bộ nhớ (Memory dump)

Sao chụp bộ nhớ (Memory dump) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi.

Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được cho phép, và chế độ thất bại. Hãy xem lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

Kiểm tra thực tế (Practical check):

- Định nghĩa `Memory dump` trong một câu.
- Nhận biết `Memory dump` trong code, câu lệnh, tài liệu, hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi (tradeoff) liên quan đến `Memory dump`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Khi đọc code, hãy hỏi: `Memory dump` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

### Sao chụp luồng (Thread dump)

Sao chụp luồng (Thread dump) là một ảnh chụp nhanh (snapshot) của tất cả các luồng đang hoạt động bên trong JVM, hiển thị trạng thái (RUNNABLE, BLOCKED, WAITING) và dấu vết ngăn xếp (stack trace) đầy đủ cho mỗi luồng.

Khái niệm này quan trọng vì nó cho phép các lập trình viên chẩn đoán khóa chết (deadlock), tranh chấp luồng (thread contention), vòng lặp vô hạn, và khóa tài nguyên trong các ứng dụng đồng thời.

Kiểm tra thực tế (Practical check):

- Định nghĩa `Thread dump` trong một câu.
- Nhận biết `Thread dump` trong code, câu lệnh, tài liệu, hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi (tradeoff) liên quan đến `Thread dump`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- `new Thread(task).start()` bắt đầu công việc trên một luồng khác.

## Ví Dụ Code (Code Examples)

### CLI Command to capture thread/heap dumps
```bash
# Capture thread dump (PID: 1234)
jstack 1234 > thread_dump.txt

# Capture heap dump (PID: 1234)
jmap -dump:format=b,file=heap_dump.hprof 1234
```

## Các Sai Lầm Thường Gặp (Common Mistakes)

- **Phân tích thủ công heap dump**: Các tệp heap dump là các tệp nhị phân và có thể cực kỳ lớn. Không mở chúng bằng các trình soạn thảo văn bản thông thường. Luôn sử dụng các công cụ chuyên dụng như Eclipse Memory Analyzer (MAT) hoặc VisualVM.
- **Không sao chụp luồng (thread dump) khi xảy ra deadlock**: Khi các luồng của ứng dụng bị treo, hãy ngay lập tức chụp từ 2 đến 3 thread dump cách nhau vài giây để xác định luồng nào đang bị chặn trên màn giám sát (monitor) nào.

## Câu Hỏi Ôn Tập Thường Gặp (Common Review Prompts)

- Khái niệm nào ở đây là quy tắc biên dịch (compile-time)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi thời gian chạy (runtime)?
- Khái niệm nào ở đây dễ là bẫy phỏng vấn?

## Tại sao Phân loại Flag JVM tồn tại (Why JVM Flag Classifications Exist)

JVM tổ chức các tùy chọn cấu hình dòng lệnh của mình thành ba loại riêng biệt—Tiêu chuẩn (Standard), Không tiêu chuẩn (`-X`), và Dành cho nhà phát triển/Thử nghiệm (`-XX`)—để quản lý tính ổn định của flag, khả năng di động giữa các nhà cung cấp (vendor portability), và các tính năng thử nghiệm. **Các tùy chọn tiêu chuẩn** (Standard options - ví dụ: `-classpath`, `-verbose:gc`) được đảm bảo sẽ được hỗ trợ trên tất cả các nhà cung cấp và phiên bản JVM tuân thủ tiêu chuẩn, đảm bảo tính ổn định cơ bản của dòng lệnh. **Các tùy chọn không tiêu chuẩn** (Non-Standard options - bắt đầu bằng tiền tố `-X`, chẳng hạn như `-Xms` và `-Xmx`) tùy chỉnh bố cục bộ nhớ hoặc cài đặt thực thi cụ thể cho HotSpot, nhưng không được đảm bảo hỗ trợ bởi các nhà cung cấp khác và có thể thay đổi mà không báo trước. **Các tùy chọn dành cho nhà phát triển, thử nghiệm hoặc không ổn định** (bắt đầu bằng tiền tố `-XX`, chẳng hạn như `-XX:NewRatio` hoặc `-XX:+UseG1GC`) cho phép tùy chỉnh sâu các thuật toán dọn rác GC, chính sách trình biên dịch JIT, và các phân vùng bộ nhớ con. Các flag này yêu cầu mở khóa rõ ràng (thông qua `-XX:+UnlockDiagnosticVMOptions` hoặc `-XX:+UnlockExperimentalVMOptions`) vì việc sử dụng không đúng cách có thể làm giảm hiệu năng nghiêm trọng, gây treo JVM, hoặc dẫn đến hành vi thời gian chạy không xác định.

### Mô Hình Tư Duy: Phân Loại Flag JVM và Tối Ưu Hóa Phân Vùng Heap (Mental Model: JVM Flag Categories and Heap Boundary Tuning)

```text
  JVM Options Spectrum:
  [ Standard: -cp, -version ]  ===> Supported universally, stable
  [ Non-Standard: -Xms, -Xmx ] ===> HotSpot-specific heap sizing, subject to change
  [ Experimental: -XX:NewRatio ]==> System developer parameters, unstable/requires unlock
  
  Heap Sizing Flags Memory Layout:
  |<---------------------------- -Xmx (Max Heap Size) ----------------------------->|
  |<--------- -Xms (Initial Heap Size) --------->|
  +----------------------------------------------+---------------------------------+
  |      Young Gen (Eden + S0 + S1)              |            Old Gen              |
  |  (Proportion tuned via -XX:NewRatio)         |                                 |
  +----------------------------------------------+---------------------------------+
```

### Ví Dụ Code (Code Example)

Dưới đây là một chương trình Java có thể chạy được để truy vấn các tham số vùng nhớ Heap nhằm hiển thị cách các tùy chọn dòng lệnh thiết lập các ranh giới bộ nhớ.

```java
package theory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class HeapTuningInspection {
    public static void main(String[] args) {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        
        long initHeap = heapMemoryUsage.getInit();
        long maxHeap = heapMemoryUsage.getMax();
        
        System.out.println("Initial Heap (-Xms): " + (initHeap / 1024 / 1024) + " MB");
        System.out.println("Maximum Heap (-Xmx): " + (maxHeap / 1024 / 1024) + " MB");
    }
}
/* Output (Default or when run with -Xms256m -Xmx512m):
Initial Heap (-Xms): 256 MB
Maximum Heap (-Xmx): 512 MB
*/
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)


```text
Cấu hình các flag tiêu chuẩn
  → Đảm bảo khả năng tương thích giữa các nhà cung cấp
  → Thêm `-Xms` và `-Xmx` thiết lập giới hạn ranh giới trên vùng nhớ Heap của Java
  → Thêm `-XX:NewRatio=2` phân bổ dung lượng cho Old Gen (Thế hệ cũ) gấp đôi Young Gen (Thế hệ trẻ)
  → Mở khóa các flag thử nghiệm `-XX` kích hoạt các tính năng nâng cao như Shenandoah
  → Đạt được hiệu năng JVM được tinh chỉnh tối ưu cho khối lượng công việc mục tiêu.
```


## Liên Kết Tham Khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/specs/man/java.html (Tài liệu tham khảo các tùy chọn công cụ dòng lệnh Java)
