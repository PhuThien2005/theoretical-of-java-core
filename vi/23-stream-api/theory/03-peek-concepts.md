# API Dòng Chảy (Stream API) - Phần 3

## Mục Tiêu Học Tập

Tài liệu này tập trung vào một phần chuyên sâu của **API Dòng Chảy (Stream API)**. Hãy nghiên cứu từng khái niệm dưới dạng quy tắc thực tế trong Java, không chỉ đơn thuần là lý thuyết từ vựng.

## Tóm Tắt Nội Dung (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `peek` | `peek` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và kịch bản thất bại của nó thay vì chỉ ghi nhớ tên gọi. |
| `limit` | `limit` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và kịch bản thất bại của nó thay vì chỉ ghi nhớ tên gọi. |
| `skip` | `skip` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và kịch bản thất bại của nó thay vì chỉ ghi nhớ tên gọi. |
| `Các thao tác kết thúc:` | Các thao tác kết thúc (Terminal operations) là một nhóm các quy tắc liên quan trong Stream API để gom nhóm nhiều chi tiết kỹ thuật có liên quan. |
| `forEach` | `forEach` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và kịch bản thất bại của nó thay vì chỉ ghi nhớ tên gọi. |
| `collect` | `collect` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và kịch bản thất bại của nó thay vì chỉ ghi nhớ tên gọi. |
| `toList` | Một `List` là một tập hợp có thứ tự có thể chứa các phần tử trùng lặp và hỗ trợ truy cập theo vị trí. |
| `count` | `count` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và kịch bản thất bại của nó thay vì chỉ ghi nhớ tên gọi. |

## Ghi Chú Chi Tiết

### peek

`peek` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và kịch bản thất bại của nó thay vì chỉ ghi nhớ tên gọi.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng thức được cho phép và kịch bản thất bại. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn mô tả thuần túy.

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

Kiểm tra thực tế:
- Định nghĩa `peek` trong một câu.
- Nhận biết `peek` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `peek`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy tự hỏi: `peek` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### limit

`limit` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và kịch bản thất bại của nó thay vì chỉ ghi nhớ tên gọi.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng thức được cho phép và kịch bản thất bại. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn mô tả thuần túy.

#### Ví dụ Code
```java
// Giới hạn dòng chảy ở một kích thước tối đa
Stream.of(1, 2, 3, 4, 5)
      .limit(3)
      .forEach(System.out::print); // Prints: 123
```

Kiểm tra thực tế:
- Định nghĩa `limit` trong một câu.
- Nhận biết `limit` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `limit`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy tự hỏi: `limit` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### skip

`skip` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và kịch bản thất bại của nó thay vì chỉ ghi nhớ tên gọi.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng thức được cho phép và kịch bản thất bại. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn mô tả thuần túy.

#### Ví dụ Code
```java
// Bỏ qua N phần tử đầu tiên
Stream.of(1, 2, 3, 4, 5)
      .skip(2)
      .forEach(System.out::print); // Prints: 345
```

Kiểm tra thực tế:
- Định nghĩa `skip` trong một câu.
- Nhận biết `skip` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `skip`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy tự hỏi: `skip` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Các thao tác kết thúc (Terminal operations)

Các thao tác kết thúc là một nhóm các quy tắc liên quan trong Stream API để gom nhóm nhiều chi tiết kỹ thuật có liên quan.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng thức được cho phép và kịch bản thất bại. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn mô tả thuần túy.

#### Ví dụ Code
```java
// Các thao tác kết thúc thực thi đường ống và đóng dòng chảy
long count = Stream.of(1, 2, 3).count();
```

Kiểm tra thực tế:
- Định nghĩa `Terminal operations:` trong một câu.
- Nhận biết `Terminal operations:` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Terminal operations:`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy tự hỏi: các thao tác kết thúc thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### forEach

`forEach` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và kịch bản thất bại của nó thay vì chỉ ghi nhớ tên gọi.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng thức được cho phép và kịch bản thất bại. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn mô tả thuần túy.

#### Ví dụ Code
```java
// Thực hiện hành động trên từng phần tử
Stream.of("a", "b").forEach(System.out::print); // Output: ab
```

Kiểm tra thực tế:
- Định nghĩa `forEach` trong một câu.
- Nhận biết `forEach` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `forEach`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy tự hỏi: `forEach` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### collect

`collect` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và kịch bản thất bại của nó thay vì chỉ ghi nhớ tên gọi.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng thức được cho phép và kịch bản thất bại. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn mô tả thuần túy.

#### Ví dụ Code
```java
// Tích lũy các phần tử vào một vùng chứa có thể thay đổi
List<String> list = Stream.of("a", "b").collect(Collectors.toList());
```

Kiểm tra thực tế:
- Định nghĩa `collect` trong một câu.
- Nhận biết `collect` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `collect`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy tự hỏi: `collect` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### toList

Một `List` là một tập hợp có thứ tự có thể chứa các phần tử trùng lặp và hỗ trợ truy cập theo vị trí.

Nó quan trọng vì việc chọn sai cấu trúc dữ liệu sẽ ảnh hưởng đến tính đúng đắn, hiệu năng và hành vi xử lý trùng lặp. Một hiểu lầm phổ biến là ghi nhớ tên lớp mà không biết thứ tự tìm kiếm, quy tắc bằng nhau, hoặc hành vi lặp.

#### Ví dụ Code
```java
// toList() (Java 16+) trực tiếp trả về một List bất biến (unmodifiable)
List<String> unmodifiableList = Stream.of("a", "b").toList();
```

Kiểm tra thực tế:
- Định nghĩa `toList` trong một câu.
- Nhận biết `toList` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `toList`.

Ví dụ nhỏ hoặc mô hình tư duy:
- `List<String> names = new ArrayList<>();` lưu trữ các phần tử có thứ tự.

### count

`count` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và kịch bản thất bại của nó thay vì chỉ ghi nhớ tên gọi.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng thức được cho phép và kịch bản thất bại. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn mô tả thuần túy.

#### Ví dụ Code
```java
// Đếm các phần tử
long total = Stream.of(1, 2, 3).count(); // 3
```

Kiểm tra thực tế:
- Định nghĩa `count` trong một câu.
- Nhận biết `count` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `count`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy tự hỏi: `count` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

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

## Các Câu Hỏi Ôn Tập Thường Gặp

- Những khái niệm nào ở đây là các quy tắc tại thời điểm biên dịch?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy?
- Những khái niệm nào ở đây dễ trở thành bẫy khi phỏng vấn?

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
