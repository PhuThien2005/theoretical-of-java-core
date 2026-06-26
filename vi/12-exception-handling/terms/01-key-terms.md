# Thuật ngữ Xử lý ngoại lệ (Exception Handling Terms)

Sử dụng tập tin này khi một từ trong lý thuyết có cảm giác quá ngắn gọn. Mỗi thuật ngữ đều có ý nghĩa, tầm quan trọng, điểm dễ nhầm lẫn và một ví dụ nhỏ.

## checked exception (ngoại lệ kiểm tra)

Một ngoại lệ kiểm tra (checked exception) bắt buộc phải được xử lý hoặc khai báo theo các quy tắc của trình biên dịch.

Tại sao nó quan trọng: Điều này quan trọng vì hành vi của ngoại lệ quyết định xem các lỗi được xử lý cục bộ, truyền đi tiếp, hay cho phép dừng chương trình. Sự nhầm lẫn phổ biến là đối xử với mọi ngoại lệ như nhau thay vì tách biệt các điều kiện có thể phục hồi khỏi các lỗi lập trình.

Điểm nhầm lẫn phổ biến: Người học thường ghi nhớ `checked exception` như một từ khóa nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy hỏi: `checked exception` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## unchecked exception (ngoại lệ không kiểm tra)

Một ngoại lệ không kiểm tra (unchecked exception) không bắt buộc phải được bắt hoặc khai báo.

Tại sao nó quan trọng: Điều này quan trọng vì hành vi của ngoại lệ quyết định xem các lỗi được xử lý cục bộ, truyền đi tiếp, hay cho phép dừng chương trình. Sự nhầm lẫn phổ biến là đối xử với mọi ngoại lệ như nhau thay vì tách biệt các điều kiện có thể phục hồi khỏi các lỗi lập trình.

Điểm nhầm lẫn phổ biến: Người học thường ghi nhớ `unchecked exception` như một từ khóa nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy hỏi: `unchecked exception` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## runtime exception (ngoại lệ thời gian chạy)

Một ngoại lệ đại diện cho một điều kiện bất thường mà chương trình có thể bắt hoặc truyền đi tiếp.

Tại sao nó quan trọng: Điều này quan trọng vì hành vi của ngoại lệ quyết định xem các lỗi được xử lý cục bộ, truyền đi tiếp, hay cho phép dừng chương trình. Sự nhầm lẫn phổ biến là đối xử với mọi ngoại lệ như nhau thay vì tách biệt các điều kiện có thể phục hồi khỏi các lỗi lập trình.

Điểm nhầm lẫn phổ biến: Người học thường ghi nhớ `runtime exception` như một từ khóa nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy hỏi: `runtime exception` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## finally

Finally (khối cuối cùng) đảm bảo mã dọn dẹp tài nguyên luôn được thực thi. (Lưu ý: Mô tả gốc tiếng Anh mô tả từ khóa `final`, nghĩa là biến, phương thức, lớp hoặc tham số bị hạn chế thay đổi sau đó theo một cách cụ thể).

Tại sao nó quan trọng: Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại. Hãy xem lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn.

Điểm nhầm lẫn phổ biến: Người học thường ghi nhớ `finally` như một từ khóa nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

Ví dụ nhỏ: `final int limit = 10;` không thể bị gán lại giá trị.

## exception propagation (truyền ngoại lệ)

Một ngoại lệ đại diện cho một điều kiện bất thường mà chương trình có thể bắt hoặc truyền đi tiếp.

Tại sao nó quan trọng: Điều này quan trọng vì hành vi của ngoại lệ quyết định xem các lỗi được xử lý cục bộ, truyền đi tiếp, hay cho phép dừng chương trình. Sự nhầm lẫn phổ biến là đối xử với mọi ngoại lệ như nhau thay vì tách biệt các điều kiện có thể phục hồi khỏi các lỗi lập trình.

Điểm nhầm lẫn phổ biến: Người học thường ghi nhớ `exception propagation` như một từ khóa nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy hỏi: `exception propagation` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## try-with-resources

Try-with-resources tự động đóng các tài nguyên triển khai giao diện `AutoCloseable`.

Tại sao nó quan trọng: Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại. Hãy xem lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn.

Điểm nhầm lẫn phổ biến: Người học thường ghi nhớ `try-with-resources` như một từ khóa nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

Ví dụ nhỏ: `try { ... } catch (IOException ex) { ... }` xử lý một đường dẫn lỗi cụ thể.
