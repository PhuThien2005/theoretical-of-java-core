import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reference solution for FileTreeDuplicateFinderSolution.
 * 
 * NIO.2 Directory Traversal:
 * - `Files.walkFileTree` performs a depth-first traversal of a directory.
 * - `SimpleFileVisitor` exposes override hooks like `visitFile` and `preVisitDirectory`.
 */
public class FileTreeDuplicateFinderSolution {

    public static Map<String, List<Path>> findDuplicates(Path startDir) throws IOException {
        Map<String, List<Path>> contentMap = new HashMap<>();

        if (startDir == null || !Files.exists(startDir)) {
            return contentMap;
        }

        // Traverse files recursively
        Files.walkFileTree(startDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (attrs.isRegularFile()) {
                    try {
                        // Read file contents as string
                        byte[] bytes = Files.readAllBytes(file);
                        String content = new String(bytes, StandardCharsets.UTF_8);

                        contentMap.computeIfAbsent(content, k -> new ArrayList<>()).add(file);
                    } catch (IOException e) {
                        // Skip file if unreadable
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });

        // Filter and retain only entries containing duplicates (list size > 1)
        Map<String, List<Path>> duplicates = new HashMap<>();
        for (Map.Entry<String, List<Path>> entry : contentMap.entrySet()) {
            if (entry.getValue().size() > 1) {
                duplicates.put(entry.getKey(), entry.getValue());
            }
        }

        return duplicates;
    }
}
