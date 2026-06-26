# Các Thuật Ngữ Về Optional (Optional Terms)

Sử dụng tài liệu này khi một từ ngữ trong phần lý thuyết có vẻ quá ngắn gọn. Mỗi thuật ngữ đều có định nghĩa, tầm quan trọng, điểm dễ nhầm lẫn và một ví dụ nhỏ đi kèm.

## Optional

Một đối tượng container bất biến có thể chứa hoặc không chứa một giá trị phi-null duy nhất.

Tại sao nó quan trọng: Nó cung cấp một dạng biểu diễn ở cấp độ kiểu dữ liệu về sự hiện diện hoặc vắng mặt của một giá trị, bắt buộc các bên tiêu thụ API phải xử lý rõ ràng trạng thái trống rỗng, từ đó giảm các lỗi `NullPointerException`.

Điểm dễ nhầm lẫn: Các lập trình viên đôi khi coi `Optional` như một sự thay thế hoàn toàn cho kiểm tra `null` trên mọi tham chiếu, dẫn đến mã nguồn phình to và chi phí của lớp bọc. Nó chủ yếu nên được sử dụng làm kiểu trả về của phương thức.

Ví dụ nhỏ: `Optional<String> optionalName = Optional.ofNullable(name);`

## optional rỗng (empty optional)

Một thực thể của `Optional` không chứa giá trị nào, được truy xuất thông qua `Optional.empty()`.

Tại sao nó quan trọng: Nó đại diện cho sự vắng mặt một cách sạch sẽ mà không cần phải trả về `null`. Trả về một `Optional` rỗng cho phép phía gọi liên kết chuỗi các phương thức một cách an toàn.

Điểm dễ nhầm lẫn: Vì `Optional.empty()` trả về một thực thể singleton được lưu đệm, các lập trình viên không bao giờ nên trả về `null` từ một phương thức được khai báo trả về một `Optional<T>`.

Ví dụ nhỏ: `return Optional.empty();`

## giá trị dự phòng (fallback value)

Một giá trị mặc định hoặc khối thực thi được cung cấp để xử lý trường hợp một `Optional` bị rỗng.

Tại sao nó quan trọng: Các phương thức như `orElse`, `orElseGet`, và `orElseThrow` cho phép mở hộp `Optional` một cách an toàn bằng cách xác định những việc cần làm khi thiếu giá trị.

Điểm dễ nhầm lẫn: Sử dụng `orElse` (ví dụ: `orElse(new DatabaseQuery())`) luôn luôn đánh giá biểu thức tham số truyền vào, gây ra các tác dụng phụ về hiệu năng. Hãy sử dụng `orElseGet` để đánh giá lười biếng các giá trị mặc định.

Ví dụ nhỏ: `String val = opt.orElse("Default");`

## map

Một phương thức trung gian biến đổi giá trị bên trong `Optional` nếu nó hiện diện.

Tại sao nó quan trọng: Nó cho phép áp dụng các hàm biến đổi lên giá trị được bọc mà không cần kiểm tra null thủ công hoặc kiểm tra `isPresent`. Giá trị trả về thô của bộ ánh xạ sẽ tự động được bọc lại vào một `Optional`.

Điểm dễ nhầm lẫn: Nếu hàm ánh xạ trả về `null`, `map` sẽ trả về `Optional.empty()`. Nếu hàm ánh xạ trả về một `Optional` khác, `map` sẽ trả về một `Optional` lồng nhau `Optional<Optional<T>>`.

Ví dụ nhỏ: `Optional<Integer> length = optName.map(String::length);`

## flatMap

Một phương thức biến đổi trung gian làm phẳng các cấu trúc `Optional` lồng nhau.

Tại sao nó quan trọng: Khi một hàm ánh xạ tự bản thân nó trả về một `Optional`, việc sử dụng `flatMap` giúp tránh việc bao bọc hai lần (`Optional<Optional<T>>`) bằng cách trả về trực tiếp `Optional` bên trong.

Điểm dễ nhầm lẫn: Không giống như `map`, nếu hàm ánh xạ trong `flatMap` trả về một giá trị `null` thay vì `Optional.empty()`, nó sẽ ném ra `NullPointerException`.

Ví dụ nhỏ: `Optional<String> email = userOpt.flatMap(User::getEmail);`

## lạm dụng optional (optional misuse)

Các phản mẫu (anti-pattern) nơi `Optional` được sử dụng trong các ngữ cảnh không phù hợp.

Tại sao nó quan trọng: Việc sử dụng sai `Optional` trong các trường thuộc tính, tham số hoặc collection sẽ làm giảm hiệu năng, phá vỡ quá trình tuần hóa (serialization), và tạo ra các nguy cơ lỗi con trỏ null mới.

Điểm dễ nhầm lẫn: Các nhà phát triển thường sử dụng `Optional` cho các trường thuộc tính lớp, điều này phá vỡ việc tuần hóa Java tiêu chuẩn vì `Optional` không triển khai interface Serializable.

Ví dụ nhỏ: Sử dụng `public void setStreet(Optional<String> street)` là một phản mẫu. Hãy sử dụng nạp chồng phương thức hoặc một tham số cho phép nullable để thay thế.
