# Thuật ngữ về Gói và Kiểm soát truy cập (Package and Access Control Terms)

Sử dụng tài liệu này khi một từ khóa trong phần lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều có định nghĩa, tầm quan trọng, hiểu lầm thường gặp và một ví dụ nhỏ.

## gói (package)

Một gói (package) nhóm các lớp có liên quan lại với nhau và cung cấp cho chúng một không gian tên (namespace).

* **Tầm quan trọng**: Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và trạng thái lỗi. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn dán.
* **Hiểu lầm thường gặp**: Người học thường ghi nhớ từ `package` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.
* **Ví dụ nhỏ**: Khi đọc mã nguồn, hãy hỏi: `package` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## nhập (import)

nhập (import) là một khái niệm cụ thể trong Gói và Kiểm soát truy cập; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và trạng thái lỗi thay vì chỉ nhớ tên của nó.

* **Tầm quan trọng**: Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và trạng thái lỗi. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn dán.
* **Hiểu lầm thường gặp**: Người học thường ghi nhớ từ `import` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.
* **Ví dụ nhỏ**: Khi đọc mã nguồn, hãy hỏi: `import` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## nhập tĩnh (static import)

Tĩnh (static) nghĩa là thành viên thuộc về kiểu lớp thay vì một đối tượng cụ thể.

* **Tầm quan trọng**: Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và trạng thái lỗi. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn dán.
* **Hiểu lầm thường gặp**: Người học thường ghi nhớ từ `static import` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.
* **Ví dụ nhỏ**: `ClassName.member` truy cập vào một thành viên ở cấp độ lớp.

## gói mặc định (default package)

Một gói mặc định (default package) nhóm các lớp có liên quan lại với nhau và cung cấp cho chúng một không gian tên (namespace).

* **Tầm quan trọng**: Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và trạng thái lỗi. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn dán.
* **Hiểu lầm thường gặp**: Người học thường ghi nhớ cụm từ `default package` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.
* **Ví dụ nhỏ**: Khi đọc mã nguồn, hãy hỏi: `default package` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## đường dẫn lớp (classpath)

Đường dẫn lớp (classpath) cho biết cho JVM và trình biên dịch biết nơi tìm kiếm các lớp và các tệp JAR.

* **Tầm quan trọng**: Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và trạng thái lỗi. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn dán.
* **Hiểu lầm thường gặp**: Người học thường ghi nhớ từ `classpath` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.
* **Ví dụ nhỏ**: Khi đọc mã nguồn, hãy hỏi: `classpath` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## đường dẫn mô-đun (module path)

Đường dẫn mô-đun (module path) là giải pháp thay thế có nhận thức về hệ thống mô-đun cho classpath đối với các mô-đun có tên.

* **Tầm quan trọng**: Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và trạng thái lỗi. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ học vẹt nhãn dán.
* **Hiểu lầm thường gặp**: Người học thường ghi nhớ cụm từ `module path` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.
* **Ví dụ nhỏ**: Khi đọc mã nguồn, hãy hỏi: `module path` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?
