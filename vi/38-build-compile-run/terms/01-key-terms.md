# Thuật Ngữ Xây Dựng, Biên Dịch, Chạy Chương Trình (Build, Compile, Run Terms)

Sử dụng tài liệu này khi một từ khóa trong lý thuyết có vẻ quá ngắn gọn. Mỗi thuật ngữ đều có định nghĩa, tầm quan trọng, điểm dễ gây nhầm lẫn và một ví dụ nhỏ đi kèm.

## javac

Công cụ dòng lệnh Trình biên dịch Java (Java Compiler) dùng để dịch mã nguồn dễ đọc của con người (các tệp `.java`) thành mã byte tương thích với JVM (các tệp `.class`).

- **Tầm quan trọng**: Nó thực thi tính an toàn kiểu tĩnh (static type safety), kiểm tra cú pháp và biên dịch các lớp phụ thuộc. Không có nó, mã nguồn Java không thể được thực thi bởi JVM.
- **Nhầm lẫn phổ biến**: Nhầm lẫn giữa việc biên dịch tên lớp và biên dịch tên tệp. Lệnh `javac` thực hiện biên dịch trên **tệp** (ví dụ: `javac Main.java` là đúng), chứ không phải biên dịch trên **lớp** (ví dụ: chạy `javac Main` sẽ báo lỗi).
- **Ví dụ nhỏ**: Lệnh `javac -d bin src/com/example/App.java` biên dịch tệp mã nguồn và lưu kết quả mã byte trong `bin/com/example/App.class`.

## lệnh java (java command)

Trình khởi chạy ứng dụng Java (Java Application Launcher) khởi động một Máy ảo Java (JVM), nạp một lớp đã biên dịch và thực thi phương thức điểm vào `public static void main(String[] args)` của nó.

- **Tầm quan trọng**: Đây là điểm vào để thực thi ứng dụng khi chạy (runtime). Kể từ Java 11, nó cũng có thể biên dịch và chạy các tệp mã nguồn đơn lẻ trực tiếp trên bộ nhớ (ví dụ: `java App.java`) mà không cần ghi tệp class ra ổ đĩa.
- **Nhầm lẫn phổ biến**: Thêm phần mở rộng `.class` khi chạy lệnh (ví dụ: chạy `java App.class` sẽ thất bại). Trình khởi chạy yêu cầu truyền vào tên lớp đầy đủ (FQCN) sử dụng dấu chấm phân tách (ví dụ: `java com.example.App`).
- **Ví dụ nhỏ**: Lệnh `java -cp bin com.example.App arg1 arg2` khởi chạy JVM, nạp lớp `com.example.App` từ thư mục `bin`.

## JAR

Tệp lưu trữ Java (Java Archive - `.jar`) là một định dạng đóng gói tệp dựa trên định dạng nén ZIP, được sử dụng để đóng gói các lớp đã biên dịch, các tài nguyên và siêu dữ liệu cấu hình.

- **Tầm quan trọng**: Nó đơn giản hóa việc triển khai và phân phối ứng dụng bằng cách đóng gói hàng trăm tệp lớp và thư mục thành một sản phẩm nén duy nhất.
- **Nhầm lẫn phổ biến**: Nghĩ rằng tệp JAR có tính bảo mật cao. Bản chất chúng chỉ là các tệp nén ZIP thông thường, có thể dễ dàng đổi tên thành `.zip`, giải nén hoặc dịch ngược (decompile) để xem lại mã nguồn.
- **Ví dụ nhỏ**: Lệnh `jar -tf app.jar` liệt kê danh sách toàn bộ các tệp và đường dẫn thư mục bên trong `app.jar`.

## classpath (Đường dẫn lớp)

Tham số đường dẫn tra cứu thông báo cho trình biên dịch (`javac`) và JVM (`java`) biết nơi tìm kiếm các định nghĩa lớp của người dùng và các tệp JAR được đóng gói.

- **Tầm quan trọng**: Nó cho phép tái sử dụng mã nguồn và tính module hóa bằng cách chỉ JVM tới các thư viện bên thứ ba. Nếu classpath bị sai, quá trình nạp lớp sẽ thất bại và ném ra lỗi `NoClassDefFoundError` hoặc ngoại lệ `ClassNotFoundException`.
- **Nhầm lẫn phổ biến**: Sử dụng sai ký tự phân tách hệ điều hành. Hệ điều hành Windows sử dụng dấu chấm phẩy `;` trong khi Linux/macOS sử dụng dấu hai chấm `:`. Ngoài ra, việc chỉ định classpath cụ thể sẽ ghi đè lên đường dẫn tra cứu thư mục hiện tại mặc định (`.`), bạn phải tự thêm lại thủ công nếu cần.
- **Ví dụ nhỏ**: `java -cp bin:lib/gson.jar com.example.App` (trên hệ điều hành Unix) hoặc `java -cp bin;lib\gson.jar com.example.App` (trên hệ điều hành Windows).

## manifest (Tệp kê khai)

Một tệp văn bản siêu dữ liệu đặc biệt (`META-INF/MANIFEST.MF`) được đặt bên trong tệp JAR để xác định các cấu hình như lớp điểm vào (`Main-Class`) và các thư viện phụ thuộc (`Class-Path`).

- **Tầm quan trọng**: Nó cho phép chạy tệp JAR bằng cách click đúp hoặc chạy trực tiếp bằng lệnh `java -jar app.jar` nhờ việc khai báo rõ lớp thực thi.
- **Nhầm lẫn phổ biến**: Quên thêm ký tự xuống dòng ở cuối tệp. Bộ phân tích cú pháp manifest sẽ bỏ qua dòng cuối cùng nếu không có một dòng trống ở cuối tệp, dẫn đến các lỗi không tìm thấy lớp điểm vào.
- **Ví dụ nhỏ**:
  ```manifest
  Manifest-Version: 1.0
  Main-Class: com.example.App
  
  ```

## Maven

Một công cụ quản lý thư viện phụ thuộc và tự động hóa xây dựng dự án dạng khai báo, sử dụng cấu hình tệp `pom.xml` để quản lý các vòng đời dự án và các thư viện.

- **Tầm quan trọng**: Nó chuẩn hóa cấu trúc thư mục dự án và tự động quản lý các thư viện phụ thuộc bắc cầu (transitive dependencies) từ kho lưu trữ trung tâm Maven Central.
- **Nhầm lẫn phổ biến**: Hiểu sai về việc thực thi các giai đoạn (phases). Việc chạy lệnh `mvn package` sẽ tự động thực thi toàn bộ các giai đoạn trước đó (`validate`, `compile`, `test`).
- **Ví dụ nhỏ**: Chạy lệnh `mvn clean package` để xóa các bản build cũ, biên dịch mã nguồn mới, chạy các bài kiểm thử và đóng gói thành tệp JAR.

## Gradle

Một công cụ tự động hóa xây dựng dự án linh hoạt dạng lập trình, sử dụng các kịch bản Groovy hoặc Kotlin DSL (`build.gradle` hoặc `build.gradle.kts`) để điều phối các tác vụ xây dựng.

- **Tầm quan trọng**: Nó cung cấp khả năng xây dựng gia tăng (incremental builds) nhanh hơn (thông qua một tiến trình nền daemon) và hỗ trợ logic xây dựng tùy biến cao so với cấu hình XML cứng nhắc của Maven.
- **Nhầm lẫn phổ biến**: Quên áp dụng plugin Java (`plugins { id 'java' }`), khiến Gradle không thể nhận diện được các lệnh biên dịch và lệnh kiểm thử.
- **Ví dụ nhỏ**: Chạy lệnh `./gradlew build` thông qua bộ bọc Gradle wrapper.

## dependency (Thư viện phụ thuộc)

Một thư viện phần mềm bên ngoài (thường là một tệp JAR) mà một dự án Java cần dựa vào để biên dịch hoặc chạy chương trình.

- **Tầm quan trọng**: Tránh việc phải tự viết lại các công cụ phổ biến (như bộ phân tích cú pháp JSON hoặc trình điều khiển cơ sở dữ liệu) bằng cách kéo các gói thư viện đã được xác minh vào dự án.
- **Nhầm lẫn phổ biến**: Hiện tượng xung đột phiên bản thư viện JAR ("Jar Hell"), xảy ra khi các thư viện phụ thuộc bắc cầu bị xung đột lẫn nhau. Lỗi này bắt buộc phải giải quyết bằng cách loại bỏ loại trừ (exclusions) thư viện xung đột.
- **Ví dụ nhỏ**: Khai báo thư viện JUnit Jupiter bên trong khối `dependencies` của Gradle với từ khóa `testImplementation`.
