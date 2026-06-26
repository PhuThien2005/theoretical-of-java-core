# Thuật ngữ về Trình sửa đổi trong Java (Modifiers in Java Terms)

Sử dụng tài liệu này khi một từ ngữ trong lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều đi kèm ý nghĩa, tầm quan trọng, hiểu lầm phổ biến và một ví dụ nhỏ.

## Trình sửa đổi truy cập (access modifier)

Các từ khóa (`public`, `protected`, `private`, hoặc default/package-private) dùng để khai báo mức độ hiển thị và ranh giới khả năng truy cập của các lớp, phương thức, hàm khởi dựng và trường dữ liệu.

- **Tầm quan trọng**: Đây là nền tảng của tính đóng gói (encapsulation), giúp bảo vệ trạng thái bên trong của đối tượng khỏi sự can thiệp làm sai lệch từ bên ngoài và giới hạn các API công khai ở các điểm truy cập ổn định, được định nghĩa rõ ràng.
- **Hiểu lầm phổ biến**: Giả định rằng `protected` cho phép bất kỳ lớp nào ở package khác truy cập vào thành phần đó. Các lớp con ở package khác chỉ có thể truy cập thành phần đó thông qua kế thừa (Inheritance), chứ không thể truy cập qua một tham chiếu thực thể của lớp cha.
- **Ví dụ nhỏ**: `private double balance;` đảm bảo rằng balance không thể bị thay đổi trực tiếp bởi các lớp khách hàng, yêu cầu phải sử dụng `deposit()` hoặc `withdraw()`.

## Trình sửa đổi phi truy cập (non-access modifier)

Các từ khóa (`static`, `final`, `abstract`, `synchronized`, `volatile`, `transient`, `native`, `strictfp`) định nghĩa các đặc tính hành vi và quy tắc thực thi cho các lớp, phương thức và biến, thay vì kiểm soát mức độ hiển thị truy cập.

- **Tầm quan trọng**: Chúng kiểm soát cách bố trí bộ nhớ (cấp lớp so với cấp thực thể), các giới hạn tối ưu hóa của JVM, đồng bộ hóa an toàn luồng (thread safety), ranh giới hiển thị của bộ nhớ đệm (cache visibility) và loại trừ khi tuần tự hóa (serialization).
- **Hiểu lầm phổ biến**: Nhầm lẫn chúng với các trình sửa đổi truy cập. Các trình sửa đổi phi truy cập có thể được sử dụng kết hợp với các trình sửa đổi truy cập, chẳng hạn như `public static final`.
- **Ví dụ nhỏ**: `public synchronized void add()` sử dụng trình sửa đổi phi truy cập `synchronized` để đảm bảo an toàn luồng trong khi vẫn giữ phương thức này có thể truy cập công khai.

## static

Một trình sửa đổi chỉ ra rằng một biến, phương thức, khối lệnh hoặc lớp lồng nhau thuộc về chính bản thiết kế lớp chứ không thuộc về bất kỳ thực thể cụ thể nào của lớp đó.

- **Tầm quan trọng**: Nó cho phép chia sẻ trạng thái chung trên toàn bộ lớp và truy cập các phương thức tiện ích mà không tốn chi phí tạo các đối tượng thực thể, với siêu dữ liệu (metadata) được cấp phát trong vùng nhớ Metaspace.
- **Hiểu lầm phổ biến**: Cố gắng truy cập các trường thể hiện hoặc sử dụng các từ khóa `this` hoặc `super` bên trong các phương thức tĩnh (static methods). Vì các phương thức tĩnh thực thi mà không có đối tượng nhận thực tế, trình biên dịch sẽ từ chối các tham chiếu này.
- **Ví dụ nhỏ**: `Math.pow(2, 3)` được gọi trực tiếp trên bản thiết kế lớp `Math` chứ không phải trên một thực thể `new Math()`.

## final

Một trình sửa đổi ngăn không cho giá trị hoặc tham chiếu của một biến bị gán lại, ngăn một phương thức bị ghi đè trong các lớp con, hoặc ngăn một lớp bị kế thừa.

- **Tầm quan trọng**: Nó cung cấp sự an toàn chỉ đọc được thực thi bởi trình biên dịch, bảo vệ các ghi đè phương thức quan trọng khỏi sự can thiệp của lớp con và cho phép tối ưu hóa hiệu năng như nội tuyến hằng số của trình biên dịch JIT (JIT constant inlining).
- **Hiểu lầm phổ biến**: Tin rằng một tham chiếu đối tượng `final` làm cho chính đối tượng được tham chiếu đó trở nên bất biến (immutable). Tham chiếu đó không thể trỏ tới một đối tượng mới, nhưng các trường bên trong của đối tượng vẫn có thể thay đổi được.
- **Ví dụ nhỏ**: `final List<String> items = new ArrayList<>(); items.add("Apple"); // OK; items = new ArrayList<>(); // Compile error`

## abstract

Một trình sửa đổi khai báo rằng một lớp không thể được khởi tạo trực tiếp mà phải được kế thừa, hoặc một phương thức không chứa thân triển khai và bắt buộc phải được ghi đè.

- **Tầm quan trọng**: Nó hoạt động như một hợp đồng cho tính đa hình (Polymorphism), buộc các lớp con cụ thể phải triển khai các chữ ký phương thức còn thiếu và định nghĩa các hành vi cụ thể tương ứng.
- **Hiểu lầm phổ biến**: Khai báo một phương thức trừu tượng bên trong một lớp không trừu tượng (lớp cụ thể). Nếu một lớp chứa dù chỉ một phương thức trừu tượng, chính lớp đó cũng phải được khai báo là abstract.
- **Ví dụ nhỏ**: `public abstract class Animal { public abstract void makeSound(); }`

## volatile

Một trình sửa đổi áp dụng cho các biến giúp đảm bảo khả năng hiển thị luồng (thread visibility) và ngăn chặn việc sắp xếp lại câu lệnh (instruction reordering) bằng cách ép buộc tất cả các thao tác đọc và ghi diễn ra trực tiếp trên bộ nhớ chính (main memory), bỏ qua các thanh ghi CPU hoặc bộ nhớ đệm (caches).

- **Tầm quan trọng**: Nó đảm bảo rằng các cập nhật được thực hiện đối với một biến bởi một luồng (thread) sẽ hiển thị ngay lập tức với tất cả các luồng khác, ngăn chặn việc đọc bộ nhớ đệm cũ (stale cache) trong các vòng lặp đa luồng.
- **Hiểu lầm phổ biến**: Giả định rằng `volatile` đảm bảo an toàn luồng (thread safety) hoặc tính nguyên tử (atomicity) cho các thao tác phức hợp như `count++`. Nó không thực hiện đồng bộ hóa khóa (lock synchronization), vì vậy các tranh chấp dữ liệu (race conditions) vẫn có thể xảy ra.
- **Ví dụ nhỏ**: `private volatile boolean stopRequest = false;` đảm bảo luồng làm việc lập tức thoát khỏi vòng lặp của nó ngay sau khi luồng điều khiển đặt cờ hiệu này thành true.

## transient

Một trình sửa đổi ngăn cản một biến thể hiện bị tuần tự hóa (serialized) khi đối tượng bao bọc nó được chuyển đổi thành một dòng byte (byte stream).

- **Tầm quan trọng**: Nó bảo vệ thông tin nhạy cảm (ví dụ: mật khẩu, khóa bí mật) khỏi việc bị lưu xuống đĩa hoặc truyền qua mạng, và bỏ qua các tài nguyên không thể tuần tự hóa như kết nối cơ sở dữ liệu hoặc dòng tệp tin (file streams).
- **Hiểu lầm phổ biến**: Đánh dấu một biến tĩnh `static` là `transient` để ngăn nó bị tuần tự hóa. Các trường tĩnh thuộc về siêu dữ liệu của lớp chứ không thuộc về thực thể đối tượng, và chúng đã được tự động bỏ qua trong quá trình tuần tự hóa đối tượng.
- **Ví dụ nhỏ**: `private transient String databasePassword;` đảm bảo các thông tin đăng nhập cơ sở dữ liệu được loại trừ khi lưu trữ đối tượng cấu hình hệ thống.
