# Cơ Chế Phản Chiếu - Phần 1 (Reflection - Part 1)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này trình bày một phần trọng tâm về **Cơ Chế Phản Chiếu (Reflection)** trong Java. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, tránh việc ghi nhớ từ vựng một cách máy móc.

## Khung Nội Dung (Outline Coverage)

| Khái niệm (Concept) | Nội dung cần biết (What to know) |
| --- | --- |
| `What is Reflection?` | Reflection cho phép chương trình kiểm tra và thao tác trên các lớp, các trường, các phương thức và các hàm khởi tạo tại thời điểm chạy (runtime). |
| `Class<?>` | Lớp đại diện cho siêu dữ liệu (metadata) của một lớp hoặc giao diện Java đã được nạp vào máy ảo JVM. |
| `Get class information` | Truy vấn các bổ từ truy cập, tên gói, lớp cha và các giao diện được triển khai tại thời điểm chạy. |
| `Get field` | Lấy các đối tượng Field công khai hoặc được khai báo đại diện cho các trường của lớp. |
| `Get method` | Lấy các đối tượng Method công khai hoặc được khai báo để kiểm tra chữ ký phương thức. |
| `Get constructor` | Lấy các đối tượng Constructor công khai hoặc được khai báo để kiểm tra các hàm khởi tạo. |
| `Invoke method using reflection` | Thực thi một phương thức một cách động tại thời điểm chạy thông qua reflection. |
| `Create object using reflection` | Khởi tạo thực thể của lớp một cách động bằng cách sử dụng các hàm khởi tạo qua reflection. |
| `Access private field/method` | Bỏ qua các kiểm tra bảo vệ quyền truy cập của ngôn ngữ để đọc/ghi các trường private hoặc gọi các phương thức private. |
| `Annotation + reflection` | Truy vấn các siêu dữ liệu chú thích (annotations) tại thời điểm chạy sử dụng các phương thức của reflection. |

---

## Ghi Chú Chi Tiết (Detailed Notes)

### Cơ Chế Phản Chiếu (Reflection) là gì?

Cơ chế phản chiếu (Reflection) là một tính năng trong Java cho phép một chương trình đang thực thi có thể tự kiểm tra hoặc thao tác trên các cấu trúc nội bộ của chính nó (các lớp, giao diện, các trường, các phương thức, các hàm khởi tạo) tại thời điểm chạy. Nó bỏ qua bước xác minh kiểu dữ liệu lúc biên dịch (compile-time type verification), cho phép chương trình có các hành vi động.

- **Nạp và Liên Kết Động (Dynamic Loading and Binding)**: Reflection cho phép các ứng dụng nạp các lớp theo tên tại thời điểm chạy và khởi tạo chúng mà không cần có các liên kết phụ thuộc lúc biên dịch.
- **Nền tảng của các Framework**: Nó là nền tảng cơ học của các bộ tiêm phụ thuộc (Dependency Injection - DI), các bộ ánh xạ quan hệ - đối tượng (Object-Relational Mappers - ORMs), các thư viện kiểm thử (JUnit) và các thư viện tuần tự hóa dữ liệu (Jackson).

#### Ví Dụ Mã Nguồn: Kiểm tra cấu trúc lớp
```java
public class SimpleInspector {
    public static void printType(Object obj) {
        if (obj == null) return;
        Class<?> clazz = obj.getClass();
        System.out.println("Class Name: " + clazz.getName());
        System.out.println("Is Interface: " + clazz.isInterface());
    }
}
```

---

### Class<?>

Lớp `java.lang.Class` là điểm bắt đầu cho mọi hoạt động phản chiếu. JVM tự động khởi tạo một đối tượng `Class` cho mọi kiểu dữ liệu được nạp vào bộ nhớ.

- **Các cách lấy đối tượng Class**:
  1. **Từ một thực thể đối tượng**: `String s = ""; Class<?> c = s.getClass();`
  2. **Từ lớp văn bản (class literal)**: `Class<?> c = String.class;`
  3. **Từ chuỗi tên động**: `Class<?> c = Class.forName("java.lang.String");` (phương thức này ném ra ngoại lệ checked `ClassNotFoundException`).

#### Ví Dụ Mã Nguồn: Ba cách lấy đối tượng Class
```java
public class ClassFetchers {
    public static void main(String[] args) throws ClassNotFoundException {
        // 1. Từ thực thể
        String text = "hello";
        Class<? extends String> c1 = text.getClass();

        // 2. Từ class literal (an toàn nhất, được xử lý tại thời điểm biên dịch)
        Class<String> c2 = String.class;

        // 3. Tra cứu động (linh hoạt nhưng ném ra CNFE)
        Class<?> c3 = Class.forName("java.lang.String");

        System.out.println(c1 == c2 && c2 == c3); // In ra: true (độc bản trên mỗi ClassLoader)
    }
}
```

## Tại Sao Reflection Bỏ Qua Tính An Toàn Kiểu Lúc Biên Dịch

Trong quá trình thực thi Java tiêu chuẩn, trình biên dịch thực thi kiểm tra kiểu tĩnh (static type checking) bằng cách xác minh tính tương thích kiểu, các chữ ký phương thức và các ràng buộc truy cập trước khi tạo ra mã byte (bytecode). Reflection bỏ qua các bước kiểm tra này vì nó hoạt động trực tiếp trên siêu dữ liệu lớp của JVM (các thực thể `Class<?>`, các đối tượng `Method`, `Field`, `Constructor`) được phân giải động tại thời điểm chạy thay vì thời điểm biên dịch. Khi mã nguồn sử dụng reflection, trình biên dịch không thể xác minh xem phương thức đích có thực sự tồn tại hay không, kiểu dữ liệu tham số truyền vào có tương thích hay không, hoặc các ràng buộc truy cập có bị vi phạm hay không. Thay vào đó, các bước kiểm tra này được chuyển giao cho công cụ thực thi của JVM xử lý, nó thực hiện tra cứu và xác minh động khi chạy ứng dụng, chuyển các lỗi đáng lẽ ở thời điểm biên dịch thành các ngoại lệ tại thời điểm chạy.

### Mô hình Tư duy: Phân giải biên dịch tĩnh vs. Phân giải động bằng Phản Chiếu

```mermaid
graph TD
    subgraph Compile Time ["Thời Điểm Biên Dịch (Kiểm Tra Tĩnh)"]
        A[Mã nguồn: obj.someMethod()] --> B[Trình biên dịch kiểm tra kiểu của obj]
        B --> C{Phương thức tồn tại trong lớp đã khai báo?}
        C -- Có --> D[Tạo mã byte invokevirtual/invokestatic]
        C -- Không --> E[Lỗi biên dịch]
    end
    subgraph Run Time ["Thời Điểm Chạy (Giải Quyết Động Bằng Phản Chiếu)"]
        F[Mã phản chiếu: method.invoke(obj)] --> G[Bỏ qua kiểm tra biên dịch: Kiểu là Object]
        G --> H[JVM truy vấn siêu dữ liệu Class tại thời điểm chạy]
        H --> I{Khớp chữ ký phương thức & Kiểm tra truy cập?}
        I -- Đạt --> J[Thực thi phương thức qua JVM Engine]
        I -- Thất bại --> K[Ném NoSuchMethodException / IllegalAccessException]
    end
```

### Ví Dụ Mã Nguồn: Bỏ qua kiểm tra kiểu lúc biên dịch
```java
import java.lang.reflect.Method;

public class TypeSafetyBypass {
    public static void main(String[] args) {
        Object target = "Hello Reflection";
        try {
            // Kiểm tra lúc biên dịch bị bỏ qua vì 'target' được khai báo là kiểu Object.
            // Kiểu thực tế String được giải quyết động lúc chạy.
            Method lengthMethod = target.getClass().getMethod("length");
            Object result = lengthMethod.invoke(target);
            System.out.println("Result: " + result); // In ra: "Result: 16"
            
            // Tên phương thức bị viết sai chính tả vượt qua biên dịch nhưng sẽ lỗi lúc chạy
            Method invalidMethod = target.getClass().getMethod("lenght"); 
        } catch (Exception e) {
            System.out.println("Error: " + e.getClass().getSimpleName()); 
            // In ra: "Error: NoSuchMethodException"
        }
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)
Tra cứu phản chiếu với tên phương thức viết sai $\rightarrow$ Trình biên dịch chỉ nhìn thấy lời gọi API siêu dữ liệu chung $\rightarrow$ Biên dịch thành công $\rightarrow$ JVM thực hiện tra cứu động khi chạy $\rightarrow$ Không tìm thấy phương thức trong siêu dữ liệu `Class<?>` $\rightarrow$ Ném ra ngoại lệ `NoSuchMethodException` tại thời điểm chạy.

---

### Lấy thông tin lớp (Get class information)

Khi sở hữu một tham chiếu `Class<?>`, bạn có thể kiểm tra mọi khía cạnh của kiểu dữ liệu đã nạp.

- **Lớp cha**: Gọi `clazz.getSuperclass()` để lấy đối tượng Class đại diện cho lớp cha.
- **Các giao diện**: Gọi `clazz.getInterfaces()` trả về một mảng chứa các giao diện được triển khai trực tiếp bởi lớp đó.
- **Các bổ từ**: Gọi `clazz.getModifiers()` trả về một mặt nạ bit kiểu số nguyên, bạn có thể giải mã nó bằng cách sử dụng lớp tiện ích `java.lang.reflect.Modifier`.

#### Ví Dụ Mã Nguồn: Giải mã các bổ từ của lớp
```java
import java.lang.reflect.Modifier;

public class ModifierInspector {
    public static void inspect(Class<?> clazz) {
        int mods = clazz.getModifiers();
        System.out.println("Is Public: " + Modifier.isPublic(mods));
        System.out.println("Is Abstract: " + Modifier.isAbstract(mods));
        System.out.println("Is Final: " + Modifier.isFinal(mods));
    }
}
```

---

### Lấy thuộc tính (Get field)

Các trường thuộc tính được kiểm tra thông qua lớp `java.lang.reflect.Field`.

- **`getField(name)` vs `getDeclaredField(name)`**:
  - `getField(name)` trả về trường thuộc tính **public** được chỉ định, thực hiện tìm kiếm trên chính lớp đó và toàn bộ các lớp cha của nó.
  - `getDeclaredField(name)` trả về trường thuộc tính được khai báo **trực tiếp** trong lớp (bao gồm cả private, protected, package-private), nhưng bỏ qua các trường kế thừa từ lớp cha.
- **Liệt kê các trường**: `getFields()` vs `getDeclaredFields()` tuân thủ cùng các quy tắc phạm vi tìm kiếm nêu trên.

#### Ví Dụ Mã Nguồn: Đọc các trường public so với các trường khai báo trực tiếp
```java
import java.lang.reflect.Field;

class Parent {
    public int publicParentField;
}
class Child extends Parent {
    private int privateChildField;
    public int publicChildField;
}

public class FieldInspector {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Child.class;

        // getFields() -> trả về publicParentField và publicChildField
        System.out.println("Public fields count: " + clazz.getFields().length); // Kết quả: 2

        // getDeclaredFields() -> chỉ trả về privateChildField và publicChildField
        System.out.println("Declared fields count: " + clazz.getDeclaredFields().length); // Kết quả: 2
    }
}
```

---

### Lấy phương thức (Get method)

Các phương thức được đại diện bởi lớp `java.lang.reflect.Method`.

- **Phạm vi**: `getMethod(name, parameterTypes...)` khớp với các phương thức public (bao gồm cả phương thức kế thừa). `getDeclaredMethod(name, parameterTypes...)` chỉ khớp với bất kỳ phương thức nào được định nghĩa trực tiếp trong chính lớp này.
- **Khớp phương thức nạp chồng**: Bạn phải truyền vào danh sách kiểu tham số chính xác để xác định đúng phương thức nạp chồng cần lấy (ví dụ: `getDeclaredMethod("setName", String.class)`).

#### Ví Dụ Mã Nguồn: Lấy phương thức theo chữ ký
```java
import java.lang.reflect.Method;

class UserService {
    public void update(int id, String name) {}
}

public class MethodFetch {
    public static void main(String[] args) throws Exception {
        Class<UserService> clazz = UserService.class;
        // Chỉ định kiểu của các tham số để khớp chính xác phương thức nạp chồng
        Method m = clazz.getDeclaredMethod("update", int.class, String.class);
        System.out.println("Found method: " + m.toString());
    }
}
```

---

### Lấy hàm khởi tạo (Get constructor)

Các hàm khởi tạo được đại diện bởi lớp `java.lang.reflect.Constructor`.

- **Khởi tạo đối tượng**: Bạn lấy hàm khởi tạo để khởi tạo các đối tượng một cách động.
- **Khớp chữ ký**: Tương tự như phương thức, bạn chỉ định mảng các kiểu đối số để tìm kiếm đúng hàm khởi tạo nạp chồng cần dùng.

#### Ví Dụ Mã Nguồn: Truy vấn các hàm khởi tạo
```java
import java.lang.reflect.Constructor;

class Product {
    public Product() {}
    public Product(String name, double price) {}
}

public class ConstructorInspector {
    public static void main(String[] args) throws Exception {
        Class<Product> clazz = Product.class;
        
        // Lấy hàm khởi tạo mặc định public
        Constructor<Product> c1 = clazz.getConstructor();
        
        // Lấy hàm khởi tạo có tham số public
        Constructor<Product> c2 = clazz.getConstructor(String.class, double.class);
        
        System.out.println("Default: " + c1);
        System.out.println("Parameterized: " + c2);
    }
}
```

---

### Gọi phương thức sử dụng reflection (Invoke method)

Bạn thực thi các phương thức một cách động bằng cách gọi phương thức `Method.invoke(targetObject, arguments...)`.

- **Phương thức tĩnh (Static Methods)**: Đối với các phương thức tĩnh, hãy truyền giá trị `null` cho tham số `targetObject`.
- **Ngoại lệ**: Mọi ngoại lệ ném ra bên trong phương thức được gọi sẽ được công cụ reflection bắt lại và bọc bên trong một ngoại lệ checked có tên là `java.lang.reflect.InvocationTargetException`. Bạn phải gọi `getTargetException()` hoặc `getCause()` để kiểm tra lỗi gốc thực tế bên dưới.

#### Ví Dụ Mã Nguồn: Gọi phương thức động
```java
import java.lang.reflect.Method;

public class Invoker {
    public static void main(String[] args) throws Exception {
        String text = "hello world";
        Method uppercaseMethod = String.class.getMethod("toUpperCase");
        
        // Tương đương với việc viết trực tiếp text.toUpperCase()
        Object result = uppercaseMethod.invoke(text);
        System.out.println(result); // In ra: "HELLO WORLD"
    }
}
```

---

### Tạo đối tượng sử dụng reflection

Trong lịch sử, bạn có thể tạo thực thể của một đối tượng bằng cách gọi trực tiếp `Class.newInstance()`. Tuy nhiên, phương thức này đã bị **loại bỏ (deprecated)** kể từ phiên bản Java 9.

- **Lý do loại bỏ `Class.newInstance()`**: Nó bỏ qua các kiểm tra ngoại lệ checked lúc biên dịch và trực tiếp ném chúng ra ngoài thời gian chạy (vi phạm các quy tắc xử lý ngoại lệ của Java).
- **Mẫu thiết kế chuẩn**: Lấy đối tượng Constructor một cách rõ ràng và gọi `Constructor.newInstance(args...)`. Cách này bọc các ngoại lệ được ném ra bên trong đối tượng `InvocationTargetException`.

#### Ví Dụ Mã Nguồn: Khởi tạo thực thể (chuẩn Java 9+)
```java
import java.lang.reflect.Constructor;

public class Instantiator {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = java.util.ArrayList.class;
        
        // CHUẨN: getConstructor().newInstance()
        Constructor<?> constructor = clazz.getConstructor();
        Object list = constructor.newInstance();
        
        System.out.println("List created: " + list.getClass().getName()); // ArrayList
    }
}
```

---

### Truy cập các trường/phương thức private

Reflection có thể vượt qua các kiểm soát quyền truy cập ngôn ngữ (như `private`, `protected`, hoặc package-private) bằng cách gọi phương thức `AccessibleObject.setAccessible(true)`.

- **AccessibleObject**: Là lớp cha của `Field`, `Method`, và `Constructor`.
- **`setAccessible(true)`**: Vô hiệu hóa các kiểm tra bổ từ truy cập tại thời điểm chạy cho riêng thực thể phản chiếu cụ thể đó.
- **Các giới hạn ràng buộc**: 
  - Một `SecurityManager` (nếu hoạt động) có thể chặn hành động này.
  - Hệ thống Module của Java (từ Java 9 trở đi) sẽ chặn việc phản chiếu sâu vào các gói không được xuất (non-exported packages) của các module có tên trừ khi tham số `--add-opens` được truyền vào lúc khởi động JVM.

#### Ví Dụ Mã Nguồn: Ghi đè giá trị vào thuộc tính private
```java
import java.lang.reflect.Field;

class Account {
    private double balance = 10.0;
}

public class PrivateAccess {
    public static void main(String[] args) throws Exception {
        Account account = new Account();
        Class<?> clazz = account.getClass();
        
        Field balanceField = clazz.getDeclaredField("balance");
        // Vượt qua kiểm tra bảo vệ private
        balanceField.setAccessible(true);
        
        // Đọc giá trị
        double value = (double) balanceField.get(account);
        System.out.println("Original Balance: " + value); // In ra: 10.0
        
        // Ghi đè giá trị mới
        balanceField.set(account, 5000.0);
        System.out.println("Modified Balance: " + balanceField.get(account)); // In ra: 5000.0
    }
}
```

## Tại Sao setAccessible(true) Có Thể Bỏ Qua Các Kiểm Soát Quyền Truy Cập và Các Ràng Buộc Của Hệ Thống Module

Các bổ từ truy cập trong Java (`private`, `protected`, package-private) là các ràng buộc cấp độ ngôn ngữ được thiết kế để áp đặt tính đóng gói và duy trì tính bất biến của lớp tại thời điểm biên dịch. Bên dưới lớp vỏ, JVM áp dụng các bổ từ này tại thời điểm chạy bằng cách xác minh các chỉ thị bytecode của việc truy cập trường hoặc gọi phương thức. Khi một chương trình gọi `AccessibleObject.setAccessible(true)` trên một đối tượng phản chiếu (như một `Field` hoặc `Method`), nó sẽ hướng dẫn JVM bỏ qua các kiểm tra quyền truy cập khi chạy cho thực thể đối tượng cụ thể đó. Tuy nhiên, trong các phiên bản Java hiện đại, khả năng này bị hạn chế rất nghiêm ngặt: một `SecurityManager` được cấu hình (nếu có) có thể chặn thao tác này bằng cách ném ra ngoại lệ `SecurityException`, và Hệ thống Module của Java (từ Java 9) ngăn chặn nghiêm ngặt việc phản chiếu sâu vào các gói được đóng gói kín của các module có tên trừ khi module đó mở (opens) hoặc xuất (exports) gói đó một cách rõ ràng cho bên gọi.

### Mô Hình Tư Duy: Sơ đồ quyết định ghi đè khả năng truy cập

```mermaid
graph TD
    A[Lớp Gọi] --> B{setAccessible(true)}
    B --> C{Lớp đích có nằm trong một module có tên?}
    C -- Có --> D{Gói có được mở/xuất tới lớp gọi?}
    D -- Không --> E[Ném InaccessibleObjectException]
    D -- Có --> F{SecurityManager có hoạt động?}
    C -- Không --> F
    F -- Có --> G{Quyền checkMemberAccess được cấp?}
    G -- No --> H[Ném SecurityException]
    G -- Yes --> I[Thiết lập cờ ghi đè: Bỏ qua kiểm tra truy cập]
    F -- No --> I
```

### Ví Dụ Mã Nguồn: Ranh giới Module và giới hạn setAccessible
```java
import java.lang.reflect.Field;

public class ModuleReflectionBypass {
    public static void main(String[] args) {
        try {
            // Thử truy cập phản chiếu vào trường private của java.lang.String (nằm trong module java.base)
            Field valueField = String.class.getDeclaredField("value");
            valueField.setAccessible(true); 
            // Trong Java 9+, thao tác này ném ra java.lang.reflect.InaccessibleObjectException 
            // bởi vì gói java.lang không được mở rộng (open) cho các module không tên (unnamed modules).
        } catch (Exception e) {
            System.out.println("Caught exception: " + e.getClass().getName());
            // In ra: "Caught exception: java.lang.reflect.InaccessibleObjectException"
        }
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)
Gọi `setAccessible(true)` trên trường nội bộ của module $\rightarrow$ JVM kiểm tra ranh giới của module $\rightarrow$ Gói chưa được mở cho module gọi $\rightarrow$ JVM từ chối yêu cầu ghi đè truy cập $\rightarrow$ Ngoại lệ `InaccessibleObjectException` bị ném ra tại thời điểm chạy.

---

### Annotation + reflection (Chú thích + Phản chiếu)

Các chú thích (annotations) chỉ hiển thị với reflection tại thời điểm chạy nếu chúng được cấu hình chính sách lưu trữ là `@Retention(RetentionPolicy.RUNTIME)`.

- **Truy vấn Siêu Dữ Liệu**: Các phương thức như `isAnnotationPresent(Annotation.class)` và `getAnnotation(Annotation.class)` được cung cấp sẵn trên các lớp `Class`, `Field`, `Method`, và `Constructor` vì tất cả chúng đều triển khai giao diện chung `AnnotatedElement`.

#### Ví Dụ Mã Nguồn: Trình xử lý annotation đơn giản
```java
import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface RunMe {}

class Task {
    @RunMe
    public void step1() { System.out.println("Running step 1"); }
    public void step2() { System.out.println("Running step 2"); }
}

public class AnnotationRunner {
    public static void main(String[] args) throws Exception {
        Task task = new Task();
        for (Method m : Task.class.getDeclaredMethods()) {
            if (m.isAnnotationPresent(RunMe.class)) {
                m.invoke(task); // Chạy step1, bỏ qua step2
            }
        }
    }
}
```

---

## Các Câu Hỏi Ôn Tập Thường Gặp (Common Review Prompts)

- **Những khái niệm nào ở đây là quy tắc tại thời điểm biên dịch?**
  Không có khái niệm nào; Reflection hoàn toàn là một API hoạt động ở thời điểm chạy. Tuy nhiên, việc cố gắng phản chiếu trên các tên sai lệch sẽ ném ra ngoại lệ lúc chạy (như `ClassNotFoundException` hoặc `NoSuchMethodException`).
- **Những khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy?**
  Việc khởi tạo thực thể đối tượng, gọi thực thi phương thức, truy cập private và xử lý các ngoại lệ.
- **Những khái niệm nào ở đây dễ là cạm bẫy phỏng vấn?**
  - Nhầm lẫn giữa `getField()` (public, có kế thừa) với `getDeclaredField()` (bất kỳ trường nào, không kế thừa).
  - Quên rằng các ngoại lệ ném ra trong mã phản chiếu sẽ được bọc lại trong `InvocationTargetException`.
  - Sử dụng phương thức đã bị loại bỏ `Class.newInstance()` thay vì dùng `Constructor.newInstance()`.
  - Bỏ qua kiểm tra private bằng `setAccessible(true)` bị thất bại dưới các cấu hình module bảo mật nghiêm ngặt của JVM.
