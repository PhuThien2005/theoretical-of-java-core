# Thuật ngữ JVM nâng cao (Advanced JVM Terms)

Sử dụng tài liệu này khi một từ khóa trong phần lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều có định nghĩa, tầm quan trọng, hiểu lầm thường gặp và một ví dụ nhỏ.

## Máy ảo Java (JVM)

Một máy tính trừu tượng cho phép máy tính chạy chương trình Java bằng cách thực thi mã bytecode Java và quản lý động các tài nguyên hệ thống (bộ nhớ, luồng và I/O).

- **Tầm quan trọng**: Nó cung cấp tính độc lập với nền tảng ("Viết một lần, Chạy mọi nơi") và xử lý các chi tiết cấp thấp như tự động quản lý bộ nhớ (Thu gom rác - Garbage Collection) và nạp mã động.
- **Hiểu lầm thường gặp**: Nhầm lẫn JVM với JRE (bao gồm các thư viện tiêu chuẩn) hoặc JDK (bao gồm các trình biên dịch và công cụ phát triển). JVM hoàn toàn chỉ là công cụ thực thi.
- **Ví dụ nhỏ**: Chạy `java MyApp` sẽ khởi động một thực thể JVM để tải, xác thực và thực thi tệp `MyApp.class`.

## Bộ nạp lớp (ClassLoader)

Một phân hệ của JVM chịu trách nhiệm tải động các tệp lớp (class file) vào Vùng phương thức (Method Area)/Metaspace tại thời điểm chạy bằng cách sử dụng phân cấp tra cứu dựa trên cơ chế ủy quyền (delegation-based lookup).

- **Tầm quan trọng**: Nó cho phép các tệp lớp được tải động từ nhiều nguồn khác nhau (hệ thống tệp cục bộ, mạng, tệp JAR) và thực thi các ranh giới bảo mật không gian tên (namespace).
- **Hiểu lầm thường gặp**: Nghĩ rằng tất cả các lớp đều được tải sẵn (eagerly) khi JVM khởi động. Việc nạp lớp là lười biếng (lazy), chỉ xảy ra khi một lớp được tham chiếu lần đầu tiên trong mã nguồn.
- **Ví dụ nhỏ**: Gọi `MyClass.class.getClassLoader()` trả về thực thể ClassLoader (ví dụ: `AppClassLoader`) chịu trách nhiệm tải lớp cụ thể đó.

## Trình biên dịch JIT (JIT compiler)

Một thành phần quan trọng về hiệu năng của Công cụ Thực thi JVM (JVM Execution Engine) giúp biên dịch mã bytecode được thực thi thường xuyên (điểm nóng - hot spot) thành mã máy bản địa (native machine code) đã được tối ưu hóa tại thời điểm chạy.

- **Tầm quan trọng**: Nó rút ngắn khoảng cách giữa việc khởi động ứng dụng nhanh chóng (thông qua trình thông dịch bytecode) và tốc độ thực thi đỉnh cao, tối ưu hóa mã nguồn ngay trong quá trình chạy dựa trên dữ liệu phân tích hiệu năng thực tế.
- **Hiểu lầm thường gặp**: Tin rằng Java hoàn toàn là ngôn ngữ thông dịch hoặc biên dịch. Thực tế là cả hai; `javac` biên dịch mã nguồn thành bytecode, và trình biên dịch JIT biên dịch các đoạn bytecode nóng thành mã máy tại thời điểm chạy.
- **Ví dụ nhỏ**: Chạy một vòng lặp 10.000 lần sẽ kích hoạt các trình biên dịch C1/C2 biên dịch thân vòng lặp, giảm thời gian thực thi của nó từ mili giây xuống micro giây.

## Vùng Eden (Eden)

Vùng bộ nhớ trong Thế hệ Trẻ (Young Generation) của bộ nhớ Heap, nơi tất cả các đối tượng mới tạo ban đầu được cấp phát.

- **Tầm quan trọng**: Nó cho phép cấp phát cực kỳ nhanh bằng cách sử dụng cơ chế tịnh tiến con trỏ tuần tự (pointer-bump), phù hợp với thực tế là hầu hết các đối tượng đều có vòng đời ngắn.
- **Hiểu lầm thường gặp**: Tin rằng các đối tượng nằm lại ở vùng Eden trong suốt vòng đời của chúng. Vùng Eden sẽ bị xóa hoàn toàn trong mỗi đợt Minor GC (thu gom rác thế hệ trẻ), và các đối tượng sống sót sẽ được di tản sang các vùng sống sót (Survivor space).
- **Ví dụ nhỏ**: Thực thi lệnh `new MyObject()` sẽ lập tức chiếm dụng một khối bộ nhớ trong vùng Eden.

## Các vùng sống sót (Survivor spaces)

Hai vùng bộ nhớ giống hệt nhau (Survivor 0 / S0 và Survivor 1 / S1) trong Thế hệ Trẻ được sử dụng như một vùng già hóa trung gian cho các đối tượng sống sót qua các đợt Minor GC.

- **Tầm quan trọng**: Chúng ngăn chặn sự phân mảnh bộ nhớ Heap bằng cách sao chép và di tản các đối tượng sống sót qua lại, cho phép JVM tăng tuổi của đối tượng trước khi nâng chúng lên Thế hệ Già (Old Generation).
- **Hiểu lầm thường gặp**: Nghĩ rằng cả hai vùng Survivor đều được sử dụng đồng thời. Chỉ có một vùng Survivor (vùng nguồn "from") là hoạt động tại một thời điểm; vùng còn lại (vùng đích "to") phải để trống để nhận các bản sao trong đợt Minor GC tiếp theo.
- **Ví dụ nhỏ**: Trong một đợt Minor GC, các đối tượng sống sót ở Eden và S0 được sao chép sang S1, S0 bị xóa sạch, và S1 trở thành vùng Survivor hoạt động.

## Bộ thu gom rác Shenandoah (Shenandoah GC)

Một bộ thu gom rác có thời gian tạm dừng ngắn (low-pause-time) thực hiện việc nén bộ nhớ (compaction) đồng thời với các luồng ứng dụng Java đang chạy, giữ cho thời gian tạm dừng ở mức tối thiểu bất kể kích thước Heap lớn như thế nào.

- **Tầm quan trọng**: Nó ngăn chặn các lần tạm dừng Stop-The-World (STW) kéo dài trong các ứng dụng có Heap lớn, giúp Java phù hợp với các hệ thống thời gian thực, độ trễ thấp.
- **Hiểu lầm thường gặp**: Giả định rằng Shenandoah loại bỏ hoàn toàn thời gian tạm dừng. Nó vẫn yêu cầu các lần tạm dừng rất ngắn để quét tập hợp gốc (root set scanning - thường dưới một mili giây).
- **Ví dụ nhỏ**: Kích hoạt Shenandoah thông qua cờ `-XX:+UseShenandoahGC` để giữ các lần tạm dừng GC dưới 10 mili giây trên một bộ nhớ Heap 100GB.

## Con trỏ Brooks (Brooks Pointer)

Một trường tham chiếu bổ sung được thêm vào trước mỗi tiêu đề đối tượng (object header) trong các triển khai bộ thu gom rác Shenandoah cũ hơn, trỏ đến chính đối tượng đó hoặc bản sao đã được chuyển tiếp của nó.

- **Tầm quan trọng**: Nó cho phép nén bộ nhớ đồng thời bằng cách chuyển hướng các thao tác đọc và ghi đến đúng vị trí bộ nhớ của một đối tượng trong khi bộ thu gom rác GC đang di chuyển nó.
- **Hiểu lầm thường gặp**: Nghĩ rằng các luồng ứng dụng phải chờ GC hoàn thành việc di chuyển đối tượng. Với Con trỏ Brooks, các luồng truy cập ngay lập tức vào bản sao mới thông qua con trỏ được chuyển hướng.
- **Ví dụ nhỏ**: Một luồng ứng dụng cố gắng ghi vào `obj.field`; một rào cản tải (load barrier) kiểm tra Con trỏ Brooks, chuyển hướng đến bản sao trong vùng đích "to-space", và thực hiện thao tác ghi.

## Các cờ JVM (JVM flags)

Các tùy chọn dòng lệnh được truyền cho trình chạy Java để cấu hình hành vi của JVM, tinh chỉnh cấp phát bộ nhớ, lựa chọn thuật toán GC, và kiểm soát hành vi của trình biên dịch.

- **Tầm quan trọng**: Chúng cho phép người vận hành tối ưu hóa hiệu năng ứng dụng cho các môi trường phần cứng cụ thể mà không cần thay đổi bất kỳ mã nguồn nào.
- **Hiểu lầm thường gặp**: Giả định rằng tất cả các cờ đều được hỗ trợ trên mọi phiên bản Java hoặc nhà cung cấp. Các cờ tuyển chuẩn thì ổn định, nhưng các tùy chọn `-XX` có thể bị gỡ bỏ hoặc thay đổi hành vi giữa các bản cập nhật JDK.
- **Ví dụ nhỏ**: Thiết lập `-Xms512m -Xmx1024m -XX:+UseG1GC` sẽ cấu hình dung lượng bộ nhớ Heap ban đầu, dung lượng tối đa, và loại bộ thu gom rác.
