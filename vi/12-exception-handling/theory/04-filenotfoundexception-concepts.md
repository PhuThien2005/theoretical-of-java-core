# Xử lý ngoại lệ (Exception Handling) - Phần 4

## Mục tiêu học tập (Learning Goal)

Tập tin này bao gồm một phần trọng tâm về **Xử lý ngoại lệ (Exception Handling)**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, không phải là từ vựng riêng lẻ.

## Khái quát nội dung (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `FileNotFoundException` | Lớp con của IOException, được ném ra khi việc mở tệp thất bại do tệp không tồn tại hoặc các vấn đề truy cập. |
| `SQLException` | Ngoại lệ kiểm tra đại diện cho các lỗi kết nối cơ sở dữ liệu hoặc thực thi truy vấn. |
| `Best practices when handling exceptions` | Các nguyên tắc kỹ thuật cốt lõi để xử lý ngoại lệ một cách mạnh mẽ và giữ cho mã nguồn dễ bảo trì. |

## Ghi chú chi tiết (Detailed Notes)

### FileNotFoundException

Một ngoại lệ kiểm tra (checked exception), lớp con của `IOException`, được ném ra khi một tệp có đường dẫn được chỉ định không thể tìm thấy, hoặc không thể mở để đọc/ghi (ví dụ: cố gắng ghi vào một thư mục, hoặc quyền truy cập bị từ chối).

Kiểm tra thực tế (Practical check):

- Định nghĩa `FileNotFoundException` trong một câu.
- Nhận diện `FileNotFoundException` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `FileNotFoundException`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- `new FileReader("does_not_exist.txt")` sẽ ném ra `FileNotFoundException`.

#### Ví dụ mã nguồn chạy được: Kích hoạt và Xử lý (Runnable Code Example: Triggering and Handling)
```java
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class FileNotFoundDemo {
    public static void main(String[] args) {
        File file = new File("invalid_path_to_file.txt");
        try {
            FileReader fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println("Caught FileNotFoundException: " + e.getMessage());
        }
    }
}
```

### SQLException

Một ngoại lệ kiểm tra cung cấp thông tin về lỗi truy cập cơ sở dữ liệu hoặc các lỗi khác liên quan đến tương tác với cơ sở dữ liệu quan hệ.

Kiểm tra thực tế (Practical check):

- Định nghĩa `SQLException` trong một câu.
- Nhận diện `SQLException` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `SQLException`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Kết nối tới cơ sở dữ liệu với thông tin đăng nhập không hợp lệ.

#### Ví dụ mã nguồn chạy được: Kích hoạt và Xử lý (mô phỏng) (Runnable Code Example: Triggering and Handling (Simulated))
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDemo {
    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://localhost:3306/non_existent_db";
        try {
            // Attempting connection (will throw SQLException if DB or driver is not set up)
            Connection conn = DriverManager.getConnection(dbUrl, "user", "password");
        } catch (SQLException e) {
            System.out.println("Caught SQLException!");
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
        }
    }
}
```

### Thực hành tốt nhất khi xử lý ngoại lệ (Best practices when handling exceptions)

Xử lý ngoại lệ mạnh mẽ đảm bảo ứng dụng có thể phục hồi từ các lỗi không mong muốn một cách suôn sẻ, ghi nhật ký (log) các chi tiết có liên quan để phục vụ gỡ lỗi và giải phóng tài nguyên một cách thích hợp.

Kiểm tra thực tế (Practical check):

- Định nghĩa `Best practices when handling exceptions` trong một câu.
- Nhận diện `Best practices when handling exceptions` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `Best practices when handling exceptions`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Luôn đóng tài nguyên bằng try-with-resources; không bao giờ bắt `Throwable` khi bạn có thể bắt các ngoại lệ cụ thể hơn.

#### Các thực hành tốt nhất cốt lõi khi xử lý ngoại lệ (Key Exception Handling Best Practices)
1. **Không bao giờ nuốt ngoại lệ (Never swallow exceptions)**: Một khối `catch` trống sẽ che giấu các lỗi. Luôn ghi log lại lỗi hoặc bọc nó và ném lại.
2. **Bắt các ngoại lệ cụ thể (Catch specific exceptions)**: Tránh bắt kiểu `Exception` hoặc `Throwable` chung chung. Hãy bắt các lớp con cụ thể để bạn không vô tình xử lý các lỗi thời gian chạy (như NPE) vốn đại diện cho lỗi logic trong mã nguồn.
3. **Sử dụng Try-with-Resources**: Tránh dọn dẹp tài nguyên thủ công trong các khối `finally`, vốn rất dài dòng và dễ phát sinh ngoại lệ thứ cấp khi đóng tài nguyên.
4. **Bảo toàn dấu vết ngăn xếp (Preserve Stack Traces)**: Khi bọc một ngoại lệ cấp thấp vào một ngoại lệ tùy chỉnh, luôn truyền ngoại lệ ban đầu vào hàm dựng để bảo toàn nguyên nhân gốc rễ.
5. **Không dùng ngoại lệ để điều khiển luồng (Do not use exceptions for flow control)**: Việc tạo ngoại lệ rất tốn kém hiệu năng do phải tạo dấu vết ngăn xếp (stack trace). Hãy sử dụng các câu lệnh điều kiện thay thế.

```java
// GOOD: Exception chaining preserves the original source stack trace
try {
    // ... file reading
} catch (IOException e) {
    throw new CustomBusinessException("Failed to read user data", e);
}

// BAD: Original exception is discarded, stack trace starts here
try {
    // ... file reading
} catch (IOException e) {
    throw new CustomBusinessException("Failed to read user data: " + e.getMessage());
}
```

## Các lỗi thường gặp (Common Mistakes)

### 1. Sử dụng Ngoại lệ để điều khiển luồng (Using Exceptions for Flow Control)
Sử dụng các ngoại lệ để điều khiển đường dẫn thực thi của một chương trình là một anti-pattern lớn. Các ngoại lệ chỉ nên được dành riêng cho các điều kiện bất thường, không mong muốn.
```java
// BAD: Using Exception to exit a loop
try {
    int i = 0;
    while (true) {
        System.out.println(array[i++]);
    }
} catch (ArrayIndexOutOfBoundsException e) {
    // Loop finished
}

// GOOD: Clean loop condition
for (int i = 0; i < array.length; i++) {
    System.out.println(array[i]);
}
```

### 2. Bắt Throwable (Catching Throwable)
Bắt `Throwable` sẽ bắt cả `Exception` và `Error`. Việc bắt các lỗi hệ thống như `OutOfMemoryError` hoặc `InternalError` là rất nguy hiểm vì JVM có thể không ở trong trạng thái ổn định để tiếp tục thực thi.
```java
// DANGEROUS: Catching system-level errors
try {
    process();
} catch (Throwable t) { 
    System.out.println("Caught everything!");
}
```

### 3. Ghi log rồi ném lại ngoại lệ (Logging and Rethrowing)
Ghi log một ngoại lệ và sau đó ném lại ngay lập tức dẫn đến việc ghi log trùng lặp ở mọi cấp của ngăn xếp cuộc gọi, làm đầy nhật ký với các thông tin nhiễu.
```java
// BAD: Duplicate logging
try {
    readFile();
} catch (IOException e) {
    logger.error("Failed to read file", e); // Logged here
    throw e; // Logged again by the caller!
}
```

## Các câu hỏi ôn tập thường gặp (Common Review Prompts)

- Khái niệm nào ở đây là quy tắc thời gian biên dịch (compile-time rule)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi thời gian chạy (runtime behavior)?
- Khái niệm nào ở đây dễ là bẫy phỏng vấn?
