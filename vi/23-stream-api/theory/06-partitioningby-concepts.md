# Stream API - Phần 6 (Stream API - Part 6)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này đề cập đến một phần trọng tâm của **Stream API**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là những thuật ngữ riêng lẻ.

## Phạm Vi Outline (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `partitioningBy` | `partitioningBy` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `counting` | `counting` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `summarizingInt` | `summarizingInt` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `mapping` | Một `Map` lưu trữ các cặp key-value và truy xuất các giá trị bằng key. |
| `reducing` | `reducing` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |

## Ghi Chú Chi Tiết (Detailed Notes)

### partitioningBy

`partitioningBy` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Phân chia các phần tử thành hai danh sách true/false dựa trên vị từ (predicate)
Map<Boolean, List<String>> partitioned = Stream.of("a", "bb", "c", "ddd")
                                               .collect(Collectors.partitioningBy(s -> s.length() > 1));
// Kết quả: {false=["a", "c"], true=["bb", "ddd"]}
```

Kiểm tra thực tế:
- Định nghĩa `partitioningBy` trong một câu.
- Nhận biết `partitioningBy` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `partitioningBy`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `partitioningBy` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### counting

`counting` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Đếm số lượng các phần tử ở hạ nguồn trong một thao tác phân nhóm hoặc phân vùng
Map<Boolean, Long> counts = Stream.of("a", "bb", "c", "ddd")
                                  .collect(Collectors.partitioningBy(
                                      s -> s.length() > 1, 
                                      Collectors.counting()
                                  ));
// Kết quả: {false=2, true=2}
```

Kiểm tra thực tế:
- Định nghĩa `counting` trong một câu.
- Nhận biết `counting` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `counting`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `counting` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### summarizingInt

`summarizingInt` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Thu thập số liệu thống kê về các phép biến đổi int (đếm, tổng, nhỏ nhất, trung bình, lớn nhất)
IntSummaryStatistics stats = Stream.of("a", "bb", "ccc")
                                   .collect(Collectors.summarizingInt(String::length));
System.out.println("Lớn nhất: " + stats.getMax() + ", Trung bình: " + stats.getAverage());
```

Kiểm tra thực tế:
- Định nghĩa `summarizingInt` trong một câu.
- Nhận biết `summarizingInt` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `summarizingInt`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `summarizingInt` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### mapping

Ánh xạ (mapping) biến đổi một bộ thu gom để chấp nhận các phần tử có kiểu khác.

Nó quan trọng vì việc chọn sai cấu trúc dữ liệu sẽ làm thay đổi tính chính xác, hiệu suất và hành vi xử lý trùng lặp. Một sự nhầm lẫn phổ biến là ghi nhớ tên lớp mà không biết thứ tự tìm kiếm, quy tắc so sánh bằng hay hành vi lặp.

#### Ví dụ mã nguồn
```java
// Điều chỉnh một bộ thu gom để nhận các phần tử có kiểu khác
Map<Integer, Set<String>> map = Stream.of("apple", "banana", "apricot")
                                      .collect(Collectors.groupingBy(
                                          String::length,
                                          Collectors.mapping(s -> s.substring(0, 1), Collectors.toSet())
                                      ));
// Kết quả: {5=["a"], 6=["b", "a"]}
```

Kiểm tra thực tế:
- Định nghĩa `mapping` trong một câu.
- Nhận biết `mapping` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `mapping`.

Ví dụ nhỏ hoặc mô hình tư duy:
- `Map<String, Integer> scores = new HashMap<>();` ánh xạ các key tới value.

### reducing

`reducing` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Thực hiện phép rút gọn (reduction) hạ nguồn
Map<Integer, Optional<String>> maxByLength = Stream.of("a", "bb", "ccc", "d")
    .collect(Collectors.groupingBy(
        s -> s.length() % 2,
        Collectors.reducing((s1, s2) -> s1.length() >= s2.length() ? s1 : s2)
    ));
```

## Các Lỗi Thường Gặp (Common Mistakes)

### 1. Kỳ vọng các khóa của partitioningBy biến mất khi trống
Map được trả về bởi `partitioningBy` luôn luôn chứa các mục nhập cho cả hai khóa `true` và `false`, ngay cả khi không có phần tử đầu vào nào khớp với một (hoặc cả hai) phân vùng đó. Giá trị liên kết của khóa trống sẽ là một danh sách rỗng, chứ không phải null.
```java
Map<Boolean, List<String>> result = Stream.of("a", "b")
    .collect(Collectors.partitioningBy(s -> s.length() > 5));
System.out.println(result.get(true)); // In ra [] (danh sách rỗng, không phải null hay thiếu key)
```

Kiểm tra thực tế:
- Định nghĩa `reducing` trong một câu.
- Nhận biết `reducing` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `reducing`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `reducing` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Các Câu Hỏi Ôn Tập Thường Gặp (Common Review Prompts)

- Những khái niệm nào ở đây là quy tắc tại thời điểm biên dịch (compile-time)?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy (runtime)?
- Những khái niệm nào ở đây có khả năng là bẫy khi phỏng vấn?

## Tại Sao groupingBy Và partitioningBy Phục Vụ Các Mục Đích Khác Nhau (Why groupingBy and partitioningBy Serve Different Purposes)

Trong Collectors API của Java, `partitioningBy` và `groupingBy` phục vụ các chiến lược phân loại riêng biệt, khác nhau về kiểu khóa, sự tối ưu hóa và cấu trúc. Bộ thu gom `partitioningBy` nhận một `Predicate` và chia stream đầu vào thành chính xác hai danh mục, trả về một map với các khóa có kiểu `Boolean` (cụ thể là `true` và `false`). Ở bên dưới, nó tận dụng một bộ thu gom chuyên biệt, hiệu quả cao chỉ dành cho nhị phân, chuẩn bị sẵn map với cả hai khóa boolean được khởi tạo các cấu trúc hạ nguồn trống. Ngược lại, `groupingBy` là một bộ phân loại đa năng nhận một `Function<T, K>`, ánh xạ các phần tử tới các khóa tùy ý có kiểu `K`. Nó tự động xây dựng các khóa và nhóm các phần tử vào một `HashMap` tiêu chuẩn (theo mặc định) hoặc một kiểu map cụ thể được chỉ định, cho phép phân chia thành nhiều nhóm tùy ý dựa trên đầu ra của bộ phân loại.

### Mô hình tư duy (Mental Model)
```
partitioningBy(s -> s.length() > 3):
[ "cat", "elephant" ]
        │
        +-----> [ true  ] ---> [ "elephant" ]
        +-----> [ false ] ---> [ "cat" ] (Cố định với các khóa true & false duy nhất)

groupingBy(String::length):
[ "a", "bb", "c" ]
        │
        +-----> [ Khóa: 1 ] ---> [ "a", "c" ]
        +-----> [ Khóa: 2 ] ---> [ "bb" ] (Các khóa tùy ý được tạo động)
```

### Ví dụ mã nguồn
```java
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassificationDemo {
    public static void main(String[] args) {
        List<String> words = List.of("dog", "elephant", "cat");

        // partitioningBy: luôn luôn có chính xác các khóa true và false
        Map<Boolean, List<String>> partition = words.stream()
            .collect(Collectors.partitioningBy(s -> s.length() > 3));

        // groupingBy: các khóa là động và phụ thuộc vào hàm phân loại
        Map<Integer, List<String>> groups = words.stream()
            .collect(Collectors.groupingBy(String::length));

        System.out.println("Phân vùng (Partition): " + partition);
        System.out.println("Phân nhóm (Groups): " + groups);
        // Kết quả bảng điều khiển:
        // Phân vùng (Partition): {false=[dog, cat], true=[elephant]}
        // Phân nhóm (Groups): {3=[dog, cat], 8=[elephant]}
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Cần phân loại các phần tử
  → Chọn `partitioningBy` cho các kiểm tra boolean đơn giản / Chọn `groupingBy` cho việc phân loại phức tạp
  → `partitioningBy` tạo sẵn các khóa `Boolean.TRUE` và `Boolean.FALSE`
  → `groupingBy` khởi tạo động các khóa theo yêu cầu
  → `partitioningBy` trả về `Map<Boolean, List<T>>` / `groupingBy` trả về `Map<K, List<T>>`.
```

