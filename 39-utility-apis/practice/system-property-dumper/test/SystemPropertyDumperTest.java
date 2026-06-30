package systempropertydumper;

import java.util.Locale;

public class SystemPropertyDumperTest {

    public static void main(String[] args) {
        try {
            testLocalizedSystemInfoUS();
            testLocalizedSystemInfoFrance();
            testSystemPropertyOrFallback();
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

    private static void testLocalizedSystemInfoUS() {
        // Timestamp for Sep 13, 2020 (UTC/GMT)
        // 1600000000000L
        long timestamp = 1600000000000L;
        double usage = 0.73;
        
        String report = SystemPropertyDumper.getLocalizedSystemInfo(Locale.US, timestamp, usage);
        
        // Assert date parts and percentage
        assertTrue(report.startsWith("On "), "Report should start with 'On '");
        assertTrue(report.contains("2020"), "Report should contain year '2020'");
        assertTrue(report.contains("73%"), "Report should contain rounded percentage '73%' for US locale");
        assertTrue(report.endsWith("system usage was 73%."), "Report should end with 'system usage was 73%.'");
    }

    private static void testLocalizedSystemInfoFrance() {
        long timestamp = 1600000000000L;
        double usage = 0.73;

        String report = SystemPropertyDumper.getLocalizedSystemInfo(Locale.FRANCE, timestamp, usage);

        assertTrue(report.startsWith("On "), "Report should start with 'On '");
        assertTrue(report.contains("2020"), "Report should contain year '2020'");
        // French locale percent formatting uses non-breaking space (or standard space depending on JDK version): "73 %" or "73\u00A0%"
        assertTrue(report.contains("73") && report.contains("%"), "Report should contain '73' and '%'");
    }

    private static void testSystemPropertyOrFallback() {
        // Test standard existing property
        String javaVersion = System.getProperty("java.version");
        String result = SystemPropertyDumper.getSystemPropertyOrFallback("java.version", "fallback");
        assertEquals(javaVersion, result, "Should return existing system property");

        // Test non-existing property
        String fallbackResult = SystemPropertyDumper.getSystemPropertyOrFallback("nonexistent.property.xyz123", "fallback");
        assertEquals("fallback", fallbackResult, "Should return fallback for non-existing property");
    }

    private static void testValidationExceptions() {
        try {
            SystemPropertyDumper.getLocalizedSystemInfo(null, 1000L, 0.5);
            throw new AssertionError("Expected IllegalArgumentException for null locale");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            SystemPropertyDumper.getSystemPropertyOrFallback(null, "fallback");
            throw new AssertionError("Expected IllegalArgumentException for null property name");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
