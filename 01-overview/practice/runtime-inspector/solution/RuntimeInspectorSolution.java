/**
 * Reference solution for RuntimeInspectorSolution.
 * 
 * JVM Memory structure:
 * - Max Memory (`maxMemory()`): Limit configured by `-Xmx` (defaults to fraction of system RAM).
 * - Total Memory (`totalMemory()`): Current size allocated to JVM heap (grows as needed up to Max).
 * - Free Memory (`freeMemory()`): Allocated space that is currently unused.
 * - Used Memory = Total Memory - Free Memory.
 */
public class RuntimeInspectorSolution {

    public static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    public static long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    public static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
