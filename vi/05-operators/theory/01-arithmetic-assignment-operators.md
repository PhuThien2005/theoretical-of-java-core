# Toán Tử Số Học Và Toán Tử Gán (Arithmetic and Assignment Operators)

Toán tử số học thực hiện các tính toán số học. Toán tử gán lưu trữ các giá trị trong biến. Chúng thường xuất hiện cùng nhau vì các chương trình thường tính toán một giá trị nào đó rồi lưu lại kết quả.

## Toán Tử Số Học (Arithmetic Operators)

Java có năm toán tử số học phổ biến:

| Toán tử | Ý nghĩa | Ví dụ | Kết quả |
|---|---|---:|---:|
| `+` | phép cộng (addition) | `7 + 3` | `10` |
| `-` | phép trừ (subtraction) | `7 - 3` | `4` |
| `*` | phép nhân (multiplication) | `7 * 3` | `21` |
| `/` | phép chia (division) | `7 / 3` | `2` khi cả hai toán hạng đều là số nguyên |
| `%` | phép chia lấy dư (remainder) | `7 % 3` | `1` |

Một biểu thức số học được đánh giá theo thứ tự ưu tiên của toán tử, sau đó từ trái sang phải đối với các toán tử có cùng mức độ ưu tiên. Phép nhân, phép chia và phép chia lấy dư có thứ tự ưu tiên cao hơn phép cộng và phép trừ.

```java
int result = 2 + 3 * 4; // 14, không phải 20
```

Java tính `3 * 4` trước, sau đó cộng thêm `2`.

## Phép Chia Số Nguyên (Integer Division)

Khi cả hai toán hạng của phép chia `/` đều là kiểu số nguyên, Java sẽ thực hiện phép chia số nguyên. Phần thập phân sẽ bị loại bỏ (cắt cụt).

```java
int a = 7 / 3;      // 2
int b = 10 / 4;     // 2
double c = 10 / 4;  // 2.0, vì phép chia đã xảy ra dưới dạng phép chia số nguyên trước đó
double d = 10 / 4.0; // 2.5
```

Cạm bẫy phổ biến là gán kết quả cho một biến kiểu `double` và mong đợi nhận được phần thập phân. Kiểu kết quả của biểu thức được quyết định trước khi phép gán được thực hiện.

## Phép Chia Lấy Dư Với % (Remainder With %)

Toán tử `%` trả về phần dư sau phép chia.

```java
int r = 17 % 5; // 2
```

Nó thường được sử dụng cho:

- Kiểm tra tính chia hết: `n % 2 == 0`
- Giới hạn (xoay vòng) chỉ số: `index % size`
- Tách các chữ số trong các bài tập nhập môn

Đối với các toán hạng âm, dấu của phần dư tuân theo số bị chia (toán hạng bên trái).

```java
System.out.println(-7 % 3); // -1
```

## Nối Chuỗi Với + (String Concatenation With +)

Toán tử `+` cũng được dùng để nối các chuỗi. Nếu một trong hai toán hạng là một `String`, Java sẽ chuyển đổi toán hạng còn lại thành văn bản và ghép chúng lại với nhau.

```java
System.out.println("Java " + 21);       // Java 21
System.out.println("Result: " + 2 + 3); // Result: 23
System.out.println("Result: " + (2 + 3)); // Result: 5
```

Một khi phép nối chuỗi bắt đầu từ trái sang phải, các phép toán `+` tiếp theo trong chuỗi đó sẽ tiếp tục là phép nối chuỗi, trừ khi có dấu ngoặc đơn bắt buộc thực hiện phép tính số học trước.

## Phép Gán (Assignment)

Toán tử gán đơn giản là `=`.

```java
int score = 10;
score = 15;
```

Phép gán lưu trữ giá trị ở vế phải vào biến ở vế trái. Đây không phải là phép so sánh bằng. Trong Java, phép so sánh bằng sử dụng toán tử `==`.

## Toán Tử Gán Liên Hợp (Compound Assignment)

Các toán tử gán liên hợp kết hợp một phép toán và phép gán:

```java
int count = 5;
count += 2; // tương tự như count = count + 2
count *= 3; // tương tự như count = count * 3
```

Các toán tử gán liên hợp phổ biến bao gồm `+=`, `-=`, `*=`, `/=`, và `%=`.

Có một sự khác biệt tinh tế: toán tử gán liên hợp bao gồm một phép ép kiểu ngầm định quay lại kiểu dữ liệu của biến vế trái.

```java
byte b = 1;
b += 1;      // được phép
// b = b + 1; // không được phép nếu thiếu ép kiểu, vì b + 1 được tự động nâng kiểu lên int
```

Điều này rất quan trọng vì các phép toán số học trên các kiểu số nguyên nhỏ hơn như `byte`, `short` và `char` sẽ bị nâng kiểu lên thành `int`.

## Tại Sao Toán Tử Gán Liên Hợp Thực Hiện Ép Kiểu Ngầm Định (Why Compound Assignment Performs Implicit Casting)

Trong Java, bất kỳ phép toán số học nào trên các kiểu số nguyên nhỏ (`byte`, `short` và `char`) đều tự động nâng các toán hạng lên kiểu `int` trước khi phép toán thực thi. Do đó, việc viết `b = b + 1` với một biến `byte b` sẽ không thể biên dịch được vì nó cố gắng gán một kết quả kiểu `int` ngược trở lại cho một biến kiểu `byte`. Để tránh việc mã nguồn bị lấp đầy bởi các phép ép kiểu tường minh lặp đi lặp lại, đặc tả ngôn ngữ Java định nghĩa các toán tử gán liên hợp (như `+=`, `*=`) bao gồm một phép ép kiểu ngầm định về kiểu của biến vế trái. Mặc dù điều này giúp cú pháp gọn gàng hơn, nhưng nó cũng che giấu các nguy cơ tràn số hoặc mất độ chính xác vì trình biên dịch sẽ không cảnh báo khi giá trị vượt quá giới hạn kiểu dữ liệu của biến.

### Mô Hình Tư Duy Cắt Cụt (Truncation Mental Model)

Khi một giá trị được ép kiểu ngầm định về kiểu nhỏ hơn, Java thực hiện chuyển đổi thu hẹp kiểu nguyên thủy (narrowing primitive conversion) bằng cách loại bỏ tất cả các bit bậc cao không khớp với kích thước của kiểu đích.

```text
Giá trị 130 ở hệ thập phân (int, 32-bit):
[00000000] [00000000] [00000000] [10000010]
                                      │
                         [Ép kiểu thu hẹp sang byte (8-bit)]
                                      ▼
                             [10000010]  --> -126 trong biểu diễn bù 2 (bit MSB có giá trị là 1)
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)


```text
Phép toán `b += 10` được đánh giá
  → Java nâng kiểu của `b` và `10` lên thành `int` rồi cộng chúng lại
  → tổng trung gian là `130` (`int` 32-bit)
  → phép ép kiểu ngầm định `(byte)` được áp dụng
  → 24 bit bậc cao bị loại bỏ
  → `8` bit còn lại `10000010` có bit dấu (Most Significant Bit - bit lớn nhất) là `1`, khiến giá trị lưu trữ cuối cùng là `-126` (tràn số).
```


### Ví Dụ Mã Nguồn (Code Example)

```java
byte b = 120;
b += 10; // Được tự động biên dịch là: b = (byte) (b + 10)
System.out.println(b); // -126
```

## Phép Gán Là Một Biểu Thức (Assignment Is An Expression)

Trong Java, phép gán có một giá trị: chính là giá trị được gán.

```java
int x;
int y;
x = y = 10;
```

Điều này hoạt động được vì `y = 10` được đánh giá thành `10`, sau đó `x = 10` diễn ra. Mặc dù điều này là hợp lệ, nhưng các chuỗi phép gán dài có thể làm giảm khả năng đọc mã.

## Quy Tắc Thực Tế (Practical Rule)

Khi các phép toán số học có những cạm bẫy dễ mắc phải, hãy tự hỏi bốn câu hỏi sau:

1. Các kiểu dữ liệu của toán hạng là gì?
2. Java có nâng kiểu các toán hạng không?
3. Biểu thức tạo ra kết quả thuộc kiểu dữ liệu nào?
4. Kết quả có được gán cho một biến có thể làm thay đổi giá trị hơn nữa hay không?

---

## Các Lỗi Thường Gặp (Common Mistakes)

### Lỗi 1 — Cắt Cụt Trong Phép Chia Số Nguyên (Mistake 1 — Integer Division Truncation)

```java
// Giả định của người mới bắt đầu: x sẽ là 2.5
double x = 10 / 4;
System.out.println(x); // 2.0  ← bị cắt cụt TRƯỚC KHI thực hiện phép gán

// Cách khắc phục: bắt buộc thực hiện phép chia dấu phẩy động
double y = 10 / 4.0;   // 2.5
double z = (double) 10 / 4; // 2.5
```

Quy tắc cốt lõi: **kiểu kết quả của một biểu thức được quyết định bởi các toán hạng, chứ không phải bởi biến nhận kết quả.**

### Lỗi 2 — Phép Chia Lấy Dư % Với Các Toán Hạng Âm (Mistake 2 — Remainder % With Negative Operands)

```java
System.out.println( 7 % 3);  //  1
System.out.println(-7 % 3);  // -1  ← dấu tuân theo số bị chia (vế trái)
System.out.println( 7 % -3); //  1  ← dấu vẫn tuân theo số bị chia
System.out.println(-7 % -3); // -1
```

Quy tắc của Java: dấu của kết quả bằng với dấu của **toán hạng bên trái**. Điều này khác với phép toán modulo trong toán học khi kết quả luôn không âm.

### Lỗi 3 — Chuỗi Phép Toán Nối Chuỗi (Mistake 3 — String Concatenation Chain)

```java
// Cả ba dòng đều trông tương tự nhau — nhưng kết quả đầu ra rất khác nhau
System.out.println(1 + 2 + " items");   // "3 items"  (các số int cộng trước, từ trái sang phải)
System.out.println("items: " + 1 + 2);  // "items: 12" (String xuất hiện trước, sau đó là nối chuỗi)
System.out.println("items: " + (1 + 2)); // "items: 3" (dấu ngoặc đơn bắt buộc cộng số int trước)
```

Một khi một `String` xuất hiện với tư cách là toán hạng bên **trái** của phép `+`, mọi phép `+` tiếp theo trong cùng một chuỗi cũng sẽ coi toán hạng bên phải của nó là văn bản.

### Lỗi 4 — Ép Kiểu Ẩn Trong Toán Tử Gán Liên Hợp (Mistake 4 — Compound Assignment Hidden Cast)

```java
byte b = 100;
b *= 2;         // biên dịch được — tự động ép kiểu ngược về byte
                // kết quả bị xoay vòng: 200 ở kiểu byte là -56 (tràn số!)
System.out.println(b); // -56

// So sánh:
// b = (byte)(b * 2); // ép kiểu tường minh; cùng kết quả nhưng ý định rõ ràng hơn
```

Phép ép kiểu ngầm định âm thầm cho phép xảy ra tràn số. Đối với các phép toán `byte`/`short`, hãy đảm bảo kết quả nằm trong phạm vi cho phép.
