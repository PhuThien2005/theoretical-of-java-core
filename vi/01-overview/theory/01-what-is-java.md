# Java là gì (What Java Is)

Java là ngôn ngữ lập trình hướng đối tượng, có mục đích chung. Nó được sử dụng cho các hệ thống phụ trợ, phát triển Android, ứng dụng doanh nghiệp, công cụ dòng lệnh, hệ thống phân tán, hệ thống tài chính và nhiều ứng dụng máy chủ chạy lâu dài.

Java không chỉ là một ngôn ngữ. Trong thực tế, người ta thường dùng từ “Java” để chỉ cả một hệ sinh thái:

- Ngôn ngữ lập trình Java.
- Trình biên dịch Java.
- Máy ảo Java (JVM - Java Virtual Machine).
- Thư viện tiêu chuẩn.
- Xây dựng các công cụ như Maven và Gradle.
- Các khung như Spring.

## Tại sao Java trở nên phổ biến (Why Java Became Popular)

Java trở nên phổ biến vì nó giải quyết được nhiều vấn đề thực tế cùng một lúc:

- Nó cung cấp một cú pháp tương đối rõ ràng so với các ngôn ngữ cấp thấp hơn.
- Nó có tính năng quản lý bộ nhớ tự động thông qua Garbage Collection.
- Nó hỗ trợ lập trình hướng đối tượng một cách mạnh mẽ.
- Nó có một thư viện và hệ sinh thái tiêu chuẩn khổng lồ.
- Nó có thể chạy trên nhiều nền tảng thông qua JVM.
- Nó đủ ổn định cho các hệ thống doanh nghiệp lớn.

## Hướng đối tượng (Object-Oriented)

Java tập trung chủ yếu vào các lớp và đối tượng. Hầu hết mã được viết bên trong các lớp. Một lớp mô tả dữ liệu và hành vi, còn các đối tượng được tạo ra từ các lớp.

Ví dụ:

```java
class Student {
    String name;

    void study() {
        System.out.println(name + " is studying");
    }
}
```

Lớp `Student` là một bản thiết kế. Đối tượng `Student` là một phiên bản cụ thể được tạo từ bản thiết kế đó.

## Nền tảng độc lập (Platform Independent)

Java được gọi là nền tảng độc lập vì mã byte Java được biên dịch có thể chạy trên bất kỳ nền tảng nào có JVM tương thích.

Điều này không có nghĩa là bản thân JVM độc lập với nền tảng. Một JVM được xây dựng cho một hệ điều hành và kiến ​​trúc CPU cụ thể.

Ý tưởng quan trọng là:

```text
Same bytecode + different JVMs = runs on different platforms
```

## Tại sao Java chạy trên máy ảo: Trừu tượng (Abstraction) hóa nền tảng (Why Java Runs on a Virtual Machine: Platform Abstraction)

Trước Java, các ngôn ngữ như C và C++ đã biên dịch mã nguồn trực tiếp thành mã máy gốc (ví dụ: hướng dẫn x86 hoặc ARM) dành riêng cho một hệ điều hành và CPU. Điều này đã tạo ra vấn đề "biên dịch trên mỗi nền tảng", trong đó các nhà phát triển phải duy trì các chuỗi công cụ biên dịch riêng biệt và sửa đổi các lệnh gọi hệ thống dành riêng cho nền tảng cho Windows, macOS và Linux. Java giải quyết vấn đề này bằng cách chèn một lớp trừu tượng (Abstract Class): Máy ảo Java (JVM). Trình biên dịch Java ( `javac` ) biên dịch mã nguồn mà con người có thể đọc được thành định dạng trung gian, được tiêu chuẩn hóa gọi là mã byte. JVM, hoạt động như một CPU ảo hóa, tải mã byte này và dịch nó nhanh chóng thành các hướng dẫn gốc cụ thể của phần cứng và hệ điều hành cơ bản. Điều này chuyển sự phụ thuộc nền tảng từ mã ứng dụng sang chính JVM, cho phép cùng một tệp mã byte chạy không bị sửa đổi trên nhiều nền tảng khác nhau.

### Mô hình tinh thần: Người phiên dịch vạn năng (Mental Model: The Universal Translator)
Hãy tưởng tượng bạn đang viết một cuốn sách bằng một ngôn ngữ phụ trợ phổ quát duy nhất (chẳng hạn như Esperanto, đại diện cho **Bytecode**). Thay vì tự mình dịch bản thảo gốc (**Mã nguồn**) sang 100 ngôn ngữ địa phương khác nhau (**Mã máy bản địa**), bạn phân phối phiên bản Esperanto. Mỗi độc giả đều có một dịch giả địa phương (**JVM**), người chuyển Esperanto sang phương ngữ địa phương của họ trong thời gian thực.

```mermaid
flowchart TD
    subgraph Before Java (C/C++)
        C_Src["Mã nguồn C (.c)"] --> C_Win["Trình biên dịch Windows"] --> Win_Bin["Windows có thể thực thi được (x86)"]
        C_Src --> C_Mac["Trình biên dịch macOS"] --> Mac_Bin["Có thể thực thi macOS (ARM)"]
    end
    subgraph With Java
        J_Src["Mã nguồn Java (.java)"] --> javac["Trình biên dịch javac"] --> Bytecode["Mã byte (.class)"]
        Bytecode --> JVM_Win["JVM của Windows"] --> Win_Run["Hệ điều hành Windows (x86)"]
        Bytecode --> JVM_Mac["macOS JVM"] --> Mac_Run["Hệ điều hành macOS (ARM)"]
    end
```

### Ví dụ về mã: Tính trừu tượng của nền tảng đang hoạt động (Code Example: Platform Abstraction in Action)
Trong khi các nhà phát triển viết cùng một mã, JVM sẽ dịch các lệnh gọi API tiêu chuẩn sang các hành vi dành riêng cho nền tảng. Ví dụ sau đây cho thấy cách JVM trừu tượng hóa các dấu phân cách đường dẫn và cách đặt tên hệ điều hành:

```java
public class PlatformDemo {
    public static void main(String[] args) {
        // The JVM abstracts away platform-specific file separators
        String separator = java.io.File.separator;
        System.out.println("Separator: " + separator); 
        // Output on Windows: "Separator: \"
        // Output on Linux/macOS: "Separator: /"

        // The JVM abstracts away the underlying OS name
        String osName = System.getProperty("os.name");
        System.out.println("Operating System: " + osName);
        // Output on a Linux machine: "Operating System: Linux"
    }
}
```

### Chuỗi nhân quả (Cause-Effect Chain)
Nhà phát triển biên dịch mã `.java` $\rightarrow$ `javac` tạo mã byte không phụ thuộc vào nền tảng ( `.class` ) $\rightarrow$ JVM tải mã byte và dịch hướng dẫn mã byte sang hướng dẫn máy gốc dành riêng cho máy chủ một cách linh hoạt $\rightarrow$ Mã chạy thành công trên Windows, macOS hoặc Linux mà không cần biên dịch lại.

## Mạnh mẽ (Robust)

Java được coi là mạnh mẽ vì nó bao gồm các tính năng giúp giảm nhiều lỗi lập trình phổ biến:

- Gõ tĩnh mạnh mẽ.
- Xử lý ngoại lệ.
- Quản lý bộ nhớ tự động.
- Kiểm tra giới hạn mảng
- Không có số học con trỏ trực tiếp trong mã Java thông thường.

Những tính năng này không làm cho Java không có lỗi nhưng chúng làm giảm một số loại lỗi thời gian chạy (Runtime) nguy hiểm.

## Đa luồng (Thread) (Multithreading) (Multithreaded)

Java hỗ trợ đa luồng trực tiếp. Một chương trình Java có thể chạy nhiều luồng thực thi trong một tiến trình.

Điều này quan trọng đối với:

- Máy chủ web xử lý nhiều yêu cầu.
- Nhiệm vụ nền.
- Xử lý đồng thời
- Ứng dụng đáp ứng.

Đa luồng rất mạnh mẽ nhưng nó cũng gây ra các vấn đề như điều kiện chạy đua và bế tắc. Những chủ đề đó xuất hiện sau trong lộ trình.

## Hiệu suất cao thông qua JIT (High Performance Through JIT)

Các chương trình Java chạy trên JVM, vì vậy những người mới bắt đầu đôi khi cho rằng Java luôn chậm. Điều đó quá đơn giản.

JVM có thể sử dụng trình biên dịch JIT (Just-In-Time compilation) để tối ưu hóa mã byte được thực thi thường xuyên trong khi chương trình đang chạy. Đối với nhiều ứng dụng máy chủ, điều này làm cho Java đủ nhanh cho các hệ thống sản xuất nghiêm túc.

## Đặc điểm của Java (Characteristics of Java)

Java được thiết kế với một tập hợp các đặc điểm cụ thể giúp nó phù hợp với các ứng dụng doanh nghiệp.

### Đơn giản (Simple)
Java được thiết kế để học và viết tương đối đơn giản. Nó loại bỏ các tính năng phức tạp và hiếm khi được sử dụng của C++ như nạp chồng toán tử rõ ràng, đa kế thừa (Inheritance) cho các lớp và quản lý bộ nhớ/số học con trỏ rõ ràng.

### Chắc chắn (Secure)
Java an toàn vì nó chạy trong hộp cát máy ảo. JVM xác minh mã byte trước khi thực thi, ngăn chặn truy cập trái phép, tràn ngăn xếp hoặc hỏng bộ nhớ. Không có con trỏ, nghĩa là các cuộc tấn công tràn bộ đệm được ngăn chặn một cách tự nhiên.

### phân phối (Distributed)
Java được thiết kế cho môi trường phân tán. Nó có hỗ trợ tích hợp cho kết nối mạng, gọi phương thức (Method) từ xa và giao thức phân tán, giúp dễ dàng xây dựng các ứng dụng giao tiếp qua mạng.

## Cơ chế “Viết một lần, chạy mọi nơi” (The "Write once, run anywhere" mechanism)
Đây là lời hứa về tính di động cốt lõi của Java. Mã byte được biên dịch hoàn toàn độc lập với nền tảng. Để thực thi nó trên bất kỳ hệ điều hành nào, hệ thống đó chỉ cần một Máy ảo Java (JVM) tương thích. JVM hoạt động như một trình dịch giữa mã byte và mã máy gốc của HĐH.


## Những hiểu lầm phổ biến (Common Misunderstandings)

### Hiểu lầm: Mã Java chạy trực tiếp trên mọi hệ điều hành (Misunderstanding: Java code runs directly on every operating system)

Không chính xác. Mã nguồn Java được biên dịch thành mã byte. Mã byte chạy trên JVM. Mỗi nền tảng cần một JVM tương thích.

### Hiểu lầm: Java và JavaScript có liên quan chặt chẽ với nhau (Misunderstanding: Java and JavaScript are closely related)

Chúng là những ngôn ngữ khác nhau. Các tên này giống nhau vì lý do tiếp thị lịch sử, nhưng ngôn ngữ, mô hình thời gian chạy và hệ sinh thái rất khác nhau.

### Hiểu lầm: Thu gom rác (Garbage Collection) có nghĩa là bộ nhớ không bao giờ quan trọng (Misunderstanding: Garbage Collection means memory never matters)

Cơ chế thu gom rác (Garbage Collection) giúp lấy lại các đối tượng không được sử dụng, nhưng các chương trình Java vẫn có thể lãng phí bộ nhớ hoặc giữ lại các tham chiếu không cần thiết.

## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/javase/specs/jvms/se21/html/jvms-1.html#jvms-1.2 (Máy ảo Java)

