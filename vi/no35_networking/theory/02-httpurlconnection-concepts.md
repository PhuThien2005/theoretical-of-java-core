# Lập Trình Mạng (Networking) - Phần 2

## Mục Tiêu Học Tập

Tài liệu này tập trung vào một phần chuyên sâu của **Lập Trình Mạng (Networking)**. Hãy nghiên cứu từng khái niệm dưới dạng quy tắc thực tế trong Java, không chỉ đơn thuần là lý thuyết từ vựng.

## Tóm Tắt Nội Dung (Outline Coverage)

- **`HttpURLConnection`** — HttpURLConnection: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Java 11 HttpClient`** — Java 11 HttpClient: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Client-server model`** — Client-server model: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

## Ghi Chú Chi Tiết

### HttpURLConnection

`HttpURLConnection` là API máy khách HTTP kế thừa của Java, được giới thiệu từ JDK 1.1. Nó đại diện cho một kết nối trực tiếp đến một máy chủ web từ xa thông qua giao thức HTTP/HTTPS. Đặc điểm chính của `HttpURLConnection` là nó hoàn toàn gây chặn (blocking): bất kỳ hoạt động vào hoặc ra nào cũng sẽ đình chỉ luồng gọi cho đến khi hoạt động mạng hoàn tất.

Hơn nữa, theo mặc định nó không thiết lập thời gian chờ kết nối (connect timeout) hoặc thời gian chờ đọc (read timeout) (chúng là vô hạn), điều này có thể khiến các luồng bị treo vô thời hạn nếu máy chủ từ xa không phản hồi. Để ngăn chặn rò rỉ tài nguyên, lập trình viên phải gọi phương thức `disconnect()` một cách tường minh trên thực thể, hoặc đảm bảo các luồng vào/ra được đóng hoàn toàn thông qua cấu trúc try-with-resources.

### Java 11 HttpClient

Được giới thiệu từ Java 11 (JEP 321), `HttpClient` thay thế cho `HttpURLConnection` kế thừa để trở thành API HTTP client tiêu chuẩn. Nó được thiết kế bất biến (immutable), an sau luồng (thread-safe), và có khả năng tái sử dụng cao, nghĩa là một thực thể client duy nhất nên được chia sẻ trong toàn bộ ứng dụng để tối đa hóa hiệu quả gom nhóm kết nối (connection pooling) và tái sử dụng luồng (thread reuse).

Nó hỗ trợ các phiên bản giao thức HTTP/1.1 và HTTP/2, cơ chế dự phòng (fallback), và hoàn toàn không gây chặn (non-blocking). Nó tích hợp tự nhiên với mô hình lập trình bất đồng bộ của Java, trả về các đối tượng `CompletableFuture` từ các cuộc gọi bất đồng bộ của nó, và sử dụng API `Flow` (Dòng phản ứng - Reactive Streams) để xử lý thân yêu cầu (request body) và thân phản hồi (response body).

### Mô hình khách-chủ (Client-server model)

Mô hình khách-chủ là một kiến trúc ứng dụng phân tán phân chia các tác vụ hoặc khối lượng công việc giữa các bên cung cấp tài nguyên hoặc dịch vụ, được gọi là máy chủ (server), và các bên yêu cầu dịch vụ, được gọi là máy khách (client). Máy khách khởi tạo các phiên truyền thông với máy chủ bằng cách gửi các yêu cầu, và máy chủ chờ đợi các yêu cầu đến, xử lý chúng, và gửi lại các phản hồi.

Trong lập trình mạng Java, máy khách sử dụng các lớp như `Socket` hoặc `HttpClient` to initiate requests, whereas servers use `ServerSocket` or HTTP server frameworks to bind to a port and listen for client connections. Kiến trúc này thường không lưu trạng thái (stateless) ở tầng truyền vận, nghĩa là mỗi chu kỳ yêu cầu-phản hồi được xử lý như một giao dịch độc lập.

---

## Tại sao Java 11 HttpClient Thay thế HttpURLConnection

`HttpURLConnection` là API máy khách HTTP kế thừa của Java, được giới thiệu từ JDK 1.1. Nó có một số hạn chế nghiêm trọng:
1. **Các API gây chặn**: Tất cả các yêu cầu và quá trình xử lý phản hồi đều chặn luồng gọi. Không có hỗ trợ tích hợp sẵn cho các yêu cầu bất đồng bộ, buộc lập trình viên phải tạo luồng thủ công hoặc sử dụng `ExecutorService`.
2. **Giá trị mặc định vô hạn**: Theo mặc định, thời gian chờ kết nối và thời gian chờ đọc là vô hạn, điều này dễ dẫn đến việc ứng dụng bị treo nếu không được cấu hình rõ ràng.
3. **Quản lý tài nguyên phức tạp**: Việc đóng các luồng dữ liệu không tự động đóng kết nối hoặc giải phóng các bộ mô tả socket bên dưới trong mọi trường hợp, thường đòi hỏi phải gọi phương thức `disconnect()` một cách tường minh.
4. **Không hỗ trợ HTTP/2**: Nó được mã hóa cứng cho HTTP/1.1 và không hỗ trợ các tối ưu hóa hiệu năng hiện đại như ghép kênh yêu cầu/phản hồi (multiplexing) hoặc nén tiêu đề (header compression).

Được giới thiệu từ Java 11, `HttpClient` thiết kế lại hoàn toàn cơ chế giao tiếp HTTP trong Java. Nó không gây chặn, được xây dựng xung quanh các dòng phản ứng tiêu chuẩn, và hỗ trợ tự nhiên cả các hoạt động đồng bộ và bất đồng bộ bằng cách sử dụng `CompletableFuture`. Nó tích hợp sẵn hỗ trợ cho HTTP/2 (với cơ chế tự động chuyển hướng về HTTP/1.1 khi cần) và WebSockets, chia sẻ tài nguyên trên một công cụ dùng chung duy nhất. Nó được thiết kế bất biến, an sau luồng, và có thể tái sử dụng trong suốt vòng đời của ứng dụng.

### Mô hình tư duy: HttpURLConnection gây Chặn so với HttpClient Phản ứng
```
[HttpURLConnection (Chặn Luồng trên Mỗi Yêu cầu)]
Luồng Gọi ---> Gửi Yêu cầu ---> Chặn Luồng ---> Nhận Phản hồi ---> Giải phóng

[HttpClient (Vòng lặp Sự kiện & CompletableFuture)]
Luồng Gọi ---> Gửi Bất đồng bộ ---> Trả về CompletableFuture ---> Luồng tự do để làm việc khác
                                                                     | (Vòng lặp sự kiện xử lý I/O)
Hàm gọi lại (Callback) <--- Hoàn tất <--- Nhận Phản hồi <-------------+
```

### Ví dụ Code: Thực thi Yêu cầu gây Chặn so với Bất đồng bộ
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

        // 1. Cuộc gọi bất đồng bộ không gây chặn trả về CompletableFuture
        CompletableFuture<HttpResponse<String>> futureResponse = 
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        // Làm việc khác trong khi yêu cầu đang thực thi ở nền sau
        System.out.println("Request sent asynchronously. Thread is free to do other tasks!");

        futureResponse.thenAccept(response -> {
            System.out.println("Async Status Code: " + response.statusCode());
            System.out.println("Async Body length: " + response.body().length());
        }).join(); // join để ngăn luồng main thoát trước khi hàm callback chạy
    }
}
```

### Chuỗi Nguyên nhân - Kết quả
Sử dụng HttpURLConnection kế thừa &rarr; Yêu cầu đang thực thi chặn luồng thực hiện &rarr; Chi phí cao do phải tạo luồng để xử lý đồng thời &rarr; Các giá trị mặc định vô hạn chặn luồng vĩnh viễn khi máy chủ hết thời gian chờ (timeout).

Chuyển sang Java 11 HttpClient &rarr; Công cụ dòng phản ứng xử lý I/O bất đồng bộ &rarr; Trả về CompletableFuture không gây chặn &rarr; Yêu cầu chạy đồng thời trên nhóm luồng chia sẻ &rarr; Ghép kênh HTTP/2 giảm chi phí kết nối &rarr; Đạt được thông lượng ứng dụng tối ưu.

---

## Các Câu Hỏi Ôn Tập Thường Gặp

- **Khái niệm nào ở đây là quy tắc thời gian biên dịch?**
  Các lớp `HttpClient`, `HttpRequest`, và `HttpResponse` sử dụng các mẫu thiết kế Builder để định cấu hình trước khi thực thi.
- **Khái niệm nào ở đây ảnh hưởng đến hành vi thời gian chạy?**
  Cơ chế bất đồng bộ của `HttpClient.sendAsync()`, quản lý nhóm luồng (thread pool) bên dưới, và hành vi tự động chuyển đổi phiên bản giao thức HTTP/2 về HTTP/1.1.
- **Khái niệm nào ở đây dễ là bẫy phỏng vấn?**
  Tạo mới một thực thể `HttpClient` cho mỗi yêu cầu HTTP thay vì chia sẻ một thực thể duy nhất, dẫn đến rò rỉ tài nguyên hệ thống và không tận dụng được các kết nối TCP đã thiết lập.

---

## Các Ví dụ Code

### Yêu cầu HTTP sử dụng HttpURLConnection (Cổ điển)
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

### Yêu cầu HTTP sử dụng HttpClient (Java 11+)
```java
HttpClient client = HttpClient.newBuilder()
    .connectTimeout(Duration.ofSeconds(5))
    .build();

HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create("https://api.github.com/users/octocat"))
    .header("Accept", "application/json")
    .GET()
    .build();

// Yêu cầu đồng bộ
HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
System.out.println("Status: " + response.statusCode());
System.out.println("Body: " + response.body());

// Yêu cầu bất đồng bộ
client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
    .thenApply(HttpResponse::body)
    .thenAccept(System.out.println)
    .join(); // Chờ xử lý bất đồng bộ hoàn tất trong luồng main
```

---

## Các lỗi thường gặp

- **Quên cấu hình thời gian chờ (timeout) trên HttpURLConnection**: Theo mặc định, `HttpURLConnection` có thời gian chờ vô hạn. Nếu máy chủ từ xa ngừng phản hồi trong khi kết nối hoặc đọc dữ liệu, luồng ứng dụng của bạn sẽ bị treo vĩnh viễn. Luôn thiết lập thời gian chờ kết nối và thời gian chờ đọc.
- **Quên ngắt kết nối HttpURLConnection**: Không giống như các luồng try-with-resources tự động đóng, việc gọi `close()` trên luồng vào của một `HttpURLConnection` không tự động giải phóng kết nối TCP bên dưới trong một số triển khai trừ khi `disconnect()` được gọi một cách rõ ràng.
- **Tạo quá nhiều thực thể HttpClient**: Lớp `HttpClient` được thiết kế để chia sẻ và tái sử dụng trong toàn bộ ứng dụng. Việc tạo một `HttpClient` mới cho mỗi yêu cầu gây lãng phí tài nguyên (như nhóm luồng và kết nối). Hãy sử dụng mẫu thiết kế Singleton hoặc tiêm (inject) một thực thể client dùng chung.
