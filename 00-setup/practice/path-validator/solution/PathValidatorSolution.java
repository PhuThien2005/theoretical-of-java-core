import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Reference solution for PathValidatorSolution.
 * 
 * Environment Variables & Separators:
 * - `System.getenv("PATH")` fetches the system path environment variable.
 * - `System.getProperty("path.separator")` fetches the OS-dependent character separating entries
 *   (e.g., `:` on Unix/macOS, `;` on Windows).
 */
public class PathValidatorSolution {

    public static List<String> getPathEntries() {
        String path = System.getenv("PATH");
        if (path == null || path.isEmpty()) {
            return Collections.emptyList();
        }

        String separator = System.getProperty("path.separator");
        // Split path entries using the OS path separator
        String[] entries = path.split(PatternQuoteHelper.quote(separator));
        return Arrays.asList(entries);
    }

    public static boolean containsDirectory(String searchKeyword) {
        if (searchKeyword == null || searchKeyword.isEmpty()) {
            return false;
        }

        List<String> entries = getPathEntries();
        String lowerKeyword = searchKeyword.toLowerCase();
        for (String entry : entries) {
            if (entry != null && entry.toLowerCase().contains(lowerKeyword)) {
                return true;
            }
        }
        return false;
    }
}

/**
 * A helper class to safely escape split separators.
 */
class PatternQuoteHelper {
    public static String quote(String s) {
        return java.util.regex.Pattern.quote(s);
    }
}
