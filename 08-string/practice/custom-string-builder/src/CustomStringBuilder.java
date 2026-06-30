/**
 * A simplified, custom implementation of StringBuilder backed by a character array.
 */
public class CustomStringBuilder {

    private char[] buffer;
    private int size;

    /**
     * Creates a CustomStringBuilder with a default initial capacity of 16.
     */
    public CustomStringBuilder() {
        // TODO: Initialize buffer with capacity 16 and size to 0
    }

    /**
     * Creates a CustomStringBuilder with the specified initial capacity.
     */
    public CustomStringBuilder(int initialCapacity) {
        // TODO: Initialize buffer with the given capacity
    }

    /**
     * Appends a String to this builder.
     * If the string is null, append the characters "null".
     */
    public CustomStringBuilder append(String str) {
        // TODO: Implement append. Ensure capacity is grown if needed.
        return this;
    }

    /**
     * Appends a character to this builder.
     */
    public CustomStringBuilder append(char c) {
        // TODO: Implement character append. Ensure capacity is grown if needed.
        return this;
    }

    /**
     * Returns the current number of characters in the builder.
     */
    public int length() {
        // TODO: Return current size
        return 0;
    }

    /**
     * Returns the current capacity of the internal buffer.
     */
    public int capacity() {
        // TODO: Return buffer capacity
        return 0;
    }

    /**
     * Returns the accumulated string.
     */
    @Override
    public String toString() {
        // TODO: Convert buffer content (up to size) to String
        return null;
    }
}
