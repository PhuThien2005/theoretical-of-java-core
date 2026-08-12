# API Luồng (Stream API) - Phần 6

## Ghi Chú Chi Tiết (Detailed Notes)

### partitioningBy

`partitioningBy(Predicate)` chia stream thành đúng hai nhóm: phần tử thỏa điều kiện (key true) và không thỏa (key false). Trả về `Map<Boolean, List<T>>` với cả hai key luôn tồn tại, kể cả khi một nhóm rỗng.

#### Ví Dụ Mã Nguồn (Code Example)
```java
// Partition elements into true/false lists based on a predicate
Map<Boolean, List<String>> partitioned = Stream.of("a", "bb", "c", "ddd")
                                               .collect(Collectors.partitioningBy(s -> s.length() > 1));
// Result: {false=["a", "c"], true=["bb", "ddd"]}
```

### counting

`Collectors.counting()` đếm số phần tử và trả về `Long` — thường dùng làm downstream collector trong `groupingBy`/`partitioningBy`.

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

### summarizingInt

`Collectors.summarizingInt(mapper)` thu thập count, sum, min, average, max trong một lần duyệt duy nhất, trả về `IntSummaryStatistics`.

#### Ví Dụ Mã Nguồn (Code Example)
```java
// Gather statistics on int transformations (count, sum, min, average, max)
IntSummaryStatistics stats = Stream.of("a", "bb", "ccc")
                                   .collect(Collectors.summarizingInt(String::length));
System.out.println("Max: " + stats.getMax() + ", Average: " + stats.getAverage());
```

### mapping

`Collectors.mapping(mapper, downstream)` cho phép biến đổi phần tử trước khi đưa vào downstream collector — ví dụ chuyển Employee thành tên rồi joining.

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

### reducing

`Collectors.reducing()` thực hiện phép gộp (reduction) trên các phần tử — tương tự `Stream.reduce()` nhưng dùng được làm downstream collector.

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

## Tại sao groupingBy và partitioningBy Phục Vụ Các Mục Đích Khác Nhau (Why groupingBy and partitioningBy Serve Different Purposes)

Trong Java Collectors API, `partitioningBy` và `groupingBy` phục vụ các chiến lược phân loại riêng biệt, khác nhau về kiểu khóa, tối ưu hóa và cấu trúc. Bộ gom tụ `partitioningBy` nhận vào một Predicate và chia luồng đầu vào thành chính xác hai danh mục với các khóa kiểu Boolean (`true` và `false`). Về mặt nội bộ, nó tận dụng một collector chuyên dụng để luôn điền sẵn cả hai khóa boolean được khởi tạo với cấu trúc hạ nguồn rỗng.

Ngược lại, `groupingBy` là một bộ phân loại đa dụng nhận vào một `Function<T, K>`, ánh xạ các phần tử tới các khóa tùy ý thuộc kiểu `K`. Nó tạo các khóa một cách động và gom nhóm các mục vào một `HashMap` tiêu chuẩn, cho phép tạo ra số lượng nhóm tùy ý dựa trên kết quả của hàm phân loại.

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
