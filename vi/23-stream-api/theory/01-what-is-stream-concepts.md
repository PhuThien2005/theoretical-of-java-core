# Stream API - Phần 1 (Stream API - Part 1)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này đề cập đến một phần trọng tâm của **Stream API**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là những thuật ngữ riêng lẻ.

## Phạm Vi Outline (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `What is Stream?` | Một Stream là một đường ống để xử lý các phần tử thông qua các thao tác lười biếng. |
| `Stream vs Collection` | Một collection là một đối tượng nhóm nhiều phần tử dưới một API chung. |
| `Create Stream:` | Một Stream là một đường ống để xử lý các phần tử thông qua các thao tác lười biếng. |
| `from List` | Một `List` là một collection có thứ tự, có thể chứa các phần tử trùng lặp và hỗ trợ truy cập theo vị trí chỉ mục. |
| `from Array` | từ Mảng (from Array) là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `from Map` | Một `Map` lưu trữ các cặp key-value và truy xuất các giá trị bằng key. |
| `Stream.of` | Một Stream là một đường ống để xử lý các phần tử thông qua các thao tác lười biếng. |
| `IntStream` | Một Stream là một đường ống để xử lý các phần tử thông qua các thao tác lười biếng. |

## Ghi Chú Chi Tiết (Detailed Notes)

### Stream là gì? (What is Stream?)

Một Stream là một đường ống để xử lý các phần tử thông qua các thao tác lười biếng.

Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều các đường ống (pipeline) kiểu hàm. Một sự nhầm lẫn phổ biến là quên mất thao tác nào là lười biếng (lazy evaluation) và thao tác nào thực sự kích hoạt việc thực thi.

#### Ví dụ mã nguồn
```java
// Stream xử lý các đường ống dữ liệu một cách lười biếng mà không sửa đổi nguồn
List<String> list = List.of("apple", "banana", "cherry");
long count = list.stream()
                 .filter(s -> s.startsWith("a"))
                 .count(); // Thao tác cuối kích hoạt thực thi
System.out.println(count); // Kết quả: 1
```

Kiểm tra thực tế:
- Định nghĩa `What is Stream?` trong một câu.
- Nhận biết `What is Stream?` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `What is Stream?`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `What is Stream?` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Stream so với Collection (Stream vs Collection)

Một collection là một đối tượng nhóm nhiều phần tử dưới một API chung.

Nó quan trọng vì việc chọn sai cấu trúc dữ liệu sẽ làm thay đổi tính chính xác, hiệu suất và hành vi xử lý trùng lặp. Một sự nhầm lẫn phổ biến là ghi nhớ tên lớp mà không biết thứ tự tìm kiếm, quy tắc so sánh bằng hay hành vi lặp.

#### Ví dụ mã nguồn
```java
// Collection lưu giữ các phần tử trong bộ nhớ; Stream là một đường ống dùng một lần
List<String> list = List.of("a", "b", "c");
// Collection có thể được duyệt qua nhiều lần
list.forEach(System.out::print);
list.forEach(System.out::print); 

// Stream chỉ có thể được tiêu thụ MỘT LẦN duy nhất
Stream<String> stream = list.stream();
stream.forEach(System.out::print);
// stream.forEach(System.out::print); // Ném ra IllegalStateException!
```

Kiểm tra thực tế:
- Định nghĩa `Stream vs Collection` trong một câu.
- Nhận biết `Stream vs Collection` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `Stream vs Collection`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `Stream vs Collection` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Tạo Stream (Create Stream:)

Một Stream là một đường ống để xử lý các phần tử thông qua các thao tác lười biếng.

Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều các đường ống (pipeline) kiểu hàm. Một sự nhầm lẫn phổ biến là quên mất thao tác nào là lười biếng (lazy evaluation) và thao tác nào thực sự kích hoạt việc thực thi.

#### Ví dụ mã nguồn
```java
// Các phương thức nhà máy khác nhau để tạo stream
Stream<String> emptyStream = Stream.empty();
Stream<Integer> streamOf = Stream.of(1, 2, 3);
```

Kiểm tra thực tế:
- Định nghĩa `Create Stream:` trong một câu.
- Nhận biết `Create Stream:` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `Create Stream:`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `Create Stream:` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### từ List (from List)

Một `List` là một collection có thứ tự, có thể chứa các phần tử trùng lặp và hỗ trợ truy cập theo vị trí chỉ mục.

Nó quan trọng vì việc chọn sai cấu trúc dữ liệu sẽ làm thay đổi tính chính xác, hiệu suất và hành vi xử lý trùng lặp. Một sự nhầm lẫn phổ biến là ghi nhớ tên lớp mà không biết thứ tự tìm kiếm, quy tắc so sánh bằng hay hành vi lặp.

#### Ví dụ mã nguồn
```java
List<String> list = List.of("Java", "Stream", "API");
Stream<String> stream = list.stream(); // Trả về Stream<String>
```

Kiểm tra thực tế:
- Định nghĩa `from List` trong một câu.
- Nhận biết `from List` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `from List`.

Ví dụ nhỏ hoặc mô hình tư duy:
- `List<String> names = new ArrayList<>();` lưu trữ các phần tử có thứ tự.

### từ Mảng (from Array)

từ Mảng (from Array) là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
String[] arr = {"x", "y", "z"};
Stream<String> stream = Arrays.stream(arr);

// Tạo mảng kiểu nguyên thủy trả về stream nguyên thủy chuyên biệt
int[] intArr = {1, 2, 3};
IntStream intStream = Arrays.stream(intArr);
```

Kiểm tra thực tế:
- Định nghĩa `from Array` trong một câu.
- Nhận biết `from Array` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `from Array`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `from Array` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### từ Map (from Map)

Một `Map` lưu trữ các cặp key-value và truy xuất các giá trị bằng key.

Nó quan trọng vì việc chọn sai cấu trúc dữ liệu sẽ làm thay đổi tính chính xác, hiệu suất và hành vi xử lý trùng lặp. Một sự nhầm lẫn phổ biến là ghi nhớ tên lớp mà không biết thứ tự tìm kiếm, quy tắc so sánh bằng hay hành vi lặp.

#### Ví dụ mã nguồn
```java
Map<String, Integer> map = Map.of("A", 1, "B", 2);
// Stream được tạo từ keySet, values, hoặc entrySet của Map
Stream<String> keyStream = map.keySet().stream();
Stream<Integer> valueStream = map.values().stream();
Stream<Map.Entry<String, Integer>> entryStream = map.entrySet().stream();
```

Kiểm tra thực tế:
- Định nghĩa `from Map` trong một câu.
- Nhận biết `from Map` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `from Map`.

Ví dụ nhỏ hoặc mô hình tư duy:
- `Map<String, Integer> scores = new HashMap<>();` ánh xạ các key tới value.

### Stream.of

Một Stream là một đường ống để xử lý các phần tử thông qua các thao tác lười biếng.

Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều các đường ống (pipeline) kiểu hàm. Một sự nhầm lẫn phổ biến là quên mất thao tác nào là lười biếng (lazy evaluation) và thao tác nào thực sự kích hoạt việc thực thi.

#### Ví dụ mã nguồn
```java
Stream<String> streamOf = Stream.of("hello", "world");
```

### từ File (from Files)

Bạn có thể tạo một Stream đọc lười biếng các dòng văn bản từ một đường dẫn tệp bằng cách sử dụng `Files.lines(Path)`.

#### Ví dụ mã nguồn
```java
// Đọc lười biếng từ file (tự động quản lý trong try-with-resources)
try (Stream<String> lines = Files.lines(Paths.get("example.txt"))) {
    lines.filter(line -> line.contains("ERROR"))
         .forEach(System.out::println);
} catch (IOException e) {
    // Xử lý ngoại lệ I/O
}
```

Kiểm tra thực tế:
- Định nghĩa `Stream.of` trong một câu.
- Nhận biết `Stream.of` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `Stream.of`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `Stream.of` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### IntStream

Một Stream là một đường ống để xử lý các phần tử thông qua các thao tác lười biếng.

Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều các đường ống (pipeline) kiểu hàm. Một sự nhầm lẫn phổ biến là quên mất thao tác nào là lười biếng (lazy evaluation) và thao tác nào thực sự kích hoạt việc thực thi.

#### Ví dụ mã nguồn
```java
// Tạo IntStream nguyên thủy
IntStream rangeStream = IntStream.range(1, 5); // Các phần tử: 1, 2, 3, 4
IntStream closedStream = IntStream.rangeClosed(1, 5); // Các phần tử: 1, 2, 3, 4, 5
```

## Các Lỗi Thường Gặp (Common Mistakes)

### 1. Tái sử dụng một stream đã đóng
Một khi thao tác cuối được gọi trên một stream, stream đó sẽ bị tiêu thụ/đóng lại. Cố gắng gọi thêm một thao tác khác sẽ ném ra `IllegalStateException`.
```java
Stream<String> stream = Stream.of("a", "b", "c");
stream.forEach(System.out::println); // Tiêu thụ stream
// stream.count(); // Ném ra IllegalStateException!
```

### 2. Tạo một Stream.of với một mảng kiểu nguyên thủy
Truyền một mảng kiểu nguyên thủy vào `Stream.of` sẽ tạo ra một `Stream<int[]>` chứa đúng một phần tử duy nhất (chính là mảng đó), chứ không phải là một stream chứa các số nguyên.
```java
int[] numbers = {1, 2, 3};
Stream<int[]> badStream = Stream.of(numbers); 
System.out.println(badStream.count()); // In ra 1

// Cách đúng để có được IntStream:
IntStream goodStream = Arrays.stream(numbers);
System.out.println(goodStream.count()); // In ra 3
```

Kiểm tra thực tế:
- Định nghĩa `IntStream` trong một câu.
- Nhận biết `IntStream` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `IntStream`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `IntStream` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Các Câu Hỏi Ôn Tập Thường Gặp (Common Review Prompts)

- Những khái niệm nào ở đây là quy tắc tại thời điểm biên dịch (compile-time)?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy (runtime)?
- Những khái niệm nào ở đây có khả năng là bẫy khi phỏng vấn?

## Tại Sao Primitive Stream Tồn Tại Và Tránh Autoboxing (Why Primitive Streams Exist and Avoid Autoboxing)

Trong Java, các tham số kiểu generic không thể là các kiểu nguyên thủy, có nghĩa là các stream tham chiếu tiêu chuẩn như `Stream<Integer>` phải làm việc với các đối tượng đóng hộp (boxed object). Mô hình đóng hộp này phát sinh chi phí đáng kể: mỗi số được bọc trong một đối tượng trên heap, làm tăng dung lượng bộ nhớ và gây ra lỗi cache do phải truy tìm con trỏ (pointer chasing). Để giải quyết vấn đề này, JDK cung cấp các primitive stream chuyên biệt: `IntStream`, `LongStream`, và `DoubleStream`. Các stream này xử lý trực tiếp các giá trị nguyên thủy bên trong ranh giới bộ nhớ gốc (native memory), hoàn toàn bỏ qua chi phí CPU của quá trình tự động đóng hộp và mở hộp. Ngoài ra, các primitive stream cung cấp các thao tác cuối dạng số học đã được tối ưu hóa như `sum()`, `average()`, và `summaryStatistics()`, vốn không có sẵn trên các stream tham chiếu chung nếu không thực hiện ánh xạ.

### Mô hình tư duy (Mental Model)
```
Stream<Integer> (Các tham chiếu đóng hộp, truy tìm con trỏ):
[ Stream Pipeline ] -> [ Integer Ref ] -> ( Đối tượng Heap: 16-byte header + 4-byte int )
                       [ Integer Ref ] -> ( Đối tượng Heap: 16-byte header + 4-byte int )

IntStream (Các kiểu nguyên thủy xếp liền kề, tra cứu cache trực tiếp):
[ Stream Pipeline ] -> [ Nguyên thủy 1 ] -> [ Nguyên thủy 2 ] -> [ Nguyên thủy 3 ]
                       (Các giá trị 32-bit thô trực tiếp trong CPU register/cache)
```

### Ví dụ mã nguồn
```java
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrimitiveStreamDemo {
    public static void main(String[] args) {
        // Primitive stream tránh các chu kỳ đóng/mở hộp
        int sum = IntStream.rangeClosed(1, 5)
                           .sum();

        // Stream tham chiếu chịu chi phí đóng hộp
        int boxedSum = Stream.of(1, 2, 3, 4, 5)
                             .mapToInt(Integer::intValue) // Mở hộp (Unboxing)
                             .sum();

        System.out.println("Tổng: " + sum + ", Tổng đóng hộp: " + boxedSum);
        // Kết quả bảng điều khiển:
        // Tổng: 15, Tổng đóng hộp: 15
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Sử dụng generic `Stream<Integer>`
  → Các tham chiếu đối tượng lưu trên Heap
  → Áp lực thu gom rác và chi phí cache-miss tăng
  → Chuyển sang sử dụng `IntStream` chuyên biệt
  → Hoạt động trực tiếp trên các giá trị 32-bit gốc
  → Loại bỏ chi phí đóng hộp & tối đa hóa hiệu năng.
```

