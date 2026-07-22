# Java Là Gì

Java là ngôn ngữ lập trình đa mục đích, hướng đối tượng. Nó được dùng trong hệ thống backend, phát triển Android, ứng dụng doanh nghiệp, công cụ dòng lệnh, hệ thống phân tán, hệ thống tài chính, và nhiều ứng dụng server chạy dài hạn.

Java không chỉ là một ngôn ngữ. Trong thực tế, người ta thường dùng từ "Java" để chỉ cả một hệ sinh thái:

- Ngôn ngữ lập trình Java.
- Trình biên dịch Java.
- Máy Ảo Java (JVM).
- Thư viện chuẩn.
- Công cụ build như Maven và Gradle.
- Các framework như Spring.

## Tại Sao Java Trở Nên Phổ Biến

Java trở nên phổ biến vì nó giải quyết được nhiều vấn đề thực tế cùng một lúc:

- Cú pháp tương đối rõ ràng so với các ngôn ngữ cấp thấp hơn.
- Quản lý bộ nhớ tự động thông qua thu gom rác (Garbage Collection).
- Hỗ trợ mạnh mẽ cho lập trình hướng đối tượng (Object-Oriented Programming).
- Thư viện chuẩn và hệ sinh thái đồ sộ.
- Có thể chạy trên nhiều nền tảng thông qua JVM.
- Đủ ổn định cho các hệ thống doanh nghiệp lớn.

## Hướng Đối Tượng (Object-Oriented)

Java được xây dựng xoay quanh các lớp (class) và đối tượng (object). Hầu hết code được viết bên trong các lớp. Lớp mô tả dữ liệu và hành vi, còn đối tượng được tạo ra từ các lớp.

Ví dụ:

```java
class Student {
    String name;

    void study() {
        System.out.println(name + " is studying");
    }
}
```

Lớp `Student` là một bản thiết kế (blueprint). Một đối tượng `Student` là một thực thể cụ thể được tạo từ bản thiết kế đó.

## Độc Lập Nền Tảng (Platform Independent)

Java được gọi là độc lập nền tảng vì bytecode Java đã biên dịch có thể chạy trên bất kỳ nền tảng nào có JVM tương thích.

Điều này không có nghĩa là bản thân JVM độc lập nền tảng. Một JVM được xây dựng cho một hệ điều hành và kiến trúc CPU cụ thể.

Ý tưởng quan trọng là:

```text
Cùng một bytecode + các JVM khác nhau = chạy trên các nền tảng khác nhau
```

## Tại Sao Java Chạy Trên Máy Ảo: Trừu Tượng Hóa Nền Tảng

Trước Java, các ngôn ngữ như C và C++ biên dịch mã nguồn trực tiếp thành mã máy gốc (ví dụ: lệnh x86 hoặc ARM) dành riêng cho một hệ điều hành và CPU. Điều này tạo ra vấn đề "biên dịch theo từng nền tảng", buộc lập trình viên phải duy trì các toolchain biên dịch riêng biệt và chỉnh sửa các lời gọi hệ thống đặc thù theo nền tảng cho Windows, macOS, và Linux. Java giải quyết vấn đề này bằng cách chèn một lớp trừu tượng: Máy Ảo Java (JVM). Trình biên dịch Java (`javac`) biên dịch mã nguồn dễ đọc thành một định dạng trung gian chuẩn hóa gọi là bytecode. JVM, đóng vai trò như một CPU ảo hóa, nạp bytecode này và dịch nó thành các lệnh gốc cụ thể của phần cứng và hệ điều hành bên dưới. Điều này chuyển sự phụ thuộc nền tảng từ code ứng dụng sang bản thân JVM, cho phép cùng một file bytecode chạy không cần chỉnh sửa trên các nền tảng khác nhau.

### Mô Hình Tư Duy: Người Phiên Dịch Toàn Cầu

Hãy tưởng tượng bạn viết một cuốn sách bằng một ngôn ngữ phụ trợ toàn cầu (như tiếng Esperanto, đại diện cho **Bytecode**). Thay vì tự dịch bản thảo gốc (**Mã nguồn**) sang 100 ngôn ngữ địa phương khác nhau (**Mã máy gốc**), bạn phân phối phiên bản Esperanto. Mỗi độc giả có một phiên dịch viên địa phương (**JVM**) chuyển tiếng Esperanto sang phương ngữ của họ theo thời gian thực.

```mermaid
flowchart TD
    subgraph Trước Java (C/C++)
        C_Src["Mã nguồn C (.c)"] --> C_Win["Trình biên dịch Windows"] --> Win_Bin["Windows Executable (x86)"]
        C_Src --> C_Mac["Trình biên dịch macOS"] --> Mac_Bin["macOS Executable (ARM)"]
    end
    subgraph Với Java
        J_Src["Mã nguồn Java (.java)"] --> javac["Trình biên dịch javac"] --> Bytecode["Bytecode (.class)"]
        Bytecode --> JVM_Win["Windows JVM"] --> Win_Run["Windows OS (x86)"]
        Bytecode --> JVM_Mac["macOS JVM"] --> Mac_Run["macOS OS (ARM)"]
    end
```

### Ví Dụ Code: Trừu Tượng Hóa Nền Tảng Trong Thực Tế

Dù lập trình viên viết cùng một code, JVM dịch các lời gọi API chuẩn thành các hành vi đặc thù theo nền tảng. Ví dụ sau minh họa cách JVM trừu tượng hóa dấu phân cách đường dẫn và tên hệ điều hành:

```java
public class PlatformDemo {
    public static void main(String[] args) {
        // JVM trừu tượng hóa dấu phân cách đường dẫn theo nền tảng
        String separator = java.io.File.separator;
        System.out.println("Separator: " + separator); 
        // Trên Windows: "Separator: \"
        // Trên Linux/macOS: "Separator: /"

        // JVM trừu tượng hóa tên hệ điều hành bên dưới
        String osName = System.getProperty("os.name");
        System.out.println("Operating System: " + osName);
        // Trên máy Linux: "Operating System: Linux"
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả

Lập trình viên biên dịch code `.java` $\rightarrow$ `javac` tạo bytecode độc lập nền tảng (`.class`) $\rightarrow$ JVM nạp bytecode và dịch các lệnh bytecode thành lệnh máy gốc của host một cách động $\rightarrow$ Code chạy thành công trên Windows, macOS, hoặc Linux mà không cần biên dịch lại.

## Bền Vững (Robust)

Java được coi là bền vững vì nó bao gồm các tính năng giúp giảm nhiều lỗi lập trình phổ biến:

- Kiểu dữ liệu tĩnh mạnh (Strong static typing).
- Xử lý ngoại lệ (Exception handling).
- Quản lý bộ nhớ tự động.
- Kiểm tra giới hạn mảng (Array bounds checking).
- Không có phép toán con trỏ trực tiếp trong code Java thông thường.

Những tính năng này không làm cho Java hoàn toàn không có lỗi, nhưng chúng giảm một số loại lỗi runtime nguy hiểm.

## Đa Luồng (Multithreaded)

Java hỗ trợ đa luồng (multithreading) trực tiếp. Một chương trình Java có thể chạy nhiều luồng thực thi bên trong một tiến trình.

Điều này quan trọng với:

- Web server xử lý nhiều yêu cầu đồng thời.
- Các tác vụ nền.
- Xử lý song song.
- Ứng dụng phản hồi nhanh.

Đa luồng mạnh mẽ, nhưng cũng gây ra các vấn đề như race condition và deadlock. Những chủ đề đó xuất hiện ở phần sau trong lộ trình học.

## Hiệu Năng Cao Nhờ JIT

Các chương trình Java chạy trên JVM, nên người mới đôi khi cho rằng Java luôn chậm. Điều đó quá đơn giản hóa.

JVM có thể dùng trình biên dịch JIT (Just-In-Time) để tối ưu hóa bytecode thường xuyên được thực thi trong khi chương trình đang chạy. Với nhiều ứng dụng server, điều này làm cho Java đủ nhanh cho các hệ thống sản xuất nghiêm túc.

## Đặc Điểm Của Java

Java được thiết kế với một tập hợp các đặc điểm cụ thể phù hợp cho ứng dụng doanh nghiệp.

### Đơn Giản (Simple)

Java được thiết kế để tương đối dễ học và viết. Nó loại bỏ các tính năng phức tạp và ít dùng của C++ như nạp chồng toán tử tường minh (explicit operator overloading), đa kế thừa lớp (multiple inheritance for classes), và quản lý bộ nhớ/con trỏ tường minh.

### Bảo Mật (Secure)

Java bảo mật vì nó chạy trong một sandbox máy ảo. JVM xác minh bytecode trước khi thực thi, ngăn chặn truy cập trái phép, tràn ngăn xếp, hoặc hỏng bộ nhớ. Không có con trỏ, nghĩa là các cuộc tấn công tràn bộ đệm (buffer overflow) tự nhiên bị ngăn chặn.

### Phân Tán (Distributed)

Java được thiết kế cho môi trường phân tán. Nó có hỗ trợ tích hợp sẵn cho mạng máy tính, gọi phương thức từ xa (remote method calls), và các giao thức phân tán, giúp dễ dàng xây dựng các ứng dụng giao tiếp qua mạng.

## Cơ Chế "Viết Một Lần, Chạy Mọi Nơi"

Đây là cam kết tính di động cốt lõi của Java. Bytecode đã biên dịch hoàn toàn độc lập nền tảng. Để thực thi trên bất kỳ hệ điều hành nào, hệ thống đó chỉ cần một JVM tương thích. JVM đóng vai trò như người phiên dịch giữa bytecode và mã máy gốc của hệ điều hành.

## Hiểu Lầm Thường Gặp

### Hiểu Lầm: Code Java chạy trực tiếp trên mọi hệ điều hành

Không hẳn. Mã nguồn Java được biên dịch thành bytecode. Bytecode chạy trên JVM. Mỗi nền tảng cần một JVM tương thích.

### Hiểu Lầm: Java và JavaScript có liên quan mật thiết

Chúng là hai ngôn ngữ khác nhau. Tên gọi tương tự vì lý do marketing lịch sử, nhưng ngôn ngữ, mô hình runtime, và hệ sinh thái rất khác nhau.

### Hiểu Lầm: Garbage Collection có nghĩa là bộ nhớ không bao giờ là vấn đề

Garbage Collection giúp thu hồi các đối tượng không dùng nữa, nhưng chương trình Java vẫn có thể lãng phí bộ nhớ hoặc giữ các tham chiếu không cần thiết sống sót.

## Tài Liệu Tham Khảo

- https://docs.oracle.com/javase/specs/jvms/se21/html/jvms-1.html#jvms-1.2 (The Java Virtual Machine)
