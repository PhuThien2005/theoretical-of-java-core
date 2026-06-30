import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Map;

/**
 * Starter template for finding duplicate files using walkFileTree.
 */
public class FileTreeDuplicateFinder {

    /**
     * Recursively traverses startDir to locate files with duplicate string content.
     * 
     * Requirements:
     * - Use Files.walkFileTree() with a custom SimpleFileVisitor.
     * - Group paths by file content (read as UTF-8 String).
     * - Only return entries that have at least one duplicate (list size > 1).
     *
     * @param startDir the directory to scan
     * @return a map of duplicate file contents to lists of file paths
     * @throws IOException on I/O issues
     */
    public static Map<String, List<Path>> findDuplicates(Path startDir) throws IOException {
        // TODO: Implement duplicate finder using walkFileTree
        return null;
    }
}
