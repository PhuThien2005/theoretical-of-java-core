# Kiểu Dữ Liệu Nguyên Thủy (Primitive Types)

Kiểu dữ liệu nguyên thủy (Primitive Types) là các kiểu dữ liệu tích hợp sẵn đơn giản nhất trong Java. Chúng lưu trữ các giá trị đơn giản một cách trực tiếp và không phải là đối tượng (object).

Java có 8 kiểu nguyên thủy:

| Kiểu | Phân loại | Cách dùng phổ biến |
| --- | --- | --- |
| `byte` | số nguyên | các giá trị số nguyên rất nhỏ |
| `short` | số nguyên | các giá trị số nguyên nhỏ |
| `int` | số nguyên | lựa chọn số nguyên mặc định |
| `long` | số nguyên | các giá trị số nguyên lớn |
| `float` | số thực dấu phẩy động | các giá trị thập phân có độ chính xác thấp hơn |
| `double` | số thực dấu phẩy động | lựa chọn số thập phân mặc định |
| `char` | ký tự | một đơn vị mã UTF-16 đơn lẻ |
| `boolean` | logic | `true` hoặc `false` |

## Kích Thước Trong Bộ Nhớ (Size In Memory)

Mỗi kiểu nguyên thủy đều có một **kích thước cố định, được đảm bảo** trong bộ nhớ. Đây là đặc tính định nghĩa của các kiểu nguyên thủy — JVM luôn biết chính xác cần cấp phát bao nhiêu bit trước khi chương trình chạy.

| Kiểu | Kích thước (bit) | Kích thước (byte) | Phạm vi |
| --- | --- | --- | --- |
| `byte` | 8 | 1 | −128 đến 127 |
| `short` | 16 | 2 | −32,768 đến 32,767 |
| `int` | 32 | 4 | −2,147,483,648 đến 2,147,483,647 |
| `long` | 64 | 8 | −9,223,372,036,854,775,808 đến 9,223,372,036,854,775,807 |
| `float` | 32 | 4 | ±3.4 × 10³⁸ (~7 chữ số thập phân) |
| `double` | 64 | 8 | ±1.7 × 10³⁰⁸ (~15 chữ số thập phân) |
| `char` | 16 | 2 | 0 đến 65,535 (không dấu) |
| `boolean` | Phụ thuộc vào JVM | thường là 1 | `true` hoặc `false` |

Kích thước cố định này là lý do tại sao các kiểu nguyên thủy có thể được lưu trữ trực tiếp trên Stack — xem [Kiểu Tham Chiếu và Mô Hình Bộ Nhớ (Reference Types and Memory Model)](02-reference-types.md) để biết chi tiết về việc lưu trữ trên Stack so với Heap.

## Các Kiểu Số Nguyên (Integer Types)

Các kiểu số nguyên dùng để lưu trữ số nguyên.

```java
byte small = 10;
short count = 300;
int age = 18;
long population = 8_000_000_000L;
```

`int` là kiểu mặc định cho các hằng số nguyên (integer literal).

Các hằng số `long` thường sử dụng hậu tố `L`:

```java
long value = 10000000000L;
```

Hãy sử dụng chữ in hoa `L`, không dùng chữ thường `l`, vì chữ thường `l` rất dễ bị nhầm lẫn với số `1`.

## Các Kiểu Số Thực Dấu Phẩy Động (Floating-Point Types)

Các kiểu số thực dấu phẩy động (floating-point) dùng để lưu trữ số thập phân.

```java
float price = 9.99F;
double score = 8.75;
```

`double` là kiểu mặc định cho các hằng số thập phân.

Các hằng số `float` cần có hậu tố `F`:

```java
float ratio = 0.5F;
```

Số thực dấu phẩy động không lý tưởng để biểu diễn tiền tệ vì chúng có thể gặp sai số làm tròn.

Ví dụ:

```java
System.out.println(0.1 + 0.2);
```

Kết quả có thể không chính xác là `0.3`.

Đối với tiền tệ, các lập trình viên Java thường sử dụng `BigDecimal`.

## Kiểu char

`char` lưu trữ một đơn vị mã UTF-16 đơn lẻ.

```java
char grade = 'A';
```

Dấu nháy đơn được sử dụng cho `char`. Dấu nháy kép được sử dụng cho `String`.

```java
char c = 'A';
String s = "A";
```

Chúng không phải là cùng một kiểu.

## Kiểu boolean

`boolean` chỉ lưu trữ:

```java
true
false
```

Ví dụ:

```java
boolean isActive = true;
```

Java không coi `0` là `false` hay `1` là `true`.

Không hợp lệ:

```java
boolean valid = 1;
```

## Giá Trị Mặc Định (Default Values)

Biến cục bộ (local variable) không tự động nhận giá trị mặc định. Chúng phải được gán giá trị trước khi sử dụng.

Các trường (field) có giá trị mặc định.

Các giá trị mặc định phổ biến:

| Kiểu | Giá trị mặc định của trường |
| --- | --- |
| kiểu nguyên thủy số | `0` hoặc `0.0` |
| `char` | `'\u0000'` |
| `boolean` | `false` |
| kiểu tham chiếu | `null` |

## Các Sai Lầm Thường Gặp (Common Mistakes)

- Sử dụng `float` không có hậu tố `F`.
- Sử dụng `long` không có hậu tố `L` cho các hằng số lớn.
- Sử dụng dấu nháy kép cho `char`.
- Giả định rằng Java coi `1` là `true`.
- Sử dụng các kiểu số thực dấu phẩy động cho các tính toán tiền tệ chính xác.

---

## Tràn Số Nguyên (Integer Overflow)

Các phép toán số nguyên trong Java sẽ tự động quay vòng (wrap around) một cách âm thầm khi vượt quá phạm vi của kiểu dữ liệu — không có ngoại lệ (exception) nào được ném ra.

Phạm vi của `int`: −2,147,483,648 đến 2,147,483,647.

```java
int max = Integer.MAX_VALUE;        // 2147483647
System.out.println(max + 1);       // -2147483648  (wraps to MIN_VALUE!)
System.out.println(max + 1 > max); // false
```

### Tại Sao Lại Xảy Ra Hiện Tượng Này

Java sử dụng phép toán bù 2 (two's complement). Việc cộng thêm 1 vào giá trị lớn nhất sẽ đảo ngược tất cả các bit và trả về giá trị nhỏ nhất.

### Cách Phát Hiện / Ngăn Ngừa Tràn Số

```java
// Tùy chọn 1: sử dụng long cho tính toán trung gian
long safe = (long) max + 1;        // 2147483648L — correct

// Tùy chọn 2: sử dụng Math.addExact (ném ra ArithmeticException khi bị tràn)
try {
    int result = Math.addExact(max, 1);
} catch (ArithmeticException e) {
    System.out.println("Overflow detected: " + e.getMessage());
}
```

### Sai Lầm Thường Gặp

Hiện tượng tràn số diễn ra âm thầm trong Java — theo mặc định nó không bao giờ ném ra ngoại lệ. Điều này có thể gây ra các lỗi tiềm ẩn (subtle bug) trong bộ đếm vòng lặp, mã kiểm tra (checksum) hoặc tính toán dung lượng.

---

## Ví Dụ Thực Tế: Tại Sao Sử Dụng Float Cho Tiền Tệ Lại Nguy Hiểm

Các kiểu số thực dấu phẩy động (`float`, `double`) lưu trữ số dưới dạng nhị phân. Các phân số thập phân như `0.1` và `0.2` không thể biểu diễn chính xác trong hệ nhị phân, gây ra sai số làm tròn.

```java
// Đoạn code lập trình viên có thể viết
double price = 0.10;
double tax   = 0.20;
double total = price + tax;

System.out.println(total);          // 0.30000000000000004  ← NOT 0.30!
System.out.println(total == 0.30);  // false
```

### Ảnh Hưởng Thực Tế

Nếu một giỏ hàng có 1.000.000 giao dịch và mỗi giao dịch bị sai số làm tròn khoảng ~0.000000000000001, sai số lũy kế có thể tạo ra chênh lệch vài cent hoặc thậm chí vài đô la — một lỗi tài chính nghiêm trọng.

### Cách Tiếp Cận Đúng: BigDecimal

```java
import java.math.BigDecimal;

BigDecimal price = new BigDecimal("0.10");
BigDecimal tax   = new BigDecimal("0.20");
BigDecimal total = price.add(tax);

System.out.println(total);           // 0.30  ← exact
System.out.println(total.compareTo(new BigDecimal("0.30")) == 0); // true
```

**Quy tắc quan trọng:** Luôn sử dụng hàm khởi dựng (constructor) nhận `String` cho `BigDecimal` — `new BigDecimal(0.1)` vẫn sẽ giữ nguyên sự không chính xác của kiểu double!

```java
// Wrong — captures double inaccuracy
BigDecimal bad = new BigDecimal(0.1);
System.out.println(bad); // 0.1000000000000000055511151231257827021181583404541015625

// Correct — exact decimal
BigDecimal good = new BigDecimal("0.1");
System.out.println(good); // 0.1
```

---

## Kiểu char Dưới Dạng Một Số

`char` lưu trữ một đơn vị mã UTF-16, bản chất là một số nguyên không dấu 16-bit (0–65535). Điều này có nghĩa là `char` có thể tham gia vào các phép toán như một số.

```java
char c = 'A';           // Unicode code point 65
System.out.println(c);  // A

// char tự động thăng cấp thành int trong các phép toán
System.out.println(c + 1);          // 66  (int, not char!)
System.out.println((char)(c + 1));  // B

// Duyệt qua các chữ cái
for (char letter = 'A'; letter <= 'Z'; letter++) {
    System.out.print(letter + " ");
}
// Output: A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
```

### Chuyển đổi char sang int và ngược lại

```java
char ch = 'Z';
int code = ch;          // nới rộng (widening): char → int, giá trị là 90
System.out.println(code); // 90

char back = (char) code;  // thu hẹp (narrowing): int → char
System.out.println(back); // Z
```

### Sai Lầm Thường Gặp

```java
char a = 'A';
char b = 'B';
// char sum = a + b; // compile error: a + b is int, not char
int sum = a + b;        // 65 + 66 = 131
System.out.println(sum); // 131
```

---

## Giá Trị Mặc Định Của Kiểu Boolean (Trường So Với Biến Cục Bộ)

Các trường `boolean` trong một lớp được khởi tạo mặc định là `false`. Các biến `boolean` cục bộ phải được gán giá trị rõ ràng trước khi sử dụng.

```java
public class Demo {
    boolean active;         // trường — mặc định là false

    void example() {
        // boolean flag;
        // System.out.println(flag); // compile error: variable flag might not have been initialized

        boolean ready = false;
        System.out.println(active); // false — giá trị mặc định của trường
        System.out.println(ready);  // false — được gán rõ ràng
    }
}
```

---

## Nới Rộng So Với Thu Hẹp: Các Trường Hợp Biên Đặc Biệt

### Ép Kiểu Nới Rộng Là Tự Động Nhưng Không Phải Lúc Nào Cũng Không Mất Mát Dữ Liệu

```java
int bigInt = 123456789;
float f = bigInt;            // nới rộng int → float (tự động)
System.out.println(f);       // 1.23456792E8  ← precision lost!
System.out.println((int) f); // 123456792     ← not the original value!
```

`float` chỉ có khoảng ~7 chữ số thập phân có nghĩa. Ép kiểu nới rộng (widening) từ `int` sang `float` vẫn có thể làm mất độ chính xác đối với các số nguyên lớn.

### Ép Kiểu Thu Hẹp Sẽ Cắt Bỏ Phần Thập Phân, Không Làm Tròn

```java
double d = -9.9;
int i = (int) d;
System.out.println(i); // -9, NOT -10  (cắt bỏ phần thập phân hướng về số 0)
```

### Chuỗi Nới Rộng

```java
byte  b  = 10;
short s  = b;      // nới rộng byte → short
int   i2 = s;      // nới rộng short → int
long  l  = i2;     // nới rộng int → long
float f2 = l;      // nới rộng long → float
double d2 = f2;    // nới rộng float → double
System.out.println(d2); // 10.0
```
