# Stream API - Phần 4 (Stream API - Part 4)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này đề cập đến một phần trọng tâm của **Stream API**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là những thuật ngữ riêng lẻ.

## Phạm Vi Outline (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `min` | `min` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `max` | `max` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `reduce` | `reduce` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `anyMatch` | `anyMatch` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `allMatch` | `allMatch` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `noneMatch` | `noneMatch` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `findFirst` | `findFirst` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |
| `findAny` | `findAny` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi. |

## Ghi Chú Chi Tiết (Detailed Notes)

### min

`min` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Tìm phần tử nhỏ nhất
Optional<Integer> minVal = Stream.of(5, 2, 8, 1)
                                 .min(Integer::compareTo); // Trả về Optional[1]
```

Kiểm tra thực tế:
- Định nghĩa `min` trong một câu.
- Nhận biết `min` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `min`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `min` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### max

`max` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Tìm phần tử lớn nhất
Optional<Integer> maxVal = Stream.of(5, 2, 8, 1)
                                 .max(Integer::compareTo); // Trả về Optional[8]
```

Kiểm tra thực tế:
- Định nghĩa `max` trong một câu.
- Nhận biết `max` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `max`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `max` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### reduce

`reduce` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Gộp các phần tử thành một giá trị duy nhất
int sum = Stream.of(1, 2, 3, 4)
                .reduce(0, (a, b) -> a + b); // Trả về 10
```

Kiểm tra thực tế:
- Định nghĩa `reduce` trong một câu.
- Nhận biết `reduce` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `reduce`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `reduce` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### anyMatch

`anyMatch` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Kiểm tra xem có bất kỳ phần tử nào khớp với vị từ (ngắn mạch - short-circuiting)
boolean hasEven = Stream.of(1, 3, 4, 5)
                        .anyMatch(n -> n % 2 == 0); // true
```

Kiểm tra thực tế:
- Định nghĩa `anyMatch` trong một câu.
- Nhận biết `anyMatch` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `anyMatch`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `anyMatch` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### allMatch

`allMatch` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Kiểm tra xem tất cả phần tử có khớp với vị từ (ngắn mạch - short-circuiting)
boolean allEven = Stream.of(2, 4, 6)
                        .allMatch(n -> n % 2 == 0); // true
```

Kiểm tra thực tế:
- Định nghĩa `allMatch` trong một câu.
- Nhận biết `allMatch` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `allMatch`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `allMatch` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### noneMatch

`noneMatch` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Kiểm tra xem không có phần tử nào khớp với vị từ (ngắn mạch - short-circuiting)
boolean noneNegative = Stream.of(1, 2, 3)
                             .noneMatch(n -> n < 0); // true
```

Kiểm tra thực tế:
- Định nghĩa `noneMatch` trong một câu.
- Nhận biết `noneMatch` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `noneMatch`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `noneMatch` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### findFirst

`findFirst` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Lấy phần tử đầu tiên theo thứ tự xuất hiện (ngắn mạch - short-circuiting)
Optional<String> first = Stream.of("banana", "apple", "cherry")
                               .findFirst(); // Optional["banana"]
```

Kiểm tra thực tế:
- Định nghĩa `findFirst` trong một câu.
- Nhận biết `findFirst` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `findFirst`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `findFirst` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### findAny

`findAny` là một khái niệm cụ thể trong Stream API; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vị chỉ ghi nhớ nhãn tên.

#### Ví dụ mã nguồn
```java
// Lấy một phần tử bất kỳ, tối ưu hóa cho stream song song (ngắn mạch - short-circuiting)
Optional<String> any = Stream.of("banana", "apple", "cherry")
                             .findAny(); // Trả về bất kỳ phần tử nào
```

## Các Lỗi Thường Gặp (Common Mistakes)

### 1. Vi phạm giá trị đồng nhất (identity value) trong reduce()
Giá trị đồng nhất trong `reduce(identity, accumulator)` phải là một giá trị đồng nhất thực sự cho hàm tích lũy (nghĩa là `accumulator.apply(identity, x) == x` với mọi `x`). Nếu vi phạm, phép rút gọn sẽ tạo ra kết quả sai, đặc biệt là khi chạy song song.
```java
// Sai giá trị đồng nhất: sử dụng 10 cho phép cộng sum
// Khi chạy tuần tự: 10 + 1 + 2 + 3 = 16
// Khi chạy song song: (10 + 1) + (10 + 2) + (10 + 3) = 36!
int sum = List.of(1, 2, 3).parallelStream()
              .reduce(10, Integer::sum); 
```

### 2. Giả định findFirst() và findAny() hoạt động giống hệt nhau trên stream song song
`findFirst()` phải tuân thủ nghiêm ngặt thứ tự xuất hiện (encounter order) của stream. Trong một stream song song, việc điều phối các luồng (Thread) đầu ra để trả về đúng phần tử đầu tiên là rất tốn kém. `findAny()` trả về phần tử đầu tiên được tính toán xong bởi bất kỳ luồng nào, giúp nó chạy nhanh hơn nhiều.
```java
// Chậm khi chạy song song vì bắt buộc phải theo dõi thứ tự xuất hiện
Optional<Integer> first = List.of(1, 2, 3, 4, 5).parallelStream()
                              .filter(n -> n > 3)
                              .findFirst();

// Nhanh khi chạy song song (trả về bất kỳ phần tử nào > 3 ngay khi tìm thấy)
Optional<Integer> any = List.of(1, 2, 3, 4, 5).parallelStream()
                             .filter(n -> n > 3)
                             .findAny();
```

Kiểm tra thực tế:
- Định nghĩa `findAny` trong một câu.
- Nhận biết `findAny` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `findAny`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy hỏi: `findAny` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Các Câu Hỏi Ôn Tập Thường Gặp (Common Review Prompts)

- Những khái niệm nào ở đây là quy tắc tại thời điểm biên dịch (compile-time)?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy (runtime)?
- Những khái niệm nào ở đây có khả năng là bẫy khi phỏng vấn?
