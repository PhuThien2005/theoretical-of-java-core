package no15_inner_nested_class.practice.custom_iterable_stack;

import java.util.Iterator;

/**
 * An array-backed stack implementing Iterable.
 */
public class CustomIterableStack<T> implements Iterable<T> {

    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public CustomIterableStack() {
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
        // TODO: Return a new instance of StackIterator (non-static inner class)
        return null;
    }

    /**
     * Non-static inner class representing the Iterator.
     * It should traverse the stack elements from top to bottom (reverse order).
     */
    private class StackIterator implements Iterator<T> {
        // TODO: Declare a cursor field starting at the top of the stack (size - 1)

        @Override
        public boolean hasNext() {
            // TODO: Return true if there are more elements (cursor >= 0)
            return false;
        }

        @Override
        public T next() {
            // TODO: Return the next item (elements[cursor]) and decrement cursor.
            // Throw java.util.NoSuchElementException if there are no more elements.
            return null;
        }
    }
}
