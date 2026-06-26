# JVM Nâng Cao - Phần 4 (Advanced JVM - Part 4)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này trình bày một phần trọng tâm của **JVM Nâng cao (Advanced JVM)**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, không chỉ là các từ vựng rời rạc.

## Khái Quát Nội Dung (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `Shenandoah` | Shenandoah là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi. |
| `Stop-the-world` | Stop-the-world (dừng thế giới) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi. |
| `Minor GC` | Minor GC (dọn rác nhỏ) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi. |
| `Major GC` | Major GC (dọn rác lớn) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi. |
| `Full GC` | Full GC (dọn rác toàn phần) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi. |
| `Basic JVM tuning:` | JVM thực thi mã bytecode và quản lý các dịch vụ thời gian chạy (runtime services) như bộ nhớ, JIT, và bộ dọn rác (Garbage Collector - GC). |
| `-Xms` | -Xms là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi. |
| `-Xmx` | -Xmx là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi. |

## Ghi Chú Chi Tiết (Detailed Notes)

### Shenandoah

Shenandoah là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi.

Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được cho phép, và chế độ thất bại. Hãy xem lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

Kiểm tra thực tế (Practical check):

- Định nghĩa `Shenandoah` trong một câu.
- Nhận biết `Shenandoah` trong code, câu lệnh, tài liệu, hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi (tradeoff) liên quan đến `Shenandoah`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Khi đọc code, hãy hỏi: `Shenandoah` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

### Stop-the-world

Stop-the-world (dừng thế giới) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi.

Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được cho phép, và chế độ thất bại. Hãy xem lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

Kiểm tra thực tế (Practical check):

- Định nghĩa `Stop-the-world` trong một câu.
- Nhận biết `Stop-the-world` trong code, câu lệnh, tài liệu, hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi (tradeoff) liên quan đến `Stop-the-world`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Khi đọc code, hãy hỏi: `Stop-the-world` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

### Minor GC

Minor GC (dọn rác nhỏ) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi.

Khái niệm này quan trọng vì hành vi thời gian chạy giải thích hiệu năng, lỗi bộ nhớ, hành vi khởi động, và nhiều câu hỏi phỏng vấn. Một sự nhầm lẫn phổ biến là trộn lẫn các khái niệm biên dịch (compile-time) với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế (Practical check):

- Định nghĩa `Minor GC` trong một câu.
- Nhận biết `Minor GC` trong code, câu lệnh, tài liệu, hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi (tradeoff) liên quan đến `Minor GC`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Khi đọc code, hãy hỏi: `Minor GC` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

### Major GC

Major GC (dọn rác lớn) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi.

Khái niệm này quan trọng vì hành vi thời gian chạy giải thích hiệu năng, lỗi bộ nhớ, hành vi khởi động, và nhiều câu hỏi phỏng vấn. Một sự nhầm lẫn phổ biến là trộn lẫn các khái niệm biên dịch (compile-time) với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế (Practical check):

- Định nghĩa `Major GC` trong một câu.
- Nhận biết `Major GC` trong code, câu lệnh, tài liệu, hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi (tradeoff) liên quan đến `Major GC`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Khi đọc code, hãy hỏi: `Major GC` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

### Full GC

Full GC (dọn rác toàn phần) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi.

Khái niệm này quan trọng vì hành vi thời gian chạy giải thích hiệu năng, lỗi bộ nhớ, hành vi khởi động, và nhiều câu hỏi phỏng vấn. Một sự nhầm lẫn phổ biến là trộn lẫn các khái niệm biên dịch (compile-time) với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế (Practical check):

- Định nghĩa `Full GC` trong một câu.
- Nhận biết `Full GC` trong code, câu lệnh, tài liệu, hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi (tradeoff) liên quan đến `Full GC`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Khi đọc code, hãy hỏi: `Full GC` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

### Tối ưu hóa JVM cơ bản (Basic JVM tuning)

JVM thực thi mã bytecode và quản lý các dịch vụ thời gian chạy (runtime services) như bộ nhớ, JIT, và bộ dọn rác (Garbage Collector - GC).

Khái niệm này quan trọng vì hành vi thời gian chạy giải thích hiệu năng, lỗi bộ nhớ, hành vi khởi động, và nhiều câu hỏi phỏng vấn. Một sự nhầm lẫn phổ biến là trộn lẫn các khái niệm biên dịch (compile-time) với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế (Practical check):

- Định nghĩa `Basic JVM tuning:` trong một câu.
- Nhận biết `Basic JVM tuning:` trong code, câu lệnh, tài liệu, hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi (tradeoff) liên quan đến `Basic JVM tuning:`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Khi đọc code, hãy hỏi: `Basic JVM tuning:` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

### -Xms

-Xms là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi.

Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được cho phép, và chế độ thất bại. Hãy xem lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

Kiểm tra thực tế (Practical check):

- Định nghĩa `-Xms` trong một câu.
- Nhận biết `-Xms` trong code, câu lệnh, tài liệu, hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi (tradeoff) liên quan đến `-Xms`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Khi đọc code, hãy hỏi: `Xms` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

### -Xmx

-Xmx là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi.

Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được cho phép, và chế độ thất bại. Hãy xem lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

Kiểm tra thực tế (Practical check):

- Định nghĩa `-Xmx` trong một câu.
- Nhận biết `-Xmx` trong code, câu lệnh, tài liệu, hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi (tradeoff) liên quan đến `-Xmx`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Khi đọc code, hãy hỏi: `Xmx` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

## Ví Dụ Code (Code Examples)

### JVM Tuning Flags Example
```bash
# Set initial heap to 1GB, max heap to 2GB, and target a 50ms GC pause time
java -Xms1g -Xmx2g -XX:MaxGCPauseMillis=50 -jar app.jar
```

## Các Sai Lầm Thường Gặp (Common Mistakes)

- **Không khớp -Xms và -Xmx**: Nếu `-Xms` nhỏ hơn `-Xmx`, JVM sẽ tự động thay đổi kích thước vùng nhớ Heap (Heap memory). Việc thay đổi kích thước này gây ra các khoảng dừng GC và chi phí hiệu năng (performance overhead). Thiết lập chúng bằng nhau là thực hành tốt nhất (best practice) cho môi trường sản xuất (production).
- **Thiết lập MaxGCPauseMillis quá thấp**: Thiết lập một mục tiêu không thực tế (ví dụ: 5ms) có thể khiến GC chạy liên tục, làm các luồng ứng dụng bị đói tài nguyên CPU.

## Câu Hỏi Ôn Tập Thường Gặp (Common Review Prompts)

- Khái niệm nào ở đây là quy tắc biên dịch (compile-time)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi thời gian chạy (runtime)?
- Khái niệm nào ở đây dễ là bẫy phỏng vấn?

## Tại sao Shenandoah GC Đạt Được Thời Gian Dừng Cực Thấp (Why Shenandoah GC Achieves Ultra-Low Pause Times)

Shenandoah GC đạt được thời gian dừng cực thấp, không phụ thuộc vào kích thước vùng chứa Heap, bằng cách thực hiện giai đoạn nén bộ nhớ (compaction phase) đồng thời (concurrently) với việc chạy các luồng ứng dụng Java (application threads). Không giống như các bộ dọn rác truyền thống như G1 hoặc Parallel GC, vốn dừng tất cả các luồng ứng dụng (Stop-The-World) để sao chép đối tượng và nén các vùng nhớ, Shenandoah thực hiện bước nén này một cách đồng thời. Để ngăn chặn tình trạng tranh chấp dữ liệu (race conditions) khi các luồng ứng dụng đọc hoặc ghi vào các đối tượng đang trong quá trình di chuyển, Shenandoah sử dụng một cơ chế gọi là **Brooks Pointers** (trong các phiên bản JDK cũ) hoặc **Load/Write Barriers** (rào cản tải/ghi - trong các phiên bản mới hơn). Mỗi đối tượng trên Heap được thêm một trường tham chiếu trỏ đến chính nó (Brooks Pointer). Khi luồng GC đồng thời sao chép một đối tượng sang một vùng nhớ mới, nó sử dụng một lệnh so sánh và tráo đổi (Compare-And-Swap - CAS) để cập nhật con trỏ Brooks Pointer của đối tượng cũ trỏ đến bản sao mới, giúp tất cả các luồng ứng dụng đang thực thi rào cản tải chuyển hướng một cách minh bạch các thao tác đọc và ghi sang vị trí đối tượng mới.

### Mô Hình Tư Duy: Nén Đồng Thời và Con Trỏ Brooks (Mental Model: Concurrent Compaction and Brooks Pointer)

```text
  1. Before Copy (Normal State):
     [ Application Reference ] ---> [ Object Header | Brooks Pointer ---> Self | Data ]
  
  2. During Concurrent Copy:
     [ GC Thread copies Object to new region ]
     Old Object (From-Space):      [ Object Header | Brooks Pointer ---> Self | Data ]
     New Object (To-Space):        [ Object Header | Brooks Pointer ---> Self | Data ]
     
  3. After CAS Pointer Update:
     Old Object (From-Space):      [ Object Header | Brooks Pointer ---> To-Space Copy | Data ]
     New Object (To-Space):        [ Object Header | Brooks Pointer ---> Self           | Data ]
     
  4. Redirection:
     [ Application Reference ] ---> Old Object ---> [ Redirected via Brooks Pointer to To-Space Copy ]
```

### Ví Dụ Code (Code Example)

Dưới đây là một ví dụ minh họa việc phân bổ bộ nhớ và chạy trong một vòng lặp để kích hoạt hoạt động dọn rác GC. Chạy chương trình này với Shenandoah GC sẽ thấy thời gian dừng gần như bằng không.

```java
package theory;

import java.util.UUID;

public class ConcurrentGcDemo {
    public static void main(String[] args) {
        System.out.println("Starting allocation loop...");
        long start = System.currentTimeMillis();
        
        // Loop designed to produce continuous garbage to trigger concurrent collection
        for (int i = 0; i < 500_000; i++) {
            String temp = UUID.randomUUID().toString();
            if (i % 100_000 == 0) {
                long now = System.currentTimeMillis();
                System.out.println("Allocated: " + i + " items. Elapsed: " + (now - start) + "ms");
            }
        }
    }
}
/* Output (Run with: java -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC):
Starting allocation loop...
Allocated: 0 items. Elapsed: 0ms
Allocated: 100000 items. Elapsed: 45ms
Allocated: 200000 items. Elapsed: 90ms
Allocated: 300000 items. Elapsed: 135ms
Allocated: 400000 items. Elapsed: 180ms
*/
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)


```text
GC chọn vùng nhớ để nén
  → GC phân bổ bản sao trong to-space
  → GC thực hiện lệnh CAS để cập nhật Brooks Pointer của from-space trỏ tới bản sao của to-space
  → Các luồng ứng dụng chặn tham chiếu đối tượng qua rào cản tải (load barrier)
  → Tham chiếu được chuyển hướng đến bản sao đối tượng mới
  → Vùng nhớ cũ được thu hồi an toàn
  → Thời gian dừng vẫn ở mức dưới một mili giây (sub-millisecond).
```


## Liên Kết Tham Khảo (Reference Links)

- https://openjdk.org/jeps/189 (JEP 189: Shenandoah: Bộ dọn rác với thời gian tạm dừng ngắn)
