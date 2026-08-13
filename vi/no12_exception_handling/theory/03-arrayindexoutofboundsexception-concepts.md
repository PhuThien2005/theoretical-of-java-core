# Xử Lý Ngoại Lệ (Exception Handling) - Phần 3

File này tập trung vào một phần cụ thể của **Xử Lý Ngoại Lệ**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tiễn, không phải từ vựng đơn thuần.

## Các Khái Niệm Được Đề Cập

## Ghi Chú Chi Tiết

### ArrayIndexOutOfBoundsException

Ném ra để báo hiệu rằng mảng đã được truy cập với chỉ số không hợp lệ. Chỉ số hoặc là âm, hoặc lớn hơn hoặc bằng kích thước mảng.

Kiểm tra thực tiễn:

- Định nghĩa `ArrayIndexOutOfBoundsException` trong một câu.
- Nhận biết `ArrayIndexOutOfBoundsException` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn, hoặc đánh đổi liên quan đến `ArrayIndexOutOfBoundsException`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Truy cập `arr[arr.length]` sẽ ném `ArrayIndexOutOfBoundsException`.

#### Ví Dụ Code Chạy Được: Kích Hoạt và Xử Lý
```java
public class ArrayOOBDemo {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3};
        try {
            int val = numbers[3]; // Chỉ số 3 vượt giới hạn (chỉ số hợp lệ: 0, 1, 2)
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
        }
    }
}
```

### StringIndexOutOfBoundsException

Ném ra bởi các phương thức của `String` để báo hiệu rằng chỉ số hoặc là âm, hoặc lớn hơn hoặc bằng độ dài chuỗi.

Kiểm tra thực tiễn:

- Định nghĩa `StringIndexOutOfBoundsException` trong một câu.
- Nhận biết `StringIndexOutOfBoundsException` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn, hoặc đánh đổi liên quan đến `StringIndexOutOfBoundsException`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Gọi `"hello".charAt(5)` sẽ ném `StringIndexOutOfBoundsException`.

#### Ví Dụ Code Chạy Được: Kích Hoạt và Xử Lý
```java
public class StringOOBDemo {
    public static void main(String[] args) {
        String greet = "Hello";
        try {
            char ch = greet.charAt(10); // Chỉ số 10 vượt giới hạn (độ dài chuỗi là 5)
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Caught StringIndexOutOfBoundsException: " + e.getMessage());
        }
    }
}
```

### ClassCastException

Ném ra khi code cố gắng ép kiểu một đối tượng sang lớp con mà nó không phải là thực thể của lớp đó.

Kiểm tra thực tiễn:

- Định nghĩa `ClassCastException` trong một câu.
- Nhận biết `ClassCastException` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn, hoặc đánh đổi liên quan đến `ClassCastException`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Ép kiểu một đối tượng `Integer` sang tham chiếu `String`.

#### Ví Dụ Code Chạy Được: Kích Hoạt và Xử Lý
```java
public class ClassCastDemo {
    public static void main(String[] args) {
        Object obj = Integer.valueOf(100);
        try {
            String str = (String) obj; // Ném ClassCastException (Integer không thể ép sang String)
        } catch (ClassCastException e) {
            System.out.println("Caught ClassCastException: " + e.getMessage());
        }
    }
}
```

### NumberFormatException

Ném ra để báo hiệu rằng ứng dụng đã cố gắng chuyển đổi một chuỗi sang một trong các kiểu số, nhưng chuỗi đó không có định dạng phù hợp.

Kiểm tra thực tiễn:

- Định nghĩa `NumberFormatException` trong một câu.
- Nhận biết `NumberFormatException` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn, hoặc đánh đổi liên quan đến `NumberFormatException`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Gọi `Integer.parseInt("abc")` sẽ ném `NumberFormatException`.

#### Ví Dụ Code Chạy Được: Kích Hoạt và Xử Lý
```java
public class NumberFormatDemo {
    public static void main(String[] args) {
        String ageInput = "25a";
        try {
            int age = Integer.parseInt(ageInput);
        } catch (NumberFormatException e) {
            System.out.println("Caught NumberFormatException: " + e.getMessage());
        }
    }
}
```

### ArithmeticException

Ném ra khi xảy ra điều kiện số học bất thường. Ví dụ, phép chia số nguyên cho không (integer divide by zero) sẽ ném ngoại lệ này.

Kiểm tra thực tiễn:

- Định nghĩa `ArithmeticException` trong một câu.
- Nhận biết `ArithmeticException` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn, hoặc đánh đổi liên quan đến `ArithmeticException`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Phép chia số nguyên: `10 / 0` ném `ArithmeticException`.

#### Ví Dụ Code Chạy Được: Kích Hoạt và Xử Lý
```java
public class ArithmeticDemo {
    public static void main(String[] args) {
        try {
            int result = 42 / 0; // Chia số nguyên cho không
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException: " + e.getMessage());
        }
    }
}
```

### IllegalArgumentException

Ném ra để báo hiệu rằng một phương thức đã nhận được đối số không hợp lệ hoặc không phù hợp.

Kiểm tra thực tiễn:

- Định nghĩa `IllegalArgumentException` trong một câu.
- Nhận biết `IllegalArgumentException` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn, hoặc đánh đổi liên quan đến `IllegalArgumentException`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Truyền tuổi âm cho phương thức `setAge(int age)` yêu cầu age >= 0.

#### Ví Dụ Code Chạy Được: Kích Hoạt và Xử Lý
```java
public class IllegalArgumentDemo {
    public static void setPercentage(int val) {
        if (val < 0 || val > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
    }

    public static void main(String[] args) {
        try {
            setPercentage(150);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught IllegalArgumentException: " + e.getMessage());
        }
    }
}
```

### IllegalStateException

Báo hiệu rằng một phương thức đã được gọi vào thời điểm không hợp lệ hoặc không phù hợp. Nói cách khác, môi trường Java hoặc đối tượng Java không ở trạng thái phù hợp cho thao tác được yêu cầu.

Kiểm tra thực tiễn:

- Định nghĩa `IllegalStateException` trong một câu.
- Nhận biết `IllegalStateException` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn, hoặc đánh đổi liên quan đến `IllegalStateException`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khởi động một Thread đã được khởi động trước đó.

#### Ví Dụ Code Chạy Được: Kích Hoạt và Xử Lý
```java
public class IllegalStateDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> System.out.println("Running"));
        thread.start();
        try {
            thread.start(); // Ném IllegalThreadStateException (lớp con của IllegalStateException)
        } catch (IllegalStateException e) {
            System.out.println("Caught IllegalStateException: " + e.getMessage());
        }
    }
}
```

### IOException

Báo hiệu rằng một ngoại lệ I/O (đầu vào/đầu ra) nào đó đã xảy ra. Đây là lớp ngoại lệ chung được tạo ra bởi các thao tác I/O thất bại hoặc bị gián đoạn.

Kiểm tra thực tiễn:

- Định nghĩa `IOException` trong một câu.
- Nhận biết `IOException` trong code, lệnh, tài liệu, hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn, hoặc đánh đổi liên quan đến `IOException`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Đọc từ một socket stream sau khi máy chủ từ xa đóng kết nối.

#### Ví Dụ Code Chạy Được: Kích Hoạt và Xử Lý
```java
import java.io.FileInputStream;
import java.io.IOException;

public class IODemo {
    public static void main(String[] args) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("does_not_exist.txt");
            fis.read();
        } catch (IOException e) { // Ngoại lệ đã kiểm tra được xử lý
            System.out.println("Caught IOException: " + e.getMessage());
        }
    }
}
```

## Lỗi Thường Gặp

### 1. Chia Số Thực Cho Không (Floating-Point Division by Zero)
Khác với phép chia số nguyên, chia số thực (`float` hoặc `double`) cho không **không** ném `ArithmeticException`. Thay vào đó, nó trả về các giá trị đặc biệt: `Infinity`, `-Infinity`, hoặc `NaN` (Not a Number — Không phải số).
```java
public class FloatingPointZeroDemo {
    public static void main(String[] args) {
        double a = 1.0 / 0.0; // Kết quả là Double.POSITIVE_INFINITY
        double b = 0.0 / 0.0; // Kết quả là Double.NaN

        System.out.println("a: " + a); // In ra Infinity
        System.out.println("b: " + b); // In ra NaN
        
        // Không có ngoại lệ nào được ném!
    }
}
```

### 2. Ép Kiểu Nhiều Bước và ClassCastException
Ép kiểu một tham chiếu đối tượng sang lớp cha (như `Object`) trước tiên không bỏ qua được kiểm tra kiểu lúc chạy. JVM theo dõi kiểu thực sự lúc chạy của đối tượng, vì vậy ép kiểu sang lớp con không tương thích vẫn sẽ ném `ClassCastException`.
```java
Object number = Integer.valueOf(42);
// Biên dịch được vì number là Object, nhưng lỗi lúc chạy!
String str = (String) number; 
```

### 3. Phân Tích Giá Trị Null Gây NumberFormatException
Truyền tham chiếu `null` cho `Integer.parseInt(str)` hoặc `Double.parseDouble(str)` sẽ ném `NullPointerException`, không phải `NumberFormatException`. Hãy đảm bảo xử lý null hoặc kiểm tra trước khi phân tích.
```java
String input = null;
try {
    Integer.parseInt(input);
} catch (NumberFormatException e) {
    // Khối catch này bị bỏ qua! NullPointerException được ném thay thế.
}
```

## Câu Hỏi Ôn Tập Phổ Biến

- Những khái niệm nào ở đây là quy tắc tại thời điểm biên dịch (compile-time)?
- Những khái niệm nào ảnh hưởng đến hành vi lúc chạy (runtime)?
- Những khái niệm nào có khả năng là bẫy trong phỏng vấn?
