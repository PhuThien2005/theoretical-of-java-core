/**
 * Reference solution for ThreadSafeSingletonSolution.
 * 
 * Modifiers explained:
 * - `private` constructor prevents direct initialization from external classes.
 * - `static` ensures the instance and getter belong to the class, not instances.
 * - `volatile` is critical for thread safety in double-checked locking: it prevents instruction
 *   reordering by the compiler/JVM, ensuring that the helper object is fully constructed
 *   before its reference is written to the variable.
 * - `synchronized` on block ensures that only one thread can execute instantiation at a time.
 */
public class ThreadSafeSingletonSolution {

    // Volatile variable ensures memory writes are visible immediately to other threads.
    private static volatile ThreadSafeSingletonSolution instance;

    private int counter = 0;

    // Private constructor prevents instantiation.
    private ThreadSafeSingletonSolution() {
    }

    /**
     * Thread-safe lazy initialization using Double-Checked Locking.
     */
    public static ThreadSafeSingletonSolution getInstance() {
        // First check (no synchronization for high performance once initialized)
        if (instance == null) {
            // Synchronize class object to serialize thread access
            synchronized (ThreadSafeSingletonSolution.class) {
                // Second check (confirm no other thread instantiated it while we waited for the lock)
                if (instance == null) {
                    instance = new ThreadSafeSingletonSolution();
                }
            }
        }
        return instance;
    }

    public synchronized void increment() {
        counter++;
    }

    public synchronized int getCounter() {
        return counter;
    }
}
