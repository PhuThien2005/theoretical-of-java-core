package no37_jvm_advanced.practice.memory_profiler_simulator;

/**
 * Starter template for estimating Java object memory footprint at runtime.
 */
public class MemoryProfilerSimulator {

    /**
     * Estimates the memory footprint of a SampleObject instance in bytes.
     * 
     * Requirements:
     * - Trigger System.gc() to clean memory state.
     * - Record used heap memory before allocation (Runtime.getRuntime().totalMemory() - freeMemory()).
     * - Allocate an array containing numInstances of new SampleObject().
     * - Trigger System.gc() again.
     * - Record used heap memory after allocation.
     * - Return the difference divided by numInstances.
     */
    public static double estimateObjectFootprintBytes(int numInstances) {
        // TODO: Estimate footprint using Runtime memory inspection
        return 0.0;
    }

    /**
     * A sample class representing an object whose memory footprint is measured.
     */
    public static class SampleObject {
        private int intVal = 42;
        private double doubleVal = 3.14159;
        private Object refVal = new Object();
    }
}
