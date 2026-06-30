/**
 * Reference solution for MemoryProfilerSimulatorSolution.
 * 
 * Object Memory Footprint elements:
 * - Object Header: 12 bytes (on 64-bit JVMs with compressed OOPs enabled) or 16 bytes.
 * - Fields: primitives take their respective sizes (int=4, double=8), references take 4 or 8 bytes.
 * - Alignment padding: objects are padded to 8-byte boundaries.
 */
public class MemoryProfilerSimulatorSolution {

    public static double estimateObjectFootprintBytes(int numInstances) {
        if (numInstances <= 0) {
            throw new IllegalArgumentException("Number of instances must be positive");
        }

        // Warm up and suggest GC to settle memory
        gcAndSleep();

        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

        // Prevent JIT/GC from cleaning this immediately by storing it in a persistent array
        SampleObject[] holdingArray = new SampleObject[numInstances];
        for (int i = 0; i < numInstances; i++) {
            holdingArray[i] = new SampleObject();
        }

        gcAndSleep();

        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long diff = usedMemoryAfter - usedMemoryBefore;

        // Keep references alive during measurement
        long hashSum = 0;
        for (SampleObject obj : holdingArray) {
            hashSum += obj.hashCode();
        }
        
        // Prevent compiler optimizer from discarding holdingArray
        if (hashSum == 0) {
            System.out.println("No objects allocated");
        }

        // Return average bytes per allocated instance
        return (double) diff / numInstances;
    }

    private static void gcAndSleep() {
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.gc();
    }

    public static class SampleObject {
        private int intVal = 42;
        private double doubleVal = 3.14159;
        private Object refVal = new Object();
    }
}
