package classdependencyparser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ClassDependencyParserTest {

    public static void main(String[] args) {
        try {
            testParseSelfClassFile();
            testInvalidMagicNumber();
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

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " - Expected: [" + expected + "], Actual: [" + actual + "]");
        }
    }

    private static void testParseSelfClassFile() throws IOException {
        // Locate the compiled .class file of this test runner dynamically
        String resourceName = ClassDependencyParserTest.class.getSimpleName() + ".class";
        URL classUrl = ClassDependencyParserTest.class.getResource(resourceName);
        assertTrue(classUrl != null, "Could not locate compiled .class file for ClassDependencyParserTest");

        String classFilePath = classUrl.getPath();
        
        // Handle URL-encoded paths if any (e.g. %20 for spaces)
        try {
            classFilePath = java.net.URLDecoder.decode(classFilePath, "UTF-8");
        } catch (Exception e) {
            // Ignore and fall back to original path
        }

        List<String> dependencies = ClassDependencyParser.parseDependencies(classFilePath);
        assertTrue(dependencies != null, "Dependencies list should not be null");
        assertTrue(!dependencies.isEmpty(), "Dependencies list should not be empty");

        // Verify key classes are found in the constant pool
        boolean containsObject = false;
        boolean containsSystem = false;
        boolean containsParser = false;

        for (String dep : dependencies) {
            if ("java/lang/Object".equals(dep)) {
                containsObject = true;
            } else if ("java/lang/System".equals(dep)) {
                containsSystem = true;
            } else if ("classdependencyparser/ClassDependencyParser".equals(dep)) {
                containsParser = true;
            }
        }

        assertTrue(containsObject, "Dependencies should contain java/lang/Object");
        assertTrue(containsSystem, "Dependencies should contain java/lang/System");
        assertTrue(containsParser, "Dependencies should contain classdependencyparser/ClassDependencyParser");
    }

    private static void testInvalidMagicNumber() {
        try {
            // Write a dummy file with invalid magic number
            File temp = File.createTempFile("invalid_class", ".class");
            temp.deleteOnExit();
            
            try (java.io.FileOutputStream out = new java.io.FileOutputStream(temp)) {
                out.write(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05});
            }

            ClassDependencyParser.parseDependencies(temp.getAbsolutePath());
            throw new AssertionError("Expected IOException for invalid magic number");
        } catch (IOException e) {
            // Success
            assertTrue(e.getMessage().contains("magic number"), "Exception message should explain magic number mismatch");
        }
    }
}
