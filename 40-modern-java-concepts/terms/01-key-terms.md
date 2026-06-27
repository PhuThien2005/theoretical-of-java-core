# Thuật Ngữ Các Khái Niệm Java Hiện Đại Cần Biết

Dùng file này khi một từ trong lý thuyết cảm thấy quá ngắn gọn. Mỗi thuật ngữ đều có nghĩa, tầm quan trọng, điểm gây nhầm lẫn và một ví dụ nhỏ.

## record

Một record (bản ghi) là lớp Java compact dùng để lưu trữ dữ liệu bất biến (immutable data carrier).

Tại sao quan trọng: Dùng để dự đoán quy tắc Java chính xác, dạng được phép và trường hợp thất bại. Ôn lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

Nhầm lẫn phổ biến: Người học thường ghi nhớ `record` như một từ khóa nhưng không giải thích được vấn đề nó giải quyết hay quy tắc nó thay đổi.

Ví dụ nhỏ: Khi đọc code, hãy hỏi: `record` thay đổi, cho phép, từ chối, hay làm rõ điều gì?

## sealed class

Một sealed class (lớp kín) giới hạn những lớp nào có thể kế thừa (extend) hoặc triển khai (implement) nó.

Tại sao quan trọng: Dùng để dự đoán quy tắc Java chính xác, dạng được phép và trường hợp thất bại. Ôn lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

Nhầm lẫn phổ biến: Người học thường ghi nhớ `sealed class` như một từ khóa nhưng không giải thích được vấn đề nó giải quyết hay quy tắc nó thay đổi.

Ví dụ nhỏ: Khi đọc code, hãy hỏi: `sealed class` thay đổi, cho phép, từ chối, hay làm rõ điều gì?

## pattern matching

Pattern matching (khớp mẫu) là một khái niệm cụ thể trong các tính năng Java hiện đại; hãy học quy tắc Java, các trường hợp dùng hợp lệ, và trường hợp thất bại thay vì chỉ ghi nhớ tên.

Tại sao quan trọng: Dùng để dự đoán quy tắc Java chính xác, dạng được phép và trường hợp thất bại. Ôn lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

Nhầm lẫn phổ biến: Người học thường ghi nhớ `pattern matching` như một từ khóa nhưng không giải thích được vấn đề nó giải quyết hay quy tắc nó thay đổi.

Ví dụ nhỏ: Khi đọc code, hãy hỏi: `pattern matching` thay đổi, cho phép, từ chối, hay làm rõ điều gì?

## text block

Text block (khối văn bản) là một khái niệm cụ thể trong các tính năng Java hiện đại; hãy học quy tắc Java, các trường hợp dùng hợp lệ, và trường hợp thất bại thay vì chỉ ghi nhớ tên.

Tại sao quan trọng: Quan trọng vì code đồng thời (concurrent) có thể trông đúng trong kiểm thử đơn luồng (single-thread) nhưng lại thất bại khi có áp lực về thời gian. Nhầm lẫn phổ biến là cho rằng visibility, ordering, và atomicity là cùng một đảm bảo.

Nhầm lẫn phổ biến: Người học thường ghi nhớ `text block` như một từ khóa nhưng không giải thích được vấn đề nó giải quyết hay quy tắc nó thay đổi.

Ví dụ nhỏ: Khi đọc code, hãy hỏi: `text block` thay đổi, cho phép, từ chối, hay làm rõ điều gì?

## virtual thread

Một thread (luồng) là một đường thực thi bên trong một process (tiến trình).

Tại sao quan trọng: Quan trọng vì code đồng thời có thể trông đúng trong kiểm thử đơn luồng nhưng lại thất bại khi có áp lực về thời gian. Nhầm lẫn phổ biến là cho rằng visibility, ordering, và atomicity là cùng một đảm bảo.

Nhầm lẫn phổ biến: Người học thường ghi nhớ `virtual thread` (luồng ảo) như một từ khóa nhưng không giải thích được vấn đề nó giải quyết hay quy tắc nó thay đổi.

Ví dụ nhỏ: `new Thread(task).start()` khởi động công việc trên một thread khác.

## sequenced collection

Một collection (tập hợp) là đối tượng nhóm nhiều phần tử lại dưới một API chung.

Tại sao quan trọng: Quan trọng vì việc chọn sai cấu trúc dữ liệu thay đổi tính đúng đắn, hiệu suất và hành vi xử lý phần tử trùng lặp. Nhầm lẫn phổ biến là ghi nhớ tên lớp mà không hiểu thứ tự tra cứu, quy tắc bằng nhau, hay hành vi duyệt.

Nhầm lẫn phổ biến: Người học thường ghi nhớ `sequenced collection` (tập hợp có thứ tự) như một từ khóa nhưng không giải thích được vấn đề nó giải quyết hay quy tắc nó thay đổi.

Ví dụ nhỏ: Khi đọc code, hãy hỏi: `sequenced collection` thay đổi, cho phép, từ chối, hay làm rõ điều gì?
