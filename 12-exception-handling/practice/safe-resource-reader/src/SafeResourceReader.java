/**
 * A utility class to demonstrate try-with-resources and AutoCloseable.
 */
public class SafeResourceReader {

    /**
     * Reads from the provided MockResource using try-with-resources.
     * 
     * Requirements:
     * - The resource must be closed automatically using try-with-resources.
     * - Call resource.read().
     * - If an exception occurs, catch it and return "fallback-value".
     *
     * @param resource the resource to read from
     * @return the read string or "fallback-value" on exception
     */
    public static String readData(MockResource resource) {
        // TODO: Implement try-with-resources. Catch Exception and return "fallback-value".
        return null;
    }

    /**
     * A mock resource implementing AutoCloseable.
     */
    public static class MockResource implements AutoCloseable {
        private final String value;
        private final boolean throwExceptionOnRead;
        private boolean closed = false;

        public MockResource(String value, boolean throwExceptionOnRead) {
            this.value = value;
            this.throwExceptionOnRead = throwExceptionOnRead;
        }

        public String read() throws Exception {
            if (throwExceptionOnRead) {
                throw new Exception("Simulated read error");
            }
            return value;
        }

        @Override
        public void close() throws Exception {
            // TODO: Mark resource as closed
        }

        public boolean isClosed() {
            return closed;
        }
    }
}
