package my.service;

import java.io.InputStream;
import java.lang.module.ModuleDescriptor;
import java.net.URL;
import java.util.List;
import my.service.ServiceLoaderProvider.TranslationService;

/**
 * Test runner for ServiceLoaderProvider using precise ModuleDescriptor file resolution.
 */
public class ServiceLoaderProviderTest {

    public static void main(String[] args) {
        try {
            testModuleServiceDeclarations();
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

    private static void testModuleServiceDeclarations() throws Exception {
        // Resolve the exact path of our compiled module-info.class relative to ServiceLoaderProvider.class location
        URL classUrl = ServiceLoaderProvider.class.getResource("ServiceLoaderProvider.class");
        assertTrue(classUrl != null, "ServiceLoaderProvider.class must exist");

        String classPathStr = classUrl.toString();
        String moduleInfoPath = classPathStr.replace("my/service/ServiceLoaderProvider.class", "module-info.class");
        URL moduleInfoUrl = new URL(moduleInfoPath);

        try (InputStream is = moduleInfoUrl.openStream()) {
            ModuleDescriptor descriptor = ModuleDescriptor.read(is);

            assertEquals("translation.module", descriptor.name(), "Module name must be 'translation.module'");

            // Verify uses my.service.ServiceLoaderProvider.TranslationService
            String serviceName = "my.service.ServiceLoaderProvider$TranslationService";
            assertTrue(descriptor.uses().contains(serviceName), "module-info must declare 'uses " + serviceName + "'");

            // Verify provides my.service.ServiceLoaderProvider.TranslationService with SpanishTranslationService
            String providerName = "my.service.ServiceLoaderProvider$SpanishTranslationService";
            boolean providesCorrectly = descriptor.provides().stream()
                .anyMatch(p -> p.service().equals(serviceName) && p.providers().contains(providerName));
            assertTrue(providesCorrectly, "module-info must declare 'provides " + serviceName + " with " + providerName + "'");
        }
    }
}
