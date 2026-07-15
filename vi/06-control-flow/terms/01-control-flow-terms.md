# Thuật Ngữ Về Luồng Điều Khiển (Control Flow Terms)

Tài liệu này giải thích chi tiết các thuật ngữ xuất hiện trong phần lý thuyết về luồng điều khiển.

## Luồng Điều Khiển (Control Flow)

Luồng điều khiển (control flow) là thứ tự thực thi của các câu lệnh trong chương trình. Mã nguồn Java cơ bản chạy từ trên xuống dưới, nhưng các nhánh rẽ, vòng lặp, ngoại lệ và lời gọi phương thức có thể thay đổi thứ tự đó.

## Nhánh Rẽ (Branch)

Một nhánh rẽ (branch) là một đường dẫn thực thi được lựa chọn dựa trên một điều kiện hoặc một giá trị cụ thể. Các từ khóa `if`, `else` và `switch` được dùng để tạo ra các nhánh rẽ.

## Điều Kiện (Condition)

Một điều kiện (condition) là một biểu thức logic (boolean expression) được sử dụng để quyết định xem một nhánh rẽ hoặc một vòng lặp có được thực thi hay không.

Các điều kiện trong Java bắt buộc phải trả về kiểu `boolean`. Khác với một số ngôn ngữ khác, Java không coi số `0` là false hay các số khác 0 là true.

## Lượt Lặp (Iteration)

Một lượt lặp (iteration) là một lần thực thi hoàn thành toàn bộ phần thân của vòng lặp.

Nếu một vòng lặp in ra năm số, thông thường nó sẽ trải qua năm lượt lặp.

## Thân Vòng Lặp (Loop Body)

Thân vòng lặp (loop body) là khối mã nguồn nằm trong vòng lặp và được thực thi lặp đi lặp lại.

## Điểm Dừng Vòng Lặp (Loop Termination)

Điểm dừng vòng lặp (loop termination) là thời điểm vòng lặp dừng lại. Điều này có thể xảy ra do điều kiện lặp trở thành false, gặp câu lệnh `break`, câu lệnh `return` thoát khỏi phương thức, hoặc gặp một ngoại lệ làm gián đoạn quá trình thực thi.

## Vòng Lặp Vô Hạn (Infinite Loop)

Một vòng lặp vô hạn (infinite loop) là vòng lặp không bao giờ tự dừng lại một cách tự nhiên. Nó có thể là cố ý (ví dụ: máy chủ liên tục lắng nghe các yêu cầu kết nối) hoặc vô tình (ví dụ: quên cập nhật giá trị biến đếm điều kiện lặp).

## Trôi Case (Fall-Through)

Hiện tượng trôi case (fall-through) xảy ra trong các câu lệnh `switch` truyền thống khi một trường hợp `case` thực thi xong và tiếp tục chạy xuống `case` tiếp theo ngay bên dưới vì thiếu câu lệnh `break`, `return`, hoặc các cách thoát khác.

## Mệnh Đề Bảo Vệ (Guard Clause)

Một mệnh đề bảo vệ (guard clause) xử lý sớm các trường hợp đặc biệt hoặc không hợp lệ (thường đi kèm câu lệnh `return`), giúp logic chính của phương thức không bị thụt lề quá nhiều cấp (lồng nhau quá sâu).

```java
if (user == null) {
    return;
}
```

## Thoát Sớm (Early Exit)

Thoát sớm (early exit) có nghĩa là rời khỏi một khối mã, một vòng lặp, hoặc một phương thức trước khi nó đi đến điểm kết thúc tự nhiên. Các công cụ hỗ trợ thoát sớm bao gồm `break`, `continue` và `return`.

## Bao Phủ Toàn Bộ (Exhaustive)

Bao phủ toàn bộ (exhaustive) nghĩa là mọi trường hợp đầu vào có thể xảy ra đều đã được xử lý. Một biểu thức switch bắt buộc phải bao phủ toàn bộ các trường hợp vì nó luôn phải trả về một giá trị xác định.

## Nhãn (Label)

Một nhãn (label) dùng để đặt tên cho một câu lệnh, thông thường là một vòng lặp ngoài cùng. Từ khóa `break` và `continue` đi kèm nhãn có thể trỏ đích thực thi trực tiếp đến vòng lặp được đặt tên đó.

Việc sử dụng nhãn rất hữu ích trong các vòng lặp lồng nhau sâu, nhưng lạm dụng nhãn có thể làm cho luồng đi của mã nguồn bị nhảy cóc và khó theo dõi.
