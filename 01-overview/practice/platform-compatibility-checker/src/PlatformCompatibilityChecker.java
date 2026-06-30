/**
 * Starter template for simulating/measuring JIT speed optimizations.
 */
public class PlatformCompatibilityChecker {

    /**
     * Executes a math calculation routine.
     * Computes a sum of square roots over the specified iterations loop.
     */
    public static long runCalculation(int iterations) {
        // TODO: Implement calculation loop summing Math.sqrt(i) casted to long
        return 0;
    }

    /**
     * Measures duration of an early run, runs a warmup loop to trigger JIT optimization,
     * then measures duration of a subsequent run.
     * Returns the speedup factor: (durationOfFirstRun / durationOfSecondRun).
     */
    public static double checkJitSpeedup() {
        // TODO: Implement measurement and JIT warmup loops
        return 0.0;
    }
}
