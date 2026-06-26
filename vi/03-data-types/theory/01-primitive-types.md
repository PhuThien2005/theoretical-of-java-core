# Kiểu Dữ Liệu Nguyên Thủy (Primitive Types)

Kiểu dữ liệu nguyên thủy (primitive types) là các kiểu dữ liệu cơ bản nhất được tích hợp sẵn trong Java. Chúng lưu trữ các giá trị đơn giản trực tiếp và không phải là đối tượng (objects).

Java có 8 kiểu dữ liệu nguyên thủy:

| Kiểu dữ liệu (Type) | Nhóm (Category) | Cách dùng phổ biến (Typical Use) |
| --- | --- | --- |
| `byte` | số nguyên | các giá trị số nguyên rất nhỏ |
| `short` | số nguyên | các giá trị số nguyên nhỏ |
| `int` | số nguyên | lựa chọn mặc định cho số nguyên |
| `long` | số nguyên | các giá trị số nguyên lớn |
| `float` | số thực dấu phẩy động | các giá trị thập phân với độ chính xác thấp hơn |
| `double` | số thực dấu phẩy động | lựa chọn mặc định cho số thập phân |
| `char` | ký tự | một đơn vị mã UTF-16 đơn lẻ |
| `boolean` | logic | `true` hoặc `false` |

## Kích Thước Trong Bộ Nhớ (Size In Memory)

Mỗi kiểu dữ liệu nguyên thủy đều có một **kích thước cố định và được đảm bảo** trong bộ nhớ. Đây là đặc điểm định nghĩa của các kiểu nguyên thủy — JVM luôn biết chính xác cần cấp phát bao nhiêu bit trước khi chương trình chạy.

| Kiểu dữ liệu (Type) | Kích thước (bits) | Kích thước (bytes) | Phạm vi giá trị (Range) |
| --- | --- | --- | --- |
| `byte` | 8 | 1 | −128 đến 127 |
| `short` | 16 | 2 | −32,768 đến 32,767 |
| `int` | 32 | 4 | −2,147,483,648 đến 2,147,483,647 |
| `long` | 64 | 8 | −9,223,372,036,854,775,808 đến 9,223,372,036,854,775,807 |
| `float` | 32 | 4 | ±3.4 × 10³⁸ (~7 chữ số thập phân có nghĩa) |
| `double` | 64 | 8 | ±1.7 × 10³⁰⁸ (~15 chữ số thập phân có nghĩa) |
| `char` | 16 | 2 | 0 đến 65,535 (không dấu - unsigned) |
| `boolean` | phụ thuộc JVM | thường là 1 | `true` hoặc `false` |

Kích thước cố định này là lý do tại sao các kiểu nguyên thủy có thể được lưu trữ trực tiếp trên ngăn xếp (Stack) — xem phần [Các Kiểu Tham Chiếu Và Mô Hình Bộ Nhớ (Reference Types And Memory Model)](02-reference-types.md) để biết chi tiết về việc lưu trữ trên Stack so với Heap.

## Các Kiểu Số Nguyên (Integer Types)

Các kiểu số nguyên dùng để lưu trữ các số nguyên.

```java
byte small = 10;
short count = 300;
int age = 18;
long population = 8_000_000_000L;
```

`int` là kiểu dữ liệu mặc định cho các hằng số nguyên (integer literals).

Các hằng số kiểu `long` thường sử dụng hậu tố `L`:

```java
long value = 10000000000L;
```

Nên sử dụng chữ `L` viết hoa, tránh dùng chữ `l` viết thường vì chữ `l` viết thường rất dễ bị nhầm lẫn với số `1`.

## Các Kiểu Số Thực Dấu Phẩy Động (Floating-Point Types)

Các kiểu số thực dấu phẩy động dùng để lưu trữ các số thập phân.

```java
float price = 9.99F;
double score = 8.75;
```

`double` là kiểu dữ liệu mặc định cho các hằng số thập phân (decimal literals).

Các hằng số kiểu `float` cần có hậu tố `F`:

```java
float ratio = 0.5F;
```

Các số thực dấu phẩy động không lý tưởng để biểu diễn tiền tệ vì chúng có thể gặp lỗi làm tròn (rounding errors).

Ví dụ:

```java
System.out.println(0.1 + 0.2);
```

Kết quả có thể không chính xác là `0.3`.

Để xử lý tiền tệ, các nhà phát triển Java thường sử dụng `BigDecimal`.

## char

`char` lưu trữ một đơn vị mã UTF-16 đơn lẻ.

```java
char grade = 'A';
```

Dấu ngoặc đơn (single quotes) được sử dụng cho kiểu `char`. Dấu ngoặc kép (double quotes) được sử dụng cho kiểu `String`.

```java
char c = 'A';
String s = "A";
```

Chúng không phải là cùng một kiểu dữ liệu.

## boolean

`boolean` chỉ lưu trữ hai giá trị:

```java
true
false
```

Ví dụ:

```java
boolean isActive = true;
```

Java không coi số `0` là `false` hay số `1` là `true`.

Không hợp lệ:

```java
boolean valid = 1;
```

## Giá Trị Mặc Định (Default Values)

Các biến cục bộ (local variables) không tự động nhận giá trị mặc định. Chúng phải được gán giá trị trước khi sử dụng.

Các trường (fields) của lớp thì có giá trị mặc định.

Các giá trị mặc định phổ biến:

| Kiểu dữ liệu (Type) | Giá trị mặc định của Field (Default Field Value) |
| --- | --- |
| kiểu số nguyên thủy | `0` hoặc `0.0` |
| `char` | `'\u0000'` |
| `boolean` | `false` |
| kiểu tham chiếu | `null` |

## Các Lỗi Thường Gặp (Common Mistakes)

- Sử dụng `float` mà không có hậu tố `F`.
- Sử dụng `long` không có hậu tố `L` đối với các hằng số lớn.
- Sử dụng dấu ngoặc kép cho kiểu `char`.
- Giả định Java coi `1` là `true`.
- Sử dụng các kiểu số thực dấu phẩy động cho các tính toán tiền tệ chính xác.

---

## Tràn Số Nguyên (Integer Overflow)

Các phép toán số nguyên trong Java sẽ tự động xoay vòng (wrap around) một cách âm thầm khi vượt quá phạm vi của kiểu dữ liệu — không có ngoại lệ (exception) nào được ném ra.

Phạm vi của `int`: −2,147,483,648 đến 2,147,483,647.

```java
int max = Integer.MAX_VALUE;        // 2147483647
System.out.println(max + 1);       // -2147483648  (xoay vòng về MIN_VALUE!)
System.out.println(max + 1 > max); // false
```

### Tại Sao Hiện Tượng Này Xảy Ra
Java sử dụng toán học số bù hai (two's complement). Việc cộng thêm 1 vào giá trị lớn nhất sẽ làm đảo ngược tất cả các bit và trả về giá trị nhỏ nhất.

### Cách Phát Hiện / Ngăn Ngừa Tràn Số
```java
// Cách 1: sử dụng kiểu long cho tính toán trung gian
long safe = (long) max + 1;        // 2147483648L — chính xác

// Cách 2: sử dụng Math.addExact (ném ra ArithmeticException khi bị tràn)
try {
    int result = Math.addExact(max, 1);
} catch (ArithmeticException e) {
    System.out.println("Overflow detected: " + e.getMessage());
}
```

### Lỗi Thường Gặp
Hiện tượng tràn số diễn ra âm thầm trong Java — theo mặc định nó không bao giờ ném ra ngoại lệ. Điều này có thể gây ra những bug tiềm ẩn trong bộ đếm vòng lặp, tính toán tổng kiểm (checksum), hoặc tính toán dung lượng.

---

## Case Study: Tại Sao Dùng Float Để Tính Tiền Lại Nguy Hiểm (Case Study: Why Using Float for Money Is Dangerous)

Các kiểu số thực dấu phẩy động (`float`, `double`) lưu trữ các con số dưới định dạng nhị phân. Các phân số thập phân như `0.1` và `0.2` không thể biểu diễn chính xác dưới dạng nhị phân, dẫn đến lỗi làm tròn.

```java
// Những gì lập trình viên có thể viết
double price = 0.10;
double tax   = 0.20;
double total = price + tax;

System.out.println(total);          // 0.30000000000000004  ← KHÔNG PHẢI 0.30!
System.out.println(total == 0.30);  // false
```

### Tác Động Trong Thực Tế
Nếu một giỏ hàng có 1,000,000 giao dịch, mỗi giao dịch bị làm tròn lệch khoảng ~0.000000000000001, sai số tích lũy có thể tạo ra chênh lệch vài cent hoặc thậm chí vài đô la — một lỗi tài chính nghiêm trọng.

### Cách Tiếp Cận Đúng: BigDecimal
```java
import java.math.BigDecimal;

BigDecimal price = new BigDecimal("0.10");
BigDecimal tax   = new BigDecimal("0.20");
BigDecimal total = price.add(tax);

System.out.println(total);           // 0.30  ← chính xác
System.out.println(total.compareTo(new BigDecimal("0.30")) == 0); // true
```

**Quy tắc cốt lõi:** Luôn sử dụng hàm khởi tạo (constructor) nhận tham số là `String` cho `BigDecimal` — việc dùng `new BigDecimal(0.1)` vẫn sẽ giữ lại độ thiếu chính xác của kiểu double!

```java
// Sai — giữ lại sự thiếu chính xác của double
BigDecimal bad = new BigDecimal(0.1);
System.out.println(bad); // 0.1000000000000000055511151231257827021181583404541015625

// Đúng — số thập phân chính xác
BigDecimal good = new BigDecimal("0.1");
System.out.println(good); // 0.1
```

---

## char Dưới Dạng Một Con Số (char As a Number)

`char` lưu trữ một đơn vị mã UTF-16, bản chất là một số nguyên không dấu 16-bit (0–65535). Điều này có nghĩa là `char` tham gia vào các phép toán số học như một con số bình thường.

```java
char c = 'A';           // Mã code point Unicode là 65
System.out.println(c);  // A

// char được thăng cấp (promote) thành int trong các phép toán số học
System.out.println(c + 1);          // 66  (kiểu int, không phải char!)
System.out.println((char)(c + 1));  // B

// Duyệt qua các chữ cái
for (char letter = 'A'; letter <= 'Z'; letter++) {
    System.out.print(letter + " ");
}
// Kết quả: A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
```

### Chuyển Đổi Từ char sang int và Ngược Lại
```java
char ch = 'Z';
int code = ch;          // Nới rộng (widening): char → int, giá trị thu được là 90
System.out.println(code); // 90

char back = (char) code;  // Thu hẹp (narrowing): int → char
System.out.println(back); // Z
```

### Lỗi Thường Gặp
```java
char a = 'A';
char b = 'B';
// char sum = a + b; // Lỗi biên dịch: a + b kết quả là int, không phải char
int sum = a + b;        // 65 + 66 = 131
System.out.println(sum); // 131
```

---

## Giá Trị Mặc Định Của Kiểu Boolean (Trường Lớp so với Biến Cục Bộ) (Boolean Default Value (Fields vs Local Variables))

Các trường dữ liệu `boolean` trong một class được khởi tạo mặc định là `false`. Các biến cục bộ kiểu `boolean` phải được gán giá trị rõ ràng trước khi sử dụng.

```java
public class Demo {
    boolean active;         // trường dữ liệu (field) — mặc định là false

    void example() {
        // boolean flag;
        // System.out.println(flag); // Lỗi biên dịch: variable flag might not have been initialized

        boolean ready = false;
        System.out.println(active); // false — giá trị mặc định của trường dữ liệu
        System.out.println(ready);  // false — được gán rõ ràng
    }
}
```

---

## Nới Rộng so với Thu Hẹp: Các Trường Hợp Đặc Biệt (Widening vs Narrowing: Extra Edge Cases)

### Nới Rộng Là Tự Động Nhưng Không Phải Lúc Nào Cũng Không Mất Mát Dữ Liệu
```java
int bigInt = 123456789;
float f = bigInt;            // Nới rộng int → float (tự động)
System.out.println(f);       // 1.23456792E8  ← mất độ chính xác!
System.out.println((int) f); // 123456792     ← không còn là giá trị ban đầu!
```

Kiểu `float` chỉ có khoảng ~7 chữ số thập phân có nghĩa. Việc nới rộng từ `int` sang `float` vẫn có thể làm mất độ chính xác đối với các số nguyên lớn.

### Thu Hẹp Sẽ Cắt Bỏ Phần Thập Phân Chứ Không Làm Tròn
```java
double d = -9.9;
int i = (int) d;
System.out.println(i); // -9, KHÔNG PHẢI -10  (cắt bỏ hướng về số 0)
```

### Chuỗi Nới Rộng (Widening Chain)
```java
byte  b  = 10;
short s  = b;      // nới rộng byte → short
int   i2 = s;      // nới rộng short → int
long  l  = i2;     // nới rộng int → long
float f2 = l;      // nới rộng long → float
double d2 = f2;    // nới rộng float → double
System.out.println(d2); // 10.0
```
