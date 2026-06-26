# Thuật ngữ API Ngày và Giờ (Date and Time API Terms)

Sử dụng file này khi một từ ngữ trong phần lý thuyết có cảm giác quá ngắn gọn. Mỗi thuật ngữ đều có ý nghĩa, tầm quan trọng, điểm dễ gây nhầm lẫn và một ví dụ nhỏ.

## LocalDate

`LocalDate` biểu diễn một ngày không có giờ hoặc múi giờ (time zone).

**Tại sao nó quan trọng:** Sử dụng nó để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi (failure mode). Xem xét nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

**Nhầm lẫn phổ biến:** Người học thường ghi nhớ `LocalDate` như một từ đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

**Ví dụ nhỏ:** Khi đọc mã nguồn (code), hãy hỏi: `LocalDate` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## LocalTime

`LocalTime` đại diện cho một giờ cụ thể không có ngày hoặc múi giờ (time zone); hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ lỗi của nó thay vì chỉ nhớ tên.

**Tại sao nó quan trọng:** Sử dụng nó để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi (failure mode). Xem xét nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

**Nhầm lẫn phổ biến:** Người học thường ghi nhớ `LocalTime` như một từ đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

**Ví dụ nhỏ:** Khi đọc mã nguồn (code), hãy hỏi: `LocalTime` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Instant

`Instant` biểu diễn một thời điểm (point) trên dòng thời gian UTC.

**Tại sao nó quan trọng:** Sử dụng nó để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi (failure mode). Xem xét nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

**Nhầm lẫn phổ biến:** Người học thường ghi nhớ `Instant` như một từ đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

**Ví dụ nhỏ:** Khi đọc mã nguồn (code), hãy hỏi: `Instant` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Duration

`Duration` biểu diễn một khoảng thời gian đo bằng giây và nano giây (dựa trên thời gian máy móc); hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ lỗi của nó thay vì chỉ nhớ tên.

**Tại sao nó quan trọng:** Sử dụng nó để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi (failure mode). Xem xét nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

**Nhầm lẫn phổ biến:** Người học thường ghi nhớ `Duration` như một từ đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

**Ví dụ nhỏ:** Khi đọc mã nguồn (code), hãy hỏi: `Duration` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Period

`Period` biểu diễn một khoảng thời gian bằng năm, tháng và ngày (dựa trên thời gian con người); hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ lỗi của nó thay vì chỉ nhớ tên.

**Tại sao nó quan trọng:** Sử dụng nó để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi (failure mode). Xem xét nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

**Nhầm lẫn phổ biến:** Người học thường ghi nhớ `Period` như một từ đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

**Ví dụ nhỏ:** Khi đọc mã nguồn (code), hãy hỏi: `Period` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## ZoneId

`ZoneId` đại diện cho một định danh múi giờ (time zone), được sử dụng để chuyển đổi giữa thời gian cục bộ và thời gian UTC; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ lỗi của nó thay vì chỉ nhớ tên.

**Tại sao nó quan trọng:** Sử dụng nó để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi (failure mode). Xem xét nó with a tiny example instead of memorizing only the label.

**Nhầm lẫn phổ biến:** Người học thường ghi nhớ `ZoneId` như một từ đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

**Ví dụ nhỏ:** Khi đọc mã nguồn (code), hãy hỏi: `ZoneId` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## DateTimeFormatter

`DateTimeFormatter` được sử dụng để phân tích cú pháp (parse) và định dạng (format) các đối tượng ngày-giờ; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ lỗi của nó thay vì chỉ nhớ tên.

**Tại sao nó quan trọng:** Sử dụng nó để dự đoán chính xác quy tắc Java, biểu mẫu được phép và chế độ lỗi (failure mode). Xem xét nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn tên.

**Nhầm lẫn phổ biến:** Người học thường ghi nhớ `DateTimeFormatter` như một từ đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc quy tắc nào nó thay đổi.

**Ví dụ nhỏ:** Khi đọc mã nguồn (code), hãy hỏi: `DateTimeFormatter` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?
