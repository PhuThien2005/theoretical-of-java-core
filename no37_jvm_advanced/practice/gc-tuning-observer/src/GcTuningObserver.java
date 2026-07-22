package no37_jvm_advanced.practice.gc_tuning_observer;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * Starter template for observing Garbage Collection statistics using MXBeans.
 */
public class GcTuningObserver {

    /**
     * Queries the JVM's GarbageCollectorMXBeans to retrieve the total accumulated
     * garbage collection count across all collectors.
     */
    public static long getGcCollectionCount() {
        // TODO: Get ManagementFactory.getGarbageCollectorMXBeans() and sum getCollectionCount()
        return -1; // Return negative by default to indicate uninitialized state
    }

    /**
     * Generates transient memory pressure by allocating short-lived objects,
     * dereferencing them, and explicitly triggering system garbage collection.
     */
    public static void generateMemoryPressureAndGc() {
        // TODO: Allocate a large loop of temporary object arrays (e.g. 500,000 arrays)
        // TODO: Explicitly call System.gc() to suggest JVM runs GC
    }
}
