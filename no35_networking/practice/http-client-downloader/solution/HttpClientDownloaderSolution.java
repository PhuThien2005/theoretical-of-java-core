package no35_networking.practice.http_client_downloader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Reference solution for HttpClientDownloaderSolution.
 * 
 * HttpClient APIs:
 * - `HttpClient` represents the execution engine.
 * - `HttpRequest` wraps the query parameters, method (GET, POST), headers, and target URI.
 * - `BodyHandlers.ofString()` converts bytes of the raw HTTP response stream into a String automatically.
 */
public class HttpClientDownloaderSolution {

    public static String downloadContent(HttpClient client, String urlStr) throws Exception {
        if (client == null || urlStr == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        // Build GET request
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(urlStr))
            .GET()
            .build();

        // Send request synchronously and retrieve response body
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
