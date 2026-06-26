# Thuật ngữ lập trình mạng (Networking Terms)

Sử dụng tài liệu này khi một từ khóa trong phần lý thuyết có vẻ quá ngắn gọn. Mỗi thuật ngữ đều đi kèm định nghĩa, tầm quan trọng, hiểu lầm phổ biến và một ví dụ nhỏ.

## Socket (socket)

Một socket là một điểm cuối phần mềm logic (logical software endpoint) cho giao tiếp mạng. Nó đóng gói một địa chỉ IP (IP address) và một số hiệu cổng (port number) để cho phép giao tiếp hai chiều (bidirectional communication) qua mạng.

Tại sao thuật ngữ này quan trọng: Nó trừu tượng hóa sự phức tạp của các gói tin mạng (network packet) và giao thức (protocol) thành một giao diện đọc/ghi đơn giản bằng cách sử dụng các luồng I/O (I/O stream) tiêu chuẩn.

Hiểu lầm phổ biến: Socket không phải là các thành phần phần cứng vật lý, cũng không giới hạn ở mạng internet; chúng là các thực thể trừu tượng hóa của nhân hệ điều hành (operating system kernel abstraction).

Ví dụ nhỏ: `Socket socket = new Socket("localhost", 8080);` thiết lập một kết nối socket máy khách (client socket connection) đến cổng cục bộ 8080.

## TCP (TCP)

Giao thức điều khiển truyền vận (Transmission Control Protocol - TCP) là một giao thức truyền vận tin cậy, hướng kết nối (connection-oriented, reliable transport protocol) nhằm đảm bảo các gói tin được phân phát theo đúng thứ tự và không có lỗi.

Tại sao thuật ngữ này quan trọng: Hầu hết các dịch vụ internet (như HTTP, kết nối cơ sở dữ liệu và thư điện tử) đều dựa vào TCP vì chúng không thể chấp nhận việc mất mát hoặc hư hỏng gói tin.

Hiểu lầm phổ biến: Mọi người thường cho rằng TCP được tích hợp sẵn trong Java; thực tế nó là một giao thức được quản lý bởi nhân hệ điều hành (operating system kernel), và Java chỉ đơn thuần hiển lộ (expose) nó thông qua các lớp `Socket`.

Ví dụ nhỏ: Việc kết nối một `Socket` tiêu chuẩn tới một cổng sẽ tự động kích hoạt quá trình bắt tay 3 bước (3-way handshake) TCP bên dưới.

## UDP (UDP)

Giao thức gói dữ liệu người dùng (User Datagram Protocol - UDP) là một giao thức truyền vận không kết nối, không tin cậy (connectionless, unreliable transport protocol) gửi các gói tin độc lập mà không cần thiết lập kết nối hoặc đảm bảo việc phân phát.

Tại sao thuật ngữ này quan trọng: UDP có chi phí hao hao (overhead) thấp và độ trễ tối thiểu (minimal latency), làm cho nó lý tưởng cho các ứng dụng thời gian thực như DNS, truyền phát video (video streaming) và chơi game.

Hiểu lầm phổ biến: Không tin cậy không có nghĩa là vô dụng; điều đó có nghĩa là tầng ứng dụng (application layer) chịu trách nhiệm xử lý các gói tin bị mất hoặc không đúng thứ tự nếu cần.

Ví dụ nhỏ: `DatagramSocket socket = new DatagramSocket(9090);` lắng nghe các gói tin UDP được gửi trực tiếp đến cổng 9090.

## `ServerSocket`

Một `ServerSocket` là một lớp trong Java thực hiện liên kết (bind) với một cổng cục bộ và chặn (block) để chờ các kết nối TCP từ máy khách gửi đến thông qua phương thức `accept()` của nó.

Tại sao thuật ngữ này quan trọng: Nó là nền tảng của bất kỳ máy chủ TCP nào, đóng vai trò như một bộ lắng nghe (listener) tạo ra một đối tượng `Socket` thông thường cho mỗi máy khách kết nối.

Hiểu lầm phổ biến: `ServerSocket` không giao tiếp trực tiếp với máy khách. Nó chỉ chấp nhận kết nối và trả về một đối tượng `Socket` mới, đối tượng này mới được sử dụng để giao tiếp thực tế.

Ví dụ nhỏ:
```java
ServerSocket server = new ServerSocket(8080);
Socket client = server.accept(); // blocks until client connects
```

## `DatagramSocket`

`DatagramSocket` là lớp Java được sử dụng để gửi và nhận các gói tin UDP (`DatagramPacket`).

Tại sao thuật ngữ này quan trọng: Khác với TCP, một `DatagramSocket` đơn lẻ có thể nhận các gói tin từ nhiều nguồn và gửi các gói tin đến nhiều đích đến khác nhau mà không cần quản lý phiên làm việc (session).

Hiểu lầm phổ biến: `DatagramSocket` có một phương thức `connect()`, nhưng phương thức này không thiết lập một kết nối; nó chỉ đơn thuần giới hạn socket đó chỉ gửi và nhận các gói tin đến/từ một địa chỉ duy nhất.

Ví dụ nhỏ: `socket.send(new DatagramPacket(buf, buf.length, address, port));` gửi một gói tin đến đích.

## URI (URI)

Một Định danh tài nguyên đồng nhất (Uniform Resource Identifier - URI) là một chuỗi các ký tự xác định một tài nguyên logic hoặc vật lý bằng tên hoặc vị trí. Nó chỉ định nghĩa cú pháp và việc phân tích cú pháp (parsing).

Tại sao thuật ngữ này quan trọng: Việc khởi tạo một `URI` không thực hiện phân giải mạng (như tra cứu DNS), giúp cho việc xác thực và thao tác trên đó trở nên an toàn, nhanh chóng và không gây ra ngoại lệ (exception-free).

Hiểu lầm phổ biến: Nhiều người nghĩ `URI` và `URL` có thể thay thế cho nhau, nhưng một `URI` có thể là một tên gọi (URN) hoặc một bộ định vị (URL), và không yêu cầu khả năng kết nối mạng.

Ví dụ nhỏ: `URI uri = URI.create("mailto:user@example.com");` tạo ra một URI biểu diễn cú pháp địa chỉ email.

## `HttpClient`

`HttpClient` là API HTTP client hiện đại trong Java 11 hỗ trợ HTTP/1.1, HTTP/2, thực thi yêu cầu đồng bộ/bất đồng bộ và WebSockets.

Tại sao thuật ngữ này quan trọng: Nó thay thế `HttpURLConnection` dạng chặn (blocking) cũ kỹ bằng một API hiện đại dựa trên mẫu thiết kế Builder (builder-based API), hỗ trợ bất đồng bộ tích hợp sẵn thông qua `CompletableFuture`, và có khả năng tái sử dụng an toàn đa luồng (thread-safe).

Hiểu lầm phổ biến: Các nhà phát triển thường khởi tạo một client mới cho mỗi yêu cầu, điều này làm rò rỉ tài nguyên. Thay vào đó, một đối tượng `HttpClient` đơn lẻ nên được chia sẻ dùng chung.

Ví dụ nhỏ:
```java
HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder(URI.create("https://api.github.com")).build();
client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
```
