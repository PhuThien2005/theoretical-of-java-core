# Stream API - Phần 1

## Mục Tiêu Học Tập

File này đề cập đến một phần trọng tâm của **Stream API**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là những từ vựng rời rạc.

## Đề Cương Khái Niệm

| Khái niệm | Những điều cần biết |
| --- | --- |
| `Stream là gì?` | Một Stream là một đường ống (pipeline) để xử lý các phần tử thông qua các hoạt động lười (lazy operation). |
| `Stream so với Collection` | Một tập hợp là một đối tượng nhóm nhiều phần tử lại với nhau dưới một API chung. |
| `Tạo Stream:` | Một Stream là một đường ống để xử lý các phần tử thông qua các hoạt động lười. |
| `Từ List` | Một List là một tập hợp có thứ tự có thể chứa các phần tử trùng lặp và hỗ trợ truy cập theo vị trí. |
| `Từ Array` | từ Array là một khái niệm cụ thể trong Stream API; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại (failure mode) của nó thay vì chỉ nhớ mỗi tên gọi. |
| `Từ Map` | Một Map lưu trữ các cặp khóa-giá trị và truy xuất các giá trị bằng khóa. |
| `Stream.of` | Một Stream là một đường ống để xử lý các phần tử thông qua các hoạt động lười. |
| `IntStream` | Một Stream là một đường ống để xử lý các phần tử thông qua các hoạt động lười. |

## Ghi Chú Chi Tiết

### Stream là gì?

Một Stream là một đường ống để xử lý các phần tử thông qua các hoạt động lười (lazy operation).

Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều đường ống kiểu lập trình hàm. Một nhầm lẫn phổ biến là quên mất hoạt động nào là lười và hoạt động nào thực sự kích hoạt việc thực thi.

#### Ví Dụ Mã Nguồn
```java
// Streams process data pipelines lazily without modifying the source
List<String> list = List.of("apple", "banana", "cherry");
long count = list.stream()
                 .filter(s -> s.startsWith("a"))
                 .count(); // Terminal operation triggers execution
System.out.println(count); // Output: 1
```

Kiểm tra thực tế:

- Định nghĩa `Stream là gì?` trong một câu.
- Nhận diện `Stream là gì?` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Stream là gì?`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy tự hỏi: `Stream là gì?` thay đổi, cho phép, từ chối hay làm rõ điều gì?

### Stream so với Collection

Một tập hợp là một đối tượng nhóm nhiều phần tử lại với nhau dưới một API chung.

Nó quan trọng vì việc chọn sai cấu trúc dữ liệu sẽ ảnh hưởng đến tính đúng đắn, hiệu năng và hành vi xử lý trùng lặp. Một nhầm lẫn phổ biến là ghi nhớ tên lớp mà không biết thứ tự tra cứu, quy tắc so sánh bằng hoặc hành vi duyệt phần tử.

#### Ví Dụ Mã Nguồn
```java
// Collection holds elements in memory; Stream is a one-time pipeline
List<String> list = List.of("a", "b", "c");
// Collection can be iterated multiple times
list.forEach(System.out::print);
list.forEach(System.out::print); 

// Stream can only be consumed ONCE
Stream<String> stream = list.stream();
stream.forEach(System.out::print);
// stream.forEach(System.out::print); // Throws IllegalStateException!
```

Kiểm tra thực tế:

- Định nghĩa `Stream so với Collection` trong một câu.
- Nhận diện `Stream so với Collection` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `Stream so với Collection`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy tự hỏi: `Stream so với Collection` thay đổi, cho phép, từ chối hay làm rõ điều gì?

### Tạo Stream:

Một Stream là một đường ống để xử lý các phần tử thông qua các hoạt động lười.

Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều đường ống kiểu lập trình hàm. Một nhầm lẫn phổ biến là quên mất hoạt động nào là lười và hoạt động nào thực sự kích hoạt việc thực thi.

#### Ví Dụ Mã Nguồn
```java
// Various factories to create streams
Stream<String> emptyStream = Stream.empty();
Stream<Integer> streamOf = Stream.of(1, 2, 3);
```

Kiểm tra thực tế:

- Định nghĩa `Tạo Stream:` trong một câu.
- Nhận diện `Tạo Stream:` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `Tạo Stream:`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy tự hỏi: `Tạo Stream:` thay đổi, cho phép, từ chối hay làm rõ điều gì?

### Từ List

Một List là một tập hợp có thứ tự có thể chứa các phần tử trùng lặp và hỗ trợ truy cập theo vị trí.

Nó quan trọng vì việc chọn sai cấu trúc dữ liệu sẽ ảnh hưởng đến tính đúng đắn, hiệu năng và hành vi xử lý trùng lặp. Một nhầm lẫn phổ biến là ghi nhớ tên lớp mà không biết thứ tự tra cứu, quy tắc so sánh bằng hoặc hành vi duyệt phần tử.

#### Ví Dụ Mã Nguồn
```java
List<String> list = List.of("Java", "Stream", "API");
Stream<String> stream = list.stream(); // Returns Stream<String>
```

Kiểm tra thực tế:

- Định nghĩa `Từ List` trong một câu.
- Nhận diện `Từ List` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `Từ List`.

Ví dụ nhỏ hoặc mô hình tư duy:

- `List<String> names = new ArrayList<>();` lưu trữ các phần tử có thứ tự.

### Từ Array

từ Array là một khái niệm cụ thể trong Stream API; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại (failure mode) của nó thay vì chỉ nhớ mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được phép và chế độ thất bại. Hãy ôn tập lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

#### Ví Dụ Mã Nguồn
```java
String[] arr = {"x", "y", "z"};
Stream<String> stream = Arrays.stream(arr);

// Primitive array creation returns a specialized primitive stream
int[] intArr = {1, 2, 3};
IntStream intStream = Arrays.stream(intArr);
```

Kiểm tra thực tế:

- Định nghĩa `Từ Array` trong một câu.
- Nhận diện `Từ Array` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `Từ Array`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy tự hỏi: `Từ Array` thay đổi, cho phép, từ chối hay làm rõ điều gì?

### Từ Map

Một Map lưu trữ các cặp khóa-giá trị và truy xuất các giá trị bằng khóa.

Nó quan trọng vì việc chọn sai cấu trúc dữ liệu sẽ ảnh hưởng đến tính đúng đắn, hiệu năng và hành vi xử lý trùng lặp. Một nhầm lẫn phổ biến là ghi nhớ tên lớp mà không biết thứ tự tra cứu, quy tắc so sánh bằng hoặc hành vi duyệt phần tử.

#### Ví Dụ Mã Nguồn
```java
Map<String, Integer> map = Map.of("A", 1, "B", 2);
// Streams are created from keySet, values, or entrySet of the Map
Stream<String> keyStream = map.keySet().stream();
Stream<Integer> valueStream = map.values().stream();
Stream<Map.Entry<String, Integer>> entryStream = map.entrySet().stream();
```

Kiểm tra thực tế:

- Định nghĩa `Từ Map` trong một câu.
- Nhận diện `Từ Map` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `Từ Map`.

Ví dụ nhỏ hoặc mô hình tư duy:

- `Map<String, Integer> scores = new HashMap<>();` ánh xạ các khóa tới các giá trị.

### Stream.of

Một Stream là một đường ống để xử lý các phần tử thông qua các hoạt động lười.

Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều đường ống kiểu lập trình hàm. Một nhầm lẫn phổ biến là quên mất hoạt động nào là lười và hoạt động nào thực sự kích hoạt việc thực thi.

#### Ví Dụ Mã Nguồn
```java
Stream<String> streamOf = Stream.of("hello", "world");
```

### Từ Files

Bạn có thể tạo một Stream lười của các dòng văn bản từ một đường dẫn tệp bằng cách sử dụng `Files.lines(Path)`.

#### Ví Dụ Mã Nguồn
```java
// Lazy reading from files (automatically managed in try-with-resources)
try (Stream<String> lines = Files.lines(Paths.get("example.txt"))) {
    lines.filter(line -> line.contains("ERROR"))
         .forEach(System.out::println);
} catch (IOException e) {
    // Handle I/O exception
}
```

Kiểm tra thực tế:

- Định nghĩa `Stream.of` trong một câu.
- Nhận diện `Stream.of` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `Stream.of`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy tự hỏi: `Stream.of` thay đổi, cho phép, từ chối hay làm rõ điều gì?

### IntStream

Một Stream là một đường ống để xử lý các phần tử thông qua các hoạt động lười.

Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều đường ống kiểu lập trình hàm. Một nhầm lẫn phổ biến là quên mất hoạt động nào là lười và hoạt động nào thực sự kích hoạt việc thực thi.

#### Ví Dụ Mã Nguồn
```java
// Create primitive IntStream
IntStream rangeStream = IntStream.range(1, 5); // Elements: 1, 2, 3, 4
IntStream closedStream = IntStream.rangeClosed(1, 5); // Elements: 1, 2, 3, 4, 5
```

## Các Lỗi Thường Gặp

### 1. Tái Sử Dụng Một Stream Đã Đóng

Một khi một hoạt động đầu cuối (terminal operation) được gọi trên một stream, stream đó sẽ bị tiêu thụ/đóng lại. Việc cố gắng gọi một hoạt động khác sẽ ném ra `IllegalStateException`.
```java
Stream<String> stream = Stream.of("a", "b", "c");
stream.forEach(System.out::println); // Consumes the stream
// stream.count(); // Throws IllegalStateException!
```

### 2. Tạo Stream.of Với Một Mảng Kiểu Nguyên Thủy

Việc truyền một mảng kiểu nguyên thủy vào `Stream.of` sẽ tạo ra một `Stream<int[]>` chứa chính xác một phần tử duy nhất (chính là mảng đó), chứ không phải là một luồng các số.
```java
int[] numbers = {1, 2, 3};
Stream<int[]> badStream = Stream.of(numbers); 
System.out.println(badStream.count()); // Prints 1

// Correct way to get IntStream:
IntStream goodStream = Arrays.stream(numbers);
System.out.println(goodStream.count()); // Prints 3
```

Kiểm tra thực tế:

- Định nghĩa `IntStream` trong một câu.
- Nhận diện `IntStream` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `IntStream`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy tự hỏi: `IntStream` thay đổi, cho phép, từ chối hay làm rõ điều gì?

## Các Câu Hỏi Ôn Tập Thường Gặp

- Những khái niệm nào ở đây là quy tắc tại thời điểm biên dịch?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy?
- Những khái niệm nào ở đây có khả năng là bẫy phỏng vấn?

## Tại Sao Các Luồng Nguyên Thủy Tồn Tại và Giúp Tránh Cơ Chế Tự Động Đóng Hộp (Autoboxing)

Trong Java, các tham số kiểu generic không thể là các kiểu dữ liệu nguyên thủy, điều này có nghĩa là các luồng tham chiếu tiêu chuẩn như `Stream<Integer>` phải hoạt động với các đối tượng đã được đóng hộp (boxed objects). Mô hình đóng hộp này gây ra chi phí rất lớn: mỗi số được bọc trong một đối tượng trên bộ nhớ heap, gây ra sự phình to bộ nhớ và trượt bộ nhớ đệm (cache miss) do phải truy đuổi con trỏ (pointer chasing). Để giải quyết vấn đề này, JDK cung cấp các luồng nguyên thủy chuyên dụng: `IntStream`, `LongStream`, và `DoubleStream`. Các luồng này xử lý trực tiếp các giá trị nguyên thủy trong ranh giới bộ nhớ gốc, bỏ qua hoàn toàn chi phí CPU của việc tự động đóng hộp và mở hộp (unboxing). Ngoài ra, các luồng nguyên thủy còn cung cấp các hoạt động đầu cuối toán học được tối ưu hóa như `sum()`, `average()`, và `summaryStatistics()`, những hoạt động không có sẵn trên các luồng tham chiếu thông thường nếu không qua ánh xạ (mapping).

### Mô Hình Tư Duy (Mental Model)
```text
Stream<Integer> (Các tham chiếu đã đóng hộp, truy đuổi con trỏ):
[ Đường ống Stream ] -> [ Tham chiếu Integer ] -> ( Heap Object: 16-byte header + 4-byte int )
                        [ Tham chiếu Integer ] -> ( Heap Object: 16-byte header + 4-byte int )

IntStream (Các kiểu nguyên thủy liền kề, tra cứu cache trực tiếp):
[ Đường ống Stream ] -> [ Nguyên thủy 1 ] -> [ Nguyên thủy 2 ] -> [ Nguyên thủy 3 ]
                        (Giá trị 32-bit thô trực tiếp trong thanh ghi CPU/bộ nhớ đệm)
```

### Ví Dụ Mã Nguồn (Code Example)
```java
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrimitiveStreamDemo {
    public static void main(String[] args) {
        // Primitive stream avoiding box/unbox cycles
        int sum = IntStream.rangeClosed(1, 5)
                           .sum();

        // Reference stream incurring boxing overhead
        int boxedSum = Stream.of(1, 2, 3, 4, 5)
                             .mapToInt(Integer::intValue) // Unboxing
                             .sum();

        System.out.println("Sum: " + sum + ", Boxed Sum: " + boxedSum);
        // Console Output:
        // Sum: 15, Boxed Sum: 15
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)
Sử dụng Stream<Integer> generic &rarr; Các tham chiếu đối tượng lưu trữ trên Heap &rarr; Áp lực lên bộ thu gom rác và chi phí trượt cache &rarr; Chuyển sang IntStream chuyên dụng &rarr; Hoạt động trực tiếp trên các giá trị 32-bit gốc &rarr; Không tốn chi phí đóng hộp & hiệu năng được tối đa hóa
