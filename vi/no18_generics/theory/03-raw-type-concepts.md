# Generics (Kiểu Chung) - Phần 3: Kiểu Nguyên Bản (Raw Type) và Hạn Chế của Kiểu Chung

## 1. Kiểu Nguyên Bản (Raw Type)

**Định nghĩa:** Kiểu nguyên bản (Raw type) là một lớp hoặc giao diện kiểu chung (Generic class/interface) được sử dụng **không** đi kèm với bất kỳ đối số kiểu (Type argument) nào.

```java
// Generic (correct)
List<String> names = new ArrayList<>();

// Raw type (avoid)
List rawList = new ArrayList();
```

**Cách thức hoạt động:** Trình biên dịch đối xử với kiểu nguyên bản như thể tất cả các tham số kiểu (Type parameter) được thay thế bằng `Object`. Toàn bộ tính năng an toàn kiểu (Type safety) của generic đều bị vô hiệu hóa.

**Ảnh hưởng đối với mã nguồn:**
```java
List rawList = new ArrayList();
rawList.add("hello");
rawList.add(42);            // no compile error — anything goes
String s = (String) rawList.get(1);  // ClassCastException at runtime!
```

**Cảnh báo của trình biên dịch:** Sử dụng kiểu nguyên bản kích hoạt các cảnh báo chưa được kiểm tra (Unchecked warning):
```
Note: MyClass.java uses unchecked or unsafe operations.
```

**Khi kiểu nguyên bản xuất hiện hợp lệ:**
1. Tương tác với các API cũ từ trước phiên bản Java 5 không hỗ trợ kiểu chung.
2. Bên trong các phép kiểm tra `instanceof` (dù thế nào bạn cũng không thể sử dụng `instanceof List<String>`):
   ```java
   if (obj instanceof List) {         // raw — necessary here
       List<?> list = (List<?>) obj;  // immediately switch to wildcard
   }
   ```

**Quy tắc mấu chốt:** Ngay sau khi bạn gán một kiểu nguyên bản cho một biến, hãy sử dụng `List<?>` (thay vì kiểu nguyên bản) cho phần còn lại của mã nguồn.

---

## 2. Hạn Chế của Kiểu Chung (Generic Limitations)

Kiểu chung trong Java có một số hạn chế được thiết kế sẵn, hầu hết đều do cơ chế **xóa kiểu (Type erasure)** gây ra.

### 2.1 Không thể Khởi tạo Tham số Kiểu

```java
class Container<T> {
    T value = new T();   // COMPILE ERROR — T erased to Object at runtime
}
```

**Giải pháp:** Truyền vào một mã nhận diện `Class<T>` (Class token):
```java
class Container<T> {
    T value;
    Container(Class<T> clazz) throws Exception {
        value = clazz.getDeclaredConstructor().newInstance();
    }
}
```

---

### 2.2 Không thể Tạo Mảng của Kiểu Chung

```java
T[] arr = new T[10];              // COMPILE ERROR
List<String>[] lists = new ArrayList<String>[3];  // COMPILE ERROR
```

**Tại sao:** Mảng mang thông tin kiểu thành phần của chúng tại thời điểm chạy (mảng `String[]` biết nó là một mảng `String[]`). Sau khi xóa kiểu, `T[]` chỉ đơn thuần là `Object[]`, làm phá vỡ tính an toàn kiểu của mảng.

**Giải pháp:**
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

### 2.3 Không thể Dùng Kiểu Nguyên Thủy làm Đối số Kiểu

```java
List<int> nums = new ArrayList<>();   // COMPILE ERROR
```

**Tại sao:** Kiểu chung được triển khai thông qua các tham chiếu `Object`; kiểu nguyên thủy (Primitive type) không phải là đối tượng.

**Giải pháp:** Sử dụng các lớp bao bọc đối tượng (Wrapper class). Cơ chế tự động đóng hộp (Autoboxing) giúp quá trình này diễn ra khá tự nhiên:
```java
List<Integer> nums = new ArrayList<>();
nums.add(1);            // autoboxed to Integer
int n = nums.get(0);    // unboxed to int
```

**Lưu ý hiệu năng:** Tự động đóng hộp có chi phí bổ sung. Đối với mã nguồn yêu cầu hiệu năng cực cao, hãy cân nhắc sử dụng `int[]` hoặc các thư viện tập hợp kiểu nguyên thủy của bên thứ ba.

---

### 2.4 Không thể Có Trường Tĩnh thuộc Kiểu của Tham số Kiểu

```java
class Bag<T> {
    static T instance;   // COMPILE ERROR — static belongs to class, not T
}
```

**Tại sao:** Các trường tĩnh (`static`) được chia sẻ giữa toàn bộ các thực thể của lớp `Bag`. `Bag<String>` và `Bag<Integer>` dùng chung một định nghĩa lớp duy nhất, nên việc có một trường tĩnh `T` dùng chung là vô nghĩa.

**Giải pháp:** Chuyển trường đó thành không tĩnh (non-static), hoặc sử dụng một tham số `Class<T>` riêng biệt.

---

### 2.5 Không thể Catch hoặc Throw Ngoại lệ thuộc Kiểu Chung

```java
class MyException<T> extends Exception { ... }   // COMPILE ERROR (extends Throwable)
// (legal to declare but not to catch with a generic type argument)

<T extends Exception> void process() throws T { }   // OK to declare
try { } catch (T e) { }                             // COMPILE ERROR in catch
```

**Tại sao:** JVM khớp các kiểu ngoại lệ tại thời điểm chạy; các kiểu bị xóa thông tin không thể sử dụng trong mệnh đề `catch`.

---

### 2.6 Không thể Nạp chồng các Phương thức có Danh sách Tham số Bị xóa thành Cùng một Chữ ký

```java
void print(List<String> list) { }
void print(List<Integer> list) { }  // COMPILE ERROR — both erase to print(List)
```

---

### 2.7 Không thể Sử dụng `instanceof` với các Kiểu được Tham số hóa

```java
if (obj instanceof List<String>) { }   // COMPILE ERROR — type info erased
if (obj instanceof List<?>)      { }   // OK — unbounded wildcard is allowed
if (obj instanceof List)         { }   // OK — raw type check
```

---

## Bảng Tóm Tắt

## Tại sao Kiểu Nguyên Bản Tồn tại và Mối Nguy hiểm của Chúng

Kiểu nguyên bản chỉ tồn tại trong ngôn ngữ Java nhằm mục đích duy nhất là duy trì khả năng tương thích ngược với mã nguồn cũ được viết trước phiên bản Java 5. Trước khi kiểu chung được giới thiệu, các tập hợp chỉ nắm giữ các tham chiếu `Object`, và kiểu nguyên bản cho phép đoạn mã cũ này biên dịch và chạy trên các môi trường chạy hiện đại mà không cần sửa đổi. Tuy nhiên, việc sử dụng kiểu nguyên bản trong mã mới sẽ bỏ qua mọi hoạt động xác minh an toàn kiểu của trình biên dịch. Do trình biên dịch không thực hiện kiểm tra kiểu trên các tập hợp nguyên bản, nó cho phép lập trình viên chèn các kiểu không khớp vào tập hợp mà không đưa ra bất kỳ cảnh báo biên dịch nào. Vi phạm an toàn kiểu thực tế sau đó sẽ bị đẩy xuống thời điểm chạy, nơi việc đọc một phần tử và cố gắng ép kiểu nó sang một kiểu không chính xác sẽ ném ra ngoại lệ `ClassCastException` và làm sập ứng dụng.

### Mô hình Tư duy

```text
Ý định của Lập trình viên: Danh sách các Chuỗi (Strings)
[List rawList] = new ArrayList() ---> Chấp nhận "Hello" (Hợp lệ)
                                 ---> Chấp nhận 123 (Chưa kiểm tra: Hợp lệ!)

Đọc lúc Chạy:
String s = (String) rawList.get(1) ---> Ép kiểu Integer (123) sang String
                                    ---> SẬP: ClassCastException
```

### Ví dụ Thực Tế

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

### Chuỗi Nguyên nhân - Kết quả

Sử dụng kiểu nguyên bản &rarr; Trình biên dịch vô hiệu hóa kiểm tra kiểu chung &rarr; Các đối tượng không khớp kiểu được đưa vào tập hợp &rarr; Mã nguồn biên dịch thành công không có lỗi &rarr; Lập trình viên cố gắng đọc và ép kiểu đối tượng tại thời điểm chạy &rarr; JVM ném ra lỗi ClassCastException.

## Tại sao Kiểu Chung không Hỗ trợ Kiểu Nguyên Thủy

Do cơ chế xóa kiểu tại thời điểm biên dịch, tất cả các tham số kiểu chung trong Java đều bị xóa về cận trái nhất của chúng, thường là `Object` nếu không có giới hạn (unbounded). Trong Máy ảo Java (JVM), các tham chiếu đến đối tượng được biểu diễn trong mã byte bởi các ô tham chiếu (Reference slot) (sử dụng tiền tố `a` trong các lệnh bytecode như `aload` và `astore`). Các kiểu nguyên thủy, như `int` hoặc `char`, không kế thừa từ `java.lang.Object` và được lưu trữ bằng các kích thước nhị phân và các lệnh bytecode khác nhau (như `iload` cho số nguyên). Do JVM không thể lưu trữ trực tiếp một kiểu nguyên thủy trong một ô nhớ được chỉ định cho các tham chiếu đối tượng, kiểu chung không thể hỗ trợ kiểu nguyên thủy một cách tự nhiên. Kết quả là, Java yêu cầu các lớp bao bọc đối tượng (như `Integer`) và sử dụng cơ chế chuyển đổi tự động (tự động đóng hộp) để bọc các kiểu nguyên thủy vào các đối tượng được phân bổ trên heap khi lưu trữ chúng trong các cấu trúc kiểu chung.

### Mô hình Tư duy

```text
Biểu diễn bộ nhớ trong JVM:
Generic Box<T> (Bị xóa thành tham chiếu Object):
[ Ô tham chiếu (4/8 bytes) ] ---> Trỏ tới Đối tượng Heap: [ Integer (123) ]
                                                           (Lớp bao đóng hộp)

Không thể lưu trữ trực tiếp kiểu nguyên thủy:
[ Ô tham chiếu (4/8 bytes) ] -x-> Không thể chứa kiểu int nhị phân 32-bit [ 123 ]
```

### Ví dụ Thực Tế

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

### Chuỗi Nguyên nhân - Kết quả

Kiểu chung trải qua quá trình Xóa kiểu &rarr; Các tham số kiểu bị xóa thành tham chiếu Object &rarr; JVM biểu diễn các tham chiếu khác với kiểu nguyên thủy &rarr; Kiểu nguyên thủy không thể chiếm dụng các ô nhớ chỉ dành cho tham chiếu &rarr; Khai báo List<int> bị cấm &rarr; Bắt buộc phải sử dụng List<Integer> với cơ chế đóng hộp.

## Tại sao Việc Tạo Mảng Kiểu Chung và Kiểm tra Kiểu lúc Chạy bị Cấm

Trong Java, các mảng được hiện thực hóa kiểu (Reified), nghĩa là chúng giữ đầy đủ thông tin về kiểu phần tử của chúng tại thời điểm chạy và thực thi tính an toàn kiểu thông qua các kiểm tra của JVM. Nếu bạn cố gắng lưu trữ một phần tử không tương thích vào một mảng, JVM sẽ ngay lập tức ném ra ngoại lệ `ArrayStoreException` tại thời điểm chạy. Ngược lại, kiểu chung bị xóa kiểu (Erased), nghĩa là tất cả thông tin tham số kiểu đều bị loại bỏ sau khi biên dịch. Nếu việc tạo mảng kiểu chung như `new T[10]` hoặc `new List<String>[10]` được cho phép, JVM sẽ không có cách nào để thực thi đúng kiểu phần tử tại thời điểm chạy vì kiểu thành phần thực tế đã bị xóa về `Object[]`. Vì những lý do tương tự, các kiểm tra kiểu tại thời điểm chạy như `instanceof List<String>` bị cấm, vì tham số kiểu bị thiếu tại thời điểm chạy, khiến JVM chỉ có thể kiểm tra kiểu nguyên bản `instanceof List`.

### Mô hình Tư duy

```text
Mảng (Được hiện thực hóa kiểu - Biết kiểu tại thời điểm chạy):
String[] strings = new String[5]; ---> JVM biết đây là [Ljava.lang.String;
strings[0] = "hello";             ---> Hợp lệ
((Object[]) strings)[1] = 123;    ---> JVM kiểm tra kiểu lúc chạy ---> Ném ArrayStoreException

Kiểu chung (Bị xóa kiểu - Mất kiểu tại thời điểm chạy):
List<String> list = new ArrayList<>(); ---> JVM chỉ biết đây là List
```

### Ví dụ Thực Tế

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

### Chuỗi Nguyên nhân - Kết quả

Mảng được hiện thực hóa kiểu &rarr; Mảng thực thi chính xác kiểu phần tử tại thời điểm chạy qua JVM &rarr; Kiểu chung bị xóa kiểu &rarr; Tham số kiểu chung bị mất tại thời điểm chạy &rarr; JVM không thể thực thi tính an toàn kiểu của Mảng Kiểu Chung tại thời điểm chạy &rarr; Việc tạo mảng kiểu chung bị cấm.

## Liên kết Tham khảo

- https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html
- https://docs.oracle.com/javase/tutorial/java/generics/erasure.html
- https://docs.oracle.com/javase/tutorial/java/generics/rawTypes.html
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-4.html#jls-4.8 (Kiểu Nguyên Bản)
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-10.html (Mảng)
