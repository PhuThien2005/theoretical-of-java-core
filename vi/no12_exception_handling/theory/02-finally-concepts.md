# Xử Lý Ngoại Lệ - Phần 2

## Mục Tiêu Học Tập

File này bao gồm một phần tập trung của **Xử Lý Ngoại Lệ (Exception Handling)**. Hãy học từng khái niệm như một quy tắc Java thực tế, không phải từ vựng biệt lập.

## Phạm Vi Đề Cương

- **`finally`** — finally: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`throw`** — throw: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`throws`** — throws: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`try-with-resources`** — try-with-resources: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Custom exception`** — Custom exception: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Exception propagation`** — Exception propagation: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Common exceptions:`** — Common exceptions:: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`NullPointerException`** — NullPointerException: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

## Ghi Chú Chi Tiết

### finally (Khối dọn dẹp)

Khối `finally` luôn được thực thi khi khối `try` thoát. Điều này đảm bảo khối `finally` được thực thi ngay cả khi một ngoại lệ không mong đợi xảy ra, hoặc khi một câu lệnh chuyển điều khiển như `return`, `break`, hoặc `continue` được đến.

Kiểm tra thực tế:

- Định nghĩa `finally` trong một câu.
- Nhận diện `finally` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một bug, giới hạn, hoặc đánh đổi liên quan đến `finally`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Dùng `finally` để giải phóng tài nguyên hệ thống (như đóng file, kết nối cơ sở dữ liệu).

#### Ví Dụ Code Chạy Được: Thứ Tự Thực Thi try-catch-finally
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

#### Ví Dụ Thực Tế: "Khối finally chạy ngay cả khi return nằm trong try"
Một bẫy phỏng vấn phổ biến là khi câu lệnh `return`, `break`, hoặc `continue` được thực thi trong khối `try` hoặc `catch`. JVM đảm bảo rằng khối `finally` sẽ thực thi *trước* khi điều khiển trả về cho người gọi.
```java
public class FinallyReturnDemo {
    public static int getValue() {
        try {
            System.out.println("Inside try");
            return 42; // Trả về 42, nhưng finally thực thi trước!
        } finally {
            System.out.println("Inside finally");
        }
    }

    public static int getTrickyValue() {
        try {
            return 10;
        } finally {
            return 20; // CẢNH BÁO: Điều này ghi đè giá trị return từ khối try!
        }
    }

    public static void main(String[] args) {
        System.out.println("Returned value: " + getValue()); // In 42
        System.out.println("Tricky returned value: " + getTrickyValue()); // In 20!
    }
}
```
*Lưu ý: Trả về giá trị hoặc ném ngoại lệ từ khối `finally` ghi đè bất kỳ return hoặc ngoại lệ nào trước đó trong khối `try`/`catch`. Đây được coi là một anti-pattern vì nó nuốt ngoại lệ và giá trị return.*

#### Khi nào `finally` KHÔNG chạy?
1. Nếu JVM thoát trong quá trình thực thi try/catch thông qua `System.exit(0)`.
2. Nếu thread đang chạy code bị kill hoặc bị ngắt.
3. Trong trường hợp hệ thống crash, mất điện, hoặc hệ điều hành tắt.

### throw (Ném ngoại lệ)

Từ khóa `throw` được dùng để ném rõ ràng một ngoại lệ từ bất kỳ phương thức hoặc khối code nào. Bạn có thể ném cả checked exception lẫn unchecked exception.

Kiểm tra thực tế:

- Định nghĩa `throw` trong một câu.
- Nhận diện `throw` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một bug, giới hạn, hoặc đánh đổi liên quan đến `throw`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hỏi: `throw` thay đổi, cho phép, từ chối, hay làm rõ điều gì?

#### Ví Dụ Code Chạy Được: Ném và Ném Lại Ngoại Lệ
```java
public class RethrowDemo {
    public static void process() throws Exception {
        try {
            // Ném ngoại lệ
            throw new IllegalArgumentException("Invalid input data");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught locally, now re-throwing...");
            throw e; // Ném lại ngoại lệ đã bắt
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

### throws (Khai báo ngoại lệ)

Từ khóa `throws` được dùng trong chữ ký phương thức để khai báo rằng phương thức này có thể ném một hoặc nhiều ngoại lệ được chỉ định. Người gọi phương thức này phải xử lý những ngoại lệ này hoặc khai báo chúng trong chữ ký của chính họ.

Kiểm tra thực tế:

- Định nghĩa `throws` trong một câu.
- Nhận diện `throws` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một bug, giới hạn, hoặc đánh đổi liên quan đến `throws`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hỏi: `throws` thay đổi, cho phép, từ chối, hay làm rõ điều gì?

#### Ví Dụ Code Chạy Được: Khai Báo và Lan Truyền Ngoại Lệ
```java
import java.io.IOException;

public class ThrowsDemo {
    // Khai báo throws cho checked exception
    public static void riskyMethod() throws IOException {
        throw new IOException("Connection failed");
    }

    public static void caller() throws IOException {
        riskyMethod(); // Lan truyền ngoại lệ lên trên
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

### try-with-resources (Thử-với-tài-nguyên)

Java 7 giới thiệu try-with-resources để đảm bảo mỗi tài nguyên được đóng ở cuối câu lệnh. Bất kỳ đối tượng nào implement `java.lang.AutoCloseable` đều có thể được dùng như tài nguyên.

Kiểm tra thực tế:

- Định nghĩa `try-with-resources` trong một câu.
- Nhận diện `try-with-resources` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một bug, giới hạn, hoặc đánh đổi liên quan đến `try-with-resources`.

Ví dụ nhỏ hoặc mô hình tư duy:

- `try (BufferedReader br = new BufferedReader(...)) { ... }` xử lý việc đóng tài nguyên.

#### Ví Dụ Code Chạy Được: Try-with-Resources và Thứ Tự Đóng
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
        // Tài nguyên được đóng theo thứ tự ngược với khai báo: R2 rồi R1
        try (Resource r1 = new Resource("R1");
             Resource r2 = new Resource("R2")) {
            System.out.println("Inside try-with-resources block");
        }
    }
}
```

### Custom exception (Ngoại lệ tùy chỉnh)

Ngoại lệ tùy chỉnh (custom exception) là các ngoại lệ do người dùng định nghĩa, tạo bằng cách kế thừa `Exception` (cho checked exception) hoặc `RuntimeException` (cho unchecked exception).

Kiểm tra thực tế:

- Định nghĩa `Custom exception` trong một câu.
- Nhận diện `Custom exception` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một bug, giới hạn, hoặc đánh đổi liên quan đến `Custom exception`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Kế thừa `RuntimeException` cho các quy tắc kinh doanh tùy chỉnh dạng unchecked.

#### Ví Dụ Code Chạy Được: Tạo Ngoại Lệ Tùy Chỉnh và Chuỗi Ngoại Lệ
```java
// Checked Custom Exception
class DatabaseConnectionException extends Exception {
    public DatabaseConnectionException(String message) {
        super(message);
    }
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause); // Constructor chuỗi ngoại lệ
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
            // Mô phỏng ngoại lệ cấp thấp
            throw new java.sql.SQLException("Connection timeout");
        } catch (java.sql.SQLException e) {
            // Chuỗi ngoại lệ: wrap ngoại lệ cấp thấp trong ngoại lệ cấp cao
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

### Exception propagation (Lan truyền ngoại lệ)

Nếu một ngoại lệ không được bắt trong phương thức hiện tại, nó bị bật khỏi call stack và lan truyền đến phương thức gọi. Quá trình này tiếp tục cho đến khi ngoại lệ được bắt, hoặc nó đến phương thức `main` (kết thúc thread).

Kiểm tra thực tế:

- Định nghĩa `Exception propagation` trong một câu.
- Nhận diện `Exception propagation` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một bug, giới hạn, hoặc đánh đổi liên quan đến `Exception propagation`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Thứ tự tìm kiếm call stack: level3 -> level2 -> level1 -> main.

#### Ví Dụ Code Chạy Được: Lan Truyền Ngoại Lệ Qua Call Stack
```java
public class ExceptionPropagationDemo {
    public static void level3() {
        int x = 10 / 0; // Ném unchecked ArithmeticException
    }

    public static void level2() {
        level3(); // Lan truyền
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

### Common exceptions (Ngoại lệ phổ biến)

Nền tảng Java cung cấp nhiều lớp ngoại lệ được định nghĩa sẵn đại diện cho các điều kiện runtime và checked tiêu chuẩn.

Kiểm tra thực tế:

- Định nghĩa `Common exceptions` trong một câu.
- Nhận diện `Common exceptions` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một bug, giới hạn, hoặc đánh đổi liên quan đến `Common exceptions`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Unchecked: NullPointerException, ArithmeticException. Checked: IOException, FileNotFoundException.

### NullPointerException

Được ném khi ứng dụng cố gắng dùng `null` trong trường hợp cần tham chiếu đối tượng.

Kiểm tra thực tế:

- Định nghĩa `NullPointerException` trong một câu.
- Nhận diện `NullPointerException` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một bug, giới hạn, hoặc đánh đổi liên quan đến `NullPointerException`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Được kích hoạt khi gọi phương thức trên biến trỏ đến `null`.

#### Ví Dụ Code Chạy Được: Kích Hoạt và Ngăn NullPointerException
```java
public class NPEDemo {
    public static void main(String[] args) {
        String name = null;
        try {
            // Kích hoạt NPE
            int len = name.length();
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException!");
        }

        // Ngăn chặn (Kiểm tra Null)
        if (name != null) {
            int len = name.length();
        } else {
            System.out.println("Name is null, null-check prevented NPE");
        }
    }
}
```

### Ví Dụ Thực Tế: Anti-Pattern Nuốt Ngoại Lệ (Exception Swallowing)
Nuốt ngoại lệ (hay che khuất ngoại lệ) xảy ra khi một khối code bắt ngoại lệ nhưng không xử lý, ghi log, hoặc lan truyền nó, khiến việc gỡ lỗi cực kỳ khó khăn. Nó cũng xảy ra khi một ngoại lệ được ném hoặc một return được thực thi trong khối `finally`, hoàn toàn che khuất (nuốt) bất kỳ ngoại lệ nào được ném trong khối `try` tương ứng.

#### Nuốt qua Khối Catch Rỗng
```java
try {
    int data = Integer.parseInt("not_a_number");
} catch (NumberFormatException e) {
    // RỖNG: Ngoại lệ bị nuốt và mất mãi mãi!
}
```

#### Nuốt qua Exception / Return trong Khối Finally
```java
public class SwallowingDemo {
    public static String readData() {
        try {
            throw new RuntimeException("Error in try block");
        } finally {
            // Câu lệnh return này im lặng nuốt RuntimeException!
            return "Success"; 
        }
    }

    public static void main(String[] args) {
        // Sẽ in "Success" và ngoại lệ sẽ bị mất hoàn toàn!
        System.out.println(readData()); 
    }
}
```
*Thực tiễn tốt nhất:* Luôn ghi log ngoại lệ hoặc wrap chúng trong ngoại lệ tùy chỉnh khi bắt, và không bao giờ return hoặc ném ngoại lệ từ khối `finally` trừ khi bạn có chủ đích loại bỏ ngoại lệ gốc.

## Lỗi Thường Gặp

### 1. Sửa Đổi Giá Trị Return trong Khối Finally (Reference vs. Primitive)
Nếu bạn return kiểu primitive từ `try`, sửa đổi nó trong `finally` mà không có câu lệnh return sẽ không thay đổi giá trị được return. Tuy nhiên, sửa đổi trạng thái của đối tượng mutable *sẽ* ảnh hưởng đến người gọi vì tham chiếu trỏ đến cùng một đối tượng trong bộ nhớ heap.
```java
public class FinallyModifyDemo {
    public static int getPrimitive() {
        int x = 10;
        try {
            return x; // Trả về 10
        } finally {
            x = 20; // Sửa đổi bản sao cục bộ, giá trị return đã được cache là 10
        }
    }

    static class Box { int val = 10; }
    public static Box getObject() {
        Box b = new Box();
        try {
            return b; // Trả về tham chiếu đến b
        } finally {
            b.val = 20; // Sửa đổi trạng thái của đối tượng b, người gọi thấy val = 20!
        }
    }

    public static void main(String[] args) {
        System.out.println("Primitive: " + getPrimitive()); // In 10
        System.out.println("Object field: " + getObject().val); // In 20
    }
}
```

### 2. Không Khai Báo Tài Nguyên Trong try-with-resources Đúng Cách
Một lỗi phổ biến là khai báo biến tài nguyên bên ngoài dấu ngoặc đơn của try-with-resources. Làm như vậy không đăng ký chúng để tự động đóng.
```java
// SAI: tài nguyên không được tự động đóng
BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
try (reader) { // Hợp lệ trong Java 9+, nhưng reader phải là effectively final
    // ...
}
```

### 3. Quên Implement AutoCloseable
Chỉ các lớp implement `java.lang.AutoCloseable` mới có thể được dùng như tài nguyên trong try-with-resources. Cố dùng bất kỳ lớp nào khác sẽ dẫn đến lỗi biên dịch.
```java
// COMPILE ERROR: incompatible types: String cannot be converted to AutoCloseable
try (String s = "Hello") { // Compile error!
    System.out.println(s);
}
```

## Câu Hỏi Ôn Tập Thường Gặp

- Khái niệm nào ở đây là quy tắc biên dịch?
- Khái niệm nào ảnh hưởng đến hành vi runtime?
- Khái niệm nào có thể là bẫy phỏng vấn?

---

## Tại Sao Finally Chạy Ngay Cả Khi Try Trả Về Sớm

Sự đảm bảo của khối `finally` được bắt buộc bởi JVM ở cấp độ bytecode, không chỉ là quy ước ngôn ngữ. Khi trình biên dịch biên dịch cấu trúc `try-finally`, nó tạo ra bytecode chèn các lệnh của khối `finally` vào mọi đường thoát có thể từ khối `try`: hoàn thành bình thường, ngoại lệ được ném, `return` được thực thi, `break` được thực thi, và `continue` được thực thi.

Lý do cho sự đảm bảo này là quản lý tài nguyên. Tài nguyên như file handle, kết nối cơ sở dữ liệu, và socket mạng phải được giải phóng bất kể code bên trong khối `try` thành công, thất bại, hay return sớm. Không có sự đảm bảo `finally`, một đường dẫn ngoại lệ không mong đợi có thể bỏ qua việc dọn dẹp tài nguyên, gây ra rò rỉ tài nguyên tích lũy theo thời gian và cuối cùng làm ứng dụng crash.

Khi `return` được đến trong khối `try`, JVM lưu giá trị return trong một thanh ghi, thực thi khối `finally`, rồi tiếp tục return với giá trị đã lưu. Nếu chính khối `finally` chứa `return`, nó ghi đè giá trị return đã lưu — đây là một anti-pattern vì nó im lặng loại bỏ giá trị return gốc và nuốt bất kỳ ngoại lệ nào từ khối `try`.

### Mô Hình Tư Duy: JVM bytecode chèn finally
```
try {
    // code A
    return 42;            ← JVM lưu 42 trong thanh ghi tạm
    // code B (không bao giờ đến sau return)
} finally {
    // cleanup C          ← JVM chèn cleanup C ở MỌI đường thoát
}
// JVM tiếp tục: trả về 42 đã lưu sau khi C hoàn thành

// JVM chèn finally vào MỌI lối thoát từ try:
//   thoát bình thường   → thực thi finally → tiếp tục
//   giá trị return      → lưu giá trị → thực thi finally → trả về giá trị đã lưu
//   ngoại lệ được ném  → thực thi finally → ném lại ngoại lệ
//   System.exit()       → finally KHÔNG được đảm bảo (JVM kết thúc)
```

### Ví Dụ Code: finally với return và với ngoại lệ
```java
public class FinallyGuarantee {
    public static int getSavedValue() {
        try {
            System.out.println("In try");
            return 42; // ← giá trị return được lưu, finally chạy trước khi phương thức thoát
        } finally {
            System.out.println("In finally"); // luôn chạy
            // Output: "In try" → "In finally" → phương thức trả về 42
        }
    }

    public static int getOverriddenValue() {
        try {
            return 10; // ← được lưu, nhưng...
        } finally {
            return 20; // ← ANTI-PATTERN: ghi đè 10 đã lưu, cũng nuốt ngoại lệ
        }
    }
}
// getSavedValue() → in: "In try", "In finally", trả về 42
// getOverriddenValue() → trả về 20 (10 bị loại bỏ, bất kỳ ngoại lệ nào cũng bị nuốt)
```

### Chuỗi Nguyên Nhân-Kết Quả
`return 42` trong `try` &rarr; JVM lưu giá trị return 42 &rarr; Thực thi khối `finally` &rarr; `finally` hoàn thành bình thường &rarr; JVM trả về 42 đã lưu. Ngoại lệ được ném trong `try` &rarr; JVM bắt đầu unwinding stack &rarr; Thực thi khối `finally` trước khi rời frame &rarr; `finally` hoàn thành &rarr; Ngoại lệ được ném lại cho người gọi. `System.exit(0)` trong `try` &rarr; JVM kết thúc ngay lập tức &rarr; `finally` KHÔNG được thực thi — shutdown hook của JVM, không phải `finally`, là biện pháp dọn dẹp cuối cùng.

---

## Tại Sao Try-With-Resources Thay Thế Finally Thủ Công cho Dọn Dẹp Tài Nguyên

Trước Java 7, việc dọn dẹp tài nguyên bằng `finally` có một bug tinh tế: nếu khối `try` ném ngoại lệ VÀ lệnh `close()` trong `finally` cũng ném ngoại lệ, ngoại lệ đầu tiên bị im lặng loại bỏ — bị ghi đè bởi ngoại lệ `close()`. Điều này làm việc gỡ lỗi cực kỳ khó khăn vì nguyên nhân lỗi gốc bị mất.

`try-with-resources` giải quyết điều này thông qua khái niệm **exception suppression (ngoại lệ bị áp chế)**. Nếu cả khối `try` lẫn lệnh `close()` đều ném ngoại lệ, ngoại lệ `close()` được đính kèm vào ngoại lệ gốc như một "suppressed exception" thay vì thay thế nó. Bạn có thể lấy suppressed exception bằng `e.getSuppressed()`. Nguyên nhân lỗi gốc luôn được bảo toàn.

Ngoài ra, `try-with-resources` tạo ra code đóng được đảm bảo bởi trình biên dịch. Tài nguyên được đóng theo thứ tự ngược khai báo (khai báo cuối, đóng trước), và tất cả tài nguyên được đảm bảo đóng ngay cả khi các lệnh close trước đó ném ngoại lệ. `finally` thủ công với nhiều tài nguyên cần các khối try-finally lồng nhau để đạt được sự đảm bảo tương tự — code dài dòng, dễ lỗi, và thường được viết không đúng.

### Mô Hình Tư Duy: finally thủ công vs try-with-resources đảm bảo đóng
```
[finally thủ công — ngoại lệ gốc có thể bị mất]
InputStream in = new FileInputStream("a");
OutputStream out = new FileOutputStream("b");
try {
    // ... work ...
    // nếu work ném IOException  ← được lưu là ngoại lệ chính
} finally {
    in.close();   // nếu in.close() ném → THAY THẾ ngoại lệ chính — gốc bị mất!
    out.close();  // nếu in.close() ném, out.close() thậm chí không chạy!
}

[try-with-resources — ngoại lệ gốc được bảo toàn, tất cả tài nguyên được đóng]
try (InputStream in = new FileInputStream("a");
     OutputStream out = new FileOutputStream("b")) {
    // ... work ...
    // nếu work ném IOException ← ngoại lệ chính được giữ
    // Code đóng được tạo bởi trình biên dịch:
    //   out.close() được gọi trước (thứ tự ngược)
    //   in.close() được gọi sau
    //   nếu close ném → được thêm là SUPPRESSED exception
    //   IOException chính KHÔNG bị thay thế
}
```

### Ví Dụ Code: try-with-resources và suppressed exception
```java
class TrackingResource implements AutoCloseable {
    private final String name;
    TrackingResource(String name) { this.name = name; System.out.println("Opened " + name); }

    @Override
    public void close() throws Exception {
        System.out.println("Closed " + name);
        // nếu close ném: throw new Exception("Close failed for " + name);
    }
}

try (TrackingResource r1 = new TrackingResource("R1");
     TrackingResource r2 = new TrackingResource("R2")) {
    System.out.println("Working...");
}
// Output:
// Opened R1
// Opened R2
// Working...
// Closed R2   ← thứ tự ngược: R2 trước
// Closed R1

// Nếu try body ném VÀ close ném:
// try { throw new IOException("primary"); }  ← ngoại lệ chính
// close() { throw new IOException("close"); } ← được thêm là suppressed
// e.getSuppressed()[0] → "close" (ngoại lệ chính được bảo toàn)
```

### Chuỗi Nguyên Nhân-Kết Quả
`finally` thủ công với `close()` trong try + khối `finally` &rarr; Cả try body lẫn `close()` đều ném &rarr; Ngoại lệ `close()` thay thế ngoại lệ chính &rarr; Nguyên nhân lỗi gốc bị mất &rarr; Cơn ác mộng gỡ lỗi. `try-with-resources` &rarr; Trình biên dịch tạo code đóng tại mọi đường thoát &rarr; Nhiều tài nguyên được đóng theo thứ tự ngược khai báo &rarr; Ngoại lệ `close()` được thêm là suppressed &rarr; Ngoại lệ gốc luôn được bảo toàn &rarr; `e.getSuppressed()` tiết lộ lỗi đóng.

---

## Tại Sao Chuỗi Ngoại Lệ Bảo Toàn Ngữ Cảnh Gỡ Lỗi

Khi ngoại lệ cấp thấp được ném bởi một dependency (như JDBC, file I/O, hoặc HTTP client), chúng chứa các chi tiết cụ thể của implementation (SQL error code, OS error number, tên driver class) không có ý nghĩa với người gọi. Người gọi có thể cần xử lý một trừu tượng cấp cao hơn — `DatabaseConnectionException` thay vì `java.sql.SQLException("ORA-12170: TNS Connect Timeout")`.

Không có chuỗi ngoại lệ, việc chuyển đổi từ cấp thấp sang cấp cao phá hủy thông tin nguyên nhân gốc. Khi ứng dụng crash, stack trace chỉ hiển thị `DatabaseConnectionException: Failed to connect` — không có gì chỉ ra điều gì thực sự xảy ra ở tầng cơ sở dữ liệu. Gỡ lỗi đòi hỏi chạy lại vấn đề, thêm ghi log bổ sung, hoặc đoán mò.

Chuỗi ngoại lệ giải quyết điều này bằng cách lưu trữ ngoại lệ gốc như **nguyên nhân (cause)** của ngoại lệ mới thông qua `new HighLevelException("msg", originalCause)`. JVM tự động bao gồm chuỗi nguyên nhân đầy đủ trong output stack trace. Bất kỳ debugger hoặc log aggregator nào hiểu Java exception tiêu chuẩn đều có thể duyệt chuỗi nguyên nhân bằng `e.getCause()` lặp đi lặp lại để đến nguyên nhân gốc.

### Mô Hình Tư Duy: Chuỗi nguyên nhân của chuỗi ngoại lệ
```
Ngoại lệ Tầng Ứng Dụng:
    DatabaseConnectionException: "Failed to initialize connection pool"
        caused by: HikariPoolException: "Pool connection timeout after 30000ms"
            caused by: java.sql.SQLException: "ORA-12170: TNS Connect Timeout occurred"
                caused by: java.net.SocketTimeoutException: "connect timed out"

Không có chuỗi (tệ):
    DatabaseConnectionException: "Failed to initialize connection pool"
    ← nguyên nhân gốc bị mất hoàn toàn. Developer không biết tại sao.

Có chuỗi (đúng):
    Tất cả 4 tầng hiển thị trong một lần in stack trace duy nhất.
    Developer ngay lập tức biết: đây là timeout socket mạng ở tầng Oracle driver.
```

### Ví Dụ Code: Chuỗi ngoại lệ với nguyên nhân
```java
class DatabaseConnectionException extends Exception {
    public DatabaseConnectionException(String msg, Throwable cause) {
        super(msg, cause); // chuỗi nguyên nhân
    }
}

public void connectToDatabase() throws DatabaseConnectionException {
    try {
        // Lời gọi driver cấp thấp
        throw new java.sql.SQLException("ORA-12170: TNS Connect Timeout");
    } catch (java.sql.SQLException e) {
        // Chuyển đổi sang ngoại lệ cấp cao, BẢO TỒN nguyên nhân gốc
        throw new DatabaseConnectionException("Failed to initialize connection pool", e);
    }
}

try {
    connectToDatabase();
} catch (DatabaseConnectionException e) {
    System.out.println("High-level: " + e.getMessage());
    System.out.println("Root cause: " + e.getCause().getMessage());
    // Output:
    // High-level: Failed to initialize connection pool
    // Root cause: ORA-12170: TNS Connect Timeout
}
```

### Chuỗi Nguyên Nhân-Kết Quả
`SQLException` cấp thấp được ném bởi JDBC driver &rarr; Bị bắt ở tầng service &rarr; Được wrap trong `new DatabaseConnectionException("msg", sqlException)` &rarr; `SQLException` gốc được lưu là nguyên nhân &rarr; `DatabaseConnectionException` lan truyền đến người gọi &rarr; Người gọi bắt ngoại lệ cấp cao &rarr; Stack trace hiển thị chuỗi nguyên nhân đầy đủ bao gồm `SQLException` gốc &rarr; `e.getCause()` trả về `SQLException` gốc với chi tiết lỗi driver. Không có chuỗi: `sqlException` bị loại bỏ &rarr; Người gọi chỉ thấy thông báo chung &rarr; Nguyên nhân gốc bị mất không thể khôi phục.

## Liên Kết Tham Khảo

- https://docs.oracle.com/javase/tutorial/essential/exceptions/ (Oracle Exception Tutorial)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Exception.html (Exception API)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/AutoCloseable.html (AutoCloseable API)
