# Thuật ngữ Phản chiếu (Reflection Terms)

Sử dụng tệp này khi một từ trong phần lý thuyết có cảm giác quá ngắn gọn. Mỗi thuật ngữ đều có ý nghĩa, tầm quan trọng, hiểu lầm phổ biến và một ví dụ nhỏ.

## Phản chiếu (reflection)

Phản chiếu (Reflection) là một khả năng tại thời điểm chạy (runtime) của Máy ảo Java (JVM) cho phép một chương trình kiểm tra, truy vấn và sửa đổi siêu dữ liệu (metadata) cũng như hành vi của các lớp, giao diện, thuộc tính, phương thức và hàm khởi tạo của chính nó trong quá trình thực thi.

* **Tại sao nó quan trọng**: Nó cho phép các khung công tác tổng quát (như Spring, Hibernate, hoặc Jackson) hoạt động trên các lớp do người dùng định nghĩa mà không cần biết về các lớp đó tại thời điểm biên dịch, cho phép cấu hình mang tính khai báo (declarative) và tự động ánh xạ.
* **Hiểu lầm phổ biến**: Các nhà phát triển thường cho rằng phản chiếu là phép thuật tại thời điểm biên dịch. Trên thực tế, phản chiếu diễn ra hoàn toàn lúc chạy, bỏ qua việc kiểm tra kiểu tĩnh (static type-checking) và xác thực quyền truy cập tại thời điểm biên dịch.
* **Ví dụ nhỏ**:
  ```java
  // Bypasses static checks to invoke a method by name at runtime
  java.lang.reflect.Method method = target.getClass().getMethod("toString");
  Object result = method.invoke(target);
  ```

## Đối tượng Class (Class object)

Một đối tượng `java.lang.Class` là một biểu diễn siêu dữ liệu (metadata) singleton đặc biệt được tạo ra bởi JVM cho mỗi lớp Java, giao diện (interface), kiểu mảng, kiểu nguyên thủy, hoặc từ khóa `void` được tải vào.

* **Tại sao nó quan trọng**: Nó đóng vai trò là điểm khởi đầu chính cho API Reflection. Nếu không có tham chiếu đến một đối tượng `Class`, bạn không thể lấy các phương thức, thuộc tính, hàm khởi tạo, hoặc chú thích (annotations).
* **Hiểu lầm phổ biến**: Nhầm lẫn đối tượng `Class` (đại diện cho siêu dữ liệu lớp, được cấp phát trong vùng nhớ Metaspace) với một thực thể (instance) của lớp đó (chứa dữ liệu đối tượng thực tế, được cấp phát trên bộ nhớ Heap).
* **Ví dụ nhỏ**:
  ```java
  Class<String> stringMetadata = String.class; // Class literal
  System.out.println("Simple Name: " + stringMetadata.getSimpleName()); // "String"
  ```

## Thuộc tính (Field)

Một đối tượng `java.lang.reflect.Field` đại diện cho siêu dữ liệu và chi tiết truy cập của một biến duy nhất của một lớp hoặc giao diện (thuộc tính tĩnh hoặc thuộc tính thực thể).

* **Tại sao nó quan trọng**: Nó cho phép đọc và ghi các giá trị thuộc tính một cách động tại thời điểm chạy, ngay cả khi các thuộc tính đó được khai báo là `private` hoặc `protected`.
* **Hiểu lầm phổ biến**: Nhầm lẫn giữa `getField(name)` (chỉ trả về các thuộc tính public, bao gồm cả các thuộc tính được kế thừa) với `getDeclaredField(name)` (trả về bất kỳ thuộc tính nào được khai báo trực tiếp bên trong lớp, nhưng không bao gồm các thuộc tính được kế thừa).
* **Ví dụ nhỏ**:
  ```java
  java.lang.reflect.Field field = MyClass.class.getDeclaredField("secretCode");
  field.setAccessible(true);
  field.set(myInstance, 42); // Modifies the private field value
  ```

## Phương thức (Method)

Một đối tượng `java.lang.reflect.Method` đại diện cho một phương thức đơn lẻ (tĩnh hoặc thực thể) được định nghĩa trên một lớp hoặc giao diện, cho phép thực thi động.

* **Tại sao nó quan trọng**: Nó cho phép gọi động hành vi dựa trên tên chuỗi và kiểu tham số, điều này rất cần thiết cho các bộ chạy kiểm thử tùy chỉnh (custom test runners) hoặc các công cụ định tuyến (routing engines).
* **Hiểu lầm phổ biến**: Quên rằng các ngoại lệ ném ra trong quá trình thực thi một phương thức được gọi động sẽ không được ném ra trực tiếp; chúng được bắt bởi công cụ phản chiếu của JVM và bao bọc bên trong một ngoại lệ đã được kiểm tra (checked exception) là `java.lang.reflect.InvocationTargetException`.
* **Ví dụ nhỏ**:
  ```java
  java.lang.reflect.Method m = Math.class.getMethod("abs", double.class);
  double result = (double) m.invoke(null, -10.5); // Static call passes null
  System.out.println(result); // 10.5
  ```

## Hàm khởi tạo (Constructor)

Một đối tượng `java.lang.reflect.Constructor` đại diện cho một hàm khởi tạo của lớp, cung cấp siêu dữ liệu về các tham số của hàm khởi tạo và cho phép khởi tạo đối tượng động.

* **Tại sao nó quan trọng**: Nó là tiêu chuẩn hiện đại để khởi tạo các lớp một cách động (thông qua `constructor.newInstance()`), thay thế cho phương thức đã lỗi thời và không an toàn `Class.newInstance()`.
* **Hiểu lầm phổ biến**: Các nhà phát triển nghĩ rằng `Class.newInstance()` giống hệt với `Constructor.newInstance()`. Tuy nhiên, `Class.newInstance()` bỏ qua các kiểm tra thời điểm biên dịch đối với các ngoại lệ đã được kiểm tra và chỉ có thể gọi các hàm khởi tạo không tham số công khai (public no-arg), trong khi `Constructor.newInstance()` có thể gọi bất kỳ hàm khởi tạo nào (ngay cả các hàm khởi tạo private nếu được cấp quyền truy cập) và bao bọc các ngoại lệ một cách chính xác.
* **Ví dụ nhỏ**:
  ```java
  java.lang.reflect.Constructor<String> c = String.class.getConstructor(byte[].class);
  String str = c.newInstance(new byte[]{72, 101, 108, 108, 111}); // "Hello"
  ```

## Truy cập private (private access)

Truy cập private đề cập đến các ràng buộc đóng gói (Encapsulation) ở cấp độ ngôn ngữ (ví dụ: từ khóa sửa đổi `private`). Trong phản chiếu, ranh giới này có thể bị vượt qua bằng cách sử dụng `AccessibleObject.setAccessible(true)`.

* **Tại sao nó quan trọng**: Việc vượt qua truy cập private là nền tảng cơ học của tiêm phụ thuộc (dependency injection — DI) và tuần tự hóa (serialization) dựa trên khung công tác, cho phép các khung công tác liên kết các trường hoặc tuần tự hóa trạng thái mà không buộc các nhà phát triển phải viết các phương thức getter/setter công khai.
* **Hiểu lầm phổ biến**: Giả định rằng `setAccessible(true)` thay đổi vĩnh viễn cấp độ truy cập thực tế của trường hoặc định nghĩa lớp. Nó chỉ ghi đè các cờ kiểm tra truy cập đối với thực thể `Field`, `Method`, hoặc `Constructor` cụ thể đó trong mã nguồn của bạn.
* **Ví dụ nhỏ**:
  ```java
  java.lang.reflect.Field f = Target.class.getDeclaredField("privateKey");
  f.setAccessible(true); // Bypasses private access check for subsequent reads/writes
  Object value = f.get(instance);
  ```
