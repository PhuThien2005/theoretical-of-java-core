# Thuật Ngữ - Xử Lý Ngoại Lệ (Exception Handling)

Dùng file này khi một từ trong tài liệu lý thuyết cảm thấy quá súc tích. Mỗi thuật ngữ đều có ý nghĩa, tầm quan trọng, điểm gây nhầm lẫn, và ví dụ nhỏ.

## checked exception (Ngoại lệ bắt buộc xử lý)

Ngoại lệ bắt buộc xử lý (checked exception) là loại ngoại lệ phải được xử lý hoặc khai báo theo quy tắc của trình biên dịch.

Tại sao quan trọng: Quan trọng vì hành vi ngoại lệ quyết định liệu lỗi được xử lý cục bộ, lan truyền, hay được phép dừng chương trình. Nhầm lẫn thường gặp là xử lý mọi ngoại lệ như nhau thay vì phân biệt điều kiện có thể phục hồi với lỗi lập trình.

Nhầm lẫn thường gặp: Người học thường ghi nhớ `checked exception` như một từ nhưng không thể giải thích vấn đề nào nó giải quyết hoặc quy tắc nào nó thay đổi.

Ví dụ nhỏ: Khi đọc code, hỏi: `checked exception` thay đổi, cho phép, từ chối, hay làm rõ điều gì?

## unchecked exception (Ngoại lệ không bắt buộc xử lý)

Ngoại lệ không bắt buộc xử lý (unchecked exception) là loại ngoại lệ không cần phải bắt hoặc khai báo.

Tại sao quan trọng: Quan trọng vì hành vi ngoại lệ quyết định liệu lỗi được xử lý cục bộ, lan truyền, hay được phép dừng chương trình. Nhầm lẫn thường gặp là xử lý mọi ngoại lệ như nhau thay vì phân biệt điều kiện có thể phục hồi với lỗi lập trình.

Nhầm lẫn thường gặp: Người học thường ghi nhớ `unchecked exception` như một từ nhưng không thể giải thích vấn đề nào nó giải quyết hoặc quy tắc nào nó thay đổi.

Ví dụ nhỏ: Khi đọc code, hỏi: `unchecked exception` thay đổi, cho phép, từ chối, hay làm rõ điều gì?

## runtime exception (Ngoại lệ thời gian chạy)

Ngoại lệ (exception) đại diện cho một điều kiện bất thường mà chương trình có thể bắt hoặc lan truyền.

Tại sao quan trọng: Quan trọng vì hành vi ngoại lệ quyết định liệu lỗi được xử lý cục bộ, lan truyền, hay được phép dừng chương trình. Nhầm lẫn thường gặp là xử lý mọi ngoại lệ như nhau thay vì phân biệt điều kiện có thể phục hồi với lỗi lập trình.

Nhầm lẫn thường gặp: Người học thường ghi nhớ `runtime exception` như một từ nhưng không thể giải thích vấn đề nào nó giải quyết hoặc quy tắc nào nó thay đổi.

Ví dụ nhỏ: Khi đọc code, hỏi: `runtime exception` thay đổi, cho phép, từ chối, hay làm rõ điều gì?

## finally (Khối dọn dẹp)

`final` có nghĩa là biến, phương thức, lớp, hoặc tham số bị hạn chế thay đổi sau đó theo một cách cụ thể.

Tại sao quan trọng: Dùng nó để dự đoán quy tắc Java chính xác, dạng được phép, và chế độ lỗi. Xem lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn.

Nhầm lẫn thường gặp: Người học thường ghi nhớ `finally` như một từ nhưng không thể giải thích vấn đề nào nó giải quyết hoặc quy tắc nào nó thay đổi.

Ví dụ nhỏ: `final int limit = 10;` không thể được gán lại.

## exception propagation (Lan truyền ngoại lệ)

Ngoại lệ (exception) đại diện cho một điều kiện bất thường mà chương trình có thể bắt hoặc lan truyền.

Tại sao quan trọng: Quan trọng vì hành vi ngoại lệ quyết định liệu lỗi được xử lý cục bộ, lan truyền, hay được phép dừng chương trình. Nhầm lẫn thường gặp là xử lý mọi ngoại lệ như nhau thay vì phân biệt điều kiện có thể phục hồi với lỗi lập trình.

Nhầm lẫn thường gặp: Người học thường ghi nhớ `exception propagation` như một từ nhưng không thể giải thích vấn đề nào nó giải quyết hoặc quy tắc nào nó thay đổi.

Ví dụ nhỏ: Khi đọc code, hỏi: `exception propagation` thay đổi, cho phép, từ chối, hay làm rõ điều gì?

## try-with-resources (Thử-với-tài-nguyên)

Try-with-resources tự động đóng các tài nguyên implement `AutoCloseable`.

Tại sao quan trọng: Dùng nó để dự đoán quy tắc Java chính xác, dạng được phép, và chế độ lỗi. Xem lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn.

Nhầm lẫn thường gặp: Người học thường ghi nhớ `try-with-resources` như một từ nhưng không thể giải thích vấn đề nào nó giải quyết hoặc quy tắc nào nó thay đổi.

Ví dụ nhỏ: `try { ... } catch (IOException ex) { ... }` xử lý một đường dẫn lỗi cụ thể.
