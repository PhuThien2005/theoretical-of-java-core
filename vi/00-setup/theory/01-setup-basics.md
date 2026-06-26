# Kiến Thức Cơ Bản Về Cài Đặt (Setup Basics)

Việc cài đặt môi trường chính xác và hiểu cách Java biên dịch (compile) cũng như thực thi (execute) mã nguồn là nền tảng để trở thành một lập trình viên Java. Mặc dù các IDE che giấu các chi tiết này, nhưng việc hiểu cơ chế bên dưới là rất quan trọng để gỡ lỗi (debug) các vấn đề lúc chạy (runtime) và hiểu kiến trúc của Java.

---

## JDK vs JRE

Để chạy và xây dựng các ứng dụng Java, bạn cần hiểu mối quan hệ giữa **Máy ảo Java (Java Virtual Machine - JVM)**, **Môi trường chạy Java (Java Runtime Environment - JRE)**, và **Bộ công cụ phát triển Java (Java Development Kit - JDK)**.

### Cơ Chế (The Mechanism)

**Đặc tả ngôn ngữ Java (Java Language Specification - JLS)** định nghĩa cú pháp, ngữ pháp và các quy tắc biên dịch (compile-time) của ngôn ngữ Java (những gì tạo nên một chương trình Java hợp lệ). **Đặc tả Máy ảo Java (Java Virtual Machine Specification - JVMS)** định nghĩa cấu trúc của các tệp lớp (class files) và tập lệnh được JVM thực thi lúc chạy (runtime).

- **JRE (Java Runtime Environment):** Là môi trường thực thi (chạy) chương trình. Nó chứa JVM và các thư viện cốt lõi (như `java.lang`, `java.util`) cần thiết để chạy mã thực thi (bytecode) Java. JRE không thể biên dịch mã nguồn Java; nó chỉ thực thi các tệp `.class` đã được biên dịch.
- **JDK (Java Development Kit):** Là môi trường phát triển. Nó là một tập hợp mẹ của JRE, nghĩa là nó chứa JRE cùng với các công cụ phát triển như trình biên dịch (`javac`), trình đóng gói (`jar`), và trình gỡ lỗi (debugger). JDK biên dịch mã nguồn tuân thủ JLS và chạy nó tuân thủ JVMS và **Mô hình bộ nhớ Java (Java Memory Model - JMM)**, mô hình quản lý việc đồng bộ hóa luồng (thread synchronization) và khả năng hiển thị bộ nhớ (memory visibility).

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

### Phép Ẩn Dụ Cuốn Sách Công Thức Nấu Ăn (The Recipe Book Analogy)

* **JLS (Đặc tả - Specification):** Đây là cuốn sách công thức nấu ăn chính thức. Nó quy định chính xác các nguyên liệu và các bước cần thiết để mô tả một món ăn. Nó không trực tiếp nấu món ăn; nó chỉ định nghĩa một công thức hợp lệ trông như thế nào.
* **JVM (Thực thi - Execution):** Đây là nhà bếp và đầu bếp. Đầu bếp đọc các hướng dẫn công thức đã được biên dịch (bytecode) và nấu món ăn (thực thi mã trên phần cứng).
* **JRE (Gói chạy chương trình - Runtime Package):** Đây là toàn bộ nhà hàng gồm phòng ăn và nhà bếp. Nó cung cấp đầu bếp (JVM) và các nguyên liệu/dụng cụ cơ bản trong phòng chứa đồ (các thư viện cốt lõi) để phục vụ món ăn cho khách hàng.
* **JDK (Gói phát triển - Development Package):** Đây là phòng thí nghiệm khoa học thực phẩm và bếp thử nghiệm. Nó chứa nhà hàng (JRE), cùng với các công cụ để viết các công thức nấu ăn mới, thử nghiệm các nguyên liệu mới và in các cuốn sách công thức nấu ăn (các công cụ biên dịch và phát triển).

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

```text
Lập trình viên viết HelloWorld.java 
  ↓ (Cần JDK)
Trình biên dịch 'javac' của JDK biên dịch mã nguồn theo các quy tắc JLS 
  ↓ (Tạo ra bytecode)
Trình biên dịch xuất ra HelloWorld.class 
  ↓ (Cần JRE/JVM)
Lệnh 'java' của JRE nạp JVM và các thư viện runtime
  ↓ (Thực thi)
JVM thực thi các lệnh bytecode trên nền tảng phần cứng đích
```

---

## Tại Sao Tên Tệp Phải Trùng Với Tên Lớp Public (Why Filename Must Match Public Class Name)

Trong Java, nếu bạn có một tệp chứa một lớp (class) được khai báo là `public`, tên tệp phải khớp chính xác với tên của lớp public đó (bao gồm cả phân biệt chữ hoa - chữ thường), kèm theo phần mở rộng `.java`.

### Cơ Chế: Nạp Lớp Của Trình Biên Dịch (The Mechanism: Compiler Class Loading)

Hạn chế này không phải là ngẫu nhiên; nó được thiết kế để tối ưu hóa tốc độ biên dịch thông qua cơ chế nạp lớp (class loading) và phân giải (resolution) của trình biên dịch.

1. **Phân giải phụ thuộc khi biên dịch (Compilation Dependency Resolution):** Khi biên dịch một lớp `A`, nó có thể tham chiếu đến một lớp `B` khác. Nếu tệp `B.class` chưa tồn tại, trình biên dịch phải tìm tệp nguồn `B.java` để biên dịch nó ngay lập tức.
2. **Hiệu suất tìm kiếm (Search Performance):** Nếu không có quy tắc khớp tên tệp, trình biên dịch sẽ phải mở và phân tích cú pháp (parse) từng tệp `.java` trong thư mục nguồn và classpath để kiểm tra xem nó có chứa khai báo `public class B` hay không. Đối với một dự án có 10.000 tệp, việc này sẽ yêu cầu `O(N)` lượt đọc tệp, làm cho quá trình biên dịch trở nên cực kỳ chậm.
3. **Tra cứu tức thì (Instant Lookup - `O(1)`):** Bằng cách bắt buộc `public class B` phải nằm trong tệp `B.java`, trình biên dịch có thể xác định ngay vị trí tệp bằng cách thực hiện tìm kiếm tệp trực tiếp trên hệ thống tệp: `sourcepath/B.java`. Điều này giảm độ phức tạp tìm kiếm xuống `O(1)`.
4. **Ràng buộc một lớp Public (One Public Class Constraint):** Do yêu cầu ánh xạ 1-1 này giữa tên tệp và tên lớp public, một tệp nguồn Java chỉ có thể chứa tối đa một lớp `public` (mặc dù nó có thể chứa nhiều lớp non-public/package-private khác).

### Ví Dụ Lỗi Biên Dịch (Compilation Failure Example)

Nếu bạn định nghĩa một lớp tên là `MyCoolProgram` là public, nhưng lưu nó trong một tệp tên là `Runner.java`:

```java
// Được lưu trong tệp: Runner.java
public class MyCoolProgram {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Việc cố gắng biên dịch tệp này sẽ dẫn đến lỗi sau:

```text
$ javac Runner.java
Runner.java:2: error: class MyCoolProgram is public, should be declared in a file named MyCoolProgram.java
public class MyCoolProgram {
       ^
1 error
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

```text
Lớp B tham chiếu đến Lớp A
  ↓
Trình biên dịch tìm kiếm định nghĩa Lớp A
  ↓
Quy tắc đặt tên bắt buộc cho phép trình biên dịch tìm kiếm trực tiếp 'A.java' trong thời gian O(1)
  ↓
Không cần quét và phân tích cú pháp các tệp nguồn khác → Thời gian biên dịch nhanh
```

---

## Biên Dịch và Thực Thi Bằng Terminal vs IDE (Terminal vs IDE Compilation and Execution)

Có hai cách chính để biên dịch và chạy các chương trình Java: sử dụng các lệnh terminal thuần túy hoặc sử dụng Môi trường phát triển tích hợp (Integrated Development Environment - IDE) như IntelliJ IDEA, VS Code, hoặc Eclipse.

### Cơ Chế (The Mechanism)

* **Biên dịch & Thực thi bằng Terminal (Terminal Compilation & Execution):**
  Bạn tương tác trực tiếp với các tệp thực thi của JDK. Bạn chạy lệnh `javac` để gọi trình biên dịch, trình biên dịch này sẽ dịch mã nguồn dễ đọc của con người thành mã thực thi độc lập với nền tảng (bytecode) (trong các tệp `.class`). Sau đó, bạn chạy lệnh `java` để gọi JVM, chương trình này sẽ nạp tệp class, xác thực bytecode và thực thi nó.
* **Quản lý tự động bằng IDE (IDE Automated Management):**
  Một IDE đóng gói các lệnh này trong một giao diện đồ họa. Thay vì biên dịch thủ công, IDE giám sát các thay đổi của tệp và thực hiện **biên dịch gia tăng (incremental compilation)** trong nền, chỉ biên dịch các tệp đã thay đổi. Nó tự động quản lý **classpath** (nơi Java tìm kiếm các thư viện phụ thuộc) và tích hợp phân tích tĩnh thời gian thực (linting) để hiển thị lỗi trước khi bạn biên dịch.

```mermaid
flowchart TD
    subgraph Terminal Workflow (Manual)
        A1["Viết HelloWorld.java"] --> A2["Chạy: javac HelloWorld.java"]
        A2 --> A3["Tạo HelloWorld.class"]
        A3 --> A4["Chạy: java HelloWorld"]
        A4 --> A5["JVM thực thi chương trình"]
    end

    subgraph IDE Workflow (Automated)
        B1["Viết mã trong IDE"] --> B2["Trình biên dịch gia tăng chạy ẩn"]
        B2 --> B3["IDE gắn cờ lỗi cú pháp ngay lập tức"]
        B3 --> B4["Nhấp vào nút Run"]
        B4 --> B5["IDE cấu hình Classpath & khởi chạy JVM"]
    end
```

### Bảng So Sánh Tóm Tắt (Contrast Summary)

| Tính năng | Biên dịch Terminal (`javac`/`java`) | Biên dịch IDE (IntelliJ, VS Code) |
|---|---|---|
| **Biên dịch** | Gọi thủ công lệnh `javac file.java`. | Biên dịch gia tăng tự động trong nền. |
| **Quản lý Classpath** | Phải được chỉ định thủ công qua `-cp` hoặc `-classpath`. | Được quản lý tự động thông qua các tệp build dự án (Maven/Gradle). |
| **Phản hồi lỗi** | Chỉ hiển thị sau khi chạy lệnh biên dịch. | Được làm nổi bật ngay lập tức trong trình soạn thảo mã (phân tích tĩnh). |
| **Trường hợp sử dụng** | Học các kiến thức cơ bản, viết kịch bản (scripting), pipeline CI/CD. | Phát triển chuyên nghiệp, tái cấu trúc (refactoring), gỡ lỗi các hệ thống phức tạp. |

### Ví Dụ Mã Nguồn: Biên Dịch và Thực Thi Thủ Công (Code Example: Manual Compilation and Execution)

Hãy cùng xem cấu trúc Hello World tiêu chuẩn và các lệnh thực thi nó:

```java
// Được lưu trong tệp: HelloWorld.java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!"); // Đầu ra: Hello, World!
    }
}
```

Các lệnh chạy trong terminal:

```bash
# 1. Kiểm tra phiên bản trình biên dịch
$ javac -version
$ javac 21.0.2

# 2. Biên dịch tệp nguồn thành bytecode (.class)
$ javac HelloWorld.java

# 3. Xác minh tệp .class đã được tạo
$ ls
HelloWorld.class  HelloWorld.java

# 4. Thực thi bytecode trên JVM (KHÔNG bao gồm phần mở rộng .class)
$ java HelloWorld
Hello, World!
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

```text
Chạy lệnh 'javac HelloWorld.java'
  ↓
Trình biên dịch phân tích cú pháp mã → kiểm tra sự tuân thủ JLS → tạo bytecode JVM
  ↓
Bytecode được ghi vào 'HelloWorld.class'
  ↓
Chạy lệnh 'java HelloWorld'
  ↓
Trình nạp lớp (class loader) của JVM lấy 'HelloWorld.class' → Trình xác thực Bytecode kiểm tra bảo mật → JIT/Trình thông dịch thực thi main()
```

---

## Liên Kết Tham Chiếu (Reference Links)

- [Oracle Java Tutorials: Getting Started](https://docs.oracle.com/javase/tutorial/getStarted/cupojava/index.html)
- [Java Language Specification (JLS) - Class Declarations](https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.1)
- [JVM Specification - Run-Time Data Areas](https://docs.oracle.com/javase/specs/jvms/se21/html/jvms-2.html#jvms-2.5)
