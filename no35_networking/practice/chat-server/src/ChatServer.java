package no35_networking.practice.chat_server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Starter template for a multi-threaded TCP Echo Server.
 */
public class ChatServer {

    private ServerSocket serverSocket;
    private final int port;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private boolean running = false;

    public ChatServer(int port) {
        this.port = port;
    }

    /**
     * Starts the TCP Echo server and begins listening for client connections.
     */
    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;
        
        // Start main listener loop in a background thread
        threadPool.submit(() -> {
            while (running && !serverSocket.isClosed()) {
                try {
                    // TODO: Accept incoming client socket connection (serverSocket.accept())
                    // TODO: Spawn a client handler task in the threadPool to read and write messages
                    if (false) {
                        serverSocket.accept(); // Keep compiler happy for IOException catch
                    }
                } catch (IOException e) {
                    // Closed
                }
            }
        });
    }

    /**
     * Returns the port the server is listening on.
     */
    public int getPort() {
        return serverSocket != null ? serverSocket.getLocalPort() : port;
    }

    /**
     * Stops the server and releases all network sockets.
     */
    public void stop() throws IOException {
        running = false;
        if (serverSocket != null) {
            serverSocket.close();
        }
        threadPool.shutdownNow();
    }
}

/**
 * Tasks to handle communicating with a single client.
 */
class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // TODO: Open BufferedReader (for input) and PrintWriter (for output)
        // TODO: Read lines from client input.
        // TODO: For each line read, write back "Echo: " + message (remember to flush!)
        // TODO: Close socket safely in a finally block
    }
}
