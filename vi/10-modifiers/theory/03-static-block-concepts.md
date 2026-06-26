# Các bộ điều chỉnh trong Java (Modifiers in Java) - Phần 3

## Mục tiêu học tập (Learning Goal)

Tập tin này bao gồm một phần trọng tâm về **Các bộ điều chỉnh trong Java (Modifiers in Java)**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, không phải là từ vựng riêng lẻ.

## Khái quát nội dung (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `Static block` | Static nghĩa là thành viên thuộc về lớp (class) thay vì một đối tượng (object) cụ thể. |
| `Static nested class` | Static nghĩa là thành viên thuộc về lớp thay vì một đối tượng cụ thể. |
| `Static import` | Static nghĩa là thành viên thuộc về lớp thay vì một đối tượng cụ thể. |
| `Final variable` | Final nghĩa là biến, phương thức, lớp hoặc tham số bị hạn chế thay đổi sau đó theo một cách cụ thể. |
| `Final method` | Final nghĩa là biến, phương thức, lớp hoặc tham số bị hạn chế thay đổi sau đó theo một cách cụ thể. |
| `Final class` | Final nghĩa là biến, phương thức, lớp hoặc tham số bị hạn chế thay đổi sau đó theo một cách cụ thể. |
| `Final parameter` | Final nghĩa là biến, phương thức, lớp hoặc tham số bị hạn chế thay đổi sau đó theo một cách cụ thể. |
| `Blank final variable` | Final nghĩa là biến, phương thức, lớp hoặc tham số bị hạn chế thay đổi sau đó theo một cách cụ thể. |

## Ghi chú chi tiết (Detailed Notes)

### Static block (Khối tĩnh)

Static nghĩa là thành viên thuộc về lớp thay vì một đối tượng cụ thể.

Nó quan trọng vì mã nguồn đồng thời (concurrent code) có thể trông chính xác trong các bài kiểm tra đơn luồng (single-thread) nhưng thất bại dưới áp lực thời gian (timing pressure). Một sự nhầm lẫn phổ biến là giả định rằng khả năng hiển thị (visibility), thứ tự (ordering), và tính nguyên tử (atomicity) là cùng một đảm bảo.

#### Ví dụ mã nguồn khối static (Static Block Code Example)
```java
public class DatabaseConnector {
    private static String connectionUrl;

    // Static block runs once when the class is first loaded by the JVM
    static {
        try {
            // Complex initialization that could throw exceptions
            connectionUrl = "jdbc:mysql://localhost:3306/prod_db";
            System.out.println("Static block: Database URL initialized.");
        } catch (Exception e) {
            System.err.println("Failed to initialize database connection URL");
        }
    }
}
```

#### Lỗi thường gặp - Truy cập các trường thể hiện hoặc ném các ngoại lệ kiểm tra trong khối static (Common Mistake - Accessing instance fields or throwing checked exceptions in static blocks)
Các khối static chạy trong quá trình tải lớp, trước khi bất kỳ thực thể nào của lớp tồn tại. Do đó, chúng không thể truy cập các trường hoặc phương thức thể hiện. Ngoài ra, bạn không thể ném các ngoại lệ kiểm tra (checked exception) ra khỏi một khối static; chúng phải được bắt bằng cách sử dụng `try-catch` bên trong khối đó, nếu không JVM sẽ ném ra lỗi `ExceptionInInitializerError`.

Kiểm tra thực tế (Practical check):

- Định nghĩa `Static block` trong một câu.
- Nhận diện `Static block` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `Static block`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- `ClassName.member` truy cập vào một thành viên cấp lớp.

### Static nested class (Lớp lồng tĩnh)

Static nghĩa là thành viên thuộc về lớp thay vì một đối tượng cụ thể.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại. Hãy xem lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn.

#### Ví dụ mã nguồn lớp lồng static (Static Nested Class Code Example)
```java
public class Outer {
    private static int outerStatic = 10;
    private int outerInstance = 20;

    // Static nested class
    public static class Nested {
        public void print() {
            System.out.println("Outer static field: " + outerStatic); // OK
            // System.out.println(outerInstance); // COMPILE ERROR! No outer instance context
        }
    }
}
```

#### Lỗi thường gặp - Nhầm lẫn giữa lớp lồng static và lớp nội bộ (Common Mistake - Confusing static nested classes with inner classes)
Một lớp lồng static (static nested class) không có một tham chiếu ngầm định đến một thực thể của lớp bên ngoài. Để khởi tạo nó, bạn không cần một thực thể lớp bên ngoài: `Outer.Nested nested = new Outer.Nested();`. Tuy nhiên, các lớp nội bộ phi tĩnh (non-static inner class) lại yêu cầu một thực thể lớp bên ngoài: `outerInstance.new Inner()`.

Kiểm tra thực tế (Practical check):

- Định nghĩa `Static nested class` trong một câu.
- Nhận diện `Static nested class` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `Static nested class`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- `ClassName.member` truy cập vào một thành viên cấp lớp.

### Static import (Nhập tĩnh)

Static nghĩa là thành viên thuộc về lớp thay vì một đối tượng cụ thể.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại. Hãy xem lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn.

#### Ví dụ mã nguồn Import static (Static Import Code Example)
```java
// Importing static Math.sqrt and Math.pow
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

public class Geometry {
    public double hypotenuse(double a, double b) {
        // We can call static Math methods directly without Math. prefix
        return sqrt(pow(a, 2) + pow(b, 2));
    }
}
```

#### Lỗi thường gặp - Viết import static sai thứ tự (Common Mistake - Writing import static in the wrong order)
Cú pháp bắt buộc phải là `import static package.Class.member;` hoặc `import static package.Class.*;`. Viết `static import` là một lỗi biên dịch. Ngoài ra, bạn không thể import static toàn bộ một gói (ví dụ, `import static java.lang.*;` là không hợp lệ; bạn import các thành viên của lớp, chứ không phải bản thân các lớp đó).

Kiểm tra thực tế (Practical check):

- Định nghĩa `Static import` trong một câu.
- Nhận diện `Static import` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `Static import`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- `ClassName.member` truy cập vào một thành viên cấp lớp.

### Final variable (Biến final)

Final nghĩa là biến, phương thức, lớp hoặc tham số bị hạn chế thay đổi sau đó theo một cách cụ thể.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại. Hãy xem lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn.

#### Ví dụ mã nguồn biến final (Final Variable Code Example)
```java
public class Calculation {
    public void run() {
        final int maxIterations = 50;
        // maxIterations = 60; // COMPILE ERROR! Cannot reassign a final variable
        System.out.println(maxIterations);
    }
}
```

#### Lỗi thường gặp - Giả định các trường final bắt buộc phải được khởi tạo khi khai báo (Common Mistake - Assuming final fields must be initialized at declaration)
Một biến thể hiện `final` không bắt buộc phải khởi tạo khi khai báo; nó có thể được để trống ban đầu và khởi tạo bên trong hàm dựng (constructor). Tuy nhiên, nó phải được gán giá trị trong mọi luồng thực thi của tất cả các hàm dựng trước khi biên dịch thành công.

Kiểm tra thực tế (Practical check):

- Định nghĩa `Final variable` trong một câu.
- Nhận diện `Final variable` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `Final variable`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- `final int limit = 10;` không thể bị gán lại giá trị.

## Tại sao các biến Final ngăn chặn việc gán lại và cho phép tối ưu hóa Inlining (Why Final Variables Prevent Re-Assignment and Enable Inlining)

Từ khóa `final` trên một biến đảm bảo rằng một khi giá trị đã được gán, tham chiếu hoặc giá trị của biến đó không thể thay đổi trong suốt thời gian tồn tại của nó. Đối với các biến kiểu nguyên thủy (primitive), điều này ngăn giá trị số bị sửa đổi, trong khi đối với các biến kiểu tham chiếu (reference), nó ngăn tham chiếu trỏ đến một đối tượng khác (mặc dù các trường nội bộ của đối tượng vẫn có thể thay đổi được). Bởi vì `final` đảm bảo các giá trị hằng số ở thời gian biên dịch (compile-time constant) khi được khởi tạo với hằng số, trình biên dịch Java và trình biên dịch Just-In-Time (JIT) có thể thực hiện một tối ưu hóa gọi là **inlining (chèn trực tiếp)**. Inlining thay thế tên biến hoặc cuộc gọi phương thức trực tiếp bằng giá trị hằng số hoặc phần thân phương thức tại thời điểm biên dịch, loại bỏ chi phí tìm kiếm biến hoặc điều phối phương thức (method dispatch) và tăng cường hiệu năng thời gian chạy.

### Mô hình tối ưu hóa chèn trực tiếp của trình biên dịch (Compiler Inlining Optimization Model)

```mermaid
graph LR
    subgraph Before_Optimization [Before Optimization]
        Code1["final int LIMIT = 100;<br/>if (x > LIMIT) { ... }"]
    end
    subgraph After_Optimization [After Optimization (Inlined)]
        Code2["if (x > 100) { ... }"]
    end
    Before_Optimization -- "Compiler replaces LIMIT with 100" --> After_Optimization
```

### Ví dụ mã nguồn: Minh họa biến Final và tối ưu hóa Inlining (Code Example: Demonstration of Final Variable and Inlining)
```java
public class OptimizationDemo {
    // Compile-time constant: final + primitive/String + constant expression
    public static final int MAX_USERS = 500;

    public void displayLimit() {
        // The compiler replaces MAX_USERS with the literal 500 in the bytecode
        System.out.println("Limit: " + MAX_USERS); 
    }

    public static void main(String[] args) {
        OptimizationDemo demo = new OptimizationDemo();
        demo.displayLimit(); // Output: Limit: 500
    }
}
```

### Chuỗi nguyên nhân - kết quả của các biến Final (Cause-Effect Chain of Final Variables)
- **Tác nhân kích hoạt (Trigger)**: Biến được khai báo với từ khóa `final`.
- **Hiệu ứng tức thời (Immediate Effect)**: Trình biên dịch ngăn chặn bất kỳ sự gán lại nào đối với biến sau lần khởi tạo đầu tiên của nó.
- **Hiệu ứng thứ cấp (Secondary Effect)**: Nếu giá trị là một hằng số thời gian biên dịch, trình biên dịch có thể thay thế trực tiếp giá trị hằng đó ở bất kỳ nơi nào biến được tham chiếu.
- **Kết quả cuối cùng (Ultimate Outcome)**: Việc gán lại biến bị chặn ở thời gian biên dịch, và hiệu năng thời gian chạy được nâng cao thông qua JIT/compiler inlining.


### Final method (Phương thức final)

Final nghĩa là biến, phương thức, lớp hoặc tham số bị hạn chế thay đổi sau đó theo một cách cụ thể.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại. Hãy xem lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn.

#### Ví dụ mã nguồn phương thức final (Final Method Code Example)
```java
public class Parent {
    public final void showMessage() {
        System.out.println("This is a final method.");
    }
}

class Child extends Parent {
    // Attempting to override final method causes compile error:
    // public void showMessage() { ... }
}
```

#### Lỗi thường gặp - Cố gắng ghi đè một phương thức final trong lớp con (Common Mistake - Attempting to override a final method in a subclass)
Nếu một lớp con cố gắng khai báo một phương thức có cùng chữ ký (signature) và kiểu trả về với một phương thức `final` trong lớp cha, trình biên dịch sẽ từ chối. Lưu ý rằng các phương thức `private` ngầm định là final, do đó việc khai báo chúng là final là hợp lệ nhưng dư thừa.

Kiểm tra thực tế (Practical check):

- Định nghĩa `Final method` trong một câu.
- Nhận diện `Final method` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `Final method`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- `final int limit = 10;` không thể bị gán lại giá trị.

### Final class (Lớp final)

Final nghĩa là biến, phương thức, lớp hoặc tham số bị hạn chế thay đổi sau đó theo một cách cụ thể.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại. Hãy xem lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn.

#### Ví dụ mã nguồn lớp final (Final Class Code Example)
```java
public final class UtilityClass {
    public static void printLog(String message) {
        System.out.println("LOG: " + message);
    }
}

// Attempting to subclass UtilityClass causes compile error:
// class SubUtility extends UtilityClass { }
```

#### Lỗi thường gặp - Giả định các trường trong một lớp final sẽ tự động là final (Common Mistake - Assuming fields in a final class are automatically final)
Khai báo một lớp là `final` chỉ ngăn lớp đó bị kế thừa (subclassed). Nó KHÔNG tự động biến các trường của lớp đó thành `final` hoặc bất biến (immutable). Nếu bạn muốn các trường là bất biến, bạn vẫn phải khai báo rõ ràng chúng là `final`.

Kiểm tra thực tế (Practical check):

- Định nghĩa `Final class` trong một câu.
- Nhận diện `Final class` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `Final class`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- `final int limit = 10;` không thể bị gán lại giá trị.

### Final parameter (Tham số final)

Final nghĩa là biến, phương thức, lớp hoặc tham số bị hạn chế thay đổi sau đó theo một cách cụ thể.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại. Hãy xem lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn.

#### Ví dụ mã nguồn tham số final (Final Parameter Code Example)
```java
public class Logger {
    public void log(final String message) {
        // message = "New message"; // COMPILE ERROR! Cannot modify final parameter
        System.out.println(message);
    }
}
```

#### Lỗi thường gặp - Gán lại các tham số phương thức bên trong thân phương thức (Common Mistake - Reassigning method parameters inside method bodies)
Đánh dấu tham số phương thức là `final` là một thực hành tốt (best practice) để ngăn ngừa việc vô tình gán lại giá trị bên trong thân phương thức. Cố gắng gán một giá trị mới cho một tham số final sẽ dẫn đến lỗi thời gian biên dịch.

Kiểm tra thực tế (Practical check):

- Định nghĩa `Final parameter` trong một câu.
- Nhận diện `Final parameter` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `Final parameter`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- `final int limit = 10;` không thể bị gán lại giá trị.

### Blank final variable (Biến final trống)

Final nghĩa là biến, phương thức, lớp hoặc tham số bị hạn chế thay đổi sau đó theo một cách cụ thể.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại. Hãy xem lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn.

#### Ví dụ mã nguồn biến final trống (Blank Final Variable Code Example)
```java
public class Order {
    private final long orderId; // Blank final variable
    private static final String DEFAULT_STATUS; // Blank static final variable

    static {
        DEFAULT_STATUS = "PENDING"; // Initialized in static block
    }

    public Order(long orderId) {
        this.orderId = orderId; // Initialized in constructor
    }
}
```

#### Lỗi thường gặp - Thất bại trong phân tích gán xác định (Common Mistake - Definite Assignment Analysis failure)
Một trường final trống bắt buộc phải được gán giá trị chính xác một lần. Nếu một constructor chứa một luồng có điều kiện (ví dụ: câu lệnh `if-else`) nơi trường final trống chỉ được gán giá trị ở một nhánh, trình biên dịch sẽ báo lỗi "variable orderId might not have been initialized" (biến orderId có thể chưa được khởi tạo).

Kiểm tra thực tế (Practical check):

- Định nghĩa `Blank final variable` trong một câu.
- Nhận diện `Blank final variable` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `Blank final variable`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- `final int limit = 10;` không thể bị gán lại giá trị.

## Các câu hỏi ôn tập thường gặp (Common Review Prompts)

- Khái niệm nào ở đây là quy tắc thời gian biên dịch (compile-time rule)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi thời gian chạy (runtime behavior)?
- Khái niệm nào ở đây dễ là bẫy phỏng vấn?
