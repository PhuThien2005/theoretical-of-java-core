# Thuật ngữ Xây dựng, Biên dịch, Chạy (Build, Compile, Run Terms)

Sử dụng tài liệu này khi một từ ngữ trong lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều có ý nghĩa, tầm quan trọng, điểm dễ nhầm lẫn và một ví dụ nhỏ.

## javac

Công cụ dòng lệnh Trình biên dịch Java (Java Compiler) giúp dịch mã nguồn mà con người có thể đọc được (tệp `.java`) thành mã bytecode tương thích với JVM (tệp `.class`).

- **Tầm quan trọng**: Nó thực thi tính an toàn kiểu tĩnh (static type safety), kiểm tra cú pháp và biên dịch các phụ thuộc của lớp. Không có nó, mã nguồn Java không thể chạy được bởi JVM.
- **Nhầm lẫn thường gặp**: Nhầm lẫn giữa việc biên dịch tên lớp với biên dịch tệp. `javac` biên dịch *các tệp* (ví dụ: `javac Main.java`), chứ không phải *các lớp* (ví dụ: `javac Main` là một lỗi).
- **Ví dụ nhỏ**: `javac -d bin src/com/example/App.java` biên dịch tệp nguồn và đặt đầu ra trong `bin/com/example/App.class`.

## Lệnh java (java command)

Trình khởi chạy ứng dụng Java (Java Application Launcher) khởi chạy một Máy ảo Java (Java Virtual Machine - JVM), tải một lớp đã được biên dịch và chạy phương thức `public static void main(String[] args)` của lớp đó.

- **Tầm quan trọng**: Đây là điểm bắt đầu thực thi trong thời gian chạy (runtime execution entry point). Từ Java 11, nó cũng có thể biên dịch và chạy trực tiếp mã nguồn của một tệp duy nhất trong bộ nhớ (ví dụ: `java App.java`) mà không cần ghi tệp class ra đĩa.
- **Nhầm lẫn thường gặp**: Thêm phần mở rộng `.class` (ví dụ: `java App.class` sẽ thất bại). Trình khởi chạy yêu cầu một tên lớp đầy đủ (fully qualified class name) sử dụng ký hiệu dấu chấm (ví dụ: `java com.example.App`).
- **Ví dụ nhỏ**: `java -cp bin com.example.App arg1 arg2` khởi chạy JVM, tải lớp `com.example.App` từ thư mục `bin`.

## JAR

Kho lưu trữ Java (Java Archive - `.jar`) là định dạng đóng gói tệp dựa trên định dạng nén ZIP, được sử dụng để gói các lớp đã biên dịch, tài nguyên và siêu dữ liệu cấu hình (configuration metadata).

- **Tầm quan trọng**: Nó đơn giản hóa việc triển khai và phân phối bằng cách đóng gói hàng trăm tệp class và thư mục thành một tệp nén (artifact) duy nhất.
- **Nhầm lẫn thường gặp**: Cho rằng các tệp JAR là an toàn. Chúng là các kho lưu trữ ZIP tiêu chuẩn, có thể dễ dàng đổi tên thành `.zip`, giải nén hoặc dịch ngược (decompile) để lộ mã nguồn.
- **Ví dụ nhỏ**: `jar -tf app.jar` liệt kê tất cả các tệp và đường dẫn thư mục bên trong `app.jar`.

## classpath

Tham số đường dẫn tìm kiếm cho trình biên dịch (`javac`) và thời gian chạy JVM (`java`) biết nơi tìm các định nghĩa lớp do người dùng xác định và các tệp JAR được đóng gói.

- **Tầm quan trọng**: Nó cho phép tái sử dụng mã nguồn và tính mô-đun bằng cách trỏ JVM tới các thư viện của bên thứ ba. Nếu classpath bị sai, quá trình tải lớp (classloading) sẽ thất bại với lỗi `NoClassDefFoundError` hoặc ngoại lệ `ClassNotFoundException`.
- **Nhầm lẫn thường gặp**: Sử dụng sai ký tự phân tách của hệ điều hành. Windows sử dụng `;` trong khi Linux/macOS sử dụng `:`. Ngoài ra, việc chỉ định một classpath sẽ ghi đè lên đường dẫn tìm kiếm mặc định tại thư mục hiện tại (`.`), thư mục này phải được thêm lại thủ công nếu cần.
- **Ví dụ nhỏ**: `java -cp bin:lib/gson.jar com.example.App` (trên Unix) hoặc `java -cp bin;lib\gson.jar com.example.App` (trên Windows).

## manifest

Một tệp văn bản siêu dữ liệu đặc biệt (`META-INF/MANIFEST.MF`) được đặt bên trong tệp JAR để xác định các cấu hình như lớp điểm bắt đầu (`Main-Class`) và các thư viện phụ thuộc (`Class-Path`).

- **Tầm quan trọng**: Nó cho phép chạy ứng dụng bằng cách nhấp đúp hoặc chạy độc lập thông qua lệnh `java -jar app.jar` bằng cách khai báo lớp có thể thực thi.
- **Nhầm lẫn thường gặp**: Quên dòng trống ở cuối tệp. Trình phân tích cú pháp manifest sẽ bỏ qua dòng cuối cùng nếu không có dòng trống ở cuối, dẫn đến lỗi thiếu điểm bắt đầu (entry point).
- **Ví dụ nhỏ**:
  ```manifest
  Manifest-Version: 1.0
  Main-Class: com.example.App
  
  ```

## Maven

Một công cụ quản lý phụ thuộc và tự động hóa bản dựng theo kiểu khai báo (declarative build automation), sử dụng cấu hình `pom.xml` để quản lý các chu kỳ phát triển (project lifecycles) và thư viện của dự án.

- **Tầm quan trọng**: Nó tiêu chuẩn hóa cấu trúc dự án và tự động quản lý các phụ thuộc bắc cầu (transitive dependencies) từ kho lưu trữ trung tâm Maven Central.
- **Nhầm lẫn thường gặp**: Hiểu sai về việc thực thi các giai đoạn (phase). Chạy lệnh `mvn package` sẽ tự động thực thi tất cả các giai đoạn trước đó (`validate`, `compile`, `test`).
- **Ví dụ nhỏ**: Chạy `mvn clean package` để xóa các bản dựng cũ, biên dịch mã mới, chạy kiểm thử và xuất ra tệp JAR.

## Gradle

Một công cụ tự động hóa bản dựng linh hoạt, theo kiểu lập trình (programmatic build automation), sử dụng các tập lệnh DSL Groovy hoặc Kotlin (`build.gradle` hoặc `build.gradle.kts`) để điều phối các tác vụ xây dựng (build tasks).

- **Tầm quan trọng**: Nó cung cấp khả năng xây dựng tăng dần (incremental builds) nhanh hơn (thông qua một tiến trình nền daemon) và hỗ trợ logic xây dựng có thể tùy biến cao so với Maven dựa trên cấu trúc XML.
- **Nhầm lẫn thường gặp**: Quên khai báo plugin Java (`plugins { id 'java' }`), điều này khiến Gradle không thể nhận diện các lệnh biên dịch và kiểm thử.
- **Ví dụ nhỏ**: Chạy `./gradlew build` bằng cách sử dụng Gradle wrapper.

## Phụ thuộc (dependency)

Một thư viện phần mềm bên ngoài (thường là tệp JAR) mà một dự án Java dựa vào để biên dịch hoặc chạy.

- **Tầm quan trọng**: Giúp tránh việc phải viết lại các tiện ích thông dụng (như trình phân tích cú pháp JSON hoặc trình điều khiển cơ sở dữ liệu) bằng cách kéo các gói đã được xác thực vào dự án.
- **Nhầm lẫn thường gặp**: Không tương thích phiên bản "Jar Hell", nơi các phụ thuộc bắc cầu xung đột lẫn nhau. Những xung đột này phải được giải quyết thông qua việc loại trừ phụ thuộc (dependency exclusions).
- **Ví dụ nhỏ**: Khai báo JUnit Jupiter bên trong một khối `dependencies` của Gradle với `testImplementation`.
