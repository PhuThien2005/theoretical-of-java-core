import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * Reference solution for DeadlockSimulatorSolution.
 * 
 * Deadlocks:
 * - A deadlock is created when locks are acquired in different orders by different threads (Lock Ordering bug).
 * - `ManagementFactory.getThreadMXBean()` is a platform management bean for the thread system of the Java Virtual Machine.
 * - `.findDeadlockedThreads()` detects cycles of threads that are in deadlock waiting to acquire locks.
 */
public class DeadlockSimulatorSolution {

    public static boolean runDeadlockCheck(Object lock1, Object lock2) throws InterruptedException {
        if (lock1 == null || lock2 == null) {
            return false;
        }

        // Thread 1: locks lock1 then lock2
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                try {
                    // Force context switch so Thread 2 can acquire lock2
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                synchronized (lock2) {
                    // Do work
                }
            }
        });

        // Thread 2: locks lock2 then lock1
        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                synchronized (lock1) {
                    // Do work
                }
            }
        });

        // Mark threads as daemon so they exit when the JVM exits
        t1.setDaemon(true);
        t2.setDaemon(true);

        t1.start();
        t2.start();

        // Give them time to deadlock
        Thread.sleep(250);

        // Detect deadlock
        ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreadIds = mxBean.findDeadlockedThreads();

        return deadlockedThreadIds != null && deadlockedThreadIds.length > 0;
    }
}
