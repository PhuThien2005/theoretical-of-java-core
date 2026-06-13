# Networking - Part 1

## Learning Goal

This file covers a focused slice of **Networking**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Socket programming` | A socket is an endpoint for network communication. |
| `TCP socket` | A socket is an endpoint for network communication. |
| `UDP socket` | A socket is an endpoint for network communication. |
| `Socket` | A socket is an endpoint for network communication. |
| `ServerSocket` | A socket is an endpoint for network communication. |
| `DatagramSocket` | A socket is an endpoint for network communication. |
| `InetAddress` |InetAddress is a specific concept in Networking; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `URL` |URL is a specific concept in Networking; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `URI` |URI is a specific concept in Networking; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Basic HTTP request` |Basic HTTP request is a specific concept in Networking; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### Socket programming

A socket is an endpoint for network communication.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Socket programming` in one sentence.
- Recognize `Socket programming` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Socket programming`.

Tiny example or mental model:

- When reading code, ask: what does `Socket programming` change, allow, reject, or clarify?

### TCP socket

A socket is an endpoint for network communication.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `TCP socket` in one sentence.
- Recognize `TCP socket` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `TCP socket`.

Tiny example or mental model:

- When reading code, ask: what does `TCP socket` change, allow, reject, or clarify?

### UDP socket

A socket is an endpoint for network communication.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `UDP socket` in one sentence.
- Recognize `UDP socket` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `UDP socket`.

Tiny example or mental model:

- When reading code, ask: what does `UDP socket` change, allow, reject, or clarify?

### Socket

A socket is an endpoint for network communication.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Socket` in one sentence.
- Recognize `Socket` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Socket`.

Tiny example or mental model:

- When reading code, ask: what does `Socket` change, allow, reject, or clarify?

### ServerSocket

A socket is an endpoint for network communication.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `ServerSocket` in one sentence.
- Recognize `ServerSocket` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `ServerSocket`.

Tiny example or mental model:

- When reading code, ask: what does `ServerSocket` change, allow, reject, or clarify?

### DatagramSocket

A socket is an endpoint for network communication.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `DatagramSocket` in one sentence.
- Recognize `DatagramSocket` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `DatagramSocket`.

Tiny example or mental model:

- When reading code, ask: what does `DatagramSocket` change, allow, reject, or clarify?

### InetAddress

InetAddress is a specific concept in Networking; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `InetAddress` in one sentence.
- Recognize `InetAddress` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `InetAddress`.

Tiny example or mental model:

- When reading code, ask: what does `InetAddress` change, allow, reject, or clarify?

### URL

URL is a specific concept in Networking; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `URL` in one sentence.
- Recognize `URL` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `URL`.

Tiny example or mental model:

- When reading code, ask: what does `URL` change, allow, reject, or clarify?

### URI

URI is a specific concept in Networking; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `URI` in one sentence.
- Recognize `URI` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `URI`.

Tiny example or mental model:

- When reading code, ask: what does `URI` change, allow, reject, or clarify?

### Basic HTTP request

Basic HTTP request is a specific concept in Networking; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Basic HTTP request` in one sentence.
- Recognize `Basic HTTP request` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Basic HTTP request`.

Tiny example or mental model:

- When reading code, ask: what does `Basic HTTP request` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?

## Code Examples

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

## Common Mistakes

- **Forgetting to Close Sockets**: Sockets utilize underlying OS resources (file descriptors). Failing to close them inside a `finally` block or try-with-resources statement leads to resource leaks and connection exhaustion.
- **Blocking accept() on Main Thread**: The `serverSocket.accept()` method blocks the calling thread until a connection is made. In server applications, this should be executed on a separate worker thread or thread pool to keep the server responsive.
- **Using deprecated URL constructors**: Calling `new URL("https://google.com")` is deprecated starting with Java 20. Always use `URI.create("https://google.com").toURL()` instead.
