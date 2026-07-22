# Xử Lý Ngoại Lệ (Exception Handling) - Phần 4

## Mục Tiêu Học Tập

File này tập trung vào một phần cụ thể của **Xử Lý Ngoại Lệ**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tiễn, không phải từ vựng đơn thuần.

## Các Khái Niệm Được Đề Cập

- **`FileNotFoundException`** — Lớp con của IOException, ném ra khi mở tệp thất bại do tệp không tồn tại hoặc vấn đề quyền truy cập.
- **`SQLException`** — Ngoại lệ đã kiểm tra (checked exception) biểu thị lỗi kết nối cơ sở dữ liệu hoặc lỗi thực thi truy vấn.
- **`Các thực tiễn tốt nhất khi xử lý ngoại lệ`** — Hướng dẫn kỹ thuật cốt lõi để xử lý ngoại lệ chắc chắn và giữ code dễ bảo trì.

## Ghi Chú Chi Tiết

### FileNotFoundException

Một ngoại lệ đã kiểm tra (checked exception), lớp con của `IOException`, được ném ra khi không thể tìm thấy tệp với đường dẫn được chỉ định, hoặc không thể mở tệp để đọc/ghi (ví dụ: cố ghi vào một thư mục, hoặc bị từ chối quyền truy cập).

Kiểm tra thực tiễn:

- Định nghĩa `FileNotFoundException` trong một câu.
- Nhận biết `FileNotFoundException` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn, hoặc đánh đổi liên quan đến `FileNotFoundException`.

Ví dụ nhỏ hoặc mô hình tư duy:

- `new FileReader("does_not_exist.txt")` ném `FileNotFoundException`.

#### Ví Dụ Code Chạy Được: Kích Hoạt và Xử Lý
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

Một ngoại lệ đã kiểm tra (checked exception) cung cấp thông tin về lỗi truy cập cơ sở dữ liệu hoặc các lỗi khác liên quan đến tương tác với cơ sở dữ liệu quan hệ.

Kiểm tra thực tiễn:

- Định nghĩa `SQLException` trong một câu.
- Nhận biết `SQLException` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn, hoặc đánh đổi liên quan đến `SQLException`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Kết nối đến cơ sở dữ liệu với thông tin đăng nhập không hợp lệ.

#### Ví Dụ Code Chạy Được: Kích Hoạt và Xử Lý (Mô phỏng)
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDemo {
    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://localhost:3306/non_existent_db";
        try {
            // Thử kết nối (sẽ ném SQLException nếu DB hoặc driver chưa được thiết lập)
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

### Các Thực Tiễn Tốt Nhất Khi Xử Lý Ngoại Lệ (Best Practices)

Xử lý ngoại lệ chắc chắn đảm bảo ứng dụng có thể phục hồi từ các lỗi bất ngờ một cách duyên dáng, ghi lại thông tin chi tiết để gỡ lỗi, và giải phóng tài nguyên đúng cách.

Kiểm tra thực tiễn:

- Định nghĩa "thực tiễn tốt nhất khi xử lý ngoại lệ" trong một câu.
- Nhận biết các thực tiễn tốt nhất trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn, hoặc đánh đổi liên quan đến xử lý ngoại lệ không đúng cách.

Ví dụ nhỏ hoặc mô hình tư duy:

- Luôn đóng tài nguyên bằng try-with-resources; không bao giờ bắt `Throwable` khi có thể bắt ngoại lệ cụ thể.

#### Các Thực Tiễn Tốt Nhất Khi Xử Lý Ngoại Lệ
1. **Không nuốt ngoại lệ**: Một khối `catch` rỗng che giấu lỗi. Luôn ghi lại thất bại hoặc gói lại và ném lên.
2. **Bắt ngoại lệ cụ thể**: Tránh bắt `Exception` hoặc `Throwable` chung. Bắt các lớp con cụ thể để không vô tình xử lý lỗi runtime (như NPE) vốn là bug logic.
3. **Dùng Try-with-Resources**: Tránh dọn dẹp tài nguyên thủ công trong khối `finally`, vốn dài dòng và dễ gây lỗi khi đóng tài nguyên thứ cấp.
4. **Bảo tồn Stack Trace**: Khi bọc ngoại lệ cấp thấp trong ngoại lệ tùy chỉnh, luôn truyền ngoại lệ gốc vào constructor để nguyên nhân gốc rễ được giữ lại.
5. **Không dùng ngoại lệ để điều khiển luồng**: Việc tạo ngoại lệ tốn kém do phải tạo stack trace. Hãy dùng câu lệnh điều kiện thay thế.

```java
// ĐÚNG: Xâu chuỗi ngoại lệ (exception chaining) giữ lại stack trace nguồn gốc ban đầu
try {
    // ... đọc file
} catch (IOException e) {
    throw new CustomBusinessException("Failed to read user data", e);
}

// SAI: Ngoại lệ gốc bị loại bỏ, stack trace bắt đầu từ đây
try {
    // ... đọc file
} catch (IOException e) {
    throw new CustomBusinessException("Failed to read user data: " + e.getMessage());
}
```

## Lỗi Thường Gặp

### 1. Dùng Ngoại Lệ Để Điều Khiển Luồng
Dùng ngoại lệ để điều khiển đường đi thực thi của chương trình là một phản mẫu (anti-pattern) lớn. Ngoại lệ chỉ nên dành riêng cho các điều kiện bất thường, không mong đợi.
```java
// SAI: Dùng Exception để thoát vòng lặp
try {
    int i = 0;
    while (true) {
        System.out.println(array[i++]);
    }
} catch (ArrayIndexOutOfBoundsException e) {
    // Vòng lặp kết thúc
}

// ĐÚNG: Điều kiện vòng lặp rõ ràng
for (int i = 0; i < array.length; i++) {
    System.out.println(array[i]);
}
```

### 2. Bắt Throwable
Bắt `Throwable` sẽ bắt cả `Exception` và `Error`. Bắt các lỗi như `OutOfMemoryError` hoặc `InternalError` rất nguy hiểm vì JVM có thể không ở trạng thái ổn định để tiếp tục thực thi.
```java
// NGUY HIỂM: Bắt lỗi cấp hệ thống
try {
    process();
} catch (Throwable t) { 
    System.out.println("Caught everything!");
}
```

### 3. Ghi Log Rồi Ném Lại
Ghi log một ngoại lệ rồi ngay lập tức ném lại nó dẫn đến log trùng lặp ở mỗi cấp của call stack, làm log đầy tiếng ồn.
```java
// SAI: Ghi log trùng lặp
try {
    readFile();
} catch (IOException e) {
    logger.error("Failed to read file", e); // Ghi log ở đây
    throw e; // Ghi log lại bởi caller!
}
```

## Câu Hỏi Ôn Tập Phổ Biến

- Những khái niệm nào ở đây là quy tắc tại thời điểm biên dịch (compile-time)?
- Những khái niệm nào ảnh hưởng đến hành vi lúc chạy (runtime)?
- Những khái niệm nào có khả năng là bẫy trong phỏng vấn?
