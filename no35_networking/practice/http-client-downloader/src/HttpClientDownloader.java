package no35_networking.practice.http_client_downloader;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Starter template for a modern HTTP downloader.
 */
public class HttpClientDownloader {

    /**
     * Downloads content from the specified URL string using HttpClient.
     * 
     * Requirements:
     * - Create a URI from the urlStr.
     * - Build a GET HttpRequest.
     * - Send the request using the client parameter and BodyHandlers.ofString().
     * - Return the response body string.
     *
     * @param client the HttpClient instance
     * @param urlStr the target URL to download
     * @return the response content body
     * @throws Exception on network or parsing errors
     */
    public static String downloadContent(HttpClient client, String urlStr) throws Exception {
        // TODO: Implement modern HttpClient GET request
        return null;
    }
}
