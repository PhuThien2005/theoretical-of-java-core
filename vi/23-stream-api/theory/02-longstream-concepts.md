# Stream API - Phần 2 (Stream API - Part 2)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này đề cập đến một phần trọng tâm của **Stream API**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là những thuật ngữ riêng lẻ.

## Phạm Vi Outline (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `LongStream` | Một Stream là một đường ống để xử lý các phần tử thông qua các thao tác lười biếng. |
| `DoubleStream` | Một Stream là một đường ống để xử lý các phần tử thông qua các thao tác lười biếng. |
| `Intermediate operations:` | Các thao tác trung gian (Intermediate operations) là một nhóm các quy tắc liên quan trong Stream API nhóm lại một số chi tiết liên quan. |
| `filter` | `filter` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `map` | Một `Map` lưu trữ các cặp key-value và truy xuất các giá trị bằng key. |
| `flatMap` | Một `Map` lưu trữ các cặp key-value và truy xuất các giá trị bằng key. |
| `distinct` | `distinct` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `sorted` | `sorted` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |

## Ghi Chú Chi Tiết (Detailed Notes)

### LongStream

Một Stream là một đường ống để xử lý các phần tử thông qua các thao tác lười biếng.

Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều các đường ống (pipeline) kiểu hàm. Một sự nhầm lẫn phổ biến là quên mất thao tác nào là lười biếng (lazy evaluation) và thao tác nào thực sự kích hoạt việc thực thi.

#### Ví dụ mã nguồn
```java
// LongStream nguyên thủy để tránh chi phí đóng hộp
LongStream longStream = LongStream.of(100L, 200L, 300L);
LongStream range = LongStream.rangeClosed(1, 100); // từ 1 đến 100 bao gồm cả hai đầu
```

Kiểm tra thực tế:
- Định nghĩa `LongStream` trong một câu.
- Nhận biết `LongStream` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `LongStream`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `LongStream` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### DoubleStream

Một Stream là một đường ống để xử lý các phần tử thông qua các thao tác lười biếng.

Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều các đường ống (pipeline) kiểu hàm. Một sự nhầm lẫn phổ biến là quên mất thao tác nào là lười biếng (lazy evaluation) và thao tác nào thực sự kích hoạt việc thực thi.

#### Ví dụ mã nguồn
```java
// DoubleStream nguyên thủy để tránh chi phí đóng hộp
DoubleStream doubleStream = DoubleStream.of(1.5, 2.5, 3.5);
DoubleSummaryStatistics stats = doubleStream.summaryStatistics();
System.out.println("Trung bình: " + stats.getAverage());
```

Kiểm tra thực tế:
- Định nghĩa `DoubleStream` trong một câu.
- Nhận biết `DoubleStream` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `DoubleStream`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `DoubleStream` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Các thao tác trung gian (Intermediate operations)

Các thao tác trung gian (Intermediate operations) là một nhóm các quy tắc liên quan trong Stream API nhóm lại một số chi tiết liên quan.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Các thao tác trung gian được liên kết chuỗi và thực thi lười biếng
Stream.of("a", "b", "c")
      .filter(s -> !s.isEmpty())
      .map(String::toUpperCase); // Trả về một Stream mới (chưa thực thi)
```

Kiểm tra thực tế:
- Định nghĩa `Intermediate operations:` trong một câu.
- Nhận biết `Intermediate operations:` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `Intermediate operations:`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `Intermediate operations:` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### filter

`filter` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Giữ lại các phần tử khớp với vị từ (predicate) được đưa ra
Stream.of("apple", "banana", "kiwi")
      .filter(s -> s.length() > 4)
      .forEach(System.out::println); // In ra: apple, banana
```

Kiểm tra thực tế:
- Định nghĩa `filter` trong một câu.
- Nhận biết `filter` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `filter`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `filter` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### map

Lớp ánh xạ (map) biến đổi từng phần tử 1-1.

Nó quan trọng vì việc chọn sai cấu trúc dữ liệu sẽ làm thay đổi tính chính xác, hiệu suất và hành vi xử lý trùng lặp. Một sự nhầm lẫn phổ biến là ghi nhớ tên lớp mà không biết thứ tự tìm kiếm, quy tắc so sánh bằng hay hành vi lặp.

#### Ví dụ mã nguồn
```java
// Biến đổi từng phần tử theo tỷ lệ 1-đến-1
Stream.of("apple", "banana")
      .map(String::toUpperCase)
      .forEach(System.out::println); // In ra: APPLE, BANANA
```

Kiểm tra thực tế:
- Định nghĩa `map` trong một câu.
- Nhận biết `map` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `map`.

Ví dụ nhỏ hoặc mô hình tư duy:
- `Map<String, Integer> scores = new HashMap<>();` ánh xạ các key tới value.

### flatMap

Ánh xạ làm phẳng (flatMap) làm phẳng các cấu trúc lồng nhau (ánh xạ 1-nhiều).

Nó quan trọng vì việc chọn sai cấu trúc dữ liệu sẽ làm thay đổi tính chính xác, hiệu suất và hành vi xử lý trùng lặp. Một sự nhầm lẫn phổ biến là ghi nhớ tên lớp mà không biết thứ tự tìm kiếm, quy tắc so sánh bằng hay hành vi lặp.

#### Ví dụ mã nguồn
```java
// Làm phẳng các cấu trúc lồng nhau (ánh xạ 1-nhiều)
List<List<String>> nestedList = List.of(
    List.of("a", "b"),
    List.of("c", "d")
);
nestedList.stream()
          .flatMap(List::stream)
          .forEach(System.out::print); // In ra: abcd
```

### Case Study: Chi tiết về flatMap so với map (Case Study: FlatMap vs Map in detail)

#### Sự khác biệt trong chữ ký và kiểu trả về (The difference in signatures and return types)
- **`map`**: Nhận một `Function<T, R>` ánh xạ một phần tử kiểu `T` thành một phần tử kiểu `R`. Trả về `Stream<R>`.
- **`flatMap`**: Nhận một `Function<T, Stream<R>>` ánh xạ một phần tử kiểu `T` thành một `Stream<R>`. Sau đó nó "làm phẳng" các stream riêng lẻ này thành một `Stream<R>` thống nhất duy nhất.

#### Khi nào sử dụng cái nào? (When to use which?)
- Sử dụng **`map`** cho các phép biến đổi một-đến-một đơn giản (ví dụ: biến đổi một chuỗi thành độ dài của nó, chuyển đổi một đối tượng thành ID của nó).
- Sử dụng **`flatMap`** khi mỗi phần tử ánh xạ tới một collection/mảng/stream, hoặc khi làm việc với các cấu trúc lồng nhau (ví dụ: lấy ra một danh sách đơn hàng từ một danh sách khách hàng).

#### Trực quan hóa việc làm phẳng (Visualizing the flattening)
Nếu chúng ta có một stream chứa các stream:
`Stream.of( Stream.of(1, 2), Stream.of(3, 4) )`
- Áp dụng `map(s -> s)` giữ nguyên cấu trúc dưới dạng `Stream<Stream<Integer>>` (lồng nhau).
- Áp dụng `flatMap(s -> s)` gộp chúng thành một `Stream<Integer>` duy nhất chứa `[1, 2, 3, 4]`.

Kiểm tra thực tế:
- Định nghĩa `flatMap` trong một câu.
- Nhận biết `flatMap` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `flatMap`.

Ví dụ nhỏ hoặc mô hình tư duy:
- `Map<String, Integer> scores = new HashMap<>();` ánh xạ các key tới value.

### distinct

`distinct` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Loại bỏ các phần tử trùng lặp dựa trên Object.equals()
Stream.of(1, 2, 2, 3, 1)
      .distinct()
      .forEach(System.out::print); // In ra: 123
```

Kiểm tra thực tế:
- Định nghĩa `distinct` trong một câu.
- Nhận biết `distinct` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `distinct`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `distinct` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### sorted

`sorted` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Sắp xếp các phần tử theo thứ tự tự nhiên
Stream.of("banana", "apple", "cherry")
      .sorted()
      .forEach(System.out::println); // In ra: apple, banana, cherry
```

## Các Lỗi Thường Gặp (Common Mistakes)

### 1. Lỗi chặn (blocking) của phương thức sorted() và tính có trạng thái
Việc gọi `.sorted()` yêu cầu tất cả các phần tử của stream phải được lưu trữ trong bộ nhớ trước khi quá trình sắp xếp có thể bắt đầu. Thực hiện điều này trên một stream vô hạn (ví dụ: `Stream.generate(...)` hoặc `Stream.iterate(...)`) sẽ gây ra tình trạng treo chương trình hoặc lỗi bộ nhớ `OutOfMemoryError`.
```java
// NGUY HIỂM: Sẽ treo vô thời hạn
Stream.iterate(0, i -> i + 1)
      .sorted()
      .limit(5)
      .forEach(System.out::println);
```

### 2. Sửa đổi các phần tử bên trong map() hoặc filter()
Các thao tác trung gian không được gây ra tác dụng phụ (side-effect-free). Sửa đổi các biến bên ngoài hoặc thay đổi trực tiếp các phần tử khả biến trong map/filter dẫn đến các tình trạng tranh chấp (race condition) và lỗi logic, đặc biệt là trong các stream song song.
```java
List<Integer> target = new ArrayList<>();
Stream.of(1, 2, 3)
      .map(x -> {
          target.add(x); // TỆ: Gây ra tác dụng phụ!
          return x * 2;
      })
      .count();
```

Kiểm tra thực tế:
- Định nghĩa `sorted` trong một câu.
- Nhận biết `sorted` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `sorted`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `sorted` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Các Câu Hỏi Ôn Tập Thường Gặp (Common Review Prompts)

- Những khái niệm nào ở đây là quy tắc tại thời điểm biên dịch (compile-time)?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy (runtime)?
- Những khái niệm nào ở đây có khả năng là bẫy khi phỏng vấn?

## Tại Sao flatMap() Khác Với map() (Why flatMap() Differs from map())

Trong Java Stream API, sự khác biệt cơ bản giữa `map()` và `flatMap()` nằm ở cấu trúc dữ liệu mà chúng tạo ra và cách chúng biến đổi các phần tử. Thao tác `map()` là một phép biến đổi một-đến-một, nhận một hàm có kiểu `T -> R` và trả về một `Stream<R>` trong đó mỗi phần tử đầu vào tương ứng với chính xác một phần tử đầu ra. Ngược lại, `flatMap()` là một phép biến đổi một-đến-nhiều, nhận một hàm ánh xạ có kiểu `T -> Stream<R>`. Thay vì tạo ra một cấu trúc stream lồng nhau như `Stream<Stream<R>>`, `flatMap()` kết hợp hoặc "làm phẳng" nội dung của từng stream tạm thời thành một `Stream<R>` liên tục duy nhất ở hạ nguồn. Khi các phần tử di chuyển, JVM thực thi hàm, tạo các đối tượng stream tạm thời, tiêu thụ các phần tử của chúng và đóng từng stream tạm thời một cách tuần tự.

### Mô hình tư duy (Mental Model)
```
map() [Một-đến-Một]:
Đầu vào:  [ "A" ] ---------> map(s -> s.toLowerCase()) ---------> Đầu ra: [ "a" ]

flatMap() [Một-đến-Nhiều & Làm Phẳng]:
Đầu vào:  [ [1, 2], [3, 4] ]
              │
              +--> flatMap(list -> list.stream())
                      │
                      ▼
          Stream[1, 2] và Stream[3, 4]  (Các Stream lồng nhau)
                      │
                      ▼ (Làm phẳng)
Đầu ra: [ 1, 2, 3, 4 ]                  (Stream duy nhất)
```

### Ví dụ mã nguồn
```java
import java.util.List;
import java.util.stream.Stream;

public class FlatMapDemo {
    public static void main(String[] args) {
        List<List<String>> nestedList = List.of(
            List.of("Java", "Python"),
            List.of("C++", "Go")
        );

        // flatMap làm phẳng Stream<List<String>> thành Stream<String>
        List<String> flattened = nestedList.stream()
            .flatMap(list -> list.stream())
            .map(String::toUpperCase)
            .toList();

        System.out.println("Đã làm phẳng: " + flattened);
        // Kết quả bảng điều khiển:
        // Đã làm phẳng: [JAVA, PYTHON, C++, GO]
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Đầu vào là collection lồng nhau
  → `map()` tạo ra Stream của các Stream (lồng nhau)
  → `flatMap()` nhận hàm ánh xạ trả về `Stream<R>`
  → `flatMap()` lấy ra và liên kết các phần tử của các stream trung gian
  → Các stream trung gian tự động được đóng lại
  → Một Stream duy nhất được tạo ra ở hạ nguồn.
```

