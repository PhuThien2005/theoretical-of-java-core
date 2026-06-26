# Thuật Ngữ Về Biến (Variable Terms)

## Thuật ngữ: Phạm vi (Scope)

### Định nghĩa ngắn (Short Definition)

Phạm vi (scope) là vùng mã nguồn mà một biến hoặc tên định danh có thể được truy cập.

### Tại sao điều này quan trọng (Why It Matters)

Phạm vi ngăn các biến tạm thời bị rò rỉ (leak) sang các vùng mã không liên quan.

### Nhầm lẫn phổ biến (Common Confusion)

Phạm vi (scope) không giống như vòng đời (lifetime). Phạm vi đề cập đến nơi một biến hiển thị; vòng đời đề cập đến việc nó tồn tại trong bao lâu.

## Thuật ngữ: Vòng đời (Lifetime)

### Định nghĩa ngắn (Short Definition)

Vòng đời (lifetime) là khoảng thời gian tồn tại của một biến hoặc đối tượng.

### Tại sao điều này quan trọng (Why It Matters)

Vòng đời giúp giải thích lý do tại sao các biến cục bộ biến mất sau khi phương thức thực thi xong và tại sao các trường dữ liệu (fields) của đối tượng vẫn tồn tại khi đối tượng còn tồn tại.

### Nhầm lẫn phổ biến (Common Confusion)

Một biến có thể nằm ngoài phạm vi hoạt động trước khi đối tượng mà nó tham chiếu thực sự bị bộ thu gom rác (garbage collector) dọn dẹp.

## Thuật ngữ: Số ma thuật (Magic Number)

### Định nghĩa ngắn (Short Definition)

Số ma thuật (magic number) là một hằng số số học được sử dụng trực tiếp trong code mà không có tên định danh giải thích ý nghĩa của nó.

### Tại sao điều này quan trọng (Why It Matters)

Các số ma thuật làm cho mã nguồn trở nên khó đọc và khó bảo trì hơn.

### Ví dụ (Example)

Hằng số `MAX_RETRY_COUNT` rõ ràng hơn việc viết số `3` ở nhiều nơi trong chương trình.
