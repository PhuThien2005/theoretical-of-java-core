# Xử lý ngoại lệ (Exception Handling) - Phần 3

## Mục tiêu học tập (Learning Goal)

Tập tin này bao gồm một phần trọng tâm về **Xử lý ngoại lệ (Exception Handling)**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, không phải là từ vựng riêng lẻ.

## Khái quát nội dung (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `ArrayIndexOutOfBoundsException` | Được ném ra khi một mảng được truy cập với một chỉ số không hợp lệ (âm hoặc >= kích thước). |
| `StringIndexOutOfBoundsException` | Được ném ra bởi các phương thức của lớp String (charAt, substring) khi chỉ số nằm ngoài phạm vi. |
| `ClassCastException` | Được ném ra khi ép kiểu một tham chiếu đối tượng sang một kiểu mà nó không kế thừa hoặc triển khai. |
| `NumberFormatException` | Lớp con của IllegalArgumentException, được ném ra khi phân tích một chuỗi số không hợp lệ. |
| `ArithmeticException` | Được ném ra đối với các điều kiện số học bất thường, như phép chia số nguyên cho không. |
| `IllegalArgumentException` | Được ném ra khi một phương thức nhận được một tham số không hợp lệ hoặc không phù hợp. |
| `IllegalStateException` | Được ném ra khi môi trường hoặc trạng thái đối tượng không phù hợp cho hoạt động được yêu cầu. |
| `IOException` | Lớp cơ sở cho các ngoại lệ kiểm tra lỗi I/O (mạng, hệ thống tệp, v.v.). |

## Ghi chú chi tiết (Detailed Notes)

### ArrayIndexOutOfBoundsException

Được ném ra để chỉ ra rằng một mảng đã được truy cập với một chỉ số không hợp lệ. Chỉ số đó có thể là số âm hoặc lớn hơn hoặc bằng kích thước của mảng.

Kiểm tra thực tế (Practical check):

- Định nghĩa `ArrayIndexOutOfBoundsException` trong một câu.
- Nhận diện `ArrayIndexOutOfBoundsException` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `ArrayIndexOutOfBoundsException`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Truy cập `arr[arr.length]` sẽ ném ra `ArrayIndexOutOfBoundsException`.

#### Ví dụ mã nguồn chạy được: Kích hoạt và Xử lý (Runnable Code Example: Triggering and Handling)
```java
public class ArrayOOBDemo {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3};
        try {
            int val = numbers[3]; // Index 3 is out of bounds (valid indices: 0, 1, 2)
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
        }
    }
}
```

### StringIndexOutOfBoundsException

Được ném ra bởi các phương thức của `String` để chỉ ra rằng một chỉ số là số âm hoặc lớn hơn hoặc bằng chiều dài của chuỗi.

Kiểm tra thực tế (Practical check):

- Định nghĩa `StringIndexOutOfBoundsException` trong một câu.
- Nhận diện `StringIndexOutOfBoundsException` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `StringIndexOutOfBoundsException`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Gọi `"hello".charAt(5)` sẽ ném ra `StringIndexOutOfBoundsException`.

#### Ví dụ mã nguồn chạy được: Kích hoạt và Xử lý (Runnable Code Example: Triggering and Handling)
```java
public class StringOOBDemo {
    public static void main(String[] args) {
        String greet = "Hello";
        try {
            char ch = greet.charAt(10); // Index 10 is out of bounds (length is 5)
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Caught StringIndexOutOfBoundsException: " + e.getMessage());
        }
    }
}
```

### ClassCastException

Được ném ra khi mã nguồn cố gắng ép kiểu (cast) một đối tượng sang một lớp con mà đối tượng đó không phải là một thực thể của lớp con đó.

Kiểm tra thực tế (Practical check):

- Định nghĩa `ClassCastException` trong một câu.
- Nhận diện `ClassCastException` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `ClassCastException`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Ép kiểu một đối tượng `Integer` sang tham chiếu kiểu `String`.

#### Ví dụ mã nguồn chạy được: Kích hoạt và Xử lý (Runnable Code Example: Triggering and Handling)
```java
public class ClassCastDemo {
    public static void main(String[] args) {
        Object obj = Integer.valueOf(100);
        try {
            String str = (String) obj; // Throws ClassCastException (Integer cannot be cast to String)
        } catch (ClassCastException e) {
            System.out.println("Caught ClassCastException: " + e.getMessage());
        }
    }
}
```

### NumberFormatException

Được ném ra để chỉ ra rằng ứng dụng đã cố gắng chuyển đổi một chuỗi thành một trong các kiểu số, nhưng chuỗi đó không có định dạng thích hợp.

Kiểm tra thực tế (Practical check):

- Định nghĩa `NumberFormatException` trong một câu.
- Nhận diện `NumberFormatException` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `NumberFormatException`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Gọi `Integer.parseInt("abc")` sẽ ném ra `NumberFormatException`.

#### Ví dụ mã nguồn chạy được: Kích hoạt và Xử lý (Runnable Code Example: Triggering and Handling)
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

Được ném ra khi xảy ra một điều kiện số học bất thường. Ví dụ: phép chia số nguyên cho số 0 "divide by zero" sẽ ném ra ngoại lệ này.

Kiểm tra thực tế (Practical check):

- Định nghĩa `ArithmeticException` trong một câu.
- Nhận diện `ArithmeticException` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `ArithmeticException`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Phép chia số nguyên: `10 / 0` sẽ ném ra `ArithmeticException`.

#### Ví dụ mã nguồn chạy được: Kích hoạt và Xử lý (Runnable Code Example: Triggering and Handling)
```java
public class ArithmeticDemo {
    public static void main(String[] args) {
        try {
            int result = 42 / 0; // Integer division by zero
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException: " + e.getMessage());
        }
    }
}
```

### IllegalArgumentException

Được ném ra để chỉ ra rằng một phương thức đã được truyền một đối số không hợp lệ hoặc không phù hợp.

Kiểm tra thực tế (Practical check):

- Định nghĩa `IllegalArgumentException` trong một câu.
- Nhận diện `IllegalArgumentException` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `IllegalArgumentException`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Truyền giá trị tuổi âm vào phương thức `setAge(int age)` trong khi phương thức yêu cầu tuổi >= 0.

#### Ví dụ mã nguồn chạy được: Kích hoạt và Xử lý (Runnable Code Example: Triggering and Handling)
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

Báo hiệu rằng một phương thức đã được gọi vào một thời điểm không hợp lệ hoặc không phù hợp. Nói cách khác, môi trường Java hoặc đối tượng Java đang không ở trạng thái thích hợp cho hoạt động được yêu cầu.

Kiểm tra thực tế (Practical check):

- Định nghĩa `IllegalStateException` trong một câu.
- Nhận diện `IllegalStateException` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `IllegalStateException`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Khởi chạy một Thread đã được khởi chạy trước đó.

#### Ví dụ mã nguồn chạy được: Kích hoạt và Xử lý (Runnable Code Example: Triggering and Handling)
```java
public class IllegalStateDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> System.out.println("Running"));
        thread.start();
        try {
            thread.start(); // Throws IllegalThreadStateException (subclass of IllegalStateException)
        } catch (IllegalStateException e) {
            System.out.println("Caught IllegalStateException: " + e.getMessage());
        }
    }
}
```

### IOException

Báo hiệu rằng một ngoại lệ I/O ở một dạng nào đó đã xảy ra. Lớp này là lớp chung cho các ngoại lệ được tạo ra bởi các thao tác I/O bị lỗi hoặc bị gián đoạn.

Kiểm tra thực tế (Practical check):

- Định nghĩa `IOException` trong một câu.
- Nhận diện `IOException` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), hạn chế hoặc sự đánh đổi liên quan đến `IOException`.

Ví dụ nhỏ hoặc mô hình tư duy (Tiny example or mental model):

- Đọc từ luồng (Thread) socket sau khi máy chủ từ xa đã đóng kết nối.

#### Ví dụ mã nguồn chạy được: Kích hoạt và Xử lý (Runnable Code Example: Triggering and Handling)
```java
import java.io.FileInputStream;
import java.io.IOException;

public class IODemo {
    public static void main(String[] args) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("does_not_exist.txt");
            fis.read();
        } catch (IOException e) { // Checked exception handled
            System.out.println("Caught IOException: " + e.getMessage());
        }
    }
}
```

## Các lỗi thường gặp (Common Mistakes)

### 1. Phép chia số thực dấu phẩy động cho không (Floating-Point Division by Zero)
Khác với phép chia số nguyên, việc chia một số thực dấu phẩy động (`float` hoặc `double`) cho số không **không** ném ra một `ArithmeticException`. Thay vào đó, nó định giá thành các giá trị đặc biệt: `Infinity` (vô cực), `-Infinity` (âm vô cực), hoặc `NaN` (không phải là số - Not a Number).
```java
public class FloatingPointZeroDemo {
    public static void main(String[] args) {
        double a = 1.0 / 0.0; // Evaluates to Double.POSITIVE_INFINITY
        double b = 0.0 / 0.0; // Evaluates to Double.NaN

        System.out.println("a: " + a); // Prints Infinity
        System.out.println("b: " + b); // Prints NaN
        
        // No exception is thrown!
    }
}
```

### 2. Ép kiểu nhiều bước và ClassCastException (Multi-step Casting and ClassCastException)
Việc ép kiểu một tham chiếu đối tượng sang một lớp cha trước (như `Object`) không bỏ qua được các kiểm tra kiểu ở thời gian chạy. JVM theo dõi kiểu thực tế ở thời gian chạy của đối tượng, vì vậy việc ép kiểu sang một lớp con không tương thích vẫn sẽ ném ra `ClassCastException`.
```java
Object number = Integer.valueOf(42);
// Compiles fine because number is an Object, but crashes at runtime!
String str = (String) number; 
```

### 3. Phân tích giá trị Null qua NumberFormatException (Parse Nulls via NumberFormatException)
Truyền một tham chiếu `null` vào `Integer.parseInt(str)` hoặc `Double.parseDouble(str)` sẽ ném ra một `NullPointerException`, chứ không phải `NumberFormatException`. Hãy chắc chắn rằng bạn kiểm tra hoặc xử lý các giá trị null trước khi phân tích cú pháp.
```java
String input = null;
try {
    Integer.parseInt(input);
} catch (NumberFormatException e) {
    // This catch block is bypassed! NullPointerException is thrown instead.
}
```

## Các câu hỏi ôn tập thường gặp (Common Review Prompts)

- Khái niệm nào ở đây là quy tắc thời gian biên dịch (compile-time rule)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi thời gian chạy (runtime behavior)?
- Khái niệm nào ở đây dễ là bẫy phỏng vấn?
