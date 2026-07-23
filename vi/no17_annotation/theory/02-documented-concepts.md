# Chú thích - Phần 2

## Mục Tiêu Học Tập

Tài liệu này tập trung vào một phần chuyên sâu của **Chú thích (Annotation)**. Hãy nghiên cứu từng khái niệm dưới dạng quy tắc thực tế trong Java, không chỉ đơn thuần là lý thuyết từ vựng.

## Tóm Tắt Nội Dung (Outline Coverage)

- **`@Documented`** — @Documented: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`@Inherited`** — @Inherited: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`@Repeatable`** — @Repeatable: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Custom annotation`** — Custom annotation: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Runtime annotation`** — Runtime annotation: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Basic annotation processing`** — Basic annotation processing: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

## Ghi Chú Chi Tiết

### @Documented

**`@Documented`** — Quy định chú thích sẽ xuất hiện trong tài liệu Javadoc được sinh ra.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng thức được cho phép và kịch bản thất bại. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn mô tả thuần túy.

Kiểm tra thực tế:
- Định nghĩa `@Documented` trong một câu.
- Nhận biết `@Documented` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `@Documented`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy tự hỏi: `@Documented` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java
Theo mặc định, các chú thích được áp dụng cho một lớp, phương thức hoặc trường dữ liệu KHÔNG được đưa vào tài liệu Javadoc (Javadoc) được tạo ra. Khi một khai báo chú thích tùy chỉnh được chú thích bằng siêu chú thích (Meta-annotation) `@Documented`, bất kỳ phần tử mã nào được chú thích bằng chú thích tùy chỉnh này sẽ hiển thị chú thích đó trong tài liệu API (API documentation) được tạo ra của chúng.

```java
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PublishedAPI {
    String version() default "1.0";
}

// When generating Javadoc, @PublishedAPI(version = "2.0") will appear above publicApiMethod()
public class JavadocDemo {
    @PublishedAPI(version = "2.0")
    public void publicApiMethod() {}
}
```

---

### @Inherited

**`@Inherited`** — Cho phép lớp con tự động kế thừa chú thích từ lớp cha.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng thức được cho phép và kịch bản thất bại. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn mô tả thuần túy.

Kiểm tra thực tế:
- Định nghĩa `@Inherited` trong một câu.
- Nhận biết `@Inherited` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `@Inherited`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy tự hỏi: `@Inherited` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java
`@Inherited` chỉ ra rằng một chú thích trên một lớp sẽ tự động được kế thừa bởi các lớp con (Subclass) của nó. Nếu lớp `Child` kế thừa từ `Parent`, và `Parent` có một chú thích được đánh dấu `@Inherited`, các truy vấn phản chiếu (Reflection query) cho chú thích trên `Child` vẫn sẽ thành công ngay cả khi `Child` không có khai báo chú thích nào.

**Các hạn chế quan trọng:**
1. Nó **chỉ** áp dụng cho kế thừa lớp. Nó KHÔNG áp dụng nếu một lớp triển khai một giao diện (Interface) có chú thích `@Inherited`.
2. Nó KHÔNG áp dụng cho các phương thức, trường dữ liệu hoặc hàm khởi tạo. Nếu một phương thức của lớp cha có một chú thích được kế thừa, một lớp con ghi đè phương thức đó sẽ không kế thừa chú thích ở cấp độ phương thức.

```java
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@interface InheritedAnnotation {}

@Retention(RetentionPolicy.RUNTIME)
@interface StandardAnnotation {}

@InheritedAnnotation
@StandardAnnotation
class SuperClass {}

class SubClass extends SuperClass {}

// At runtime:
// SubClass.class.isAnnotationPresent(InheritedAnnotation.class) -> true
// SubClass.class.isAnnotationPresent(StandardAnnotation.class) -> false
```

---

### @Repeatable

**`@Repeatable`** — Cho phép áp dụng cùng một chú thích nhiều lần trên một khai báo.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng thức được cho phép và kịch bản thất bại. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn mô tả thuần túy.

Kiểm tra thực tế:
- Định nghĩa `@Repeatable` trong một câu.
- Nhận biết `@Repeatable` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `@Repeatable`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy tự hỏi: `@Repeatable` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java
Được giới thiệu từ Java 8, `@Repeatable` cho phép áp dụng cùng một chú thích nhiều lần trên cùng một phần tử. Nó yêu cầu một kiểu "chú thích vùng chứa (Container annotation)", chú thích này phải có một phương thức `value()` trả về một mảng của kiểu chú thích có thể lặp lại. Chú thích vùng chứa phải có chính sách duy trì (Retention policy) kéo dài ít nhất là bằng chú thích có thể lặp lại đó.

```java
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 1. The Container Annotation
@Retention(RetentionPolicy.RUNTIME)
@interface Schedules {
    Schedule[] value();
}

// 2. The Repeatable Annotation, referencing the container class
@Repeatable(Schedules.class)
@Retention(RetentionPolicy.RUNTIME)
@interface Schedule {
    String day();
    String time();
}

// 3. Usage
public class ScheduleDemo {
    @Schedule(day = "Monday", time = "09:00")
    @Schedule(day = "Friday", time = "17:00")
    public void runJob() {}
}
```

---

### Chú thích Tùy chỉnh (Custom annotation)

Một chú thích đính kèm siêu dữ liệu (Metadata) vào các phần tử của chương trình như lớp, phương thức, hoặc trường dữ liệu.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng thức được cho phép và kịch bản thất bại. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn mô tả thuần túy.

Kiểm tra thực tế:
- Định nghĩa `Custom annotation` trong một câu.
- Nhận biết `Custom annotation` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Custom annotation`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy tự hỏi: `Custom annotation` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java
Các chú thích tùy chỉnh được khai báo bằng cú pháp `@interface`. Chúng ngầm định kế thừa từ `java.lang.annotation.Annotation`. Các phần tử của chúng được định nghĩa giống như các phương thức không có tham số và có thể chỉ định các giá trị mặc định bằng từ khóa `default`.

**Các kiểu trả về hợp lệ của phần tử:**
- Tất cả các kiểu nguyên thủy (`int`, `char`, `double`, `boolean`, v.v.)
- `java.lang.String`
- `java.lang.Class` (có thể được tham số hóa)
- Bất kỳ kiểu `enum` nào
- Bất kỳ kiểu chú thích nào
- Mảng một chiều của bất kỳ kiểu nào ở trên (ví dụ: `String[]`, `int[]`, `MyEnum[]`)

**Các kiểu trả về không hợp lệ:**
- Mảng lồng nhau hoặc đa chiều (ví dụ: `String[][]`, `int[][]`)
- Lớp bao bọc đối tượng (ví dụ: `Integer`, `Double`, `Boolean`)
- Các lớp thông thường bất kỳ (ví dụ: `java.util.Date`, `java.util.List`)
- Tham số kiểu (Generics)

```java
public @interface Configuration {
    String name();                    // Required element
    int poolSize() default 10;        // Element with a default value
    Class<?> driverClass() default Object.class; // Class element
    String[] options() default {};    // Array element
}
```

---

### Chú thích thời điểm chạy (Runtime annotation)

Một chú thích đính kèm siêu dữ liệu vào các phần tử của chương trình như lớp, phương thức, hoặc trường dữ liệu.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng thức được cho phép và kịch bản thất bại. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn mô tả thuần túy.

Kiểm tra thực tế:
- Định nghĩa `Runtime annotation` trong một câu.
- Nhận biết `Runtime annotation` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Runtime annotation`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy tự hỏi: `Runtime annotation` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java
Các chú thích thời điểm chạy được khai báo với `@Retention(RetentionPolicy.RUNTIME)`. JVM giữ siêu dữ liệu của chúng trong bộ nhớ, cho phép kiểm tra chúng một cách động thông qua Phản chiếu (Reflection).

```java
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Priority {
    int value() default 1;
}
```

---

### Xử lý chú thích cơ bản (Basic annotation processing)

Một chú thích đính kèm siêu dữ liệu vào các phần tử của chương trình như lớp, phương thức, hoặc trường dữ liệu.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng thức được cho phép và kịch bản thất bại. Hãy ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn mô tả thuần túy.

Kiểm tra thực tế:
- Định nghĩa `Basic annotation processing` trong một câu.
- Nhận biết `Basic annotation processing` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Basic annotation processing`.

Ví dụ nhỏ hoặc mô hình tư duy:
- Khi đọc mã nguồn, hãy tự hỏi: `Basic annotation processing` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java
Xử lý chú thích có thể diễn ra tại thời điểm biên dịch (sử dụng API `Processor` tiêu chuẩn trong gói `javax.annotation.processing`) hoặc tại thời điểm chạy (sử dụng API Phản chiếu).
Tại thời điểm chạy, các lớp như `Class`, `Method`, `Field`, `Constructor`, và `Parameter` đều triển khai giao diện `AnnotatedElement`, giao diện này cung cấp các phương thức phản chiếu để đọc siêu dữ liệu chú thích:
- `isAnnotationPresent(Class<? extends Annotation> annotationClass)`: Trả về `true` nếu phần tử có chú thích được chỉ định.
- `getAnnotation(Class<T> annotationClass)`: Trả về chú thích của kiểu được chỉ định nếu có, hoặc `null`.
- `getAnnotations()`: Trả về tất cả các chú thích có mặt trên phần tử (bao gồm cả các chú thích được kế thừa).
- `getDeclaredAnnotations()`: Trả về các chú thích được khai báo trực tiếp trên phần tử (loại trừ các chú thích được kế thừa).

```java
import java.lang.reflect.Method;

public class BasicProcessor {
    public static void runProcess() throws Exception {
        Method method = MyClass.class.getMethod("executeTask");
        if (method.isAnnotationPresent(Priority.class)) {
            Priority priority = method.getAnnotation(Priority.class);
            System.out.println("Processing method with priority: " + priority.value());
        }
    }
}

class MyClass {
    @Priority(5)
    public void executeTask() {}
}
```

#### Tại sao Xử lý Chú thích Thời điểm chạy Sử dụng Ủy quyền Động (Dynamic Proxy) Dưới nền tảng

Tại thời điểm chạy, khi phản chiếu truy vấn một chú thích bằng các phương thức như `element.getAnnotation(MyAnnotation.class)`, JVM không trả về một thực thể trực tiếp của một lớp do trình biên dịch tạo ra. Thay vào đó, vì chú thích là giao diện, JVM sẽ tổng hợp động một lớp ủy quyền (Proxy class) để triển khai giao diện chú thích đó.

Dưới nền tảng, lớp ủy quyền này chuyển toàn bộ các phương thức truy cập thuộc tính (những phương thức trông giống như khai báo phương thức trong định nghĩa chú thích) đến một `InvocationHandler`, thường là `sun.reflect.annotation.AnnotationInvocationHandler` (trong các triển khai HotSpot/OpenJDK tiêu chuẩn). Bộ xử lý này ánh xạ tên phương thức tới các giá trị siêu dữ liệu đã được phân tích cú pháp trước đó và lưu trữ trong vùng lưu trữ hằng số (Constant pool) của lớp.

Thiết kế này cực kỳ hiệu quả: nó tránh được việc tạo, tải và xác thực một tệp lớp vật lý riêng biệt cho mọi khai báo chú thích tại thời điểm biên dịch, giúp tiết kiệm bộ nhớ và giữ cho quá trình tải lớp diễn ra nhanh chóng.

**Mô hình Tư duy:**
*Ví dụ thực tế:* Hãy tưởng tượng một thực đơn nhà hàng (giao diện chú thích). Khi bạn gọi món, người phục vụ (lớp ủy quyền động) sẽ ghi nhận yêu cầu của bạn. Người phục vụ không tự nấu ăn; họ tham khảo sách công thức nấu ăn (bản đồ siêu dữ liệu vùng lưu trữ hằng số đã được phân tích cú pháp) và mang ra món ăn đã chuẩn bị sẵn (giá trị siêu dữ liệu).

```
[Cuộc gọi Phản chiếu từ User] -> MyAnnotation.class.getAnnotation(...)
                                 |
                                 v
                [Lớp Ủy quyền do JVM Tổng hợp động]
             (ví dụ: $Proxy1 triển khai MyAnnotation)
                                 |
                                 v
                   [AnnotationInvocationHandler]
           (nắm giữ một Map<String, Object> chứa các giá trị)
                                 |
                                 v
             [Bản đồ Siêu dữ liệu Vùng hằng số đã Phân tích]
             (ví dụ: "value" -> 5, "message" -> "Hello")
```

**Ví dụ Code với Đầu ra Mong đợi:**
```java
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@interface RuntimeCheck {
    String value() default "Default";
}

@RuntimeCheck("ProxyTest")
class AnnotatedClass {}

public class ProxyDemo {
    public static void main(String[] args) {
        RuntimeCheck anno = AnnotatedClass.class.getAnnotation(RuntimeCheck.class);
        
        // Verify that the annotation instance is a Proxy
        System.out.println(java.lang.reflect.Proxy.isProxyClass(anno.getClass())); // true
        
        // Print the synthesized proxy class name
        System.out.println(anno.getClass().getName().contains("Proxy")); // true
        
        // Print the interfaces implemented by the proxy class
        for (Class<?> iface : anno.getClass().getInterfaces()) {
            System.out.println(iface.getSimpleName());
            // output:
            // RuntimeCheck
        }
    }
}
```

**Chuỗi Nguyên nhân - Kết quả:**
Ứng dụng gọi `element.getAnnotation(MyAnno.class)` &rarr; Phân hệ phản chiếu kiểm tra các thuộc tính vùng lưu trữ hằng số của tệp lớp &rarr; JVM phát hiện sự hiện diện của `MyAnno` &rarr; JVM gọi cơ chế tạo ủy quyền động để tạo ra một lớp triển khai `MyAnno` &rarr; Lớp ủy quyền chuyển các cuộc gọi phương thức tới `AnnotationInvocationHandler` &rarr; Bộ xử lý truy xuất các giá trị tương ứng từ bản đồ siêu dữ liệu đã được phân tích cú pháp trước đó &rarr; Mã máy khách nhận được giá trị của phần tử được chú thích.

---

## Ví Dụ Thực Tế: Custom Validation Annotation & Reflection Processor

Hãy cùng xây dựng một khung công tác xác thực dữ liệu thực tế bằng cách sử dụng một chú thích trường tùy chỉnh `@NonNull` và một bộ xác thực dựa trên phản chiếu để kiểm tra các trường đối tượng tại thời điểm chạy.

### 1. Khai báo Chú thích Tùy chỉnh
Chúng ta yêu cầu chú thích phải nhắm mục tiêu vào các trường dữ liệu (`ElementType.FIELD`) và hiển thị tại thời điểm chạy (`RetentionPolicy.RUNTIME`).

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NonNull {
    String message() default "Field value cannot be null";
}
```

### 2. Áp dụng Chú thích vào một Lớp Mô hình
Chúng ta sẽ áp dụng chú thích `@NonNull` cho một số trường riêng tư của lớp `User`.

```java
public class User {
    @NonNull(message = "Username is required and cannot be null")
    private String username;

    @NonNull(message = "Email address must not be null")
    private String email;

    private String phoneNumber; // Optional field, not annotated

    public User(String username, String email, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
```

### 3. Viết Bộ Xác thực Dựa trên Phản chiếu
Bộ xác thực sử dụng phản chiếu để kiểm tra tất cả các trường (ngay cả các trường riêng tư) của một đối tượng được cung cấp. Nếu một trường được chú thích bằng `@NonNull` và có giá trị là `null`, nó sẽ ném ra một ngoại lệ.

```java
import java.lang.reflect.Field;

public class ObjectValidator {
    
    public static void validate(Object obj) throws IllegalAccessException {
        if (obj == null) {
            throw new IllegalArgumentException("Cannot validate a null object");
        }
        
        Class<?> clazz = obj.getClass();
        
        // Loop through all fields declared in the class
        for (Field field : clazz.getDeclaredFields()) {
            // Check if the field is annotated with @NonNull
            if (field.isAnnotationPresent(NonNull.class)) {
                // Since fields might be private, make them accessible
                field.setAccessible(true);
                
                Object value = field.get(obj);
                
                // If value is null, read the message and throw exception
                if (value == null) {
                    NonNull annotation = field.getAnnotation(NonNull.class);
                    throw new IllegalArgumentException(
                        "Validation failed for field '" + field.getName() + "': " + annotation.message()
                    );
                }
            }
        }
    }
}
```

### 4. Chạy Ví Dụ Thực Tế
```java
public class ValidationApp {
    public static void main(String[] args) {
        User validUser = new User("alice", "alice@example.com", null);
        User invalidUser = new User("bob", null, "123-456");

        // 1. Validate Valid User
        try {
            ObjectValidator.validate(validUser);
            System.out.println("Valid user successfully verified.");
        } catch (IllegalAccessException | IllegalArgumentException e) {
            System.out.println("Unexpected failure: " + e.getMessage());
        }

        // 2. Validate Invalid User
        try {
            ObjectValidator.validate(invalidUser);
            System.out.println("Invalid user mistakenly verified.");
        } catch (IllegalAccessException | IllegalArgumentException e) {
            System.out.println("Caught expected validation error: " + e.getMessage());
        }
    }
}
```

---

## Các lỗi thường gặp

### 1. Chú thích Lặp lại: Sử dụng `getAnnotation()` thay vì `getAnnotationsByType()`
Nếu một chú thích có thể lặp lại (ví dụ: `@Schedule`) và nó được áp dụng nhiều lần cho một phần tử, công cụ phản chiếu của Java sẽ bọc chúng bên trong chú thích vùng chứa của chúng (ví dụ: `@Schedules`).
Việc gọi `element.getAnnotation(Schedule.class)` sẽ trả về `null` tại thời điểm chạy nếu có nhiều chú thích `@Schedule`.
Để truy xuất chính xác các chú thích lặp lại:
- Gọi `element.getAnnotationsByType(Schedule.class)` để tự động giải nén vùng chứa.
- Hoặc truy vấn chính vùng chứa đó: `element.getAnnotation(Schedules.class)`.

### 2. Giả định rằng `@Inherited` áp dụng cho các Giao diện
Một bẫy phỏng vấn phổ biến là giả định `@Inherited` hoạt động với triển khai giao diện. Nếu lớp `Child` triển khai giao diện `MyInterface`, và `MyInterface` được chú thích bằng một chú thích có đánh dấu `@Inherited`, `Child` sẽ **KHÔNG** kế thừa chú thích đó.

### 3. Kiểu Trả về Không Hợp lệ trong các Phần tử Chú thích
Cố gắng khai báo một phần tử trả về một lớp đối tượng bao bọc (chẳng hạn như `Integer` hoặc `Boolean`) hoặc cấu trúc mảng lồng nhau (chẳng hạn như `String[][]`) là một lỗi biên dịch. Chỉ cho phép các kiểu nguyên thủy, String, Class, enum, chú thích và mảng một chiều của các kiểu này.

---

## Các Câu Hỏi Ôn Tập Thường Gặp

- Những khái niệm nào ở đây là các quy tắc tại thời điểm biên dịch?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy?
- Những khái niệm nào ở đây dễ trở thành bẫy khi phỏng vấn?

## Liên kết Tham khảo

- [Java reflection API](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Proxy.html)
- [OpenJDK sun.reflect.annotation.AnnotationInvocationHandler Source](https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/sun/reflect/annotation/AnnotationInvocationHandler.java)
- [Java Language Specification: Annotations](https://docs.oracle.com/javase/specs/jls/se21/html/jls-9.html#jls-9.7)
