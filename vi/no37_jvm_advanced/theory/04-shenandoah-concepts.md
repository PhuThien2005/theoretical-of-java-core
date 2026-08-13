# JVM nâng cao (Advanced JVM) - Phần 4

## Ghi Chú Chi Tiết

### Shenandoah

**`Shenandoah`** — Bộ thu gom rác độ trễ cực thấp thực hiện nén bộ nhớ song song.

### Stop-the-world

### Minor GC

Khái niệm này rất quan trọng vì hành vi thời gian chạy sẽ giải thích các vấn đề về hiệu năng, lỗi bộ nhớ, hành vi khi khởi động và nhiều câu hỏi phỏng vấn. Hiểu lầm phổ biến là trộn lẫn các khái niệm biên dịch (compile-time) với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế:

- Định nghĩa `Minor GC` trong một câu.
- Nhận biết `Minor GC` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `Minor GC`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `Minor GC` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Major GC

Khái niệm này rất quan trọng vì hành vi thời gian chạy sẽ giải thích các vấn đề về hiệu năng, lỗi bộ nhớ, hành vi khi khởi động và nhiều câu hỏi phỏng vấn. Hiểu lầm phổ biến là trộn lẫn các khái niệm biên dịch (compile-time) với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế:

- Định nghĩa `Major GC` trong một câu.
- Nhận biết `Major GC` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `Major GC`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `Major GC` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Full GC

Khái niệm này rất quan trọng vì hành vi thời gian chạy sẽ giải thích các vấn đề về hiệu năng, lỗi bộ nhớ, hành vi khi khởi động và nhiều câu hỏi phỏng vấn. Hiểu lầm phổ biến là trộn lẫn các khái niệm biên dịch (compile-time) với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế:

- Định nghĩa `Full GC` trong một câu.
- Nhận biết `Full GC` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `Full GC`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `Full GC` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Tinh chỉnh JVM cơ bản (Basic JVM tuning):

Tinh chỉnh JVM cơ bản (Basic JVM tuning) là một nhóm các quy tắc dùng để tối ưu hóa cách JVM thực thi mã byte và quản lý các dịch vụ thời gian chạy như bộ nhớ, JIT và GC.

Khái niệm này rất quan trọng vì hành vi thời gian chạy sẽ giải thích các vấn đề về hiệu năng, lỗi bộ nhớ, hành vi khi khởi động và nhiều câu hỏi phỏng vấn. Hiểu lầm phổ biến là trộn lẫn các khái niệm biên dịch (compile-time) với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế:

- Định nghĩa `Basic JVM tuning:` trong một câu.
- Nhận biết `Basic JVM tuning:` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `Basic JVM tuning:`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `Basic JVM tuning:` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### -Xms

### -Xmx

## Các Ví Dụ Mã Nguồn

### Ví dụ về các cờ tinh chỉnh JVM
```bash
# Thiết lập heap ban đầu là 1GB, heap tối đa là 2GB và mục tiêu thời gian tạm dừng GC là 50ms
java -Xms1g -Xmx2g -XX:MaxGCPauseMillis=50 -jar app.jar
```

## Sai Lầm Thường Gặp

- **Sử dụng -Xms và -Xmx không khớp nhau**: Nếu `-Xms` nhỏ hơn `-Xmx`, JVM sẽ tự động thay đổi kích thước của vùng Heap (dynamic resizing). Việc thay đổi kích thước này gây ra các khoảng dừng GC và phát sinh thêm chi phí hiệu năng. Việc đặt hai tham số này bằng nhau là phương pháp tốt nhất trong môi trường thực tế (production).
- **Đặt MaxGCPauseMillis quá thấp**: Đặt tham số này ở một mục tiêu không thực tế (ví dụ: 5ms) có thể khiến GC chạy liên tục, làm đói các luồng ứng dụng do cạn kiệt tài nguyên CPU.

## Các Câu Hỏi Ôn Tập Thường Gặp

- Những khái niệm nào ở đây là quy tắc trong thời gian biên dịch (compile-time)?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi khi chạy ứng dụng (runtime)?
- Những khái niệm nào ở đây có khả năng là bẫy phỏng vấn?

## Tại Sao Shenandoah GC Đạt Được Thời Gian Tạm Dừng Cực Thấp

Shenandoah GC đạt được thời gian tạm dừng cực thấp độc lập với kích thước vùng Heap bằng cách thực hiện giai đoạn nén (compaction phase) đồng thời với việc chạy các luồng ứng dụng Java. Không giống như các bộ dọn rác truyền thống như G1 hoặc Parallel GC, vốn sẽ tạm dừng tất cả các luồng ứng dụng (Stop-The-World) để sao chép đối tượng và nén các vùng nhớ, Shenandoah thực hiện bước nén này một cách đồng thời. Để ngăn chặn các tình trạng tranh chấp (race condition) khi các luồng ứng dụng đọc hoặc ghi vào các đối tượng đang trong quá trình di chuyển, Shenandoah sử dụng một cơ chế gọi là **Brooks Pointer** (trong các phiên bản JDK cũ) hoặc các rào cản tải/ghi (**Load/Write Barrier** - trong các phiên bản mới hơn). Mỗi đối tượng trên vùng Heap được tiền tố hóa một trường tham chiếu trỏ đến chính nó (Brooks Pointer). Khi luồng GC đồng thời sao chép một đối tượng sang một vùng nhớ mới, nó sử dụng một chỉ lệnh Compare-And-Swap (CAS) để cập nhật Brooks Pointer của đối tượng cũ trỏ đến bản sao mới, giúp tất cả các luồng ứng dụng đang thực thi rào cản tải chuyển hướng các hoạt động đọc và ghi sang vị trí đối tượng mới một cách minh bạch.

### Mô Hình Tư Duy: Nén Đồng Thời và Brooks Pointer

```text
  1. Trước khi sao chép (Trạng thái bình thường):
     [ Tham chiếu ứng dụng ] ---> [ Object Header | Brooks Pointer ---> Chính nó | Dữ liệu ]
  
  2. Trong quá trình sao chép đồng thời:
     [ Luồng GC sao chép đối tượng sang vùng mới ]
     Đối tượng cũ (From-Space):      [ Object Header | Brooks Pointer ---> Chính nó      | Dữ liệu ]
     Đối tượng mới (To-Space):        [ Object Header | Brooks Pointer ---> Chính nó      | Dữ liệu ]
     
  3. Sau khi cập nhật con trỏ bằng CAS:
     Đối tượng cũ (From-Space):      [ Object Header | Brooks Pointer ---> Bản sao đích  | Dữ liệu ]
     Đối tượng mới (To-Space):        [ Object Header | Brooks Pointer ---> Chính nó      | Dữ liệu ]
     
  4. Chuyển hướng:
     [ Tham chiếu ứng dụng ] ---> Đối tượng cũ ---> [ Chuyển hướng qua Brooks Pointer đến Bản sao vùng đích ]
```

### Ví Dụ Mã Nguồn

Dưới đây là một minh họa về việc phân bổ bộ nhớ và chạy trong một vòng lặp để kích hoạt hoạt động của GC. Chạy đoạn mã này với Shenandoah GC cho thấy thời gian tạm dừng gần như bằng không.

```java
package theory;

import java.util.UUID;

public class ConcurrentGcDemo {
    public static void main(String[] args) {
        System.out.println("Starting allocation loop...");
        long start = System.currentTimeMillis();
        
        // Vòng lặp được thiết kế để liên tục tạo rác nhằm kích hoạt dọn rác đồng thời
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

### Chuỗi Nguyên Nhân - Kết Quả

GC lựa chọn vùng nhớ để nén &rarr; GC phân bổ bản sao trong vùng đích (to-space) &rarr; GC thực hiện CAS để cập nhật Brooks Pointer của vùng nguồn (from-space) trỏ đến bản sao của vùng đích &rarr; Các luồng ứng dụng chặn tham chiếu đối tượng qua rào cản tải (load barrier) &rarr; Tham chiếu được chuyển hướng đến bản sao đối tượng mới &rarr; Vùng nhớ cũ được thu hồi an toàn &rarr; Thời gian tạm dừng vẫn ở mức dưới một mili giây.

## Liên Kết Tham Khảo

- https://openjdk.org/jeps/189 (JEP 189: Shenandoah: A Low-Pause-Time Garbage Collector)
