# Lập trình mạng - Phần 1 (Networking - Part 1)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm một phần trọng tâm của **Lập trình mạng (Networking)**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là từ vựng rời rạc.

## Phạm vi đề cương (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `Socket programming` | Socket là một điểm cuối (endpoint) cho giao tiếp mạng (network communication). |
| `TCP socket` | Socket là một điểm cuối cho giao tiếp mạng. |
| `UDP socket` | Socket là một điểm cuối cho giao tiếp mạng. |
| `Socket` | Socket là một điểm cuối cho giao tiếp mạng. |
| `ServerSocket` | ServerSocket là một điểm cuối cho giao tiếp mạng. |
| `DatagramSocket` | DatagramSocket là một điểm cuối cho giao tiếp mạng. |
| `InetAddress` | `InetAddress` là một khái niệm cụ thể trong Lập trình mạng; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại (failure mode) của nó thay vì chỉ nhớ tên. |
| `URL` | `URL` là một khái niệm cụ thể trong Lập trình mạng; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên. |
| `URI` | `URI` là một khái niệm cụ thể trong Lập trình mạng; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên. |
| `Basic HTTP request` | `Basic HTTP request` là một khái niệm cụ thể trong Lập trình mạng; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên. |

## Ghi chú chi tiết (Detailed Notes)

### Lập trình socket (Socket programming)

Một socket là một điểm cuối (endpoint) cho giao tiếp mạng (network communication).

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Socket programming` trong một câu.
- Nhận biết `Socket programming` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Socket programming`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `Socket programming` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Socket TCP (TCP socket)

Một socket là một điểm cuối (endpoint) cho giao tiếp mạng (network communication).

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `TCP socket` trong một câu.
- Nhận biết `TCP socket` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `TCP socket`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `TCP socket` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### Socket UDP (UDP socket)

Một socket là một điểm cuối (endpoint) cho giao tiếp mạng (network communication).

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `UDP socket` trong một câu.
- Nhận biết `UDP socket` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `UDP socket`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `UDP socket` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Tại sao Bắt tay TCP khác với UDP không kết nối (Why TCP Handshakes Differ From Connectionless UDP)

Giao thức điều khiển truyền vận (Transmission Control Protocol - TCP) là một giao thức vận chuyển (transport protocol) đáng tin cậy, hướng kết nối (connection-oriented), đảm bảo việc truyền tải các luồng byte theo đúng thứ tự và không bị lỗi. Để đạt được điều này, TCP yêu cầu một giai đoạn thiết lập kết nối chính thức (bắt tay ba bước (three-way handshake): SYN, SYN-ACK, ACK) giữa `Socket` phía client (máy khách) và `ServerSocket` phía server (máy chủ) để đồng bộ hóa các số trình tự (sequence numbers) và phân bổ tài nguyên trước khi bắt đầu bất kỳ quá trình truyền dữ liệu nào. Ngược lại, Giao thức dữ liệu người dùng (User Datagram Protocol - UDP) là một giao thức dung lượng nhẹ, không kết nối (connectionless), truyền các gói tin độc lập (`DatagramPacket` thông qua `DatagramSocket`) mà không cần thiết lập một phiên làm việc (session). UDP không theo dõi xem các gói tin có đến hay không, không truyền lại các gói tin bị mất, và không bắt buộc thứ tự gói tin. Việc loại bỏ quá trình bắt tay và theo dõi phân phát này giúp UDP nhanh hơn đáng kể và có độ trễ (latency) thấp hơn, rất lý tưởng cho truyền phát video thời gian thực hoặc chơi game, trong khi TCP là bắt buộc đối với các ứng dụng yêu cầu tính toàn vẹn dữ liệu (data integrity) tuyệt đối, chẳng hạn như kết nối HTTP hoặc kết nối cơ sở dữ liệu.

### Mô hình tư duy: Kết nối TCP so với Gửi thư UDP (Mental Model: TCP Connection vs. UDP Mailing)
```mermaid
flowchart TD
    subgraph TCP Connection [TCP: Connection-Oriented (Phone Call)]
        A[Client: Socket connect] -->|1. SYN| B[Server: ServerSocket accept]
        B -->|2. SYN-ACK| A
        A -->|3. ACK| B
        B --> C[Established Session: Reliable Byte Stream]
    end
    subgraph UDP Packet [UDP: Connectionless (Mailing Letters)]
        D[Client: DatagramSocket send] -->|DatagramPacket| E[Server: DatagramSocket receive]
        D -->|DatagramPacket| E
        Note over D,E: Không bắt tay, không xác nhận, các gói tin có thể đến không đúng thứ tự hoặc bị mất
    end
```

### Ví dụ Code: Kết nối TCP so với Nhận trực tiếp UDP (Code Example: TCP Connection vs. UDP Direct Receive)
```java
// TCP ServerSocket blocks waiting for handshake connection
try (java.net.ServerSocket server = new java.net.ServerSocket(8080)) {
    java.net.Socket client = server.accept(); // Handshake completes here
    System.out.println("TCP client connected: " + client.getRemoteSocketAddress());
}

// UDP DatagramSocket receives packets without handshake
try (java.net.DatagramSocket udpSocket = new java.net.DatagramSocket(9090)) {
    byte[] buf = new byte[256];
    java.net.DatagramPacket packet = new java.net.DatagramPacket(buf, buf.length);
    udpSocket.receive(packet); // Blocks until a packet arrives directly
    System.out.println("UDP packet received from: " + packet.getSocketAddress());
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Yêu cầu kết nối socket TCP
  → Client và server thực hiện bắt tay 3 bước
  → Hệ điều hành (OS) phân bổ bộ đệm theo dõi gói tin và số trình tự
  → Luồng byte đáng tin cậy, có thứ tự được thiết lập
  → Xảy ra hiện tượng rớt gói tin mạng
  → Hệ điều hành phát hiện thiếu ACK, truyền lại gói tin
  → Đạt được mục tiêu không mất dữ liệu.
```


---

### Socket (Socket)

Một socket là một điểm cuối (endpoint) cho giao tiếp mạng (network communication).

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Socket` trong một câu.
- Nhận biết `Socket` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Socket`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `Socket` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### ServerSocket (ServerSocket)

Một socket là một điểm cuối (endpoint) cho giao tiếp mạng (network communication).

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `ServerSocket` trong một câu.
- Nhận biết `ServerSocket` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `ServerSocket`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `ServerSocket` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Tại sao các thao tác Socket chặn không được chạy trên Luồng chính (Why Blocking Socket Operations Must Not Run on the Main Thread)

Các thao tác mạng trong I/O cổ điển của Java (Classic I/O - sockets) có tính chất chặn (blocking) theo thiết kế. Các phương thức như `ServerSocket.accept()`, `InputStream.read()`, và `OutputStream.write()` sẽ tạm dừng việc thực thi luồng (thread execution) cho đến khi nhận được một kết nối, dữ liệu mạng được truyền đến, hoặc các bộ đệm được xả (flush). Nếu các thao tác này chạy trực tiếp trên luồng chính (main thread) của ứng dụng (ví dụ: luồng giao diện người dùng hoặc luồng dịch vụ chính), toàn bộ ứng dụng sẽ bị đóng băng và không phản hồi với các hoạt động nhập liệu từ người dùng hoặc các sự kiện vòng đời trong khi chờ đợi các gói tin mạng. Để duy trì khả năng phản hồi của ứng dụng và xử lý các client đồng thời, các thao tác socket phải được chuyển sang các luồng làm việc (worker threads) riêng biệt hoặc các nhóm luồng (thread pools) được quản lý. Trong mô hình server đa luồng tiêu chuẩn, luồng chính chạy một vòng lặp bị chặn tại `accept()`, và khi nhận được một kết nối, ngay lập tức chuyển client `Socket` đó sang một luồng mới hoặc một tác vụ executor, giải phóng luồng chính để tiếp tục chặn chờ kết nối tiếp theo.

### Mô hình tư duy: Chấp nhận kết nối đa luồng (Mental Model: Multi-threaded Connection Acceptance)
```
Vòng lặp luồng chính (Main Thread Loop)
      |
      v
serverSocket.accept() (Chặn luồng chính cho đến khi client kết nối)
      |
      +---> Nhận được kết nối!
      |
      +---> Tạo luồng xử lý Client mới (Luồng làm việc - Worker Thread)
      |         |
      |         +---> Xử lý đọc/ghi socket của client (Chỉ chặn luồng làm việc này)
      |
      v
Lặp lại vòng lặp: serverSocket.accept() (Luồng chính ngay lập tức sẵn sàng chấp nhận client tiếp theo)
```

### Ví dụ Code: Máy chủ Echo đa luồng (Code Example: Multi-threaded Echo Server)
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
                // Main thread blocks here, but does not block client processing
                Socket clientSocket = serverSocket.accept(); 
                
                // Offload blocking client reads/writes to thread pool
                threadPool.submit(() -> handleClient(clientSocket));
            } catch (IOException e) {
                break;
            }
        }
    }

    private static void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            // Blocks worker thread, not main thread
            String line = in.readLine(); 
            out.println("Echo: " + line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Socket accept/read chạy trên luồng chính
  → Việc thực thi luồng bị tạm dừng để chờ phản hồi mạng
  → Ứng dụng bị đóng băng, từ chối tương tác của người dùng và các sự kiện giao diện người dùng (UI)
  → Việc xử lý kết nối được chuyển sang nhóm luồng Executor
  → Các luồng làm việc bị chặn để chờ dữ liệu của client
  → Luồng chính vẫn tự do để chấp nhận các kết nối mới, cho phép xử lý đồng thời cao.
```


---

## Tại sao Socket và Stream phải được đóng đúng cách (Why Sockets and Streams Must Be Closed Properly)

Mỗi `Socket` and `ServerSocket` mạng được mở sẽ phân bổ một bộ mô tả tệp (file descriptor) tương ứng trong nhân (kernel) của hệ điều hành máy chủ để quản lý bộ đệm TCP/IP bên dưới. Hệ điều hành áp đặt một giới hạn nghiêm ngặt về số lượng bộ mô tả tệp mà một tiến trình có thể mở. Nếu một ứng dụng không đóng các socket và các luồng vào/ra (input/output streams) liên kết với chúng khi một kết nối kết thúc, các bộ mô tả tệp này sẽ vẫn mở trong nhân hệ điều hành, dẫn đến rò rỉ bộ mô tả tệp (file descriptor leak). Theo thời gian, ứng dụng sẽ cạn kiệt giới hạn bộ mô tả tệp, khiến hệ điều hành từ chối bất kỳ kết nối socket tiếp theo nào và ném ra ngoại lệ `java.net.SocketException: Too many open files`. Hơn nữa, việc không đóng server socket sẽ ngăn cản socket đó giải phóng cổng (port) đã liên kết, dẫn đến `java.net.BindException: Address already in use` khi ứng dụng cố gắng khởi động lại. Sử dụng try-with-resources đảm bảo các socket được tự động đóng và tài nguyên được giải phóng ngay lập tức về cho hệ điều hành khi khối mã kết thúc.

### Mô hình tư duy: Tích lũy bộ mô tả tệp trong nhân hệ điều hành (Mental Model: File Descriptor Accumulation in Kernel)
```
Ứng dụng JVM mở Socket
      | (Mở kết nối)
      v
Nhân OS phân bổ Bộ mô tả tệp (FD) & Ràng buộc cổng (ví dụ: Cổng 8080)
      |
      +---> Phiên kết nối kết thúc, Socket KHÔNG được đóng!
      |
Nhân OS vẫn giữ phân bổ FD và Cổng 8080 tiếp tục bị khóa trong trạng thái CLOSE_WAIT.
      |
Cố gắng khởi động lại ứng dụng -> BindException: Address already in use.
Cố gắng mở thêm kết nối -> SocketException: Too many open files.
```

### Ví dụ Code: Đóng kết nối an toàn (Code Example: Secure Connection Closure)
```java
import java.io.*;
import java.net.*;

public class SocketClosureDemo {
    public static void main(String[] args) {
        // Try-with-resources guarantees file descriptors are released back to the OS kernel
        try (Socket socket = new Socket("example.com", 80);
             OutputStream out = socket.getOutputStream();
             InputStream in = socket.getInputStream()) {
             
            out.write("GET / HTTP/1.1\r\nHost: example.com\r\n\r\n".getBytes());
            int data = in.read(); // Read single byte
            System.out.println("First response byte: " + data);
            
        } catch (IOException e) {
            e.printStackTrace();
        } // socket.close() is automatically called, releasing OS File Descriptor immediately
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Kết nối mạng kết thúc mà không đóng socket
  → Nhân OS duy trì phân bổ bộ mô tả tệp và ràng buộc cổng trong bảng hoạt động
  → Số lượng bộ mô tả tệp tăng liên tục
  → Tiến trình đạt đến giới hạn tối đa của hệ điều hành
  → JVM ném ngoại lệ `SocketException: Too many open files` cho các yêu cầu mạng tiếp theo
  → Cổng không thể liên kết lại khi khởi động lại, ném ngoại lệ `BindException`.
```


---

### DatagramSocket (DatagramSocket)

Một socket là một điểm cuối (endpoint) cho giao tiếp mạng (network communication).

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `DatagramSocket` trong một câu.
- Nhận biết `DatagramSocket` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `DatagramSocket`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `DatagramSocket` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### InetAddress (InetAddress)

`InetAddress` là một khái niệm cụ thể trong Lập trình mạng; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `InetAddress` trong một câu.
- Nhận biết `InetAddress` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `InetAddress`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `InetAddress` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### URL (URL)

`URL` là một khái niệm cụ thể trong Lập trình mạng; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `URL` trong một câu.
- Nhận biết `URL` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `URL`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `URL` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

### URI (URI)

`URI` là một khái niệm cụ thể trong Lập trình mạng; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `URI` trong một câu.
- Nhận biết `URI` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `URI`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `URI` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Tại sao Java 20 loại bỏ hàm khởi tạo URL để chuyển sang URI (Why Java 20 Deprecated URL Constructors in Favor of URI)

Bộ định danh tài nguyên đồng nhất (Uniform Resource Identifier - URI) là một đại diện chuỗi dựa hoàn toàn trên cú pháp để xác định tài nguyên, trong khi Bộ định vị tài nguyên đồng nhất (Uniform Resource Locator - URL) cung cấp giao thức và vị trí mạng cụ thể để tìm vị trí và truy cập tài nguyên. Trước Java 20, các lập trình viên thường khởi tạo `URL` bằng cách sử dụng các hàm khởi tạo (constructors) như `new URL("https://example.com")`. Tuy nhiên, điều này đã bị loại bỏ vì hàm khởi tạo `URL` cố gắng giải quyết tên máy chủ (hostname) qua truy vấn DNS (DNS lookup) trong các thao tác khởi tạo và so sánh (chẳng hạn như `url.equals(otherUrl)` hoặc `url.hashCode()`), thực hiện các truy vấn mạng dạng chặn (blocking network lookups). Việc chạy truy vấn DNS bên trong một hàm khởi tạo đơn giản hoặc kiểm tra tập hợp (collection check) sẽ vi phạm các nguyên tắc thiết kế API cơ bản, làm cho các bảng băm (hash maps) sử dụng `URL` trở nên cực kỳ chậm và có thể làm sập ứng dụng nếu quá trình phân giải DNS bị lỗi hoặc hết thời gian chờ (timeout). Ngược lại, lớp `URI` thực hiện phân tích cú pháp nghiêm ngặt theo tiêu chuẩn RFC 2396 mà không phân giải máy chủ hoặc thực hiện các cuộc gọi mạng, giúp việc khởi tạo nó hoàn toàn an toàn và có thể dự đoán được; các lập trình viên nên xây dựng một `URI` trước, sau đó chuyển đổi nó thành `URL` bằng cách sử dụng `uri.toURL()` chỉ khi thực sự cần một kết nối vật lý.

### Mô hình tư duy: URI chỉ kiểm tra cú pháp so với URL phụ thuộc vào mạng (Mental Model: Syntax-only URI vs Network-dependent URL)
```
Sử dụng hàm khởi tạo URL (Không an toàn):
new URL("https://example.com") ---> Gọi DNS Lookup (Chặn mạng!) ---> Có thể thất bại trong lúc khởi tạo

Sử dụng URI và chuyển đổi thành URL (An toàn):
URI.create("https://example.com") ---> Chỉ phân tích cú pháp chuỗi cục bộ (Nhanh, Cục bộ)
      |
      v
uri.toURL() ---> Đối tượng URL chỉ được tạo khi truy cập mạng được yêu cầu một cách rõ ràng
```

### Ví dụ Code: Kiểm tra cú pháp với URI (Code Example: Syntax checking with URI)
```java
import java.net.*;

public class UrlUriDemo {
    public static void main(String[] args) throws Exception {
        // DEPRECATED in Java 20: Blocks waiting for DNS resolution during constructor check
        // URL oldUrl = new URL("https://example.com"); 

        // RECOMMENDED: Local string syntax checking only (Instant execution)
        URI uri = URI.create("https://example.com"); 
        
        // Convert to URL only when preparing to open a connection
        URL url = uri.toURL(); 
        System.out.println("Protocol: " + url.getProtocol()); // Prints "Protocol: https"
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Hàm khởi tạo `new URL(string)` được gọi
  → Hàm khởi tạo kích hoạt truy vấn DNS mạng để phân giải tên máy chủ
  → Thời gian chờ hoặc lỗi của máy chủ DNS lan truyền thành ngoại lệ trong quá trình khởi tạo đối tượng
  → Luồng bị chặn trong quá trình khởi tạo đối tượng
  → Sử dụng `URI.create(string)` để thay thế
  → Chuỗi được phân tích cú pháp bằng ngữ pháp RFC 2396 tại chỗ
  → Đạt được việc tạo URI nhanh chóng, không có ngoại lệ.
```


---

### Yêu cầu HTTP cơ bản (Basic HTTP request)

`Basic HTTP request` là một khái niệm cụ thể trong Lập trình mạng; hãy học quy tắc Java, các trường hợp sử dụng hợp lệ và chế độ thất bại của nó thay vì chỉ nhớ tên.

Hãy sử dụng nó để dự đoán quy tắc Java chính xác, dạng được cho phép, và chế độ thất bại (failure mode). Đọc lại nó với một ví dụ nhỏ thay vì chỉ ghi nhớ nhãn của nó.

Kiểm tra thực tế:

- Định nghĩa `Basic HTTP request` trong một câu.
- Nhận biết `Basic HTTP request` trong mã nguồn, lệnh, tài liệu hoặc câu hỏi phỏng vấn.
- Giải thích một lỗi (bug), giới hạn hoặc sự đánh đổi liên quan đến `Basic HTTP request`.

Ví dụ nhỏ hoặc mô hình tư duy (mental model):

- Khi đọc mã nguồn, hãy hỏi: `Basic HTTP request` thay đổi, cho phép, từ chối hoặc làm rõ điều gì?

## Câu hỏi ôn tập phổ biến (Common Review Prompts)

- Khái niệm nào ở đây là quy tắc ở thời điểm biên dịch (compile-time)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi lúc chạy (runtime)?
- Khái niệm nào ở đây dễ là bẫy phỏng vấn (interview traps)?

## Các ví dụ code (Code Examples)

### TCP Socket Server & Client

```java
// ServerSocket listening on port 8080
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

// Client connecting to localhost:8080
try (Socket socket = new Socket("localhost", 8080);
     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
     
    out.println("Hello Server!");
    String response = in.readLine();
    System.out.println("Server response: " + response);
}
```

### UDP Datagram Server & Client

```java
// Receiving a DatagramPacket (UDP)
try (DatagramSocket socket = new DatagramSocket(9090)) {
    byte[] buffer = new byte[1024];
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    socket.receive(packet); // Blocks until a packet is received
    String message = new String(packet.getData(), 0, packet.getLength());
    System.out.println("Received UDP: " + message);
}

// Sending a DatagramPacket
try (DatagramSocket socket = new DatagramSocket()) {
    String msg = "Hello UDP!";
    byte[] buffer = msg.getBytes();
    InetAddress address = InetAddress.getByName("localhost");
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 9090);
    socket.send(packet);
}
```

## Các lỗi thường gặp (Common Mistakes)

- **Quên đóng Socket**: Sockets sử dụng các tài nguyên hệ điều hành bên dưới (bộ mô tả tệp). Việc không đóng chúng trong khối `finally` hoặc câu lệnh try-with-resources sẽ dẫn đến rò rỉ tài nguyên và cạn kiệt kết nối.
- **Chặn accept() trên Luồng chính**: Phương thức `serverSocket.accept()` chặn luồng gọi nó cho đến khi có kết nối được thiết lập. Trong các ứng dụng máy chủ, việc này nên được thực thi trên một luồng làm việc hoặc nhóm luồng riêng biệt để giữ cho máy chủ luôn phản hồi nhanh.
- **Sử dụng các hàm khởi tạo URL đã lỗi thời**: Việc gọi `new URL("https://google.com")` đã bị loại bỏ kể từ Java 20. Luôn luôn sử dụng `URI.create("https://google.com").toURL()` để thay thế.

## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/javase/tutorial/networking/sockets/index.html (Sockets Programming Oracle Tutorial)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/net/Socket.html (Socket JavaDoc)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/net/URI.html (URI JavaDoc)
- https://openjdk.org/jeps/321 (HTTP Client JEP)
