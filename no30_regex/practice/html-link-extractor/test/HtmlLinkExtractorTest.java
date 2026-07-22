package no30_regex.practice.html_link_extractor;

import java.util.Arrays;
import java.util.List;

/**
 * Test runner for HtmlLinkExtractor.
 */
public class HtmlLinkExtractorTest {

    public static void main(String[] args) {
        try {
            testExtractMultipleLinks();
            testCaseInsensitivity();
            testOtherAttributes();
            testNoLinks();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: [" + expected + "], Actual: [" + actual + "])");
        }
    }

    private static void testExtractMultipleLinks() {
        String html = "<a href=\"https://google.com\">Google</a> standard search. Check <a href=\"/docs/index.html\">documentation</a>.";
        List<String> links = HtmlLinkExtractor.extractLinks(html);

        assertEquals(2, links.size(), "Should find 2 links");
        assertEquals(Arrays.asList("https://google.com", "/docs/index.html"), links, "Link URL matching");
    }

    private static void testCaseInsensitivity() {
        String html = "<A HREF=\"https://oracle.com\">Oracle</A>";
        List<String> links = HtmlLinkExtractor.extractLinks(html);

        assertEquals(1, links.size(), "Should find uppercase link");
        assertEquals("https://oracle.com", links.get(0), "Uppercase href value");
    }

    private static void testOtherAttributes() {
        String html = "<a class=\"btn-link\" id=\"my-link\" href=\"/home\" target=\"_blank\">Home</a>";
        List<String> links = HtmlLinkExtractor.extractLinks(html);

        assertEquals(1, links.size(), "Should handle other attributes preceding href");
        assertEquals("/home", links.get(0), "Parsed href value");
    }

    private static void testNoLinks() {
        String html = "<div>No anchor tags here!</div>";
        List<String> links = HtmlLinkExtractor.extractLinks(html);
        assertEquals(0, links.size(), "Empty list when no links present");

        assertEquals(0, HtmlLinkExtractor.extractLinks(null).size(), "Empty list on null HTML input");
    }
}
