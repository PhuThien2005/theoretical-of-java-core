# Stream API - Phần 5 (Stream API - Part 5)

## Mục tiêu học tập

Tài liệu này tập trung vào một phần trọng tâm của **Stream API**. Hãy nghiên cứu từng khái niệm dưới dạng quy tắc Java thực tế, thay vì chỉ học các từ vựng rời rạc.

## Đề cương chi tiết

- **`Lazy evaluation`** — Đánh giá lười biếng (Lazy evaluation): Đánh giá lười biếng là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và trạng thái lỗi thay vì chỉ nhớ tên của nó.
- **`Short-circuiting`** — Ngắt mạch (Short-circuiting): Ngắt mạch là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và trạng thái lỗi thay vì chỉ nhớ tên của nó.
- **`Parallel stream`** — Luồng song song (Parallel stream): Một Stream là một đường ống để xử lý các phần tử thông qua các thao tác lười biếng.
- **`Collectors:`** — Các bộ thu thập (Collectors:): Collectors là một nhóm các quy tắc liên quan trong Stream API nhóm một số chi tiết liên quan.
- **`toSet`** — toSet: Một Set là một bộ sưu tập từ chối các phần tử trùng lặp theo quy tắc bằng nhau.
- **`toMap`** — toMap: Một Map lưu trữ các cặp khóa-giá trị và truy xuất các giá trị theo khóa.
- **`joining`** — joining: joining là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và trạng thái lỗi thay vì chỉ nhớ tên của nó.
- **`groupingBy`** — groupingBy: groupingBy là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và trạng thái lỗi thay vì chỉ nhớ tên của nó.

## Ghi chú chi tiết

### Đánh giá lười biếng (Lazy evaluation)

Đánh giá lười biếng (lazy evaluation) là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và trạng thái lỗi thay vì chỉ nhớ tên của nó.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và trạng thái lỗi. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn dán.

#### Ví dụ mã nguồn
```java
// Intermediate operations do not execute until a terminal operation is called
Stream<String> stream = Stream.of("a", "b", "c")
                              .peek(s -> System.out.println("Processing: " + s));
System.out.println("Stream pipeline built.");
stream.count(); // Now execution starts!
```

Kiểm tra thực tế:
- Định nghĩa `Đánh giá lười biếng` trong một câu.
- Nhận diện `Đánh giá lười biếng` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Đánh giá lười biếng`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `Đánh giá lười biếng` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Ngắt mạch (Short-circuiting)

Ngắt mạch (short-circuiting) là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và trạng thái lỗi thay vì chỉ nhớ tên của nó.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và trạng thái lỗi. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn dán.

#### Ví dụ mã nguồn
```java
// Short-circuiting halts pipeline processing early
Stream.iterate(1, i -> i + 1)
      .peek(i -> System.out.println("Generated: " + i))
      .limit(3) // Limits elements flowing downstream
      .count(); // Prints Generated: 1, 2, 3
```

Kiểm tra thực tế:
- Định nghĩa `Ngắt mạch` trong một câu.
- Nhận diện `Ngắt mạch` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Ngắt mạch`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `Ngắt mạch` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Luồng song song (Parallel stream)

Một Stream là một đường ống để xử lý các phần tử thông qua các thao tác lười biếng.

Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều đường ống kiểu hàm. Một sự hiểu lầm thường gặp là quên mất thao tác nào là lười biếng và thao tác nào thực sự kích hoạt thực thi.

#### Ví dụ mã nguồn
```java
// Processing in parallel using ForkJoinPool.commonPool()
long sum = LongStream.rangeClosed(1, 1_000_000)
                     .parallel()
                     .sum();
```

### Ví Dụ Thực Tế: Tại sao luồng song song có thể chậm hơn đối với tập dữ liệu nhỏ hoặc các thao tác lưu trạng thái (stateful)

Các luồng song song chia tách dữ liệu bằng cách sử dụng `Spliterator`, gửi các tác vụ đến `ForkJoinPool.commonPool()`, điều phối việc thực thi giữa các luồng CPU và gộp các kết quả riêng phần lại với nhau.

#### Tại sao tập dữ liệu nhỏ lại chậm hơn
Chi phí của việc quản lý luồng, chuyển đổi ngữ cảnh, chia tách tác vụ và trộn kết quả lớn hơn nhiều so với thời gian thực thi của một vòng lặp nhỏ. Ví dụ, xử lý tuần tự 100 số nguyên chỉ mất vài nano giây, trong khi việc thiết lập thực thi song song mất tới vài mili giây.

#### Tại sao các thao tác lưu trạng thái làm giảm hiệu năng song song
Các thao tác trung gian lưu trạng thái như `sorted()`, `distinct()`, `limit()`, và `skip()` yêu cầu sự điều phối giữa các luồng và đồng bộ hóa rào cản (barrier synchronization).
- Đối với `sorted()`, các phần tử phải được thu thập đầy đủ, hợp nhất, sắp xếp và phân tách lại.
- Đối với `limit(n)`, các phần tử phải được xử lý trong khi theo dõi nghiêm ngặt thứ tự gặp (encounter order) giữa nhiều luồng, tạo ra các nút thắt cổ chai tuần tự.

#### Quy tắc N * Q
Một phương pháp phán đoán tốt để quyết định có nên sử dụng luồng song song hay không là $N \times Q > 10.000$, trong đó:
- $N$ là số lượng phần tử dữ liệu.
- $Q$ là chi phí tính toán cho mỗi phần tử.
Nếu $N \times Q$ nhỏ, các luồng tuần tự hầu như luôn nhanh hơn.

Kiểm tra thực tế:
- Định nghĩa `Luồng song song` trong một câu.
- Nhận diện `Luồng song song` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Luồng song song`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `Luồng song song` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Các bộ thu thập (Collectors)

Collectors là một nhóm các quy tắc liên quan trong Stream API nhóm một số chi tiết liên quan.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và trạng thái lỗi. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn dán.

#### Ví dụ mã nguồn
```java
// General use of Collectors factory methods
List<String> list = Stream.of("a", "b").collect(Collectors.toList());
```

Kiểm tra thực tế:
- Định nghĩa `Bộ thu thập` trong một câu.
- Nhận diện `Bộ thu thập` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Bộ thu thập`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `Bộ thu thập` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### toSet

Một Set là một bộ sưu tập từ chối các phần tử trùng lặp theo quy tắc bằng nhau.

Nó quan trọng vì việc chọn sai cấu trúc dữ liệu sẽ làm thay đổi tính chính xác, hiệu năng và hành vi xử lý trùng lặp. Một sự hiểu lầm thường gặp là ghi nhớ tên các lớp mà không biết thứ tự tra cứu, quy tắc bằng nhau hoặc hành vi lặp.

#### Ví dụ mã nguồn
```java
// Accumulate into a Set to eliminate duplicates
Set<String> set = Stream.of("a", "b", "a").collect(Collectors.toSet()); // ["a", "b"]
```

Kiểm tra thực tế:
- Định nghĩa `toSet` trong một câu.
- Nhận diện `toSet` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `toSet`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `toSet` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### toMap

Một Map lưu trữ các cặp khóa-giá trị và truy xuất các giá trị theo khóa.

Nó quan trọng vì việc chọn sai cấu trúc dữ liệu sẽ làm thay đổi tính chính xác, hiệu năng và hành vi xử lý trùng lặp. Một sự hiểu lầm thường gặp là ghi nhớ tên các lớp mà không biết thứ tự tra cứu, quy tắc bằng nhau hoặc hành vi lặp.

#### Ví dụ mã nguồn
```java
// Accumulate into a Map (requires key mapper, value mapper, and optional merge function)
Map<Integer, String> map = Stream.of("apple", "banana")
                                 .collect(Collectors.toMap(
                                     String::length, 
                                     s -> s, 
                                     (existing, replacement) -> existing
                                 ));
```

Kiểm tra thực tế:
- Định nghĩa `toMap` trong một câu.
- Nhận diện `toMap` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `toMap`.

Ví dụ nhỏ hoặc mô hình tư duy:
- `Map<String, Integer> scores = new HashMap<>();` ánh xạ các khóa sang các giá trị.

### joining

joining là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và trạng thái lỗi thay vì chỉ nhớ tên của nó.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và trạng thái lỗi. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn dán.

#### Ví dụ mã nguồn
```java
// Join string elements with delimiter
String joined = Stream.of("a", "b", "c")
                      .collect(Collectors.joining(", ")); // "a, b, c"
```

Kiểm tra thực tế:
- Định nghĩa `joining` trong một câu.
- Nhận diện `joining` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `joining`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `joining` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### groupingBy

groupingBy là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và trạng thái lỗi thay vì chỉ nhớ tên của nó.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và trạng thái lỗi. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn dán.

#### Ví dụ mã nguồn
```java
// Group elements by classifier function
Map<Integer, List<String>> groups = Stream.of("a", "bb", "c", "ddd")
                                          .collect(Collectors.groupingBy(String::length));
// Result: {1=["a", "c"], 2=["bb"], 3=["ddd"]}
```

## Các lỗi thường gặp

### 1. Ngoại lệ trùng lặp khóa trong Collectors.toMap()
Nếu các khóa được trả về bởi hàm ánh xạ khóa không phải là duy nhất, `Collectors.toMap` sẽ ném ra ngoại lệ `IllegalStateException` trừ khi cung cấp một hàm trộn (merge function).
```java
// Throws IllegalStateException: Duplicate key 5
Stream.of("apple", "peach")
      .collect(Collectors.toMap(String::length, s -> s)); 

// Correct way with merge function:
Map<Integer, String> map = Stream.of("apple", "peach")
      .collect(Collectors.toMap(
          String::length, 
          s -> s, 
          (existing, replacement) -> existing // Keeps first one found
      ));
```

### 2. Sửa đổi trạng thái chia sẻ từ các thao tác luồng
Các luồng song song thực thi các thao tác trên nhiều luồng. Việc thay đổi một bộ sưu tập được chia sẻ như `ArrayList` từ bên trong các thao tác luồng sẽ dẫn đến tình trạng tranh đoạt dữ liệu (race condition).
```java
List<Integer> list = new ArrayList<>();
List.of(1, 2, 3, 4).parallelStream().forEach(list::add); // DANGEROUS: Race condition!
```

Kiểm tra thực tế:
- Định nghĩa `groupingBy` trong một câu.
- Nhận diện `groupingBy` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `groupingBy`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `groupingBy` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Các câu hỏi ôn tập thường gặp

- Khái niệm nào ở đây là quy tắc thời điểm biên dịch?
- Khái niệm nào ở đây ảnh hưởng đến hành vi thời điểm chạy?
- Khái niệm nào ở đây có khả năng là bẫy phỏng vấn?

## Tại sao các Stream được đánh giá lười biếng

Java Stream đạt được tính lười biếng bằng cách tách biệt việc xây dựng đường ống với việc xử lý phần tử. Khi bạn gọi các thao tác trung gian như `filter()` hoặc `map()`, JVM không duyệt qua nguồn dữ liệu hoặc thực thi bất kỳ biểu thức lambda nào. Thay vào đó, mỗi thao tác trung gian trả về một giai đoạn Stream mới được đại diện bởi một lớp con `AbstractPipeline`, tự nối thêm để tạo thành một danh sách liên kết của các giai đoạn luồng. Giao diện `Sink` hỗ trợ việc thực thi thực tế; mỗi giai đoạn bao bọc `Sink` hạ nguồn bên trong triển khai `Sink` của chính nó, thiết lập một kiến trúc gọi lại dạng chuỗi (chained callback). Chỉ khi một thao tác kết thúc được gọi, đường ống mới duyệt qua nguồn, đẩy từng phần tử một xuống chuỗi `Sink` lồng nhau. Phép đánh giá một lượt (single-pass) này cho phép các thao tác ngắt mạch chấm dứt sớm và ngăn chặn việc tạo ra các bộ sưu tập trung gian tốn kém chi phí.

### Mô hình tư duy
```text
[Source] -> AbstractPipeline (filter) -> AbstractPipeline (map) -> Terminal (collect)
                  |                            |
            Sink.begin()                 Sink.begin()
            Sink.accept()  ------------> Sink.accept()
            Sink.end()                   Sink.end()
```

### Ví dụ mã nguồn
```java
import java.util.List;
import java.util.stream.Stream;

public class LazyEvaluationDemo {
    public static void main(String[] args) {
        List<String> result = Stream.of("apple", "banana", "pear")
            .filter(s -> {
                System.out.println("Filtering: " + s);
                return s.length() > 4;
            })
            .map(s -> {
                System.out.println("Mapping: " + s);
                return s.toUpperCase();
            })
            .limit(1)
            .toList();
        
        System.out.println("Result: " + result);
        // Console Output:
        // Filtering: apple
        // Mapping: apple
        // Result: [APPLE]
    }
}
```

### Chuỗi nguyên nhân - kết quả
Các thao tác trung gian được đăng ký &rarr; Đường ống được xây dựng dưới dạng các nút AbstractPipeline liên kết &rarr; Thao tác kết thúc được gọi &rarr; Các phần tử nguồn được kéo qua chuỗi Sink từng phần tử một &rarr; Thao tác ngắt mạch (limit) kích hoạt chấm dứt sớm &rarr; Khối lượng công việc CPU tối thiểu được thực hiện

## Tại sao luồng song song không phải là một giải pháp mặc định

Các luồng song song phân tách dữ liệu nguồn của luồng bằng cách sử dụng một `Spliterator` và gửi các tác vụ đến `ForkJoinPool.commonPool()` chung. Do bể chứa (pool) này được chia sẻ toàn cục trên toàn bộ classloader của JVM, các thao tác chạy lâu, tốn nhiều tài nguyên CPU hoặc gây nghẽn (blocking) trong một luồng sẽ làm cạn kiệt tài nguyên luồng của các phần khác trong ứng dụng. Hiệu quả của việc phân tách dữ liệu cũng phụ thuộc rất nhiều vào cấu trúc của nguồn dữ liệu; một mảng hoặc `ArrayList` có thể được phân tách trong thời gian O(1) bằng cách chia các phạm vi chỉ số, trong khi một `LinkedList` yêu cầu duyệt tuần tự qua O(N) phần tử để tìm điểm phân tách, làm triệt tiêu bất kỳ mức tăng hiệu năng nào. Hơn nữa, việc điều phối, chuyển đổi ngữ cảnh và gộp kết quả trên các luồng làm việc tạo ra chi phí đáng kể cho JVM. Do đó, đối với các tập dữ liệu nhỏ hoặc các tác vụ bị giới hạn bởi I/O không tầm thường, một luồng song song có thể chạy chậm hơn đáng kể so với luồng tuần tự tương ứng.

### Mô hình tư duy
```text
Phân tách ArrayList (Phân tách dựa trên chỉ số O(1)):
[ Phần tử 0 - 3 ] ---> Luồng 1
[ Phần tử 4 - 7 ] ---> Luồng 2

Phân tách LinkedList (Duyệt tuần tự O(N)):
[Đầu] -> [Nút] -> [Nút] -> [Nút] -> [Nút] -> [Nút] -> [Nút] -> [Cuối]
 (Phải duyệt qua các liên kết từng bước để tìm điểm phân tách)
```

### Ví dụ mã nguồn
```java
import java.util.List;
import java.util.stream.LongStream;

public class ParallelStreamDemo {
    public static void main(String[] args) {
        // Parallel stream processing using the common ForkJoinPool
        long sum = LongStream.rangeClosed(1, 1_000_000)
                             .parallel()
                             .filter(n -> n % 2 == 0)
                             .sum();
        
        System.out.println("Sum: " + sum);
        // Console Output:
        // Sum: 250000500000
    }
}
```

### Chuỗi nguyên nhân - kết quả
Nguồn dữ liệu (ví dụ: LinkedList) khó phân tách &rarr; Spliterator thực hiện duyệt tuần tự O(N) để phân chia công việc &rarr; Chi phí phân tách và điều phối cao &rarr; Thực thi luồng trong bể chung toàn cục ForkJoinPool.commonPool() &rarr; Các thao tác gây nghẽn làm cạn kiệt tài nguyên luồng &rarr; Hiệu năng bị suy giảm so với luồng tuần tự

## Liên kết tham khảo
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/ConcurrentHashMap.html (ConcurrentHashMap API docs)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Collections.html#synchronizedMap(java.util.Map) (synchronizedMap wrapper)
