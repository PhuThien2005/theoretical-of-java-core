# Các Thuật Ngữ về Lớp Object (Object class)

Sử dụng tài liệu này khi một từ ngữ trong phần lý thuyết (theory) tạo cảm giác quá ngắn gọn. Mỗi thuật ngữ (term) đều có ý nghĩa (meaning), tầm quan trọng (importance), điểm gây mơ hồ (confusion) và một ví dụ (example) nhỏ.

## Lớp Object

**`Lớp Object`** — Lớp cha cao nhất của mọi lớp trong Java; tất cả các lớp đều kế thừa mặc định từ Object.

Tầm quan trọng: Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được phép (allowed form) và trường hợp thất bại. Hãy xem lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn (label) của nó.

Sự mơ hồ thường gặp: người học (learner) thường ghi nhớ `Object class` như một từ vựng nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy hỏi: `Object class` thay đổi, cho phép, từ chối hay làm rõ điều gì?

## Hợp đồng equals (equals contract)

`equals()` định nghĩa sự bằng nhau logic (logical equality) giữa các đối tượng (object).

Tầm quan trọng: Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được phép và trường hợp thất bại. Hãy xem lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Sự mơ hồ thường gặp: người học thường ghi nhớ `equals contract` như một từ vựng nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy hỏi: `equals contract` thay đổi, cho phép, từ chối hay làm rõ điều gì?

## Hợp đồng hashCode (hashCode contract)

`hashCode()` trả về một mã băm số nguyên (integer hash) được sử dụng bởi các bộ sưu tập dựa trên bảng băm (hash-based collections).

Tầm quan trọng: Điều này quan trọng vì việc chọn sai cấu trúc dữ liệu (data structure) sẽ làm thay đổi tính chính xác (correctness), hiệu năng (performance) và hành vi xử lý trùng lặp (duplicate-handling behavior). Một sự mơ hồ thường gặp là ghi nhớ tên lớp (class name) mà không biết thứ tự tra cứu (lookup order), quy tắc so sánh bằng (equality rule) hoặc hành vi duyệt (iteration behavior).

Sự mơ hồ thường gặp: người học thường ghi nhớ `hashCode contract` như một từ vựng nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy hỏi: `hashCode contract` thay đổi, cho phép, từ chối hay làm rõ điều gì?

## So sánh bằng tham chiếu (reference equality)

**`So sánh bằng tham chiếu`** — Kiểm tra xem hai biến tham chiếu có cùng trỏ tới một địa chỉ ô nhớ trên heap hay không (dùng ==).

Tầm quan trọng: Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được phép và trường hợp thất bại. Hãy xem lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Sự mơ hồ thường gặp: người học thường ghi nhớ `reference equality` như một từ vựng nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy hỏi: `reference equality` thay đổi, cho phép, từ chối hay làm rõ điều gì?

## So sánh bằng giá trị (value equality)

**`So sánh bằng giá trị`** — Kiểm tra xem hai đối tượng có chứa trạng thái/giá trị bằng nhau về mặt logic hay không (dùng .equals()).

Tầm quan trọng: Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được phép và trường hợp thất bại. Hãy xem lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Sự mơ hồ thường gặp: người học thường ghi nhớ `value equality` như một từ vựng nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy hỏi: `value equality` thay đổi, cho phép, từ chối hay làm rõ điều gì?

## Các phương thức giám sát (monitor methods)

**`Các phương thức giám sát`** — Các phương thức wait(), notify(), notifyAll() dùng để đồng bộ hóa thread.

Tầm quan trọng: Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được phép và trường hợp thất bại. Hãy xem lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Sự mơ hồ thường gặp: người học thường ghi nhớ `monitor methods` như một từ vựng nhưng không thể giải thích nó giải quyết vấn đề gì hoặc nó thay đổi quy tắc nào.

Ví dụ nhỏ: Khi đọc mã nguồn, hãy hỏi: `monitor methods` thay đổi, cho phép, từ chối hay làm rõ điều gì?
