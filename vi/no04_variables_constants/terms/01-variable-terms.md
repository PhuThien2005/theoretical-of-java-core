# Các Thuật ngữ về Biến (Variable Terms)

## Thuật ngữ: Phạm vi (Scope)

### Định nghĩa Ngắn gọn

Phạm vi là vùng mã nguồn mà tại đó một tên gọi có thể truy cập được.

### Lý do Quan trọng

Phạm vi giúp ngăn chặn các biến tạm thời rò rỉ vào các phần mã nguồn không liên quan.

### Nhầm lẫn Phổ biến

Phạm vi không giống với vòng đời (Lifetime). Phạm vi quyết định nơi một biến hiển thị; trong khi vòng đời quyết định thời gian tồn tại của biến đó.

## Thuật ngữ: Vòng đời (Lifetime)

### Định nghĩa Ngắn gọn

Vòng đời là khoảng thời gian tồn tại của một biến hoặc đối tượng (object).

### Lý do Quan trọng

Vòng đời giúp giải thích lý do tại sao các biến cục bộ (local variable) biến mất sau khi phương thức (method) kết thúc thực thi, và tại sao các trường (field) của đối tượng vẫn được giữ lại khi đối tượng đó tồn tại.

### Nhầm lẫn Phổ biến

Một biến có thể đã nằm ngoài phạm vi trước khi đối tượng mà nó tham chiếu (reference) đến bị thu hồi.

## Thuật ngữ: Số ma thuật (Magic Number)

### Định nghĩa Ngắn gọn

Số ma thuật là một giá trị số trực tiếp (literal number) được sử dụng trực tiếp trong mã nguồn mà không có tên gọi giải thích ý nghĩa của nó.

### Lý do Quan trọng

Các số ma thuật làm cho mã nguồn trở nên khó đọc và khó bảo trì (maintain) hơn.

### Ví dụ

`MAX_RETRY_COUNT` rõ nghĩa hơn là viết `3` ở nhiều nơi.
