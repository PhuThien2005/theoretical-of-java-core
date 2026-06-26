# Quản lý bộ nhớ Java (Java Memory Management) - Phần 3

## Mục tiêu học tập (Learning Goal)

Tập tin này bao gồm một phần trọng tâm về **Quản lý bộ nhớ Java (Java Memory Management)**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, không phải là từ vựng riêng lẻ.

## Khái quát nội dung (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `OutOfMemoryError` | OutOfMemoryError là một khái niệm cụ thể trong Quản lý bộ nhớ Java; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ tên. |
| `StackOverflowError` | Stack lưu trữ các khung phương thức, các biến cục bộ, và luồng gọi cho mỗi luồng. |

## Ghi chú chi tiết (Detailed Notes)

### OutOfMemoryError

`java.lang.OutOfMemoryError` là một lỗi thời gian chạy (runtime error) xảy ra khi Máy ảo Java (JVM) không thể phân bổ một đối tượng vì nó bị hết bộ nhớ, và không thể giải phóng thêm bộ nhớ nào bằng Bộ thu gom rác (Garbage collection).

#### Quy tắc JVM
- OOM là một **Lỗi (Error)** (kế thừa `java.lang.VirtualMachineError`), chỉ ra một lỗi hệ thống nghiêm trọng mà các ứng dụng thông thường không nên bắt hoặc cố gắng phục hồi.
- Nó có thể xảy ra ở các vùng bộ nhớ khác nhau, được báo hiệu bởi thông điệp lỗi:
  - **`java.lang.OutOfMemoryError: Java heap space`**: Heap đã chứa đầy các đối tượng có thể tiếp cận được.
  - **`java.lang.OutOfMemoryError: GC OverLimit exceeded`**: GC đang dành quá nhiều thời gian (98%) để thu hồi quá ít bộ nhớ (<2%).
  - **`java.lang.OutOfMemoryError: Metaspace`**: Bộ nhớ native Metaspace bị cạn kiệt do tải quá nhiều lớp.
- **Chẩn đoán**: Sử dụng cờ `-XX:+HeapDumpOnOutOfMemoryError` và `-XX:HeapDumpPath` để tạo một tệp nhị phân `.hprof` làm heap dump phục vụ phân tích khi xảy ra lỗi OOM.

#### Ví dụ mã nguồn: Kịch bản OutOfMemoryError (Code Example: OutOfMemoryError Scenario)
```java
import java.util.ArrayList;
import java.util.List;

public class OOMDemo {
    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        
        // Infinite loop holding strong references to massive byte arrays.
        // The GC cannot reclaim these arrays because they are reachable from the list.
        while (true) {
            list.add(new byte[10 * 1024 * 1024]); // Allocate 10 MB per iteration
        }
    }
}
```

### StackOverflowError

`java.lang.StackOverflowError` là một lỗi thời gian chạy được ném ra khi không gian ngăn xếp (stack) của một luồng bị cạn kiệt.

#### Quy tắc JVM
- Tương tự như OOM, đây là một `VirtualMachineError` và không nên cố gắng bắt nó.
- Nó thường xảy ra khi ngăn xếp cuộc gọi (call stack) phát triển quá sâu do đệ quy (recursion), hoặc nếu các khung phương thức có kích thước cực kỳ lớn.
- Ngăn xếp luồng bị giới hạn kích thước (mặc định thường là 1MB trên các hệ thống 64-bit) và được cấu hình bằng cờ JVM `-Xss` (ví dụ: `-Xss512k`).

#### Ví dụ mã nguồn: Kịch bản StackOverflowError (Code Example: StackOverflowError Scenario)
```java
public class StackOverflowDemo {
    public static void main(String[] args) {
        recursiveCall(1);
    }

    // Bug: No base case to terminate recursion.
    // Each call pushes a new frame until the thread stack is completely full.
    private static void recursiveCall(int depth) {
        System.out.println("Depth: " + depth);
        recursiveCall(depth + 1); // Infinite recursion
    }
}
```

---

## Các lỗi thường gặp (Common Mistakes)

### 1. Bắt OutOfMemoryError hoặc StackOverflowError (Catching OutOfMemoryError or StackOverflowError)
Nhiều nhà phát triển viết các khối `try-catch (Throwable t)` hoặc `try-catch (OutOfMemoryError e)` với suy nghĩ rằng họ có thể phục hồi ứng dụng hoặc ghi log lỗi một cách an toàn.
**Cạm bẫy:** Khi lỗi OOM xảy ra, trạng thái của JVM đã bị tổn hại hoàn toàn. Bộ thu gom rác đã thất bại trong việc giải phóng bộ nhớ, các luồng bị đình trệ, và việc cố gắng ghi log hay thực thi mã phục hồi có thể tự nó cũng thất bại do một lỗi OOM khác.
**Cách xử lý đúng:** Hãy để JVM kết thúc (terminate), thu thập tệp heap dump, và khởi động lại tiến trình sau khi đã sửa mã nguồn hoặc điều chỉnh lại các giới hạn bộ nhớ.

### 2. Nhầm lẫn giữa lỗi Heap và lỗi Stack (Confusing Heap and Stack Errors)
- **Heap OOM**: Gây ra bởi rò rỉ bộ nhớ, các vấn đề về bộ nhớ đệm, hoặc đơn giản là xử lý quá nhiều dữ liệu cùng một lúc. Khắc phục bằng cách tối ưu hóa mã nguồn (loại bỏ rò rỉ) hoặc tăng `-Xmx`.
- **StackOverflowError**: Gây ra bởi lỗi logic (đệ quy vô hạn). Không thể khắc phục bằng cách tăng kích thước heap (`-Xmx`). Nó yêu cầu sửa lại logic đệ quy hoặc tăng kích thước stack (`-Xss`).

### 3. Giả định GC Overhead Limit Exceeded là lỗi không gian Heap (Assuming GC Overhead Limit Exceeded is a Heap space error)
Mặc dù có liên quan, lỗi `GC Overhead Limit exceeded` xảy ra *trước khi* không gian heap vật lý thực sự bị cạn kiệt hoàn toàn. JVM ném ra lỗi này một cách chủ động để ngăn ứng dụng bị đóng băng hoàn toàn khi không làm gì ngoài việc chạy bộ thu gom rác. Tăng kích thước heap có thể giúp ích, nhưng sửa lỗi rò rỉ bộ nhớ mới là giải pháp thực sự.

## Các câu hỏi ôn tập thường gặp (Common Review Prompts)

- Khái niệm nào ở đây là quy tắc thời gian biên dịch (compile-time rule)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi thời gian chạy (runtime behavior)?
- Khái niệm nào ở đây dễ là bẫy phỏng vấn?

## Tại sao lỗi Heap và lỗi Stack khác nhau (Why Heap and Stack Errors Differ)

JVM cách ly các cấu trúc cuộc gọi riêng biệt của từng luồng khỏi dữ liệu ứng dụng dùng chung bằng cách chia bộ nhớ của nó thành các vùng riêng biệt, dẫn đến các loại lỗi khác nhau khi tài nguyên bị cạn kiệt. OutOfMemoryError xảy ra trong Heap hoặc Metaspace khi phân bổ bộ nhớ động vượt quá giới hạn không gian vật lý và Bộ thu gom rác không thể giải phóng thêm bất kỳ không gian nào. Mặt khác, StackOverflowError xảy ra trong ngăn xếp (stack) riêng của luồng khi các khung cuộc gọi phương thức phát triển quá sâu và làm cạn kiệt khe bộ nhớ ngăn xếp được phân bổ. Việc bắt các lớp con VirtualMachineError này trong mã nguồn ứng dụng là một anti-pattern nguy hiểm vì trạng thái nội bộ của JVM đã bị ảnh hưởng và không thể đảm bảo sự ổn định. Việc cố gắng thực hiện các thao tác phục hồi hoặc ghi nhật ký sau khi lỗi xảy ra rất dễ bị thất bại, có khả năng gây ra lỗi thứ cấp như lỗi OutOfMemoryError lồng nhau.

### Mô hình tư duy (Mental Model)
```
+-------------------------------------------------------------+
| StackOverflowError (Thread Stack)                           |
| [ Frame n ] - Vượt quá giới hạn Stack (Vòng lặp đệ quy)      |
|   ...                                                       |
| [ Frame 1 ] - Cuộc gọi phương thức ban đầu                  |
+-------------------------------------------------------------+

+-------------------------------------------------------------+
| OutOfMemoryError (Heap/Metaspace)                           |
| [ Đối tượng tiếp cận | Đối tượng tiếp cận | ... ]           |
| Heap đầy 100%. GC không thể giải phóng chỗ cho phân bổ mới    |
+-------------------------------------------------------------+
```

### Ví dụ mã nguồn (Code Example)
```java
public class MemoryErrorsDemo {
    public static void main(String[] args) {
        try {
            causeStackOverflow(1);
        } catch (StackOverflowError e) {
            System.err.println("Caught StackOverflowError");
        }
    }

    private static void causeStackOverflow(int depth) {
        causeStackOverflow(depth + 1); // Infinite recursion
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Kích thước ngăn xếp luồng bị vượt quá
  → StackOverflowError được ném ra
  → Trạng thái hệ thống bị tổn hại
  → Mã phục hồi cố gắng bắt Lỗi
  → Thao tác ghi log yêu cầu thêm bộ nhớ
  → Xảy ra lỗi OutOfMemoryError lồng nhau/Sập hệ thống.
```


## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/javase/specs/jvms/se21/html/jvms-2.html#jvms-2.5 (Run-Time Data Areas)
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-11.html#jls-11.1.1 (Kinds of Exceptions - Errors)
