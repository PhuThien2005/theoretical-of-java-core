package my.module;

import java.io.InputStream;
import java.lang.module.ModuleDescriptor;
import java.net.URL;

/**
 * Test runner for ModularApp using precise ModuleDescriptor file resolution.
 */
public class ModularAppTest {

    public static void main(String[] args) {
        try {
            testModuleDescriptor();
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

    private static void testModuleDescriptor() throws Exception {
        // Resolve the exact path of our compiled module-info.class relative to ModularApp.class location
        URL classUrl = ModularApp.class.getResource("ModularApp.class");
        assertTrue(classUrl != null, "ModularApp.class must exist");
        
        String classPathStr = classUrl.toString();
        String moduleInfoPath = classPathStr.replace("my/module/ModularApp.class", "module-info.class");
        URL moduleInfoUrl = new URL(moduleInfoPath);

        try (InputStream is = moduleInfoUrl.openStream()) {
            ModuleDescriptor descriptor = ModuleDescriptor.read(is);

            assertEquals("my.module", descriptor.name(), "Module name must be 'my.module'");

            // Verify requires java.logging
            boolean requiresLogging = descriptor.requires().stream()
                .anyMatch(r -> r.name().equals("java.logging"));
            assertTrue(requiresLogging, "module-info must require 'java.logging'");

            // Verify exports my.module
            boolean exportsPackage = descriptor.exports().stream()
                .anyMatch(e -> e.source().equals("my.module"));
            assertTrue(exportsPackage, "module-info must export 'my.module' package");
        }

        // Execute runLogMessage
        ModularApp.runLogMessage("Modular app successfully verified!");
    }
}
