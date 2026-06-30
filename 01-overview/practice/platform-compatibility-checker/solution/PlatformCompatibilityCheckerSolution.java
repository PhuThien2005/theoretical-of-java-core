/**
 * Reference solution for PlatformCompatibilityCheckerSolution.
 * 
 * JIT Compilation:
 * - Initially, Java bytecode runs interpreted.
 * - If code is executed frequently (hot code), JVM compiles it directly to native machine code.
 * - Warmup loop triggers compilation threshold.
 * - Native compiled code is significantly faster than interpreted bytecode.
 */
public class PlatformCompatibilityCheckerSolution {

    public static long runCalculation(int iterations) {
        long sum = 0;
        for (int i = 0; i < iterations; i++) {
            // Math operations to simulate CPU-bound load
            sum += (long) Math.sqrt(i);
        }
        return sum;
    }

    public static double checkJitSpeedup() {
        int iterations = 100_000;

        // Measure run 1 (cold start, interpreted/profiled compilation)
        long start1 = System.nanoTime();
        runCalculation(iterations);
        long duration1 = System.nanoTime() - start1;

        // Warmup loop: trigger JIT (HotSpot compile threshold is ~10,000 runs or loop backedge counts)
        for (int i = 0; i < 500; i++) {
            runCalculation(iterations);
        }

        // Measure run 2 (hot run, JIT optimized compilation)
        long start2 = System.nanoTime();
        runCalculation(iterations);
        long duration2 = System.nanoTime() - start2;

        // Prevent Division by Zero if system clock resolution is low
        if (duration2 == 0) {
            duration2 = 1;
        }

        // Speedup factor: larger than 1 means run 2 was faster
        return (double) duration1 / duration2;
    }
}
