import java.util.function.Supplier;

/**
 * Reference solution for LazyEvaluatorSolution.
 * 
 * Lazy evaluation:
 * - `Supplier<T>` is a functional interface representing a producer of values.
 * - The lambda `() -> heavyComputation()` is passed as a Supplier, but NOT executed immediately.
 * - Calling `supplier.get()` triggers execution.
 * - We cache/memoize the output so that expensive work runs exactly once.
 */
public class LazyEvaluatorSolution {
    // Wrapper class
}

class Lazy<T> {
    private final Supplier<T> supplier;
    private T value;
    private boolean evaluated = false;

    public Lazy(Supplier<T> supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier cannot be null");
        }
        this.supplier = supplier;
    }

    /**
     * Synchronized getter to guarantee thread-safe lazy initialization and caching.
     */
    public synchronized T get() {
        if (!evaluated) {
            value = supplier.get();
            evaluated = true;
        }
        return value;
    }

    public synchronized boolean isEvaluated() {
        return evaluated;
    }
}
