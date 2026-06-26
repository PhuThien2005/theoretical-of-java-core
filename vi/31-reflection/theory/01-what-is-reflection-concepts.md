# Phản chiếu (Reflection) - Phần 1

## Mục tiêu học tập (Learning Goal)

Tệp này bao gồm một phần tập trung của **Phản chiếu (Reflection)**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, không phải là từ vựng cô lập.

## Đề cương chi tiết (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `What is Reflection?` | Phản chiếu cho phép mã nguồn kiểm tra và thao tác các lớp, thuộc tính, phương thức, và hàm khởi tạo tại thời điểm chạy. |
| `Class<?>` | Class<?> đại diện cho siêu dữ liệu của một lớp hoặc giao diện Java đã tải trong JVM. |
| `Get class information` | Truy vấn các công cụ sửa đổi (modifiers), tên gói (package names), các lớp cha, và các giao diện được triển khai lúc chạy. |
| `Get field` | Lấy các đối tượng Field công khai hoặc được khai báo (declared) đại diện cho các thuộc tính của lớp. |
| `Get method` | Lấy các đối tượng Method công khai hoặc được khai báo để kiểm tra chữ ký phương thức. |
| `Get constructor` | Lấy các đối tượng Constructor công khai hoặc được khai báo để kiểm tra hàm khởi tạo của lớp. |
| `Invoke method using reflection` | Thực thi một phương thức động tại thời điểm chạy thông qua phản chiếu. |
| `Create object using reflection` | Khởi tạo thực thể của các lớp một cách động bằng phản chiếu hàm khởi tạo. |
| `Access private field/method` | Vượt qua các kiểm soát truy cập ngôn ngữ để đọc/ghi thuộc tính private hoặc gọi phương thức private. |
| `Annotation + reflection` | Truy vấn siêu dữ liệu của các chú thích thời điểm chạy (runtime annotations) bằng phương thức phản chiếu. |

---

## Chi tiết tài liệu học tập (Detailed Notes)

### Phản chiếu là gì? (What is Reflection?)

Phản chiếu (Reflection) là một tính năng trong Java cho phép một chương trình đang thực thi tự kiểm tra hoặc thao tác các cấu trúc nội bộ của chính nó (lớp, giao diện, thuộc tính, phương thức, hàm khởi tạo) lúc chạy. Nó bỏ qua việc xác thực kiểu tại thời điểm biên dịch, cho phép hành vi động.

- **Tải và Liên kết Động (Dynamic Loading and Binding)**: Phản chiếu cho phép các ứng dụng tải các lớp theo tên tại thời điểm chạy và khởi tạo chúng mà không cần các phụ thuộc lúc biên dịch.
- **Bản chất của các Khung công tác (Framework Enabler)**: Nó là nền tảng cơ học của các bộ chứa Tiêm phụ thuộc (DI), các bộ ánh xạ quan hệ đối tượng (ORMs), thư viện kiểm thử (JUnit), và khung công tác tuần tự hóa (Jackson).

#### Ví dụ mã nguồn: Kiểm tra cấu trúc lớp (Code Example: Checking Class Structure)
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

`java.lang.Class` là điểm khởi đầu cho mọi thao tác phản chiếu. JVM khởi tạo một đối tượng `Class` cho mỗi kiểu được tải vào.

- **Cách lấy một Tham chiếu Class**:
  1. **Từ một thực thể (instance)**: `String s = ""; Class<?> c = s.getClass();`
  2. **Từ một lớp literal (từ khóa class)**: `Class<?> c = String.class;`
  3. **Từ một chuỗi tên động**: `Class<?> c = Class.forName("java.lang.String");` (ném ra ngoại lệ đã kiểm tra `ClassNotFoundException`).

#### Ví dụ mã nguồn: Ba cách để lấy tham chiếu Class (Code Example: Three Ways to Get Class Reference)
```java
public class ClassFetchers {
    public static void main(String[] args) throws ClassNotFoundException {
        // 1. Thực thể
        String text = "hello";
        Class<? extends String> c1 = text.getClass();

        // 2. Class literal (an toàn nhất, được giải quyết lúc biên dịch)
        Class<String> c2 = String.class;

        // 3. Tra cứu động (linh hoạt nhưng ném ra CNFE)
        Class<?> c3 = Class.forName("java.lang.String");

        System.out.println(c1 == c2 && c2 == c3); // Prints true (singleton per ClassLoader)
    }
}
```

## Tại sao phản chiếu bỏ qua tính an toàn kiểu ở thời điểm biên dịch (Why Reflection Bypasses Compile-Time Type Safety)

Trong quá trình thực thi Java tiêu chuẩn, trình biên dịch thực thi việc kiểm tra kiểu tĩnh (static type checking) bằng cách xác thực tính tương thích kiểu, chữ ký phương thức, và các ràng buộc về quyền truy cập trước khi tạo mã máy (bytecode). Phản chiếu bỏ qua các kiểm tra này vì nó hoạt động trực tiếp trên siêu dữ liệu (metadata) lớp của JVM (các thực thể `Class<?>`, các đối tượng `Method`, `Field`, `Constructor`) được giải quyết động lúc chạy thay vì tại thời điểm biên dịch. Khi mã nguồn sử dụng phản chiếu, trình biên dịch không thể xác minh xem một phương thức đích có thực sự tồn tại hay không, kiểu đối số có tương thích hay không, hoặc các ràng buộc truy cập có bị vi phạm hay không. Thay vào đó, các kiểm tra này được trì hoãn cho đến công cụ thực thi của JVM, vốn thực hiện tra cứu và xác thực động trong quá trình thực thi, chuyển các lỗi vốn xảy ra ở thời điểm biên dịch thành các ngoại lệ lúc chạy.

### Sơ đồ tư duy: Giải quyết lúc biên dịch so với lúc chạy của Phản chiếu (Compile-Time vs. Runtime Reflection Resolution)
```mermaid
graph TD
    subgraph Compile Time [Thời điểm biên dịch (Kiểm tra tĩnh)]
        A[Mã nguồn: obj.someMethod()] --> B[Trình biên dịch kiểm tra Kiểu của obj]
        B --> C{Phương thức tồn tại trong lớp đã khai báo?}
        C -- Yes --> D[Tạo Bytecode invokevirtual/invokestatic]
        C -- No --> E[Lỗi biên dịch]
    end
    subgraph Run Time [Lúc chạy (Giải quyết động của Phản chiếu)]
        F[Mã phản chiếu: method.invoke(obj)] --> G[Bỏ qua kiểm tra của trình biên dịch: Kiểu là java.lang.Object]
        G --> H[JVM truy vấn siêu dữ liệu Class<?> lúc chạy]
        H --> I{Khớp chữ ký phương thức & Kiểm tra truy cập?}
        I -- Pass --> J[Thực thi phương thức qua Công cụ JVM]
        I -- Fail --> K[Ném NoSuchMethodException / IllegalAccessException]
    end
```

### Ví dụ mã nguồn: Bỏ qua xác thực ở thời điểm biên dịch (Bypassing Compile-time Verification)
```java
import java.lang.reflect.Method;

public class TypeSafetyBypass {
    public static void main(String[] args) {
        Object target = "Hello Reflection";
        try {
            // Compile-time checks are bypassed because 'target' is declared as Object.
            // String type is resolved at runtime dynamically.
            Method lengthMethod = target.getClass().getMethod("length");
            Object result = lengthMethod.invoke(target);
            System.out.println("Result: " + result); // Prints "Result: 16"
            
            // Misspelled method name bypasses compile-time verification but fails at runtime
            Method invalidMethod = target.getClass().getMethod("lenght"); 
        } catch (Exception e) {
            System.out.println("Error: " + e.getClass().getSimpleName()); 
            // Prints "Error: NoSuchMethodException"
        }
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Tra cứu bằng phản chiếu với tên phương thức viết sai
  → Trình biên dịch nhìn thấy lệnh gọi API siêu dữ liệu chung
  → Biên dịch thành công
  → JVM cố gắng tra cứu động lúc chạy
  → Không tìm thấy phương thức trong siêu dữ liệu `Class<?>`
  → Ngoại lệ `NoSuchMethodException` bị ném ra lúc chạy
```


---

### Lấy thông tin lớp (Get class information)

Khi đã có tham chiếu `Class<?>`, bạn có thể kiểm tra mọi khía cạnh của kiểu được tải.

- **Lớp cha (Superclass)**: `clazz.getSuperclass()` trả về đối tượng `Class` của lớp cha.
- **Giao diện (Interfaces)**: `clazz.getInterfaces()` trả về một mảng chứa các giao diện được triển khai trực tiếp bởi lớp đó.
- **Sửa đổi (Modifiers)**: `clazz.getModifiers()` trả về một mặt nạ bit kiểu số nguyên đại diện cho các công cụ sửa đổi, có thể được phân tích bằng `java.lang.reflect.Modifier`.

#### Ví dụ mã nguồn: Trích xuất các công cụ sửa đổi lớp (Extracting Class Modifiers)
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

Các thuộc tính được kiểm tra bằng cách sử dụng `java.lang.reflect.Field`.

- **`getField(name)` so với `getDeclaredField(name)`**:
  - `getField(name)` trả về thuộc tính **public** chỉ định, tìm kiếm xuyên suốt lớp đó và tất cả các lớp cha của nó.
  - `getDeclaredField(name)` trả về thuộc tính chỉ định được khai báo **trực tiếp** trong lớp (bao gồm cả private, protected, package-private), nhưng bỏ qua các thuộc tính được kế thừa.
- **Danh sách thuộc tính (Listing Fields)**: Các phương thức `getFields()` so với `getDeclaredFields()` cũng tuân theo cùng quy tắc phạm vi như trên.

#### Ví dụ mã nguồn: Đọc thuộc tính Public so với thuộc tính Khai báo (Reading Public vs Declared Fields)
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

        // getFields() -> returns publicParentField and publicChildField
        System.out.println("Public fields count: " + clazz.getFields().length); // 2

        // getDeclaredFields() -> returns only privateChildField and publicChildField
        System.out.println("Declared fields count: " + clazz.getDeclaredFields().length); // 2
    }
}
```

---

### Lấy phương thức (Get method)

Các phương thức được đại diện bởi `java.lang.reflect.Method`.

- **Phạm vi (Scoping)**: `getMethod(name, parameterTypes...)` khớp với các phương thức public (bao gồm cả kế thừa). `getDeclaredMethod(name, parameterTypes...)` chỉ khớp với bất kỳ phương thức nào được định nghĩa trực tiếp trong lớp này.
- **Khớp nạp chồng (Overloading Match)**: Bạn phải truyền vào các kiểu tham số chính xác để định vị các phương thức nạp chồng (ví dụ: `getDeclaredMethod("setName", String.class)`).

#### Ví dụ mã nguồn: Tìm kiếm một phương thức bằng chữ ký (Fetching a Method by Signature)
```java
import java.lang.reflect.Method;

class UserService {
    public void update(int id, String name) {}
}

public class MethodFetch {
    public static void main(String[] args) throws Exception {
        Class<UserService> clazz = UserService.class;
        // Specify parameter types to match the overload exactly
        Method m = clazz.getDeclaredMethod("update", int.class, String.class);
        System.out.println("Found method: " + m.toString());
    }
}
```

---

### Lấy hàm khởi tạo (Get constructor)

Hàm khởi tạo được đại diện bởi `java.lang.reflect.Constructor`.

- **Khởi tạo (Instantiation)**: Bạn lấy một hàm khởi tạo để khởi tạo thực thể đối tượng một cách động.
- **Khớp chữ ký (Signature matching)**: Tương tự như phương thức, bạn chỉ định một mảng kiểu đối số để tìm kiếm một hàm khởi tạo nạp chồng cụ thể.

#### Ví dụ mã nguồn: Tìm kiếm hàm khởi tạo (Fetching Constructors)
```java
import java.lang.reflect.Constructor;

class Product {
    public Product() {}
    public Product(String name, double price) {}
}

public class ConstructorInspector {
    public static void main(String[] args) throws Exception {
        Class<Product> clazz = Product.class;
        
        // Fetch default public constructor
        Constructor<Product> c1 = clazz.getConstructor();
        
        // Fetch parameterized constructor
        Constructor<Product> c2 = clazz.getConstructor(String.class, double.class);
        
        System.out.println("Default: " + c1);
        System.out.println("Parameterized: " + c2);
    }
}
```

---

### Gọi phương thức bằng phản chiếu (Invoke method using reflection)

Bạn thực thi các phương thức một cách động bằng cách sử dụng `Method.invoke(targetObject, arguments...)`.

- **Phương thức tĩnh**: Đối với phương thức tĩnh, truyền `null` cho `targetObject`.
- **Ngoại lệ (Exceptions)**: Bất kỳ ngoại lệ nào được ném ra bên trong phương thức được gọi sẽ bị bắt bởi công cụ phản chiếu và bao bọc bên trong một ngoại lệ đã được kiểm tra `java.lang.reflect.InvocationTargetException`. Bạn gọi `getTargetException()` hoặc `getCause()` để kiểm tra lỗi thực sự bên dưới.

#### Ví dụ mã nguồn: Gọi một phương thức động (Dynamically Invoking a Method)
```java
import java.lang.reflect.Method;

public class Invoker {
    public static void main(String[] args) throws Exception {
        String text = "hello world";
        Method uppercaseMethod = String.class.getMethod("toUpperCase");
        
        // Equivalent to text.toUpperCase()
        Object result = uppercaseMethod.invoke(text);
        System.out.println(result); // Prints "HELLO WORLD"
    }
}
```

---

### Tạo đối tượng bằng phản chiếu (Create object using reflection)

Trước đây, bạn có thể tạo thực thể đối tượng bằng cách sử dụng `Class.newInstance()`. Tuy nhiên, phương pháp này đã bị đánh dấu lỗi thời (deprecated) bắt đầu từ Java 9.

- **Phương thức cũ Class.newInstance()**: Bỏ qua các kiểm tra ngoại lệ thời điểm biên dịch và truyền trực tiếp các ngoại lệ đã được kiểm tra (vi phạm quy tắc xử lý ngoại lệ của Java).
- **Mẫu thiết kế chính xác**: Lấy rõ ràng hàm khởi tạo và gọi `Constructor.newInstance(args...)`. Cách này bao bọc các ngoại lệ bị ném ra vào `InvocationTargetException`.

#### Ví dụ mã nguồn: Khởi tạo thực thể (Tiêu chuẩn Java 9+) (Creating Instances - Java 9+ standard)
```java
import java.lang.reflect.Constructor;

public class Instantiator {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = java.util.ArrayList.class;
        
        // CORRECT: getConstructor().newInstance()
        Constructor<?> constructor = clazz.getConstructor();
        Object list = constructor.newInstance();
        
        System.out.println("List created: " + list.getClass().getName()); // ArrayList
    }
}
```

---

### Truy cập thuộc tính/phương thức private (Access private field/method)

Phản chiếu có thể vượt qua các kiểm soát truy cập (như `private`, `protected`, hoặc package-private) bằng cách sử dụng `AccessibleObject.setAccessible(true)`.

- **AccessibleObject**: Lớp cha của `Field`, `Method`, và `Constructor`.
- **`setAccessible(true)`**: Vô hiệu hóa việc kiểm tra công cụ sửa đổi quyền truy cập lúc chạy cho lần gọi phản chiếu cụ thể đó.
- **Các ràng buộc (Constraints)**: 
  - Một `SecurityManager` (nếu có) có thể chặn hành động này.
  - Hệ thống mô-đun Java (Java Module System — giới thiệu từ Java 9+) sẽ chặn phản chiếu sâu (deep reflection) vào các mô-đun không được xuất khẩu (non-exported) trừ khi cờ `--add-opens` được truyền cho JVM.

#### Ví dụ mã nguồn: Tiêm giá trị vào một thuộc tính Private (Injecting Value Into a Private Field)
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
        // Bypass the private check
        balanceField.setAccessible(true);
        
        // Read value
        double value = (double) balanceField.get(account);
        System.out.println("Original Balance: " + value); // 10.0
        
        // Write value
        balanceField.set(account, 5000.0);
        System.out.println("Modified Balance: " + balanceField.get(account)); // 5000.0
    }
}
```

## Tại sao setAccessible(true) có thể bỏ qua các kiểm soát truy cập và các ràng buộc của Hệ thống mô-đun (Why setAccessible(true) Can Bypass Access Controls and Its Module System Constraints)

Các công cụ sửa đổi quyền truy cập của Java (`private`, `protected`, package-private) là các ràng buộc cấp ngôn ngữ được thiết kế để thực thi tính đóng gói (Encapsulation) và duy trì các bất biến lớp tại thời điểm biên dịch. Cơ chế hoạt động bên dưới là JVM thực thi các sửa đổi này lúc chạy bằng cách xác thực các chỉ thị mã máy truy cập thuộc tính và gọi phương thức. Khi một chương trình gọi `AccessibleObject.setAccessible(true)` trên một đối tượng phản chiếu (chẳng hạn như một `Field` hoặc `Method`), nó sẽ hướng dẫn JVM tắt các kiểm tra truy cập lúc chạy này cho thực thể đối tượng cụ thể đó. Tuy nhiên, trong các phiên bản Java hiện đại, khả năng này bị hạn chế nghiêm trọng: một `SecurityManager` được cấu hình (nếu có) có thể chặn hoạt động này bằng cách ném ra ngoại lệ `SecurityException`, và Hệ thống mô-đun Java (giới thiệu từ Java 9) nghiêm cấm việc phản chiếu sâu vào các gói được đóng gói của các mô-đun được đặt tên trừ khi mô-đun đó xuất khẩu (export) hoặc mở (open) gói một cách rõ ràng cho bên gọi.

### Sơ đồ tư duy: Sơ đồ quyết định ghi đè khả năng truy cập (Accessibility Override Decision Tree)
```mermaid
graph TD
    A[Lớp gọi] --> B{setAccessible(true)}
    B --> C{Lớp đích có trong mô-đun được đặt tên không?}
    C -- Yes --> D{Gói có được mở/xuất khẩu cho lớp gọi không?}
    D -- No --> E[Ném ra InaccessibleObjectException]
    D -- Yes --> F{SecurityManager có đang hoạt động không?}
    C -- No --> F
    F -- Yes --> G{Đã cấp quyền checkMemberAccess?}
    G -- No --> H[Ném ra SecurityException]
    G -- Yes --> I[Đặt cờ ghi đè: các kiểm tra truy cập được bỏ qua]
    F -- No --> I
```

### Ví dụ mã nguồn: Ranh giới mô-đun và các ràng buộc của setAccessible (Module Boundaries and setAccessible Constraints)
```java
import java.lang.reflect.Field;

public class ModuleReflectionBypass {
    public static void main(String[] args) {
        try {
            // Attempting to reflectively access a private field in java.lang.String (module java.base)
            Field valueField = String.class.getDeclaredField("value");
            valueField.setAccessible(true); 
            // In Java 9+, this throws java.lang.reflect.InaccessibleObjectException 
            // because java.lang package is not opened to unnamed modules.
        } catch (Exception e) {
            System.out.println("Caught exception: " + e.getClass().getName());
            // Prints "Caught exception: java.lang.reflect.InaccessibleObjectException"
        }
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Gọi `setAccessible(true)` trên thuộc tính nội bộ mô-đun
  → JVM kiểm tra ranh giới mô-đun
  → Gói không được mở cho mô-đun gọi
  → JVM từ chối yêu cầu ghi đè
  → Ngoại lệ `InaccessibleObjectException` bị ném ra lúc chạy
```


---

### Annotation + phản chiếu (Annotation + reflection)

Các chú thích (annotations) chỉ hiển thị cho phản chiếu vào lúc chạy nếu chúng được cấu hình với `@Retention(RetentionPolicy.RUNTIME)`.

- **Lấy siêu dữ liệu**: Các phương thức như `isAnnotationPresent(Annotation.class)` and `getAnnotation(Annotation.class)` có sẵn trên `Class`, `Field`, `Method`, và `Constructor` vì chúng triển khai giao diện `AnnotatedElement`.

#### Ví dụ mã nguồn: Bộ xử lý Annotation đơn giản (Simple Annotation Processor)
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
                m.invoke(task); // Runs step1, skips step2
            }
        }
    }
}
```

---

## Các câu hỏi ôn tập thường gặp (Common Review Prompts)

- **Khái niệm nào ở đây là quy tắc thời gian biên dịch (compile-time)?**
  Không có; Phản chiếu hoàn toàn là một API lúc chạy. Tuy nhiên, việc cố gắng phản chiếu trên các tên không hợp lệ sẽ ném ra các ngoại lệ (như `ClassNotFoundException` hoặc `NoSuchMethodException`) lúc chạy.
- **Khái niệm nào ở đây ảnh hưởng đến hành vi lúc chạy (runtime)?**
  Khởi tạo thực thể, thực thi phương thức, truy cập private, và các ngoại lệ.
- **Khái niệm nào ở đây dễ là bẫy phỏng vấn?**
  - Nhầm lẫn `getField()` (public, được kế thừa) với `getDeclaredField()` (bất kỳ thuộc tính nào, không có kế thừa).
  - Quên rằng các ngoại lệ ném ra bên trong mã phản chiếu được bao bọc trong `InvocationTargetException`.
  - Sử dụng phương thức đã cũ `Class.newInstance()` thay vì `Constructor.newInstance()`.
  - Vượt qua các sửa đổi private bằng `setAccessible(true)` thất bại dưới các cấu hình mô-đun JVM hạn chế.
