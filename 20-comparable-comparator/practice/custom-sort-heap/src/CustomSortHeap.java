import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * A wrapper around PriorityQueue implementing a heap that supports either
 * natural ordering (Comparable) or custom ordering (Comparator).
 */
public class CustomSortHeap<T> {

    private final PriorityQueue<T> pq;

    /**
     * Initializes the heap using natural ordering.
     * Elements of type T must implement Comparable.
     */
    public CustomSortHeap() {
        // TODO: Initialize pq using default constructor (natural ordering)
        this.pq = null;
    }

    /**
     * Initializes the heap using a custom Comparator.
     */
    public CustomSortHeap(Comparator<? super T> comparator) {
        // TODO: Initialize pq passing the comparator
        this.pq = null;
    }

    public void add(T element) {
        // TODO: Add element to heap
    }

    public T poll() {
        // TODO: Remove and return the top element from heap
        return null;
    }

    public int size() {
        // TODO: Return current size
        return 0;
    }

    public boolean isEmpty() {
        // TODO: Return if heap is empty
        return false;
    }
}
