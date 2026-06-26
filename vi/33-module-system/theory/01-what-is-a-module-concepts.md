# Hệ thống Mô-đun Java (Java Module System) - Phần 1

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm **Hệ thống Mô-đun Java** (được giới thiệu từ Java 9 với tên gọi Project Jigsaw). Hãy nghiên cứu cách các mô-đun thực thi tính đóng gói mạnh mẽ, giải quyết "địa ngục classpath" (classpath hell), và thay đổi cách JVM nạp cũng như bảo mật các lớp.

## Phạm vi đề mục (Outline Coverage)

| Khái niệm (Concept) | Mô tả (Description) |
| --- | --- |
| `Mô-đun là gì? (What is a module?)` | Một tập hợp tự mô tả gồm mã nguồn (các gói) và dữ liệu (các tài nguyên) đi kèm với một trình mô tả mô-đun. |
| `module-info.java` | Tệp mô tả mô-đun xác định tên mô-đun, các phụ thuộc và các gói được xuất khẩu. |
| `requires` | Chỉ thị khai báo một phụ thuộc vào một mô-đun khác. |
| `exports` | Chỉ thị giúp các kiểu dữ liệu public trong một gói có thể truy cập được bởi các mô-đun khác tại thời điểm biên dịch và thời điểm chạy. |
| `opens` | Chỉ thị cho phép phản chiếu sâu (deep reflection) tại thời điểm chạy trên một gói trong khi chặn quyền truy cập tại thời điểm biên dịch. |
| `Named module` | Một mô-đun có tên được xác định trong tệp `module-info.class`, được nạp từ module path. |
| `Unnamed module` | Một mô-đun chứa tất cả cho các lớp được nạp từ classpath nhằm duy trì khả năng tương thích ngược. |
| `Automatic module` | Một mô-đun cầu nối được tạo ra khi một tệp JAR truyền thống (không có tệp `module-info.class`) được đặt trên module path. |
| `Module-level encapsulation` | Các kiểm soát truy cập mạnh mẽ được thực thi ở cấp độ JVM, chặn rò rỉ API public và phản chiếu bất hợp pháp. |
| `Module path vs Classpath` | Classpath là một danh sách phẳng, nhạy cảm với thứ tự của các tệp JAR; Module path là một tập hợp các mô-đun có tên có nhận biết cấu trúc. |

---

## Ghi chú chi tiết (Detailed Notes)

### Mô-đun là gì? (What is a Module?)

Một **mô-đun (module)** là một gói các lớp Java, các giao diện (interfaces) và tài nguyên được nhóm lại với nhau cùng với một **trình mô tả mô-đun** (`module-info.class`). Nó giới thiệu một cấp độ tổng hợp cao hơn các gói, chuyển đổi Java từ một danh sách phẳng các lớp thành một đồ thị phụ thuộc có cấu trúc.

#### Tại sao các Mô-đun giải quyết được địa ngục Classpath và các lỗ hổng bảo mật (Why Modules Solve Classpath Hell and Security Gaps)

Trong Java truyền thống (Java 8 trở về trước), tất cả các lớp được nạp qua **Classpath** được gom chung vào một không gian tên phẳng duy nhất. Điều này dẫn đến hai lỗi thiết kế nghiêm trọng:
1. **Địa ngục Classpath (Classpath Hell)**: Nếu hai tệp JAR chứa cùng một tên lớp trong cùng một gói (gọi là "chia tách gói" - split package), JVM sẽ nạp bất kỳ lớp nào xuất hiện trước trong dòng lệnh. Điều này làm cho việc triển khai ứng dụng mang tính không xác định (non-deterministic). Hơn nữa, nếu một thư viện phụ thuộc bị thiếu, trình biên dịch sẽ không biết; ứng dụng vẫn khởi động bình thường và chỉ bị sụp đổ vài giờ sau đó khi mã nguồn cố gắng thực thi lớp bị thiếu, ném ra lỗi `NoClassDefFoundError`.
2. **Tính đóng gói yếu (Weak Encapsulation)**: Từ khóa sửa đổi `public` có nghĩa là "công khai đối với toàn bộ JVM". Bất kỳ nhà phát triển nào cũng có thể nhập (import) các API nội bộ của JDK (như `sun.misc.Unsafe`) hoặc các gói nội bộ của thư viện. Điều này ngăn cản các tác giả thư viện sửa đổi các chi tiết triển khai nội bộ, vì các thay đổi sẽ làm hỏng ứng dụng của người tiêu dùng.

Hệ thống Mô-đun Java (Project Jigsaw) giải quyết những vấn đề này bằng cách giới thiệu **Tính đóng gói mạnh mẽ (Strong Encapsulation)** và **Cấu hình đáng tin cậy (Reliable Configuration)**:
- **Tính đóng gói mạnh mẽ**: Các gói bên trong một mô-đun là ẩn đối với các mô-đun khác trừ khi được xuất khẩu rõ ràng. Ngay cả khi một lớp và các phương thức của nó được khai báo là `public`, chúng vẫn không thể được truy cập bởi một mô-đun khác trừ khi mô-đun chứa lớp đó thực hiện chỉ thị `exports` cho gói của nó.
- **Cấu hình đáng tin cậy**: Các phụ thuộc được khai báo một cách tường minh. Khi khởi động, JVM sẽ kiểm tra đồ thị mô-đun. Nếu thiếu bất kỳ mô-đun yêu cầu nào, hoặc nếu có phụ thuộc vòng (cyclic dependency), JVM sẽ lập tức hủy bỏ quá trình thực thi với một lỗi rõ ràng trước khi chạy bất kỳ mã ứng dụng nào.

#### Phép so sánh: Đống hỗn độn so với các Container vận chuyển được niêm phong (Analogy: Rummaging Heap vs. Sealed Shipping Containers)
- **Classpath (Cũ)**: Một đống hỗn độn phẳng gồm các bộ phận rời rạc. Bất kỳ ai cũng có thể thò tay vào, lấy bất kỳ bộ phận nào, hoặc vô tình ghi đè lên một bộ phận bằng một bản trùng lặp vì không có ranh giới bảo vệ.
- **Module Path (Hiện đại)**: Các container vận chuyển được niêm phong. Mỗi container có một bản kê khai ở bên ngoài (`module-info.class`) công bố những gì nó cần từ các container khác và những nội dung cụ thể nào bên trong được phép mang ra ngoài.

```mermaid
flowchart TD
    subgraph Classpath (Đống hỗn độn phẳng - Flat Heap)
        A[Lớp A] --- B[Lớp B]
        C[Lớp C] --- A
        D[Lớp B bị trùng lặp]
    end
    subgraph Module Path (Các mô-đun được đóng gói - Encapsulated Modules)
        subgraph Mô-đun A (Module A)
            DirA[module-info.class] -->|yêu cầu (requires)| ModuleB[Mô-đun B]
            ExportA[xuất khẩu package.a]
        end
        subgraph Mô-đun B (Module B)
            DirB[module-info.class]
            ExportB[xuất khẩu package.b]
            InternalB[gói nội bộ internal.package.c]
        end
    end
```

#### Chuỗi nguyên nhân - kết quả của việc xác thực khi khởi động (Cause-Effect Chain of Startup Verification)
```text
Thiếu tệp JAR trên Classpath
  → JVM bỏ qua nó khi khởi động
  → Classloader cố gắng nạp lớp vào thời gian chạy
  → ClassNotFoundException / NoClassDefFoundError (Ứng dụng crash trên môi trường sản xuất)

Thiếu Mô-đun trên Module Path
  → JVM phân giải đồ thị mô-đun khi khởi động
  → Phát hiện thiếu phụ thuộc trong module-info
  → Ứng dụng lập tức dừng chạy với thông báo lỗi chi tiết (Thất bại nhanh an toàn)
```

---

### module-info.java và các chỉ thị Mô-đun (module-info.java and Module Directives)

Trình mô tả mô-đun phải được đặt tên là `module-info.java` và đặt ở thư mục gốc của thư mục nguồn (ví dụ: `src/main/java/module-info.java`). Nó được biên dịch thành `module-info.class`.

```java
// File: src/main/java/module-info.java
module com.example.app {
    // Yêu cầu thư viện tiêu chuẩn hoặc các mô-đun tùy chỉnh
    requires java.sql;
    requires transitive com.example.util; // Bất kỳ mô-đun nào yêu cầu 'com.example.app' sẽ tự động nhận được 'com.example.util'

    // Xuất khẩu các lớp public trong gói này cho tất cả các mô-đun
    exports com.example.app.api;

    // Giới hạn xuất khẩu cho một mô-đun cụ thể (Xuất khẩu có điều kiện - Qualified Export)
    exports com.example.app.internal to com.example.trusted;

    // Mở một gói cho phản chiếu sâu (ngay cả các trường private) chỉ vào thời gian chạy
    opens com.example.app.model to spring.beans, jackson.databind;
}
```

#### Các chỉ thị requires (The requires Directives)
- `requires <module-name>`: Chỉ định rằng mô-đun này phụ thuộc vào một mô-đun khác. Nó thiết lập **khả năng đọc hiểu (readability)** (Mô-đun A có thể đọc Mô-đun B).
- `requires transitive <module-name>`: Chỉ định một phụ thuộc tự động chuyển tiếp đến bất kỳ mô-đun nào phụ thuộc vào mô-đun hiện tại.
- `requires static <module-name>`: Một phụ thuộc chỉ cần tại thời điểm biên dịch. Nó là tùy chọn (optional) tại thời điểm chạy.

---

### exports so với opens (exports vs opens)

Một nguồn gây nhầm lẫn phổ biến là khi nào nên sử dụng `exports` so với `opens`. Chúng đại diện cho các phong cách kiểm soát truy cập khác nhau:

| Tính năng (Feature) | `exports` | `opens` |
| --- | --- | --- |
| **Truy cập tại thời điểm biên dịch (Compile-time Access)** | ✅ Được phép (Các mô-đun khác có thể viết mã nguồn sử dụng các lớp này) | ❌ Bị chặn (Trình biên dịch ném ra lỗi "gói không tồn tại") |
| **Truy cập trực tiếp tại thời điểm chạy (Runtime Direct Access)** | ✅ Được phép | ❌ Bị chặn |
| **Phản chiếu nông tại thời điểm chạy (Runtime Reflection (Shallow))**| ✅ Được phép (Chỉ các phần tử public mới có thể được kiểm tra) | ✅ Được phép |
| **Phản chiếu sâu tại thời điểm chạy (Runtime Deep Reflection)** | ❌ Bị chặn (Truy cập các thành viên `private` sẽ ném ra ngoại lệ) | ✅ Được phép (Có thể truy cập các trường/phương thức `private` thông qua `.setAccessible(true)`) |

#### Chặn phản chiếu sâu (Deep Reflection Block)

Nếu bạn cố gắng thực hiện phản chiếu sâu (deep reflection) trên một gói được xuất khẩu nhưng không được mở:
```java
package com.example.app.api;
public class User {
    private String secretToken = "12345";
}
```
Nếu một framework cố gắng truy cập `secretToken` bằng phản chiếu:
```java
// Mã nguồn bên trong một mô-đun framework
Field field = User.class.getDeclaredField("secretToken");
field.setAccessible(true); // Ném ra InaccessibleObjectException dưới cơ chế đóng gói mạnh mẽ!
String secret = (String) field.get(userInstance); 
```
**Ngoại lệ đầu ra**:
```text
java.lang.reflect.InaccessibleObjectException: Unable to make field private java.lang.String com.example.app.api.User.secretToken accessible: 
module com.example.app does not "opens com.example.app.api" to framework.module
```

#### Chuỗi nguyên nhân - kết quả của opens so với exports (Cause-Effect Chain of opens vs exports)
```text
Lớp A cần gọi Lớp B tại thời điểm biên dịch
  → Gói chứa Lớp B phải được EXPORTED
  → Trình biên dịch biên dịch thành công.

Framework cần kiểm tra các trường private của Lớp B tại thời điểm chạy
  → Gói chứa Lớp B phải được OPENED
  → JVM cho phép gọi setAccessible(true)
  → Quá trình phản chiếu thành công mà không có ngoại lệ.
```

---

### Classpath so với Module Path (Classpath vs Module Path)

Trình biên dịch Java (`javac`) và thời gian chạy (`java`) hành xử khác nhau tùy thuộc vào việc mã nguồn được đặt trên Classpath hay Module Path.

```mermaid
flowchart TD
    subgraph Các tùy chọn thực thi (Execution Options)
        Command[Lệnh java] --> CP[--class-path / -cp]
        Command --> MP[--module-path / -p]
    end
    CP -->|Tìm kiếm phẳng| Unnamed[Mô-đun không tên (Unnamed Module)]
    Unnamed -->|Có thể truy cập| AllModules[Tất cả các mô-đun trên Module Path]
    
    MP -->|Phân giải có cấu trúc| Named[Mô-đun có tên / tự động]
    Named -->|Không thể đọc| Unnamed
```

#### Nạp lớp dạng mô-đun so với nạp lớp phẳng (Modular vs Flat Classloading)
1. **Classpath (Phẳng)**:
   - Dòng lệnh: `java -cp lib/dep.jar:app.jar com.example.Main`
   - Các Classloader tìm kiếm các tệp JAR tuần tự trong một danh sách phẳng.
   - Mọi thứ được nạp từ classpath đều được đưa vào **Mô-đun không tên (Unnamed Module)**.
2. **Module Path (Mô-đun)**:
   - Dòng lệnh: `java --module-path lib:app.jar -m com.example.app/com.example.Main`
   - JVM sử dụng module path để xây dựng một đồ thị có cấu trúc.
   - Chỉ các gói được xuất khẩu rõ ràng bởi các mô-đun chứa chúng mới có thể được tìm kiếm bởi các bộ nạp lớp khác.
   - Các phụ thuộc vòng được phát hiện trong quá trình phân giải và bị từ chối trước khi quá trình nạp lớp bắt đầu.

---

### Mô-đun có tên, không tên, và tự động (Named, Unnamed, and Automatic Modules)

Để cho phép di trú dần dần từ Java 8, Java định nghĩa ba loại mô-đun:

| Kiểu mô-đun (Module Type) | Cách được định nghĩa (How it is Defined) | Được nạp từ đâu (Where it is Loaded From) | exports / opens | Quy tắc đọc hiểu (Readability Rules) | Nguồn gốc tên (Name Source) |
| --- | --- | --- | --- | --- | --- |
| **Mô-đun có tên (Named Module)** | Có tệp `module-info.class` | Module Path (`--module-path`) | Rõ ràng như đã định nghĩa | Có thể đọc các mô-đun mà nó yêu cầu (`requires`) một cách rõ ràng | Được định nghĩa trong `module-info.java` |
| **Mô-đun không tên (Unnamed Module)** | Không có `module-info.class` (Ngầm định) | Classpath (`-cp`) | Xuất khẩu và mở mọi thứ | Có thể đọc tất cả các mô-đun trên Module Path | Không có (được gọi là mô-đun không tên) |
| **Mô-đun tự động (Automatic Module)** | Tệp JAR cũ (Không có `module-info.class`) | Module Path (`--module-path`) | Xuất khẩu và mở mọi thứ | Có thể đọc tất cả các mô-đun (có tên, không tên, tự động) | Được suy ra từ tên tệp JAR hoặc tiêu đề manifest |

#### Cơ chế cầu nối di trú (The Migration Bridge Mechanism)
Bởi vì các mô-đun có tên không thể yêu cầu mô-đun không tên (vì nó không có tên và không thể được tham chiếu một cách rõ ràng), việc nạp mã nguồn cũ trực tiếp vào một mô-đun có tên sẽ bị hỏng. Để làm cầu nối cho việc này:
1. Bạn đặt một tệp JAR thư viện cũ trên **Module Path**.
2. JVM coi nó như một **Mô-đun tự động (Automatic Module)**. Nó tự động có được một tên mô-đun (ví dụ: `commons.lang3` từ tệp `commons-lang3-3.12.0.jar` hoặc thông qua tiêu đề manifest `Automatic-Module-Name`).
3. Các mô-đun có tên bây giờ có thể khai báo `requires commons.lang3;`.
4. Mô-đun tự động có thể đọc tất cả các mô-đun khác (bao gồm cả mô-đun không tên của classpath), tạo cầu nối kết nối giữa thế giới cũ và mới.

```text
Mô-đun có tên (Named Module)
  → requires com.helper (Mô-đun tự động)
  → truy cập lớp cũ (legacy class)
  → Thành công!
```

---

## Liên kết tham khảo (Reference Links)

- [Hướng dẫn về các Mô-đun Java chính thức của Oracle](https://dev.java/learn/modules/)
- [Đặc tả Ngôn ngữ Java: Chương 7.7 (Khai báo Mô-đun)](https://docs.oracle.com/javase/specs/jls/se21/html/jls-7.html#jls-7.7)
- [Hướng dẫn Bắt đầu Nhanh Project Jigsaw](https://openjdk.org/projects/jigsaw/quick-start)
