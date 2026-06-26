# Comparable và Comparator - Phần 2 (Comparable and Comparator - Part 2)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này đề cập đến một phần trọng tâm của **Comparable và Comparator**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là những thuật ngữ riêng lẻ.

## Phạm Vi Outline (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `Reverse order` | Đảo ngược thứ tự (Reverse order) là một khái niệm cụ thể trong `Comparable` và `Comparator`; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `Null handling:` | Xử lý giá trị null (Null handling) là một nhóm các quy tắc liên quan trong `Comparable` và `Comparator` nhóm lại một số chi tiết liên quan. |
| `nullsFirst` | `nullsFirst` là một khái niệm cụ thể trong `Comparable` và `Comparator`; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `nullsLast` | `nullsLast` là một khái niệm cụ thể trong `Comparable` và `Comparator`; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |

## Ghi Chú Chi Tiết (Detailed Notes)

### Đảo ngược thứ tự (Reverse order)

Đảo ngược thứ tự cho phép sắp xếp các phần tử theo trình tự ngược lại với trình tự thông thường của chúng.

#### Giải thích chi tiết (Enriched Explanation)
Có hai cách chính để đảo ngược thứ tự sắp xếp trong Java 8+:
1. `Comparator.reverseOrder()`: Một phương thức tiện ích tĩnh trả về một bộ so sánh áp dụng thứ tự đảo ngược của **thứ tự tự nhiên (natural ordering)** trên các đối tượng `Comparable`.
2. `comparator.reversed()`: Một phương thức mặc định trên một thực thể `Comparator` hiện có để trả về một bộ so sánh mới áp dụng thứ tự đảo ngược của bộ so sánh ban đầu.

#### Ví dụ mã nguồn (Code Example)
```java
List<String> list = new ArrayList<>(List.of("A", "C", "B"));
// Đảo ngược thứ tự tự nhiên
list.sort(Comparator.reverseOrder()); // [C, B, A]Custom

// Đảo ngược thứ tự tùy biến (theo độ dài)
Comparator<String> lenComp = Comparator.comparingInt(String::length);
list.sort(lenComp.reversed());
```

#### Lỗi thường gặp & Chế độ thất bại (Gotchas & Failure Modes)
- **Bẫy đảo ngược chuỗi so sánh (Chaining Reversal Trap)**: Một lỗi rất phổ biến là đảo ngược một bộ so sánh chuỗi (chained comparator) không chính xác.
  ```java
  // BỊ LỖI: Lệnh này đảo ngược TOÀN BỘ chuỗi (cả phòng ban và lương)
  Comparator<Employee> comp = Comparator.comparing(Employee::getDepartment)
                                        .thenComparingDouble(Employee::getSalary)
                                        .reversed();
  ```
  Nếu bạn chỉ muốn lương được sắp xếp theo thứ tự giảm dần trong khi phòng ban vẫn giữ thứ tự tăng dần, hãy áp dụng phép đảo ngược cụ thể cho bộ so sánh lương:
  ```java
  // ĐÃ SỬA: Chỉ có lương bị đảo ngược
  Comparator<Employee> comp = Comparator.comparing(Employee::getDepartment)
                                        .thenComparing(Comparator.comparingDouble(Employee::getSalary).reversed());
  ```

### Xử lý giá trị null (Null handling)

Xử lý giá trị null là cực kỳ quan trọng vì các hoạt động so sánh mặc định sẽ ném ra `NullPointerException` khi gặp các giá trị null.

#### Giải thích chi tiết (Enriched Explanation)
Theo mặc định, việc so sánh các giá trị `null` bằng cách sử dụng `compareTo` hoặc các bộ so sánh tiêu chuẩn sẽ ném ra `NullPointerException`. Java 8 giới thiệu các bộ trang trí tiện ích (utility decorators) `Comparator.nullsFirst` và `Comparator.nullsLast` để giúp bất kỳ bộ so sánh nào trở nên an toàn với null (null-safe) bằng cách chỉ định các giá trị null sẽ được sắp xếp lên đầu hoặc xuống cuối collection.

#### Ví dụ mã nguồn (Code Example)
```java
List<String> list = Arrays.asList("Apple", null, "Banana");
// Nếu không xử lý null, lệnh này sẽ ném ra NPE:
// list.sort(Comparator.naturalOrder()); 

// Xử lý null an toàn:
list.sort(Comparator.nullsFirst(Comparator.naturalOrder())); // [null, Apple, Banana]
```

#### Lỗi thường gặp & Chế độ thất bại (Gotchas & Failure Modes)
- **Bộ so sánh hạ nguồn bị Null (Null Downstream Comparator)**: Nếu bạn truyền `null` vào `nullsFirst` hoặc `nullsLast` (ví dụ: `Comparator.nullsFirst(null)`), tất cả các giá trị phi null sẽ được coi là bằng nhau, dẫn đến thứ tự tương đối của chúng được giữ nguyên (hoặc hoàn toàn không được sắp xếp), trong khi các giá trị null bị đẩy về biên.

### nullsFirst

`nullsFirst` là một công cụ tiện ích để sắp xếp các giá trị null đứng trước các giá trị phi null.

#### Giải thích chi tiết (Enriched Explanation)
`Comparator.nullsFirst(Comparator<? super T> downstream)` trả về một bộ so sánh thân thiện với null (null-friendly) coi các giá trị `null` nhỏ hơn các giá trị phi null. Nếu cả hai phần tử được so sánh đều khác null, nó sẽ ủy quyền (delegate) cho bộ so sánh `downstream` được cung cấp.

#### Ví dụ mã nguồn (Code Example)
```java
List<Integer> list = Arrays.asList(5, null, 2, null, 8);
list.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
System.out.println(list); // [null, null, 2, 5, 8]
```

#### Lỗi thường gặp & Chế độ thất bại (Gotchas & Failure Modes)
- **Lỗi NPE khi trích xuất không được trang trí (NPE on Un-Decorated Extraction)**: Nếu bạn sử dụng `Comparator.comparing(Employee::getName)` và thuộc tính name là `null`, `nullsFirst` được đặt bên ngoài bộ so sánh ngoài cùng sẽ KHÔNG ngăn chặn được NPE nếu bản thân hàm trích xuất khóa trả về `null`. Bạn phải làm cho việc so sánh khóa trở nên an toàn với null:
  ```java
  // Cách an toàn để xử lý tên bị null:
  Comparator<Employee> comp = Comparator.comparing(
      Employee::getName, 
      Comparator.nullsFirst(Comparator.naturalOrder())
  );
  ```

### nullsLast

`nullsLast` là một công cụ tiện ích để sắp xếp các giá trị null đứng sau các giá trị phi null.

#### Giải thích chi tiết (Enriched Explanation)
`Comparator.nullsLast(Comparator<? super T> downstream)` trả về một bộ so sánh thân thiện với null coi các giá trị `null` lớn hơn các giá trị phi null. Nếu cả hai phần tử đều khác null, nó sẽ ủy quyền cho bộ so sánh `downstream`.

#### Ví dụ mã nguồn (Code Example)
```java
List<Integer> list = Arrays.asList(5, null, 2, null, 8);
list.sort(Comparator.nullsLast(Comparator.naturalOrder()));
System.out.println(list); // [2, 5, 8, null, null]
```

#### Lỗi thường gặp & Chế độ thất bại (Gotchas & Failure Modes)
- **Đóng hộp kiểu nguyên thủy so với Kiểu bao bọc (Wrapper vs Primitive Boxing)**: Nếu bộ so sánh hạ nguồn sử dụng so sánh kiểu nguyên thủy (như `comparingInt`), việc sắp xếp một danh sách chứa các đối tượng bao bọc (wrapper object) bị `null` có thể gây ra `NullPointerException` trong quá trình tự động mở hộp (auto-unboxing) trước khi bộ so sánh có thể chạy. Hãy đảm bảo collection của bạn chứa các đối tượng và sử dụng các bộ so sánh dựa trên đối tượng với lớp bọc an toàn với null.

## Tại Sao Java Sử Dụng Dual-Pivot Quicksort Cho Kiểu Nguyên Thủy Nhưng Sử Dụng TimSort Cho Đối Tượng (Why Java Uses Dual-Pivot Quicksort for Primitives but TimSort for Objects)

Java phân tách các thuật toán sắp xếp mảng dựa trên việc đầu vào chứa các giá trị nguyên thủy (primitive) hay các tham chiếu đối tượng (object reference). Các kiểu nguyên thủy là các kiểu giá trị thuần túy không có định danh riêng biệt, nghĩa là tính ổn định của sắp xếp (sorting stability) — việc bảo toàn thứ tự đầu vào tương đối của các phần tử bằng nhau — là không phù hợp vì một giá trị nguyên thủy `7` hoàn toàn không thể phân biệt được với một giá trị `7` khác. Để tối ưu hóa hiệu suất, JDK sử dụng **Dual-Pivot Quicksort** cho các mảng nguyên thủy vì nó hiệu quả cao về bộ nhớ đệm (cache-efficient), yêu cầu không gian ngăn xếp phụ trợ nhỏ $O(\log N)$ và thực thi nhanh hơn trên bộ nhớ thô. Ngược lại, các đối tượng có định danh, tham chiếu và thuộc tính riêng biệt, nghĩa là tính ổn định của sắp xếp là bắt buộc để đảm bảo rằng việc sắp xếp các phần tử theo tiêu chí phụ không làm xáo trộn thứ tự đã được thiết lập bởi lượt sắp xếp chính trước đó. Do đó, Java sử dụng **TimSort** (sự kết hợp giữa sắp xếp trộn - merge sort và sắp xếp chèn - insertion sort) cho các mảng đối tượng, giúp đảm bảo hiệu suất trường hợp xấu nhất ổn định là $O(N \log N)$ và thích ứng hiệu quả với các đoạn đã được sắp xếp trước, mặc dù nó yêu cầu bộ nhớ lưu trữ phụ trợ $O(N)$ để quản lý các đoạn chạy.

### Mô hình tư duy: Sắp xếp ổn định (TimSort) so với Sắp xếp không ổn định (Quicksort) (Mental Model: Stable Sort (TimSort) vs Unstable Sort (Quicksort))
Giả sử chúng ta có một danh sách các lá bài và muốn sắp xếp chúng theo giá trị.
Đầu vào: `[5♣, 5♥]` trong đó `5♣` xuất hiện trước `5♥`.

```text
Sắp xếp ổn định (TimSort):     [5♣, 5♥] (thứ tự tương đối của các giá trị bằng nhau được đảm bảo bảo toàn)
Sắp xếp không ổn định (Quicksort): [5♥, 5♣] (các giá trị bằng nhau có thể bị tráo đổi thứ tự tương đối)
```

| Tiêu chí | Dual-Pivot Quicksort (Kiểu Nguyên Thủy) | TimSort (Đối Tượng) |
|---|---|---|
| **Tính ổn định (Stability)** | Không ổn định | Ổn định |
| **Thời gian trường hợp xấu nhất** | $O(N^2)$ (hiếm gặp) / $O(N \log N)$ | $O(N \log N)$ |
| **Thời gian trường hợp tốt nhất** | $O(N)$ (nếu đã sắp xếp hoặc đồng nhất) | $O(N)$ (nếu phần tử nằm trong các đoạn đã sắp xếp trước) |
| **Độ phức tạp không gian** | $O(\log N)$ (ngăn xếp đệ quy tại chỗ) | $O(N)$ (yêu cầu mảng tạm để theo dõi các đoạn) |

### Ví dụ mã nguồn: Minh họa tầm quan trọng của Sắp xếp ổn định cho đối tượng (Code Example: Illustrating the Importance of Stable Sorting for Objects)
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

        // 1. Sắp xếp theo dấu thời gian (đã theo thứ tự)
        logs.sort(Comparator.comparingInt(l -> l.timestamp));

        // 2. Sắp xếp theo mức độ nghiêm trọng (severity). TimSort đảm bảo rằng đối với cùng mức độ nghiêm trọng,
        // thứ tự dấu thời gian ban đầu vẫn được bảo toàn.
        logs.sort(Comparator.comparing(l -> l.severity));
        System.out.println(logs);
        // Kết quả: [ERROR@100, ERROR@102, INFO@101, INFO@103]
        // Lưu ý: ERROR@100 vẫn đứng trước ERROR@102, và INFO@101 đứng trước INFO@103.
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)
Sắp xếp mảng kiểu nguyên thủy $\rightarrow$ Các phần tử riêng lẻ là các giá trị thuần túy không có định danh $\rightarrow$ Tính ổn định là không cần thiết $\rightarrow$ Sử dụng Dual-Pivot Quicksort để tối đa hóa tính cục bộ của bộ nhớ đệm CPU và tránh cấp phát bộ nhớ heap phụ trợ.

Sắp xếp mảng đối tượng $\rightarrow$ Các phần tử riêng lẻ là các tham chiếu nơi thứ tự tương đối phải được bảo toàn $\rightarrow$ Tính ổn định là bắt buộc để sắp xếp nhiều khóa chính xác $\rightarrow$ Sử dụng TimSort để đảm bảo sắp xếp ổn định với chi phí cấp phát thêm bộ nhớ theo dõi đoạn chạy.

## Các Lỗi Thường Gặp với Phép Đảo Ngược và Giá Trị Null (Common Mistakes with Reversal and Nulls)

1. **Đảo ngược các bộ so sánh kiểu nguyên thủy không chính xác**: Đảo ngược bộ so sánh kiểu nguyên thủy bằng cách sử dụng phép trừ lambda tùy biến `(a, b) -> b - a` rất dễ xảy ra lỗi tràn số (ví dụ: `Integer.MIN_VALUE` so với `1`). Luôn sử dụng `Comparator.reverseOrder()` hoặc `Comparator.comparingInt(...).reversed()`.
2. **Đảo ngược hai lần (Double Reversal)**: Sử dụng `comparator.reversed().reversed()` chỉ đơn giản là trả về thứ tự ban đầu nhưng thêm chi phí thực thi do các lớp bọc trung gian.
3. **Lỗi NPE mở hộp ngầm định (Implicit unboxing NPE)**: Khi sắp xếp các lớp bao bọc (wrapper class) với `nullsLast` hoặc `nullsFirst`, hãy đảm bảo hàm trích xuất trả về đối tượng bao bọc (như `Integer`) chứ không phải kiểu nguyên thủy (`int`), nếu không JVM sẽ cố gắng tự động mở hộp giá trị `null` thành kiểu nguyên thủy trước khi truyền đi, gây ra `NullPointerException`.

## Các Câu Hỏi Ôn Tập Thường Gặp (Common Review Prompts)

- Những khái niệm nào ở đây là quy tắc tại thời điểm biên dịch (compile-time)?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy (runtime)?
- Những khái niệm nào ở đây có khả năng là bẫy khi phỏng vấn?

## Liên Kết Tham Khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Comparator.html (Comparator nullsFirst/nullsLast specification)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Arrays.html#sort(int%5B%5D) (Dual-Pivot Quicksort specification)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Arrays.html#sort(java.lang.Object%5B%5D) (TimSort specification)
