# Thuật ngữ các Khái Niệm Java Hiện Đại Cần Biết (Modern Java Concepts To Know Terms)

Sử dụng tài liệu này khi một từ ngữ trong lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều có ý nghĩa, tầm quan trọng, điểm dễ nhầm lẫn và một ví dụ nhỏ.

## record

`record` là một lớp Java rút gọn đóng vai trò là vật chứa dữ liệu bất biến (immutable data carriers).

- **Tầm quan trọng**: Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được cho phép, và chế độ thất bại. Hãy xem lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.
- **Nhầm lẫn thường gặp**: Người học thường ghi nhớ `record` như một từ ngữ nhưng không thể giải thích nó giải quyết vấn đề gì hoặc thay đổi quy tắc nào.
- **Ví dụ nhỏ**: Khi đọc code, hãy hỏi: `record` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

## sealed class (lớp niêm phong)

`sealed class` giới hạn lớp nào có thể kế thừa hoặc triển khai nó.

- **Tầm quan trọng**: Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được cho phép, và chế độ thất bại. Hãy xem lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.
- **Nhầm lẫn thường gặp**: Người học thường ghi nhớ `sealed class` như một từ ngữ nhưng không thể giải thích nó giải quyết vấn đề gì hoặc thay đổi quy tắc nào.
- **Ví dụ nhỏ**: Khi đọc code, hãy hỏi: `sealed class` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

## pattern matching (khớp mẫu)

`pattern matching` là một khái niệm cụ thể trong các Khái Niệm Java Hiện Đại Cần Biết; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi.

- **Tầm quan trọng**: Hãy sử dụng nó để dự đoán chính xác quy tắc Java, dạng thức được cho phép, và chế độ thất bại. Hãy xem lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.
- **Nhầm lẫn thường gặp**: Người học thường ghi nhớ `pattern matching` như một từ ngữ nhưng không thể giải thích nó giải quyết vấn đề gì hoặc thay đổi quy tắc nào.
- **Ví dụ nhỏ**: Khi đọc code, hãy hỏi: `pattern matching` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

## text block (khối văn bản)

`text block` là một khái niệm cụ thể trong các Khái Niệm Java Hiện Đại Cần Biết; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ mỗi tên gọi.

- **Tầm quan trọng**: Khái niệm này quan trọng vì mã nguồn đồng thời có vẻ chạy đúng trong các kiểm thử đơn luồng nhưng có thể thất bại dưới áp lực thời gian chạy. Một nhầm lẫn phổ biến là giả định tính hiển thị (visibility), thứ tự (ordering), và tính nguyên tử (atomicity) đều mang lại sự bảo đảm như nhau.
- **Nhầm lẫn thường gặp**: Người học thường ghi nhớ `text block` như một từ ngữ nhưng không thể giải thích nó giải quyết vấn đề gì hoặc thay đổi quy tắc nào.
- **Ví dụ nhỏ**: Khi đọc code, hãy hỏi: `text block` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

## virtual thread (luồng ảo)

Luồng (thread) là một đường dẫn thực thi bên trong một tiến trình.

- **Tầm quan trọng**: Khái niệm này quan trọng vì mã nguồn đồng thời có vẻ chạy đúng trong các kiểm thử đơn luồng nhưng có thể thất bại dưới áp lực thời gian chạy. Một nhầm lẫn phổ biến là giả định tính hiển thị (visibility), thứ tự (ordering), và tính nguyên tử (atomicity) đều mang lại sự bảo đảm như nhau.
- **Nhầm lẫn thường gặp**: Người học thường ghi nhớ `virtual thread` như một từ ngữ nhưng không thể giải thích nó giải quyết vấn đề gì hoặc thay đổi quy tắc nào.
- **Ví dụ nhỏ**: `new Thread(task).start()` bắt đầu công việc trên một luồng khác.

## sequenced collection (bộ sưu tập có thứ tự)

Một bộ sưu tập (collection) là một đối tượng nhóm nhiều phần tử lại dưới một API chung.

- **Tầm quan trọng**: Việc chọn sai cấu trúc dữ liệu sẽ làm thay đổi tính chính xác, hiệu năng, và hành vi xử lý trùng lặp. Một nhầm lẫn phổ biến là ghi nhớ tên các lớp mà không biết thứ tự tìm kiếm, quy tắc bằng nhau, hoặc hành vi lặp.
- **Nhầm lẫn thường gặp**: Người học thường ghi nhớ `sequenced collection` như một từ ngữ nhưng không thể giải thích nó giải quyết vấn đề gì hoặc thay đổi quy tắc nào.
- **Ví dụ nhỏ**: Khi đọc code, hãy hỏi: `sequenced collection` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?
