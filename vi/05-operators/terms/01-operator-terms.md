# Thuật Ngữ Về Toán Tử (Operator Terms)

Tài liệu này giải thích chi tiết các thuật ngữ thường dùng khi nói về toán tử.

## Toán Hạng (Operand)

Toán hạng (operand) là một giá trị mà toán tử tác động lên.

```java
int result = a + b;
```

Trong biểu thức `a + b`, `a` và `b` là các toán hạng, và `+` là toán tử.

## Biểu Thức (Expression)

Biểu thức (expression) là một đoạn mã tạo ra một giá trị.

```java
2 + 3
age >= 18
name.toUpperCase()
```

Biểu thức có thể có kích thước nhỏ hoặc lớn. Các toán tử thường kết hợp các biểu thức nhỏ hơn thành biểu thức lớn hơn.

## Độ Ưu Tiên (Precedence)

Độ ưu tiên (precedence) là thứ tự ưu tiên của các toán tử. Nó trả lời câu hỏi: "Toán tử nào được nhóm và thực hiện trước?"

Trong biểu thức `2 + 3 * 4`, phép nhân được ưu tiên thực hiện trước, do đó kết quả là `14`.

## Tính Kết Hợp (Associativity)

Tính kết hợp (associativity) quyết định thứ tự thực hiện khi các toán tử có cùng độ ưu tiên.

Trong biểu thức `20 / 5 / 2`, phép chia được nhóm từ trái qua phải, vì vậy kết quả tương đương với `(20 / 5) / 2`, tức là `2`.

## Xử Lý Ngắn Mạch (Short-Circuit)

Xử lý ngắn mạch (short-circuit) là cơ chế mà Java sẽ dừng việc đánh giá một biểu thức logic (boolean) ngay khi kết quả cuối cùng đã được xác định chắc chắn.

Đối với toán tử `&&`, nếu vế trái là false thì đã đủ để kết luận toàn bộ biểu thức là false. Đối với toán tử `||`, nếu vế trái là true thì đã đủ để kết luận toàn bộ biểu thức là true.

Cơ chế ngắn mạch rất hữu ích cho các kiểm tra an toàn và tối ưu hóa hiệu suất, nhưng nó cũng có nghĩa là phần mã bị bỏ qua sẽ không được thực thi.

## Tác Dụng Phụ (Side Effect)

Tác dụng phụ (side effect) là một sự thay đổi xảy ra trong quá trình đánh giá đoạn mã. Các ví dụ bao gồm thay đổi giá trị của một biến, in kết quả ra màn hình, sửa đổi trạng thái của đối tượng, ghi vào cơ sở dữ liệu, hoặc gọi một phương thức làm thay đổi trạng thái.

Biểu thức `x++` có tác dụng phụ vì nó làm thay đổi giá trị của biến `x`.

## Phép Chia Số Nguyên (Integer Division)

Phép chia số nguyên (integer division) là phép chia mà cả hai toán hạng đều thuộc kiểu số nguyên. Java sẽ loại bỏ phần thập phân của kết quả.

Phép chia `7 / 3` trả về kết quả là `2`, chứ không phải `2.333`.

## Phần Dư (Remainder)

Phần dư (remainder) là số còn lại sau phép chia. Trong Java, toán tử `%` được dùng để tính phần dư.

Biểu thức `17 % 5` trả về kết quả là `2` vì `17 = 5 * 3 + 2`.

## So Sánh Bằng Nội Dung (Content Equality)

So sánh bằng nội dung (content equality) kiểm tra xem hai đối tượng có biểu diễn cùng một giá trị ý nghĩa hay không. Đối với nhiều đối tượng, việc so sánh nội dung được thực hiện qua phương thức `.equals()`.

Đối với lớp `String`, biểu thức `"Java".equals(input)` dùng để kiểm tra nội dung chuỗi văn bản.

## So Sánh Bằng Tham Chiếu (Reference Equality)

So sánh bằng tham chiếu (reference equality) kiểm tra xem hai biến tham chiếu có trỏ tới cùng một đối tượng chính xác trong bộ nhớ hay không. Trong Java, toán tử `==` dùng để kiểm tra tính đồng nhất tham chiếu của các đối tượng.

Hai đối tượng `String` khác nhau có thể chứa cùng một nội dung văn bản nhưng vẫn trả về kết quả false khi so sánh bằng toán tử `==`.

## Tính Tương Thích Kiểu Dữ Liệu (Type Compatibility)

Tính tương thích kiểu dữ liệu (type compatibility) có nghĩa là một giá trị có thể được xử lý một cách hợp lệ dưới dạng một kiểu dữ liệu nhất định. Toán tử `instanceof` dùng để kiểm tra tính tương thích kiểu dữ liệu của các tham chiếu đối tượng tại thời điểm thực thi.

Nếu biểu thức `value instanceof String text` trả về true, Java sẽ hiểu rằng biến `text` thuộc kiểu `String` bên trong khối lệnh đó.

## Dịch Phải Có Dấu (Signed Right Shift - >>)

Toán tử dịch phải có dấu (`>>`) thực hiện dịch chuyển biểu diễn nhị phân của một số sang bên phải, các bit trống ở phía ngoài cùng bên trái sẽ được lấp đầy bằng bit dấu ban đầu (0 đối với số dương, 1 đối với số âm). Phép dịch số học này giúp bảo toàn dấu toán học của giá trị.

## Dịch Phải Không Dấu (Unsigned Right Shift - >>>)

Toán tử dịch phải không dấu (`>>>`) thực hiện dịch chuyển biểu diễn nhị phân của một số sang bên phải, luôn luôn lấp đầy các bit trống ở phía ngoài cùng bên trái bằng số 0 bất kể dấu ban đầu là gì. Phép dịch logic này chuyển đổi các số âm thành các số nguyên dương.

## Ngăn Xếp Toán Hạng (Operand Stack)

Ngăn xếp toán hạng (operand stack) là một cấu trúc dữ liệu runtime của JVM được sử dụng trong quá trình thực thi phương thức để đẩy vào (push) và lấy ra (pop) các toán hạng, từ đó thực hiện các phép toán một cách động.

## Ô Biến Cục Bộ (Local Variable Slot)

Ô biến cục bộ (local variable slot) là một vị trí bộ nhớ được cấp phát bên trong một khung ngăn xếp (stack frame) của JVM để lưu giữ giá trị của một biến cục bộ hoặc tham số trong quá trình thực thi phương thức.

## Biến Mẫu (Pattern Variable)

Biến mẫu (pattern variable) là một biến cục bộ được khai báo bên trong một phép kiểm tra mẫu (chẳng hạn như `instanceof String text`). Biến này sẽ tự động được định kiểu, ép kiểu và liên kết dữ liệu nếu phép kiểm tra kiểu thành công, với phạm vi hoạt động giới hạn trong vùng mã nguồn mà phép kiểm tra kiểu được đảm bảo là đúng.
