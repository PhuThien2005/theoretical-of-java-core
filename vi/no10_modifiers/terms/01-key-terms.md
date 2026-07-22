# Thuật Ngữ Về Các Công Cụ Sửa Đổi Trong Java (Modifiers in Java Terms)

Hãy sử dụng tài liệu này khi một từ khóa trong lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều có định nghĩa, tầm quan trọng, các điểm dễ gây nhầm lẫn và một ví dụ nhỏ đi kèm.

## Công Cụ Sửa Đổi Quyền Truy Cập (Access Modifier)

Các từ khóa (`public`, `protected`, `private`, hoặc mặc định/package-private) khai báo mức độ hiển thị (visibility level) và ranh giới khả năng truy cập của các lớp, phương thức, hàm khởi tạo và trường dữ liệu.

- **Tầm quan trọng**: Đây là nền tảng của tính đóng gói (encapsulation), giúp bảo vệ trạng thái nội bộ của đối tượng khỏi các tác động sai lệch từ bên ngoài và giới hạn các API công khai ở những điểm truy cập ổn định, được định nghĩa rõ ràng.
- **Điểm dễ nhầm lẫn**: Cho rằng từ khóa `protected` cho phép bất kỳ lớp nào ở gói (package) khác cũng có thể truy cập thành viên đó. Các lớp con ở gói khác chỉ có thể truy cập thành viên đó thông qua kế thừa, chứ không thể truy cập qua một tham chiếu thực thể của lớp cha.
- **Ví dụ nhỏ**: `private double balance;` đảm bảo rằng số dư tài khoản không thể bị sửa đổi trực tiếp bởi các lớp khách, bắt buộc phải sử dụng các phương thức `deposit()` hoặc `withdraw()`.

## Công Cụ Sửa Đổi Không Quyền Truy Cập (Non-Access Modifier)

Các từ khóa (`static`, `final`, `abstract`, `synchronized`, `volatile`, `transient`, `native`, `strictfp`) định nghĩa các đặc tính hành vi và quy tắc thực thi cho các lớp, phương thức và biến chứ không liên quan đến mức độ hiển thị quyền truy cập.

- **Tầm quan trọng**: Chúng kiểm soát cách bố trí bộ nhớ (cấp lớp so với cấp thực thể), các giới hạn tối ưu hóa của JVM, đồng bộ hóa an toàn đa luồng (thread safety), ranh giới hiển thị của bộ nhớ đệm (cache) và loại trừ tuần tự hóa (serialization).
- **Điểm dễ nhầm lẫn**: Nhầm lẫn chúng với các công cụ sửa đổi quyền truy cập. Các từ khóa không quyền truy cập này có thể được sử dụng kết hợp với các công cụ sửa đổi quyền truy cập, chẳng hạn như `public static final`.
- **Ví dụ nhỏ**: `public synchronized void add()` sử dụng từ khóa không quyền truy cập `synchronized` để đảm bảo an toàn đa luồng trong khi vẫn giữ cho phương thức có thể truy cập công khai.

## Từ Khóa tĩnh (static)

Một công cụ sửa đổi biểu thị rằng một biến, phương thức, khối lệnh hoặc lớp lồng nhau thuộc về chính bản thiết kế lớp (class blueprint) chứ không thuộc về bất kỳ thực thể (instance) cụ thể nào của lớp đó.

- **Tầm quan trọng**: Nó cho phép chia sẻ trạng thái chung trên toàn bộ lớp và truy cập vào các phương thức tiện ích mà không tốn chi phí tạo các đối tượng thực thể, với siêu dữ liệu được cấp phát trong vùng nhớ Metaspace.
- **Điểm dễ nhầm lẫn**: Cố gắng truy cập các trường thực thể hoặc sử dụng các từ khóa `this` hoặc `super` bên trong các phương thức tĩnh. Bởi vì các phương thức tĩnh thực thi mà không có đối tượng nhận cụ thể nào, trình biên dịch sẽ từ chối các tham chiếu này.
- **Ví dụ nhỏ**: Lời gọi `Math.pow(2, 3)` được gọi trực tiếp trên bản thiết kế lớp `Math` chứ không phải trên một thực thể `new Math()`.

## Từ Khóa final (final)

Một công cụ sửa đổi giúp ngăn chặn giá trị hoặc tham chiếu của một biến bị gán lại, ngăn một phương thức bị ghi đè trong các lớp con, hoặc ngăn một lớp bị kế thừa.

- **Tầm quan trọng**: Nó cung cấp tính năng an toàn chỉ đọc được thực thi bởi trình biên dịch, bảo vệ các phương thức ghi đè quan trọng khỏi sự can thiệp của lớp con và cho phép các tối ưu hóa hiệu suất như nội tuyến hằng số của trình biên dịch JIT.
- **Điểm dễ nhầm lẫn**: Tin rằng một tham chiếu đối tượng `final` sẽ làm cho bản thân đối tượng được tham chiếu đó trở nên bất biến. Tham chiếu đó không thể trỏ tới một đối tượng mới, nhưng các trường nội bộ của đối tượng đó vẫn có thể thay đổi được.
- **Ví dụ nhỏ**: `final List<String> items = new ArrayList<>(); items.add("Apple"); // Hợp lệ; items = new ArrayList<>(); // Lỗi biên dịch`

## Từ Khóa trừu tượng (abstract)

Một công cụ sửa đổi khai báo rằng một lớp không thể được khởi tạo trực tiếp mà bắt buộc phải được kế thừa, hoặc một phương thức không chứa phần thân triển khai và bắt buộc phải được ghi đè ở lớp con.

- **Tầm quan trọng**: Nó hoạt động như một hợp đồng cho tính đa hình, bắt buộc các lớp con cụ thể phải triển khai các chữ ký phương thức còn thiếu và định nghĩa các hành vi cụ thể tương ứng.
- **Điểm dễ nhầm lẫn**: Khai báo một phương thức abstract bên trong một lớp không abstract (lớp cụ thể). Nếu một lớp chứa dù chỉ một phương thức abstract, bản thân lớp đó cũng bắt buộc phải được khai báo là abstract.
- **Ví dụ nhỏ**: `public abstract class Animal { public abstract void makeSound(); }`

## Từ Khóa volatile (volatile)

Một công cụ sửa đổi áp dụng cho các biến giúp đảm bảo khả năng hiển thị giữa các luồng (thread visibility) và ngăn chặn việc tái sắp xếp lệnh (instruction reordering) bằng cách bắt buộc tất cả các thao tác đọc và ghi phải thực hiện trực tiếp trên bộ nhớ chính, bỏ qua các thanh ghi CPU hoặc bộ nhớ đệm CPU.

- **Tầm quan trọng**: Nó đảm bảo rằng các cập nhật được thực hiện trên một biến bởi một luồng sẽ lập tức hiển thị đối với tất cả các luồng khác, ngăn chặn việc đọc dữ liệu bộ nhớ đệm cũ trong các vòng lặp đa luồng.
- **Điểm dễ nhầm lẫn**: Cho rằng `volatile` đảm bảo an toàn đa luồng hoặc tính nguyên tử (atomicity) cho các hoạt động phức hợp như `count++`. Nó không thực hiện đồng bộ hóa khóa (lock synchronization), vì vậy các tranh chấp tài nguyên (race condition) vẫn có thể xảy ra.
- **Ví dụ nhỏ**: `private volatile boolean stopRequest = false;` đảm bảo luồng thực thi lập tức thoát khỏi vòng lặp của nó ngay sau khi luồng điều khiển đặt cờ này thành true.

## Từ Khóa transient (transient)

Một công cụ sửa đổi giúp ngăn một biến thực thể bị tuần tự hóa khi đối tượng chứa nó được chuyển đổi thành một luồng dữ liệu byte (byte stream).

- **Tầm quan trọng**: Nó bảo vệ các thông tin nhạy cảm (ví dụ: mật khẩu, khóa bảo mật) không bị lưu xuống đĩa hoặc truyền qua mạng, và bỏ qua các tài nguyên không thể tuần tự hóa như kết nối cơ sở dữ liệu hoặc luồng tệp tin.
- **Điểm dễ nhầm lẫn**: Đánh dấu một biến `static` là `transient` để ngăn nó bị tuần tự hóa. Các trường tĩnh thuộc về siêu dữ liệu lớp chứ không thuộc về thực thể đối tượng, và chúng đã được mặc định bỏ qua trong quá trình tuần tự hóa đối tượng.
- **Ví dụ nhỏ**: `private transient String databasePassword;` đảm bảo rằng thông tin đăng nhập cơ sở dữ liệu sẽ bị loại trừ khi lưu trữ đối tượng cấu hình hệ thống.
