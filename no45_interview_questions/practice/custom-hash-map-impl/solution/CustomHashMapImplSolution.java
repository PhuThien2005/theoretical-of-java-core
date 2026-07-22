package customhashmapimpl;

public class CustomHashMapImplSolution<K, V> {

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

    private final Node<K, V>[] buckets;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    public CustomHashMapImplSolution() {
        // Generic arrays cannot be directly created in Java due to type erasure, 
        // so we instantiate a raw array and cast it to the generic array type.
        this.buckets = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
        this.size = 0;
    }

    private int getIndex(K key) {
        if (key == null) {
            return 0; // Null keys are always stored in the first bucket.
        }
        // Use Math.abs to handle negative hashCodes safely.
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public void put(K key, V value) {
        int index = getIndex(key);
        Node<K, V> head = buckets[index];
        Node<K, V> curr = head;

        // Traverse the chain to check if the key already exists
        while (curr != null) {
            if (isKeyEqual(curr.key, key)) {
                curr.value = value; // Update value if key exists
                return;
            }
            curr = curr.next;
        }

        // Key doesn't exist, prepend the new node to the bucket chain
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = head;
        buckets[index] = newNode;
        size++;
    }

    public V get(K key) {
        int index = getIndex(key);
        Node<K, V> curr = buckets[index];

        while (curr != null) {
            if (isKeyEqual(curr.key, key)) {
                return curr.value;
            }
            curr = curr.next;
        }
        return null;
    }

    public V remove(K key) {
        int index = getIndex(key);
        Node<K, V> curr = buckets[index];
        Node<K, V> prev = null;

        while (curr != null) {
            if (isKeyEqual(curr.key, key)) {
                if (prev == null) {
                    // Node is the head of the bucket list
                    buckets[index] = curr.next;
                } else {
                    // Node is in the middle or end of the chain
                    prev.next = curr.next;
                }
                size--;
                return curr.value;
            }
            prev = curr;
            curr = curr.next;
        }
        return null;
    }

    public int size() {
        return size;
    }

    private boolean isKeyEqual(K k1, K k2) {
        if (k1 == null && k2 == null) {
            return true;
        }
        if (k1 == null || k2 == null) {
            return false;
        }
        return k1.equals(k2);
    }
}
