import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * Test runner for CustomHotClassLoader.
 */
public class CustomHotClassLoaderTest {

    public static void main(String[] args) {
        Path tempDir = null;
        try {
            tempDir = Files.createTempDirectory("hot_load_test");
            testHotReloading(tempDir.toFile());
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

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: [" + expected + "], Actual: [" + actual + "])");
        }
    }

    private static void testHotReloading(File classDir) throws Exception {
        // 1. Write and compile HelloPlugin Version 1
        writeSourceFile(classDir, "Version 1");
        compileJavaFile(classDir);

        // Load Version 1
        CustomHotClassLoader loader1 = new CustomHotClassLoader(classDir.getPath());
        Class<?> clazz1 = loader1.loadClass("HelloPlugin");
        Object inst1 = clazz1.getDeclaredConstructor().newInstance();
        String msg1 = (String) clazz1.getMethod("getMessage").invoke(inst1);
        assertEquals("Hello Version 1", msg1, "First version loads successfully");

        // 2. Write and compile HelloPlugin Version 2 (same class name, different message)
        writeSourceFile(classDir, "Version 2");
        compileJavaFile(classDir);

        // Load Version 2 (must use a NEW classloader instance!)
        CustomHotClassLoader loader2 = new CustomHotClassLoader(classDir.getPath());
        Class<?> clazz2 = loader2.loadClass("HelloPlugin");
        Object inst2 = clazz2.getDeclaredConstructor().newInstance();
        String msg2 = (String) clazz2.getMethod("getMessage").invoke(inst2);
        
        // Assert hot reload was successful
        assertEquals("Hello Version 2", msg2, "Second version reloads successfully");
    }

    private static void writeSourceFile(File dir, String versionLabel) throws IOException {
        File sourceFile = new File(dir, "HelloPlugin.java");
        try (PrintWriter writer = new PrintWriter(new FileWriter(sourceFile))) {
            writer.println("public class HelloPlugin {");
            writer.println("    public String getMessage() {");
            writer.println("        return \"Hello " + versionLabel + "\";");
            writer.println("    }");
            writer.println("}");
        }
    }

    private static void compileJavaFile(File dir) {
        File sourceFile = new File(dir, "HelloPlugin.java");
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("System Java Compiler is not available. Please run tests under a full JDK, not a JRE.");
        }
        int result = compiler.run(null, null, null, sourceFile.getPath());
        if (result != 0) {
            throw new RuntimeException("Compilation failed for " + sourceFile.getPath());
        }
    }

    private static void deleteDirectoryRecursively(Path path) {
        try {
            Files.walkFileTree(path, new java.nio.file.SimpleFileVisitor<Path>() {
                @Override
                public java.nio.file.FileVisitResult visitFile(Path file, java.nio.file.attribute.BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return java.nio.file.FileVisitResult.CONTINUE;
                }

                @Override
                public java.nio.file.FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return java.nio.file.FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            // Ignore
        }
    }
}
