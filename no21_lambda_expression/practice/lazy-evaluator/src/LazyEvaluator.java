package no21_lambda_expression.practice.lazy_evaluator;

import java.util.function.Supplier;

/**
 * Starter template for a lazy evaluation wrapper.
 */
public class LazyEvaluator {
    // Wrapper class
}

/**
 * Wraps a computation defined by a Supplier to delay its execution
 * until its value is explicitly requested, caching the result.
 */
class Lazy<T> {
    private final Supplier<T> supplier;
    private T value;
    private boolean evaluated = false;

    public Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * Retrieves the evaluated value.
     * On the first invocation, this should trigger the supplier and cache the result.
     * Subsequent calls should return the cached value directly without re-running the supplier.
     */
    public T get() {
        // TODO: Implement thread-safe (optional but good) or basic lazy evaluation and caching.
        return null;
    }

    public boolean isEvaluated() {
        return evaluated;
    }
}
