# if, else, và switch (if, else, and switch)

Cấu trúc rẽ nhánh (branching) cho phép một chương trình Java lựa chọn các đường dẫn thực thi khác nhau.

## `if`

Một câu lệnh `if` sẽ chỉ thực thi một khối mã nguồn khi điều kiện của nó là true.

```java
if (score >= 60) {
    System.out.println("Pass");
}
```

Điều kiện bắt buộc phải là một biểu thức trả về kiểu `boolean`. Java không tự động chuyển đổi số `0`, `1`, chuỗi rỗng hay đối tượng phi-null thành điều kiện boolean giống như cách một số ngôn ngữ lập trình khác thực hiện.

```java
int count = 1;
// if (count) { } // không hợp lệ trong Java
```

## `if/else`

Mệnh đề `else` cung cấp một đường dẫn thực thi thay thế khi điều kiện của `if` trả về false.

```java
if (score >= 60) {
    System.out.println("Pass");
} else {
    System.out.println("Fail");
}
```

Chính xác chỉ có một trong hai khối mã trên được thực thi.

## `else if`

Một chuỗi `else if` thực hiện kiểm tra các điều kiện theo thứ tự từ trên xuống dưới. Java sẽ chạy nhánh đầu tiên thỏa mãn điều kiện và bỏ qua tất cả các nhánh còn lại ở phía sau.

```java
if (score >= 90) {
    grade = "A";
} else if (score >= 80) {
    grade = "B";
} else if (score >= 70) {
    grade = "C";
} else {
    grade = "D";
}
```

Thứ tự điều kiện rất quan trọng. Hãy đặt các điều kiện cụ thể hoặc nghiêm ngặt hơn lên trước các điều kiện mang tính bao quát.

## Nhập Nhằng Mệnh Đề `else` Bơ Vơ (The Dangling `else`)

Khi các dấu ngoặc nhọn bị bỏ qua, một mệnh đề `else` sẽ tự động liên kết với mệnh đề `if` gần nhất chưa được khớp ở phía trước.

```java
if (loggedIn)
    if (isAdmin)
        System.out.println("Admin");
    else
        System.out.println("Not admin");
```

Mệnh đề `else` ở trên sẽ thuộc về `if (isAdmin)`, chứ không thuộc về `if (loggedIn)`. Hãy luôn sử dụng dấu ngoặc nhọn để tránh sự mơ hồ này.

## Tại Sao Hiện Tượng Nhập Nhằng Dangling Else Xảy Ra và Cách Java Giải Quyết (Why Dangling Else Ambiguity Occurs and How Java Resolves It)

Vấn đề dangling-else là một sự nhập nhằng cú pháp kinh điển trong các cấu trúc điều khiển lồng nhau. Khi phân tích cú pháp các câu lệnh `if` lồng nhau mà không có các dấu ngoặc nhọn bao quanh khối mã, cú pháp của chương trình về mặt ngữ pháp tương thích với hai cây phân tích cú pháp khác nhau: liên kết `else` với `if` bên ngoài hoặc liên kết với `if` bên trong. Để ngăn chặn các xung đột phân tích cú pháp, Java giải quyết sự mơ hồ này ở cấp độ thiết kế ngôn ngữ bằng cách quy định rằng một mệnh đề `else` luôn liên kết với mệnh đề `if` gần nhất phía trước chưa được ghép cặp ở cùng một cấp độ lồng nhau. Mặc dù quy tắc khớp-gần-nhất này giúp quá trình phân tích cú pháp mang tính xác định (deterministic), nó có thể dễ dàng dẫn đến các lỗi logic âm thầm khi thụt lề (indentation) của mã nguồn gợi ý một liên kết khác với liên kết thực tế mà trình biên dịch tạo ra.

```mermaid
graph TD
    subgraph Actual Compiler Parsing [Cách hiểu thực tế của Trình biên dịch (Nearest-Match)]
        i1["if (loggedIn)"] --> t1["[Thân lệnh]"]
        t1 --> i2["if (isAdmin)"]
        i2 --> t2["in 'Admin'"]
        i2 --> e2["else: in 'Not admin'"]
    end
    subgraph Incorrect Indentation Assumption [Giả định sai do Thụt lề (Ý đồ của Lập trình viên)]
        i1_alt["if (loggedIn)"] --> t1_alt["[Thân lệnh]"]
        t1_alt --> i2_alt["if (isAdmin)"]
        i2_alt --> t2_alt["in 'Admin'"]
        i1_alt --> e1_alt["else: in 'Not admin'"]
    end
```

### Ví dụ Code: Bug Dangling Else
Trong ví dụ dưới đây, nhà phát triển thụt lề mệnh đề `else` thẳng hàng với câu lệnh `if` ngoài cùng, với mục đích in ra `"Logged out"` khi `loggedIn` có giá trị `false`.

```java
boolean loggedIn = false;
boolean isAdmin = false;

if (loggedIn)
    if (isAdmin)
        System.out.println("Admin");
else
    System.out.println("Logged out"); // Thụt lề thẳng với 'if' ngoài, nhưng thực tế liên kết với 'if (isAdmin)'!

// Kết quả in ra:
// (Không có kết quả nào được in ra!)
```

### Chuỗi Nguyên Nhân - Kết Quả

```text
Bỏ qua dấu ngoặc nhọn `{}` trong các câu lệnh `if` lồng nhau
  → Trình biên dịch áp dụng quy tắc giải quyết nhập nhằng khớp-gần-nhất của đặc tả JLS
  → Mệnh đề `else` liên kết với câu lệnh `if (isAdmin)` bên trong
  → Câu lệnh `if (loggedIn)` bên ngoài trả về `false`
  → Toàn bộ khối `if-else` bên trong bị bỏ qua
  → Hành động dự phòng mong muốn không bao giờ được thực thi, tạo ra một lỗi logic âm thầm.
```


## Mệnh Đề Bảo Vệ (Guard Clauses)

Một mệnh đề bảo vệ (guard clause) xử lý sớm một trường hợp không hợp lệ hoặc đặc biệt, thường kết hợp với lệnh `return`.

```java
void printName(String name) {
    if (name == null || name.isBlank()) {
        return;
    }

    System.out.println(name);
}
```

Các mệnh đề bảo vệ giúp giảm bớt cấu trúc code lồng nhau. Thay vì bao bọc toàn bộ logic chính của phương thức bên trong một khối `if` lớn, phương thức sẽ thoát sớm khi không thể tiếp tục xử lý.

## Câu Lệnh `switch` (switch Statement)

Một câu lệnh `switch` lựa chọn một nhánh thực thi dựa trên giá trị của một biểu thức.

```java
switch (day) {
    case 1:
        System.out.println("Monday");
        break;
    case 2:
        System.out.println("Tuesday");
        break;
    default:
        System.out.println("Unknown");
}
```

Các câu lệnh `switch` truyền thống bắt buộc phải có từ khóa `break` để ngăn chặn hiện tượng trôi qua (fall-through).

## Hiện Tượng Trôi Qua (Fall-Through)

Hiện tượng trôi qua (fall-through) có nghĩa là luồng thực thi sẽ tiếp tục đi từ `case` này sang `case` tiếp theo phía dưới do thiếu câu lệnh `break`, `return` hoặc lệnh thoát khác.

```java
switch (level) {
    case 1:
        System.out.println("Beginner");
    case 2:
        System.out.println("Intermediate");
}
```

Nếu `level` có giá trị là `1`, cả hai dòng chữ đều được in ra. Đôi khi hiện tượng trôi qua được sử dụng có chủ đích, nhưng trong code của người mới bắt đầu, đây thường là một lỗi logic (bug).

## Khi Nào Nên Dùng `switch`

- Sử dụng `switch` khi bạn cần so sánh một biểu thức duy nhất với một tập hợp rõ ràng các giá trị đã biết trước.
- Sử dụng `if/else` khi các điều kiện liên quan đến các khoảng giá trị, nhiều biến số khác nhau hoặc các logic boolean phức tạp.

---

## Các Lỗi Thường Gặp (Common Mistakes)

### Lỗi 1 — Đảo ngược thứ tự trong `else if` (bao quát đứng trước cụ thể)

Đặt một điều kiện bao quát lên trước một điều kiện cụ thể sẽ âm thầm nuốt chửng trường hợp cụ thể đó.

```java
// BUG: score là 95 in ra "Pass", không bao giờ in ra "A"
int score = 95;
if (score >= 60) {
    System.out.println("Pass");       // khớp điều kiện đầu tiên → thoát khỏi chuỗi rẽ nhánh
} else if (score >= 90) {
    System.out.println("A");          // không bao giờ chạm tới được
}

// KHẮC PHỤC: đặt điều kiện cụ thể hơn lên trước
if (score >= 90) {
    System.out.println("A");
} else if (score >= 60) {
    System.out.println("Pass");
}
```

### Lỗi 2 — Nhập nhằng dangling `else` gây hiểu lầm cho người đọc

Nếu không có dấu ngoặc nhọn, mệnh đề `else` sẽ thuộc về câu lệnh `if` gần nhất chưa được khớp, chứ không phải câu lệnh `if` ngoài cùng.

```java
// Nhìn qua có vẻ như: nếu loggedIn là false → in ra "Guest"
// Nhưng thực tế: mệnh đề else thuộc về if (isAdmin)
boolean loggedIn = true;
boolean isAdmin  = false;

if (loggedIn)
    if (isAdmin)
        System.out.println("Admin");
    else
        System.out.println("Not admin");   // in ra dòng này — KHÔNG PHẢI "Guest"

// Nếu loggedIn là false, hoàn toàn không có gì được in ra.
// KHẮC PHỤC: luôn luôn sử dụng dấu ngoặc nhọn
if (loggedIn) {
    if (isAdmin) {
        System.out.println("Admin");
    } else {
        System.out.println("Not admin");
    }
}
```

### Lỗi 3 — Thiếu `break` gây ra hiện tượng trôi qua switch ngoài ý muốn

```java
int day = 1;
switch (day) {
    case 1:
        System.out.println("Monday");
        // quên break — luồng thực thi trôi xuống tiếp!
    case 2:
        System.out.println("Tuesday");
        break;
    default:
        System.out.println("Unknown");
}
// Kết quả in ra: Monday
//               Tuesday   ← ngoài ý muốn!
```

**Khắc phục**: Thêm câu lệnh `break` sau mỗi case, hoặc chuyển sang sử dụng cú pháp switch dạng mũi tên (arrow-case syntax) được hỗ trợ từ Java 14 trở lên.

```java
switch (day) {
    case 1 -> System.out.println("Monday");
    case 2 -> System.out.println("Tuesday");
    default -> System.out.println("Unknown");
}
// Cú pháp dạng mũi tên không bao giờ xảy ra hiện tượng trôi qua.
```

---

## Case Study — Trôi Qua Có Chủ Đích so với Ngoài Ý Muốn (Case Study — Intentional vs. Accidental Fall-Through)

Đôi khi hiện tượng trôi qua được sử dụng *có chủ đích* và mang lại sự tiện lợi:

```java
// Gom nhóm nhiều ngày dưới một hành động chung
switch (day) {
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
        System.out.println("Weekday");
        break;
    case 6:
    case 7:
        System.out.println("Weekend");
        break;
}
```

Đây là một phong cách viết quen thuộc trong Java. Sự "trôi qua" ở đây chỉ là các case rỗng chia sẻ chung một lệnh `break`. Cú pháp mũi tên hiện đại viết tương đương sẽ sạch sẽ và rõ ràng hơn:

```java
String type = switch (day) {
    case 1, 2, 3, 4, 5 -> "Weekday";
    case 6, 7           -> "Weekend";
    default             -> "Unknown";
};
System.out.println(type);
```

## Liên Kết Tham Khảo (Reference Links)

- https://docs.oracle.com/javase/specs/jls/se21/html/jls-14.html#jls-14.9.2 (Sự nhập nhằng dangling-else trong Đặc tả Ngôn ngữ Java)
- https://docs.oracle.com/javase/tutorial/java/nutsandbolts/flow.html (Tài liệu hướng dẫn về các câu lệnh điều khiển của Oracle Java)
