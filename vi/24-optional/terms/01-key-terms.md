# Thuật Ngữ Optional

Dùng file này khi một từ trong lý thuyết cảm thấy quá cô đọng. Mỗi thuật ngữ có phần ý nghĩa, tầm quan trọng, điểm dễ nhầm lẫn và ví dụ nhỏ.

## Optional

Một đối tượng container bất biến (immutable) có thể chứa hoặc không chứa một giá trị khác null.

Tại sao quan trọng: Nó cung cấp biểu diễn ở cấp kiểu (type-level) cho sự hiện diện hoặc vắng mặt của một giá trị, buộc người dùng API phải xử lý rõ ràng trạng thái rỗng, từ đó giảm lỗi `NullPointerException`.

Điểm dễ nhầm: Lập trình viên đôi khi coi `Optional` là sự thay thế hoàn toàn cho kiểm tra null trên mọi tham chiếu, dẫn đến code cồng kềnh và tốn chi phí bọc thêm đối tượng. Nên dùng chủ yếu làm kiểu trả về của phương thức.

Ví dụ nhỏ: `Optional<String> optionalName = Optional.ofNullable(name);`

## Optional rỗng (empty optional)

Một instance của `Optional` không chứa giá trị nào, lấy về qua `Optional.empty()`.

Tại sao quan trọng: Nó biểu diễn sự vắng mặt một cách rõ ràng mà không cần trả về `null`. Trả về `Optional` rỗng cho phép người gọi xâu chuỗi các phương thức an toàn.

Điểm dễ nhầm: Vì `Optional.empty()` trả về một singleton được cache sẵn, lập trình viên không bao giờ nên trả về `null` từ phương thức được khai báo trả về `Optional<T>`.

Ví dụ nhỏ: `return Optional.empty();`

## Giá trị dự phòng (fallback value)

Giá trị mặc định hoặc khối xử lý được cung cấp để xử lý trường hợp `Optional` rỗng.

Tại sao quan trọng: Các phương thức như `orElse`, `orElseGet`, và `orElseThrow` cho phép mở bọc `Optional` an toàn bằng cách xác định việc cần làm khi giá trị vắng mặt.

Điểm dễ nhầm: Dùng `orElse` (ví dụ: `orElse(new DatabaseQuery())`) luôn đánh giá biểu thức tham số, gây ra tác dụng phụ về hiệu năng. Dùng `orElseGet` để đánh giá lười biếng (lazy) giá trị mặc định.

Ví dụ nhỏ: `String val = opt.orElse("Default");`

## map

Phương thức trung gian (intermediate method) dùng để biến đổi giá trị bên trong `Optional` nếu giá trị đó tồn tại.

Tại sao quan trọng: Cho phép áp dụng các hàm biến đổi lên giá trị được bọc mà không cần kiểm tra null hay `isPresent` thủ công. Giá trị thô trả về từ hàm ánh xạ được tự động bọc lại thành `Optional`.

Điểm dễ nhầm: Nếu hàm ánh xạ trả về `null`, `map` trả về `Optional.empty()`. Nếu hàm ánh xạ trả về một `Optional` khác, `map` trả về `Optional<Optional<T>>` lồng nhau.

Ví dụ nhỏ: `Optional<Integer> length = optName.map(String::length);`

## flatMap

Phương thức biến đổi trung gian dùng để làm phẳng (flatten) các cấu trúc `Optional` lồng nhau.

Tại sao quan trọng: Khi hàm ánh xạ bản thân trả về một `Optional`, dùng `flatMap` tránh bọc đôi (`Optional<Optional<T>>`) bằng cách trả về trực tiếp `Optional` bên trong.

Điểm dễ nhầm: Khác với `map`, nếu hàm ánh xạ trong `flatMap` trả về `null` thay vì `Optional.empty()`, nó ném `NullPointerException`.

Ví dụ nhỏ: `Optional<String> email = userOpt.flatMap(User::getEmail);`

## Lạm dụng Optional (optional misuse)

Các anti-pattern khi dùng `Optional` trong những ngữ cảnh không phù hợp.

Tại sao quan trọng: Lạm dụng `Optional` trong trường (field), tham số, hoặc collection làm giảm hiệu năng, phá vỡ tuần tự hóa (serialization), và gây ra rủi ro null pointer mới.

Điểm dễ nhầm: Lập trình viên thường dùng `Optional` cho trường của class, điều này phá vỡ tuần tự hóa Java tiêu chuẩn vì `Optional` không phải `Serializable`.

Ví dụ nhỏ: Dùng `public void setStreet(Optional<String> street)` là anti-pattern. Thay bằng nạp chồng phương thức (method overloading) hoặc tham số nullable.
