# Thuật Ngữ Giao Diện Chức Năng (Functional Interface Terms)

Sử dụng file này khi một từ trong phần lý thuyết có cảm giác quá ngắn gọn. Mỗi thuật ngữ đều đi kèm với ý nghĩa, tầm quan trọng, điểm dễ gây nhầm lẫn và một ví dụ nhỏ.

## Predicate

`Predicate` (Hàm điều kiện) là một khái niệm cụ thể trong Giao diện chức năng (Functional Interface); hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại (failure mode) của nó thay vì chỉ nhớ mỗi tên gọi.

Tại sao điều này quan trọng: Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều các đường ống (pipeline) kiểu lập trình hàm. Một nhầm lẫn phổ biến là quên mất hoạt động nào là lười (lazy) và hoạt động nào thực sự kích hoạt việc thực thi.

Nhầm lẫn phổ biến: Người học thường ghi nhớ `Predicate` như một từ vựng thuần túy nhưng không thể giải thích nó giải quyết vấn đề gì hoặc làm thay đổi quy tắc nào.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy tự hỏi: `Predicate` thay đổi, cho phép, từ chối hay làm rõ điều gì?

## Function

`Function` (Hàm chuyển đổi) là một khái niệm cụ thể trong Giao diện chức năng; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ mỗi tên gọi.

Tại sao điều này quan trọng: Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều các đường ống kiểu lập trình hàm. Một nhầm lẫn phổ biến là quên mất hoạt động nào là lười (lazy) và hoạt động nào thực sự kích hoạt việc thực thi.

Nhầm lẫn phổ biến: Người học thường ghi nhớ `Function` như một từ vựng nhưng không thể giải thích nó giải quyết vấn đề gì hoặc làm thay đổi quy tắc nào.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy tự hỏi: `Function` thay đổi, cho phép, từ chối hay làm rõ điều gì?

## Consumer

`Consumer` (Hàm tiêu thụ) là một khái niệm cụ thể trong Giao diện chức năng; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ mỗi tên gọi.

Tại sao điều này quan trọng: Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều các đường ống kiểu lập trình hàm. Một nhầm lẫn phổ biến là quên mất hoạt động nào là lười (lazy) và hoạt động nào thực sự kích hoạt việc thực thi.

Nhầm lẫn phổ biến: Người học thường ghi nhớ `Consumer` như một từ vựng nhưng không thể giải thích nó giải quyết vấn đề gì hoặc làm thay đổi quy tắc nào.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy tự hỏi: `Consumer` thay đổi, cho phép, từ chối hay làm rõ điều gì?

## Supplier

`Supplier` (Hàm cung cấp) là một khái niệm cụ thể trong Giao diện chức năng; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ mỗi tên gọi.

Tại sao điều này quan trọng: Nó quan trọng vì các API Java hiện đại sử dụng rất nhiều các đường ống kiểu lập trình hàm. Một nhầm lẫn phổ biến là quên mất hoạt động nào là lười (lazy) và hoạt động nào thực sự kích hoạt việc thực thi.

Nhầm lẫn phổ biến: Người học thường ghi nhớ `Supplier` như một từ vựng nhưng không thể giải thích nó giải quyết vấn đề gì hoặc làm thay đổi quy tắc nào.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy tự hỏi: `Supplier` thay đổi, cho phép, từ chối hay làm rõ điều gì?

## Operator

`Operator` (Hàm toán tử) là một khái niệm cụ thể trong Giao diện chức năng; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ mỗi tên gọi.

Tại sao điều này quan trọng: Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được phép và chế độ thất bại. Hãy ôn tập lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Nhầm lẫn phổ biến: Người học thường ghi nhớ `Operator` như một từ vựng nhưng không thể giải thích nó giải quyết vấn đề gì hoặc làm thay đổi quy tắc nào.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy tự hỏi: `Operator` thay đổi, cho phép, từ chối hay làm rõ điều gì?

## Bi-interface

`Bi-interface` (Giao diện kép) là một khái niệm cụ thể trong Giao diện chức năng; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ mỗi tên gọi.

Tại sao điều này quan trọng: Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được phép và chế độ thất bại. Hãy ôn tập lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Nhầm lẫn phổ biến: Người học thường ghi nhớ `Bi-interface` như một từ vựng nhưng không thể giải thích nó giải quyết vấn đề gì hoặc làm thay đổi quy tắc nào.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy tự hỏi: `Bi-interface` thay đổi, cho phép, từ chối hay làm rõ điều gì?
