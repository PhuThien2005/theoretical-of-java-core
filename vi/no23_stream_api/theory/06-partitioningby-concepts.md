# API Luồng (Stream API) - Phần 6

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này đề cập đến một phần trọng tâm của API Luồng (Stream API). Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là từ vựng rời rạc.

## Nội Dung Tổng Quan (Outline Coverage)

- **`partitioningBy`** — Collector phân chia các phần tử của stream thành 2 nhóm (true/false) dựa trên Predicate.
- **`counting`** — Collector đếm số lượng phần tử trong stream và trả về kết quả kiểu Long.
- **`summarizingInt`** — Collector thu thập các thống kê tổng hợp (count, sum, min, average, max) cho các phần tử int.
- **`mapping`** — mapping: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`reducing`** — reducing: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

## Ghi Chú Chi Tiết (Detailed Notes)

### partitioningBy

**`partitioningBy`** — Collector phân chia các phần tử của stream thành 2 nhóm dựa trên Predicate.

Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được cho phép, và chế độ thất bại. Xem lại khái niệm này với một ví dụ nhỏ thay vì chỉ ghi nhớ máy móc mỗi nhãn tên.

#### Ví Dụ Mã Nguồn (Code Example)
```java
// Partition elements into true/false lists based on a predicate
Map<Boolean, List<String>> partitioned = Stream.of("a", "bb", "c", "ddd")
                                               .collect(Collectors.partitioningBy(s -> s.length() > 1));
// Result: {false=["a", "c"], true=["bb", "ddd"]}
```

Kiểm tra thực tế:

- Định nghĩa `partitioningBy` trong một câu.
- Nhận biết `partitioningBy` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế (limitation), hoặc sự đánh đổi (tradeoff) liên quan đến `partitioningBy`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy tự hỏi: `partitioningBy` thay đổi, cho phép, từ chối hay làm rõ điều gì?

### counting

**`counting`** — counting: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong lập trình Java.

Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được cho phép, và chế độ thất bại. Xem lại khái niệm này với một ví dụ nhỏ thay vì chỉ ghi nhớ máy móc mỗi nhãn tên.

#### Ví Dụ Mã Nguồn (Code Example)
```java
// Count elements downstream in a grouping or partitioning operation
Map<Boolean, Long> counts = Stream.of("a", "bb", "c", "ddd")
                                  .collect(Collectors.partitioningBy(
                                      s -> s.length() > 1, 
                                      Collectors.counting()
                                  ));
// Result: {false=2, true=2}
```

Kiểm tra thực tế:

- Định nghĩa `counting` trong một câu.
- Nhận biết `counting` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi liên quan đến `counting`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy tự hỏi: `counting` thay đổi, cho phép, từ chối hay làm rõ điều gì?

### summarizingInt

**`summarizingInt`** — summarizingInt: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong lập trình Java.

Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được cho phép, và chế độ thất bại. Xem lại khái niệm này với một ví dụ nhỏ thay vì chỉ ghi nhớ máy móc mỗi nhãn tên.

#### Ví Dụ Mã Nguồn (Code Example)
```java
// Gather statistics on int transformations (count, sum, min, average, max)
IntSummaryStatistics stats = Stream.of("a", "bb", "ccc")
                                   .collect(Collectors.summarizingInt(String::length));
System.out.println("Max: " + stats.getMax() + ", Average: " + stats.getAverage());
```

Kiểm tra thực tế:

- Định nghĩa `summarizingInt` trong một câu.
- Nhận biết `summarizingInt` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi liên quan đến `summarizingInt`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy tự hỏi: `summarizingInt` thay đổi, cho phép, từ chối hay làm rõ điều gì?

### mapping

Một cấu trúc Map lưu trữ các cặp khóa-giá trị và truy xuất giá trị bằng khóa.

Điều này rất quan trọng vì việc chọn sai cấu trúc dữ liệu (data structure) sẽ làm thay đổi tính chính xác (correctness), hiệu năng (performance), và hành vi xử lý trùng lặp (duplicate-handling behavior). Sự nhầm lẫn phổ biến là ghi nhớ tên lớp (class names) mà không hiểu rõ thứ tự tra cứu (lookup order), quy tắc so sánh bằng (equality rules), hoặc hành vi duyệt (iteration behavior).

#### Ví Dụ Mã Nguồn (Code Example)
```java
// Adapt a collector to accept elements of a different type
Map<Integer, Set<String>> map = Stream.of("apple", "banana", "apricot")
                                      .collect(Collectors.groupingBy(
                                          String::length,
                                          Collectors.mapping(s -> s.substring(0, 1), Collectors.toSet())
                                      ));
// Result: {5=["a"], 6=["b", "a"]}
```

Kiểm tra thực tế:

- Định nghĩa `mapping` trong một câu.
- Nhận biết `mapping` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi liên quan đến `mapping`.

Ví dụ nhỏ hoặc mô hình tư duy:

- `Map<String, Integer> scores = new HashMap<>();` ánh xạ các khóa sang các giá trị.

### reducing

**`reducing`** — reducing: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong lập trình Java.

Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được cho phép, và chế độ thất bại. Xem lại khái niệm này với một ví dụ nhỏ thay vì chỉ ghi nhớ máy móc mỗi nhãn tên.

#### Ví Dụ Mã Nguồn (Code Example)
```java
// Perform downstream reduction
Map<Integer, Optional<String>> maxByLength = Stream.of("a", "bb", "ccc", "d")
    .collect(Collectors.groupingBy(
        s -> s.length() % 2,
        Collectors.reducing((s1, s2) -> s1.length() >= s2.length() ? s1 : s2)
    ));
```

## Sai Lầm Thường Gặp (Common Mistakes)

### 1. Kỳ vọng các khóa của partitioningBy sẽ không tồn tại khi trống
Bản đồ được trả về bởi `partitioningBy` luôn chứa các mục nhập cho cả `true` và `false`, ngay cả khi không có phần tử đầu vào nào khớp với một (hoặc cả hai) phần phân hoạch (partition). Giá trị liên kết sẽ là một danh sách rỗng, chứ không phải null.
```java
Map<Boolean, List<String>> result = Stream.of("a", "b")
    .collect(Collectors.partitioningBy(s -> s.length() > 5));
System.out.println(result.get(true)); // Prints [] (empty list, not null or missing key)
```

Kiểm tra thực tế:

- Định nghĩa `reducing` trong một câu.
- Nhận biết `reducing` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi liên quan đến `reducing`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy tự hỏi: `reducing` thay đổi, cho phép, từ chối hay làm rõ điều gì?

## Câu Hỏi Ôn Tập Thường Gặp (Common Review Prompts)

- Những khái niệm nào ở đây là quy tắc thời gian biên dịch (compile-time rules)?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi thời gian chạy (runtime behavior)?
- Những khái niệm nào ở đây dễ là bẫy phỏng vấn (interview traps)?

## Tại sao groupingBy và partitioningBy Phục Vụ Các Mục Đích Khác Nhau (Why groupingBy and partitioningBy Serve Different Purposes)

Trong API Bộ gom tụ Java (Java Collectors API), `partitioningBy` và `groupingBy` phục vụ các chiến lược phân loại (classification strategies) riêng biệt, khác nhau về kiểu khóa (key types), tối ưu hóa (optimization) và cấu trúc (structure). Bộ gom tụ `partitioningBy` nhận vào một vị từ (Predicate) và chia luồng đầu vào thành chính xác hai danh mục, trả về một bản đồ với các khóa thuộc kiểu `Boolean` (cụ thể là `true` và `false`). Về mặt nội bộ, nó tận dụng một bộ gom tụ nhị phân (binary-only collector) chuyên dụng, hiệu năng cao để điền sẵn vào bản đồ cả hai khóa boolean được khởi tạo với các cấu trúc hạ nguồn (downstream structures) rỗng. Ngược lại, `groupingBy` là một bộ phân loại đa dụng (general-purpose classifier) nhận vào một `Function<T, K>`, ánh xạ các phần tử tới các khóa tùy ý thuộc kiểu `K`. Nó tạo các khóa một cách động và gom nhóm các mục vào một `HashMap` tiêu chuẩn (theo mặc định) hoặc một kiểu bản đồ được chỉ định, cho phép tạo ra nhiều ngăn chứa (buckets) tùy ý dựa trên đầu ra của bộ phân loại (classifier's output).

### Mô Hình Tư Duy (Mental Model)
```
partitioningBy(s -> s.length() > 3):
[ "cat", "elephant" ]
        |
        +-----> [ true  ] ---> [ "elephant" ]
        +-----> [ false ] ---> [ "cat" ] (Fixed to true & false keys only)

groupingBy(String::length):
[ "a", "bb", "c" ]
        |
        +-----> [ Key: 1 ] ---> [ "a", "c" ]
        +-----> [ Key: 2 ] ---> [ "bb" ] (Dynamic, arbitrary keys created)
```

### Ví Dụ Mã Nguồn (Code Example)
```java
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassificationDemo {
    public static void main(String[] args) {
        List<String> words = List.of("dog", "elephant", "cat");

        // partitioningBy: always exactly true and false keys
        Map<Boolean, List<String>> partition = words.stream()
            .collect(Collectors.partitioningBy(s -> s.length() > 3));

        // groupingBy: keys are dynamic and depend on classification function
        Map<Integer, List<String>> groups = words.stream()
            .collect(Collectors.groupingBy(String::length));

        System.out.println("Partition: " + partition);
        System.out.println("Groups: " + groups);
        // Console Output:
        // Partition: {false=[dog, cat], true=[elephant]}
        // Groups: {3=[dog, cat], 8=[elephant]}
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)
Cần phân loại các phần tử &rarr; Chọn `partitioningBy` cho việc kiểm tra luận lý đơn giản / Chọn `groupingBy` cho việc phân loại phức tạp &rarr; `partitioningBy` tự động điền sẵn các khóa `Boolean.TRUE` và `Boolean.FALSE` &rarr; `groupingBy` khởi tạo các khóa một cách động theo nhu cầu &rarr; `partitioningBy` trả về `Map&lt;Boolean, List&lt;T&gt;&gt;` / `groupingBy` trả về `Map&lt;K, List&lt;T&gt;&gt;`
