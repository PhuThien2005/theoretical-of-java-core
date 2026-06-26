# Lập trình mạng - Phần 2 (Networking - Part 2)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm một phần trọng tâm của **Lập trình mạng (Networking)**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là từ vựng rời rạc.

## Phạm vi đề cương (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `HttpURLConnection` | Lớp HTTP client cũ (legacy HTTP client class), hoạt động chặn (blocking), thiếu hỗ trợ giao thức hiện đại, yêu cầu cấu hình thời gian chờ (timeout) rõ ràng. |
| `Java 11 HttpClient` | HttpClient hiện đại, không chặn (non-blocking) hỗ trợ HTTP/2, WebSockets và các thao tác bất đồng bộ (asynchronous operations). |
| `Client-server model` | Kiến trúc phân tán (distributed architecture) nơi các client khởi tạo các yêu cầu (requests) và các server xử lý và phản hồi (respond) lại chúng. |

## Ghi chú chi tiết (Detailed Notes)

### HttpURLConnection

`HttpURLConnection` là API HTTP client cũ của Java, được giới thiệu trong JDK 1.1. Nó đại diện cho một kết nối trực tiếp đến một máy chủ web từ xa qua HTTP/HTTPS. Một đặc điểm quan trọng của `HttpURLConnection` là nó hoạt động hoàn toàn theo cơ chế chặn (blocking): bất kỳ thao tác nhập hoặc xuất (input/output) nào đều tạm dừng luồng gọi (calling thread) cho đến khi thao tác mạng hoàn thành.

Hơn nữa, nó không thiết lập thời gian chờ kết nối (connect timeout) hoặc thời gian chờ đọc (read timeout) theo mặc định (chúng là vô hạn), điều này có thể khiến các luồng bị treo vô tận nếu máy chủ từ xa không phản hồi. Để ngăn chặn rò rỉ tài nguyên, lập trình viên phải gọi `disconnect()` một cách rõ ràng trên instance, hoặc đảm bảo các luồng vào/ra (input/output streams) được đóng kỹ càng thông qua try-with-resources.

### HttpClient trong Java 11 (Java 11 HttpClient)

Được giới thiệu trong Java 11 (JEP 321), `HttpClient` thay thế `HttpURLConnection` cũ để trở thành API HTTP client tiêu chuẩn. Nó được thiết kế để bất biến (immutable), an toàn đa luồng (thread-safe), và có khả năng tái sử dụng cao, nghĩa là một instance client duy nhất nên được chia sẻ trong toàn bộ ứng dụng để tối đa hóa việc gom nhóm kết nối (connection pooling) và tái sử dụng luồng (thread reuse).

Nó hỗ trợ các phiên bản giao thức HTTP/1.1 và HTTP/2, cơ chế dự phòng (fallback mechanics), và hoàn toàn không chặn (non-blocking). Nó tích hợp tự nhiên với mô hình lập trình bất đồng bộ của Java, trả về các đối tượng `CompletableFuture` từ các lệnh gọi bất đồng bộ của nó, và sử dụng API `Flow` (Luồng phản ứng - Reactive Streams) để xử lý phần thân của yêu cầu và phản hồi (request and response body).

### Mô hình client-server (Client-server model)

Mô hình client-server là một kiến trúc ứng dụng phân tán phân chia các tác vụ hoặc khối lượng công việc giữa các nhà cung cấp tài nguyên hoặc dịch vụ, được gọi là server (máy chủ), và những thực thể yêu cầu dịch vụ, được gọi là client (máy khách). Các client khởi tạo các phiên giao tiếp với server bằng cách gửi các yêu cầu, và server chờ đợi các yêu cầu đến, xử lý chúng và gửi lại các phản hồi.

Trong lập trình mạng Java, client sử dụng các lớp như `Socket` hoặc `HttpClient` để khởi tạo các yêu cầu, trong khi server sử dụng `ServerSocket` hoặc các framework máy chủ HTTP để liên kết với một cổng (port) và lắng nghe các kết nối từ client. Kiến trúc này thường không lưu trạng thái (stateless) ở tầng vận chuyển, nghĩa là mỗi chu kỳ yêu cầu-phản hồi được xử lý như một giao dịch độc lập.

---

## Tại sao HttpClient trong Java 11 thay thế HttpURLConnection (Why Java 11 HttpClient Supersedes HttpURLConnection)

`HttpURLConnection` là API HTTP client cũ của Java, được giới thiệu trong JDK 1.1. Nó có một số hạn chế nghiêm trọng:
1. **Các API chặn (Blocking APIs)**: Tất cả các yêu cầu và xử lý phản hồi đều chặn luồng gọi. Không có hỗ trợ tích hợp cho các yêu cầu bất đồng bộ, yêu cầu lập trình viên phải tạo luồng thủ công hoặc sử dụng `ExecutorService`.
2. **Giá trị mặc định vô hạn**: Theo mặc định, thời gian chờ kết nối và thời gian chờ đọc là vô hạn, điều này có thể dễ dàng dẫn đến việc ứng dụng bị treo nếu không được cấu hình rõ ràng.
3. **Quản lý tài nguyên phức tạp**: Việc đóng các luồng dữ liệu (streams) không phải lúc nào cũng tự động đóng kết nối hoặc giải phóng các bộ mô tả socket bên dưới trong mọi trường hợp, thường đòi hỏi phải gọi `disconnect()` một cách rõ ràng.
4. **Không hỗ trợ HTTP/2**: Nó được code cứng (hardcoded) cho HTTP/1.1 và không hỗ trợ các tối ưu hóa hiệu năng hiện đại như ghép kênh yêu cầu/phản hồi (request/response multiplexing) hoặc nén tiêu đề (header compression).

Được giới thiệu trong Java 11, `HttpClient` thiết kế lại hoàn toàn việc giao tiếp HTTP trong Java. Nó không chặn (non-blocking), được xây dựng xung quanh các luồng phản ứng tiêu chuẩn, và hỗ trợ cả các thao tác đồng bộ và bất đồng bộ một cách tự nhiên bằng cách sử dụng `CompletableFuture`. Nó có hỗ trợ tích hợp cho HTTP/2 (tự động chuyển về HTTP/1.1 nếu cần) và WebSockets, chia sẻ tài nguyên trên một công cụ chia sẻ duy nhất. Nó được thiết kế để bất biến, an toàn đa luồng, và có thể tái sử dụng trong suốt vòng đời của ứng dụng.

### Mô hình tư duy: HttpURLConnection chặn so với HttpClient phản ứng (Mental Model: Blocking HttpURLConnection vs Reactive HttpClient)
```
[HttpURLConnection (Chặn trên mỗi luồng yêu cầu - Thread-per-Request Blocking)]
Luồng gọi ---> Gửi yêu cầu ---> Chặn luồng ---> Nhận phản hồi ---> Giải phóng

[HttpClient (Vòng lặp sự kiện & CompletableFuture - Event Loop & CompletableFuture)]
Luồng gọi ---> Gửi bất đồng bộ ---> Trả về CompletableFuture ---> Giải phóng luồng
                                                                     | (Vòng lặp sự kiện xử lý I/O)
Hàm gọi lại tác vụ <--- Hoàn thành <--- Nhận phản hồi <---------------+
```

### Ví dụ Code: Thực thi yêu cầu Chặn so với Bất đồng bộ (Code Example: Blocking vs. Asynchronous Request Execution)
```java
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class HttpComparisonDemo {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.github.com"))
            .GET()
            .build();

        // 1. Asynchronous non-blocking call returning CompletableFuture
        CompletableFuture<HttpResponse<String>> futureResponse = 
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        // Do other work while request executes in background
        System.out.println("Request sent asynchronously. Thread is free to do other tasks!");

        futureResponse.thenAccept(response -> {
            System.out.println("Async Status Code: " + response.statusCode());
            System.out.println("Async Body length: " + response.body().length());
        }).join(); // join to prevent main from exiting before callback runs
    }
}
// Output:
// Request sent asynchronously. Thread is free to do other tasks!
// Async Status Code: 200
// Async Body length: 512
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Sử dụng HttpURLConnection cũ
  → Yêu cầu đang xử lý chặn luồng thực thi
  → Chi phí cao từ việc tạo luồng để xử lý đồng thời
  → Các giá trị mặc định vô hạn chặn luồng vĩnh viễn khi máy chủ hết thời gian phản hồi.
```

Chuyển sang HttpClient trong Java 11 &rarr; Cơ chế luồng phản ứng xử lý I/O bất đồng bộ &rarr; Trả về CompletableFuture không chặn &rarr; Yêu cầu chạy đồng thời trên nhóm luồng dùng chung &rarr; Ghép kênh HTTP/2 giảm chi phí kết nối &rarr; Đạt được thông lượng ứng dụng tối ưu.

---

## Câu hỏi ôn tập phổ biến (Common Review Prompts)

- Khái niệm nào ở đây là quy tắc ở thời điểm biên dịch (compile-time)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi lúc chạy (runtime)?
- Khái niệm nào ở đây dễ là bẫy phỏng vấn (interview traps)?

## Các ví dụ code (Code Examples)

### Yêu cầu HTTP sử dụng HttpURLConnection (Cũ) (HTTP request using HttpURLConnection (Legacy))
```java
URL url = URI.create("https://api.github.com/users/octocat").toURL();
HttpURLConnection conn = (HttpURLConnection) url.openConnection();
conn.setRequestMethod("GET");
conn.setConnectTimeout(5000);
conn.setReadTimeout(5000);

int responseCode = conn.getResponseCode();
System.out.println("Response Code: " + responseCode);

try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
    String inputLine;
    StringBuilder response = new StringBuilder();
    while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
    }
    System.out.println("Response Body: " + response);
} finally {
    conn.disconnect();
}
```

### Yêu cầu HTTP sử dụng HttpClient (Java 11+) (HTTP request using HttpClient (Java 11+))
```java
HttpClient client = HttpClient.newBuilder()
    .connectTimeout(Duration.ofSeconds(5))
    .build();

HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create("https://api.github.com/users/octocat"))
    .header("Accept", "application/json")
    .GET()
    .build();

// Synchronous Request
HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
System.out.println("Status: " + response.statusCode());
System.out.println("Body: " + response.body());

// Asynchronous Request
client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
    .thenApply(HttpResponse::body)
    .thenAccept(System.out.println)
    .join(); // Wait for async completion in main thread
```

## Các lỗi thường gặp (Common Mistakes)

- **Không cấu hình thời gian chờ (timeout) trên HttpURLConnection**: Theo mặc định, `HttpURLConnection` có thời gian chờ vô hạn. Nếu máy chủ từ xa dừng phản hồi trong quá trình kết nối hoặc đọc, luồng ứng dụng của bạn sẽ bị treo mãi mãi. Luôn luôn thiết lập thời gian chờ kết nối và thời gian chờ đọc.
- **Quên ngắt kết nối HttpURLConnection**: Không giống như các luồng try-with-resources, việc gọi `close()` trên luồng đầu vào (input stream) của một `HttpURLConnection` không tự động giải phóng kết nối TCP bên dưới trong mọi trường hợp, trừ khi `disconnect()` được gọi.
- **Tạo quá nhiều instance HttpClient**: `HttpClient` được thiết kế để được chia sẻ và tái sử dụng trong toàn bộ ứng dụng. Việc tạo một `HttpClient` mới cho mỗi yêu cầu gây lãng phí tài nguyên (ví dụ: nhóm luồng và kết nối). Hãy sử dụng một singleton hoặc inject một instance client dùng chung.
