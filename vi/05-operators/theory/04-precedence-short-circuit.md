# Thứ Tự Ưu Tiên Và Đánh Giá Ngắn Mạch (Precedence and Short-Circuit Evaluation)

Thứ tự ưu tiên của toán tử kiểm soát phần nào của biểu thức được đánh giá trước. Đánh giá ngắn mạch kiểm soát việc liệu có cần đánh giá một số phần của biểu thức hay không.

## Thứ Tự Ưu Tiên (Precedence)

Thứ tự ưu tiên là thứ tự ưu tiên giữa các toán tử.

```java
int result = 2 + 3 * 4;
```

Kết quả là `14` vì toán tử `*` có thứ tự ưu tiên cao hơn toán tử `+`.

Dấu ngoặc đơn sẽ ghi đè (thay đổi) thứ tự ưu tiên:

```java
int result = (2 + 3) * 4; // 20
```

## Tính Kết Hợp (Associativity)

Tính kết hợp quyết định cách gom nhóm đánh giá khi các toán tử có cùng thứ tự ưu tiên.

Hầu hết các toán tử số học được gom nhóm từ trái sang phải:

```java
int x = 20 / 5 / 2; // (20 / 5) / 2 = 2
```

Các toán tử gán được gom nhóm từ phải sang trái:

```java
int a;
int b;
a = b = 10;
```

Điều này có nghĩa là phép gán `b = 10` diễn ra trước, sau đó mới đến phép gán `a = 10`.

## Tại Sao Thứ Tự Ưu Tiên Và Tính Kết Hợp Lại Quyết Định Tính Chính Xác Của Biểu Thức (Why Precedence and Associativity Dictate Expression Correctness)

Thứ tự ưu tiên của toán tử và tính kết hợp xác định cây phân tích cú pháp (parser tree) mà trình biên dịch xây dựng để đánh giá các biểu thức phức hợp. Nếu không có các quy tắc nghiêm ngặt và mang tính xác định, các biểu thức chứa nhiều toán tử hỗn hợp sẽ tạo ra các kết quả mơ hồ và không thể dự đoán được.

Thứ tự ưu tiên quyết định toán tử nào được đánh giá trước (như phép nhân trước phép cộng), trong khi tính kết hợp giải quyết thứ tự đánh giá cho các toán tử có cùng mức ưu tiên (hầu hết được gom nhóm từ trái sang phải, trong khi phép gán và các toán tử một ngôi được gom nhóm từ phải sang trái). Dấu ngoặc đơn đóng vai trò như một sự ghi đè rõ ràng đối với cây phân tích cú pháp mặc định này, bắt buộc các biểu thức con cụ thể phải được gom nhóm và đánh giá trước. Việc phụ thuộc hoàn toàn vào các quy tắc ưu tiên ngầm định sẽ làm cho mã nguồn trở nên mong manh và khó đọc, trong khi việc sử dụng dấu ngoặc đơn giúp làm rõ ý định của lập trình viên và ngăn chặn các lỗi logic tinh vi.

### Mô Hình Tư Duy Cây Phân Tích Toán Tử (Operator Parsing Tree Mental Model)

Biểu đồ này minh họa cách thứ tự ưu tiên của toán tử xây dựng các cây phân tích cú pháp khác nhau, thay đổi thứ tự thực thi đối với `10 - 2 * 3` so với `(10 - 2) * 3`:

```mermaid
graph TD
    subgraph Cây phân tích cú pháp: 10 - 2 * 3
        Minus1[-] --> Ten1[10]
        Minus1 --> Times1[*]
        Times1 --> Two1[2]
        Times1 --> Three1[3]
    end

    subgraph Cây phân tích cú pháp: (10 - 2) * 3
        Times2[*] --> Minus2[-]
        Times2 --> Three2[3]
        Minus2 --> Ten2[10]
        Minus2 --> Two2[2]
    end
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)


```text
Biểu thức `10 - 2 * 3` được phân tích cú pháp bởi trình biên dịch
  → trình biên dịch kiểm tra bảng thứ tự ưu tiên toán tử và phát hiện thấy `*` có thứ tự ưu tiên cao hơn `-`
  → biểu thức con `2 * 3` được gom nhóm và đánh giá trước thành `6`
  → toán tử `-` thực hiện phép trừ `6` từ `10`
  → biểu thức tạo ra kết quả `4` (trong khi việc bắt buộc gom nhóm bằng `(10 - 2)` sẽ tạo ra `24`).
```


### Ví Dụ Mã Nguồn (Code Example)

```java
// Trường hợp A: Thứ tự ưu tiên kiểm soát việc đánh giá
int noParens = 10 - 2 * 3;
System.out.println(noParens); // 4 (phép nhân xảy ra trước)

// Trường hợp B: Dấu ngoặc đơn ghi đè thứ tự ưu tiên
int withParens = (10 - 2) * 3;
System.out.println(withParens); // 24 (phép trừ xảy ra trước)

// Trường hợp C: Tính kết hợp giải quyết sự trùng khớp (từ trái sang phải)
int assoc = 12 / 3 / 2; // Được đánh giá là (12 / 3) / 2
System.out.println(assoc); // 2
```

## Dấu Ngoặc Đơn Cũng Dành Cho Con Người (Parentheses Are For Humans Too)

Bạn không cần dùng dấu ngoặc đơn trong mọi biểu thức, nhưng bạn nên sử dụng chúng khi chúng giúp thể hiện rõ ràng ý đồ lập trình.

```java
boolean canAccess = (age >= 18 && hasTicket) || isStaff;
```

Cách viết này dễ đọc hơn nhiều so với việc buộc người đọc phải nhớ thứ tự ưu tiên giữa `&&` và `||`.

## Đánh Giá Ngắn Mạch Với && (Short-Circuit With &&)

`&&` chỉ đánh giá vế bên phải nếu vế bên trái là đúng (true).

```java
if (account != null && account.isActive()) {
    process(account);
}
```

Nếu `account` là null, Java sẽ dừng ngay lập tức. Đây là một mẫu chốt chặn (guard pattern) phổ biến.

## Đánh Giá Ngắn Mạch Với || (Short-Circuit With ||)

`||` chỉ đánh giá vế bên phải nếu vế bên trái là sai (false).

```java
if (isAdmin || hasPermission("DELETE")) {
    deleteItem();
}
```

Nếu `isAdmin` là true, Java sẽ không gọi phương thức `hasPermission`.

## Đánh Giá Ngắn Mạch Và Tác Dụng Phụ (Short-Circuit and Side Effects)

Đánh giá ngắn mạch làm thay đổi việc liệu các tác dụng phụ có xảy ra hay không.

```java
int attempts = 0;
boolean ok = true || ++attempts > 0;
System.out.println(attempts); // 0
```

Phép toán tăng bị bỏ qua. Đây là lý do tại sao các tác dụng phụ bên trong các điều kiện có thể làm cho mã nguồn trở nên khó suy luận hơn.

## Các Thực Hành Tốt Nhất (Best Practices)

- Sử dụng dấu ngoặc đơn khi các toán tử hỗn hợp làm biểu thức khó quét nhanh.
- Tránh các tác dụng phụ bên trong các biểu thức boolean phức tạp.
- Sử dụng `&&` và `||` cho các điều kiện thông thường.
- Chỉ sử dụng `&` và `|` với các kiểu boolean khi bạn chủ ý muốn cả hai vế đều được đánh giá.
- Ưu tiên sử dụng các biến boolean đơn giản, có tên gọi rõ ràng khi một điều kiện trở nên quá dài.

```java
boolean hasValidAge = age >= 18;
boolean hasEntryRight = hasTicket || isStaff;

if (hasValidAge && hasEntryRight) {
    enter();
}
```

---

## Các Lỗi Thường Gặp (Common Mistakes)

### Lỗi 1 — Bất Ngờ Về Thứ Tự Ưu Tiên: || và && (Mistake 1 — Precedence Surprise: || and &&)

```java
// Hai điều kiện này mang ý nghĩa khác nhau!
boolean a = true, b = false, c = true;

boolean r1 = a || b && c;    // a || (b && c) → true || false → true
boolean r2 = (a || b) && c;  // (true || false) && true → true

// Bây giờ với b=true, c=false:
b = true; c = false;
boolean r3 = a || b && c;    // a || (b && c) → true || false → true
boolean r4 = (a || b) && c;  // (true) && false → false  ← kết quả khác nhau!
```

`&&` có thứ tự ưu tiên cao hơn `||`. Luôn đặt dấu ngoặc đơn cho các điều kiện hỗn hợp `&&`/`||` để ngăn ngừa lỗi.

### Lỗi 2 — Đánh Giá Ngắn Mạch Che Giấu Lỗi (Mistake 2 — Short-Circuit Hides a Bug)

```java
int[] arr = null;
int index = 0;

// Điều này trông có vẻ an toàn, nhưng...
if (arr == null | arr[index] > 0) { // | KHÔNG thực hiện đánh giá ngắn mạch!
    System.out.println("check");
}
// Ném ra ngoại lệ NullPointerException vì arr[index] vẫn được đánh giá
// Cách khắc phục: sử dụng && và || chứ không phải & và |
```

### Lỗi 3 — Giả Định Tác Dụng Phụ Luôn Chạy (Mistake 3 — Side Effect Assumed to Always Run)

```java
int counter = 0;

boolean ok = isReady() || (++counter > 0); // nếu isReady() là true, counter vẫn giữ nguyên là 0!
System.out.println(counter); // có thể là 0 hoặc 1 tùy thuộc vào kết quả của isReady()
```

Đoạn mã giả định rằng `++counter` luôn luôn chạy sẽ hoạt động không chính xác khi `isReady()` trả về `true`. Hãy tách phép tăng ra khỏi điều kiện.

### Lỗi 4 — Nhầm Lẫn Giữa = Và == Trong Các Điều Kiện (Mistake 4 — Confusing = and == in Conditions)

```java
boolean enabled = false;
if (enabled = true) {      // biên dịch được! Đây là phép gán, không phải phép so sánh.
    System.out.println("always runs"); // luôn được in ra vì phép gán tạo ra giá trị true
}
// Cách khắc phục:
if (enabled == true) { }   // phép so sánh
if (enabled) { }           // chuẩn Java thông dụng
```

Java cho phép thực hiện phép gán bên trong `if` (vì phép gán là một biểu thức), điều này làm cho lỗi logic này diễn ra âm thầm.
