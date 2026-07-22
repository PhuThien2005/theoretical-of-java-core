# Thuật ngữ về Stream API (Stream API Terms)

Sử dụng tài liệu này khi một từ khóa trong phần lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều có định nghĩa, tầm quan trọng, hiểu lầm thường gặp và một ví dụ nhỏ.

## đường ống luồng (stream pipeline)

Một đường ống luồng (stream pipeline) là một chuỗi các bước xử lý bao gồm một nguồn dữ liệu (chẳng hạn như một bộ sưu tập, một mảng, một hàm tạo, hoặc một kênh I/O), theo sau bởi không hoặc nhiều thao tác trung gian, và duy nhất một thao tác kết thúc. Nó hoạt động như một trình xây dựng truy vấn mang tính khai báo thay vì một vòng lặp chủ động.

* **Tầm quan trọng**: Nó cho phép các nhà phát triển thể hiện logic biến đổi dữ liệu phức tạp một cách dễ đọc và mang tính khai báo, bảo vệ họ khỏi các cơ chế lặp cấp thấp. Bằng cách tách biệt việc xây dựng đường ống với việc thực thi, nó tối ưu hóa các bước tính toán và tránh lưu trữ tạm thời các cấu trúc trung gian.
* **Hiểu lầm thường gặp**: Các nhà phát triển thường nghĩ rằng một đường ống được thực thi từng bước một cho toàn bộ bộ sưu tập (ví dụ: thực thi bộ lọc filter trên tất cả các phần tử, sau đó ánh xạ map tất cả các phần tử). Trên thực tế, các phần tử chảy dọc theo đường ống từng phần tử một chỉ khi được kéo bởi thao tác kết thúc.
* **Ví dụ nhỏ**:
  ```java
  // A pipeline consisting of: List source -> filter -> map -> collect (terminal)
  List<String> activeNames = users.stream()
                                  .filter(User::isActive)
                                  .map(User::getName)
                                  .collect(Collectors.toList());
  ```

## thao tác trung gian (intermediate operation)

Một thao tác trung gian (intermediate operation) là một thao tác luồng (như `filter`, `map`, `flatMap`, `sorted`, hoặc `distinct`) biến đổi một luồng này thành một luồng khác. Các thao tác này luôn luôn lười biếng (lazy) và không thực hiện bất kỳ xử lý nào trên các phần tử nguồn.

* **Tầm quan trọng**: Chúng cho phép đường ống được xây dựng tăng dần, kích hoạt tính năng đánh giá lười biếng và tối ưu hóa. Nếu không có chúng, mọi giai đoạn biến đổi sẽ yêu cầu các bộ sưu tập trung gian, dẫn đến việc cấp phát bộ nhớ heap cao và các vòng lặp dư thừa.
* **Hiểu lầm thường gặp**: Nghĩ rằng việc gọi một thao tác trung gian sẽ lập tức xử lý các phần tử. Nếu không có thao tác kết thúc ở cuối đường ống, các thao tác trung gian hoàn toàn không chạy.
* **Ví dụ nhỏ**:
  ```java
  // No output is printed because filter is an intermediate operation and the stream is not terminated!
  Stream.of("apple", "banana")
        .filter(s -> {
            System.out.println(s);
            return true;
        });
  ```

## thao tác kết thúc (terminal operation)

Một thao tác kết thúc (terminal operation) là thao tác cuối cùng trong một đường ống luồng (chẳng hạn như `collect`, `forEach`, `reduce`, `count`, `min`, `max`, `anyMatch`, hoặc `toList`) kích hoạt quá trình duyệt qua đường ống và tạo ra kết quả (hoặc tác dụng phụ).

* **Tầm quan trọng**: Nó đóng đường ống luồng, bắt đầu quá trình duyệt qua phần tử, đẩy các phần tử qua các thao tác đã đăng ký, và thu thập đầu ra cuối cùng. Khi một thao tác kết thúc được gọi, luồng được coi là đã tiêu thụ và không thể tái sử dụng.
* **Hiểu lầm thường gặp**: Cố gắng gọi nhiều thao tác kết thúc trên cùng một thực thể luồng. Làm như vậy sẽ ném ra ngoại lệ `IllegalStateException` vì luồng đã bị đóng sau thao tác kết thúc đầu tiên.
* **Ví dụ nhỏ**:
  ```java
  Stream<String> stream = Stream.of("a", "b");
  long count = stream.count(); // Terminal operation executes successfully
  // stream.forEach(System.out::println); // Throws IllegalStateException!
  ```

## đánh giá lười biếng (lazy evaluation)

Đánh giá lười biếng (lazy evaluation) là một chiến lược tối ưu hóa của trình biên dịch/thời gian chạy trong đó tính toán được trì hoãn cho đến khi kết quả của nó thực sự được yêu cầu bởi một thao tác kết thúc.

* **Tầm quan trọng**: Nó giảm thiểu chu kỳ CPU và dung lượng bộ nhớ bằng cách ngăn chặn các tính toán không cần thiết. Nó cũng cho phép các luồng xử lý các nguồn vô hạn (như các hàm tạo chuỗi) vì các phần tử chỉ được đánh giá theo nhu cầu.
* **Hiểu lầm thường gặp**: Tin rằng đánh giá lười biếng giống hệt như thực thi bất đồng bộ. Các thao tác lười biếng hoàn toàn đồng bộ nhưng được trì hoãn; chúng thực thi trên luồng gọi trừ khi được song song hóa.
* **Ví dụ nhỏ**:
  ```java
  // Elements are only processed up to the first match because of lazy evaluation combined with limit
  Stream.iterate(1, i -> i + 1)
        .filter(i -> i % 2 == 0)
        .limit(1)
        .forEach(System.out::println); // Prints: 2
  ```

## ngắt mạch (short-circuiting)

Một thao tác ngắt mạch (short-circuiting operation) là một thao tác luồng có thể tạo ra kết quả hữu hạn hoặc chấm dứt thực thi ngay cả khi gặp phải đầu vào vô hạn. Các ví dụ bao gồm các thao tác trung gian như `limit()` và các thao tác kết thúc như `findFirst()`, `anyMatch()`, `allMatch()`, và `noneMatch()`.

* **Tầm quan trọng**: Nó cho phép tối ưu hóa hiệu năng mạnh mẽ bằng cách tạm dừng xử lý luồng ngay lập tức sau khi đáp ứng điều kiện phù hợp hoặc giới hạn kích thước, tránh công việc CPU vô ích cho phần còn lại của tập dữ liệu.
* **Hiểu lầm thường gặp**: Nghĩ rằng các thao tác ngắt mạch luôn xử lý phần tử đầu tiên. Trong các luồng song song, các thao tác ngắt mạch có thể đánh giá nhiều phần tử đồng thời và có thể chấm dứt dựa trên kết quả của luồng nhanh nhất.
* **Ví dụ nhỏ**:
  ```java
  // Stops as soon as any element matches, rather than checking the rest of the stream
  boolean hasMatch = Stream.of("apple", "banana", "cherry")
                           .anyMatch(s -> {
                               System.out.println("Checking: " + s);
                               return s.startsWith("b");
                           }); // Prints "Checking: apple", "Checking: banana", then halts.
  ```

## bộ thu thập (collector)

Một bộ thu thập (collector) là một triển khai của giao diện `Collector` (thường thu được thông qua lớp tiện ích `Collectors`) được sử dụng làm đối số cho thao tác kết thúc `collect()` để tích lũy các phần tử luồng vào một vùng chứa có thể thay đổi (mutable container).

* **Tầm quan trọng**: Nó định nghĩa cách các phần tử luồng sẽ được tổng hợp vào các cấu trúc dữ liệu (như List, Set, hoặc Map) hoặc tóm tắt (nối chuỗi, tính tổng, gom nhóm, phân vùng). Nó xử lý việc khởi tạo vùng chứa, tích lũy phần tử, hợp nhất song song, và biến đổi cuối cùng.
* **Hiểu lầm thường gặp**: Nhầm lẫn `Collectors.toList()` (trả về một bộ bao bọc ArrayList có thể thay đổi) với `Stream.toList()` (từ Java 16+, trả về một List không thể sửa đổi và nhanh hơn vì nó tránh được chi phí của bộ thu thập).
* **Ví dụ nhỏ**:
  ```java
  // Grouping words by length into a Map using a collector
  Map<Integer, List<String>> groups = Stream.of("a", "bb", "c")
      .collect(Collectors.groupingBy(String::length)); // {1=[a, c], 2=[bb]}
  ```

## luồng song song (parallel stream)

Một luồng song song (parallel stream) là một chế độ thực thi luồng chia nhỏ đường ống luồng thành nhiều tác vụ, thực thi chúng đồng thời bằng cách sử dụng `ForkJoinPool.commonPool()` chung của JVM.

* **Tầm quan trọng**: Nó cho phép thực thi đa luồng một cách dễ dàng và mang tính khai báo để tận dụng CPU nhiều lõi cho các tập dữ liệu lớn, có tiềm năng giảm thời gian thực thi cho các tác vụ tốn nhiều tài nguyên CPU.
* **Hiểu lầm thường gặp**: Giả định rằng các luồng song song sẽ luôn tăng tốc độ thực thi. Đối với các tập dữ liệu nhỏ, các nguồn không thể tách phân tách (như LinkedList), hoặc các tác vụ bị giới hạn bởi I/O, các luồng song song thực tế có thể chạy chậm hơn do chi phí quản lý luồng và sự cạn kiệt tài nguyên của bể luồng (pool starvation).
* **Ví dụ nhỏ**:
  ```java
  // Computes sum concurrently on multiple threads
  long sum = LongStream.rangeClosed(1, 100_000)
                       .parallel()
                       .sum();
  ```
