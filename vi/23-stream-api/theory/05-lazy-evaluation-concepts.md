# Stream API - Phần 5 (Stream API - Part 5)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này đề cập đến một phần trọng tâm của **Stream API**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là những thuật ngữ riêng lẻ.

## Phạm Vi Outline (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `Lazy evaluation` | Đánh giá lười biếng (Lazy evaluation) là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `Short-circuiting` | Ngắn mạch (Short-circuiting) là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `Parallel stream` | Một Stream là một đường ống để xử lý các phần tử thông qua các thao tác lười biếng. |
| `Collectors:` | Các bộ thu gom (Collectors) là một nhóm các quy tắc liên quan trong Stream API nhóm lại một số chi tiết liên quan. |
| `toSet` | Một `Set` là một collection từ chối các phần tử trùng lặp theo quy tắc so sánh bằng. |
| `toMap` | Một `Map` lưu trữ các cặp key-value và truy xuất các giá trị bằng key. |
| `joining` | `joining` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `groupingBy` | `groupingBy` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |

## Ghi Chú Chi Tiết (Detailed Notes)

### Đánh giá lười biếng (Lazy evaluation)

Đánh giá lười biếng (Lazy evaluation) là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Các thao tác trung gian không thực thi cho đến khi một thao tác cuối được gọi
Stream<String> stream = Stream.of("a", "b", "c")
                              .peek(s -> System.out.println("Đang xử lý: " + s));
System.out.println("Đường ống Stream đã được dựng.");
stream.count(); // Bây giờ việc thực thi mới bắt đầu!
```

Kiểm tra thực tế:
- Định nghĩa `Lazy evaluation` trong một câu.
- Nhận biết `Lazy evaluation` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `Lazy evaluation`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `Lazy evaluation` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Ngắn mạch (Short-circuiting)

Ngắn mạch (Short-circuiting) là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Ngắn mạch dừng xử lý đường ống sớm
Stream.iterate(1, i -> i + 1)
      .peek(i -> System.out.println("Đã tạo: " + i))
      .limit(3) // Giới hạn các phần tử chạy xuống hạ nguồn
      .count(); // In ra Đã tạo: 1, 2, 3
```

Kiểm tra thực tế:
- Định nghĩa `Short-circuiting` trong một câu.
- Nhận biết `Short-circuiting` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `Short-circuiting`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `Short-circuiting` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Stream song song (Parallel stream)

Một Stream là một đường ống để xử lý các phần tử thông qua các thao tác lười biếng.

Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều các đường ống (pipeline) kiểu hàm. Một sự nhầm lẫn phổ biến là quên mất thao tác nào là lười biếng (lazy evaluation) và thao tác nào thực sự kích hoạt việc thực thi.

#### Ví dụ mã nguồn
```java
// Xử lý song song sử dụng ForkJoinPool.commonPool()
long sum = LongStream.rangeClosed(1, 1_000_000)
                     .parallel()
                     .sum();
```

### Case Study: Tại sao stream song song có thể chạy chậm hơn đối với tập dữ liệu nhỏ hoặc các thao tác có trạng thái (Case Study: Why parallel streams can be slower for small datasets or stateful operations)

Stream song song phân chia dữ liệu bằng cách sử dụng `Spliterator`, gửi các tác vụ đến `ForkJoinPool.commonPool()`, điều phối việc thực thi giữa các luồng (Thread) CPU, và gộp các kết quả thành phần lại với nhau.

#### Tại sao các tập dữ liệu nhỏ lại chạy chậm hơn
Chi phí quản lý luồng, chuyển đổi ngữ cảnh (context switching), phân chia tác vụ và trộn kết quả lớn hơn nhiều so với thời gian thực thi của một vòng lặp nhỏ. Ví dụ, xử lý tuần tự 100 số nguyên chỉ mất vài nano giây, trong khi việc thiết lập thực thi song song mất tới vài mili giây.

#### Tại sao các thao tác có trạng thái làm giảm hiệu năng chạy song song
Các thao tác trung gian có trạng thái (stateful intermediate operations) như `sorted()`, `distinct()`, `limit()`, và `skip()` yêu cầu sự điều phối giữa các luồng và đồng bộ hóa rào cản (barrier synchronization).
- Đối với `sorted()`, các phần tử phải được thu thập đầy đủ, trộn, sắp xếp và phân chia lại.
- Đối với `limit(n)`, các phần tử phải được xử lý trong khi theo dõi chặt chẽ thứ tự xuất hiện (encounter order) trên nhiều luồng, tạo ra các nút cổ chai tuần tự.

#### Quy Tắc N * Q (The N * Q Rule)
Một quy tắc suy nghiệm tốt để quyết định có nên sử dụng stream song song hay không là $N \times Q > 10,000$, trong đó:
- $N$ là số lượng phần tử dữ liệu.
- $Q$ là chi phí tính toán cho mỗi phần tử.
Nếu $N \times Q$ nhỏ, các stream tuần tự hầu như luôn luôn nhanh hơn.

Kiểm tra thực tế:
- Định nghĩa `Parallel stream` trong một câu.
- Nhận biết `Parallel stream` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `Parallel stream`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `Parallel stream` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Các bộ thu gom: (Collectors:)

Các bộ thu gom (Collectors) là một nhóm các quy tắc liên quan trong Stream API nhóm lại một số chi tiết liên quan.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Cách sử dụng chung các phương thức nhà máy của Collectors
List<String> list = Stream.of("a", "b").collect(Collectors.toList());
```

Kiểm tra thực tế:
- Định nghĩa `Collectors:` trong một câu.
- Nhận biết `Collectors:` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `Collectors:`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `Collectors:` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### toSet

Một `Set` là một collection từ chối các phần tử trùng lặp theo quy tắc so sánh bằng.

Nó quan trọng vì việc chọn sai cấu trúc dữ liệu sẽ làm thay đổi tính chính xác, hiệu suất và hành vi xử lý trùng lặp. Một sự nhầm lẫn phổ biến là ghi nhớ tên lớp mà không biết thứ tự tìm kiếm, quy tắc so sánh bằng hay hành vi lặp.

#### Ví dụ mã nguồn
```java
// Tích lũy vào một Set để loại bỏ các phần tử trùng lặp
Set<String> set = Stream.of("a", "b", "a").collect(Collectors.toSet()); // ["a", "b"]
```

Kiểm tra thực tế:
- Định nghĩa `toSet` trong một câu.
- Nhận biết `toSet` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `toSet`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `toSet` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### toMap

Một `Map` lưu trữ các cặp key-value và truy xuất các giá trị bằng key.

Nó quan trọng vì việc chọn sai cấu trúc dữ liệu sẽ làm thay đổi tính chính xác, hiệu suất và hành vi xử lý trùng lặp. Một sự nhầm lẫn phổ biến là ghi nhớ tên lớp mà không biết thứ tự tìm kiếm, quy tắc so sánh bằng hay hành vi lặp.

#### Ví dụ mã nguồn
```java
// Tích lũy vào một Map (yêu cầu hàm ánh xạ key, hàm ánh xạ value và hàm merge tùy chọn)
Map<Integer, String> map = Stream.of("apple", "banana")
                                 .collect(Collectors.toMap(
                                     String::length, 
                                     s -> s, 
                                     (existing, replacement) -> existing
                                 ));
```

Kiểm tra thực tế:
- Định nghĩa `toMap` trong một câu.
- Nhận biết `toMap` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `toMap`.

Ví dụ nhỏ hoặc mô hình tư duy:
- `Map<String, Integer> scores = new HashMap<>();` ánh xạ các key tới value.

### joining

`joining` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Nối các phần tử chuỗi bằng ký tự phân tách
String joined = Stream.of("a", "b", "c")
                      .collect(Collectors.joining(", ")); // "a, b, c"
```

Kiểm tra thực tế:
- Định nghĩa `joining` trong một câu.
- Nhận biết `joining` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `joining`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `joining` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### groupingBy

`groupingBy` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Phân nhóm các phần tử theo một hàm phân loại (classifier)
Map<Integer, List<String>> groups = Stream.of("a", "bb", "c", "ddd")
                                          .collect(Collectors.groupingBy(String::length));
// Kết quả: {1=["a", "c"], 2=["bb"], 3=["ddd"]}
```

## Các Lỗi Thường Gặp (Common Mistakes)

### 1. Ngoại lệ trùng lặp khóa (Duplicate Keys Exception) trong Collectors.toMap()
Nếu các khóa được trả về bởi hàm ánh xạ khóa không phải là duy nhất, `Collectors.toMap` sẽ ném ra ngoại lệ `IllegalStateException` trừ khi một hàm merge được cung cấp.
```java
// Ném ra IllegalStateException: Duplicate key 5
Stream.of("apple", "peach")
      .collect(Collectors.toMap(String::length, s -> s)); 

// Cách sửa đúng bằng cách thêm hàm merge:
Map<Integer, String> map = Stream.of("apple", "peach")
      .collect(Collectors.toMap(
          String::length, 
          s -> s, 
          (existing, replacement) -> existing // Giữ lại phần tử đầu tiên tìm thấy
      ));
```

### 2. Sửa đổi trạng thái chia sẻ từ các thao tác stream
Stream song song thực thi các thao tác trên nhiều luồng. Việc sửa đổi một collection chia sẻ như `ArrayList` từ bên trong các thao tác stream sẽ dẫn đến tình trạng tranh chấp luồng (race conditions).
```java
List<Integer> list = new ArrayList<>();
List.of(1, 2, 3, 4).parallelStream().forEach(list::add); // NGUY HIỂM: Tranh chấp luồng!
```

Kiểm tra thực tế:
- Định nghĩa `groupingBy` trong một câu.
- Nhận biết `groupingBy` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `groupingBy`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `groupingBy` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Các Câu Hỏi Ôn Tập Thường Gặp (Common Review Prompts)

- Những khái niệm nào ở đây là quy tắc tại thời điểm biên dịch (compile-time)?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy (runtime)?
- Những khái niệm nào ở đây có khả năng là bẫy khi phỏng vấn?

## Tại Sao Stream Được Đánh Giá Lười Biếng (Why Streams Are Lazily Evaluated)

Các Stream trong Java đạt được tính chất lười biếng bằng cách tách biệt việc xây dựng đường ống với quá trình xử lý phần tử. Khi bạn gọi các thao tác trung gian như `filter()` hoặc `map()`, JVM hoàn toàn không duyệt qua nguồn dữ liệu hoặc thực thi bất kỳ biểu thức lambda nào. Thay vào đó, mỗi thao tác trung gian trả về một giai đoạn Stream mới được biểu diễn bởi một phân lớp của `AbstractPipeline`, tự nối thêm vào để tạo thành một danh sách liên kết của các giai đoạn stream. Một interface `Sink` sẽ tạo điều kiện cho việc thực thi thực sự; mỗi giai đoạn bọc `Sink` hạ nguồn bên trong triển khai `Sink` của riêng nó, thiết lập một cấu trúc gọi lại (callback) dạng chuỗi liên kết. Chỉ khi một thao tác cuối được gọi thì đường ống mới bắt đầu duyệt qua nguồn dữ liệu, đẩy từng phần tử một xuống chuỗi `Sink` lồng nhau. Quá trình đánh giá một lượt (single-pass) này cho phép các thao tác ngắn mạch kết thúc sớm và ngăn chặn việc tạo ra các collection trung gian tiêu tốn tài nguyên.

### Mô hình tư duy (Mental Model)
```
[Nguồn] -> AbstractPipeline (filter) -> AbstractPipeline (map) -> Thao tác cuối (collect)
                  │                            │
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
                System.out.println("Đang lọc (Filtering): " + s);
                return s.length() > 4;
            })
            .map(s -> {
                System.out.println("Đang ánh xạ (Mapping): " + s);
                return s.toUpperCase();
            })
            .limit(1)
            .toList();
        
        System.out.println("Kết quả: " + result);
        // Kết quả bảng điều khiển:
        // Đang lọc (Filtering): apple
        // Đang ánh xạ (Mapping): apple
        // Kết quả: [APPLE]
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Các thao tác trung gian được đăng ký
  → Đường ống được xây dựng dưới dạng các nút AbstractPipeline liên kết chuỗi
  → Thao tác cuối được gọi
  → Các phần tử nguồn được kéo qua chuỗi Sink lần lượt từng phần tử một
  → Ngắn mạch (limit) kích hoạt kết thúc sớm
  → CPU thực hiện khối lượng công việc tối thiểu.
```


## Tại Sao Parallel Stream Không Phải Là Giải Pháp Mặc Định (Why Parallel Streams Are Not a Default Solution)

Stream song song phân chia dữ liệu nguồn của stream bằng cách sử dụng một `Spliterator` và gửi các tác vụ đến `ForkJoinPool.commonPool()` dùng chung. Bởi vì pool này được chia sẻ trên toàn bộ hệ thống lớp nạp (classloader) của JVM, các hoạt động chạy lâu, tốn nhiều CPU hoặc gây tắc nghẽn (blocking) trong một stream sẽ làm nghẽn tài nguyên luồng của các phần khác trong ứng dụng. Hiệu quả của việc phân tách dữ liệu cũng phụ thuộc rất nhiều vào cấu trúc của nguồn dữ liệu; một mảng hoặc `ArrayList` có thể được phân tách trong thời gian $O(1)$ bằng cách chia các phạm vi chỉ mục, trong khi một `LinkedList` yêu cầu duyệt tuần tự qua $O(N)$ phần tử để tìm điểm phân tách, làm mất đi mọi lợi ích về mặt hiệu năng. Hơn nữa, việc điều phối, chuyển đổi ngữ cảnh luồng và gộp các kết quả giữa các luồng xử lý phát sinh chi phí quản lý JVM đáng kể. Do đó, đối với các tập dữ liệu nhỏ hoặc các tác vụ bị giới hạn bởi I/O (I/O-bound tasks), một stream song song có thể chạy chậm hơn đáng kể so với phiên bản tuần tự tương đương của nó.

### Mô hình tư duy (Mental Model)
```
Phân tách ArrayList (Phân chia dựa trên chỉ mục O(1)):
[ Phần tử 0 - 3 ] ---> Luồng 1
[ Phần tử 4 - 7 ] ---> Luồng 2

Phân tách LinkedList (Duyệt tuần tự O(N)):
[Head] -> [Node] -> [Node] -> [Node] -> [Node] -> [Node] -> [Node] -> [Tail]
 (Phải duyệt qua từng liên kết một để tìm điểm phân tách)
```

### Ví dụ mã nguồn
```java
import java.util.List;
import java.util.stream.LongStream;

public class ParallelStreamDemo {
    public static void main(String[] args) {
        // Xử lý stream song song sử dụng ForkJoinPool chung
        long sum = LongStream.rangeClosed(1, 1_000_000)
                             .parallel()
                             .filter(n -> n % 2 == 0)
                             .sum();
        
        System.out.println("Tổng: " + sum);
        // Kết quả bảng điều khiển:
        // Tổng: 250000500000
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Nguồn dữ liệu (ví dụ: LinkedList) khó phân tách
  → Spliterator thực hiện duyệt tuần tự $O(N)$ để phân chia công việc
  → Chi phí phân chia và điều phối luồng cao
  → Luồng thực thi trong pool chung `ForkJoinPool.commonPool()`
  → Các hoạt động gây nghẽn làm đói tài nguyên luồng
  → Hiệu năng giảm so với stream tuần tự.
```

