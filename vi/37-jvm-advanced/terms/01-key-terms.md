# Thuật ngữ JVM nâng cao (Advanced JVM Terms)

Hãy sử dụng tài liệu này khi một từ ngữ trong phần lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều đi kèm ý nghĩa, tầm quan trọng, sự nhầm lẫn phổ biến và một ví dụ nhỏ.

## JVM (JVM)

Một máy tính trừu tượng cho phép máy tính chạy chương trình Java bằng cách thực thi bytecode Java và quản lý động các tài nguyên hệ thống (bộ nhớ, luồng và I/O).

- **Tại sao nó quan trọng**: Nó cung cấp tính độc lập nền tảng ("Viết một lần, chạy mọi nơi") và xử lý các chi tiết cấp thấp như quản lý bộ nhớ tự động (Thu gom rác - Garbage Collection) và nạp mã động.
- **Sự nhầm lẫn phổ biến**: Nhầm lẫn JVM với JRE (bao gồm các thư viện tiêu chuẩn) hoặc JDK (bao gồm các trình biên dịch và công cụ phát triển). JVM thực chất chỉ là động cơ thực thi (execution engine).
- **Ví dụ nhỏ**: Chạy lệnh `java MyApp` sẽ khởi tạo một instance JVM để nạp, xác thực và thực thi `MyApp.class`.

## ClassLoader (ClassLoader)

Một phân hệ của JVM chịu trách nhiệm nạp động các tệp class vào Vùng phương thức (Method Area) / Metaspace tại thời điểm chạy bằng cách sử dụng một phân cấp tra cứu dựa trên ủy quyền (delegation-based lookup hierarchy).

- **Tại sao nó quan trọng**: Nó cho phép nạp các tệp class từ nhiều nguồn khác nhau (hệ thống tệp cục bộ, mạng, tệp JAR) một cách năng động và thực thi các ranh giới bảo mật không gian tên (namespace).
- **Sự nhầm lẫn phổ biến**: Nghĩ rằng tất cả các lớp được nạp ngay khi JVM khởi động. Quá trình nạp lớp là lười biếng (lazy), chỉ xảy ra khi lớp đó lần đầu tiên được tham chiếu trong mã nguồn.
- **Ví dụ nhỏ**: Gọi `MyClass.class.getClassLoader()` trả về instance ClassLoader (ví dụ: `AppClassLoader`) chịu trách nhiệm nạp lớp cụ thể đó.

## Trình biên dịch JIT (JIT compiler)

Một thành phần cực kỳ quan trọng về hiệu năng của Động cơ thực thi JVM (JVM Execution Engine) giúp biên dịch các bytecode được thực thi thường xuyên (các điểm nóng - hot spots) thành mã máy bản địa (native machine code) tối ưu tại thời điểm chạy.

- **Tại sao nó quan trọng**: Nó thu hẹp khoảng cách giữa khởi động ứng dụng nhanh (thông qua thông dịch bytecode) và tốc độ thực thi đỉnh cao, tối ưu hóa mã nguồn ngay lập tức dựa trên dữ liệu phân tích thực tế (profiling data).
- **Sự nhầm lẫn phổ biến**: Tin rằng Java hoặc là chỉ thông dịch hoặc là chỉ biên dịch. Thực tế là cả hai; `javac` biên dịch mã nguồn thành bytecode, và trình biên dịch JIT biên dịch bytecode nóng thành mã máy tại thời điểm chạy.
- **Ví dụ nhỏ**: Chạy một vòng lặp 10.000 lần sẽ kích hoạt các trình biên dịch C1/C2 để biên dịch thân vòng lặp, giảm thời gian thực thi của nó từ mili giây xuống micro giây.

## Vùng Eden (Eden)

Vùng nhớ thuộc Thế hệ trẻ (Young Generation) của heap, nơi tất cả các đối tượng mới tạo được phân bổ ban đầu.

- **Tại sao nó quan trọng**: Nó cho phép phân bổ cực kỳ nhanh bằng cách sử dụng cơ chế tăng con trỏ tuần tự (pointer-bump), phù hợp với thực tế là hầu hết các đối tượng đều có vòng đời ngắn.
- **Sự nhầm lẫn phổ biến**: Tin rằng các đối tượng nằm trong Eden suốt toàn bộ vòng đời của chúng. Thực tế, Eden được dọn sạch hoàn toàn trong mỗi chu kỳ Minor GC, và các đối tượng sống sót sẽ được di tản sang các vùng Survivor.
- **Ví dụ nhỏ**: Thực hiện `new MyObject()` sẽ ngay lập tức yêu cầu một khối bộ nhớ trong vùng Eden.

## Các vùng Survivor (Survivor spaces)

Hai vùng nhớ giống hệt nhau (Survivor 0 / S0 và Survivor 1 / S1) trong Thế hệ trẻ (Young Generation) được sử dụng làm vùng chuyển tiếp tính tuổi cho các đối tượng sống sót qua các chu kỳ Minor GC.

- **Tại sao nó quan trọng**: Chúng ngăn chặn sự phân mảnh bộ nhớ heap (heap fragmentation) bằng cách sao chép và di tản (copy-evacuating) các đối tượng sống sót qua lại giữa hai vùng, cho phép JVM tăng tuổi của đối tượng trước khi đẩy chúng lên Thế hệ già (Old Generation).
- **Sự nhầm lẫn phổ biến**: Nghĩ rằng cả hai vùng Survivor được sử dụng đồng thời. Chỉ có một vùng Survivor (vùng "from") hoạt động tại bất kỳ thời điểm nào; vùng còn lại (vùng "to") phải trống để nhận dữ liệu sao chép trong chu kỳ Minor GC tiếp theo.
- **Ví dụ nhỏ**: Trong một chu kỳ Minor GC, các đối tượng sống sót trong Eden và S0 được sao chép sang S1, S0 được dọn sạch, và S1 trở thành vùng Survivor hoạt động.

## Shenandoah GC (Shenandoah GC)

Một bộ thu gom rác có thời gian tạm dừng thấp thực hiện việc nén bộ nhớ (compaction) đồng thời với các luồng ứng dụng Java đang chạy, giúp giữ thời gian tạm dừng ở mức tối thiểu bất kể kích thước heap lớn thế nào.

- **Tại sao nó quan trọng**: Nó ngăn chặn các lần tạm dừng Stop-The-World (STW) kéo dài trong các ứng dụng có heap lớn, giúp Java phù hợp cho các hệ thống thời gian thực, độ trễ thấp.
- **Sự nhầm lẫn phổ biến**: Giả định rằng Shenandoah loại bỏ hoàn toàn thời gian tạm dừng. Nó vẫn yêu cầu các lần tạm dừng rất ngắn để quét tập hợp gốc (thường dưới một mili giây).
- **Ví dụ nhỏ**: Kích hoạt Shenandoah thông qua cờ `-XX:+UseShenandoahGC` để giữ các lần tạm dừng GC dưới 10ms trên một heap 100GB.

## Brooks Pointer (Brooks Pointer)

Một trường tham chiếu bổ sung được thêm vào trước mỗi tiêu đề đối tượng (object header) trong các triển khai Shenandoah GC cũ hơn, trỏ đến chính đối tượng đó hoặc bản sao đã được chuyển tiếp của nó.

- **Tại sao nó quan trọng**: Nó cho phép nén bộ nhớ đồng thời bằng cách chuyển hướng các thao tác đọc và ghi đến đúng vị trí bộ nhớ của đối tượng trong khi GC đang di chuyển đối tượng đó.
- **Sự nhầm lẫn phổ biến**: Nghĩ rằng các luồng ứng dụng phải đợi GC hoàn thành việc di chuyển đối tượng. Với Brooks Pointer, các luồng ngay lập tức truy cập bản sao mới thông qua con trỏ được chuyển hướng.
- **Ví dụ nhỏ**: Một luồng ứng dụng cố gắng ghi vào `obj.field`; một rào cản tải (load barrier) kiểm tra Brooks Pointer, chuyển hướng đến bản sao trong vùng to-space, và thực hiện thao tác ghi.

## Các cờ JVM (JVM flags)

Các tùy chọn dòng lệnh được truyền cho trình chạy Java để cấu hình hành vi của JVM, tinh chỉnh phân bổ bộ nhớ, chọn thuật toán GC và kiểm soát hành vi của trình biên dịch.

- **Tại sao nó quan trọng**: Chúng cho phép người vận hành tối ưu hóa hiệu năng ứng dụng cho các môi trường phần cứng cụ thể mà không cần thay đổi bất kỳ mã nguồn nào.
- **Sự nhầm lẫn phổ biến**: Giả định rằng tất cả các cờ đều được hỗ trợ trên mọi phiên bản hoặc nhà cung cấp Java. Các cờ tiêu chuẩn thì ổn định, nhưng các tùy chọn `-XX` có thể bị xóa hoặc thay đổi hành vi giữa các bản cập nhật JDK.
- **Ví dụ nhỏ**: Thiết lập `-Xms512m -Xmx1024m -XX:+UseG1GC` cấu hình heap ban đầu, heap tối đa và loại bộ thu gom rác.
