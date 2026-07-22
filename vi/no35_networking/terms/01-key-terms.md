# Các Thuật Ngữ Mạng (Networking Terms)

Sử dụng tài liệu này khi một từ ngữ trong phần lý thuyết có vẻ quá ngắn gọn. Mỗi thuật ngữ đều đi kèm ý nghĩa, tầm quan trọng, hiểu lầm phổ biến và một ví dụ nhỏ.

## Socket (Cổng nối)

Một socket là một đầu cuối phần mềm logic dành cho giao tiếp mạng. Nó bao bọc một địa chỉ IP và một số cổng (Port number) để cho phép truyền thông hai chiều qua mạng.

**Tại sao điều này quan trọng:** Nó trừu tượng hóa sự phức tạp của các gói tin và giao thức mạng thành một giao diện đọc/ghi đơn giản bằng cách sử dụng các luồng I/O tiêu chuẩn.

**Hiểu lầm phổ biến:** Sockets không phải là các thành phần phần cứng vật lý, cũng không giới hạn trong môi trường Internet; chúng là các thực thể trừu tượng của nhân hệ điều hành (OS kernel).

**Ví dụ nhỏ:** `Socket socket = new Socket("localhost", 8080);` thiết lập một kết nối socket máy khách tới cổng cục bộ 8080.

## TCP (Giao thức Điều khiển Truyền vận)

Giao thức Điều khiển Truyền vận (Transmission Control Protocol - TCP) là một giao thức truyền vận hướng kết nối, đáng tin cậy, đảm bảo các gói tin được gửi đi đúng thứ tự và không bị lỗi.

**Tại sao điều này quan trọng:** Hầu hết các dịch vụ Internet (như HTTP, kết nối cơ sở dữ liệu, và email) đều dựa vào TCP vì chúng không thể chấp nhận việc mất mát hoặc hư hỏng gói tin.

**Hiểu lầm phổ biến:** Mọi người thường nghĩ TCP được tích hợp sẵn trong Java; thực tế nó là một giao thức được quản lý bởi nhân hệ điều hành, và Java chỉ đơn thuần để lộ nó thông qua các lớp `Socket`.

**Ví dụ nhỏ:** Việc kết nối một `Socket` tiêu chuẩn tới một cổng sẽ tự động kích hoạt quá trình bắt tay 3 bước (3-way handshake) của giao thức TCP bên dưới.

## UDP (Giao thức Gói tin Người dùng)

Giao thức Gói tin Người dùng (User Datagram Protocol - UDP) là một giao thức truyền vận không hướng kết nối, không đảm bảo độ tin cậy, gửi các gói tin độc lập mà không cần thiết lập kết nối hay đảm bảo dữ liệu sẽ tới đích.

**Tại sao điều này quan trọng:** UDP có chi phí quản lý thấp và độ trễ tối thiểu, làm cho nó trở nên lý tưởng cho các ứng dụng thời gian thực như DNS, truyền phát video (video streaming), và trò chơi trực tuyến.

**Hiểu lầm phổ biến:** Không đáng tin cậy không có nghĩa là vô dụng; nó có nghĩa là tầng ứng dụng (Application layer) sẽ chịu trách nhiệm xử lý các gói tin bị mất hoặc sai thứ tự nếu cần thiết.

**Ví dụ nhỏ:** `DatagramSocket socket = new DatagramSocket(9090);` lắng nghe các gói tin UDP được gửi trực tiếp tới cổng 9090.

## ServerSocket

`ServerSocket` là một lớp trong Java thực hiện liên kết (bind) với một cổng cục bộ và chặn luồng để chờ các kết nối TCP đến từ máy khách thông qua phương thức `accept()` của nó.

**Tại sao điều này quan trọng:** Nó là nền tảng của bất kỳ máy chủ TCP nào, đóng vai trò như một bộ lắng nghe để tạo ra một đối tượng `Socket` thông thường cho mỗi máy khách kết nối thành công.

**Hiểu lầm phổ biến:** Lớp `ServerSocket` không trực tiếp giao tiếp với máy khách. Nó chỉ chấp nhận kết nối và trả về một đối tượng `Socket` mới, đối tượng mới này mới là thứ được dùng cho việc truyền thông thực tế.

**Ví dụ nhỏ:**
```java
ServerSocket server = new ServerSocket(8080);
Socket client = server.accept(); // blocks until client connects
```

## DatagramSocket

`DatagramSocket` là lớp Java được sử dụng để gửi và nhận các gói tin UDP (`DatagramPacket`).

**Tại sao điều này quan trọng:** Không giống như TCP, một `DatagramSocket` đơn lẻ có thể nhận các gói tin từ nhiều nguồn và gửi các gói tin tới nhiều đích khác nhau mà không cần quản lý các phiên kết nối (session).

**Hiểu lầm phổ biến:** `DatagramSocket` có phương thức `connect()`, nhưng phương thức này không thiết lập một kết nối thực sự; nó chỉ giới hạn socket chỉ gửi và nhận các gói tin đến/từ một địa chỉ duy nhất.

**Ví dụ nhỏ:** `socket.send(new DatagramPacket(buf, buf.length, address, port));` gửi một gói tin tới đích.

## URI (Mã định danh Tài nguyên Thống nhất)

Mã định danh Tài nguyên Thống nhất (Uniform Resource Identifier - URI) là một chuỗi ký tự dùng để định danh một tài nguyên logic hoặc vật lý bằng tên hoặc vị trí của nó. Nó chỉ định nghĩa cú pháp và phân tích cú pháp.

**Tại sao điều này quan trọng:** Việc khởi tạo một `URI` không thực hiện phân giải mạng (chẳng hạn như tra cứu DNS), giúp cho quá trình xác thực và xử lý chuỗi diễn ra an toàn, nhanh chóng và không gây ra ngoại lệ.

**Hiểu lầm phổ biến:** Nhiều người nghĩ rằng `URI` và `URL` có thể thay thế cho nhau, nhưng một `URI` có thể là một cái tên (URN) hoặc một bộ định vị (URL), và không yêu cầu khả năng kết nối mạng.

**Ví dụ nhỏ:** `URI uri = URI.create("mailto:user@example.com");` tạo một URI đại diện cho cú pháp của một địa chỉ email.

## HttpClient

`HttpClient` là API máy khách HTTP hiện đại được giới thiệu từ Java 11, hỗ trợ HTTP/1.1, HTTP/2, thực thi yêu cầu đồng bộ/bất đồng bộ, và WebSockets.

**Tại sao điều này quan trọng:** Nó thay thế cho lớp `HttpURLConnection` cồng kềnh và gây chặn luồng bằng một API hiện đại dựa trên mẫu thiết kế Builder, hỗ trợ bất đồng bộ tích hợp sẵn thông qua `CompletableFuture`, và khả năng tái sử dụng an toàn luồng.

**Hiểu lầm phổ biến:** Lập trình viên thường khởi tạo một máy khách mới cho mỗi yêu cầu, điều này làm rò rỉ tài nguyên hệ thống. Thay vào đó, một thực thể `HttpClient` duy nhất nên được chia sẻ dùng chung.

**Ví dụ nhỏ:**
```java
HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder(URI.create("https://api.github.com")).build();
client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
```
