# Thuật Ngữ Về Lớp Nội Và Lớp Lồng (Inner Class and Nested Class Terms)

Sử dụng tài liệu này khi một từ khóa trong phần lý thuyết có vẻ quá ngắn gọn. Mỗi thuật ngữ đều có định nghĩa, tầm quan trọng, điểm dễ gây nhầm lẫn và một ví dụ nhỏ đi kèm.

## lớp lồng (nested class)

Lớp lồng (nested class) là một lớp được định nghĩa bên trong một lớp khác. Nó được phân thành hai nhóm: lớp lồng tĩnh (static nested class) và lớp nội (inner class).

- **Tầm quan trọng**: Giúp gom nhóm các lớp chỉ sử dụng ở một nơi duy nhất, tăng cường tính đóng gói và làm cho mã nguồn dễ đọc, dễ bảo trì hơn.
- **Nhầm lẫn phổ biến**: Học viên thường ghi nhớ từ "lớp lồng" nhưng không giải thích được chúng giải quyết vấn đề gì hoặc các quy tắc hoạt động của chúng.
- **Ví dụ nhỏ**: Khi đọc mã nguồn, hãy tự hỏi: lớp lồng này có thay đổi, cho phép, từ chối hay làm rõ điều gì đối với lớp bao bọc bên ngoài không?

## lớp lồng tĩnh (static nested class)

Lớp lồng tĩnh (static nested class) là một lớp lồng được khai báo kèm từ khóa `static`. Nó hoạt động giống như một lớp độc lập thông thường nhưng được đặt trong không gian tên của lớp bao bọc bên ngoài.

- **Tầm quan trọng**: Nó không liên kết với bất kỳ thực thể nào của lớp ngoài, giúp tiết kiệm bộ nhớ và không giữ tham chiếu ngầm định đến thực thể lớp ngoài.
- **Nhầm lẫn phổ biến**: Thường nhầm lẫn rằng lớp lồng tĩnh có thể truy cập trực tiếp vào các trường phi tĩnh của lớp ngoài. Thực tế nó chỉ có thể truy cập trực tiếp vào các thành viên tĩnh của lớp ngoài.
- **Ví dụ nhỏ**: Truy cập lớp lồng tĩnh bằng cú pháp: `OuterClass.StaticNestedClass nested = new OuterClass.StaticNestedClass();`.

## lớp nội (inner class)

Lớp nội (inner class) là một lớp lồng phi tĩnh (non-static nested class). Mỗi thực thể của lớp nội luôn liên kết chặt chẽ với một thực thể của lớp ngoài và giữ một tham chiếu ngầm định đến thực thể lớp ngoài đó.

- **Tầm quan trọng**: Cho phép lớp nội truy cập trực tiếp vào tất cả các trường và phương thức (kể cả private) của thực thể lớp ngoài.
- **Nhầm lẫn phổ biến**: Cố gắng khởi tạo lớp nội mà không thông qua thực thể của lớp ngoài, ví dụ như viết `new InnerClass()` từ ngữ cảnh tĩnh sẽ gây lỗi biên dịch.
- **Ví dụ nhỏ**: Phải khởi tạo thông qua thực thể lớp ngoài: `OuterClass outer = new OuterClass(); OuterClass.InnerClass inner = outer.new InnerClass();`.

## lớp cục bộ (local class)

Lớp cục bộ (local class) là một lớp nội được định nghĩa bên trong một khối mã nguồn cụ thể, thường là bên trong thân của một phương thức.

- **Tầm quan trọng**: Nó chỉ hiển thị và có thể sử dụng bên trong phạm vi khối mã nguồn mà nó được định nghĩa, giúp ẩn thông tin triệt để.
- **Nhầm lẫn phổ biến**: Không thể khai báo các bổ từ truy cập (như `public`, `private`, `protected`) hoặc bổ từ `static` trên lớp cục bộ.
- **Ví dụ nhỏ**: Định nghĩa một lớp xử lý tạm thời bên trong một phương thức phức tạp và khởi tạo sử dụng nó ngay bên dưới.

## lớp vô danh (anonymous class)

Lớp vô danh (anonymous class) là một lớp nội không có tên, được khai báo và khởi tạo đồng thời trong một biểu thức duy nhất bằng cách triển khai một giao diện hoặc kế thừa một lớp khác.

- **Tầm quan trọng**: Rất hữu ích khi bạn chỉ cần tạo ra một thực thể duy nhất sử dụng một lần (như các trình lắng nghe sự kiện hoặc luồng chạy tạm thời) mà không muốn tạo ra một tệp lớp riêng biệt.
- **Nhầm lẫn phổ biến**: Lớp vô danh không thể có hàm khởi tạo tự đặt tên vì bản thân lớp không có tên. Nó chỉ có thể sử dụng các hàm dựng mặc định hoặc truyền tham số cho hàm dựng của lớp cha.
- **Ví dụ nhỏ**: Khởi tạo một luồng chạy nhanh: `Runnable r = new Runnable() { @Override public void run() { System.out.println("Run"); } };`.

## sự thu giữ biến (variable capture)

Sự thu giữ biến (variable capture) là cơ chế cho phép các lớp nội (đặc biệt là lớp cục bộ và lớp vô danh) truy cập các biến cục bộ trong phạm vi bao bọc của phương thức chứa chúng.

- **Tầm quan trọng**: Giúp truyền tải trạng thái từ ngữ cảnh bên ngoài vào bên trong các hành vi xử lý của lớp lồng.
- **Nhầm lẫn phổ biến**: Các biến cục bộ được thu giữ bắt buộc phải là hằng số (`final`) hoặc hiệu dụng hằng số (`effectively final` - tức là không bị thay đổi giá trị sau khi gán). Nếu cố tình gán lại giá trị cho biến đã thu giữ, trình biên dịch sẽ báo lỗi.
- **Ví dụ nhỏ**:
  ```java
  int score = 100; // Hiệu dụng hằng số
  Runnable r = () -> System.out.println(score); // Thu giữ biến score thành công
  // score = 200; // Nếu bỏ chú thích dòng này sẽ gây lỗi biên dịch!
  ```
