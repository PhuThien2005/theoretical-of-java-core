# Stream API - Phần 4

## Mục Tiêu Học Tập

Tài liệu này trình bày một phần trọng tâm của **Stream API**. Hãy nghiên cứu từng khái niệm dưới dạng một quy tắc Java thực tế, thay vì chỉ học từ vựng riêng lẻ.

## Nội Dung Tổng Quan

- **`min`** — min: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`max`** — max: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`reduce`** — reduce: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`anyMatch`** — anyMatch: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`allMatch`** — allMatch: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`noneMatch`** — noneMatch: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`findFirst`** — findFirst: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`findAny`** — findAny: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

## Ghi Chú Chi Tiết

### min

**`min`** — Trả về phần tử nhỏ nhất trong stream dựa trên Comparator cung cấp.

Sử dụng khái niệm này để dự đoán chính xác quy tắc Java, dạng được cho phép và trường hợp lỗi xảy ra. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn định nghĩa.

#### Ví Dụ Mã Nguồn
```java
// Find the minimum element
Optional<Integer> minVal = Stream.of(5, 2, 8, 1)
                                 .min(Integer::compareTo); // Returns Optional[1]
```

Kiểm tra thực tế:

- Định nghĩa `min` trong một câu.
- Nhận biết `min` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `min`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `min` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### max

**`max`** — max: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong lập trình Java.

Sử dụng khái niệm này để dự đoán chính xác quy tắc Java, dạng được cho phép và trường hợp lỗi xảy ra. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn định nghĩa.

#### Ví Dụ Mã Nguồn
```java
// Find the maximum element
Optional<Integer> maxVal = Stream.of(5, 2, 8, 1)
                                 .max(Integer::compareTo); // Returns Optional[8]
```

Kiểm tra thực tế:

- Định nghĩa `max` trong một câu.
- Nhận biết `max` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `max`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `max` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### reduce

**`reduce`** — reduce: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong lập trình Java.

Sử dụng khái niệm này để dự đoán chính xác quy tắc Java, dạng được cho phép và trường hợp lỗi xảy ra. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn định nghĩa.

#### Ví Dụ Mã Nguồn
```java
// Reduce elements to a single value
int sum = Stream.of(1, 2, 3, 4)
                .reduce(0, (a, b) -> a + b); // Returns 10
```

Kiểm tra thực tế:

- Định nghĩa `reduce` trong một câu.
- Nhận biết `reduce` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `reduce`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `reduce` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### anyMatch

**`anyMatch`** — anyMatch: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong lập trình Java.

Sử dụng khái niệm này để dự đoán chính xác quy tắc Java, dạng được cho phép và trường hợp lỗi xảy ra. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn định nghĩa.

#### Ví Dụ Mã Nguồn
```java
// Check if any element matches predicate (short-circuiting)
boolean hasEven = Stream.of(1, 3, 4, 5)
                        .anyMatch(n -> n % 2 == 0); // true
```

Kiểm tra thực tế:

- Định nghĩa `anyMatch` trong một câu.
- Nhận biết `anyMatch` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `anyMatch`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `anyMatch` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### allMatch

**`allMatch`** — allMatch: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong lập trình Java.

Sử dụng khái niệm này để dự đoán chính xác quy tắc Java, dạng được cho phép và trường hợp lỗi xảy ra. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn định nghĩa.

#### Ví Dụ Mã Nguồn
```java
// Check if all elements match predicate (short-circuiting)
boolean allEven = Stream.of(2, 4, 6)
                        .allMatch(n -> n % 2 == 0); // true
```

Kiểm tra thực tế:

- Định nghĩa `allMatch` trong một câu.
- Nhận biết `allMatch` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `allMatch`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `allMatch` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### noneMatch

**`noneMatch`** — noneMatch: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong lập trình Java.

Sử dụng khái niệm này để dự đoán chính xác quy tắc Java, dạng được cho phép và trường hợp lỗi xảy ra. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn định nghĩa.

#### Ví Dụ Mã Nguồn
```java
// Check if no elements match predicate (short-circuiting)
boolean noneNegative = Stream.of(1, 2, 3)
                             .noneMatch(n -> n < 0); // true
```

Kiểm tra thực tế:

- Định nghĩa `noneMatch` trong một câu.
- Nhận biết `noneMatch` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `noneMatch`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `noneMatch` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### findFirst

**`findFirst`** — findFirst: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong lập trình Java.

Sử dụng khái niệm này để dự đoán chính xác quy tắc Java, dạng được cho phép và trường hợp lỗi xảy ra. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn định nghĩa.

#### Ví Dụ Mã Nguồn
```java
// Get the first element in encounter order (short-circuiting)
Optional<String> first = Stream.of("banana", "apple", "cherry")
                               .findFirst(); // Optional["banana"]
```

Kiểm tra thực tế:

- Định nghĩa `findFirst` trong một câu.
- Nhận biết `findFirst` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `findFirst`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `findFirst` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### findAny

**`findAny`** — findAny: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong lập trình Java.

Sử dụng khái niệm này để dự đoán chính xác quy tắc Java, dạng được cho phép và trường hợp lỗi xảy ra. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn định nghĩa.

#### Ví Dụ Mã Nguồn
```java
// Get any element, optimized for parallel streams (short-circuiting)
Optional<String> any = Stream.of("banana", "apple", "cherry")
                             .findAny(); // Returns any elements
```

Kiểm tra thực tế:

- Định nghĩa `findAny` trong một câu.
- Nhận biết `findAny` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `findAny`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `findAny` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

---

## Sai Lầm Thường Gặp

### 1. Vi phạm giá trị đồng nhất (identity value) trong reduce()
Giá trị đồng nhất (identity value) trong `reduce(identity, accumulator)` phải là một phần tử đơn vị thực sự cho hàm tích lũy (accumulator) (nghĩa là `accumulator.apply(identity, x) == x` với mọi `x`). Nếu không, phép rút gọn sẽ tạo ra kết quả sai, đặc biệt là khi chạy song song.
```java
// Incorrect identity: using 10 for sum
// In sequential: 10 + 1 + 2 + 3 = 16
// In parallel: (10 + 1) + (10 + 2) + (10 + 3) = 36!
int sum = List.of(1, 2, 3).parallelStream()
              .reduce(10, Integer::sum); 
```

### 2. Giả định rằng findFirst() và findAny() có hiệu năng giống nhau trên các luồng song song
`findFirst()` phải tuân thủ nghiêm ngặt thứ tự xuất hiện (encounter order) của luồng. Trong một luồng song song, việc điều phối đầu ra của các luồng để trả về phần tử đầu tiên là rất tốn kém. `findAny()` trả về phần tử đầu tiên được tính toán bởi bất kỳ luồng nào, điều này nhanh hơn nhiều.
```java
// Slow in parallel because it forces encounter order tracking
Optional<Integer> first = List.of(1, 2, 3, 4, 5).parallelStream()
                              .filter(n -> n > 3)
                              .findFirst();

// Fast in parallel (returns any element > 3 as soon as found)
Optional<Integer> any = List.of(1, 2, 3, 4, 5).parallelStream()
                             .filter(n -> n > 3)
                             .findAny();
```

## Các Câu Hỏi Ôn Tập Thường Gặp

- Những khái niệm nào ở đây là quy tắc trong thời gian biên dịch (compile-time)?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi khi chạy ứng dụng (runtime)?
- Những khái niệm nào ở đây có khả năng là bẫy phỏng vấn?
