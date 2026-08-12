# Stream API - Phần 1

## Ghi Chú Chi Tiết

### Stream là gì?

Stream là một chuỗi các phần tử hỗ trợ các thao tác tổng hợp tuần tự và song song. Nó xử lý dữ liệu theo hướng khai báo thông qua một đường ống, và quan trọng nhất là nó không làm thay đổi nguồn dữ liệu gốc (không làm thay đổi cấu trúc dữ liệu ban đầu).

> Xem thêm: Các phương thức trung gian và kết thúc trong Stream API thường nhận tham số là Biểu thức Lambda, được trình bày chi tiết trong [Ch.21 - Lambda Expression](../../no21_lambda_expression/README.md).

#### Ví Dụ Mã Nguồn
```java
// Streams process data pipelines lazily without modifying the source
List<String> list = List.of("apple", "banana", "cherry");
long count = list.stream()
                 .filter(s -> s.startsWith("a"))
                 .count(); // Terminal operation triggers execution
System.out.println(count); // Output: 1
```

### Stream so với Collection

Collection là cấu trúc dữ liệu lưu trữ các phần tử trong bộ nhớ, trong khi Stream không lưu trữ dữ liệu mà chỉ truyền chúng qua một đường ống xử lý. Hơn nữa, Stream chỉ có thể được duyệt một lần duy nhất, còn Collection có thể duyệt nhiều lần.

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

### Tạo Stream

Việc khởi tạo một Stream là bước đầu tiên để xây dựng đường ống xử lý, với nhiều phương thức factory được cung cấp bởi Java để tạo Stream từ các nguồn dữ liệu khác nhau.

#### Ví Dụ Mã Nguồn
```java
// Various factories to create streams
Stream<String> emptyStream = Stream.empty();
Stream<Integer> streamOf = Stream.of(1, 2, 3);
```

### Từ List

Bạn có thể dễ dàng tạo một Stream từ một List (hoặc bất kỳ Collection nào) bằng cách gọi phương thức `.stream()` được cung cấp mặc định trên interface Collection.

#### Ví Dụ Mã Nguồn
```java
List<String> list = List.of("Java", "Stream", "API");
Stream<String> stream = list.stream(); // Returns Stream<String>
```

### Từ Array

Lớp tiện ích `Arrays` cung cấp phương thức `Arrays.stream()` để tạo Stream từ một mảng. Nếu truyền vào một mảng kiểu nguyên thủy, nó sẽ trả về một Stream nguyên thủy tương ứng (như IntStream, DoubleStream) để tối ưu hiệu năng.

#### Ví Dụ Mã Nguồn
```java
String[] arr = {"x", "y", "z"};
Stream<String> stream = Arrays.stream(arr);

// Primitive array creation returns a specialized primitive stream
int[] intArr = {1, 2, 3};
IntStream intStream = Arrays.stream(intArr);
```

### Từ Map

Map không kế thừa Collection nên không có trực tiếp phương thức `.stream()`. Tuy nhiên, bạn có thể tạo Stream từ Map gián tiếp thông qua `.keySet().stream()`, `.values().stream()`, hoặc phổ biến nhất là `.entrySet().stream()`.

#### Ví Dụ Mã Nguồn
```java
Map<String, Integer> map = Map.of("A", 1, "B", 2);
// Streams are created from keySet, values, or entrySet of the Map
Stream<String> keyStream = map.keySet().stream();
Stream<Integer> valueStream = map.values().stream();
Stream<Map.Entry<String, Integer>> entryStream = map.entrySet().stream();
```

### Stream.of

Phương thức tĩnh `Stream.of()` cho phép bạn tạo nhanh một Stream từ các giá trị truyền vào trực tiếp (varargs).

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

### IntStream

`IntStream` (và tương tự là `LongStream`, `DoubleStream`) là các luồng chuyên biệt dành riêng cho kiểu dữ liệu nguyên thủy để tránh chi phí autoboxing. Bạn có thể dễ dàng tạo dãy số liên tiếp bằng các phương thức như `IntStream.range()`.

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
