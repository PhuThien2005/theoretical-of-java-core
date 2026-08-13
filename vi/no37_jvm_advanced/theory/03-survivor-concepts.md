# JVM Nâng Cao - Phần 3 (Advanced JVM - Part 3)

## Khung Nội Dung (Outline Coverage)

---

## Ghi Chú Chi Tiết (Detailed Notes)

### Vùng Sống Sót (Survivor Space)

Vùng Sống Sót (Survivor) bao gồm hai không gian nhớ nhỏ có kích thước bằng nhau ký hiệu là S0 và S1 trong thế hệ Trẻ. Chúng đóng vai trò là vùng đệm dịch chuyển cho các đối tượng còn sống từ vùng Eden trước khi chúng đủ điều kiện để được đưa lên thế hệ Già.

- **Quy tắc dịch chuyển**: Tại một thời điểm, một vùng Survivor sẽ ở trạng thái hoạt động (active) để chứa các đối tượng sống sót, trong khi vùng còn lại hoàn toàn trống (empty). Lượt GC tiếp theo sẽ đảo ngược vai trò của hai vùng này.
- **Tích tuổi đối tượng**: Mỗi lần đối tượng sống sót qua một đợt Minor GC và được sao chép giữa S0 và S1, tuổi (age) của nó tăng lên 1. Khi tuổi đạt đến ngưỡng quy định (`-XX:MaxTenuringThreshold`), đối tượng sẽ được thăng cấp (promoted) lên thế hệ Già.

---

### Thế Hệ Già (Old Generation)

Thế hệ Già (Old Generation hay Tenured Generation) là vùng lưu trữ các đối tượng có thời gian sống lâu trong ứng dụng (như các dịch vụ Singleton, cấu hình hệ thống, bộ đệm dữ liệu lớn).

- **Tần suất dọn rác**: Các đợt dọn rác trên thế hệ Già (Major GC hoặc Full GC) xảy ra ít thường xuyên hơn so với thế hệ Trẻ nhưng tốn nhiều thời gian tạm dừng (Stop-The-World) hơn do kích thước vùng nhớ lớn và cấu trúc đối tượng phức tạp.
- **Trường hợp thất bại**: Nếu thế hệ Già bị đầy và GC không thể giải phóng thêm không gian, JVM sẽ ném ra ngoại lệ `java.lang.OutOfMemoryError: Java heap space`.

---

### Các thuật toán thu gom rác (GC algorithms)

Việc hiểu rõ hành vi thời gian chạy của các thuật toán GC giúp giải thích hiệu năng, các lỗi bộ nhớ, hành vi khởi động của ứng dụng và giải quyết các câu hỏi phỏng vấn chuyên sâu.

#### 1. Serial GC
Bộ thu gom rác chạy đơn luồng. Khi thực hiện dọn rác, nó sẽ dừng toàn bộ các luồng ứng dụng (Stop-The-World).
- **Phù hợp**: Các ứng dụng dòng lệnh nhỏ, các vi dịch vụ chạy trong container chỉ có 1 CPU.
- **Kích hoạt**: `-XX:+UseSerialGC`

#### 2. Parallel GC
Sử dụng nhiều luồng song song để thực hiện dọn rác nhằm giảm thiểu thời gian Stop-The-World, tối ưu hóa tối đa băng thông (throughput) cho CPU.
- **Phù hợp**: Các ứng dụng xử lý dữ liệu theo lô (batch processing), tính toán khoa học.
- **Kích hoạt**: `-XX:+UseParallelGC`

#### 3. Concurrent Mark Sweep (CMS) GC
Thuật toán dọn rác đồng thời thế hệ cũ, cố gắng thực hiện phần lớn các công việc đánh dấu và quét rác song song với luồng ứng dụng để giảm thiểu thời gian dừng.
- **Trạng thái**: Đã bị **loại bỏ hoàn toàn (removed)** từ Java 14 do cấu trúc phức tạp và dễ gây phân mảnh bộ nhớ.

#### 4. G1 GC (Garbage-First)
Bộ thu gom rác mặc định từ Java 9. Nó chia bộ nhớ heap thành hàng nghìn vùng ảo (regions) có kích thước bằng nhau. Nó ưu tiên dọn dẹp các vùng chứa nhiều rác nhất trước (Garbage-First) để tối ưu hóa hiệu quả thu hồi bộ nhớ trong khoảng thời gian tạm dừng mục tiêu được cấu hình.
- **Phù hợp**: Các ứng dụng máy chủ lớn có bộ nhớ heap từ 4GB đến vài chục GB.
- **Kích hoạt**: `-XX:+UseG1GC`

#### 5. ZGC (Z Garbage Collector)
Bộ thu gom rác thế hệ mới có độ trễ siêu thấp. Nó thực hiện tất cả các công việc dọn rác nặng (bao gồm cả việc di chuyển đối tượng để nén bộ nhớ) một cách đồng thời với luồng chạy của ứng dụng, đảm bảo thời gian tạm dừng Stop-The-World không vượt quá 10 mili giây ngay cả với các bộ nhớ heap khổng lồ lên tới hàng Terabyte.
- **Phù hợp**: Các ứng dụng yêu cầu thời gian phản hồi thời gian thực cực nhanh (tài chính, game, dịch vụ nhạy cảm độ trễ).
- **Kích hoạt**: `-XX:+UseZGC`

---

## Ví Dụ Mã Nguồn (Code Examples)

### Lựa chọn thuật toán GC thông qua các cờ CLI khi khởi động JVM
```bash
# Kích hoạt bộ thu gom rác G1 GC
java -XX:+UseG1GC -jar app.jar

# Kích hoạt bộ thu gom rác độ trễ thấp ZGC
java -XX:+UseZGC -jar app.jar
```

---

## Các Sai Lầm Thường Gặp (Common Mistakes)

- **Sử dụng Serial GC trên các máy chủ đa nhân**: Serial GC chỉ sử dụng một luồng duy nhất để dọn rác. Nó hoạt động tốt cho các công cụ nhỏ gọn, nhưng sẽ gây ra những khoảng dừng Stop-The-World cực dài và làm giảm hiệu năng nghiêm trọng trên các hệ thống máy chủ đa luồng.
- **Lầm tưởng G1 GC chia các phân thế hệ vật lý cố định**: Không giống như Parallel GC chia các phân vùng thế hệ vật lý cố định, G1 GC chia heap thành các vùng ảo nhỏ độc lập. Một vùng ảo có thể hoạt động như Eden, Survivor hoặc Old một cách động dựa trên thuật toán điều phối tại thời điểm chạy.

---

## Các Câu Hỏi Ôn Tập Thường Gặp (Common Review Prompts)

- Khái niệm nào ở đây là quy tắc tại thời điểm chạy? (Các thuật toán hoạt động của GC, cơ chế sao chép giữa các Survivor, thăng cấp lên Old Gen).
- Cờ CLI nào được dùng để kích hoạt bộ thu gom rác ZGC? (`-XX:+UseZGC`).

---

## Tại Sao Các Vùng Survivor Ngăn Chặn Phân Mảnh Bộ Nhớ Heap (Why Survivor Spaces Prevent Heap Fragmentation)

Mô hình thu gom rác phân thế hệ sử dụng các vùng Sống Sót (S0 và S1) bên cạnh vùng Eden để ngăn chặn tình trạng phân mảnh bộ nhớ heap và tránh các thao tác nén toàn bộ heap (full-heap compactions) tốn kém hiệu năng. Theo giả thuyết phân thế hệ yếu (weak generational hypothesis), phần lớn các đối tượng sẽ chết ngay sau khi được tạo ra. Thay vì cấp phát và giải phóng bộ nhớ tại chỗ, JVM sẽ luôn cấp phát các đối tượng mới trong vùng **Eden**. Trong một đợt dọn rác phụ (Minor GC), các đối tượng còn sống sót trong vùng Eden sẽ được sao chép sang một trong hai vùng Survivor đang trống (ví dụ: S0), để lại vùng Eden hoàn toàn sạch sẽ và liên tục. Trong các đợt Minor GC tiếp theo, JVM sẽ sao chép các đối tượng sống sót từ cả vùng Eden và vùng Survivor đang hoạt động (S0) sang vùng Survivor thứ hai (S1), sau đó hoán đổi vai trò của S0 và S1. Bằng cách sao chép các đối tượng sống sót sang một vùng đích sạch sẽ và liên tục, đồng thời xóa sạch toàn bộ các vùng nguồn, JVM tránh được sự phân mảnh bộ nhớ mà không cần đến các thuật toán nén bộ nhớ phức tạp và chậm chạp.

### Mô hình Tư duy: Quy trình Sao chép và Sơ tán (Copy-and-Evacuate Process)

```text
  Trạng thái ban đầu:
  [ Vùng Eden: Đối tượng A (sống), Đối tượng B (chết) ]  ===> Minor GC sao chép Đối tượng A sang S0
  [ Vùng S0: Trống                                    ]       và dọn sạch hoàn toàn vùng Eden.
  [ Vùng S1: Trống                                    ]
  
  Sau đợt Minor GC 1:
  [ Vùng Eden: Trống                                  ]
  [ Vùng S0: Đối tượng A (sống, tuổi = 1)             ]
  [ Vùng S1: Trống                                    ]
  
  Trạng thái tiếp theo (sau khi cấp phát các đối tượng mới):
  [ Vùng Eden: Đối tượng C (sống), Đối tượng D (chết) ]  ===> Minor GC sao chép Đối tượng C và A sang S1,
  [ Vùng S0: Đối tượng A (sống, tuổi = 1)             ]       dọn sạch hoàn toàn vùng Eden và S0.
  [ Vùng S1: Trống                                    ]
  
  Sau đợt Minor GC 2:
  [ Vùng Eden: Trống                                  ]
  [ Vùng S0: Trống                                    ]
  [ Vùng S1: Đối tượng A (tuổi = 2), Đối tượng C (1)  ]
```

### Ví Dụ Mã Nguồn

Dưới đây là mã nguồn mô phỏng việc tạo liên tục các đối tượng ngắn hạn. Vì các đối tượng này có vòng đời ngắn, chúng được tạo ra trong vùng Eden, chết ngay tại đây và được giải phóng bộ nhớ trong các đợt Minor GC từ vùng Eden/Survivor mà không bao giờ bị lão hóa để chuyển lên thế hệ Già.

```java
package theory;

public class GenerationalGcSimulation {
    public static void main(String[] args) {
        // Tốc độ cấp phát cao cho các đối tượng ngắn hạn
        for (int i = 0; i < 1_000_000; i++) {
            // Các chuỗi này được tạo trong Eden và chết ngay lập tức trong đợt Minor GC tiếp theo
            String shortLived = new String("Short-lived object " + i);
            
            // Mô phỏng các đối tượng sống sót lâu hơn để di chuyển sang Survivor/Old
            if (i % 100_000 == 0) {
                System.out.println("Allocated: " + shortLived);
            }
        }
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)
Các đối tượng mới được cấp phát trong vùng Eden $\rightarrow$ Minor GC được kích hoạt $\rightarrow$ Các đối tượng còn sống được sao chép sang S0, Eden được dọn sạch $\rightarrow$ Đợt Minor GC tiếp theo kích hoạt $\rightarrow$ Các đối tượng còn sống từ Eden & S0 được sao chép sang S1, Eden & S0 được dọn sạch $\rightarrow$ Các đối tượng lâu năm được thăng cấp lên thế hệ Già sau khi đạt ngưỡng tuổi $\rightarrow$ Cấu trúc bộ nhớ liên tục được duy trì, tránh phân mảnh.

## Liên Kết Tham Khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/gctuning/factors-affecting-garbage-collection-performance.html (Tập trung vào dọn rác phân thế hệ & Tích tuổi đối tượng)
