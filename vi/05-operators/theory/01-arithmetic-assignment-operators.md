# Các Toán Tử Số Học Và Gán (Arithmetic and Assignment Operators)

Các toán tử số học thực hiện các phép toán số học. Các toán tử gán lưu trữ các giá trị vào biến. Chúng thường xuất hiện cùng nhau vì các chương trình thường tính toán một giá trị nào đó rồi lưu lại kết quả.

## Các Toán Tử Số Học (Arithmetic Operators)

Java có năm toán tử số học phổ biến:

| Toán tử | Ý nghĩa | Ví dụ | Kết quả |
|---|---|---:|---:|
| `+` | phép cộng | `7 + 3` | `10` |
| `-` | phép trừ | `7 - 3` | `4` |
| `*` | phép nhân | `7 * 3` | `21` |
| `/` | phép chia | `7 / 3` | `2` khi cả hai toán hạng đều là số nguyên |
| `%` | phép chia lấy dư | `7 % 3` | `1` |

Một biểu thức số học được đánh giá theo thứ tự ưu tiên của toán tử (operator precedence), sau đó từ trái sang phải đối với các toán tử có cùng mức độ ưu tiên. Phép nhân, phép chia và phép chia lấy dư có độ ưu tiên cao hơn phép cộng và phép trừ.

```java
int result = 2 + 3 * 4; // 14, không phải 20
```

Java tính `3 * 4` trước, sau đó cộng thêm `2`.

## Phép Chia Số Nguyên (Integer Division)

Khi cả hai toán hạng (operand) của phép chia `/` đều là kiểu số nguyên, Java thực hiện phép chia số nguyên (integer division). Phần thập phân sẽ bị loại bỏ.

```java
int a = 7 / 3;      // 2
int b = 10 / 4;     // 2
double c = 10 / 4;  // 2.0, vì phép chia đã diễn ra như phép chia số nguyên trước đó
double d = 10 / 4.0; // 2.5
```

Cạm bẫy phổ biến là gán kết quả cho một biến `double` và mong đợi nhận được một kết quả thập phân. Kiểu kết quả của biểu thức được quyết định trước khi thực hiện phép gán.

## Phép Chia Lấy Dư Với `%` (Remainder With `%`)

Toán tử `%` trả về phần dư sau phép chia.

```java
int r = 17 % 5; // 2
```

Nó thường được sử dụng để:

- Kiểm tra tính chia hết: `n % 2 == 0`
- Giới hạn và xoay vòng chỉ số (wrap index): `index % size`
- Tách các chữ số trong các bài tập cơ bản

Đối với các toán hạng âm, dấu của phần dư sẽ tuân theo số bị chia (dividend) - tức là toán hạng bên trái.

```java
System.out.println(-7 % 3); // -1
```

## Phép Ghép Chuỗi Với `+` (String Concatenation With `+`)

Toán tử `+` cũng dùng để ghép chuỗi (string concatenation). Nếu một trong hai toán hạng là một `String`, Java sẽ chuyển đổi toán hạng còn lại thành văn bản và ghép chúng lại với nhau.

```java
System.out.println("Java " + 21);       // Java 21
System.out.println("Result: " + 2 + 3); // Result: 23
System.out.println("Result: " + (2 + 3)); // Result: 5
```

Một khi phép ghép chuỗi bắt đầu từ trái sang phải, các phép toán `+` tiếp theo trong chuỗi đó sẽ tiếp tục được xử lý như phép ghép chuỗi, trừ khi dấu ngoặc đơn bắt buộc thực hiện phép toán số học trước.

## Phép Gán (Assignment)

Toán tử gán đơn giản là `=`.

```java
int score = 10;
score = 15;
```

Phép gán lưu trữ giá trị bên vế phải vào biến ở vế trái. Đây không phải là so sánh bằng. Trong Java, phép so sánh bằng sử dụng `==`.

## Phép Gán Hỗn Hợp (Compound Assignment)

Các toán tử gán hỗn hợp (compound assignment operator) kết hợp một phép toán và phép gán:

```java
int count = 5;
count += 2; // ý nghĩa tương tự như count = count + 2
count *= 3; // ý nghĩa tương tự như count = count * 3
```

Các toán tử gán hỗn hợp phổ biến bao gồm `+=`, `-=`, `*=`, `/=`, và `%=`.

Có một sự khác biệt nhỏ: phép gán hỗn hợp bao gồm một phép ép kiểu ngầm định (implicit cast) về kiểu của biến ở vế trái.

```java
byte b = 1;
b += 1;      // được cho phép
// b = b + 1; // không được cho phép nếu không ép kiểu, vì b + 1 bị thăng cấp lên int
```

Điều này quan trọng vì các phép toán trên các kiểu số nguyên nhỏ hơn như `byte`, `short` và `char` sẽ bị thăng cấp (promote) lên `int`.

## Tại Sao Phép Gán Hỗn Hợp Lại Thực Hiện Ép Kiểu Ngầm Định

Trong Java, bất kỳ phép toán số học nào trên các kiểu số nguyên nhỏ (`byte`, `short` và `char`) đều tự động thăng cấp các toán hạng lên `int` trước khi phép toán thực thi. Do đó, việc viết `b = b + 1` với một biến `byte b` sẽ bị lỗi biên dịch vì nó cố gắng gán một kết quả kiểu `int` ngược lại cho một biến `byte`. Để tránh việc mã nguồn bị lấp đầy bởi các phép ép kiểu tường minh (explicit cast) lặp đi lặp lại, đặc tả ngôn ngữ Java định nghĩa các toán tử gán hỗn hợp (như `+=`, `*=`) tự động thực hiện ép kiểu ngầm định về kiểu của biến vế trái. Mặc dù điều này giúp cú pháp gọn gàng hơn, nó cũng che giấu hiện tượng tràn số hoặc mất độ chính xác tiềm ẩn vì trình biên dịch sẽ không đưa ra cảnh báo khi giá trị vượt quá giới hạn kiểu dữ liệu của biến.

### Mô Hình Tư Duy Về Cắt Bớt Dữ Liệu (Truncation)

Khi một giá trị được ép kiểu ngầm định về một kiểu nhỏ hơn, Java sẽ thực hiện chuyển đổi thu hẹp kiểu nguyên thủy (narrowing primitive conversion) bằng cách loại bỏ tất cả các bit bậc cao (high-order bit) không vừa với kích thước của kiểu đích.

```text
Giá trị 130 ở hệ thập phân (int, 32-bit):
[00000000] [00000000] [00000000] [10000010]
                                      │
                         [Ép kiểu thu hẹp sang byte (8-bit)]
                                      ▼
                             [10000010]  --> -126 ở dạng bù 2 (bit có trọng số lớn nhất MSB là 1)
```

### Chuỗi Nguyên Nhân - Kết Quả

`b += 10` được đánh giá → Java thăng cấp `b` và `10` lên `int` rồi cộng chúng lại → tổng trung gian thu được là `130` (kiểu `int` 32-bit) → phép ép kiểu ngầm định `(byte)` được áp dụng → 24 bit bậc cao bị loại bỏ → 8 bit còn lại `10000010` có bit dấu (Most Significant Bit - MSB) là `1`, khiến giá trị cuối cùng được lưu trữ là `-126` (bị tràn số).

### Ví dụ Code

```java
byte b = 120;
b += 10; // Được biên dịch âm thầm dưới dạng: b = (byte) (b + 10)
System.out.println(b); // -126
```

## Phép Gán Bản Chất Là Một Biểu Thức

Trong Java, phép gán có một giá trị: chính là giá trị được gán.

```java
int x;
int y;
x = y = 10;
```

Cách hoạt động này khả thi vì `y = 10` trả về giá trị `10`, sau đó `x = 10` xảy ra. Mặc dù điều này là hợp lệ, việc viết các chuỗi phép gán dài có thể làm giảm khả năng đọc của mã nguồn.

## Quy Tắc Thực Tế

Khi gặp các cạm bẫy về toán học, hãy tự hỏi bốn câu hỏi sau:

1. Kiểu của các toán hạng là gì?
2. Java có thăng cấp các toán hạng hay không?
3. Biểu thức tạo ra kết quả thuộc kiểu dữ liệu nào?
4. Kết quả có được gán cho một biến làm thay đổi thêm giá trị hay không?

---

## Các Sai Lầm Thường Gặp (Common Mistakes)

### Sai Lầm 1 — Cắt Bớt Dữ Liệu Trong Phép Chia Số Nguyên

```java
// Giả định của người mới bắt đầu: x sẽ là 2.5
double x = 10 / 4;
System.out.println(x); // 2.0  ← bị cắt bớt TRƯỚC KHI gán

// Cách khắc phục: bắt buộc thực hiện phép chia số thực dấu phẩy động
double y = 10 / 4.0;   // 2.5
double z = (double) 10 / 4; // 2.5
```

Quy tắc quan trọng: **kiểu kết quả của một biểu thức được quyết định bởi các toán hạng, chứ không phải bởi biến nhận giá trị.**

### Sai Lầm 2 — Phép Chia Lấy Dư `%` Với Các Toán Hạng Âm

```java
System.out.println( 7 % 3);  //  1
System.out.println(-7 % 3);  // -1  ← dấu tuân theo SỐ BỊ CHIA (vế trái)
System.out.println( 7 % -3); //  1  ← dấu vẫn tuân theo SỐ BỊ CHIA
System.out.println(-7 % -3); // -1
```

Quy tắc của Java: dấu của kết quả bằng với dấu của **toán hạng bên trái**. Điều này khác với phép đồng dư (modulo) trong toán học nơi kết quả luôn không âm.

### Sai Lầm 3 — Chuỗi Phép Cộng Ghép Chuỗi

```java
// Cả ba dòng trông có vẻ tương tự — nhưng đầu ra lại rất khác nhau
System.out.println(1 + 2 + " items");   // "3 items"  (các số int cộng trước, từ trái sang phải)
System.out.println("items: " + 1 + 2);  // "items: 12" (String xuất hiện đầu tiên, sau đó thực hiện ghép chuỗi)
System.out.println("items: " + (1 + 2)); // "items: 3" (dấu ngoặc đơn bắt buộc thực hiện phép cộng int trước)
```

Một khi một `String` xuất hiện với vai trò là toán hạng **bên trái** của `+`, mọi phép `+` tiếp theo trong cùng chuỗi đó cũng sẽ coi toán hạng bên phải của nó là văn bản.

### Sai Lầm 4 — Phép Ép Kiểu Ẩn Của Phép Gán Hỗn Hợp

```java
byte b = 100;
b *= 2;         // biên dịch được — tự động ép kiểu ngầm định về byte
                // kết quả bị quay vòng: 200 dưới dạng byte là -56 (tràn số!)
System.out.println(b); // -56

// So sánh:
// b = (byte)(b * 2); // ép kiểu tường minh; kết quả tương tự nhưng thể hiện rõ ý định
```

Phép ép kiểu ngầm định cho phép tràn số diễn ra âm thầm. Đối với các phép toán `byte`/`short`, hãy đảm bảo kết quả luôn nằm trong phạm vi hợp lệ.
