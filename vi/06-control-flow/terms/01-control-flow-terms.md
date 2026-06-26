# Thuật Ngữ Về Cấu Trúc Điều Khiển (Control Flow Terms)

Tệp này giải thích chi tiết về các thuật ngữ xuất hiện xuyên suốt các bài viết về cấu trúc điều khiển.

## Cấu Trúc Điều Khiển (Control Flow)

Cấu trúc điều khiển (control flow) là thứ tự thực thi của các câu lệnh trong chương trình. Mã nguồn Java cơ bản sẽ chạy tuần tự từ trên xuống dưới, nhưng các cấu trúc rẽ nhánh, vòng lặp, ngoại lệ (exceptions) và lời gọi phương thức có thể làm thay đổi thứ tự đó.

## Rẽ Nhánh (Branch)

Một nhánh (branch) là một đường dẫn thực thi được lựa chọn dựa trên một điều kiện hoặc một giá trị cụ thể. Các cấu trúc `if`, `else`, và `switch` tạo ra các nhánh.

## Điều Kiện (Condition)

Một điều kiện (condition) là một biểu thức boolean dùng để quyết định xem một nhánh hoặc một vòng lặp có được phép thực thi hay không.

Các điều kiện trong Java bắt buộc phải trả về kiểu `boolean`. Khác với một số ngôn ngữ khác, Java không coi số `0` là false hoặc các số khác không là true.

## Lần Lặp (Iteration)

Một lần lặp (iteration) là một lượt thực thi đơn lẻ đi qua toàn bộ phần thân của vòng lặp (loop body).

Nếu một vòng lặp in ra năm con số, nó thường thực hiện qua năm lần lặp.

## Thân Vòng Lặp (Loop Body)

Thân vòng lặp (loop body) là khối mã nguồn sẽ được thực thi lặp đi lặp lại bởi vòng lặp.

## Kết Thúc Vòng Lặp (Loop Termination)

Kết thúc vòng lặp (loop termination) là thời điểm vòng lặp dừng lại. Điều này có thể xảy ra do điều kiện lặp trở thành false, gặp câu lệnh `break`, sử dụng `return` để thoát khỏi phương thức, hoặc do một ngoại lệ làm gián đoạn quá trình thực thi.

## Vòng Lặp Vô Hạn (Infinite Loop)

Vòng lặp vô hạn (infinite loop) là vòng lặp không thể dừng lại một cách tự nhiên. Nó có thể được sử dụng có chủ đích, chẳng hạn như máy chủ luôn chạy để lắng nghe các yêu cầu gửi đến, hoặc do sơ suất vô ý, ví dụ quên cập nhật biến đếm vòng lặp.

## Hiện Tượng Trôi Qua (Fall-Through)

Hiện tượng trôi qua (fall-through) xảy ra trong các câu lệnh `switch` truyền thống khi luồng thực thi đi từ `case` này sang `case` tiếp theo do thiếu câu lệnh `break`, `return` hoặc lệnh thoát khác.

## Mệnh Đề Bảo Vệ (Guard Clause)

Mệnh đề bảo vệ (guard clause) xử lý sớm một trường hợp đặc biệt hoặc không hợp lệ, thường kết hợp với lệnh `return`, giúp cho logic chính của chương trình ít bị lồng nhau (nested) hơn.

```java
if (user == null) {
    return;
}
```

## Thoát Sớm (Early Exit)

Thoát sớm (early exit) có nghĩa là rời khỏi một khối mã, một vòng lặp hoặc một phương thức trước khi nó đi đến điểm kết thúc tự nhiên. Các từ khóa `break`, `continue` và `return` là các công cụ hỗ trợ thoát sớm.

## Tính Bao Phủ Toàn Bộ (Exhaustive)

Tính bao phủ toàn bộ (exhaustive) có nghĩa là mọi trường hợp khả thi đều được xử lý và bao quát hết. Một biểu thức switch bắt buộc phải đảm bảo tính bao phủ toàn bộ bởi vì nó phải tạo ra một giá trị trả về cụ thể.

## Nhãn (Label)

Nhãn (label) đặt tên cho một câu lệnh, thông thường là một vòng lặp bên ngoài. Các lệnh `break` có nhãn và `continue` có nhãn có thể chỉ định đích đến là vòng lặp được đặt tên đó.

Các nhãn rất hữu ích trong các vòng lặp lồng nhau, nhưng việc lạm dụng chúng có thể làm cho mã nguồn trở nên rối rắm, nhảy cóc và khó theo dõi hơn.
