import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;

/**
 * Test runner for HttpClientDownloader using a local lightweight HttpServer.
 */
public class HttpClientDownloaderTest {

    public static void main(String[] args) {
        HttpServer server = null;
        try {
            // Start local HTTP server on random available port
            server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
            server.createContext("/download", exchange -> {
                byte[] response = "Success! Loaded content via Java 11 HttpClient.".getBytes();
                exchange.sendResponseHeaders(200, response.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response);
                }
            });
            server.start();

            int port = server.getAddress().getPort();
            testHttpDownload(port);
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        } finally {
            if (server != null) {
                server.stop(0);
            }
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: [" + expected + "], Actual: [" + actual + "])");
        }
    }

    private static void testHttpDownload(int port) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String url = "http://localhost:" + port + "/download";

        String result = HttpClientDownloader.downloadContent(client, url);

        assertEquals("Success! Loaded content via Java 11 HttpClient.", result, "Verify downloaded payload matches");
    }
}
