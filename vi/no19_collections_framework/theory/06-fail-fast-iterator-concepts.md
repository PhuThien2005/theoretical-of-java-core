# Fail-Fast & Fail-Safe Iterators Trong Java

## Khái Niệm Iterator & Trạng Thái Sửa Đổi Cấu Trúc

Trong Java Collections Framework, việc duyệt qua một tập hợp dữ liệu được thực hiện thông qua giao diện `Iterator<E>`. Tuy nhiên, điều gì sẽ xảy ra nếu một luồng đang duyệt tập hợp trong khi tập hợp đó bị **thay đổi cấu trúc (structural modification)** bởi một hành động khác?

Thay đổi cấu trúc bao gồm bất kỳ thao tác nào thêm, xóa hoặc làm thay đổi kích thước (`size`) của tập hợp bên dưới (không bao gồm việc sửa đổi nội dung bên trong của một phần tử hiện có). 

Để xử lý bài toán này, Java chia các trình duyệt (Iterators) thành hai chiến lược chính: **Fail-Fast** và **Fail-Safe (Weakly-Consistent)**.

---

## Phân Tích Chuyên Sâu Trình Duyệt Fail-Fast

Hầu hết các tập hợp chuẩn không an toàn đa luồng trong `java.util` (như `ArrayList`, `HashSet`, `HashMap`, `LinkedList`, `Vector`) đều sử dụng trình duyệt **Fail-Fast**.

```mermaid
sequenceDiagram
    participant Loop as Vòng lặp For-Each
    participant Iter as Fail-Fast Iterator
    participant Coll as ArrayList (modCount)
    
    Loop->>Iter: next()
    Iter->>Coll: Kiểm tra expectedModCount == modCount
    Coll-->>Iter: Khớp (Equal)
    Iter-->>Loop: Trả về phần tử
    
    Note over Coll: Luồng khác gọi list.add() -> modCount++
    
    Loop->>Iter: next()
    Iter->>Coll: Kiểm tra expectedModCount == modCount
    Coll-->>Iter: Không khớp (Mismatch!)
    Iter-->>Loop: Ném ConcurrentModificationException!
```

### 1. Cơ Chế Báo Động `modCount`
- Lớp tập hợp duy trì một trường dữ liệu đếm số lần sửa đổi cấu trúc gọi là `modCount` (bảo vệ bởi từ khóa `transient int modCount`).
- Khi một `Iterator` được tạo ra, nó lưu lại giá trị `modCount` tại thời điểm đó vào trường nội bộ `expectedModCount`:
  $$\text{expectedModCount} = \text{modCount}$$
- Trong **mỗi lời gọi** `next()` hoặc `remove()`, `Iterator` kiểm tra điều kiện an toàn:
  ```java
  final void checkForComodification() {
      if (modCount != expectedModCount)
          throw new ConcurrentModificationException();
  }
  ```
- Nếu phát hiện `modCount != expectedModCount`, `Iterator` sẽ ngay lập tức ném ra ngoại lệ `ConcurrentModificationException` để ngăn chặn việc duyệt tiếp trên một tập hợp đã bị hỏng trạng thái.

### 2. Chiến Lược Sửa Đổi An Toàn Khi Duyệt Tập Hợp Fail-Fast
Có 2 cách hợp lệ để sửa đổi tập hợp trong quá trình duyệt mà không gây ra ngoại lệ:
1. **Sử dụng `Iterator.remove()`**: Phương thức `remove()` của chính Iterator sẽ cập nhật cả `modCount` của tập hợp lẫn `expectedModCount` của Iterator, giữ cho chúng luôn bằng nhau.
2. **Sử dụng `Collection.removeIf(Predicate)` (Java 8+)**: Phương thức tiện ích nội bộ xử lý việc xóa an toàn trong một thao tác duy nhất.

---

## Phân Tích Chuyên Sâu Trình Duyệt Fail-Safe / Nhất Quán Yếu

Các tập hợp đa luồng trong `java.util.concurrent` (như `CopyOnWriteArrayList`, `ConcurrentHashMap`, `ConcurrentLinkedQueue`) sử dụng trình duyệt **Fail-Safe** hoặc **Weakly-Consistent**.

### 1. Cơ Chế Sao Chép Khi Ghi (`CopyOnWriteArrayList`)
- Khi tạo `Iterator`, nó tham chiếu trực tiếp đến **mảng ảnh chụp (snapshot array)** tại thời điểm đó.
- Bất kỳ thao tác thêm/xóa nào trên `CopyOnWriteArrayList` sẽ tạo ra một bản sao mảng mới hoàn toàn. `Iterator` hiện tại vẫn tiếp tục duyệt trên mảng ảnh chụp cũ mà không bị ảnh hưởng.
- *Đánh đổi*: Tốn bộ nhớ khi ghi nhiều, nhưng bù lại thao tác đọc/duyệt hoàn toàn không cần khóa và không bao giờ ném `ConcurrentModificationException`.

### 2. Cơ Chế Nhất Quán Yếu (`ConcurrentHashMap`)
- Duyệt trực tiếp trên mảng thùng băm mà không cần tạo bản sao mảng.
- Phản ánh trạng thái của Map tại hoặc sau thời điểm tạo Iterator.
- Không ném `ConcurrentModificationException` khi có luồng khác ghi đồng thời.

---

## Bảng So Sánh Chi Tiết Fail-Fast vs Fail-Safe

| Tiêu Chí | Fail-Fast Iterator | Fail-Safe / Weakly-Consistent Iterator |
| :--- | :--- | :--- |
| **Gói hỗ trợ (Package)** | `java.util.*` (`ArrayList`, `HashMap`...) | `java.util.concurrent.*` (`CopyOnWriteArrayList`...) |
| **Ném ngoại lệ `ConcurrentModificationException`** | **Có** (Ngay lập tức khi phát hiện sửa đổi) | **Không bao giờ** |
| **Cơ chế hoạt động** | Kiểm tra biến đếm `modCount == expectedModCount` | Duyệt trên mảng ảnh chụp (Snapshot) hoặc con trỏ Volatile |
| **Chi phí bộ nhớ & Hiệu năng** | Thấp ($O(1)$ bộ nhớ phụ) | Cao hơn nếu phải tạo mảng ảnh chụp khi ghi |
| **Phản ánh thay đổi tức thời** | Không (Hành vi bị cấm) | Có thể phản ánh hoặc không phản ánh thay đổi |

---

## Minh Họa Mã Nguồn Chạy Được

```java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class IteratorBehaviorDemo {
    public static void main(String[] args) {
        // 1. Fail-Fast Iterator với ArrayList (Xóa an toàn bằng Iterator.remove)
        List<String> list = new ArrayList<>(List.of("A", "B", "C", "D"));
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String val = it.next();
            if (val.equals("B")) {
                it.remove(); // Hợp lệ! Cập nhật đồng thời modCount & expectedModCount
            }
        }
        System.out.println("ArrayList sau khi xóa an toàn qua Iterator: " + list);

        // 2. Fail-Safe Iterator với CopyOnWriteArrayList
        List<String> cowList = new CopyOnWriteArrayList<>(List.of("X", "Y", "Z"));
        for (String item : cowList) {
            if (item.equals("Y")) {
                cowList.add("NEW"); // Không ném ngoại lệ! Ghi vào bản sao mảng mới
            }
        }
        System.out.println("CopyOnWriteArrayList sau khi chỉnh sửa trong loop: " + cowList);
    }
}
```

---

## Bẫy Phỏng Vấn & Lưu Ý Thực Tế

1. **Lầm Tưởng Duyệt For-Index Là An Toàn Tuyệt Đối**:
   - *Bẫy*: Sử dụng vòng lặp chỉ số `for (int i = 0; i < list.size(); i++)` để xóa phần tử.
   - *Thực tế*: Dù không ném `ConcurrentModificationException`, việc `list.remove(i)` sẽ làm dịch chuyển các phần tử phía sau sang trái $\implies$ Phần tử ngay sau `i` sẽ bị **bỏ sót hoàn toàn** không được duyệt!

2. **Tính Tính Chất Best-Effort Của Fail-Fast**:
   - *Thực tế*: Javadoc của Java nhấn mạnh rằng cơ chế fail-fast chỉ mang tính chất *nỗ lực tối đa (best-effort)*. Không nên dựa vào `ConcurrentModificationException` để viết logic chương trình chính xác trong môi trường đa luồng.
