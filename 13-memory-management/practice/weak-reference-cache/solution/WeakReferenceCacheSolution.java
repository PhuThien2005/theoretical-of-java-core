import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.HashMap;

/**
 * Reference solution for WeakReferenceCacheSolution.
 * 
 * Modifiers & API:
 * - `WeakReference` allows its referent to be GC'ed if there are no strong references remaining to it.
 * - This prevents the cache itself from being a memory leak source.
 */
public class WeakReferenceCacheSolution<K, V> {

    private final Map<K, WeakReference<V>> cache = new HashMap<>();

    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (value == null) {
            cache.remove(key);
        } else {
            cache.put(key, new WeakReference<>(value));
        }
    }

    public V get(K key) {
        WeakReference<V> ref = cache.get(key);
        if (ref == null) {
            return null;
        }

        V value = ref.get();
        if (value == null) {
            // Clean up: Value has been collected, remove entry from map
            cache.remove(key);
            return null;
        }
        return value;
    }

    public int size() {
        return cache.size();
    }

    public void clear() {
        cache.clear();
    }
}
