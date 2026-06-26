# JVM nâng cao - Phần 1 (Advanced JVM - Part 1)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm một phần trọng tâm của **JVM nâng cao (Advanced JVM)**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là từ vựng rời rạc.

## Phạm vi đề cương (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `JVM architecture` | JVM thực thi bytecode và quản lý các dịch vụ thời gian chạy (runtime services) như bộ nhớ, JIT và GC. |
| `Class Loader Subsystem` | Phân hệ nạp lớp (Class Loader Subsystem) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại (failure mode) của nó thay vì chỉ nhớ tên. |
| `Runtime Data Areas:` | Các vùng dữ liệu thời gian chạy (Runtime Data Areas) là một nhóm các quy tắc liên quan trong JVM nâng cao tập hợp nhiều chi tiết liên quan lại với nhau. |
| `Heap` | Heap lưu trữ các đối tượng được tạo ra trong quá trình chạy ứng dụng. |
| `Stack` | Stack lưu trữ các khung phương thức (method frames), các biến cục bộ và luồng (Thread) cuộc gọi cho mỗi luồng. |
| `Method Area / Metaspace` | Metaspace lưu trữ siêu dữ liệu của lớp (class metadata) bên ngoài bộ nhớ heap thông thường của Java trong các JVM hiện đại. |
| `PC Register` | Thanh ghi PC (PC Register) theo dõi lệnh JVM hiện tại của một luồng. |
| `Native Method Stack` | Stack phương thức bản địa (Native Method Stack) lưu trữ các khung để thực thi các phương thức bản địa (không phải Java). |

## Ghi chú chi tiết (Detailed Notes)

### Kiến trúc JVM (JVM architecture)

JVM thực thi bytecode và quản lý các dịch vụ thời gian chạy (runtime services) như bộ nhớ, JIT và GC.

Nó quan trọng vì hành vi thời gian chạy giải thích hiệu suất, lỗi bộ nhớ, hành vi khi khởi động và nhiều câu hỏi phỏng vấn. Sự nhầm lẫn phổ biến là nhầm lẫn các khái niệm ở thời điểm biên dịch (compile-time) với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế:

- Định nghĩa `JVM architecture` trong một câu.
- Nhận biết `JVM architecture` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `JVM architecture`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `JVM architecture` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Phân hệ nạp lớp (Class Loader Subsystem)

Phân hệ nạp lớp (Class Loader Subsystem) là một khái niệm cụ thể trong JVM nâng cao; hãy tìm hiểu quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Class Loader Subsystem` trong một câu.
- Nhận biết `Class Loader Subsystem` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Class Loader Subsystem`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `Class Loader Subsystem` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Vùng dữ liệu thời gian chạy (Runtime Data Areas)

Các vùng dữ liệu thời gian chạy (Runtime Data Areas) là một nhóm các quy tắc liên quan trong JVM nâng cao tập hợp nhiều chi tiết liên quan lại với nhau.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Runtime Data Areas:` trong một câu.
- Nhận biết `Runtime Data Areas:` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Runtime Data Areas:`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `Runtime Data Areas:` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Vùng Heap (Heap)

Heap lưu trữ các đối tượng được tạo ra trong quá trình chạy ứng dụng.

Nó quan trọng vì hành vi thời gian chạy giải thích hiệu suất, lỗi bộ nhớ, hành vi khi khởi động và nhiều câu hỏi phỏng vấn. Sự nhầm lẫn phổ biến là nhầm lẫn các khái niệm ở thời điểm biên dịch (compile-time) với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế:

- Định nghĩa `Heap` trong một câu.
- Nhận biết `Heap` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Heap`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `Heap` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Vùng Stack (Stack)

Stack lưu trữ các khung phương thức (method frames), các biến cục bộ và luồng cuộc gọi cho mỗi luồng.

Nó quan trọng vì hành vi thời gian chạy giải thích hiệu suất, lỗi bộ nhớ, hành vi khi khởi động và nhiều câu hỏi phỏng vấn. Sự nhầm lẫn phổ biến là nhầm lẫn các khái niệm ở thời điểm biên dịch (compile-time) với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế:

- Định nghĩa `Stack` trong một câu.
- Nhận biết `Stack` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Stack`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `Stack` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Vùng phương thức / Metaspace (Method Area / Metaspace)

Metaspace lưu trữ siêu dữ liệu của lớp (class metadata) bên ngoài bộ nhớ heap thông thường của Java trong các JVM hiện đại.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Method Area / Metaspace` trong một câu.
- Nhận biết `Method Area / Metaspace` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Method Area / Metaspace`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `Method Area / Metaspace` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Thanh ghi PC (PC Register)

Thanh ghi PC (PC Register) theo dõi lệnh JVM hiện tại của một luồng.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `PC Register` trong một câu.
- Nhận biết `PC Register` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `PC Register`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `PC Register` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Stack phương thức bản địa (Native Method Stack)

Stack phương thức bản địa (Native Method Stack) lưu trữ các khung để thực thi các phương thức bản địa (native - không phải Java), chẳng hạn như các hàm JNI được viết bằng C hoặc C++.

Nó quan trọng vì khi mã Java gọi mã bản địa (như thư viện mật mã bản địa hoặc API nền tảng), ngữ cảnh thực thi của luồng sẽ chuyển sang stack này. Việc tràn stack (stack overflow) ở đây có thể làm hỏng toàn bộ tiến trình JVM mà không ném ra ngoại lệ `java.lang.StackOverflowError` thông thường của Java.

Kiểm tra thực tế:

- Định nghĩa `Native Method Stack` trong một câu.
- Nhận biết `Native Method Stack` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Native Method Stack`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `Native Method Stack` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Các ví dụ code (Code Examples)

### Truy vấn thông tin bộ nhớ bằng mã nguồn (Querying Memory Information Programmatically)
```java
Runtime runtime = Runtime.getRuntime();
long maxMemory = runtime.maxMemory();   // Equivalent to -Xmx
long totalMemory = runtime.totalMemory(); // Current heap size allocated
long freeMemory = runtime.freeMemory();   // Free space in current heap

System.out.println("Max Heap: " + (maxMemory / 1024 / 1024) + " MB");
```

## Các lỗi thường gặp (Common Mistakes)

- **Giả định StackOverflowError liên quan đến Heap**: Ngoại lệ `StackOverflowError` xảy ra trong Stack của luồng khi các khung cuộc gọi vượt quá giới hạn bộ nhớ của stack (thường do đệ quy vô hạn). Điều này hoàn toàn không liên quan đến Heap.
- **Nhầm lẫn Metaspace với Heap**: Siêu dữ liệu lớp được lưu trữ trong Metaspace (bộ nhớ bản địa ngoài heap - off-heap) kể từ Java 8. Nó không tranh giành không gian Heap với các đối tượng Java, nhưng vẫn có thể làm cạn kiệt bộ nhớ bản địa của hệ thống nếu có quá nhiều lớp được nạp.

## Câu hỏi ôn tập phổ biến (Common Review Prompts)

- Khái niệm nào ở đây là quy tắc ở thời điểm biên dịch (compile-time)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi lúc chạy (runtime)?
- Khái niệm nào ở đây dễ là bẫy phỏng vấn (interview traps)?

---

## Tại sao Quá trình nạp lớp có ba giai đoạn riêng biệt (Why Class Loading Has Three Distinct Phases)

Phân hệ nạp lớp (class loading subsystem) của JVM chia việc nạp lớp thành ba giai đoạn riêng biệt (Nạp (Loading), Liên kết (Linking), và Khởi tạo (Initializing)) để thực thi bảo mật, xác minh tính toàn vẹn cấu trúc và tối ưu hóa phân bổ bộ nhớ trước khi thực thi mã nguồn.

Trong giai đoạn **Nạp (Loading)**, JVM xác định vị trí biểu diễn nhị phân của một lớp (thường là tệp `.class`) và nạp nó vào Vùng phương thức (Method Area) / Metaspace, tạo ra một đối tượng `java.lang.Class`.

Trong giai đoạn **Liên kết (Linking)**, JVM thực hiện Xác thực (Verification - cực kỳ quan trọng cho bảo mật, kiểm tra định dạng, các ràng buộc bytecode và quy tắc kiểu dữ liệu để ngăn chặn khai thác độc hại), Chuẩn bị (Preparation - phân bổ bộ nhớ cho các trường tĩnh và khởi tạo chúng về giá trị mặc định của kiểu dữ liệu), và Phân giải (Resolution - tùy chọn giải quyết các tham chiếu tượng trưng (symbolic references) thành các tham chiếu trực tiếp (direct references)).

Cuối cùng, trong giai đoạn **Khởi tạo (Initialization)**, JVM thực thi các khối khởi tạo tĩnh (static initialization blocks) và gán các giá trị thực tế được khai báo trong code cho các biến tĩnh thông qua phương thức `<clinit>` do trình biên dịch tạo ra.

### Mô hình tư duy: Các giai đoạn nạp lớp (Mental Model: Class Loading Phases)

```text
+-------------------------------------------------------------------------------+
|                               NẠP LỚP (CLASS LOADING)                         |
|-------------------------------------------------------------------------------|
|  1. NẠP (LOADING)    |  2. LIÊN KẾT (LINKING)                      | 3. KHỞI TẠO|
|                      |  a. Xác thực -> b. Chuẩn bị -> c. Phân giải | (INIT)     |
|  [Tìm bytecode]      |  [Kiểm tra an toàn] [Cấp bộ nhớ] [Giải quyết]| [<clinit>  |
|  tệp .class -> JVM   |  Kiểm tra kiểu dữ liệu tĩnh x = 0  kết nối   |  x = 42]   |
+-------------------------------------------------------------------------------+
```

### Ví dụ Code (Code Example)

```java
package theory;

public class ClassLoaderDemo {
    // Phân bổ bộ nhớ tĩnh xảy ra trong bước Chuẩn bị, nhưng việc gán giá trị xảy ra trong bước Khởi tạo
    public static final int CONSTANT_VAL = 42; 
    public static int mutableVal = 99;

    static {
        System.out.println("ClassLoaderDemo initialized!");
        mutableVal = 100;
    }

    public static void main(String[] args) {
        // Việc truy cập CONSTANT_VAL (một giá trị hằng số ở thời điểm biên dịch) KHÔNG kích hoạt quá trình khởi tạo đầy đủ
        System.out.println("Constant: " + ClassLoaderDemo.CONSTANT_VAL);
        // Việc truy cập mutableVal kích hoạt thực thi khối tĩnh static (Khởi tạo)
        System.out.println("Mutable Value: " + ClassLoaderDemo.mutableVal);
    }
}
/* Output:
Constant: 42
ClassLoaderDemo initialized!
Mutable Value: 100
*/
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)


```text
ClassLoader đọc luồng byte `.class`
  → Quá trình xác thực chạy kiểm tra kiểu dữ liệu
  → Quá trình chuẩn bị phân bổ bộ nhớ với các giá trị mặc định
  → Quá trình khởi tạo chạy phương thức `<clinit>`
  → Lớp đã sẵn sàng hoàn toàn để ứng dụng sử dụng.
```


## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/javase/specs/jvms/se21/html/jvms-5.html (Chapter 5. Loading, Linking, and Initializing)
