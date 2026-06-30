import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.Map;

/**
 * Test runner for FileTreeDuplicateFinder.
 */
public class FileTreeDuplicateFinderTest {

    public static void main(String[] args) {
        Path tempDir = null;
        try {
            tempDir = Files.createTempDirectory("test_finder_dir");
            testDuplicateFinder(tempDir);
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        } finally {
            if (tempDir != null) {
                deleteDirectoryRecursively(tempDir);
            }
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testDuplicateFinder(Path baseDir) throws IOException {
        // Create folder structure
        Path subDir1 = Files.createDirectory(baseDir.resolve("dir1"));
        Path subDir2 = Files.createDirectory(baseDir.resolve("dir2"));

        // Create duplicate files
        Path fileA = Files.write(subDir1.resolve("fileA.txt"), "hello duplicate".getBytes(StandardCharsets.UTF_8));
        Path fileB = Files.write(subDir2.resolve("fileB.txt"), "hello duplicate".getBytes(StandardCharsets.UTF_8));

        // Create unique file
        Path fileC = Files.write(subDir1.resolve("fileC.txt"), "hello unique".getBytes(StandardCharsets.UTF_8));

        // Find duplicates
        Map<String, List<Path>> dupes = FileTreeDuplicateFinder.findDuplicates(baseDir);

        // Assertions
        assertEquals(1, dupes.size(), "Should find exactly 1 group of duplicates");
        
        List<Path> paths = dupes.get("hello duplicate");
        if (paths == null) {
            throw new AssertionError("Could not find duplicate entry for content 'hello duplicate'");
        }
        assertEquals(2, paths.size(), "Duplicate group should contain 2 paths");
        if (!paths.contains(fileA) || !paths.contains(fileB)) {
            throw new AssertionError("Duplicate paths list should contain fileA and fileB");
        }
        if (paths.contains(fileC)) {
            throw new AssertionError("Duplicate paths list should NOT contain fileC (unique file)");
        }
    }

    private static void deleteDirectoryRecursively(Path path) {
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, java.nio.file.attribute.BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            // Ignore clean up errors
        }
    }
}
