# Khung Bộ Sưu Tập - Phần 7 (Collections Framework - Part 7)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này tập trung vào một phần nhỏ trong **Khung Bộ Sưu Tập (Collections Framework)** bao gồm các bộ sưu tập bao bọc (`unmodifiableList`, `synchronizedList`) và các thuật toán thiết yếu của lớp tiện ích `Arrays`.

## Khung Nội Dung (Outline Coverage)

- **`Collections.unmodifiableList`** — Collections.unmodifiableList: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Collections.synchronizedList`** — Collections.synchronizedList: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Arrays.sort`** — Arrays.sort: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Arrays.binarySearch`** — Arrays.binarySearch: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Arrays.asList`** — Arrays.asList: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Arrays.copyOf`** — Arrays.copyOf: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Arrays.equals`** — Arrays.equals: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Arrays.deepEquals`** — Arrays.deepEquals: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

## Ghi Chú Chi Tiết (Detailed Notes)

### Danh Sách Không Thể Sửa Đổi vs. Danh Sách Bất Biến (Unmodifiable vs Immutable Lists)

`Collections.unmodifiableList(List)` trả về một **chế độ xem không thể sửa đổi (unmodifiable view)** của danh sách nền tảng bên dưới. Nó không hoàn toàn bất biến vì các thay đổi đối với danh sách gốc vẫn hiển thị trong chế độ xem này. Ngược lại, `List.copyOf()` và `List.of()` trả về các danh sách hoàn toàn **bất biến (immutable)** và không giữ bất kỳ tham chiếu nào đến các bộ sưu tập gốc.

**Ví Dụ Mã Nguồn Có Thể Chạy Được:**
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

        backingList.add("C"); // Sửa đổi danh sách gốc bên dưới

        System.out.println("Unmodifiable view: " + unmodifiableView); // [A, B, C]
        System.out.println("Immutable List: " + immutableList);       // [A, B]

        try {
            unmodifiableView.add("D"); // Ném ra ngoại lệ
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify unmodifiable view directly");
        }
    }
}
```

### Collections.synchronizedList

Trả về một lớp bao bọc được đồng bộ hóa (an toàn luồng).
- **Cạm Bẫy Khi Duyệt (Iteration Trap)**: Mặc dù các phương thức riêng lẻ (`add`, `get`) đều được đồng bộ hóa, việc duyệt (iterate) qua danh sách này KHÔNG tự động an toàn luồng. Bạn phải thực hiện đồng bộ hóa thủ công trên đối tượng danh sách bao bọc trong quá trình duyệt qua nó.

**Ví Dụ Mã Nguồn Có Thể Chạy Được:**
```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedListDemo {
    public static void main(String[] args) {
        List<String> syncList = Collections.synchronizedList(new ArrayList<>());
        syncList.add("A");
        syncList.add("B");

        // Việc duyệt qua danh sách an toàn đòi hỏi phải đồng bộ hóa thủ công
        synchronized (syncList) {
            for (String s : syncList) {
                System.out.println(s);
            }
        }
    }
}
```

### Lớp Tiện Ích Arrays (Arrays Utility Class)

- **`Arrays.sort()`** — Arrays.sort(): Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Arrays.binarySearch()`** — Arrays.binarySearch(): Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
  - **Quy tắc**: Nếu tìm thấy phần tử, nó trả về chỉ mục của nó. Nếu không tìm thấy, nó trả về `-(điểm_chèn) - 1`.
- **`Arrays.asList()`** — Arrays.asList(): Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Arrays.equals()`** — Arrays.equals(): Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

**Ví Dụ Mã Nguồn Có Thể Chạy Được:**
```java
import java.util.Arrays;
import java.util.List;

public class ArraysDemo {
    public static void main(String[] args) {
        // 1. Hành vi danh sách có kích thước cố định của Arrays.asList
        String[] arr = {"One", "Two"};
        List<String> list = Arrays.asList(arr);
        list.set(0, "Updated"); // Ghi trực tiếp xuống mảng nền tảng bên dưới
        System.out.println("Array value: " + arr[0]); // In ra: Updated

        // 2. Tìm kiếm nhị phân trên mảng đã sắp xếp
        int[] numbers = {10, 20, 30, 40};
        int index = Arrays.binarySearch(numbers, 30);
        System.out.println("Index of 30: " + index); // In ra: 2

        // 3. So sánh equals vs deepEquals
        int[][] matrix1 = {{1, 2}, {3, 4}};
        int[][] matrix2 = {{1, 2}, {3, 4}};
        System.out.println("Equals: " + Arrays.equals(matrix1, matrix2)); // false (so sánh định danh tham chiếu mảng 1 chiều)
        System.out.println("Deep Equals: " + Arrays.deepEquals(matrix1, matrix2)); // true (so sánh đệ quy nội dung lồng nhau)
    }
}
```

---

## Ví Dụ Thực Tế: So Sánh Collections.unmodifiableList vs List.copyOf vs List.of

Hãy xem xét hành vi tham chiếu, khả năng chấp nhận giá trị null và các đặc tính hiệu năng của các phương thức tạo tập hợp không thể sửa đổi/bất biến này.

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnmodifiableComparison {
    public static void main(String[] args) {
        List<String> original = new ArrayList<>();
        original.add("A");
        original.add(null); // original có chứa giá trị null

        // 1. Collections.unmodifiableList cho phép giá trị null vì nó là một chế độ xem bọc ngoài
        List<String> view = Collections.unmodifiableList(original);
        System.out.println("View size: " + view.size()); // In ra: 2

        // 2. List.copyOf ném ra NullPointerException nếu bộ sưu tập nguồn chứa giá trị null
        try {
            List.copyOf(original);
        } catch (NullPointerException e) {
            System.out.println("List.copyOf rejected list containing null");
        }

        // 3. List.of từ chối trực tiếp các phần tử null khi thêm vào
        try {
            List.of("A", null);
        } catch (NullPointerException e) {
            System.out.println("List.of rejected direct null insertion");
        }

        // 4. Tối ưu hóa vùng nhớ
        // Gọi List.copyOf trên một danh sách đã bất biến (được tạo bởi List.copyOf/List.of)
        // sẽ trả về CÙNG một tham chiếu (không nhân đôi đối tượng).
        List<String> immutable1 = List.of("X", "Y");
        List<String> immutable2 = List.copyOf(immutable1);
        System.out.println("Same reference: " + (immutable1 == immutable2)); // In ra: true!
    }
}
```

---

## Các Sai Lầm Thường Gặp (Common Mistakes)

### 1. Thêm/Xóa phần tử từ danh sách trả về bởi `Arrays.asList`
Vì danh sách trả về từ `Arrays.asList` có kích thước cố định, việc gọi `add()` hoặc `remove()` sẽ ném ra ngoại lệ `UnsupportedOperationException`. Để có một bản sao hoàn toàn khả biến, hãy bọc nó lại: `new ArrayList<>(Arrays.asList(arr))`.

### 2. Tìm kiếm nhị phân trên mảng chưa được sắp xếp
Việc gọi `Arrays.binarySearch()` trên một mảng chưa được sắp xếp sẽ trả về kết quả không thể dự đoán được. Hãy luôn sắp xếp mảng trước khi tìm kiếm.

### 3. Duyệt qua các danh sách được đồng bộ hóa mà không khóa thủ công
Thực hiện các vòng lặp đồng thời trên `Collections.synchronizedList()` mà không bao bọc trong một khối `synchronized(list)` là một lỗi lập trình dẫn đến tranh chấp điều kiện hoặc ném ra ngoại lệ `ConcurrentModificationException` nếu một luồng khác sửa đổi danh sách trong khi duyệt.

---

## Các Câu Hỏi Ôn Tập Thường Gặp (Common Review Prompts)

- Điều gì xảy ra khi bạn gọi `add()` trên một danh sách được tạo bởi `Arrays.asList()`? (Ném ra ngoại lệ UnsupportedOperationException)
- Sự khác biệt giữa `Arrays.equals` và `Arrays.deepEquals` là gì? (`equals` dùng cho mảng 1 chiều, `deepEquals` so sánh đệ quy cấu trúc mảng đa chiều)
- Lớp `List.copyOf` có sao chép các phần tử nếu danh sách nguồn đã là một danh sách bất biến không? (Không, nó trả về chính thực thể đó như một hình thức tối ưu hóa)

---

## Tại sao Chế Độ Xem Không Thể Sửa Đổi và Bộ Sưu Tập Bất Biến Khác Nhau (Why Unmodifiable Views and Immutable Collections Differ)

Trong Java, có sự khác biệt lớn về mặt kiến trúc giữa chế độ xem không thể sửa đổi (unmodifiable views) và bộ sưu tập thực sự bất biến (immutable collections). Khi bạn gọi `Collections.unmodifiableList()`, JVM sẽ xây dựng một lớp bao bọc (một thực thể của `Collections.UnmodifiableList`) để ủy quyền tất cả các hoạt động đọc trực tiếp cho danh sách gốc bên dưới, đồng thời chặn đứng các hoạt động ghi và ném ra ngoại lệ `UnsupportedOperationException`. Vì lớp bao bọc này duy trì một tham chiếu trực tiếp đến danh sách gốc, bất kỳ thay đổi cấu trúc nào được thực hiện trực tiếp trên danh sách gốc sẽ ngay lập tức được phản ánh khi truy vấn thông qua chế độ xem không thể sửa đổi đó. Ngược lại, `List.of()` và `List.copyOf()` tạo ra các thực thể bộ sưu tập hoàn toàn độc lập và bất biến (ví dụ: `ImmutableCollections.ListN`) để phân bổ một mảng mới, cô lập hoàn toàn bên dưới. Các bộ sưu tập bất biến này không tham chiếu đến bất kỳ mảng khả biến bên ngoài nào, lưu trữ các phần tử trong một cấu trúc được tối ưu hóa cao, từ chối hoàn toàn các phần tử `null` để ngăn ngừa lỗi thiết kế và cho phép các tối ưu hóa nội bộ của JVM như trả về cùng một thực thể khi sao chép một danh sách đã bất biến.

### Mô hình Tư duy (Mental Model)

Chế độ xem không thể sửa đổi bọc một danh sách khả biến đang hoạt động, trong khi các bộ sưu tập bất biến sao chép dữ liệu vào một cấu trúc riêng tư mới:
```text
Chế độ xem không thể sửa đổi (Unmodifiable View):
[ Unmodifiable View ] ---> [ Backing List (Khả biến) ] ---> [ Mảng phần tử (Heap) ]
    (Ném lỗi khi ghi)        (Có thể sửa đổi trực tiếp)        [ A ] [ B ] [ C ]

Bộ sưu tập bất biến (Immutable Collection - List.copyOf):
[ Immutable Collection ] ---> [ Mảng bất biến riêng tư (Heap) ]
    (Ném lỗi khi ghi)             [ A ] [ B ] (Hoàn toàn cô lập)
```

### Ví Dụ Mã Nguồn (Code Example)

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImmutabilityDemo {
    public static void main(String[] args) {
        List<String> mutableList = new ArrayList<>();
        mutableList.add("Red");
        mutableList.add("Green");

        // 1. Tạo chế độ xem bọc ngoài
        List<String> view = Collections.unmodifiableList(mutableList);

        // 2. Tạo bản sao của danh sách
        List<String> copy = List.copyOf(mutableList);

        // Sửa đổi danh sách gốc
        mutableList.add("Blue");

        System.out.println("Original List: " + mutableList); // In ra: Original List: [Red, Green, Blue]
        System.out.println("Unmodifiable View: " + view);    // In ra: Unmodifiable View: [Red, Green, Blue]
        System.out.println("Immutable Copy: " + copy);        // In ra: Immutable Copy: [Red, Green]
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

```text
Gọi Collections.unmodifiableList() → Lớp bọc giữ tham chiếu đến danh sách gốc → Danh sách gốc bị sửa đổi → Đọc thông qua chế độ xem truy cập trực tiếp danh sách gốc đã sửa đổi → Nhìn thấy các thay đổi.
Gọi List.copyOf() → Các phần tử được sao chép sang một cấu trúc mảng riêng tư mới → Danh sách gốc bị sửa đổi → Bộ sưu tập bất biến vẫn cô lập hoàn toàn → Không nhìn thấy các thay đổi.
```

## Liên Kết Tham Khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Collections.html#unmodifiableList(java.util.List) (Tài liệu về unmodifiableList)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/List.html#copyOf(java.util.Collection) (Tài liệu về List.copyOf)
