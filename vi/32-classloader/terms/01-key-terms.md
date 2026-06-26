# Thuật ngữ ClassLoader (ClassLoader Terms)

Sử dụng file này khi một từ ngữ trong phần lý thuyết có cảm giác quá ngắn gọn. Mỗi thuật ngữ đều có ý nghĩa, tầm quan trọng, điểm dễ gây nhầm lẫn và một ví dụ nhỏ.

## Class loader

Một lớp trừu tượng (`java.lang.ClassLoader`) chịu trách nhiệm nạp các định nghĩa lớp (class definitions) từ các nguồn dữ liệu nhị phân (binary data sources) (như tệp hệ thống, tài nguyên mạng, hoặc cơ sở dữ liệu) vào bộ nhớ chạy của JVM, biến chúng thành các đối tượng Class.

- **Tại sao nó quan trọng:** Đây là cơ chế nạp mã bytecode một cách động vào thời gian chạy, cho phép thực hiện các tính năng như cô lập lớp (class isolation), các plugin, nạp nóng (hot-reloading), và các container máy chủ (server containers).
- **Nhầm lẫn phổ biến:** Biên dịch mã nguồn bằng `javac` chỉ xác thực tính an toàn kiểu dữ liệu và tạo ra mã bytecode `.class`; ClassLoader là một hệ thống chỉ chạy vào thời gian chạy (runtime-only system) khi các lớp được tham chiếu trong suốt quá trình chạy chương trình.
- **Ví dụ nhỏ:** `MyClass.class.getClassLoader()` trả về thể hiện ClassLoader đã nạp lớp `MyClass`.

## Bootstrap ClassLoader

Bộ nạp lớp cấp gốc (root-level classloader) của JVM, thông thường được viết bằng mã gốc C/C++, chịu trách nhiệm nạp các lớp chạy cơ bản của JDK (như `java.lang.Object`, `java.lang.String`) từ các hình ảnh chạy nền tảng (platform runtime images).

- **Tại sao nó quan trọng:** Nó khởi động máy ảo bằng cách nạp các gói cốt lõi mà tất cả các lớp Java khác phụ thuộc vào, hoạt động như một điểm tin cậy nền tảng cho phân cấp ủy quyền cha (parent delegation hierarchy).
- **Nhầm lẫn phổ biến:** Vì nó được viết bằng mã gốc (native code), nó không có biểu diễn đối tượng Java; gọi `String.class.getClassLoader()` sẽ trả về `null`.
- **Ví dụ nhỏ:** `Object.class.getClassLoader(); // trả về null`

## Platform ClassLoader

Bộ nạp lớp tiêu chuẩn (được gọi là Extension ClassLoader trong Java 8 trở về trước) nạp các API và mô-đun không cốt lõi dành riêng cho nền tảng, vốn là một phần của tiêu chuẩn Java nhưng không được nhúng trực tiếp trong mô-đun cơ sở cốt lõi.

- **Tại sao nó quan trọng:** Nó phân tách các thư viện đặc tả Java SE (như JDBC, xử lý XML) khỏi môi trường chạy tối thiểu, giúp bộ nạp Bootstrap nhỏ hơn và sạch hơn.
- **Nhầm lẫn phổ biến:** Trong Java 9 trở lên, cơ chế thư mục mở rộng (extension directory mechanism - `jre/lib/ext`) đã bị loại bỏ để chuyển sang các mô tả nền tảng mô-đun (modular platform descriptors), nhưng Platform ClassLoader vẫn được giữ lại như một thành phần chuyển tiếp.
- **Ví dụ nhỏ:** `java.sql.Connection.class.getClassLoader(); // trả về thể hiện Platform ClassLoader`

## Application ClassLoader

Còn được gọi là System ClassLoader, bộ nạp lớp Java này nạp các lớp ứng dụng do người dùng định nghĩa và các thư viện bên ngoài được chỉ định bởi đường dẫn lớp classpath (biến môi trường `-classpath`, `-cp`, hoặc `CLASSPATH`).

- **Tại sao nó quan trọng:** Đây là bộ nạp thực sự chạy mã nguồn tùy chỉnh của bạn, phương thức main của bạn, và các tệp JAR phụ thuộc của bên thứ ba.
- **Nhầm lẫn phổ biến:** Nó thường được coi là bộ nạp mặc định cho *mọi thứ*, nhưng thực tế nó sẽ ủy quyền cho bộ nạp Platform và Bootstrap đối với các lớp thư viện tiêu chuẩn.
- **Ví dụ nhỏ:** `ClassLoader.getSystemClassLoader(); // trả về Application ClassLoader`

## Parent delegation model

Một giao thức tìm kiếm trong đó một bộ nạp lớp, khi nhận được yêu cầu nạp một lớp, sẽ ủy quyền yêu cầu đó cho bộ nạp cha của nó trước khi tự mình tìm kiếm và nạp lớp.

- **Tại sao nó quan trọng:** Nó ngăn chặn hiện tượng che bóng lớp (class shadowing), đảm bảo rằng các API nền tảng cốt lõi không thể bị chiếm đoạt hoặc ghi đè bởi các lớp do ứng dụng cung cấp, đồng thời duy trì các không gian tên bảo mật của JVM.
- **Nhầm lẫn phổ biến:** Mối quan hệ bộ nạp cha được thiết lập bằng liên kết thành phần (composition - một trường parent bên trong thể hiện bộ nạp lớp), không phải bằng kế thừa (Inheritance) lớp Java.
- **Ví dụ nhỏ:** Khi nạp `java.lang.Object`, Application ClassLoader ủy quyền cho Platform ClassLoader, bộ nạp này tiếp tục ủy quyền cho Bootstrap ClassLoader để thực hiện nạp lớp.

## Thread Context ClassLoader (TCCL)

Bộ nạp lớp phụ thuộc vào ngữ cảnh được liên kết với Luồng (Thread) đang chạy hiện tại, có thể được lấy bằng cách sử dụng `Thread.currentThread().getContextClassLoader()`.

- **Tại sao nó quan trọng:** Nó cho phép các lớp hệ thống cốt lõi được nạp bởi Bootstrap ClassLoader bỏ qua mô hình ủy quyền cha nghiêm ngặt để nạp các lớp do ứng dụng người dùng cung cấp (rất quan trọng đối với các framework SPI như JDBC và JNDI).
- **Nhầm lẫn phổ biến:** TCCL không phải là một loại bộ nạp lớp mới; nó chỉ đơn giản là một tham chiếu trên một luồng trỏ đến một thực thể ClassLoader hiện có (thường là Application ClassLoader).
- **Ví dụ nhỏ:** `Thread.currentThread().setContextClassLoader(myCustomLoader);`

## Loading phase

Bước đầu tiên trong vòng đời nạp lớp, nơi JVM xác định vị trí bytecode nhị phân cho một lớp theo tên và tạo cấu trúc lớp thô trong Metaspace.

- **Tại sao nó quan trọng:** Nó quyết định cách các lớp được tìm kiếm, đọc và đăng ký dưới dạng siêu dữ liệu (metadata).
- **Nhầm lẫn phổ biến:** Việc nạp một lớp không tự động thực thi các khối tĩnh (static blocks) của nó hoặc phân giải các phụ thuộc của nó; những việc đó xảy ra trong các giai đoạn liên kết (linking) và khởi tạo (initialization) tiếp theo.
- **Ví dụ nhỏ:** `Class.forName("com.example.Demo", false, loader); // Nạp lớp mà không khởi tạo`

## Linking phase

Bước thứ hai trong vòng đời nạp lớp, bao gồm Xác thực (Verification - kiểm tra an toàn bytecode), Chuẩn bị (Preparation - phân bổ bộ nhớ trường tĩnh với các giá trị mặc định), và Phân giải (Resolution - ánh xạ các tham chiếu tượng trưng (symbolic references) thành các con trỏ bộ nhớ trực tiếp (direct memory pointers)).

- **Tại sao nó quan trọng:** Nó đảm bảo lớp an toàn để chạy và liên kết tất cả các tham chiếu thời gian chạy để mã bytecode có thể thực thi chính xác.
- **Nhầm lẫn phổ biến:** Trong bước Chuẩn bị, các biến tĩnh chỉ nhận các giá trị mặc định của JVM (như `0` hoặc `null`), không phải các giá trị khởi tạo do lập trình viên chỉ định (như `42`).
- **Ví dụ nhỏ:** `static int x = 42; // x nhận giá trị 0 trong bước chuẩn bị của giai đoạn liên kết`

## Initialization phase

Bước cuối cùng của vòng đời nạp lớp, nơi JVM chạy phương thức `<clinit>` đã biên dịch, thực thi các khối tĩnh (static blocks) và gán các giá trị khởi tạo do nhà phát triển chỉ định cho các biến tĩnh.

- **Tại sao nó quan trọng:** Nó chuẩn bị trạng thái cấp lớp sẵn sàng cho việc sử dụng, chạy logic khởi tạo một cách lười (lazily) khi lớp lần đầu tiên được tham chiếu tích cực.
- **Nhầm lẫn phổ biến:** Giai đoạn này chỉ được thực thi một lần duy nhất cho mỗi định nghĩa lớp trên mỗi bộ nạp lớp, dưới các khóa an toàn luồng nghiêm ngặt do JVM nắm giữ.
- **Ví dụ nhỏ:** Truy cập `MyClass.myStaticField` hoặc khởi tạo `new MyClass()` sẽ kích hoạt giai đoạn khởi tạo.

## Metaspace

Vùng bộ nhớ gốc (native memory) của JVM (được giới thiệu từ Java 8 để thay thế cho PermGen) nơi lưu trữ siêu dữ liệu lớp, các bộ nạp lớp, bytecode của phương thức, và hằng số vùng nhớ (constant pool).

- **Tại sao nó quan trọng:** Khác với PermGen, Metaspace không bị giới hạn ở một kích thước mặc định cố định mà tự động mở rộng theo bộ nhớ gốc của hệ điều hành, mặc dù nó vẫn có thể bị giới hạn bằng cách sử dụng `-XX:MaxMetaspaceSize`.
- **Nhầm lẫn phổ biến:** Nó không lưu trữ các đối tượng Java thực tế (vốn thuộc về Java Heap được gom rác) mà lưu trữ các cấu trúc lớp cần thiết để khởi tạo chúng.
- **Ví dụ nhỏ:** Việc nạp hàng triệu lớp một cách động có thể dẫn đến lỗi `java.lang.OutOfMemoryError: Metaspace`.

## Memory leak

Một lỗi phần mềm khi các đối tượng không còn cần thiết cho ứng dụng vẫn được tham chiếu trực tiếp hoặc gián tiếp từ các gốc GC (GC roots), ngăn cản bộ gom rác (garbage collector) thu hồi bộ nhớ của chúng.

- **Tại sao nó quan trọng:** Trong ngữ cảnh nạp lớp, nếu một tham chiếu ứng dụng giữ cho dù chỉ một thực thể của lớp tồn tại, nó sẽ giữ tham chiếu đến đối tượng Class của lớp đó, đối tượng Class này lại tham chiếu đến ClassLoader của nó, giữ cho *tất cả* các định nghĩa lớp đã nạp tiếp tục tồn tại trong Metaspace, gây ra một vụ rò rỉ bộ nhớ nghiêm trọng.
- **Nhầm lẫn phổ biến:** Việc đặt tham chiếu bộ nạp lớp tùy chỉnh thành `null` là không đủ để gom rác nó nếu các luồng, ThreadLocals, hoặc các đăng ký toàn hệ thống (như JDBC) vẫn giữ tham chiếu đến bất kỳ lớp nào mà nó đã nạp.
- **Ví dụ nhỏ:** Một ứng dụng web được gỡ bỏ (undeploy) nhưng để lại một giá trị thread-local chứa một thể hiện lớp của ứng dụng, ngăn không cho toàn bộ bộ nạp lớp của ứng dụng web đó được thu hồi.
