import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Reference solution for CustomIterableStackSolution.
 * 
 * Non-static inner class concept:
 * - `StackIterator` is a non-static inner class of `CustomIterableStackSolution`.
 * - It has an implicit reference to the outer stack instance.
 * - This allows it to read the private `elements` array and `size` variable directly
 *   using `CustomIterableStackSolution.this.elements` or simply `elements`.
 */
public class CustomIterableStackSolution<T> implements Iterable<T> {

    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public CustomIterableStackSolution() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public void push(T item) {
        if (size == elements.length) {
            grow();
        }
        elements[size++] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (size == 0) {
            throw new java.util.EmptyStackException();
        }
        T item = (T) elements[--size];
        elements[size] = null; // Prevent memory leak
        return item;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void grow() {
        Object[] newElements = new Object[elements.length * 2];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator();
    }

    /**
     * Non-static inner class.
     * Note: Cannot define static fields or static methods inside a non-static inner class prior to Java 16.
     */
    private class StackIterator implements Iterator<T> {
        // Starts at the top element (size - 1)
        private int cursor = size - 1;

        @Override
        public boolean hasNext() {
            return cursor >= 0;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in stack");
            }
            // Directly accesses outer class's elements array
            T item = (T) elements[cursor];
            cursor--;
            return item;
        }
    }
}
