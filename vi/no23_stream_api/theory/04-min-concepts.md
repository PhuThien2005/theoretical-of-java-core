# Stream API - Phần 4

### min

min(Comparator) duyệt toàn bộ stream và trả về phần tử nhỏ nhất theo Comparator cho trước, gói trong Optional vì stream có thể rỗng.
Trên IntStream/LongStream/DoubleStream, min() không cần Comparator vì thứ tự tự nhiên của số đã xác định.

#### Ví Dụ Mã Nguồn
```java
// Find the minimum element
Optional<Integer> minVal = Stream.of(5, 2, 8, 1)
                                 .min(Integer::compareTo); // Returns Optional[1]
```

### max

max(Comparator) hoạt động tương tự min() nhưng trả về phần tử lớn nhất — cũng trả Optional.

#### Ví Dụ Mã Nguồn
```java
// Find the maximum element
Optional<Integer> maxVal = Stream.of(5, 2, 8, 1)
                                 .max(Integer::compareTo); // Returns Optional[8]
```

### reduce

reduce() gộp tất cả phần tử thành một giá trị duy nhất bằng cách áp dụng BinaryOperator lặp đi lặp lại. Có hai dạng: với identity (trả về T) và không identity (trả về Optional<T>).

#### Ví Dụ Mã Nguồn
```java
// Reduce elements to a single value
int sum = Stream.of(1, 2, 3, 4)
                .reduce(0, (a, b) -> a + b); // Returns 10
```

### anyMatch

anyMatch(Predicate) trả về true ngay khi tìm thấy phần tử đầu tiên thỏa điều kiện — là short-circuiting terminal operation.

#### Ví Dụ Mã Nguồn
```java
// Check if any element matches predicate (short-circuiting)
boolean hasEven = Stream.of(1, 3, 4, 5)
                        .anyMatch(n -> n % 2 == 0); // true
```

### allMatch

allMatch(Predicate) trả về true chỉ khi tất cả phần tử thỏa điều kiện. Trả false ngay khi gặp phần tử không thỏa (short-circuiting).

#### Ví Dụ Mã Nguồn
```java
// Check if all elements match predicate (short-circuiting)
boolean allEven = Stream.of(2, 4, 6)
                        .allMatch(n -> n % 2 == 0); // true
```

### noneMatch

noneMatch(Predicate) trả về true chỉ khi không có phần tử nào thỏa điều kiện. Trả false ngay khi gặp phần tử thỏa (short-circuiting).

#### Ví Dụ Mã Nguồn
```java
// Check if no elements match predicate (short-circuiting)
boolean noneNegative = Stream.of(1, 2, 3)
                             .noneMatch(n -> n < 0); // true
```

### findFirst

findFirst() trả về phần tử đầu tiên theo encounter order, gói trong Optional. Là short-circuiting — dừng ngay khi có kết quả.

#### Ví Dụ Mã Nguồn
```java
// Get the first element in encounter order (short-circuiting)
Optional<String> first = Stream.of("banana", "apple", "cherry")
                               .findFirst(); // Optional["banana"]
```

### findAny

findAny() trả về một phần tử bất kỳ trong stream, được tối ưu cho parallel stream vì không cần giữ encounter order.

#### Ví Dụ Mã Nguồn
```java
// Get any element, optimized for parallel streams (short-circuiting)
Optional<String> any = Stream.of("banana", "apple", "cherry")
                             .findAny(); // Returns any elements
```

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
