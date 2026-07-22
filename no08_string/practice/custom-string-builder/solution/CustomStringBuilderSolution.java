package no08_string.practice.custom_string_builder;

/**
 * Reference solution for CustomStringBuilderSolution.
 */
public class CustomStringBuilderSolution {

    private char[] buffer;
    private int size;

    public CustomStringBuilderSolution() {
        this(16);
    }

    public CustomStringBuilderSolution(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative: " + initialCapacity);
        }
        buffer = new char[initialCapacity];
        size = 0;
    }

    public CustomStringBuilderSolution append(String str) {
        if (str == null) {
            str = "null";
        }
        int len = str.length();
        ensureCapacity(size + len);
        
        // Copy characters from string into the buffer
        for (int i = 0; i < len; i++) {
            buffer[size + i] = str.charAt(i);
        }
        size += len;
        
        return this;
    }

    public CustomStringBuilderSolution append(char c) {
        ensureCapacity(size + 1);
        buffer[size] = c;
        size++;
        return this;
    }

    public int length() {
        return size;
    }

    public int capacity() {
        return buffer.length;
    }

    @Override
    public String toString() {
        // Construct a new String containing the active characters in the buffer
        return new String(buffer, 0, size);
    }

    /**
     * Checks if the buffer can accommodate the minimum capacity.
     * If not, it allocates a new larger array, copies elements, and updates the reference.
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > buffer.length) {
            // Grow capacity by doubling it, or matching the required minCapacity if doubling isn't enough
            int newCapacity = buffer.length * 2;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            
            char[] newBuffer = new char[newCapacity];
            System.arraycopy(buffer, 0, newBuffer, 0, size);
            buffer = newBuffer;
        }
    }
}
