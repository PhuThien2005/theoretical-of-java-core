# Các Thuật Ngữ Biểu Thức Lambda (Lambda Expression Terms)

Sử dụng tài liệu này khi một từ ngữ trong phần lý thuyết có vẻ quá ngắn gọn. Mỗi thuật ngữ đều đi kèm ý nghĩa, tầm quan trọng, hiểu lầm phổ biến và một ví dụ nhỏ.

## Biểu thức Lambda (Lambda)

Một biểu thức lambda là một khối mã dạng hàm ngắn gọn được sử dụng ở những nơi yêu cầu một giao diện chức năng (Functional interface).

**Tại sao điều này quan trọng:** Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều các đường ống xử lý dạng hàm (Function-style pipeline). Một hiểu lầm phổ biến là quên mất thao tác nào là lười (lazy) và thao tác nào thực sự kích hoạt quá trình thực thi.

**Hiểu lầm phổ biến:** Người học thường ghi nhớ từ `lambda` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc làm thay đổi quy tắc nào.

**Ví dụ nhỏ:** `n -> n > 0` là một biểu thức lambda được sử dụng làm điều kiện lọc (Predicate).

## Suy luận kiểu đích (Target typing)

Suy luận kiểu đích là một khái niệm cụ thể trong Biểu thức Lambda; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và kịch bản thất bại thay vì chỉ ghi nhớ tên gọi của nó.

**Tại sao điều này quan trọng:** Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng thức được cho phép và kịch bản thất bại. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn mô tả thuần túy.

**Hiểu lầm phổ biến:** Người học thường ghi nhớ từ `suy luận kiểu đích` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc làm thay đổi quy tắc nào.

**Ví dụ nhỏ:** Khi đọc mã nguồn, hãy tự hỏi: `suy luận kiểu đích` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Tham chiếu phương thức (Method reference)

Tham chiếu phương thức là một khái niệm cụ thể trong Biểu thức Lambda; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và kịch bản thất bại thay vì chỉ ghi nhớ tên gọi của nó.

**Tại sao điều này quan trọng:** Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng thức được cho phép và kịch bản thất bại. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn mô tả thuần túy.

**Hiểu lầm phổ biến:** Người học thường ghi nhớ từ `tham chiếu phương thức` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc làm thay đổi quy tắc nào.

**Ví dụ nhỏ:** Khi đọc mã nguồn, hãy tự hỏi: `tham chiếu phương thức` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Bắt giữ biến (Variable capture)

Bắt giữ biến là một khái niệm cụ thể trong Biểu thức Lambda; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và kịch bản thất bại thay vì chỉ ghi nhớ tên gọi của nó.

**Tại sao điều này quan trọng:** Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng thức được cho phép và kịch bản thất bại. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn mô tả thuần túy.

**Hiểu lầm phổ biến:** Người học thường ghi nhớ từ `bắt giữ biến` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc làm thay đổi quy tắc nào.

**Ví dụ nhỏ:** Khi đọc mã nguồn, hãy tự hỏi: `bắt giữ biến` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Chung cuộc trên thực tế (Effectively final)

Chung cuộc (Final) có nghĩa là biến, phương thức, lớp, hoặc tham số bị hạn chế thay đổi sau đó theo một cách cụ thể.

**Tại sao điều này quan trọng:** Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng thức được cho phép và kịch bản thất bại. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn mô tả thuần túy.

**Hiểu lầm phổ biến:** Người học thường ghi nhớ từ `chung cuộc trên thực tế` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc làm thay đổi quy tắc nào.

**Ví dụ nhỏ:** `final int limit = 10;` không thể bị gán lại giá trị khác.
