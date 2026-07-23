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
| `InetAddress` |`InetAddress` — InetAddress provides specific functionality and rules in Java development. |
| `URL` |`URL` — URL provides specific functionality and rules in Java development. |
| `URI` |`URI` — URI provides specific functionality and rules in Java development. |
| `Basic HTTP request` |`Basic HTTP request` — Basic HTTP request provides specific functionality and rules in Java development. |

## Detailed Notes

### Socket programming

A socket is an endpoint for network communication.

### TCP socket

A socket is an endpoint for network communication.

### UDP socket

A socket is an endpoint for network communication.

## Why TCP Handshakes Differ From Connectionless UDP

Transmission Control Protocol (TCP) is a connection-oriented, reliable transport protocol that guarantees ordered and error-checked delivery of byte streams. To achieve this, TCP requires a formal connection establishment phase (the three-way handshake: SYN, SYN-ACK, ACK) between the client `Socket` and server `ServerSocket` to synchronize sequence numbers and allocate resources before any data transfer can begin. In contrast, User Datagram Protocol (UDP) is a connectionless, lightweight protocol that transmits independent packets (`DatagramPacket` via `DatagramSocket`) without establishing a session. UDP does not track whether packets arrive, does not retransmit lost packets, and does not enforce packet ordering. This lack of handshake and delivery tracking overhead makes UDP significantly faster and lower in latency, making it ideal for real-time video streaming or gaming, whereas TCP is required for applications that mandate absolute data integrity, such as HTTP or database connections.

### Mental Model: TCP Connection vs. UDP Mailing
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
        Note over D,E: No handshake, no confirmation, packets can arrive out of order or be lost
    end
```

### Code Example: TCP Connection vs. UDP Direct Receive
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

### Cause-Effect Chain
TCP socket connection requested &rarr; Client and server execute 3-way handshake &rarr; OS allocates packet tracking buffers and sequence numbers &rarr; Reliable, ordered byte stream established &rarr; Network packet drop occurs &rarr; OS detects missing ACK, retransmits packet &rarr; Zero data loss achieved.

---

A socket is an endpoint for network communication.

### ServerSocket

A socket is an endpoint for network communication.

## Why Blocking Socket Operations Must Not Run on the Main Thread

Network operations in Java's Classic I/O (sockets) are blocking by nature. Methods such as `ServerSocket.accept()`, `InputStream.read()`, and `OutputStream.write()` suspend thread execution until a connection is received, network data arrives, or buffers are flushed. If these operations are run directly on the application's main thread (e.g. user interface thread or main service thread), the entire application freezes and becomes unresponsive to user inputs or lifecycle events while waiting for network packets. To maintain application responsiveness and handle concurrent clients, socket operations must be offloaded to separate worker threads or managed thread pools. In a standard multi-threaded server model, the main thread runs a loop that blocks on `accept()`, and upon receiving a connection, immediately hands the client `Socket` off to a new thread or executor task, freeing the main thread to block on the next incoming connection.

### Mental Model: Multi-threaded Connection Acceptance
```
Main Thread Loop
      |
      v
serverSocket.accept() (Blocks thread until client connects)
      |
      +---> Connection Received!
      |
      +---> Spawn Client Handler Thread (Worker Thread)
      |         |
      |         +---> Handles client socket read/write (Blocks worker thread only)
      |
      v
Loop repeats: serverSocket.accept() (Main thread immediately ready to accept next client)
```

### Code Example: Multi-threaded Echo Server
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

### Cause-Effect Chain
Socket accept/read runs on main thread &rarr; Thread execution suspended waiting for network response &rarr; Application freezes, rejecting user interaction and UI events &rarr; Connection handling offloaded to Executor thread pool &rarr; Worker threads block waiting for client data &rarr; Main thread remains free to accept new incoming connections, enabling high concurrency.

---

## Why Sockets and Streams Must Be Closed Properly

Every open network `Socket` and `ServerSocket` allocates a corresponding file descriptor in the host operating system's kernel to manage the underlying TCP/IP socket buffer. The operating system places a strict limit on the number of file descriptors a single process can open. If an application fails to close sockets and their associated input/output streams when a connection terminates, these file descriptors remain open in the kernel, leading to a file descriptor leak. Over time, the application will exhaust its file descriptor limit, causing the OS to reject any subsequent socket connections and throw `java.net.SocketException: Too many open files`. Furthermore, failing to close a server socket prevents the socket from releasing its bound port, leading to `java.net.BindException: Address already in use` when the application attempts to restart. Utilizing try-with-resources ensures that sockets are automatically closed and resources released immediately back to the OS when the block exits.

### Mental Model: File Descriptor Accumulation in Kernel
```
JVM Application Socket
      | (Opens connection)
      v
OS Kernel allocates File Descriptor (FD) & Port Binding (e.g. Port 8080)
      |
      +---> Session finishes, Socket NOT closed!
      |
Kernel retains FD allocation and Port 8080 remains locked in CLOSE_WAIT state.
      |
Attempting to restart app -> BindException: Address already in use.
Attempting to open more connections -> SocketException: Too many open files.
```

### Code Example: Secure Connection Closure
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

### Cause-Effect Chain
Network connection terminates without socket closure &rarr; OS kernel retains file descriptor allocation and port binding in active table &rarr; File descriptor count increases continuously &rarr; Process reaches maximum OS limit &rarr; JVM throws `SocketException: Too many open files` on subsequent network requests &rarr; Port cannot be bound again on restart, throwing `BindException`.

---

### DatagramSocket

A socket is an endpoint for network communication.

### InetAddress

### URL

### URI

## Why Java 20 Deprecated URL Constructors in Favor of URI

A Uniform Resource Identifier (URI) is a purely syntax-based string representation that identifies a resource, whereas a Uniform Resource Locator (URL) provides the specific network location and protocol to locate and access the resource. Prior to Java 20, developers commonly instantiated URLs using constructors like `new URL("https://example.com")`. However, this was deprecated because the `URL` constructor attempts to resolve the host name via DNS during construction and comparison operations (such as `url.equals(otherUrl)` or `url.hashCode()`), which executes blocking network lookups. Running a DNS lookup inside a simple constructor or collection check violates basic API design principles, makes hash maps using URLs extremely slow, and can crash applications if DNS resolution fails or times out. In contrast, the `URI` class performs strict RFC 2396 syntax-based parsing without resolving hosts or executing network calls, making it completely safe and predictable to instantiate; developers should construct a `URI` first and then convert it to a `URL` using `uri.toURL()` only when a physical connection is actually required.

### Mental Model: Syntax-only URI vs Network-dependent URL
```
Using URL Constructor (Unsafe):
new URL("https://example.com") ---> Calls DNS Lookup (Blocks network!) ---> Can fail during instantiation

Using URI and converting to URL (Safe):
URI.create("https://example.com") ---> Strict Syntax Parsing only (Fast, Local)
      |
      v
uri.toURL() ---> URL object created only when network access is explicitly needed
```

### Code Example: Syntax checking with URI
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

### Cause-Effect Chain
`new URL(string)` constructor invoked &rarr; Constructor triggers underlying network DNS lookup to resolve hostname &rarr; DNS server timeouts or failures propagate as exceptions during object instantiation &rarr; Thread blocked during object instantiation &rarr; `URI.create(string)` used instead &rarr; String parsed using RFC 2396 grammar locally &rarr; Instant, exception-free URI creation achieved.

---

### Basic HTTP request

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

## Reference Links

- https://docs.oracle.com/javase/tutorial/networking/sockets/index.html (Sockets Programming Oracle Tutorial)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/net/Socket.html (Socket JavaDoc)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/net/URI.html (URI JavaDoc)
- https://openjdk.org/jeps/321 (HTTP Client JEP)

