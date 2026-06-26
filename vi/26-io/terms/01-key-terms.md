# Thuật ngữ IO trong Java (IO in Java Terms)

Sử dụng file này khi một từ ngữ trong phần lý thuyết có cảm giác quá ngắn gọn. Mỗi thuật ngữ đều có ý nghĩa, tầm quan trọng, điểm dễ gây nhầm lẫn và một ví dụ nhỏ.

## stream

Một luồng (Stream) là một đường ống để xử lý các phần tử thông qua các thao tác lười (lazy operations).

**Tại sao nó quan trọng:** Nó quan trọng vì các API Java hiện đại sử dụng nhiều đường ống theo phong cách hàm (function-style pipelines). Một sự nhầm lẫn phổ biến là quên mất thao tác nào là thao tác lười và thao tác nào thực sự kích hoạt quá trình thực thi.

**Nhầm lẫn phổ biến:** Người học thường ghi nhớ `stream` như một từ đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

**Ví dụ nhỏ:** Khi đọc mã nguồn (code), hãy hỏi: `stream` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## reader

`reader` là một khái niệm cụ thể trong IO trong Java; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ lỗi của nó thay vì chỉ nhớ tên.

**Tại sao nó quan trọng:** Sử dụng nó để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi (failure mode). Xem xét nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

**Nhầm lẫn phổ biến:** Người học thường ghi nhớ `reader` như một từ đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

**Ví dụ nhỏ:** Khi đọc mã nguồn (code), hãy hỏi: `reader` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## writer

`writer` là một khái niệm cụ thể trong IO trong Java; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ lỗi của nó thay vì chỉ nhớ tên.

**Tại sao nó quan trọng:** Sử dụng nó để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi (failure mode). Xem xét nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

**Nhầm lẫn phổ biến:** Người học thường ghi nhớ `writer` như một từ đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

**Ví dụ nhỏ:** Khi đọc mã nguồn (code), hãy hỏi: `writer` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## buffering

`buffering` là một khái niệm cụ thể trong IO trong Java; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ lỗi của nó thay vì chỉ nhớ tên.

**Tại sao nó quan trọng:** Sử dụng nó để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi (failure mode). Xem xét nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

**Nhầm lẫn phổ biến:** Người học thường ghi nhớ `buffering` như một từ đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

**Ví dụ nhỏ:** Khi đọc mã nguồn (code), hãy hỏi: `buffering` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## serialization

Tuần tự hóa (Serialization) chuyển đổi một đồ thị đối tượng (object graph) thành các byte để nó có thể được lưu trữ hoặc truyền tải.

**Tại sao nó quan trọng:** Sử dụng nó để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi (failure mode). Xem xét nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

**Nhầm lẫn phổ biến:** Người học thường ghi nhớ `serialization` như một từ đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

**Ví dụ nhỏ:** Khi đọc mã nguồn (code), hãy hỏi: `serialization` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## transient

`transient` đánh dấu một trường (field) cần được bỏ qua trong quá trình tuần tự hóa Java.

**Tại sao nó quan trọng:** Sử dụng nó để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi (failure mode). Xem xét nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

**Nhầm lẫn phổ biến:** Người học thường ghi nhớ `transient` như một từ đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

**Ví dụ nhỏ:** Khi đọc mã nguồn (code), hãy hỏi: `transient` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## serialVersionUID

`serialVersionUID` là một khái niệm cụ thể trong IO trong Java; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ lỗi của nó thay vì chỉ nhớ tên.

**Tại sao nó quan trọng:** Sử dụng nó để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi (failure mode). Xem xét nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

**Nhầm lẫn phổ biến:** Người học thường ghi nhớ `serialVersionUID` như một từ đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

**Ví dụ nhỏ:** Khi đọc mã nguồn (code), hãy hỏi: `serialVersionUID` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?
