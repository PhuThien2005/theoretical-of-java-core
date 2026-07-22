package no13_memory_management.practice.memory_leak_simulator;

import java.util.List;
import java.util.ArrayList;

/**
 * Reference solution for MemoryLeakSimulatorSolution.
 * 
 * In Java, the Garbage Collector automatically reclaims objects that are no longer referenced.
 * However, if a reference to an unused object is held by a "GC Root" (such as a static field),
 * the GC cannot reclaim it. This is a classic memory leak.
 * 
 * If memory leaks continue, the JVM will exhaust its Heap memory and throw `java.lang.OutOfMemoryError`.
 */
public class MemoryLeakSimulatorSolution {

    private static final List<byte[]> leakContainer = new ArrayList<>();

    public static boolean triggerOom() {
        try {
            while (true) {
                // Allocate 1MB blocks continuously
                leakContainer.add(new byte[1024 * 1024]);
            }
        } catch (OutOfMemoryError oom) {
            // Recover: Clear the container to release strong references, making them eligible for GC
            leakContainer.clear();
            
            // Suggest GC to clean up heap immediately to regain stability
            System.gc();
            return true;
        }
    }

    public static int getLeakedCount() {
        return leakContainer.size();
    }
}
