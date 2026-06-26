# Collections Framework - Phần 6 (Collections Framework - Part 6)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm một phần nội dung trọng tâm về **Collections Framework** bao gồm các hành vi của iterator (`Fail-fast` so với `Fail-safe`), các sửa đổi cấu trúc dữ liệu, và các thuật toán trong lớp tiện ích tiêu chuẩn `Collections`.

## Các khái niệm bao phủ (Outline Coverage)

| Khái niệm | Những điều cần biết |
| --- | --- |
| `Fail-fast iterator` | Ném ra ngoại lệ `ConcurrentModificationException` ngay lập tức nếu tập hợp bị thay đổi cấu trúc trong quá trình lặp (thông qua các phương thức khác ngoài phương thức của chính iterator). |
| `Fail-safe iterator` | Hoạt động trên một bản chụp (snapshot) hoặc một dạng hiển thị đồng nhất yếu (weakly consistent view) của tập hợp, cho phép thực hiện các sửa đổi trong quá trình lặp mà không ném ra ngoại lệ. |
| `ConcurrentModificationException` | Ngoại lệ lúc chạy (runtime exception) được ném ra khi phát hiện ra thay đổi cấu trúc trên một tập hợp trong khi đang thực hiện lặp qua nó. |
| `Collections.sort` | Sắp xếp trực tiếp trên danh sách trong thời gian trung bình/xấu nhất là $O(N \log N)$. |
| `Collections.reverse` | Đảo ngược thứ tự các phần tử trong danh sách. |
| `Collections.shuffle` | Xáo trộn ngẫu nhiên thứ tự các phần tử trong danh sách. |
| `Collections.max` | Trả về phần tử lớn nhất trong một tập hợp theo thứ tự tự nhiên hoặc theo một comparator tùy chỉnh. |
| `Collections.min` | Trả về phần tử nhỏ nhất trong tập hợp. |

---

## Ghi chú chi tiết (Detailed Notes)

### Fail-Fast Iterator

Các iterator của các tập hợp tiêu chuẩn (như `ArrayList`, `HashSet`, `HashMap`) đều có tính chất **fail-fast**.
- **Cơ chế**: Tập hợp duy trì một biến đếm gọi là `modCount` (modification count - số lần sửa đổi). Khi một iterator được tạo ra, nó sẽ sao chép giá trị `modCount` vào biến `expectedModCount` của chính nó. Mỗi khi gọi `next()` hoặc `remove()`, iterator sẽ so sánh hai giá trị đếm này. Nếu có sự khác biệt (nghĩa là đã xảy ra một sửa đổi cấu trúc từ bên ngoài iterator), nó lập tức ném ra ngoại lệ `ConcurrentModificationException`.

**Ví dụ Code có thể chạy (Hành vi Fail-Fast) (Runnable Code Example (Fail-Fast Behavior)):**
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

### Iterator Fail-Safe / Đồng nhất Yếu (Fail-Safe / Weakly Consistent Iterator)

Các iterator cho các tập hợp đồng thời (như `CopyOnWriteArrayList`, `ConcurrentHashMap`) sẽ không ném ra ngoại lệ `ConcurrentModificationException`.
- **Dựa trên bản chụp (Snapshot-based - ví dụ: `CopyOnWriteArrayList`)**: Iterator hoạt động trên một bản chụp (snapshot) của mảng bên dưới được chụp lại tại thời điểm iterator được tạo ra. Mọi sửa đổi trên tập hợp trong quá trình lặp sẽ tạo ra các bản sao mảng mới, giữ cho bản chụp của iterator không bị ảnh hưởng.
- **Đồng nhất yếu (Weakly consistent - ví dụ: `ConcurrentHashMap`)**: Iterator duyệt qua các phần tử khi chúng tồn tại, có thể phản ánh hoặc không phản ánh các sửa đổi tiếp theo, nhưng sẽ không bao giờ gây sập chương trình.

**Ví dụ Code có thể chạy (Lặp qua Bản chụp) (Runnable Code Example (Snapshot Iteration)):**
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

### Lớp tiện ích Collections (Collections Utility Class)

`java.util.Collections` cung cấp các thuật toán tĩnh hoạt động trên các tập hợp.
- **`sort(List<T> list)`**: Sắp xếp danh sách. Sử dụng thuật toán Timsort. Sắp xếp trực tiếp trên danh sách truyền vào.
- **`reverse(List<?> list)`**: Đảo ngược thứ tự các phần tử của danh sách.
- **`shuffle(List<?> list)`**: Xáo trộn ngẫu nhiên thứ tự các phần tử.
- **`max(Collection<? extends T> coll)`** / **`min(Collection<? extends T> coll)`**: Tìm các phần tử cực trị dựa trên thứ tự sắp xếp.

**Ví dụ Code có thể chạy (Runnable Code Example):**
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

## Case Study: Phân tích Hiệu năng của Iterator Fail-Fast so với Snapshot (Case Study: Analyzing Fail-Fast vs Snapshot Iterator Performance)

Chúng ta hãy viết một bài kiểm tra hiệu năng để quan sát chi phí của các sửa đổi trên các tập hợp tiêu chuẩn (ném ra ngoại lệ trừ khi sử dụng `iterator.remove()`) so với các tập hợp dựa trên bản chụp (copy-on-write) đồng thời (sẽ sao chép toàn bộ mảng hỗ trợ mỗi khi ghi dữ liệu).

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

## Các lỗi thường gặp (Common Mistakes)

### 1. Sửa đổi một danh sách trong vòng lặp for-each (Modifying a list in a for-each loop)
Vòng lặp for-each trong Java thực chất sử dụng một iterator ở bên dưới. Việc gọi `list.remove(item)` bên trong vòng lặp for-each sẽ kích hoạt ngoại lệ `ConcurrentModificationException` vì thay đổi này mang tính cấu trúc và diễn ra bên ngoài iterator.
```java
// BUG: Will throw ConcurrentModificationException
for (String item : list) {
    if (item.equals("target")) {
        list.remove(item);
    }
}
```

### 2. Chi phí ghi cực lớn của CopyOnWriteArrayList (High overhead of CopyOnWriteArrayList writes)
Việc sử dụng `CopyOnWriteArrayList` trong một vòng lặp có mật độ ghi cao là một sai lầm lớn. Vì mỗi thao tác ghi sẽ sao chép nhân bản mảng hỗ trợ phía sau, các danh sách kích thước lớn sẽ gây ra áp lực bộ nhớ và các khoảng dừng do thu dọn rác cực lớn.

### 3. Giả định rằng việc sửa đổi qua Iterator ảnh hưởng đến Collection gốc trên mọi kiểu danh sách (Assuming Iterator modification affects the original Collection in all list types)
Một số dạng hiển thị danh sách (như `List.of()` hoặc `Collections.unmodifiableList()`) sẽ ném ra ngoại lệ `UnsupportedOperationException` nếu bạn cố tình gọi `iterator.remove()`.

---

## Các câu hỏi ôn tập phổ biến (Common Review Prompts)

- Làm thế nào một iterator fail-fast phát hiện các thay đổi đồng thời? (Bằng cách so sánh giá trị expectedModCount của iterator với modCount của tập hợp)
- Kiểu đối tượng nào được trả về bởi iterator của CopyOnWriteArrayList? (Một iterator mảng bản chụp (snapshot array iterator) không theo dõi các thay đổi cấu trúc)
- Phương thức Collections.sort thực hiện sắp xếp trực tiếp trên danh sách hay trả về một danh sách mới? (Nó thực hiện sắp xếp trực tiếp trên danh sách được truyền vào)

---

## Tại sao các Fail-Fast Iterator ném ra ngoại lệ ConcurrentModificationException (Why Fail-Fast Iterators Throw ConcurrentModificationException)

Để ngăn ngừa hành vi lúc chạy khó lường và lỗi sai lệch dữ liệu, các tập hợp không đồng thời của Java sử dụng cơ chế iterator fail-fast để phát hiện các sửa đổi đồng thời. Tập hợp hỗ trợ bên dưới duy trì một biến đếm nội bộ gọi là `modCount` (modification count), biến này sẽ tăng lên với mỗi sửa đổi cấu trúc như thêm mới, chèn hoặc xóa phần tử. Khi một iterator được khởi tạo, nó sẽ lưu giá trị đếm này vào trường private của riêng mình, `expectedModCount`. Trong các thao tác tiếp theo như `next()`, `remove()`, hoặc `forEachRemaining()`, iterator sẽ so sánh giá trị `modCount` hiện tại của tập hợp với giá trị `expectedModCount` đã lưu của nó. Nếu chúng không khớp nhau, cho biết tập hợp đã bị thay đổi bên ngoài tầm kiểm soát của iterator, iterator sẽ lập tức ném ra ngoại lệ `ConcurrentModificationException`. Ngược lại, các iterator fail-safe hoặc weakly-consistent (như của lớp `CopyOnWriteArrayList`) tránh hoàn toàn xung đột này bằng cách lặp trên một bản chụp (snapshot) bất biến của mảng hỗ trợ được tạo ra tại thời điểm khởi dựng iterator, nghĩa là các sửa đổi đối với tập hợp trực tiếp sẽ nhắm vào một bản sao riêng biệt và không bao giờ can thiệp vào bản chụp của iterator.

### Mô hình tư duy (Mental Model)

Iterator fail-fast kiểm tra các giá trị đếm sửa đổi ở từng bước lặp:
```text
Trạng thái Collection: modCount = 3
Khởi tạo Iterator -> expectedModCount = 3

1. Gọi iterator.next():
   So sánh modCount (3) == expectedModCount (3) -> OK! Trả về phần tử.

2. Gọi collection.remove(x) (ngoài iterator):
   Collection tăng modCount lên 4.

3. Gọi iterator.next():
   So sánh modCount (4) == expectedModCount (3) -> Phát hiện không khớp!
   Hành động: Ném ra ConcurrentModificationException lập tức.
```

### Ví dụ Code (Code Example)

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
        }
    }
}
```

### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)


```text
Khởi tạo Iterator
  → Lưu `modCount` vào `expectedModCount`
  → Sửa đổi cấu trúc tập hợp (thêm/xóa)
  → `modCount` tăng lên trên tập hợp gốc
  → Iterator gọi `next()` và so sánh `modCount` với `expectedModCount`
  → Phát hiện không khớp
  → Ném ra ngoại lệ `ConcurrentModificationException` ngay lập tức.
```


## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ConcurrentModificationException.html (Ngoại lệ ConcurrentModificationException API)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/CopyOnWriteArrayList.html (Tài liệu API CopyOnWriteArrayList)
