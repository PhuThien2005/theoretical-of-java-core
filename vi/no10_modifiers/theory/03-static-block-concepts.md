# Các Từ Khóa Đặc Tả Trong Java - Phần 3 (Modifiers in Java - Part 3)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này bao quát một phần trọng tâm của **Các Từ Khóa Đặc Tả Trong Java**. Hãy nghiên cứu từng khái niệm dưới dạng quy tắc thực tế trong Java, chứ không phải các thuật ngữ lý thuyết đơn thuần.

## Đề Cương Bao Phủ (Outline Coverage)

- **`Static block`** — Khối mã chạy một lần duy nhất khi lớp được tải lần đầu bởi JVM, thường dùng để khởi tạo biến tĩnh.
- **`Static nested class`** — Lớp lồng tĩnh hoạt động độc lập, không yêu cầu thể hiện của lớp bên ngoài để khởi tạo.
- **`Static import`** — Cho phép truy cập trực tiếp các thành viên tĩnh (biến, phương thức) của lớp khác mà không cần tiền tố tên lớp.
- **`Final variable`** — Biến có giá trị không thể bị thay đổi sau khi đã được khởi tạo (tạo ra hằng số hoặc đối tượng bất biến).
- **`Final method`** — Phương thức không thể bị ghi đè (override) bởi bất kỳ lớp con nào.
- **`Final class`** — Lớp không thể bị kế thừa (extend) bởi bất kỳ lớp nào khác, đảm bảo tính bảo mật và bất biến.
- **`Final parameter`** — Tham số phương thức không thể bị gán lại giá trị mới bên trong thân phương thức.
- **`Blank final variable`** — Biến `final` chưa được gán giá trị khi khai báo, nhưng bắt buộc phải được gán giá trị chính xác một lần trong constructor hoặc khối tĩnh.

## Ghi Chú Chi Tiết

### Khối tĩnh (Static block)

Khối mã chạy một lần duy nhất khi lớp được tải lần đầu bởi JVM, thường dùng để khởi tạo biến tĩnh hoặc thực hiện các thiết lập ban đầu.

Khái niệm này rất quan trọng vì mã nguồn xử lý đồng thời có thể hoạt động chính xác trong các bài kiểm tra đơn luồng nhưng lại thất bại khi chịu áp lực về mặt thời gian thực thi. Một sự nhầm lẫn phổ biến là giả định rằng khả năng hiển thị (visibility), thứ tự thực thi (ordering) và tính nguyên tử (atomicity) đều là các cơ chế đảm bảo giống nhau.

#### Ví Dụ Mã Nguồn Khối Tĩnh
```java
public class DatabaseConnector {
    private static String connectionUrl;

    // Khối tĩnh chạy một lần duy nhất khi lớp được tải lần đầu bởi JVM
    static {
        try {
            // Khởi tạo phức tạp có thể ném ra ngoại lệ
            connectionUrl = "jdbc:mysql://localhost:3306/prod_db";
            System.out.println("Static block: Đã khởi tạo URL cơ sở dữ liệu.");
        } catch (Exception e) {
            System.err.println("Không thể khởi tạo URL kết nối cơ sở dữ liệu");
        }
    }
}
```

#### Lỗi Thường Gặp - Truy cập các trường thực thể hoặc ném ra các ngoại lệ checked trong khối tĩnh
Các khối tĩnh chạy trong quá trình tải lớp, trước khi bất kỳ thể hiện nào của lớp được tạo ra. Do đó, chúng không thể truy cập các trường hoặc phương thức thực thể (instance). Thêm vào đó, bạn không thể ném các ngoại lệ checked ra ngoài khối tĩnh; chúng bắt buộc phải được bắt bằng khối `try-catch` bên trong khối tĩnh, nếu không JVM sẽ ném ra lỗi `ExceptionInInitializerError`.

### Lớp lồng tĩnh (Static nested class)

Lớp lồng tĩnh hoạt động độc lập, không chứa tham chiếu ngầm định đến thể hiện của lớp bên ngoài và không yêu cầu thể hiện của lớp ngoài để khởi tạo.

#### Ví Dụ Mã Nguồn Lớp Lồng Tĩnh
```java
public class Outer {
    private static int outerStatic = 10;
    private int outerInstance = 20;

    // Lớp lồng tĩnh
    public static class Nested {
        public void print() {
            System.out.println("Trường tĩnh của lớp ngoài: " + outerStatic); // Hợp lệ
            // System.out.println(outerInstance); // LỖI BIÊN DỊCH! Không có ngữ cảnh thể hiện của lớp ngoài
        }
    }
}
```

#### Lỗi Thường Gặp - Nhầm lẫn lớp lồng tĩnh với lớp nội bộ (inner class)
Một lớp lồng tĩnh không chứa một tham chiếu ngầm định đến một thể hiện của lớp bên ngoài. Để khởi tạo nó, bạn không cần một thể hiện của lớp ngoài: `Outer.Nested nested = new Outer.Nested();`. Ngược lại, các lớp nội bộ phi tĩnh yêu cầu một thể hiện của lớp ngoài: `outerInstance.new Inner()`.

### Import tĩnh (Static import)

Cú pháp cho phép truy cập trực tiếp các thành viên tĩnh (biến, phương thức) của lớp khác mà không cần lặp lại tiền tố tên lớp, giúp mã nguồn ngắn gọn hơn.

#### Ví Dụ Mã Nguồn Import Tĩnh
```java
// Import tĩnh Math.sqrt và Math.pow
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

public class Geometry {
    public double hypotenuse(double a, double b) {
        // Chúng ta có thể gọi các phương thức Math tĩnh trực tiếp mà không cần tiền tố Math.
        return sqrt(pow(a, 2) + pow(b, 2));
    }
}
```

#### Lỗi Thường Gặp - Viết sai thứ tự import static
Cú pháp bắt buộc phải là `import static package.Class.member;` hoặc `import static package.Class.*;`. Viết `static import` sẽ gây ra lỗi biên dịch. Thêm vào đó, bạn không thể import tĩnh toàn bộ một package (ví dụ: `import static java.lang.*;` là không hợp lệ; bạn chỉ có thể import các thành viên của lớp chứ không phải bản thân các lớp đó).

### Biến final (Final variable)

Biến có giá trị không thể bị thay đổi sau khi đã được khởi tạo, thường được sử dụng để tạo các hằng số hoặc đảm bảo tính bất biến (immutability).

#### Quy Ước Đặt Tên (Naming Convention)
Khi kết hợp `static final` để tạo hằng số toàn cục, quy ước bắt buộc trong Java là sử dụng chữ in hoa phân cách bằng dấu gạch dưới (UPPER_SNAKE_CASE). Ví dụ: `public static final int MAX_USERS = 500;`.

#### Thread-Safety Của Biến Final (JMM Guarantee)
Biến `final` có một ý nghĩa đặc biệt trong Mô hình Bộ nhớ Java (JMM). JMM đảm bảo rằng nếu một biến `final` được khởi tạo trong constructor, thì **bất kỳ luồng nào** khi nhận được tham chiếu của đối tượng đó cũng sẽ nhìn thấy giá trị chính xác của biến `final` (miễn là không để lọt tham chiếu đối tượng ra ngoài `this` trước khi constructor hoàn tất). Điều này làm cho các đối tượng bất biến (immutable objects) tự động đạt chuẩn thread-safe.

#### Ví Dụ Mã Nguồn Biến Final
```java
public class Calculation {
    public void run() {
        final int maxIterations = 50;
        // maxIterations = 60; // LỖI BIÊN DỊCH! Không thể gán lại giá trị cho một biến final
        System.out.println(maxIterations);
    }
}
```

#### Lỗi Thường Gặp - Giả định các trường final bắt buộc phải khởi tạo khi khai báo
Một biến thể hiện final không nhất thiết phải khởi tạo ngay khi khai báo; nó có thể được để trống lúc đầu và được khởi tạo bên trong hàm khởi dựng. Tuy nhiên, nó bắt buộc phải được gán giá trị trên mọi nhánh rẽ của tất cả các hàm khởi dựng trước khi trình biên dịch chấp nhận.

## Tại Sao Các Biến Final Ngăn Chặn Việc Gán Lại Giá Trị và Cho Phép Nhúng Mã (Inlining)

Từ khóa `final` trên một biến đảm bảo rằng một khi đã được gán giá trị, tham chiếu hoặc giá trị của biến đó không thể bị thay đổi trong suốt vòng đời còn lại của nó. Đối với các biến kiểu nguyên thủy, điều này ngăn chặn giá trị số bị thay đổi, trong khi đối với các biến kiểu tham chiếu, nó ngăn chặn tham chiếu trỏ tới một đối tượng khác (mặc dù các trường nội bộ của đối tượng đó vẫn có thể thay đổi được). Do `final` đảm bảo các giá trị hằng số tại thời điểm biên dịch khi được khởi tạo bằng hằng số, trình biên dịch Java và trình biên dịch JIT (Just-In-Time) có thể thực hiện một tối ưu hóa gọi là **nhúng mã (inlining)**. Cơ chế nhúng mã thay thế trực tiếp tên biến hoặc lời gọi phương thức bằng giá trị hằng số hoặc nội dung phương thức tại thời điểm biên dịch, loại bỏ chi phí tìm kiếm biến hoặc điều phối phương thức và tăng cường hiệu năng lúc chạy chương trình.

### Mô Hình Tối Ưu Hóa Nhúng Mã Của Trình Biên Dịch

```mermaid
graph LR
    subgraph Trước_Tối_Ưu_Hóa [Trước Khi Tối Ưu Hóa]
        Code1["final int LIMIT = 100;<br/>if (x > LIMIT) { ... }"]
    end
    subgraph Sau_Tối_Ưu_Hóa ["Sau Khi Tối Ưu Hóa (Nhúng Mã)"]
        Code2["if (x > 100) { ... }"]
    end
    Trước_Tối_Ưu_Hóa -- "Trình biên dịch thay thế LIMIT bằng 100" --> Sau_Tối_Ưu_Hóa
```

### Ví Dụ Mã Nguồn: Minh Họa Biến Final và Nhúng Mã (Inlining)
```java
public class OptimizationDemo {
    // Hằng số thời điểm biên dịch: final + kiểu nguyên thủy/String + biểu thức hằng số
    public static final int MAX_USERS = 500;

    public void displayLimit() {
        // Trình biên dịch thay thế MAX_USERS bằng giá trị trực tiếp 500 trong mã bytecode
        System.out.println("Giới hạn: " + MAX_USERS); 
    }

    public static void main(String[] args) {
        OptimizationDemo demo = new OptimizationDemo();
        demo.displayLimit(); // Đầu ra: Giới hạn: 500
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả của Biến Final (Cause-Effect Chain)
- **Tác nhân kích hoạt**: Biến được khai báo với từ khóa `final`.
- **Hiệu ứng tức thì**: Trình biên dịch ngăn chặn bất kỳ hành vi gán lại giá trị nào sau lần khởi tạo đầu tiên của biến.
- **Hiệu ứng thứ cấp**: Nếu giá trị là một hằng số tại thời điểm biên dịch, trình biên dịch có thể thay thế trực tiếp giá trị hằng số đó vào tất cả các vị trí biến được tham chiếu.
- **Kết quả cuối cùng**: Việc gán lại giá trị cho biến bị chặn từ thời điểm biên dịch, và hiệu năng runtime được nâng cao thông qua cơ chế nhúng mã của trình biên dịch/JIT.

> Xem thêm: Khái niệm Effectively Final khi sử dụng trong Lambda Expression, được trình bày chi tiết trong [Ch.21 - Lambda Expression](../../no21_lambda_expression/README.md).

### Phương thức final (Final method)

Phương thức không thể bị ghi đè (override) bởi các lớp con, đảm bảo hành vi nguyên bản của phương thức được giữ nguyên trong toàn bộ cây kế thừa.

#### Sự dư thừa `private final`
Mọi phương thức `private` đều không thể bị lớp con nhìn thấy, do đó hiển nhiên không thể bị ghi đè. Việc khai báo một phương thức là `private final` là hoàn toàn hợp lệ về mặt cú pháp nhưng thừa thãi. Trình biên dịch ngầm coi mọi phương thức `private` đều là `final`.

#### Ví Dụ Mã Nguồn Phương Thức Final
```java
public class Parent {
    public final void showMessage() {
        System.out.println("Đây là một phương thức final.");
    }
}

class Child extends Parent {
    // Việc cố gắng ghi đè phương thức final sẽ gây ra lỗi biên dịch:
    // public void showMessage() { ... }
}
```

#### Lỗi Thường Gặp - Cố gắng ghi đè (override) một phương thức final trong lớp con
Nếu một lớp con cố gắng khai báo một phương thức có cùng chữ ký và kiểu trả về với một phương thức `final` trong lớp cha, trình biên dịch sẽ báo lỗi từ chối. Lưu ý rằng các phương thức `private` mặc nhiên là final một cách ngầm định, do đó việc khai báo chúng là final là hợp lệ nhưng dư thừa.

### Lớp final (Final class)

Lớp không thể bị kế thừa (extend) bởi bất kỳ lớp nào khác, thường được sử dụng cho các lớp cốt lõi để đảm bảo tính bảo mật và ngăn chặn việc thay đổi hành vi chuẩn.

#### Tại sao `String` và `Integer` là lớp final?
Các lớp cốt lõi trong Java như `String`, `Integer`, `Double` đều được thiết kế là lớp `final`. Quyết định này nhằm:
1. **Bảo mật (Security)**: Ngăn chặn hacker tạo một lớp con mạo danh `String` với hành vi độc hại để vượt qua các khâu kiểm tra an ninh hệ thống.
2. **Bất biến (Immutability)**: Đảm bảo trạng thái của chuỗi không bao giờ bị thay đổi sau khi tạo, giúp chia sẻ chuỗi an toàn trong môi trường đa luồng và String Pool.

#### Ví Dụ Mã Nguồn Lớp Final
```java
public final class UtilityClass {
    public static void printLog(String message) {
        System.out.println("LOG: " + message);
    }
}

// Việc cố gắng kế thừa UtilityClass sẽ gây ra lỗi biên dịch:
// class SubUtility extends UtilityClass { }
```

#### Lỗi Thường Gặp - Giả định các trường trong một lớp final sẽ tự động là final
Khai báo một lớp là `final` chỉ giúp ngăn chặn việc lớp đó bị mở rộng (kế thừa). Nó KHÔNG tự động biến các trường của lớp đó thành `final` hoặc bất biến (immutable). Nếu bạn muốn các trường là bất biến, bạn vẫn phải khai báo chúng là `final` một cách tường minh.

### Tham số final (Final parameter)

Tham số phương thức không thể bị gán lại giá trị mới bên trong thân phương thức, giúp ngăn chặn lỗi vô tình thay đổi đầu vào của hàm.

#### Ví Dụ Mã Nguồn Tham Số Final
```java
public class Logger {
    public void log(final String message) {
        // message = "Thông điệp mới"; // LỖI BIÊN DỊCH! Không thể sửa đổi tham số final
        System.out.println(message);
    }
}
```

#### Lỗi Thường Gặp - Gán lại giá trị cho tham số của phương thức bên trong thân phương thức
Khai báo tham số phương thức là `final` là một thực hành lập trình tốt để ngăn chặn việc vô tình gán lại giá trị cho tham số đó bên trong thân phương thức. Việc cố gắng gán một giá trị mới cho một tham số final sẽ dẫn đến lỗi tại thời điểm biên dịch.

### Biến final trống (Blank final variable)

Biến `final` chưa được gán giá trị khi khai báo, nhưng bắt buộc phải được gán giá trị chính xác một lần (trong constructor đối với biến thể hiện, hoặc khối tĩnh đối với biến tĩnh).

#### Ví Dụ Mã Nguồn Biến Final Trống
```java
public class Order {
    private final long orderId; // Biến final trống
    private static final String DEFAULT_STATUS; // Biến tĩnh final trống

    static {
        DEFAULT_STATUS = "PENDING"; // Khởi tạo trong khối tĩnh
    }

    public Order(long orderId) {
        this.orderId = orderId; // Khởi tạo trong hàm khởi dựng
    }
}
```

#### Lỗi Thường Gặp - Thất bại trong Phân Tích Gán Giá Trị Xác Định (Definite Assignment Analysis)
Một trường final trống phải được gán giá trị chính xác một lần duy nhất. Nếu một hàm khởi dựng chứa một nhánh rẽ điều kiện (ví dụ: câu lệnh `if-else`) mà trường final trống chỉ được gán giá trị trong một nhánh, trình biên dịch sẽ báo lỗi "biến orderId có thể chưa được khởi tạo".

## Các Câu Hỏi Ôn Tập Phổ Biến

- Khái niệm nào ở đây là quy tắc tại thời điểm biên dịch (compile-time)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy (runtime)?
- Khái niệm nào ở đây có khả năng là bẫy phỏng vấn?
