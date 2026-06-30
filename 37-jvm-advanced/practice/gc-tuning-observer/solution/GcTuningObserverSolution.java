import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * Reference solution for GcTuningObserverSolution.
 * 
 * Garbage Collection monitoring:
 * - JVM exposes JMX Management Beans (`GarbageCollectorMXBean`) for GC statistics.
 * - `getCollectionCount()` returns number of collection runs.
 * - `getCollectionTime()` returns accumulated pause durations in milliseconds.
 * - `System.gc()` requests the JVM to run garbage collection (execution is JVM implementation dependent).
 */
public class GcTuningObserverSolution {

    public static long getGcCollectionCount() {
        long count = 0;
        List<GarbageCollectorMXBean> beans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean bean : beans) {
            long gcCount = bean.getCollectionCount();
            if (gcCount != -1) {
                count += gcCount;
            }
        }
        return count;
    }

    public static void generateMemoryPressureAndGc() {
        // Allocate temporary objects to generate garbage collection pressure
        for (int i = 0; i < 20_000; i++) {
            // Allocate transient double array (approx 800 bytes each)
            double[] temp = new double[100];
            temp[0] = i; // prevents JIT optimizations from discarding loop
        }

        // Suggest the JVM reclaims memory
        System.gc();

        // Pause briefly to let GC processing threads run
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
