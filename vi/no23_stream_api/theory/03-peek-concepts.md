# API Dòng Chảy (Stream API) - Phần 3

## Ghi Chú Chi Tiết

### peek()

peek() nhận một Consumer và thực thi nó trên mỗi phần tử mà không thay đổi stream — giống như đặt camera giám sát trên đường ống dữ liệu.

#### Ví dụ Code
```java
// Kiểm tra các phần tử khi chúng đi qua đường ống (để debug)
List<String> result = Stream.of("one", "two", "three")
                            .filter(s -> s.length() > 3)
                            .peek(s -> System.out.println("Filtered: " + s))
                            .map(String::toUpperCase)
                            .peek(s -> System.out.println("Mapped: " + s))
                            .collect(Collectors.toList());
```

### peek() vs forEach()

Cả hai nhận Consumer nhưng peek() là intermediate (lazy, cần terminal operation mới chạy) còn forEach() là terminal (kích hoạt pipeline ngay).

#### Ví dụ Code
```java
// Thực hiện hành động trên từng phần tử
Stream.of("a", "b").forEach(System.out::print); // Output: ab
```

### peek() và Lazy Evaluation

Vì peek() là thao tác trung gian, nó chỉ thực thi khi terminal operation kéo dữ liệu qua pipeline.

### peek() không nên thay đổi trạng thái (should not mutate)

Theo API spec, peek() chỉ dành cho quan sát (debug/logging), không phải để thay đổi phần tử hay trạng thái bên ngoài.

### peek() trong Parallel Streams

Trong parallel stream, nhiều thread cùng xử lý nên thứ tự peek() in ra không đảm bảo, nhưng kết quả cuối vẫn giữ đúng encounter order.

### peek() có thể bị bỏ qua (Java 9+)

Từ Java 9, JVM có thể tối ưu hóa bằng cách bỏ qua toàn bộ pipeline nếu terminal operation (như count()) có thể lấy kết quả trực tiếp từ source metadata.

### limit

#### Ví dụ Code
```java
// Giới hạn dòng chảy ở một kích thước tối đa
Stream.of(1, 2, 3, 4, 5)
      .limit(3)
      .forEach(System.out::print); // Prints: 123
```

### skip

#### Ví dụ Code
```java
// Bỏ qua N phần tử đầu tiên
Stream.of(1, 2, 3, 4, 5)
      .skip(2)
      .forEach(System.out::print); // Prints: 345
```

### Các thao tác kết thúc (Terminal operations)

#### Ví dụ Code
```java
// Các thao tác kết thúc thực thi đường ống và đóng dòng chảy
long count = Stream.of(1, 2, 3).count();
```

### collect

#### Ví dụ Code
```java
// Tích lũy các phần tử vào một vùng chứa có thể thay đổi
List<String> list = Stream.of("a", "b").collect(Collectors.toList());
```

### toList

#### Ví dụ Code
```java
// toList() (Java 16+) trực tiếp trả về một List bất biến (unmodifiable)
List<String> unmodifiableList = Stream.of("a", "b").toList();
```

### count

#### Ví dụ Code
```java
// Đếm các phần tử
long total = Stream.of(1, 2, 3).count(); // 3
```

---

## Các lỗi thường gặp

### 1. Giả định rằng peek() luôn thực thi
Vì các stream hoạt động theo cơ chế lười (lazy), các thao tác trung gian như `peek()` sẽ không chạy trừ khi một thao tác kết thúc (terminal operation) được gọi. Ngoài ra, các tối ưu hóa của JDK có thể bỏ qua `peek()` nếu thao tác kết thúc (như `count()`) có thể xác định được kết quả mà không cần duyệt qua các phần tử.
```java
// peek() KHÔNG thực thi ở đây (không có thao tác kết thúc)
Stream.of("a", "b").peek(System.out::println); 

// peek() có thể bị tối ưu hóa bỏ qua trong Java 9+ vì map/peek không ảnh hưởng đến kích thước stream
long size = Stream.of("a", "b")
                  .peek(System.out::println) // Có thể không in ra gì!
                  .count();
```

### 2. Sửa đổi phần tử bên trong forEach trên các dòng chảy song song (parallel stream)
Việc sửa đổi trạng thái chung không an toàn luồng (non-thread-safe) từ phương thức `forEach` của một parallel stream sẽ dẫn đến tình trạng tranh chấp (race condition).
```java
List<Integer> list = new ArrayList<>(); // Không an toàn luồng
List.of(1, 2, 3, 4).parallelStream().forEach(list::add); // Race condition!
```

---

## Tại sao không nên sử dụng peek() để thay đổi trạng thái

Đặc tả API của `Stream.peek()` nêu rõ mục đích chính của nó là hỗ trợ gỡ lỗi, cho phép bạn quan sát các phần tử khi chúng chảy qua một điểm cụ thể trong đường ống. Việc sử dụng `peek()` để thay đổi trạng thái (mutate) của các phần tử hoặc các biến bên ngoài bị khuyến cáo mạnh mẽ là không nên và rất dễ phát sinh lỗi, do triển khai của stream có quyền tự do tối ưu hóa để bỏ qua các bước trung gian của đường ống. Ví dụ, nếu sử dụng một thao tác kết thúc như `count()`, các phiên bản JDK hiện đại (từ Java 9 trở đi) có thể xác định số lượng phần tử trực tiếp từ phần mô tả nguồn stream mà không cần duyệt qua đường ống, có nghĩa là `peek()` sẽ không bao giờ được thực thi. Hơn nữa, trong các đường ống xử lý song song, việc kích hoạt các tác dụng phụ (Side effect) bên trong `peek()` sẽ gây ra tranh chấp dữ liệu (Data race) và vi phạm an toàn luồng trừ khi áp dụng các cơ chế đồng bộ hóa phức tạp. Thay đổi trạng thái bên trong `peek()` phá vỡ mục tiêu thiết kế cốt lõi của các đường ống dòng chảy chức năng, vốn phải thuần túy, không có tác dụng phụ và có tính xác định (Deterministic).

### Mô hình Tư duy
```
Nguồn Stream (đã biết kích thước) -> peek(thay đổi trạng thái) -> count()
                                  |
                                  v
                    [ JVM Tối ưu hóa đếm số lượng ]
    (Kích thước nguồn được truy vấn trực tiếp; bỏ qua việc duyệt đường ống)
                                  |
                                  v
                    peek() KHÔNG BAO GIỜ được thực thi!
```

### Ví dụ Thực Tế

```java
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PeekMutationDemo {
    public static void main(String[] args) {
        List<String> mutatedList = new ArrayList<>();
        
        // NGUY HIỂM: Sử dụng peek để thay đổi trạng thái bên ngoài
        long totalCount = Stream.of("a", "b", "c")
                                .peek(mutatedList::add)
                                .count();
        
        System.out.println("Total Count: " + totalCount);
        System.out.println("Mutated List Size: " + mutatedList.size());
        // Console Output (Java 9+):
        // Total Count: 3
        // Mutated List Size: 0
    }
}
```

### Chuỗi Nguyên nhân - Kết quả
Thay đổi trạng thái trong peek() &rarr; Quá trình thực thi phụ thuộc vào việc duyệt đường ống &rarr; Thao tác kết thúc được tối ưu hóa (ví dụ: count() truy vấn trực tiếp nguồn) &rarr; Việc duyệt đường ống bị bỏ qua &rarr; Logic thay đổi trạng thái không bao giờ chạy &rarr; Trạng thái bên ngoài không nhất quán và phát sinh lỗi logic.
