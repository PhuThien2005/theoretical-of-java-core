# Bảo mật cơ bản (Basic Security) - Phần 2

## Mục Tiêu Học Tập

Tài liệu này trình bày một phần trọng tâm của **Bảo mật cơ bản (Basic Security)**. Hãy nghiên cứu từng khái niệm dưới dạng một quy tắc Java thực tế, thay vì chỉ học từ vựng riêng lẻ.

## Nội Dung Tổng Quan

- **`Avoid insecure deserialization`** — Tuần tự hóa (Serialization) chuyển đổi một đồ thị đối tượng thành các byte để có thể lưu trữ hoặc truyền đi.

## Ghi Chú Chi Tiết

### Tránh giải tuần tự hóa không an toàn (Avoid insecure deserialization)

Giải tuần tự hóa không an toàn (Insecure deserialization) xảy ra khi dữ liệu đã tuần tự hóa không đáng tin cậy được phân tích cú pháp, có khả năng dẫn đến Thực thi mã từ xa (RCE - Remote Code Execution), từ chối dịch vụ (denial of service), hoặc leo thang đặc quyền (privilege escalation).

> Xem thêm: Cơ chế Tuần tự hóa cơ bản trong Java, được trình bày chi tiết trong [Ch.26 - IO](../../26-io/theory/03-serialization-concepts.md).

Khái niệm này rất quan trọng vì quá trình giải tuần tự hóa của Java khởi tạo các lớp một cách động và cấu hình trạng thái của chúng mà không cần thực thi các hàm khởi tạo (constructor) tiêu chuẩn. Kẻ tấn công có thể xây dựng một payload chứa các lớp con lồng nhau gọi là "lớp tiện ích (gadget class)", các lớp này sẽ thực thi mã độc hại khi giải tuần tự hóa (ví dụ: bên trong phương thức `readObject()`).

Kiểm tra thực tế:

- Định nghĩa `Avoid insecure deserialization` trong một câu.
- Nhận biết `Avoid insecure deserialization` trong mã nguồn, câu lệnh, tài liệu hoặc các câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế hoặc đánh đổi liên quan đến `Avoid insecure deserialization`.

Ví dụ nhỏ hoặc mô hình tư duy:

- Khi đọc mã nguồn, hãy hỏi: `Avoid insecure deserialization` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Các Câu Hỏi Ôn Tập Thường Gặp

- Những khái niệm nào ở đây là quy tắc trong thời gian biên dịch (compile-time)?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi khi chạy ứng dụng (runtime)?
- Những khái niệm nào ở đây có khả năng là bẫy phỏng vấn?

## Các Ví Dụ Mã Nguồn

### Sử dụng ObjectInputFilter (Java 9+) để ngăn chặn giải tuần tự hóa không an toàn
```java
try (FileInputStream fileIn = new FileInputStream("object.ser");
     ObjectInputStream in = new ObjectInputStream(fileIn)) {
     
    // Cấu hình bộ lọc để chỉ cho phép các lớp cụ thể (tiếp cận theo danh sách trắng - whitelist)
    ObjectInputFilter filter = ObjectInputFilter.Config.createFilter("com.example.model.*;java.base/*;!*");
    in.setObjectInputFilter(filter);
    
    MyModel model = (MyModel) in.readObject();
    System.out.println("Object loaded safely: " + model);
}
```

## Sai Lầm Thường Gặp

- **Giải tuần tự hóa các luồng byte không đáng tin cậy**: Giải tuần tự hóa dữ liệu do người dùng cung cấp từ các yêu cầu mạng hoặc các cột cơ sở dữ liệu mà không có xác thực nghiêm ngặt. Điều này có thể cho phép kẻ tấn công thực thi mã từ xa nếu tồn tại các thư viện dễ bị tấn công (gadget) trong đường dẫn lớp (classpath).
- **Không sử dụng transient cho các trường nhạy cảm**: Quên đánh dấu các trường chứa mật khẩu, mã token hoặc khóa là `transient`. Điều này khiến chúng bị tuần tự hóa và ghi vào luồng byte, dẫn đến rủi ro rò rỉ thông tin.
- **Không sử dụng ObjectInputFilter**: Cho phép các đồ thị lớp tùy ý được khởi tạo trong quá trình giải tuần tự hóa. Hãy luôn sử dụng bộ lọc lớp để giới hạn việc giải tuần tự hóa trong một danh sách trắng gồm các lớp an toàn đã biết.

---

## Tại Sao Giải Tuần Tự Hóa Không An Toàn Lại Cho Phép Thực Thi Mã Từ Xa (RCE)

Cơ chế tuần tự hóa mặc định của Java, khi giải tuần tự hóa một luồng byte thông qua `ObjectInputStream.readObject()`, sẽ tự động khởi tạo tất cả các lớp được tham chiếu trong luồng, cấu hình các trường của chúng từ dữ liệu byte và gọi các phương thức vòng đời như `readObject()` nếu chúng tồn tại. Mối nguy hiểm cực kỳ nghiêm trọng là việc này diễn ra trước khi ứng dụng có bất kỳ cơ hội nào để xác thực dữ liệu — đồ thị lớp được hiện thực hóa trước rồi mới được kiểm tra sau.

Kẻ tấn công có thể chèn một luồng byte tuần tự hóa được thiết kế tinh vi để khai thác điều này bằng cách xây dựng một payload chứa một chuỗi các "lớp tiện ích (gadget class)" — các lớp đã có sẵn trong classpath của JVM (trong các thư viện phổ biến như Apache Commons Collections, Spring, hoặc Groovy) để khi các phương thức vòng đời của chúng được gọi trong quá trình giải tuần tự hóa, chúng sẽ thực thi các lệnh do kẻ tấn công kiểm soát. Loại tấn công này được gọi là "chuỗi tiện ích giải tuần tự hóa (deserialization gadget chain)".

Trong một cuộc tấn công thực tế, kẻ tấn công liên kết nhiều lệnh gọi `readObject()` và `hashCode()` trên nhiều đối tượng khác nhau sao cho lệnh gọi cuối cùng trong chuỗi sẽ kích hoạt `Runtime.exec("malicious_command")` — từ đó đạt được Thực thi mã từ xa (RCE) mà không cần khai thác lỗ hổng ở tầng mạng.

`ObjectInputFilter` (được giới thiệu từ Java 9) cung cấp cơ chế danh sách trắng (whitelist) cho lớp: bạn định nghĩa những lớp nào được phép giải tuần tự hóa, và JVM sẽ từ chối bất kỳ lớp nào không nằm trong danh sách trắng trong quá trình phân tích luồng — trước khi việc khởi tạo đối tượng diễn ra. Điều này bẻ gãy các chuỗi tiện ích (gadget chain) ngay tại ranh giới cấp độ lớp.

### Mô Hình Tư Duy: Chuỗi Tiện Ích Giải Tuần Tự Hóa
```
Kẻ tấn công tạo luồng byte độc hại:
[SerializedPayload] = [GadgetClass1 → GadgetClass2 → GadgetClass3 → Runtime.exec("cmd")]
                                                                               |
ObjectInputStream.readObject() khởi tạo tất cả các lớp                          |
    → GadgetClass1.readObject() gọi hashCode() trên GadgetClass2             |
    → GadgetClass2.hashCode() gọi compare() trên GadgetClass3                |
    → GadgetClass3.compare() gọi Runtime.exec("rm -rf /")  ←----------------+
         ↑ Lệnh OS tùy ý được thực thi!


Khi có ObjectInputFilter:
[SerializedPayload] → Filter kiểm tra: GadgetClass1 có trong whitelist không? KHÔNG → TỪ CHỐI
→ Ngoại lệ được ném ra trước khi bất kỳ lớp nào được khởi tạo → Không có mã nào được thực thi
```

### Ví Dụ Mã Nguồn: ObjectInputFilter Whitelist để Ngăn Chặn RCE
```java
import java.io.*;

public class SafeDeserializationDemo {
    public static Object safeDeserialize(byte[] data) throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data))) {
            // Định nghĩa một danh sách trắng nghiêm ngặt — chỉ cho phép các lớp an toàn đã biết
            ObjectInputFilter safeFilter = ObjectInputFilter.Config.createFilter(
                "com.example.model.*;java.lang.String;java.util.ArrayList;!*"
                //  ^^ Cho phép các lớp này  ^^ Cho phép String      ^^ Từ chối mọi thứ khác
            );
            in.setObjectInputFilter(safeFilter);

            return in.readObject();
            // Nếu payload chứa GadgetClass từ Apache Commons → bộ lọc sẽ từ chối nó
            // → Ném ra InvalidClassException → Không bị RCE
        }
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả
Luồng byte không đáng tin cậy được truyền vào `ObjectInputStream.readObject()` &rarr; JVM khởi tạo tất cả các lớp trong luồng mà không qua xác thực &rarr; Chuỗi gọi `readObject()` của lớp tiện ích (gadget) được kích hoạt &rarr; `Runtime.exec()` được gọi với lệnh của kẻ tấn công &rarr; Đạt được RCE. Khi có bộ lọc: lớp của luồng được kiểm tra đối chiếu danh sách trắng &rarr; Lớp không xác định bị từ chối trước khi khởi tạo &rarr; Chuỗi tiện ích không bao giờ được thực thi.

## Liên Kết Tham Khảo

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/ObjectInputFilter.html (ObjectInputFilter JavaDoc)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/ObjectInputStream.html (ObjectInputStream JavaDoc)
- https://docs.oracle.com/javase/tutorial/security/ (Java Security Tutorial)
