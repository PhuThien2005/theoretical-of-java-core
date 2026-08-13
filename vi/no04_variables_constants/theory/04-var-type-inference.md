# `var` Và Suy Luận Kiểu (Type Inference)

`var` được giới thiệu trong Java 10 cho việc suy luận kiểu biến cục bộ (local variable type inference).

Nó cho phép trình biên dịch (compiler) suy luận kiểu biến từ giá trị khởi tạo (initializer).

```java
var name = "Alice"; // String
var age = 18;       // int
```

`var` không làm cho Java trở thành ngôn ngữ định kiểu động (dynamically typed). Kiểu dữ liệu vẫn được cố định tại thời điểm biên dịch (compile time).

```java
var age = 18;
// age = "eighteen"; // does not compile
```

## Nơi Có Thể Sử Dụng `var` (Where `var` Can Be Used)

`var` có thể được sử dụng cho các biến cục bộ.

```java
public void demo() {
    var message = "Hello";
}
```

Nó không thể được sử dụng cho các trường (fields).

```java
class Demo {
    // var name = "Alice"; // invalid
}
```

Nó không thể được sử dụng nếu không có giá trị khởi tạo.

```java
// var x; // invalid
```

## Tại Sao Suy Luận Kiểu Với var Chỉ Hoạt Động Cục Bộ (Why var Type Inference Only Works Locally)

Trong Java, suy luận kiểu sử dụng `var` bị giới hạn nghiêm ngặt trong các biến cục bộ. Quyết định thiết kế này xuất phát từ vai trò nền tảng của các trường trong lớp và chữ ký phương thức (method signatures) trong việc thiết lập các hợp đồng lớp (class contracts) và ranh giới API (API boundaries). Các trường và phương thức có thể được nhìn thấy từ bên ngoài lớp khai báo (declaring class) của chúng, và kiểu dữ liệu của chúng phải được xác định rõ ràng trong siêu dữ liệu (metadata) ở thời điểm biên dịch (các tệp `.class`) để các lớp khác có thể được biên dịch độc lập (separate compilation). Việc cho phép các trường hoặc chữ ký phương thức sử dụng `var` sẽ đồng nghĩa với việc trình biên dịch phải phân tích cú pháp các khối khởi tạo nội bộ của một tệp lớp để phân giải các kiểu dữ liệu cần thiết cho một tệp lớp khác, làm phá vỡ tính năng biên dịch độc lập. Mặt khác, các biến cục bộ là các chi tiết triển khai nội bộ (internal implementation details) chỉ giới hạn trong một khối phương thức duy nhất, giúp việc suy luận trở nên hoàn toàn an toàn và mang tính cục bộ.

### Hợp Đồng API Đối Với Triển Khai Nội Bộ (API Contract vs. Internal Implementation)

```text
Public Boundary (API Contract) ──> Must be Explicitly Typed
[Class Demo] 
  ├── Field: public int count; ──> Explicit Type (Required)
  └── Method: public String process() ──> Explicit Return Type (Required)
  
Internal implementation (Hidden) ──> Local Inference Allowed
  └── Method Body:
        └── var list = new ArrayList<String>(); ──> Inferred Local Type
```

### Minh Họa Code Về Ranh Giới Suy Luận Kiểu (Type Inference Boundary Code Demo)

```java
public class ContractDemo {
    // Compile Error: 'var' is not allowed on fields
    // public var status = "ACTIVE"; 
    
    // Compile Error: 'var' is not allowed in method parameter or return types
    // public var doWork(var input) { return "Done"; }

    public String getStatus() {
        // Allowed: local variable is confined to getStatus() execution frame
        var currentStatus = "ACTIVE"; 
        return currentStatus;
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả Của Hợp Đồng API (API Contract Cause-Effect Chain)

`var` được cho phép trên các trường/phương thức $\rightarrow$ Trình biên dịch phải phân tích thân phương thức để xác định các kiểu API công khai $\rightarrow$ Quá trình biên dịch độc lập giữa các lớp trở nên phụ thuộc lẫn nhau $\rightarrow$ Việc sửa đổi mã nội bộ làm hỏng các lớp bên ngoài một cách bất ngờ $\rightarrow$ `var` bị giới hạn trong phạm vi cục bộ $\rightarrow$ Các giao diện lớp vẫn ở trạng thái tĩnh và tường minh, duy trì tốc độ biên dịch và sự ổn định.

## Khi Nào `var` Có Ích (When `var` Helps)

`var` có thể giảm bớt sự rườm rà khi kiểu dữ liệu đã quá rõ ràng.

```java
var names = new ArrayList<String>();
```

## Khi Nào `var` Gây Hại (When `var` Hurts)

`var` có thể làm cho mã nguồn khó đọc hơn khi kiểu dữ liệu không rõ ràng.

Kém hiệu quả:

```java
var result = service.process(input);
```

Nếu `process` không làm rõ kiểu trả về, việc định kiểu tường minh có thể sẽ tốt hơn.

## `var` Trong Vòng Lặp For-Each (`var` in For-Each Loops)

`var` cũng hoạt động trong các vòng lặp for-each cải tiến (enhanced for-each loops) (Java 10+):

```java
var names = List.of("Alice", "Bob", "Carol");
for (var name : names) {
    System.out.println(name); // name is inferred as String
}
```

## Ví Dụ Thực Tế: Khi Nào `var` Có Ích và Khi Nào Gây Hại (Case Study: When `var` Helps vs. When It Hurts)

**✅ `var` cải thiện khả năng đọc** — kiểu dữ liệu đã rõ ràng từ vế phải:

```java
// Without var — verbose and redundant
HashMap<String, List<Integer>> scores = new HashMap<String, List<Integer>>();

// With var — type is still clear, less noise
var scores = new HashMap<String, List<Integer>>();
```

**❌ `var` làm giảm khả năng đọc** — kiểu trả về bị ẩn sau tên phương thức:

```java
// What is result? String? Integer? List? Nobody knows without checking the method.
var result = parser.parse(rawInput);

// Explicit type communicates intent immediately
ParsedResult result = parser.parse(rawInput);
```

**Quy tắc bất di bất dịch**: sử dụng `var` khi biểu thức ở vế phải *thể hiện rõ* kiểu dữ liệu (các lệnh gọi hàm khởi tạo (constructor calls), giá trị tường minh (literals), `new`, hoặc ép kiểu (casts)). Tránh sử dụng `var` khi kiểu dữ liệu đến từ một lệnh gọi phương thức mà tên của nó không tiết lộ kiểu trả về.

## `var` Không Thể Được Sử Dụng Cho (`var` Cannot Be Used For)

```java
class Config {
    var timeout = 30;         // compile error: 'var' not allowed here (field)

    var compute() {           // compile error: 'var' not allowed here (return type)
        return 42;
    }
}

void broken() {
    var x;                    // compile error: cannot infer type (no initializer)
    var y = null;             // compile error: cannot infer type from null alone
}
```

## Các Sai Lầm Thường Gặp (Common Mistakes)

- Nghĩ rằng `var` nghĩa là định kiểu động — kiểu dữ liệu thực chất được cố định tại thời điểm biên dịch.
- Cố gắng sử dụng `var` cho các trường — nó chỉ được phép dùng cho các biến cục bộ.
- Sử dụng `var` không có giá trị khởi tạo — trình biên dịch cần có giá trị khởi tạo để suy luận kiểu dữ liệu.
- Sử dụng `var` khi nó che giấu thông tin kiểu dữ liệu quan trọng, đặc biệt là với các giá trị trả về của phương thức.
- Khởi tạo `var` với `null` — trình biên dịch không thể suy luận kiểu dữ liệu chỉ từ duy nhất giá trị `null`.

## Các Liên Kết Tham Khảo (Reference Links)

- [Đặc tả Ngôn ngữ Java: Suy luận Kiểu Biến Cục bộ](https://docs.oracle.com/javase/specs/jls/se21/html/jls-14.html#jls-14.4)
- [OpenJDK FAQ: Suy luận Kiểu Biến Cục bộ](https://openjdk.org/projects/amber/LVTIstyle.html)
