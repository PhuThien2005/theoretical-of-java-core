# Bảo mật cơ bản - Phần 2 (Basic Security - Part 2)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm một phần trọng tâm của **Bảo mật cơ bản (Basic Security)**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là từ vựng rời rạc.

## Phạm vi đề cương (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `Avoid insecure deserialization` | Quá trình tuần tự hóa (Serialization) chuyển đổi một đồ thị đối tượng (object graph) thành chuỗi byte để có thể lưu trữ hoặc truyền tải. |

## Ghi chú chi tiết (Detailed Notes)

### Tránh giải tuần tự hóa không an toàn (Avoid insecure deserialization)

Giải tuần tự hóa không an toàn (Insecure deserialization) xảy ra khi dữ liệu tuần tự hóa không đáng tin cậy được phân tích cú pháp, có khả năng dẫn đến lỗi Thực thi mã từ xa (Remote Code Execution - RCE), từ chối dịch vụ (denial of service), hoặc leo thang đặc quyền (privilege escalation).

Nó nguy hiểm vì quá trình giải tuần tự hóa của Java khởi tạo động các lớp và định cấu hình trạng thái của chúng mà không thực thi các hàm khởi tạo (constructors) tiêu chuẩn. Kẻ tấn công có thể xây dựng một dữ liệu tải (payload) chứa các lớp gadget (gadget classes) lồng nhau để thực thi mã độc khi giải tuần tự hóa (ví dụ: bên trong `readObject()`).

Kiểm tra thực tế:

- Định nghĩa `Avoid insecure deserialization` trong một câu.
- Nhận biết `Avoid insecure deserialization` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Avoid insecure deserialization`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `Avoid insecure deserialization` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Câu hỏi ôn tập phổ biến (Common Review Prompts)

- Khái niệm nào ở đây là quy tắc ở thời điểm biên dịch (compile-time)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi lúc chạy (runtime)?
- Khái niệm nào ở đây dễ là bẫy phỏng vấn (interview traps)?

## Các ví dụ code (Code Examples)

### Sử dụng ObjectInputFilter (Java 9+) để ngăn chặn giải tuần tự hóa không an toàn (Using ObjectInputFilter (Java 9+) to prevent insecure deserialization)
```java
try (FileInputStream fileIn = new FileInputStream("object.ser");
     ObjectInputStream in = new ObjectInputStream(fileIn)) {
     
    // Configure filter to only allow specific classes (whitelist approach)
    ObjectInputFilter filter = ObjectInputFilter.Config.createFilter("com.example.model.*;java.base/*;!*");
    in.setObjectInputFilter(filter);
    
    MyModel model = (MyModel) in.readObject();
    System.out.println("Object loaded safely: " + model);
}
```

## Các lỗi thường gặp (Common Mistakes)

- **Giải tuần tự hóa các luồng byte không đáng tin cậy**: Việc giải tuần tự hóa dữ liệu do người dùng cung cấp từ các yêu cầu mạng hoặc các cột cơ sở dữ liệu mà không có kiểm thực nghiêm ngặt. Điều này có thể cho phép kẻ tấn công thực hiện RCE nếu các thư viện chứa lỗ hổng (gadget) tồn tại trong classpath.
- **Không sử dụng từ khóa transient cho các trường nhạy cảm**: Quên đánh dấu các trường chứa mật khẩu, token, hoặc khóa là `transient`. Điều này khiến chúng bị tuần tự hóa và ghi vào luồng byte, có nguy cơ bị lộ thông tin.
- **Không sử dụng ObjectInputFilter**: Cho phép khởi tạo đồ thị lớp tùy ý trong quá trình giải tuần tự hóa. Luôn luôn sử dụng các bộ lọc lớp để giới hạn việc giải tuần tự hóa trong một danh sách cho phép (whitelist) gồm các lớp an toàn đã biết.

---

## Tại sao Giải tuần tự hóa không an toàn cho phép Thực thi mã từ xa (Why Insecure Deserialization Enables Remote Code Execution)

Cơ chế tuần tự hóa mặc định của Java, khi giải tuần tự hóa một luồng byte thông qua `ObjectInputStream.readObject()`, tự động khởi tạo tất cả các lớp được tham chiếu trong luồng dữ liệu, định cấu hình các trường của chúng từ dữ liệu byte, và gọi các phương thức vòng đời (lifecycle methods) như `readObject()` nếu chúng tồn tại. Nguy cơ cực kỳ nghiêm trọng là việc này xảy ra trước khi ứng dụng có bất kỳ cơ hội nào để kiểm thực dữ liệu — đồ thị đối tượng được hiện thực hóa trước rồi mới được kiểm tra sau.

Kẻ tấn công có thể chèn một luồng byte tuần tự hóa được thiết kế đặc biệt để khai thác điều này bằng cách xây dựng một dữ liệu tải chứa một chuỗi các lớp gadget (gadget classes) — các lớp vốn đã có sẵn trong classpath của JVM (trong các thư viện phổ biến như Apache Commons Collections, Spring, hoặc Groovy) mà khi các phương thức vòng đời của chúng được gọi trong quá trình giải tuần tự hóa, sẽ thực thi các câu lệnh do kẻ tấn công kiểm soát. Loại tấn công này được gọi là "chuỗi gadget giải tuần tự hóa" (deserialization gadget chain).

Trong một cuộc tấn công thực tế, kẻ tấn công liên kết chuỗi nhiều lệnh gọi `readObject()` và `hashCode()` trên nhiều đối tượng khác nhau sao cho lệnh gọi cuối cùng trong chuỗi sẽ kích hoạt `Runtime.exec("malicious_command")` — đạt được mục tiêu Thực thi mã từ xa (RCE) mà không cần khai thác lỗ hổng ở tầng mạng.

`ObjectInputFilter` (được giới thiệu từ Java 9) cung cấp một cơ chế danh sách cho phép (whitelist) lớp: bạn xác định lớp nào được phép giải tuần tự hóa, và JVM sẽ từ chối bất kỳ lớp nào không có trong danh sách cho phép trong quá trình phân tích cú pháp luồng dữ liệu — trước khi quá trình khởi tạo lớp diễn ra. Điều này bẻ gãy các chuỗi gadget ngay tại ranh giới cấp lớp.

### Mô hình tư duy: Chuỗi gadget giải tuần tự hóa (Mental Model: Deserialization Gadget Chain)
```
Kẻ tấn công tạo luồng byte độc hại:
[SerializedPayload] = [GadgetClass1 → GadgetClass2 → GadgetClass3 → Runtime.exec("cmd")]
                                                                               |
ObjectInputStream.readObject() khởi tạo tất cả các lớp                         |
    → GadgetClass1.readObject() gọi hashCode() trên GadgetClass2              |
    → GadgetClass2.hashCode() gọi compare() trên GadgetClass3                |
    → GadgetClass3.compare() gọi Runtime.exec("rm -rf /")  ←-----------------+
         ↑ Thực thi lệnh hệ điều hành tùy ý!


Khi sử dụng ObjectInputFilter:
[SerializedPayload] → Bộ lọc kiểm tra: GadgetClass1 có trong whitelist không? KHÔNG → TỪ CHỐI
→ Ngoại lệ được ném ra trước khi bất kỳ lớp nào được khởi tạo &rarr; Không có mã nào bị thực thi
```

### Ví dụ Code: Sử dụng Danh sách cho phép của ObjectInputFilter để ngăn chặn RCE (Code Example: ObjectInputFilter Whitelist to Prevent RCE)
```java
import java.io.*;

public class SafeDeserializationDemo {
    public static Object safeDeserialize(byte[] data) throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data))) {
            // Định nghĩa một whitelist lớp nghiêm ngặt — chỉ cho phép các lớp an toàn đã biết
            ObjectInputFilter safeFilter = ObjectInputFilter.Config.createFilter(
                "com.example.model.*;java.lang.String;java.util.ArrayList;!*"
                //  ^^ Cho phép các lớp này ^^ Cho phép String   ^^ Từ chối tất cả các lớp khác còn lại
            );
            in.setObjectInputFilter(safeFilter);

            return in.readObject();
            // Nếu payload chứa GadgetClass từ Apache Commons → bộ lọc sẽ từ chối nó
            // → InvalidClassException được ném ra → Không có RCE xảy ra
        }
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Luồng byte không đáng tin cậy được chuyển cho `ObjectInputStream.readObject()`
  → JVM khởi tạo tất cả các lớp trong luồng dữ liệu mà không qua kiểm thực
  → Chuỗi `readObject()` của lớp gadget được kích hoạt
  → `Runtime.exec()` được gọi với lệnh của kẻ tấn công
  → Đạt được RCE.
```

Khi có bộ lọc: lớp trong luồng dữ liệu được kiểm tra với whitelist &rarr; Các lớp không xác định bị từ chối trước khi khởi tạo &rarr; Chuỗi gadget không bao giờ được thực thi.

## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/ObjectInputFilter.html (ObjectInputFilter JavaDoc)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/ObjectInputStream.html (ObjectInputStream JavaDoc)
- https://docs.oracle.com/javase/tutorial/security/ (Java Security Tutorial)
