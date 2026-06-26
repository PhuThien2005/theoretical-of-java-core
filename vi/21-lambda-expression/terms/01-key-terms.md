# Các Thuật Ngữ Về Biểu Thức Lambda (Lambda Expression Terms)

Sử dụng tài liệu này khi một từ ngữ trong phần lý thuyết có vẻ quá ngắn gọn. Mỗi thuật ngữ đều có định nghĩa, tầm quan trọng, điểm dễ nhầm lẫn và một ví dụ nhỏ đi kèm.

## lambda

Một biểu thức lambda là một khối giống như hàm nhỏ gọn được sử dụng ở những nơi mong đợi một interface chức năng (functional interface).

Tại sao nó quan trọng: Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều các đường ống (pipeline) kiểu hàm. Một sự nhầm lẫn phổ biến là quên mất thao tác nào là lười biếng (lazy evaluation) và thao tác nào thực sự kích hoạt việc thực thi.

Điểm dễ nhầm lẫn: Người học thường ghi nhớ từ `lambda` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

Ví dụ nhỏ: `n -> n > 0` là một lambda được sử dụng làm vị từ (predicate).

## kiểu mục tiêu (target typing)

Kiểu mục tiêu (Target typing) là một khái niệm cụ thể trong Biểu thức Lambda; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Tại sao nó quan trọng: Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

Điểm dễ nhầm lẫn: Người học thường ghi nhớ từ `target typing` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy hỏi: `target typing` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## tham chiếu phương thức (method reference)

Tham chiếu phương thức (Method reference) là một khái niệm cụ thể trong Biểu thức Lambda; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Tại sao nó quan trọng: Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

Điểm dễ nhầm lẫn: Người học thường ghi nhớ từ `method reference` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy hỏi: `method reference` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## chụp biến (variable capture)

Chụp biến (Variable capture) là một khái niệm cụ thể trong Biểu thức Lambda; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và lỗi thường gặp của nó thay vì chỉ biết mỗi tên gọi.

Tại sao nó quan trọng: Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

Điểm dễ nhầm lẫn: Người học thường ghi nhớ từ `variable capture` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy hỏi: `variable capture` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## hiệu dụng final (effectively final)

Hiệu dụng final (Effectively final) có nghĩa là biến, phương thức, lớp hoặc tham số bị hạn chế thay đổi sau đó theo một cách cụ thể.

Tại sao nó quan trọng: Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và lỗi thường gặp. Hãy ôn tập nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

Điểm dễ nhầm lẫn: Người học thường ghi nhớ từ `effectively final` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

Ví dụ nhỏ: `final int limit = 10;` không thể bị gán lại giá trị khác.
