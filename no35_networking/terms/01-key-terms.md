# Networking Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## socket

A socket is a logical software endpoint for network communication. It wraps an IP address and a port number to allow bidirectional communication over the network.

Why it matters: It abstracts the complexity of network packets and protocols into a simple read/write interface using standard I/O streams.

Common confusion: Sockets are not physical hardware components, nor are they limited to the internet; they are operating system kernel abstractions.

Small example: `Socket socket = new Socket("localhost", 8080);` establishes a client socket connection to local port 8080.

## TCP

Transmission Control Protocol (TCP) is a connection-oriented, reliable transport protocol that guarantees packets are delivered in order and without errors.

Why it matters: Most internet services (like HTTP, database connections, and mail) rely on TCP because they cannot tolerate packet loss or corruption.

Common confusion: People assume TCP is built into Java; it is actually a protocol managed by the operating system kernel, and Java merely exposes it via `Socket` classes.

Small example: Connecting a standard `Socket` to a port automatically triggers the underlying TCP 3-way handshake.

## UDP

User Datagram Protocol (UDP) is a connectionless, unreliable transport protocol that sends independent packets without establishing a connection or guaranteeing delivery.

Why it matters: UDP has low overhead and minimal latency, making it ideal for real-time applications like DNS, video streaming, and gaming.

Common confusion: Unreliable does not mean useless; it means the application layer is responsible for handling missing or out-of-order packets if needed.

Small example: `DatagramSocket socket = new DatagramSocket(9090);` listens for UDP packets sent directly to port 9090.

## ServerSocket

A `ServerSocket` is a Java class that binds to a local port and blocks waiting for incoming client TCP connections via its `accept()` method.

Why it matters: It is the foundation of any TCP server, acting as a listener that spawns a regular `Socket` object for each client that connects.

Common confusion: `ServerSocket` does not communicate directly with the client. It only accepts the connection and returns a new `Socket` object which is used for the actual communication.

Small example:
```java
ServerSocket server = new ServerSocket(8080);
Socket client = server.accept(); // blocks until client connects
```

## DatagramSocket

`DatagramSocket` is the Java class used to send and receive UDP packets (`DatagramPacket`).

Why it matters: Unlike TCP, a single `DatagramSocket` can receive packets from multiple sources and send packets to multiple destinations without managing sessions.

Common confusion: `DatagramSocket` has a `connect()` method, but this does not establish a connection; it merely restricts the socket to sending and receiving packets to/from a single address.

Small example: `socket.send(new DatagramPacket(buf, buf.length, address, port));` sends a packet to the destination.

## URI

A Uniform Resource Identifier (URI) is a sequence of characters that identifies a logical or physical resource by name or location. It only defines syntax and parsing.

Why it matters: Instantiating a `URI` does not perform network resolution (such as DNS lookups), making it safe, fast, and exception-free for validation and manipulation.

Common confusion: Many think `URI` and `URL` are interchangeable, but a `URI` can be a name (URN) or a locator (URL), and does not require network capability.

Small example: `URI uri = URI.create("mailto:user@example.com");` creates a URI representing an email address syntax.

## HttpClient

`HttpClient` is the modern Java 11 HTTP client API supporting HTTP/1.1, HTTP/2, synchronous/asynchronous request execution, and WebSockets.

Why it matters: It replaces the clunky, blocking `HttpURLConnection` with a modern builder-based API, built-in asynchronous support via `CompletableFuture`, and thread-safe reuse.

Common confusion: Developers often instantiate a new client for every request, which leaks resources. Instead, a single `HttpClient` should be shared.

Small example:
```java
HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder(URI.create("https://api.github.com")).build();
client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
```
