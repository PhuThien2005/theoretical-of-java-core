package no19_collections_framework.practice.lru_cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Reference solution for LruCacheSolution.
 * 
 * LinkedHashMap features:
 * - When `accessOrder` is true, calling `get` or `put` moves the accessed element to the end of the map.
 * - The eldest element (least recently accessed) is kept at the front.
 * - `removeEldestEntry` is evaluated after each `put` invocation; if it returns true, the head of the map is evicted.
 */
public class LruCacheSolution<K, V> extends LinkedHashMap<K, V> {

    private final int maxCapacity;

    public LruCacheSolution(int maxCapacity) {
        // Initial capacity = maxCapacity, load factor = 0.75f, accessOrder = true
        super(maxCapacity, 0.75f, true);
        this.maxCapacity = maxCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // Evict eldest entry when cache grows beyond capacity limit
        return size() > maxCapacity;
    }
}
