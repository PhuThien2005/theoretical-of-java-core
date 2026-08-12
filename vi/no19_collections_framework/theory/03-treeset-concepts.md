# TreeSet, SortedSet, NavigableSet & Hàng Đợi Ưu Tiên

## Tổng Quan Về Tập Hợp Có Thứ Tự (Ordered Set Abstractions)

Trong Java Collections Framework, giao diện `Set<E>` đảm bảo tính độc nhất của phần tử. Đối với các bài toán yêu cầu các phần tử **không chỉ độc nhất mà còn phải tự động duy trì thứ tự sắp xếp**, Java cung cấp các giao diện chuyên biệt `SortedSet<E>` và `NavigableSet<E>`, được triển khai cụ thể thông qua lớp `TreeSet<E>`.

---

## Phân Tích Chuyên Sâu Cấu Trúc Nội Tại `TreeSet`

### 1. Cơ Chế Cây Đỏ-Đen (Red-Black Tree Backing)
`TreeSet` được triển khai dựa trên một cây tìm kiếm nhị phân tự cân bằng (Self-Balancing Binary Search Tree) — cụ thể là lớp `TreeMap<E, Object>`.

```mermaid
graph TD
    Root[20 (Black)] --> Left[10 (Black)]
    Root --> Right[30 (Black)]
    Left --> LeftLeft[5 (Red)]
    Left --> LeftRight[15 (Red)]
```

- **Tính chất tự cân bằng**: Đảm bảo chiều cao của cây luôn duy trì ở mức $O(\log N)$.
- **Độ phức tạp thuật toán**: Tất cả các thao tác tìm kiếm (`contains`), thêm (`add`), và xóa (`remove`) đều có độ phức tạp thời gian guaranteed là $O(\log N)$.

---

### 2. Quy Tắc Loại Bỏ Trùng Lặp: `compareTo()` vs `equals()`

> [!IMPORTANT]
> `TreeSet` KHÔNG sử dụng `equals()` hay `hashCode()` để kiểm tra hai phần tử có trùng nhau hay không. Thay vào đó, nó dựa hoàn toàn vào phương thức `compareTo()` (từ `Comparable`) hoặc `compare()` (từ `Comparator`).

- **Quy tắc đẳng thức trong TreeSet**: 
  Nếu `compareTo(a, b) == 0`, `TreeSet` coi hai phần tử $a$ và $b$ là **trùng lặp hoàn toàn** và sẽ từ chối thêm $b$ vào tập hợp, ngay cả khi `a.equals(b)` trả về `false`.
- **Khuyến nghị nhất quán (Consistency with Equals)**:
  Phương thức `compareTo()` nên nhất quán với `equals()`:
  $$(a.\text{compareTo}(b) == 0) \iff (a.\text{equals}(b) == \text{true})$$
  Nếu vi phạm điều này, `TreeSet` vẫn hoạt động đúng về mặt kỹ thuật nhưng sẽ vi phạm hợp đồng chung của giao diện `Set`.

---

## Giao Diện `NavigableSet` & Các Thao Tác Tìm Kiếm Định Hướng

`NavigableSet<E>` mở rộng từ `SortedSet<E>` và bổ sung các phương thức điều hướng mạnh mẽ:

| Phương Thức | Mô Tả Ý Nghĩa Bại Số |
| :--- | :--- |
| `lower(e)` | Trả về phần tử lớn nhất $< e$, hoặc `null` nếu không có. |
| `floor(e)` | Trả về phần tử lớn nhất $\le e$, hoặc `null` nếu không có. |
| `ceiling(e)` | Trả về phần tử nhỏ nhất $\ge e$, hoặc `null` nếu không có. |
| `higher(e)` | Trả về phần tử nhỏ nhất $> e$, hoặc `null` nếu không có. |
| `pollFirst()` | Lấy ra và xóa phần tử nhỏ nhất (đầu cây). |
| `pollLast()` | Lấy ra và xóa phần tử lớn nhất (cuối cây). |
| `subSet(from, fromInc, to, toInc)` | Trả về góc nhìn (view) giới hạn phạm vi tùy chỉnh bao đóng hoặc mở. |

---

## Hàng Đợi Ưu Tiên `PriorityQueue` & `ArrayDeque`

### 1. `PriorityQueue<E>`
`PriorityQueue` là một hàng đợi không giới hạn sức chứa dựa trên một **Binary Min-Heap** được lưu dưới dạng mảng (`Object[] queue`).

- **Cơ chế hoạt động**: Phần tử nhỏ nhất (theo thứ tự sắp xếp) luôn nằm ở đầu hàng đợi (`head`).
- **Độ phức tạp**: `offer()` và `poll()` mất $O(\log N)$; `peek()` mất $O(1)$.
- **Lưu ý**: Duyệt qua `PriorityQueue` bằng Iterator sẽ **không bảo đảm thứ tự sắp xếp**. Chỉ lời gọi `poll()` liên tục mới rút ra các phần tử theo đúng thứ tự ưu tiên.

### 2. `ArrayDeque<E>`
`ArrayDeque` triển khai giao diện `Deque` dựa trên một **mảng vòng động (Resizable Circular Array)**.
- Không có chi phí tạo nút (no node allocation overhead).
- Vượt trội hoàn toàn so với `Stack` (do không bị lock `synchronized`) và `LinkedList` (do tính cục bộ bộ đệm CPU).
- Khuyên dùng làm lựa chọn mặc định cho ngăn xếp LIFO và hàng đợi FIFO.

---

## Bảng So Sánh Chi Tiết Các Triển Khai Set

| Tiêu Chí | `HashSet` | `LinkedHashSet` | `TreeSet` |
| :--- | :--- | :--- | :--- |
| **Cấu trúc lưu trữ** | Bảng băm (`HashMap`) | Bảng băm + Danh sách liên kết | Cây Đỏ-Đen (`TreeMap`) |
| **Độ phức tạp thao tác** | $O(1)$ | $O(1)$ | $O(\log N)$ |
| **Thứ tự phần tử** | Ngẫu nhiên | Thứ tự chèn (Insertion Order) | Thứ tự sắp xếp (Sorted Order) |
| **Phần tử `null`** | Cho phép 1 phần tử `null` | Cho phép 1 phần tử `null` | **Cấm `null`** (ném `NullPointerException`) |
| **Cơ chế so sánh** | `hashCode()` & `equals()` | `hashCode()` & `equals()` | `compareTo()` / `compare()` |

---

## Minh Họa Mã Nguồn Chạy Được

```java
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NavigableSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;

public class TreeSetNavigableDemo {
    public static void main(String[] args) {
        // 1. NavigableSet API Demo
        NavigableSet<Integer> scores = new TreeSet<>();
        scores.add(60);
        scores.add(75);
        scores.add(85);
        scores.add(90);

        System.out.println("Scores: " + scores);
        System.out.println("Lớn nhất <= 80 (floor): " + scores.floor(80)); // 75
        System.out.println("Nhỏ nhất > 75 (higher): " + scores.higher(75)); // 85

        // 2. PriorityQueue Demo (Min-Heap)
        Queue<Integer> minHeap = new PriorityQueue<>();
        minHeap.offer(40);
        minHeap.offer(10);
        minHeap.offer(25);

        System.out.print("Rút phần tử từ PriorityQueue: ");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " "); // Prints 10 25 40
        }
        System.out.println();

        // 3. ArrayDeque làm Stack (LIFO)
        Deque<String> undoStack = new ArrayDeque<>();
        undoStack.push("Action 1");
        undoStack.push("Action 2");
        System.out.println("Undo action: " + undoStack.pop()); // Action 2
    }
}
```

---

## Bẫy Phỏng Vấn & Lưu Ý Thực Tế

1. **Thêm Phần Tử `null` Vào `TreeSet`**:
   - *Bẫy*: Gọi `treeSet.add(null)`.
   - *Thực tế*: Ngay lập tức ném ra `NullPointerException` vì `TreeSet` cần gọi `null.compareTo(...)` để xác định vị trí trên cây.

2. **Duyệt `PriorityQueue` Bằng For-Each**:
   - *Bẫy*: Nghĩ rằng vòng lặp `for (int item : priorityQueue)` sẽ in ra các số đã sắp xếp.
   - *Thực tế*: Vòng lặp for-each chỉ duyệt trực tiếp qua mảng bên trong (Min-Heap array layout), không đảm bảo thứ tự tăng dần. Phải dùng `while (!queue.isEmpty()) queue.poll()`.
