/**
 * Reference solution for ThreadSafeCounterSolution.
 * 
 * Synchronized keyword:
 * - `synchronized` ensures only one thread can execute the method on the same object instance at a time.
 * - This provides mutual exclusion and memory visibility, preventing thread interference (race conditions).
 */
public class ThreadSafeCounterSolution {

    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}
