import java.util.List;
import java.util.ArrayList;

/**
 * A utility class to simulate a memory leak leading to an OutOfMemoryError.
 */
public class MemoryLeakSimulator {

    // Static collection to hold references (GC Roots prevent GC from reclaiming them)
    private static final List<byte[]> leakContainer = new ArrayList<>();

    /**
     * Intentionally leaks memory by allocating large byte arrays and adding them to the 
     * static `leakContainer` in an infinite loop, until an OutOfMemoryError is thrown.
     * 
     * When the OutOfMemoryError is thrown:
     * 1. Catch it.
     * 2. Clear `leakContainer` to release the memory.
     * 3. Return true.
     *
     * @return true if OutOfMemoryError was successfully triggered and recovered from
     */
    public static boolean triggerOom() {
        // TODO: Implement the OOM trigger loop. Catch OutOfMemoryError, clear the list, and return true.
        return false;
    }

    /**
     * Returns the number of items currently leaked.
     */
    public static int getLeakedCount() {
        return leakContainer.size();
    }
}
