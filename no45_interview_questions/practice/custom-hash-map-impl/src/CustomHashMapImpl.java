package customhashmapimpl;

public class CustomHashMapImpl<K, V> {

    public static class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    // TODO: Declare bucket array (e.g. Node<K,V>[]) and size field.

    public CustomHashMapImpl() {
        // TODO: Initialize the bucket array with a default size (e.g. 16).
    }

    public void put(K key, V value) {
        // TODO: Map key to bucket index using hashCode.
        // TODO: Handle null keys (map to index 0).
        // TODO: If key already exists in the chain, update the value.
        // TODO: If key is new, add a new Node and increment size.
    }

    public V get(K key) {
        // TODO: Map key to bucket index.
        // TODO: Traverse the chain to find key and return its value.
        // Return null if not found.
        return null;
    }

    public V remove(K key) {
        // TODO: Map key to bucket index.
        // TODO: Traverse the chain, remove the node matching key, decrement size, and return the removed value.
        // Return null if key is not found.
        return null;
    }

    public int size() {
        // TODO: Return map size.
        return 0;
    }
}
