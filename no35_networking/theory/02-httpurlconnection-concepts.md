# Networking - Part 2

## Learning Goal

This file covers a focused slice of **Networking**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `HttpURLConnection` | Legacy HTTP client class, blocking, lacks modern protocol support, requires explicit timeout configuration. |
| `Java 11 HttpClient` | Modern, non-blocking HTTP client supporting HTTP/2, WebSockets, and asynchronous operations. |
| `Client-server model` | Distributed architecture where clients initiate requests and servers process and respond to them. |

## Detailed Notes

### HttpURLConnection

`HttpURLConnection` is Java's legacy HTTP client API, introduced in JDK 1.1. It represents a direct connection to a remote web server over HTTP/HTTPS. A key characteristic of `HttpURLConnection` is that it is fully blocking: any input or output operations suspend the calling thread until the network operation completes. 

Furthermore, it does not set connect or read timeouts by default (they are infinite), which can cause threads to hang indefinitely if the remote server is unresponsive. To prevent leaks, developers must explicitly call `disconnect()` on the instance, or ensure the input/output streams are thoroughly closed via try-with-resources.

### Java 11 HttpClient

Introduced in Java 11 (JEP 321), `HttpClient` replaces the legacy `HttpURLConnection` as the standard HTTP client API. It is designed to be immutable, thread-safe, and highly reusable, meaning a single client instance should be shared across the entire application to maximize connection pooling and thread reuse.

It supports HTTP/1.1 and HTTP/2 protocol versions, fallback mechanics, and is fully non-blocking. It integrates natively with Java's asynchronous programming model, returning `CompletableFuture` objects from its async calls, and utilizes the `Flow` API (Reactive Streams) for request and response body handling.

### Client-server model

The client-server model is a distributed application architecture that partitions tasks or workloads between the providers of a resource or service, called servers, and service requesters, called clients. Clients initiate communication sessions with servers by sending requests, and servers wait for incoming requests, process them, and send back responses.

In Java networking, clients use classes like `Socket` or `HttpClient` to initiate requests, whereas servers use `ServerSocket` or HTTP server frameworks to bind to a port and listen for client connections. The architecture is typically stateless at the transport level, meaning each request-response cycle is treated as an independent transaction.

---

## Why Java 11 HttpClient Supersedes HttpURLConnection

`HttpURLConnection` is Java's legacy HTTP client API, introduced in JDK 1.1. It has several severe limitations:
1. **Blocking APIs**: All requests and response processing block the calling thread. There is no built-in support for asynchronous requests, requiring developers to manually spawn threads or use `ExecutorService`.
2. **Infinite Defaults**: By default, connect and read timeouts are infinite, which can easily lead to application freezes if not explicitly configured.
3. **Complex Resource Management**: Closing streams does not automatically close the connection or release underlying socket descriptors in all cases, often requiring explicit `disconnect()` calls.
4. **No HTTP/2 Support**: It is hardcoded to HTTP/1.1 and does not support modern performance optimizations like request/response multiplexing or header compression.

Introduced in Java 11, `HttpClient` completely redesigns HTTP communication in Java. It is non-blocking, built around standard reactive streams, and supports both synchronous and asynchronous operations natively using `CompletableFuture`. It features built-in support for HTTP/2 (with automatic fallback to HTTP/1.1) and WebSockets, sharing resources across a single shared engine. It is designed to be immutable, thread-safe, and reusable across the entire application lifecycle.

### Mental Model: Blocking HttpURLConnection vs Reactive HttpClient
```
[HttpURLConnection (Thread-per-Request Blocking)]
Caller Thread ---> Send Request ---> Blocks Thread ---> Receive Response ---> Release

[HttpClient (Event Loop & CompletableFuture)]
Caller Thread ---> Send Async ---> Returns CompletableFuture ---> Thread Free
                                                                    | (Event Loop handles I/O)
Task Callback <--- Completes <--- Receive Response <----------------+
```

### Code Example: Blocking vs. Asynchronous Request Execution
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

### Cause-Effect Chain
Use legacy HttpURLConnection &rarr; Undergoing request blocks execution thread &rarr; High overhead from thread spawning for concurrency &rarr; Infinite defaults block threads permanently on server timeouts.
Transition to Java 11 HttpClient &rarr; Reactive streams engine handles asynchronous I/O &rarr; Non-blocking CompletableFuture returned &rarr; Request runs concurrently on shared thread pool &rarr; HTTP/2 multiplexing reduces connection overhead &rarr; Optimal application throughput achieved.

---

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?

## Code Examples

### HTTP request using HttpURLConnection (Legacy)
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

### HTTP request using HttpClient (Java 11+)
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

## Common Mistakes

- **Failing to configure timeouts on HttpURLConnection**: By default, `HttpURLConnection` has infinite timeouts. If the remote server stops responding during connection or reading, your application thread will hang forever. Always set connection and read timeouts.
- **Forgetting to disconnect HttpURLConnection**: Unlike try-with-resources streams, calling `close()` on the input stream of an `HttpURLConnection` does not automatically release the underlying TCP connection in all implementations unless `disconnect()` is called.
- **Over-creating HttpClient instances**: `HttpClient` is designed to be shared and reused across the entire application. Creating a new `HttpClient` for every request wastes resources (e.g. thread pools and connections). Use a singleton or inject a shared client instance.
