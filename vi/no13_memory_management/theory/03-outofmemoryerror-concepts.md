# Quản Lý Bộ Nhớ Java - Phần 3 (Java Memory Management - Part 3)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này bao gồm một phần nội dung trọng tâm về **Quản lý bộ nhớ Java (Java Memory Management)**. Hãy nghiên cứu từng khái niệm dưới dạng quy tắc thực tế trong Java, không chỉ đơn thuần là các từ vựng học thuật.

## Đề Cương Nội Dung (Outline Coverage)

- **`OutOfMemoryError`** — Ngoại lệ nghiêm trọng xảy ra khi JVM không thể cấp phát bộ nhớ heap/native.
- **`StackOverflowError`** — StackOverflowError: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

## Ghi Chú Chi Tiết (Detailed Notes)

### Ngoại Lệ OutOfMemoryError (OutOfMemoryError)

`java.lang.OutOfMemoryError` là một lỗi runtime xảy ra khi Máy ảo Java (JVM) không thể cấp phát bộ nhớ cho một đối tượng mới vì đã cạn kiệt bộ nhớ, và Bộ thu gom rác (Garbage Collector - GC) không thể giải phóng thêm bất kỳ vùng nhớ nào.

#### Quy Tắc JVM (JVM Rule)
- OOM là một **Lỗi (Error)** (kế thừa từ `java.lang.VirtualMachineError`), biểu thị một sự cố hệ thống nghiêm trọng mà các ứng dụng thông thường không nên cố gắng bắt lấy (catch) hoặc phục hồi.
- Lỗi này có thể xảy ra ở các phân vùng bộ nhớ khác nhau, được phân biệt qua thông báo lỗi:
- **`java.lang.OutOfMemoryError: Java heap space`** — java.lang.OutOfMemoryError: Java heap space: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`java.lang.OutOfMemoryError: GC OverLimit exceeded`** — java.lang.OutOfMemoryError: GC OverLimit exceeded: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`java.lang.OutOfMemoryError: Metaspace`** — java.lang.OutOfMemoryError: Metaspace: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **Chẩn đoán (Diagnostics)**: Sử dụng các cờ cấu hình JVM `-XX:+HeapDumpOnOutOfMemoryError` và `-XX:HeapDumpPath` để tự động tạo ra tệp tin Heap dump nhị phân dạng `.hprof` nhằm phân tích heap khi xảy ra lỗi OOM.

#### Ví Dụ Minh Họa Code: Kịch Bản Gây Ra OutOfMemoryError (Code Example: OutOfMemoryError Scenario)
```java
import java.util.ArrayList;
import java.util.List;

public class OOMDemo {
    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        
        // Vòng lặp vô hạn giữ các tham chiếu mạnh (strong reference) tới các mảng byte dung lượng lớn.
        // GC không thể thu hồi các mảng này vì chúng vẫn có thể tiếp cận được từ danh sách list.
        while (true) {
            list.add(new byte[10 * 1024 * 1024]); // Cấp phát 10 MB sau mỗi vòng lặp
        }
    }
}
```

### Ngoại Lệ StackOverflowError (StackOverflowError)

`java.lang.StackOverflowError` là một lỗi runtime xảy ra khi không gian ngăn xếp (stack space) của một luồng thực thi bị cạn kiệt hoàn toàn.

#### Quy Tắc JVM (JVM Rule)
- Tương tự như OOM, đây là một lỗi hệ thống `VirtualMachineError` và không nên được bắt lấy trong mã nguồn.
- Lỗi này thường xảy ra khi ngăn xếp gọi hàm (call stack) phát triển quá sâu do các hàm đệ quy (recursion), hoặc khi các khung phương thức (method frame) chiếm dụng dung lượng quá lớn.
- Kích thước ngăn xếp của mỗi luồng là có giới hạn (mặc định thường là 1MB trên các hệ thống 64-bit) và được cấu hình bằng cờ JVM `-Xss` (ví dụ: `-Xss512k`).

#### Ví Dụ Minh Họa Code: Kịch Bản Gây Ra StackOverflowError (Code Example: StackOverflowError Scenario)
```java
public class StackOverflowDemo {
    public static void main(String[] args) {
        recursiveCall(1);
    }

    // Lỗi: Không có điều kiện dừng để kết thúc đệ quy.
    // Mỗi lời gọi hàm đẩy một khung ngăn xếp mới vào cho đến khi ngăn xếp của luồng đầy hoàn toàn.
    private static void recursiveCall(int depth) {
        System.out.println("Độ sâu: " + depth);
        recursiveCall(depth + 1); // Đệ quy vô hạn
    }
}
```

---

## Các Lỗi Thường Gặp (Common Mistakes)

### 1. Bắt Lỗi OutOfMemoryError hoặc StackOverflowError (Catching OutOfMemoryError or StackOverflowError)
Nhiều lập trình viên viết các khối lệnh `try-catch (Throwable t)` hoặc `try-catch (OutOfMemoryError e)` với suy nghĩ có thể khôi phục ứng dụng hoặc ghi log lỗi một cách an toàn.
**Cạm bẫy:** Khi lỗi OOM xảy ra, trạng thái của JVM đã bị tổn hại hoàn toàn. Bộ thu gom rác đã thất bại trong việc giải phóng bộ nhớ, các luồng bị dừng đột ngột, và việc cố gắng ghi log hoặc chạy mã khôi phục có thể tự nó lại bị thất bại do một lỗi OOM khác.
**Cách khắc phục:** Hãy để JVM tự chấm dứt, thu thập tệp tin Heap dump, và khởi động lại tiến trình sau khi đã sửa lỗi code hoặc điều chỉnh lại giới hạn bộ nhớ.

### 2. Nhầm Lẫn Giữa Lỗi Bộ Nhớ Heap và Ngăn Xếp (Confusing Heap and Stack Errors)
- **Heap OOM**: Gây ra bởi rò rỉ bộ nhớ (memory leak), các vấn đề về bộ nhớ đệm (caching), hoặc do xử lý quá nhiều dữ liệu cùng lúc. Lỗi này được khắc phục bằng cách tối ưu hóa code (loại bỏ rò rỉ vùng nhớ) hoặc tăng dung lượng heap qua cờ `-Xmx`.
- **StackOverflowError**: Gây ra bởi lỗi logic (đệ quy vô hạn). Lỗi này không thể giải quyết bằng cách tăng dung lượng Heap (`-Xmx`). Nó bắt buộc phải được sửa đổi ở logic đệ quy hoặc tăng kích thước ngăn xếp của luồng qua cờ `-Xss`.

### 3. Cho rằng GC Overhead Limit Exceeded là lỗi không gian Heap thông thường (Assuming GC Overhead Limit Exceeded is a Heap space error)
Mặc dù có liên quan, lỗi `GC Overhead Limit exceeded` xảy ra *trước khi* không gian Heap vật lý bị cạn kiệt hoàn toàn. JVM ném ra lỗi này một cách chủ động để ngăn ứng dụng rơi vào trạng thái đóng băng hoàn toàn khi chỉ liên tục thực hiện dọn rác mà không xử lý được gì. Việc tăng dung lượng Heap có thể giúp ích tạm thời, nhưng giải quyết triệt để rò rỉ bộ nhớ mới là giải pháp thực sự.

## Các Câu Hỏi Ôn Tập Thường Gặp (Common Review Prompts)

- Khái niệm nào ở đây thuộc về quy tắc thời điểm biên dịch (compile-time)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi lúc runtime?
- Khái niệm nào ở đây dễ là cạm bẫy trong các buổi phỏng vấn?

## Tại Sao Lỗi Heap và Stack Lại Khác Nhau (Why Heap and Stack Errors Differ)

JVM phân tách các cấu trúc gọi hàm riêng biệt của luồng khỏi dữ liệu ứng dụng dùng chung bằng cách chia bộ nhớ thành các phân vùng khác nhau, dẫn đến các loại lỗi khác nhau khi cạn kiệt tài nguyên. Lỗi `OutOfMemoryError` xảy ra trong bộ nhớ Heap hoặc Metaspace khi việc cấp phát bộ nhớ động vượt quá giới hạn không gian vật lý và GC không thể thu hồi thêm vùng nhớ nào. Mặt khác, lỗi `StackOverflowError` xảy ra bên trong ngăn xếp riêng của luồng khi các khung gọi phương thức phát triển quá sâu và làm tràn vùng nhớ ngăn xếp được cấp phát cho luồng đó. Việc bắt các lớp con của `VirtualMachineError` này trong code ứng dụng là một mẫu thiết kế phản tác dụng nguy hiểm (anti-pattern) vì trạng thái nội bộ của JVM đã bị hủy hoại và không thể đảm bảo tính ổn định. Việc cố gắng thực hiện các thao tác khôi phục hoặc ghi log sau khi lỗi xảy ra rất dễ bị thất bại, có khả năng gây ra các lỗi thứ cấp như lỗi `OutOfMemoryError` lồng nhau.

### Mô Hình Tư Duy (Mental Model)
```
+-------------------------------------------------------------+
| StackOverflowError (Ngăn xếp của Luồng)                     |
| [ Khung n ] - Vượt quá giới hạn ngăn xếp (Vòng lặp đệ quy)  |
|   ...                                                       |
| [ Khung 1 ] - Lời gọi phương thức ban đầu                  |
+-------------------------------------------------------------+

+-------------------------------------------------------------+
| OutOfMemoryError (Heap/Metaspace)                           |
| [ Đối tượng có thể tiếp cận | Đối tượng tiếp cận được | ... ]|
| Heap đã đầy 100%. GC không thể giải phóng vùng nhớ cho mới  |
+-------------------------------------------------------------+
```

### Ví Dụ Minh Họa Code (Code Example)
```java
public class MemoryErrorsDemo {
    public static void main(String[] args) {
        try {
            causeStackOverflow(1);
        } catch (StackOverflowError e) {
            System.err.println("Đã bắt được lỗi StackOverflowError");
        }
    }

    private static void causeStackOverflow(int depth) {
        causeStackOverflow(depth + 1); // Đệ quy vô hạn
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)
Kích thước ngăn xếp luồng bị vượt quá &rarr; Lỗi StackOverflowError được ném ra &rarr; Trạng thái hệ thống bị tổn hại &rarr; Mã khôi phục cố gắng bắt Error &rarr; Thao tác ghi log yêu cầu cấp phát bộ nhớ &rarr; Xảy ra lỗi OutOfMemoryError lồng nhau/Sập chương trình.

## Liên Kết Tham Khảo (Reference Links)

- https://docs.oracle.com/javase/specs/jvms/se21/html/jvms-2.html#jvms-2.5 (Các phân vùng dữ liệu lúc Runtime)
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-11.html#jls-11.1.1 (Các loại Ngoại lệ - Lỗi)
