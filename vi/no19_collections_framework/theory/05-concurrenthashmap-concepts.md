# ConcurrentHashMap & Xử Lý Đa Luồng Trong Collections Framework

## Thách Thức An Toàn Luồng (Thread-Safety Challenges)

Trong môi trường lập trình đa luồng (multithreaded environments), việc ghi đồng thời vào các cấu trúc dữ liệu không an toàn luồng như `HashMap` hoặc `ArrayList` gây ra các hiện tượng cực kỳ nguy hiểm:
- **Tranh chấp dữ liệu (Data Race)**: Ghi đè các phần tử, dẫn đến mất mát dữ liệu mà không có cảnh báo.
- **Vòng lặp vô tận (Infinite Loops in Legacy HashMap)**: Trong Java 7 trở về trước, việc tái băm (`resize()`) đồng thời trên `HashMap` có thể làm hỏng các con trỏ của danh sách liên kết, tạo thành một vòng lặp kín gây treo CPU 100%.
- **Ngoại lệ `ConcurrentModificationException`**: Ném ra khi một luồng đang duyệt tập hợp trong khi một luồng khác thực hiện thay đổi cấu trúc.

---

## Tiến Trình Tiến Hóa Đa Luồng Trong Java Map

```mermaid
graph TD
    Hashtable[Hashtable / SynchronizedMap: Lock Toàn Bộ Bảng] --> SegmentLock[Java 7 ConcurrentHashMap: Lock Theo Segment (Segmented Locking)]
    SegmentLock --> BucketCAS[Java 8+ ConcurrentHashMap: CAS + Synchronized Từng Thùng Băm]
```

### 1. `Hashtable` & `Collections.synchronizedMap()`
- **Cơ chế**: Khóa cấp bảng băm (Table-Level Locking). Tất cả các luồng muốn đọc hoặc ghi đều phải tranh chấp một khóa duy nhất (`synchronized(this)` hoặc `synchronized(mutex)`).
- **Hạn chế**: Khi số lượng luồng tăng lên, nút thắt hiệu năng (bottleneck) xuất hiện nghiêm trọng do các luồng bị chặn (blocked) chờ khóa.

### 2. `ConcurrentHashMap` Trong Java 7 (Segmented Locking)
- Chia mảng thùng băm thành 16 phân đoạn độc lập (Segments). Mỗi `Segment` hoạt động như một ReentrantLock riêng biệt.
- Cho phép tối đa 16 luồng ghi đồng thời vào các phân đoạn khác nhau mà không cạnh tranh khóa.

### 3. `ConcurrentHashMap` Từ Java 8 Trở Đi (CAS + Bucket Synchronization)
Java 8 đã loại bỏ hoàn toàn kiến trúc `Segment` và thay thế bằng cơ chế khóa mịn hơn ở cấp từng thùng băm (Bucket-Level Locking):
- **Phép toán CAS (Compare-And-Swap) không khóa**: Nếu thùng băm đang trống (`node == null`), `ConcurrentHashMap` dùng lệnh CPU gốc `Unsafe.compareAndSwapObject` để chèn nút mới mà **không cần dùng khóa**.
- **Khóa mịn `synchronized` trên nút đầu (`Head Node`)**: Nếu thùng băm đã có phần tử (xung đột băm), nó chỉ khóa duy nhất nút đầu tiên của thùng băm đó (`synchronized (f)`). Các thùng băm khác hoàn toàn không bị ảnh hưởng.
- **Đọc không dùng khóa (Lock-free Reads)**: Các phương thức đọc như `get()` hoàn toàn không dùng khóa nhờ sử dụng các từ khóa `volatile` cho mảng thùng băm và con trỏ liên kết nút (`volatile Node<K,V> next`).

---

## Các Phương Thức Nguyên Tử (Atomic Operations)

Để tránh hiện tượng Check-Then-Act Race Condition (kiểm tra rồi mới hành động), `ConcurrentHashMap` cung cấp các phương thức thao tác nguyên tử:

- `putIfAbsent(K key, V value)`: Thêm cặp (key, value) nếu key chưa tồn tại.
- `computeIfAbsent(K key, Function mappingFunction)`: Tính toán và thêm giá trị chỉ khi key chưa có trong Map.
- `merge(K key, V value, BiFunction remappingFunction)`: Gộp giá trị cũ và mới một cách nguyên tử.

---

## Bảng So Sánh Các Giải Pháp Map Đa Luồng

| Tiêu Chí | `HashMap` | `Collections.synchronizedMap()` | `ConcurrentHashMap` |
| :--- | :--- | :--- | :--- |
| **An toàn đa luồng** | Không | Có | **Có** |
| **Cơ chế khóa** | Không có | Khóa toàn bộ Map (Mutex) | **CAS cho thùng trống + Synchronized cho thùng băm** |
| **Độ song song (Concurrency)** | 0 | 1 luồng tại một thời điểm | **Rất cao (Song song theo từng bucket)** |
| **Thao tác Đọc `get()`** | Không khóa | Phải lấy khóa Mutex | **Lock-free ($O(1)$)** |
| **Cho phép Khóa/Giá trị `null`** | Cho phép | Cho phép | **Cấm cả Key & Value `null`** |

> [!CAUTION]
> `ConcurrentHashMap` cấm hoàn toàn `null` cho cả Key và Value. Lý do là để tránh sự mơ hồ trong môi trường đa luồng: nếu `get(key)` trả về `null`, ta không thể phân biệt giữa "Key không tồn tại" và "Key tồn tại nhưng giá trị là null" bằng cách gọi `containsKey(key)`, vì trạng thái của Map có thể đã bị luồng khác thay đổi giữa 2 lời gọi hàm đó.

---

## Minh Họa Mã Nguồn Chạy Được

```java
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentHashMapDemo {
    public static void main(String[] args) throws InterruptedException {
        Map<String, Integer> wordCounts = new ConcurrentHashMap<>();

        // Giả lập 10 luồng cập nhật bộ đếm từ đồng thời
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                // Thao tác gộp nguyên tử safe-thread không bị thất thoát dữ liệu
                wordCounts.merge("Java", 1, Integer::sum);
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Tổng số đếm từ (Kỳ vọng 1000): " + wordCounts.get("Java"));
    }
}
```

---

## Bẫy Phỏng Vấn & Lưu Ý Thực Tế

1. **Ghép Nối Nhiều Phương Thức An ToànLuồng Nhưng Tạo Ra Lỗi Race Condition**:
   - *Bẫy*: 
     ```java
     if (!map.containsKey(key)) {
         map.put(key, value); // RACE CONDITION! Luồng khác có thể chèn giữa 2 lệnh này
     }
     ```
   - *Thực tế*: Dù `containsKey` và `put` đều an toàn đa luồng độc lập, việc ghép chúng lại vẫn tạo ra lỗi hổng thời gian. Phải dùng `map.putIfAbsent(key, value)`.

2. **Duyệt `ConcurrentHashMap` Bằng Iterator**:
   - *Thực tế*: Iterator của `ConcurrentHashMap` là **Weakly-Consistent (Nhất quán yếu)**. Nó không ném `ConcurrentModificationException` và phản ánh trạng thái của Map tại hoặc sau thời điểm Iterator được tạo.
