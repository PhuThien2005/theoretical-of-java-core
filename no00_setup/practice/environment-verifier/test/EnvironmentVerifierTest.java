package no00_setup.practice.environment_verifier;

/**
 * Test runner for EnvironmentVerifier.
 */
public class EnvironmentVerifierTest {

    public static void main(String[] args) {
        try {
            testSystemProperties();
            testJavaVersionComparisons();
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

    private static void testSystemProperties() {
        String version = EnvironmentVerifier.getJavaVersion();
        String vendor = EnvironmentVerifier.getJavaVendor();

        assertTrue(version != null && !version.isEmpty(), "Java version must be retrieved");
        assertTrue(vendor != null && !vendor.isEmpty(), "Java vendor must be retrieved");
    }

    private static void testJavaVersionComparisons() {
        // Since we are running at least Java 11 or 17, check major version constraints
        assertTrue(EnvironmentVerifier.isJavaVersionAtLeast(8), "JVM must be at least Java 8");
        assertTrue(EnvironmentVerifier.isJavaVersionAtLeast(11), "JVM must be at least Java 11");

        // Java 99 should return false unless running on far future JDK
        assertTrue(!EnvironmentVerifier.isJavaVersionAtLeast(99), "JVM version check should be false for Java 99");
    }
}
