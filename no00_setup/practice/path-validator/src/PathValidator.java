package no00_setup.practice.path_validator;

import java.util.List;

/**
 * Starter template for a system PATH environment variable validator.
 */
public class PathValidator {

    /**
     * Retrieves and parses the system "PATH" environment variable.
     * Splits entries using the system-dependent path separator.
     * Return an empty list if PATH is missing/null.
     */
    public static List<String> getPathEntries() {
        // TODO: Get System.getenv("PATH"), split by System.getProperty("path.separator")
        return null;
    }

    /**
     * Checks if any entry in the parsed PATH directories contains the search keyword.
     * Ignore case when checking.
     */
    public static boolean containsDirectory(String searchKeyword) {
        // TODO: Scan path entries for searchKeyword (case-insensitive)
        return false;
    }
}
