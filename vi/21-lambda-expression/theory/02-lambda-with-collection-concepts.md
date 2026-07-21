# Biểu Thức Lambda (Lambda Expression) - Phần 2

## Mục Tiêu Học Tập

Tệp (File) này cung cấp một phần trọng tâm về **biểu thức Lambda**. Hãy học từng khái niệm như một quy tắc Java thực tế, chứ không phải như các từ vựng riêng lẻ.

## Nội Dung Tóm Tắt

- **`Lambda with Collection`** — Một bộ sưu tập (Collection) là một đối tượng nhóm nhiều phần tử lại với nhau dưới một giao diện lập trình ứng dụng (API) chung.
- **`Lambda with Thread`** — Một biểu thức Lambda là một khối tương tự như hàm ngắn gọn được sử dụng ở những nơi mong đợi một giao diện chức năng (Functional Interface).
- **`Lambda with Comparator`** — Comparator định nghĩa việc sắp xếp tùy chỉnh bên ngoài cho các đối tượng (Object).

## Ghi Chú Chi Tiết

### Lambda với Collection (Lambda with Collection)

Một bộ sưu tập là một đối tượng nhóm nhiều phần tử lại với nhau dưới một API chung.

> Xem thêm: Ứng dụng mạnh mẽ của Lambda trong việc thao tác và xử lý Collection, được trình bày chi tiết trong [Ch.23 - Stream API](../../23-stream-api/theory/01-what-is-stream-concepts.md).

Điều này quan trọng vì việc lựa chọn sai cấu trúc dữ liệu (Data Structure) sẽ làm thay đổi tính chính xác, hiệu năng (Performance) và hành vi xử lý phần tử trùng lặp. Một sự nhầm lẫn phổ biến là việc ghi nhớ tên các lớp (Class) mà không biết rõ thứ tự tra cứu (Lookup Order), quy tắc so sánh bằng (Equality Rules) hay hành vi duyệt (Iteration Behavior).

Kiểm tra thực tế (Practical Check):

- Định nghĩa `Lambda with Collection` trong một câu.
- Nhận biết `Lambda with Collection` trong mã nguồn (Code), câu lệnh (Command), tài liệu (Documentation) hoặc các câu hỏi phỏng vấn (Interview Prompt).
- Giải thích một lỗi (Bug), hạn chế (Limitation) hoặc sự đánh đổi (Tradeoff) liên quan đến `Lambda with Collection`.

Ví dụ nhỏ hoặc mô hình tư duy (Mental Model):

- `n -> n > 0` là một biểu thức Lambda được sử dụng làm hàm vị từ (Predicate).

#### Ví dụ mã nguồn (Code Example): Các phương thức của Collection chấp nhận Lambda
```java
java.util.List<String> list = new java.util.ArrayList<>(java.util.List.of("apple", "banana", "cherry"));

// 1. Iteration with Consumer
list.forEach(item -> System.out.println(item));

// 2. Inline filtering with Predicate
list.removeIf(item -> item.startsWith("b")); // removes "banana"

// 3. Inline replacing with UnaryOperator
list.replaceAll(item -> item.toUpperCase()); // replaces remaining with "APPLE", "CHERRY"
```

#### Sai lầm thường gặp (Common Mistake): Sửa đổi các bộ sưu tập không thể sửa đổi (Unmodifiable Collections) tại thời điểm chạy (Runtime)
Các phương thức như `List.of()`, `Map.of()`, hoặc `Collections.unmodifiableList()` tạo ra các bộ sưu tập không thể sửa đổi. Việc truyền một biểu thức Lambda vào `removeIf()` hoặc `replaceAll()` trên các danh sách này vẫn biên dịch (Compile) bình thường nhưng sẽ ném ra ngoại lệ `UnsupportedOperationException` tại thời điểm chạy.
```java
java.util.List<String> fixedList = java.util.List.of("a", "b");
// Throws UnsupportedOperationException at runtime!
fixedList.removeIf(s -> s.equals("a")); 
```

### Lambda với Thread (Lambda with Thread)

Một biểu thức Lambda là một khối tương tự như hàm ngắn gọn được sử dụng ở những nơi mong đợi một giao diện chức năng.

Điều này quan trọng vì mã nguồn đồng thời (Concurrent Code) có thể trông có vẻ chính xác trong các bài kiểm tra đơn luồng (Single-thread) nhưng lại thất bại dưới áp lực về thời gian phản hồi. Một sự nhầm lẫn phổ biến là giả định rằng tính hiển thị (Visibility), tính tuần tự (Ordering) và tính nguyên tử (Atomicity) là cùng một sự đảm bảo.

Kiểm tra thực tế:

- Định nghĩa `Lambda with Thread` trong một câu.
- Nhận biết `Lambda with Thread` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Lambda with Thread`.

Ví dụ nhỏ hoặc mô hình tư duy:

- `n -> n > 0` là một biểu thức Lambda được sử dụng làm hàm vị từ.

#### Ví dụ mã nguồn: Chạy các tác vụ bất đồng bộ (Asynchronously)
Bởi vì `Runnable` là một giao diện chức năng (chỉ có duy nhất phương thức trừu tượng (Abstract Method) `run()`), chúng ta có thể sử dụng các biểu thức Lambda để định nghĩa các tác vụ luồng (Thread Task) hoặc gửi tác vụ cho dịch vụ thực thi (Executor Service).
```java
// 1. Thread constructor
new Thread(() -> System.out.println("Async run")).start();

// 2. ExecutorService submission
java.util.concurrent.ExecutorService executor = java.util.concurrent.Executors.newSingleThreadExecutor();
executor.submit(() -> System.out.println("Executor task"));
executor.shutdown();
```

### Lambda với Comparator (Lambda with Comparator)

Comparator định nghĩa việc sắp xếp tùy chỉnh bên ngoài cho các đối tượng.

Điều này quan trọng vì các API Java hiện đại sử dụng rất nhiều các đường ống xử lý kiểu hàm (Function-style Pipeline). Một sự nhầm lẫn phổ biến là việc quên mất thao tác nào là lười (Lazy) và thao tác nào thực sự kích hoạt quá trình thực thi.

Kiểm tra thực tế:

- Định nghĩa `Lambda with Comparator` trong một câu.
- Nhận biết `Lambda with Comparator` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Lambda with Comparator`.

Ví dụ nhỏ hoặc mô hình tư duy:

- `n -> n > 0` là một biểu thức Lambda được sử dụng làm hàm vị từ.

#### Ví dụ mã nguồn: Logic sắp xếp tùy chỉnh
```java
java.util.List<String> names = new java.util.ArrayList<>(java.util.List.of("Charles", "Bob", "Alice"));

// Sorting using custom lambda comparator (sorts by length)
names.sort((s1, s2) -> Integer.compare(s1.length(), s2.length()));

// Sorting using Comparator utility methods and method references
names.sort(java.util.Comparator.comparingInt(String::length));
```

#### Sai lầm thường gặp: Tràn số nguyên (Integer Overflow) trong bộ so sánh sử dụng phép trừ (Subtraction Comparator)
Một sai lầm kinh điển khi so sánh các giá trị số nguyên là sử dụng phép trừ thay vì `Integer.compare()`.
```java
// Compile-safe but prone to integer overflow bugs!
names.sort((s1, s2) -> s1.length() - s2.length()); 
// If s1.length() is Integer.MAX_VALUE and s2.length() is -1, subtraction overflows!
// Correct approach is to always use Integer.compare(x, y).
```

## Các Câu Hỏi Ôn Tập Thường Gặp (Common Review Prompts)

- Những khái niệm nào ở đây là các quy tắc tại thời điểm biên dịch (Compile-time)?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy?
- Những khái niệm nào ở đây dễ là bẫy phỏng vấn?