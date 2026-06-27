# Thuật ngữ về Phản chiếu (Reflection Terms)

Sử dụng tài liệu này khi một từ khóa trong phần lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều có định nghĩa, tầm quan trọng, hiểu lầm thường gặp và một ví dụ nhỏ.

## phản chiếu (reflection)

Phản chiếu (reflection) là một khả năng tại thời điểm chạy (runtime) của Máy ảo Java (JVM) cho phép một chương trình kiểm tra, truy vấn, và sửa đổi siêu dữ liệu (metadata) cũng như hành vi của các lớp, giao diện, trường, phương thức, và hàm khởi tạo của chính nó trong quá trình thực thi.

* **Tầm quan trọng**: Nó cho phép các framework tổng quát (như Spring, Hibernate, hoặc Jackson) hoạt động trên các lớp do người dùng định nghĩa mà không cần biết về các lớp đó tại thời điểm biên dịch, cho phép cấu hình khai báo và tự động ánh xạ.
* **Hiểu lầm thường gặp**: Các nhà phát triển thường cho rằng phản chiếu là một phép thuật tại thời điểm biên dịch. Trong thực tế, phản chiếu diễn ra hoàn toàn tại thời điểm chạy, bỏ qua các kiểm tra kiểu tĩnh và xác thực quyền truy cập tại thời điểm biên dịch.
* **Ví dụ nhỏ**:
  ```java
  // Bypasses static checks to invoke a method by name at runtime
  java.lang.reflect.Method method = target.getClass().getMethod("toString");
  Object result = method.invoke(target);
  ```

## đối tượng Class (Class object)

Một đối tượng `java.lang.Class` là một biểu diễn siêu dữ liệu singleton đặc biệt được JVM tạo ra cho mỗi lớp Java, giao diện, kiểu mảng, kiểu nguyên thủy, hoặc từ khóa `void` được tải lên.

* **Tầm quan trọng**: Nó đóng vai trò là điểm bắt đầu chính cho API Reflection. Nếu không có tham chiếu đến một đối tượng `Class`, bạn không thể lấy ra các phương thức, trường, hàm khởi tạo, hoặc chú thích (annotation).
* **Hiểu lầm thường gặp**: Nhầm lẫn đối tượng `Class` (đại diện cho siêu dữ liệu của lớp, được cấp phát trong Metaspace) với một thực thể của lớp đó (nắm giữ dữ liệu thực tế của đối tượng, được cấp phát trên Heap).
* **Ví dụ nhỏ**:
  ```java
  Class<String> stringMetadata = String.class; // Class literal
  System.out.println("Simple Name: " + stringMetadata.getSimpleName()); // "String"
  ```

## trường (Field)

Một đối tượng `java.lang.reflect.Field` đại diện cho siêu dữ liệu và chi tiết truy cập của một biến duy nhất trong một lớp hoặc giao diện (trường tĩnh static hoặc trường thực thể instance field).

* **Tầm quan trọng**: Nó cho phép đọc và ghi các giá trị của trường một cách động tại thời điểm chạy, ngay cả khi các trường đó được khai báo là `private` hoặc `protected`.
* **Hiểu lầm thường gặp**: Nhầm lẫn giữa `getField(name)` (chỉ trả về các trường public, bao gồm cả các trường được kế thừa) với `getDeclaredField(name)` (trả về bất kỳ trường nào được khai báo trực tiếp bên trong lớp, nhưng không bao gồm các trường được kế thừa).
* **Ví dụ nhỏ**:
  ```java
  java.lang.reflect.Field field = MyClass.class.getDeclaredField("secretCode");
  field.setAccessible(true);
  field.set(myInstance, 42); // Modifies the private field value
  ```

## phương thức (Method)

Một đối tượng `java.lang.reflect.Method` đại diện cho một phương thức duy nhất (tĩnh hoặc thực thể) được định nghĩa trên một lớp hoặc giao diện, cho phép thực thi động.

* **Tầm quan trọng**: Nó cho phép gọi động hành vi dựa trên tên chuỗi và các kiểu tham số, điều này rất cần thiết cho các trình chạy kiểm thử (test runner) tùy chỉnh hoặc các công cụ định tuyến (routing engine).
* **Hiểu lầm thường gặp**: Quên rằng các ngoại lệ ném ra trong quá trình thực thi một phương thức được gọi động không được ném trực tiếp; chúng được công cụ phản chiếu của JVM bắt lại và bao bọc bên trong một ngoại lệ kiểm tra (checked exception) `java.lang.reflect.InvocationTargetException`.
* **Ví dụ nhỏ**:
  ```java
  java.lang.reflect.Method m = Math.class.getMethod("abs", double.class);
  double result = (double) m.invoke(null, -10.5); // Static call passes null
  System.out.println(result); // 10.5
  ```

## hàm khởi tạo (Constructor)

Một đối tượng `java.lang.reflect.Constructor` đại diện cho một hàm khởi tạo của lớp, cung cấp siêu dữ liệu về các tham số của hàm khởi tạo và cho phép khởi tạo đối tượng động.

* **Tầm quan trọng**: Nó là tiêu chuẩn hiện đại để khởi tạo các lớp một cách động (thông qua `constructor.newInstance()`), thay thế cho phương thức đã lỗi thời và không an toàn `Class.newInstance()`.
* **Hiểu lầm thường gặp**: Các nhà phát triển thường nghĩ `Class.newInstance()` giống hệt `Constructor.newInstance()`. Tuy nhiên, `Class.newInstance()` bỏ qua các kiểm tra thời điểm biên dịch đối với các ngoại lệ được kiểm tra và chỉ có thể gọi các hàm khởi tạo public không tham số, trong khi `Constructor.newInstance()` có thể gọi bất kỳ hàm khởi tạo nào (ngay cả các hàm khởi tạo private nếu được thiết lập quyền truy cập) và bao bọc các ngoại lệ một cách chính xác.
* **Ví dụ nhỏ**:
  ```java
  java.lang.reflect.Constructor<String> c = String.class.getConstructor(byte[].class);
  String str = c.newInstance(new byte[]{72, 101, 108, 108, 111}); // "Hello"
  ```

## truy cập riêng tư (private access)

Truy cập riêng tư (private access) đề cập đến các ràng buộc đóng gói ở cấp độ ngôn ngữ (ví dụ: từ khóa bổ trợ `private`). Trong phản chiếu, ranh giới này được bỏ qua bằng cách sử dụng `AccessibleObject.setAccessible(true)`.

* **Tầm quan trọng**: Việc bỏ qua truy cập riêng tư là nền tảng kỹ thuật cho cơ chế tiêm phụ thuộc (dependency injection - DI) và tuần tự hóa (serialization) dựa trên framework, cho phép các framework liên kết các trường hoặc tuần tự thái trạng thái mà không buộc các nhà phát triển phải viết các phương thức getter/setter công khai.
* **Hiểu lầm thường gặp**: Giả định rằng `setAccessible(true)` thay đổi vĩnh viễn mức độ truy cập thực tế của trường hoặc định nghĩa lớp. Nó chỉ ghi đè cờ kiểm tra truy cập cho thực thể `Field`, `Method`, hoặc `Constructor` cụ thể đó trong mã nguồn của bạn.
* **Ví dụ nhỏ**:
  ```java
  java.lang.reflect.Field f = Target.class.getDeclaredField("privateKey");
  f.setAccessible(true); // Bypasses private access check for subsequent reads/writes
  Object value = f.get(instance);
  ```
