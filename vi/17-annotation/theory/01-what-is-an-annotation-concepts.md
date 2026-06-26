# Chú thích - Phần 1 (Annotation - Part 1)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm một phần nội dung trọng tâm về **Chú thích (Annotation)**. Hãy nghiên cứu từng khái niệm dưới dạng quy tắc thực tế trong Java, chứ không chỉ là từ vựng rời rạc.

## Các khái niệm bao phủ (Outline Coverage)

| Khái niệm | Những điều cần biết |
| --- | --- |
| `What is an annotation?` | Chú thích đính kèm siêu dữ liệu (metadata) vào các phần tử chương trình như lớp, phương thức hoặc trường dữ liệu. |
| `Built-in annotations:` | Chú thích đính kèm siêu dữ liệu vào các phần tử chương trình như lớp, phương thức hoặc trường dữ liệu. |
| `@Override` | @Override là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó. |
| `@Deprecated` | @Deprecated là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó. |
| `@SuppressWarnings` | @SuppressWarnings là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó. |
| `@FunctionalInterface` | @FunctionalInterface là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó. |
| `@SafeVarargs` | @SafeVarargs là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó. |
| `Meta-annotations:` | Chú thích đính kèm siêu dữ liệu vào các phần tử chương trình như lớp, phương thức hoặc trường dữ liệu. |
| `@Target` | @Target là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó. |
| `@Retention` | @Retention là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó. |

---

## Ghi chú chi tiết (Detailed Notes)

### Chú thích là gì? (What is an annotation?)

Một chú thích (annotation) đính kèm các siêu dữ liệu (metadata) vào các phần tử chương trình như lớp, phương thức hoặc trường dữ liệu.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và cơ chế phát hiện lỗi. Ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn định nghĩa.

Kiểm tra thực tế:

- Định nghĩa `What is an annotation?` trong một câu.
- Nhận biết `What is an annotation?` trong code, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `What is an annotation?`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hãy hỏi: `What is an annotation?` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java (Concrete Explanation & Java Rules)
Chú thích là một dạng siêu dữ liệu cú pháp có thể được thêm vào mã nguồn Java. Chúng khai báo một loại thẻ đánh dấu, sử dụng từ khóa `@interface`, bản thân nó không trực tiếp ảnh hưởng đến việc thực thi chương trình. Tuy nhiên, chúng có thể được xử lý:
1. Tại **thời điểm biên dịch (compile time)** bởi các trình cắm của trình biên dịch (Trình xử lý chú thích - Annotation Processors) để tạo mã nguồn, các tệp mô tả XML hoặc thực hiện các xác thực bổ sung.
2. Tại **thời điểm tải lớp / chạy chương trình (class-load / runtime)** thông qua Phản chiếu Java (Java Reflection) để cấu hình hành vi một cách động (ví dụ: các khung phát triển Spring, Hibernate).

```java
// Declaring a simple custom annotation
@interface MyMetadata {
    String value() default "No Description";
}

// Applying it to class, field, and method
@MyMetadata("Applied at class level")
public class AnnotationDemo {
    
    @MyMetadata("Applied at field level")
    private String name;

    @MyMetadata("Applied at method level")
    public void performAction() {}
}
```

---

### Chú thích tích hợp sẵn (Built-in annotations)

Một chú thích đính kèm siêu dữ liệu vào các phần tử chương trình như lớp, phương thức hoặc trường dữ liệu.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và cơ chế phát hiện lỗi. Ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn định nghĩa.

Kiểm tra thực tế:

- Định nghĩa `Built-in annotations:` trong một câu.
- Nhận biết `Built-in annotations:` trong code, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Built-in annotations:`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hãy hỏi: `Built-in annotations:` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java (Concrete Explanation & Java Rules)
Java cung cấp các chú thích tích hợp sẵn tiêu chuẩn. Những chú thích được định nghĩa trong `java.lang` (ví dụ: `@Override`, `@Deprecated`, `@SuppressWarnings`, `@SafeVarargs`, `@FunctionalInterface`) được sử dụng chủ yếu bởi trình biên dịch. Những chú thích được định nghĩa trong `java.lang.annotation` (ví dụ: `@Target`, `@Retention`, `@Documented`, `@Inherited`, `@Repeatable`) là các siêu chú thích (meta-annotations) được áp dụng trên các chú thích tùy chỉnh để định nghĩa hành vi của chúng.

```java
public class BuiltInDemo {
    @Override
    public String toString() {
        return "Built-in demo class";
    }
}
```

---

### @Override

@Override là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và cơ chế phát hiện lỗi. Ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn định nghĩa.

Kiểm tra thực tế:

- Định nghĩa `@Override` trong một câu.
- Nhận biết `@Override` trong code, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `@Override`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hãy hỏi: `@Override` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java (Concrete Explanation & Java Rules)
Chú thích `@Override` hướng dẫn trình biên dịch xác minh rằng phương thức được chú thích thực sự ghi đè hoặc triển khai một phương thức được khai báo trong một lớp cha hoặc giao diện cha. Nếu chữ ký phương thức không khớp chính xác (do lỗi chính tả, khác biệt kiểu tham số hoặc sai kiểu trả về), trình biên dịch sẽ báo lỗi biên dịch.

```java
class Base {
    public void execute(String value) {}
}

class Sub extends Base {
    // Correct usage
    @Override
    public void execute(String value) {
        System.out.println("Sub executed: " + value);
    }

    // Compilation error: Parent class does not have an "execut" method
    // @Override
    // public void execut(String value) {}

    // Compilation error: Parameter mismatch (int vs String)
    // @Override
    // public void execute(int value) {}
}
```

**Trường hợp lỗi & Bẫy thường gặp (Gotcha):**
Các phương thức `private` không thể bị ghi đè. Nếu một lớp con khai báo một phương thức có cùng tên và danh sách tham số như một phương thức `private` trong lớp cha, và đánh dấu nó bằng `@Override`, điều này sẽ gây ra lỗi biên dịch.

---

### @Deprecated

@Deprecated là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và cơ chế phát hiện lỗi. Ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn định nghĩa.

Kiểm tra thực tế:

- Định nghĩa `@Deprecated` trong một câu.
- Nhận biết `@Deprecated` trong code, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `@Deprecated`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hãy hỏi: `@Deprecated` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java (Concrete Explanation & Java Rules)
`@Deprecated` đánh dấu một phần tử chương trình (lớp, phương thức, trường dữ liệu, hàm khởi dựng) là đã lỗi thời. Nếu mã nguồn khác sử dụng một phần tử bị deprecated, trình biên dịch sẽ tạo ra một cảnh báo (warning). Kể từ Java 9, chú thích này bao gồm các phần tử cấu hình bổ sung:
- `since`: Một chuỗi `String` cho biết phiên bản mà phần tử đó bắt đầu bị coi là lỗi thời.
- `forRemoval`: Một biến `boolean` cho biết liệu phần tử đó có bị loại bỏ hoàn toàn trong một phiên bản tương lai hay không. Nếu là `true`, việc biên dịch mã nguồn có chứa phương thức này sẽ kích hoạt một cảnh báo "deprecated ở mức nghiêm trọng" (terminal deprecation).

```java
public class DeprecatedExample {
    @Deprecated(since = "2.0", forRemoval = true)
    public void oldAPIMethod() {
        System.out.println("This method is obsolete and will be removed.");
    }
}
```

---

### @SuppressWarnings

@SuppressWarnings là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và cơ chế phát hiện lỗi. Ôn tập bằng một ví dụ nhỏ thay hygiene chỉ ghi nhớ nhãn định nghĩa.

Kiểm tra thực tế:

- Định nghĩa `@SuppressWarnings` trong một câu.
- Nhận biết `@SuppressWarnings` trong code, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `@SuppressWarnings`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hãy hỏi: `@SuppressWarnings` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java (Concrete Explanation & Java Rules)
`@SuppressWarnings` ngăn chặn các cảnh báo cụ thể của trình biên dịch trong phần tử được chú thích và các phần tử con của nó. Nó có một phần tử duy nhất thuộc kiểu `String[]` (mảng chứa tên các cảnh báo cần ngăn chặn, chẳng hạn như `"unchecked"`, `"deprecation"`, `"rawtypes"`, hoặc `"all"`).

```java
import java.util.ArrayList;
import java.util.List;

public class SuppressExample {
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void processLegacyData() {
        List rawList = new ArrayList(); // rawtypes warning suppressed
        rawList.add("Hello");           // unchecked warning suppressed
    }
}
```

---

### @FunctionalInterface

@FunctionalInterface là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó.

Nó quan trọng bởi vì các API Java hiện đại sử dụng rất nhiều các đường dẫn xử lý kiểu hàm (function-style pipelines). Một hiểu lầm phổ biến là quên mất thao tác nào là lười biếng (lazy) và thao tác nào thực sự kích hoạt việc thực thi.

Kiểm tra thực tế:

- Định nghĩa `@FunctionalInterface` trong một câu.
- Nhận biết `@FunctionalInterface` trong code, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `@FunctionalInterface`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hãy hỏi: `@FunctionalInterface` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java (Concrete Explanation & Java Rules)
`@FunctionalInterface` là một chú thích thông tin được sử dụng để khai báo rằng một giao diện được thiết kế để trở thành một giao diện chức năng (chỉ có duy nhất một phương thức trừu tượng). Nếu giao diện chứa không có hoặc có nhiều hơn một phương thức trừu tượng, trình biên dịch sẽ báo lỗi. Lưu ý rằng các phương thức mặc định (default methods), phương thức tĩnh (static methods) và các phương thức public ghi đè các phương thức của lớp `java.lang.Object` sẽ không được tính vào giới hạn duy nhất một phương thức này.

```java
@FunctionalInterface
public interface MathOperation {
    int operate(int a, int b); // The single abstract method
    
    // Allowed: Default methods
    default void printName() {
        System.out.println("Operation");
    }

    // Allowed: Static methods
    static void log() {
        System.out.println("Logging...");
    }

    // Allowed: Public method overriding Object class
    @Override
    boolean equals(Object obj);
}
```

---

### @SafeVarargs

@SafeVarargs là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và cơ chế phát hiện lỗi. Ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn định nghĩa.

Kiểm tra thực tế:

- Định nghĩa `@SafeVarargs` trong một câu.
- Nhận biết `@SafeVarargs` trong code, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `@SafeVarargs`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hãy hỏi: `@SafeVarargs` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java (Concrete Explanation & Java Rules)
`@SafeVarargs` ngăn chặn các cảnh báo về "ô nhiễm bộ nhớ heap tiềm ẩn" (potential heap pollution) khi sử dụng các tham số varargs generic. Varargs trong Java được triển khai bằng cách sử dụng các mảng, vốn không giữ lại thông tin kiểu generic tại thời điểm chạy (cơ chế reification).
Bởi vì điều này, việc viết `T...` khiến phương thức có nguy cơ xảy ra lỗi ép kiểu (class cast exceptions) nếu một kiểu mảng không tương thích được truyền vào.

`@SafeVarargs` đại diện cho một lời hứa của lập trình viên rằng thân phương thức sẽ chỉ đọc dữ liệu từ mảng varargs và không ghi các đối tượng thuộc kiểu không tương thích vào đó (vốn là nguồn gốc gây ô nhiễm bộ nhớ heap).

**Ràng buộc quan trọng:**
Chỉ có thể được áp dụng cho:
1. Phương thức tĩnh (static methods)
2. Phương thức thể hiện final (final instance methods)
3. Phương thức thể hiện private (private instance methods) (kể từ Java 9+)

Nó **không thể** được áp dụng cho các phương thức thể hiện không final, không private vì các ghi đè ở lớp con có thể đưa vào các thao tác ghi không an toàn.

```java
public class SafeVarargsDemo {
    @SafeVarargs
    public static <T> java.util.List<T> safeList(T... elements) {
        // Safe: we are only reading elements, not modifying the array
        java.util.List<T> list = new java.util.ArrayList<>();
        for (T element : elements) {
            list.add(element);
        }
        return list;
    }
}
```

---

### Siêu chú thích (Meta-annotations)

Chú thích đính kèm siêu dữ liệu vào các phần tử chương trình như lớp, phương thức hoặc trường dữ liệu.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và cơ chế phát hiện lỗi. Ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn định nghĩa.

Kiểm tra thực tế:

- Định nghĩa `Meta-annotations:` trong một câu.
- Nhận biết `Meta-annotations:` trong code, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `Meta-annotations:`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hãy hỏi: `Meta-annotations:` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java (Concrete Explanation & Java Rules)
Siêu chú thích (meta-annotations) là các chú thích được áp dụng cho các khai báo chú thích khác. Chúng chỉ định cách chú thích tùy chỉnh đó hoạt động (ví dụ: nơi nó được phép áp dụng, nó được lưu giữ bao lâu trong mã biên dịch, nó có được kế thừa hay không, v.v.).

---

### @Target

@Target là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và cơ chế phát hiện lỗi. Ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn định nghĩa.

Kiểm tra thực tế:

- Định nghĩa `@Target` trong một câu.
- Nhận biết `@Target` trong code, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `@Target`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hãy hỏi: `@Target` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java (Concrete Explanation & Java Rules)
`@Target` chỉ định các ngữ cảnh (phần tử chương trình) nơi chú thích có thể được áp dụng. Nó nhận vào một mảng các giá trị `ElementType`, bao gồm:
- `TYPE`: Lớp, giao diện (bao gồm kiểu chú thích), record, hoặc enum
- `FIELD`: Trường dữ liệu (bao gồm các hằng số enum)
- `METHOD`: Phương thức
- `PARAMETER`: Tham số hình thức
- `CONSTRUCTOR`: Hàm khởi dựng
- `LOCAL_VARIABLE`: Biến cục bộ
- `ANNOTATION_TYPE`: Kiểu chú thích
- `TYPE_USE`: Bất kỳ việc sử dụng một kiểu dữ liệu nào (kể từ Java 8+)

Nếu `@Target` không được khai báo, chú thích có thể được áp dụng cho bất kỳ ngữ cảnh khai báo nào (nhưng không áp dụng cho ngữ cảnh sử dụng kiểu dữ liệu - type use contexts).

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

// This custom annotation can ONLY be applied to methods and fields.
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface MethodAndFieldOnly {
    String value() default "";
}
```

#### Tại sao @Target tồn tại: Giới hạn phạm vi và Ngăn ngừa lạm dụng (Why @Target Exists: Restricting Scope and Preventing Misuse)

Chú thích giúp đính kèm siêu dữ liệu vào các phần tử code. Nếu không có `@Target`, một chú thích có thể được đặt trên bất kỳ ngữ cảnh khai báo nào, bao gồm lớp, phương thức, tham số, biến cục bộ và hàm khởi dựng. Việc thiếu hạn chế này có thể dẫn đến sự bừa bãi về mặt cấu trúc, mơ hồ về mặt ngữ nghĩa và những giả định không an toàn trong các thư viện xử lý code. Bằng cách định nghĩa `@Target`, các nhà thiết kế ngôn ngữ cho phép nhà phát triển giới hạn khả năng áp dụng của một chú thích vào những vị trí cụ thể mà nó hợp lệ và được mong đợi. Ví dụ, một chú thích xác thực như `@NonNull` chỉ có ý nghĩa trên các trường dữ liệu, tham số phương thức hoặc giá trị trả về, trong khi một chú thích ánh xạ như `@RequestMapping` chỉ có ý nghĩa trên các phương thức hoặc các lớp. Việc giới hạn mục tiêu giúp ngăn nhà phát triển sử dụng chú thích trong các ngữ cảnh mà logic xử lý không hỗ trợ, tránh các lỗi lúc chạy hoặc sai sót logic.

**Mô hình tư duy:**
*Ẩn dụ:* Một tấm biển báo "Xin đừng làm phiền" được thiết kế cho các cánh cửa. Việc treo nó lên bàn phím, cốc cà phê hoặc đầu của một ai đó là điều vô lý và gây bối rối. `@Target` hoạt động như các hướng dẫn chỉ rõ chính xác nơi tấm biển đó có thể được treo.

```mermaid
flowchart TD
    A[Chú thích được khai báo] --> B{Có chú thích @Target không?}
    B -- Không --> C[Được phép trên bất kỳ ngữ cảnh khai báo nào (lớp, phương thức, trường, v.v.)]
    B -- Có --> D[CHỈ được phép trên các ngữ cảnh ElementType được chỉ định]
    E[Kiểm tra thời điểm biên dịch] --> F{Ngữ cảnh áp dụng có nằm trong @Target?}
    F -- Có --> G[Biên dịch thành công]
    F -- Không --> H[Biên dịch thất bại: kiểu chú thích không áp dụng được cho kiểu khai báo này]
```

#### Ví dụ Code với Kết quả mong đợi (Code Example with Expected Output)
```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@interface MethodOnly {}

public class TargetDemo {
    // Compilation Error: annotation type not applicable to this kind of declaration
    // @MethodOnly
    private String field;

    @MethodOnly
    public void execute() {
        // OK: applied to method
    }
}
```

#### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)

```text
`@Target(ElementType.METHOD)` được định nghĩa trên `@MethodOnly`
  → Nhà phát triển cố gắng viết `@MethodOnly` trên khai báo một trường dữ liệu
  → Trình biên dịch Java kiểm tra định nghĩa của `@MethodOnly`
  → Trình biên dịch phát hiện ra rằng `ElementType.FIELD` không có sẵn trong các mục tiêu được phép
  → Trình biên dịch hủy bỏ quá trình xây dựng và ném ra lỗi "annotation type not applicable to this kind of declaration".
```


---

### @Retention

@Retention là một khái niệm cụ thể trong Chú thích; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép và cơ chế phát hiện lỗi. Ôn tập bằng một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn định nghĩa.

Kiểm tra thực tế:

- Định nghĩa `@Retention` trong một câu.
- Nhận biết `@Retention` trong code, câu lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc sự đánh đổi liên quan đến `@Retention`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc code, hãy hỏi: `@Retention` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

#### Giải thích cụ thể & Quy tắc Java (Concrete Explanation & Java Rules)
`@Retention` định nghĩa thời gian một chú thích được lưu giữ. Nó nhận vào một giá trị enum `RetentionPolicy`:
1. `RetentionPolicy.SOURCE`: Chỉ được duy trì trong tệp nguồn. Bị trình biên dịch bỏ qua và không xuất hiện trong tệp `.class` được biên dịch. (Được dùng cho phân tích code tĩnh, như thư viện Lombok hoặc `@Override`).
2. `RetentionPolicy.CLASS`: Được ghi lại trong tệp `.class` bởi trình biên dịch, nhưng KHÔNG được tải vào JVM lúc chạy. **Đây là chính sách lưu giữ mặc định nếu không được chỉ định.**
3. `RetentionPolicy.RUNTIME`: Được ghi lại trong tệp `.class` và được tải vào JVM. Những chú thích này có thể hiển thị và truy cập được lúc chạy thông qua API phản chiếu của Java.

```java
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface VisibleAtRuntime {
    String message();
}
```

#### Tại sao @Retention tồn tại và Sự khác biệt giữa các Chính sách lưu trữ (Why @Retention Exists and How Retention Policies Differ)

Các chú thích tồn tại trong các giai đoạn khác nhau của vòng đời chương trình: mã nguồn, tệp class, và bộ nhớ thực thi. Nếu không có `@Retention`, Java mặc định sử dụng `RetentionPolicy.CLASS`, cơ chế này sẽ loại bỏ các siêu dữ liệu chú thích khi trình tải lớp tải lớp vào bộ nhớ heap của JVM. Việc khai báo một chính sách lưu giữ rõ ràng cho phép nhà phát triển cân bằng giữa tính khả dụng của thông tin với chi phí bộ nhớ và hiệu năng.

*   `RetentionPolicy.SOURCE` lý tưởng cho các kiểm tra biên dịch bằng công cụ (như `@Override`) hoặc các trình tạo code thời điểm biên dịch (như Project Lombok), giúp ngăn các siêu dữ liệu chỉ dùng khi biên dịch gây nặng cho các tệp `.class` thành phẩm.
*   `RetentionPolicy.CLASS` hữu ích cho các công cụ phân tích mã byte, các chương trình kiểm tra cú pháp (linters), hoặc các trình biên dịch xử lý lớp một cách tĩnh mà không cần tải chúng vào bộ nhớ heap của JVM. Nó được ghi trong tệp `.class` nhưng bị loại bỏ khi chạy.
*   `RetentionPolicy.RUNTIME` giữ nguyên vẹn các siêu dữ liệu bên trong bộ nhớ heap của JVM, cho phép các khung phát triển dựa trên phản chiếu (như Spring, Hibernate, hoặc Jackson) có thể truy vấn các chú thích và điều chỉnh hành vi chương trình một cách linh hoạt trong quá trình thực thi.

**Mô hình tư duy:**
*Ẩn dụ:*
- `SOURCE`: Một bản thiết kế giàn giáo được sử dụng để xây dựng một tòa nhà nhưng bị dỡ bỏ sau khi tòa nhà hoàn thành.
- `CLASS`: Một cuốn hướng dẫn xây dựng được gửi kèm theo vật liệu xây dựng nhưng không bao giờ được mở ra một khi tòa nhà đã có người vào ở.
- `RUNTIME`: Một nhãn dán vật lý trên hộp điện của tòa nhà luôn hiển thị và có thể đọc được bởi nhân viên bảo trì bất kỳ lúc nào trong suốt thời gian tồn tại của tòa nhà.

```
+------------------+                   +--------------------+                   +-------------------+
|     Mã nguồn     | --[ Biên dịch ]-> |     Tệp .class     | --[ Tải lớp ]----> |  Bộ nhớ Heap JVM  |
|  (MyClass.java)  |                   |   (MyClass.class)  |                   | (Bộ nhớ Runtime)  |
+------------------+                   +--------------------+                   +-------------------+
      |                                          |                                        |
      | Các chú thích SOURCE                     | Các chú thích CLASS                    | Các chú thích RUNTIME
      v (bị loại bỏ bởi trình biên dịch)         v (bị bỏ qua bởi trình tải lớp)          v (truy cập qua phản chiếu)
   [Kiểm tra khi biên dịch]                   [Công cụ mã byte tĩnh]                   [Framework động]
```

#### Ví dụ Code với Kết quả mong đợi (Code Example with Expected Output)
```java
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@interface SourceAnno {}

@Retention(RetentionPolicy.CLASS)
@interface ClassAnno {}

@Retention(RetentionPolicy.RUNTIME)
@interface RuntimeAnno {}

@SourceAnno
@ClassAnno
@RuntimeAnno
class RetentionTest {}

public class RetentionDemo {
    public static void main(String[] args) {
        Class<?> clazz = RetentionTest.class;
        System.out.println(clazz.isAnnotationPresent(SourceAnno.class));  // false
        System.out.println(clazz.isAnnotationPresent(ClassAnno.class));   // false
        System.out.println(clazz.isAnnotationPresent(RuntimeAnno.class)); // true
    }
}
```

#### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)

```text
`@Retention(RetentionPolicy.SOURCE)`
  → Trình biên dịch xử lý mã nguồn Java
  → Trình biên dịch loại bỏ các chú thích khớp với chính sách SOURCE
  → Mã byte được tạo ra không có các chú thích này
  → Trình tải lớp JVM phân tích tệp `.class`
  → Lời gọi phản chiếu `clazz.isAnnotationPresent()` trả về `false`.
```


---

## Các lỗi thường gặp (Common Mistakes)

### 1. Quên thiết lập @Retention(RetentionPolicy.RUNTIME) (Forgetting to set @Retention(RetentionPolicy.RUNTIME))
Theo mặc định, các chú thích tùy chỉnh sử dụng chính sách `RetentionPolicy.CLASS`. Các nhà phát triển viết các thư viện dựa trên phản chiếu (như xác thực tùy chỉnh, tuần tự hóa, tiêm phụ thuộc) thường quên viết rõ ràng `@Retention(RetentionPolicy.RUNTIME)`. Kết quả là, việc gọi `element.isAnnotationPresent(MyAnnotation.class)` trả về `false` lúc chạy, gây ra các lỗi cấu hình âm thầm.

### 2. Ghi đè một phương thức private với @Override (Overriding a private method with @Override)
Nhà phát triển đôi khi khai báo một phương thức trong lớp con có cùng chữ ký với một phương thức `private` trong lớp cha và đánh dấu nó bằng `@Override`. Các phương thức private không hiển thị đối với lớp con, vì vậy đây không phải là ghi đè. Nó chỉ là định nghĩa phương thức mới, và việc áp dụng `@Override` sẽ dẫn đến lỗi biên dịch.

### 3. Áp dụng sai @SafeVarargs (Misapplying @SafeVarargs)
Đặt `@SafeVarargs` trên một phương thức có hành vi sửa đổi nội dung của mảng varargs (ví dụ: gán các phần tử mới vào mảng đó) là một sai lầm. Chú thích này tắt các cảnh báo của trình biên dịch, nhưng nó KHÔNG ngăn chặn lỗi `ClassCastException` lúc chạy do ô nhiễm bộ nhớ heap nếu phía gọi truyền vào một mảng thuộc kiểu lớp chạy thực tế khác.

---

## Các câu hỏi ôn tập phổ biến (Common Review Prompts)

- Khái niệm nào ở đây là các quy tắc tại thời điểm biên dịch?
- Khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy?
- Khái niệm nào ở đây dễ là những bẫy câu hỏi phỏng vấn?

## Reference Links

- [Java Tutorials: Annotations](https://docs.oracle.com/javase/tutorial/java/annotations/)
- [Java Language Specification: Annotation Types](https://docs.oracle.com/javase/specs/jls/se21/html/jls-9.html#jls-9.6)
- [Java Language Specification: Retention Policy](https://docs.oracle.com/javase/specs/jls/se21/html/jls-9.html#jls-9.6.4.2)
- [Java Language Specification: Target](https://docs.oracle.com/javase/specs/jls/se21/html/jls-9.html#jls-9.6.4.1)
