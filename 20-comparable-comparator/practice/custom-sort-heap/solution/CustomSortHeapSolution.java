import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Reference solution for CustomSortHeapSolution.
 * 
 * PriorityQueue sorting features:
 * - Natural Ordering: elements added must implement `Comparable` so they can compare themselves.
 * - Custom Comparator: if provided, PriorityQueue delegates comparison checks to the comparator
 *   instead of calling `Comparable.compareTo()`.
 */
public class CustomSortHeapSolution<T> {

    private final PriorityQueue<T> pq;

    /**
     * Initializes the heap using natural ordering.
     */
    public CustomSortHeapSolution() {
        // Natural ordering relies on elements implementing Comparable
        this.pq = new PriorityQueue<>();
    }

    /**
     * Initializes the heap using a custom Comparator.
     */
    public CustomSortHeapSolution(Comparator<? super T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        // Expose comparator to priority queue
        this.pq = new PriorityQueue<>(11, comparator);
    }

    public void add(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
        pq.add(element);
    }

    public T poll() {
        return pq.poll();
    }

    public int size() {
        return pq.size();
    }

    public boolean isEmpty() {
        return pq.isEmpty();
    }
}
