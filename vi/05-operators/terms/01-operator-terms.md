# Thuật Ngữ Toán Tử (Operator Terms)

Tệp này giải thích chi tiết các thuật ngữ thường được sử dụng nhanh trong các bài giải thích về toán tử.

## Toán Hạng (Operand)

Toán hạng là giá trị mà toán tử tác động lên.

```java
int result = a + b;
```

Trong biểu thức `a + b`, `a` và `b` là các toán hạng, và `+` là toán tử.

## Biểu Thức (Expression)

Biểu thức là đoạn mã nguồn tạo ra một giá trị.

```java
2 + 3
age >= 18
name.toUpperCase()
```

Biểu thức có thể nhỏ hoặc lớn. Các toán tử thường kết hợp các biểu thức nhỏ hơn thành các biểu thức lớn hơn.

## Thứ Tự Ưu Tiên (Precedence)

Thứ tự ưu tiên là thứ tự ưu tiên của các toán tử. Nó trả lời câu hỏi: "Toán tử nào được gom nhóm trước?"

Trong `2 + 3 * 4`, phép nhân được gom nhóm trước, vì vậy kết quả là `14`.

## Tính Kết Hợp (Associativity)

Tính kết hợp quyết định việc gom nhóm khi các toán tử có cùng mức độ ưu tiên.

Trong `20 / 5 / 2`, phép chia được gom nhóm từ trái sang phải, vì vậy kết quả là `(20 / 5) / 2`, tức là `2`.

## Đánh Giá Ngắn Mạch (Short-Circuit)

Đánh giá ngắn mạch (Short-circuit) có nghĩa là Java dừng đánh giá một biểu thức boolean ngay khi kết quả cuối cùng đã được xác định.

Đối với `&&`, giá trị false ở vế trái là đủ để quyết định toàn bộ biểu thức là false. Đối với `||`, giá trị true ở vế trái là đủ để quyết định toàn bộ biểu thức là true.

Đánh giá ngắn mạch rất hữu ích cho các kiểm tra an toàn và hiệu suất, nhưng nó cũng có nghĩa là đoạn mã bị bỏ qua sẽ không được thực thi.

## Tác Dụng Phụ (Side Effect)

Tác dụng phụ (side effect) là một sự thay đổi xảy ra trong khi đánh giá mã nguồn. Các ví dụ bao gồm thay đổi giá trị của một biến, in dữ liệu ra đầu ra, thay đổi trạng thái của đối tượng, ghi vào cơ sở dữ liệu, hoặc gọi một phương thức làm thay đổi trạng thái.

`x++` có tác dụng phụ vì nó làm thay đổi giá trị của `x`.

## Phép Chia Số Nguyên (Integer Division)

Phép chia số nguyên là phép chia trong đó cả hai toán hạng đều thuộc kiểu số nguyên. Java loại bỏ phần thập phân.

`7 / 3` bằng `2`, không phải `2.333`.

## Phép Chia Lấy Dư (Remainder)

Phần dư là những gì còn lại sau phép chia. Trong Java, toán tử `%` tính toán phần dư này.

`17 % 5` bằng `2` vì `17 = 5 * 3 + 2`.

## So Sánh Bằng Về Nội Dung (Content Equality)

So sánh bằng về mặt nội dung nhằm kiểm tra xem hai đối tượng có đại diện cho cùng một giá trị có ý nghĩa hay không. Đối với nhiều đối tượng, sự bằng nhau về mặt nội dung được kiểm tra bằng phương thức `.equals()`.

Đối với `String`, `"Java".equals(input)` kiểm tra nội dung văn bản.

## So Sánh Bằng Về Tham Chiếu (Reference Equality)

So sánh bằng về mặt tham chiếu nhằm kiểm tra xem hai tham chiếu có trỏ đến chính xác cùng một đối tượng hay không. Trong Java, toán tử `==` kiểm tra tính bằng nhau về tham chiếu đối với các đối tượng.

Hai đối tượng `String` khác nhau có thể chứa cùng một nội dung văn bản nhưng vẫn không vượt qua được phép so sánh `==`.

## Tính Tương Thích Kiểu Dữ Liệu (Type Compatibility)

Tính tương thích kiểu dữ liệu có nghĩa là một giá trị có thể được coi là một kiểu dữ liệu cụ thể một cách hợp lệ. Toán tử `instanceof` kiểm tra khả năng tương thích kiểu dữ liệu lúc chạy (runtime) đối với các tham chiếu đối tượng.

Nếu biểu thức `value instanceof String text` là true, Java biết rằng `text` là một `String` bên trong khối mã đó.

## Dịch Phải Có Dấu (>> - Signed Right Shift)

Toán tử dịch phải có dấu (`>>`) dịch chuyển biểu diễn nhị phân của một số sang phải, lấp đầy các bit trống ngoài cùng bên trái bằng bit dấu ban đầu (0 đối với số dương, 1 đối với số âm). Phép dịch số học này giữ nguyên dấu toán học của giá trị.

## Dịch Phải Không Dấu (>>> - Unsigned Right Shift)

Toán tử dịch phải không dấu (`>>>`) dịch chuyển biểu diễn nhị phân của một số sang phải, luôn lấp đầy các bit trống ngoài cùng bên trái bằng các số không bất kể dấu ban đầu là gì. Phép dịch logic này chuyển đổi các số âm thành các số nguyên dương.

## Ngăn Xếp Toán Hạng (Operand Stack)

Ngăn xếp toán hạng (operand stack) là một cấu trúc dữ liệu runtime của JVM được sử dụng trong quá trình thực thi phương thức để đẩy vào (push) và lấy ra (pop) các toán hạng, thực hiện các phép toán một cách động.

## Ô Nhớ Biến Cục Bộ (Local Variable Slot)

Ô nhớ biến cục bộ (local variable slot) là một vị trí bộ nhớ được cấp phát trong một khung ngăn xếp (stack frame) của JVM để lưu trữ giá trị của biến cục bộ hoặc tham số trong quá trình thực thi phương thức.

## Biến Khớp Mẫu (Pattern Variable)

Biến khớp mẫu (pattern variable) là một biến cục bộ được khai báo bên trong một phép kiểm tra khớp mẫu (chẳng hạn như `instanceof String text`). Biến này được tự động xác định kiểu, ép kiểu và liên kết nếu phép kiểm tra kiểu thành công, với phạm vi hoạt động bị giới hạn trong khu vực mà phép kiểm tra kiểu được đảm bảo là đúng.
