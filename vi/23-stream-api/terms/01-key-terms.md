# Các Thuật Ngữ Về Stream API (Stream API Terms)

Sử dụng tài liệu này khi một từ ngữ trong phần lý thuyết có vẻ quá ngắn gọn. Mỗi thuật ngữ đều có định nghĩa, tầm quan trọng, điểm dễ nhầm lẫn và một ví dụ nhỏ đi kèm.

## đường ống stream (stream pipeline)

Một đường ống stream là một chuỗi các bước xử lý bao gồm một nguồn (chẳng hạn như một collection, một mảng, một hàm tạo, hoặc một kênh I/O), theo sau bởi không hoặc nhiều thao tác trung gian, và đúng một thao tác cuối. Nó hoạt động như một bộ dựng truy vấn khai báo (declarative query builder) chứ không phải là một vòng lặp chủ động.

Tại sao nó quan trọng: Nó cho phép các lập trình viên thể hiện logic chuyển đổi dữ liệu phức tạp một cách dễ đọc và khai báo, bảo vệ họ khỏi các cơ chế lặp cấp thấp. Bằng cách tách biệt việc xây dựng đường ống với việc thực thi, nó tối ưu hóa các bước tính toán và tránh lưu trữ tạm thời các cấu trúc trung gian.

Điểm dễ nhầm lẫn: Các lập trình viên thường nghĩ rằng một đường ống được thực thi từng bước một cho toàn bộ collection (ví dụ: thực thi filter trên tất cả các phần tử, sau đó ánh xạ tất cả các phần tử). Trên thực tế, các phần tử chạy dọc theo đường ống lần lượt từng cái một chỉ khi được kéo bởi thao tác cuối.

Ví dụ nhỏ:
```java
// Một đường ống bao gồm: Nguồn List -> filter -> map -> collect (thao tác cuối)
List<String> activeNames = users.stream()
                                .filter(User::isActive)
                                .map(User::getName)
                                .collect(Collectors.toList());
```

## thao tác trung gian (intermediate operation)

Một thao tác trung gian là một thao tác trên stream (như `filter`, `map`, `flatMap`, `sorted`, hoặc `distinct`) chuyển đổi một stream thành một stream khác. Các thao tác này luôn luôn lười biếng (lazy) và không thực hiện bất kỳ xử lý nào trên các phần tử nguồn.

Tại sao nó quan trọng: Chúng cho phép đường ống được xây dựng dần dần, kích hoạt việc đánh giá lười biếng và tối ưu hóa. Nếu không có chúng, mỗi giai đoạn chuyển đổi sẽ yêu cầu các collection trung gian, dẫn đến việc cấp phát heap cao và các vòng lặp dư thừa.

Điểm dễ nhầm lẫn: Nghĩ rằng việc gọi một thao tác trung gian sẽ xử lý các phần tử ngay lập tức. Nếu không có thao tác cuối ở cuối đường ống, các thao tác trung gian sẽ hoàn toàn không chạy.

Ví dụ nhỏ:
```java
// Không có kết quả nào được in ra vì filter là một thao tác trung gian và stream chưa được kết thúc!
Stream.of("apple", "banana")
      .filter(s -> {
          System.out.println(s);
          return true;
      });
```

## thao tác cuối (terminal operation)

Một thao tác cuối là thao tác cuối cùng trong một đường ống stream (như `collect`, `forEach`, `reduce`, `count`, `min`, `max`, `anyMatch`, hoặc `toList`) để kích hoạt việc duyệt qua đường ống và tạo ra một kết quả (hoặc tác dụng phụ).

Tại sao nó quan trọng: Nó đóng đường ống stream, bắt đầu duyệt phần tử, đẩy các phần tử qua các thao tác đã đăng ký và thu thập kết quả đầu ra cuối cùng. Một khi thao tác cuối được gọi, stream được coi là đã tiêu thụ và không thể tái sử dụng.

Điểm dễ nhầm lẫn: Cố gắng gọi nhiều thao tác cuối trên cùng một thực thể stream. Làm như vậy sẽ ném ra một `IllegalStateException` vì stream bị đóng sau thao tác cuối đầu tiên.

Ví dụ nhỏ:
```java
Stream<String> stream = Stream.of("a", "b");
long count = stream.count(); // Thao tác cuối thực thi thành công
// stream.forEach(System.out::println); // Ném ra IllegalStateException!
```

## đánh giá lười biếng (lazy evaluation)

Đánh giá lười biếng là một chiến lược tối ưu hóa của trình biên dịch/thời gian chạy, trong đó việc tính toán được trì hoãn cho đến khi kết quả của nó thực sự được yêu cầu bởi một thao tác cuối.

Tại sao nó quan trọng: Nó giảm thiểu chu kỳ CPU và dung lượng bộ nhớ bằng cách ngăn chặn các tính toán không cần thiết. Nó cũng cho phép stream xử lý các nguồn vô hạn (như các bộ tạo chuỗi) vì các phần tử chỉ được đánh giá khi có yêu cầu.

Điểm dễ nhầm lẫn: Tin rằng đánh giá lười biếng giống hệt với thực thi bất đồng bộ. Các thao tác lười biếng hoàn toàn là đồng bộ nhưng được trì hoãn; chúng thực thi trên luồng (Thread) gọi trừ khi được song song hóa.

Ví dụ nhỏ:
```java
// Các phần tử chỉ được xử lý cho đến khi khớp phần tử đầu tiên nhờ đánh giá lười biếng kết hợp với limit
Stream.iterate(1, i -> i + 1)
      .filter(i -> i % 2 == 0)
      .limit(1)
      .forEach(System.out::println); // In ra: 2
```

## ngắn mạch (short-circuiting)

Một thao tác ngắn mạch là một thao tác trên stream có thể tạo ra một kết quả hữu hạn hoặc kết thúc thực thi ngay cả khi nhận vào một nguồn vô hạn. Các ví dụ bao gồm các thao tác trung gian như `limit()` và các thao tác cuối như `findFirst()`, `anyMatch()`, `allMatch()`, và `noneMatch()`.

Tại sao nó quan trọng: Nó cho phép tối ưu hóa hiệu năng cực lớn bằng cách dừng xử lý stream ngay lập tức sau khi đạt điều kiện khớp hoặc giới hạn kích thước, tránh việc lãng phí CPU cho phần còn lại của tập dữ liệu.

Điểm dễ nhầm lẫn: Nghĩ rằng các thao tác ngắn mạch luôn luôn xử lý phần tử đầu tiên. Trong stream song song, các thao tác ngắn mạch có thể đánh giá nhiều phần tử đồng thời và có thể kết thúc dựa trên kết quả của luồng chạy nhanh nhất.

Ví dụ nhỏ:
```java
// Dừng lại ngay khi có bất kỳ phần tử nào khớp, thay vì kiểm tra phần còn lại của stream
boolean hasMatch = Stream.of("apple", "banana", "cherry")
                         .anyMatch(s -> {
                             System.out.println("Đang kiểm tra: " + s);
                             return s.startsWith("b");
                         }); // In "Đang kiểm tra: apple", "Đang kiểm tra: banana", sau đó dừng.
```

## bộ thu gom (collector)

Một bộ thu gom là một triển khai của interface `Collector` (thường thu được thông qua lớp tiện ích `Collectors`) được sử dụng làm đối số cho thao tác cuối `collect()` để tích lũy các phần tử stream vào một container khả biến.

Tại sao nó quan trọng: Nó định nghĩa cách các phần tử stream được tổng hợp vào các cấu trúc dữ liệu (như List, Set, hoặc Map) hoặc được tổng hợp thông số (nối chuỗi, tính tổng, phân nhóm, phân vùng). Nó xử lý việc khởi tạo container, tích lũy phần tử, trộn song song và chuyển đổi cuối cùng.

Điểm dễ nhầm lẫn: Nhầm lẫn giữa `Collectors.toList()` (trả về một ArrayList khả biến bọc ngoài) với `Stream.toList()` (Java 16+, trả về một List bất biến và nhanh hơn vì tránh được chi phí của bộ thu gom).

Ví dụ nhỏ:
```java
// Phân nhóm các từ theo độ dài vào một Map sử dụng một bộ thu gom
Map<Integer, List<String>> groups = Stream.of("a", "bb", "c")
    .collect(Collectors.groupingBy(String::length)); // {1=[a, c], 2=[bb]}
```

## stream song song (parallel stream)

Một stream song song là một chế độ thực thi stream phân chia đường ống stream thành nhiều tác vụ, thực thi chúng đồng thời bằng cách sử dụng chung `ForkJoinPool.commonPool()` dùng chung của JVM.

Tại sao nó quan trọng: Nó cho phép thực thi đa luồng dễ dàng, khai báo để tận dụng các CPU đa nhân cho các tập dữ liệu lớn, có khả năng làm giảm thời gian thực thi cho các tác vụ tính toán chuyên sâu (CPU-intensive).

Điểm dễ nhầm lẫn: Giả định rằng stream song song luôn luôn tăng tốc thực thi. Đối với các tập dữ liệu nhỏ, các nguồn không thể phân tách (như LinkedList), hoặc các tác vụ bị nghẽn I/O, stream song song thực tế có thể chạy chậm hơn do chi phí quản lý luồng và tình trạng đói tài nguyên luồng trong pool.

Ví dụ nhỏ:
```java
// Tính tổng đồng thời trên nhiều luồng
long sum = LongStream.rangeClosed(1, 100_000)
                     .parallel()
                     .sum();
```
