# Kiến Trúc JVM Nâng Cao (Advanced JVM) - Phần 1

## Mục Tiêu Học Tập

File này đề cập đến một phần trọng tâm của **Kiến Trúc JVM Nâng Cao (Advanced JVM)**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là những từ vựng rời rạc.

## Đề Cương Khái Niệm

| Khái niệm | Những điều cần biết |
| --- | --- |
| `Kiến trúc JVM` | JVM thực thi bytecode và quản lý các dịch vụ thời gian chạy (runtime) như bộ nhớ, trình biên dịch JIT và bộ thu gom rác GC. |
| `Phân hệ Class Loader` | Phân hệ Class Loader là một khái niệm cụ thể trong JVM nâng cao; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ mỗi tên gọi. |
| `Vùng dữ liệu thời gian chạy:` | Vùng dữ liệu thời gian chạy là một nhóm các quy tắc liên quan trong JVM nâng cao nhóm nhiều chi tiết liên quan. |
| `Heap` | Heap lưu trữ các đối tượng được tạo ra tại thời điểm chạy. |
| `Stack` | Stack lưu trữ các khung phương thức (method frame), các biến cục bộ và luồng cuộc gọi cho mỗi luồng. |
| `Method Area / Metaspace` | Metaspace lưu trữ siêu dữ liệu lớp (class metadata) bên ngoài heap Java thông thường trong các JVM hiện đại. |
| `Thanh ghi PC` | Thanh ghi PC theo dõi chỉ thị lệnh JVM hiện tại của một luồng. |
| `Native Method Stack` | Native Method Stack lưu trữ các khung để thực thi các phương thức bản địa (native - không phải Java). |

## Ghi Chú Chi Tiết

### Kiến Trúc JVM (JVM Architecture)

JVM thực thi bytecode và quản lý các dịch vụ thời gian chạy như bộ nhớ, JIT và GC.

Nó quan trọng vì hành vi runtime giải thích hiệu năng, lỗi bộ nhớ, hành vi khi khởi động và nhiều câu hỏi phỏng vấn. Một nhầm lẫn phổ biến là trộn lẫn các khái niệm tại thời điểm biên dịch với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế:

- Định nghĩa `Kiến trúc JVM` trong một câu.
- Nhận diện `Kiến trúc JVM` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Kiến trúc JVM`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy tự hỏi: `Kiến trúc JVM` thay đổi, cho phép, từ chối hay làm rõ điều gì?

### Phân Hệ Class Loader (Class Loader Subsystem)

Phân hệ Class Loader là một khái niệm cụ thể trong JVM nâng cao; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ mỗi tên gọi.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được phép và chế độ thất bại. Hãy ôn tập lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Phân hệ Class Loader` trong một câu.
- Nhận diện `Phân hệ Class Loader` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `Phân hệ Class Loader`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy tự hỏi: `Phân hệ Class Loader` thay đổi, cho phép, từ chối hay làm rõ điều gì?

### Vùng Dữ Liệu Thời Gian Chạy (Runtime Data Areas)

Vùng dữ liệu thời gian chạy là một nhóm các quy tắc liên quan trong JVM nâng cao nhóm nhiều chi tiết liên quan.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được phép và chế độ thất bại. Hãy ôn tập lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Vùng dữ liệu thời gian chạy` trong một câu.
- Nhận diện `Vùng dữ liệu thời gian chạy` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `Vùng dữ liệu thời gian chạy`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy tự hỏi: `Vùng dữ liệu thời gian chạy` thay đổi, cho phép, từ chối hay làm rõ điều gì?

### Heap

Heap lưu trữ các đối tượng được tạo ra tại thời điểm chạy.

> Xem thêm: Mối quan hệ giữa Heap và Stack trong quản lý bộ nhớ, được trình bày chi tiết trong [Ch.13 - Memory Management](../../13-memory-management/theory/01-stack-vs-heap-concepts.md).

Nó quan trọng vì hành vi runtime giải thích hiệu năng, lỗi bộ nhớ, hành vi khi khởi động và nhiều câu hỏi phỏng vấn. Một nhầm lẫn phổ biến là trộn lẫn các khái niệm tại thời điểm biên dịch với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế:

- Định nghĩa `Heap` trong một câu.
- Nhận diện `Heap` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `Heap`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy tự hỏi: `Heap` thay đổi, cho phép, từ chối hay làm rõ điều gì?

### Stack

Stack lưu trữ các khung phương thức (method frame), các biến cục bộ và luồng cuộc gọi cho mỗi luồng.

Nó quan trọng vì hành vi runtime giải thích hiệu năng, lỗi bộ nhớ, hành vi khi khởi động và nhiều câu hỏi phỏng vấn. Một nhầm lẫn phổ biến là trộn lẫn các khái niệm tại thời điểm biên dịch với các dịch vụ thời gian chạy của JVM.

Kiểm tra thực tế:

- Định nghĩa `Stack` trong một câu.
- Nhận diện `Stack` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `Stack`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy tự hỏi: `Stack` thay đổi, cho phép, từ chối hay làm rõ điều gì?

### Method Area / Metaspace

Metaspace lưu trữ siêu dữ liệu lớp (class metadata) bên ngoài heap Java thông thường trong các JVM hiện đại.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được phép và chế độ thất bại. Hãy ôn tập lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Method Area / Metaspace` trong một câu.
- Nhận diện `Method Area / Metaspace` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `Method Area / Metaspace`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy tự hỏi: `Method Area / Metaspace` thay đổi, cho phép, từ chối hay làm rõ điều gì?

### Thanh Ghi PC (PC Register)

Thanh ghi PC theo dõi chỉ thị lệnh JVM hiện tại của một luồng.

Sử dụng nó để dự đoán quy tắc Java chính xác, dạng được phép và chế độ thất bại. Hãy ôn tập lại với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Thanh ghi PC` trong một câu.
- Nhận diện `Thanh ghi PC` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `Thanh ghi PC`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy tự hỏi: `Thanh ghi PC` thay đổi, cho phép, từ chối hay làm rõ điều gì?

### Native Method Stack

Native Method Stack lưu trữ các khung để thực thi các phương thức bản địa (native - không phải Java), chẳng hạn như các hàm JNI viết bằng C hoặc C++.

> Xem thêm: Từ khóa `native` dùng để định nghĩa các phương thức bản địa trong mã Java, được trình bày chi tiết trong [Ch.10 - Access Modifiers](../../10-modifiers/theory/02-abstract-concepts.md).

Nó quan trọng vì khi mã Java gọi mã bản địa (như thư viện mật mã bản địa hoặc các API nền tảng), ngữ cảnh thực thi của luồng sẽ chuyển sang stack này. Việc tràn ngăn xếp ở đây có thể làm sụp đổ toàn bộ tiến trình JVM mà không ném ra lỗi StackOverflowError tiêu chuẩn của Java.

Kiểm tra thực tế:

- Định nghĩa `Native Method Stack` trong một câu.
- Nhận diện `Native Method Stack` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, giới hạn hoặc sự đánh đổi liên quan đến `Native Method Stack`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy tự hỏi: `Native Method Stack` thay đổi, cho phép, từ chối hay làm rõ điều gì?

## Các Ví Dụ Mã Nguồn

### Truy Vấn Thông Tin Bộ Nhớ Bằng Mã Lệnh
```java
Runtime runtime = Runtime.getRuntime();
long maxMemory = runtime.maxMemory();   // Equivalent to -Xmx
long totalMemory = runtime.totalMemory(); // Current heap size allocated
long freeMemory = runtime.freeMemory();   // Free space in current heap

System.out.println("Max Heap: " + (maxMemory / 1024 / 1024) + " MB");
```

## Các Lỗi Thường Gặp

- **Giả định StackOverflowError liên quan đến Heap**: Lỗi `StackOverflowError` xảy ra trong Thread Stack (ngăn xếp luồng) khi các khung cuộc gọi vượt quá giới hạn bộ nhớ ngăn xếp (thường là do đệ quy vô hạn). Điều này không liên quan đến Heap.
- **Lầm lẫn Metaspace với Heap**: Siêu dữ liệu lớp được lưu trữ trong Metaspace (ngoài heap / bộ nhớ bản địa) kể từ Java 8. Nó không cạnh tranh không gian Heap với các đối tượng Java, nhưng vẫn có thể làm cạn kiệt bộ nhớ bản địa nếu có quá nhiều lớp được tải lên.

## Các Câu Hỏi Ôn Tập Thường Gặp

- Những khái niệm nào ở đây là quy tắc tại thời điểm biên dịch?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy?
- Những khái niệm nào ở đây có khả năng là bẫy phỏng vấn?

## Tại Sao Việc Tải Lớp Có Ba Giai Đoạn Riêng Biệt

Phân hệ class loading của JVM chia việc tải lớp thành ba giai đoạn riêng biệt (Loading - Tải, Linking - Liên kết, và Initializing - Khởi tạo) để thực thi bảo mật, xác thực tính toàn vẹn cấu trúc và tối ưu hóa việc cấp phát bộ nhớ trước khi thực thi mã nguồn. Trong giai đoạn **Tải (Loading)**, JVM xác định vị trí biểu diễn nhị phân của một lớp (thường là tệp `.class`) và nhập nó vào Method Area/Metaspace, tạo ra một đối tượng `java.lang.Class`. Trong giai đoạn **Liên kết (Linking)**, JVM thực hiện Xác thực (Verification - quan trọng đối với bảo mật, kiểm tra định dạng, các ràng buộc bytecode và các quy tắc kiểu dữ liệu để ngăn chặn các hoạt động khai thác độc hại), Chuẩn bị (Preparation - cấp phát bộ nhớ cho các trường static và khởi tạo chúng về các giá trị mặc định), và Phân giải (Resolution - tùy chọn phân giải các tham chiếu tượng trưng thành các tham chiếu trực tiếp). Cuối cùng, trong giai đoạn **Khởi tạo (Initialization)**, JVM thực thi các khối khởi tạo tĩnh và gán các giá trị thực tế được khai báo trong code cho các biến static thông qua phương thức `<clinit>` do trình biên dịch tự động tạo ra.

### Các giai đoạn tải lớp (Mental Model)

```text
+-------------------------------------------------------------------------------+
|                        QUÁ TRÌNH TẢI LỚP (CLASS LOADING)                      |
+-------------------------------------------------------------------------------+
|  1. TẢI (LOADING)    |  2. LIÊN KẾT (LINKING)                      |  3. KHỞI TẠO|
|                      |  a. Xác thực -> b. Chuẩn bị -> c. Phân giải |    (INIT)   |
|  [Tìm bytecode]      |  [Xác thực an toàn] [Cấp phát mặc định]     | [<clinit>   |
|  file .class -> JVM  |  Kiểm tra kiểu      static x = 0  symbols   |  x = 42]    |
+-------------------------------------------------------------------------------+
```

### Ví Dụ Mã Nguồn

```java
package theory;

public class ClassLoaderDemo {
    // Allocation of static memory occurs in Preparation, but value assignment occurs in Initialization
    public static final int CONSTANT_VAL = 42; 
    public static int mutableVal = 99;

    static {
        System.out.println("ClassLoaderDemo initialized!");
        mutableVal = 100;
    }

    public static void main(String[] args) {
        // Accessing CONSTANT_VAL (a constant compile-time value) does NOT trigger full initialization
        System.out.println("Constant: " + ClassLoaderDemo.CONSTANT_VAL);
        // Accessing mutableVal triggers static block execution (Initialization)
        System.out.println("Mutable Value: " + ClassLoaderDemo.mutableVal);
    }
}
/* Output:
Constant: 42
ClassLoaderDemo initialized!
Mutable Value: 100
*/
```

### Chuỗi Nguyên Nhân - Kết Quả

Classloader đọc luồng byte `.class` &rarr; Xác thực chạy các kiểm tra kiểu &rarr; Chuẩn bị cấp phát bộ nhớ với các giá trị mặc định &rarr; Khởi tạo chạy phương thức `<clinit>` &rarr; Lớp đã sẵn sàng hoàn toàn để sử dụng bởi ứng dụng.

> Xem thêm: Chi tiết về các loại ClassLoader và quá trình tải lớp, được trình bày chi tiết trong [Ch.32 - Classloader](../../32-classloader/theory/01-class-loading-process-concepts.md).

## Liên Kết Tham Khảo (Reference Links)

- https://docs.oracle.com/javase/specs/jvms/se21/html/jvms-5.html (Chương 5. Tải, Liên kết, và Khởi tạo)
