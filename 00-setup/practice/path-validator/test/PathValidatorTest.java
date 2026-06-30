import java.util.List;

/**
 * Test runner for PathValidator.
 */
public class PathValidatorTest {

    public static void main(String[] args) {
        try {
            testPathSplitting();
            testContainsDirMatches();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testPathSplitting() {
        List<String> entries = PathValidator.getPathEntries();
        String rawPath = System.getenv("PATH");

        if (rawPath != null && !rawPath.isEmpty()) {
            assertTrue(entries != null, "Entries list must not be null");
            assertTrue(entries.size() >= 1, "Parsed entries list must have at least 1 folder");
            
            // Verify no entry contains the path separator itself
            String separator = System.getProperty("path.separator");
            for (String entry : entries) {
                assertTrue(!entry.contains(separator), "Path entry should not contain the path separator: " + entry);
            }
        }
    }

    private static void testContainsDirMatches() {
        String rawPath = System.getenv("PATH");
        if (rawPath != null && !rawPath.isEmpty()) {
            // "bin" or "system" or "windows" keyword is almost universally present in development PATHs
            boolean hasBin = PathValidator.containsDirectory("bin");
            boolean hasSystem = PathValidator.containsDirectory("system");
            boolean hasWindows = PathValidator.containsDirectory("windows");
            boolean hasUser = PathValidator.containsDirectory("user");
            boolean hasLocal = PathValidator.containsDirectory("local");

            assertTrue(hasBin || hasSystem || hasWindows || hasUser || hasLocal, 
                "System PATH should match at least one standard keyword (bin/system/windows/user/local)");

            // Non-existent directory search should be false
            assertTrue(!PathValidator.containsDirectory("this_folder_definitely_does_not_exist_in_path"), 
                "Should return false for non-existent path keywords");
        }
    }
}
