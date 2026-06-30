import java.util.List;

/**
 * Test runner for ClassLoaderHierarchyPrinter.
 */
public class ClassLoaderHierarchyPrinterTest {

    public static void main(String[] args) {
        try {
            testSystemClassHierarchy();
            testBootstrapClassHierarchy();
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
            throw new AssertionError(message + " (Expected: [" + expected + "], Actual: [" + actual + "])");
        }
    }

    private static void testSystemClassHierarchy() {
        // A user class is loaded by the System (App) ClassLoader
        List<String> hierarchy = ClassLoaderHierarchyPrinter.getHierarchy(ClassLoaderHierarchyPrinterTest.class);

        assertTrue(hierarchy.size() >= 2, "User class loader hierarchy should have at least AppClassLoader and Bootstrap");
        
        // Assert bottom is System ClassLoader (usually AppClassLoader in standard JVM)
        String appLoaderName = hierarchy.get(0);
        assertTrue(appLoaderName.contains("AppClassLoader") || appLoaderName.contains("SystemClassLoader"), "Leaf must be AppClassLoader");

        // Assert root is Bootstrap ClassLoader
        String rootLoaderName = hierarchy.get(hierarchy.size() - 1);
        assertEquals("Bootstrap ClassLoader", rootLoaderName, "Root must be Bootstrap ClassLoader");
    }

    private static void testBootstrapClassHierarchy() {
        // String class is loaded by the Bootstrap ClassLoader directly
        List<String> hierarchy = ClassLoaderHierarchyPrinter.getHierarchy(String.class);

        assertEquals(1, hierarchy.size(), "Bootstrap class should have exactly 1 level in hierarchy");
        assertEquals("Bootstrap ClassLoader", hierarchy.get(0), "Bootstrap class loaded directly by Bootstrap ClassLoader");
    }
}
