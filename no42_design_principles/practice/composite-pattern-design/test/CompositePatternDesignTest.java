package compositepatterndesign;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CompositePatternDesignTest {

    public static void main(String[] args) {
        try {
            testLeafSize();
            testCompositeSize();
            testPrintOutput();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(long expected, long actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " - Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void assertEquals(String expected, String actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + "\nExpected:\n" + expected + "\nActual:\n" + actual);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testLeafSize() {
        var file = new CompositePatternDesign.File("doc.txt", 1024L);
        assertEquals("doc.txt", file.getName(), "File name mismatch");
        assertEquals(1024L, file.getSize(), "File size mismatch");
    }

    private static void testCompositeSize() {
        var root = new CompositePatternDesign.Directory("root");
        var subDir = new CompositePatternDesign.Directory("sub");
        var file1 = new CompositePatternDesign.File("file1.bin", 500L);
        var file2 = new CompositePatternDesign.File("file2.bin", 300L);
        var file3 = new CompositePatternDesign.File("file3.bin", 200L);

        subDir.add(file2);
        subDir.add(file3); // sub contains file2 + file3 = 500L

        root.add(file1);
        root.add(subDir);  // root contains file1 + subDir = 500L + 500L = 1000L

        assertEquals(500L, subDir.getSize(), "Subdirectory size calculation mismatch");
        assertEquals(1000L, root.getSize(), "Root directory size calculation mismatch");

        // Remove a file and check size
        subDir.remove(file3);
        assertEquals(300L, subDir.getSize(), "Subdirectory size mismatch after child removal");
        assertEquals(800L, root.getSize(), "Root directory size mismatch after nested child removal");
    }

    private static void testPrintOutput() {
        var root = new CompositePatternDesign.Directory("root");
        var subDir = new CompositePatternDesign.Directory("bin");
        var file1 = new CompositePatternDesign.File("notes.md", 150L);
        var file2 = new CompositePatternDesign.File("run.sh", 45L);

        root.add(file1);
        subDir.add(file2);
        root.add(subDir);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));
        try {
            root.print("");
        } finally {
            System.setOut(originalOut);
        }

        String output = out.toString().replace("\r\n", "\n");
        String expected = "+ Directory: root\n" +
                          "  - File: notes.md (150 bytes)\n" +
                          "  + Directory: bin\n" +
                          "    - File: run.sh (45 bytes)\n";

        assertEquals(expected, output, "Recursively printed tree output mismatch");
    }
}
