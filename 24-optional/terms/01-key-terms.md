# Thuật Ngữ Optional

Sử dụng file này khi một từ trong phần lý thuyết cảm thấy quá cô đọng. Mỗi thuật ngữ đều có ý nghĩa, tầm quan trọng, điểm dễ nhầm lẫn và một ví dụ nhỏ.

## Optional

Một đối tượng chứa (container) bất biến (immutable) có thể có hoặc không có một giá trị khác `null`.

Tại sao quan trọng: Nó cung cấp một biểu diễn ở cấp độ kiểu dữ liệu cho sự hiện diện hoặc vắng mặt của một giá trị, buộc người dùng API phải xử lý rõ ràng trạng thái rỗng, từ đó giảm thiểu lỗi `NullPointerException`.

Điểm dễ nhầm lẫn: Các lập trình viên đôi khi coi `Optional` là phương án thay thế hoàn toàn cho kiểm tra `null` trên mọi tham chiếu, dẫn đến code cồng kềnh và chi phí bao bọc không cần thiết. Nó chủ yếu nên được dùng làm kiểu trả về của phương thức.

Ví dụ nhỏ: `Optional<String> optionalName = Optional.ofNullable(name);`

## optional rỗng (empty optional)

Một thực thể của `Optional` không chứa giá trị nào, lấy về qua `Optional.empty()`.

Tại sao quan trọng: Nó biểu diễn sự vắng mặt một cách rõ ràng mà không cần trả về `null`. Trả về một `Optional` rỗng cho phép người gọi xâu chuỗi các phương thức một cách an toàn.

Điểm dễ nhầm lẫn: Vì `Optional.empty()` trả về một thực thể singleton được lưu trữ sẵn (cached), các lập trình viên không bao giờ nên trả về `null` từ một phương thức được khai báo trả về `Optional<T>`.

Ví dụ nhỏ: `return Optional.empty();`

## giá trị dự phòng (fallback value)

Giá trị mặc định hoặc khối logic được cung cấp để xử lý trường hợp `Optional` rỗng.

Tại sao quan trọng: Các phương thức như `orElse`, `orElseGet` và `orElseThrow` cho phép mở bao `Optional` một cách an toàn bằng cách xác định hành động khi giá trị bị thiếu.

Điểm dễ nhầm lẫn: Sử dụng `orElse` (ví dụ: `orElse(new DatabaseQuery())`) luôn đánh giá biểu thức tham số, gây ra tác dụng phụ về hiệu năng. Dùng `orElseGet` để đánh giá giá trị mặc định theo kiểu lười biếng (lazy).

Ví dụ nhỏ: `String val = opt.orElse("Default");`

## map

Phương thức trung gian dùng để biến đổi giá trị bên trong `Optional` nếu nó tồn tại.

Tại sao quan trọng: Nó cho phép áp dụng hàm biến đổi lên giá trị được bao bọc mà không cần kiểm tra `null` hay `isPresent` thủ công. Giá trị trả về thô của hàm ánh xạ tự động được bao lại trong một `Optional`.

Điểm dễ nhầm lẫn: Nếu hàm ánh xạ trả về `null`, `map` trả về `Optional.empty()`. Nếu hàm ánh xạ trả về một `Optional` khác, `map` trả về một `Optional<Optional<T>>` lồng nhau.

Ví dụ nhỏ: `Optional<Integer> length = optName.map(String::length);`

## flatMap

Phương thức biến đổi trung gian dùng để làm phẳng (flatten) cấu trúc `Optional` lồng nhau.

Tại sao quan trọng: Khi một hàm ánh xạ bản thân nó đã trả về một `Optional`, dùng `flatMap` tránh việc bao bọc đôi (`Optional<Optional<T>>`) bằng cách trả về trực tiếp `Optional` bên trong.

Điểm dễ nhầm lẫn: Khác với `map`, nếu hàm ánh xạ trong `flatMap` trả về `null` thay vì `Optional.empty()`, nó sẽ ném `NullPointerException`.

Ví dụ nhỏ: `Optional<String> email = userOpt.flatMap(User::getEmail);`

## lạm dụng optional (optional misuse)

Các phản mẫu (anti-pattern) khi `Optional` được sử dụng trong ngữ cảnh không phù hợp.

Tại sao quan trọng: Lạm dụng `Optional` trong các trường, tham số hoặc collection làm giảm hiệu năng, phá vỡ khả năng tuần tự hóa (serialization) và tạo ra các rủi ro con trỏ null mới.

Điểm dễ nhầm lẫn: Các lập trình viên thường dùng `Optional` cho trường lớp, điều này phá vỡ quá trình tuần tự hóa Java tiêu chuẩn vì `Optional` không triển khai `Serializable`.

Ví dụ nhỏ: Sử dụng `public void setStreet(Optional<String> street)` là một phản mẫu. Thay vào đó hãy dùng nạp chồng (overloading) hoặc tham số có thể null.
