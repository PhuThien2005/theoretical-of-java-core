package jarpackager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class JarPackagerTest {

    public static void main(String[] args) {
        try {
            testPackageJarSuccess();
            testPackageJarNoMainClass();
            testValidationExceptions();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " - Expected: [" + expected + "], Actual: [" + actual + "]");
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testPackageJarSuccess() throws IOException {
        // Create temp folder and source files
        File tempDir = new File(System.getProperty("java.io.tmpdir"), "jar_test_src_" + System.currentTimeMillis());
        assertTrue(tempDir.mkdir(), "Temp directory creation failed");
        tempDir.deleteOnExit();

        File packageDir = new File(tempDir, "com/example");
        assertTrue(packageDir.mkdirs(), "Package directories creation failed");

        File classFile1 = new File(packageDir, "Main.class");
        try (FileWriter w = new FileWriter(classFile1)) {
            w.write("fake bytecode Main");
        }

        File classFile2 = new File(packageDir, "Helper.class");
        try (FileWriter w = new FileWriter(classFile2)) {
            w.write("fake bytecode Helper");
        }

        File outputJar = File.createTempFile("output_test", ".jar");
        outputJar.deleteOnExit();

        // Run JarPackager
        JarPackager.packageJar(tempDir.getAbsolutePath(), outputJar.getAbsolutePath(), "com.example.Main");

        // Verify the JAR and its Manifest
        assertTrue(outputJar.exists(), "Output JAR file was not created");
        
        try (JarFile jar = new JarFile(outputJar)) {
            Manifest manifest = jar.getManifest();
            assertTrue(manifest != null, "Manifest should exist in JAR");
            assertEquals("1.0", manifest.getMainAttributes().getValue(Attributes.Name.MANIFEST_VERSION), "Manifest Version mismatch");
            assertEquals("com.example.Main", manifest.getMainAttributes().getValue(Attributes.Name.MAIN_CLASS), "Main Class mismatch");

            // Verify entries are packed correctly
            assertTrue(jar.getJarEntry("com/example/Main.class") != null, "Main.class missing from JAR");
            assertTrue(jar.getJarEntry("com/example/Helper.class") != null, "Helper.class missing from JAR");
        }

        // Clean up files recursively
        classFile1.delete();
        classFile2.delete();
        packageDir.delete();
        new File(tempDir, "com").delete();
        tempDir.delete();
    }

    private static void testPackageJarNoMainClass() throws IOException {
        File tempDir = new File(System.getProperty("java.io.tmpdir"), "jar_test_src2_" + System.currentTimeMillis());
        assertTrue(tempDir.mkdir(), "Temp directory creation failed");
        tempDir.deleteOnExit();

        File classFile = new File(tempDir, "Simple.class");
        try (FileWriter w = new FileWriter(classFile)) {
            w.write("fake Simple class");
        }

        File outputJar = File.createTempFile("output_test2", ".jar");
        outputJar.deleteOnExit();

        // Package with null/empty mainClassName
        JarPackager.packageJar(tempDir.getAbsolutePath(), outputJar.getAbsolutePath(), null);

        try (JarFile jar = new JarFile(outputJar)) {
            Manifest manifest = jar.getManifest();
            assertTrue(manifest != null, "Manifest should exist");
            assertEquals(null, manifest.getMainAttributes().getValue(Attributes.Name.MAIN_CLASS), "Main Class should not be present in non-executable jar");
            assertTrue(jar.getJarEntry("Simple.class") != null, "Simple.class missing from JAR");
        }

        classFile.delete();
        tempDir.delete();
    }

    private static void testValidationExceptions() {
        try {
            JarPackager.packageJar("nonexistent_dir_path_12345", "out.jar", null);
            throw new AssertionError("Expected IllegalArgumentException for non-existent source directory");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (IOException e) {
            throw new AssertionError("Expected IllegalArgumentException, but got IOException", e);
        }
    }
}
