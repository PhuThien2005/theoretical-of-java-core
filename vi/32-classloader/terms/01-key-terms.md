# Các Thuật Ngữ Của ClassLoader

Hãy sử dụng tài liệu này khi một từ trong phần lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều có phần ý nghĩa, tầm quan trọng, hiểu lầm phổ biến và một ví dụ nhỏ.

## Bộ tải lớp (Class loader)

Một lớp trừu tượng (`java.lang.ClassLoader`) chịu trách nhiệm tải các định nghĩa lớp từ các nguồn dữ liệu nhị phân (như tệp hệ thống, tài nguyên mạng hoặc cơ sở dữ liệu) vào bộ nhớ chạy (runtime memory) của JVM, chuyển đổi chúng thành các đối tượng Class.

- **Tầm quan trọng:** Đây là cơ chế tải mã byte (bytecode) một cách động tại thời điểm chạy, kích hoạt các tính năng như cô lập lớp (class isolation), plugin, tải lại nóng (hot-reloading) và các container máy chủ.
- **Hiểu lầm phổ biến:** Biên dịch mã nguồn bằng `javac` chỉ xác minh tính an toàn kiểu dữ liệu và tạo ra mã byte `.class`; ClassLoader là hệ thống chỉ hoạt động tại thời điểm chạy (runtime-only) được thực thi khi các lớp được tham chiếu trong quá trình chạy.
- **Ví dụ nhỏ:** `MyClass.class.getClassLoader()` trả về thể hiện ClassLoader đã tải lớp `MyClass`.

## Bootstrap ClassLoader

Bộ tải lớp cấp gốc (root-level classloader) của JVM, thường được viết bằng mã nguồn C/C++ gốc, chịu trách nhiệm tải các lớp thời gian chạy JDK thiết yếu (như `java.lang.Object`, `java.lang.String`) từ các hình ảnh chạy của nền tảng (platform runtime image).

- **Tầm quan trọng:** Nó khởi động (bootstrap) máy ảo bằng cách tải các gói cốt lõi mà tất cả các lớp Java khác phụ thuộc vào, đóng vai trò là mốc tin cậy nền tảng cho hệ thống phân cấp ủy quyền cha.
- **Hiểu lầm phổ biến:** Vì nó được viết bằng mã gốc, nó không có đại diện đối tượng Java; gọi `String.class.getClassLoader()` sẽ trả về `null`.
- **Ví dụ nhỏ:** `Object.class.getClassLoader(); // trả về null`

## Platform ClassLoader

Bộ tải lớp tiêu chuẩn (được gọi là Extension ClassLoader trong Java 8 trở về trước) tải các API và mô-đun phi cốt lõi đặc thù của nền tảng là một phần của tiêu chuẩn Java nhưng không được nhúng trực tiếp trong mô-đun cơ sở cốt lõi.

- **Tầm quan trọng:** Nó tách các thư viện đặc tả Java SE (như JDBC, xử lý XML) ra khỏi thời gian chạy tối thiểu tuyệt đối, giúp bộ tải Bootstrap nhỏ hơn và sạch hơn.
- **Hiểu lầm phổ biến:** Trong Java 9 trở đi, cơ chế thư mục mở rộng (`jre/lib/ext`) đã bị loại bỏ để ủng hộ các mô tả nền tảng mô-đun (modular platform descriptor), nhưng Platform ClassLoader vẫn tồn tại như một thành phần chuyển tiếp.
- **Ví dụ nhỏ:** `java.sql.Connection.class.getClassLoader(); // trả về thể hiện Platform ClassLoader`

## Application ClassLoader

Còn được gọi là System ClassLoader, bộ tải lớp Java này tải các lớp ứng dụng do người dùng định nghĩa và các thư viện bên ngoài được chỉ định bởi đường dẫn lớp (classpath - qua `-classpath`, `-cp`, hoặc biến môi trường `CLASSPATH`).

- **Tầm quan trọng:** Đây là bộ tải thực sự chạy mã nguồn tùy chỉnh của bạn, phương thức main của bạn và các tệp JAR phụ thuộc của bên thứ ba.
- **Hiểu lầm phổ biến:** Nó thường bị coi là bộ tải mặc định cho *mọi thứ*, nhưng nó sẽ ủy quyền cho các bộ tải Platform và Bootstrap đối với các lớp thư viện tiêu chuẩn.
- **Ví dụ nhỏ:** `ClassLoader.getSystemClassLoader(); // trả về Application ClassLoader`

## Mô hình ủy quyền cha (Parent delegation model)

Một văn bản tìm kiếm trong đó một bộ tải lớp, khi nhận được yêu cầu tải một lớp, sẽ ủy quyền yêu cầu đó cho bộ tải cha của nó trước khi tự mình tìm kiếm và tải lớp đó.

- **Tầm quan trọng:** Nó ngăn chặn việc che khuất lớp (class shadowing), đảm bảo rằng các API nền tảng cốt lõi không thể bị chiếm đoạt hoặc ghi đè bởi các lớp do ứng dụng cung cấp, và duy trì không gian tên bảo mật của JVM.
- **Hiểu lầm phổ biến:** Mối quan hệ bộ tải cha được thiết lập bằng liên kết cấu thành (composition - một trường parent bên trong thể hiện bộ tải lớp), chứ không phải bằng kế thừa lớp trong Java.
- **Ví dụ nhỏ:** Khi tải `java.lang.Object`, Application ClassLoader ủy quyền cho Platform ClassLoader, bộ tải này ủy quyền cho Bootstrap ClassLoader để thực hiện việc tải.

## Thread Context ClassLoader (TCCL)

Một bộ tải lớp phụ thuộc vào ngữ cảnh được liên kết với Luồng (Thread) hiện đang chạy, có thể được lấy ra bằng cách sử dụng `Thread.currentThread().getContextClassLoader()`.

- **Tầm quan trọng:** Nó cho phép các lớp hệ thống cốt lõi được tải bởi Bootstrap ClassLoader vượt qua mô hình ủy quyền cha nghiêm ngặt để tải các lớp do ứng dụng người dùng cung cấp (rất quan trọng đối với các khung công tác SPI như JDBC và JNDI).
- **Hiểu lầm phổ biến:** TCCL không phải là một kiểu bộ tải lớp mới; nó chỉ đơn giản là một tham chiếu liên kết trên một luồng trỏ đến một thể hiện ClassLoader hiện có (thường là Application ClassLoader).
- **Ví dụ nhỏ:** `Thread.currentThread().setContextClassLoader(myCustomLoader);`

## Giai đoạn Tải (Loading phase)

Bước đầu tiên trong vòng đời tải lớp, nơi JVM xác định vị trí mã byte nhị phân của một lớp theo tên và tạo cấu trúc lớp thô trong Metaspace.

- **Tầm quan trọng:** Nó xác định cách thức các lớp được tìm kiếm, đọc và đăng ký dưới dạng siêu dữ liệu (metadata).
- **Hiểu lầm phổ biến:** Tải một lớp không tự động thực thi các khối tĩnh (static block) của nó hoặc phân giải các phụ thuộc; những việc đó diễn ra ở các giai đoạn liên kết và khởi tạo tiếp theo.
- **Ví dụ nhỏ:** `Class.forName("com.example.Demo", false, loader); // Tải mà không khởi tạo`

## Giai đoạn Liên kết (Linking phase)

Bước thứ hai trong vòng đời tải lớp, bao gồm Xác minh - Verification (kiểm tra an toàn mã byte), Chuẩn bị - Preparation (phân bổ bộ nhớ trường tĩnh với các giá trị mặc định) và Phân giải - Resolution (ánh xạ các tham chiếu tượng trưng thành các con trỏ bộ nhớ trực tiếp).

- **Tầm quan trọng:** Nó đảm bảo lớp đó là an toàn để chạy và kết nối tất cả các tham chiếu tại thời điểm chạy để mã byte có thể thực thi bình thường.
- **Hiểu lầm phổ biến:** Trong bước Chuẩn bị (Preparation), các biến tĩnh chỉ nhận các giá trị mặc định của JVM (như `0` hoặc `null`), chứ không nhận các giá trị khởi tạo do lập trình viên chỉ định (như `42`).
- **Ví dụ nhỏ:** `static int x = 42; // x nhận giá trị 0 trong bước chuẩn bị của giai đoạn liên kết`

## Giai đoạn Khởi tạo (Initialization phase)

Bước cuối cùng của vòng đời tải lớp nơi JVM chạy phương thức `<clinit>` đã biên dịch, thực thi các khối tĩnh và gán các giá trị khởi tạo do lập trình viên chỉ định cho các biến tĩnh.

- **Tầm quan trọng:** Nó chuẩn bị trạng thái cấp lớp để sử dụng, chạy logic khởi tạo một cách lười biếng (lazy) khi lớp được tham chiếu chủ động lần đầu tiên.
- **Hiểu lầm phổ biến:** Giai đoạn này chỉ được thực thi một lần duy nhất cho mỗi định nghĩa lớp trên mỗi bộ tải lớp, dưới các khóa an toàn luồng nghiêm ngặt do JVM nắm giữ.
- **Ví dụ nhỏ:** Truy cập `MyClass.myStaticField` hoặc khởi tạo `new MyClass()` kích hoạt giai đoạn khởi tạo.

## Metaspace

Vùng nhớ gốc (native memory) của JVM (được giới thiệu trong Java 8 để thay thế PermGen) nơi lưu trữ siêu dữ liệu của lớp, các bộ tải lớp, mã byte của phương thức và bể hằng số (constant pool).

- **Tầm quan trọng:** Không giống như PermGen, Metaspace không bị giới hạn ở một kích thước mặc định cố định mà tự động mở rộng theo bộ nhớ gốc của hệ điều hành, mặc dù nó vẫn có thể bị giới hạn bằng tùy chọn `-XX:MaxMetaspaceSize`.
- **Hiểu lầm phổ biến:** Nó không lưu trữ các đối tượng Java thực tế (vốn nằm trên vùng Heap của Java được dọn rác), mà lưu trữ các cấu trúc lớp cần thiết để khởi tạo chúng.
- **Ví dụ nhỏ:** Tải hàng triệu lớp một cách động có thể dẫn đến `java.lang.OutOfMemoryError: Metaspace`.

## Rò rỉ bộ nhớ (Memory leak)

Một lỗi phần mềm trong đó các đối tượng không còn cần thiết cho ứng dụng nhưng vẫn được tham chiếu trực tiếp hoặc gián tiếp từ các gốc GC (GC root), ngăn cản bộ dọn rác giải phóng bộ nhớ của chúng.

- **Tầm quan trọng:** Trong ngữ cảnh tải lớp, nếu một tham chiếu ứng dụng giữ cho dù chỉ một thể hiện lớp duy nhất còn sống, nó sẽ giữ một tham chiếu đến đối tượng Class của nó, đối tượng này lại tham chiếu đến ClassLoader của nó, bộ tải này sẽ giữ cho *tất cả* các định nghĩa lớp đã tải còn sống trong Metaspace, gây ra rò rỉ bộ nhớ nghiêm trọng.
- **Hiểu lầm phổ biến:** Chỉ đặt tham chiếu bộ tải lớp tùy chỉnh thành `null` là không đủ để dọn rác nếu các luồng, ThreadLocal hoặc các đăng ký toàn hệ thống (như JDBC) vẫn giữ tham chiếu đến bất kỳ lớp nào mà nó đã tải.
- **Ví dụ nhỏ:** Một ứng dụng web dừng hoạt động (undeploy) nhưng để lại một giá trị thread-local chứa một thể hiện lớp của ứng dụng, ngăn chặn toàn bộ bộ tải lớp của ứng dụng web được thu hồi.
