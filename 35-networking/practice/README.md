# Practice Exercises: Networking

This folder contains hands-on practice exercises to reinforce your understanding of Java TCP Socket programming and modern HTTP Client APIs introduced in Java 11.

## Exercises

### 1. Multi-Client Chat Server (`chat-server`)
TCP Socket programming allows raw byte communications between servers and clients. A server can support multiple concurrent clients by spawning a background handler thread for each incoming connection.
- **Goal**: Implement a multi-threaded `ChatServer` that listens on an available port, accepts multiple client connections in a loop, and echoes client messages back concurrently.

#### Directory Structure
- [ChatServer.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/35-networking/practice/chat-server/src/ChatServer.java)
- [ChatServerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/35-networking/practice/chat-server/test/ChatServerTest.java)
- [ChatServer.java (Solution)](file:///home/fhu_thjen/projects/learning-java/35-networking/practice/chat-server/solution/ChatServer.java)

---

### 2. HTTP Client Downloader (`http-client-downloader`)
Java 11 introduces a modern `java.net.http.HttpClient` supporting HTTP/2, asynchronous requests, and clean reactive request/response handlers.
- **Goal**: Implement a file content downloader in `HttpClientDownloader` that sends a GET request to a URL and returns the response body using `HttpClient` and `HttpRequest` APIs.

#### Directory Structure
- [HttpClientDownloader.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/35-networking/practice/http-client-downloader/src/HttpClientDownloader.java)
- [HttpClientDownloaderTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/35-networking/practice/http-client-downloader/test/HttpClientDownloaderTest.java)
- [HttpClientDownloader.java (Solution)](file:///home/fhu_thjen/projects/learning-java/35-networking/practice/http-client-downloader/solution/HttpClientDownloader.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 35-networking
```
