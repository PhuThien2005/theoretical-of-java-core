# Xử lý ngoại lệ (Exception Handling) - Phần 2

## Mục tiêu học tập (Learning Goal)

Tập tin này bao gồm một phần trọng tâm về **Xử lý ngoại lệ (Exception Handling)**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, không phải là từ vựng riêng lẻ.

## Khái quát nội dung (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `finally` | Một khối mã luôn thực thi khi khối try thoát ra, đảm bảo việc dọn dẹp tài nguyên xảy ra. |
| `throw` | Được sử dụng để ném một ngoại lệ một cách rõ ràng từ một phương thức hoặc khối mã. |
| `throws` | Khai báo trong chữ ký phương thức những ngoại lệ mà phương thức đó có thể truyền đi tiếp. |
| `try-with-resources` | Tự động đóng các tài nguyên triển khai AutoCloseable khi thoát khỏi khối mã. |
| `Custom exception` | Một lớp ngoại lệ do người dùng định nghĩa, kế thừa từ Exception hoặc RuntimeException. |
| `Exception propagation` | Quá trình trong đó các ngoại lệ không được xử lý sẽ được chuyển ngược lên ngăn xếp cuộc gọi đến các bên gọi. |
| `Common exceptions:` | Các ngoại lệ tích hợp tiêu chuẩn như NPE, ClassCastException, v.v. |
| `NullPointerException` | Ngoại lệ được ném ra khi giải tham chiếu (dereference) một tham chiếu đối tượng null. |

## Ghi chú chi tiết (Detailed Notes)

### finally

Một khối `finally` luôn thực thi khi khối `try` thoát ra. Điều này đảm bảo rằng khối `finally` được thực thi ngay cả khi xảy ra một ngoại lệ không mong muốn, hoặc khi gặp một câu lệnh chuyển hướng điều khiển như `return`, `break`, hoặc `continue`.

Kiểm tra thực tế (Practical check):

- Định nghĩa `finally` trong một câu.
- Nhận diện `finally` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `finally`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Sử dụng `finally` để giải phóng tài nguyên hệ thống (như đóng tệp, kết nối cơ sở dữ liệu).

#### Ví dụ mã nguồn chạy được: Thứ tự thực thi try-catch-finally (Runnable Code Example: try-catch-finally Execution Order)
```java
public class FinallyOrderDemo {
    public static void main(String[] args) {
        try {
            System.out.println("1. Inside try");
            int x = 10 / 0;
            System.out.println("2. This won't print");
        } catch (ArithmeticException e) {
            System.out.println("3. Inside catch");
        } finally {
            System.out.println("4. Inside finally");
        }
        System.out.println("5. Outside try-catch-finally");
    }
}
```

#### Case Study: "khối finally vẫn chạy ngay cả khi có return trong try"
Một câu hỏi phỏng vấn rất phổ biến là khi câu lệnh `return`, `break`, hoặc `continue` được thực thi bên trong khối `try` hoặc `catch`. JVM đảm bảo rằng khối `finally` sẽ thực thi *trước khi* điều khiển được chuyển ngược lại cho bên gọi.
```java
public class FinallyReturnDemo {
    public static int getValue() {
        try {
            System.out.println("Inside try");
            return 42; // Returns 42, but finally executes first!
        } finally {
            System.out.println("Inside finally");
        }
    }

    public static int getTrickyValue() {
        try {
            return 10;
        } finally {
            return 20; // WARNING: This overrides the return value from the try block!
        }
    }

    public static void main(String[] args) {
        System.out.println("Returned value: " + getValue()); // Prints 42
        System.out.println("Tricky returned value: " + getTrickyValue()); // Prints 20!
    }
}
```
*Lưu ý: Việc trả về một giá trị hoặc ném một ngoại lệ từ một khối `finally` sẽ ghi đè lên bất kỳ câu lệnh return hoặc ngoại lệ nào trước đó trong khối `try`/`catch`. Đây được coi là một anti-pattern vì nó nuốt các ngoại lệ và các giá trị trả về.*

#### Khi nào `finally` KHÔNG chạy?
1. Nếu JVM thoát trong quá trình thực thi try/catch thông qua `System.exit(0)`.
2. Nếu luồng chạy mã bị tắt (kill) hoặc bị gián đoạn.
3. Trong trường hợp hệ thống bị sập (crash), mất điện, hoặc hệ điều hành tắt đột ngột.

### throw

Từ khóa `throw` được sử dụng để ném một ngoại lệ một cách rõ ràng từ bất kỳ phương thức hoặc khối mã nào. Bạn có thể ném một ngoại lệ kiểm tra hoặc không kiểm tra.

Kiểm tra thực tế (Practical check):

- Định nghĩa `throw` trong một câu.
- Nhận diện `throw` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `throw`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Khi đọc mã nguồn, hãy hỏi: `throw` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Ví dụ mã nguồn chạy được: Ném lại ngoại lệ (Runnable Code Example: Throwing and Re-throwing Exceptions)
```java
public class RethrowDemo {
    public static void process() throws Exception {
        try {
            // Throwing an exception
            throw new IllegalArgumentException("Invalid input data");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught locally, now re-throwing...");
            throw e; // Re-throwing the caught exception
        }
    }

    public static void main(String[] args) {
        try {
            process();
        } catch (Exception e) {
            System.out.println("Caught in main: " + e.getMessage());
        }
    }
}
```

### throws

Từ khóa `throws` được sử dụng trong chữ ký phương thức để khai báo rằng phương thức này có thể ném ra một hoặc nhiều ngoại lệ được chỉ định. Các bên gọi phương thức này phải xử lý các ngoại lệ này hoặc khai báo chúng trong chữ ký của chính họ.

Kiểm tra thực tế (Practical check):

- Định nghĩa `throws` trong một câu.
- Nhận diện `throws` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `throws`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Khi đọc mã nguồn, hãy hỏi: `throws` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Ví dụ mã nguồn chạy được: Khai báo và truyền ngoại lệ (Runnable Code Example: Declaring and Propagating Exceptions)
```java
import java.io.IOException;

public class ThrowsDemo {
    // Declarative throws clause for checked exception
    public static void riskyMethod() throws IOException {
        throw new IOException("Connection failed");
    }

    public static void caller() throws IOException {
        riskyMethod(); // Propagates the exception upward
    }

    public static void main(String[] args) {
        try {
            caller();
        } catch (IOException e) {
            System.out.println("Caught propagated exception: " + e.getMessage());
        }
    }
}
```

### try-with-resources

Java 7 giới thiệu `try-with-resources` để đảm bảo rằng mỗi tài nguyên được đóng ở cuối câu lệnh. Bất kỳ đối tượng nào triển khai giao diện `java.lang.AutoCloseable` đều có thể được sử dụng làm tài nguyên.

Kiểm tra thực tế (Practical check):

- Định nghĩa `try-with-resources` trong một câu.
- Nhận diện `try-with-resources` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `try-with-resources`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- `try (BufferedReader br = new BufferedReader(...)) { ... }` tự động đóng tài nguyên.

#### Ví dụ mã nguồn chạy được: Try-with-Resources và Thứ tự đóng (Runnable Code Example: Try-with-Resources and Close Order)
```java
public class TryWithResourcesDemo {
    static class Resource implements AutoCloseable {
        private final String name;
        Resource(String name) {
            this.name = name;
            System.out.println("Opened " + name);
        }
        @Override
        public void close() {
            System.out.println("Closed " + name);
        }
    }

    public static void main(String[] args) {
        // Resources are closed in reverse order of their declaration: R2 then R1
        try (Resource r1 = new Resource("R1");
             Resource r2 = new Resource("R2")) {
            System.out.println("Inside try-with-resources block");
        }
    }
}
```

### Ngoại lệ tùy chỉnh (Custom exception)

Các ngoại lệ tùy chỉnh là các ngoại lệ do người dùng định nghĩa được tạo ra bằng cách kế thừa `Exception` (đối với ngoại lệ kiểm tra - checked) hoặc `RuntimeException` (đối với ngoại lệ không kiểm tra - unchecked).

Kiểm tra thực tế (Practical check):

- Định nghĩa `Custom exception` trong một câu.
- Nhận diện `Custom exception` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `Custom exception`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Kế thừa `RuntimeException` cho các ngoại lệ nghiệp vụ tùy chỉnh (custom business rule) không cần kiểm tra.

#### Ví dụ mã nguồn chạy được: Tạo ngoại lệ tùy chỉnh và chuỗi ngoại lệ (Runnable Code Example: Creating Custom Exceptions and Exception Chaining)
```java
// Checked Custom Exception
class DatabaseConnectionException extends Exception {
    public DatabaseConnectionException(String message) {
        super(message);
    }
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause); // Exception chaining constructor
    }
}

// Unchecked Custom Exception
class InvalidUserRoleException extends RuntimeException {
    public InvalidUserRoleException(String message) {
        super(message);
    }
}

public class CustomExceptionDemo {
    public static void connectDatabase() throws DatabaseConnectionException {
        try {
            // Simulate low-level exception
            throw new java.sql.SQLException("Connection timeout");
        } catch (java.sql.SQLException e) {
            // Exception chaining: wrap the low-level exception in a high-level one
            throw new DatabaseConnectionException("Failed to initialize database", e);
        }
    }

    public static void main(String[] args) {
        try {
            connectDatabase();
        } catch (DatabaseConnectionException e) {
            System.out.println("Caught: " + e.getMessage());
            System.out.println("Underlying cause: " + e.getCause());
        }
    }
}
```

### Truyền ngoại lệ (Exception propagation)

Nếu một ngoại lệ không được bắt trong phương thức hiện tại, nó sẽ bị bật ra khỏi ngăn xếp cuộc gọi và truyền sang phương thức gọi (caller method). Quá trình này tiếp tục cho đến khi ngoại lệ được bắt, hoặc nó chạm tới phương thức `main` (làm dừng luồng thực thi).

Kiểm tra thực tế (Practical check):

- Định nghĩa `Exception propagation` trong một câu.
- Nhận diện `Exception propagation` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `Exception propagation`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Thứ tự tìm kiếm của ngăn xếp cuộc gọi: level3 -> level2 -> level1 -> main.

#### Ví dụ mã nguồn chạy được: Truyền ngoại lệ qua ngăn xếp cuộc gọi (Runnable Code Example: Call Stack Exception Propagation)
```java
public class ExceptionPropagationDemo {
    public static void level3() {
        int x = 10 / 0; // Throws unchecked ArithmeticException
    }

    public static void level2() {
        level3(); // Propagation
    }

    public static void level1() {
        try {
            level2();
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException at level 1: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        level1();
    }
}
```

### Các ngoại lệ phổ biến (Common exceptions:)

Nền tảng Java cung cấp vô số các lớp ngoại lệ được định nghĩa trước đại diện cho các điều kiện thời gian chạy (runtime) và điều kiện kiểm tra (checked) tiêu chuẩn.

Kiểm tra thực tế (Practical check):

- Định nghĩa `Common exceptions:` trong một câu.
- Nhận diện `Common exceptions:` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `Common exceptions:`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Unchecked: NullPointerException, ArithmeticException. Checked: IOException, FileNotFoundException.

### NullPointerException

Được ném ra khi một ứng dụng cố gắng sử dụng hằng số `null` trong trường hợp cần một tham chiếu đối tượng.

Kiểm tra thực tế (Practical check):

- Định nghĩa `NullPointerException` trong một câu.
- Nhận diện `NullPointerException` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `NullPointerException`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Kích hoạt khi gọi một phương thức trên một biến đang trỏ tới `null`.

#### Ví dụ mã nguồn chạy được: Kích hoạt và phòng tránh NullPointerException (Runnable Code Example: Triggering and Preventing NullPointerException)
```java
public class NPEDemo {
    public static void main(String[] args) {
        String name = null;
        try {
            // Triggering NPE
            int len = name.length();
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException!");
        }

        // Prevention (Null check)
        if (name != null) {
            int len = name.length();
        } else {
            System.out.println("Name is null, null-check prevented NPE");
        }
    }
}
```

### Case Study: Anti-Pattern Nuốt ngoại lệ (Exception Swallowing Anti-Pattern)
Nuốt (hoặc che giấu) ngoại lệ xảy ra khi một khối mã bắt một ngoại lệ nhưng không xử lý, không ghi log, và cũng không truyền nó đi, khiến việc gỡ lỗi trở nên cực kỳ khó khăn. Nó cũng xảy ra khi một ngoại lệ được ném ra hoặc một câu lệnh return được thực thi trong một khối `finally`, điều này sẽ che giấu hoàn toàn bất kỳ ngoại lệ nào được ném ra trong khối `try` tương ứng.

#### Nuốt ngoại lệ qua khối Catch trống (Swallowing via Empty Catch Block)
```java
try {
    int data = Integer.parseInt("not_a_number");
} catch (NumberFormatException e) {
    // EMPTY: Exception is swallowed and lost forever!
}
```

#### Nuốt ngoại lệ qua ngoại lệ/lệnh return của khối Finally (Swallowing via Finally Block Exception / Return)
```java
public class SwallowingDemo {
    public static String readData() {
        try {
            throw new RuntimeException("Error in try block");
        } finally {
            // This return statement silently swallows the RuntimeException!
            return "Success"; 
        }
    }

    public static void main(String[] args) {
        // Will print "Success" and the exception will be completely lost!
        System.out.println(readData()); 
    }
}
```
*Best Practice (Thực hành tốt nhất):* Luôn ghi log (ghi nhật ký) các ngoại lệ hoặc bọc chúng trong các ngoại lệ tùy chỉnh khi bắt, và không bao giờ return hoặc ném ngoại lệ từ một khối `finally` trừ khi bạn cố tình muốn loại bỏ ngoại lệ ban đầu.

## Các lỗi thường gặp (Common Mistakes)

### 1. Sửa đổi giá trị trả về trong khối Finally (Tham chiếu so với Kiểu nguyên thủy) (Modifying Return Value in Finally Block (Reference vs. Primitive))
Nếu bạn trả về một kiểu nguyên thủy (primitive type) từ `try`, việc sửa đổi nó trong `finally` mà không có câu lệnh return sẽ không làm thay đổi giá trị trả về. Tuy nhiên, việc sửa đổi trạng thái của một đối tượng có thể thay đổi (mutable object) *sẽ* ảnh hưởng đến bên gọi vì tham chiếu vẫn trỏ đến cùng một đối tượng trong bộ nhớ heap.
```java
public class FinallyModifyDemo {
    public static int getPrimitive() {
        int x = 10;
        try {
            return x; // Returns 10
        } finally {
            x = 20; // Modifies local copy, return value is already cached as 10
        }
    }

    static class Box { int val = 10; }
    public static Box getObject() {
        Box b = new Box();
        try {
            return b; // Returns reference to b
        } finally {
            b.val = 20; // Modifies state of object b, caller sees val = 20!
        }
    }

    public static void main(String[] args) {
        System.out.println("Primitive: " + getPrimitive()); // Prints 10
        System.out.println("Object field: " + getObject().val); // Prints 20
    }
}
```

### 2. Không khai báo tài nguyên trong try-with-resources một cách chính xác (Not Declaring Resources in try-with-resources Correctly)
Một sai lầm phổ biến là khai báo các biến tài nguyên bên ngoài cặp ngoặc đơn của try-with-resources. Làm như vậy sẽ không đăng ký chúng cho việc đóng tự động.
```java
// INCORRECT: resource is not automatically closed
BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
try (reader) { // Valid in Java 9+, but reader must be effectively final
    // ...
}
```

### 3. Quên triển khai AutoCloseable (Forgetting to Implement AutoCloseable)
Chỉ các lớp triển khai `java.lang.AutoCloseable` mới có thể được sử dụng làm tài nguyên trong try-with-resources. Cố gắng sử dụng bất kỳ lớp nào khác sẽ dẫn đến lỗi biên dịch.
```java
// COMPILE ERROR: incompatible types: String cannot be converted to AutoCloseable
try (String s = "Hello") { // Compile error!
    System.out.println(s);
}
```

## Các câu hỏi ôn tập thường gặp (Common Review Prompts)

- Khái niệm nào ở đây là quy tắc thời gian biên dịch (compile-time rule)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi thời gian chạy (runtime behavior)?
- Khái niệm nào ở đây dễ là bẫy phỏng vấn?

---

## Tại sao Finally thực thi ngay cả khi Try trả về kết quả sớm (Why Finally Executes Even When Try Returns Early)

Sự đảm bảo của khối `finally` được JVM thực thi ở cấp độ bytecode, chứ không chỉ là một quy ước ngôn ngữ. Khi trình biên dịch biên dịch một cấu trúc `try-finally`, nó sẽ tạo ra bytecode chèn các hướng dẫn của khối `finally` vào mọi đường thoát có thể xảy ra từ khối `try`: hoàn thành bình thường, ném ngoại lệ, thực thi `return`, thực thi `break`, và thực thi `continue`.

Lý do cho sự đảm bảo này là để quản lý tài nguyên. Các tài nguyên như trình xử lý tệp, kết nối cơ sở dữ liệu và socket mạng phải được giải phóng bất kể mã nguồn bên trong khối `try` thành công, thất bại hay trả về sớm. Không có sự đảm bảo của `finally`, một đường thoát ngoại lệ không mong muốn duy nhất có thể bỏ qua việc dọn dẹp tài nguyên, gây ra rò rỉ tài nguyên (resource leak) tích tụ theo thời gian và cuối cùng làm sập ứng dụng.

Khi `return` được tiếp cận bên trong khối `try`, JVM lưu giá trị trả về trong một thanh ghi, thực thi khối `finally`, sau đó tiếp tục trả về giá trị đã lưu. Nếu bản thân khối `finally` chứa một câu lệnh `return`, nó sẽ ghi đè lên giá trị trả về đã lưu — đây là một anti-pattern vì nó âm thầm loại bỏ giá trị trả về ban đầu và nuốt chửng mọi ngoại lệ từ khối `try`.

### Mô hình tư duy: Chèn code finally ở cấp độ bytecode của JVM (Mental Model: JVM bytecode finally insertion)
```
try {
    // code A
    return 42;            ← JVM lưu 42 vào thanh ghi tạm thời
    // code B (never reached after return)
} finally {
    // cleanup C          ← JVM chèn cleanup C tại MỌI đường thoát
}
// JVM tiếp tục: trả về 42 đã lưu sau khi C hoàn thành

// JVM chèn finally tại MỌI lối thoát khỏi try:
//   thoát bình thường   → thực thi finally → tiếp tục
//   giá trị trả về      → lưu giá trị → thực thi finally → trả về giá trị đã lưu
//   ném ngoại lệ        → thực thi finally → ném lại ngoại lệ
//   System.exit()       → finally KHÔNG được đảm bảo chạy (JVM kết thúc)
```

### Ví dụ mã nguồn: finally với return và với exception (finally with return and with exception)
```java
public class FinallyGuarantee {
    public static int getSavedValue() {
        try {
            System.out.println("In try");
            return 42; // ← return value saved, finally runs before method exits
        } finally {
            System.out.println("In finally"); // always runs
            // Output: "In try" → "In finally" → method returns 42
        }
    }

    public static int getOverriddenValue() {
        try {
            return 10; // ← saved, but...
        } finally {
            return 20; // ← ANTI-PATTERN: overrides saved 10, also swallows exceptions
        }
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
`return 42` trong `try`
  → JVM lưu giá trị trả về 42
  → Thực thi khối `finally`
  → `finally` hoàn thành bình thường
  → JVM trả về giá trị 42 đã lưu. Ngoại lệ được ném ra trong `try`
  → JVM bắt đầu thu hồi ngăn xếp (stack unwinding)
  → Thực thi khối `finally` trước khi rời khỏi khung ngăn xếp
  → `finally` hoàn thành
  → Ngoại lệ được ném lại cho bên gọi. `System.exit(0)` trong `try`
  → JVM kết thúc ngay lập tức
  → `finally` KHÔNG được thực thi — cơ chế shutdown hook của JVM, chứ không phải `finally`, là phương án dọn dẹp cuối cùng.
```


---

## Tại sao Try-With-Resources thay thế Finally thủ công cho việc dọn dẹp tài nguyên (Why Try-With-Resources Replaces Manual Finally for Resource Cleanup)

Trước Java 7, việc dọn dẹp tài nguyên bằng `finally` có một lỗi tinh vi: nếu khối `try` ném ra một ngoại lệ VÀ lệnh gọi `close()` của khối `finally` cũng ném ra một ngoại lệ, ngoại lệ đầu tiên sẽ bị loại bỏ một cách âm thầm — bị ghi đè bởi ngoại lệ của `close()`. Điều này khiến việc gỡ lỗi trở nên cực kỳ khó khăn vì nguyên nhân lỗi ban đầu đã bị mất.

`try-with-resources` giải quyết vấn đề này thông qua khái niệm **áp chế ngoại lệ (exception suppression)**. Nếu cả khối `try` và cuộc gọi `close()` đều ném ra ngoại lệ, ngoại lệ của `close()` sẽ được đính kèm vào ngoại lệ ban đầu dưới dạng một "ngoại lệ bị áp chế" (suppressed exception) thay vì thay thế nó. Bạn có thể lấy các ngoại lệ bị áp chế bằng `e.getSuppressed()`. Nguyên nhân gây lỗi ban đầu luôn được bảo toàn.

Ngoài ra, `try-with-resources` tạo ra mã đóng tài nguyên được trình biên dịch đảm bảo. Các tài nguyên được đóng theo thứ tự ngược lại với khai báo (khai báo sau cùng, đóng trước tiên), và tất cả các tài nguyên đều được đảm bảo sẽ đóng ngay cả khi các cuộc gọi close trước đó ném ra ngoại lệ. Việc sử dụng `finally` thủ công với nhiều tài nguyên đòi hỏi các khối try-finally lồng nhau để đạt được sự đảm bảo tương tự — mã nguồn như vậy rất dài dòng, dễ xảy ra lỗi và thường xuyên bị viết sai.

### Mô hình tư duy: So sánh sự đảm bảo đóng tài nguyên của finally thủ công và try-with-resources (Mental Model: Manual finally vs try-with-resources closing guarantee)
```
[Finally thủ công — ngoại lệ ban đầu có thể bị mất]
InputStream in = new FileInputStream("a");
OutputStream out = new FileOutputStream("b");
try {
    // ... work ...
    // nếu work ném ra IOException  ← được lưu làm ngoại lệ chính
} finally {
    in.close();   // nếu in.close() ném ngoại lệ → GHI ĐÈ ngoại lệ chính — mất lỗi ban đầu!
    out.close();  // nếu in.close() ném ngoại lệ, out.close() sẽ không bao giờ được chạy!
}

[try-with-resources — ngoại lệ ban đầu được bảo toàn, tất cả tài nguyên được đóng]
try (InputStream in = new FileInputStream("a");
     OutputStream out = new FileOutputStream("b")) {
    // ... work ...
    // nếu work ném ra IOException ← ngoại lệ chính được giữ lại
    // Mã close do trình biên dịch tự tạo:
    //   out.close() được gọi trước (thứ tự ngược)
    //   in.close() được gọi sau
    //   nếu close ném ngoại lệ → được thêm làm ngoại lệ BỊ ÁP CHẾ (SUPPRESSED)
    //   IOException chính KHÔNG bị thay thế
}
```

### Ví dụ mã nguồn: try-with-resources và các ngoại lệ bị áp chế (try-with-resources and suppressed exceptions)
```java
class TrackingResource implements AutoCloseable {
    private final String name;
    TrackingResource(String name) { this.name = name; System.out.println("Opened " + name); }

    @Override
    public void close() throws Exception {
        System.out.println("Closed " + name);
        // if close throws: throw new Exception("Close failed for " + name);
    }
}

// inside main:
try (TrackingResource r1 = new TrackingResource("R1");
     TrackingResource r2 = new TrackingResource("R2")) {
    System.out.println("Working...");
}
// Output:
// Opened R1
// Opened R2
// Working...
// Closed R2   ← reversed order: R2 first
// Closed R1
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Sử dụng `finally` thủ công với `close()` trong khối `try` + `finally`
  → Cả thân `try` và `close()` đều ném ngoại lệ
  → Ngoại lệ của `close()` thay thế ngoại lệ chính
  → Nguyên nhân lỗi ban đầu bị mất
  → Cơn ác mộng gỡ lỗi. `try-with-resources`
  → Trình biên dịch tạo mã đóng tài nguyên ở tất cả các lối thoát
  → Nhiều tài nguyên được đóng theo thứ tự ngược lại so với khai báo
  → Ngoại lệ của `close()` được thêm làm ngoại lệ bị áp chế
  → Ngoại lệ ban đầu luôn được bảo toàn
  → `e.getSuppressed()` tiết lộ lỗi của close.
```


---

## Tại sao chuỗi ngoại lệ (Exception Chaining) giúp bảo toàn ngữ cảnh gỡ lỗi (Why Exception Chaining Preserves Debugging Context)

Khi các ngoại lệ cấp thấp bị ném ra bởi một thành phần phụ thuộc (như JDBC, I/O tệp hoặc một HTTP client), chúng chứa các chi tiết triển khai cụ thể (mã lỗi SQL, số lỗi hệ điều hành, tên lớp driver) không có ý nghĩa đối với bên gọi. Bên gọi có thể chỉ cần xử lý một mức trừu tượng (Abstraction) cao hơn — ví dụ `DatabaseConnectionException` thay vì `java.sql.SQLException("ORA-12170: TNS Connect Timeout")`.

Nếu không có chuỗi ngoại lệ (exception chaining), việc dịch các ngoại lệ cấp thấp sang cấp cao sẽ phá hủy thông tin nguyên nhân gốc rễ. Khi ứng dụng bị sập, dấu vết ngăn xếp (stack trace) chỉ hiển thị `DatabaseConnectionException: Failed to connect` — mà không có dấu hiệu nào cho biết điều gì thực sự đã xảy ra ở lớp cơ sở dữ liệu. Việc gỡ lỗi sẽ yêu cầu chạy lại để tái hiện sự cố, thêm nhật ký bổ sung hoặc đoán mò.

Chuỗi ngoại lệ giải quyết vấn đề này bằng cách lưu trữ ngoại lệ ban đầu dưới dạng **nguyên nhân (cause)** của ngoại lệ mới thông qua `new HighLevelException("msg", originalCause)`. JVM tự động bao gồm toàn bộ chuỗi nguyên nhân trong đầu ra dấu vết ngăn xếp. Bất kỳ trình gỡ lỗi (debugger) hoặc trình tổng hợp log nào hiểu các ngoại lệ Java tiêu chuẩn đều có thể duyệt qua chuỗi nguyên nhân bằng cách gọi liên tiếp `e.getCause()` để tìm ra nguyên nhân gốc rễ.

### Mô hình tư duy: Chuỗi nguyên nhân của Exception Chaining (Mental Model: Exception chaining cause chain)
```
Ngoại lệ lớp ứng dụng (Application Layer Exception):
    DatabaseConnectionException: "Failed to initialize connection pool"
        caused by: HikariPoolException: "Pool connection timeout after 30000ms"
            caused by: java.sql.SQLException: "ORA-12170: TNS Connect Timeout occurred"
                caused by: java.net.SocketTimeoutException: "connect timed out"

Nếu không có chuỗi ngoại lệ (tệ):
    DatabaseConnectionException: "Failed to initialize connection pool"
    ← nguyên nhân gốc rễ bị mất hoàn toàn. Nhà phát triển không có manh mối tại sao.

Nếu có chuỗi ngoại lệ (đúng):
    Tất cả 4 lớp lỗi đều hiển thị trong một lần in stack trace duy nhất.
    Nhà phát triển lập tức biết: đó là lỗi hết thời gian chờ socket mạng ở cấp độ driver Oracle.
```

### Ví dụ mã nguồn: Chuỗi ngoại lệ có nguyên nhân (Code Example: Exception chaining with cause)
```java
class DatabaseConnectionException extends Exception {
    public DatabaseConnectionException(String msg, Throwable cause) {
        super(msg, cause); // chains the cause
    }
}

public void connectToDatabase() throws DatabaseConnectionException {
    try {
        // Low-level driver call
        throw new java.sql.SQLException("ORA-12170: TNS Connect Timeout");
    } catch (java.sql.SQLException e) {
        // Translate to high-level exception, PRESERVING the root cause
        throw new DatabaseConnectionException("Failed to initialize connection pool", e);
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
`SQLException` cấp thấp được ném ra bởi JDBC driver
  → Bị bắt ở lớp dịch vụ (service layer)
  → Được bọc trong `new DatabaseConnectionException("msg", sqlException)`
  → `SQLException` ban đầu được lưu trữ dưới dạng nguyên nhân (cause)
  → `DatabaseConnectionException` truyền tới bên gọi
  → Bên gọi bắt ngoại lệ cấp cao
  → Stack trace hiển thị toàn bộ chuỗi nguyên nhân bao gồm cả `SQLException` gốc
  → `e.getCause()` trả về `SQLException` ban đầu với các chi tiết lỗi của driver. Nếu không có chuỗi ngoại lệ: `sqlException` bị loại bỏ
  → Bên gọi chỉ nhìn thấy thông báo chung chung
  → Nguyên nhân gốc rễ bị mất vĩnh viễn.
```


## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/javase/tutorial/essential/exceptions/ (Oracle Exception Tutorial)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Exception.html (Exception API)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/AutoCloseable.html (AutoCloseable API)
