# Exercise: Custom HashMap Implementation

## Objective
Implement a minimal working HashMap from scratch using bucket arrays and linked-list chaining for collision resolution.

## Requirements
Implement a class `CustomHashMapImpl<K, V>` that supports the following operations:
1. **Buckets**:
   - Maintain an array of `Node<K, V>` buckets of a fixed size (e.g. 16).
   - A `Node` should contain `K key`, `V value`, and `Node<K, V> next`.

2. **Operations**:
   - `void put(K key, V value)`:
     - Compute the bucket index: `Math.abs(key.hashCode()) % bucketArray.length` (handle `null` keys by assigning them to index `0` or throw `IllegalArgumentException` - let's allow `null` keys or just throw if simple. To follow standard `HashMap`, let's allow `null` keys by mapping them to index `0`).
     - Traverse the linked list at that index:
       - If the key already exists (use `equals()` for comparison), update its value.
       - If the key does not exist, append a new node to the head or tail of the list. Increment `size`.
   - `V get(K key)`:
     - Compute index, search the chain, and return the value if key is found. Return `null` otherwise.
   - `V remove(K key)`:
     - Search the chain, remove the node, decrement `size`, and return the removed value (or `null` if not found).
   - `int size()`:
     - Return the number of key-value pairs stored in the map.
     - Note: You do not need to implement rehashing/resizing for this exercise.
