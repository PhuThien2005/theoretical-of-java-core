# Stream API - Phần 3 (Stream API - Part 3)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này đề cập đến một phần trọng tâm của **Stream API**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là những thuật ngữ riêng lẻ.

## Phạm Vi Outline (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `peek` | `peek` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `limit` | `limit` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `skip` | `skip` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `Terminal operations:` | Các thao tác cuối (Terminal operations) là một nhóm các quy tắc liên quan trong Stream API nhóm lại một số chi tiết liên quan. |
| `forEach` | `forEach` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `collect` | `collect` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `toList` | Một `List` là một collection có thứ tự, có thể chứa các phần tử trùng lặp và hỗ trợ truy cập theo vị trí chỉ mục. |
| `count` | `count` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |

## Ghi Chú Chi Tiết (Detailed Notes)

### peek

`peek` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Quan sát các phần tử khi chúng di chuyển qua đường ống (phục vụ mục đích gỡ lỗi)
List<String> result = Stream.of("one", "two", "three")
                            .filter(s -> s.length() > 3)
                            .peek(s -> System.out.println("Đã lọc (Filtered): " + s))
                            .map(String::toUpperCase)
                            .peek(s -> System.out.println("Đã ánh xạ (Mapped): " + s))
                            .collect(Collectors.toList());
```

Kiểm tra thực tế:
- Định nghĩa `peek` trong một câu.
- Nhận biết `peek` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `peek`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `peek` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### limit

`limit` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Giới hạn kích thước tối đa của stream
Stream.of(1, 2, 3, 4, 5)
      .limit(3)
      .forEach(System.out::print); // In ra: 123
```

Kiểm tra thực tế:
- Định nghĩa `limit` trong một câu.
- Nhận biết `limit` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `limit`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `limit` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### skip

`skip` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Bỏ qua N phần tử đầu tiên
Stream.of(1, 2, 3, 4, 5)
      .skip(2)
      .forEach(System.out::print); // In ra: 345
```

Kiểm tra thực tế:
- Định nghĩa `skip` trong một câu.
- Nhận biết `skip` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `skip`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `skip` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Các thao tác cuối (Terminal operations)

Các thao tác cuối (Terminal operations) là một nhóm các quy tắc liên quan trong Stream API nhóm lại một số chi tiết liên quan.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Các thao tác cuối thực thi đường ống và đóng stream lại
long count = Stream.of(1, 2, 3).count();
```

Kiểm tra thực tế:
- Định nghĩa `Terminal operations:` trong một câu.
- Nhận biết `Terminal operations:` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `Terminal operations:`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `Terminal operations:` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### forEach

`forEach` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Thực hiện hành động trên từng phần tử
Stream.of("a", "b").forEach(System.out::print); // Kết quả: ab
```

Kiểm tra thực tế:
- Định nghĩa `forEach` trong một câu.
- Nhận biết `forEach` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `forEach`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `forEach` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### collect

`collect` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Tích lũy các phần tử vào một container khả biến
List<String> list = Stream.of("a", "b").collect(Collectors.toList());
```

Kiểm tra thực tế:
- Định nghĩa `collect` trong một câu.
- Nhận biết `collect` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `collect`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `collect` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### toList

Một `List` là một collection có thứ tự, có thể chứa các phần tử trùng lặp và hỗ trợ truy cập theo vị trí chỉ mục.

Nó quan trọng vì việc chọn sai cấu trúc dữ liệu sẽ làm thay đổi tính chính xác, hiệu suất và hành vi xử lý trùng lặp. Một sự nhầm lẫn phổ biến là ghi nhớ tên lớp mà không biết thứ tự tìm kiếm, quy tắc so sánh bằng hay hành vi lặp.

#### Ví dụ mã nguồn
```java
// toList() (Từ Java 16+) trả về một danh sách bất biến (unmodifiable) trực tiếp
List<String> unmodifiableList = Stream.of("a", "b").toList();
```

Kiểm tra thực tế:
- Định nghĩa `toList` trong một câu.
- Nhận biết `toList` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `toList`.

Ví dụ nhỏ hoặc mô hình tư duy:
- `List<String> names = new ArrayList<>();` lưu trữ các phần tử có thứ tự.

### count

`count` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Đếm các phần tử
long total = Stream.of(1, 2, 3).count(); // 3
```

## Các Lỗi Thường Gặp (Common Mistakes)

### 1. Giả định peek() luôn luôn thực thi
Bởi vì stream hoạt động lười biếng, các thao tác trung gian như `peek()` sẽ không chạy trừ khi một thao tác cuối được gọi. Thêm vào đó, các tối ưu hóa của JDK có thể bỏ qua `peek()` nếu thao tác cuối (như `count()`) có thể được đánh giá mà không cần phải duyệt qua các phần tử.
```java
// peek() hoàn toàn KHÔNG thực thi ở đây (thiếu thao tác cuối)
Stream.of("a", "b").peek(System.out::println); 

// peek() có thể bị tối ưu hóa bỏ qua trong Java 9+ vì map/peek không ảnh hưởng đến số lượng phần tử
long size = Stream.of("a", "b")
                  .peek(System.out::println) // Có thể không in ra gì!
                  .count();
```

### 2. Sửa đổi các phần tử bên trong forEach trên stream song song
Sửa đổi trạng thái chia sẻ không an toàn với luồng (non-thread-safe state) từ phương thức `forEach` của stream song song dẫn đến tình trạng tranh chấp (race conditions).
```java
List<Integer> list = new ArrayList<>(); // Không an toàn với luồng (Non-thread-safe)
List.of(1, 2, 3, 4).parallelStream().forEach(list::add); // Tranh chấp luồng xảy ra!
```

Kiểm tra thực tế:
- Định nghĩa `count` trong một câu.
- Nhận biết `count` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `count`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `count` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Các Câu Hỏi Ôn Tập Thường Gặp (Common Review Prompts)

- Những khái niệm nào ở đây là quy tắc tại thời điểm biên dịch (compile-time)?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy (runtime)?
- Những khái niệm nào ở đây có khả năng là bẫy khi phỏng vấn?

## Tại Sao Không Nên Sử Dụng peek() Để Thay Đổi Trạng Thái (Why peek() Should Not Be Used for State Mutation)

Đặc tả API của `Stream.peek()` nêu rõ ràng rằng mục đích chính của nó là hỗ trợ gỡ lỗi, cho phép bạn quan sát các phần tử khi chúng đi qua một điểm nhất định trong đường ống. Việc sử dụng `peek()` để thay đổi trạng thái của các phần tử hoặc các biến bên ngoài là cực kỳ không nên và dễ xảy ra lỗi vì triển khai stream có quyền tối ưu hóa để bỏ qua các bước đường ống trung gian. Ví dụ, nếu một thao tác cuối như `count()` được sử dụng, các phiên bản JDK hiện đại (Java 9 trở lên) có thể xác định số lượng phần tử trực tiếp từ mô tả nguồn stream mà không cần duyệt qua đường ống, nghĩa là `peek()` sẽ không bao giờ được thực thi. Hơn nữa, trong các đường ống stream song song, việc kích hoạt các tác dụng phụ bên trong `peek()` sẽ tạo ra xung đột dữ liệu (data races) và vi phạm an toàn luồng trừ khi thêm đồng bộ hóa phức tạp. Việc thay đổi trạng thái bên trong `peek()` vi phạm mục tiêu thiết kế cơ bản của các đường ống stream chức năng, vốn nên giữ tính thuần túy (pure), không có tác dụng phụ (side-effect-free) và mang tính xác định (deterministic).

### Mô hình tư duy (Mental Model)
```
Nguồn Stream (đã biết kích thước) -> peek(thay đổi trạng thái) -> count()
                                              │
                                              ▼
                             [ Tối ưu hóa đếm của JVM ]
               (Truy vấn trực tiếp kích thước nguồn; bỏ qua duyệt đường ống)
                                              │
                                              ▼
                                peek() KHÔNG BAO GIỜ chạy!
```

### Ví dụ mã nguồn
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
        
        System.out.println("Tổng số lượng (Total Count): " + totalCount);
        System.out.println("Kích thước danh sách đã sửa đổi (Mutated List Size): " + mutatedList.size());
        // Kết quả bảng điều khiển (Java 9+):
        // Tổng số lượng (Total Count): 3
        // Kích thước danh sách đã sửa đổi (Mutated List Size): 0
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Thay đổi trạng thái bên trong `peek()`
  → Việc thực thi phụ thuộc vào việc duyệt đường ống
  → Thao tác cuối được tối ưu hóa (ví dụ: `count()` truy vấn trực tiếp từ nguồn)
  → Bỏ qua việc duyệt qua các phần tử của đường ống
  → Logic thay đổi trạng thái không bao giờ chạy
  → Trạng thái bên ngoài không nhất quán và gây lỗi logic.
```

