package no27_nio.practice.nio_fast_file_copy;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Test runner for NioFastFileCopy.
 */
public class NioFastFileCopyTest {

    public static void main(String[] args) {
        File srcFile = null;
        File destFile = null;
        try {
            srcFile = File.createTempFile("src_copy", ".txt");
            destFile = File.createTempFile("dest_copy", ".txt");
            
            // Clean up target file so it starts empty/non-existent
            destFile.delete();

            testFileCopying(srcFile, destFile);
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        } finally {
            if (srcFile != null && srcFile.exists()) {
                srcFile.delete();
            }
            if (destFile != null && destFile.exists()) {
                destFile.delete();
            }
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: [" + expected + "], Actual: [" + actual + "])");
        }
    }

    private static void testFileCopying(File source, File destination) throws IOException {
        String testData = "NIO Channels and Direct ByteBuffers are extremely fast for I/O operations!";
        Files.write(source.toPath(), testData.getBytes(StandardCharsets.UTF_8));

        // Copy file
        NioFastFileCopy.copyFile(source, destination);

        // Assert file exists
        if (!destination.exists()) {
            throw new AssertionError("Destination file was not created by copyFile");
        }

        // Read and verify destination contents
        byte[] destBytes = Files.readAllBytes(destination.toPath());
        String destContent = new String(destBytes, StandardCharsets.UTF_8);

        assertEquals(testData, destContent, "Copied file content matches original");
    }
}
