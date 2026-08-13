# JVM nâng cao - Phần 5 (Advanced JVM - Part 5)

## Đề cương chi tiết

- **`-XX`** — Cờ tùy chỉnh dòng lệnh của JVM dùng để thiết lập nâng cao các tham số hệ thống và GC.
- **`Basic profiling`** — Phân tích đo lường hiệu năng ứng dụng (CPU, bộ nhớ, thread) để tìm điểm nghẽn.
- **`Memory dump`** — Bản chụp toàn bộ trạng thái bộ nhớ Heap tại một thời điểm phục vụ phân tích rò rỉ bộ nhớ.

## Ghi chú chi tiết

### -XX

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và trạng thái lỗi. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn dán.

Kiểm tra thực tế:

- Định nghĩa `-XX` trong một câu.
- Nhận diện `-XX` trong mã nguồn, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi liên quan đến `-XX`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `XX` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Phân tích hiệu năng cơ bản (Basic profiling)

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và trạng thái lỗi. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn dán.

Kiểm tra thực tế:

- Định nghĩa `Basic profiling` trong một câu.
- Nhận diện `Basic profiling` trong mã nguồn, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi liên quan đến `Basic profiling`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `Basic profiling` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Kết xuất bộ nhớ (Memory dump)

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và trạng thái lỗi. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn dán.

Kiểm tra thực tế:

- Định nghĩa `Memory dump` trong một câu.
- Nhận diện `Memory dump` trong mã nguồn, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi liên quan đến `Memory dump`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `Memory dump` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Kết xuất luồng (Thread dump)

Một bản kết xuất luồng (thread dump) là một ảnh chụp nhanh của tất cả các luồng đang hoạt động bên trong JVM, hiển thị trạng thái (RUNNABLE, BLOCKED, WAITING) và dấu vết ngăn xếp đầy đủ cho mỗi luồng.

It matters because it allows developers to diagnose deadlocks, thread contention, infinite loops, and resource locks in concurrent applications.

Kiểm tra thực tế:

- Định nghĩa `Thread dump` trong một câu.
- Nhận diện `Thread dump` trong mã nguồn, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi liên quan đến `Thread dump`.

Ví dụ nhỏ hoặc mô hình tư duy:

- `new Thread(task).start()` bắt đầu công việc trên một luồng khác.

## Các ví dụ mã nguồn

### Lệnh CLI để chụp kết xuất luồng/bộ nhớ
```bash
# Capture thread dump (PID: 1234)
jstack 1234 > thread_dump.txt

# Capture heap dump (PID: 1234)
jmap -dump:format=b,file=heap_dump.hprof 1234
```

## Các lỗi thường gặp

- **Phân tích kết xuất bộ nhớ bằng tay**: Kết xuất bộ nhớ (heap dump) là các tệp nhị phân và có thể rất lớn. Không mở chúng trong các trình biên tập văn bản thô. Luôn sử dụng các công cụ chuyên dụng như Eclipse Memory Analyzer (MAT) hoặc VisualVM.
- **Không chụp kết xuất luồng khi xảy ra deadlock**: Khi các luồng ứng dụng bị treo, hãy chụp ngay 2-3 bản kết xuất luồng cách nhau vài giây để xác định luồng nào đang bị chặn trên bộ giám sát nào.

## Câu hỏi ôn tập thường gặp

- Khái niệm nào ở đây là quy tắc thời điểm biên dịch?
- Khái niệm nào ở đây ảnh hưởng đến hành vi thời điểm chạy?
- Khái niệm nào ở đây có khả năng là bẫy phỏng vấn?

## Tại sao các phân loại cờ JVM tồn tại

JVM tổ chức các tùy chọn cấu hình dòng lệnh của mình thành ba loại riêng biệt—Tiêu chuẩn (Standard), Không tiêu chuẩn (Non-Standard `-X`), và Nhà phát triển/Thử nghiệm (Developer/Experimental `-XX`)—để quản lý tính ổn định của cờ, khả năng di động giữa các nhà cung cấp, và các tính năng thử nghiệm. **Các tùy chọn tiêu chuẩn** (ví dụ: `-classpath`, `-verbose:gc`) được đảm bảo hỗ trợ trên tất cả các nhà cung cấp và phiên bản JVM tuân thủ tiêu chuẩn, đảm bảo tính ổn định cơ bản cho dòng lệnh. **Các tùy chọn không tiêu chuẩn** (bắt đầu bằng `-X`, chẳng hạn như `-Xms` và `-Xmx`) tùy chỉnh bố cục bộ nhớ hoặc cài đặt thực thi đặc thù của HotSpot, nhưng không đảm bảo được hỗ trợ bởi các nhà cung cấp khác và có thể thay đổi mà không báo trước. **Các tùy chọn dành cho nhà phát triển, thử nghiệm hoặc không ổn định** (bắt đầu bằng `-XX`, chẳng hạn như `-XX:NewRatio` hoặc `-XX:+UseG1GC`) cho phép tùy chỉnh sâu các thuật toán GC, các chính sách trình biên dịch JIT, và các ranh giới bộ nhớ phụ. Các cờ này yêu cầu mở khóa rõ ràng (thông qua `-XX:+UnlockDiagnosticVMOptions` hoặc `-XX:+UnlockExperimentalVMOptions`) vì việc sử dụng không đúng cách có thể làm giảm hiệu năng nghiêm trọng, gây treo JVM, hoặc dẫn đến các hành vi không xác định lúc chạy.

### Mô hình tư duy: Phổ các tùy chọn JVM và Tinh chỉnh Ranh giới Heap

```text
  Phổ các tùy chọn JVM:
  [ Tiêu chuẩn: -cp, -version ]    ===> Được hỗ trợ phổ quát, ổn định
  [ Không tiêu chuẩn: -Xms, -Xmx ] ===> Kích thước Heap đặc thù HotSpot, có thể thay đổi
  [ Thử nghiệm: -XX:NewRatio ]    ===> Tham số phát triển hệ thống, không ổn định/cần mở khóa
  
  Bố cục bộ nhớ của các cờ kích thước Heap:
  |<---------------------------- -Xmx (Kích thước Heap tối đa) --------------------->|
  |<--------- -Xms (Kích thước Heap ban đầu) ---->|
  +----------------------------------------------+---------------------------------+
  |      Young Gen (Eden + S0 + S1)              |            Old Gen              |
  |  (Tỷ lệ được tinh chỉnh qua -XX:NewRatio)     |                                 |
  +----------------------------------------------+---------------------------------+
```

### Ví dụ mã nguồn

Dưới đây là một chương trình Java chạy được truy vấn các tham số Heap để chỉ ra cách các tùy chọn dòng lệnh thiết lập ranh giới bộ nhớ.

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
```

### Chuỗi nguyên nhân - kết quả

Cấu hình các cờ tiêu chuẩn &rarr; Đảm bảo khả năng di động giữa các nhà cung cấp &rarr; Thêm `-Xms` và `-Xmx` thiết lập giới hạn ranh giới trên Java Heap &rarr; Thêm `-XX:NewRatio=2` cấp phát không gian cho Thế hệ Già nhiều gấp đôi Thế hệ Trẻ &rarr; Mở khóa các cờ thử nghiệm `-XX` kích hoạt các tính năng nâng cao như Shenandoah &rarr; Đạt được hiệu năng JVM được tinh chỉnh tối ưu cho khối lượng công việc mục tiêu.

## Liên kết tham khảo

- https://docs.oracle.com/en/java/javase/21/docs/specs/man/java.html (Java Command-Line Tool Options Reference)
