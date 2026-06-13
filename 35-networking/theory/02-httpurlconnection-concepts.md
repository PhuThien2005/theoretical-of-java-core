# Networking - Part 2

## Learning Goal

This file covers a focused slice of **Networking**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `HttpURLConnection` |HttpURLConnection is a specific concept in Networking; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Java 11 HttpClient` |Java 11 HttpClient is a specific concept in Networking; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Client-server model` |Client-server model is a specific concept in Networking; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### HttpURLConnection

HttpURLConnection is a specific concept in Networking; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `HttpURLConnection` in one sentence.
- Recognize `HttpURLConnection` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `HttpURLConnection`.

Tiny example or mental model:

- When reading code, ask: what does `HttpURLConnection` change, allow, reject, or clarify?

### Java 11 HttpClient

Java 11 HttpClient is a specific concept in Networking; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Java 11 HttpClient` in one sentence.
- Recognize `Java 11 HttpClient` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Java 11 HttpClient`.

Tiny example or mental model:

- When reading code, ask: what does `Java 11 HttpClient` change, allow, reject, or clarify?

### Client-server model

Client-server model is a specific concept in Networking; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Client-server model` in one sentence.
- Recognize `Client-server model` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Client-server model`.

Tiny example or mental model:

- When reading code, ask: what does `Client-server model` change, allow, reject, or clarify?

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
