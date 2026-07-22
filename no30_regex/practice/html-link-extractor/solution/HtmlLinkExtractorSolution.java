package no30_regex.practice.html_link_extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reference solution for HtmlLinkExtractorSolution.
 * 
 * Case-Insensitive Matching:
 * - `Pattern.CASE_INSENSITIVE` compiles the pattern to accept `<a href` and `<A HREF` similarly.
 * - `Matcher.find()` loops to extract multiple matches from a single text block.
 */
public class HtmlLinkExtractorSolution {

    // Regex pattern:
    // <a                 -> matches anchor start
    // \s+                -> at least one space
    // [^>]*              -> zero or more characters except closing brace '>' (allows attributes like class, id before href)
    // href=\"([^\"]+)\"  -> group 1: matches href attribute value
    private static final Pattern LINK_PATTERN = Pattern.compile("<a\\s+[^>]*href=\"([^\"]+)\"", Pattern.CASE_INSENSITIVE);

    public static List<String> extractLinks(String html) {
        List<String> links = new ArrayList<>();
        if (html == null || html.isEmpty()) {
            return links;
        }

        Matcher matcher = LINK_PATTERN.matcher(html);
        while (matcher.find()) {
            // Add extracted capture group 1 (the URL)
            links.add(matcher.group(1));
        }

        return links;
    }
}
