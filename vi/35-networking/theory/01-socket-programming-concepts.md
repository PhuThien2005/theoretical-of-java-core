# Lập Trình Mạng (Networking) - Phần 1

## Mục Tiêu Học Tập

Tài liệu này tập trung vào một phần chuyên sâu của **Lập Trình Mạng (Networking)**. Hãy nghiên cứu từng khái niệm dưới dạng quy tắc thực tế trong Java, không chỉ đơn thuần là lý thuyết từ vựng.

## Tóm Tắt Nội Dung (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `Socket programming` | Một socket là một đầu cuối (endpoint) cho giao tiếp mạng. |
| `TCP socket` | Một socket TCP thiết lập giao tiếp mạng hướng kết nối và đáng tin cậy. |
| `UDP socket` | Một socket UDP gửi các gói tin không hướng kết nối và không đảm bảo độ tin cậy. |
| `Socket` | Lớp đại diện cho một socket máy khách (client socket) trong Java. |
| `ServerSocket` | Lớp đại diện cho một socket máy chủ lắng nghe kết nối TCP trong Java. |
| `DatagramSocket` | Lớp Java dùng để gửi và nhận gói tin UDP. |
| `InetAddress` | InetAddress là một khái niệm cụ thể trong Lập trình mạng; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ lỗi của nó thay vì chỉ ghi nhớ tên gọi. |
| `URL` | URL là một khái niệm cụ thể trong Lập trình mạng; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ lỗi của nó thay vì chỉ ghi nhớ tên gọi. |
| `URI` | URI là một khái niệm cụ thể trong Lập trình mạng; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ lỗi của nó thay vì chỉ ghi nhớ tên gọi. |
| `Basic HTTP request` | Yêu cầu HTTP cơ bản là một khái niệm cụ thể trong Lập trình mạng; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ lỗi của nó thay vì chỉ ghi nhớ tên gọi. |

## Ghi Chú Chi Tiết

### Lập trình Socket (Socket programming)

Một socket là một đầu cuối (endpoint) dành cho giao tiếp mạng.

Hãy sử dụng khái niệm này để dự đoán chính xác quy tắc Java, dạng thức được phép, và chế độ lỗi. Hãy xem lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn lý thuyết.

Kiểm tra thực tế:
- Định nghĩa `Lập trình Socket` trong một câu.
- Nhận biết `Lập trình Socket` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi liên quan đến `Lập trình Socket`.

Mô hình tư duy hoặc ví dụ nhỏ:
- Khi đọc mã nguồn, hãy hỏi: `Lập trình Socket` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

### Socket TCP

Một socket TCP đại diện cho một đầu cuối giao tiếp mạng sử dụng giao thức TCP.

Kiểm tra thực tế:
- Định nghĩa `Socket TCP` trong một câu.
- Nhận biết `Socket TCP` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi liên quan đến `Socket TCP`.

Mô hình tư duy hoặc ví dụ nhỏ:
- Khi đọc mã nguồn, hãy hỏi: `Socket TCP` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

### Socket UDP

Một socket UDP đại diện cho một đầu cuối giao tiếp mạng sử dụng giao thức UDP.

Kiểm tra thực tế:
- Định nghĩa `Socket UDP` trong một câu.
- Nhận biết `Socket UDP` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi, hạn chế, hoặc sự đánh đổi liên quan đến `Socket UDP`.

Mô hình tư duy hoặc ví dụ nhỏ:
- Khi đọc mã nguồn, hãy hỏi: `Socket UDP` thay đổi, cho phép, từ chối, hoặc làm rõ điều gì?

## Tại sao Bắt tay TCP Khác với UDP Không hướng Kết nối

Giao thức Điều khiển Truyền vận (Transmission Control Protocol - TCP) là một giao thức truyền vận hướng kết nối, đáng tin cậy, đảm bảo việc phân phát các luồng byte theo đúng thứ tự và đã được kiểm tra lỗi. Để đạt được điều này, TCP yêu cầu một giai đoạn thiết lập kết nối chính thức (bắt tay ba bước: SYN, SYN-ACK, ACK) giữa `Socket` máy khách và `ServerSocket` máy chủ để đồng bộ hóa số thứ tự (sequence number) và cấp phát tài nguyên trước khi bắt đầu bất kỳ quá trình truyền dữ liệu nào. Ngược lại, Giao thức Gói tin Người dùng (User Datagram Protocol - UDP) là một giao thức không hướng kết nối, gọn nhẹ, truyền tải các gói tin độc lập (`DatagramPacket` qua `DatagramSocket`) mà không cần thiết lập phiên kết nối. UDP không theo dõi xem các gói tin có đến đích hay không, không truyền lại các gói tin bị mất, và không bắt buộc thứ tự của gói tin. Việc loại bỏ chi phí bắt tay và theo dõi phân phát này làm cho UDP nhanh hơn đáng kể và có độ trễ thấp hơn, là lựa chọn lý tưởng cho việc truyền phát video thời gian thực hoặc chơi game, trong khi TCP là bắt buộc đối với các ứng dụng yêu cầu tính toàn vẹn dữ liệu tuyệt đối, chẳng hạn như HTTP hoặc các kết nối cơ sở dữ liệu.

### Mô hình tư duy: Kết nối TCP so với Gửi thư UDP
```mermaid
flowchart TD
    subgraph TCP Connection [TCP: Hướng Kết nối (Cuộc gọi Điện thoại)]
        A[Client: Socket connect] -->|1. SYN| B[Server: ServerSocket accept]
        B -->|2. SYN-ACK| A
        A -->|3. ACK| B
        B --> C[Established Session: Luồng Byte Đáng tin cậy]
    end
    subgraph UDP Packet [UDP: Không hướng Kết nối (Gửi Thư qua Bưu điện)]
        D[Client: DatagramSocket send] -->|DatagramPacket| E[Server: DatagramSocket receive]
        D -->|DatagramPacket| E
        Note over D,E: Không bắt tay, không xác nhận, các gói tin có thể đến sai thứ tự hoặc bị mất
    end
```

### Ví dụ Code: Kết nối TCP so với Nhận trực tiếp UDP
```java
// TCP ServerSocket blocks waiting for handshake connection
try (java.net.ServerSocket server = new java.net.ServerSocket(8080)) {
    java.net.Socket client = server.accept(); // Quá trình bắt tay hoàn tất tại đây
    System.out.println("TCP client connected: " + client.getRemoteSocketAddress());
}

// UDP DatagramSocket receives packets without handshake
try (java.net.DatagramSocket udpSocket = new java.net.DatagramSocket(9090)) {
    byte[] buf = new byte[256];
    java.net.DatagramPacket packet = new java.net.DatagramPacket(buf, buf.length);
    udpSocket.receive(packet); // Chặn cho đến khi có một gói tin gửi trực tiếp tới
    System.out.println("UDP packet received from: " + packet.getSocketAddress());
}
```

### Chuỗi Nguyên nhân - Kết quả
Yêu cầu kết nối socket TCP được gửi &rarr; Máy khách và máy chủ thực hiện bắt tay 3 bước &rarr; Hệ điều hành cấp phát bộ đệm theo dõi gói tin và các số thứ tự &rarr; Thiết lập luồng byte đáng tin cậy, đúng thứ tự &rarr; Xảy ra mất gói tin trên mạng &rarr; Hệ điều hành phát hiện thiếu gói tin ACK, thực hiện gửi lại gói tin &rarr; Đạt được mục tiêu không mất dữ liệu.

---

### Socket

Một socket là một đầu cuối (endpoint) cho giao tiếp mạng.

### ServerSocket

Một ServerSocket lắng nghe các kết nối đến từ các socket máy khách.

## Tại sao các Hoạt động Socket gây Chặn không được Chạy trên Luồng chính (Main Thread)

Các hoạt động mạng trong I/O cổ điển của Java (sockets) về bản chất là hoạt động gây chặn (blocking). Các phương thức như `ServerSocket.accept()`, `InputStream.read()`, và `OutputStream.write()` sẽ đình chỉ việc thực thi của luồng cho đến khi nhận được kết nối, dữ liệu mạng được gửi tới, hoặc các bộ đệm được đẩy sạch (flush). Nếu các hoạt động này được chạy trực tiếp trên luồng chính của ứng dụng (ví dụ: luồng giao diện người dùng - UI thread hoặc luồng dịch vụ chính), toàn bộ ứng dụng sẽ bị đóng băng và không phản hồi với các hoạt động nhập liệu của người dùng hoặc các sự kiện vòng đời khác trong khi chờ các gói tin mạng. Để duy trì khả năng phản hồi của ứng dụng và xử lý các máy khách đồng thời, các hoạt động socket phải được đẩy sang các luồng làm việc (worker thread) riêng biệt hoặc các nhóm luồng (thread pool) được quản lý. Trong mô hình máy chủ đa luồng tiêu chuẩn, luồng chính chạy một vòng lặp bị chặn tại phương thức `accept()`, và khi nhận được một kết nối, nó lập tức bàn giao đối tượng `Socket` của máy khách cho một luồng mới hoặc một tác vụ thực thi, giải phóng luồng chính để tiếp tục chặn chờ kết nối tiếp theo.

### Mô hình tư duy: Chấp nhận Kết nối Đa luồng
```
Vòng lặp Luồng chính (Main Thread Loop)
      |
      v
serverSocket.accept() (Chặn luồng cho đến khi máy khách kết nối)
      |
      +---> Nhận được Kết nối!
      |
      +---> Tạo Luồng Xử lý Máy khách (Luồng làm việc - Worker Thread)
      |         |
      |         +---> Xử lý đọc/ghi socket máy khách (Chỉ chặn luồng làm việc này)
      |
      v
Lặp lại vòng lặp: serverSocket.accept() (Luồng chính sẵn sàng nhận máy khách tiếp theo ngay lập tức)
```

### Ví dụ Code: Máy chủ Echo Đa luồng
```java
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class MultiThreadedServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        System.out.println("Multi-threaded server running...");

        while (!Thread.currentThread().isInterrupted()) {
            try {
                // Luồng chính bị chặn tại đây, nhưng không chặn việc xử lý của máy khách khác
                Socket clientSocket = serverSocket.accept(); 
                
                // Đẩy các hoạt động đọc/ghi gây chặn của máy khách vào nhóm luồng
                threadPool.submit(() -> handleClient(clientSocket));
            } catch (IOException e) {
                break;
            }
        }
    }

    private static void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            // Chặn luồng làm việc, không chặn luồng chính
            String line = in.readLine(); 
            out.println("Echo: " + line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### Chuỗi Nguyên nhân - Kết quả
Hoạt động accept/read của Socket chạy trên luồng chính &rarr; Luồng thực thi bị đình chỉ để chờ phản hồi từ mạng &rarr; Ứng dụng bị đóng băng, từ chối tương tác của người dùng và các sự kiện UI &rarr; Việc xử lý kết nối được đẩy sang nhóm luồng Executor &rarr; Các luồng làm việc bị chặn để chờ dữ liệu từ máy khách &rarr; Luồng chính vẫn tự do để chấp nhận các kết nối mới đến, cho phép tính đồng thời cao.

---

## Tại sao các Socket và Luồng dữ liệu phải được Đóng đúng cách

Mỗi socket mạng `Socket` and `ServerSocket` khi mở ra đều được cấp phát một bộ mô tả tệp (File descriptor) tương ứng trong nhân hệ điều hành máy chủ để quản lý bộ đệm socket TCP/IP bên dưới. Hệ điều hành đặt ra giới hạn nghiêm ngặt về số lượng bộ mô tả tệp mà một tiến trình có thể mở. Nếu một ứng dụng không đóng các socket và các luồng vào/ra liên quan của chúng khi kết nối kết thúc, các bộ mô tả tệp này vẫn mở trong nhân hệ điều hành, dẫn đến hiện tượng rò rỉ bộ mô tả tệp (File descriptor leak). Theo thời gian, ứng dụng sẽ cạn kiệt giới hạn bộ mô tả tệp, khiến hệ điều hành từ chối bất kỳ kết nối socket tiếp theo nào và ném ra ngoại lệ `java.net.SocketException: Too many open files`. Ngoài ra, việc không đóng socket máy chủ sẽ ngăn cản socket giải phóng cổng đã liên kết, dẫn đến lỗi `java.net.BindException: Address already in use` khi ứng dụng cố gắng khởi động lại. Việc sử dụng cấu trúc try-with-resources đảm bảo các socket được tự động đóng và tài nguyên được giải phóng trả lại cho hệ điều hành ngay khi khối lệnh kết thúc.

### Mô hình tư duy: Tích lũy Bộ mô tả Tệp trong Nhân hệ điều hành
```
Socket ứng dụng JVM
      | (Mở kết nối)
      v
Nhân OS cấp phát Bộ mô tả tệp (FD) & Liên kết cổng (ví dụ: Cổng 8080)
      |
      +---> Phiên làm việc kết thúc, Socket KHÔNG được đóng!
      |
Nhân OS duy trì cấp phát FD và Cổng 8080 tiếp tục bị khóa ở trạng thái CLOSE_WAIT.
      |
Cố gắng khởi động lại ứng dụng -> BindException: Address already in use.
Cố gắng mở thêm kết nối -> SocketException: Too many open files.
```

### Ví dụ Code: Đóng Kết nối An sau
```java
import java.io.*;
import java.net.*;

public class SocketClosureDemo {
    public static void main(String[] args) {
        // Try-with-resources đảm bảo bộ mô tả tệp được giải phóng trả lại cho nhân OS
        try (Socket socket = new Socket("example.com", 80);
             OutputStream out = socket.getOutputStream();
             InputStream in = socket.getInputStream()) {
             
            out.write("GET / HTTP/1.1\r\nHost: example.com\r\n\r\n".getBytes());
            int data = in.read(); // Đọc một byte duy nhất
            System.out.println("First response byte: " + data);
            
        } catch (IOException e) {
            e.printStackTrace();
        } // socket.close() được gọi tự động, giải phóng Bộ mô tả tệp của OS ngay lập tức
    }
}
```

### Chuỗi Nguyên nhân - Kết quả
Kết nối mạng kết thúc mà không đóng socket &rarr; Nhân OS duy trì cấp phát bộ mô tả tệp và liên kết cổng trong bảng hoạt động &rarr; Số lượng bộ mô tả tệp tăng liên tục &rarr; Tiến trình đạt đến giới hạn tối đa của OS &rarr; JVM ném ra `SocketException: Too many open files` cho các yêu cầu mạng tiếp theo &rarr; Cổng không thể liên kết lại khi khởi động lại, ném ra ngoại lệ `BindException`.

---

### DatagramSocket

DatagramSocket là một socket để gửi hoặc nhận các gói tin datagram.

### InetAddress

Lớp InetAddress đại diện cho một địa chỉ IP (Internet Protocol).

### URL

Lớp URL đại diện cho một Bộ định vị Tài nguyên Thống nhất (Uniform Resource Locator).

### URI

Lớp URI đại diện cho một Mã định danh Tài nguyên Thống nhất (Uniform Resource Identifier).

## Tại sao Java 20 Khai tử các Hàm khởi tạo URL để Ưu tiên dùng URI

Một Mã định danh Tài nguyên Thống nhất (Uniform Resource Identifier - URI) là một biểu diễn chuỗi dựa thuần túy trên cú pháp để xác định một tài nguyên, trong khi một Bộ định vị Tài nguyên Thống nhất (Uniform Resource Locator - URL) cung cấp vị trí mạng cụ thể và giao thức để định vị và truy cập tài nguyên đó. Trước Java 20, lập trình viên thường khởi tạo đối tượng URL bằng cách sử dụng các hàm khởi tạo như `new URL("https://example.com")`. Tuy nhiên, điều này đã bị khai tử (deprecated) vì hàm khởi tạo `URL` cố gắng phân giải tên máy chủ (host name) qua DNS trong quá trình khởi tạo và thực hiện các thao tác so sánh (như `url.equals(otherUrl)` hoặc `url.hashCode()`), hành vi này sẽ thực hiện các truy vấn mạng gây chặn luồng. Chạy một truy vấn DNS bên trong một hàm khởi tạo đơn giản hoặc kiểm tra tập hợp (collection check) là vi phạm các nguyên tắc thiết kế API cơ bản, làm cho các bản đồ băm (hash map) sử dụng URL chạy cực kỳ chậm, và có thể làm sập ứng dụng nếu quá trình phân giải DNS thất bại hoặc hết thời gian chờ (timeout). Ngược lại, lớp `URI` thực hiện phân tích cú pháp dựa trên cú pháp RFC 2396 nghiêm ngặt mà không phân giải máy chủ hoặc thực hiện các cuộc gọi mạng, làm cho việc khởi tạo nó hoàn toàn an toàn và có thể dự đoán được; lập trình viên nên xây dựng một đối tượng `URI` trước, sau đó chuyển đổi nó sang một đối tượng `URL` bằng phương thức `uri.toURL()` chỉ khi thực sự cần một kết nối vật lý.

### Mô hình tư duy: URI chỉ kiểm tra Cú pháp so với URL phụ thuộc vào Mạng
```
Sử dụng Hàm khởi tạo URL (Không an sau):
new URL("https://example.com") ---> Gọi tra cứu DNS (Chặn mạng!) ---> Có thể thất bại khi khởi tạo

Sử dụng URI và chuyển đổi sang URL (An toàn):
URI.create("https://example.com") ---> Chỉ phân tích cú pháp cục bộ (Nhanh, Cục bộ)
      |
      v
uri.toURL() ---> Đối tượng URL được tạo chỉ khi thực sự cần truy cập mạng
```

### Ví dụ Code: Kiểm tra Cú pháp với URI
```java
import java.net.*;

public class UrlUriDemo {
    public static void main(String[] args) throws Exception {
        // ĐÃ KHAI TỬ từ Java 20: Chặn chờ phân giải DNS trong quá trình kiểm tra của hàm khởi tạo
        // URL oldUrl = new URL("https://example.com"); 

        // KHUYÊN DÙNG: Chỉ kiểm tra cú pháp chuỗi cục bộ (Thực thi tức thì)
        URI uri = URI.create("https://example.com"); 
        
        // Chỉ chuyển đổi sang URL khi chuẩn bị mở kết nối mạng
        URL url = uri.toURL(); 
        System.out.println("Protocol: " + url.getProtocol()); // In ra "Protocol: https"
    }
}
```

### Chuỗi Nguyên nhân - Kết quả
Hàm khởi tạo `new URL(string)` được gọi &rarr; Hàm khởi tạo kích hoạt tra cứu DNS mạng bên dưới để phân giải tên máy chủ &rarr; Hết thời gian chờ hoặc lỗi của máy chủ DNS lan truyền thành các ngoại lệ trong quá trình khởi tạo đối tượng &rarr; Luồng bị chặn trong khi khởi tạo đối tượng &rarr; `URI.create(string)` được sử dụng thay thế &rarr; Chuỗi được phân tích cú pháp theo ngữ pháp RFC 2396 cục bộ &rarr; Đạt được việc tạo URI tức thì và không ném ngoại lệ.

---

### Yêu cầu HTTP cơ bản (Basic HTTP request)

Một yêu cầu HTTP cơ bản được gửi qua mạng để truy xuất hoặc sửa đổi tài nguyên.

---

## Các Câu Hỏi Ôn Tập Thường Gặp

- **Khái niệm nào ở đây là quy tắc thời gian biên dịch?**
  Hầu hết các lớp API mạng đều có các kiểu ngoại lệ đã được kiểm tra (checked exception) buộc lập trình viên phải bắt hoặc khai báo ném ra (ví dụ: `IOException`, `UnknownHostException`).
- **Khái niệm nào ở đây ảnh hưởng đến hành vi thời gian chạy?**
  Các thao tác mạng gây chặn (blocking) như `Socket.accept()` và `InputStream.read()`, cùng cơ chế tra cứu DNS của các constructor `URL` cũ.
- **Khái niệm nào ở đây dễ là bẫy phỏng vấn?**
  Việc sử dụng các constructor của `URL` (đã bị deprecated trong Java 20) gây ra truy vấn DNS ngầm làm chậm ứng dụng, và hành vi của `URL.equals()` cũng thực hiện tra cứu DNS.

---

## Các Ví dụ Code

### Máy chủ & Máy khách Socket TCP
```java
// ServerSocket lắng nghe trên cổng 8080
try (ServerSocket serverSocket = new ServerSocket(8080)) {
    System.out.println("Server listening on port 8080...");
    try (Socket clientSocket = serverSocket.accept();
         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
         BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
         
        String inputLine = in.readLine();
        System.out.println("Received: " + inputLine);
        out.println("Hello Client!");
    }
}

// Máy khách kết nối tới localhost:8080
try (Socket socket = new Socket("localhost", 8080);
     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
     
    out.println("Hello Server!");
    String response = in.readLine();
    System.out.println("Server response: " + response);
}
```

### Máy chủ & Máy khách Datagram UDP
```java
// Nhận một gói tin DatagramPacket (UDP)
try (DatagramSocket socket = new DatagramSocket(9090)) {
    byte[] buffer = new byte[1024];
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    socket.receive(packet); // Chặn cho đến khi nhận được một gói tin
    String message = new String(packet.getData(), 0, packet.getLength());
    System.out.println("Received UDP: " + message);
}

// Gửi một gói tin DatagramPacket
try (DatagramSocket socket = new DatagramSocket()) {
    String msg = "Hello UDP!";
    byte[] buffer = msg.getBytes();
    InetAddress address = InetAddress.getByName("localhost");
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 9090);
    socket.send(packet);
}
```

---

## Các lỗi thường gặp

- **Quên đóng Socket**: Các socket sử dụng tài nguyên hệ điều hành bên dưới (bộ mô tả tệp). Việc không đóng them trong khối `finally` hoặc bằng cấu trúc try-with-resources sẽ dẫn đến rò rỉ tài nguyên và cạn kiệt kết nối.
- **Chặn accept() trên Luồng chính**: Phương thức `serverSocket.accept()` chặn luồng gọi cho đến khi một kết nối được thiết lập. Trong các ứng dụng máy chủ, điều này nên được chạy trên một luồng làm việc riêng biệt hoặc nhóm luồng để đảm bảo máy chủ luôn sẵn sàng phản hồi.
- **Sử dụng các hàm khởi tạo URL đã bị khai tử**: Việc gọi `new URL("https://google.com")` đã bị khai tử từ Java 20. Luôn sử dụng `URI.create("https://google.com").toURL()` để thay thế.

---

## Liên Kết Tham Khảo

- https://docs.oracle.com/javase/tutorial/networking/sockets/index.html (Hướng dẫn lập trình Socket của Oracle)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/net/Socket.html (Javadoc của lớp Socket)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/net/URI.html (Javadoc của lớp URI)
- https://openjdk.org/jeps/321 (Đề xuất JEP 321 về HTTP Client)
