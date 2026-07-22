# Kiến Thức Cơ Bản Về Cài Đặt

Thiết lập môi trường đúng cách và hiểu cơ chế biên dịch, thực thi của Java là nền tảng để trở thành một lập trình viên Java. Dù các IDE che giấu nhiều chi tiết kỹ thuật, việc nắm vững cơ chế hoạt động bên dưới là điều thiết yếu để gỡ lỗi (debug) các vấn đề runtime và hiểu kiến trúc của Java.

---

## JDK Và JRE

Để chạy và xây dựng ứng dụng Java, bạn cần hiểu mối quan hệ giữa **Máy Ảo Java (JVM — Java Virtual Machine)**, **Môi Trường Thực Thi Java (JRE — Java Runtime Environment)**, và **Bộ Công Cụ Phát Triển Java (JDK — Java Development Kit)**.

### Cơ Chế Hoạt Động

**Đặc tả ngôn ngữ Java (JLS — Java Language Specification)** định nghĩa cú pháp, ngữ pháp và các quy tắc biên dịch của ngôn ngữ Java (những gì tạo nên một chương trình Java hợp lệ). **Đặc tả Máy Ảo Java (JVMS — Java Virtual Machine Specification)** định nghĩa cấu trúc file class và tập lệnh được JVM thực thi lúc chạy.

- **JRE (Môi Trường Thực Thi Java):** Là môi trường thực thi (runtime). JRE chứa JVM và các thư viện lõi (như `java.lang`, `java.util`) cần thiết để chạy bytecode Java. JRE không thể biên dịch mã nguồn Java — nó chỉ thực thi các file `.class` đã được biên dịch sẵn.
- **JDK (Bộ Công Cụ Phát Triển Java):** Là môi trường phát triển. JDK là tập cha (superset) của JRE, nghĩa là nó bao gồm JRE cùng với các công cụ phát triển như trình biên dịch (`javac`), công cụ đóng gói (`jar`), và debugger. JDK biên dịch mã nguồn tuân theo JLS và chạy nó theo JVMS và **Mô hình bộ nhớ Java (JMM — Java Memory Model)**, thứ quản lý đồng bộ hóa luồng và khả năng hiển thị bộ nhớ.

```text
+-------------------------------------------------------+
| JDK (Development Kit: javac, jar, jdb)                |
|  +-------------------------------------------------+  |
|  | JRE (Runtime Environment: libraries, rt.jar)    |  |
|  |  +-------------------------------------------+  |  |
|  |  | JVM (Virtual Machine: execution engine)   |  |  |
|  |  +-------------------------------------------+  |  |
|  +-------------------------------------------------+  |
+-------------------------------------------------------+
```

### Ẩn Dụ Quyển Sách Công Thức

* **JLS (Đặc tả):** Đây là quyển sách công thức chính thức. Nó quy định chính xác các nguyên liệu và bước thực hiện để mô tả một món ăn. Nó không nấu bữa ăn — nó chỉ định nghĩa thế nào là một công thức hợp lệ.
* **JVM (Thực thi):** Đây là bếp của đầu bếp và chính người đầu bếp. Đầu bếp đọc các hướng dẫn công thức đã biên dịch (bytecode) và nấu bữa ăn (thực thi code trên phần cứng).
* **JRE (Gói Runtime):** Đây là toàn bộ phòng ăn và nhà bếp của nhà hàng. Nó cung cấp đầu bếp (JVM) và các nguyên liệu/dụng cụ cơ bản (thư viện lõi) để phục vụ thực khách.
* **JDK (Gói Phát Triển):** Đây là phòng thí nghiệm khoa học thực phẩm và bếp thử nghiệm. Nó bao gồm nhà hàng (JRE), cộng thêm các công cụ để viết công thức mới, thử nguyên liệu mới, và in sách công thức (công cụ biên dịch và phát triển).

### Chuỗi Nguyên Nhân - Kết Quả

```text
Lập trình viên viết HelloWorld.java 
  ↓ (Cần JDK)
'javac' của JDK biên dịch mã nguồn theo quy tắc JLS 
  ↓ (Tạo ra bytecode)
Trình biên dịch xuất HelloWorld.class 
  ↓ (Cần JRE/JVM)
Lệnh 'java' của JRE nạp JVM và thư viện runtime
  ↓ (Thực thi)
JVM thực thi bytecode trên phần cứng đích
```

---

## Tại Sao Tên File Phải Trùng Với Tên Lớp Public

Trong Java, nếu một file chứa lớp được khai báo là `public`, tên file phải trùng chính xác với tên lớp public đó (bao gồm cả phân biệt chữ hoa/thường), kèm phần mở rộng `.java`.

### Cơ Chế: Nạp Lớp Của Trình Biên Dịch

Ràng buộc này không phải tùy tiện — nó được thiết kế để tối ưu hóa tốc độ biên dịch thông qua cơ chế nạp và phân giải lớp của trình biên dịch.

1. **Phân giải phụ thuộc biên dịch:** Khi biên dịch lớp `A`, nó có thể tham chiếu đến lớp `B`. Nếu `B.class` chưa tồn tại, trình biên dịch phải tìm file nguồn `B.java` để biên dịch nó ngay.
2. **Hiệu năng tìm kiếm:** Nếu không có quy tắc đặt tên theo file, trình biên dịch sẽ phải mở và phân tích cú pháp từng file `.java` trong thư mục nguồn và classpath để kiểm tra xem file đó có chứa khai báo `public class B` không. Với dự án có 10.000 file, điều này đòi hỏi `O(N)` lần đọc file, làm cho quá trình biên dịch vô cùng chậm.
3. **Tra cứu tức thì (`O(1)`):** Bằng cách quy định `public class B` phải nằm trong `B.java`, trình biên dịch có thể tìm file ngay lập tức bằng cách tra cứu trực tiếp trên hệ thống file: `sourcepath/B.java`. Điều này giảm độ phức tạp tìm kiếm xuống `O(1)`.
4. **Giới hạn một lớp public:** Do yêu cầu ánh xạ 1-1 giữa tên file và tên lớp public, một file nguồn Java chỉ có thể chứa nhiều nhất một lớp `public` (dù có thể chứa nhiều lớp không public / package-private).

### Ví Dụ Biên Dịch Thất Bại

Nếu bạn khai báo lớp `MyCoolProgram` là public nhưng lưu trong file tên `Runner.java`:

```java
// Lưu trong file: Runner.java
public class MyCoolProgram {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Cố gắng biên dịch file này sẽ gặp lỗi sau:

```text
$ javac Runner.java
Runner.java:2: error: class MyCoolProgram is public, should be declared in a file named MyCoolProgram.java
public class MyCoolProgram {
       ^
1 error
```

### Chuỗi Nguyên Nhân - Kết Quả

```text
Lớp B tham chiếu đến Lớp A
  ↓
Trình biên dịch tìm định nghĩa Lớp A
  ↓
Quy tắc đặt tên bắt buộc giúp trình biên dịch tìm trực tiếp 'A.java' trong O(1)
  ↓
Không cần quét và phân tích các file nguồn khác → Thời gian biên dịch nhanh
```

---

## Biên Dịch Và Thực Thi: Terminal Và IDE

Có hai cách chính để biên dịch và chạy chương trình Java: dùng lệnh terminal trực tiếp hoặc dùng Môi Trường Phát Triển Tích Hợp (IDE) như IntelliJ IDEA, VS Code, hoặc Eclipse.

### Cơ Chế Hoạt Động

* **Biên Dịch & Thực Thi Qua Terminal:**
  Bạn tương tác trực tiếp với các nhị phân JDK. Bạn chạy `javac` để gọi trình biên dịch, chuyển đổi mã nguồn thành bytecode độc lập nền tảng (trong file `.class`). Sau đó bạn chạy `java` để gọi JVM, nạp file class, xác minh bytecode và thực thi nó.
* **IDE Tự Động Hóa:**
  IDE bọc các lệnh này trong giao diện đồ họa. Thay vì biên dịch thủ công, IDE theo dõi thay đổi file và thực hiện **biên dịch gia tăng (incremental compilation)** trong nền, chỉ biên dịch các file đã thay đổi. Nó tự động quản lý **classpath** (nơi Java tìm kiếm phụ thuộc) và tích hợp phân tích tĩnh thời gian thực (linting) để hiển thị lỗi trước khi biên dịch.

```mermaid
flowchart TD
    subgraph Terminal Workflow (Thủ Công)
        A1[Viết HelloWorld.java] --> A2[Chạy: javac HelloWorld.java]
        A2 --> A3[Tạo HelloWorld.class]
        A3 --> A4[Chạy: java HelloWorld]
        A4 --> A5[JVM thực thi chương trình]
    end

    subgraph IDE Workflow (Tự Động)
        B1[Viết Code trong IDE] --> B2[Trình biên dịch gia tăng nền]
        B2 --> B3[IDE báo lỗi cú pháp ngay lập tức]
        B3 --> B4[Nhấn nút Run]
        B4 --> B5[IDE cấu hình Classpath & khởi chạy JVM]
    end
```

### Bảng So Sánh

**Terminal (`javac`/`java`)** yêu cầu gọi biên dịch thủ công qua lệnh `javac file.java`. Bạn phải chỉ định thủ công quá trình quản lý Classpath thông qua cờ `-cp` hoặc `-classpath`. Phản hồi lỗi chỉ hiển thị sau khi chạy lệnh biên dịch. Phương pháp này phù hợp cho việc học nền tảng, scripting, hoặc tích hợp trong pipeline CI/CD.

**IDE (IntelliJ, VS Code)** hỗ trợ biên dịch gia tăng tự động trong nền. Quản lý Classpath được xử lý tự động thông qua các file build như Maven hoặc Gradle. Phản hồi lỗi được tô sáng ngay lập tức trong trình soạn thảo thông qua phân tích tĩnh. IDE là lựa chọn tối ưu cho phát triển chuyên nghiệp, tái cấu trúc mã nguồn và debug hệ thống phức tạp.

### Ví Dụ Code: Biên Dịch Và Thực Thi Thủ Công

Cùng xem cấu trúc Hello World chuẩn và các lệnh tương ứng:

```java
// Lưu trong file: HelloWorld.java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!"); // Output: Hello, World!
    }
}
```

Các lệnh chạy trong terminal:

```bash
# 1. Kiểm tra phiên bản trình biên dịch
$ javac -version
javac 21.0.2

# 2. Biên dịch file nguồn thành bytecode (.class)
$ javac HelloWorld.java

# 3. Xác nhận file .class đã được tạo
$ ls
HelloWorld.class  HelloWorld.java

# 4. Thực thi bytecode trên JVM (KHÔNG có phần mở rộng .class)
$ java HelloWorld
Hello, World!
```

### Chuỗi Nguyên Nhân - Kết Quả

```text
Chạy 'javac HelloWorld.java'
  ↓
Trình biên dịch phân tích code → kiểm tra tuân thủ JLS → tạo bytecode JVM
  ↓
Bytecode được ghi vào 'HelloWorld.class'
  ↓
Chạy 'java HelloWorld'
  ↓
Class loader của JVM nạp 'HelloWorld.class' → Bytecode Verifier kiểm tra bảo mật → JIT/Interpreter thực thi main()
```

---

## Tài Liệu Tham Khảo

- [Oracle Java Tutorials: Getting Started](https://docs.oracle.com/javase/tutorial/getStarted/cupojava/index.html)
- [Java Language Specification (JLS) - Class Declarations](https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.1)
- [JVM Specification - Run-Time Data Areas](https://docs.oracle.com/javase/specs/jvms/se21/html/jvms-2.html#jvms-2.5)
