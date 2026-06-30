/**
 * A class demonstrating a thread-safe lazy-loaded Singleton pattern.
 */
public class ThreadSafeSingleton {

    // TODO: Declare a private static volatile instance variable of type ThreadSafeSingleton.
    
    private int counter;

    // TODO: Declare a private constructor to prevent external instantiation.
    public ThreadSafeSingleton() {
        // Leave public for now in starter if needed, but the instructions require private.
        // Let's make it public in starter to compile, but add TODO to make it private.
    }

    /**
     * Retrieves the single instance of ThreadSafeSingleton.
     * Uses double-checked locking.
     */
    public static ThreadSafeSingleton getInstance() {
        // TODO: Implement double-checked locking for thread-safe instantiation.
        return null;
    }

    public synchronized void increment() {
        counter++;
    }

    public synchronized int getCounter() {
        return counter;
    }
}
