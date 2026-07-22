# Stream API - Phần 2

## Mục Tiêu Học Tập

Tài liệu này trình bày một phần trọng tâm của **Stream API**. Hãy nghiên cứu từng khái niệm dưới dạng một quy tắc Java thực tế, thay vì chỉ học từ vựng riêng lẻ.

## Nội Dung Khái Quát

- **`LongStream`** — Luồng (Stream) là một đường ống (pipeline) để xử lý các phần tử thông qua các thao tác trì hoãn (lazy operations).
- **`DoubleStream`** — Luồng là một đường ống để xử lý các phần tử thông qua các thao tác trì hoãn.
- **`Intermediate operations:`** — Các thao tác trung gian (intermediate operations) là một nhóm các quy tắc liên quan trong Stream API nhằm gom nhóm một số chi tiết liên quan.
- **`filter`** — Bộ lọc (filter) là một khái niệm cụ thể trong Stream API; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và trạng thái lỗi (failure mode) của nó thay vì chỉ nhớ mỗi tên gọi.
- **`map`** — Bản đồ (Map) lưu trữ các cặp khóa - giá trị (key-value pairs) và truy xuất các giá trị theo khóa.
- **`flatMap`** — Bản đồ lưu trữ các cặp khóa - giá trị và truy xuất các giá trị theo khóa.
- **`distinct`** — Loại bỏ trùng lặp (distinct) là một khái niệm cụ thể trong Stream API; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và trạng thái lỗi của nó thay vì chỉ nhớ mỗi tên gọi.
- **`sorted`** — Sắp xếp (sorted) là một khái niệm cụ thể trong Stream API; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và trạng thái lỗi của nó thay vì chỉ nhớ mỗi tên gọi.

## Ghi Chú Chi Tiết

### LongStream

Luồng là một đường ống để xử lý các phần tử thông qua các thao tác trì hoãn.

Điều này rất quan trọng vì các API Java hiện đại sử dụng rất nhiều đường ống kiểu hàm (function-style pipelines). Một sự nhầm lẫn phổ biến là quên mất thao tác nào là trì hoãn và thao tác nào thực sự kích hoạt việc thực thi.

#### Ví Dụ Mã Nguồn
```java
// Primitive LongStream to avoid boxing overhead
LongStream longStream = LongStream.of(100L, 200L, 300L);
LongStream range = LongStream.rangeClosed(1, 100); // 1 to 100 inclusive
```

Kiểm tra thực tế:

- Định nghĩa `LongStream` trong một câu.
- Nhận biết `LongStream` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `LongStream`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `LongStream` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### DoubleStream

Luồng là một đường ống để xử lý các phần tử thông qua các thao tác trì hoãn.

Điều này rất quan trọng vì các API Java hiện đại sử dụng rất nhiều đường ống kiểu hàm. Một sự nhầm lẫn phổ biến là quên mất thao tác nào là trì hoãn và thao tác nào thực sự kích hoạt việc thực thi.

#### Ví Dụ Mã Nguồn
```java
// Primitive DoubleStream to avoid boxing overhead
DoubleStream doubleStream = DoubleStream.of(1.5, 2.5, 3.5);
DoubleSummaryStatistics stats = doubleStream.summaryStatistics();
System.out.println("Average: " + stats.getAverage());
```

Kiểm tra thực tế:

- Định nghĩa `DoubleStream` trong một câu.
- Nhận biết `DoubleStream` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `DoubleStream`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `DoubleStream` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Các thao tác trung gian (Intermediate operations)

Các thao tác trung gian là một nhóm các quy tắc liên quan trong Stream API nhằm gom nhóm một số chi tiết liên quan.

Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được phép và trạng thái lỗi. Hãy ôn tập nó bằng một ví dụ nhỏ thay vì chỉ ghi nhớ mỗi nhãn tên.

#### Ví Dụ Mã Nguồn
```java
// Intermediate operations are chained and executed lazily
Stream.of("a", "b", "c")
      .filter(s -> !s.isEmpty())
      .map(String::toUpperCase); // Returns a new Stream (not executed yet)
```

Kiểm tra thực tế:

- Định nghĩa `Intermediate operations:` trong một câu.
- Nhận biết `Intermediate operations:` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `Intermediate operations:`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `Intermediate operations:` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Bộ lọc (filter)

`filter` là một khái niệm cụ thể trong Stream API; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và trạng thái lỗi của nó thay vì chỉ nhớ mỗi tên gọi.

Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được phép và trạng thái lỗi. Hãy ôn tập nó bằng một ví dụ nhỏ thay vì chỉ ghi nhớ mỗi nhãn tên.

#### Ví Dụ Mã Nguồn
```java
// Retain elements that match the given predicate
Stream.of("apple", "banana", "kiwi")
      .filter(s -> s.length() > 4)
      .forEach(System.out::println); // Prints: apple, banana
```

Kiểm tra thực tế:

- Định nghĩa `filter` trong một câu.
- Nhận biết `filter` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `filter`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `filter` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Ánh xạ (map)

Bản đồ lưu trữ các cặp khóa - giá trị và truy xuất các giá trị theo khóa.

Điều này rất quan trọng vì việc chọn sai cấu trúc dữ liệu (data structure) sẽ làm thay đổi tính đúng đắn, hiệu năng và hành vi xử lý trùng lặp. Một sự nhầm lẫn phổ biến là ghi nhớ tên các lớp (class names) mà không biết thứ tự tra cứu (lookup order), quy tắc so sánh bằng (equality rules) hoặc hành vi duyệt (iteration behavior).

#### Ví Dụ Mã Nguồn
```java
// Transform each element 1-to-1
Stream.of("apple", "banana")
      .map(String::toUpperCase)
      .forEach(System.out::println); // Prints: APPLE, BANANA
```

Kiểm tra thực tế:

- Định nghĩa `map` trong một câu.
- Nhận biết `map` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `map`.

Ví dụ nhỏ hoặc mô hình tư duy:

- `Map<String, Integer> scores = new HashMap<>();` ánh xạ các khóa sang các giá trị.

### Ánh xạ phẳng (flatMap)

Bản đồ lưu trữ các cặp khóa - giá trị và truy xuất các giá trị theo khóa.

Điều này rất quan trọng vì việc chọn sai cấu trúc dữ liệu sẽ làm thay đổi tính đúng đắn, hiệu năng và hành vi xử lý trùng lặp. Một sự nhầm lẫn phổ biến là ghi nhớ tên các lớp mà không biết thứ tự tra cứu, quy tắc so sánh bằng hoặc hành vi duyệt.

#### Ví Dụ Mã Nguồn
```java
// Flatten nested structures (1-to-many mapping)
List<List<String>> nestedList = List.of(
    List.of("a", "b"),
    List.of("c", "d")
);
nestedList.stream()
          .flatMap(List::stream)
          .forEach(System.out::print); // Prints: abcd
```

### Ví Dụ Thực Tế: So sánh chi tiết flatMap và map

#### Sự khác biệt về chữ ký phương thức (signatures) và kiểu trả về (return types)
- **`map`**: Nhận một `Function<T, R>` ánh xạ một phần tử kiểu `T` thành một phần tử kiểu `R`. Trả về `Stream<R>`.
- **`flatMap`**: Nhận một `Function<T, Stream<R>>` ánh xạ một phần tử kiểu `T` thành một `Stream<R>`. Sau đó, nó "làm phẳng" (flatten) các luồng riêng lẻ này thành một `Stream<R>` hợp nhất duy nhất.

#### Khi nào nên sử dụng phương thức nào?
- Sử dụng **`map`** cho các phép biến đổi một-đối-một (one-to-one transformations) đơn giản (ví dụ: biến đổi một chuỗi thành độ dài của nó, chuyển đổi một đối tượng thành định danh của nó).
- Sử dụng **`flatMap`** khi mỗi phần tử ánh xạ tới một bộ sưu tập (collection)/mảng (array)/luồng, hoặc khi làm việc với các cấu trúc lồng nhau (nested structures) (ví dụ: trích xuất danh sách đơn hàng từ danh sách khách hàng).

#### Trực quan hóa việc làm phẳng
Nếu chúng ta có một luồng của các luồng:
Stream.of( Stream.of(1, 2), Stream.of(3, 4) )
- Áp dụng `map(s -> s)` sẽ giữ nguyên dưới dạng `Stream<Stream<Integer>>` (lồng nhau).
- Áp dụng `flatMap(s -> s)` sẽ hợp nhất chúng thành một `Stream<Integer>` duy nhất chứa `[1, 2, 3, 4]`.

Kiểm tra thực tế:

- Định nghĩa `flatMap` trong một câu.
- Nhận biết `flatMap` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `flatMap`.

Ví dụ nhỏ hoặc mô hình tư duy:

- `Map<String, Integer> scores = new HashMap<>();` ánh xạ các khóa sang các giá trị.

### Loại bỏ trùng lặp (distinct)

`distinct` là một khái niệm cụ thể trong Stream API; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và trạng thái lỗi của nó thay vì chỉ nhớ mỗi tên gọi.

Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được phép và trạng thái lỗi. Hãy ôn tập nó bằng một ví dụ nhỏ thay vì chỉ ghi nhớ mỗi nhãn tên.

#### Ví Dụ Mã Nguồn
```java
// Remove duplicates based on Object.equals()
Stream.of(1, 2, 2, 3, 1)
      .distinct()
      .forEach(System.out::print); // Prints: 123
```

Kiểm tra thực tế:

- Định nghĩa `distinct` trong một câu.
- Nhận biết `distinct` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `distinct`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `distinct` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Sắp xếp (sorted)

`sorted` là một khái niệm cụ thể trong Stream API; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và trạng thái lỗi của nó thay vì chỉ nhớ mỗi tên gọi.

Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được phép và trạng thái lỗi. Hãy ôn tập nó bằng một ví dụ nhỏ thay vì chỉ ghi nhớ mỗi nhãn tên.

#### Ví Dụ Mã Nguồn
```java
// Sort elements in natural order
Stream.of("banana", "apple", "cherry")
      .sorted()
      .forEach(System.out::println); // Prints: apple, banana, cherry
```

## Các Lỗi Thường Gặp

### 1. Tính có trạng thái (Statefulness) và việc sorted() gây chặn

Việc gọi `.sorted()` yêu cầu tất cả các phần tử của luồng phải được lưu trữ trong bộ nhớ trước khi quá trình sắp xếp có thể bắt đầu. Thực hiện điều này trên một luồng vô hạn (infinite stream) (ví dụ: `Stream.generate(...)` hoặc `Stream.iterate(...)`) sẽ gây ra tình trạng treo chương trình hoặc OutOfMemoryError.
```java
// DANGEROUS: Will hang indefinitely
Stream.iterate(0, i -> i + 1)
      .sorted()
      .limit(5)
      .forEach(System.out::println);
```

### 2. Thay đổi các phần tử bên trong map() hoặc filter()

Các thao tác trung gian không nên gây ra tác dụng phụ. Việc sửa đổi các biến bên ngoài hoặc thay đổi trạng thái của các phần tử trong map/filter sẽ dẫn đến tình trạng tranh chấp (race conditions) và lỗi, đặc biệt là trong các luồng song song (parallel streams).
```java
List<Integer> target = new ArrayList<>();
Stream.of(1, 2, 3)
      .map(x -> {
          target.add(x); // BAD: Side effect!
          return x * 2;
      })
      .count();
```

Kiểm tra thực tế:

- Định nghĩa `sorted` trong một câu.
- Nhận biết `sorted` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `sorted`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `sorted` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Các Câu Hỏi Ôn Tập Thường Gặp

- Khái niệm nào ở đây là quy tắc tại thời điểm biên dịch (compile-time rules)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm thực thi (runtime behavior)?
- Khái niệm nào ở đây có khả năng là bẫy phỏng vấn?

## Tại sao flatMap() khác với map()

Trong Java Stream API, sự khác biệt cơ bản giữa `map()` và `flatMap()` nằm ở cấu trúc dữ liệu mà chúng tạo ra và cách chúng biến đổi các phần tử. Thao tác `map()` là một phép biến đổi một-đối-một, nhận vào một hàm có kiểu `T -> R` và trả về một `Stream<R>` trong đó mỗi phần tử đầu vào tương ứng với chính xác một phần tử đầu ra. Ngược lại, `flatMap()` là một phép biến đổi một-nhiều (one-to-many transformation), nhận vào một hàm ánh xạ (mapper function) có kiểu `T -> Stream<R>`. Thay vì tạo ra một cấu trúc luồng lồng nhau như `Stream<Stream<R>>`, `flatMap()` sẽ hợp nhất hoặc "làm phẳng" nội dung của từng luồng tạm thời (transient stream) thành một luồng hạ lưu (downstream) `Stream<R>` liên tục duy nhất. Khi các phần tử đi qua luồng, JVM sẽ thực thi hàm này, tạo ra các đối tượng luồng tạm thời, tiêu thụ các phần tử của chúng và đóng tuần tự từng luồng tạm thời.

### Mô Hình Tư Duy
```
map() [One-to-One]:
Input:  [ "A" ] ---------> map(s -> s.toLowerCase()) ---------> Output: [ "a" ]

flatMap() [One-to-Many & Flatten]:
Input:  [ [1, 2], [3, 4] ]
              |
              +--> flatMap(list -> list.stream())
                      |
                      v
          Stream[1, 2] and Stream[3, 4]  (Nested Streams)
                      |
                      v (Flattening)
Output: [ 1, 2, 3, 4 ]                   (Single Stream)
```

### Ví Dụ Mã Nguồn
```java
import java.util.List;
import java.util.stream.Stream;

public class FlatMapDemo {
    public static void main(String[] args) {
        List<List<String>> nestedList = List.of(
            List.of("Java", "Python"),
            List.of("C++", "Go")
        );

        // flatMap flattens the Stream<List<String>> into Stream<String>
        List<String> flattened = nestedList.stream()
            .flatMap(list -> list.stream())
            .map(String::toUpperCase)
            .toList();

        System.out.println("Flattened: " + flattened);
        // Console Output:
        // Flattened: [JAVA, PYTHON, C++, GO]
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)
Đầu vào bộ sưu tập lồng nhau (Nested collection input) &rarr; map() tạo ra Luồng của các Luồng (Stream of Streams) (lồng nhau) &rarr; flatMap() nhận hàm ánh xạ trả về Stream&lt;R&gt; &rarr; flatMap() trích xuất và liên kết các phần tử của các luồng trung gian (intermediate streams) &rarr; Các luồng trung gian tự động đóng &rarr; Tạo ra một Luồng hạ lưu thống nhất duy nhất

---