import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Test runner for ChatServer.
 */
public class ChatServerTest {

    public static void main(String[] args) {
        ChatServer server = null;
        try {
            // Port 0 dynamically assigns an available port
            server = new ChatServer(0);
            server.start();

            // Give server a brief moment to bind and start
            Thread.sleep(100);

            testEchoCommunication(server.getPort());
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        } finally {
            if (server != null) {
                try {
                    server.stop();
                } catch (Exception e) {
                    // Ignore
                }
            }
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: [" + expected + "], Actual: [" + actual + "])");
        }
    }

    private static void testEchoCommunication(int port) throws Exception {
        // Connect Client 1
        try (Socket s1 = new Socket("localhost", port)) {
            s1.setSoTimeout(1000); // Prevent hanging if server is unresponsive
            try (PrintWriter w1 = new PrintWriter(s1.getOutputStream(), true);
                 BufferedReader r1 = new BufferedReader(new InputStreamReader(s1.getInputStream()))) {

                w1.println("Hello Echo Server");
                String response1 = r1.readLine();
                assertEquals("Echo: Hello Echo Server", response1, "Client 1 Echo message");

                // Connect Client 2 concurrently
                try (Socket s2 = new Socket("localhost", port)) {
                    s2.setSoTimeout(1000); // Prevent hanging if server is unresponsive
                    try (PrintWriter w2 = new PrintWriter(s2.getOutputStream(), true);
                         BufferedReader r2 = new BufferedReader(new InputStreamReader(s2.getInputStream()))) {

                        w2.println("Hello Multi-client Connection");
                        String response2 = r2.readLine();
                        assertEquals("Echo: Hello Multi-client Connection", response2, "Client 2 Echo message");
                    }
                }
            }
        }
    }
}
