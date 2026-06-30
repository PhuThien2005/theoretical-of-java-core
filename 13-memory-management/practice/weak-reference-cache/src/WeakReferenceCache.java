import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.HashMap;

/**
 * A simple key-value cache utilizing WeakReferences to allow cache values
 * to be garbage collected when memory is low or no strong references remain.
 */
public class WeakReferenceCache<K, V> {

    private final Map<K, WeakReference<V>> cache = new HashMap<>();

    /**
     * Associates the specified value with the specified key in this cache.
     * The value must be stored inside a WeakReference.
     */
    public void put(K key, V value) {
        // TODO: Store value wrapped in a WeakReference. Handle null value appropriately.
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or null if this cache contains no mapping for the key or the value has been GC'ed.
     */
    public V get(K key) {
        // TODO: Retrieve the WeakReference, get the value, and return it.
        // Option: If the value has been garbage collected (referent is null), remove the key from the map.
        return null;
    }

    /**
     * Returns the number of key-value mappings in this cache.
     */
    public int size() {
        return cache.size();
    }

    /**
     * Clears all of the mappings from this cache.
     */
    public void clear() {
        cache.clear();
    }
}
