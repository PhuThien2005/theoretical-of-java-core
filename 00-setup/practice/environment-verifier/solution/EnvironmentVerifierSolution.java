/**
 * Reference solution for EnvironmentVerifierSolution.
 * 
 * System properties & Runtime API:
 * - `System.getProperty(key)` queries current JVM configuration parameters.
 * - `Runtime.version()` returns a structured version descriptor of the JVM.
 * - `.feature()` extracts the major Java version (e.g. 11, 17, 21).
 */
public class EnvironmentVerifierSolution {

    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    public static String getJavaVendor() {
        return System.getProperty("java.vendor");
    }

    public static boolean isJavaVersionAtLeast(int majorVersion) {
        // Runtime.version() is available since Java 9
        return Runtime.version().feature() >= majorVersion;
    }
}
