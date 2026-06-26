# Biểu Thức Lambda - Phần 2 (Lambda Expression - Part 2)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này đề cập đến một phần trọng tâm của **Biểu Thức Lambda**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là những thuật ngữ riêng lẻ.

## Phạm Vi Outline (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `Lambda with Collection` | Một collection là một đối tượng nhóm nhiều phần tử dưới một API chung. |
| `Lambda with Thread` | Một biểu thức lambda là một khối giống như hàm nhỏ gọn được sử dụng ở những nơi mong đợi một interface chức năng (functional interface). |
| `Lambda with Comparator` | `Comparator` định nghĩa thứ tự tùy biến từ bên ngoài cho các đối tượng. |

## Ghi Chú Chi Tiết (Detailed Notes)

### Lambda với Collection (Lambda with Collection)

Một collection là một đối tượng nhóm nhiều phần tử dưới một API chung.

Nó quan trọng vì việc chọn sai cấu trúc dữ liệu sẽ làm thay đổi tính chính xác, hiệu suất và hành vi xử lý trùng lặp. Một sự nhầm lẫn phổ biến là ghi nhớ tên lớp mà không biết thứ tự tìm kiếm, quy tắc so sánh bằng hay hành vi lặp.

Kiểm tra thực tế:
- Định nghĩa `Lambda với Collection` trong một câu.
- Nhận biết `Lambda với Collection` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `Lambda với Collection`.

Ví dụ nhỏ hoặc mô hình tư duy:
- `n -> n > 0` là một lambda được sử dụng làm vị từ (predicate).

#### Ví dụ mã nguồn: Các phương thức của Collection nhận Lambda (Code Example: Collection methods accepting Lambdas)
```java
java.util.List<String> list = new java.util.ArrayList<>(java.util.List.of("apple", "banana", "cherry"));

// 1. Duyệt qua các phần tử với Consumer
list.forEach(item -> System.out.println(item));

// 2. Lọc phần tử tại chỗ với Predicate
list.removeIf(item -> item.startsWith("b")); // xóa "banana"

// 3. Thay thế phần tử tại chỗ với UnaryOperator
list.replaceAll(item -> item.toUpperCase()); // thay thế các phần tử còn lại thành "APPLE", "CHERRY"
```

#### Lỗi Thường Gặp: Sửa đổi các Collection Bất Biến tại thời điểm chạy (Common Mistake: Modifying Unmodifiable Collections at Runtime)
Các phương thức như `List.of()`, `Map.of()`, hoặc `Collections.unmodifiableList()` tạo ra các collection bất biến (unmodifiable). Việc truyền một lambda vào `removeIf()` hoặc `replaceAll()` trên các danh sách này vẫn biên dịch bình thường nhưng sẽ ném ra ngoại lệ `UnsupportedOperationException` tại thời điểm chạy.
```java
java.util.List<String> fixedList = java.util.List.of("a", "b");
// Ném ra UnsupportedOperationException tại thời điểm chạy!
fixedList.removeIf(s -> s.equals("a")); 
```

### Lambda với Thread (Lambda with Thread)

Một biểu thức lambda là một khối giống như hàm nhỏ gọn được sử dụng ở những nơi mong đợi một interface chức năng (functional interface).

Nó quan trọng vì mã nguồn bất đồng bộ (concurrent code) có vẻ hoạt động đúng trong các kiểm thử đơn luồng (single-thread tests) nhưng lại thất bại dưới áp lực về thời gian thực thi (timing pressure). Một sự nhầm lẫn phổ biến là giả định rằng tính hiển thị (visibility), tính thứ tự (ordering) và tính nguyên tử (atomicity) là những sự đảm bảo giống nhau.

Kiểm tra thực tế:
- Định nghĩa `Lambda với Thread` trong một câu.
- Nhận biết `Lambda với Thread` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `Lambda với Thread`.

Ví dụ nhỏ hoặc mô hình tư duy:
- `n -> n > 0` là một lambda được sử dụng làm vị từ (predicate).

#### Ví dụ mã nguồn: Chạy các tác vụ bất đồng bộ (Code Example: Running Tasks Asynchronously)
Bởi vì `Runnable` là một functional interface (chỉ có duy nhất phương thức trừu tượng `run()`), chúng ta có thể sử dụng biểu thức lambda để định nghĩa các tác vụ cho luồng (thread task) hoặc đệ trình lên dịch vụ thực thi (executor service submit).
```java
// 1. Constructor Thread
new Thread(() -> System.out.println("Chạy bất đồng bộ (Async run)")).start();

// 2. Đệ trình ExecutorService
java.util.concurrent.ExecutorService executor = java.util.concurrent.Executors.newSingleThreadExecutor();
executor.submit(() -> System.out.println("Tác vụ Executor"));
executor.shutdown();
```

### Lambda với Comparator (Lambda with Comparator)

`Comparator` định nghĩa thứ tự tùy biến từ bên ngoài cho các đối tượng.

Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều các đường ống (pipeline) kiểu hàm. Một sự nhầm lẫn phổ biến là quên mất thao tác nào là lười biếng (lazy evaluation) và thao tác nào thực sự kích hoạt việc thực thi.

Kiểm tra thực tế:
- Định nghĩa `Lambda với Comparator` trong một câu.
- Nhận biết `Lambda với Comparator` trong mã nguồn, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `Lambda với Comparator`.

Ví dụ nhỏ hoặc mô hình tư duy:
- `n -> n > 0` là một lambda được sử dụng làm vị từ (predicate).

#### Ví dụ mã nguồn: Logic sắp xếp tùy biến (Code Example: Custom sorting logic)
```java
java.util.List<String> names = new java.util.ArrayList<>(java.util.List.of("Charles", "Bob", "Alice"));

// Sắp xếp sử dụng bộ so sánh lambda tùy biến (sắp xếp theo độ dài)
names.sort((s1, s2) -> Integer.compare(s1.length(), s2.length()));

// Sắp xếp sử dụng phương thức tiện ích của Comparator và tham chiếu phương thức
names.sort(java.util.Comparator.comparingInt(String::length));
```

#### Lỗi Thường Gặp: Tràn số nguyên trong Bộ so sánh phép trừ (Common Mistake: Integer Overflow in Subtraction Comparator)
Một sai lầm kinh điển khi so sánh các giá trị số nguyên là sử dụng phép trừ thay vì `Integer.compare()`.
```java
// An toàn khi biên dịch nhưng dễ xảy ra lỗi tràn số nguyên!
names.sort((s1, s2) -> s1.length() - s2.length()); 
// Nếu s1.length() là Integer.MAX_VALUE và s2.length() là -1, phép trừ sẽ bị tràn số!
// Cách tiếp cận chính xác là luôn luôn sử dụng Integer.compare(x, y).
```

## Các Câu Hỏi Ôn Tập Thường Gặp (Common Review Prompts)

- Những khái niệm nào ở đây là quy tắc tại thời điểm biên dịch (compile-time)?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy (runtime)?
- Những khái niệm nào ở đây có khả năng là bẫy khi phỏng vấn?
