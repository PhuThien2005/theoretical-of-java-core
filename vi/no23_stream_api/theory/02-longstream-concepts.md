# Stream API - Phần 2

## Ghi Chú Chi Tiết

### LongStream

LongStream là một phiên bản stream chuyên biệt cho kiểu nguyên thủy `long`, giúp tránh chi phí boxing/unboxing khi làm việc với dữ liệu số nguyên lớn.

#### Ví Dụ Mã Nguồn
```java
// Primitive LongStream to avoid boxing overhead
LongStream longStream = LongStream.of(100L, 200L, 300L);
LongStream range = LongStream.rangeClosed(1, 100); // 1 to 100 inclusive
```

### DoubleStream

DoubleStream là phiên bản stream chuyên biệt dành cho kiểu nguyên thủy `double`. Tương tự như LongStream, nó giúp cải thiện hiệu suất bằng cách loại bỏ chi phí boxing/unboxing.

#### Ví Dụ Mã Nguồn
```java
// Primitive DoubleStream to avoid boxing overhead
DoubleStream doubleStream = DoubleStream.of(1.5, 2.5, 3.5);
DoubleSummaryStatistics stats = doubleStream.summaryStatistics();
System.out.println("Average: " + stats.getAverage());
```

### Các thao tác trung gian (Intermediate operations)

Các thao tác trung gian (như filter, map) luôn trả về một stream mới và được thực thi theo cơ chế lười biếng (lazy evaluation). Chúng chỉ thực sự chạy khi có một thao tác kết thúc (terminal operation) được gọi.

#### Ví Dụ Mã Nguồn
```java
// Intermediate operations are chained and executed lazily
Stream.of("a", "b", "c")
      .filter(s -> !s.isEmpty())
      .map(String::toUpperCase); // Returns a new Stream (not executed yet)
```

### Bộ lọc (filter)

Thao tác `filter` nhận vào một Predicate (điều kiện) và trả về một stream mới chỉ chứa các phần tử thỏa mãn điều kiện đó.

#### Ví Dụ Mã Nguồn
```java
// Retain elements that match the given predicate
Stream.of("apple", "banana", "kiwi")
      .filter(s -> s.length() > 4)
      .forEach(System.out::println); // Prints: apple, banana
```

### Ánh xạ (map)

Thao tác `map` biến đổi từng phần tử của stream bằng cách áp dụng một Function, chuyển đổi phần tử từ kiểu này sang kiểu khác một cách độc lập.

#### Ví Dụ Mã Nguồn
```java
// Transform each element 1-to-1
Stream.of("apple", "banana")
      .map(String::toUpperCase)
      .forEach(System.out::println); // Prints: APPLE, BANANA
```

### Ánh xạ phẳng (flatMap)

Thao tác `flatMap` được sử dụng để 'làm phẳng' (flatten) các cấu trúc lồng nhau, chẳng hạn như chuyển đổi một luồng chứa các danh sách thành một luồng chứa tất cả các phần tử của các danh sách đó.

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

### Loại bỏ trùng lặp (distinct)

Thao tác `distinct` loại bỏ các phần tử trùng lặp trong stream. Cơ chế so sánh dựa vào phương thức `equals()` của các đối tượng.

#### Ví Dụ Mã Nguồn
```java
// Remove duplicates based on Object.equals()
Stream.of(1, 2, 2, 3, 1)
      .distinct()
      .forEach(System.out::print); // Prints: 123
```

### Sắp xếp (sorted)

Thao tác `sorted` sắp xếp các phần tử trong stream theo thứ tự tự nhiên (natural order) hoặc theo một Comparator được cung cấp.

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
