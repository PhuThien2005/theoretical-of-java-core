# Generics – Phần 3: Kiểu thô và Các hạn chế của Generic (Generics – Part 3: Raw Types and Generic Limitations)

## 1. Kiểu thô (Raw Types)

**Định nghĩa:** Một kiểu thô (raw type) là tên của một lớp hoặc giao diện generic được sử dụng **không đi kèm** bất kỳ đối số kiểu nào.

```java
// Generic (correct)
List<String> names = new ArrayList<>();

// Raw type (avoid)
List rawList = new ArrayList();
```

**Cách hoạt động:** Trình biên dịch xử lý kiểu thô như thể tất cả các tham số kiểu được thay thế bằng kiểu `Object`. Mọi cơ chế an toàn của generic đều bị vô hiệu hóa.

**Ảnh hưởng đối với code:**
```java
List rawList = new ArrayList();
rawList.add("hello");
rawList.add(42);            // no compile error — anything goes
String s = (String) rawList.get(1);  // ClassCastException at runtime!
```

**Cảnh báo của trình biên dịch:** Sử dụng kiểu thô sẽ kích hoạt các cảnh báo `unchecked`:
```
Note: MyClass.java uses unchecked or unsafe operations.
```

**Khi nào kiểu thô xuất hiện một cách hợp lệ:**
1. Tương tác với các API cũ trước thời kỳ Java 5 vốn không có các phiên bản generic.
2. Bên trong các phép kiểm tra `instanceof` (dù sao bạn cũng không thể sử dụng `instanceof List<String>`):
   ```java
   if (obj instanceof List) {         // raw — necessary here
       List<?> list = (List<?>) obj;  // immediately switch to wildcard
   }
   ```

**Quy tắc then chốt:** Ngay khi bạn gán một kiểu thô cho một biến, hãy sử dụng `List<?>` (thay vì kiểu thô) cho phần còn lại của mã nguồn.

---

## 2. Các hạn chế của Generic (Generic Limitations)

Generics trong Java có một số hạn chế tích hợp sẵn, hầu hết đều do cơ chế **xóa bỏ kiểu (type erasure)** gây ra.

### 2.1 Không thể Khởi tạo Thực thể từ Tham số kiểu (Cannot Instantiate Type Parameters)

```java
class Container<T> {
    T value = new T();   // COMPILE ERROR — T erased to Object at runtime
}
```

**Giải pháp thay thế:** Truyền vào một token lớp `Class<T>`:
```java
class Container<T> {
    T value;
    Container(Class<T> clazz) throws Exception {
        value = clazz.getDeclaredConstructor().newInstance();
    }
}
```

---

### 2.2 Không thể Tạo mảng Generic (Cannot Create Generic Arrays)

```java
T[] arr = new T[10];              // COMPILE ERROR
List<String>[] lists = new ArrayList<String>[3];  // COMPILE ERROR
```

**Tại sao:** Các mảng mang theo kiểu thành phần của chúng tại thời điểm chạy (ví dụ: `String[]` biết nó là một `String[]`). Sau khi xóa bỏ kiểu, `T[]` chỉ đơn thuần là `Object[]`, làm phá vỡ tính an toàn kiểu của mảng.

**Giải pháp thay thế:**
```java
// Option 1: use List<T>
List<T> list = new ArrayList<>();

// Option 2: unchecked cast with class token
@SuppressWarnings("unchecked")
T[] arr = (T[]) new Object[10];

// Option 3: Array.newInstance
T[] arr = (T[]) Array.newInstance(clazz, 10);
```

---

### 2.3 Không thể Sử dụng các Đối số kiểu Nguyên thủy (Cannot Use Primitive Type Arguments)

```java
List<int> nums = new ArrayList<>();   // COMPILE ERROR
```

**Tại sao:** Generics được triển khai thông qua các tham chiếu `Object`; kiểu nguyên thủy không phải là các đối tượng.

**Giải pháp thay thế:** Sử dụng các lớp bao bọc (wrapper classes). Cơ chế autoboxing khiến việc này diễn ra gần như tự động:
```java
List<Integer> nums = new ArrayList<>();
nums.add(1);            // autoboxed to Integer
int n = nums.get(0);    // unboxed to int
```

**Lưu ý hiệu năng:** Cơ chế autoboxing có phát sinh chi phí hiệu năng. Đối với các mã nguồn yêu cầu hiệu năng cực cao, hãy cân nhắc sử dụng `int[]` hoặc các thư viện tập hợp nguyên thủy của bên thứ ba.

---

### 2.4 Không thể có các Trường tĩnh kiểu Tham số kiểu (Cannot Have Static Fields of Type Parameter Type)

```java
class Bag<T> {
    static T instance;   // COMPILE ERROR — static belongs to class, not T
}
```

**Tại sao:** Các trường tĩnh `static` được chia sẻ giữa tất cả các thực thể của `Bag`. `Bag<String>` và `Bag<Integer>` dùng chung một lớp đã được tải, vì vậy việc có một trường `T` tĩnh duy nhất là vô nghĩa.

**Giải pháp thay thế:** Chuyển trường đó thành phi tĩnh (non-static), hoặc sử dụng một tham số `Class<T>` riêng biệt.

---

### 2.5 Không thể Bắt hoặc Ném ra các Ngoại lệ Generic (Cannot Catch or Throw Generic Exceptions)

```java
class MyException<T> extends Exception { ... }   // COMPILE ERROR (extends Throwable)
// (legal to declare but not to catch with a generic type argument)

<T extends Exception> void process() throws T { }   // OK to declare
try { } catch (T e) { }                             // COMPILE ERROR in catch
```

**Tại sao:** JVM khớp các kiểu ngoại lệ tại thời điểm chạy; các kiểu bị xóa bỏ không thể được sử dụng trong khối `catch`.

---

### 2.6 Không thể Nạp chồng các Phương thức có Danh sách tham số Xóa về cùng một Chữ ký (Cannot Overload Methods Whose Parameter Lists Erase to the Same Signature)

```java
void print(List<String> list) { }
void print(List<Integer> list) { }  // COMPILE ERROR — both erase to print(List)
```

---

### 2.7 Không thể Sử dụng instanceof với các Kiểu được tham số hóa (Cannot Use instanceof with Parameterized Types)

```java
if (obj instanceof List<String>) { }   // COMPILE ERROR — type info erased
if (obj instanceof List<?>)      { }   // OK — unbounded wildcard is allowed
if (obj instanceof List)         { }   // OK — raw type check
```

---

## Bảng tổng hợp (Summary Table)

| Hạn chế | Nguyên nhân | Giải pháp thay thế |
|------------|-------|-------------|
| `new T()` | Xóa bỏ kiểu | Token `Class<T>` + phản chiếu |
| `new T[n]` | Tính cụ thể hóa của kiểu mảng (Reification) | `List<T>` hoặc `(T[]) new Object[n]` |
| `List<int>` | Kiểu nguyên thủy không phải là đối tượng | `List<Integer>` + autoboxing |
| `static T field` | Tĩnh (static) dùng chung giữa các tham số kiểu | Trường phi tĩnh (non-static field) |
| `catch (T e)` | JVM cần kiểu dữ liệu cụ thể | Kiểu ngoại lệ cụ thể |
| Nạp chồng các phương thức có cùng kiểu xóa bỏ | Cùng chữ ký mã byte (bytecode) | Đổi tên phương thức |
| `instanceof List<String>` | Kiểu bị xóa bỏ | Sử dụng `List<?>` hoặc kiểu thô |

## Tại sao Kiểu thô Tồn tại và Các nguy cơ của chúng (Why Raw Types Exist and Their Dangers)

Các kiểu thô tồn tại trong ngôn ngữ Java duy nhất để hỗ trợ tính tương thích ngược với các mã nguồn cũ được viết trước Java 5. Trước khi có generics, các tập hợp (collections) chỉ đơn thuần chứa các tham chiếu `Object`, và kiểu thô cho phép đoạn mã cũ này biên dịch và chạy trên các môi trường chạy hiện đại mà không cần sửa đổi. Tuy nhiên, việc sử dụng các kiểu thô trong mã nguồn mới sẽ bỏ qua tất cả các kiểm tra an toàn kiểu generic của trình biên dịch. Vì trình biên dịch không thực hiện kiểm tra kiểu trên các tập hợp thô, nó cho phép nhà phát triển chèn các kiểu dữ liệu không khớp vào tập hợp mà không có bất kỳ cảnh báo nào lúc biên dịch. Việc vi phạm an toàn kiểu thực tế sau đó sẽ bị hoãn lại cho đến khi chạy chương trình, nơi việc đọc một phần tử và cố gắng ép nó sang kiểu không chính xác sẽ kích hoạt một lỗi `ClassCastException` và làm sập ứng dụng.

### Mô hình tư duy (Mental Model)

```text
Ý định của nhà phát triển: List của String
[List rawList] = new ArrayList() ---> Chấp nhận "Hello" (OK)
                                 ---> Chấp nhận 123 (Không kiểm tra: OK!)

Đọc lúc chạy:
String s = (String) rawList.get(1) ---> Ép kiểu Integer (123) sang String
                                   ---> BỊ CRASH: ClassCastException
```

### Ví dụ Code (Code Example)

```java
import java.util.ArrayList;
import java.util.List;

public class RawTypeDanger {
    public static void main(String[] args) {
        // Raw type usage bypasses compile-time checks
        List rawList = new ArrayList();
        rawList.add("Safe String");
        rawList.add(Integer.valueOf(100)); // Compiles, but triggers unchecked warning

        System.out.println("Elements added successfully.");

        try {
            // This line compiles but throws an exception at runtime
            String element = (String) rawList.get(1); 
            System.out.println(element);
        } catch (ClassCastException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
            // Output: Caught expected exception: class java.lang.Integer cannot be cast to class java.lang.String
        }
    }
}
```

### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)


```text
Sử dụng kiểu thô
  → Trình biên dịch vô hiệu hóa kiểm tra kiểu generic
  → Các đối tượng không khớp kiểu bị chèn vào tập hợp
  → Mã nguồn biên dịch không lỗi
  → Nhà phát triển cố gắng đọc và ép kiểu đối tượng đó khi chạy
  → JVM ném ra ClassCastException.
```


## Tại sao Generics không Hỗ trợ các kiểu Nguyên thủy (Why Generics Do Not Support Primitives)

Do cơ chế xóa bỏ kiểu thời điểm biên dịch, tất cả các tham số kiểu generic trong Java đều bị xóa thành giới hạn ngoài cùng bên trái của chúng, thường là kiểu `Object` nếu không có giới hạn. Trong Máy ảo Java (JVM), các tham chiếu đến đối tượng được biểu diễn trong mã byte bởi các ô tham chiếu (sử dụng tiền tố `a` trong các lệnh mã byte như `aload` và `astore`). Các kiểu nguyên thủy, như `int` hoặc `char`, không kế thừa từ `java.lang.Object` và được lưu trữ bằng các kích thước nhị phân và các lệnh mã byte khác nhau (như `iload` cho số nguyên). Bởi vì JVM không thể lưu trữ trực tiếp một kiểu nguyên thủy trong một ô nhớ được chỉ định cho các tham chiếu đối tượng, generics không thể hỗ trợ các kiểu nguyên thủy một cách tự nhiên. Do đó, Java yêu cầu các lớp bao bọc (như `Integer`) và sử dụng chuyển đổi tự động (autoboxing) để bọc các kiểu nguyên thủy trong các đối tượng được cấp phát trên heap khi lưu trữ trong các cấu trúc generic.

### Mô hình tư duy (Mental Model)

```text
Biểu diễn bộ nhớ trong JVM:
Box<T> Generic (Bị xóa thành tham chiếu Object):
[ Ô chứa tham chiếu (4/8 bytes) ] ---> Trỏ tới Đối tượng trên Heap: [ Integer (123) ]
                                                                     (Lớp bao bọc Autoboxed)

Không thể lưu trữ kiểu nguyên thủy trực tiếp:
[ Ô chứa tham chiếu (4/8 bytes) ] -x-> Không thể chứa số int nhị phân 32-bit thô [ 123 ]
```

### Ví dụ Code (Code Example)

```java
import java.util.ArrayList;
import java.util.List;

public class PrimitiveGenericsLimit {
    public static void main(String[] args) {
        // List<int> list = new ArrayList<>(); // Compile Error
        
        List<Integer> list = new ArrayList<>();
        
        // Autoboxing: compiler automatically converts primitive 42 into Integer.valueOf(42)
        list.add(42); 
        
        // Unboxing: compiler converts retrieved Integer back to primitive int via intValue()
        int val = list.get(0);
        
        System.out.println("Value: " + val); // Output: Value: 42
    }
}
```

### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)


```text
Generics trải qua quá trình Xóa bỏ kiểu
  → Các tham số kiểu bị xóa thành tham chiếu Object
  → JVM biểu diễn tham chiếu khác với kiểu nhị phân nguyên thủy
  → Kiểu nguyên thủy không thể chiếm dụng các ô nhớ chỉ dành cho tham chiếu
  → Cấm List<int>
  → Bắt buộc phải sử dụng List<Integer> kèm theo boxing.
```


## Tại sao việc Tạo mảng Generic và Kiểm tra kiểu lúc chạy bị Cấm (Why Generic Array Creation and Runtime Type Checks Are Forbidden)

Trong Java, các mảng được cụ thể hóa (reified), nghĩa là chúng giữ đầy đủ thông tin về kiểu phần tử của chúng lúc chạy và thực thi tính an toàn kiểu thông qua các kiểm tra của JVM. Nếu bạn cố gắng lưu trữ một phần tử không tương thích vào một mảng, JVM sẽ lập tức ném ra một ngoại lệ `ArrayStoreException` lúc chạy. Ngược lại, generics bị xóa bỏ kiểu, nghĩa là tất cả các thông tin tham số kiểu bị loại bỏ sau khi biên dịch. Nếu việc tạo mảng generic như `new T[10]` hoặc `new List<String>[10]` được cho phép, JVM sẽ không có cách nào để thực thi đúng kiểu phần tử tại thời điểm chạy bởi vì kiểu thành phần thực tế khi đó đã bị xóa thành `Object[]`. Vì những lý do tương tự, các kiểm tra thời điểm chạy như `instanceof List<String>` bị cấm, bởi vì tham số kiểu bị thiếu lúc chạy, khiến JVM chỉ có khả năng kiểm tra kiểu thô `instanceof List`.

### Mô hình tư duy (Mental Model)

```text
Mảng (Được cụ thể hóa - Biết kiểu lúc chạy):
String[] strings = new String[5]; ---> JVM biết đây là kiểu [Ljava.lang.String;
strings[0] = "hello";             ---> OK
((Object[]) strings)[1] = 123;    ---> JVM kiểm tra kiểu lúc chạy ---> Ném ra ArrayStoreException

Generics (Bị xóa - Mất kiểu lúc chạy):
List<String> list = new ArrayList<>(); ---> JVM chỉ biết đây là List
```

### Ví dụ Code (Code Example)

```java
import java.util.ArrayList;
import java.util.List;

public class ArrayAndInstanceofLimit {
    public static void main(String[] args) {
        // 1. Generic array creation is forbidden:
        // List<String>[] listArray = new ArrayList<String>[5]; // Compile Error

        // 2. Runtime type checks with generics are forbidden:
        List<String> stringList = new ArrayList<>();
        // if (stringList instanceof ArrayList<String>) {} // Compile Error
        
        // Unbounded wildcard or raw type instanceof is allowed:
        if (stringList instanceof ArrayList<?>) {
            System.out.println("Check passed using wildcard."); // Output: Check passed using wildcard.
        }
    }
}
```

### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)


```text
Các mảng được cụ thể hóa
  → Mảng thực thi kiểu phần tử chính xác của chúng lúc chạy thông qua JVM
  → Generics bị xóa bỏ kiểu
  → Tham số kiểu generic bị mất lúc chạy
  → JVM không thể thực thi tính an toàn kiểu của Mảng Generic lúc chạy
  → Việc tạo mảng generic bị cấm.
```

