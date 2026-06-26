# JVM nâng cao - Phần 3 (Advanced JVM - Part 3)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm một phần trọng tâm của **JVM nâng cao (Advanced JVM)**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là từ vựng rời rạc.

## Phạm vi đề cương (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `Survivor` | Vùng Survivor (Survivor) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại (failure mode) của nó thay vì chỉ nhớ tên. |
| `Old Generation` | Thế hệ già (Old Generation) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên. |
| `GC algorithms:` | Các thuật toán GC (GC algorithms) là một nhóm các quy tắc liên quan trong JVM nâng cao tập hợp nhiều chi tiết liên quan lại với nhau. |
| `Serial GC` | Serial GC là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên. |
| `Parallel GC` | Parallel GC là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên. |
| `old CMS` | CMS cũ (old CMS) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên. |
| `G1 GC` | G1 GC là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên. |
| `ZGC` | ZGC là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên. |

## Ghi chú chi tiết (Detailed Notes)

### Vùng Survivor (Survivor)

Vùng Survivor (Survivor) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Survivor` trong một câu.
- Nhận biết `Survivor` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Survivor`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- When reading code, ask: what does `Survivor` change, allow, reject, or clarify?

### Thế hệ già (Old Generation)

Thế hệ già (Old Generation) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Old Generation` trong một câu.
- Nhận biết `Old Generation` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Old Generation`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- When reading code, ask: what does `Old Generation` change, allow, reject, or clarify?

### Các thuật toán GC (GC algorithms)

Các thuật toán GC (GC algorithms) là một nhóm các quy tắc liên quan trong JVM nâng cao tập hợp nhiều chi tiết liên quan lại với nhau.

Nó quan trọng vì hành vi thời gian chạy giải thích hiệu suất, lỗi bộ nhớ, hành vi khi khởi động và nhiều câu hỏi phỏng vấn. Sự nhầm lẫn phổ biến là nhầm lẫn các khái niệm ở thời điểm biên dịch (compile-time) với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế:

- Định nghĩa `GC algorithms:` trong một câu.
- Nhận biết `GC algorithms:` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `GC algorithms:`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- When reading code, ask: what does `GC algorithms:` change, allow, reject, or clarify?

### Serial GC (Serial GC)

Serial GC là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Nó quan trọng vì hành vi thời gian chạy giải thích hiệu suất, lỗi bộ nhớ, hành vi khi khởi động và nhiều câu hỏi phỏng vấn. Sự nhầm lẫn phổ biến là nhầm lẫn các khái niệm ở thời điểm biên dịch (compile-time) với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế:

- Định nghĩa `Serial GC` trong một câu.
- Nhận biết `Serial GC` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Serial GC`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- When reading code, ask: what does `Serial GC` change, allow, reject, or clarify?

### Parallel GC (Parallel GC)

Parallel GC là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Nó quan trọng vì hành vi thời gian chạy giải thích hiệu suất, lỗi bộ nhớ, hành vi khi khởi động và nhiều câu hỏi phỏng vấn. Sự nhầm lẫn phổ biến là nhầm lẫn các khái niệm ở thời điểm biên dịch (compile-time) với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế:

- Định nghĩa `Parallel GC` trong một câu.
- Nhận biết `Parallel GC` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Parallel GC`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- When reading code, ask: what does `Parallel GC` change, allow, reject, or clarify?

### CMS cũ (old CMS)

CMS cũ (old CMS) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `old CMS` trong một câu.
- Nhận biết `old CMS` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `old CMS`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- When reading code, ask: what does `old CMS` change, allow, reject, or clarify?

### G1 GC (G1 GC)

G1 GC là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Nó quan trọng vì hành vi thời gian chạy giải thích hiệu suất, lỗi bộ nhớ, hành vi khi khởi động và nhiều câu hỏi phỏng vấn. Sự nhầm lẫn phổ biến là nhầm lẫn các khái niệm ở thời điểm biên dịch (compile-time) với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế:

- Định nghĩa `G1 GC` trong một câu.
- Nhận biết `G1 GC` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `G1 GC`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- When reading code, ask: what does `G1 GC` change, allow, reject, or clarify?

### ZGC (ZGC)

ZGC là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Nó quan trọng vì hành vi thời gian chạy giải thích hiệu suất, lỗi bộ nhớ, hành vi khi khởi động và nhiều câu hỏi phỏng vấn. Sự nhầm lẫn phổ biến là nhầm lẫn các khái niệm ở thời điểm biên dịch (compile-time) với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế:

- Định nghĩa `ZGC` trong một câu.
- Nhận biết `ZGC` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `ZGC`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- When reading code, ask: what does `ZGC` change, allow, reject, or clarify?

## Các ví dụ code (Code Examples)

### Chọn một thuật toán GC thông qua các cờ CLI (Selecting a GC Algorithm via CLI Flags)
```bash
# Kích hoạt G1 GC
java -XX:+UseG1GC -jar app.jar

# Kích hoạt ZGC độ trễ thấp
java -XX:+UseZGC -jar app.jar
```

## Các lỗi thường gặp (Common Mistakes)

- **Sử dụng Serial GC trên các máy chủ đa nhân**: Serial GC sử dụng một luồng (Thread) duy nhất để thu gom rác. Nó phù hợp với các công cụ CLI nhỏ hoặc các container đơn nhân, nhưng gây ra thời gian tạm dừng khủng khiếp trên các máy chủ đa luồng.
- **Giả định G1 có các phân vùng thế hệ liền kề**: Không giống như Parallel GC, G1 phân chia bộ nhớ heap thành các vùng ảo có kích thước bằng nhau. Một vùng có thể đóng vai trò là Eden, Survivor hoặc Old một cách năng động.

## Câu hỏi ôn tập phổ biến (Common Review Prompts)

- Khái niệm nào ở đây là quy tắc ở thời điểm biên dịch (compile-time)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi lúc chạy (runtime)?
- Khái niệm nào ở đây dễ là bẫy phỏng vấn (interview traps)?

---

## Tại sao các vùng Survivor ngăn ngừa phân mảnh bộ nhớ Heap (Why Survivor Spaces Prevent Heap Fragmentation)

Mô hình thu gom rác phân thế hệ (generational garbage collection model) sử dụng các vùng Survivor (S0 và S1) bên cạnh Eden để ngăn chặn phân mảnh bộ nhớ heap (heap fragmentation) và tránh việc thực hiện nén toàn bộ heap (full-heap compaction) vốn rất tốn kém.

Theo giả thuyết phân thế hệ yếu (weak generational hypothesis), phần lớn các đối tượng được tạo ra sẽ chết ngay sau khi khởi tạo. Thay vì cấp phát và giải phóng bộ nhớ tại chỗ, JVM sẽ cấp phát các đối tượng mới trong vùng **Eden**.

Trong một chu kỳ thu gom rác nhỏ (minor garbage collection), các đối tượng hoạt động (sống sót) trong Eden được sao chép sang một trong các vùng Survivor đang trống (ví dụ: S0), để lại vùng Eden hoàn toàn liền mạch và không có khoảng trống.

Trong các chu kỳ Minor GC tiếp theo, JVM sao chép các đối tượng sống sót từ cả vùng Eden và vùng Survivor đang hoạt động (S0) sang vùng Survivor thứ hai (S1), rồi tráo đổi vai trò của hai vùng này.

Bằng cách sao chép các đối tượng sống sót đến một vùng đích sạch sẽ, liền mạch và xóa hoàn toàn các vùng nguồn, JVM tránh được sự phân mảnh bộ nhớ mà không cần đến các thuật toán nén bộ nhớ (compaction) phức tạp và chậm chạp.

### Mô hình tư duy: Quá trình sao chép và di tản (Mental Model: Copy-and-Evacuate Process)

```text
  Trạng thái ban đầu:
  [ Eden: Đối tượng A (sống), Đối tượng B (chết) ]  ===> Minor GC sao chép Đối tượng A sang S0
  [ S0: Trống                                    ]       và dọn sạch Eden hoàn toàn.
  [ S1: Trống                                    ]
  
  Sau chu kỳ Minor GC thứ 1:
  [ Eden: Trống                                  ]
  [ S0: Đối tượng A (sống, tuổi 1)               ]
  [ S1: Trống                                    ]
  
  Trạng thái tiếp theo (sau khi cấp phát mới):
  [ Eden: Đối tượng C (sống), Đối tượng D (chết) ]  ===> Minor GC sao chép Đối tượng C và A sang S1,
  [ S0: Đối tượng A (sống, tuổi 1)               ]       dọn sạch Eden và S0 hoàn toàn.
  [ S1: Trống                                    ]
  
  Sau chu kỳ Minor GC thứ 2:
  [ Eden: Trống                                  ]
  [ S0: Trống                                    ]
  [ S1: Đối tượng A (tuổi 2), Đối tượng C (tuổi 1) ]
```

### Ví dụ Code (Code Example)

Dưới đây là mã mô phỏng việc tạo nhanh các đối tượng có vòng đời ngắn. Vì các đối tượng này có vòng đời ngắn, chúng được tạo ra trong Eden, không bao giờ đủ tuổi để lên Thế thế già, và được dọn sạch trong chu kỳ Minor GC từ vùng Eden/Survivor.

```java
package theory;

import java.util.ArrayList;
import java.util.List;

public class GenerationalGcSimulation {
    public static void main(String[] args) {
        // Tốc độ cấp phát cao của các đối tượng có tuổi thọ ngắn
        for (int i = 0; i < 1_000_000; i++) {
            // Các chuỗi này được tạo ra trong Eden và chết ngay trong chu kỳ Minor GC tiếp theo
            String shortLived = new String("Short-lived object " + i);
            
            // Để mô phỏng các đối tượng sống sót già đi và chuyển sang Survivor/Old
            if (i % 100_000 == 0) {
                System.out.println("Allocated: " + shortLived);
            }
        }
    }
}
/* Output:
Allocated: Short-lived object 0
Allocated: Short-lived object 100000
Allocated: Short-lived object 200000
Allocated: Short-lived object 300000
Allocated: Short-lived object 400000
Allocated: Short-lived object 500000
Allocated: Short-lived object 600000
Allocated: Short-lived object 700000
Allocated: Short-lived object 800000
Allocated: Short-lived object 900000
*/
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)


```text
Các đối tượng mới được phân bổ trong Eden
  → Kích hoạt Minor GC
  → Đối tượng sống sót được sao chép sang S0, Eden được dọn sạch
  → Kích hoạt Minor GC tiếp theo
  → Đối tượng sống sót từ Eden & S0 được sao chép sang S1, Eden & S0 được dọn sạch
  → Các đối tượng già đi được đẩy lên Old Gen sau khi vượt ngưỡng tuổi
  → Bố cục bộ nhớ liền mạch được duy trì mà không bị phân mảnh.
```


## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/gctuning/factors-affecting-garbage-collection-performance.html (Generational GC & Aging)
