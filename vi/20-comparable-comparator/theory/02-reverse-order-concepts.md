# Comparable và Comparator - Phần 2

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này trình bày một phần trọng tâm về **Comparable và Comparator**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là những từ vựng rời rạc.

## Phạm Vi Nội Dung (Outline Coverage)

- **`Reverse order`** — Thứ tự đảo ngược (Reverse order) là một khái niệm cụ thể trong Comparable và Comparator; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và các lỗi thường gặp của nó thay vì chỉ nhớ mỗi tên gọi.
- **`Null handling:`** — Xử lý giá trị null (Null handling) là một nhóm các quy tắc liên quan trong Comparable và Comparator, tập hợp nhiều chi tiết liên quan lại với nhau.
- **`nullsFirst`** — nullsFirst là một khái niệm cụ thể trong Comparable và Comparator; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và các lỗi thường gặp của nó thay vì chỉ nhớ mỗi tên gọi.
- **`nullsLast`** — nullsLast là một khái niệm cụ thể trong Comparable và Comparator; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và các lỗi thường gặp của nó thay vì chỉ nhớ mỗi tên gọi.

## Ghi Chú Chi Tiết (Detailed Notes)

### Thứ tự đảo ngược (Reverse order)

Thứ tự đảo ngược cho phép sắp xếp các phần tử theo trình tự ngược lại với trình tự thông thường của chúng.

#### Giải Thích Chi Tiết (Enriched Explanation)
Có hai cách chính để đảo ngược thứ tự sắp xếp trong Java 8+:
1. `Comparator.reverseOrder()`: Một phương thức tiện ích tĩnh (static utility method) trả về một bộ so sánh áp đặt thứ tự ngược lại với thứ tự tự nhiên (natural ordering) trên các đối tượng `Comparable`.
2. `comparator.reversed()`: Một phương thức mặc định (default method) trên một thể hiện `Comparator` hiện có, trả về một bộ so sánh mới áp đặt thứ tự đảo ngược của bộ so sánh ban đầu.

#### Ví Dụ Mã Nguồn (Code Example)
```java
List<String> list = new ArrayList<>(List.of("A", "C", "B"));
// Reversing natural ordering
list.sort(Comparator.reverseOrder()); // [C, B, A]

// Reversing custom ordering (by length)
Comparator<String> lenComp = Comparator.comparingInt(String::length);
list.sort(lenComp.reversed());
```

#### Điểm Cần Lưu Ý & Các Trường Hợp Lỗi (Gotchas & Failure Modes)
- **Bẫy đảo ngược chuỗi (Chaining Reversal Trap)**: Một lỗi cực kỳ phổ biến là đảo ngược một bộ so sánh chuỗi (chained comparator) không đúng cách.
  ```java
  // Trapped: This reverses the ENTIRE chain (both department and salary)
  Comparator<Employee> comp = Comparator.comparing(Employee::getDepartment)
                                        .thenComparingDouble(Employee::getSalary)
                                        .reversed();
  ```
  Nếu bạn chỉ muốn sắp xếp lương theo thứ tự giảm dần trong khi phòng ban vẫn giữ nguyên thứ tự tăng dần, hãy áp dụng phép đảo ngược cụ thể cho bộ so sánh lương:
  ```java
  // Corrected: Only salary is reversed
  Comparator<Employee> comp = Comparator.comparing(Employee::getDepartment)
                                        .thenComparing(Comparator.comparingDouble(Employee::getSalary).reversed());
  ```

### Xử lý giá trị null (Null handling)

Xử lý giá trị null đóng vai trò rất quan trọng vì các phép toán so sánh mặc định sẽ ném ra ngoại lệ `NullPointerException` khi gặp các giá trị `null`.

#### Giải Thích Chi Tiết (Enriched Explanation)
Theo mặc định, việc so sánh các giá trị `null` bằng cách sử dụng `compareTo` hoặc các bộ so sánh tiêu chuẩn sẽ ném ra `NullPointerException`. Java 8 đã giới thiệu các trình trang trí tiện ích (utility decorator) `Comparator.nullsFirst` và `Comparator.nullsLast` để giúp mọi bộ so sánh trở nên an toàn với giá trị null (null-safe) bằng cách chỉ định xem các giá trị null nên được xếp vào đầu hay cuối tập hợp.

#### Ví Dụ Mã Nguồn (Code Example)
```java
List<String> list = Arrays.asList("Apple", null, "Banana");
// Without null handling, this throws NPE:
// list.sort(Comparator.naturalOrder()); 

// Safe null handling:
list.sort(Comparator.nullsFirst(Comparator.naturalOrder())); // [null, Apple, Banana]
```

#### Điểm Cần Lưu Ý & Các Trường Hợp Lỗi (Gotchas & Failure Modes)
- **Bộ so sánh hạ nguồn Null (Null Downstream Comparator)**: Nếu bạn truyền `null` vào `nullsFirst` hoặc `nullsLast` (ví dụ: `Comparator.nullsFirst(null)`), tất cả các giá trị không null sẽ được coi là bằng nhau, dẫn đến việc thứ tự tương đối của chúng được giữ nguyên (hoặc hoàn toàn không được sắp xếp), trong khi các giá trị null bị đẩy về biên tập hợp.

### nullsFirst

`nullsFirst` là một tiện ích để sắp xếp các giá trị null trước các giá trị không null.

#### Giải Thích Chi Tiết (Enriched Explanation)
`Comparator.nullsFirst(Comparator<? super T> downstream)` trả về một bộ so sánh thân thiện với null (null-friendly) coi các giá trị `null` nhỏ hơn các giá trị không null. Nếu cả hai phần tử được so sánh đều khác null, nó sẽ ủy quyền cho bộ so sánh hạ nguồn (downstream) được cung cấp.

#### Ví Dụ Mã Nguồn (Code Example)
```java
List<Integer> list = Arrays.asList(5, null, 2, null, 8);
list.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
System.out.println(list); // [null, null, 2, 5, 8]
```

#### Điểm Cần Lưu Ý & Các Trường Hợp Lỗi (Gotchas & Failure Modes)
- **Lỗi NPE khi trích xuất không được trang trí (NPE on Un-Decorated Extraction)**: Nếu bạn sử dụng `Comparator.comparing(Employee::getName)` và thuộc tính tên bị `null`, việc đặt `nullsFirst` bọc ngoài bộ so sánh sẽ KHÔNG ngăn được lỗi NPE nếu bản thân bộ trích xuất khóa (key extractor) trả về giá trị `null`. Bạn phải đảm bảo phép so sánh khóa được an toàn với giá trị null:
  ```java
  // Safe way to handle null names:
  Comparator<Employee> comp = Comparator.comparing(
      Employee::getName, 
      Comparator.nullsFirst(Comparator.naturalOrder())
  );
  ```

### nullsLast

`nullsLast` là một tiện ích để sắp xếp các giá trị null sau các giá trị không null.

#### Giải Thích Chi Tiết (Enriched Explanation)
`Comparator.nullsLast(Comparator<? super T> downstream)` trả về một bộ so sánh thân thiện với null coi các giá trị `null` lớn hơn các giá trị không null. Nếu cả hai phần tử đều khác null, nó sẽ ủy quyền cho bộ so sánh hạ nguồn.

#### Ví Dụ Mã Nguồn (Code Example)
```java
List<Integer> list = Arrays.asList(5, null, 2, null, 8);
list.sort(Comparator.nullsLast(Comparator.naturalOrder()));
System.out.println(list); // [2, 5, 8, null, null]
```

#### Điểm Cần Lưu Ý & Các Trường Hợp Lỗi (Gotchas & Failure Modes)
- **Đóng hộp giữa lớp bọc và kiểu nguyên thủy (Wrapper vs Primitive Boxing)**: Nếu bộ so sánh hạ nguồn sử dụng các phép so sánh nguyên thủy (như `comparingInt`), việc sắp xếp một danh sách chứa các đối tượng bọc (wrapper object) bị `null` có thể gây ra lỗi `NullPointerException` trong quá trình tự động giải bọc (auto-unboxing) trước khi bộ so sánh có thể chạy. Hãy đảm bảo tập hợp của bạn chứa các đối tượng và sử dụng các bộ so sánh dựa trên đối tượng được bọc an toàn với giá trị null.

## Tại sao Java Sử dụng Dual-Pivot Quicksort cho Kiểu Nguyên Thủy nhưng dùng TimSort cho Đối Tượng (Why Java Uses Dual-Pivot Quicksort for Primitives but TimSort for Objects)

Java phân chia các thuật toán sắp xếp mảng dựa trên việc dữ liệu đầu vào chứa các giá trị nguyên thủy hay các tham chiếu đối tượng. Các kiểu dữ liệu nguyên thủy (primitive) là các kiểu giá trị thuần túy không có định danh (identity) riêng biệt, nghĩa là tính ổn định khi sắp xếp (sorting stability) — việc bảo toàn thứ tự đầu vào tương đối của các phần tử bằng nhau — là không cần thiết vì kiểu nguyên thủy `7` này hoàn toàn không thể phân biệt được với kiểu nguyên thủy `7` khác. Để tối ưu hóa hiệu năng, JDK sử dụng thuật toán **Dual-Pivot Quicksort** cho các mảng nguyên thủy vì nó có hiệu quả bộ nhớ đệm (cache-efficient) cao, chỉ yêu cầu không gian ngăn xếp phụ trợ (auxiliary stack space) $O(\log N)$ nhỏ và thực thi nhanh hơn trên bộ nhớ thô. Ngược lại, các đối tượng có định danh, tham chiếu và thuộc tính riêng biệt, nghĩa là tính ổn định khi sắp xếp là bắt buộc để đảm bảo rằng việc sắp xếp các phần tử theo tiêu chí phụ không làm xáo trộn thứ tự đã được thiết lập bởi lượt sắp xếp chính trước đó. Do đó, Java sử dụng thuật toán **TimSort** (sự kết hợp giữa sắp xếp trộn (merge sort) và sắp xếp chèn (insertion sort)) cho các mảng đối tượng, giúp đảm bảo hiệu năng trong trường hợp xấu nhất (worst-case performance) là $O(N \log N)$ ổn định và thích ứng hiệu quả với các đoạn đã sắp xếp trước (run), mặc dù nó đòi hỏi không gian lưu trữ phụ trợ $O(N)$ để quản lý các đoạn run này.

### Mô hình Tư duy: Sắp xếp ổn định (TimSort) so với Sắp xếp không ổn định (Quicksort) (Mental Model: Stable Sort (TimSort) vs Unstable Sort (Quicksort))
Giả sử chúng ta có một danh sách các lá bài và muốn sắp xếp chúng theo giá trị.
Đầu vào: `[5♣, 5♥]` trong đó `5♣` xuất hiện trước `5♥`.

```text
Stable Sort (TimSort):     [5♣, 5♥] (relative order of equal values is guaranteed to be preserved)
Unstable Sort (Quicksort): [5♥, 5♣] (equal values may have their relative order swapped)
```

**Tính ổn định** — Dual-Pivot Quicksort (Kiểu nguyên thủy) không ổn định, trong khi TimSort (Đối tượng) ổn định.

**Thời gian trong trường hợp xấu nhất** — Dual-Pivot Quicksort (Kiểu nguyên thủy) mất $O(N^2)$ (hiếm gặp) hoặc $O(N \log N)$, trong khi TimSort (Đối tượng) mất $O(N \log N)$.

**Thời gian trong trường hợp tốt nhất** — Dual-Pivot Quicksort (Kiểu nguyên thủy) mất $O(N)$ (nếu đã được sắp xếp hoặc đồng nhất), trong khi TimSort (Đối tượng) mất $O(N)$ (nếu các phần tử nằm trong các đoạn đã sắp xếp trước - run).

**Độ phức tạp không gian** — Dual-Pivot Quicksort (Kiểu nguyên thủy) cần không gian $O(\log N)$ (ngăn xếp đệ quy tại chỗ), trong khi TimSort (Đối tượng) cần không gian $O(N)$ (yêu cầu mảng tạm thời để lưu các đoạn run).

### Ví Dụ Mã Nguồn: Minh họa Tầm quan trọng của Sắp xếp ổn định cho Đối tượng (Code Example: Illustrating the Importance of Stable Sorting for Objects)
```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortingStabilityDemo {
    public static class LogEntry {
        final String severity;
        final int timestamp;

        public LogEntry(String severity, int timestamp) {
            this.severity = severity;
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return severity + "@" + timestamp;
        }
    }

    public static void main(String[] args) {
        List<LogEntry> logs = new ArrayList<>(List.of(
            new LogEntry("ERROR", 100),
            new LogEntry("INFO",  101),
            new LogEntry("ERROR", 102),
            new LogEntry("INFO",  103)
        ));

        // 1. Sort by timestamp (already in order)
        logs.sort(Comparator.comparingInt(l -> l.timestamp));

        // 2. Sort by severity. TimSort guarantees that for the same severity,
        // the original timestamp order is preserved.
        logs.sort(Comparator.comparing(l -> l.severity));
        System.out.println(logs);
        // Output: [ERROR@100, ERROR@102, INFO@101, INFO@103]
        // Note: ERROR@100 still precedes ERROR@102, and INFO@101 precedes INFO@103.
    }
}
```

### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)
Sắp xếp mảng kiểu nguyên thủy $\rightarrow$ Các phần tử riêng lẻ là các giá trị thuần túy không có định danh $\rightarrow$ Tính ổn định là không cần thiết $\rightarrow$ Sử dụng Dual-Pivot Quicksort để tối đa hóa tính cục bộ của bộ nhớ đệm CPU (CPU cache locality) và tránh cấp phát bộ nhớ heap phụ trợ.
Sắp xếp mảng đối tượng $\rightarrow$ Các phần tử riêng lẻ là các tham chiếu mà thứ tự tương đối phải được bảo toàn $\rightarrow$ Tính ổn định là bắt buộc để sắp xếp nhiều khóa (multi-key sorting) một cách chính xác $\rightarrow$ Sử dụng TimSort để đảm bảo sắp xếp ổn định với chi phí cấp phát thêm bộ nhớ theo dõi đoạn run.

## Các Sai lầm Thường gặp với Phép đảo ngược và Giá trị null (Common Mistakes with Reversal and Nulls)

1. **Đảo ngược các bộ so sánh nguyên thủy không đúng cách**: Việc đảo ngược một bộ so sánh nguyên thủy bằng cách sử dụng phép trừ lambda tự chế `(a, b) -> b - a` rất dễ gây ra lỗi tràn số (overflow bug) (ví dụ: `Integer.MIN_VALUE` so với `1`). Hãy luôn sử dụng `Comparator.reverseOrder()` hoặc `Comparator.comparingInt(...).reversed()`.
2. **Đảo ngược kép (Double Reversal)**: Việc sử dụng `comparator.reversed().reversed()` chỉ đơn giản là trả về thứ tự ban đầu nhưng lại làm tăng thêm chi phí thực thi do các lớp bọc.
3. **Lỗi NPE do tự động giải bọc ngầm định (Implicit unboxing NPE)**: Khi sắp xếp các lớp bọc bằng `nullsLast` hoặc `nullsFirst`, hãy đảm bảo bộ trích xuất trả về đối tượng lớp bọc (như `Integer`) chứ không phải kiểu nguyên thủy (`int`), nếu không JVM sẽ cố gắng tự động giải bọc giá trị `null` thành kiểu nguyên thủy trước khi truyền nó đi, dẫn đến lỗi `NullPointerException`.


## Câu hỏi Ôn tập Thường gặp (Common Review Prompts)

- Những khái niệm nào ở đây là các quy tắc thời gian biên dịch (compile-time rules)?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi thời gian chạy (runtime behavior)?
- Những khái niệm nào ở đây có khả năng là các bẫy phỏng vấn (interview traps)?

## Đường dẫn Tham khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Comparator.html (Đặc tả nullsFirst/nullsLast của Comparator)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Arrays.html#sort(int%5B%5D) (Đặc tả Dual-Pivot Quicksort)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Arrays.html#sort(java.lang.Object%5B%5D) (Đặc tả TimSort)