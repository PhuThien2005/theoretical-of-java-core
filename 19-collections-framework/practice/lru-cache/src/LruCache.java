import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A simple Least Recently Used (LRU) Cache extending LinkedHashMap.
 */
public class LruCache<K, V> extends LinkedHashMap<K, V> {

    private final int maxCapacity;

    /**
     * Initializes the LRU Cache with the specified maximum capacity.
     * Remember that LinkedHashMap needs to be initialized with access-order enabled (true).
     */
    public LruCache(int maxCapacity) {
        // TODO: Call superclass constructor with appropriate capacity, load factor (e.g. 0.75f),
        // and accessOrder set to true.
        super(16, 0.75f, false);
        this.maxCapacity = maxCapacity;
    }

    /**
     * Overrides removeEldestEntry to evict the eldest entry when the map size exceeds maxCapacity.
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // TODO: Return true if the eldest entry should be removed (i.e. size exceeds maxCapacity)
        return false;
    }
}
