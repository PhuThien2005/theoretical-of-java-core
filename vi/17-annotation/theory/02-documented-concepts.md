# Chú thích - Phần 2 (Annotation - Part 2)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm một phần nội dung trọng tâm về **Chú thích (Annotation)**. Hãy nghiên cứu từng khái niệm dưới dạng quy tắc thực tế trong Java, chứ không chỉ là từ vựng rời rạc.

## Các khái niệm bao phủ (Outline Coverage)

| Khái niệm | Những điều cần biết |
| --- | --- |
| `@Documented` | @Documented là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó. |
| `@Inherited` | @Inherited là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó. |
| `@Repeatable` | @Repeatable là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó. |
| `Custom annotation` | Chú thích đính kèm siêu dữ liệu (metadata) vào các phần tử chương trình như lớp, phương thức hoặc trường dữ liệu. |
| `Runtime annotation` | Chú thích đính kèm siêu dữ liệu vào các phần tử chương trình như lớp, phương thức hoặc trường dữ liệu. |
| `Basic annotation processing` | Chú thích đính kèm siêu dữ liệu vào các phần tử chương trình như lớp, phương thức hoặc trường dữ liệu. |

---

## Ghi chú chi tiết (Detailed Notes)

### @Documented

@Documented là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và cơ chế phát hiện lỗi. Ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn định nghĩa.

Kiểm tra thực tế:

- Định nghĩa `@Documented` trong một câu.
- Nhận biết `@Documented` trong code, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `@Documented`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hãy hỏi: `@Documented` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java (Concrete Explanation & Java Rules)
Theo mặc định, các chú thích được áp dụng cho một lớp, phương thức hoặc trường dữ liệu sẽ KHÔNG xuất hiện trong tài liệu Javadoc được tạo ra. Khi một khai báo chú thích tùy chỉnh được đánh dấu bằng siêu chú thích `@Documented`, bất kỳ phần tử mã nguồn nào được áp dụng chú thích tùy chỉnh này sẽ hiển thị chú thích đó trong tài liệu API Javadoc được tạo tương ứng.

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

@Inherited là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và cơ chế phát hiện lỗi. Ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn định nghĩa.

Kiểm tra thực tế:

- Định nghĩa `@Inherited` trong một câu.
- Nhận biết `@Inherited` trong code, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `@Inherited`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hãy hỏi: `@Inherited` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java (Concrete Explanation & Java Rules)
`@Inherited` chỉ ra rằng một chú thích đặt trên một lớp sẽ được tự động kế thừa bởi các lớp con của nó. Nếu lớp `Child` kế thừa `Parent`, và `Parent` có một chú thích được đánh dấu `@Inherited`, các truy vấn phản chiếu cho chú thích đó trên lớp `Child` vẫn sẽ thành công ngay cả khi lớp `Child` không khai báo chú thích đó một cách trực tiếp.

**Hạn chế cực kỳ quan trọng:**
1. Nó **chỉ** áp dụng cho kế thừa lớp (class inheritance). Nó KHÔNG hoạt động nếu một lớp triển khai một giao diện (interface) có chú thích được đánh dấu `@Inherited`.
2. Nó KHÔNG áp dụng cho các phương thức, trường dữ liệu hoặc hàm khởi dựng. Nếu một phương thức của lớp cha có một chú thích kế thừa, một lớp con ghi đè phương thức đó sẽ không kế thừa chú thích cấp phương thức đó.

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

@Repeatable là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và cơ chế phát hiện lỗi. Ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn định nghĩa.

Kiểm tra thực tế:

- Định nghĩa `@Repeatable` trong một câu.
- Nhận biết `@Repeatable` trong code, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `@Repeatable`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hãy hỏi: `@Repeatable` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java (Concrete Explanation & Java Rules)
Được giới thiệu từ Java 8, `@Repeatable` cho phép áp dụng cùng một chú thích nhiều lần trên cùng một phần tử chương trình. Nó yêu cầu một kiểu "chú thích chứa" (container annotation), kiểu này phải có một phương thức `value()` trả về một mảng của kiểu chú thích có thể lặp lại đó. Chú thích chứa phải có chính sách lưu trữ (retention policy) dài hạn tối thiểu bằng với chú thích lặp lại.

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

### Chú thích tùy chỉnh (Custom annotation)

Một chú thích đính kèm siêu dữ liệu vào các phần tử chương trình như lớp, phương thức hoặc trường dữ liệu.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và cơ chế phát hiện lỗi. Ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn định nghĩa.

Kiểm tra thực tế:

- Định nghĩa `Custom annotation` trong một câu.
- Nhận biết `Custom annotation` trong code, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Custom annotation`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hãy hỏi: `Custom annotation` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java (Concrete Explanation & Java Rules)
Các chú thích tùy chỉnh được khai báo bằng cú pháp `@interface`. Chúng ngầm định kế thừa interface `java.lang.annotation.Annotation`. Các phần tử của chúng được định nghĩa giống như các phương thức không tham số và có thể chỉ định các giá trị mặc định bằng cách sử dụng từ khóa `default`.

**Các kiểu dữ liệu trả về hợp lệ cho phần tử:**
- Tất cả các kiểu dữ liệu nguyên thủy (`int`, `char`, `double`, `boolean`, v.v.)
- Lớp `java.lang.String`
- Lớp `java.lang.Class` (có thể tham số hóa tùy chọn)
- Bất kỳ kiểu `enum` nào
- Bất kỳ kiểu chú thích (annotation) nào
- Các mảng một chiều thuộc bất kỳ kiểu dữ liệu nào ở trên (ví dụ: `String[]`, `int[]`, `MyEnum[]`)

**Các kiểu dữ liệu trả về không hợp lệ:**
- Các mảng đa chiều hoặc lồng nhau (ví dụ: `String[][]`, `int[][]`)
- Các lớp bao bọc đối tượng (ví dụ: `Integer`, `Double`, `Boolean`)
- Các lớp tùy ý khác (ví dụ: `java.util.Date`, `java.util.List`)
- Các tham số kiểu (kiểu generic)

```java
public @interface Configuration {
    String name();                    // Required element
    int poolSize() default 10;        // Element with a default value
    Class<?> driverClass() default Object.class; // Class element
    String[] options() default {};    // Array element
}
```

---

### Chú thích Runtime (Runtime annotation)

Một chú thích đính kèm siêu dữ liệu vào các phần tử chương trình như lớp, phương thức hoặc trường dữ liệu.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và cơ chế phát hiện lỗi. Ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn định nghĩa.

Kiểm tra thực tế:

- Định nghĩa `Runtime annotation` trong một câu.
- Nhận biết `Runtime annotation` trong code, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Runtime annotation`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hãy hỏi: `Runtime annotation` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java (Concrete Explanation & Java Rules)
Các chú thích runtime được khai báo với `@Retention(RetentionPolicy.RUNTIME)`. JVM giữ siêu dữ liệu của chúng trong bộ nhớ, cho phép chúng có thể được kiểm tra một cách động thông qua API Phản chiếu (Reflection).

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

Một chú thích đính kèm siêu dữ liệu vào các phần tử chương trình như lớp, phương thức hoặc trường dữ liệu.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và cơ chế phát hiện lỗi. Ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn định nghĩa.

Kiểm tra thực tế:

- Định nghĩa `Basic annotation processing` trong một câu.
- Nhận biết `Basic annotation processing` trong code, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Basic annotation processing`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hãy hỏi: `Basic annotation processing` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java (Concrete Explanation & Java Rules)
Việc xử lý chú thích có thể diễn ra tại thời điểm biên dịch (sử dụng API `Processor` tiêu chuẩn trong gói `javax.annotation.processing`) hoặc tại thời điểm chạy (sử dụng API Phản chiếu).
Tại thời điểm chạy, các lớp như `Class`, `Method`, `Field`, `Constructor`, và `Parameter` đều triển khai giao diện `AnnotatedElement`, cung cấp các phương thức phản chiếu để đọc siêu dữ liệu chú thích:
- `isAnnotationPresent(Class<? extends Annotation> annotationClass)`: Trả về true nếu phần tử có chú thích được chỉ định.
- `getAnnotation(Class<T> annotationClass)`: Trả về chú thích của kiểu được chỉ định nếu có, ngược lại trả về null.
- `getAnnotations()`: Trả về tất cả các chú thích xuất hiện trên phần tử đó (bao gồm cả các chú thích được kế thừa).
- `getDeclaredAnnotations()`: Trả về các chú thích được khai báo trực tiếp trên phần tử đó (không bao gồm các chú thích kế thừa).

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

#### Tại sao xử lý chú thích lúc chạy sử dụng các Dynamic Proxy ở bên dưới mui xe (Why Runtime Annotation Processing Uses Dynamic Proxies Under the Hood)

Lúc chạy, khi cơ chế phản chiếu truy vấn một chú thích bằng cách sử dụng các phương thức như `element.getAnnotation(MyAnnotation.class)`, JVM không trả về một thực thể trực tiếp của một lớp do trình biên dịch tạo ra. Thay vào đó, vì các chú thích bản chất là các giao diện (interfaces), JVM sẽ tổng hợp động một lớp proxy triển khai giao diện chú thích đó.

Bên dưới mui xe, lớp proxy này ủy quyền tất cả các phương thức truy cập phần tử (vốn trông giống như các phương thức trong khai báo chú thích) cho một `InvocationHandler`, thường là lớp `sun.reflect.annotation.AnnotationInvocationHandler` (trong các triển khai HotSpot/OpenJDK tiêu chuẩn). Trình xử lý (handler) này thực hiện ánh xạ các tên phương thức với các giá trị siêu dữ liệu đã được phân tích cú pháp trước và lưu trữ trong vùng constant pool của lớp.

Thiết kế này cực kỳ hiệu quả: nó tránh được việc phải tạo, tải và xác thực một tệp class vật lý riêng biệt cho mỗi khai báo chú thích lúc biên dịch, giúp tiết kiệm bộ nhớ và duy trì tốc độ tải lớp nhanh chóng.

**Mô hình tư duy:**
*Ẩn dụ:* Hãy tưởng tượng một thực đơn nhà hàng (giao diện chú thích). Khi bạn gọi món, người phục vụ (dynamic proxy) sẽ nhận order của bạn. Người phục vụ không tự mình nấu món ăn; họ tham khảo một cuốn sách công thức (bản đồ siêu dữ liệu đã phân tích trong constant-pool) và mang món ăn đã chế biến sẵn ra (giá trị siêu dữ liệu).

```
[Lời gọi phản chiếu của người dùng] -> MyAnnotation.class.getAnnotation(...)
                                              |
                                              v
                                [Lớp Proxy được JVM tổng hợp]
                            (Ví dụ: $Proxy1 triển khai MyAnnotation)
                                              |
                                              v
                                [AnnotationInvocationHandler]
                           (chứa một Map<String, Object> của các giá trị)
                                              |
                                              v
                             [Bản đồ siêu dữ liệu đã được phân tích từ Constant-Pool]
                             (Ví dụ: "value" -> 5, "message" -> "Hello")
```

#### Ví dụ Code với Kết quả mong đợi (Code Example with Expected Output)
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

#### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)

```text
Ứng dụng gọi `element.getAnnotation(MyAnno.class)`
  → Phân hệ phản chiếu kiểm tra các thuộc tính constant pool của tệp class
  → JVM phát hiện sự hiện diện của `MyAnno`
  → JVM kích hoạt quá trình tạo dynamic proxy để sản sinh một lớp triển khai `MyAnno`
  → Proxy ủy quyền các cuộc gọi phương thức cho `AnnotationInvocationHandler`
  → Trình xử lý truy xuất các giá trị tương ứng từ bản đồ siêu dữ liệu đã được phân tích trước
  → Phía gọi nhận được giá trị của phần tử chú thích.
```


---

## Case Study: Chú thích Xác thực Tùy chỉnh & Bộ xử lý Phản chiếu (Case Study: Custom Validation Annotation & Reflection Processor)

Hãy cùng xây dựng một khung kiểm tra xác thực thực tế sử dụng chú thích trường tùy chỉnh `@NonNull` và một bộ xác thực dựa trên phản chiếu giúp kiểm tra các trường của đối tượng lúc chạy.

### 1. Khai báo chú thích tùy chỉnh (1. Declaring the Custom Annotation)
Chúng ta yêu cầu chú thích phải nhắm mục tiêu vào các trường dữ liệu (`ElementType.FIELD`) và hiển thị được lúc chạy (`RetentionPolicy.RUNTIME`).

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

### 2. Áp dụng chú thích lên một Lớp Mô hình (2. Applying the Annotation to a Model Class)
Chúng ta sẽ áp dụng `@NonNull` cho một số trường private của một lớp `User`.

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

### 3. Viết Bộ xác thực dựa trên Phản chiếu (3. Writing the Reflection-based Validator)
Bộ xác thực sử dụng phản chiếu để kiểm tra tất cả các trường (ngay cả các trường private) của một đối tượng được truyền vào. Nếu một trường được chú thích `@NonNull` và có giá trị là `null`, nó sẽ ném ra một ngoại lệ.

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

### 4. Chạy Case Study (4. Running the Case Study)
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

## Các lỗi thường gặp (Common Mistakes)

### 1. Các chú thích lặp lại: Sử dụng getAnnotation() thay vì getAnnotationsByType() (Repeatable Annotations: Using getAnnotation() instead of getAnnotationsByType())
Nếu một chú thích có thể lặp lại (ví dụ: `@Schedule`), và nó được áp dụng nhiều lần trên một phần tử, công cụ phản chiếu của Java sẽ bọc chúng bên trong chú thích chứa tương ứng của chúng (ví dụ: `@Schedules`).
Việc gọi `element.getAnnotation(Schedule.class)` sẽ trả về `null` lúc chạy nếu có nhiều chú thích `@Schedule` xuất hiện.
Để lấy chính xác các chú thích lặp lại:
- Gọi `element.getAnnotationsByType(Schedule.class)`, phương thức này sẽ tự động giải nén chú thích chứa ra.
- Hoặc truy vấn trực tiếp chú thích chứa: `element.getAnnotation(Schedules.class)`.

### 2. Giả định rằng @Inherited áp dụng cho các Giao diện (Assuming @Inherited applies to Interfaces)
Một bẫy phỏng vấn phổ biến là giả định rằng `@Inherited` hoạt động với triển khai giao diện. Nếu lớp `Child` triển khai giao diện `MyInterface`, và `MyInterface` được đánh dấu bằng một chú thích có `@Inherited`, lớp `Child` **KHÔNG** kế thừa chú thích đó.

### 3. Các kiểu trả về không hợp lệ trong phần tử của Chú thích (Invalid Return Types in Annotation Elements)
Cố gắng khai báo một phần tử trả về một lớp đối tượng bao bọc (chẳng hạn như `Integer` hoặc `Boolean`) hoặc các cấu trúc mảng lồng nhau (chẳng hạn như `String[][]`) là một lỗi biên dịch. Chỉ các kiểu dữ liệu nguyên thủy, String, Class, enum, chú thích và mảng 1 chiều của các kiểu này mới được cho phép.

---

## Các câu hỏi ôn tập phổ biến (Common Review Prompts)

- Khái niệm nào ở đây là các quy tắc tại thời điểm biên dịch?
- Khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy?
- Khái niệm nào ở đây dễ là những bẫy câu hỏi phỏng vấn?

## Reference Links

- [Java reflection API](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Proxy.html)
- [OpenJDK sun.reflect.annotation.AnnotationInvocationHandler Source](https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/sun/reflect/annotation/AnnotationInvocationHandler.java)
- [Java Language Specification: Annotations](https://docs.oracle.com/javase/specs/jls/se21/html/jls-9.html#jls-9.7)
