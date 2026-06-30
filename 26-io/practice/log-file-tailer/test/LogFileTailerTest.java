import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Test runner for LogFileTailer.
 */
public class LogFileTailerTest {

    public static void main(String[] args) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("test_log", ".log");
            testTailerAppends(tempFile);
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: [" + expected + "], Actual: [" + actual + "])");
        }
    }

    private static void testTailerAppends(File file) throws IOException {
        LogFileTailer tailer = new LogFileTailer();

        // 1. Initial write
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.println("Line 1");
            writer.println("Line 2");
        }

        List<String> firstTail = tailer.readNewLines(file);
        assertEquals(2, firstTail.size(), "Should read first 2 lines");
        assertEquals("Line 1", firstTail.get(0), "First line");
        assertEquals("Line 2", firstTail.get(1), "Second line");

        // 2. Read again without modifications -> should be empty
        List<String> secondTail = tailer.readNewLines(file);
        assertEquals(0, secondTail.size(), "Should read 0 new lines if file was not modified");

        // 3. Append new lines
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.println("Line 3");
            writer.println("Line 4");
        }

        List<String> thirdTail = tailer.readNewLines(file);
        assertEquals(2, thirdTail.size(), "Should read only the 2 newly appended lines");
        assertEquals("Line 3", thirdTail.get(0), "First appended line");
        assertEquals("Line 4", thirdTail.get(1), "Second appended line");
    }
}
