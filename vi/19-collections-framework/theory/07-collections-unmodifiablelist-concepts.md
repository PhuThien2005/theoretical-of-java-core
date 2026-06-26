# Collections Framework - Phần 7 (Collections Framework - Part 7)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm một phần nội dung trọng tâm về **Collections Framework** bao gồm các tập hợp bao bọc (`unmodifiableList`, `synchronizedList`) và các thuật toán trong lớp tiện ích tiêu chuẩn `Arrays`.

## Các khái niệm bao phủ (Outline Coverage)

| Khái niệm | Những điều cần biết |
| --- | --- |
| `Collections.unmodifiableList` | Trả về một dạng hiển thị chỉ đọc (read-only view) của một danh sách hỗ trợ. Mọi nỗ lực sửa đổi nó đều ném ra ngoại lệ `UnsupportedOperationException`. Các sửa đổi trên danh sách hỗ trợ vẫn được cập nhật tới dạng hiển thị chỉ đọc này. |
| `Collections.synchronizedList` | Trả về một danh sách an toàn luồng được hỗ trợ bởi danh sách được chỉ định. Việc lặp qua phần tử yêu cầu đồng bộ hóa thủ công trên đối tượng danh sách. |
| `Arrays.sort` | Sắp xếp các mảng kiểu nguyên thủy hoặc mảng đối tượng. Mảng đối tượng sử dụng Timsort; mảng kiểu nguyên thủy sử dụng Dual-Pivot Quicksort. |
| `Arrays.binarySearch` | Tìm kiếm trên một mảng đã được sắp xếp. Trả về chỉ mục của kết quả khớp, hoặc một giá trị âm đại diện cho điểm chèn nếu không tìm thấy. Kết quả không xác định nếu mảng chưa được sắp xếp. |
| `Arrays.asList` | Trả về một danh sách có kích thước cố định được hỗ trợ bởi mảng được truyền vào. Các sửa đổi đối với các phần tử sẽ ghi trực tiếp vào mảng, nhưng các thay đổi cấu trúc (thêm/xóa) sẽ ném ra `UnsupportedOperationException`. |
| `Arrays.copyOf` | Sao chép mảng được chỉ định, cắt ngắn hoặc đệm thêm các giá trị mặc định nếu cần thiết. |
| `Arrays.equals` | So sánh hai mảng 1 chiều xem có bằng nhau hay không dựa trên nội dung phần tử. |
| `Arrays.deepEquals` | So sánh đệ quy các mảng đa chiều xem có bằng nhau hay không. |

---

## Ghi chú chi tiết (Detailed Notes)

### Danh sách Không thể sửa đổi so với Danh sách Bất biến (Unmodifiable vs Immutable Lists)

`Collections.unmodifiableList(List)` trả về một **giao diện chỉ đọc (unmodifiable view)** của danh sách hỗ trợ phía sau. Nó không hoàn toàn bất biến vì các sửa đổi đối với danh sách hỗ trợ gốc vẫn có thể nhìn thấy được từ giao diện chỉ đọc này. Ngược lại, `List.copyOf()` và `List.of()` trả về các danh sách hoàn toàn **bất biến (immutable)** và không giữ bất kỳ tham chiếu nào đến tập hợp gốc ban đầu.

**Ví dụ Code có thể chạy (Runnable Code Example):**
```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnmodifiableDemo {
    public static void main(String[] args) {
        List<String> backingList = new ArrayList<>();
        backingList.add("A");
        backingList.add("B");

        List<String> unmodifiableView = Collections.unmodifiableList(backingList);
        List<String> immutableList = List.copyOf(backingList);

        backingList.add("C"); // Modifying backing list

        System.out.println("Unmodifiable view: " + unmodifiableView); // [A, B, C]
        System.out.println("Immutable List: " + immutableList);       // [A, B]

        try {
            unmodifiableView.add("D"); // Throws exception
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify unmodifiable view directly");
        }
    }
}
```

### Collections.synchronizedList

Trả về một bộ bao bọc đồng bộ hóa (an toàn luồng).
- **Bẫy khi Duyệt qua (Iteration Trap)**: Mặc dù các phương thức đơn lẻ (`add`, `get`) là đồng bộ hóa, việc lặp qua danh sách thì KHÔNG an toàn luồng. Bạn bắt buộc phải đồng bộ hóa thủ công trên đối tượng danh sách bao bọc trong quá trình lặp.

**Ví dụ Code có thể chạy (Runnable Code Example):**
```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedListDemo {
    public static void main(String[] args) {
        List<String> syncList = Collections.synchronizedList(new ArrayList<>());
        syncList.add("A");
        syncList.add("B");

        // Safe iteration requires manual synchronization
        synchronized (syncList) {
            for (String s : syncList) {
                System.out.println(s);
            }
        }
    }
}
```

### Lớp tiện ích Arrays (Arrays Utility Class)

- **`Arrays.sort()`**: Sắp xếp trực tiếp trên mảng.
- **`Arrays.binarySearch()`**: Yêu cầu mảng phải được sắp xếp trước.
  - **Quy tắc**: Nếu tìm thấy phần tử, nó trả về chỉ mục. Nếu không tìm thấy, nó trả về `-(điểm chèn) - 1`.
- **`Arrays.asList()`**: Bao bọc một mảng thành một danh sách có kích thước cố định.
- **`Arrays.equals()` so với `Arrays.deepEquals()`**: `equals()` so sánh các phần tử tham chiếu của mảng 1 chiều; `deepEquals()` so sánh đệ quy các mảng con trong mảng đa chiều.

**Ví dụ Code có thể chạy (Runnable Code Example):**
```java
import java.util.Arrays;
import java.util.List;

public class ArraysDemo {
    public static void main(String[] args) {
        // 1. Arrays.asList fixed-size list behavior
        String[] arr = {"One", "Two"};
        List<String> list = Arrays.asList(arr);
        list.set(0, "Updated"); // Writes through to backing array
        System.out.println("Array value: " + arr[0]); // Updated

        // 2. Binary search on sorted array
        int[] numbers = {10, 20, 30, 40};
        int index = Arrays.binarySearch(numbers, 30);
        System.out.println("Index of 30: " + index); // 2

        // 3. Equals vs Deep Equals
        int[][] matrix1 = {{1, 2}, {3, 4}};
        int[][] matrix2 = {{1, 2}, {3, 4}};
        System.out.println("Equals: " + Arrays.equals(matrix1, matrix2)); // false (checks 1D reference identity)
        System.out.println("Deep Equals: " + Arrays.deepEquals(matrix1, matrix2)); // true (checks nested contents)
    }
}
```

---

## Case Study: Đánh giá Collections.unmodifiableList so với List.copyOf và List.of (Case Study: Evaluating Collections.unmodifiableList vs List.copyOf vs List.of)

Chúng ta hãy xem xét hành vi tham chiếu, việc cho phép null và các đặc tính hiệu năng của các factory chỉ đọc/bất biến này.

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnmodifiableComparison {
    public static void main(String[] args) {
        List<String> original = new ArrayList<>();
        original.add("A");
        original.add(null); // original has null

        // 1. Collections.unmodifiableList allows nulls because it's a wrapper view
        List<String> view = Collections.unmodifiableList(original);
        System.out.println("View size: " + view.size()); // 2

        // 2. List.copyOf throws NullPointerException if collection contains null
        try {
            List.copyOf(original);
        } catch (NullPointerException e) {
            System.out.println("List.copyOf rejected list containing null");
        }

        // 3. List.of rejects null elements directly
        try {
            List.of("A", null);
        } catch (NullPointerException e) {
            System.out.println("List.of rejected direct null insertion");
        }

        // 4. Memory footprint and optimization
        // List.copyOf of an already immutable list returned by List.copyOf/List.of
        // will return the SAME reference (no duplication).
        List<String> immutable1 = List.of("X", "Y");
        List<String> immutable2 = List.copyOf(immutable1);
        System.out.println("Same reference: " + (immutable1 == immutable2)); // true!
    }
}
```

---

## Các lỗi thường gặp (Common Mistakes)

### 1. Thêm/xóa các phần tử từ một danh sách Arrays.asList (Adding/removing elements from an Arrays.asList list)
Vì danh sách được trả về bởi `Arrays.asList` có kích thước cố định, việc gọi `add()` hoặc `remove()` sẽ ném ra ngoại lệ `UnsupportedOperationException`. Để có một bản sao hoàn toàn có thể thay đổi được, hãy bao bọc nó: `new ArrayList<>(Arrays.asList(arr))`.

### 2. Tìm kiếm nhị phân trên mảng chưa được sắp xếp (Binary search on unsorted arrays)
Việc gọi `Arrays.binarySearch()` trên một mảng chưa được sắp xếp sẽ trả về các kết quả không thể dự đoán được. Hãy luôn sắp xếp mảng trước khi tìm kiếm.

### 3. Duyệt qua các danh sách đồng bộ hóa mà không khóa thủ công (Iterating synchronized lists without manual locking)
Việc viết các vòng lặp đồng thời trên `Collections.synchronizedList()` mà không bao bọc trong khối `synchronized(list)` là một lỗi lập trình dẫn đến tranh chấp dữ liệu (race conditions) hoặc ném ra `ConcurrentModificationException` nếu một luồng khác sửa đổi danh sách trong quá trình duyệt qua.

---

## Các câu hỏi ôn tập phổ biến (Common Review Prompts)

- Điều gì xảy ra nếu bạn gọi `add()` trên một danh sách trả về từ `Arrays.asList()`? (Ném ra ngoại lệ UnsupportedOperationException)
- Sự khác biệt giữa `Arrays.equals` và `Arrays.deepEquals` là gì? (equals dành cho mảng 1 chiều, deepEquals so sánh đệ quy các cấu trúc mảng đa chiều)
- Lệnh List.copyOf có sao chép các phần tử nếu danh sách nguồn đã là một danh sách bất biến? (Không, nó trả về chính thực thể đó như một cách tối ưu hóa)

---

## Tại sao giao diện không thể sửa đổi (Unmodifiable Views) và Tập hợp bất biến (Immutable Collections) khác nhau (Why Unmodifiable Views and Immutable Collections Differ)

Trong Java, có sự khác biệt cơ bản về mặt kiến trúc giữa giao diện không thể sửa đổi (unmodifiable views) và tập hợp thực sự bất biến (immutable collections). Khi bạn gọi `Collections.unmodifiableList()`, JVM xây dựng một lớp bao bọc (một thực thể của `Collections.UnmodifiableList`) ủy quyền tất cả các thao tác đọc trực tiếp đến danh sách hỗ trợ gốc, trong khi chặn các thao tác ghi để ném ra ngoại lệ `UnsupportedOperationException`. Vì bộ bao bọc giữ một tham chiếu trực tiếp đến danh sách gốc, mọi thay đổi cấu trúc được thực hiện trực tiếp trên danh sách hỗ trợ đó sẽ ngay lập tức được phản ánh khi truy vấn giao diện không thể sửa đổi. Ngược lại, `List.of()` và `List.copyOf()` tạo ra các thực thể tập hợp hoàn toàn độc lập, bất biến (ví dụ: `ImmutableCollections.ListN`) cấp phát một mảng mới, cô lập bên dưới. Các tập hợp bất biến này không tham chiếu đến bất kỳ mảng khả biến bên ngoài nào, lưu trữ các phần tử trong một cấu trúc được tối ưu hóa cao, từ chối hoàn toàn các phần tử `null` để ngăn chặn các lỗi thiết kế, và cho phép các tối ưu hóa nội bộ của JVM như trả về cùng một thực thể khi sao chép một danh sách đã bất biến.

### Mô hình tư duy (Mental Model)

Giao diện không thể sửa đổi bao bọc một danh sách khả biến đang hoạt động, trong khi tập hợp bất biến sao chép dữ liệu vào một cấu trúc private mới:
```text
Unmodifiable View (Giao diện không thể sửa đổi):
[ Unmodifiable View ] ---> [ Backing List (Mutable) ] ---> [ Mảng phần tử (Heap) ]
    (Ném lỗi khi ghi)        (Có thể sửa đổi trực tiếp)         [ A ] [ B ] [ C ]

Immutable Collection (List.copyOf - Tập hợp bất biến):
[ Immutable Collection ] ---> [ Mảng bất biến private (Heap) ]
    (Ném lỗi khi ghi)             [ A ] [ B ] (Hoàn toàn cô lập)
```

### Ví dụ Code (Code Example)

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImmutabilityDemo {
    public static void main(String[] args) {
        List<String> mutableList = new ArrayList<>();
        mutableList.add("Red");
        mutableList.add("Green");

        // 1. Create wrapper view
        List<String> view = Collections.unmodifiableList(mutableList);

        // 2. Create copy of list
        List<String> copy = List.copyOf(mutableList);

        // Modify the original list
        mutableList.add("Blue");

        System.out.println("Original List: " + mutableList); // Original List: [Red, Green, Blue]
        System.out.println("Unmodifiable View: " + view);    // Unmodifiable View: [Red, Green, Blue]
        System.out.println("Immutable Copy: " + copy);        // Immutable Copy: [Red, Green]
    }
}
```

### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)

```text
Gọi Collections.unmodifiableList() &rarr; Bộ bao bọc giữ tham chiếu đến danh sách hỗ trợ &rarr; Danh sách hỗ trợ bị sửa đổi &rarr; Các lượt đọc qua view truy cập danh sách hỗ trợ đã bị sửa đổi &rarr; Các thay đổi hiển thị được.
Gọi List.copyOf() &rarr; Các phần tử được sao chép sang cấu trúc mảng private mới &rarr; Danh sách hỗ trợ bị sửa đổi &rarr; Tập hợp bất biến vẫn hoàn toàn cô lập &rarr; Các thay đổi không hiển thị được.
```

## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Collections.html#unmodifiableList(java.util.List) (Tài liệu unmodifiableList)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/List.html#copyOf(java.util.Collection) (Tài liệu List.copyOf)
