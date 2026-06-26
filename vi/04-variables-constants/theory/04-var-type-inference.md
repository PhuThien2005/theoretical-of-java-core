# Từ Khóa `var` Và Suy Luận Kiểu Dữ Liệu (`var` And Type Inference)

Từ khóa `var` được giới thiệu từ phiên bản Java 10 nhằm hỗ trợ cơ chế suy luận kiểu dữ liệu của biến cục bộ (local variable type inference).

Nó cho phép trình biên dịch tự động suy luận kiểu dữ liệu của biến dựa trên biểu thức khởi tạo (initializer).

```java
var name = "Alice"; // Trình biên dịch tự động hiểu là String
var age = 18;       // Trình biên dịch tự động hiểu là int
```

Việc sử dụng `var` không biến Java thành một ngôn ngữ định kiểu động (dynamically typed). Kiểu dữ liệu của biến vẫn được cố định ở thời điểm biên dịch (compile-time).

```java
var age = 18;
// age = "eighteen"; // không biên dịch được vì age đã được cố định kiểu int
```

## Nơi Có Thể Sử Dụng `var` (Where `var` Can Be Used)

Từ khóa `var` chỉ có thể được dùng cho các biến cục bộ (local variables).

```java
public void demo() {
    var message = "Hello";
}
```

Nó không thể sử dụng để khai báo các trường dữ liệu (fields) của lớp.

```java
class Demo {
    // var name = "Alice"; // không hợp lệ
}
```

Nó không thể sử dụng nếu không có biểu thức gán giá trị khởi tạo.

```java
// var x; // không hợp lệ
```

## Tại Sao Suy Luận Kiểu Dữ Liệu var Chỉ Hoạt Động Cục Bộ (Why var Type Inference Only Works Locally)

Trong Java, cơ chế suy luận kiểu dữ liệu bằng `var` bị giới hạn nghiêm ngặt trong phạm vi các biến cục bộ. Quyết định thiết kế này bắt nguồn từ vai trò nền tảng của các trường dữ liệu lớp và chữ ký phương thức (method signatures) trong việc thiết lập các hợp đồng của lớp (class contracts) và ranh giới API. Các trường dữ liệu và phương thức hiển thị ra bên ngoài lớp khai báo chúng, và kiểu dữ liệu của chúng bắt buộc phải được định nghĩa tường minh trong siêu dữ liệu lúc biên dịch (các file `.class`) để các lớp khác có thể được biên dịch một cách độc lập. Nếu cho phép các trường dữ liệu hoặc chữ ký phương thức sử dụng `var`, trình biên dịch sẽ phải phân tích cú pháp khối khởi tạo nội bộ của một file lớp để giải quyết các kiểu dữ liệu mà một file lớp khác cần, điều này phá vỡ cơ chế biên dịch độc lập (separate compilation). Ngược lại, biến cục bộ chỉ là chi tiết triển khai nội bộ nằm gọn trong một khối phương thức duy nhất, giúp việc suy luận kiểu hoàn toàn an toàn và mang tính cục bộ.

### Hợp Đồng API so với Triển Khai Nội Bộ (API Contract vs. Internal Implementation)

```text
Ranh giới công khai (Hợp đồng API) ──> Bắt buộc phải khai báo kiểu rõ ràng
[Lớp Demo] 
  ├── Trường dữ liệu (Field): public int count; ──> Kiểu tường minh (Bắt buộc)
  └── Phương thức: public String process() ──> Kiểu trả về tường minh (Bắt buộc)
  
Triển khai nội bộ (Ẩn) ──> Cho phép suy luận kiểu cục bộ
  └── Thân phương thức (Method Body):
        └── var list = new ArrayList<String>(); ──> Kiểu cục bộ được suy luận
```

### Minh Họa Code Về Ranh Giới Suy Luận Kiểu Dữ Liệu
```java
public class ContractDemo {
    // Lỗi biên dịch: 'var' không được phép dùng cho trường dữ liệu của lớp
    // public var status = "ACTIVE"; 
    
    // Lỗi biên dịch: 'var' không được phép dùng cho tham số hoặc kiểu trả về của phương thức
    // public var doWork(var input) { return "Done"; }

    public String getStatus() {
        // Hợp lệ: biến cục bộ được giới hạn trong khung thực thi của getStatus()
        var currentStatus = "ACTIVE"; 
        return currentStatus;
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả Về Hợp Đồng API

```text
Cho phép `var` trên các trường/phương thức
  → Trình biên dịch bắt buộc phải phân tích thân phương thức để xác định kiểu API công khai
  → Quá trình biên dịch độc lập của các lớp trở nên phụ thuộc lẫn nhau
  → Việc sửa đổi mã nguồn nội bộ làm lỗi các lớp bên ngoài một cách bất ngờ
  → `var` bị giới hạn trong phạm vi cục bộ
  → Các giao diện của lớp (class interfaces) duy trì tính tĩnh và rõ ràng, bảo toàn tốc độ biên dịch và sự ổn định của hệ thống.
```


## Khi Nào `var` Có Ích (When `var` Helps)

Từ khóa `var` giúp giảm bớt sự rườm rà (noise) trong viết code khi mà kiểu dữ liệu đã quá rõ ràng.

```java
var names = new ArrayList<String>();
```

## Khi Nào `var` Gây Hại (When `var` Hurts)

Sử dụng `var` có thể làm cho mã nguồn trở nên khó đọc hơn khi kiểu dữ liệu của biến không còn hiển nhiên.

Cách viết chưa tốt:

```java
var result = service.process(input);
```

Nếu phương thức `process` không thể hiện rõ kiểu trả về thông qua tên gọi của nó, việc khai báo kiểu tường minh sẽ là lựa chọn tốt hơn.

## `var` Trong Vòng Lặp For-Each

Từ khóa `var` cũng hoạt động tốt trong các vòng lặp for-each cải tiến (Java 10 trở lên):

```java
var names = List.of("Alice", "Bob", "Carol");
for (var name : names) {
    System.out.println(name); // name được suy luận là String
}
```

## Case Study: Khi Nào Nên Dùng và Không Nên Dùng `var` (Case Study: When var Helps vs. When It Hurts)

**✅ `var` cải thiện khả năng đọc code** — kiểu dữ liệu hiển nhiên từ vế phải của phép gán:

```java
// Không dùng var — rườm rà và dư thừa
HashMap<String, List<Integer>> scores = new HashMap<String, List<Integer>>();

// Dùng var — kiểu dữ liệu vẫn rõ ràng, code gọn hơn
var scores = new HashMap<String, List<Integer>>();
```

**❌ `var` làm giảm khả năng đọc code** — kiểu trả về bị ẩn sau tên phương thức:

```java
// Biến result chứa cái gì? String? Integer? List? Không ai biết nếu không tra cứu phương thức.
var result = parser.parse(rawInput);

// Khai báo kiểu tường minh giúp truyền đạt ý đồ lập trình ngay lập tức
ParsedResult result = parser.parse(rawInput);
```

**Quy tắc cốt lõi (Rule of thumb)**: Hãy sử dụng `var` khi biểu thức ở vế phải *thể hiện rõ* kiểu dữ liệu (gọi constructor, hằng số, toán tử `new`, ép kiểu). Tránh dùng `var` khi kiểu dữ liệu trả về từ một lệnh gọi phương thức mà tên phương thức đó không làm lộ kiểu trả về.

## Các Trường Hợp `var` Không Thể Sử Dụng

```java
class Config {
    var timeout = 30;         // lỗi biên dịch: 'var' không được phép ở đây (trường dữ liệu của lớp)

    var compute() {           // lỗi biên dịch: 'var' không được phép ở đây (kiểu trả về)
        return 42;
    }
}

void broken() {
    var x;                    // lỗi biên dịch: không thể suy luận kiểu (thiếu gán giá trị khởi tạo)
    var y = null;             // lỗi biên dịch: không thể suy luận kiểu từ riêng giá trị null
}
```

## Các Lỗi Thường Gặp (Common Mistakes)

- Nghĩ rằng `var` mang ý nghĩa định kiểu động — kiểu dữ liệu thực tế vẫn được cố định lúc biên dịch.
- Cố gắng sử dụng `var` cho các trường dữ liệu của lớp — nó chỉ được phép dùng cho biến cục bộ.
- Sử dụng `var` mà không gán giá trị khởi tạo — trình biên dịch cần biểu thức khởi tạo để suy luận kiểu.
- Sử dụng `var` khi nó che giấu đi thông tin kiểu dữ liệu quan trọng, đặc biệt là với các giá trị trả về của phương thức.
- Khởi tạo `var` với giá trị `null` — trình biên dịch không thể suy luận kiểu chỉ từ riêng giá trị `null`.


## Liên kết tham khảo (Reference Links)

- [Java Language Specification: Local Variable Type Inference](https://docs.oracle.com/javase/specs/jls/se21/html/jls-14.html#jls-14.4)
- [OpenJDK FAQ: Local Variable Type Inference](https://openjdk.org/projects/amber/LVTIstyle.html)
