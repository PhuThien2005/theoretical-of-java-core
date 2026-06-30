import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Reference solution for ChatServerSolution.
 * 
 * Networking with Sockets:
 * - `ServerSocket(port)` listens for TCP connection requests.
 * - `.accept()` blocks until a client connects.
 * - Spawning threads or using an `ExecutorService` thread pool allows handling multiple connections concurrently.
 * - Streams (`InputStreamReader`, `PrintWriter`) provide byte-to-text translations.
 */
public class ChatServerSolution {

    private ServerSocket serverSocket;
    private final int port;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private volatile boolean running = false;

    public ChatServerSolution(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;

        threadPool.submit(() -> {
            while (running && !serverSocket.isClosed()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    // Delegate client communication to thread pool
                    threadPool.submit(new ClientHandler(clientSocket));
                } catch (IOException e) {
                    // Closed
                }
            }
        });
    }

    public int getPort() {
        return serverSocket != null ? serverSocket.getLocalPort() : port;
    }

    public void stop() throws IOException {
        running = false;
        if (serverSocket != null) {
            serverSocket.close();
        }
        threadPool.shutdownNow();
    }
}

class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) { // Autoflush enabled

            String line;
            while ((line = reader.readLine()) != null) {
                writer.println("Echo: " + line);
            }
        } catch (IOException e) {
            // Client disconnected or connection error
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }
}
