# Cấu Trúc Tập Hợp (Collections Framework) - Phần 6

## Mục Tiêu Học Tập

File này đề cập đến một phần trọng tâm của **Cấu Trúc Tập Hợp (Collections Framework)** bao gồm các hành vi của bộ lặp (iterator) (`Fail-fast` so với `Fail-safe`), các sửa đổi cấu trúc, và các thuật toán trong lớp tiện ích `Collections` tiêu chuẩn.

## Đề Cương Khái Niệm

| Khái niệm | Những điều cần biết |
| --- | --- |
| `Fail-fast iterator` | Ném ra `ConcurrentModificationException` ngay lập tức nếu tập hợp bị sửa đổi cấu trúc trong quá trình duyệt (thông qua các phương thức khác ngoài phương thức của chính bộ lặp). |
| `Fail-safe iterator` | Hoạt động trên một bản chụp (snapshot) hoặc một dạng xem nhất quán yếu (weakly consistent view) của tập hợp, cho phép sửa đổi trong quá trình duyệt mà không ném ra ngoại lệ. |
| `ConcurrentModificationException` | Ngoại lệ thời gian chạy (runtime exception) được ném ra khi phát hiện sửa đổi cấu trúc trên một tập hợp trong quá trình duyệt đang diễn ra. |
| `Collections.sort` | Sắp xếp một danh sách tại chỗ với thời gian trung bình/tệ nhất là $O(N \log N)$. |
| `Collections.reverse` | Đảo ngược thứ tự các phần tử trong danh sách. |
| `Collections.shuffle` | Hoán vị ngẫu nhiên các phần tử trong danh sách. |
| `Collections.max` | Trả về phần tử lớn nhất trong một tập hợp theo thứ tự tự nhiên hoặc theo một bộ so sánh (comparator) tùy chỉnh. |
| `Collections.min` | Trả về phần tử nhỏ nhất trong một tập hợp. |

## Ghi Chú Chi Tiết

### Bộ Lặp Fail-Fast (Fail-Fast Iterator)

Các bộ lặp cho các tập hợp tiêu chuẩn (như `ArrayList`, `HashSet`, `HashMap`) có tính chất **fail-fast**.
- **Cơ chế**: Tập hợp duy trì một bộ đếm được gọi là `modCount` (số lần sửa đổi - modification count). Khi một bộ lặp được tạo ra, nó sao chép `modCount` vào `expectedModCount`. Trên mỗi lời gọi `next()` hoặc `remove()`, bộ lặp sẽ so sánh hai giá trị đếm này. Nếu chúng không khớp (nghĩa là đã xảy ra sửa đổi bên ngoài bộ lặp), nó sẽ ngay lập tức ném ra `ConcurrentModificationException`.

**Ví dụ Mã Nguồn Chạy Được (Hành vi Fail-Fast):**
```java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FailFastDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("A", "B", "C"));
        Iterator<String> it = list.iterator();

        try {
            while (it.hasNext()) {
                String val = it.next();
                if (val.equals("B")) {
                    list.remove(val); // Modifies the collection structurally!
                }
            }
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Caught expected ConcurrentModificationException");
        }
    }
}
```

### Bộ Lặp Fail-Safe / Nhất Quán Yếu (Fail-Safe / Weakly Consistent Iterator)

Các bộ lặp cho các tập hợp đồng thời (như `CopyOnWriteArrayList`, `ConcurrentHashMap`) không ném ra `ConcurrentModificationException`.
- **Dựa trên bản chụp (Snapshot-based) (ví dụ: `CopyOnWriteArrayList`)**: Bộ lặp hoạt động trên một bản chụp (snapshot) của mảng cơ sở được chụp lại khi bộ lặp được tạo ra. Các sửa đổi trong quá trình duyệt sẽ tạo ra các bản sao mảng mới, giữ cho bản chụp của bộ lặp không bị ảnh hưởng.
- **Nhất quán yếu (Weakly consistent) (ví dụ: `ConcurrentHashMap`)**: Bộ lặp duyệt qua các phần tử khi chúng tồn tại, và có thể phản ánh hoặc không phản ánh các sửa đổi tiếp theo, nhưng sẽ không bao giờ gây lỗi crash.

**Ví dụ Mã Nguồn Chạy Được (Duyệt Trên Bản Chụp):**
```java
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class FailSafeDemo {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[]{"A", "B", "C"});
        Iterator<String> it = list.iterator();

        while (it.hasNext()) {
            String val = it.next();
            if (val.equals("B")) {
                list.remove(val); // Safe, makes a copy under the hood
            }
        }
        System.out.println("List after loop: " + list); // [A, C]
    }
}
```

### Lớp Tiện Ích Collections

`java.util.Collections` cung cấp các thuật toán tĩnh hoạt động trên các tập hợp.
- **`sort(List<T> list)`**: Sắp xếp danh sách. Sử dụng thuật toán Timsort. Sửa đổi danh sách tại chỗ.
- **`reverse(List<?> list)`**: Đảo ngược thứ tự các phần tử của danh sách.
- **`shuffle(List<?> list)`**: Sắp xếp lại ngẫu nhiên các phần tử.
- **`max(Collection<? extends T> coll)`** / **`min(Collection<? extends T> coll)`**: Tìm các phần tử cực trị dựa trên thứ tự sắp xếp.

**Ví dụ Mã Nguồn Chạy Được:**
```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsDemo {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(List.of(30, 10, 20));

        Collections.sort(numbers);
        System.out.println("Sorted: " + numbers); // [10, 20, 30]

        Collections.reverse(numbers);
        System.out.println("Reversed: " + numbers); // [30, 20, 10]

        Collections.shuffle(numbers);
        System.out.println("Max: " + Collections.max(numbers)); // 30
    }
}
```

---

## Ví Dụ Thực Tế: Phân Tích Hiệu Năng Của Bộ Lặp Fail-Fast Với Bộ Lặp Snapshot

Hãy viết một bài kiểm tra hiệu năng để quan sát chi phí của các sửa đổi trên tập hợp tiêu chuẩn (vốn ném ra ngoại lệ trừ khi sử dụng `iterator.remove()`) so với các tập hợp đồng thời dựa trên bản chụp (vốn sao chép toàn bộ mảng sao lưu khi ghi).

```java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class IteratorBenchmark {
    public static void main(String[] args) {
        int count = 10_000;
        
        // 1. Benchmarking ArrayList using Iterator.remove()
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < count; i++) arrayList.add(i);

        long start = System.currentTimeMillis();
        Iterator<Integer> it1 = arrayList.iterator();
        while (it1.hasNext()) {
            if (it1.next() % 2 == 0) {
                it1.remove(); // Structural modification through iterator is fast and allowed
            }
        }
        long durationArrayList = System.currentTimeMillis() - start;

        // 2. Benchmarking CopyOnWriteArrayList using list.remove()
        List<Integer> cowList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < count; i++) cowList.add(i);

        start = System.currentTimeMillis();
        Iterator<Integer> it2 = cowList.iterator();
        while (it2.hasNext()) {
            int val = it2.next();
            if (val % 2 == 0) {
                cowList.remove(Integer.valueOf(val)); // Triggers array copying every time!
            }
        }
        long durationCowList = System.currentTimeMillis() - start;

        System.out.println("ArrayList (iterator.remove()): " + durationArrayList + " ms");
        System.out.println("CopyOnWriteArrayList (cowList.remove()): " + durationCowList + " ms");
    }
}
```

---

## Các Lỗi Thường Gặp

### 1. Sửa Đổi Một List Trong Vòng Lặp For-Each

Vòng lặp for-each trong Java sử dụng một bộ lặp một cách nội bộ. Gọi `list.remove(item)` bên trong vòng lặp for-each sẽ kích hoạt một `ConcurrentModificationException` vì sửa đổi này mang tính cấu trúc và diễn ra bên ngoài bộ lặp.
```java
// BUG: Will throw ConcurrentModificationException
for (String item : list) {
    if (item.equals("target")) {
        list.remove(item);
    }
}
```

### 2. Chi Phí Lớn Của Các Phép Ghi Trên CopyOnWriteArrayList

Sử dụng `CopyOnWriteArrayList` trong một vòng lặp chuyên ghi dữ liệu là một sai lầm rất lớn. Vì mỗi lần ghi sẽ tạo một bản sao của mảng sao lưu, các danh sách lớn sẽ gây ra hiện tượng xáo trộn bộ nhớ và dẫn đến các khoảng tạm dừng để thu gom rác (garbage collection pause).

### 3. Giả Định Rằng Việc Sửa Đổi Iterator Luôn Ảnh Hưởng Đến Tập Hợp Gốc Trong Mọi Kiểu Danh Sách

Một số dạng xem danh sách (như `List.of()` hoặc `Collections.unmodifiableList()`) sẽ ném ra `UnsupportedOperationException` nếu bạn gọi `iterator.remove()`.

---

## Các Câu Hỏi Ôn Tập Thường Gặp

- Làm thế nào một bộ lặp fail-fast phát hiện các sửa đổi đồng thời? (Bằng cách so sánh giá trị expectedModCount của bộ lặp với modCount của tập hợp)
- Kiểu bộ lặp nào được trả về bởi bộ lặp của CopyOnWriteArrayList? (Một bộ lặp mảng bản chụp không theo dõi các sửa đổi)
- Liệu Collections.sort sửa đổi danh sách tại chỗ hay trả về một danh sách mới? (Nó sửa đổi danh sách tại chỗ)

## Tại Sao Bộ Lặp Fail-Fast Ném Ra ConcurrentModificationException

Để ngăn chặn các hành vi runtime không thể đoán trước và lỗi hỏng dữ liệu, các tập hợp không đồng thời của Java sử dụng cơ chế bộ lặp fail-fast để phát hiện các sửa đổi đồng thời. Tập hợp bên dưới duy trì một trình theo dõi nội bộ gọi là `modCount` (số lần sửa đổi), số này sẽ tăng lên sau mỗi lần sửa đổi cấu trúc như thêm, chèn hoặc xóa. Khi một bộ lặp được khởi tạo, nó sẽ lưu giá trị bộ đếm này vào trường riêng của nó là `expectedModCount`. Trong suốt các hoạt động tiếp theo như `next()`, `remove()`, hoặc `forEachRemaining()`, bộ lặp so sánh `modCount` đang chạy của tập hợp với giá trị `expectedModCount` mà nó lưu trữ. Nếu chúng không khớp, cho thấy tập hợp đã bị thay đổi ngoài tầm kiểm soát của bộ lặp, bộ lặp sẽ ngay lập tức ném ra một ngoại lệ `ConcurrentModificationException`. Ngược lại, các bộ lặp fail-safe hoặc nhất quán yếu (như của `CopyOnWriteArrayList`) tránh hoàn toàn xung đột này bằng cách duyệt trên một bản chụp bất biến của mảng sao lưu được tạo tại thời điểm xây dựng bộ lặp, nghĩa là các sửa đổi đối với tập hợp đang chạy sẽ nhắm vào một bản sao riêng biệt và không bao giờ can thiệp vào bản chụp của bộ lặp.

### Bộ Lặp Fail-Fast Kiểm Tra Số Lần Sửa Đổi Ở Mỗi Bước (Mental Model)

```text
Trạng thái tập hợp: modCount = 3
Khởi tạo bộ lặp -> expectedModCount = 3

1. Gọi iterator.next():
   So sánh modCount (3) == expectedModCount (3) -> OK! Trả về phần tử.

2. Gọi collection.remove(x) (ngoài bộ lặp):
   Tập hợp tăng modCount lên 4.

3. Gọi iterator.next():
   So sánh modCount (4) == expectedModCount (3) -> Phát hiện không khớp!
   Hành động: Ném ConcurrentModificationException ngay lập tức.
```

### Ví Dụ Mã Nguồn (Code Example)

```java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConcurrentModificationDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Python");
        list.add("Go");

        Iterator<String> iterator = list.iterator();

        try {
            while (iterator.hasNext()) {
                String language = iterator.next();
                if (language.equals("Python")) {
                    // Modifying the backing list directly, not via iterator.remove()
                    list.remove(language);
                }
            }
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Caught ConcurrentModificationException!");
            // Caught ConcurrentModificationException!
        }
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

```text
Khởi tạo Bộ lặp → Lưu modCount vào expectedModCount → Sửa đổi cấu trúc tập hợp (thêm/xóa) → modCount tăng trên tập hợp cơ sở → Bộ lặp gọi next() và so sánh modCount với expectedModCount → Phát hiện không khớp → Ném ConcurrentModificationException ngay lập tức
```

## Liên Kết Tham Khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ConcurrentModificationException.html (Tài liệu API ConcurrentModificationException)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/CopyOnWriteArrayList.html (Tài liệu API CopyOnWriteArrayList)
