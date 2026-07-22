package no12_exception_handling.practice.safe_resource_reader;

/**
 * Reference solution for SafeResourceReaderSolution.
 * 
 * Try-with-resources is a resource specification statement. Any object implementing
 * `java.lang.AutoCloseable` (or `java.io.Closeable`) can be used as a resource.
 * The JVM guarantees that the `close()` method is called on the resource as the code block exits,
 * regardless of whether the block completes normally or throws an exception.
 */
public class SafeResourceReaderSolution {

    public static String readData(MockResource resource) {
        // try-with-resources: resource is declared in parenthesis, guarantees close is called.
        try (MockResource res = resource) {
            return res.read();
        } catch (Exception e) {
            // Exception is caught safely, resource is already closed at this point.
            return "fallback-value";
        }
    }

    public static class MockResource implements AutoCloseable {
        private final String value;
        private final boolean throwExceptionOnRead;
        private boolean closed = false;

        public MockResource(String value, boolean throwExceptionOnRead) {
            this.value = value;
            this.throwExceptionOnRead = throwExceptionOnRead;
        }

        public String read() throws Exception {
            if (closed) {
                throw new IllegalStateException("Cannot read from a closed resource");
            }
            if (throwExceptionOnRead) {
                throw new Exception("Simulated read error");
            }
            return value;
        }

        @Override
        public void close() throws Exception {
            this.closed = true;
        }

        public boolean isClosed() {
            return closed;
        }
    }
}
