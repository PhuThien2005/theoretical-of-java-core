# Thuật ngữ Kiểm thử đơn vị cơ bản (Basic Unit Testing Terms)

Hãy sử dụng tài liệu này khi một từ ngữ trong phần lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều đi kèm ý nghĩa, tầm quan trọng, sự nhầm lẫn phổ biến và một ví dụ nhỏ.

## JUnit (JUnit)

JUnit là một framework kiểm thử dành cho Java để viết và chạy các bài kiểm thử tự động.

**Tại sao nó quan trọng**: Các bài kiểm thử bảo vệ hành vi của mã nguồn khi có sự thay đổi. Một sự nhầm lẫn phổ biến là đi kiểm thử các chi tiết triển khai bên dưới (implementation details) thay vì kiểm thử các hành vi có thể quan sát được (observable behavior) của hệ thống.

**Sự nhầm lẫn phổ biến**: Học viên thường ghi nhớ `JUnit` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

**Ví dụ nhỏ**: Khi đọc mã nguồn, hãy tự hỏi: `JUnit` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Ca kiểm thử (Test case)

Ca kiểm thử (Test case) là một khái niệm cụ thể trong Kiểm thử đơn vị cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại (failure mode) của nó thay vì chỉ nhớ tên.

**Tại sao nó quan trọng**: Các bài kiểm thử bảo vệ hành vi của mã nguồn khi có sự thay đổi. Một sự nhầm lẫn phổ biến là đi kiểm thử các chi tiết triển khai thay vì kiểm thử các hành vi có thể quan sát được.

**Sự nhầm lẫn phổ biến**: Học viên thường ghi nhớ `test case` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

**Ví dụ nhỏ**: Khi đọc mã nguồn, hãy tự hỏi: `test case` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Khẳng định (Assertion)

Khẳng định (Assertion) là một khái niệm cụ thể trong Kiểm thử đơn vị cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

**Tại sao nó quan trọng**: Các bài kiểm thử bảo vệ hành vi của mã nguồn khi có sự thay đổi. Một sự nhầm lẫn phổ biến là đi kiểm thử các chi tiết triển khai thay vì kiểm thử các hành vi có thể quan sát được.

**Sự nhầm lẫn phổ biến**: Học viên thường ghi nhớ `assertion` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

**Ví dụ nhỏ**: Khi đọc mã nguồn, hãy tự hỏi: `assertion` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Fixture (Fixture)

Fixture (môi trường kiểm thử) là một khái niệm cụ thể trong Kiểm thử đơn vị cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

**Tại sao nó quan trọng**: Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và chế độ thất bại (failure mode). Xem xét nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

**Sự nhầm lẫn phổ biến**: Học viên thường ghi nhớ `fixture` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

**Ví dụ nhỏ**: Khi đọc mã nguồn, hãy tự hỏi: `fixture` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Mockito (Mockito)

Mockito là một framework giả lập (mocking framework) được sử dụng để cô lập các đơn vị được kiểm thử (units under test).

**Tại sao nó quan trọng**: Các bài kiểm thử bảo vệ hành vi của mã nguồn khi có sự thay đổi. Một sự nhầm lẫn phổ biến là đi kiểm thử các chi tiết triển khai thay vì kiểm thử các hành vi có thể quan sát được.

**Sự nhầm lẫn phổ biến**: Học viên thường ghi nhớ `Mockito` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

**Ví dụ nhỏ**: Khi đọc mã nguồn, hãy tự hỏi: `Mockito` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Đối tượng giả lập (Mock object)

Đối tượng giả lập (Mock object) là một khái niệm cụ thể trong Kiểm thử đơn vị cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

**Tại sao nó quan trọng**: Các bài kiểm thử bảo vệ hành vi của mã nguồn khi có sự thay đổi. Một sự nhầm lẫn phổ biến là đi kiểm thử các chi tiết triển khai thay vì kiểm thử các hành vi có thể quan sát được.

**Sự nhầm lẫn phổ biến**: Học viên thường ghi nhớ `mock object` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

**Ví dụ nhỏ**: Khi đọc mã nguồn, hãy tự hỏi: `mock object` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Độ bao phủ mã nguồn (Code coverage)

Độ bao phủ mã nguồn (Code coverage) là một khái niệm cụ thể trong Kiểm thử đơn vị cơ bản; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

**Tại sao nó quan trọng**: Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và chế độ thất bại (failure mode). Xem xét nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

**Sự nhầm lẫn phổ biến**: Học viên thường ghi nhớ `code coverage` như một từ vựng đơn thuần nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

**Ví dụ nhỏ**: Khi đọc mã nguồn, hãy tự hỏi: `code coverage` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?
